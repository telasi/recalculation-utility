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
package telasi.recutil.gui.comp.recalc.item;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.RecalcItem;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class RecalcItemGeneralPane extends Composite {

	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblGeneral;
	private Label lblId;
	private Text txtMeterStatus;
	private Label lblMeterStatus;
	private Text txtMeter;
	private Label lblMeter;
	private Composite composite6;
	private Text txtAccount;
	private Text txtCoeff;
	private Label lblCoeff;
	private Label label3;
	private Label lblMeterTitle;
	private Composite composite5;
	private Text txtStatus;
	private Label lblStatus;
	private Text txtDocument;
	private Label lblDocument;
	private Text txtCustomer;
	private Label lblCustomer;
	private Label lblAccount;
	private Text txtItemId;
	private Label lblItemId;
	private Text txtId;
	private Composite composite4;
	private Label label2;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label label1;
	private RecalcItem item;

	public RecalcItemGeneralPane(Composite parent, int style) {
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
			// this.setSize(411, 512);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
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
							.getMessage("comp.recalc_item_general_pane.title"));
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
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/ktip.png"));
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription
							.setText(GUIMessages
									.getMessage("comp.recalc_item_general_pane.description"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblGeneral = new Label(composite3, SWT.NONE);
					lblGeneral
							.setText(GUIMessages
									.getMessage("comp.recalc_item_general_pane.general_section_title"));
					lblGeneral.setFont(GUIUtils.createSubtitleFont(lblGeneral
							.getFont()));
				}
			}
			{
				GridData label2LData = new GridData();
				label2LData.horizontalAlignment = GridData.FILL;
				label2LData.grabExcessHorizontalSpace = true;
				label2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				label2.setLayoutData(label2LData);
			}
			{
				composite4 = new Composite(this, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				composite4Layout.marginLeft = 10;
				composite4Layout.marginRight = 10;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					lblId = new Label(composite4, SWT.NONE);
					lblId.setText(GUIMessages.getMessage("comp.general.db_id"));
				}
				{
					GridData txtIdLData = new GridData();
					txtIdLData.horizontalAlignment = GridData.FILL;
					txtIdLData.grabExcessHorizontalSpace = true;
					txtId = new Text(composite4, SWT.READ_ONLY | SWT.BORDER);
					txtId.setLayoutData(txtIdLData);
				}
				{
					lblItemId = new Label(composite4, SWT.NONE);
					lblItemId.setText("Item Id");
				}
				{
					GridData txtItemIdLData = new GridData();
					txtItemIdLData.horizontalAlignment = GridData.FILL;
					txtItemIdLData.grabExcessHorizontalSpace = true;
					txtItemId = new Text(composite4, SWT.READ_ONLY | SWT.BORDER);
					txtItemId.setLayoutData(txtItemIdLData);
				}
				{
					lblCustomer = new Label(composite4, SWT.NONE);
					lblCustomer.setText(GUIMessages
							.getMessage("comp.general.customer"));
				}
				{
					GridData txtCustomerLData = new GridData();
					txtCustomerLData.horizontalAlignment = GridData.FILL;
					txtCustomerLData.grabExcessHorizontalSpace = true;
					txtCustomer = new Text(composite4, SWT.READ_ONLY
							| SWT.BORDER);
					txtCustomer.setLayoutData(txtCustomerLData);
				}
				{
					lblAccount = new Label(composite4, SWT.NONE);
					lblAccount.setText(GUIMessages
							.getMessage("comp.general.account"));
				}
				{
					GridData txtAccountLData = new GridData();
					txtAccountLData.horizontalAlignment = GridData.FILL;
					txtAccountLData.grabExcessHorizontalSpace = true;
					txtAccount = new Text(composite4, SWT.READ_ONLY
							| SWT.BORDER);
					txtAccount.setLayoutData(txtAccountLData);
				}
				{
					lblDocument = new Label(composite4, SWT.NONE);
					lblDocument.setText(GUIMessages
							.getMessage("comp.general.document"));
				}
				{
					GridData txtDocumentLData = new GridData();
					txtDocumentLData.horizontalAlignment = GridData.FILL;
					txtDocumentLData.grabExcessHorizontalSpace = true;
					txtDocument = new Text(composite4, SWT.READ_ONLY
							| SWT.BORDER);
					txtDocument.setLayoutData(txtDocumentLData);
				}
				{
					lblStatus = new Label(composite4, SWT.NONE);
					lblStatus.setText(GUIMessages
							.getMessage("comp.general.status"));
				}
				{
					GridData txtStatusLData = new GridData();
					txtStatusLData.horizontalAlignment = GridData.FILL;
					txtStatusLData.grabExcessHorizontalSpace = true;
					txtStatus = new Text(composite4, SWT.READ_ONLY | SWT.BORDER);
					txtStatus.setLayoutData(txtStatusLData);
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
					lblMeterTitle = new Label(composite5, SWT.NONE);
					lblMeterTitle
							.setText(GUIMessages
									.getMessage("comp.recalc_item_general_pane.meter_section_title"));
					lblMeterTitle.setFont(GUIUtils
							.createSubtitleFont(lblMeterTitle.getFont()));
				}
			}
			{
				GridData label3LData = new GridData();
				label3LData.horizontalAlignment = GridData.FILL;
				label3LData.grabExcessHorizontalSpace = true;
				label3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				label3.setLayoutData(label3LData);
			}
			{
				composite6 = new Composite(this, SWT.NONE);
				GridLayout composite6Layout = new GridLayout();
				composite6Layout.numColumns = 2;
				composite6Layout.marginRight = 10;
				composite6Layout.marginLeft = 10;
				GridData composite6LData = new GridData();
				composite6LData.horizontalAlignment = GridData.FILL;
				composite6LData.grabExcessHorizontalSpace = true;
				composite6.setLayoutData(composite6LData);
				composite6.setLayout(composite6Layout);
				{
					lblMeter = new Label(composite6, SWT.NONE);
					lblMeter.setText(GUIMessages
							.getMessage("comp.general.meter"));
				}
				{
					GridData txtMeterLData = new GridData();
					txtMeterLData.horizontalAlignment = GridData.FILL;
					txtMeterLData.grabExcessHorizontalSpace = true;
					txtMeter = new Text(composite6, SWT.READ_ONLY | SWT.BORDER);
					txtMeter.setLayoutData(txtMeterLData);
				}
				{
					lblMeterStatus = new Label(composite6, SWT.NONE);
					lblMeterStatus.setText(GUIMessages
							.getMessage("comp.general.status"));
				}
				{
					GridData txtMeterStatusLData = new GridData();
					txtMeterStatusLData.horizontalAlignment = GridData.FILL;
					txtMeterStatusLData.grabExcessHorizontalSpace = true;
					txtMeterStatus = new Text(composite6, SWT.READ_ONLY
							| SWT.BORDER);
					txtMeterStatus.setLayoutData(txtMeterStatusLData);
				}
				{
					lblCoeff = new Label(composite6, SWT.NONE);
					lblCoeff.setText(GUIMessages
							.getMessage("comp.general.coeff"));
				}
				{
					GridData txtCoeffLData = new GridData();
					txtCoeffLData.horizontalAlignment = GridData.FILL;
					txtCoeffLData.grabExcessHorizontalSpace = true;
					txtCoeff = new Text(composite6, SWT.READ_ONLY | SWT.BORDER);
					txtCoeff.setLayoutData(txtCoeffLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RecalcItem getItem() {
		return item;
	}

	public void setItem(RecalcItem item) {
		this.item = item;
		reset();
	}

	private void reset() {
		if (item == null) {
			txtId.setText("");
			txtItemId.setText("");
			txtAccount.setText("");
			txtCustomer.setText("");
			txtDocument.setText("");
			txtStatus.setText("");
			txtMeter.setText("");
			txtMeterStatus.setText("");
			txtCoeff.setText("");
		} else {
			txtId.setText(String.valueOf(item.getId()));
			txtItemId.setText(String.valueOf(item.getItemId()));
			txtAccount.setText(item.getAccount().getNumber());
			txtCustomer.setText(GUITranslator.GEO_ASCII_TO_KA(item
					.getCustomer().getName()));
			txtDocument.setText(item.getItemNumber() == null ? ""
					: GUITranslator.GEO_ASCII_TO_KA(item.getItemNumber()));
			txtStatus.setText(GUIUtils.getItemStatusName(item.getStatus()));
			txtMeter.setText(item.getMeter().toString());
			txtMeterStatus.setText(String.valueOf(item.getMeterStatus()));
			NumberFormat nf = new DecimalFormat("#,###.00");
			txtCoeff.setText(nf.format(item.getMeterCoeff()));
		}
	}

}
