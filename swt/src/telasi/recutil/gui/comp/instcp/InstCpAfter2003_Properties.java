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
package telasi.recutil.gui.comp.instcp;

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

import telasi.recutil.beans.Date;
import telasi.recutil.beans.instcp.InstCpAfter2003Record;
import telasi.recutil.ejb.Request;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.spinner.SwingNumericSpinner;
import telasi.recutil.service.eclipse.instcp.InstCpHistoryAfter2003InsertRequest;
import telasi.recutil.service.eclipse.instcp.InstCpHistoryAfter2003UpdateRequest;

public class InstCpAfter2003_Properties extends Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Label lblYear;
	private SwingNumericSpinner spYear;
	private Button btnClose;
	private Button btnOk;
	private Composite composite2;
	private Label lblError;
	private SwingNumericSpinner spInstcp;
	private Label lblInstcp;
	private SwingNumericSpinner spMonth;
	private Label lblMonth;
	private boolean approved = false;
	private boolean isNewMode;
	private InstCpAfter2003Record record;

	public InstCpAfter2003_Properties(Shell parent, int style) {
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
				composite1Layout.marginTop = 5;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblYear = new Label(composite1, SWT.NONE);
					lblYear
							.setText(GUIMessages
									.getMessage("comp.general.year"));
				}
				{
					GridData spYearLData = new GridData();
					spYearLData.widthHint = 150;
					spYear = new SwingNumericSpinner(composite1, SWT.NONE);
					spYear.setLayoutData(spYearLData);
					spYear.setMask("0000");
				}
				{
					lblMonth = new Label(composite1, SWT.NONE);
					lblMonth.setText(GUIMessages
							.getMessage("comp.general.month"));
				}
				{
					GridData spMonthLData = new GridData();
					spMonthLData.widthHint = 150;
					spMonth = new SwingNumericSpinner(composite1, SWT.NONE);
					spMonth.setLayoutData(spMonthLData);
					spMonth.setMask("00");
				}
				{
					lblInstcp = new Label(composite1, SWT.NONE);
					lblInstcp.setText(GUIMessages
							.getMessage("comp.general.instcp"));
				}
				{
					GridData spInstcpLData = new GridData();
					spInstcpLData.widthHint = 150;
					spInstcp = new SwingNumericSpinner(composite1, SWT.NONE);
					spInstcp.setLayoutData(spInstcpLData);
				}
			}
			{
				GridData lblErrorLData = new GridData();
				lblErrorLData.verticalAlignment = GridData.FILL;
				lblErrorLData.grabExcessVerticalSpace = true;
				lblErrorLData.horizontalAlignment = GridData.FILL;
				lblErrorLData.grabExcessHorizontalSpace = true;
				lblError = new Label(dialogShell, SWT.WRAP);
				lblError.setLayoutData(lblErrorLData);
				lblError.setForeground(lblError.getDisplay().getSystemColor(
						SWT.COLOR_RED));
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
					btnClose.setText(GUIMessages
							.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							approved = false;
							dialogShell.dispose();
						}
					});
				}
			}

			// $protect>>$
			dialogShell.pack();
			GUIUtils.centerShell(dialogShell);
			reset();
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
		if (record == null) {
			isNewMode = true;
			dialogShell.setText("New");
			record = new InstCpAfter2003Record();
			Date current = Date.create(System.currentTimeMillis());
			record.setYear(current.getYear());
			record.setMonth(current.getMonth());
			record.setInstcp(0);
		} else {
			isNewMode = false;
			dialogShell.setText("Properties");
		}
		spInstcp.setValue(new Double(record.getInstcp()));
		spYear.setValue(new Integer(record.getYear()));
		spMonth.setValue(new Integer(record.getMonth()));

		spMonth.setEnabled(isNewMode);
		spYear.setEnabled(isNewMode);
	}

	public boolean isApproved() {
		return approved;
	}

	private void onOk() {
		lblError.setText("");
		if (!Application.validateConnection()) {
			lblError.setText("no connection");
			return;
		}
		spMonth.apply();
		spYear.apply();
		spInstcp.apply();
		int month = ((Number) spMonth.getValue()).intValue();
		int year = ((Number) spYear.getValue()).intValue();
		double instcp = ((Number) spInstcp.getValue()).doubleValue();
		if (month < 1 || month > 12) {
			lblError.setText("1 <= month <= 12");
			return;
		}
		if (year < 1999 || year > 2999) {
			lblError.setText("1999 <= year <= 2999");
			return;
		}
		if (instcp < 0) {
			lblError.setText("intalled capacity => 0");
			return;
		}
		InstCpAfter2003Record newRecord = new InstCpAfter2003Record();
		newRecord.setInstcp(instcp);
		newRecord.setMonth(month);
		newRecord.setYear(year);
		try {
			Request request = null;
			if (isNewMode) {
				InstCpHistoryAfter2003InsertRequest r = new InstCpHistoryAfter2003InsertRequest(
						Application.USER_NAME, Application.PASSWORD);
				r.setRecord(newRecord);
				request = r;
			} else {
				InstCpHistoryAfter2003UpdateRequest r = new InstCpHistoryAfter2003UpdateRequest(
						Application.USER_NAME, Application.PASSWORD);
				r.setRecord(newRecord);
				request = r;
			}
			DefaultRecutilClient.processRequest(request);

			record = newRecord;
			approved = true;
			dialogShell.dispose();
		} catch (Throwable t) {
			lblError.setText(t.toString());
		}
	}

	public InstCpAfter2003Record getInstcpRecord() {
		return record;
	}

	public void setInstcpRecord(InstCpAfter2003Record instcpRecord) {
		this.record = instcpRecord;
	}

}
