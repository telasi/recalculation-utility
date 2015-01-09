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
import org.eclipse.swt.widgets.Text;

import telasi.recutil.gui.plugin.ConnectionDescriptor;
import telasi.recutil.gui.plugin.OracleConnectionDescriptor;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class OracleConnectionEditor extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Composite composite2;
	private Label lblName;
	private Text txtName;
	private Label label1;
	private Label lblImage;
	private Text txtService;
	private Label lblService;
	private Label lblError;
	private Button btnClose;
	private Button btnOk;
	private Text txtPort;
	private Label lblPort;
	private Text txtHost;
	private Label lblHost;
	private boolean approved = false;
	private OracleConnectionDescriptor descriptor;

	public OracleConnectionEditor(Shell parent, int style) {
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
			dialogShell.setText(GUIMessages.getMessage("comp.url.properties.oracle.title"));
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblImage = new Label(composite1, SWT.NONE);
					lblImage.setImage(Plugin.getImage("icons/other/oracle.png"));
				}
				{
					label1 = new Label(composite1, SWT.NONE);
					label1.setText("");
				}
				{
					lblName = new Label(composite1, SWT.NONE);
					lblName.setText(GUIMessages.getMessage("comp.general.name"));
				}
				{
					GridData txtNameLData = new GridData();
					txtNameLData.horizontalAlignment = GridData.FILL;
					txtNameLData.grabExcessHorizontalSpace = true;
					txtName = new Text(composite1, SWT.BORDER);
					txtName.setLayoutData(txtNameLData);
				}
				{
					lblHost = new Label(composite1, SWT.NONE);
					lblHost.setText(GUIMessages.getMessage("comp.url.properties.host"));
				}
				{
					GridData txtHostLData = new GridData();
					txtHostLData.horizontalAlignment = GridData.FILL;
					txtHostLData.grabExcessHorizontalSpace = true;
					txtHost = new Text(composite1, SWT.BORDER);
					txtHost.setLayoutData(txtHostLData);
				}
				{
					lblPort = new Label(composite1, SWT.NONE);
					lblPort.setText(GUIMessages.getMessage("comp.url.properties.port"));
				}
				{
					GridData text1LData = new GridData();
					text1LData.horizontalAlignment = GridData.FILL;
					text1LData.grabExcessHorizontalSpace = true;
					txtPort = new Text(composite1, SWT.BORDER);
					txtPort.setLayoutData(text1LData);
				}
				{
					lblService = new Label(composite1, SWT.NONE);
					lblService.setText(GUIMessages.getMessage("comp.url.properties.serviceName"));
				}
				{
					GridData txtServiceLData = new GridData();
					txtServiceLData.horizontalAlignment = GridData.FILL;
					txtServiceLData.grabExcessHorizontalSpace = true;
					txtService = new Text(composite1, SWT.BORDER);
					txtService.setLayoutData(txtServiceLData);
				}
			}
			{
				GridData lblErrorLData = new GridData();
				lblErrorLData.horizontalAlignment = GridData.FILL;
				lblErrorLData.grabExcessHorizontalSpace = true;
				lblErrorLData.verticalAlignment = GridData.FILL;
				lblErrorLData.grabExcessVerticalSpace = true;
				lblError = new Label(dialogShell, SWT.WRAP);
				lblError.setLayoutData(lblErrorLData);
				lblError.setForeground(lblError.getDisplay().getSystemColor(SWT.COLOR_RED));
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
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
			// $protect>>$
			reset();
			//dialogShell.setSize(400, 300);
			dialogShell.setMinimumSize(350, 0);
			dialogShell.pack();
			GUIUtils.centerShell(dialogShell);
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

	private void reset() {
		if (descriptor == null) {
			descriptor = new OracleConnectionDescriptor();
		}
		String name = descriptor.getName();
		String host = descriptor.getHost();
		String port = descriptor.getPort();
		String service = descriptor.getServiceName();
		txtName.setText(name == null ? "" : name);
		txtHost.setText(host == null ? "" : host);
		txtPort.setText(port == null ? "" : port);
		txtService.setText(service == null ? "" : service);
	}

	public boolean isApproved() {
		return approved;
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	private void onOk() {
		String name = txtName.getText();
		String host = txtHost.getText();
		String port = txtPort.getText();
		String service = txtService.getText();

		lblError.setText("");
		if (name == null || name.trim().length() == 0) {
			lblError.setText("name not defined");
			txtName.setFocus();
			return;
		}
		if (host == null || host.trim().length() == 0) {
			lblError.setText("host not defined");
			txtHost.setFocus();
			return;
		}
		if (port == null || port.trim().length() == 0) {
			lblError.setText("port not defined");
			txtPort.setFocus();
			return;
		}
		if (service == null || service.trim().length() == 0) {
			lblError.setText("service not defined");
			txtService.setFocus();
			return;
		}

		descriptor = new OracleConnectionDescriptor();
		descriptor.setHost(host);
		descriptor.setName(name);
		descriptor.setPort(port);
		descriptor.setServiceName(service);

		approved = true;
		dialogShell.dispose();
	}

	public ConnectionDescriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(ConnectionDescriptor descriptor) {
		if (!(descriptor instanceof OracleConnectionDescriptor))
			throw new IllegalArgumentException();
		this.descriptor = (OracleConnectionDescriptor)descriptor;
	}

}
