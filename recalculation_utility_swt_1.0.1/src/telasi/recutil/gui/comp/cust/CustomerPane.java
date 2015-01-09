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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Customer;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class CustomerPane extends Composite {

	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblNumber;
	private Label lblId;
	private Text txtId;
	private Text txtCat;
	private Label lblCategory;
	private Text txtBalance;
	private Label lblBalace;
	private Text txtRooms;
	private Label lblRooms;
	private Text txtAddress;
	private Text txtName;
	private Label lblAddress;
	private Label lblName;
	private Text txtNumber;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label label1;
	private Customer customer;

	public CustomerPane(Composite parent, int style) {
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
			this.setSize(394, 340);
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
					lblTitle.setText(GUIMessages
							.getMessage("comp.cust_pane_new.title"));
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
					lblImage.setImage(Plugin.getImage("icons/48x48/cust.png"));
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
							.getMessage("comp.cust_pane_new.descr"));
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
					lblNumber = new Label(composite3, SWT.NONE);
					lblNumber.setText(GUIMessages
							.getMessage("comp.general.accnumb"));
				}
				{
					GridData txtAccNumberLData = new GridData();
					txtAccNumberLData.horizontalAlignment = GridData.FILL;
					txtAccNumberLData.grabExcessHorizontalSpace = true;
					txtNumber = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtNumber.setLayoutData(txtAccNumberLData);
				}
				{
					lblName = new Label(composite3, SWT.NONE);
					lblName
							.setText(GUIMessages
									.getMessage("comp.general.name"));
				}
				{
					GridData txtNameLData = new GridData();
					txtNameLData.horizontalAlignment = GridData.FILL;
					txtNameLData.grabExcessHorizontalSpace = true;
					txtName = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtName.setLayoutData(txtNameLData);
				}
				{
					lblAddress = new Label(composite3, SWT.NONE);
					lblAddress.setText(GUIMessages
							.getMessage("comp.general.address"));
				}
				{
					GridData txtAddressLData = new GridData();
					txtAddressLData.horizontalAlignment = GridData.FILL;
					txtAddressLData.grabExcessHorizontalSpace = true;
					txtAddress = new Text(composite3, SWT.READ_ONLY
							| SWT.BORDER);
					txtAddress.setLayoutData(txtAddressLData);
				}
				{
					lblRooms = new Label(composite3, SWT.NONE);
					lblRooms.setText(GUIMessages
							.getMessage("comp.general.room_count"));
				}
				{

					GridData txtRoomsLData = new GridData();
					txtRoomsLData.horizontalAlignment = GridData.FILL;
					txtRoomsLData.grabExcessHorizontalSpace = true;
					txtRooms = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtRooms.setLayoutData(txtRoomsLData);
				}
				{
					lblBalace = new Label(composite3, SWT.NONE);
					lblBalace.setText(GUIMessages
							.getMessage("comp.general.balance"));
				}
				{
					GridData txtBalanceLData = new GridData();
					txtBalanceLData.horizontalAlignment = GridData.FILL;
					txtBalanceLData.grabExcessHorizontalSpace = true;
					txtBalance = new Text(composite3, SWT.READ_ONLY
							| SWT.BORDER);
					txtBalance.setLayoutData(txtBalanceLData);
				}
				{
					lblCategory = new Label(composite3, SWT.NONE);
					lblCategory.setText(GUIMessages
							.getMessage("comp.general.category"));
				}
				{
					GridData txtCatLData = new GridData();
					txtCatLData.horizontalAlignment = GridData.FILL;
					txtCatLData.grabExcessHorizontalSpace = true;
					txtCat = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtCat.setLayoutData(txtCatLData);
				}
				{
					lblId = new Label(composite3, SWT.NONE);
					lblId.setText(GUIMessages.getMessage("comp.general.db_id"));
				}
				{
					GridData txtIdLData = new GridData();
					txtIdLData.horizontalAlignment = GridData.FILL;
					txtIdLData.grabExcessHorizontalSpace = true;
					txtId = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtId.setLayoutData(txtIdLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		reset();
	}

	private void reset() {
		if (customer == null) {
			txtNumber.setText("");
			txtName.setText("");
			txtAddress.setText("");
			txtRooms.setText("");
			txtBalance.setText("");
			txtId.setText("");
			txtCat.setText("");
		} else {

			String number = customer.getNumber() == null ? "" : customer
					.getNumber();
			String name = customer.getName() == null ? "" : customer.getName();
			String address = customer.getAddress() == null ? "" : customer
					.getAddress().toString();

			txtNumber.setText(GUITranslator.GEO_ASCII_TO_KA(number));
			txtName.setText(GUITranslator.GEO_ASCII_TO_KA(name));
			txtAddress.setText(GUITranslator.GEO_ASCII_TO_KA(address));
			txtRooms.setText(customer.getRoomCount() <= 0 ? "<undefined>"
					: String.valueOf(customer.getRoomCount()));
			txtBalance.setText(nf.format(customer.getCurrentBalance()));
			txtId.setText(String.valueOf(customer.getId()));
			txtCat
					.setText(customer.getCategory() == 0 ? "<undefined>"
							: customer.getCategory() == 1 ? GUIMessages
									.getMessage("comp.general.cust_cat_common")
									: GUIMessages
											.getMessage("comp.general.cust_cat_others"));
		}
	}

	private NumberFormat nf = new DecimalFormat("#,###.00");

}
