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
package telasi.recutil.gui.comp.bop;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Operation;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class BillOperationRecalcPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private TableViewer viewer;
	private Text txtDiffGroup;
	private Label label1;
	private Composite composite5;
	private Label lblUnits;
	private Composite composite4;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;
	private Operation operation;

	public BillOperationRecalcPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(323, 403);
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.horizontalSpacing = 0;
				composite3Layout.marginHeight = 0;
				composite3Layout.marginWidth = 0;
				composite3Layout.verticalSpacing = 0;
				GridData composite3LData = new GridData();
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					composite1 = new Composite(composite3, SWT.NONE);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.makeColumnsEqualWidth = true;
					composite1Layout.marginTop = 10;
					GridData composite1LData = new GridData();
					composite1LData.horizontalAlignment = GridData.FILL;
					composite1LData.grabExcessHorizontalSpace = true;
					composite1LData.verticalAlignment = GridData.FILL;
					composite1.setLayoutData(composite1LData);
					composite1.setLayout(composite1Layout);
					{
						lblTitle = new Label(composite1, SWT.NONE);
						GridData lblTitleLData = new GridData();
						lblTitleLData.horizontalAlignment = GridData.FILL;
						lblTitleLData.grabExcessHorizontalSpace = true;
						lblTitle.setLayoutData(lblTitleLData);
						lblTitle.setText(GUIMessages.getMessage("comp.billoper_recalc_pane.title"));
						lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
					}
				}
				{
					GridData lblSeparator1LData = new GridData();
					lblSeparator1LData.horizontalAlignment = GridData.FILL;
					lblSeparator1LData.grabExcessHorizontalSpace = true;
					lblSeparator1 = new Label(composite3, SWT.SEPARATOR
							| SWT.HORIZONTAL);
					lblSeparator1.setLayoutData(lblSeparator1LData);
				}
				{
					composite2 = new Composite(composite3, SWT.NONE);
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
						lblImage.setLayoutData(lblImageLData);
						lblImage.setImage(Plugin.getImage("icons/48x48/recalc.png"));
					}
					{
						GridData lblDescriptionLData = new GridData();
						lblDescriptionLData.horizontalAlignment = GridData.FILL;
						lblDescriptionLData.grabExcessHorizontalSpace = true;
						lblDescriptionLData.verticalAlignment = GridData.FILL;
						lblDescriptionLData.grabExcessVerticalSpace = true;
						lblDescription = new Label(composite2, SWT.WRAP);
						lblDescription.setLayoutData(lblDescriptionLData);
						lblDescription.setText(GUIMessages.getMessage("comp.billoper_recalc_pane.descr"));
					}
				}
				{
					composite4 = new Composite(composite3, SWT.NONE);
					GridLayout composite4Layout = new GridLayout();
					GridData composite4LData = new GridData();
					composite4LData.horizontalAlignment = GridData.FILL;
					composite4LData.grabExcessHorizontalSpace = true;
					composite4.setLayoutData(composite4LData);
					composite4.setLayout(composite4Layout);
					{
						composite5 = new Composite(composite4, SWT.NONE);
						GridLayout composite5Layout = new GridLayout();
						composite5Layout.numColumns = 2;
						composite5Layout.marginHeight = 0;
						composite5Layout.marginWidth = 0;
						composite5Layout.marginBottom = 10;
						GridData composite5LData = new GridData();
						composite5LData.horizontalAlignment = GridData.FILL;
						composite5LData.grabExcessHorizontalSpace = true;
						composite5.setLayoutData(composite5LData);
						composite5.setLayout(composite5Layout);
						{
							label1 = new Label(composite5, SWT.NONE);
							label1
									.setText(GUIMessages
											.getMessage("comp.billoper_recalc_pane.diff_group"));
						}
						{
							GridData txtDiffGroupLData = new GridData();
							txtDiffGroupLData.horizontalAlignment = GridData.FILL;
							txtDiffGroupLData.grabExcessHorizontalSpace = true;
							txtDiffGroup = new Text(composite5, SWT.READ_ONLY
									| SWT.BORDER);
							txtDiffGroup.setLayoutData(txtDiffGroupLData);
						}
					}
					{
						lblUnits = new Label(composite4, SWT.NONE);
						lblUnits
								.setText(GUIMessages
										.getMessage("comp.billoper_recalc_pane.default_values"));
					}
					{
						GridData viewerLData = new GridData();
						viewerLData.horizontalAlignment = GridData.FILL;
						viewerLData.grabExcessHorizontalSpace = true;
						viewerLData.heightHint = 50;
						viewer = new TableViewer(composite4, SWT.VIRTUAL
								| SWT.BORDER | SWT.SINGLE);
						viewer.getControl().setLayoutData(viewerLData);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
		reset();
	}

	private void reset() {
		viewer.getTable().clearAll();
		NumberFormat nf = new DecimalFormat("#,###.######");
		if (operation != null) {
			txtDiffGroup.setText(GUIUtils.getDiffGroupName(operation
					.getDiffCategory()));
		}
		if (!(operation == null || operation.getSubsidyAttachment() == null)) {
			int[] units = operation.getSubsidyAttachment().getUnits();
			for (int i = 0; i < units.length; i++) {
				int unit = units[i];
				String unitStr = GUIUtils.getUnitName(unit);
				String text = nf.format(operation.getSubsidyAttachment()
						.getAmounts()[i])
						+ " " + unitStr;
				viewer.add(text);
			}
		}
	}

}
