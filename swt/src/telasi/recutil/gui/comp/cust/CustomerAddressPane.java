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
package telasi.recutil.gui.comp.cust;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Address;
import telasi.recutil.beans.Customer;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class CustomerAddressPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Label label1;
	private Label lblHouse;
	private Label lblDescription;
	private Label lblBusinessCenter;
	private Text txtFlate;
	private Label lblFlate;
	private Text txtPourch;
	private Label lblPourch;
	private Text txtBuilding;
	private Label lblBuilding;
	private Text txtHouse;
	private Text txtStreet;
	private Label lblStreet;
	private Text txtPostIndex;
	private Label lblPostIndex;
	private Text txtBusinessCenter;
	private Composite composite3;
	private Label lblImage;
	private Composite composite2;
	private Customer customer;

	public CustomerAddressPane(Composite parent, int style) {
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
			this.setSize(328, 354);
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
					lblTitle.setText(GUIMessages
							.getMessage("comp.customer_address_pane.title"));
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
					lblImage.setImage(Plugin.getImage("icons/48x48/home.png"));
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
							.getMessage("comp.customer_address_pane.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblBusinessCenter = new Label(composite3, SWT.NONE);
					lblBusinessCenter.setText(GUIMessages
							.getMessage("comp.general.business_center"));
				}
				{
					GridData txtBusinessCenterLData = new GridData();
					txtBusinessCenterLData.horizontalAlignment = GridData.FILL;
					txtBusinessCenterLData.grabExcessHorizontalSpace = true;
					txtBusinessCenter = new Text(composite3, SWT.READ_ONLY
							| SWT.BORDER);
					txtBusinessCenter.setLayoutData(txtBusinessCenterLData);
				}
				{
					lblPostIndex = new Label(composite3, SWT.NONE);
					lblPostIndex.setText(GUIMessages
							.getMessage("comp.general.postindex"));
				}
				{
					GridData txtPostIndexLData = new GridData();
					txtPostIndexLData.horizontalAlignment = GridData.FILL;
					txtPostIndexLData.grabExcessHorizontalSpace = true;
					txtPostIndex = new Text(composite3, SWT.READ_ONLY
							| SWT.BORDER);
					txtPostIndex.setLayoutData(txtPostIndexLData);
				}
				{
					lblStreet = new Label(composite3, SWT.NONE);
					lblStreet.setText(GUIMessages
							.getMessage("comp.general.street"));
				}
				{
					GridData txtStreetLData = new GridData();
					txtStreetLData.horizontalAlignment = GridData.FILL;
					txtStreetLData.grabExcessHorizontalSpace = true;
					txtStreet = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtStreet.setLayoutData(txtStreetLData);
				}
				{
					lblHouse = new Label(composite3, SWT.NONE);
					lblHouse.setText(GUIMessages
							.getMessage("comp.general.house"));
				}
				{
					GridData txtHouseLData = new GridData();
					txtHouseLData.horizontalAlignment = GridData.FILL;
					txtHouseLData.grabExcessHorizontalSpace = true;
					txtHouse = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtHouse.setLayoutData(txtHouseLData);
				}
				{
					lblBuilding = new Label(composite3, SWT.NONE);
					lblBuilding.setText(GUIMessages
							.getMessage("comp.general.building"));
				}
				{
					GridData txtBuildingLData = new GridData();
					txtBuildingLData.horizontalAlignment = GridData.FILL;
					txtBuildingLData.grabExcessHorizontalSpace = true;
					txtBuilding = new Text(composite3, SWT.READ_ONLY
							| SWT.BORDER);
					txtBuilding.setLayoutData(txtBuildingLData);
				}
				{
					lblPourch = new Label(composite3, SWT.NONE);
					lblPourch.setText(GUIMessages
							.getMessage("comp.general.pourch"));
				}
				{
					GridData txtPourchLData = new GridData();
					txtPourchLData.horizontalAlignment = GridData.FILL;
					txtPourchLData.grabExcessHorizontalSpace = true;
					txtPourch = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtPourch.setLayoutData(txtPourchLData);
				}
				{
					lblFlate = new Label(composite3, SWT.NONE);
					lblFlate.setText(GUIMessages
							.getMessage("comp.general.flate"));
				}
				{
					GridData txtFlateLData = new GridData();
					txtFlateLData.horizontalAlignment = GridData.FILL;
					txtFlateLData.grabExcessHorizontalSpace = true;
					txtFlate = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtFlate.setLayoutData(txtFlateLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		reset();
	}

	private void reset() {
		if (customer == null || customer.getAddress() == null) {
			txtBusinessCenter.setText("");
			txtBuilding.setText("");
			txtFlate.setText("");
			txtHouse.setText("");
			txtPostIndex.setText("");
			txtPourch.setText("");
			txtStreet.setText("");
		} else {
			Address address = customer.getAddress();
			txtBusinessCenter.setText(address.getRegionName() == null ? ""
					: GUITranslator.GEO_ASCII_TO_KA(address.getRegionName()
							.trim()));
			txtBuilding.setText(address.getBuilding() == null ? ""
					: GUITranslator.GEO_ASCII_TO_KA(address.getBuilding()
							.trim()));
			txtFlate.setText(address.getFlate() == null ? "" : GUITranslator
					.GEO_ASCII_TO_KA(address.getFlate().trim()));
			txtHouse.setText(address.getHouse() == null ? "" : GUITranslator
					.GEO_ASCII_TO_KA(address.getHouse().trim()));
			txtPostIndex.setText(address.getPostIndex() == null ? ""
					: GUITranslator.GEO_ASCII_TO_KA(address.getPostIndex()
							.trim()));
			txtPourch.setText(address.getPorch() == null ? "" : GUITranslator
					.GEO_ASCII_TO_KA(address.getPorch().trim()));
			txtStreet.setText(address.getStreetName() == null ? ""
					: GUITranslator.GEO_ASCII_TO_KA(address.getStreetName()
							.trim()));
		}
	}

}
