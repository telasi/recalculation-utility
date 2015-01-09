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
package telasi.recutil.gui.comp.avrgcharge;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.avrgcalc.AvearageKwhSeed;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.calendar.SWTCalendarDialog;
import telasi.recutil.service.eclipse.avrgcharge.AvrgChargeCalculationRequest;
import telasi.recutil.service.eclipse.avrgcharge.CreateAvrgCalculationRecalc;

public class AvearageChargeCalculationPane extends Composite {
	private Account account;
	private TableViewer viewer1;
	private TableColumn colItemImage;
	private TableColumn colAccount;
	private Composite composite2;
	private Composite composite1;
	private TableColumn colCycle;
	private TableColumn tableColumn1;
	private ToolItem tiEnable;
	private ToolItem toolItem2;
	private ToolItem tiClearDate;
	private ToolItem toolItem1;
	private TableColumn colChangedDate;
	private ToolItem tiChangeDate;
	private Text txtAvearage;
	private Label label1;
	private Composite composite4;
	private TableColumn tableColumn4;
	private TableColumn colAvearage;
	private TableColumn tableColumn3;
	private TableColumn colDays;
	private TableColumn tableColumn2;
	private TableColumn colEndDate;
	private TableColumn colStartDate;
	private TableViewer viewer3;
	private Composite composite3;
	private TabItem tabResults;
	private TabItem tabCharges;
	private TabFolder tabFolder1;
	private ToolItem tiCut;
	private ToolBar toolBar2;
	private ToolItem tiDeleteInterval;
	private ToolBar toolBar1;
	private TableColumn colGel;
	private TableColumn colKwh;
	private TableColumn colReading;
	private TableColumn colOperation;
	private TableColumn colItemDate;
	private TableColumn colEnd;
	private TableColumn colStart;
	private TableColumn colName;
	private TableColumn colImage;
	private TableViewer viewer2;
	private SashForm sashForm1;
	private Recalc recalc;

	public AvearageChargeCalculationPane(Composite parent, int style) {
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
			this.setSize(730, 285);
			{
				tabFolder1 = new TabFolder(this, SWT.NONE);
				{
					tabCharges = new TabItem(tabFolder1, SWT.NONE);
					tabCharges.setText(GUIMessages
							.getMessage("comp.avrg_charge_pane.charge_tab"));
					tabCharges.setImage(Plugin
							.getImage("icons/16x16/bop/reading.png"));
					{
						sashForm1 = new SashForm(tabFolder1, SWT.NONE);
						tabCharges.setControl(sashForm1);
						{
							composite1 = new Composite(sashForm1, SWT.NONE);
							GridLayout composite1Layout = new GridLayout();
							composite1Layout.horizontalSpacing = 0;
							composite1Layout.marginHeight = 0;
							composite1Layout.marginWidth = 0;
							composite1Layout.verticalSpacing = 0;
							composite1.setLayout(composite1Layout);
							{
								viewer1 = new TableViewer(composite1,
										SWT.BORDER | SWT.SINGLE | SWT.VIRTUAL
												| SWT.FULL_SELECTION);
								viewer1.getTable().setBounds(36, 63, 260, 170);
								viewer1.getTable().setLinesVisible(true);
								GridData viewer1LData = new GridData();
								viewer1LData.horizontalAlignment = GridData.FILL;
								viewer1LData.grabExcessHorizontalSpace = true;
								viewer1LData.verticalAlignment = GridData.FILL;
								viewer1LData.grabExcessVerticalSpace = true;
								viewer1.getControl()
										.setLayoutData(viewer1LData);
								viewer1.getTable().setHeaderVisible(true);
								// $protect>>$
								viewer1
										.addSelectionChangedListener(new ISelectionChangedListener() {
											public void selectionChanged(
													SelectionChangedEvent event) {
												validateItemsWithIntervals();
												validateView();
											}
										});
								// $protect<<$
								{
									colImage = new TableColumn(viewer1
											.getTable(), SWT.NONE);
									colImage.setWidth(20);
									colImage.setResizable(false);
								}
								{
									colName = new TableColumn(viewer1
											.getTable(), SWT.NONE);
									colName.setText(GUIMessages
											.getMessage("comp.general.name"));
									colName.setWidth(75);
								}
								{
									colStart = new TableColumn(viewer1
											.getTable(), SWT.NONE);
									colStart
											.setText(GUIMessages
													.getMessage("comp.general.start_date"));
									colStart.setWidth(100);
								}
								{
									colEnd = new TableColumn(
											viewer1.getTable(), SWT.NONE);
									colEnd
											.setText(GUIMessages
													.getMessage("comp.general.end_date"));
									colEnd.setWidth(100);
								}
							}
							{
								GridData toolBar1LData = new GridData();
								toolBar1LData.horizontalAlignment = GridData.FILL;
								toolBar1LData.grabExcessHorizontalSpace = true;
								toolBar1 = new ToolBar(composite1, SWT.NONE);
								toolBar1.setLayoutData(toolBar1LData);
								{
									tiDeleteInterval = new ToolItem(toolBar1,
											SWT.NONE);
									tiDeleteInterval
											.setToolTipText(GUIMessages
													.getMessage("comp.avrg_charge_pane.delete_interval"));
									tiDeleteInterval.setImage(Plugin
											.getImage("icons/22x22/trash.png"));
									tiDeleteInterval
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent e) {
													onDelete();
												}
											});
								}
							}
						}
						{
							composite2 = new Composite(sashForm1, SWT.NONE);
							GridLayout composite2Layout = new GridLayout();
							composite2Layout.horizontalSpacing = 0;
							composite2Layout.marginHeight = 0;
							composite2Layout.marginWidth = 0;
							composite2Layout.verticalSpacing = 0;
							composite2.setLayout(composite2Layout);
							{
								viewer2 = new TableViewer(composite2,
										SWT.BORDER | SWT.MULTI | SWT.VIRTUAL
												| SWT.FULL_SELECTION);
								viewer2.getTable().setLinesVisible(true);
								GridData viewer2LData = new GridData();
								viewer2LData.horizontalAlignment = GridData.FILL;
								viewer2LData.grabExcessHorizontalSpace = true;
								viewer2LData.verticalAlignment = GridData.FILL;
								viewer2LData.grabExcessVerticalSpace = true;
								viewer2.getControl()
										.setLayoutData(viewer2LData);
								viewer2.getTable().setHeaderVisible(true);
								// $protect>>$
								viewer2
										.addSelectionChangedListener(new ISelectionChangedListener() {
											public void selectionChanged(
													SelectionChangedEvent event) {
												validateView();
											}
										});
								// $protect<<$
								{
									colItemImage = new TableColumn(viewer2
											.getTable(), SWT.NONE);
									colItemImage.setWidth(20);
									colItemImage.setResizable(false);
								}
								{
									colItemDate = new TableColumn(viewer2
											.getTable(), SWT.NONE);
									colItemDate
											.setText(GUIMessages
													.getMessage("comp.general.itemdate"));
									colItemDate.setWidth(100);
								}
								{
									colChangedDate = new TableColumn(viewer2
											.getTable(), SWT.NONE);
									colChangedDate
											.setText(GUIMessages
													.getMessage("comp.avrg_charge_pane.corr_date"));
									colChangedDate.setWidth(100);
								}
								{
									colOperation = new TableColumn(viewer2
											.getTable(), SWT.NONE);
									colOperation
											.setText(GUIMessages
													.getMessage("comp.general.operation"));
									colOperation.setWidth(200);
								}
								{
									colCycle = new TableColumn(viewer2
											.getTable(), SWT.NONE);
									colCycle.setWidth(20);
									colCycle.setResizable(false);
								}
								{
									colReading = new TableColumn(viewer2
											.getTable(), SWT.RIGHT);
									colReading
											.setText(GUIMessages
													.getMessage("comp.general.reading"));
									colReading.setWidth(75);
								}
								{
									colKwh = new TableColumn(
											viewer2.getTable(), SWT.RIGHT);
									colKwh.setText(GUIMessages
											.getMessage("comp.general.kwh"));
									colKwh.setWidth(75);
								}
								{
									colGel = new TableColumn(
											viewer2.getTable(), SWT.RIGHT);
									colGel.setText(GUIMessages
											.getMessage("comp.general.gel"));
									colGel.setWidth(75);
								}
								{
									colAccount = new TableColumn(viewer2
											.getTable(), SWT.NONE);
									colAccount
											.setText(GUIMessages
													.getMessage("comp.general.account"));
									colAccount.setWidth(75);
								}
								{
									tableColumn1 = new TableColumn(viewer2
											.getTable(), SWT.NONE);
									tableColumn1.setWidth(10);
									tableColumn1.setResizable(false);
								}
							}
							{
								GridData toolBar2LData = new GridData();
								toolBar2LData.horizontalAlignment = GridData.FILL;
								toolBar2LData.grabExcessHorizontalSpace = true;
								toolBar2 = new ToolBar(composite2, SWT.NONE);
								toolBar2.setLayoutData(toolBar2LData);
								{
									tiCut = new ToolItem(toolBar2, SWT.NONE);
									tiCut.setImage(Plugin
											.getImage("icons/22x22/cut.png"));
									tiCut
											.setToolTipText(GUIMessages
													.getMessage("comp.avrg_charge_pane.cut_interval"));
									tiCut
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent e) {
													onCut();
												}
											});
								}
								{
									toolItem1 = new ToolItem(toolBar2,
											SWT.SEPARATOR);
									toolItem1.setText("");
								}
								{
									tiEnable = new ToolItem(toolBar2, SWT.NONE);
									tiEnable
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent e) {
													onEnable();
												}

											});
								}
								{
									toolItem2 = new ToolItem(toolBar2,
											SWT.SEPARATOR);
									toolItem2.setText("");
								}
								{
									tiChangeDate = new ToolItem(toolBar2,
											SWT.NONE);
									tiChangeDate
											.setToolTipText(GUIMessages
													.getMessage("comp.avrg_charge_pane.change_date"));
									tiChangeDate
											.setImage(Plugin
													.getImage("icons/22x22/history.png"));
									tiChangeDate
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent e) {
													onChangeDate();
												}
											});
								}
								{
									tiClearDate = new ToolItem(toolBar2,
											SWT.NONE);
									tiClearDate
											.setToolTipText(GUIMessages
													.getMessage("comp.avrg_charge_pane.clear_date"));
									tiClearDate
											.setImage(Plugin
													.getImage("icons/22x22/history_clear.png"));
									tiClearDate
											.addSelectionListener(new SelectionAdapter() {
												public void widgetSelected(
														SelectionEvent e) {
													onClearDate();
												}
											});
								}
							}
						}
						sashForm1.setWeights(new int[] { 1, 3 });
					}
				}
				{
					tabResults = new TabItem(tabFolder1, SWT.NONE);
					tabResults.setText(GUIMessages
							.getMessage("comp.avrg_charge_pane.results_tab"));
					{
						composite3 = new Composite(tabFolder1, SWT.NONE);
						GridLayout composite3Layout = new GridLayout();
						composite3Layout.horizontalSpacing = 0;
						composite3Layout.marginHeight = 0;
						composite3Layout.marginWidth = 0;
						composite3Layout.verticalSpacing = 0;
						composite3.setLayout(composite3Layout);
						tabResults.setControl(composite3);
						{
							GridData viewer3LData = new GridData();
							viewer3LData.horizontalAlignment = GridData.FILL;
							viewer3LData.grabExcessHorizontalSpace = true;
							viewer3LData.verticalAlignment = GridData.FILL;
							viewer3LData.grabExcessVerticalSpace = true;
							viewer3 = new TableViewer(composite3, SWT.BORDER
									| SWT.SINGLE | SWT.FULL_SELECTION
									| SWT.VIRTUAL);
							viewer3.getControl().setLayoutData(viewer3LData);
							viewer3.getTable().setHeaderVisible(true);
							viewer3.getTable().setLinesVisible(true);
							viewer3
									.addSelectionChangedListener(new ISelectionChangedListener() {
										public void selectionChanged(
												SelectionChangedEvent event) {
											AvearageKwhSeed seed = getSelectedSeed();
											if (seed == null) {
												txtAvearage.setText("");
											} else {
												txtAvearage
														.setText(nf2
																.format(seed
																		.getAveargeKwh()));
											}
										}
									});
							{
								tableColumn2 = new TableColumn(viewer3
										.getTable(), SWT.NONE);
								tableColumn2.setText(GUIMessages
										.getMessage("comp.general.name"));
								tableColumn2.setWidth(100);
							}
							{
								colStartDate = new TableColumn(viewer3
										.getTable(), SWT.NONE);
								colStartDate.setText(GUIMessages
										.getMessage("comp.general.start_date"));
								colStartDate.setWidth(130);
							}
							{
								colEndDate = new TableColumn(
										viewer3.getTable(), SWT.NONE);
								colEndDate.setText(GUIMessages
										.getMessage("comp.general.end_date"));
								colEndDate.setWidth(130);
							}
							{
								colDays = new TableColumn(viewer3.getTable(),
										SWT.RIGHT);
								colDays
										.setText(GUIMessages
												.getMessage("comp.avrg_charge_pane.days_interval"));
								colDays.setWidth(100);
							}
							{
								tableColumn3 = new TableColumn(viewer3
										.getTable(), SWT.RIGHT);
								tableColumn3.setText(GUIMessages
										.getMessage("comp.general.kwh"));
								tableColumn3.setWidth(100);
							}
							{
								colAvearage = new TableColumn(viewer3
										.getTable(), SWT.RIGHT);
								colAvearage
										.setText(GUIMessages
												.getMessage("comp.avrg_charge_pane.kwh_avearage"));
								colAvearage.setWidth(100);
							}
							{
								tableColumn4 = new TableColumn(viewer3
										.getTable(), SWT.NONE);
								tableColumn4.setWidth(10);
								tableColumn4.setResizable(false);
							}
						}
						{
							composite4 = new Composite(composite3, SWT.NONE);
							GridLayout composite4Layout = new GridLayout();
							composite4Layout.numColumns = 2;
							GridData composite4LData = new GridData();
							composite4LData.horizontalAlignment = GridData.FILL;
							composite4LData.grabExcessHorizontalSpace = true;
							composite4.setLayoutData(composite4LData);
							composite4.setLayout(composite4Layout);
							{
								label1 = new Label(composite4, SWT.NONE);
								label1
										.setText(GUIMessages
												.getMessage("comp.avrg_charge_pane.kwh_avearage"));
							}
							{
								GridData txtAvearageLData = new GridData();
								txtAvearageLData.horizontalAlignment = GridData.FILL;
								txtAvearageLData.grabExcessHorizontalSpace = true;
								txtAvearage = new Text(composite4,
										SWT.READ_ONLY | SWT.BORDER);
								txtAvearage.setLayoutData(txtAvearageLData);
							}
						}
					}
					tabResults.setImage(Plugin
							.getImage("icons/16x16/bop/voucher.png"));

				}
				GridData tabFolder1LData = new GridData();
				tabFolder1LData.horizontalAlignment = GridData.FILL;
				tabFolder1LData.grabExcessHorizontalSpace = true;
				tabFolder1LData.verticalAlignment = GridData.FILL;
				tabFolder1LData.grabExcessVerticalSpace = true;
				tabFolder1.setLayoutData(tabFolder1LData);
				tabFolder1.setSelection(0);
			}
			this.layout();
			// $protect>>$
			customInit();
			// $protect<<$
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void customInit() {
		validateView();
	}

	private void initDataBehaivour() {
		displayIntervals(null);
		viewer1.setInput(this);
		viewer1.setLabelProvider(new IntervalLabelProvider());
		displayItems(null);
		viewer2.setInput(this);
		viewer2.setLabelProvider(new ItemLabelProvider());
		displayResults(null);
		viewer3.setInput(this);
		viewer3.setLabelProvider(new ResultsLabelProvider());
	}

	private void displayIntervals(List intervals) {
		viewer1.setItemCount(intervals == null ? 0 : intervals.size());
		viewer1.setContentProvider(new CommonItemContentProvider(intervals));
	}

	private void displayItems(List items) {
		viewer2.setItemCount(items == null ? 0 : items.size());
		viewer2.setContentProvider(new CommonItemContentProvider(items));
	}

	private void displayResults(List results) {
		viewer3.setItemCount(results == null ? 0 : results.size());
		viewer3.setContentProvider(new CommonItemContentProvider(results));
	}

	private void validateItemsWithIntervals() {
		RecalcInterval interval = getSelectedInterval();
		displayItems(interval == null ? null : interval.getItems());
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void refresh() {
		tabFolder1.setSelection(0);
		if (account == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			CreateAvrgCalculationRecalc request = new CreateAvrgCalculationRecalc(
					Application.USER_NAME, Application.PASSWORD);
			request.setAccount(account);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			CreateAvrgCalculationRecalc callback = (CreateAvrgCalculationRecalc) resp
					.getRequest();
			this.recalc = (Recalc) callback.getRecalc();
			RecalcInterval interval = recalc.getDetails().getIntervals() == null ? null
					: (RecalcInterval) recalc.getDetails().getIntervals()
							.get(0);
			for (int i = 0; interval != null && interval.getItems() != null
					&& i < interval.getItems().size(); i++) {
				RecalcItem item = (RecalcItem) interval.getItems().get(i);
				item.setOperation(Cache.findOperationById(item.getOperation()
						.getId()));
			}
		} catch (Throwable t) {
			t.printStackTrace();
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
			this.recalc = null;
		}
		displayIntervals(recalc == null ? null : recalc.getDetails()
				.getIntervals());
		validateItemsWithIntervals();
		validateView();
	}

	public void clean() {
		displayIntervals(null);
		displayItems(null);
		displayResults(null);
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public RecalcInterval getSelectedInterval() {
		int index = viewer1.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer1
				.getContentProvider();
		return (RecalcInterval) provider.items.get(index);
	}

	@SuppressWarnings("unchecked")
	public List getSelectedItems() {
		int indecies[] = viewer2.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		List selection = new ArrayList();
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer2
				.getContentProvider();
		List items = provider.items;
		for (int i = 0; i < indecies.length; i++) {
			selection.add(items.get(indecies[i]));
		}
		return selection;
	}

	public RecalcItem getSelectedItem() {
		int index = viewer2.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer2
				.getContentProvider();
		List items = provider.items;
		return (RecalcItem) items.get(index);
	}

	private class IntervalLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			if (element == null) {
				return null;
			}
			RecalcInterval interval = (RecalcInterval) element;
			switch (columnIndex) {
			case 0:
				return interval.isEditable() ? Plugin
						.getImage("icons/16x16/bop/voucher.png") : null;
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return "";
			}
			RecalcInterval interval = (RecalcInterval) element;
			switch (columnIndex) {
			case 1:
				return interval.getName();
			case 2:
				return Date.format(interval.getStartDate());
			case 3:
				return Date.format(interval.getEndDate());
			default:
				return "";
			}
		}

	}

	private class ItemLabelProvider extends LabelProvider implements
			ITableLabelProvider, ITableColorProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			if (element == null) {
				return null;
			}
			RecalcItem item = (RecalcItem) element;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getItemImage(item);
			case 4:
				return item.getCycle() ? Plugin
						.getImage("icons/16x16/true.png") : null;
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return "";
			}
			RecalcItem item = (RecalcItem) element;
			switch (columnIndex) {
			case 1:
				return Date.format(item.getItemDate());
			case 2:
				return Date.format(item.getCurrentOperationDate());
			case 3:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation()
						.getName());
			case 5:
				return nf.format(item.getReading());
			case 6:
				return nf.format(item.getKwh());
			case 7:
				return nf.format(item.getGel());
			case 8:
				return item.getAccount().getNumber();
			default:
				return "";
			}
		}

		public Color getForeground(Object element, int columnIndex) {
			RecalcItem item = (RecalcItem) element;
			switch (item.getStatus()) {
			case RecalcItem.DELETED:
				return Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
			case RecalcItem.OTHER:
				return Display.getDefault().getSystemColor(SWT.COLOR_BLUE);
			case RecalcItem.NEW:
				return Display.getDefault()
						.getSystemColor(SWT.COLOR_DARK_GREEN);
			default:
				return null;
			}
		}

		public Color getBackground(Object element, int columnIndex) {
			return null;
		}

	}

	private class ResultsLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return "";
			}
			AvearageKwhSeed seed = (AvearageKwhSeed) element;
			switch (columnIndex) {
			case 0:
				return seed.getName();
			case 1:
				return Date.format(seed.getStartDate());
			case 2:
				return Date.format(seed.getEndDate());
			case 3:
				return String.valueOf(seed.getDayInterval());
			case 4:
				return nf.format(seed.getKwh());
			case 5:
				return nf2.format(seed.getAveargeKwh());
			default:
				return "";
			}
		}

	}

	private void validateView() {

		// get parameters
		RecalcInterval interval = getSelectedInterval();
		RecalcItem selectedItem = getSelectedItem();
		boolean itemsSelected = selectedItem != null;
		boolean intervalSelected = interval != null;

		// cut
		tiCut.setEnabled(intervalSelected && itemsSelected
				&& !interval.isEditable());

		// delete interval
		tiDeleteInterval.setEnabled(intervalSelected && interval.isEditable());

		// change date
		tiChangeDate.setEnabled(intervalSelected && itemsSelected);
		tiClearDate.setEnabled(intervalSelected && itemsSelected
				&& selectedItem.getCurrentOperationDate() != null);

		// enable/disable item
		if (!itemsSelected || selectedItem.getStatus() == RecalcItem.ORIGINAL) {
			tiEnable.setEnabled(itemsSelected);
			tiEnable.setImage(Plugin.getImage("icons/22x22/disable.png"));
			tiEnable.setToolTipText(GUIMessages
					.getMessage("comp.avrg_charge_pane.disable_item"));
		} else {
			tiEnable.setEnabled(true);
			tiEnable.setImage(Plugin.getImage("icons/22x22/enable.png"));
			tiEnable.setToolTipText(GUIMessages
					.getMessage("comp.avrg_charge_pane.enable_item"));
		}

	}

	private void onDelete() {
		if (recalc == null) {
			return;
		}
		RecalcInterval interval = getSelectedInterval();
		// int intervalIndex = viewer1.getTable().getSelectionIndex();
		if (!interval.isEditable()) {
			return;
		}
		interval.setEditable(false);
		validateRecalcIntervals(recalc);
		displayIntervals(recalc.getDetails().getIntervals());
		validateItemsWithIntervals();
		validateView();
	}

	@SuppressWarnings("unchecked")
	private void onCut() {
		if (recalc == null) {
			return;
		}
		RecalcInterval interval = getSelectedInterval();
		int intervalIndex = viewer1.getTable().getSelectionIndex();
		if (interval.isEditable()) {
			return;
		}
		int indecies[] = viewer2.getTable().getSelectionIndices();
		int index1 = indecies[0]; // first index
		int index2 = indecies[indecies.length - 1]; // last index

		// first interval
		RecalcInterval interval_1 = new RecalcInterval();
		interval_1.setEditable(false);
		interval_1.setName("temp_1");
		List intItems_1 = new ArrayList();
		for (int i = 0; i < index1; i++) {
			RecalcItem item = (RecalcItem) interval.getItems().get(i);
			item.setInterval(interval_1);
			intItems_1.add(item);
		}
		interval_1.setItems(intItems_1);
		interval_1.validateDates();

		// second interval
		RecalcInterval interval_2 = new RecalcInterval();
		interval_2.setEditable(true);
		interval_2.setName("temp_2");
		List intItems_2 = new ArrayList();
		for (int i = index1; i <= index2; i++) {
			RecalcItem item = (RecalcItem) interval.getItems().get(i);
			item.setInterval(interval_2);
			intItems_2.add(item);
		}
		interval_2.setItems(intItems_2);
		interval_2.validateDates();

		// third interval
		RecalcInterval interval_3 = new RecalcInterval();
		interval_3.setEditable(false);
		interval_3.setName("temp_3");
		List intItems_3 = new ArrayList();
		for (int i = index2 + 1; i < interval.getItems().size(); i++) {
			RecalcItem item = (RecalcItem) interval.getItems().get(i);
			item.setInterval(interval_3);
			intItems_3.add(item);
		}
		interval_3.setItems(intItems_3);
		interval_3.validateDates();

		// insert intervals
		// int index = recalc.getDetails().getIntervals().indexOf(interval);
		int selectIndex = -1;
		int count = 0;
		recalc.getDetails().getIntervals().remove(intervalIndex);
		if (interval_1.getItems().size() > 0) {
			recalc.getDetails().getIntervals().add(intervalIndex + count,
					interval_1);
			count++;
		}
		if (interval_2.getItems().size() > 0) {
			recalc.getDetails().getIntervals().add(intervalIndex + count,
					interval_2);
			selectIndex = intervalIndex + count;
			count++;
		}
		if (interval_3.getItems().size() > 0) {
			recalc.getDetails().getIntervals().add(intervalIndex + count,
					interval_3);
			count++;
		}

		// show intervals
		validateRecalcIntervals(recalc);
		displayIntervals(recalc.getDetails().getIntervals());
		viewer1.getTable().setSelection(selectIndex);
		validateItemsWithIntervals();
		validateView();

	}

	private NumberFormat nf = new DecimalFormat("###00");

	private NumberFormat nf2 = new DecimalFormat("#,###.00###");

	private int count_editable, count_not_editable;

	@SuppressWarnings("unchecked")
	private void validateRecalcIntervals(Recalc recalc) {
		resetIntervalCounters();
		List intervals = recalc.getDetails().getIntervals();
		List newIntervals = new ArrayList();
		RecalcInterval prevInterval = null;
		RecalcInterval newInterval = null;
		for (int i = 0; i < intervals.size(); i++) {
			RecalcInterval interval = (RecalcInterval) intervals.get(i);
			boolean add = false;
			if (prevInterval == null) {
				newInterval = interval;
				newInterval.validateDates();
				add = true;
			} else {
				if (interval.isEditable() == prevInterval.isEditable()) {
					List fullItems = new ArrayList();
					fullItems.addAll(newInterval.getItems());
					fullItems.addAll(interval.getItems());
					newInterval.setItems(fullItems);
					newInterval.validateDates();
					add = false;
				} else {
					newInterval = interval;
					newInterval.validateDates();
					add = true;
				}
			}
			if (add) {
				newInterval.setName(getName(newInterval.isEditable()));
				newIntervals.add(newInterval);
			}
			prevInterval = interval;
		}
		recalc.getDetails().getIntervals().clear();
		recalc.getDetails().getIntervals().addAll(newIntervals);
	}

	private void resetIntervalCounters() {
		count_editable = 1;
		count_not_editable = 1;
	}

	private String getName(boolean editable) {
		String name = null;
		if (editable) {
			name = nf.format(count_editable);
			count_editable++;
		} else {
			name = "sys_" + nf.format(count_not_editable);
			count_not_editable++;
		}
		return name;
	}

	public void makeCalculation() {
		try {
			if (!Application.validateConnection()) {
				return;
			}
			AvrgChargeCalculationRequest request = new AvrgChargeCalculationRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRecalc(recalc);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			AvrgChargeCalculationRequest callback = (AvrgChargeCalculationRequest) resp
					.getRequest();
			List results = callback.getResults();

			displayResults(results);
			tabFolder1.setSelection(1);

		} catch (Throwable t) {
			// t.printStackTrace();
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	private AvearageKwhSeed getSelectedSeed() {
		int index = viewer3.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer3
				.getContentProvider();
		return (AvearageKwhSeed) provider.items.get(index);
	}

	private void onChangeDate() {
		RecalcItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		int index = viewer2.getTable().getSelectionIndex();
		Date date = item.getCurrentOperationDate() == null ? item.getItemDate()
				: item.getCurrentOperationDate();
		SWTCalendarDialog dialog = new SWTCalendarDialog(getShell(), SWT.NONE);
		GUIUtils.centerShell(dialog.getShell());
		Calendar cal = null;
		cal = Calendar.getInstance();
		cal.setTime(date.toDate());
		dialog.open(cal, cal);
		if (!dialog.isApproved()) {
			return;
		}
		item.setCurrentOperationDate(Date
				.create(dialog.getCalendar().getTime()));
		viewer2.refresh();
		viewer2.getTable().setSelection(index);
		viewer2.getTable().showSelection();
		validateView();
	}

	private void onClearDate() {
		RecalcItem item = getSelectedItem();
		if (item == null || item.getCurrentOperationDate() == null) {
			return;
		}
		int index = viewer2.getTable().getSelectionIndex();
		item.setCurrentOperationDate(null);
		viewer2.refresh();
		viewer2.getTable().setSelection(index);
		viewer2.getTable().showSelection();
		validateView();
	}

	private void onEnable() {
		RecalcItem item = getSelectedItem();
		List items = getSelectedItems();
		if (item == null) {
			return;
		}
		boolean enable = item.getStatus() != RecalcItem.ORIGINAL;
		String msg;
		if (enable) {
			msg = GUIMessages.getMessage(
					"comp.general.confirm_enable_object_count",
					new Object[] { new Integer(items.size()) });
		} else {
			msg = GUIMessages.getMessage(
					"comp.general.confirm_disable_object_count",
					new Object[] { new Integer(items.size()) });
		}
		String title = GUIMessages.getMessage("comp.general.confirm");
		boolean resp = MessageDialog.openQuestion(getShell(), title, msg);
		if (!resp) {
			return;
		}

		for (int i = 0; i < items.size(); i++) {
			RecalcItem it = (RecalcItem) items.get(i);
			it.setStatus(enable ? RecalcItem.ORIGINAL : RecalcItem.DELETED);
		}
		int indices[] = viewer2.getTable().getSelectionIndices();
		validateItemsWithIntervals();
		viewer2.getTable().setSelection(indices);
		viewer2.getTable().showSelection();
		validateView();
	}

}
