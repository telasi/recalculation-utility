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

import telasi.recutil.beans.User;
import telasi.recutil.gui.utils.GUITranslator;

public class UserPicker extends Composite {
	private Label lblUser;
	private Button btnSelect;
	private User user;
	private User defaultUser;

	public UserPicker(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginWidth = 0;
			thisLayout.marginHeight = 0;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				GridData lblUserLData = new GridData();
				lblUserLData.horizontalAlignment = GridData.FILL;
				lblUserLData.grabExcessHorizontalSpace = true;
				lblUser = new Label(this, SWT.NONE);
				lblUser.setLayoutData(lblUserLData);
			}
			{
				btnSelect = new Button(this, SWT.PUSH | SWT.CENTER);
				btnSelect.setText("...");
				btnSelect.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onSelect();
					}
				});
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onSelect() {
		UserSelector selector = new UserSelector(getShell(), SWT.NONE);
		if (user != null) {
			selector.setUser(user);
		} else {
			selector.setUser(defaultUser);
		}
		selector.open();
		if (selector.isApproved()) {
			user = selector.getUser();
			reset();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		reset();
	}

	private void reset() {
		lblUser.setText(user == null ? "" : GUITranslator.GEO_ASCII_TO_KA(user
				.getFullName())
				+ " (" + user.getUserName() + ")");
	}

	public User getDefaultUser() {
		return defaultUser;
	}

	public void setDefaultUser(User defaultUser) {
		this.defaultUser = defaultUser;
	}

}
