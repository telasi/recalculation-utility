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

public class RegularOperationPicker extends Composite {

	private Label lblOperation;
	private Button btnSelect;
	private Operation operation;

	public RegularOperationPicker(Composite parent, int style) {
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
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				GridData lblOperationLData = new GridData();
				lblOperationLData.horizontalAlignment = GridData.FILL;
				lblOperationLData.grabExcessHorizontalSpace = true;
				lblOperation = new Label(this, SWT.NONE);
				lblOperation.setLayoutData(lblOperationLData);
			}
			{
				btnSelect = new Button(this, SWT.PUSH | SWT.CENTER);
				btnSelect.setText("...");
				btnSelect.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onSelect();
					}
				});
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onSelect() {
		RegularOperationSelector selector = new RegularOperationSelector(
				getShell(), SWT.NONE);
		selector.setOperation(operation);
		selector.open();
		if (selector.isApproved()) {
			setOperation(selector.getOperation());
		}
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		int id = -1;
		if (this.operation != null) {
			id = this.operation.getId();
		}
		this.operation = operation;
		reset();
		if (operation == null || operation.getId() != id) {
			fireOperationChange();
		}
	}

	private void reset() {
		lblOperation.setText(operation == null ? "" : GUITranslator
				.GEO_ASCII_TO_KA(operation.getName()));
	}

	private Vector propertyListeners = new Vector();

	@SuppressWarnings("unchecked")
	public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
		propertyListeners.add(l);
	}

	public synchronized void removePropertyChangeListener(
			PropertyChangeListener l) {
		propertyListeners.remove(l);
	}

	private synchronized void fireOperationChange() {
		PropertyChangeEvent event = new PropertyChangeEvent(this, "operation",
				null, null);
		for (int i = 0; i < propertyListeners.size(); i++) {
			PropertyChangeListener l = (PropertyChangeListener) propertyListeners
					.get(i);
			l.propertyChange(event);
		}
	}

}
