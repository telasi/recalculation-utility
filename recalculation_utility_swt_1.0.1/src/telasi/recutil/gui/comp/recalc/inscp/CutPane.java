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
package telasi.recutil.gui.comp.recalc.inscp;


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
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcCutItem;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.recalc.RecalcCutDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcCutMoveRequest;
import telasi.recutil.service.eclipse.recalc.RecalcCutSelectRequest;

public class CutPane extends Composite {

	private TableColumn colEnd;
	private TableColumn colStartDate;
	private TableColumn colImage;
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private TableViewer viewer;
	private ToolItem tiRefresh;
	private ToolBar toolBar1;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label label1;
	private Recalc recalc;
	private ToolItem tiDown;
	private ToolItem tiUp;
	private ToolItem toolItem2;
	private ToolItem toolItem1;
	private ToolItem tiProperties;
	private ToolItem tiDelete;
	private ToolItem tiNew;

	public CutPane(Composite parent, int style) {
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
			this.setSize(461, 324);
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
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setText(GUIMessages
							.getMessage("comp.cut_pane.title"));
					lblTitle.setFont(GUIUtils.createSubtitleFont(lblTitle
							.getFont()));
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
					lblImageLData.heightHint = 32;
					lblImageLData.widthHint = 32;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/32x32/cut.png"));
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages
							.getMessage("comp.cut_pane.descr"));
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
					viewer = new TableViewer(composite3, SWT.BORDER | SWT.MULTI
							| SWT.FULL_SELECTION | SWT.VIRTUAL);
					viewer.getControl().setLayoutData(viewerLData);
					viewer.getTable().setHeaderVisible(true);
					viewer.getTable().setLinesVisible(true);
					viewer
							.addSelectionChangedListener(new ISelectionChangedListener() {
								public void selectionChanged(
										SelectionChangedEvent event) {
									validateView();
								}
							});
				}
				{
					colImage = new TableColumn(viewer.getTable(), SWT.NONE);
					colImage.setWidth(20);
					colImage.setResizable(false);
				}
				{
					colStartDate = new TableColumn(viewer.getTable(), SWT.NONE);
					colStartDate.setText(GUIMessages
							.getMessage("comp.general.start_date"));
					colStartDate.setWidth(100);
				}
				{
					colEnd = new TableColumn(viewer.getTable(), SWT.NONE);
					colEnd.setText(GUIMessages
							.getMessage("comp.general.end_date"));
					colEnd.setWidth(100);
				}
			}
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiRefresh = new ToolItem(toolBar1, SWT.NONE);
					tiRefresh.setToolTipText(GUIMessages
							.getMessage("comp.general.refresh"));
					tiRefresh.setImage(Plugin
							.getImage("icons/22x22/refresh.png"));
					tiRefresh.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							refreshInternal();
							validateView();
						}
					});
				}
				{
					toolItem1 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem1.setToolTipText("");
				}
				{
					tiNew = new ToolItem(toolBar1, SWT.NONE);
					tiNew.setImage(Plugin.getImage("icons/22x22/new.gif"));
					tiNew.setToolTipText(GUIMessages
							.getMessage("comp.cut_pane.new"));
					tiNew.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onNew();
						}
					});
				}
				{
					tiDelete = new ToolItem(toolBar1, SWT.NONE);
					tiDelete.setImage(Plugin.getImage("icons/22x22/trash.png"));
					tiDelete.setToolTipText(GUIMessages
							.getMessage("comp.cut_pane.delete"));
					tiDelete.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onDelete();
						}
					});
				}
				{
					tiProperties = new ToolItem(toolBar1, SWT.NONE);
					tiProperties.setImage(Plugin
							.getImage("icons/22x22/properties.gif"));
					tiProperties.setToolTipText(GUIMessages
							.getMessage("comp.cut_pane.prop"));
					tiProperties.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onProperties();
						}
					});
				}
				{
					toolItem2 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem2.setToolTipText("");
				}
				{
					tiUp = new ToolItem(toolBar1, SWT.NONE);
					tiUp.setImage(Plugin.getImage("icons/22x22/up.png"));
					tiUp.setToolTipText(GUIMessages
							.getMessage("comp.cut_pane.up"));
					tiUp.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onMove(true);
						}
					});
				}
				{
					tiDown = new ToolItem(toolBar1, SWT.NONE);
					tiDown.setImage(Plugin.getImage("icons/22x22/down.png"));
					tiDown.setToolTipText(GUIMessages
							.getMessage("comp.cut_pane.down"));
					tiDown.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onMove(false);
						}
					});
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public void refresh() {
		refreshInternal();
		validateView();
	}

	@SuppressWarnings("unchecked")
	private void refreshInternal() {
		if (recalc == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcCutSelectRequest request = new RecalcCutSelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRecalc(recalc);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			RecalcCutSelectRequest callback = (RecalcCutSelectRequest) resp
					.getRequest();
			List items = callback.getCuts();
			if (items != null) {
				for (int i = 0; i < items.size(); i++) {
					RecalcCutItem item = (RecalcCutItem) items.get(i);
					item.setDetails(recalc.getDetails());
				}
			}
			recalc.getDetails().getCuts().clear();
			if (items != null) {
				recalc.getDetails().getCuts().addAll(items);
			}
			displayItems(items);
		} catch (Throwable t) {
			t.printStackTrace();
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
		viewer.getTable().deselectAll();
		// validateView();
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void clear() {
		recalc = null;
		displayItems(null);
		validateView();
	}

	private void displayItems(List items) {
		viewer.setItemCount(items == null ? 0 : items.size());
		viewer.setContentProvider(new CommonItemContentProvider(items));
	}

	private void validateView() {
		boolean hasRecalc = recalc != null;
		RecalcCutItem item = getSelectedItem();
		boolean selected = hasRecalc && item != null;
		tiRefresh.setEnabled(hasRecalc);
		tiNew.setEnabled(hasRecalc);
		tiDelete.setEnabled(selected);
		tiProperties.setEnabled(selected);
		tiUp.setEnabled(selected);
		tiDown.setEnabled(selected);
	}

	public RecalcCutItem getSelectedItem() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		return (RecalcCutItem) provider.items.get(index);
	}

	@SuppressWarnings("unchecked")
	public List getSelectedItems() {
		int indecies[] = viewer.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		List items = new ArrayList();
		for (int i = 0; i < indecies.length; i++) {
			int index = indecies[i];
			items.add(provider.items.get(index));
		}
		return items;
	}

	private void selectById(long id) {
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		if (provider.items != null) {
			for (int i = 0; i < provider.items.size(); i++) {
				RecalcCutItem item = (RecalcCutItem) provider.items.get(i);
				if (item.getId() == id) {
					viewer.getTable().select(i);
					viewer.getTable().showSelection();
					break;
				}
			}
		}
	}

	private void initDataBehaivour() {
		displayItems(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new CutLabelProvider());
	}

	private class CutLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Plugin.getImage("icons/16x16/cut.png");
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			RecalcCutItem item = (RecalcCutItem) element;
			switch (columnIndex) {
			case 1:
				return item == null ? "" : Date.format(item.getStartDate());
			case 2:
				return item == null ? "" : Date.format(item.getEndDate());
			default:
				return "";
			}

		}

	}

	private void onProperties() {
		RecalcCutItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		CutProperties prop = new CutProperties(getShell(), SWT.NONE);
		prop.setItem(item);
		prop.open();
		if (prop.isApproved()) {
			refreshInternal();
			selectById(prop.getItem().getId());
			validateView();
		}
	}

	private void onNew() {
		CutProperties prop = new CutProperties(getShell(), SWT.NONE);
		prop.setRecalculation(recalc);
		RecalcCutItem item = getSelectedItem();
		int seq = -1;
		if (item != null) {
			seq = item.getDetails().getCuts().indexOf(item);
		}
		prop.setSequence(seq);
		prop.open();
		if (prop.isApproved()) {
			refreshInternal();
			selectById(prop.getItem().getId());
			validateView();
		}
	}

	@SuppressWarnings("unchecked")
	private void onDelete() {
		List items = getSelectedItems();
		if (items == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		List tasks = new ArrayList();
		for (int i = 0; i < items.size(); i++) {
			RecalcCutItem item = (RecalcCutItem) items.get(i);
			RecalcCutDeleteRequest task = new RecalcCutDeleteRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setItem(item);
			tasks.add(task);
		}
		boolean resp = MessageDialog.openQuestion(getShell(), GUIMessages
				.getMessage("comp.general.confirm"), GUIMessages.getMessage(
				"comp.general.confirm_delete_object_count",
				new Object[] { new Integer(items.size()) }));
		if (!resp) {
			return;
		}
		try {
			DefaultRecutilClient.processRequests(tasks);
			refreshInternal();
			validateView();
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	private void onMove(boolean up) {
		RecalcCutItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcCutMoveRequest task = new RecalcCutMoveRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setItem(item);
			task.setMoveUp(up);
			DefaultRecutilClient.processRequest(task);
			refreshInternal();
			selectById(item.getId());
			validateView();
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

}
