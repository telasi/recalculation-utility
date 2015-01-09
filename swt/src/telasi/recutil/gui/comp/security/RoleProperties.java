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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import telasi.recutil.beans.Role;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.security.RoleUpdateRequest;

public class RoleProperties extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private TabFolder tabs;
	private Button btnOk;
	private Button btnClose;
	private Composite composite2;
	private Label lblError;
	private PermissionPane permissionPane1;
	private UserPane userPane1;
	private RoleGeneralPane rolePane1;
	private TabItem tabItem3;
	private TabItem tabItem2;
	private TabItem tabItem1;
	private Role role;
	private boolean approved;

	public RoleProperties(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
			dialogShell.setLayout(new GridLayout());
			dialogShell.layout();
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					tabs = new TabFolder(composite1, SWT.NONE);
					{
						tabItem1 = new TabItem(tabs, SWT.NONE);
						tabItem1.setText(GUIMessages.getMessage("comp.roleproperties.general_tab"));
						{
							//$hide>>$
							rolePane1 = new RoleGeneralPane(tabs, SWT.NONE);
							tabItem1.setControl(rolePane1);
							//$hide<<$
						}
					}
					{
						tabItem2 = new TabItem(tabs, SWT.NONE);
						tabItem2.setText(GUIMessages.getMessage("comp.roleproperties.users_tab"));
						{
							//$hide>>$
							userPane1 = new UserPane(tabs, SWT.NONE);
							tabItem2.setControl(userPane1);
							//$hide<<$
						}
					}
					{
						tabItem3 = new TabItem(tabs, SWT.NONE);
						tabItem3.setText(GUIMessages.getMessage("comp.roleproperties.permission_tab"));
						{
							//$hide>>$
							permissionPane1 = new PermissionPane(tabs, SWT.NONE);
							tabItem3.setControl(permissionPane1);
							//$hide<<$
						}
					}
					GridData tabsLData = new GridData();
					tabsLData.horizontalAlignment = GridData.FILL;
					tabsLData.grabExcessHorizontalSpace = true;
					tabsLData.verticalAlignment = GridData.FILL;
					tabsLData.grabExcessVerticalSpace = true;
					tabs.setLayoutData(tabsLData);
					tabs.setSelection(0);
				}
				{
					lblError = new Label(composite1, SWT.WRAP);
					GridData lblErrorLData = new GridData();
					lblErrorLData.horizontalAlignment = GridData.FILL;
					lblErrorLData.grabExcessHorizontalSpace = true;
					lblErrorLData.heightHint = 50;
					lblError.setLayoutData(lblErrorLData);
					lblError.setForeground(lblError.getDisplay().getSystemColor(SWT.COLOR_RED));
				}
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.numColumns = 2;
					GridData composite2LData = new GridData();
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					{
						btnOk = new Button(composite2, SWT.PUSH | SWT.CENTER);
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
						btnClose = new Button(composite2, SWT.PUSH | SWT.CENTER);
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
			}

			//$protect>>$
			dialogShell.setMinimumSize(500, 0);
			dialogShell.pack();
			//dialogShell.setSize(500, 550);
			GUIUtils.centerShell(dialogShell);
			dialogShell.setText(GUIMessages.getMessage("comp.roleproperties.title"));
			reset();
			//$protect<<$

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isApproved() {
		return approved;
	}

	private void reset() {
		userPane1.setRole(role);
		rolePane1.displayRole(role);
		permissionPane1.setRole(role);
	}

	private void onOk() {

		// get parameters and validate them
		lblError.setText("");
		String roleName = rolePane1.getRoleName();
		String roleDescription = rolePane1.getRoleDescription();
		boolean roleEnabled = rolePane1.isRoleEnabled();
		if (roleName == null || roleName.trim().length() == 0) {
			lblError.setText(GUIMessages.getMessage("comp.general.empty_name_field"));
			return;
		}

		// create new role
		Role newRole = new Role(role.getId());
		newRole.setName(roleName);
		newRole.setDescription(roleDescription);
		newRole.setEnabled(roleEnabled);

		// process request
		RoleUpdateRequest request = new RoleUpdateRequest(Application.USER_NAME, Application.PASSWORD);
		request.setRole(newRole);
		try {
			DefaultRecutilClient.processRequest(request);
			role.setName(newRole.getName());
			role.setDescription(newRole.getDescription());
			role.setEnabled(newRole.isEnabled());
			approved = true;
			dialogShell.dispose();
		} catch (Throwable t) {
			lblError.setText(t.toString());
		}

	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

}
