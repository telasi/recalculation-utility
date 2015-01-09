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
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.Role;
import telasi.recutil.beans.User;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class UserSelector extends Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Composite composite2;
	private Composite composite3;
	private RolePane rolePane1;
	private Button btnClose;
	private Button btnOk;
	private Composite composite4;
	private UserPane userPane1;
	private SashForm sashForm1;
	private boolean approved = false;
	private User user;

	public UserSelector(Shell parent, int style) {
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
			dialogShell.setText(GUIMessages
					.getMessage("comp.user_selector.title"));
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.horizontalSpacing = 0;
				composite1Layout.marginHeight = 0;
				composite1Layout.marginWidth = 0;
				composite1Layout.verticalSpacing = 0;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					sashForm1 = new SashForm(composite1, SWT.NONE);
					GridData sashForm1LData = new GridData();
					sashForm1LData.horizontalAlignment = GridData.FILL;
					sashForm1LData.grabExcessHorizontalSpace = true;
					sashForm1LData.verticalAlignment = GridData.FILL;
					sashForm1LData.grabExcessVerticalSpace = true;
					sashForm1.setLayoutData(sashForm1LData);
					sashForm1.setSize(60, 30);
					{
						composite2 = new Composite(sashForm1, SWT.BORDER);
						GridLayout composite2Layout = new GridLayout();
						composite2Layout.horizontalSpacing = 0;
						composite2Layout.marginHeight = 0;
						composite2Layout.marginWidth = 0;
						composite2Layout.verticalSpacing = 0;
						composite2.setLayout(composite2Layout);
						composite2.setBounds(39, 84, 592, 372);
						{
							// $hide>>$
							GridData rolePane1LData = new GridData();
							rolePane1LData.verticalAlignment = GridData.FILL;
							rolePane1LData.grabExcessVerticalSpace = true;
							rolePane1LData.horizontalAlignment = GridData.FILL;
							rolePane1LData.grabExcessHorizontalSpace = true;
							rolePane1 = new RolePane(composite2, SWT.NONE);
							rolePane1.setLayoutData(rolePane1LData);
							// $hide<<$
						}
					}
					{
						composite3 = new Composite(sashForm1, SWT.BORDER);
						GridLayout composite3Layout = new GridLayout();
						composite3Layout.horizontalSpacing = 0;
						composite3Layout.marginHeight = 0;
						composite3Layout.marginWidth = 0;
						composite3Layout.verticalSpacing = 0;
						composite3.setLayout(composite3Layout);
						composite3.setBounds(62, 82, 295, 372);
						{
							// $hide>>$
							GridData userPane1LData = new GridData();
							userPane1LData.verticalAlignment = GridData.FILL;
							userPane1LData.grabExcessVerticalSpace = true;
							userPane1LData.horizontalAlignment = GridData.FILL;
							userPane1LData.grabExcessHorizontalSpace = true;
							userPane1 = new UserPane(composite3, SWT.NONE);
							userPane1.setLayoutData(userPane1LData);
							// $hide<<$
						}
					}
				}
			}
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				composite4Layout.marginWidth = 10;
				composite4Layout.marginHeight = 10;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					btnOk = new Button(composite4, SWT.PUSH | SWT.CENTER);
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
					btnClose = new Button(composite4, SWT.PUSH | SWT.CENTER);
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
			//dialogShell.setSize(800, 600);
			dialogShell.setMinimumSize(1000, 600);
			dialogShell.pack();
			GUIUtils.centerShell(dialogShell);
			initRelation();
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

	private void initRelation() {
		rolePane1.addRoleSelectionListener(new RoleSelectionListener() {
			public void roleSelectionChanged(RoleEvent event) {
				Role role = event.getRole();
				userPane1.setRole(role);
			}
		});
	}

	private void reset() {
		rolePane1.refresh(false);
		if (user != null) {
			rolePane1.selectRoleById(user.getRole().getId());
			userPane1.selectUserById(user.getId());
		}
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	private void onOk() {
		User user = userPane1.getSelectedUser();
		if (user == null) {
			return;
		}
		this.user = user;
		approved = true;
		dialogShell.dispose();
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

}
