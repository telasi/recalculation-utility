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


import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import telasi.recutil.gui.utils.GUIMessages;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{

public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
{
	super(configurer);
}

public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer)
{
	return new ApplicationActionBarAdvisor(configurer);
}

public void preWindowOpen()
{
	IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
	configurer.setInitialSize(new Point(1024, 800));
	configurer.setShowCoolBar(false);
	configurer.setShowProgressIndicator(false);
	configurer.setShowStatusLine(false);
	configurer.setTitle(GUIMessages.getMessage("application.title"));
}
}
