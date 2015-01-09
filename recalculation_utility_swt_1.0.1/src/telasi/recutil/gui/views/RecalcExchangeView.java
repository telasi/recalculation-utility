package telasi.recutil.gui.views;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.gui.comp.exchange.IExchangeNode;
import telasi.recutil.gui.comp.exchange.RecalcExchangePane;
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
/**
 * This view is used for display recalculations.
 * @author dimitri
 *
 */
public class RecalcExchangeView extends ViewPart {
    public static final String ID_VIEW = "telasi.recutil.gui.views.recalcExchangeView"; //$NON-NLS-1$
    public static final String ACTIVE_SECONDARY_ID = "5";
    private RecalcExchangePane pnRecalcImpExpPane;

    Composite composite1;
    
    public RecalcExchangeView() {
        super();
    }

    public void createPartControl(Composite parent) {
    	setPartName(GUIMessages.getMessage("application.action.recalcexchange"));
    	parent.setSize(460, 392);
        composite1 = new Composite(parent, SWT.NULL);
		GridLayout composite1Layout = new GridLayout();
		composite1Layout.horizontalSpacing = 0;
		composite1Layout.marginHeight = 0;
		composite1Layout.marginWidth = 0;
		composite1Layout.verticalSpacing = 0;
        composite1.setLayout(composite1Layout);
		{
			GridData pnRecalcImpExpPaneLData = new GridData();
			pnRecalcImpExpPaneLData.horizontalAlignment = GridData.FILL;
			pnRecalcImpExpPaneLData.grabExcessHorizontalSpace = true;
			pnRecalcImpExpPaneLData.verticalAlignment = GridData.FILL;
			pnRecalcImpExpPaneLData.grabExcessVerticalSpace = true;
			pnRecalcImpExpPane = new RecalcExchangePane(composite1, SWT.NONE);
			pnRecalcImpExpPane.setLayoutData(pnRecalcImpExpPaneLData);
		}

    }

    public void setFocus() {
    }
    
    public void dispose() {
        super.dispose();
    }
    
    public void addSource(IExchangeNode src, List recalcs) {
    	pnRecalcImpExpPane.addSource(src, recalcs);
    }
}
