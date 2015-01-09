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
package telasi.recutil.gui.comp.bop;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import telasi.recutil.beans.Operation;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class BillOperationProperties extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Composite composite2;
	private BillOperationGeneralPane billOperationGeneralPane1;
	private TabItem tabAttachment;
	private TabItem tabRequiments;
	private TabItem tabGeneral;
	private TabFolder tabFolder1;
	private Button btnClose;
	private BillOperationRecalcPane billOperationRecalcPane1;
	private BillOperationRequimentPane billingOperationRequimentPane1;
	private Operation operation;

	public BillOperationProperties(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM| SWT.APPLICATION_MODAL | SWT.RESIZE);
			dialogShell.setLayout(new GridLayout());
			dialogShell.layout();
			dialogShell.setText(GUIMessages.getMessage("comp.billoper_prop.title"));
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					tabFolder1 = new TabFolder(composite1, SWT.NONE);
					GridData tabFolder1LData = new GridData();
					tabFolder1LData.horizontalAlignment = GridData.FILL;
					tabFolder1LData.grabExcessHorizontalSpace = true;
					tabFolder1LData.verticalAlignment = GridData.FILL;
					tabFolder1LData.grabExcessVerticalSpace = true;
					tabFolder1.setLayoutData(tabFolder1LData);
					tabFolder1.setSelection(0);
					{
						tabGeneral = new TabItem(tabFolder1, SWT.NONE);
						tabGeneral.setText(GUIMessages
								.getMessage("comp.billoper_prop.general"));
						{
							// $hide>>$
							billOperationGeneralPane1 = new BillOperationGeneralPane(
									tabFolder1, SWT.NONE);
							tabGeneral.setControl(billOperationGeneralPane1);
							// $hide<<$
						}
					}
					{
						tabRequiments = new TabItem(tabFolder1, SWT.NONE);
						tabRequiments.setText(GUIMessages
								.getMessage("comp.billoper_prop.requiment"));
						{
							// $hide>>$
							billingOperationRequimentPane1 = new BillOperationRequimentPane(
									tabFolder1, SWT.NONE);
							tabRequiments
									.setControl(billingOperationRequimentPane1);
							// $hide<<$
						}
					}
					{
						tabAttachment = new TabItem(tabFolder1, SWT.NONE);
						tabAttachment.setText(GUIMessages
								.getMessage("comp.billoper_prop.attachment"));
						{
							// $hide>>$
							billOperationRecalcPane1 = new BillOperationRecalcPane(
									tabFolder1, SWT.NONE);
							tabAttachment.setControl(billOperationRecalcPane1);
							// $hide<<$
						}
					}
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				composite2Layout.marginWidth = 10;
				composite2Layout.marginHeight = 10;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					btnClose = new Button(composite2, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnCloseLData.horizontalAlignment = GridData.END;
					btnCloseLData.grabExcessHorizontalSpace = true;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText(GUIMessages
							.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							dialogShell.dispose();
						}
					});
				}
			}
			// $protect>>$
			reset();
			dialogShell.pack();
			//dialogShell.setSize(450, 600);
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

		boolean showTabs = operation != null;
		// boolean showAttachment = showTabs && operation.getSubsidyAttachment()
		// != null;

		tabFolder1.setVisible(showTabs);

		billOperationGeneralPane1.setOperation(operation);
		billingOperationRequimentPane1.setOperation(operation);
		billOperationRecalcPane1.setOperation(operation);

	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Operation getOperation() {
		return operation;
	}

}
