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

import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.security.MyUserPasswordUpdateRequest;

public class ChangePasswordDialog extends Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Label lblImage;
	private Label lblCurrentPassword;
	private Label lblNewPassword;
	private Button btnClose;
	private Button btnOk;
	private Composite composite4;
	private Label lblError;
	private Composite composite3;
	private Text txtNewPasswordRepeat;
	private Label lblNewPasswordRepeat;
	private Text txtNewPassword;
	private Text txtCurrent;
	private Composite composite2;
	private Label lblDescr;

	public ChangePasswordDialog(Shell parent, int style) {
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
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1Layout.marginLeft = 10;
				composite1Layout.marginRight = 10;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 48;
					lblImageLData.widthHint = 48;
					lblImage = new Label(composite1, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin
							.getImage("icons/48x48/permission.png"));
				}
				{
					GridData lblDescrLData = new GridData();
					lblDescrLData.horizontalAlignment = GridData.FILL;
					lblDescrLData.grabExcessHorizontalSpace = true;
					lblDescrLData.verticalAlignment = GridData.FILL;
					lblDescrLData.grabExcessVerticalSpace = true;
					lblDescr = new Label(composite1, SWT.WRAP);
					lblDescr.setLayoutData(lblDescrLData);
					lblDescr.setText(GUIMessages
							.getMessage("comp.changepassword.descr"));
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				composite2Layout.marginRight = 10;
				composite2Layout.marginLeft = 10;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					lblCurrentPassword = new Label(composite2, SWT.NONE);
					lblCurrentPassword.setText(GUIMessages
							.getMessage("comp.changepassword.currentpassword"));
				}
				{
					GridData txtCurrentLData = new GridData();
					txtCurrentLData.horizontalAlignment = GridData.FILL;
					txtCurrentLData.grabExcessHorizontalSpace = true;
					txtCurrent = new Text(composite2, SWT.BORDER);
					txtCurrent.setLayoutData(txtCurrentLData);
					txtCurrent.setEchoChar('*');
				}
				{
					lblNewPassword = new Label(composite2, SWT.NONE);
					lblNewPassword.setText(GUIMessages
							.getMessage("comp.changepassword.new_password"));
				}
				{
					GridData txtNewPasswordLData = new GridData();
					txtNewPasswordLData.horizontalAlignment = GridData.FILL;
					txtNewPasswordLData.grabExcessHorizontalSpace = true;
					txtNewPassword = new Text(composite2, SWT.BORDER);
					txtNewPassword.setLayoutData(txtNewPasswordLData);
					txtNewPassword.setEchoChar('*');
				}
				{
					lblNewPasswordRepeat = new Label(composite2, SWT.NONE);
					lblNewPasswordRepeat
							.setText(GUIMessages
									.getMessage("comp.changepassword.new_password_repeat"));
				}
				{
					GridData txtNewPasswordRepeatLData = new GridData();
					txtNewPasswordRepeatLData.horizontalAlignment = GridData.FILL;
					txtNewPasswordRepeatLData.grabExcessHorizontalSpace = true;
					txtNewPasswordRepeat = new Text(composite2, SWT.BORDER);
					txtNewPasswordRepeat
							.setLayoutData(txtNewPasswordRepeatLData);
					txtNewPasswordRepeat.setEchoChar('*');
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.marginLeft = 10;
				composite3Layout.marginRight = 10;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblError = new Label(composite3, SWT.WRAP);
					GridData lblErrorLData = new GridData();
					lblErrorLData.verticalAlignment = GridData.FILL;
					lblErrorLData.grabExcessVerticalSpace = true;
					lblErrorLData.horizontalAlignment = GridData.FILL;
					lblErrorLData.grabExcessHorizontalSpace = true;
					lblError.setLayoutData(lblErrorLData);
					lblError.setForeground(lblError.getDisplay()
							.getSystemColor(SWT.COLOR_RED));
				}
			}
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				composite4Layout.marginRight = 10;
				composite4Layout.marginLeft = 10;
				composite4Layout.marginBottom = 5;
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
					btnOk.setText("OK");
					btnOk.getShell().setDefaultButton(btnOk);
					btnOk.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							onOk();
						}
					});
				}
				{
					btnClose = new Button(composite4, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText("Close");
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							onClose();
						}
					});
				}
			}

			// $protect>>$
			dialogShell.setMinimumSize(550, 0);
			dialogShell.pack();
			//dialogShell.setSize(500, 350);
			GUIUtils.centerShell(dialogShell);
			dialogShell.setText(GUIMessages
					.getMessage("application.action.changepassword"));
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

	private void onOk() {
		lblError.setText("");
		String current = txtCurrent.getText();
		String newPassword = txtNewPassword.getText();
		String newPasswordRepeat = txtNewPasswordRepeat.getText();
		if (current == null || current.length() == 0) {
			lblError.setText("enter current password");
			txtCurrent.setFocus();
			return;
		}
		if (newPassword == null || newPassword.length() == 0) {
			lblError.setText("enter new password");
			txtNewPassword.setFocus();
			return;
		}
		if (newPasswordRepeat == null || newPasswordRepeat.length() == 0) {
			lblError.setText("confirm new password");
			txtNewPasswordRepeat.setFocus();
			return;
		}
		if (!newPassword.equals(newPasswordRepeat)) {
			lblError.setText("password doesnot match");
			txtNewPassword.selectAll();
			txtNewPassword.setFocus();
			return;
		}

		// TODO
		try {

			MyUserPasswordUpdateRequest request = new MyUserPasswordUpdateRequest(
					Application.USER_NAME, current);
			request.setPassword(newPassword);
			DefaultRecutilClient.processRequest(request);
			Application.PASSWORD = newPassword;
			dialogShell.dispose();

		} catch (Throwable t) {

			lblError.setText(t.toString());

		}

	}

	private void onClose() {
		dialogShell.dispose();
	}

}
