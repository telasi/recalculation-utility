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
package telasi.recutil.gui.comp.security;

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

import telasi.recutil.beans.Role;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.security.RoleInsertRequest;

public class NewRoleDialog extends Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Composite composite2;
	private Button btnOk;
	private Button btnClose;
	private Composite composite3;
	private Label lblErrorMessages;
	private RoleGeneralPane roleGeneralPane1;
	private long newRoleId = -1;

	public NewRoleDialog(Shell parent, int style) {
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
			dialogShell.layout();
			dialogShell.setText(GUIMessages.getMessage("comp.newroledialog.title"));
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData roleGeneralPane1LData = new GridData();
					roleGeneralPane1LData.verticalAlignment = GridData.FILL;
					roleGeneralPane1LData.grabExcessVerticalSpace = true;
					roleGeneralPane1LData.horizontalAlignment = GridData.FILL;
					roleGeneralPane1LData.grabExcessHorizontalSpace = true;
					roleGeneralPane1 = new RoleGeneralPane(composite1, SWT.NONE);
					roleGeneralPane1.setLayoutData(roleGeneralPane1LData);
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				composite2Layout.marginHeight = 10;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.verticalAlignment = GridData.FILL;
				composite2LData.grabExcessVerticalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					lblErrorMessages = new Label(composite2, SWT.WRAP);
					GridData lblErrorMessagesLData = new GridData();
					lblErrorMessagesLData.horizontalAlignment = GridData.FILL;
					lblErrorMessagesLData.grabExcessHorizontalSpace = true;
					lblErrorMessagesLData.verticalAlignment = GridData.FILL;
					lblErrorMessagesLData.grabExcessVerticalSpace = true;
					lblErrorMessages.setLayoutData(lblErrorMessagesLData);
					lblErrorMessages.setForeground(lblErrorMessages
							.getDisplay().getSystemColor(SWT.COLOR_RED));
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.marginHeight = 10;
				composite3Layout.marginWidth = 10;
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					btnOk = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.widthHint = 75;
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
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
					btnClose = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText(GUIMessages.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onClose();
						}
					});
				}
			}

			dialogShell.pack();
			//dialogShell.setSize(500, 550);
			GUIUtils.centerShell(dialogShell);
			roleGeneralPane1.displayRole(null);

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

	private boolean validate() {
		String name = roleGeneralPane1.getRoleName();
		if (name == null || name.trim().length() == 0) {
			lblErrorMessages.setText(GUIMessages
					.getMessage("comp.general.empty_name_field"));
			return false;
		}
		return true;
	}

	private void onOk() {

		// clear previous messages
		lblErrorMessages.setText("");

		// validate fields
		if (!validate()) {
			return;
		}

		// validate connection
		if (!Application.validateConnection()) {
			lblErrorMessages.setText(GUIMessages
					.getMessage("comp.general.no_connection"));
			return;
		}

		// create new role
		Role newRole = new Role(-1L);
		newRole.setName(roleGeneralPane1.getRoleName());
		newRole.setDescription(roleGeneralPane1.getRoleDescription());
		newRole.setEnabled(roleGeneralPane1.isRoleEnabled());

		// process database task
		try {
			RoleInsertRequest request = new RoleInsertRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRole(newRole);
			DefaultEJBResponse response = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			RoleInsertRequest callbackRequest = (RoleInsertRequest) response
					.getRequest();
			newRoleId = callbackRequest.getId();
			dialogShell.dispose();
		} catch (Throwable t) {
			lblErrorMessages.setText(t.toString());
			return;
		}

	}

	private void onClose() {
		newRoleId = -1;
		dialogShell.dispose();
	}

	public long getNewRoleId() {
		return newRoleId;
	}

}
