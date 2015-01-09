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
import java.util.Calendar;
import java.util.List;

import org.eclipse.jface.dialogs.InputDialog;
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
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.calc.calc07.Utilities;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.comp.recalc.item.RecalcItemChangesDialog;
import telasi.recutil.gui.comp.recalc.item.RecalcItemProperties;
import telasi.recutil.gui.comp.recalc.item.SummaryPane;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.SummaryCalculator;
import telasi.recutil.gui.widgets.calendar.SWTCalendarDialog;
import telasi.recutil.service.eclipse.recalc.RecalcIntervalInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemEnableRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemMoveRequest;
import telasi.recutil.utils.CoreUtils;

public class NormalChargePane extends ChargePaneView {

	private TableViewer viewer;
	private ToolItem toolItem3;
	private ToolItem tiCutInterval;
	private SummaryPane summaryPane1;
	private ToolItem tiChanges;
	private ToolItem tiMoveDown;
	private ToolItem tiMoveUp;
	private ToolItem toolItem2;
	private ToolItem tiEnable;
	private ToolItem toolItem1;
	private ToolItem tiProperties;
	private ToolItem tiDeleteItem;
	private ToolItem tiNewItem;
	private ToolItem tiMoveInTime;
	private ToolItem toolItem4;
	private ToolBar toolBar1;

	public NormalChargePane(Composite parent, int style) {
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
			this.setSize(423, 154);
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewerLData.grabExcessVerticalSpace = true;
				viewer = new TableViewer(this, SWT.BORDER | SWT.MULTI
						| SWT.FULL_SELECTION | SWT.VIRTUAL);
				viewer.getTable().setLinesVisible(true);
				viewer.getTable().setHeaderVisible(true);
				viewer.getControl().setLayoutData(viewerLData);
				viewer
						.addSelectionChangedListener(new ISelectionChangedListener() {
							public void selectionChanged(
									SelectionChangedEvent event) {
								validateView();
							}
						});
				// $hide>>$
				ViewUtils.createCommonTable(viewer);
				// $hide<<$
			}
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiCutInterval = new ToolItem(toolBar1, SWT.NONE);
					tiCutInterval
							.setToolTipText(GUIMessages
									.getMessage("comp.normal_charge_pane.cut_interval"));
					tiCutInterval.setImage(Plugin
							.getImage("icons/22x22/cut.png"));
					tiCutInterval.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							onCutInterval();
						}
					});
				}
				{
					toolItem1 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem1.setText("");
				}
				{
					tiNewItem = new ToolItem(toolBar1, SWT.NONE);
					tiNewItem.setImage(Plugin.getImage("icons/22x22/new.gif"));
					tiNewItem.setToolTipText(GUIMessages
							.getMessage("comp.normal_charge_pane.new_item"));
					tiNewItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onNewItem();
						}
					});
				}
				{
					tiDeleteItem = new ToolItem(toolBar1, SWT.NONE);
					tiDeleteItem.setImage(Plugin
							.getImage("icons/22x22/trash.png"));
					tiDeleteItem.setToolTipText(GUIMessages
							.getMessage("comp.normal_charge_pane.delete_item"));
					tiDeleteItem.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onDeleteItem();
						}
					});
				}
				{
					tiProperties = new ToolItem(toolBar1, SWT.NONE);
					tiProperties.setImage(Plugin
							.getImage("icons/22x22/properties.gif"));
					tiProperties
							.setToolTipText(GUIMessages
									.getMessage("comp.normal_charge_pane.properties_item"));
					tiProperties.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onItemProperties();
						}
					});
				}
				{
					toolItem2 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem2.setText("");
				}
				{
					tiChanges = new ToolItem(toolBar1, SWT.NONE);
					tiChanges.setImage(Plugin
							.getImage("icons/22x22/changes.png"));
					tiChanges
							.setToolTipText(GUIMessages
									.getMessage("comp.normal_charge_pane.changes_item"));
					tiChanges.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onChanges();
						}
					});
				}
				{
					tiEnable = new ToolItem(toolBar1, SWT.NONE);
					tiEnable.setImage(Plugin
							.getImage("icons/22x22/disable.png"));
					tiEnable
							.setToolTipText(GUIMessages
									.getMessage("comp.normal_charge_pane.disable_item"));
					tiEnable.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onEnable();
						}
					});
				}
				{
					toolItem3 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem3.setText("");
				}
				{
					tiMoveUp = new ToolItem(toolBar1, SWT.NONE);
					tiMoveUp.setImage(Plugin.getImage("icons/22x22/up.png"));
					tiMoveUp
							.setToolTipText(GUIMessages
									.getMessage("comp.normal_charge_pane.move_up_item"));
					tiMoveUp.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onItemMove(true);
						}
					});
				}
				{
					tiMoveDown = new ToolItem(toolBar1, SWT.NONE);
					tiMoveDown
							.setImage(Plugin.getImage("icons/22x22/down.png"));
					tiMoveDown
							.setToolTipText(GUIMessages
									.getMessage("comp.normal_charge_pane.move_down_item"));
					tiMoveDown.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onItemMove(false);
						}
					});
				}
				{
					toolItem4 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem4.setText("");
				}
				{
					tiMoveInTime = new ToolItem(toolBar1, SWT.NONE);
					tiMoveInTime
							.setToolTipText(GUIMessages
									.getMessage("comp.normal_charge_pane.move_item_in_date"));
					tiMoveInTime.setImage(Plugin
							.getImage("icons/22x22/history.png"));
					tiMoveInTime.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onMoveInTime();
						}
					});
				}
			}
			{
				// $hide>>$
				GridData summaryPane1LData = new GridData();
				summaryPane1LData.horizontalAlignment = GridData.FILL;
				summaryPane1LData.grabExcessHorizontalSpace = true;
				summaryPane1 = new SummaryPane(this, SWT.NONE);
				summaryPane1.setLayoutData(summaryPane1LData);
				// $hide<<$
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayItems(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new ViewUtils.ItemLabelProvider());
	}

	public void displayItems(List items) {
		viewer.getTable().deselectAll();
		viewer.setItemCount(items == null ? 0 : items.size());
		viewer.setContentProvider(new CommonItemContentProvider(items));
		validateView();
	}

	public long getSelectedId() {
		RecalcItem item = getSelectedItem();
		return item == null ? -1 : item.getId();
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

	public RecalcItem getSelectedItem() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		return (RecalcItem) provider.items.get(index);
	}

	public void clear() {
		displayItems(null);
		setInterval(null);
	}

	public void validateView() {

		RecalcItem item = getSelectedItem();
		List items = getSelectedItems();

		boolean intervalKnown = getInterval() != null;
		boolean itemSelected = item != null;
		boolean itemMoveable = itemSelected && item.getItemId() == -1;
		boolean canDelete = false;
		boolean itemEnabled = itemSelected
				&& item.getStatus() != RecalcItem.DELETED;

		if (items != null) {
			for (int i = 0; i < items.size(); i++) {
				RecalcItem curr = (RecalcItem) items.get(i);
				if (curr.getItemId() == -1) {
					canDelete = true;
					break;
				}
			}
		}

		tiCutInterval.setEnabled(item != null
				&& !item.getInterval().isEditable());

		// new/delete/properties/changes
		tiNewItem.setEnabled(intervalKnown);
		tiDeleteItem.setEnabled(intervalKnown && canDelete
				&& !item.isSysRestruct());
		tiProperties.setEnabled(intervalKnown && itemSelected);
		tiChanges.setEnabled(intervalKnown && itemSelected && item.isChanged());

		// motion
		tiMoveUp.setEnabled(intervalKnown && itemMoveable
				&& !item.isSysRestruct());
		tiMoveDown.setEnabled(intervalKnown && itemMoveable
				&& !item.isSysRestruct());
		tiMoveInTime.setEnabled(intervalKnown && itemSelected);

		// enable/disable
		if (!itemSelected) {
			tiEnable.setEnabled(false);
		} else {
			tiEnable.setEnabled(true);
			if (itemEnabled) {
				tiEnable.setImage(Plugin.getImage("icons/22x22/disable.png"));
				tiEnable.setToolTipText(GUIMessages
						.getMessage("comp.normal_charge_pane.disable_item"));
			} else {
				tiEnable.setImage(Plugin.getImage("icons/22x22/enable.png"));
				tiEnable.setToolTipText(GUIMessages
						.getMessage("comp.normal_charge_pane.enable_item"));
			}
		}

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

	@SuppressWarnings("unchecked")
	public List getSelectedItems() {
		int[] indecies = viewer.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		List items = new ArrayList();
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		for (int i = 0; i < indecies.length; i++) {
			items.add(provider.items.get(indecies[i]));
		}
		return items;
	}

	private void onChanges() {
		RecalcItem item = getSelectedItem();
		if (item == null || !item.isChanged()) {
			return;
		}
		RecalcItemChangesDialog changes = new RecalcItemChangesDialog(
				getShell(), SWT.NONE);
		changes.setItem(item);
		changes.open();
		viewer.refresh();
		validateView();
	}

	@SuppressWarnings("unchecked")
	private void onEnable() {
		RecalcItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		boolean disabled = item.getStatus() == RecalcItem.DELETED;
		List items = getSelectedItems();
		List tasks = new ArrayList();
		if (!Application.validateConnection()) {
			return;
		}
		for (int i = 0; i < items.size(); i++) {
			RecalcItem item2 = (RecalcItem) items.get(i);
			RecalcItemEnableRequest task = new RecalcItemEnableRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setEnabled(disabled);
			task.setRecalcItem(item2);
			tasks.add(task);
		}
		String msgId = disabled ? "comp.general.confirm_enable_object_count"
				: "comp.general.confirm_disable_object_count";
		String msg = GUIMessages.getMessage(msgId, new Object[] { new Integer(
				tasks.size()) });
		String title = GUIMessages.getMessage("comp.general.confirm");
		boolean resp = MessageDialog.openQuestion(getShell(), title, msg);
		if (!resp) {
			return;
		}
		try {
			DefaultRecutilClient.processRequests(tasks);
			getParentPane().refresh();
			getParentPane().selectInterval(getInterval());
			selectById(((RecalcItem) items.get(0)).getId());
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}

	}

	private void onNewItem() {
		int sequence = -1;
		RecalcItem proto = getSelectedItem();
		if (proto == null) {
			proto = (RecalcItem) getInterval().getItems().get(0);
		} else {
			sequence = getInterval().getItems().indexOf(proto);
		}

		RecalcItem newItem = CoreUtils.copyRecalcItem(proto);
		newItem.setId(-1L);
		newItem.setItemId(-1L);
		newItem.setItemNumber("new_item");
		newItem.setStatus(RecalcItem.NEW);
		newItem.setBalanceGap(0.f);
		newItem.setBalance(0.f);
		newItem.setPreviousOperationDate(null);
		newItem.setCurrentOperationDate(null);

		RecalcItemProperties prop = new RecalcItemProperties(getShell(),
				SWT.NONE);
		prop.setItem(newItem);
		prop.setSequence(sequence);
		prop.open();

		if (!prop.isApproved()) {
			return;
		}

		getParentPane().refresh();
		getParentPane().selectInterval(getInterval());
		selectById(prop.getItem().getId());
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
	private void onDeleteItem() {
		List items = getSelectedItems();
		if (items == null || items.isEmpty()) {
			return;
		}
		List tasks = new ArrayList();
		if (!Application.validateConnection()) {
			return;
		}
		for (int i = 0; i < items.size(); i++) {
			RecalcItem item = (RecalcItem) items.get(i);
			if (item.getItemId() == -1) {
				RecalcItemDeleteRequest request = new RecalcItemDeleteRequest(
						Application.USER_NAME, Application.PASSWORD);
				request.setRecalcItem(item);
				tasks.add(request);
			}
		}
		if (tasks.isEmpty()) {
			return;
		}
		boolean resp = MessageDialog.openQuestion(getShell(), GUIMessages
				.getMessage("comp.general.confirm"), GUIMessages.getMessage(
				"comp.general.confirm_delete_object_count",
				new Object[] { new Integer(tasks.size()) }));
		if (!resp) {
			return;
		}

		try {
			DefaultRecutilClient.processRequests(tasks);
			getParentPane().refresh();
			getParentPane().selectInterval(getInterval());
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}

	}

	private void onItemMove(boolean up) {
		RecalcItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}

		try {
			RecalcItemMoveRequest request = new RecalcItemMoveRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setMoveUp(up);
			request.setRecalcItem(item);
			DefaultRecutilClient.processRequest(request);
			getParentPane().refresh();
			getParentPane().selectInterval(getInterval());
			selectById(item.getId());
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}

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

	private void onCutInterval() {
		List items = getSelectedItems();
		if (items == null /* || items.size() < 2 */) {
			return;
		}
		RecalcItem item1 = (RecalcItem) items.get(0);
		RecalcItem item2 = (RecalcItem) items.get(items.size() - 1);
		if (item1.getInterval().isEditable()) {
			return;
		}
		String title = GUIMessages
				.getMessage("comp.normal_charge_pane.new_interval_title");
		String message = GUIMessages
				.getMessage("comp.normal_charge_pane.new_interval_message");
		InputDialog input = new InputDialog(getShell(), title, message, null,
				null);
		if (input.open() != InputDialog.OK) {
			return;
		}
		if (input.getValue() == null || input.getValue().trim().length() == 0) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}

		Recalc recalc = item1.getInterval().getDetails().getRecalc();
		RecalcIntervalInsertRequest task = new RecalcIntervalInsertRequest(
				Application.USER_NAME, Application.PASSWORD);
		task.setItem1Id(item1.getId());
		task.setItem2Id(item2.getId());
		task.setRecalcId(recalc.getId());
		task.setRecalcName(input.getValue());

		try {

			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(task);
			RecalcIntervalInsertRequest callback = (RecalcIntervalInsertRequest) resp
					.getRequest();
			long newIntervalId = callback.getIntervalId();
			getParentPane().refresh();
			getParentPane().selectIntervalById(newIntervalId);

		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}

	}

	@SuppressWarnings("unchecked")
	private void onMoveInTime() {
		RecalcItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		SWTCalendarDialog dialog = new SWTCalendarDialog(getShell(), SWT.NONE);
		Calendar initDate = Calendar.getInstance();
		initDate.setTime(item.getItemDate().toDate());
		dialog.open(initDate, null);
		if (!dialog.isApproved()) {
			return;
		}
		Date finalDate = Date.create(dialog.getCalendar().getTime().getTime());
		RecalcItem newItem = CoreUtils.copyRecalcItem(item);
		newItem.setItemDate(finalDate);
		newItem.setEnterDate(finalDate);
		newItem.setPreviousOperationDate(null);
		newItem.setCurrentOperationDate(null);
		newItem.setItemId(-1);
		newItem.setId(-1);
		newItem.setBalance(0);
		newItem.setKwh(0);
		newItem.setGel(0);
		int sequence = -1;
		List allItems = item.getInterval().getItems();
		boolean itemIsCharge = Utilities.isCharge(item.getOperation()
				.getId());
		for (int i = 0; i < allItems.size(); i++) {
			RecalcItem currItem = (RecalcItem) allItems.get(i);
			boolean currItemIsCharge = Utilities.isCharge(currItem
					.getOperation().getId());
			if (Date.isLessOrEqual(currItem.getItemDate(), finalDate)) {
				sequence++;
			} else {
				if (itemIsCharge && !currItemIsCharge) {
					for (int j = i + 1; j < allItems.size(); j++) {
						RecalcItem currItem2 = (RecalcItem) allItems.get(j);
						boolean currItem2IsCharge = Utilities
								.isCharge(currItem2.getOperation().getId());
						if (currItem2IsCharge) {
							if (Date.isLessOrEqual(currItem2.getItemDate(),
									finalDate)) {
								sequence = allItems.indexOf(currItem2);
							}
							break;
						}
					}
				}
				break;
			}
		}

		if (!Application.validateConnection()) {
			return;
		}
		RecalcItemInsertRequest task1 = new RecalcItemInsertRequest(
				Application.USER_NAME, Application.PASSWORD);
		task1.setRecalcItem(newItem);
		task1.setSequence(sequence);
		DefaultEJBRequest task2;
		if (item.getItemId() == -1) {
			RecalcItemDeleteRequest anotherTask = new RecalcItemDeleteRequest(
					Application.USER_NAME, Application.PASSWORD);
			anotherTask.setRecalcItem(item);
			task2 = anotherTask;
		} else {
			RecalcItemEnableRequest anotherTask = new RecalcItemEnableRequest(
					Application.USER_NAME, Application.PASSWORD);
			anotherTask.setEnabled(false);
			anotherTask.setRecalcItem(item);
			task2 = anotherTask;
		}

		try {

			List tasks = new ArrayList();
			tasks.add(task1);
			tasks.add(task2);

			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequests(tasks).get(0);
			RecalcItemInsertRequest callback = (RecalcItemInsertRequest) resp
					.getRequest();
			long newItemId = callback.getId();
			getParentPane().refresh();
			getParentPane().selectIntervalById(item.getInterval().getId());
			selectById(newItemId);

		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}

	}

}
