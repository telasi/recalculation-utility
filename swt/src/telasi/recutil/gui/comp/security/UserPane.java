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

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Role;
import telasi.recutil.beans.User;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.security.UserDeleteRequest;

public class UserPane extends Composite {

	private Composite composite1;
	private Composite composite2;
	private TableViewer viewer;
	private TableColumn colFullName;
	private TableColumn colUserName;
	private TableColumn colImage;
	private TableColumn colNumber;
	private ToolItem tiUserProperties;
	private ToolItem tiDeleteUser;
	private ToolItem tiNewUser;
	private ToolBar toolBar1;
	private Composite composite3;
	private Label lblSeparator1;
	private Label lblDescription;
	private Label lblTitle;
	private Label lblImage;
	private Role role;

	public UserPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	//
	// Initialization
	//

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(444, 286);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1Layout.marginHeight = 10;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblTitle = new Label(composite1, SWT.NONE);
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitleLData.verticalAlignment = GridData.FILL;
					lblTitleLData.grabExcessVerticalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setText(GUIMessages
							.getMessage("comp.userpane.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
				}
			}
			{
				GridData lblSeparator1LData = new GridData();
				lblSeparator1LData.horizontalAlignment = GridData.FILL;
				lblSeparator1LData.grabExcessHorizontalSpace = true;
				lblSeparator1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator1.setLayoutData(lblSeparator1LData);
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblImageLabelLData = new GridData();
					lblImageLabelLData.widthHint = 48;
					lblImageLabelLData.heightHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLabelLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/user.png"));
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.username.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite3, SWT.BORDER
							| SWT.SINGLE | SWT.FULL_SELECTION | SWT.VIRTUAL);
					viewer.getTable().setHeaderVisible(true);
					viewer.getTable().setLinesVisible(true);
					viewer.getControl().setLayoutData(viewerLData);
					viewer.addSelectionChangedListener(new ISelectionChangedListener() {
								public void selectionChanged(
										SelectionChangedEvent event) {
									validateView();
								}
							});
					{
						colImage = new TableColumn(viewer.getTable(), SWT.NONE);
						colImage.setWidth(20);
						colImage.setResizable(false);
					}
					{
						colNumber = new TableColumn(viewer.getTable(), SWT.NONE);
						colNumber.setWidth(60);
						colNumber.setResizable(true);
						colNumber.setText("#");
					}
					{
						colUserName = new TableColumn(viewer.getTable(), SWT.NONE);
						colUserName.setText(GUIMessages.getMessage("comp.general.username"));
						colUserName.setWidth(150);
					}
					{
						colFullName = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colFullName.setText(GUIMessages
								.getMessage("comp.general.fullname"));
						colFullName.setWidth(200);
						colFullName.setResizable(false);
					}
				}
			}
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiNewUser = new ToolItem(toolBar1, SWT.NONE);
					tiNewUser.setImage(Plugin.getImage("icons/22x22/user_add.png"));
					tiNewUser.setToolTipText(GUIMessages.getMessage("comp.userpane.newuser"));
					tiNewUser.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onNewUser();
						}
					});
				}
				{
					tiDeleteUser = new ToolItem(toolBar1, SWT.NONE);
					tiDeleteUser.setImage(Plugin.getImage("icons/22x22/trash.png"));
					tiDeleteUser.setToolTipText(GUIMessages.getMessage("comp.userpane.deleteuser"));
					tiDeleteUser.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onDelete();
						}
					});
				}
				{
					tiUserProperties = new ToolItem(toolBar1, SWT.NONE);
					tiUserProperties.setImage(Plugin.getImage("icons/22x22/properties.gif"));
					tiUserProperties.setToolTipText(GUIMessages.getMessage("comp.userpane.userproperties"));
					tiUserProperties.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									onProperties();
								}
							});
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayUsers(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new UserLabelProvider());
	}

	//
	// Private section
	//

	private void displayUsers(List users) {
		viewer.setItemCount(users == null ? 0 : users.size());
		viewer.setContentProvider(new UserContentProvider(users));
		validateView();
	}

	private void validateView() {
		boolean roleExists = role != null;
		User user = getSelectedUser();
		boolean userSelected = user != null;
		tiNewUser.setEnabled(roleExists);
		tiDeleteUser.setEnabled(roleExists && userSelected);
		tiUserProperties.setEnabled(roleExists && userSelected);
	}

	private void onNewUser() {
		if (role == null) {
			return;
		}
		User user = new User(-1);
		user.setUserName("");
		user.setFullName("");
		user.setPassword("");
		user.setRole(role);
		user.setEnabled(true);
		NewUserDialog dialog = new NewUserDialog(getShell(), SWT.NONE);
		dialog.setUser(user);
		dialog.open();
		if (dialog.getUser().getId() == -1) {
			return;
		}
		User newUser = dialog.getUser();
		newUser.getRole().addUser(newUser);
		viewer.getTable().deselectAll();
		displayUsers(role.getUsers());
		selectUserById(newUser.getId());
	}

	private void onDelete() {
		User user = getSelectedUser();
		if (user == null) {
			return;
		}
		String title = GUIMessages.getMessage("comp.general.confirm");
		String message = GUIMessages.getMessage("comp.general.confirm_delete",
				new Object[] { user.getFullName() + " (" + user.getUserName()
						+ ")" });
		boolean resp = MessageDialog.openQuestion(getShell(), title, message);
		if (!resp) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		// delete user
		try {
			UserDeleteRequest request = new UserDeleteRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setUser(user);
			DefaultRecutilClient.processRequest(request);
			user.getRole().removeUser(user);
			viewer.getTable().deselectAll();
			displayUsers(role.getUsers());
		} catch (Throwable t) {
			String tit = GUIMessages.getMessage("comp.general.error");
			String msg = t.toString();
			MessageDialog.openError(getShell(), tit, msg);
		}

	}

	private void onProperties() {
		User user = getSelectedUser();
		if (user == null) {
			return;
		}
		UserProperties prop = new UserProperties(getShell(), SWT.NONE);
		prop.setUser(user);
		prop.open();
		if (prop.isApproved()) {
			if (!role.equals(prop.getUser().getRole())) {
				role.removeUser(prop.getUser());
				prop.getUser().getRole().addUser(prop.getUser());
			}
			viewer.getTable().deselectAll();
			displayUsers(role.getUsers());
			selectUserById(user.getId());
		}
	}

	//
	// Public methods
	//

	public void setRole(Role role) {
		this.role = role;
		displayUsers(role == null ? null : role.getUsers());
		validateView();
	}

	public Role getRole() {
		return role;
	}

	public User getSelectedUser() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		UserContentProvider provider = (UserContentProvider) viewer
				.getContentProvider();
		return (User) provider.users.get(index);
	}

	public void selectUserById(long id) {
		UserContentProvider provider = (UserContentProvider) viewer
				.getContentProvider();
		if (provider.users == null) {
			return;
		}
		for (int i = 0; i < provider.users.size(); i++) {
			User user = (User) provider.users.get(i);
			if (user.getId() == id) {
				viewer.getTable().setSelection(i);
				viewer.getTable().showSelection();
				break;
			}
		}
		validateView();
	}

	//
	// Inner classes
	//

	private class UserContentProvider implements IStructuredContentProvider {

		private List users;

		public UserContentProvider(List users) {
			this.users = users;
		}

		public Object[] getElements(Object inputElement) {
			return users == null ? new Object[] {} : users.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private class UserLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			User user = (User) element;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getUserImage(user);
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			User user = (User) element;
			switch (columnIndex) {
			case 1:
				return user.getNumber() == null ? "" : user.getNumber();
			case 2:
				return user.getUserName();
			case 3:
				return GUITranslator.GEO_ASCII_TO_KA(user.getFullName());
			default:
				return "";
			}
		}

	}

}
