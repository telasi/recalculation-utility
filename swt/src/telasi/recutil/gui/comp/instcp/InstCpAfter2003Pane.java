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
package telasi.recutil.gui.comp.instcp;


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

import telasi.recutil.beans.instcp.InstCpAfter2003Record;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.service.eclipse.instcp.InstCpHistoryAfter2003DeleteRequest;
import telasi.recutil.service.eclipse.instcp.InstCpHistoryAfter2003Request;

public class InstCpAfter2003Pane extends Composite {

	private ToolItem tiRefresh;
	private ToolBar toolBar1;
	private ToolItem tiProperties;
	private Label lblDescription;
	private Label lblImage;
	private Composite composite1;
	private ToolItem tiDelete;
	private ToolItem tiNew;
	private ToolItem toolItem1;
	private TableViewer viewer;
	private TableColumn colYear;
	private TableColumn colInstcp;
	private TableColumn colEnd;
	private TableColumn colMonth;

	public InstCpAfter2003Pane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
		validateView();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(481, 353);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 32;
					lblImageLData.widthHint = 32;
					lblImage = new Label(composite1, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage
							.setImage(Plugin.getImage("icons/32x32/energy.png"));
				}
				{
					lblDescription = new Label(composite1, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages
							.getMessage("comp.inst_cp_after2003_pane.descr"));
				}
			}
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewerLData.grabExcessVerticalSpace = true;
				viewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION
						| SWT.MULTI | SWT.VIRTUAL);
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
					TableColumn colImage = new TableColumn(viewer.getTable(),
							SWT.NONE);
					colImage.setWidth(20);
					colImage.setResizable(false);
				}
				{
					colYear = new TableColumn(viewer.getTable(), SWT.NONE);
					colYear
							.setText(GUIMessages
									.getMessage("comp.general.year"));
					colYear.setWidth(200);
				}
				{
					colMonth = new TableColumn(viewer.getTable(), SWT.NONE);
					colMonth.setText(GUIMessages
							.getMessage("comp.general.month"));
					colMonth.setWidth(200);
				}
				{
					colInstcp = new TableColumn(viewer.getTable(), SWT.RIGHT);
					colInstcp.setText(GUIMessages
							.getMessage("comp.general.instcp"));
					colInstcp.setWidth(200);
				}
				{
					colEnd = new TableColumn(viewer.getTable(), SWT.NONE);
					colEnd.setWidth(10);
					colEnd.setResizable(false);
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
					tiRefresh.setImage(Plugin
							.getImage("icons/22x22/refresh.png"));
					tiRefresh.setToolTipText(GUIMessages
							.getMessage("comp.general.refresh"));
					tiRefresh.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							refresh();
						}
					});
				}
				{
					toolItem1 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem1.setText("");
				}
				{
					tiNew = new ToolItem(toolBar1, SWT.NONE);
					tiNew.setImage(Plugin.getImage("icons/22x22/new.gif"));
					tiNew.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onNew();
						}
					});
				}
				{
					tiDelete = new ToolItem(toolBar1, SWT.NONE);
					tiDelete.setImage(Plugin.getImage("icons/22x22/trash.png"));
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
					tiProperties.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onUpdate();
						}
					});
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		display(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new InstcpLabelProvider());
	}

	private void display(List items) {
		viewer.setItemCount(items == null ? 0 : items.size());
		viewer.setContentProvider(new CommonItemContentProvider(items));
	}

	private class InstcpLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Plugin.getImage("icons/16x16/bop/charge.png");
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return "";
			}
			InstCpAfter2003Record rec = (InstCpAfter2003Record) element;
			switch (columnIndex) {
			case 1:
				return String.valueOf(rec.getYear());
			case 2:
				String monthName = "comp.genral.month." + rec.getMonth();
				return GUIMessages.getMessage(monthName);
			case 3:
				return nf.format(rec.getInstcp());
			default:
				return "";
			}
		}
	}

	private void validateView() {
		boolean selected = viewer.getTable().getSelectionIndex() != -1;
		tiRefresh.setEnabled(true);
		tiNew.setEnabled(true);
		tiProperties.setEnabled(selected);
		tiDelete.setEnabled(selected);
	}

	public void refresh() {
		if (!Application.validateConnection()) {
			return;
		}
		try {

			InstCpHistoryAfter2003Request request = new InstCpHistoryAfter2003Request(
					Application.USER_NAME, Application.PASSWORD);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			InstCpHistoryAfter2003Request callback = (InstCpHistoryAfter2003Request) resp
					.getRequest();
			display(callback.getHistory());

		} catch (Throwable t) {
			display(null);
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
		validateView();
	}

	private void onNew() {
		InstCpAfter2003_Properties prop = new InstCpAfter2003_Properties(
				getShell(), SWT.NONE);
		prop.open();
		if (!prop.isApproved()) {
			return;
		}
		refresh();
		selectInstCpRecord(prop.getInstcpRecord());
		validateView();
	}

	private void onUpdate() {
		InstCpAfter2003Record record = getSelectedRecord();
		if (record == null) {
			return;
		}
		InstCpAfter2003_Properties prop = new InstCpAfter2003_Properties(
				getShell(), SWT.NONE);
		prop.setInstcpRecord(record);
		prop.open();
		if (!prop.isApproved()) {
			return;
		}
		refresh();
		selectInstCpRecord(prop.getInstcpRecord());
		validateView();
	}

	@SuppressWarnings("unchecked")
	private void onDelete() {
		List selection = getSelectedRecords();
		if (selection == null || selection.size() == 0) {
			return;
		}
		String msg = GUIMessages.getMessage(
				"comp.general.confirm_delete_object_count",
				new Object[] { new Integer(selection.size()) });
		String title = GUIMessages.getMessage("comp.general.confirm");
		boolean resp = MessageDialog.openQuestion(getShell(), title, msg);
		if (!resp) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		List tasks = new ArrayList();
		for (int i = 0; i < selection.size(); i++) {
			InstCpAfter2003Record record = (InstCpAfter2003Record) selection
					.get(i);
			InstCpHistoryAfter2003DeleteRequest task = new InstCpHistoryAfter2003DeleteRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setRecord(record);
			tasks.add(task);
		}
		try {

			DefaultRecutilClient.processRequests(tasks);
			refresh();
			validateView();

		} catch (Throwable t) {

			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());

		}
	}

	public InstCpAfter2003Record getSelectedRecord() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		return (InstCpAfter2003Record) provider.getElements(null)[index];
	}

	@SuppressWarnings("unchecked")
	public List getSelectedRecords() {
		int[] indecies = viewer.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		Object[] elements = provider.getElements(null);
		List selection = new ArrayList();
		for (int i = 0; i < indecies.length; i++) {
			selection.add(elements[indecies[i]]);
		}
		return selection;
	}

	private void selectInstCpRecord(InstCpAfter2003Record r) {
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		Object[] elements = provider.getElements(null);
		if (elements.length == 0) {
			return;
		}
		for (int i = 0; i < elements.length; i++) {
			InstCpAfter2003Record record = (InstCpAfter2003Record) elements[i];
			if (record.getMonth() == r.getMonth()
					&& record.getYear() == r.getYear()) {
				viewer.getTable().setSelection(i);
				viewer.getTable().showSelection();
				break;
			}
		}
	}

}
