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


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.gui.comp.instcp.InstCpAfter2003Pane;
import telasi.recutil.gui.utils.GUIMessages;

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
public class InspCpHistoryView extends ViewPart
{

public static final String ID_VIEW = "ge.telasi.recut.guiapp.views.InspCpHistoryView"; //$NON-NLS-1$

private InstCpAfter2003Pane instCpAfter2003Pane1;

Composite composite1;

public InspCpHistoryView()
{
	super();
}

public void createPartControl(Composite parent)
{
	FillLayout parentLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
	parent.setLayout(parentLayout);
	composite1 = new Composite(parent, SWT.NULL);
	GridLayout composite1Layout = new GridLayout();
	composite1.setLayout(composite1Layout);
	{
		GridData instCpAfter2003Pane1LData = new GridData();
		instCpAfter2003Pane1LData.horizontalAlignment = GridData.FILL;
		instCpAfter2003Pane1LData.grabExcessHorizontalSpace = true;
		instCpAfter2003Pane1LData.verticalAlignment = GridData.FILL;
		instCpAfter2003Pane1LData.grabExcessVerticalSpace = true;
		instCpAfter2003Pane1 = new InstCpAfter2003Pane(composite1, SWT.NONE);
		instCpAfter2003Pane1.setLayoutData(instCpAfter2003Pane1LData);
	}
	composite1Layout.horizontalSpacing = 0;
	composite1Layout.marginHeight = 0;
	composite1Layout.marginWidth = 0;
	composite1Layout.verticalSpacing = 0;
	setPartName(GUIMessages.getMessage("application.action.instcp_history"));
}

boolean firstFocused = true;

public void setFocus()
{
	if (firstFocused) {
		instCpAfter2003Pane1.refresh();
		firstFocused = false;
	}
}

public void dispose()
{
	super.dispose();
}

}
