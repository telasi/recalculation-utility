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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.ConnectionDescriptor;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.plugin.Settings;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.security.ConnectRequest;

public class LoginDialog extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Label lblLoginImage;
	private Label lblLoginDescription;
	private Label lblUserName;
	private Button btnClose;
	private Text txtUserName;
	private ServerSelector pkServer;
	private Label lblServer;
	private Button btnOk;
	private Button btnTest;
	private Composite composite3;
	private Text txtPassword;
	private Label lblPassword;
	private Composite composite2;
	private boolean approved;
	private String userName, password;
	private ConnectionDescriptor descriptor;
	private Label lblTestMessage;
	private Composite composite4;

	public LoginDialog(Shell parent, int style) {
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
					GridData lblLoginImageLData = new GridData();
					lblLoginImageLData.widthHint = 48;
					lblLoginImageLData.heightHint = 48;
					lblLoginImage = new Label(composite1, SWT.NONE);
					lblLoginImage.setLayoutData(lblLoginImageLData);
					lblLoginImage.setImage(Plugin.getImage("icons/48x48/connect.png"));
				}
				{
					lblLoginDescription = new Label(composite1, SWT.WRAP);
					GridData lblLoginDescriptionLData = new GridData();
					lblLoginDescriptionLData.verticalAlignment = GridData.FILL;
					lblLoginDescriptionLData.grabExcessVerticalSpace = true;
					lblLoginDescriptionLData.horizontalAlignment = GridData.FILL;
					lblLoginDescriptionLData.grabExcessHorizontalSpace = true;
					lblLoginDescription.setLayoutData(lblLoginDescriptionLData);
					lblLoginDescription.setText(GUIMessages.getMessage("comp.logindialog.description"));
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				composite2Layout.marginHeight = 15;
				composite2Layout.marginWidth = 15;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					lblServer = new Label(composite2, SWT.NONE);
					lblServer.setText(GUIMessages.getMessage("comp.logindialog.server"));
				}
				{
					GridData serverSelector1LData = new GridData();
					serverSelector1LData.horizontalAlignment = GridData.FILL;
					serverSelector1LData.grabExcessHorizontalSpace = true;
					pkServer = new ServerSelector(composite2, SWT.BORDER);
					pkServer.setLayoutData(serverSelector1LData);
					pkServer.setDescriptor(Settings.getLastConnectionDescriptor());
				}
				{
					lblUserName = new Label(composite2, SWT.NONE);
					lblUserName.setText(GUIMessages.getMessage("comp.logindialog.username"));
				}
				{
					GridData txtUserNameLData = new GridData();
					txtUserNameLData.horizontalAlignment = GridData.FILL;
					txtUserNameLData.grabExcessHorizontalSpace = true;
					txtUserName = new Text(composite2, SWT.BORDER);
					txtUserName.setLayoutData(txtUserNameLData);
					txtUserName.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent e) {
							validateView();
							lblTestMessage.setText("");
						}
					});
				}
				{
					lblPassword = new Label(composite2, SWT.NONE);
					lblPassword.setText(GUIMessages.getMessage("comp.logindialog.password"));
				}
				{
					GridData txtPasswordLData = new GridData();
					txtPasswordLData.horizontalAlignment = GridData.FILL;
					txtPasswordLData.grabExcessHorizontalSpace = true;
					txtPassword = new Text(composite2, SWT.BORDER);
					txtPassword.setLayoutData(txtPasswordLData);
					txtPassword.setEchoChar('*');
					txtPassword.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent e) {
							validateView();
							lblTestMessage.setText("");
						}
					});
				}
			}
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				GridData composite4LData = new GridData();
				composite4LData.verticalAlignment = GridData.FILL;
				composite4LData.grabExcessVerticalSpace = true;
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					GridData lblTestMessageLData = new GridData();
					lblTestMessageLData.horizontalAlignment = GridData.FILL;
					lblTestMessageLData.grabExcessHorizontalSpace = true;
					lblTestMessageLData.verticalAlignment = GridData.FILL;
					lblTestMessageLData.grabExcessVerticalSpace = true;
					lblTestMessage = new Label(composite4, SWT.WRAP);
					lblTestMessage.setLayoutData(lblTestMessageLData);
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 3;
				composite3Layout.marginWidth = 15;
				composite3Layout.marginHeight = 15;
				GridData composite3LData = new GridData();
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					btnTest = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnTestLData = new GridData();
					btnTestLData.grabExcessVerticalSpace = true;
					btnTestLData.verticalAlignment = GridData.END;
					btnTestLData.widthHint = 75;
					btnTest.setLayoutData(btnTestLData);
					btnTest
							.setText(GUIMessages
									.getMessage("comp.general.test"));
					btnTest.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onTest();
						}
					});
				}
				{
					btnOk = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
					btnOkLData.verticalAlignment = GridData.END;
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
					btnCloseLData.verticalAlignment = GridData.END;
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
			dialogShell.setText(GUIMessages.getMessage("comp.logindialog.title"));
			dialogShell.setMinimumSize(620, 0);
			dialogShell.pack();
			//dialogShell.setSize(550, 350);
			GUIUtils.centerShell(dialogShell);
			validateView();
			if (Application.USER_NAME != null) {
				txtUserName.setText(Application.USER_NAME);
				txtPassword.setFocus();
			} else {
				txtUserName.setFocus();
			}
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

	private void validateView() {
		String userName = txtUserName.getText();
		String password = txtPassword.getText();
		boolean userNameExists = !(userName == null || userName.trim().length() == 0);
		boolean passwordExists = !(password == null || password.trim().length() == 0);
		btnOk.setEnabled(userNameExists && passwordExists);
		btnTest.setEnabled(userNameExists && passwordExists);
	}

	private boolean onTest() {
		String userName = txtUserName.getText();
		String password = txtPassword.getText();
		ConnectionDescriptor descriptor = pkServer.getDescriptor();
		DefaultRecutilClient.destroy();
		if (descriptor == null) {
			displayText("server not defined", true);
			return false;
		}
		if (userName == null || userName.trim().length() == 0) {
			displayText("empty user name", true);
			return false;
		}
		if (password == null || password.trim().length() == 0) {
			displayText("empty password", true);
			return false;
		}
		try {
			ConnectRequest request = new ConnectRequest(userName, password);
			Application.PROVIDER_URL = descriptor.getUrl();
			Application.PROVIDER_URL2 = descriptor.toString();
			DefaultRecutilClient.processRequest(request);
			displayText("Success", false);
			return true;
		} catch (Throwable ex) {
			displayText(ex.toString(), true);
			ex.printStackTrace();
			return false;
		}
	}

	private void displayText(String text, boolean error) {
		lblTestMessage.setText(text);
		Color aColor;
		Display display = lblTestMessage.getDisplay();
		if (error) {
			aColor = display.getSystemColor(SWT.COLOR_RED);
		} else {
			aColor = display.getSystemColor(SWT.COLOR_GREEN);
		}
		lblTestMessage.setForeground(aColor);
	}

	private void onOk() {
		if (!onTest()) return;
		userName = txtUserName.getText();
		password = txtPassword.getText();
		descriptor = pkServer.getDescriptor();
		Settings.setLastConnectionDescriptor(descriptor);
		approved = true;
		dialogShell.dispose();
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	public boolean isApproved() {
		return approved;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public ConnectionDescriptor getDescriptor() {
		return descriptor;
	}

}
