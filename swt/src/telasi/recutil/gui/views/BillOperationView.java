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
package telasi.recutil.gui.views;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.beans.OperationType;
import telasi.recutil.gui.comp.bop.BillOperationPane;
import telasi.recutil.gui.comp.bop.BillOperationTypePane;
import telasi.recutil.gui.utils.GUIMessages;

public class BillOperationView extends ViewPart {
	public static final String ID_VIEW = "ge.telasi.recut.guiapp.views.BillOperationView"; //$NON-NLS-1$
	private Composite composite6;
	private BillOperationPane billOperationPane1;
	private Composite composite5;
	private SashForm sashForm1;
	private Composite composite4;
	private BillOperationTypePane billOperationTypePane1;
	private boolean firstCall = true;

	public BillOperationView() {
		super();

	}

	public void createPartControl(Composite parent) {
		{
			FillLayout parentLayout = new FillLayout(
					org.eclipse.swt.SWT.HORIZONTAL);
			parent.setLayout(parentLayout);
			parent.setSize(556, 237);
			{
				composite4 = new Composite(parent, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.horizontalSpacing = 0;
				composite4Layout.marginWidth = 0;
				composite4Layout.verticalSpacing = 0;
				composite4Layout.marginHeight = 0;
				composite4.setLayout(composite4Layout);
				{
					sashForm1 = new SashForm(composite4, SWT.NONE);
					GridData sashForm1LData = new GridData();
					sashForm1LData.horizontalAlignment = GridData.FILL;
					sashForm1LData.verticalAlignment = GridData.FILL;
					sashForm1LData.grabExcessVerticalSpace = true;
					sashForm1LData.grabExcessHorizontalSpace = true;
					sashForm1.setLayoutData(sashForm1LData);
					sashForm1.setSize(60, 30);
					{
						composite5 = new Composite(sashForm1, SWT.BORDER);
						GridLayout composite5Layout = new GridLayout();
						composite5Layout.horizontalSpacing = 0;
						composite5Layout.marginHeight = 0;
						composite5Layout.marginWidth = 0;
						composite5Layout.verticalSpacing = 0;
						composite5.setLayout(composite5Layout);
						composite5.setBounds(33, 38, 278, 237);
						{
							// $hide>>$
							GridData billOperationTypePane1LData = new GridData();
							billOperationTypePane1LData.verticalAlignment = GridData.FILL;
							billOperationTypePane1LData.grabExcessVerticalSpace = true;
							billOperationTypePane1LData.horizontalAlignment = GridData.FILL;
							billOperationTypePane1LData.grabExcessHorizontalSpace = true;
							billOperationTypePane1 = new BillOperationTypePane(
									composite5, SWT.NONE);
							billOperationTypePane1
									.setLayoutData(billOperationTypePane1LData);
							// $hide<<$
						}
					}
					{
						composite6 = new Composite(sashForm1, SWT.BORDER);
						GridLayout composite6Layout = new GridLayout();
						composite6Layout.horizontalSpacing = 0;
						composite6Layout.marginHeight = 0;
						composite6Layout.marginWidth = 0;
						composite6Layout.verticalSpacing = 0;
						composite6.setLayout(composite6Layout);
						composite6.setBounds(49, 38, 138, 237);
						{
							// $hide>>$
							GridData billOperationPane1LData = new GridData();
							billOperationPane1LData.horizontalAlignment = GridData.FILL;
							billOperationPane1LData.grabExcessHorizontalSpace = true;
							billOperationPane1LData.verticalAlignment = GridData.FILL;
							billOperationPane1LData.grabExcessVerticalSpace = true;
							billOperationPane1 = new BillOperationPane(
									composite6, SWT.NONE);
							billOperationPane1
									.setLayoutData(billOperationPane1LData);
							// $hide<<$
						}
					}
				}
			}
		}
		// $protect>>$
		sashForm1.setWeights(new int[] { 1, 1 });
		customInit();
		// $protect<<$
	}

	private void customInit() {
		setPartName(GUIMessages.getMessage("application.action.bopview"));
		billOperationTypePane1
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@SuppressWarnings("unchecked")
					public void selectionChanged(SelectionChangedEvent event) {
						List types = billOperationTypePane1.getSelectedTypes();
						if (types == null) {
							billOperationPane1.displayOperations(null);
						} else {
							List operations = new ArrayList();
							for (int i = 0; i < types.size(); i++) {
								OperationType type = (OperationType) types.get(i);
								List opers = type.getOperations();
								if (opers != null)
									operations.addAll(opers);
							}
							billOperationPane1.displayOperations(operations);
						}
					}
				});
	}

	public void setFocus() {
		if (firstCall) {
			billOperationTypePane1.refresh(false);
			firstCall = false;
		}
	}

	public void dispose() {
		super.dispose();
	}

}
