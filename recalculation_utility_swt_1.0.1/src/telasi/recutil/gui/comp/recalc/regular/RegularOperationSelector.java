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
package telasi.recutil.gui.comp.recalc.regular;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.Operation;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class RegularOperationSelector extends Dialog {

	private Shell dialogShell;
	private TableViewer viewer;
	private Button btnClose;
	private Button btnOk;
	private Composite composite1;
	private Operation operation;
	private boolean approved;

	public RegularOperationSelector(Shell parent, int style) {
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
			dialogShell.pack();
			dialogShell.setText(GUIMessages
					.getMessage("comp.regular_selector.title"));
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewerLData.grabExcessVerticalSpace = true;
				viewer = new TableViewer(dialogShell, SWT.BORDER | SWT.SINGLE
						| SWT.FULL_SELECTION | SWT.VIRTUAL);
				viewer.getControl().setLayoutData(viewerLData);
			}
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1Layout.marginWidth = 15;
				composite1Layout.marginHeight = 15;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					btnOk = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
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
					btnClose = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText(GUIMessages
							.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onClose();
						}
					});
				}
			}

			// $protect>>$
			dialogShell.setSize(350, 400);
			GUIUtils.centerShell(dialogShell);
			reset();
			dialogShell.open();
			viewer.getTable().showSelection();
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

	private void reset() {
		try {
			Cache.refreshBillOperationsList(false);
		} catch (Throwable t) {
			MessageDialog.openError(dialogShell, GUIMessages
					.getMessage("comp.general.error"), t.toString());
			dialogShell.dispose();
			return;
		}
		viewer.setItemCount(Cache.REGULAR_OPERATIONS_LIST == null ? 0
				: Cache.REGULAR_OPERATIONS_LIST.size());
		RegularOperationContentProvider provider = new RegularOperationContentProvider(
				Cache.REGULAR_OPERATIONS_LIST);
		viewer.setContentProvider(provider);
		viewer.setInput(this);
		viewer.setLabelProvider(new RegularOperationLabelProvider());
		if (operation != null && provider.operations != null) {
			for (int i = 0; i < provider.operations.size(); i++) {
				Operation oper = (Operation) provider.operations.get(i);
				if (oper.getId() == operation.getId()) {
					viewer.getTable().setSelection(i);
					break;
				}
			}
		}
	}

	private class RegularOperationContentProvider implements
			IStructuredContentProvider {

		private List operations;

		public RegularOperationContentProvider(List operations) {
			this.operations = operations;
		}

		public Object[] getElements(Object inputElement) {
			return operations == null ? new Object[] {} : operations.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class RegularOperationLabelProvider extends LabelProvider {

		public Image getImage(Object element) {
			Operation operation = (Operation) element;
			return GUIUtils.getOperationTypeImage(operation.getType().getId());
		}

		public String getText(Object element) {
			Operation operation = (Operation) element;
			return GUITranslator.GEO_ASCII_TO_KA(operation.getName());
		}

	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	private void onOk() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return;
		}
		RegularOperationContentProvider provider = (RegularOperationContentProvider) viewer
				.getContentProvider();
		operation = (Operation) provider.operations.get(index);
		approved = true;
		dialogShell.dispose();
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public boolean isApproved() {
		return approved;
	}

}
