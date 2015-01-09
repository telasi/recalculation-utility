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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import telasi.recutil.beans.Operation;
import telasi.recutil.gui.utils.GUITranslator;

public class OperationPicker extends Composite {
	private Label txtOperation;
	private Button btnSelect;
	private Operation operation;

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
		if (operation == null) {
			txtOperation.setText("");
		} else {
			txtOperation.setText(GUITranslator.GEO_ASCII_TO_KA(operation
					.getName()));
		}
	}

	public OperationPicker(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initActions();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginHeight = 0;
			thisLayout.horizontalSpacing = 0;
			thisLayout.verticalSpacing = 0;
			thisLayout.marginWidth = 0;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				GridData txtOperationLData = new GridData();
				txtOperationLData.horizontalAlignment = GridData.FILL;
				txtOperationLData.grabExcessHorizontalSpace = true;
				txtOperation = new Label(this, SWT.READ_ONLY);
				txtOperation.setLayoutData(txtOperationLData);
			}
			{
				btnSelect = new Button(this, SWT.PUSH | SWT.CENTER);
				GridData btnSelectLData = new GridData();
				// btnSelectLData.heightHint = 20;
				btnSelect.setLayoutData(btnSelectLData);
				btnSelect.setText("...");
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setEnabled(boolean enabled) {
		// super.setEnabled(enabled);
		btnSelect.setEnabled(enabled);
	}

	private void initActions() {
		btnSelect.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				OperationDialog dialog = new OperationDialog(getShell(),
						SWT.NONE);
				dialog.open(operation);
				if (dialog.isApproved() && dialog.getOperation() != null) {
					setOperation(dialog.getOperation());
					fireOperationChange();
				}
			}
		});
	}

	//
	// listeners
	//

	private Vector listeners = new Vector();

	@SuppressWarnings("unchecked")
	public void addPropertyChangeListener(PropertyChangeListener l) {
		listeners.add(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		listeners.remove(l);
	}

	private void fireOperationChange() {
		if (!listeners.isEmpty()) {
			PropertyChangeEvent pce = new PropertyChangeEvent(this,
					"operation", operation, operation);

			for (int i = 0; i < listeners.size(); i++) {
				PropertyChangeListener l = (PropertyChangeListener) listeners
						.get(i);
				l.propertyChange(pce);
			}
		}
	}
}
