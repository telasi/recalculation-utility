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

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.calc.calc07.Utilities;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.comp.recalc.item.CalculationHintSelector;
import telasi.recutil.gui.comp.recalc.item.MeterProperties;
import telasi.recutil.gui.comp.recalc.item.RecalcItemProperties;
import telasi.recutil.gui.comp.recalc.item.SummaryPane;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.SummaryCalculator;
import telasi.recutil.service.eclipse.recalc.RecalcItemHintRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemMeterUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemUpdateRequest;
import telasi.recutil.utils.CoreUtils;

public class ElEnergyChargePane extends ChargePaneView {
	private SummaryPane summaryPane1;
	private TableViewer viewer;
	private ToolItem tiMeterProperties;
	private TableColumn colAcceletation;
	private TableColumn colCoeff;
	private TableColumn colMeter;
	private ToolItem tiCalculationHint;
	private ToolItem tiDeriveReadings;
	private ToolItem toolItem1;
	private ToolItem tiProperties;
	private ToolBar toolBar1;
	private TableColumn colCalculationHint;
	private TableColumn colKwh;
	private TableColumn colReading;
	private TableColumn colCycle;
	private TableColumn colOperation;
	private TableColumn colDate;
	private TableColumn colImage;
	private RecalcItem item1;
	private RecalcItem item2;
	private ToolItem tiAddCyclesUp;
	private ToolItem toolItem2;
	private ToolItem tiAddCyclesDown;
	private List derivedItems;

	public ElEnergyChargePane(Composite parent, int style) {
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
			this.setSize(542, 160);
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
					colOperation.setWidth(120);
				}
				{
					colCycle = new TableColumn(viewer.getTable(), SWT.NONE);
					colCycle.setWidth(20);
					colCycle.setResizable(false);
				}
				{
					colReading = new TableColumn(viewer.getTable(), SWT.RIGHT);
					colReading.setText(GUIMessages
							.getMessage("comp.general.reading"));
					colReading.setWidth(75);
				}
				{
					colKwh = new TableColumn(viewer.getTable(), SWT.RIGHT);
					colKwh.setText(GUIMessages.getMessage("comp.general.kwh"));
					colKwh.setWidth(75);
				}
				{
					colCalculationHint = new TableColumn(viewer.getTable(),
							SWT.NONE);
					colCalculationHint.setText(GUIMessages
							.getMessage("comp.general.calc_hint"));
					colCalculationHint.setWidth(150);
				}
				{
					colMeter = new TableColumn(viewer.getTable(), SWT.NONE);
					colMeter.setText("Meter");
					colMeter.setWidth(100);
				}
				{
					colCoeff = new TableColumn(viewer.getTable(), SWT.RIGHT);
					colCoeff.setText("Coeff.");
					colCoeff.setWidth(75);
				}
				{
					colAcceletation = new TableColumn(viewer.getTable(),
							SWT.NONE);
					colAcceletation.setText("Acceleration");
					colAcceletation.setWidth(75);
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
					tiMeterProperties = new ToolItem(toolBar1, SWT.NONE);
					tiMeterProperties.setToolTipText(GUIMessages
							.getMessage("comp.elenergy_view.meter_properties"));
					tiMeterProperties.setImage(Plugin
							.getImage("icons/22x22/configure.png"));
					tiMeterProperties
							.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									onMeterProperties();
								}
							});
				}
				{
					toolItem1 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem1.setText("");
				}
				{
					tiCalculationHint = new ToolItem(toolBar1, SWT.NONE);
					tiCalculationHint.setToolTipText(GUIMessages
							.getMessage("comp.general.calc_hint"));
					tiCalculationHint.setImage(Plugin
							.getImage("icons/22x22/colors.png"));
					tiCalculationHint
							.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									onCalculationHint();
								}
							});
				}
				{
					tiDeriveReadings = new ToolItem(toolBar1, SWT.NONE);
					tiDeriveReadings.setToolTipText(GUIMessages
							.getMessage("comp.elenergy_view.derive_readings"));
					tiDeriveReadings.setImage(Plugin
							.getImage("icons/22x22/readings.png"));
					tiDeriveReadings
							.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									onDeriveReadings();
								}
							});
				}
				{
					toolItem2 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem2.setText("");
				}
				{
					tiAddCyclesDown = new ToolItem(toolBar1, SWT.NONE);
					tiAddCyclesDown
							.setToolTipText(GUIMessages
									.getMessage("comp.elenergy_view.insert_cycles_down"));
					tiAddCyclesDown.setImage(Plugin
							.getImage("icons/22x22/add_missed_down.png"));
					tiAddCyclesDown
							.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									onInsertCycles(false);
								}
							});
				}
				{
					tiAddCyclesUp = new ToolItem(toolBar1, SWT.NONE);
					tiAddCyclesUp.setToolTipText(GUIMessages
							.getMessage("comp.elenergy_view.insert_cycles_up"));
					tiAddCyclesUp.setImage(Plugin
							.getImage("icons/22x22/add_missed_up.png"));
					tiAddCyclesUp.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onInsertCycles(true);
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
		viewer.setLabelProvider(new ViewUtils.ElEnergyLabelProvider());
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
				case OperationType.READING:
				case OperationType.CHARGE:
					switch (item.getOperation().getId()) {
					case Operation.SUMMARY_CHARGE:
					case Operation.CURRENT_CHARGE:
					case Operation.CURRENT_CHARGE_ACT:
					case Operation.CURRENT_CHARGE_VOUCHER:
						break;
					default:
						filtered.add(item);
					}
				}
			}
		}
		viewer.setItemCount(filtered.size());
		viewer.setContentProvider(new CommonItemContentProvider(filtered));
	}

	public void validateView() {
		RecalcItem item = getSelectedItem();
		List items = getSelectedItems();
		boolean selected = item != null;
		tiProperties.setEnabled(selected);
		tiMeterProperties.setEnabled(selected);
		tiCalculationHint.setEnabled(selected);
		tiDeriveReadings.setEnabled(enableDeriveReadings());
		tiAddCyclesDown.setEnabled(item != null && item.getCycle());
		tiAddCyclesUp.setEnabled(item != null && item.getCycle());

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
	private void onCalculationHint() {
		RecalcItem item = getSelectedItem();
		if (item == null)
			return;

		CalculationHintSelector hintSelector = new CalculationHintSelector(
				getShell(), SWT.NONE);
		hintSelector.setHint(item.getCalculationHint());
		hintSelector.open();
		if (!hintSelector.isApproved())
			return;
		int hint = hintSelector.getHint();

		if (!Application.validateConnection())
			return;

		List items = getSelectedItems();
		long[] ids = getSelectedIds();
		List tasks = new ArrayList();
		for (int i = 0; i < items.size(); i++) {
			RecalcItem item2 = (RecalcItem) items.get(i);
			RecalcItemHintRequest request = new RecalcItemHintRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRecalcItem(item2);
			request.setHint(hint);
			tasks.add(request);
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

	@SuppressWarnings("unchecked")
	private void onMeterProperties() {
		RecalcItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		MeterProperties prop = new MeterProperties(getShell(), SWT.NONE);
		prop.setMeter(item.getMeter());
		prop.setAcceleration(item.getMeterAcceleration());
		prop.setStatus(item.getMeterStatus());
		prop.setCoeff(item.getMeterCoeff());
		prop.open();
		if (!prop.isApproved()) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		List tasks = new ArrayList();
		List items = getSelectedItems();
		long[] ids = getSelectedIds();

		for (int i = 0; i < items.size(); i++) {
			RecalcItem aItem = (RecalcItem) items.get(i);
			RecalcItemMeterUpdateRequest task = new RecalcItemMeterUpdateRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setItem(aItem);
			task.setMeterAcceleration(prop.getAcceleration());
			task.setMeterCoeff(prop.getCoeff());
			task.setMeterId(prop.getMeter().getId());
			task.setMeterStatus(prop.getStatus());
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

	@SuppressWarnings("unchecked")
	private boolean enableDeriveReadings() {
		List items = getSelectedItems();
		if (items == null || items.size() != 2) {
			return false;
		}
		item1 = (RecalcItem) items.get(0);
		item2 = (RecalcItem) items.get(1);
		if (Date.isEqual(item1.getItemDate(), item2.getItemDate())) {
			return false;
		}
		if (Date.isGreater(item1.getItemDate(), item2.getItemDate())) {
			RecalcItem temp = item1;
			item1 = item2;
			item2 = temp;
		}
		if (item1.getOperation().getType().getId() != 1
				|| item2.getOperation().getType().getId() != 1) {
			return false;
		}
		int index1 = item1.getInterval().getItems().indexOf(item1);
		int index2 = item2.getInterval().getItems().indexOf(item2);
		derivedItems = new ArrayList();
		for (int i = index1 + 1; i < index2; i++) {
			RecalcItem item = (RecalcItem) item1.getInterval().getItems()
					.get(i);
			int operTypeId = item.getOperation().getType().getId();
			boolean add = false;
			switch (operTypeId) {
			case OperationType.READING:
				int operId = item.getOperation().getId();
				switch (operId) {
				case Operation.READING:
				case Operation.CONTROL_READING:
				case Operation.SALE:
				case Operation.METER_INSTALL:
				case Operation.METER_DEINSTALL:
				case Operation.BALANCE_READING:
				case Operation.CUT_READING:
				case Operation.REPAIR_READING:
					add = true;
					break;
				default:
					add = false;
					break;
				}
				break;
			case OperationType.CHARGE:
				add = true;
				break;
			default:
				add = false;
				break;
			}
			if (add) {
				derivedItems.add(item);
			}
		}
		if (derivedItems.isEmpty()) {
			return false;
		}
		int oper1 = item1.getOperation().getId();
		int oper2 = item2.getOperation().getId();
		switch (oper1) {
		case Operation.READING:
		case Operation.CONTROL_READING:
		case Operation.METER_INSTALL:
			break;
		default:
			return false;
		}
		switch (oper2) {
		case Operation.READING:
		case Operation.METER_DEINSTALL:
			break;
		default:
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private void onDeriveReadings() {
		if (!enableDeriveReadings()) {
			return;
		}
		int size = derivedItems.size();
		boolean resp = MessageDialog.openQuestion(getShell(), GUIMessages
				.getMessage("comp.general.confirm"), GUIMessages.getMessage(
				"comp.elenergy_view.confirm_reading_generation",
				new Object[] { new Integer(size) }));
		if (!resp) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}

		try {

			//double r1 = item1.getReading();
			// double rr1 = Math.round(r1);
			// boolean r1IsRound = Math.abs(r1 - rr1) < CoreUtils.MIN_KWH;
			//double r2 = item2.getReading();
			// double rr2 = Math.round(r2);
			// boolean r2IsRound = Math.abs(r2 - rr2) < CoreUtils.MIN_KWH;
			// XXX why we need to round?
			boolean useRound = false; // r1IsRound && r2IsRound;

			List tasks = new ArrayList();
			long ids[] = new long[size];
			for (int i = 0; i < size; i++) {
				RecalcItem proto = (RecalcItem) derivedItems.get(i);
				RecalcItem item = CoreUtils.copyRecalcItem(proto);
				item.setOperation(Cache.findOperationById(Operation.READING));
				double reading = Utilities.deriveReading(item1.getReading(),
						item2.getReading(), item1.getItemDate(), item2
								.getItemDate(), item.getItemDate(), item2
								.getMeter().getDigits(), true);
				if (useRound) {
					reading = Math.round(reading);
				}
				item.setReading(reading);
				RecalcItemUpdateRequest task = new RecalcItemUpdateRequest(
						Application.USER_NAME, Application.PASSWORD);
				task.setRecalcItem(item);
				tasks.add(task);
				ids[i] = item.getId();
			}

			DefaultRecutilClient.processRequests(tasks);
			getParentPane().refresh();
			getParentPane().selectInterval(getInterval());
			selectById(ids);

		} catch (Throwable t) {
			t.printStackTrace();
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());

		}

	}

	private RecalcItem getEnclosingCycleItem(RecalcItem item, List allItems, boolean up) {
		int startIndex = allItems.indexOf(item);

		for (int i = up ? (startIndex - 1) : (startIndex + 1); up ? (i >= 0)
				: (i < allItems.size()); i = up ? (i - 1) : (i + 1)) {
			RecalcItem currItem = (RecalcItem) allItems.get(i);
			if (currItem.getCycle()
					&& Utilities.isCharge(currItem.getOperation().getId())) {
				return currItem;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void onInsertCycles(boolean up) {
		RecalcItem selectedItem = getSelectedItem();
		if (selectedItem == null || !selectedItem.getCycle()) {
			return;
		}

		List allItems = selectedItem.getInterval().getItems();
		RecalcItem item1;
		RecalcItem item2;
		if (up) {
			item1 = getEnclosingCycleItem(selectedItem, allItems, up);
			item2 = selectedItem;
		} else {
			item1 = selectedItem;
			item2 = getEnclosingCycleItem(selectedItem, allItems, up);
		}

		if (item1 == null || item2 == null) {
			String msg = GUIMessages
					.getMessage("comp.elenergy_view.no_enclosing_cycle");
			MessageDialog.openWarning(getShell(), GUIMessages
					.getMessage("comp.general.warning"), msg);
			return;
		}

		int year1 = item1.getItemDate().getYear();
		int month1 = item1.getItemDate().getMonth();
		int year2 = item2.getItemDate().getYear();
		int month2 = item2.getItemDate().getMonth();
		if (oneMonthDistance(year1, month1, year2, month2)) {
			String msg = GUIMessages
					.getMessage("comp.elenergy_view.nothing_to_insert");
			MessageDialog.openWarning(getShell(), GUIMessages
					.getMessage("comp.general.warning"), msg);
			return;
		}

		try {
			List items = new ArrayList();
			do {
				if (month1 == 12) {
					month1 = 1;
					year1++;
				} else {
					month1++;
				}
				Date newDate = new Date(year1, month1, up ? item2.getItemDate()
						.getDay() : item1.getItemDate().getDay());
				RecalcItem newItem = (RecalcItem) (up ? CoreUtils
						.copyRecalcItem(item2) : CoreUtils
						.copyRecalcItem(item1));
				newItem.setItemDate(newDate);
				newItem.setEnterDate(newDate);
				newItem.setOperation(Cache
						.findOperationById(Operation.ESTIMATE));
				newItem.setKwh(0);
				newItem.setGel(0);
				newItem.setCycle(true);
				newItem.setItemId(-1);
				newItem.setId(-1);
				newItem.setPreviousOperationDate(null);
				newItem.setCurrentOperationDate(null);
				items.add(newItem);
			} while (!oneMonthDistance(year1, month1, year2, month2));

			String msg = GUIMessages.getMessage(
					"comp.elenergy_view.insert_items",
					new Object[] { new Integer(items.size()) });
			boolean resp = MessageDialog.openQuestion(getShell(), GUIMessages
					.getMessage("comp.general.confirm"), msg);
			if (!resp) {
				return;
			}
			if (!Application.validateConnection()) {
				return;
			}

			int startIndex = allItems.indexOf(item1);
			int endIndex = allItems.indexOf(item2);
			int increment;
			List tasks = new ArrayList();
			for (int i = 0; i < items.size(); i++) {
				increment = 0;
				RecalcItem item = (RecalcItem) items.get(i);
				for (int j = startIndex + 1; j < endIndex; j++) {
					RecalcItem someItem = (RecalcItem) allItems.get(j);
					if (Date.isLessOrEqual(someItem.getItemDate(), item
							.getItemDate())) {
						increment++;
					} else {
						break;
					}
				}
				RecalcItemInsertRequest task = new RecalcItemInsertRequest(
						Application.USER_NAME, Application.PASSWORD);
				task.setRecalcItem(item);
				task.setSequence(startIndex + i + increment);
				tasks.add(task);
			}
			List responces = DefaultRecutilClient.processRequests(tasks);

			long[] ids = new long[responces.size()];
			for (int i = 0; i < responces.size(); i++) {
				DefaultEJBResponse response = (DefaultEJBResponse) responces
						.get(i);
				RecalcItemInsertRequest callback = (RecalcItemInsertRequest) response
						.getRequest();
				ids[i] = callback.getId();
			}
			getParentPane().refresh();
			getParentPane().selectInterval(getInterval());
			selectById(ids);

		} catch (Throwable ex) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), ex.toString());
			ex.printStackTrace();
			return;
		}

	}

	private boolean oneMonthDistance(int year1, int month1, int year2,
			int month2) {
		return (year1 == year2 && (month2 - month1) <= 1)
				|| (year2 == year1 + 1 && month1 == 12 && month2 == 1);
	}

}
