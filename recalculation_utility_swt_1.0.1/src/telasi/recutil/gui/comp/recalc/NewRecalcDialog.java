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
package telasi.recutil.gui.comp.recalc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.Recalc2;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.recalc.RecalcInsertRequest;

public class NewRecalcDialog extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private RecalcGeneralPane recalculationGeneralPane1;
	private Composite composite2;
	private Button btnOk;
	private Label lblError;
	private Composite composite3;
	private Button btnClose;
	private Customer customer;
	private Account account;
	private Recalc2 recalc;
	private boolean approved = false;

	public NewRecalcDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL | SWT.RESIZE);
			dialogShell.setText(GUIMessages
					.getMessage("comp.recalc_new_dialog.title"));
			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.horizontalSpacing = 0;
			dialogShellLayout.marginHeight = 0;
			dialogShellLayout.marginWidth = 0;
			dialogShellLayout.verticalSpacing = 0;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.END;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData recalculationGeneralPane1LData = new GridData();
					recalculationGeneralPane1LData.horizontalAlignment = GridData.FILL;
					recalculationGeneralPane1LData.grabExcessHorizontalSpace = true;
					recalculationGeneralPane1 = new RecalcGeneralPane(
							composite1, SWT.NONE);
					recalculationGeneralPane1
							.setLayoutData(recalculationGeneralPane1LData);
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
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
					lblError = new Label(composite3, SWT.WRAP);
					GridData lblErrorLData = new GridData();
					lblErrorLData.horizontalAlignment = GridData.FILL;
					lblErrorLData.grabExcessHorizontalSpace = true;
					lblErrorLData.verticalAlignment = GridData.FILL;
					lblErrorLData.grabExcessVerticalSpace = true;
					lblError.setLayoutData(lblErrorLData);
					lblError.setForeground(lblError.getDisplay()
							.getSystemColor(SWT.COLOR_RED));
					lblError.setForeground(lblError.getDisplay()
							.getSystemColor(SWT.COLOR_RED));
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				composite2Layout.marginWidth = 10;
				composite2Layout.marginHeight = 10;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					btnOk = new Button(composite2, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.horizontalAlignment = GridData.END;
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.widthHint = 75;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
					btnOk.getShell().setDefaultButton(btnOk);
					btnOk.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onOk();
						}
					});
				}
				{
					btnClose = new Button(composite2, SWT.PUSH | SWT.CENTER);
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
			reset();
			dialogShell.setMinimumSize(500, 0);
			dialogShell.pack();
			//dialogShell.setSize(500, 600);
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private void reset() {
		recalc = new Recalc2();
		recalc.setId(-1L);
		recalc.setAccount(account);
		recalc.setCustomer(customer);
		recalculationGeneralPane1.setRecalc(recalc);
	}

	private boolean apply() {
		lblError.setText("");
		if (recalc == null) {
			lblError.setText("empty recalc");
			return false;
		}
		//String number = recalculationGeneralPane1.getNumber();
		Customer customer = recalculationGeneralPane1.getCustomer();
		Account account = recalculationGeneralPane1.getAccount();
		String description = recalculationGeneralPane1.getDescription();
		//if (number == null || number.trim().length() == 0) {
		//	lblError.setText(GUIMessages.getMessage("comp.recalc_new_dialog.error.empty_number"));
		//	return false;
		//}
		if (customer == null) {
			lblError.setText(GUIMessages.getMessage("comp.recalc_new_dialog.error.empty_customer"));
			return false;
		}
		if (account == null) {
			lblError.setText(GUIMessages.getMessage("comp.recalc_new_dialog.error.empty_account"));
			return false;
		}
		//recalc.setNumber(GUITranslator.KA_TO_GEO_ASCII(number.trim()));
		recalc.setCustomer(customer);
		recalc.setAccount(account);
		recalc.setStatus(Recalc2.STATUS_DEFAULT);
		recalc.setDescription(GUITranslator.KA_TO_GEO_ASCII(description));
		return true;
	}

	private void onOk() {
		if (!apply()) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcInsertRequest request = new RecalcInsertRequest(Application.USER_NAME, Application.PASSWORD);
			request.setRecalculation(recalc);
			request.setUser(Application.USER);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			RecalcInsertRequest callback = (RecalcInsertRequest) resp.getRequest();
			recalc.setId(callback.getId());
			recalc.setNumber(callback.getRecalculation().getNumber());
			approved = true;
			dialogShell.dispose();
		} catch (Throwable t) {
			approved = false;
			lblError.setText(t.toString());
		}

	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	public boolean isApproved() {
		return approved;
	}

	public Recalc getRecalc() {
		return recalc;
	}

}
