/*
 *   Copyright (C) 2006 by JSC Telasi
 *   http://www.telasi.ge
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the
 *   Free Software Foundation, Inc.,
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package telasi.recutil.gui.comp.recalc.item;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.recalc.RecalcItemRestoreOriginalRequest;

public class RecalcItemChangesPane extends Composite {

	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblDescription;
	private Label lblImage;
	private Label label1;
	private RecalcItem item;
	private ToolItem tiRestore;
	private TableViewer viewer;
	private ToolBar toolBar1;
	private TableColumn colCurrent;
	private TableColumn colOriginal;
	private TableColumn colFiled;
	private Composite composite3;

	public RecalcItemChangesPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(600, 326);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblTitle = new Label(composite1, SWT.NONE);
					lblTitle.setText(GUIMessages
							.getMessage("comp.item_changes_pane.title"));
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
				}
			}
			{
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.grabExcessHorizontalSpace = true;
				label1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				label1.setLayoutData(label1LData);
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 48;
					lblImageLData.widthHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/changes.png"));
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.item_changes_pane.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite3, SWT.BORDER
							| SWT.VIRTUAL | SWT.FULL_SELECTION | SWT.MULTI);
					viewer.getControl().setLayoutData(viewerLData);
					viewer.getTable().setLinesVisible(true);
					viewer.getTable().setHeaderVisible(true);
					viewer.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							validateView();
						}
					});
					{
						colFiled = new TableColumn(viewer.getTable(), SWT.NONE);
						colFiled.setText(GUIMessages
								.getMessage("comp.item_changes_pane.field"));
						colFiled.setWidth(150);
					}
					{
						colOriginal = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colOriginal.setText(GUIMessages
								.getMessage("comp.item_changes_pane.original"));
						colOriginal.setWidth(150);
					}
					{
						colCurrent = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colCurrent.setText(GUIMessages
								.getMessage("comp.item_changes_pane.current"));
						colCurrent.setWidth(150);
					}
				}
			}
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiRestore = new ToolItem(toolBar1, SWT.NONE);
					tiRestore.setImage(Plugin
							.getImage("icons/22x22/enable.png"));
					tiRestore
							.setToolTipText(GUIMessages
									.getMessage("comp.item_changes_pane.remove_change"));
					tiRestore.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							onRestoreOriginals();
						}
					});
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setItem(RecalcItem item) {
		this.item = item;
		reset();
	}

	private void initDataBehaivour() {
		reset();
		viewer.setInput(this);
		viewer.setLabelProvider(new ChangesLabelProvider());
	}

	private void reset() {
		List changes = item == null ? null : item.getChanges();
		viewer.setItemCount(changes == null ? 0 : changes.size());
		viewer.setContentProvider(new CommonItemContentProvider(changes));
		validateView();
	}

	private class ChangesLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {

			RecalcItem.Changes change = (RecalcItem.Changes) element;
			if (change == null) {
				return "";
			}

			switch (columnIndex) {
			case 0:
				switch (change.getCode()) {
				case RecalcItem.CHANGE_CYCLE:
					return GUIMessages.getMessage("comp.general.cycle");
				case RecalcItem.CHANGE_ENTERDATE:
					return GUIMessages.getMessage("comp.general.enterdate");
				case RecalcItem.CHANGE_ITEMDATE:
					return GUIMessages.getMessage("comp.general.itemdate");
				case RecalcItem.CHANGE_GEL:
					return GUIMessages.getMessage("comp.general.gel");
				case RecalcItem.CHANGE_KWH:
					return GUIMessages.getMessage("comp.general.kwh");
				case RecalcItem.CHANGE_READING:
					return GUIMessages.getMessage("comp.general.reading");
				case RecalcItem.CHANGE_OPERATION:
					return GUIMessages.getMessage("comp.general.operation");
				default:
					return "";
				}
			case 1:
				switch (change.getCode()) {
				case RecalcItem.CHANGE_CYCLE:
					Boolean orig = (Boolean) change.getOriginal();
					return orig.toString();
				case RecalcItem.CHANGE_ENTERDATE:
				case RecalcItem.CHANGE_ITEMDATE:
					Date date = (Date) change.getOriginal();
					return Date.format(date);
				case RecalcItem.CHANGE_GEL:
				case RecalcItem.CHANGE_KWH:
				case RecalcItem.CHANGE_READING:
					Double num = (Double) change.getOriginal();
					return nf.format(num.doubleValue());
				case RecalcItem.CHANGE_OPERATION:
					Operation oper = (Operation) change.getOriginal();
					Operation oper2 = null;
					try {
						oper2 = Cache.findOperationById(oper.getId());
					} catch (Throwable t) {
					}
					return oper2 == null ? String.valueOf(oper.getId())
							: GUITranslator.GEO_ASCII_TO_KA(oper2.getName());
				default:
					return "";
				}
			case 2:
				switch (change.getCode()) {
				case RecalcItem.CHANGE_CYCLE:
					Boolean orig = (Boolean) change.getCurrent();
					return orig.toString();
				case RecalcItem.CHANGE_ENTERDATE:
				case RecalcItem.CHANGE_ITEMDATE:
					Date date = (Date) change.getCurrent();
					return Date.format(date);
				case RecalcItem.CHANGE_GEL:
				case RecalcItem.CHANGE_KWH:
				case RecalcItem.CHANGE_READING:
					Double num = (Double) change.getCurrent();
					return nf.format(num.doubleValue());
				case RecalcItem.CHANGE_OPERATION:
					Operation oper = (Operation) change.getCurrent();
					Operation oper2 = null;
					try {
						oper2 = Cache.findOperationById(oper.getId());
					} catch (Throwable t) {
					}
					return oper2 == null ? String.valueOf(oper.getId())
							: GUITranslator.GEO_ASCII_TO_KA(oper2.getName());
				default:
					return "";
				}
			default:
				return "";
			}

		}

	}

	@SuppressWarnings("unchecked")
	public List getSelectedChanges() {
		int indecies[] = viewer.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		List changes = new ArrayList();
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		for (int i = 0; i < indecies.length; i++) {
			int index = indecies[i];
			changes.add(provider.items.get(index));
		}
		return changes;
	}

	private void validateView() {
		List selection = getSelectedChanges();
		boolean selected = selection != null && !selection.isEmpty();
		tiRestore.setEnabled(selected);
	}

	@SuppressWarnings("unchecked")
	private void onRestoreOriginals() {
		List selection = getSelectedChanges();
		if (selection == null || selection.isEmpty()) {
			return;
		}
		boolean resp = MessageDialog.openQuestion(getShell(), GUIMessages
				.getMessage("comp.general.confirm"), GUIMessages.getMessage(
				"comp.item_changes_pane.confirm_restore",
				new Object[] { new Integer(selection.size()) }));
		if (!resp) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		List tasks = new ArrayList();
		for (int i = 0; i < selection.size(); i++) {
			RecalcItem.Changes change = (RecalcItem.Changes) selection.get(i);
			RecalcItemRestoreOriginalRequest task = new RecalcItemRestoreOriginalRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setRecalcItem(item);
			task.setOption(change.getCode());
			tasks.add(task);
		}
		try {
			DefaultRecutilClient.processRequests(tasks);
			for (int i = 0; i < tasks.size(); i++) {
				RecalcItemRestoreOriginalRequest request = (RecalcItemRestoreOriginalRequest) tasks
						.get(i);
				restoreOriginal(request.getOption());
			}
			reset();
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	private void restoreOriginal(int seq) {
		switch (seq) {
		case RecalcItem.CHANGE_CYCLE:
			((RecalcItem) item).setCycle(item.getOriginalCycle());
			break;
		case RecalcItem.CHANGE_ENTERDATE:
			((RecalcItem) item).setEnterDate(item.getOriginalEnterDate());
			break;
		case RecalcItem.CHANGE_GEL:
			((RecalcItem) item).setGel(item.getOriginalGel());
			break;
		case RecalcItem.CHANGE_ITEMDATE:
			((RecalcItem) item).setItemDate(item.getOriginalItemDate());
			break;
		case RecalcItem.CHANGE_KWH:
			((RecalcItem) item).setKwh(item.getOriginalKwh());
			break;
		case RecalcItem.CHANGE_OPERATION:
			Operation oper = item.getOriginalOperation();
			try {
				oper = Cache.findOperationById(oper.getId());
			} catch (Throwable t) {
			}
			((RecalcItem) item).setOperation(oper);
			break;
		case RecalcItem.CHANGE_READING:
			((RecalcItem) item).setReading(item.getOriginalReading());
			break;
		}
	}

}
