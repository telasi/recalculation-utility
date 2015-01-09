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
package telasi.recutil.gui.comp.report;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIUtils;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ReportViewer extends org.eclipse.swt.widgets.Dialog
{

private Shell dialogShell;

private Browser browser1;

public ReportViewer(Shell parent, int style)
{
	super(parent, style);
}

public void open()
{
	try {
		Shell parent = getParent();
		dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
		GridLayout thisLayout = new GridLayout();
		thisLayout.horizontalSpacing = 0;
		thisLayout.marginHeight = 0;
		thisLayout.marginWidth = 0;
		thisLayout.verticalSpacing = 0;
		dialogShell.setLayout(thisLayout);
		{
			GridData toolBar1LData = new GridData();
			toolBar1LData.horizontalAlignment = GridData.FILL;
			toolBar1LData.verticalAlignment = GridData.BEGINNING;
			toolBar1 = new ToolBar(dialogShell, SWT.FLAT);
			toolBar1.setLayoutData(toolBar1LData);
			{
				tiPrint = new ToolItem(toolBar1, SWT.NONE);
				tiPrint.setToolTipText("Print");
				tiPrint.setImage(Plugin.getImage("/icons/22x22/print.png"));
				tiPrint.addSelectionListener(new SelectionAdapter()
				{
					public void widgetSelected(SelectionEvent e)
					{
						print();
					}
				});
			}
		}
		{
			composite1 = new Composite(dialogShell, SWT.NONE);
			GridLayout composite1Layout = new GridLayout();
			composite1Layout.horizontalSpacing = 0;
			composite1Layout.marginHeight = 0;
			composite1Layout.marginWidth = 0;
			composite1Layout.verticalSpacing = 0;
			GridData composite1LData = new GridData();
			composite1LData.verticalAlignment = GridData.FILL;
			composite1LData.grabExcessVerticalSpace = true;
			composite1LData.horizontalAlignment = GridData.FILL;
			composite1LData.grabExcessHorizontalSpace = true;
			composite1.setLayoutData(composite1LData);
			composite1.setLayout(composite1Layout);
			{
				GridData browser1LData = new GridData();
				browser1LData.horizontalAlignment = GridData.FILL;
				browser1LData.grabExcessHorizontalSpace = true;
				browser1LData.verticalAlignment = GridData.FILL;
				browser1LData.grabExcessVerticalSpace = true;
				browser1 = new Browser(composite1, SWT.BORDER);
				browser1.setLayoutData(browser1LData);
			}
		}

		dialogShell.setLayout(thisLayout);
		dialogShell.layout();
		dialogShell.pack();
		dialogShell.setSize(550, 700);
		dialogShell.setText("Report Viewer");

		try {
			browser1.setText(text);
		} catch (Throwable t) {
			StringBuffer errorMsg = new StringBuffer();
			errorMsg.append("<html><body><h1><font color=\"red\">Can not display report.</font></h1>");
			errorMsg.append("<p>Error message is:<br><br><pre>");
			errorMsg.append(t.toString());
			errorMsg.append("</pre></p>");
			errorMsg.append("</body></html>");
			browser1.setText(errorMsg.toString());
		}

		GUIUtils.centerShell(dialogShell);
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

private String text;

private ToolItem tiPrint;

private ToolBar toolBar1;

private Composite composite1;

public void setHTML(String text)
{
	this.text = text;
}

void print()
{
	browser1.execute("javascript:window.print()");
}

}
