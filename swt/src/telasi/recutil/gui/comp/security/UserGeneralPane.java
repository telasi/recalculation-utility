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
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Role;
import telasi.recutil.beans.User;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class UserGeneralPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblMainProperties;
	private Label lblSeparator2;
	private Label lblPasswordDescription;
	private Text txtPassword2;
	private Label lblPassword2;
	private Text txtPassword1;
	private Label lblUserName;
	private Label lblId;
	private RoleSelector pkRole;
	private Text txtId;
	private Composite composite9;
	private Label lblSeparator4;
	private Label lblAdditionalProperties;
	private Composite composite8;
	private Button chkEnabled;
	private Label label1;
	private Label lblRole;
	private Text txtFullName;
	private Label lblFullName;
	private Label lblSeparator3;
	private Label lblPasswordSection;
	private Composite composite7;
	private Composite composite6;
	private Composite composite5;
	private Text txtUserName;
	private Label lblPassword1;
	private Composite composite4;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;
	private Text txtSequence;
	private Text txtNumber;
	private Composite composite11;
	private Label lblNumber;
	private Button btnClearAdvisor;
	private Composite composite10;
	private UserPicker pkAdvisor;
	private Label lblAdvisor;
	private User user;

	public UserGeneralPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(684, 489);
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
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setText(GUIMessages
							.getMessage("comp.usergeneralpane.title"));
					// $protect>>$
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
					// $protect<<$
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
					lblImageLData.heightHint = 48;
					lblImageLData.widthHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					// $protect>>$
					lblImage.setImage(Plugin.getImage("icons/48x48/user.png"));
					// $protect<<$
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages
							.getMessage("comp.usergeneralpane.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				composite3Layout.marginTop = 10;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblMainProperties = new Label(composite3, SWT.NONE);
					lblMainProperties.setText(GUIMessages
							.getMessage("comp.genera.main_properties"));
					lblMainProperties.setFont(GUIUtils
							.createSubtitleFont(lblMainProperties.getFont()));
				}
			}
			{
				GridData lblSeparator2LData = new GridData();
				lblSeparator2LData.horizontalAlignment = GridData.FILL;
				lblSeparator2LData.grabExcessHorizontalSpace = true;
				lblSeparator2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator2.setLayoutData(lblSeparator2LData);
			}
			{
				composite4 = new Composite(this, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					lblUserName = new Label(composite4, SWT.NONE);
					lblUserName.setText(GUIMessages
							.getMessage("comp.general.username"));
				}
				{
					GridData txtUserNameLData = new GridData();
					txtUserNameLData.horizontalAlignment = GridData.FILL;
					txtUserNameLData.grabExcessHorizontalSpace = true;
					txtUserName = new Text(composite4, SWT.BORDER);
					txtUserName.setLayoutData(txtUserNameLData);
				}
				{
					lblFullName = new Label(composite4, SWT.NONE);
					lblFullName.setText(GUIMessages
							.getMessage("comp.general.fullname"));
				}
				{
					GridData txtFullNameLData = new GridData();
					txtFullNameLData.horizontalAlignment = GridData.FILL;
					txtFullNameLData.grabExcessHorizontalSpace = true;
					txtFullName = new Text(composite4, SWT.BORDER);
					txtFullName.setLayoutData(txtFullNameLData);
				}
				{
					lblNumber = new Label(composite4, SWT.NONE);
					lblNumber.setText(GUIMessages
						.getMessage("comp.usergeneralpane.number_sequence"));
				}
				{
					composite11 = new Composite(composite4, SWT.NONE);
					GridLayout composite11Layout = new GridLayout();
					composite11Layout.marginHeight = 0;
					composite11Layout.marginWidth = 0;
					composite11Layout.makeColumnsEqualWidth = true;
					composite11Layout.numColumns = 2;
					GridData composite11LData = new GridData();
					composite11LData.horizontalAlignment = GridData.FILL;
					composite11LData.grabExcessHorizontalSpace = true;
					composite11.setLayoutData(composite11LData);
					composite11.setLayout(composite11Layout);
					{
						GridData txtNumberLData = new GridData();
						txtNumberLData.horizontalAlignment = GridData.FILL;
						txtNumberLData.grabExcessHorizontalSpace = true;
						txtNumber = new Text(composite11, SWT.BORDER);
						txtNumber.setLayoutData(txtNumberLData);
					}
					{
						GridData txtSequenceLData = new GridData();
						txtSequenceLData.horizontalAlignment = GridData.FILL;
						txtSequenceLData.grabExcessHorizontalSpace = true;
						txtSequence = new Text(composite11, SWT.READ_ONLY
							| SWT.BORDER);
						txtSequence.setLayoutData(txtSequenceLData);
					}
				}
				{
					lblRole = new Label(composite4, SWT.NONE);
					lblRole.setText(GUIMessages.getMessage("comp.usergeneralpane.role"));
				}
				{
					GridData pkRoleLData = new GridData();
					pkRoleLData.horizontalAlignment = GridData.FILL;
					pkRoleLData.grabExcessHorizontalSpace = true;
					pkRole = new RoleSelector(composite4, SWT.BORDER);
					pkRole.setLayoutData(pkRoleLData);
				}
				{
					lblAdvisor = new Label(composite4, SWT.NONE);
					lblAdvisor.setText(GUIMessages
							.getMessage("comp.general.advisor"));
				}
				{
					composite10 = new Composite(composite4, SWT.NONE);
					GridLayout composite10Layout = new GridLayout();
					composite10Layout.marginHeight = 0;
					composite10Layout.numColumns = 2;
					composite10Layout.marginWidth = 0;
					GridData composite10LData = new GridData();
					composite10LData.horizontalAlignment = GridData.FILL;
					composite10LData.grabExcessHorizontalSpace = true;
					composite10.setLayoutData(composite10LData);
					composite10.setLayout(composite10Layout);
					{
						GridData pkAdvisorLData = new GridData();
						pkAdvisorLData.horizontalAlignment = GridData.FILL;
						pkAdvisorLData.grabExcessHorizontalSpace = true;
						pkAdvisor = new UserPicker(composite10, SWT.BORDER);
						pkAdvisor.setLayoutData(pkAdvisorLData);
					}
					{
						btnClearAdvisor = new Button(composite10, SWT.PUSH
								| SWT.CENTER);
						btnClearAdvisor.setText(GUIMessages
								.getMessage("comp.general.clear"));
						btnClearAdvisor
								.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent e) {
										pkAdvisor.setUser(null);
									}
								});
					}
				}
				{
					label1 = new Label(composite4, SWT.NONE);
					label1.setText("");
				}
				{
					chkEnabled = new Button(composite4, SWT.CHECK | SWT.LEFT);
					chkEnabled.setText(GUIMessages
							.getMessage("comp.general.enabled"));
				}
			}
			{
				composite7 = new Composite(this, SWT.NONE);
				GridLayout composite7Layout = new GridLayout();
				composite7Layout.makeColumnsEqualWidth = true;
				composite7Layout.marginTop = 10;
				GridData composite7LData = new GridData();
				composite7LData.horizontalAlignment = GridData.FILL;
				composite7LData.grabExcessHorizontalSpace = true;
				composite7.setLayoutData(composite7LData);
				composite7.setLayout(composite7Layout);
				{
					lblPasswordSection = new Label(composite7, SWT.NONE);
					lblPasswordSection.setText(GUIMessages
							.getMessage("comp.usergeneralpane.password"));
					lblPasswordSection.setFont(GUIUtils
							.createSubtitleFont(lblPasswordSection.getFont()));
				}
			}
			{
				GridData lblSeparator3LData = new GridData();
				lblSeparator3LData.horizontalAlignment = GridData.FILL;
				lblSeparator3LData.grabExcessHorizontalSpace = true;
				lblSeparator3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator3.setLayoutData(lblSeparator3LData);
			}
			{
				composite6 = new Composite(this, SWT.NONE);
				GridLayout composite6Layout = new GridLayout();
				GridData composite6LData = new GridData();
				composite6LData.horizontalAlignment = GridData.FILL;
				composite6LData.grabExcessHorizontalSpace = true;
				composite6.setLayoutData(composite6LData);
				composite6.setLayout(composite6Layout);
				{
					lblPasswordDescription = new Label(composite6, SWT.WRAP);
					GridData lblPasswordDescriptionLData = new GridData();
					lblPasswordDescriptionLData.horizontalAlignment = GridData.FILL;
					lblPasswordDescriptionLData.grabExcessHorizontalSpace = true;
					lblPasswordDescriptionLData.verticalAlignment = GridData.FILL;
					lblPasswordDescriptionLData.grabExcessVerticalSpace = true;
					lblPasswordDescription
							.setLayoutData(lblPasswordDescriptionLData);
					lblPasswordDescription
							.setText(GUIMessages
									.getMessage("comp.usergeneralpane.password_description"));
				}
			}
			{
				composite5 = new Composite(this, SWT.NONE);
				GridLayout composite5Layout = new GridLayout();
				composite5Layout.numColumns = 2;
				GridData composite5LData = new GridData();
				composite5LData.horizontalAlignment = GridData.FILL;
				composite5LData.grabExcessHorizontalSpace = true;
				composite5.setLayoutData(composite5LData);
				composite5.setLayout(composite5Layout);
				{
					lblPassword1 = new Label(composite5, SWT.NONE);
					lblPassword1.setText(GUIMessages
							.getMessage("comp.usergeneralpane.password"));
				}
				{
					GridData txtPassword1LData = new GridData();
					txtPassword1LData.horizontalAlignment = GridData.FILL;
					txtPassword1LData.grabExcessHorizontalSpace = true;
					txtPassword1 = new Text(composite5, SWT.BORDER);
					txtPassword1.setLayoutData(txtPassword1LData);
					txtPassword1.setEchoChar('*');
				}
				{
					lblPassword2 = new Label(composite5, SWT.NONE);
					lblPassword2
							.setText(GUIMessages
									.getMessage("comp.usergeneralpane.confirm_password"));
				}
				{
					GridData txtPassword2LData = new GridData();
					txtPassword2LData.horizontalAlignment = GridData.FILL;
					txtPassword2LData.grabExcessHorizontalSpace = true;
					txtPassword2 = new Text(composite5, SWT.BORDER);
					txtPassword2.setLayoutData(txtPassword2LData);
					txtPassword2.setEchoChar('*');
				}
			}
			{
				composite8 = new Composite(this, SWT.NONE);
				GridLayout composite8Layout = new GridLayout();
				composite8Layout.marginTop = 10;
				GridData composite8LData = new GridData();
				composite8LData.horizontalAlignment = GridData.FILL;
				composite8LData.grabExcessHorizontalSpace = true;
				composite8.setLayoutData(composite8LData);
				composite8.setLayout(composite8Layout);
				{
					lblAdditionalProperties = new Label(composite8, SWT.NONE);
					lblAdditionalProperties.setText(GUIMessages
							.getMessage("comp.genera.additional_properties"));
					lblAdditionalProperties.setFont(GUIUtils
							.createSubtitleFont(lblAdditionalProperties
									.getFont()));
				}
			}
			{
				GridData lblSeparator4LData = new GridData();
				lblSeparator4LData.horizontalAlignment = GridData.FILL;
				lblSeparator4LData.grabExcessHorizontalSpace = true;
				lblSeparator4 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator4.setLayoutData(lblSeparator4LData);
			}
			{
				composite9 = new Composite(this, SWT.NONE);
				GridLayout composite9Layout = new GridLayout();
				composite9Layout.numColumns = 2;
				GridData composite9LData = new GridData();
				composite9LData.horizontalAlignment = GridData.FILL;
				composite9LData.grabExcessHorizontalSpace = true;
				composite9.setLayoutData(composite9LData);
				composite9.setLayout(composite9Layout);
				{
					lblId = new Label(composite9, SWT.NONE);
					lblId.setText(GUIMessages.getMessage("comp.general.db_id"));
				}
				{
					GridData txtIdLData = new GridData();
					txtIdLData.horizontalAlignment = GridData.FILL;
					txtIdLData.grabExcessHorizontalSpace = true;
					txtId = new Text(composite9, SWT.READ_ONLY | SWT.BORDER);
					txtId.setLayoutData(txtIdLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
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
		if (user == null) {
			txtUserName.setText("");
			txtPassword1.setText("");
			txtPassword2.setText("");
			txtFullName.setText("");
			pkRole.setRole(null);
			chkEnabled.setSelection(true);
			txtId.setText("");
			pkAdvisor.setUser(null);
			txtNumber.setText("");
			txtSequence.setText("");
		} else {
			txtUserName.setText(user.getUserName());
			txtPassword1.setText("");
			txtPassword2.setText("");
			txtFullName.setText(GUITranslator.GEO_ASCII_TO_KA(user.getFullName()));
			pkRole.setRole(user.getRole());
			chkEnabled.setSelection(user.isEnabled());
			txtId.setText(String.valueOf(user.getId()));
			pkAdvisor.setUser(user.getAdvisor());
			txtNumber.setText(user.getNumber() == null ? "" : user.getNumber());
			txtSequence.setText(String.valueOf(user.getSequence()));
		}
	}

	public String getUserName() {
		return txtUserName.getText();
	}

	public String getPassword1() {
		return txtPassword1.getText();
	}

	public String getPassword2() {
		return txtPassword2.getText();
	}

	public String getFullName() {
		return GUITranslator.KA_TO_GEO_ASCII(txtFullName.getText());
	}

	public Role getRole() {
		return pkRole.getRole();
	}

	public boolean isUserEnabled() {
		return chkEnabled.getSelection();
	}

	public User getAdvisor() {
		return pkAdvisor.getUser();
	}
	
	public String getNumber() {
		return txtNumber.getText();
	}

	public void setForNewUser(boolean isNewUser) {
		txtUserName.setEditable(isNewUser);
		composite8.setVisible(!isNewUser);
		lblSeparator4.setVisible(!isNewUser);
		composite9.setVisible(!isNewUser);
		if (isNewUser) {
			lblPasswordDescription.setText(GUIMessages.getMessage("comp.usergeneralpane.password_description_new"));
		}
	}
}
