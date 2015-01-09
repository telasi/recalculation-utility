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
import org.eclipse.swt.widgets.Label;

import telasi.recutil.beans.Role;
import telasi.recutil.gui.utils.GUIMessages;

public class RoleSelector extends Composite {

	private Label lblRoleName;
	private Button btnEclipses;
	private Role role;

	public RoleSelector(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 2;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 2;
			thisLayout.marginHeight = 0;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				GridData lblRoleNameLData = new GridData();
				lblRoleNameLData.horizontalAlignment = GridData.FILL;
				lblRoleNameLData.grabExcessHorizontalSpace = true;
				lblRoleName = new Label(this, SWT.NONE);
				lblRoleName.setLayoutData(lblRoleNameLData);
			}
			{
				btnEclipses = new Button(this, SWT.PUSH | SWT.CENTER);
				btnEclipses.setText("...");
				btnEclipses.setToolTipText(GUIMessages
						.getMessage("comp.roleselector.title"));
				btnEclipses.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onSelectRole();
					}
				});
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onSelectRole() {
		RoleSelectorDialog dialog = new RoleSelectorDialog(getShell(), SWT.NONE);
		dialog.setRole(role);
		dialog.open();
		if (dialog.isApproved()) {
			setRole(dialog.getRole());
		}
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
		lblRoleName.setText(role == null ? "<null>" : role.getName());
	}

}
