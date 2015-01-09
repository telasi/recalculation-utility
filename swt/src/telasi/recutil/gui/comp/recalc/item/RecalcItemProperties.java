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
package telasi.recutil.gui.comp.recalc.item;

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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.RecalcSubsidyAttachment;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.recalc.RecalcItemInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemUpdateRequest;
import telasi.recutil.utils.CoreUtils;

public class RecalcItemProperties extends Dialog {
	private Shell dialogShell;
	private TabItem tabGeneral;
	private TabItem tabDates;
	private Composite composite2;
	private Button btnOk;
	private Button btnClose;
	private Composite composite3;
	private Label lblError;
	private TabItem tabOperation;
	private Composite composite1;
	private TabFolder tabFolder1;
	private RecalcItem item;
	private Button btnReset;
	private RecalcItemDatesPane recalcItemDatesPane1;
	private RecalcItemOperationPane recalcItemOperationPane1;
	private boolean isNewMode = true;
	private RecalcItemGeneralPane recalcItemGeneralPane1;
	private boolean approved = false;
	private int sequence = 0;

	public RecalcItemProperties(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
			GridLayout dialogShellLayout = new GridLayout();
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.BEGINNING;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					tabFolder1 = new TabFolder(composite1, SWT.NONE);
					{
						tabGeneral = new TabItem(tabFolder1, SWT.NONE);
						tabGeneral.setText(GUIMessages.getMessage("comp.recalc_item_prop.general_tab"));
						{
							// $hide>>$
							recalcItemGeneralPane1 = new RecalcItemGeneralPane(tabFolder1, SWT.NONE);
							tabGeneral.setControl(recalcItemGeneralPane1);
							// $hide<<$
						}
					}
					{
						tabOperation = new TabItem(tabFolder1, SWT.NONE);
						tabOperation.setText(GUIMessages.getMessage("comp.recalc_item_prop.operation_tab"));
						{
							// $hide>>$
							recalcItemOperationPane1 = new RecalcItemOperationPane(tabFolder1, SWT.NONE);
							tabOperation.setControl(recalcItemOperationPane1);
							// $hide<<$
						}
					}
					{
						tabDates = new TabItem(tabFolder1, SWT.NONE);
						tabDates.setText(GUIMessages.getMessage("comp.recalc_item_prop.dates_tab"));
						{
							// $hide>>$
							recalcItemDatesPane1 = new RecalcItemDatesPane(tabFolder1, SWT.NONE);
							tabDates.setControl(recalcItemDatesPane1);
							// $hide<<$
						}
					}
					GridData tabFolder1LData = new GridData();
					tabFolder1LData.horizontalAlignment = GridData.FILL;
					tabFolder1LData.grabExcessHorizontalSpace = true;
					tabFolder1LData.verticalAlignment = GridData.BEGINNING;
					tabFolder1.setLayoutData(tabFolder1LData);
					tabFolder1.setSelection(0);
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.horizontalSpacing = 0;
				composite2Layout.marginHeight = 0;
				composite2Layout.marginWidth = 0;
				composite2Layout.verticalSpacing = 0;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2LData.verticalAlignment = GridData.FILL;
				composite2LData.grabExcessVerticalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					lblError = new Label(composite2, SWT.WRAP);
					GridData lblErrorLData = new GridData();
					lblErrorLData.horizontalAlignment = GridData.FILL;
					lblErrorLData.grabExcessHorizontalSpace = true;
					lblErrorLData.grabExcessVerticalSpace = true;
					lblErrorLData.verticalAlignment = GridData.FILL;
					//lblErrorLData.heightHint = 50;
					lblError.setLayoutData(lblErrorLData);
					lblError.setForeground(lblError.getDisplay()
							.getSystemColor(SWT.COLOR_RED));
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 3;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.END;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					btnReset = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnResetLData = new GridData();
					btnResetLData.widthHint = 75;
					btnReset.setLayoutData(btnResetLData);
					btnReset.setText(GUIMessages.getMessage("comp.general.reset"));
					btnReset.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							reset();
						}
					});
				}
				{
					btnOk = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.horizontalAlignment = GridData.END;
					btnOkLData.grabExcessHorizontalSpace = true;
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
					btnClose = new Button(composite3, SWT.PUSH | SWT.CENTER);
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
			dialogShell.pack();
			//dialogShell.setSize(500, 650);
			GUIUtils.centerShell(dialogShell);
			tabFolder1.setSelection(1);
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

	public RecalcItem getItem() {
		return item;
	}

	public void setItem(RecalcItem item) {
		this.item = item;
		if (item != null) {
			isNewMode = item.getId() == -1;
		}
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	private void reset() {

		// set item
		recalcItemGeneralPane1.setItem(item);
		recalcItemOperationPane1.setItem(item);
		recalcItemDatesPane1.setItem(item);

		// disable/enable OK button
		boolean disableOk = (item == null || item.getId() == -1) && !isNewMode;
		btnOk.setEnabled(!disableOk);

		// set dialog title
		if (isNewMode) {
			dialogShell.setText(GUIMessages
					.getMessage("comp.recalc_item_prop.title_for_new"));
		} else {
			dialogShell.setText(GUIMessages
					.getMessage("comp.recalc_item_prop.title_for_properties"));
		}

	}

	private void onOk() {

		lblError.setText("");

		Date curr = recalcItemDatesPane1.getCurrentDate();
		Date prev = recalcItemDatesPane1.getPreviousDate();
		Operation oper = recalcItemOperationPane1.getOperation();
		Date itemDate = recalcItemOperationPane1.getItemDate();
		Date enterDate = recalcItemOperationPane1.getEnterDate();
		double gel = recalcItemOperationPane1.getGel();
		double kwh = recalcItemOperationPane1.getKwh();
		double reading = recalcItemOperationPane1.getReading();
		int calcHint = recalcItemOperationPane1.getCalculationHint();
		RecalcSubsidyAttachment attachment = recalcItemOperationPane1
				.getAttachment();
		boolean cycle = recalcItemOperationPane1.getCycle();

		RecalcItem copy = CoreUtils.copyRecalcItem(item);
		copy.setCurrentOperationDate(curr);
		copy.setPreviousOperationDate(prev);
		copy.setOperation(oper);
		copy.setItemDate(itemDate);
		copy.setEnterDate(enterDate);
		copy.setGel(gel);
		copy.setKwh(kwh);
		copy.setReading(reading);
		copy.setCalculationHint(calcHint);
		copy.setSubsidyAttachment(attachment);
		copy.setCycle(cycle);

		if (!Application.validateConnection()) {
			return;
		}

		try {
			if (isNewMode) {
				RecalcItemInsertRequest request = new RecalcItemInsertRequest(
						Application.USER_NAME, Application.PASSWORD);
				request.setRecalcItem(copy);
				request.setSequence(sequence);
				DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
						.processRequest(request);
				RecalcItemInsertRequest callback = (RecalcItemInsertRequest) resp
						.getRequest();
				copy.setId(callback.getId());
			} else {
				RecalcItemUpdateRequest request = new RecalcItemUpdateRequest(
						Application.USER_NAME, Application.PASSWORD);
				request.setRecalcItem(copy);
				DefaultRecutilClient.processRequest(request);
			}
			approved = true;
			item = copy;
			dialogShell.dispose();
		} catch (Throwable t) {
			approved = false;
			lblError.setText(t.toString());
		}

	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	public boolean isApproved() {
		return approved;
	}

}
