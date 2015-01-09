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
package telasi.recutil.gui.comp.cust.histories;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import telasi.recutil.beans.Customer;
import telasi.recutil.gui.comp.cust.CustomerChargeHistoryPane;

public class CustomerChargeHistoryPaneForHistory extends Composite implements
		ICustomerHistory {

	private CustomerChargeHistoryPane customerChargeHistoryPane1;

	public CustomerChargeHistoryPaneForHistory(Composite parent, int style) {
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
			this.setSize(377, 249);
			{
				GridData customerChargeHistoryPane1LData = new GridData();
				customerChargeHistoryPane1LData.horizontalAlignment = GridData.FILL;
				customerChargeHistoryPane1LData.grabExcessHorizontalSpace = true;
				customerChargeHistoryPane1LData.verticalAlignment = GridData.FILL;
				customerChargeHistoryPane1LData.grabExcessVerticalSpace = true;
				customerChargeHistoryPane1 = new CustomerChargeHistoryPane(
						this, SWT.NONE);
				customerChargeHistoryPane1
						.setLayoutData(customerChargeHistoryPane1LData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Customer getCustomer() {
		return customerChargeHistoryPane1.getCustomer();
	}

	public void refresh() {
		customerChargeHistoryPane1.refresh();
	}

	public void setCustomer(Customer customer) {
		customerChargeHistoryPane1.setCustomer(customer);
	}

}
