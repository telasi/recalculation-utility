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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Role;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class RoleGeneralPane extends Composite {

	private Composite composite1;
	private Label lblName;
	private Text txtName;
	private Text txtDescription;
	private Composite composite3;
	private Label lblSeparator1;
	private Label lblSubtitle1;
	private Text txtID;
	private Label lblID;
	private Label label2;
	private Label lblGroupImage;
	private Composite composite6;
	private Label lblSeparator2;
	private Label lblSeparator0;
	private Label lblTitle;
	private Composite composite5;
	private Label lblSubtitle2;
	private Composite composite4;
	private Composite composite2;
	private Button chkEnabled;
	private Label label1;
	private Label lblDescription;

	public RoleGeneralPane(Composite parent, int style) {
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
			this.setSize(450, 400);
			{
				composite5 = new Composite(this, SWT.NONE);
				GridLayout composite5Layout = new GridLayout();
				composite5Layout.makeColumnsEqualWidth = true;
				composite5Layout.marginTop = 10;
				GridData composite5LData = new GridData();
				composite5LData.horizontalAlignment = GridData.FILL;
				composite5LData.grabExcessHorizontalSpace = true;
				composite5.setLayoutData(composite5LData);
				composite5.setLayout(composite5Layout);
				{
					lblTitle = new Label(composite5, SWT.NONE);
					lblTitle.setText(GUIMessages
							.getMessage("comp.rolegenprop.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
				}
			}
			{
				GridData lblSeparator0LData = new GridData();
				lblSeparator0LData.horizontalAlignment = GridData.FILL;
				lblSeparator0LData.grabExcessHorizontalSpace = true;
				lblSeparator0 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator0.setLayoutData(lblSeparator0LData);
			}
			{
				composite6 = new Composite(this, SWT.NONE);
				GridLayout composite6Layout = new GridLayout();
				composite6Layout.numColumns = 2;
				GridData composite6LData = new GridData();
				composite6LData.horizontalAlignment = GridData.FILL;
				composite6LData.grabExcessHorizontalSpace = true;
				composite6.setLayoutData(composite6LData);
				composite6.setLayout(composite6Layout);
				{
					GridData lblGroupImageLData = new GridData();
					lblGroupImageLData.widthHint = 48;
					lblGroupImageLData.heightHint = 48;
					lblGroupImage = new Label(composite6, SWT.NONE);
					lblGroupImage.setLayoutData(lblGroupImageLData);
					lblGroupImage.setImage(Plugin
							.getImage("icons/48x48/group.png"));
				}
				{
					label2 = new Label(composite6, SWT.WRAP);
					GridData label2LData = new GridData();
					label2LData.verticalAlignment = GridData.FILL;
					label2LData.grabExcessVerticalSpace = true;
					label2LData.horizontalAlignment = GridData.FILL;
					label2LData.grabExcessHorizontalSpace = true;
					label2.setLayoutData(label2LData);
					label2.setText(GUIMessages
							.getMessage("comp.rolegenprop.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblSubtitle1 = new Label(composite3, SWT.NONE);
					GridData lblSubtitle1LData = new GridData();
					lblSubtitle1LData.horizontalAlignment = GridData.FILL;
					lblSubtitle1LData.grabExcessHorizontalSpace = true;
					lblSubtitle1.setLayoutData(lblSubtitle1LData);
					lblSubtitle1.setText(GUIMessages
							.getMessage("comp.genera.main_properties"));
					lblSubtitle1.setFont(GUIUtils
							.createSubtitleFont(lblSubtitle1.getFont()));
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
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1Layout.marginBottom = 10;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblName = new Label(composite1, SWT.NONE);
					lblName
							.setText(GUIMessages
									.getMessage("comp.general.name"));
				}
				{
					GridData txtNameLData = new GridData();
					txtNameLData.horizontalAlignment = GridData.FILL;
					txtNameLData.grabExcessHorizontalSpace = true;
					txtName = new Text(composite1, SWT.BORDER);
					txtName.setLayoutData(txtNameLData);
				}
				{
					lblDescription = new Label(composite1, SWT.NONE);
					lblDescription.setText(GUIMessages
							.getMessage("comp.general.description"));
				}
				{
					GridData txtDescriptionLData = new GridData();
					txtDescriptionLData.horizontalAlignment = GridData.FILL;
					txtDescriptionLData.grabExcessHorizontalSpace = true;
					txtDescription = new Text(composite1, SWT.BORDER);
					txtDescription.setLayoutData(txtDescriptionLData);
				}
				{
					label1 = new Label(composite1, SWT.NONE);
					label1.setText("");
				}
				{
					chkEnabled = new Button(composite1, SWT.CHECK | SWT.LEFT);
					chkEnabled.setText(GUIMessages
							.getMessage("comp.general.enabled"));
				}
			}
			{
				composite4 = new Composite(this, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.makeColumnsEqualWidth = true;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					lblSubtitle2 = new Label(composite4, SWT.NONE);
					lblSubtitle2.setText(GUIMessages
							.getMessage("comp.genera.additional_properties"));
					lblSubtitle2.setFont(GUIUtils
							.createSubtitleFont(lblSubtitle2.getFont()));
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
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					lblID = new Label(composite2, SWT.NONE);
					lblID.setText(GUIMessages.getMessage("comp.general.db_id"));
				}
				{
					GridData txtIDLData = new GridData();
					txtIDLData.horizontalAlignment = GridData.FILL;
					txtIDLData.grabExcessHorizontalSpace = true;
					txtID = new Text(composite2, SWT.READ_ONLY | SWT.BORDER);
					txtID.setLayoutData(txtIDLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRoleName() {
		return GUITranslator.KA_TO_GEO_ASCII(txtName.getText());
	}

	public String getRoleDescription() {
		return GUITranslator.KA_TO_GEO_ASCII(txtDescription.getText());
	}

	public boolean isRoleEnabled() {
		return chkEnabled.getSelection();
	}

	public void displayRole(Role role) {
		if (role == null) {
			txtID.setText("");
			txtName.setText("");
			txtDescription.setText("");
			chkEnabled.setSelection(true);
		} else {
			txtID.setText(String.valueOf(role.getId()));
			txtName.setText(GUITranslator.GEO_ASCII_TO_KA(role.getName()));
			txtDescription.setText(GUITranslator.GEO_ASCII_TO_KA(role
					.getDescription()));
			chkEnabled.setSelection(role.isEnabled());
		}
	}

}
