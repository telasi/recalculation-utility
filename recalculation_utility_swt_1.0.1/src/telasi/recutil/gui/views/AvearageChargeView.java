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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.gui.comp.avrgcharge.SimpleAvrgChargeCalcPane;
import telasi.recutil.gui.plugin.Plugin;
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
public class AvearageChargeView extends ViewPart
{

public static final String ID_VIEW = "ge.telasi.recut.guiapp.views.AvearageChargeView"; //$NON-NLS-1$

private ToolItem tiCalculate;

private SimpleAvrgChargeCalcPane simpleAvrgChargeCalcPane1;

private ToolBar toolBar1;

private Composite composite1;

public AvearageChargeView()
{
	super();
}

public void createPartControl(Composite parent)
{
	parent.setSize(571, 389);
	composite1 = new Composite(parent, SWT.NULL);
	GridLayout composite1Layout = new GridLayout();
	composite1Layout.horizontalSpacing = 0;
	composite1Layout.marginHeight = 0;
	composite1Layout.marginWidth = 0;
	composite1Layout.verticalSpacing = 0;
	composite1.setLayout(composite1Layout);
	{
		GridData toolBar1LData = new GridData();
		toolBar1LData.horizontalAlignment = GridData.FILL;
		toolBar1LData.grabExcessHorizontalSpace = true;
		toolBar1 = new ToolBar(composite1, SWT.NONE);
		toolBar1.setLayoutData(toolBar1LData);
		{
			tiCalculate = new ToolItem(toolBar1, SWT.NONE);
			tiCalculate.setToolTipText(GUIMessages.getMessage("comp.avrg_charge_view.calculate"));
			tiCalculate.setImage(Plugin.getImage("icons/22x22/recalc.png"));
			tiCalculate.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent e)
				{
					onCalculate();
				}
			});
		}
	}
	{
		GridData simpleAvrgChargeCalcPane1LData = new GridData();
		simpleAvrgChargeCalcPane1LData.horizontalAlignment = GridData.FILL;
		simpleAvrgChargeCalcPane1LData.grabExcessHorizontalSpace = true;
		simpleAvrgChargeCalcPane1LData.verticalAlignment = GridData.FILL;
		simpleAvrgChargeCalcPane1LData.grabExcessVerticalSpace = true;
		simpleAvrgChargeCalcPane1 = new SimpleAvrgChargeCalcPane(composite1, SWT.NONE);
		simpleAvrgChargeCalcPane1.setLayoutData(simpleAvrgChargeCalcPane1LData);
	}
	//$protect>>$
	customInit();
	//$protect<<$
}

private void customInit()
{
	setPartName(GUIMessages.getMessage("application.action.avrg_day_chagre_calc"));
	validateView();
}

public void setFocus()
{
}

public void dispose()
{
	super.dispose();
}

private void validateView()
{
}

private void onCalculate()
{
	simpleAvrgChargeCalcPane1.calculate();
}

}
