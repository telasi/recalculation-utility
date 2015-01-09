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
package telasi.recutil.gui.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.beans.Role;
import telasi.recutil.gui.comp.security.PermissionPane;
import telasi.recutil.gui.comp.security.RoleEvent;
import telasi.recutil.gui.comp.security.RolePane;
import telasi.recutil.gui.comp.security.RoleSelectionListener;
import telasi.recutil.gui.comp.security.UserPane;
import telasi.recutil.gui.utils.GUIMessages;

public class UserView extends ViewPart {
	public static final String ID_VIEW = "ge.telasi.recut.guiapp.views.UserView"; //$NON-NLS-1$
	private RolePane rolePane;
	private Composite composite7;
	private Composite composite6;
	private SashForm sashForm2;
	private Composite composite5;
	private Composite composite4;
	private SashForm sashForm1;
	private Composite composite3;
	private PermissionPane permissionPane;
	private UserPane userPane;
	private boolean firstFocusCall = true;

	public UserView() {
		super();
	}

	public void createPartControl(Composite parent) {
		parent.setSize(300, 250);
		composite3 = new Composite(parent, SWT.NONE);
		GridLayout composite3Layout = new GridLayout();
		composite3Layout.horizontalSpacing = 0;
		composite3Layout.marginHeight = 0;
		composite3Layout.marginWidth = 0;
		composite3Layout.verticalSpacing = 0;
		composite3.setLayout(composite3Layout);
		{
			sashForm1 = new SashForm(composite3, SWT.NONE);
			GridData sashForm1LData = new GridData();
			sashForm1LData.horizontalAlignment = GridData.FILL;
			sashForm1LData.grabExcessHorizontalSpace = true;
			sashForm1LData.verticalAlignment = GridData.FILL;
			sashForm1LData.grabExcessVerticalSpace = true;
			sashForm1.setLayoutData(sashForm1LData);
			sashForm1.setSize(60, 30);
			{
				composite4 = new Composite(sashForm1, SWT.BORDER);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.makeColumnsEqualWidth = true;
				composite4Layout.horizontalSpacing = 0;
				composite4Layout.marginHeight = 0;
				composite4Layout.marginWidth = 0;
				composite4Layout.verticalSpacing = 0;
				composite4.setLayout(composite4Layout);
				composite4.setBounds(-108, 62, 133, 64);
			}
			{
				GridData data1 = new GridData();
				data1.verticalAlignment = GridData.FILL;
				data1.horizontalAlignment = GridData.FILL;
				data1.grabExcessVerticalSpace = true;
				data1.grabExcessHorizontalSpace = true;
				// $hide>>$
				rolePane = new RolePane(composite4, SWT.NONE);
				rolePane.setLayoutData(data1);
				// $hide<<$
			}
			{
				composite5 = new Composite(sashForm1, SWT.NONE);
				GridLayout composite5Layout = new GridLayout();
				composite5Layout.horizontalSpacing = 0;
				composite5Layout.marginHeight = 0;
				composite5Layout.marginWidth = 0;
				composite5Layout.verticalSpacing = 0;
				composite5.setLayout(composite5Layout);
				composite5.setBounds(-89, 65, 65, 64);
				{
					sashForm2 = new SashForm(composite5, SWT.VERTICAL);
					GridData sashForm2LData = new GridData();
					sashForm2LData.horizontalAlignment = GridData.FILL;
					sashForm2LData.grabExcessHorizontalSpace = true;
					sashForm2LData.verticalAlignment = GridData.FILL;
					sashForm2LData.grabExcessVerticalSpace = true;
					sashForm2.setLayoutData(sashForm2LData);
					sashForm2.setSize(60, 30);
					{
						composite6 = new Composite(sashForm2, SWT.BORDER);
						GridLayout composite6Layout = new GridLayout();
						composite6Layout.marginHeight = 0;
						composite6Layout.horizontalSpacing = 0;
						composite6Layout.verticalSpacing = 0;
						composite6Layout.marginWidth = 0;
						composite6.setLayout(composite6Layout);
					}
					{
						GridData data3 = new GridData();
						data3.horizontalAlignment = GridData.FILL;
						data3.grabExcessHorizontalSpace = true;
						data3.verticalAlignment = GridData.FILL;
						data3.grabExcessVerticalSpace = true;
						// $hide>>$
						userPane = new UserPane(composite6, SWT.NONE);
						userPane.setLayoutData(data3);
						// $hide<<$
					}
					{
						composite7 = new Composite(sashForm2, SWT.BORDER);
						GridLayout composite7Layout = new GridLayout();
						composite7Layout.horizontalSpacing = 0;
						composite7Layout.marginHeight = 0;
						composite7Layout.marginWidth = 0;
						composite7Layout.verticalSpacing = 0;
						composite7.setLayout(composite7Layout);
					}
					{
						GridData data4 = new GridData();
						data4.horizontalAlignment = GridData.FILL;
						data4.grabExcessHorizontalSpace = true;
						data4.verticalAlignment = GridData.FILL;
						data4.grabExcessVerticalSpace = true;
						// $hide>>$
						permissionPane = new PermissionPane(composite7,
								SWT.NONE);
						permissionPane.setLayoutData(data4);
						// $hide<<$
					}
				}
			}
		}

		// $protect>>$
		customInit();
		// $protect<<$
	}

	private void customInit() {
		setPartName(GUIMessages.getMessage("application.action.users"));
		rolePane.addRoleSelectionListener(new RoleSelectionListener() {
			public void roleSelectionChanged(RoleEvent event) {
				Role role = event.getRole();
				userPane.setRole(role);
				permissionPane.setRole(role);
			}
		});
	}

	public void setFocus() {
		if (firstFocusCall) {
			rolePane.refresh(false);
			firstFocusCall = false;
		}
	}

	public void dispose() {
		super.dispose();
	}

}
