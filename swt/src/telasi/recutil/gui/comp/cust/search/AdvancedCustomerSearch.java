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
package telasi.recutil.gui.comp.cust.search;

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

import telasi.recutil.beans.Customer;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class AdvancedCustomerSearch extends Dialog {

	private Shell dialogShell;

	private AdvancedCustomerSearchPane advancedCustomerSearchPane1;

	private Button btnClose;

	private Button btnOk;

	private Composite composite1;

	public AdvancedCustomerSearch(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.horizontalSpacing = 0;
			dialogShellLayout.marginHeight = 0;
			dialogShellLayout.marginWidth = 0;
			dialogShellLayout.verticalSpacing = 0;
			dialogShell.setLayout(dialogShellLayout);
			{
				// $hide>>$
				GridData advancedCustomerSearchPane1LData = new GridData();
				advancedCustomerSearchPane1LData.horizontalAlignment = GridData.FILL;
				advancedCustomerSearchPane1LData.grabExcessHorizontalSpace = true;
				advancedCustomerSearchPane1LData.verticalAlignment = GridData.FILL;
				advancedCustomerSearchPane1LData.grabExcessVerticalSpace = true;
				advancedCustomerSearchPane1 = new AdvancedCustomerSearchPane(dialogShell, SWT.NONE);
				advancedCustomerSearchPane1.setLayoutData(advancedCustomerSearchPane1LData);
				// $hide<<$
			}
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					btnOk = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.widthHint = 75;
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
					btnOk.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onOk();
						}
					});

				}
				{
					btnClose = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText(GUIMessages
							.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onClose();
						}
					});
				}
			}

			// $protect>>$
			dialogShell.layout();
			//dialogShell.setSize(800, 700);
			dialogShell.pack();
			dialogShell.setText(GUIMessages.getMessage("comp.advanced_customer_search_dialog"));
			GUIUtils.centerShell(dialogShell);
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

	private boolean approved = false;

	public boolean isApproved() {
		return approved;
	}

	private Customer customer;

	private void onOk() {
		Customer customer = advancedCustomerSearchPane1.getCustomer();
		if (customer == null) {
			return;
		}
		this.customer = customer;
		approved = true;
		dialogShell.dispose();
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	public Customer getCustomer() {
		return customer;
	}

}
