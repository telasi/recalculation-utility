package telasi.recutil.gui.comp.tpowner;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.tpowner.TpOwnerAccount;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.tpowner.TpOwnerAccountSelectRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerSaveRequest;

public class TpOwnerPane extends Composite {
	private Account account;
	private TpOwnerAccount tpOwner;
	private Label label1;
	private Button btnSaveType;
	private Button btnActive;
	private TransTypePicker transTypePicker;
	private Composite composite3;
	private Composite view1_3;
	private Composite composite6;
	private Label label3;
	private Button btnAddTransType;
	private Composite view1_2;
	private Label label2;
	private Composite view1_1;
	private Composite viewstack1;
	private Label lblTransType;
	private Label lblTransTypeImage;
	private Composite composite2;
	private Composite composite1;

	public TpOwnerPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginHeight = 0;
			thisLayout.verticalSpacing = 0;
			thisLayout.marginWidth = 0;
			this.setLayout(thisLayout);
			this.setSize(452, 280);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.marginWidth = 0;
				composite1Layout.verticalSpacing = 0;
				composite1Layout.horizontalSpacing = 0;
				composite1Layout.marginHeight = 0;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.numColumns = 2;
					GridData composite2LData = new GridData();
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					{
						GridData lblTransTypeImageLData = new GridData();
						lblTransTypeImageLData.heightHint = 22;
						lblTransTypeImageLData.widthHint = 22;
						lblTransTypeImage = new Label(composite2, SWT.NONE);
						lblTransTypeImage.setLayoutData(lblTransTypeImageLData);
						lblTransTypeImage.setImage(Plugin.getImage("icons/22x22/power.png"));
					}
					{
						lblTransType = new Label(composite2, SWT.NONE);
						lblTransType.setText(GUIMessages.getMessage("comp.tpowner.transformator"));
						lblTransType.setFont(GUIUtils.createSubtitleFont(lblTransType.getFont()));
					}
				}
				{
					viewstack1 = new Composite(composite1, SWT.NONE);
					StackLayout composite3Layout = new StackLayout();
					GridData composite3LData = new GridData();
					composite3LData.horizontalAlignment = GridData.FILL;
					composite3LData.grabExcessHorizontalSpace = true;
					composite3LData.heightHint = 100;
					viewstack1.setLayoutData(composite3LData);
					viewstack1.setLayout(composite3Layout);
					{
						view1_1 = new Composite(viewstack1, SWT.NONE);
						composite3Layout.topControl = view1_1;
						GridLayout composite4Layout = new GridLayout();
						view1_1.setLayout(composite4Layout);
						{
							label2 = new Label(view1_1, SWT.WRAP);
							GridData label2LData = new GridData();
							label2LData.horizontalAlignment = GridData.CENTER;
							label2LData.grabExcessHorizontalSpace = true;
							label2LData.grabExcessVerticalSpace = true;
							label2.setLayoutData(label2LData);
							label2.setText(GUIMessages.getMessage("comp.tpowner.select_account_from_a_list"));
						}
					}
					{
						view1_2 = new Composite(viewstack1, SWT.NONE);
						GridLayout composite5Layout = new GridLayout();
						composite5Layout.makeColumnsEqualWidth = true;
						view1_2.setLayout(composite5Layout);
						{
							composite6 = new Composite(view1_2, SWT.NONE);
							GridLayout composite6Layout = new GridLayout();
							composite6Layout.makeColumnsEqualWidth = true;
							GridData composite6LData = new GridData();
							composite6LData.grabExcessHorizontalSpace = true;
							composite6LData.horizontalAlignment = GridData.CENTER;
							composite6LData.grabExcessVerticalSpace = true;
							composite6.setLayoutData(composite6LData);
							composite6.setLayout(composite6Layout);
							{
								label3 = new Label(composite6, SWT.NONE);
								GridData label3LData = new GridData();
								label3.setLayoutData(label3LData);
								label3.setText(GUIMessages.getMessage("comp.tpowner.no_transformator"));
							}
							{
								btnAddTransType = new Button(composite6, SWT.PUSH | SWT.CENTER);
								GridData btnAddTransTypeLData = new GridData();
								btnAddTransTypeLData.grabExcessHorizontalSpace = true;
								btnAddTransTypeLData.horizontalAlignment = GridData.CENTER;
								btnAddTransType.setLayoutData(btnAddTransTypeLData);
								btnAddTransType.setText(GUIMessages.getMessage("comp.tpowner.addtype"));
								btnAddTransType.setImage(Plugin.getImage("icons/22x22/add.png"));
								btnAddTransType.addSelectionListener(new SelectionAdapter() {
									@Override
									public void widgetSelected(SelectionEvent e) {
										if (account != null) {
											StackLayout layout = (StackLayout) viewstack1.getLayout();
											layout.topControl = view1_3;
											viewstack1.layout();
										}
									}
								});
							}
						}
					}
					{
						view1_3 = new Composite(viewstack1, SWT.NONE);
						GridLayout composite7Layout = new GridLayout();
						composite7Layout.makeColumnsEqualWidth = true;
						view1_3.setLayout(composite7Layout);
						{
							composite3 = new Composite(view1_3, SWT.NONE);
							GridLayout composite4Layout = new GridLayout();
							composite4Layout.numColumns = 2;
							GridData composite4LData = new GridData();
							composite4LData.grabExcessHorizontalSpace = true;
							composite4LData.horizontalAlignment = GridData.CENTER;
							composite4LData.grabExcessVerticalSpace = true;
							composite3.setLayoutData(composite4LData);
							composite3.setLayout(composite4Layout);
							{
								GridData transTypePickerLData = new GridData();
								transTypePickerLData.widthHint = 150;
								transTypePicker = new TransTypePicker(composite3, SWT.BORDER);
								transTypePicker.setLayoutData(transTypePickerLData);
							}
							{
								btnActive = new Button(composite3, SWT.CHECK | SWT.LEFT);
								btnActive.setText(GUIMessages.getMessage("comp.tpowner.status"));
							}
						}
						{
							btnSaveType = new Button(view1_3, SWT.PUSH | SWT.CENTER);
							GridData btnSaveTypeLData = new GridData();
							btnSaveTypeLData.grabExcessHorizontalSpace = true;
							btnSaveTypeLData.horizontalAlignment = GridData.END;
							btnSaveType.setLayoutData(btnSaveTypeLData);
							btnSaveType.setText(GUIMessages.getMessage("comp.general.save"));
							btnSaveType.setImage(Plugin.getImage("icons/22x22/save.png"));
							btnSaveType.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									onSave();
								}
							});
						}
					}
				}
			}
			{
				label1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.grabExcessHorizontalSpace = true;
				label1.setLayoutData(label1LData);
				label1.setText("label1");
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account acc) {
		this.account = acc;
		resetAccount();
	}

	private void resetAccount() {
		StackLayout layout = (StackLayout) viewstack1.getLayout();
		tpOwner = null;
		transTypePicker.setTransformatorType(null);
		btnActive.setSelection(true);
		if (account == null) {
			layout.topControl = view1_1;
		} else {
			if (Application.validateConnection()) {
				try {
					TpOwnerAccountSelectRequest request = new TpOwnerAccountSelectRequest(Application.USER_NAME, Application.PASSWORD);
					request.setAccount(account);
					DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
					TpOwnerAccountSelectRequest callback = (TpOwnerAccountSelectRequest) resp.getRequest();
					tpOwner = callback.getTpowner();
					if (tpOwner == null) {
						layout.topControl = view1_2;
					} else {
						layout.topControl = view1_3;
						btnActive.setSelection(tpOwner.getStatusId() == 0);
						transTypePicker.setTransformatorType(tpOwner.getTransformatorType());
					}
				} catch (Exception ex) {
					MessageDialog.openError(getShell(), "Error", ex.toString());
					layout.topControl = view1_1;
					ex.printStackTrace();
				}
			} else {
				layout.topControl = view1_1;
			}
		}
		viewstack1.layout();
	}

	private void onSave() {
		if (account == null || transTypePicker.getTransformatorType() == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}

		TpOwnerSaveRequest request = new TpOwnerSaveRequest(Application.USER_NAME, Application.PASSWORD);
		request.setAccountId(account.getId());
		request.setTypeId(transTypePicker.getTransformatorType().getId());
		request.setStatusId(btnActive.getSelection() ? 0 : 1);

		try {
			DefaultRecutilClient.processRequest(request);
			if (tpOwner!=null) {
				tpOwner.setTransformatorType(transTypePicker.getTransformatorType());
				tpOwner.setStatusId(btnActive.getSelection() ? 0 : 1);
			}
		} catch(Exception ex) {
			MessageDialog.openError(getShell(), "Error", ex.toString());
			ex.printStackTrace();
		}
	}

}

