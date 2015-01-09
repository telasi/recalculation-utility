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
package telasi.recutil.gui.comp.recalc.regular;

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
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcRegularItem;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.recalc.RecalcRegularDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRegularMoveRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRegularSelectRequest;

public class RegularPane extends Composite {

	private ToolBar toolBar1;
	private ToolItem toolItem1;
	private TableViewer viewer;
	private TableColumn colDetails;
	private TableColumn colOperation;
	private TableColumn colEndDate;
	private TableColumn colStartDate;
	private TableColumn colImage;
	private ToolItem tiDown;
	private ToolItem tiUp;
	private ToolItem toolItem2;
	private ToolItem tiProperties;
	private ToolItem tiDelete;
	private ToolItem tiNew;
	private ToolItem tiRefresh;
	private Recalc recalc;

	public RegularPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	private synchronized void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(558, 163);
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
					toolItem1.setText("");
				}
				{
					tiNew = new ToolItem(toolBar1, SWT.NONE);
					tiNew.setToolTipText(GUIMessages
							.getMessage("comp.regular_pane.new_regular"));
					tiNew.setImage(Plugin.getImage("icons/22x22/new.gif"));
					tiNew.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onNew();
						}
					});
				}
				{
					tiDelete = new ToolItem(toolBar1, SWT.NONE);
					tiDelete.setToolTipText(GUIMessages
							.getMessage("comp.regular_pane.delete_regular"));
					tiDelete.setImage(Plugin.getImage("icons/22x22/trash.png"));
					tiDelete.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onDelete();
						}
					});
				}
				{
					tiProperties = new ToolItem(toolBar1, SWT.NONE);
					tiProperties
							.setToolTipText(GUIMessages
									.getMessage("comp.regular_pane.properties_regular"));
					tiProperties.setImage(Plugin
							.getImage("icons/22x22/properties.gif"));
					tiProperties.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onProperties();
						}
					});
				}
				{
					toolItem2 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem2.setText("");
				}
				{
					tiUp = new ToolItem(toolBar1, SWT.NONE);
					tiUp.setToolTipText(GUIMessages
							.getMessage("comp.regular_pane.moveup_regular"));
					tiUp.setImage(Plugin.getImage("icons/22x22/up.png"));
					tiUp.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onMove(true);
						}
					});
				}
				{
					tiDown = new ToolItem(toolBar1, SWT.NONE);
					tiDown.setToolTipText(GUIMessages
							.getMessage("comp.regular_pane.movedown_regular"));
					tiDown.setImage(Plugin.getImage("icons/22x22/down.png"));
					tiDown.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onMove(false);
						}
					});
				}
			}
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewerLData.grabExcessVerticalSpace = true;
				viewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION
						| SWT.VIRTUAL | SWT.MULTI);
				viewer.getControl().setLayoutData(viewerLData);
				viewer.getTable().setLinesVisible(true);
				viewer.getTable().setHeaderVisible(true);
				viewer
						.addSelectionChangedListener(new ISelectionChangedListener() {
							public void selectionChanged(
									SelectionChangedEvent event) {
								validateView();
							}
						});
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
					colEndDate = new TableColumn(viewer.getTable(), SWT.NONE);
					colEndDate.setText(GUIMessages
							.getMessage("comp.general.end_date"));
					colEndDate.setWidth(100);
				}
				{
					colOperation = new TableColumn(viewer.getTable(), SWT.NONE);
					colOperation.setText(GUIMessages
							.getMessage("comp.general.operation"));
					colOperation.setWidth(200);
				}
				{
					colDetails = new TableColumn(viewer.getTable(), SWT.NONE);
					colDetails.setText(GUIMessages
							.getMessage("comp.general.details"));
					colDetails.setWidth(200);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onNew() {
		if (recalc == null) {
			return;
		}
		RegularProperties prop = new RegularProperties(getShell(), SWT.NONE);
		prop.setRecalculation(recalc);
		RecalcRegularItem item = getSelectedItem();
		int seq = -1;
		if (item != null) {
			seq = item.getDetails().getRegulars().indexOf(item);
		}
		prop.setSequence(seq);
		prop.open();
		if (prop.isApproved()) {
			refreshInternal();
			selectById(prop.getItem().getId());
			validateView();
		}
	}

	private void onProperties() {
		RecalcRegularItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		RegularProperties prop = new RegularProperties(getShell(), SWT.NONE);
		prop.setItem(item);
		prop.open();
		if (prop.isApproved()) {
			refreshInternal();
			selectById(prop.getItem().getId());
			validateView();
		}
	}

	private void onMove(boolean up) {
		RecalcRegularItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcRegularMoveRequest task = new RecalcRegularMoveRequest(
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
			RecalcRegularItem item = (RecalcRegularItem) items.get(i);
			RecalcRegularDeleteRequest task = new RecalcRegularDeleteRequest(
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

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	private void initDataBehaivour() {
		displayItems(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new RegularLabelProvider());
	}

	private void displayItems(List items) {
		viewer.setItemCount(items == null ? 0 : items.size());
		viewer.setContentProvider(new CommonItemContentProvider(items));
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
			if (this.isDisposed()) {
				System.out.println("disposed");
			}
			RecalcRegularSelectRequest request = new RecalcRegularSelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRecalc(recalc);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			RecalcRegularSelectRequest callback = (RecalcRegularSelectRequest) resp
					.getRequest();
			List items = callback.getItems();
			if (items != null) {
				for (int i = 0; i < items.size(); i++) {
					RecalcRegularItem item = (RecalcRegularItem) items.get(i);
					item.setOperation(Cache.findOperationById(item
							.getOperation().getId()));
					item.setDetails(recalc.getDetails());
				}
			}
			recalc.getDetails().getRegulars().clear();
			if (items != null) {
				recalc.getDetails().getRegulars().addAll(items);
			}
			displayItems(items);
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
		viewer.getTable().deselectAll();
		// validateView();
	}

	public void clear() {
		displayItems(null);
		recalc = null;
		validateView();
	}

	private void validateView() {
		boolean hasRecalc = recalc != null;
		RecalcRegularItem item = getSelectedItem();
		boolean selected = hasRecalc && item != null;
		tiRefresh.setEnabled(hasRecalc);
		tiNew.setEnabled(hasRecalc);
		tiDelete.setEnabled(selected);
		tiProperties.setEnabled(selected);
		tiUp.setEnabled(selected);
		tiDown.setEnabled(selected);
	}

	public RecalcRegularItem getSelectedItem() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		return (RecalcRegularItem) provider.items.get(index);
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
			RecalcRegularItem item = (RecalcRegularItem) provider.items
					.get(index);
			items.add(item);
		}
		return items;
	}

	private class RegularLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Plugin.getImage("icons/16x16/bop/regular.png");
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			RecalcRegularItem item = (RecalcRegularItem) element;
			switch (columnIndex) {
			case 1:
				return item == null ? "" : Date.format(item.getStartDate());
			case 2:
				return item == null ? "" : Date.format(item.getEndDate());
			case 3:
				return item == null ? "" : GUITranslator.GEO_ASCII_TO_KA(item
						.getOperation().getName());
			case 4:
				StringBuffer sb = new StringBuffer("");
				if (item != null) {
					sb.append("[");
					sb.append(item.getAttachment().getAmount());
					if (item.getAttachment().hasAmount2()) {
						sb.append("/");
						sb.append(item.getAttachment().getAmount2());
					}
					sb.append(" ");
					sb.append(GUIUtils.getUnitName(item.getAttachment()
							.getUnit()));
					sb.append("] x ");
					sb.append(item.getAttachment().getCount());

				}
				return sb.toString();
			default:
				return "";
			}

		}

	}

	private void selectById(long id) {
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		if (provider.items != null) {
			for (int i = 0; i < provider.items.size(); i++) {
				RecalcRegularItem item = (RecalcRegularItem) provider.items
						.get(i);
				if (item.getId() == id) {
					viewer.getTable().select(i);
					viewer.getTable().showSelection();
					break;
				}
			}
		}
	}

}
