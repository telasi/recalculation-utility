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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Customer;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class CustomerSimplePane extends Composite {

	private Label lblTitle;
	private Composite composite2;
	private Text txtAccNumb;
	private Button btnDetails;
	private Composite composite1;
	private Label lblDescription;
	private Composite composite4;
	private Text txtName;
	private Label lblImage;
	private Text txtAddress;
	private Label lblAddress;
	private Label lblName;
	private Label lblAccNumb;
	private Composite composite3;
	private Customer customer;

	public CustomerSimplePane(Composite parent, int style) {
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
			setLayout(thisLayout);
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				composite2Layout.marginTop = 10;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.widthHint = 32;
					lblImageLData.heightHint = 32;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin
							.getImage("icons/32x32/personal.png"));
				}
				{
					composite4 = new Composite(composite2, SWT.NONE);
					GridLayout composite4Layout = new GridLayout();
					composite4Layout.marginHeight = 0;
					composite4Layout.marginWidth = 0;
					GridData composite4LData = new GridData();
					composite4LData.horizontalAlignment = GridData.FILL;
					composite4LData.grabExcessHorizontalSpace = true;
					composite4LData.verticalAlignment = GridData.FILL;
					composite4LData.grabExcessVerticalSpace = true;
					composite4.setLayoutData(composite4LData);
					composite4.setLayout(composite4Layout);
					{
						lblTitle = new Label(composite4, SWT.NONE);
						GridData lblTitleLData = new GridData();
						lblTitleLData.horizontalAlignment = GridData.FILL;
						lblTitleLData.grabExcessHorizontalSpace = true;
						lblTitle.setLayoutData(lblTitleLData);
						lblTitle.setText(GUIMessages
								.getMessage("comp.customerpane.title"));
						lblTitle.setFont(GUIUtils.createSubtitleFont(lblTitle
								.getFont()));
					}
					{
						GridData lblDescriptionLData = new GridData();
						lblDescriptionLData.horizontalAlignment = GridData.FILL;
						lblDescriptionLData.grabExcessHorizontalSpace = true;
						lblDescription = new Label(composite4, SWT.NONE);
						lblDescription.setLayoutData(lblDescriptionLData);
						lblDescription.setText(GUIMessages
								.getMessage("comp.customerpane.descr"));
					}
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblAccNumb = new Label(composite3, SWT.NONE);
					lblAccNumb.setText(GUIMessages
							.getMessage("comp.general.accnumb"));
				}
				{
					GridData txtAccNumbLData = new GridData();
					txtAccNumbLData.horizontalAlignment = GridData.FILL;
					txtAccNumbLData.grabExcessHorizontalSpace = true;
					txtAccNumb = new Text(composite3, SWT.READ_ONLY
							| SWT.BORDER);
					txtAccNumb.setLayoutData(txtAccNumbLData);
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
			}
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
					btnDetails = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData btnDetailsLData = new GridData();
					btnDetailsLData.grabExcessHorizontalSpace = true;
					btnDetailsLData.horizontalAlignment = GridData.END;
					btnDetails.setLayoutData(btnDetailsLData);
					btnDetails.setText(GUIMessages
							.getMessage("comp.general.details"));
					btnDetails.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							onDetails();
						}
					});
				}
			}
			this.layout();
			reset();
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
			txtAccNumb.setText("");
			txtName.setText("");
			txtAddress.setText("");
			btnDetails.setEnabled(false);
		} else {
			txtAccNumb.setText(GUITranslator.GEO_ASCII_TO_KA(customer
					.getNumber()));
			txtName.setText(GUITranslator.GEO_ASCII_TO_KA(customer.getName()));
			txtAddress.setText(GUITranslator.GEO_ASCII_TO_KA(customer
					.getAddress() != null ? customer.getAddress().toString()
					: ""));
			btnDetails.setEnabled(true);
		}
	}

	private void onDetails() {
		CustomerProperties prop = new CustomerProperties(getShell(), SWT.NONE);
		prop.setCustomer(customer);
		prop.open();
	}

}
