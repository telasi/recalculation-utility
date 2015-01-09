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
package telasi.recutil.gui.comp.recalc.tariff;

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

import telasi.recutil.beans.ITariff;
import telasi.recutil.gui.utils.GUITranslator;

public class TariffPicker extends Composite {

	private Label lblOperation;
	private Button btnSelect;
	private ITariff tariff;

	public TariffPicker(Composite parent, int style) {
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
		TariffSelector selector = new TariffSelector(getShell(), SWT.NONE);
		selector.setTariff(tariff);
		selector.open();
		if (selector.isApproved()) {
			setTariff(selector.getTariff());
		}
	}

	public ITariff getTariff() {
		return tariff;
	}

	public void setTariff(ITariff tariff) {
		int id = -1;
		if (this.tariff != null) {
			id = this.tariff.getId();
		}
		this.tariff = tariff;
		reset();
		if (tariff == null || tariff.getId() != id) {
			fireTariffChange();
		}
	}

	private void reset() {
		lblOperation.setText(tariff == null ? "" : GUITranslator
				.GEO_ASCII_TO_KA(tariff.getName()));
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

	private synchronized void fireTariffChange() {
		PropertyChangeEvent event = new PropertyChangeEvent(this, "tariff",
				null, null);
		for (int i = 0; i < propertyListeners.size(); i++) {
			PropertyChangeListener l = (PropertyChangeListener) propertyListeners
					.get(i);
			l.propertyChange(event);
		}
	}

}
