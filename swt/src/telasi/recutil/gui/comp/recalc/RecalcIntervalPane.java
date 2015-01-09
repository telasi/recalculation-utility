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
package telasi.recutil.gui.comp.recalc;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.service.eclipse.recalc.RecalcIntervalSelectRequest;

public class RecalcIntervalPane extends org.eclipse.swt.widgets.Composite {

	private TableViewer viewer;
	// private TableColumn colStartBalance;
	private TableColumn colEnd;
	private TableColumn colStart;
	private TableColumn colName;
	private Recalc recalc;
	private List intervals;

	public RecalcIntervalPane(Composite parent, int style) {
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
			this.setSize(434, 214);
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewerLData.grabExcessVerticalSpace = true;
				viewer = new TableViewer(this, SWT.BORDER | SWT.SINGLE
						| SWT.FULL_SELECTION | SWT.VIRTUAL);
				viewer.getControl().setLayoutData(viewerLData);
				viewer.getTable().setHeaderVisible(true);
				viewer.getTable().setLinesVisible(true);
				viewer
						.addSelectionChangedListener(new ISelectionChangedListener() {
							public void selectionChanged(
									SelectionChangedEvent event) {
								notifyRelated();
							}
						});
				{
					TableColumn colImage = new TableColumn(viewer.getTable(),
							SWT.NONE);
					colImage.setWidth(20);
					colImage.setResizable(false);
				}
				{
					colName = new TableColumn(viewer.getTable(), SWT.NONE);
					colName
							.setText(GUIMessages
									.getMessage("comp.general.name"));
					colName.setWidth(100);
				}
				{
					colStart = new TableColumn(viewer.getTable(), SWT.NONE);
					colStart.setText(GUIMessages
							.getMessage("comp.general.start_date"));
					colStart.setWidth(100);
				}
				{
					colEnd = new TableColumn(viewer.getTable(), SWT.NONE);
					colEnd.setText(GUIMessages
							.getMessage("comp.general.end_date"));
					colEnd.setWidth(100);
				}
				// {
				// colStartBalance = new TableColumn(viewer.getTable(),
				// SWT.NONE);
				// colStartBalance.setText(GUIMessages.getMessage("comp.general.start_balance"));
				// colStartBalance.setWidth(100);
				// }
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayIntervals(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new IntervalLabelProvider());
	}

	private void displayIntervals(List intervals) {
		viewer.setItemCount(intervals == null ? 0 : intervals.size());
		viewer.setContentProvider(new IntervalContentProvider(intervals));
	}

	@SuppressWarnings("unchecked")
	public void refresh() {
		if (recalc == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcIntervalSelectRequest request = new RecalcIntervalSelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRecalc(recalc);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			RecalcIntervalSelectRequest callback = (RecalcIntervalSelectRequest) resp
					.getRequest();
			intervals = callback.getIntervals();
			if (intervals != null) {
				for (int i = 0; i < intervals.size(); i++) {
					RecalcInterval interval = (RecalcInterval) intervals
							.get(i);
					List items = interval.getItems();
					if (items != null) {
						for (int j = 0; j < items.size(); j++) {
							RecalcItem item = (RecalcItem) items.get(j);
							item.setOperation(Cache.findOperationById(item
									.getOperation().getId()));
							item.setMeter(Cache.findMeterById(item.getMeter()
									.getId()));
						}
					}
				}
			}
			recalc.getDetails().getIntervals().clear();
			if (intervals != null) {
				recalc.getDetails().getIntervals().addAll(intervals);
			}
			displayIntervals(intervals);
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
		viewer.getTable().deselectAll();
		notifyRelated();
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void clear() {
		recalc = null;
		displayIntervals(null);
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public RecalcInterval getSelectedInterval() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}

		IntervalContentProvider provider = (IntervalContentProvider) viewer
				.getContentProvider();
		return (RecalcInterval) provider.intervals.get(index);
	}

	public void selectIntervalById(long id) {
		IntervalContentProvider provider = (IntervalContentProvider) viewer
				.getContentProvider();
		for (int i = 0; i < provider.intervals.size(); i++) {
			RecalcInterval interval = (RecalcInterval) provider.intervals
					.get(i);
			if (interval.getId() == id) {
				viewer.getTable().setSelection(i);
				break;
			}
		}
	}

	//
	// data
	//

	private class IntervalContentProvider implements IStructuredContentProvider {

		public List intervals;

		public IntervalContentProvider(List intervals) {
			this.intervals = intervals;
		}

		public Object[] getElements(Object inputElement) {
			return intervals == null ? new Object[] {} : intervals.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	// private NumberFormat nf = new DecimalFormat("#,###.00");

	private class IntervalLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			RecalcInterval interval = (RecalcInterval) element;
			if (interval == null) {
				return null;
			}
			switch (columnIndex) {
			case 0:
				return interval.isEditable() ? Plugin
						.getImage("icons/16x16/bop/voucher.png") : null;
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			RecalcInterval interval = (RecalcInterval) element;
			if (interval == null) {
				return "";
			}
			switch (columnIndex) {
			case 1:
				return interval.getName();
			case 2:
				return Date.format(interval.getStartDate());
			case 3:
				return Date.format(interval.getEndDate());
				// case 3:
				// return nf.format(interval.getStartBalance());
			default:
				return "";
			}
		}

	}

	//
	// relation
	//

	private RecalcChargePane relatedRecalcPane;

	public void setRelatedRecalcChargePane(RecalcChargePane pane) {
		this.relatedRecalcPane = pane;
	}

	private void notifyRelated() {
		if (relatedRecalcPane != null) {
			relatedRecalcPane.validateView();
		}
	}

}
