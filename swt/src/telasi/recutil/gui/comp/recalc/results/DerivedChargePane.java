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
package telasi.recutil.gui.comp.recalc.results;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.CalculationInterval;
import telasi.recutil.beans.CalculationItem;
import telasi.recutil.beans.Date;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.comp.recalc.RecalcChargePane;
import telasi.recutil.gui.comp.recalc.item.RecalcItemProperties;
import telasi.recutil.gui.comp.recalc.item.SummaryPane;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.utils.SummaryCalculator;

public class DerivedChargePane extends Composite {
	private TableViewer viewer2;
	private Composite composite2;
	private Composite composite1;
	private SashForm sashForm1;
	private TableColumn colDocument;
	private ToolItem tiShowProducer;
	private ToolBar toolBar1;
	//private TableColumn colCorrReading;
	//private TableColumn colCorrGel;
	//private TableColumn colCorrKwh;
	private TableViewer viewer1;
	//private TableColumn colBalance;
	private TableColumn colGel;
	private TableColumn colKwh;
	private TableColumn colReading;
	private TableColumn colCycle;
	private TableColumn colAccount;
	private ToolItem tiDetails;
	private TableColumn colItemDate;
	private TableColumn colOperation;
	private TableColumn colImage;
	private SummaryPane summaryPane1;

	public DerivedChargePane(Composite parent, int style) {
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
			this.setSize(757, 245);
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiShowProducer = new ToolItem(toolBar1, SWT.NONE);
					tiShowProducer.setImage(Plugin
							.getImage("icons/22x22/commit.png"));
					tiShowProducer
							.setToolTipText(GUIMessages
									.getMessage("comp.derived_item_pane.show_producer"));
					tiShowProducer.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							onShowProducer();
						}
					});
				}
				{
					tiDetails = new ToolItem(toolBar1, SWT.NONE);
					tiDetails.setImage(Plugin.getImage("icons/22x22/goto.png"));
					tiDetails.setToolTipText(GUIMessages
							.getMessage("comp.derived_item_pane.show_trace"));
					tiDetails.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							onShowTrace();
						}
					});
				}
			}
			{
				sashForm1 = new SashForm(this, SWT.NONE);
				GridData sashForm1LData = new GridData();
				sashForm1LData.horizontalAlignment = GridData.FILL;
				sashForm1LData.grabExcessHorizontalSpace = true;
				sashForm1LData.verticalAlignment = GridData.FILL;
				sashForm1LData.grabExcessVerticalSpace = true;
				sashForm1.setLayoutData(sashForm1LData);
				sashForm1.setSize(60, 30);
				{
					composite1 = new Composite(sashForm1, SWT.NONE);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.horizontalSpacing = 0;
					composite1Layout.marginHeight = 0;
					composite1Layout.marginWidth = 0;
					composite1Layout.verticalSpacing = 0;
					composite1.setLayout(composite1Layout);
					composite1.setBounds(39, -28, 757, 133);
					{
						GridData viewer1LData = new GridData();
						viewer1LData.horizontalAlignment = GridData.FILL;
						viewer1LData.grabExcessHorizontalSpace = true;
						viewer1LData.verticalAlignment = GridData.FILL;
						viewer1LData.grabExcessVerticalSpace = true;
						viewer1 = new TableViewer(composite1, SWT.BORDER
								| SWT.FULL_SELECTION | SWT.VIRTUAL | SWT.SINGLE);
						viewer1.getControl().setLayoutData(viewer1LData);
						viewer1.getTable().setHeaderVisible(true);
						viewer1.getTable().setLinesVisible(true);
						viewer1
								.addSelectionChangedListener(new ISelectionChangedListener() {
									public void selectionChanged(
											SelectionChangedEvent event) {
										validateView();
									}
								});
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
					composite2.setBounds(383, -2, 377, 245);
					{
						viewer2 = new TableViewer(composite2, SWT.BORDER
								| SWT.FULL_SELECTION | SWT.MULTI | SWT.VIRTUAL);
						viewer2.getTable().setHeaderVisible(true);
						GridData viewerLData = new GridData();
						viewerLData.grabExcessHorizontalSpace = true;
						viewerLData.grabExcessVerticalSpace = true;
						viewerLData.verticalAlignment = GridData.FILL;
						viewerLData.horizontalAlignment = GridData.FILL;
						viewer2.getControl().setLayoutData(viewerLData);
						viewer2.getTable().setLinesVisible(true);
						viewer2
								.addSelectionChangedListener(new ISelectionChangedListener() {
									public void selectionChanged(
											SelectionChangedEvent event) {
										validateItemView();

									}
								});
						viewer2.getTable().addMouseListener(new MouseAdapter() {

							public void mouseDoubleClick(MouseEvent arg0) {
								onShowProducer();
							}
						});
						{
							colImage = new TableColumn(viewer2.getTable(),
									SWT.NONE);
							colImage.setWidth(20);
						}
						{
							colItemDate = new TableColumn(viewer2.getTable(),
									SWT.NONE);
							colItemDate.setText(GUIMessages
									.getMessage("comp.general.itemdate"));
							colItemDate.setWidth(75);
						}
						{
							colAccount = new TableColumn(viewer2.getTable(),
									SWT.NONE);
							colAccount.setText(GUIMessages
									.getMessage("comp.general.account"));
							colAccount.setWidth(75);
						}
						{
							colOperation = new TableColumn(viewer2.getTable(),
									SWT.NONE);
							colOperation.setText(GUIMessages
									.getMessage("comp.general.operation"));
							colOperation.setWidth(200);
						}
						{
							colCycle = new TableColumn(viewer2.getTable(),
									SWT.NONE);
							colCycle.setWidth(20);
						}
						{
							colReading = new TableColumn(viewer2.getTable(),
									SWT.RIGHT);
							colReading.setText(GUIMessages
									.getMessage("comp.general.reading"));
							colReading.setWidth(75);
						}
						{
							colKwh = new TableColumn(viewer2.getTable(),
									SWT.RIGHT);
							colKwh.setText(GUIMessages
									.getMessage("comp.general.kwh"));
							colKwh.setWidth(75);
						}
						{
							colGel = new TableColumn(viewer2.getTable(),
									SWT.RIGHT);
							colGel.setText(GUIMessages
									.getMessage("comp.general.gel"));
							colGel.setWidth(75);
						}
//						{
//							colBalance = new TableColumn(viewer2.getTable(),
//									SWT.RIGHT);
//							colBalance.setText(GUIMessages
//									.getMessage("comp.general.balance"));
//							colBalance.setWidth(75);
//						}
						{
							colDocument = new TableColumn(viewer2.getTable(),
									SWT.NONE);
							colDocument.setText(GUIMessages
									.getMessage("comp.general.document"));
							colDocument.setWidth(150);
						}
//						{
//							colCorrReading = new TableColumn(
//									viewer2.getTable(), SWT.RIGHT);
//							colCorrReading.setText(GUIMessages
//									.getMessage("comp.general.corr_reading"));
//							colCorrReading.setWidth(75);
//						}
//						{
//							colCorrKwh = new TableColumn(viewer2.getTable(),
//									SWT.RIGHT);
//							colCorrKwh.setText(GUIMessages
//									.getMessage("comp.general.corr_kwh"));
//							colCorrKwh.setWidth(75);
//						}
//						{
//							colCorrGel = new TableColumn(viewer2.getTable(),
//									SWT.RIGHT);
//							colCorrGel.setText(GUIMessages
//									.getMessage("comp.general.corr_gel"));
//							colCorrGel.setWidth(75);
//						}
					}
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
			// $protect>>$
			sashForm1.setWeights(new int[] { 1, 4 });
			// $protect<<$
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayIntervals(null);
		viewer1.setInput(this);
		viewer1.setLabelProvider(new IntervalLabelProvider());

		displayItems(null);
		viewer2.setInput(this);
		viewer2.setLabelProvider(new ItemLabelProvider());
	}

	public void displayIntervals(List intervals) {
		viewer1.getTable().deselectAll();
		viewer1.setItemCount(intervals == null ? 0 : intervals.size());
		viewer1.setContentProvider(new CommonItemContentProvider(intervals));
		validateView();
	}

	private void displayItems(CalculationInterval interval) {
		List items = null;
		if (interval != null)
			items = interval.getItems();
		viewer2.setItemCount(items == null ? 0 : items.size());
		viewer2.setContentProvider(new CommonItemContentProvider(items));
	}

	private class IntervalLabelProvider extends LabelProvider {

		public String getText(Object element) {
			CalculationInterval interval = (CalculationInterval) element;
			return interval == null ? "" : interval.getName();
		}

	}

	private class ItemLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			CalculationItem item = (CalculationItem) element;
			if (item == null) {
				return null;
			}
			switch (columnIndex) {
			case 0:
				return GUIUtils.getOperationImage(item.getOperation());
			case 4:
				return item.getCycle() ? Plugin
						.getImage("icons/16x16/true.png") : null;
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			CalculationItem item = (CalculationItem) element;
			if (item == null) {
				return "";
			}
			switch (columnIndex) {
			case 1:
				return Date.format(item.getItemDate());
			case 2:
				return item.getAccount().getNumber();
			case 3:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation()
						.getName());
			case 5:
				return nf.format(item.getReading());
			case 6:
				return nf.format(item.getCharge().getKwh());
			case 7:
				return nf.format(item.getCharge().getGel());
//			case 8:
//				return nf.format(item.getBalance());
			case 8:
				return item.getItemNumber() == null ? "" : item.getItemNumber(); // document
//			case 10:
//				// return item.getCorrectedReading() == null ? "" :
//				// nf.format(item
//				// .getCorrectedReading());
//				return "";
//			case 11:
//				// return item.getCorrectedKwh() == null ? "" : nf.format(item
//				// .getCorrectedKwh());
//				return "";
//			case 12:
//				// return item.getCorrectedGel() == null ? "" : nf.format(item
//				// .getCorrectedGel());
//				return "";
			default:
				return "";
			}
		}
	}

	public CalculationInterval getSelectedInterval() {
		int index = viewer1.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer1
				.getContentProvider();
		return (CalculationInterval) provider.items.get(index);
	}

	private void validateView() {
		displayItems(getSelectedInterval());
		validateItemView();
	}

	private void validateItemView() {
		CalculationItem item = getSelectedItem();
		List items = getSelectedItems();

		//
		boolean isSelected = item != null;
		boolean hasProducer = isSelected && item.getProducer() != null;
		tiShowProducer.setEnabled(hasProducer);
		tiDetails.setEnabled(isSelected);

		// summaries
		double gel = 0.f;
		double kwh = 0.f;
		double balance = 0.f;
		int record = -1;
		int full = -1;
		if (items != null) {
			SummaryCalculator calc = new SummaryCalculator();
			calc.calculateSummary(items);
			gel = calc.getGel();
			kwh = calc.getKwh();
			balance = calc.getBalance();
			CommonItemContentProvider provider = (CommonItemContentProvider) viewer2
					.getContentProvider();
			full = provider.items.size();
			record = provider.items.indexOf(item) + 1;
		}
		summaryPane1.setGel(gel);
		summaryPane1.setKwh(kwh);
		summaryPane1.setBalance(balance);
		summaryPane1.setLocation(record, full);
	}

	public CalculationItem getSelectedItem() {
		int index = viewer2.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer2
				.getContentProvider();
		return (CalculationItem) provider.items.get(index);
	}

	@SuppressWarnings("unchecked")
	public List getSelectedItems() {
		int indecies[] = viewer2.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		List selected = new ArrayList();
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer2
				.getContentProvider();
		for (int i = 0; i < indecies.length; i++) {
			int index = indecies[i];
			selected.add(provider.items.get(index));
		}
		return selected;
	}

	private void onShowProducer() {
		CalculationItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		if (item.getProducer() == null) {
			return;
		}
		RecalcItemProperties prop = new RecalcItemProperties(getShell(),
				SWT.NONE);
		prop.setItem(item.getProducer());
		prop.open();
		if (prop.isApproved()) {
			if (relatedChargePane != null) {
				relatedChargePane.refresh();
			}
		}
	}

	private void onShowTrace() {
		CalculationItem item = getSelectedItem();
		if (item == null)
			return;
		TraceDialog td = new TraceDialog(getShell(), SWT.NONE);
		td.setCharge(item.getCharge());
		td.open();
	}

	private RecalcChargePane relatedChargePane;

	public void setRelatedChargePane(RecalcChargePane relatedChargePane) {
		this.relatedChargePane = relatedChargePane;
	}

}
