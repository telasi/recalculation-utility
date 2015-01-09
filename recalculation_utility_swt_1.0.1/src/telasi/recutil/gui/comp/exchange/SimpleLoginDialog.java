package telasi.recutil.gui.comp.exchange;

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

import telasi.recutil.gui.ejb.RecutilClient;
import telasi.recutil.gui.plugin.ConnectionDescriptor;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.security.ConnectRequest;

public class SimpleLoginDialog extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Text txtUserName;
	private Label lblError;
	private Label lblDescription;
	private Label label1;
	private Composite composite4;
	private Composite composite3;
	private Button btnClose;
	private Button btnOk;
	private Composite composite2;
	private Text txtPassword;
	private Label lblPassword;
	private Label lblUserName;
	private ConnectionDescriptor descriptor;
	private boolean approved = false;
	private String userName, password;

	public SimpleLoginDialog(Shell parent, int style) {
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
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				composite4.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				{
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescription = new Label(composite4, SWT.WRAP);
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(descriptor == null  ? "" : GUIMessages.getMessage("comp.exchange.logindialog.descr", new Object[] {descriptor}));
					lblDescription.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				}
			}
			{
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.grabExcessHorizontalSpace = true;
				label1 = new Label(dialogShell, SWT.SEPARATOR | SWT.HORIZONTAL);
				label1.setLayoutData(label1LData);
			}
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.BEGINNING;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblUserName = new Label(composite1, SWT.NONE);
					lblUserName.setText(GUIMessages.getMessage("comp.general.username"));
				}
				{
					GridData txtUserNameLData = new GridData();
					txtUserNameLData.horizontalAlignment = GridData.FILL;
					txtUserNameLData.grabExcessHorizontalSpace = true;
					txtUserName = new Text(composite1, SWT.BORDER);
					txtUserName.setLayoutData(txtUserNameLData);
				}
				{
					lblPassword = new Label(composite1, SWT.NONE);
					lblPassword.setText(GUIMessages.getMessage("comp.logindialog.password"));
				}
				{
					GridData txtPasswordLData = new GridData();
					txtPasswordLData.horizontalAlignment = GridData.FILL;
					txtPasswordLData.grabExcessHorizontalSpace = true;
					txtPassword = new Text(composite1, SWT.BORDER);
					txtPassword.setLayoutData(txtPasswordLData);
					txtPassword.setEchoChar('*');
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData lblErrorLData = new GridData();
					lblErrorLData.horizontalAlignment = GridData.FILL;
					lblErrorLData.grabExcessHorizontalSpace = true;
					lblErrorLData.verticalAlignment = GridData.FILL;
					lblErrorLData.grabExcessVerticalSpace = true;
					lblError = new Label(composite3, SWT.WRAP);
					lblError.setLayoutData(lblErrorLData);
					lblError.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				}
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
					btnOk.setText("OK");
					btnOk.getShell().setDefaultButton(btnOk);
					btnOk.addSelectionListener(new SelectionAdapter() {
						@Override
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
					btnClose.setText("Close");
					btnClose.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							approved = false;
							dialogShell.dispose();
						}
					});
				}
			}
			dialogShell.setText(GUIMessages.getMessage("comp.exchange.logindialog.title"));
			dialogShell.layout();
			dialogShell.pack();
			GUIUtils.centerShell(dialogShell);
			txtUserName.setFocus();
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

	private void onOk() {
		// validate input fields
		lblError.setText("");
		String userName = txtUserName.getText();
		String password = txtPassword.getText();
		if (userName == null || userName.trim().length() == 0) {
			lblError.setText("Define user name.");
			return;
		}
		if (password == null || password.length() == 0) {
			lblError.setText("Define password.");
			return;
		}

		// validate connection
		try {
			RecutilClient client = new RecutilClient(descriptor.getUrl());
			ConnectRequest request = new ConnectRequest(userName, password);
			client.processRequest(request);			
		} catch (Throwable t) {
			lblError.setText(t.toString());
			return;
		}

		// exit with OK
		this.userName = userName;
		this.password = password;
		approved = true;
		dialogShell.dispose();
	}

	public ConnectionDescriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(ConnectionDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public boolean isApproved() {
		return approved;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

}
