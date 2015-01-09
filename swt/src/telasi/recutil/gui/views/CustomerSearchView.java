package telasi.recutil.gui.views;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.gui.comp.cust.search.AdvancedCustomerSearchPane;
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
public class CustomerSearchView extends ViewPart
{
public static final String ID_VIEW = "ge.telasi.recut.guiapp.views.CustomerSearchView"; //$NON-NLS-1$

private AdvancedCustomerSearchPane advancedCustomerSearchPane1;

Composite composite1;

/**
 * 
 */
public CustomerSearchView()
{
	super();
}

public void createPartControl(Composite parent)
{
	{
		FillLayout parentLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
		parent.setLayout(parentLayout);
		parent.setSize(519, 276);
	}
	composite1 = new Composite(parent, SWT.NULL);
	GridLayout composite1Layout = new GridLayout();
	composite1Layout.horizontalSpacing = 0;
	composite1Layout.marginHeight = 0;
	composite1Layout.marginWidth = 0;
	composite1Layout.verticalSpacing = 0;
	composite1.setLayout(composite1Layout);
	{
		GridData advancedCustomerSearchPane1LData = new GridData();
		advancedCustomerSearchPane1LData.horizontalAlignment = GridData.FILL;
		advancedCustomerSearchPane1LData.grabExcessHorizontalSpace = true;
		advancedCustomerSearchPane1LData.verticalAlignment = GridData.FILL;
		advancedCustomerSearchPane1LData.grabExcessVerticalSpace = true;
		advancedCustomerSearchPane1 = new AdvancedCustomerSearchPane(composite1, SWT.NONE);
		advancedCustomerSearchPane1.setLayoutData(advancedCustomerSearchPane1LData);
	}
	setPartName(GUIMessages.getMessage("application.action.customer_search"));
}

public void setFocus()
{
}

public void dispose()
{
	super.dispose();
}

}
