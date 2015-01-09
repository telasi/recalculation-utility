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
import telasi.recutil.beans.User;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.security.UserUpdateRequest;

public class UserProperties extends Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Composite composite2;
	private Button btnOk;
	private Button btnClose;
	private Composite composite3;
	private Label lblErrorMessages;
	private UserGeneralPane userGeneralPane;
	private User user;
	private boolean approved = false;

	public UserProperties(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL | SWT.RESIZE);
			dialogShell.setText(GUIMessages
					.getMessage("comp.userproperties.title"));
			GridLayout dialogShellLayout = new GridLayout();
			dialogShell.setLayout(dialogShellLayout);
			dialogShellLayout.marginHeight = 0;
			dialogShellLayout.horizontalSpacing = 0;
			dialogShellLayout.marginWidth = 0;
			dialogShellLayout.verticalSpacing = 0;
			dialogShell.layout();
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData userGeneralPaneLData = new GridData();
					userGeneralPaneLData.horizontalAlignment = GridData.FILL;
					userGeneralPaneLData.grabExcessHorizontalSpace = true;
					userGeneralPaneLData.verticalAlignment = GridData.FILL;
					userGeneralPaneLData.grabExcessVerticalSpace = true;
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
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
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
					btnClose = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText("Close");
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
			userGeneralPane.setUser(getUser());
			userGeneralPane.setForNewUser(false);
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

	public boolean isApproved() {
		return approved;
	}

	private boolean apply() {
		lblErrorMessages.setText("");
		String userName = userGeneralPane.getUserName();
		String password1 = userGeneralPane.getPassword1();
		String password2 = userGeneralPane.getPassword2();
		String fullName = userGeneralPane.getFullName();
		String number = userGeneralPane.getNumber();
		Role role = userGeneralPane.getRole();
		if (userName == null || userName.trim().length() == 0) {
			lblErrorMessages.setText(GUIMessages.getMessage("comp.newuserdialog.empty_username"));
			return false;
		}
		if (password1 != null && password1.trim().length() > 0) {
			if (!password1.equals(password2)) {
				lblErrorMessages.setText(GUIMessages.getMessage("comp.newuserdialog.password_not_match"));
				return false;
			}
		}
		if (fullName == null || fullName.trim().length() == 0) {
			lblErrorMessages.setText(GUIMessages.getMessage("comp.newuserdialog.empty_fullname"));
			return false;
		}
		if (role == null) {
			lblErrorMessages.setText(GUIMessages.getMessage("comp.newuserdialog.empty_role"));
			return false;
		}
		if (number == null || number.trim().length() == 0) {
			lblErrorMessages.setText(GUIMessages.getMessage("comp.newuserdialog.empty_number"));
			return false;
		}
		return true;
	}

	private void onOk() {
		if (!apply()) {
			return;
		}

		// get fields
		String userName = userGeneralPane.getUserName();
		String password1 = userGeneralPane.getPassword1();
		String fullName = userGeneralPane.getFullName();
		String number = userGeneralPane.getNumber();
		Role role = userGeneralPane.getRole();
		boolean enabled = userGeneralPane.isUserEnabled();
		User advisor = userGeneralPane.getAdvisor();

		// make a copy
		User copy = new User(user.getId());
		copy.setEnabled(enabled);
		copy.setUserName(userName);
		copy.setPassword(password1);
		copy.setFullName(fullName);
		copy.setRole(role);
		copy.setAdvisor(advisor);
		copy.setNumber(number);

		// check authentification status
		if (!Application.validateConnection()) {
			lblErrorMessages.setText(GUIMessages.getMessage("comp.general.no_connection"));
			return;
		}

		// process update using copy
		UserUpdateRequest request = new UserUpdateRequest(Application.USER_NAME, Application.PASSWORD);
		request.setUser(copy);
		try {
			DefaultRecutilClient.processRequest(request);
			user.setEnabled(copy.isEnabled());
			user.setFullName(copy.getFullName());
			user.setRole(copy.getRole());
			user.setAdvisor(copy.getAdvisor());
			user.setNumber(copy.getNumber());
			approved = true;
			dialogShell.dispose();
		} catch (Throwable t) {
			lblErrorMessages.setText(t.toString());
		}
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

}
