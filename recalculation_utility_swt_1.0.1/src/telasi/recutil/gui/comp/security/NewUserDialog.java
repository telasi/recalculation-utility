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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.Role;
import telasi.recutil.beans.User;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.security.UserInsertRequest;

public class NewUserDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Composite composite2;
	private Button btnOk;
	private Button btnClose;
	private Composite composite3;
	private Label lblErrorMessage;
	private UserGeneralPane userGeneralPane;
	private User user;

	public NewUserDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL | SWT.RESIZE);

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
				composite1LData.verticalAlignment = GridData.BEGINNING;
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData userGeneralPaneLData = new GridData();
					userGeneralPaneLData.verticalAlignment = GridData.FILL;
					userGeneralPaneLData.grabExcessVerticalSpace = true;
					userGeneralPaneLData.horizontalAlignment = GridData.FILL;
					userGeneralPaneLData.grabExcessHorizontalSpace = true;
					userGeneralPane = new UserGeneralPane(composite1, SWT.NONE);
					userGeneralPane.setLayoutData(userGeneralPaneLData);
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				GridData composite2LData = new GridData();
				composite2LData.verticalAlignment = GridData.FILL;
				composite2LData.grabExcessVerticalSpace = true;
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblErrorMessageLData = new GridData();
					lblErrorMessageLData.verticalAlignment = GridData.FILL;
					lblErrorMessageLData.grabExcessVerticalSpace = true;
					lblErrorMessageLData.horizontalAlignment = GridData.FILL;
					lblErrorMessageLData.grabExcessHorizontalSpace = true;
					lblErrorMessage = new Label(composite2, SWT.WRAP);
					lblErrorMessage.setLayoutData(lblErrorMessageLData);
					lblErrorMessage.setForeground(lblErrorMessage.getDisplay()
							.getSystemColor(SWT.COLOR_RED));
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				composite3Layout.marginWidth = 10;
				composite3Layout.marginHeight = 10;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
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
			dialogShell.setMinimumSize(550, 0);
			dialogShell.pack();
			//dialogShell.setSize(500, 700);
			GUIUtils.centerShell(dialogShell);
			userGeneralPane.setUser(user);
			userGeneralPane.setForNewUser(true);
			dialogShell.setText(GUIMessages
					.getMessage("comp.newuserdialog.title"));
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private void onOk() {

		// create new user
		if (user == null) {
			user = new User(-1);
		}
		String userName = userGeneralPane.getUserName();
		String password1 = userGeneralPane.getPassword1();
		String password2 = userGeneralPane.getPassword2();
		String fullName = userGeneralPane.getFullName();
		boolean enabled = userGeneralPane.isUserEnabled();
		String number = userGeneralPane.getNumber();
		Role role = userGeneralPane.getRole();
		User advisor = userGeneralPane.getAdvisor();
		lblErrorMessage.setText("");
		if (userName == null || userName.trim().length() == 0) {
			lblErrorMessage.setText(GUIMessages
					.getMessage("comp.newuserdialog.empty_username"));
			return;
		}
		if (password1 == null || password1.trim().length() == 0) {
			lblErrorMessage.setText(GUIMessages
					.getMessage("comp.newuserdialog.empty_password"));
			return;
		}
		if (!password1.equals(password2)) {
			lblErrorMessage.setText(GUIMessages
					.getMessage("comp.newuserdialog.password_not_match"));
			return;
		}
		if (fullName == null || fullName.trim().length() == 0) {
			lblErrorMessage.setText(GUIMessages
					.getMessage("comp.newuserdialog.empty_fullname"));
			return;
		}
		if (role == null) {
			lblErrorMessage.setText(GUIMessages.getMessage("comp.newuserdialog.empty_role"));
			return;
		}
		if (number == null || number.trim().length() == 0) {
			lblErrorMessage.setText(GUIMessages.getMessage("comp.newuserdialog.empty_number"));
			return;
		}
		user.setUserName(userName);
		user.setPassword(password1);
		user.setFullName(fullName);
		user.setEnabled(enabled);
		user.setRole(role);
		user.setAdvisor(advisor);
		user.setNumber(number);

		// check authentication status
		if (!Application.validateConnection()) {
			lblErrorMessage.setText(GUIMessages
					.getMessage("comp.general.no_connection"));
			return;
		}

		// sending request
		try {
			UserInsertRequest request = new UserInsertRequest(Application.USER_NAME, Application.PASSWORD);
			request.setUser(user);
			DefaultEJBResponse response = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			UserInsertRequest callback = (UserInsertRequest) response.getRequest();
			user.setId(callback.getId());
			dialogShell.dispose();
		} catch (Throwable t) {
			lblErrorMessage.setText(t.toString());
		}
	}

	private void onClose() {
		user = new User(-1);
		dialogShell.dispose();
	}

}
