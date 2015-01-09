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
package telasi.recutil.gui.comp.recalc.charge_pane_views;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.OperationType;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.comp.recalc.item.RecalcItemProperties;
import telasi.recutil.gui.comp.recalc.item.SummaryPane;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.SummaryCalculator;
import telasi.recutil.service.eclipse.recalc.RecalcItemSubsAttUpdateRequest;
import telasi.recutil.utils.CoreUtils;

public class SubsidyChargePane extends ChargePaneView {
	private SummaryPane summaryPane1;
	private TableViewer viewer;
	private ToolItem tiSetDefault;
	private ToolItem tiLock;
	private ToolItem toolItem1;
	private ToolItem tiProperties;
	private ToolBar toolBar1;
	private TableColumn colRecalc;
	private TableColumn colGel;
	private TableColumn colKwh;
	private TableColumn colCycle;
	private TableColumn colOperation;
	private TableColumn colDate;
	private TableColumn colImage;

	public SubsidyChargePane(Composite parent, int style) {
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
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewerLData.grabExcessVerticalSpace = true;
				viewer = new TableViewer(this, SWT.MULTI | SWT.BORDER
						| SWT.FULL_SELECTION | SWT.VIRTUAL);
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
					colDate = new TableColumn(viewer.getTable(), SWT.NONE);
					colDate.setText(GUIMessages
							.getMessage("comp.general.itemdate"));
					colDate.setWidth(75);
				}
				{
					colOperation = new TableColumn(viewer.getTable(), SWT.NONE);
					colOperation.setText(GUIMessages
							.getMessage("comp.general.operation"));
					colOperation.setWidth(200);
				}
				{
					colCycle = new TableColumn(viewer.getTable(), SWT.NONE);
					colCycle.setWidth(20);
					colCycle.setResizable(false);
				}
				{
					colKwh = new TableColumn(viewer.getTable(), SWT.RIGHT);
					colKwh.setText(GUIMessages.getMessage("comp.general.kwh"));
					colKwh.setWidth(75);
				}
				{
					colGel = new TableColumn(viewer.getTable(), SWT.RIGHT);
					colGel.setText(GUIMessages.getMessage("comp.general.gel"));
					colGel.setWidth(75);
				}
				{
					colRecalc = new TableColumn(viewer.getTable(), SWT.NONE);
					colRecalc.setText(GUIMessages
							.getMessage("comp.general.lock_unlock"));
					colRecalc.setWidth(150);
				}
			}
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiProperties = new ToolItem(toolBar1, SWT.NONE);
					tiProperties
							.setToolTipText(GUIMessages
									.getMessage("comp.normal_charge_pane.properties_item"));
					tiProperties.setImage(Plugin
							.getImage("icons/22x22/properties.gif"));
					tiProperties.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onItemProperties();
						}
					});
				}
				{
					toolItem1 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem1.setText("");
				}
				{
					tiLock = new ToolItem(toolBar1, SWT.NONE);
					tiLock.setToolTipText(GUIMessages
							.getMessage("comp.subsidy_view.lock_subsidy"));
					tiLock.setImage(Plugin.getImage("icons/22x22/lock.png"));
					tiLock.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							processSubsAtttUpdateRequests(true);
						}
					});
				}
				{
					tiSetDefault = new ToolItem(toolBar1, SWT.NONE);
					tiSetDefault
							.setToolTipText(GUIMessages
									.getMessage("comp.subsidy_view.recalc_subsidy_by_default"));
					tiSetDefault.setImage(Plugin
							.getImage("icons/22x22/enable.png"));
					tiSetDefault.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							processSubsAtttUpdateRequests(false);
						}
					});
				}
			}
			// $hide>>$
			GridData summaryPane1LData = new GridData();
			summaryPane1LData.horizontalAlignment = GridData.FILL;
			summaryPane1LData.grabExcessHorizontalSpace = true;
			summaryPane1 = new SummaryPane(this, SWT.NONE);
			summaryPane1.setLayoutData(summaryPane1LData);
			// $hide<<$

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayItems(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new ViewUtils.SubsidyLabelProvider());
	}

	@SuppressWarnings("unchecked")
	public void displayItems(List items) {
		List filtered = new ArrayList();
		if (items != null) {
			for (int i = 0; i < items.size(); i++) {
				RecalcItem item = (RecalcItem) items.get(i);
				if (item.getAccount().getId() != item.getInterval()
						.getDetails().getRecalc().getAccount().getId()) {
					continue;
				}
				switch (item.getOperation().getType().getId()) {
				case OperationType.SUBSIDY:
				case OperationType.ADD_CHARGE:
					filtered.add(item);
				}
			}
		}
		viewer.setItemCount(filtered.size());
		viewer.setContentProvider(new CommonItemContentProvider(filtered));
	}

	public RecalcItem getSelectedItem() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		return (RecalcItem) provider.items.get(index);
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
			RecalcItem item = (RecalcItem) provider.items.get(indecies[i]);
			items.add(item);
		}
		return items;
	}

	public long getSelectedId() {
		RecalcItem item = getSelectedItem();
		return item == null ? -1 : item.getId();
	}

	public void selectById(long id) {
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		if (provider.items != null) {
			for (int i = 0; i < provider.items.size(); i++) {
				RecalcItem item = (RecalcItem) provider.items.get(i);
				if (item.getId() == id) {
					viewer.getTable().setSelection(i);
					viewer.getTable().showSelection();
					break;
				}
			}
		}
		validateView();
	}

	public long[] getSelectedIds() {
		List items = getSelectedItems();
		if (items == null || items.isEmpty()) {
			return null;
		}
		long[] ids = new long[items.size()];
		for (int i = 0; i < items.size(); i++) {
			RecalcItem item = (RecalcItem) items.get(i);
			ids[i] = item.getId();
		}
		return ids;
	}

	public void selectById(long[] ids) {
		if (ids == null || ids.length == 0) {
			return;
		}
		viewer.getTable().deselectAll();
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		for (int j = 0; j < ids.length; j++) {
			long id = ids[j];
			if (provider.items != null) {
				for (int i = 0; i < provider.items.size(); i++) {
					RecalcItem item = (RecalcItem) provider.items.get(i);
					if (item.getId() == id) {
						viewer.getTable().select(i);
						break;
					}
				}
			}
		}
		viewer.getTable().showSelection();
		validateView();
	}

	public void validateView() {
		RecalcItem item = getSelectedItem();
		List items = getSelectedItems();
		boolean selected = item != null;
		tiProperties.setEnabled(selected);
		tiLock.setEnabled(selected);
		tiSetDefault.setEnabled(selected);

		// summaries
		double gel = 0.f;
		double kwh = 0.f;
		double balance = 0.f;
		int record = -1;
		int full = -1;
		if (items != null) {
			SummaryCalculator calc = new SummaryCalculator();
			calc.calculateBalance(item);
			calc.calculateSummary(items);
			gel = calc.getGel();
			kwh = calc.getKwh();
			balance = calc.getBalance();
			CommonItemContentProvider provider = (CommonItemContentProvider) viewer
					.getContentProvider();
			full = provider.items.size();
			record = provider.items.indexOf(item) + 1;
			// full = items.size();
			// record = items.indexOf(item) + 1;
		}
		summaryPane1.setGel(gel);
		summaryPane1.setKwh(kwh);
		summaryPane1.setBalance(balance);
		summaryPane1.setLocation(record, full);

	}

	private void onItemProperties() {

		// get selected item
		RecalcItem item = getSelectedItem();
		if (item == null) {
			return;
		}

		// display properties
		RecalcItemProperties prop = new RecalcItemProperties(getShell(),
				SWT.NONE);
		prop.setItem(item);
		prop.open();

		if (!prop.isApproved()) {
			return;
		}

		getParentPane().refresh();
		getParentPane().selectInterval(getInterval());
		selectById(prop.getItem().getId());
	}

	@SuppressWarnings("unchecked")
	private void processSubsAtttUpdateRequests(boolean useNull) {
		List items = getSelectedItems();
		long ids[] = getSelectedIds();
		if (items == null || items.isEmpty()) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		List tasks = new ArrayList();
		for (int i = 0; i < items.size(); i++) {
			RecalcItem item = (RecalcItem) items.get(i);
			RecalcItemSubsAttUpdateRequest task = new RecalcItemSubsAttUpdateRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setRecalcItem(item);
			task.setAttachment(useNull ? null : CoreUtils.createDefaultAttachment(item));
			tasks.add(task);
		}
		try {
			DefaultRecutilClient.processRequests(tasks);
			getParentPane().refresh();
			getParentPane().selectInterval(getInterval());
			selectById(ids);
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

}
