package telasi.recutil.gui.comp.tpowner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.gui.utils.GUIMessages;

public class TpOwnerCalculationView extends ViewPart {
	public static final String ID_VIEW = "telasi.recutil.gui.comp.tpowner.TpOwnerCalculationView"; //$NON-NLS-1$
	private TpOwnerRecalcPane tpOwnerRecalcPane1;
	private Composite composite2;
	private TpOwnerAccountSearchPane pnCustomerSearch;
	private SashForm sashForm1;

	Composite composite1;

	public TpOwnerCalculationView() {
		super();
	}

	public void createPartControl(Composite parent) {
		{
			parent.setSize(737, 503);
		}
		composite1 = new Composite(parent, SWT.NULL);
		GridLayout composite1Layout = new GridLayout();
		composite1Layout.horizontalSpacing = 0;
		composite1Layout.verticalSpacing = 0;
		composite1Layout.marginWidth = 0;
		composite1Layout.marginHeight = 0;
		composite1.setLayout(composite1Layout);
		{
			sashForm1 = new SashForm(composite1, SWT.NONE);
			GridData sashForm1LData = new GridData();
			sashForm1LData.horizontalAlignment = GridData.FILL;
			sashForm1LData.grabExcessHorizontalSpace = true;
			sashForm1LData.verticalAlignment = GridData.FILL;
			sashForm1LData.grabExcessVerticalSpace = true;
			sashForm1.setLayoutData(sashForm1LData);
			sashForm1.setSize(60, 30);
			{
				pnCustomerSearch = new TpOwnerAccountSearchPane(sashForm1, SWT.BORDER);
				pnCustomerSearch.setBounds(0, 0, 395, 503);
			}
			{
				composite2 = new Composite(sashForm1, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.marginHeight = 0;
				composite2Layout.marginWidth = 0;
				composite2.setLayout(composite2Layout);
				{
					GridData tpOwnerRecalcPane1LData = new GridData();
					tpOwnerRecalcPane1LData.verticalAlignment = GridData.FILL;
					tpOwnerRecalcPane1LData.grabExcessVerticalSpace = true;
					tpOwnerRecalcPane1LData.horizontalAlignment = GridData.FILL;
					tpOwnerRecalcPane1LData.grabExcessHorizontalSpace = true;
					tpOwnerRecalcPane1 = new TpOwnerRecalcPane(composite2, SWT.NONE);
					tpOwnerRecalcPane1.setLayoutData(tpOwnerRecalcPane1LData);
				}
			}
		}
		//$protect>>$
		customInitialization();
		//$protect<<$
	}

	private void customInitialization() {
		setPartName(GUIMessages.getMessage("comp.tpowner.calcview.title"));
		pnCustomerSearch.setRecalculationPane(tpOwnerRecalcPane1);
		sashForm1.setWeights(new int[] {2, 5});
	}
	
	public void setFocus() {
	}

	public void dispose() {
		super.dispose();
	}

}
