package telasi.recutil.gui.comp.tpowner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.gui.comp.cust.AccountPane;
import telasi.recutil.gui.comp.cust.CustomerSearchPane;
import telasi.recutil.gui.comp.cust.CustomerSimplePane;
import telasi.recutil.gui.utils.GUIMessages;

public class TpownerView extends ViewPart {
    public static final String ID_VIEW = "telasi.recutil.gui.comp.tpowner.TpownerView"; //$NON-NLS-1$
    private AccountPane accountPane1;
    private TpOwnerPane tpOwnerPane1;
    private Label label2;
    private CustomerSimplePane customerSimplePane1;
    private Label label1;
    private CustomerSearchPane customerSearchPane1;
    private Composite composite3;
    private Composite composite2;
    private SashForm sashForm1;

    Composite composite1;

    public TpownerView() {
        super();
    }

    public void createPartControl(Composite parent) {
    	{
	    	parent.setSize(585, 448);
    	}
        composite1 = new Composite(parent, SWT.NULL);
        GridLayout composite1Layout = new GridLayout();
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
        		composite2 = new Composite(sashForm1, SWT.BORDER);
        		GridLayout composite2Layout = new GridLayout();
        		composite2Layout.marginWidth = 0;
        		composite2Layout.marginHeight = 0;
        		composite2Layout.verticalSpacing = 0;
        		composite2.setLayout(composite2Layout);
        		composite2.setBounds(13, 59, 585, 448);
        		{
        			GridData customerSearchPane1LData = new GridData();
        			customerSearchPane1LData.horizontalAlignment = GridData.FILL;
        			customerSearchPane1LData.grabExcessHorizontalSpace = true;
        			customerSearchPane1 = new CustomerSearchPane(composite2, SWT.NONE);
        			customerSearchPane1.setLayoutData(customerSearchPane1LData);
        		}
        		{
        			label1 = new Label(composite2, SWT.SEPARATOR | SWT.HORIZONTAL);
        			GridData label1LData = new GridData();
        			label1LData.horizontalAlignment = GridData.FILL;
        			label1LData.grabExcessHorizontalSpace = true;
        			label1.setLayoutData(label1LData);
        			label1.setText("label1");
        		}
        		{
        			GridData customerSimplePane1LData = new GridData();
        			customerSimplePane1LData.horizontalAlignment = GridData.FILL;
        			customerSimplePane1LData.grabExcessHorizontalSpace = true;
        			customerSimplePane1 = new CustomerSimplePane(composite2, SWT.NONE);
        			customerSimplePane1.setLayoutData(customerSimplePane1LData);
        		}
        		{
        			label2 = new Label(composite2, SWT.HORIZONTAL | SWT.SEPARATOR);
        			GridData label2LData = new GridData();
        			label2LData.horizontalAlignment = GridData.FILL;
        			label2LData.grabExcessHorizontalSpace = true;
        			label2.setLayoutData(label2LData);
        			label2.setText("label2");
        		}
        		{
        			GridData accountPane1LData = new GridData();
        			accountPane1LData.horizontalAlignment = GridData.FILL;
        			accountPane1LData.grabExcessHorizontalSpace = true;
        			accountPane1LData.verticalAlignment = GridData.FILL;
        			accountPane1LData.grabExcessVerticalSpace = true;
        			accountPane1 = new AccountPane(composite2, SWT.NONE);
        			accountPane1.setLayoutData(accountPane1LData);
        		}
        	}
        	{
        		composite3 = new Composite(sashForm1, SWT.BORDER);
        		GridLayout composite3Layout = new GridLayout();
        		composite3Layout.marginHeight = 0;
        		composite3Layout.marginWidth = 0;
        		composite3.setLayout(composite3Layout);
        		{
        			GridData tpOwnerPane1LData = new GridData();
        			tpOwnerPane1LData.horizontalAlignment = GridData.FILL;
        			tpOwnerPane1LData.grabExcessHorizontalSpace = true;
        			tpOwnerPane1LData.verticalAlignment = GridData.FILL;
        			tpOwnerPane1LData.grabExcessVerticalSpace = true;
        			tpOwnerPane1 = new TpOwnerPane(composite3, SWT.NONE);
        			tpOwnerPane1.setLayoutData(tpOwnerPane1LData);
        		}
        	}
        }
        //$protect>>$
        customInit();
        //$protect<<$
    }

    private void customInit() {
    	setPartName(GUIMessages.getMessage("comp.tpowner.title"));
    	sashForm1.setWeights(new int[] {1, 2});
    	customerSearchPane1.setRelatedCustomerPane(customerSimplePane1);
    	customerSearchPane1.setRelatedAccountPane(accountPane1);
    	accountPane1.setRelatedTpownerPage(tpOwnerPane1);
    }

    public void setFocus() {
    }
    
    public void dispose() {
        super.dispose();
    }
}
