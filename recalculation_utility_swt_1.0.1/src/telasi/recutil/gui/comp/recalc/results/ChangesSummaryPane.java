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
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.DiffDetail;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class ChangesSummaryPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private TableViewer viewer;
	private Composite composite3;
	private Label label1;
	private Label lblDescription;
	private Label lblImage;
	private List items;

	public ChangesSummaryPane(Composite parent, int style) {
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
			this.setSize(675, 203);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
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
							.getMessage("comp.changes_summary_pane.title"));
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
					lblImageLData.heightHint = 48;
					lblImageLData.widthHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin
							.getImage("icons/48x48/summary.png"));
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages
							.getMessage("comp.changes_summary_pane.descr"));
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
					viewer = new TableViewer(composite3, SWT.BORDER
							| SWT.FULL_SELECTION | SWT.MULTI | SWT.VIRTUAL);
					viewer.getControl().setLayoutData(viewerLData);
				}
				{
					// $protect>>$
					Table table = viewer.getTable();
					table.setHeaderVisible(true);
					table.setLinesVisible(true);

					TableColumn colImage = new TableColumn(table, SWT.NONE);
					colImage.setWidth(20);
					colImage.setResizable(false);

					TableColumn colOperation = new TableColumn(table, SWT.NONE);
					colOperation.setWidth(400);
					colOperation.setText(GUIMessages
							.getMessage("comp.general.operation"));

					TableColumn colGel = new TableColumn(table, SWT.RIGHT);
					colGel.setWidth(100);
					colGel.setText(GUIMessages.getMessage("comp.general.gel"));
					
					TableColumn colKwh = new TableColumn(table, SWT.RIGHT);
					colKwh.setWidth(100);
					colKwh.setText(GUIMessages.getMessage("comp.general.kwh"));

					

					// TableColumn colBalance = new TableColumn(table,
					// SWT.RIGHT);
					// colBalance.setWidth(75);
					// colBalance.setText(GUIMessages.getMessage("comp.general.balance2"));

					new TableColumn(table, SWT.RIGHT).setWidth(10);
					// $protect<<$
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
		viewer.setLabelProvider(new SummaryLabelProvider());
	}

	public void display(List items) {
		this.items = items;
		viewer.setItemCount(items == null ? 0 : items.size());
		viewer.setContentProvider(new CommonItemContentProvider(items));
	}

	private class SummaryLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			if (element == null) {
				return null;
			}
			DiffDetail item = (DiffDetail) element;
			switch (columnIndex) {
			case 0:
				return item.getOperation() == null ? Plugin
						.getImage("icons/16x16/bop/voucher.png") : GUIUtils
						.getOperationImage(item.getOperation());
			default:
				return null;
			}

		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return "";
			}
			DiffDetail item = (DiffDetail) element;
			switch (columnIndex) {
			case 1:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation()
						.getName());
			case 2:
				return nf.format(item.getOriginalGel());
			case 3:
				return nf.format(item.getOriginalKwh());
				// case 4:
				// return nf.format(item.getOriginalBalance());
			default:
				return "";
			}
		}

	}

	public List getSummary() {
		return items;
	}

}
