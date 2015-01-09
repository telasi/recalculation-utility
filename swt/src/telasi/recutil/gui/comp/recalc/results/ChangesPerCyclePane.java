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
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.DiffSummary;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class ChangesPerCyclePane extends Composite {

	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private TableTreeViewer viewer;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label label1;

	public ChangesPerCyclePane(Composite parent, int style) {
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
			this.setSize(760, 270);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
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
							.getMessage("comp.changes_per_cycle_pane.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
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
					lblImageLData.widthHint = 48;
					lblImageLData.heightHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setImage(Plugin
							.getImage("icons/48x48/compare.png"));
					lblImage.setLayoutData(lblImageLData);
				}
				{
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription = new Label(composite2, SWT.WRAP);
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages
							.getMessage("comp.changes_per_cycle_pane.descr"));
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
					viewer = new TableTreeViewer(composite3, SWT.BORDER
							| SWT.FULL_SELECTION | SWT.MULTI);
					viewer.getControl().setLayoutData(viewerLData);
				}
				// $protect>>$
				Table table = viewer.getTableTree().getTable();
				table.setHeaderVisible(true);
				table.setLinesVisible(true);

				TableColumn colExpand = new TableColumn(table, SWT.NONE);
				colExpand.setWidth(30);
				colExpand.setResizable(false);

				TableColumn colImage = new TableColumn(table, SWT.NONE);
				colImage.setWidth(25);
				colImage.setResizable(false);

				TableColumn colDate = new TableColumn(table, SWT.NONE);
				colDate.setWidth(100);
				colDate.setText(GUIMessages
						.getMessage("comp.changes_per_cycle_pane.date"));

				TableColumn colCategory = new TableColumn(table, SWT.NONE);
				colCategory.setWidth(300);
				colCategory.setText(GUIMessages
						.getMessage("comp.changes_per_cycle_pane.category"));

				TableColumn colGelInit = new TableColumn(table, SWT.RIGHT);
				colGelInit.setWidth(100);
				colGelInit.setText(GUIMessages
						.getMessage("comp.changes_per_cycle_pane.gel_init"));
				
				TableColumn colKwhInit = new TableColumn(table, SWT.RIGHT);
				colKwhInit.setWidth(100);
				colKwhInit.setText(GUIMessages
						.getMessage("comp.changes_per_cycle_pane.kwh_init"));

				// TableColumn colBalanceInit = new TableColumn(table,
				// SWT.RIGHT);
				// colBalanceInit.setWidth(75);
				// colBalanceInit.setText(GUIMessages.getMessage("comp.changes_per_cycle_pane.balance_init"));

				TableColumn colGelFin = new TableColumn(table, SWT.RIGHT);
				colGelFin.setWidth(100);
				colGelFin.setText(GUIMessages
						.getMessage("comp.changes_per_cycle_pane.gel_fin"));

				TableColumn colKwhFin = new TableColumn(table, SWT.RIGHT);
				colKwhFin.setWidth(100);
				colKwhFin.setText(GUIMessages
						.getMessage("comp.changes_per_cycle_pane.kwh_fin"));
				
				// TableColumn colBalanceFin = new TableColumn(table,
				// SWT.RIGHT);
				// colBalanceFin.setWidth(75);
				// colBalanceFin.setText(GUIMessages.getMessage("comp.changes_per_cycle_pane.balance_fin"));

				new TableColumn(table, SWT.RIGHT).setWidth(10);
				// $protect<<$
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		display(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new DiffByCycleLabelProvider());
	}

	public void display(List diffs) {
		viewer.setContentProvider(new DiffByCycleContentProvider(diffs));
	}

	private class DiffByCycleContentProvider implements ITreeContentProvider {

		private List diffs;

		public DiffByCycleContentProvider(List diffs) {
			this.diffs = diffs;
		}

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof DiffSummary) {
				List details = ((DiffSummary) parentElement).getDetails();
				return details == null ? new Object[] {} : details.toArray();
			}
			return new Object[] {};
		}

		public Object getParent(Object element) {
			if (element instanceof DiffDetail) {
				return ((DiffDetail) element).getSummary();
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		public Object[] getElements(Object inputElement) {
			return diffs == null ? new Object[] {} : diffs.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class DiffByCycleLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			if (element == null) {
				return null;
			}
			if (element instanceof DiffDetail) {
				DiffDetail detail = (DiffDetail) element;
				switch (columnIndex) {
				case 1:
					return detail.getOperation() == null ? null : GUIUtils
							.getOperationImage(detail.getOperation());
				default:
					return null;
				}
			} else {
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return "";
			}
			if (element instanceof DiffSummary) {
				DiffSummary summary = (DiffSummary) element;
				switch (columnIndex) {
				case 2:
					return Date.format(summary.getDate());
				default:
					return "";
				}
			}
			if (element instanceof DiffDetail) {
				DiffDetail detail = (DiffDetail) element;
				switch (columnIndex) {
				case 3:
					StringBuffer sb = new StringBuffer();
					sb.append(GUIUtils.getDiffGroupName(detail.getCategory()));
					if (detail.getOperation() != null) {
						sb.append(" - ");
						sb.append(GUITranslator.GEO_ASCII_TO_KA(detail
								.getOperation().getName()));
					}
					return sb.toString();
				case 4:
					return nf.format(detail.getOriginalGel());
				case 5:
					return nf.format(detail.getOriginalKwh());
					// case 6:
					// return nf.format(detail.getOriginalBalance());
				case 6:
					return nf.format(detail.getDerivedGel());
				case 7:
					return nf.format(detail.getDerivedKwh());
					// case 9:
					// return nf.format(detail.getDerivedBalance());
				default:
					return "";
				}
			}
			return "";
		}

	}

}
