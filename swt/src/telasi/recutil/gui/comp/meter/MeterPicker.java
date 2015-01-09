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
package telasi.recutil.gui.comp.meter;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import telasi.recutil.beans.Meter;
import telasi.recutil.gui.utils.GUITranslator;

public class MeterPicker extends Composite {

	private Label lblMeter;
	private Button btnSelect;
	private Meter meter;

	public MeterPicker(Composite parent, int style) {
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
				GridData lblMeterLData = new GridData();
				lblMeterLData.horizontalAlignment = GridData.FILL;
				lblMeterLData.grabExcessHorizontalSpace = true;
				lblMeter = new Label(this, SWT.NONE);
				lblMeter.setLayoutData(lblMeterLData);
			}
			{
				btnSelect = new Button(this, SWT.PUSH | SWT.CENTER);
				btnSelect.setText("...");
				btnSelect.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onSelection();
					}
				});
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onSelection() {
		MeterSelectionDialog selector = new MeterSelectionDialog(getShell(),
				SWT.NONE);
		selector.setMeter(meter);
		selector.open();
		if (selector.isApproved()) {
			meter = selector.getMeter();
		}
		reset();
	}

	public Meter getMeter() {
		return meter;
	}

	public void setMeter(Meter meter) {
		this.meter = meter;
		reset();
	}

	private void reset() {
		if (meter == null) {
			lblMeter.setText("");
		} else {
			lblMeter.setText(GUITranslator.GEO_ASCII_TO_KA(meter.toString()));
		}
	}

}
