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

import org.eclipse.jface.viewers.TableViewer;
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
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.ActionConstants;
import telasi.recutil.service.eclipse.security.PermissionInsertRequest;

public class ActionSelectorDialog extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Composite composite2;
	private Button btnOk;
	private TableViewer viewer;
	private Label lblSeparator1;
	private Label lblTitle;
	private Composite composite5;
	private Label lblError;
	private Button btnClose;
	private Composite composite4;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImageLabel;
	private Role role;
	private boolean approved;

	public ActionSelectorDialog(Shell parent, int style) {
		super(parent, style);
	}

	public boolean isApproved() {
		return approved;
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
				composite5 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite5Layout = new GridLayout();
				composite5Layout.makeColumnsEqualWidth = true;
				composite5Layout.marginTop = 10;
				GridData composite5LData = new GridData();
				composite5LData.horizontalAlignment = GridData.FILL;
				composite5LData.grabExcessHorizontalSpace = true;
				composite5.setLayoutData(composite5LData);
				composite5.setLayout(composite5Layout);
				{
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle = new Label(composite5, SWT.NONE);
					lblTitle.setLayoutData(lblTitleLData);
					// $protect>>$
					lblTitle.setText(GUIMessages
							.getMessage("comp.action_selector.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
					// $protect<<$
				}
			}
			{
				GridData lblSeparator1LData = new GridData();
				lblSeparator1LData.horizontalAlignment = GridData.FILL;
				lblSeparator1LData.grabExcessHorizontalSpace = true;
				lblSeparator1 = new Label(dialogShell, SWT.SEPARATOR
						| SWT.HORIZONTAL);
				lblSeparator1.setLayoutData(lblSeparator1LData);
			}
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
					GridData lblImageLabelLData = new GridData();
					lblImageLabelLData.widthHint = 48;
					lblImageLabelLData.heightHint = 48;
					lblImageLabel = new Label(composite1, SWT.NONE);
					lblImageLabel.setLayoutData(lblImageLabelLData);
					// $protect>>$
					lblImageLabel.setImage(Plugin
							.getImage("icons/48x48/action.png"));
					// $protect>>$
				}
				{
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription = new Label(composite1, SWT.WRAP);
					lblDescription.setLayoutData(lblDescriptionLData);
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout layout2 = new GridLayout();
				layout2.makeColumnsEqualWidth = true;
				layout2.marginWidth = 10;
				GridData data2 = new GridData();
				data2.horizontalAlignment = GridData.FILL;
				data2.grabExcessHorizontalSpace = true;
				data2.verticalAlignment = GridData.FILL;
				data2.grabExcessVerticalSpace = true;
				composite2.setLayoutData(data2);
				composite2.setLayout(layout2);
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite2, SWT.BORDER | SWT.MULTI
							| SWT.FULL_SELECTION | SWT.VIRTUAL);
					viewer.getControl().setLayoutData(viewerLData);
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.heightHint = 100;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblError = new Label(composite3, SWT.WRAP);
					GridData lblErrorLData = new GridData();
					lblErrorLData.horizontalAlignment = GridData.FILL;
					lblErrorLData.grabExcessHorizontalSpace = true;
					lblErrorLData.verticalAlignment = GridData.FILL;
					lblErrorLData.grabExcessVerticalSpace = true;
					lblError.setLayoutData(lblErrorLData);
					// $protect>>$
					lblError.setForeground(lblError.getDisplay()
							.getSystemColor(SWT.COLOR_RED));
					// $protect<<$
				}
			}
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.marginWidth = 10;
				composite4Layout.marginHeight = 10;
				composite4Layout.numColumns = 2;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					btnOk = new Button(composite4, SWT.PUSH | SWT.CENTER);
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
			dialogShell.setText(GUIMessages.getMessage("comp.action_selector.title"));
			lblDescription.setText(GUIMessages.getMessage("comp.action_selector.descr"));
			dialogShell.setMinimumSize(550, 500);
			dialogShell.pack();
			//dialogShell.setSize(500, 500);
			GUIUtils.centerShell(dialogShell);
			initData();
			// $protect>>$

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

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	@SuppressWarnings("unchecked")
	public void initData() {
		if (role == null || role.getId() == ActionConstants.ROLE_ADMIN) {
			return;
		}
		List actions = new ArrayList(ActionConstants.ALL_ACTIONS);
		List forRemove = new ArrayList();
		if (role.getActions() != null) {
			forRemove.addAll(role.getActions());
		}
		actions.removeAll(forRemove);
		viewer.setItemCount(actions.size());
		viewer.setContentProvider(new PermissionPane.PermissionContentProvider(
				actions));
		viewer.setInput(this);
		viewer.setLabelProvider(new PermissionPane.PermissionLabelProvider());
	}

	@SuppressWarnings("unchecked")
	private void onOk() {
		int indecies[] = viewer.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			lblError.setText("select actions");
			return;
		}
		if (role == null) {
			lblError.setText("empty role");
			return;
		}
		PermissionPane.PermissionContentProvider provider = (PermissionPane.PermissionContentProvider) viewer
				.getContentProvider();
		List requests = new ArrayList();
		List actions = new ArrayList();
		for (int i = 0; i < indecies.length; i++) {
			Integer actionId = (Integer) provider.actions.get(indecies[i]);
			PermissionInsertRequest request = new PermissionInsertRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setAction(actionId.intValue());
			request.setRole(role);
			requests.add(request);
			actions.add(actionId);
		}
		try {
			DefaultRecutilClient.processRequests(requests);
			for (int i = 0; i < actions.size(); i++) {
				Integer actionId = (Integer) actions.get(i);
				role.addAction(actionId.intValue());
			}
			approved = true;
			dialogShell.dispose();
		} catch (Throwable t) {
			lblError.setText(t.toString());
			approved = false;
		}
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

}
