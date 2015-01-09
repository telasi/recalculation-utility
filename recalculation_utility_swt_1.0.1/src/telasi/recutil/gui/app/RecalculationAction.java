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
package telasi.recutil.gui.app;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import telasi.recutil.gui.views.RecalculationView;

public class RecalculationAction extends Action
{
private final IWorkbenchWindow window;

private int instanceNum = 0;

RecalculationAction(IWorkbenchWindow window)
{
	this.window = window;
}

public void run()
{
	try {
		window.getActivePage().showView(RecalculationView.ID_VIEW, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
	} catch (PartInitException e) {
		MessageDialog.openError(window.getShell(), "Error", "Error opening view:" + e.getMessage());
	}
}
}
