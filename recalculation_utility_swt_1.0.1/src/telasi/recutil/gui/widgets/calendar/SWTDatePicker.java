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
package telasi.recutil.gui.widgets.calendar;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Date;
import telasi.recutil.gui.utils.GUIUtils;

public class SWTDatePicker extends Composite {

	private Text txtDate;

	private Date defDate;

	public void setDefault(Date def) {
		defDate = def;
	}

	public Date getDefault() {
		return defDate;
	}

	private Button btnSelect;

	public SWTDatePicker(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initActions();
		refreshDate();
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
				GridData txtDateLData = new GridData();
				txtDateLData.horizontalAlignment = GridData.FILL;
				txtDateLData.grabExcessHorizontalSpace = true;
				txtDate = new Text(this, SWT.READ_ONLY);
				txtDate.setLayoutData(txtDateLData);
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

	private void initActions() {
		btnSelect.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SWTCalendarDialog dialog = new SWTCalendarDialog(getShell(),
						SWT.NONE);
				GUIUtils.centerShell(dialog.getShell());
				Calendar cal = null;
				if (date != null) {
					cal = Calendar.getInstance();
					cal.setTime(date.toDate());
				}
				Calendar cal2 = null;
				if (defDate != null) {
					cal2 = Calendar.getInstance();
					cal2.setTime(defDate.toDate());
				}
				dialog.open(cal, cal2);
				if (dialog.isApproved()) {
					setDate(Date.create(dialog.getCalendar().getTime()));
				}
			}
		});
	}

	private Date date;

	public void setEnabled(boolean enabled) {
		// super.setEnabled(enabled);
		btnSelect.setEnabled(enabled);
	}

	public void applyDate(Date date, boolean generateChangeEvents) {
		if (this.date != null && date != null) {
			if (this.date.equals(date)) {
				return;
			}
		}
		Date oldValue = this.date;
		this.date = date;
		refreshDate();
		if (generateChangeEvents) {
			fireDateChangeEvent(oldValue, this.date);
		}
	}

	public void setDate(Date date) {
		applyDate(date, true);
	}

	public Date getDate() {
		return date;
	}

	private void refreshDate() {
		if (date == null) {
			txtDate.setText("<not defined>");
		} else {
			txtDate.setText(Date.format(date));
		}
	}

	//
	// მოვლენების მართვა
	//

	private Vector listeners = new Vector();

	@SuppressWarnings("unchecked")
	public void addPropertyChangeListener(PropertyChangeListener l) {
		listeners.add(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		listeners.remove(l);
	}

	private void fireDateChangeEvent(Date oldValue, Date newValue) {
		if (!listeners.isEmpty()) {
			PropertyChangeEvent evt = new PropertyChangeEvent(this, "date",
					oldValue, newValue);

			for (int i = 0; i < listeners.size(); i++) {
				PropertyChangeListener l = (PropertyChangeListener) listeners
						.get(i);
				l.propertyChange(evt);
			}
		}
	}

}
