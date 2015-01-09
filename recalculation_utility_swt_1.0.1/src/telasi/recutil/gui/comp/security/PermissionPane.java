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


import java.util.ArrayList;
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
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Role;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.ActionConstants;
import telasi.recutil.service.eclipse.security.PermissionDeleteRequest;

public class PermissionPane extends Composite {

	private Composite composite1;
	private Composite composite2;
	private TableViewer viewer;
	private ToolItem tiRemovePermission;
	private ToolItem tiAddPermission;
	private ToolBar toolbar;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;
	private Label lblTitle;
	private Role role;

	public PermissionPane(Composite parent, int style) {
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
			this.setSize(375, 297);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				composite1Layout.marginHeight = 10;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblTitle = new Label(composite1, SWT.NONE);
					GridData lblTitleLData = new GridData();
					lblTitleLData.verticalAlignment = GridData.FILL;
					lblTitleLData.grabExcessVerticalSpace = true;
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
					lblTitle.setText(GUIMessages
							.getMessage("comp.permissionpane.title"));
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
					GridData lblImageLData = new GridData();
					lblImageLData.widthHint = 48;
					lblImageLData.heightHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin
							.getImage("icons/48x48/permission.png"));
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages
							.getMessage("comp.permissionpane.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
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
					viewer = new TableViewer(composite3, SWT.BORDER | SWT.MULTI
							| SWT.VIRTUAL | SWT.FULL_SELECTION);
					viewer.getControl().setLayoutData(viewerLData);
					// $protect>>$
					viewer
							.addSelectionChangedListener(new ISelectionChangedListener() {
								public void selectionChanged(
										SelectionChangedEvent event) {
									validateView();
								}
							});
					// $protect<<$
				}
			}
			{
				GridData toolbarLData = new GridData();
				toolbarLData.horizontalAlignment = GridData.FILL;
				toolbarLData.grabExcessHorizontalSpace = true;
				toolbar = new ToolBar(this, SWT.NONE);
				toolbar.setLayoutData(toolbarLData);
				{
					tiAddPermission = new ToolItem(toolbar, SWT.NONE);
					tiAddPermission.setImage(Plugin
							.getImage("icons/22x22/add.png"));
					tiAddPermission.setToolTipText(GUIMessages
							.getMessage("comp.permissionpane.addaction"));
					tiAddPermission
							.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									onAdd();
								}
							});
				}
				{
					tiRemovePermission = new ToolItem(toolbar, SWT.NONE);
					tiRemovePermission.setImage(Plugin
							.getImage("icons/22x22/remove.png"));
					tiRemovePermission.setToolTipText(GUIMessages
							.getMessage("comp.permissionpane.removeaction"));
					tiRemovePermission
							.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent e) {
									onRemove();
								}
							});
				}
			}
			this.layout();
			// $protect>>$
			validateView();
			// $protect<<$

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayPermissionsFor(role);
		viewer.setInput(this);
		viewer.setLabelProvider(new PermissionLabelProvider());
	}

	@SuppressWarnings("unchecked")
	private void displayPermissionsFor(Role role) {
		List actions = new ArrayList();
		if (role == null) {
		} else if (role.getId() == ActionConstants.ROLE_ADMIN) {
			actions.add(new Integer(0));// all permissions
		} else {
			if (role.getActions() != null) {
				actions.addAll(role.getActions());
			}
		}
		viewer.setItemCount(actions.size());
		viewer.setContentProvider(new PermissionContentProvider(actions));
		validateView();
	}

	//
	// Public section
	//

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
		displayPermissionsFor(role);
	}

	@SuppressWarnings("unchecked")
	public List getSelectedActions() {
		int indecies[] = viewer.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		List actions = new ArrayList();
		PermissionContentProvider provider = (PermissionContentProvider) viewer
				.getContentProvider();
		for (int i = 0; i < indecies.length; i++) {
			actions.add(provider.actions.get(indecies[i]));
		}
		return actions;
	}

	//
	// Private methods
	//

	private void validateView() {
		boolean roleExist = role != null;
		boolean isAdminRole = roleExist
				&& role.getId() == ActionConstants.ROLE_ADMIN;
		if (!roleExist || isAdminRole) {
			tiAddPermission.setEnabled(false);
			tiRemovePermission.setEnabled(false);
			return;
		}
		List actions = getSelectedActions();
		boolean actionsSelected = actions != null && !actions.isEmpty();
		tiAddPermission.setEnabled(true);
		tiRemovePermission.setEnabled(actionsSelected);
	}

	private void onAdd() {
		boolean roleExist = role != null;
		boolean isAdminRole = roleExist
				&& role.getId() == ActionConstants.ROLE_ADMIN;
		if (!roleExist || isAdminRole) {
			return;
		}

		ActionSelectorDialog selector = new ActionSelectorDialog(getShell(),
				SWT.NONE);
		selector.setRole(role);
		selector.open();
		if (selector.isApproved()) {
			displayPermissionsFor(role);
			validateView();
		}
	}

	@SuppressWarnings("unchecked")
	private void onRemove() {
		boolean roleExist = role != null;
		boolean isAdminRole = roleExist
				&& role.getId() == ActionConstants.ROLE_ADMIN;
		if (!roleExist || isAdminRole) {
			return;
		}
		List forRemove = getSelectedActions();
		if (forRemove == null || forRemove.isEmpty()) {
			return;
		}
		String msg = GUIMessages.getMessage(
				"comp.permissionpane.remove_permission_confirm",
				new Object[] { new Integer(forRemove.size()) });
		String title = GUIMessages.getMessage("comp.general.confirm");
		boolean resp = MessageDialog.openQuestion(getShell(), title, msg);
		if (!resp) {
			return;
		}
		// process
		List requests = new ArrayList();
		for (int i = 0; i < forRemove.size(); i++) {
			PermissionDeleteRequest req = new PermissionDeleteRequest(
					Application.USER_NAME, Application.PASSWORD);
			req.setRole(role);
			req.setAction(((Integer) forRemove.get(i)).intValue());
			requests.add(req);
		}
		try {
			DefaultRecutilClient.processRequests(requests);
			role.getActions().removeAll(forRemove);
			displayPermissionsFor(role);
			validateView();
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	//
	// Inner classes
	//

	static class PermissionContentProvider implements
			IStructuredContentProvider {

		List actions;

		public PermissionContentProvider(List actions) {
			this.actions = actions;
		}

		public Object[] getElements(Object inputElement) {
			return actions == null ? new Object[] {} : actions.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	static class PermissionLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			Integer actionId = (Integer) element;
			return GUIUtils.getActionName(actionId.intValue());
		}
	}

}
