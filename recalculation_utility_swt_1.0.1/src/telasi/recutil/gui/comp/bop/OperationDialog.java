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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class OperationDialog extends Dialog {

	private Shell dialogShell;
	private BillOperationPane operationPane1;
	private Composite composite2;
	private Button btnOk;
	private BillOperationTypePane operationTypePane1;
	private Button btnClose;
	private Composite composite1;
	private SashForm sashForm1;

	public OperationDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open(Operation selection) {

		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.verticalSpacing = 0;
			dialogShellLayout.marginWidth = 0;
			dialogShellLayout.marginHeight = 0;
			dialogShellLayout.horizontalSpacing = 0;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.setText(GUIMessages.getMessage("comp.operation_picker_dialog.title"));
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1Layout.verticalSpacing = 2;
				composite1Layout.horizontalSpacing = 2;
				composite1Layout.marginHeight = 2;
				composite1Layout.marginWidth = 2;
				GridData composite1LData = new GridData();
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				// ------------------
				sashForm1 = new SashForm(composite1, SWT.NONE);
				GridData sashForm1LData = new GridData();
				sashForm1LData.horizontalAlignment = GridData.FILL;
				sashForm1LData.verticalAlignment = GridData.FILL;
				sashForm1LData.grabExcessVerticalSpace = true;
				sashForm1LData.grabExcessHorizontalSpace = true;
				sashForm1.setLayoutData(sashForm1LData);
				// ------------------
				{
					GridData operationTypePane1LData = new GridData();
					operationTypePane1LData.verticalAlignment = GridData.FILL;
					operationTypePane1LData.grabExcessVerticalSpace = true;
					// operationTypePane1LData.widthHint = 152;
					operationTypePane1 = new BillOperationTypePane(sashForm1,
							SWT.BORDER);
					operationTypePane1.setLayoutData(operationTypePane1LData);
				}
				{
					GridData operationPane1LData = new GridData();
					operationPane1LData.verticalAlignment = GridData.FILL;
					operationPane1LData.grabExcessVerticalSpace = true;
					operationPane1LData.horizontalAlignment = GridData.FILL;
					operationPane1LData.grabExcessHorizontalSpace = true;
					operationPane1 = new BillOperationPane(sashForm1,
							SWT.BORDER);
					operationPane1.setLayoutData(operationPane1LData);
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
					btnOkLData.horizontalAlignment = GridData.END;
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.widthHint = 75;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
				}
				{
					btnClose = new Button(composite2, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText(GUIMessages.getMessage("comp.general.close"));
				}
			}

			// $protect>>$
			operationTypePane1.refresh(false);
			dialogShell.pack();
			//dialogShell.setSize(800, 650);
			GUIUtils.centerShell(dialogShell);
			beforeOpen(selection);
			dialogShell.open();
			// $protect<<$

			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	// fills data before opening
	//
	private void beforeOpen(Operation selection) {
		// operationPane1.link(operationTypePane1);

		operationTypePane1
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@SuppressWarnings("unchecked")
					public void selectionChanged(SelectionChangedEvent event) {
						List types = operationTypePane1.getSelectedTypes();
						if (types == null) {
							operationPane1.displayOperations(null);
						} else {
							List operations = new ArrayList();
							for (int i = 0; i < types.size(); i++) {
								OperationType type = (OperationType) types.get(i);
								List opers = type.getOperations();
								if (opers != null) {
									operations.addAll(opers);
								}
							}
							operationPane1.displayOperations(operations);
						}
					}
				});

		if (selection != null) {
			operationTypePane1.selectTypeById(selection.getType().getId());
			OperationType type = (OperationType) operationTypePane1
					.getSelectedTypes().get(0);
			operationPane1.displayOperations(type.getOperations());
			operationPane1.selectOperationById(selection.getId());
		}

		// add selection listener
		btnClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				operation = null;
				approved = false;
				dialogShell.dispose();
			}
		});
		btnOk.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				operation = operationPane1.getSelectedOperation();
				approved = true;
				dialogShell.dispose();
			}
		});
		btnOk.getShell().setDefaultButton(btnOk);
	}

	private boolean approved = false;

	private Operation operation;

	public Operation getOperation() {
		return operation;
	}

	public boolean isApproved() {
		return approved;
	}

}
