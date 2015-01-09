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

import java.util.Calendar;

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

public class SWTCalendarDialog extends Dialog
{

private Shell dialogShell;

public Shell getShell()
{
	return dialogShell;
}

private SWTCalendar calendar;

private Button btnClose;

private Button btnOk;

private Composite composite1;

private Calendar date;

public Calendar getCalendar()
{
	return date;
}

public SWTCalendarDialog(Shell parent, int style)
{
	super(parent, style);
	initGUI();
	initActions();
}

private void initGUI()
{
	Shell parent = getParent();
	dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

	dialogShell.setLayout(new GridLayout());
	dialogShell.setText("Select Date");
	{
		calendar = new SWTCalendar(dialogShell, SWT.NONE);
	}
	{
		composite1 = new Composite(dialogShell, SWT.NONE);
		GridLayout composite1Layout = new GridLayout();
		composite1Layout.numColumns = 2;
		GridData composite1LData = new GridData();
		composite1LData.horizontalAlignment = GridData.FILL;
		composite1LData.grabExcessHorizontalSpace = true;
		composite1.setLayoutData(composite1LData);
		composite1.setLayout(composite1Layout);
		{
			btnOk = new Button(composite1, SWT.PUSH | SWT.CENTER);
			GridData btnOkLData = new GridData();
			btnOkLData.widthHint = 50;
			btnOkLData.grabExcessHorizontalSpace = true;
			btnOkLData.horizontalAlignment = GridData.END;
			btnOk.setLayoutData(btnOkLData);
			btnOk.setText("OK");
		}
		{
			btnClose = new Button(composite1, SWT.PUSH | SWT.CENTER);
			GridData btnCloseLData = new GridData();
			btnCloseLData.widthHint = 50;
			btnClose.setLayoutData(btnCloseLData);
			btnClose.setText("Close");
		}
	}
	dialogShell.layout();
	dialogShell.pack();
}

private void initActions()
{
	btnOk.addSelectionListener(new SelectionAdapter()
	{
		public void widgetSelected(SelectionEvent e)
		{
			date = calendar.getCalendar();
			approved = true;
			dialogShell.dispose();
		}
	});
	btnClose.addSelectionListener(new SelectionAdapter()
	{
		public void widgetSelected(SelectionEvent e)
		{
			approved = false;
			dialogShell.dispose();
		}
	});
}

public void open(Calendar cal, Calendar cal2)
{
	if (cal != null) {
		calendar.setCalendar(cal);
	} else {
		if (cal2 != null) {
			calendar.setCalendar(cal2);
		}
	}
	dialogShell.open();
	Display display = dialogShell.getDisplay();
	while (!dialogShell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
}

private boolean approved;

public boolean isApproved()
{
	return approved;
}

}
