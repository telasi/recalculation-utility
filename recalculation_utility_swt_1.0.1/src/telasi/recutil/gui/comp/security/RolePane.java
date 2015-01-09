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
import java.util.Vector;

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
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.security.RoleDeleteRequest;

public class RolePane extends Composite {

	//
	// Local fields
	//

	private Composite composite1;
	private TableViewer viewer;
	private Composite composite3;
	private Label lblSeparatorTop;
	private Label lblTitle;
	private ToolItem tiProperties;
	private ToolItem tiDeleteGroup;
	private ToolItem toolItem1;
	private ToolItem tiNewGroup;
	private ToolItem tiRefresh;
	private ToolBar toolbar;
	private TableColumn colDescription;
	private TableColumn colName;
	private TableColumn colImage;
	private Composite composite2;
	private Label lblRoleText;
	private Label lblRoleImage;

	//
	// Constructor(s)
	//

	public RolePane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	//
	// Private section
	//

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			thisLayout.horizontalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(314, 265);
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.marginHeight = 10;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle = new Label(composite3, SWT.NONE);
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setText(GUIMessages
							.getMessage("comp.rolepane.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
				}
			}
			{
				GridData lblSeparatorTopLData = new GridData();
				lblSeparatorTopLData.horizontalAlignment = GridData.FILL;
				lblSeparatorTopLData.grabExcessHorizontalSpace = true;
				lblSeparatorTop = new Label(this, SWT.SEPARATOR
						| SWT.HORIZONTAL);
				lblSeparatorTop.setLayoutData(lblSeparatorTopLData);
			}
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData lblUserGroupImageLData = new GridData();
					lblUserGroupImageLData.widthHint = 48;
					lblUserGroupImageLData.heightHint = 48;
					lblRoleImage = new Label(composite1, SWT.NONE);
					lblRoleImage.setLayoutData(lblUserGroupImageLData);
					lblRoleImage.setImage(Plugin
							.getImage("icons/48x48/group.png"));
				}
				{
					GridData lblGroupTexrLData = new GridData();
					lblGroupTexrLData.verticalAlignment = GridData.FILL;
					lblGroupTexrLData.grabExcessVerticalSpace = true;
					lblGroupTexrLData.horizontalAlignment = GridData.FILL;
					lblGroupTexrLData.grabExcessHorizontalSpace = true;
					lblRoleText = new Label(composite1, SWT.WRAP);
					lblRoleText.setLayoutData(lblGroupTexrLData);
					lblRoleText.setText(GUIMessages
							.getMessage("comp.rolepane.description"));
				}
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2LData.verticalAlignment = GridData.FILL;
				composite2LData.grabExcessVerticalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData viewerLData = new GridData();
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewer = new TableViewer(composite2, SWT.BORDER
							| SWT.FULL_SELECTION | SWT.SINGLE | SWT.VIRTUAL);
					viewer.getControl().setLayoutData(viewerLData);
					viewer.getTable().setHeaderVisible(true);
					viewer.getTable().setLinesVisible(true);
					{
						colImage = new TableColumn(viewer.getTable(), SWT.NONE);
						colImage.setWidth(20);
						colImage.setResizable(false);
					}
					{
						colName = new TableColumn(viewer.getTable(), SWT.NONE);
						colName.setWidth(150);
						colName.setText(GUIMessages
								.getMessage("comp.rolepane.role"));
					}
					{
						colDescription = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colDescription.setText(GUIMessages
								.getMessage("comp.general.description"));
						colDescription.setWidth(200);
					}
					viewer
							.addSelectionChangedListener(new ISelectionChangedListener() {
								public void selectionChanged(
										SelectionChangedEvent event) {
									validateView();
								}
							});
				}
			}
			{
				GridData toolbarLData = new GridData();
				toolbarLData.horizontalAlignment = GridData.FILL;
				toolbarLData.grabExcessHorizontalSpace = true;
				toolbar = new ToolBar(this, SWT.NONE);
				toolbar.setLayoutData(toolbarLData);
				{
					tiRefresh = new ToolItem(toolbar, SWT.NONE);
					tiRefresh.setToolTipText(GUIMessages
							.getMessage("comp.general.refresh"));
					tiRefresh.setImage(Plugin
							.getImage("icons/22x22/refresh.png"));
					tiRefresh.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							refresh(true);
						}
					});
				}
				{
					toolItem1 = new ToolItem(toolbar, SWT.SEPARATOR);
					toolItem1.setText("");
				}
				{
					tiNewGroup = new ToolItem(toolbar, SWT.NONE);
					tiNewGroup.setToolTipText(GUIMessages
							.getMessage("comp.rolepane.new_action"));
					tiNewGroup.setImage(Plugin.getImage("icons/22x22/new.gif"));
					tiNewGroup.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onNew();
						}
					});
				}
				{
					tiDeleteGroup = new ToolItem(toolbar, SWT.NONE);
					tiDeleteGroup.setToolTipText(GUIMessages
							.getMessage("comp.rolepane.delete_action"));
					tiDeleteGroup.setImage(Plugin
							.getImage("icons/22x22/trash.png"));
					tiDeleteGroup.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onDelete();
						}
					});
				}
				{
					tiProperties = new ToolItem(toolbar, SWT.NONE);
					tiProperties.setToolTipText(GUIMessages
							.getMessage("comp.rolepane.properties_action"));
					tiProperties.setImage(Plugin
							.getImage("icons/22x22/properties.gif"));
					tiProperties.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onProperties();
						}
					});
				}
			}
			layout();
			// $hide>>$
			validateView();
			// $hide<<$
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayRoles(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new RoleLabelProvider());
	}

	private void displayRoles(List roles) {
		viewer.setItemCount(roles == null ? 0 : roles.size());
		viewer.setContentProvider(new RoleContentProvider(roles));
		validateView();
	}

	private void validateView() {

		// get selected role
		Role role = getSelectedRole();

		// enable/disable actions
		boolean roleSelected = role != null;
		tiDeleteGroup.setEnabled(roleSelected);
		tiProperties.setEnabled(roleSelected);
		tiNewGroup.setEnabled(true);
		tiRefresh.setEnabled(true);

		// fire change event
		fireRoleSelectionChanged(role);
	}

	private void onNew() {
		NewRoleDialog dialog = new NewRoleDialog(getShell(), SWT.NONE);
		dialog.open();
		long newRoleId = dialog.getNewRoleId();
		if (newRoleId == -1) {
			return;
		}
		refresh(true);
		selectRoleById(newRoleId);
	}

	private void onDelete() {
		Role role = getSelectedRole();
		if (role == null) {
			return;
		}
		String title = GUIMessages.getMessage("comp.general.confirm");
		String msg = GUIMessages.getMessage("comp.general.confirm_delete",
				new Object[] { role.getName() });
		boolean resp = MessageDialog.openQuestion(getShell(), title, msg);
		if (!resp) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RoleDeleteRequest request = new RoleDeleteRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRole(role);
			DefaultRecutilClient.processRequest(request);
			refresh(true);
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	private void onProperties() {
		Role role = getSelectedRole();
		if (role == null) {
			return;
		}
		RoleProperties prop = new RoleProperties(getShell(), SWT.NONE);
		prop.setRole(role);
		prop.open();
		if (prop.isApproved()) {
			viewer.refresh();
		}
	}

	//
	// Public section
	//

	public void refresh(boolean forceNew) {
		refreshRolePane(this, forceNew);
	}

	public Role getSelectedRole() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		RoleContentProvider provider = (RoleContentProvider) viewer
				.getContentProvider();
		return (Role) provider.roles.get(index);
	}

	public void selectRoleById(long roleId) {
		RoleContentProvider provider = (RoleContentProvider) viewer
				.getContentProvider();
		if (provider.roles == null) {
			return;
		}
		for (int i = 0; i < provider.roles.size(); i++) {
			Role role = (Role) provider.roles.get(i);
			long id = role.getId();
			if (roleId == id) {
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

	private class RoleContentProvider implements IStructuredContentProvider {

		private List roles;

		public RoleContentProvider(List roles) {
			this.roles = roles;
		}

		public Object[] getElements(Object inputElement) {
			return roles == null ? new Object[] {} : roles.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private class RoleLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			Role role = (Role) element;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getRoleImage(role);
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			Role role = (Role) element;
			switch (columnIndex) {
			case 1:
				return GUITranslator.GEO_ASCII_TO_KA(role.getName());
			case 2:
				return GUITranslator.GEO_ASCII_TO_KA(role.getDescription());
			default:
				return "";
			}
		}
	}

	//
	// Factory methods
	//

	public static void refreshRolePane(RolePane pane, boolean forceNew) {
		try {
			Cache.refreshRoleList(forceNew);
		} catch (Throwable t) {
			MessageDialog.openError(pane.getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
		pane.displayRoles(Cache.ROLE_LIST);
	}

	//
	// Event listeners
	//

	private Vector roleSelectionListeners = new Vector();

	@SuppressWarnings("unchecked")
	public void addRoleSelectionListener(RoleSelectionListener l) {
		roleSelectionListeners.add(l);
	}

	public void removeRoleSelectionListener(RoleSelectionListener l) {
		roleSelectionListeners.remove(l);
	}

	protected void fireRoleSelectionChanged(Role role) {
		for (int i = 0; i < roleSelectionListeners.size(); i++) {
			RoleSelectionListener l = (RoleSelectionListener) roleSelectionListeners
					.get(i);
			l.roleSelectionChanged(new RoleEvent(this, role));
		}
	}

}
