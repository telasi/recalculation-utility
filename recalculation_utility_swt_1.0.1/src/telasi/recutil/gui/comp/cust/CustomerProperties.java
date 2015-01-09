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
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import telasi.recutil.beans.Customer;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class CustomerProperties extends Dialog {

	private Shell dialogShell;
	private TabItem tabCustomer;
	private TabItem tabAccounts;
	private AccountPane accountPane1;
	private CustomerAddressPane customerAddressPane1;
	private TabItem tabAddress;
	private Button btnClose;
	private Composite composite2;
	private Composite composite1;
	private CustomerPane customerPane1;
	private TabFolder tabFolder1;
	private Customer customer;

	public CustomerProperties(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL | SWT.RESIZE);

			dialogShell.setLayout(new GridLayout());
			dialogShell.layout();
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					tabFolder1 = new TabFolder(composite1, SWT.NONE);
					{
						tabCustomer = new TabItem(tabFolder1, SWT.NONE);
						tabCustomer.setText(GUIMessages
								.getMessage("comp.general.customer"));
						{
							// $hide>>$
							customerPane1 = new CustomerPane(tabFolder1,
									SWT.NONE);
							tabCustomer.setControl(customerPane1);
							// $hide<<$
						}
					}
					{
						tabAddress = new TabItem(tabFolder1, SWT.NONE);
						tabAddress.setText(GUIMessages
								.getMessage("comp.general.address"));
						{
							// $hide>>$
							customerAddressPane1 = new CustomerAddressPane(
									tabFolder1, SWT.NONE);
							tabAddress.setControl(customerAddressPane1);
							// $hide<<$
						}
					}
					{
						tabAccounts = new TabItem(tabFolder1, SWT.NONE);
						tabAccounts.setText(GUIMessages
								.getMessage("comp.general.accounts"));
						{
							// $hide>>$
							accountPane1 = new AccountPane(tabFolder1, SWT.NONE);
							tabAccounts.setControl(accountPane1);
							// $hide<<$
						}
					}
					GridData tabFolder1LData = new GridData();
					tabFolder1LData.horizontalAlignment = GridData.FILL;
					tabFolder1LData.grabExcessHorizontalSpace = true;
					tabFolder1LData.verticalAlignment = GridData.FILL;
					tabFolder1LData.grabExcessVerticalSpace = true;
					tabFolder1.setLayoutData(tabFolder1LData);
					tabFolder1.setSelection(0);
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					btnClose = new Button(composite2, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.grabExcessHorizontalSpace = true;
					btnCloseLData.horizontalAlignment = GridData.END;
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText(GUIMessages
							.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							dialogShell.dispose();
						}
					});
				}
			}
			// $protect>>$
			//dialogShell.setSize(500, 450);
			dialogShell.pack();
			dialogShell.setText(GUIMessages.getMessage("comp.cust_pane_new.title"));
			GUIUtils.centerShell(dialogShell);
			reset();
			// $protect<<$
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reset() {
		customerPane1.setCustomer(customer);
		accountPane1.setCustomer(customer);
		customerAddressPane1.setCustomer(customer);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
