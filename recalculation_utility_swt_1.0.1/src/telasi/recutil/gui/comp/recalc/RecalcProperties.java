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
package telasi.recutil.gui.comp.recalc;

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

import telasi.recutil.beans.Recalc;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.recalc.RecalcUpdateRequest;

public class RecalcProperties extends Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private RecalcGeneralPane recalculationGeneralPane1;
	private Composite composite2;
	private Button btnOk;
	private Button btnClose;
	private Composite composite3;
	private Label lblError;
	private boolean approved;
	private Recalc recalc;

	public RecalcProperties(Shell parent, int style) {
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
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData recalculationGeneralPane1LData = new GridData();
					recalculationGeneralPane1LData.horizontalAlignment = GridData.FILL;
					recalculationGeneralPane1LData.grabExcessHorizontalSpace = true;
					recalculationGeneralPane1LData.verticalAlignment = GridData.FILL;
					recalculationGeneralPane1LData.grabExcessVerticalSpace = true;
					recalculationGeneralPane1 = new RecalcGeneralPane(
							composite1, SWT.NONE);
					recalculationGeneralPane1
							.setLayoutData(recalculationGeneralPane1LData);
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
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
					GridData lblErrorLData = new GridData();
					lblErrorLData.horizontalAlignment = GridData.FILL;
					lblErrorLData.grabExcessHorizontalSpace = true;
					lblErrorLData.verticalAlignment = GridData.FILL;
					lblErrorLData.grabExcessVerticalSpace = true;
					lblError = new Label(composite2, SWT.WRAP);
					lblError.setLayoutData(lblErrorLData);
					lblError.setForeground(lblError.getDisplay()
							.getSystemColor(SWT.COLOR_RED));
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.marginWidth = 10;
				composite3Layout.marginHeight = 10;
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					btnOk = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.horizontalAlignment = GridData.END;
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.widthHint = 75;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText("OK");
					btnOk.getShell().setDefaultButton(btnOk);
					btnOk.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onOk();
						}
					});
				}
				{
					btnClose = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText("Close");
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onClose();
						}
					});
				}
			}

			// $protect>>$
			dialogShell.setText(GUIMessages.getMessage("comp.recalc_properties.title"));
			reset();
			dialogShell.setMinimumSize(500, 0);
			dialogShell.pack();
			dialogShell.setSize(500, dialogShell.getSize().y);
			//dialogShell.setSize(500, 600);
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

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	private void reset() {
		recalculationGeneralPane1.enableAccountChange(false);
		recalculationGeneralPane1.setRecalc(recalc);
	}

	private boolean apply() {
		if (recalc == null) {
			lblError.setText("empty recalculation");
			return false;
		}
		String description = recalculationGeneralPane1.getDescription();
//		String number = recalculationGeneralPane1.getNumber();
//		if (number == null || number.trim().length() == 0) {
//			lblError.setText(GUIMessages.getMessage("comp.recalc_new_dialog.error.empty_number"));
//			return false;
//		}
		Recalc newRecalc = new Recalc();
		newRecalc.setId(recalc.getId());
		newRecalc.setNumber(recalc.getNumber().trim());
		newRecalc.setAccount(recalc.getAccount());
		newRecalc.setCreateDate(recalc.getCreateDate());
		newRecalc.setCustomer(recalc.getCustomer());
		newRecalc.setDescription(GUITranslator.KA_TO_GEO_ASCII(description));
		recalc = newRecalc;
		return true;
	}

	private void onOk() {
		if (!apply()) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcUpdateRequest request = new RecalcUpdateRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRecalculation(recalc);
			DefaultRecutilClient.processRequest(request);
			approved = true;
			dialogShell.dispose();
		} catch (Throwable t) {
			lblError.setText(t.toString());
		}
		approved = true;
		dialogShell.dispose();
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	public boolean isApproved() {
		return approved;
	}

}
