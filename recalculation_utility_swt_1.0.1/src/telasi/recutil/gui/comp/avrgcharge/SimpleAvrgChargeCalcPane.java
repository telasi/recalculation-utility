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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.avrgcalc.AvearageKwhSeed;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.service.eclipse.avrgcharge.AvrgChargeCalculationRequest;

public class SimpleAvrgChargeCalcPane extends Composite {

	private TabFolder tabFolder;
	private TabItem tabItem2;
	private TableColumn tableColumn1;
	private TableColumn colName;
	private Text txtAvearage;
	private Label label1;
	private Composite composite3;
	private TableColumn tableColumn2;
	private TableColumn colAvrgKwh;
	private TableColumn colKwh;
	private TableColumn colDays;
	private TableColumn colStart;
	private TableViewer viewer2;
	private Composite composite2;
	private ToolItem tiDown;
	private ToolItem tiUp;
	private ToolItem toolItem1;
	private TableColumn colDigits;
	private TableColumn colAcceleration;
	private TableColumn colCoeff;
	private TableColumn colEnd;
	private TableColumn colR2;
	private TableColumn colR1;
	private TableColumn colEndDate;
	private TableColumn colStartDate;
	private TableViewer viewer1;
	private ToolItem tiProperties;
	private ToolItem tiDelete;
	private ToolItem tiNew;
	private ToolBar toolBar1;
	private Composite composite1;
	private TabItem tabItem1;
	private Recalc recalc = new Recalc();

	public SimpleAvrgChargeCalcPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
		reset();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(762, 257);
			{
				tabFolder = new TabFolder(this, SWT.NONE);
				{
					tabItem1 = new TabItem(tabFolder, SWT.NONE);
					tabItem1
							.setText(GUIMessages
									.getMessage("comp.simple_avrg_charge.readings_tab"));
					{
						composite1 = new Composite(tabFolder, SWT.NONE);
						GridLayout composite1Layout = new GridLayout();
						composite1Layout.horizontalSpacing = 0;
						composite1Layout.marginHeight = 0;
						composite1Layout.marginWidth = 0;
						composite1Layout.verticalSpacing = 0;
						composite1.setLayout(composite1Layout);
						tabItem1.setControl(composite1);
						{
							GridData toolBar1LData = new GridData();
							toolBar1LData.horizontalAlignment = GridData.FILL;
							toolBar1LData.grabExcessHorizontalSpace = true;
							toolBar1 = new ToolBar(composite1, SWT.NONE);
							toolBar1.setLayoutData(toolBar1LData);
							{
								tiNew = new ToolItem(toolBar1, SWT.NONE);
								tiNew.setImage(Plugin
										.getImage("icons/22x22/new.gif"));
								tiNew
										.setToolTipText(GUIMessages
												.getMessage("comp.simple_avrg_charge.new_interval"));
								tiNew
										.addSelectionListener(new SelectionAdapter() {
											public void widgetSelected(
													SelectionEvent e) {
												onNew();
											}
										});
							}
							{
								tiDelete = new ToolItem(toolBar1, SWT.NONE);
								tiDelete.setImage(Plugin
										.getImage("icons/22x22/trash.png"));
								tiDelete
										.setToolTipText(GUIMessages
												.getMessage("comp.simple_avrg_charge.delete_interval"));
								tiDelete
										.addSelectionListener(new SelectionAdapter() {
											public void widgetSelected(
													SelectionEvent e) {
												onDelete();
											}
										});
							}
							{
								tiProperties = new ToolItem(toolBar1, SWT.NONE);
								tiProperties
										.setImage(Plugin
												.getImage("icons/22x22/properties.gif"));
								tiProperties
										.setToolTipText(GUIMessages
												.getMessage("comp.simple_avrg_charge.interval_properties"));
								tiProperties
										.addSelectionListener(new SelectionAdapter() {
											public void widgetSelected(
													SelectionEvent e) {
												onProperties();
											}
										});
							}
							{
								toolItem1 = new ToolItem(toolBar1,
										SWT.SEPARATOR);
								toolItem1.setText("");
							}
							{
								tiUp = new ToolItem(toolBar1, SWT.NONE);
								tiUp.setImage(Plugin
										.getImage("icons/22x22/up.png"));
								tiUp.setToolTipText("move up");
								tiUp
										.addSelectionListener(new SelectionAdapter() {
											public void widgetSelected(
													SelectionEvent e) {
												onMove(true);
											}
										});
							}
							{
								tiDown = new ToolItem(toolBar1, SWT.NONE);
								tiDown.setImage(Plugin
										.getImage("icons/22x22/down.png"));
								tiDown.setToolTipText("move down");
								tiDown
										.addSelectionListener(new SelectionAdapter() {
											public void widgetSelected(
													SelectionEvent e) {
												onMove(false);
											}
										});
							}
						}
						{
							GridData viewer1LData = new GridData();
							viewer1LData.horizontalAlignment = GridData.FILL;
							viewer1LData.grabExcessHorizontalSpace = true;
							viewer1LData.verticalAlignment = GridData.FILL;
							viewer1LData.grabExcessVerticalSpace = true;
							viewer1 = new TableViewer(composite1, SWT.BORDER
									| SWT.FULL_SELECTION | SWT.MULTI
									| SWT.VIRTUAL);
							viewer1.getTable().setLinesVisible(true);
							viewer1.getTable().setHeaderVisible(true);
							viewer1.getControl().setLayoutData(viewer1LData);
							viewer1
									.addSelectionChangedListener(new ISelectionChangedListener() {
										public void selectionChanged(
												SelectionChangedEvent event) {
											validateView();
										}
									});
							{
								colStartDate = new TableColumn(viewer1
										.getTable(), SWT.NONE);
								colStartDate.setText(GUIMessages
										.getMessage("comp.general.start_date"));
								colStartDate.setWidth(150);
							}
							{
								colR1 = new TableColumn(viewer1.getTable(),
										SWT.RIGHT);
								colR1
										.setText(GUIMessages
												.getMessage("comp.simple_avrg_charge.r1"));
								colR1.setWidth(100);
							}
							{
								colEndDate = new TableColumn(
										viewer1.getTable(), SWT.NONE);
								colEndDate.setText(GUIMessages
										.getMessage("comp.general.end_date"));
								colEndDate.setWidth(150);
							}
							{
								colR2 = new TableColumn(viewer1.getTable(),
										SWT.RIGHT);
								colR2
										.setText(GUIMessages
												.getMessage("comp.simple_avrg_charge.r2"));
								colR2.setWidth(100);
							}
							{
								colCoeff = new TableColumn(viewer1.getTable(),
										SWT.RIGHT);
								colCoeff.setText(GUIMessages
										.getMessage("comp.general.coeff"));
								colCoeff.setWidth(150);
							}
							{
								colDigits = new TableColumn(viewer1.getTable(),
										SWT.RIGHT);
								colDigits
										.setText(GUIMessages
												.getMessage("comp.general.meter_digits"));
								colDigits.setWidth(150);
							}
							{
								colAcceleration = new TableColumn(viewer1
										.getTable(), SWT.RIGHT);
								colAcceleration
										.setText(GUIMessages
												.getMessage("comp.general.acceleration"));
								colAcceleration.setWidth(150);
							}
							{
								colEnd = new TableColumn(viewer1.getTable(),
										SWT.NONE);
								colEnd.setWidth(10);
								colEnd.setResizable(false);
							}
						}
					}
					tabItem1.setImage(Plugin
							.getImage("icons/16x16/bop/reading.png"));
				}
				{
					tabItem2 = new TabItem(tabFolder, SWT.NONE);
					tabItem2.setText(GUIMessages
							.getMessage("comp.simple_avrg_charge.results_tab"));
					{
						composite2 = new Composite(tabFolder, SWT.NONE);
						GridLayout composite2Layout = new GridLayout();
						composite2Layout.horizontalSpacing = 0;
						composite2Layout.marginHeight = 0;
						composite2Layout.marginWidth = 0;
						composite2Layout.verticalSpacing = 0;
						composite2.setLayout(composite2Layout);
						tabItem2.setControl(composite2);
						{
							GridData viewer2LData = new GridData();
							viewer2LData.horizontalAlignment = GridData.FILL;
							viewer2LData.grabExcessHorizontalSpace = true;
							viewer2LData.verticalAlignment = GridData.FILL;
							viewer2LData.grabExcessVerticalSpace = true;
							viewer2 = new TableViewer(composite2, SWT.BORDER
									| SWT.FULL_SELECTION | SWT.SINGLE
									| SWT.VIRTUAL);
							viewer2.getControl().setLayoutData(viewer2LData);
							viewer2.getTable().setLinesVisible(true);
							viewer2.getTable().setHeaderVisible(true);
							{
								colName = new TableColumn(viewer2.getTable(),
										SWT.NONE);
								colName.setText(GUIMessages
										.getMessage("comp.general.name"));
								colName.setWidth(150);
							}
							viewer2
									.addSelectionChangedListener(new ISelectionChangedListener() {
										public void selectionChanged(
												SelectionChangedEvent event) {
											AvearageKwhSeed seed = getSelectedSeed();
											if (seed == null) {
												txtAvearage.setText("");
											} else {
												txtAvearage
														.setText(nf3
																.format(seed
																		.getAveargeKwh()));
											}
										}
									});
							{
								colStart = new TableColumn(viewer2.getTable(),
										SWT.NONE);
								colStart.setText(GUIMessages
										.getMessage("comp.general.start_date"));
								colStart.setWidth(150);
							}
							{
								tableColumn1 = new TableColumn(viewer2
										.getTable(), SWT.NONE);
								tableColumn1.setText(GUIMessages
										.getMessage("comp.general.end_date"));
								tableColumn1.setWidth(150);
							}
							{
								colDays = new TableColumn(viewer2.getTable(),
										SWT.RIGHT);
								colDays
										.setText(GUIMessages
												.getMessage("comp.avrg_charge_pane.days_interval"));
								colDays.setWidth(150);
							}
							{
								colKwh = new TableColumn(viewer2.getTable(),
										SWT.RIGHT);
								colKwh.setText(GUIMessages
										.getMessage("comp.general.kwh"));
								colKwh.setWidth(150);
							}
							{
								colAvrgKwh = new TableColumn(
										viewer2.getTable(), SWT.RIGHT);
								colAvrgKwh
										.setText(GUIMessages
												.getMessage("comp.avrg_charge_pane.kwh_avearage"));
								colAvrgKwh.setWidth(150);
							}
							{
								tableColumn2 = new TableColumn(viewer2
										.getTable(), SWT.NONE);
								tableColumn2.setWidth(10);
								tableColumn2.setResizable(false);
							}
						}
						{
							composite3 = new Composite(composite2, SWT.NONE);
							GridLayout composite3Layout = new GridLayout();
							composite3Layout.numColumns = 2;
							composite3Layout.marginWidth = 0;
							composite3Layout.marginHeight = 0;
							GridData composite3LData = new GridData();
							composite3LData.horizontalAlignment = GridData.FILL;
							composite3LData.grabExcessHorizontalSpace = true;
							composite3.setLayoutData(composite3LData);
							composite3.setLayout(composite3Layout);
							{
								label1 = new Label(composite3, SWT.NONE);
								label1
										.setText(GUIMessages
												.getMessage("comp.avrg_charge_pane.kwh_avearage"));
							}
							{
								GridData txtAvearageLData = new GridData();
								txtAvearageLData.horizontalAlignment = GridData.FILL;
								txtAvearageLData.grabExcessHorizontalSpace = true;
								txtAvearage = new Text(composite3,
										SWT.READ_ONLY | SWT.BORDER);
								txtAvearage.setLayoutData(txtAvearageLData);
							}
						}
					}
					tabItem2.setImage(Plugin
							.getImage("icons/16x16/bop/voucher.png"));
				}
				GridData tabFolderLData = new GridData();
				tabFolderLData.horizontalAlignment = GridData.FILL;
				tabFolderLData.grabExcessHorizontalSpace = true;
				tabFolderLData.verticalAlignment = GridData.FILL;
				tabFolderLData.grabExcessVerticalSpace = true;
				tabFolder.setLayoutData(tabFolderLData);
				tabFolder.setSelection(0);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayIntervals(null);
		viewer1.setInput(this);
		viewer1.setLabelProvider(new IntervalContentProvider());

		displayResults(null);
		viewer2.setInput(this);
		viewer2.setLabelProvider(new ResultsLabelProvider());
	}

	public void calculate() {
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
			tabFolder.setSelection(1);

		} catch (Throwable t) {
			// t.printStackTrace();
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	public Recalc getRecalc() {
		return recalc;
	}

	@SuppressWarnings("unchecked")
	private void onNew() {
		AvrgChargeIntervalProperties prop = new AvrgChargeIntervalProperties(
				getShell(), SWT.NONE);
		prop.open();

		if (!prop.isApproved()) {
			return;
		}

		recalc.getDetails().getIntervals().add(prop.getInterval());
		reset();

	}

	@SuppressWarnings("unchecked")
	private void onDelete() {
		List list = getSelectedIntervals();
		if (list == null || list.isEmpty()) {
			return;
		}
		String msg = GUIMessages.getMessage(
				"comp.general.confirm_delete_object_count",
				new Object[] { new Integer(list.size()) });
		String title = GUIMessages.getMessage("comp.general.confirm");
		boolean resp = MessageDialog.openQuestion(getShell(), title, msg);
		if (!resp) {
			return;
		}
		System.out.println(list.size());
		recalc.getDetails().getIntervals().removeAll(list);
		reset();
	}

	@SuppressWarnings("unchecked")
	private void onProperties() {
		RecalcInterval interval = getSelectedInterval();
		if (interval == null) {
			return;
		}
		AvrgChargeIntervalProperties prop = new AvrgChargeIntervalProperties(
				getShell(), SWT.NONE);
		prop.setInterval(interval);
		prop.open();

		if (!prop.isApproved()) {
			return;
		}
		int index = viewer1.getTable().getSelectionIndex();
		recalc.getDetails().getIntervals().remove(index);
		recalc.getDetails().getIntervals().add(index, prop.getInterval());
		reset();

	}

	@SuppressWarnings("unchecked")
	private void onMove(boolean up) {
		int index = viewer1.getTable().getSelectionIndex();
		if (index == -1) {
			return;
		}
		if (up && index <= 0) {
			return;
		}
		if (!up && index >= recalc.getDetails().getIntervals().size() - 1) {
			return;
		}
		int newIndex = up ? index - 1 : index + 1;
		RecalcInterval interval = (RecalcInterval) recalc.getDetails()
				.getIntervals().remove(index);
		recalc.getDetails().getIntervals().add(newIndex, interval);
		viewer1.getTable().deselectAll();
		viewer1.getTable().select(newIndex);
		reset();
		viewer1.getTable().showSelection();
	}

	private void reset() {
		List intervals = recalc.getDetails().getIntervals();
		displayIntervals(intervals);
		validateView();
	}

	private void displayIntervals(List intervals) {
		viewer1.setItemCount(intervals == null ? 0 : intervals.size());
		viewer1.setContentProvider(new CommonItemContentProvider(intervals));
	}

	private void displayResults(List results) {
		viewer2.setItemCount(results == null ? 0 : results.size());
		viewer2.setContentProvider(new CommonItemContentProvider(results));
	}

	public RecalcInterval getSelectedInterval() {
		int index = viewer1.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		return (RecalcInterval) recalc.getDetails().getIntervals().get(index);
	}

	@SuppressWarnings("unchecked")
	public List getSelectedIntervals() {
		int indices[] = viewer1.getTable().getSelectionIndices();
		if (indices == null || indices.length == 0) {
			return null;
		}
		List results = new ArrayList();
		for (int i = 0; i < indices.length; i++) {
			int index = indices[i];
			results.add(recalc.getDetails().getIntervals().get(index));
		}
		return results;
	}

	private void validateView() {
		boolean intervalSelected = getSelectedInterval() != null;
		tiNew.setEnabled(true);
		tiDelete.setEnabled(intervalSelected);
		tiProperties.setEnabled(intervalSelected);
		tiUp.setEnabled(intervalSelected);
		tiDown.setEnabled(intervalSelected);
	}

	private AvearageKwhSeed getSelectedSeed() {
		int index = viewer2.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer2
				.getContentProvider();
		return (AvearageKwhSeed) provider.items.get(index);
	}

	private NumberFormat nf1 = new DecimalFormat("#,###.00");

	private NumberFormat nf2 = new DecimalFormat("#,###");

	private NumberFormat nf3 = new DecimalFormat("#,###.00###");

	private class IntervalContentProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			RecalcInterval interval = (RecalcInterval) element;
			if (interval == null) {
				return "";
			}
			RecalcItem item1 = (RecalcItem) interval.getItems().get(0);
			RecalcItem item2 = (RecalcItem) interval.getItems().get(1);
			switch (columnIndex) {
			case 0:
				return Date.format(item1.getItemDate());
			case 1:
				return nf1.format(item1.getReading());
			case 2:
				return Date.format(item2.getItemDate());
			case 3:
				return nf1.format(item2.getReading());
			case 4:
				return nf2.format(item1.getMeterCoeff());
			case 5:
				return nf2.format(item1.getMeter().getDigits());
			case 6:
				return nf1.format(item1.getMeterAcceleration());
			default:
				return "";
			}

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
				return nf1.format(seed.getKwh());
			case 5:
				return nf3.format(seed.getAveargeKwh());
			default:
				return "";
			}
		}

	}

}
