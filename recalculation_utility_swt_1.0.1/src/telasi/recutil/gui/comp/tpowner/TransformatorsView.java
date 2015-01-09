package telasi.recutil.gui.comp.tpowner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.beans.tpowner.LossItem;
import telasi.recutil.beans.tpowner.TransformatorType;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.tpowner.TransformatorTypeSelectRequest;

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
public class TransformatorsView extends ViewPart {
    public static final String ID_VIEW = "telasi.recutil.gui.comp.tpowner.TransformatorsView"; //$NON-NLS-1$
    private TableColumn colDummy1;
    private TableColumn colDummy2;
    private TableColumn colLoss;
    private TableColumn colKwhInterval;
    private TableColumn colPercentInterval;
    private TableViewer tblLosses;
    private TableColumn colZeroLoss;
    private TableColumn colPower;
    private TableColumn colName;
    private TableViewer tblTypes;
    private Label lblDescription;
    private Label label1;
    private Label lblTitle;
    private Composite composite5;
    private Label lblImage;
    private Composite composite4;
    private Composite composite3;
    private Composite composite2;
    private SashForm sashForm1;

    private Composite composite1;

    public TransformatorsView() {
        super();
    }

    public void createPartControl(Composite parent) {
    	{
	    	parent.setSize(548, 364);
    	}
        composite1 = new Composite(parent, SWT.NULL);
        GridLayout composite1Layout = new GridLayout();
        composite1Layout.marginWidth = 0;
        composite1Layout.verticalSpacing = 0;
        composite1Layout.marginHeight = 0;
        composite1Layout.horizontalSpacing = 0;
        composite1.setLayout(composite1Layout);
        {
        	composite4 = new Composite(composite1, SWT.NONE);
        	GridLayout composite4Layout = new GridLayout();
        	composite4Layout.numColumns = 2;
        	GridData composite4LData = new GridData();
        	composite4LData.horizontalAlignment = GridData.FILL;
        	composite4LData.grabExcessHorizontalSpace = true;
        	composite4.setLayoutData(composite4LData);
        	composite4.setLayout(composite4Layout);
        	{
        		GridData lblImageLData = new GridData();
        		lblImageLData.heightHint = 48;
        		lblImageLData.widthHint = 48;
        		lblImage = new Label(composite4, SWT.NONE);
        		lblImage.setLayoutData(lblImageLData);
        		lblImage.setImage(Plugin.getImage("icons/48x48/energy.png"));
        	}
        	{
        		composite5 = new Composite(composite4, SWT.NONE);
        		GridLayout composite5Layout = new GridLayout();
        		composite5Layout.marginWidth = 0;
        		composite5Layout.marginHeight = 0;
        		GridData composite5LData = new GridData();
        		composite5LData.horizontalAlignment = GridData.FILL;
        		composite5LData.grabExcessHorizontalSpace = true;
        		composite5LData.verticalAlignment = GridData.FILL;
        		composite5LData.grabExcessVerticalSpace = true;
        		composite5.setLayoutData(composite5LData);
        		composite5.setLayout(composite5Layout);
        		{
        			lblTitle = new Label(composite5, SWT.NONE);
        			lblTitle.setText(GUIMessages.getMessage("comp.tpowner.transformator.title"));
        			lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
        		}
        		{
        			label1 = new Label(composite5, SWT.SEPARATOR | SWT.HORIZONTAL);
        			GridData label1LData = new GridData();
        			label1LData.horizontalAlignment = GridData.FILL;
        			label1LData.grabExcessHorizontalSpace = true;
        			label1.setLayoutData(label1LData);
        			label1.setText("label1");
        		}
        		{
        			lblDescription = new Label(composite5, SWT.WRAP);
        			GridData lblDescriptionLData = new GridData();
        			lblDescriptionLData.horizontalAlignment = GridData.FILL;
        			lblDescriptionLData.grabExcessHorizontalSpace = true;
        			lblDescription.setLayoutData(lblDescriptionLData);
        			lblDescription.setText(GUIMessages.getMessage("comp.tpowner.transformator.descr"));
        		}
        	}
        }
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
        		composite2Layout.makeColumnsEqualWidth = true;
        		composite2.setLayout(composite2Layout);
        		composite2.setBounds(7, 73, 548, 354);
        		{
        			GridData tblTypesLData = new GridData();
        			tblTypesLData.horizontalAlignment = GridData.FILL;
        			tblTypesLData.grabExcessHorizontalSpace = true;
        			tblTypesLData.verticalAlignment = GridData.FILL;
        			tblTypesLData.grabExcessVerticalSpace = true;
        			tblTypes = new TableViewer(composite2, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.VIRTUAL);
        			tblTypes.getControl().setLayoutData(tblTypesLData);
        			tblTypes.getTable().setLinesVisible(true);
        			tblTypes.getTable().setHeaderVisible(true);
        			{
        				TableColumn column = new TableColumn(tblTypes.getTable(), SWT.NONE);
        				column.setResizable(false);
        				column.setWidth(25);
        			}
        			{
        				colName = new TableColumn(tblTypes.getTable(), SWT.NONE);
        				colName.setText(GUIMessages.getMessage("comp.general.name"));
        				colName.setWidth(100);
        			}
        			{
        				colPower = new TableColumn(tblTypes.getTable(), SWT.RIGHT);
        				colPower.setText(GUIMessages.getMessage("comp.general.power_kva"));
        				colPower.setWidth(100);
        			}
        			{
        				colZeroLoss = new TableColumn(tblTypes.getTable(), SWT.RIGHT);
        				colZeroLoss.setText(GUIMessages.getMessage("comp.general.zero_loss"));
        				colZeroLoss.setWidth(150);
        			}
        			{
        				colDummy1 = new TableColumn(tblTypes.getTable(), SWT.NONE);
        				colDummy1.setWidth(10);
        				colDummy1.setResizable(false);
        			}
        		}
        	}
        	{
        		composite3 = new Composite(sashForm1, SWT.BORDER);
        		GridLayout composite3Layout = new GridLayout();
        		composite3Layout.makeColumnsEqualWidth = true;
        		composite3.setLayout(composite3Layout);
        		{
        			GridData tblLossesLData = new GridData();
        			tblLossesLData.horizontalAlignment = GridData.FILL;
        			tblLossesLData.grabExcessHorizontalSpace = true;
        			tblLossesLData.verticalAlignment = GridData.FILL;
        			tblLossesLData.grabExcessVerticalSpace = true;
        			tblLosses = new TableViewer(composite3, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL | SWT.MULTI);
        			tblLosses.getControl().setLayoutData(tblLossesLData);
        			tblLosses.getTable().setHeaderVisible(true);
        			tblLosses.getTable().setLinesVisible(true);
        			{
        				colPercentInterval = new TableColumn(tblLosses.getTable(), SWT.CENTER);
        				colPercentInterval.setText(GUIMessages.getMessage("comp.tpowner.transformator.busy_percent"));
        				colPercentInterval.setWidth(200);
        			}
        			{
        				colKwhInterval = new TableColumn(tblLosses.getTable(), SWT.CENTER);
        				colKwhInterval.setText(GUIMessages.getMessage("comp.tpowner.transformator.busy_kwh"));
        				colKwhInterval.setWidth(200);
        			}
        			{
        				colLoss = new TableColumn(tblLosses.getTable(), SWT.RIGHT);
        				colLoss.setText(GUIMessages.getMessage("comp.tpowner.transformator.loss_percent"));
        				colLoss.setWidth(150);
        			}
        			{
        				colDummy2 = new TableColumn(tblLosses.getTable(), SWT.NONE);
        				colDummy2.setWidth(10);
        				colDummy2.setResizable(false);
        			}
        		}
        	}
        }
        //$protect>>$
        customInit();
        //$protect<<$
    }

    private void customInit() {
    	setPartName(GUIMessages.getMessage("comp.tpowner.transformator.title"));
    	sashForm1.setWeights(new int[] {2, 3});
    	initDataBehaiviour();
    	refresh();
    	tblTypes.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent arg0) {
				int index = tblTypes.getTable().getSelectionIndex();
				if (index == -1) {
					displayLosses(null);
					return;
				}
				CommonItemContentProvider provider = (CommonItemContentProvider) tblTypes.getContentProvider();
				TransformatorType type = (TransformatorType) provider.getFullItems().get(index);
				displayLosses(type.getLossItems());
			}
		});
    }

    private void initDataBehaiviour() {
    	displayTransTypes(null);
    	tblTypes.setInput(this);
    	tblTypes.setLabelProvider(new TransTypeLabelProvider());
    	displayLosses(null);
    	tblLosses.setInput(this);
    	tblLosses.setLabelProvider(new LossLabelProvider());
    }

    private void displayTransTypes(List items) {
		tblTypes.getTable().setItemCount(items == null ? 0 : items.size());
		tblTypes.setContentProvider(new CommonItemContentProvider(items));
	}

    private void displayLosses(List items) {
    	tblLosses.getTable().setItemCount(items == null ? 0 : items.size());
    	tblLosses.setContentProvider(new CommonItemContentProvider(items));
    }
    
    
    public void setFocus() {
    }

    public void dispose() {
        super.dispose();
    }

    private class TransTypeLabelProvider extends LabelProvider implements ITableLabelProvider {
		private NumberFormat nf = new DecimalFormat("#,###.##");

		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Plugin.getImage("icons/16x16/energy.png");
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			TransformatorType type = (TransformatorType) element;
			switch (columnIndex) {
			case 1:
				return GUITranslator.GEO_ASCII_TO_KA(type.getName());
			case 2:
				return nf.format(type.getPower());
			case 3:
				return nf.format(type.getZeroLoss());
			default:
				return "";
			}
		}
	}

    private class LossLabelProvider extends LabelProvider implements ITableLabelProvider {
		private NumberFormat nf = new DecimalFormat("#,##0.##");
		private NumberFormat nf2 = new DecimalFormat("#,##0");

		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			LossItem loss = (LossItem) element;
			switch (columnIndex) {
			case 0:
				return nf.format(loss.getBusyFrom() * 100) + "% - " + nf.format(loss.getBusyTo() * 100) + "%";
			case 1:
				double fullPower = loss.getType().getPower() * 720;
				return nf2.format(loss.getBusyFrom() * fullPower) + " - " + nf2.format(loss.getBusyTo() * fullPower);
			case 2:
				return nf.format(loss.getBusyLoss()) + "%";
			default:
				return "";
			}
		}
	}

    private void refresh() {
    	if (!Application.validateConnection()) {
    		return;
    	}
    	TransformatorTypeSelectRequest request = new TransformatorTypeSelectRequest(Application.USER_NAME, Application.PASSWORD);
    	try {
    		DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
    		TransformatorTypeSelectRequest callback = (TransformatorTypeSelectRequest) resp.getRequest();
			displayTransTypes(callback.getTypes());
    	} catch (Exception ex) {
    		MessageDialog.openError(getSite().getShell(), "Error", ex.toString());
    	}
    }

}
