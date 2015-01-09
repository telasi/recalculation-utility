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
package telasi.recutil.gui.comp.recalc.savetab;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.User;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.security.UserPicker;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.recalc.RecalcUpdateRequest;

public class SavePane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Label label1;
	private Label lblDescription;
	private Text txtDescription;
	private Composite composite4;
	private Label lblDescriptionField;
	private Button button2;
	private Composite composite5;
	private Button button1;
	private UserPicker pkAdvisor;
	private Label lblAdvisor;
	private Composite composite3;
	private Label lblImage;
	private Composite composite2;
	private Recalc recalc;

	public SavePane(Composite parent, int style) {
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
			this.setSize(498, 335);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblTitle = new Label(composite1, SWT.NONE);
					lblTitle.setText(GUIMessages
							.getMessage("comp.savepane.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
				}
			}
			{
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.grabExcessHorizontalSpace = true;
				label1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				label1.setLayoutData(label1LData);
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
					lblImageLData.heightHint = 32;
					lblImageLData.widthHint = 32;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/32x32/save.png"));
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
							.getMessage("comp.savepane.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.BEGINNING;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblAdvisor = new Label(composite3, SWT.NONE);
					lblAdvisor.setText(GUIMessages
							.getMessage("comp.savepane.advisor"));
				}
				{
					composite5 = new Composite(composite3, SWT.NONE);
					GridLayout composite5Layout = new GridLayout();
					composite5Layout.marginHeight = 0;
					composite5Layout.marginWidth = 0;
					composite5Layout.numColumns = 2;
					GridData composite5LData = new GridData();
					composite5LData.grabExcessHorizontalSpace = true;
					composite5LData.horizontalAlignment = GridData.FILL;
					composite5.setLayoutData(composite5LData);
					composite5.setLayout(composite5Layout);
					{
						GridData userPicker1LData = new GridData();
						userPicker1LData.horizontalAlignment = GridData.FILL;
						userPicker1LData.grabExcessHorizontalSpace = true;
						pkAdvisor = new UserPicker(composite5, SWT.BORDER);
						pkAdvisor.setLayoutData(userPicker1LData);
						((org.eclipse.swt.layout.GridLayout) pkAdvisor
								.getLayout()).numColumns = 2;
						((org.eclipse.swt.layout.GridLayout) pkAdvisor
								.getLayout()).marginWidth = 0;
						((org.eclipse.swt.layout.GridLayout) pkAdvisor
								.getLayout()).marginHeight = 0;
						if (Application.USER != null) {
							pkAdvisor.setDefaultUser(Application.USER
									.getAdvisor());
						}
					}
					{
						button2 = new Button(composite5, SWT.PUSH | SWT.CENTER);
						button2.setText(GUIMessages
								.getMessage("comp.general.clear"));
						button2.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent arg0) {
								pkAdvisor.setUser(null);
							}
						});
					}
				}
				{
					lblDescriptionField = new Label(composite3, SWT.NONE);
					GridData lblDescriptionFieldLData = new GridData();
					lblDescriptionFieldLData.verticalAlignment = GridData.BEGINNING;
					lblDescriptionField.setLayoutData(lblDescriptionFieldLData);
					lblDescriptionField.setText(GUIMessages
							.getMessage("comp.savepane.description_field"));
				}
				{
					GridData txtDescriptionLData = new GridData();
					txtDescriptionLData.horizontalAlignment = GridData.FILL;
					txtDescriptionLData.heightHint = 150;
					txtDescriptionLData.grabExcessHorizontalSpace = true;
					txtDescription = new Text(composite3, SWT.MULTI | SWT.WRAP
							| SWT.BORDER | SWT.V_SCROLL);
					txtDescription.setLayoutData(txtDescriptionLData);
				}
			}
			{
				composite4 = new Composite(this, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4LData.verticalAlignment = GridData.FILL;
				composite4LData.grabExcessVerticalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					button1 = new Button(composite4, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1LData.grabExcessHorizontalSpace = true;
					button1LData.horizontalAlignment = GridData.END;
					button1.setLayoutData(button1LData);
					button1.setText(GUIMessages
							.getMessage("comp.general.apply"));
					button1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							onApply();
						}
					});
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public User getAdvisor() {
		return pkAdvisor.getUser();
	}

	public String getDescription() {
		return GUITranslator.KA_TO_GEO_ASCII(txtDescription.getText());
	}

	private void reset() {
		if (recalc == null) {
			pkAdvisor.setUser(null);
			txtDescription.setText("");
		} else {
			pkAdvisor.setUser(recalc.getAdvisor());
			txtDescription.setText(recalc.getDescription() == null ? ""
					: GUITranslator.GEO_ASCII_TO_KA(recalc.getDescription()));
		}
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
		reset();
	}

	private void onApply() {
		if (recalc == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		String dscrText = txtDescription.getText();
		String dscr = dscrText == null || dscrText.trim().length() == 0 ? null
				: GUITranslator.KA_TO_GEO_ASCII(dscrText);
		User advisor = pkAdvisor.getUser();
		Recalc newRecalc = new Recalc();
		newRecalc.setId(recalc.getId());
		newRecalc.setNumber(recalc.getNumber());
		newRecalc.setDescription(dscr);
		newRecalc.setAdvisor(advisor);
		try {
			RecalcUpdateRequest request = new RecalcUpdateRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRecalculation(newRecalc);
			DefaultRecutilClient.processRequest(request);

			if (recalc instanceof Recalc) {
				Recalc r = (Recalc) recalc;
				r.setDescription(dscr);
				r.setAdvisor(advisor);
			}

		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}

	}

}
