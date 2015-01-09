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


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Operation;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class BillOperationGeneralPane extends Composite {
	private Operation operation;
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblSeparator2;
	private Text txtDatabaseId;
	private Label lblDatabaseId;
	private Composite composite6;
	private Label lblSeparator3;
	private Label lblAdditional;
	private Composite composite5;
	private Text txtEndDate;
	private Label lblEndDate;
	private Text txtStartDate;
	private Label lblStartDate;
	private Text txtName;
	private Label lblName;
	private Text txtType;
	private Label lblType;
	private Composite composite4;
	private Label lblMainSection;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;

	public BillOperationGeneralPane(Composite parent, int style) {
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
			this.setSize(360, 369);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				composite1Layout.marginTop = 10;
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
							.getMessage("comp.billoper_general_pane.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
				}
			}
			{
				GridData lblSeparator1LData = new GridData();
				lblSeparator1LData.horizontalAlignment = GridData.FILL;
				lblSeparator1LData.grabExcessHorizontalSpace = true;
				lblSeparator1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator1.setLayoutData(lblSeparator1LData);
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
					lblImage.setImage(Plugin.getImage("icons/48x48/bop.png"));
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
							.getMessage("comp.billoper_general_pane.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				composite3Layout.marginTop = 10;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblMainSection = new Label(composite3, SWT.NONE);
					GridData lblMainSectionLData = new GridData();
					lblMainSectionLData.horizontalAlignment = GridData.FILL;
					lblMainSectionLData.grabExcessHorizontalSpace = true;
					lblMainSection.setLayoutData(lblMainSectionLData);
					lblMainSection.setText(GUIMessages
							.getMessage("comp.genera.main_properties"));
					lblMainSection.setFont(GUIUtils
							.createSubtitleFont(lblMainSection.getFont()));
				}
			}
			{
				GridData lblSeparator2LData = new GridData();
				lblSeparator2LData.horizontalAlignment = GridData.FILL;
				lblSeparator2LData.grabExcessHorizontalSpace = true;
				lblSeparator2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator2.setLayoutData(lblSeparator2LData);
			}
			{
				composite4 = new Composite(this, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					lblType = new Label(composite4, SWT.NONE);
					lblType.setText(GUIMessages
							.getMessage("comp.general.operation_type"));
				}
				{
					GridData txtTypeLData = new GridData();
					txtTypeLData.horizontalAlignment = GridData.FILL;
					txtTypeLData.grabExcessHorizontalSpace = true;
					txtType = new Text(composite4, SWT.READ_ONLY | SWT.BORDER);
					txtType.setLayoutData(txtTypeLData);
				}
				{
					lblName = new Label(composite4, SWT.NONE);
					lblName
							.setText(GUIMessages
									.getMessage("comp.general.name"));
				}
				{
					GridData txtNameLData = new GridData();
					txtNameLData.horizontalAlignment = GridData.FILL;
					txtNameLData.grabExcessHorizontalSpace = true;
					txtName = new Text(composite4, SWT.READ_ONLY | SWT.BORDER);
					txtName.setLayoutData(txtNameLData);
				}
				{
					lblStartDate = new Label(composite4, SWT.NONE);
					lblStartDate.setText(GUIMessages
							.getMessage("comp.general.start_date"));
				}
				{
					GridData txtStartDateLData = new GridData();
					txtStartDateLData.horizontalAlignment = GridData.FILL;
					txtStartDateLData.grabExcessHorizontalSpace = true;
					txtStartDate = new Text(composite4, SWT.READ_ONLY
							| SWT.BORDER);
					txtStartDate.setLayoutData(txtStartDateLData);
				}
				{
					lblEndDate = new Label(composite4, SWT.NONE);
					lblEndDate.setText(GUIMessages
							.getMessage("comp.general.end_date"));
				}
				{
					GridData txtEndDateLData = new GridData();
					txtEndDateLData.horizontalAlignment = GridData.FILL;
					txtEndDateLData.grabExcessHorizontalSpace = true;
					txtEndDate = new Text(composite4, SWT.READ_ONLY
							| SWT.BORDER);
					txtEndDate.setLayoutData(txtEndDateLData);
				}
			}
			{
				composite5 = new Composite(this, SWT.NONE);
				GridLayout composite5Layout = new GridLayout();
				composite5Layout.makeColumnsEqualWidth = true;
				composite5Layout.marginTop = 10;
				GridData composite5LData = new GridData();
				composite5LData.horizontalAlignment = GridData.FILL;
				composite5LData.grabExcessHorizontalSpace = true;
				composite5.setLayoutData(composite5LData);
				composite5.setLayout(composite5Layout);
				{
					GridData lblAdditionalLData = new GridData();
					lblAdditionalLData.horizontalAlignment = GridData.FILL;
					lblAdditionalLData.grabExcessHorizontalSpace = true;
					lblAdditional = new Label(composite5, SWT.NONE);
					lblAdditional.setLayoutData(lblAdditionalLData);
					lblAdditional.setText(GUIMessages
							.getMessage("comp.genera.additional_properties"));
					lblAdditional.setFont(GUIUtils
							.createSubtitleFont(lblAdditional.getFont()));
				}
			}
			{
				GridData lblSeparator3LData = new GridData();
				lblSeparator3LData.horizontalAlignment = GridData.FILL;
				lblSeparator3LData.grabExcessHorizontalSpace = true;
				lblSeparator3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator3.setLayoutData(lblSeparator3LData);
			}
			{
				composite6 = new Composite(this, SWT.NONE);
				GridLayout composite6Layout = new GridLayout();
				composite6Layout.numColumns = 2;
				GridData composite6LData = new GridData();
				composite6LData.horizontalAlignment = GridData.FILL;
				composite6LData.grabExcessHorizontalSpace = true;
				composite6.setLayoutData(composite6LData);
				composite6.setLayout(composite6Layout);
				{
					lblDatabaseId = new Label(composite6, SWT.NONE);
					lblDatabaseId.setText(GUIMessages
							.getMessage("comp.general.db_id"));
				}
				{
					GridData txtDatabaseIdLData = new GridData();
					txtDatabaseIdLData.horizontalAlignment = GridData.FILL;
					txtDatabaseIdLData.grabExcessHorizontalSpace = true;
					txtDatabaseId = new Text(composite6, SWT.READ_ONLY
							| SWT.BORDER);
					txtDatabaseId.setLayoutData(txtDatabaseIdLData);
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
		if (operation == null) {
			txtType.setText("");
			txtName.setText("");
			txtStartDate.setText("");
			txtEndDate.setText("");
			txtDatabaseId.setText("");
		} else {
			txtType.setText(GUITranslator.GEO_ASCII_TO_KA(operation.getType()
					.getName()));
			txtName.setText(GUITranslator.GEO_ASCII_TO_KA(operation.getName()));
			txtStartDate.setText(Date.format(operation.getStartDate()));
			txtEndDate.setText(Date.format(operation.getEndDate()));
			txtDatabaseId.setText(String.valueOf(operation.getId()));
		}
	}

}
