package telasi.recutil.gui.comp.tpowner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;

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
public class TransformatorSelector extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private TableViewer tblTypes;
	private Button btnClose;
	private Button btnOk;
	private Composite composite2;
	private TransformatorType transformatorType;

	public TransformatorSelector(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.horizontalSpacing = 0;
			dialogShellLayout.marginHeight = 0;
			dialogShellLayout.marginWidth = 0;
			dialogShellLayout.verticalSpacing = 0;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(350, 300);
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1.setLayout(composite1Layout);
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1.setLayoutData(composite1LData);
				{
					GridData tblTransTypesLData = new GridData();
					tblTransTypesLData.horizontalAlignment = GridData.FILL;
					tblTransTypesLData.verticalAlignment = GridData.FILL;
					tblTransTypesLData.grabExcessVerticalSpace = true;
					tblTransTypesLData.grabExcessHorizontalSpace = true;
					tblTypes = new TableViewer(composite1, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.VIRTUAL);
					tblTypes.getControl().setLayoutData(tblTransTypesLData);
					tblTypes.getTable().setLinesVisible(true);
					tblTypes.getTable().setHeaderVisible(true);
				}
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.numColumns = 2;
					GridData composite2LData = new GridData();
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					{
						btnOk = new Button(composite2, SWT.PUSH | SWT.CENTER);
						GridData btnOkLData = new GridData();
						btnOkLData.widthHint = 75;
						btnOkLData.grabExcessHorizontalSpace = true;
						btnOkLData.horizontalAlignment = GridData.END;
						btnOk.setLayoutData(btnOkLData);
						btnOk.setText("OK");
						btnOk.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								onOk();
							}
						});
					}
					{
						btnClose = new Button(composite2, SWT.PUSH | SWT.CENTER);
						GridData btnCloseLData = new GridData();
						btnCloseLData.widthHint = 75;
						btnClose.setLayoutData(btnCloseLData);
						btnClose.setText("Close");
						btnClose.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								onClose();
							}
						});
					}
				}
				{
    				TableColumn column = new TableColumn(tblTypes.getTable(), SWT.NONE);
    				column.setResizable(false);
    				column.setWidth(25);
    			}
    			{
    				TableColumn colName = new TableColumn(tblTypes.getTable(), SWT.NONE);
    				colName.setText(GUIMessages.getMessage("comp.general.name"));
    				colName.setWidth(100);
    			}
    			{
    				TableColumn colPower = new TableColumn(tblTypes.getTable(), SWT.RIGHT);
    				colPower.setText(GUIMessages.getMessage("comp.general.power_kva"));
    				colPower.setWidth(150);
    			}
//    			{
//    				TableColumn colZeroLoss = new TableColumn(tblTypes.getTable(), SWT.RIGHT);
//    				colZeroLoss.setText(GUIMessages.getMessage("comp.general.zero_loss"));
//    				colZeroLoss.setWidth(150);
//    			}
    			{
    				TableColumn colDummy1 = new TableColumn(tblTypes.getTable(), SWT.NONE);
    				colDummy1.setWidth(10);
    				colDummy1.setResizable(false);
    			}
			}
			//$protect>>$
			customInit();
			//$protect<<$
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

	private void customInit() {
		initDataBehaiviour();
		GUIUtils.centerShell(dialogShell);
		refresh();
		dialogShell.setText("Select Transformator Type");
		dialogShell.setDefaultButton(btnOk);
		preselect();
	}

	private void preselect() {
		tblTypes.getTable().deselectAll();
		if (transformatorType != null) {
			CommonItemContentProvider provider = (CommonItemContentProvider) tblTypes.getContentProvider();
			for (int i = 0; provider.getFullItems() != null && i < provider.getFullItems().size(); i++) {
				TransformatorType type = (TransformatorType) provider.getFullItems().get(i);
				if (type.getId() == transformatorType.getId()) {
					tblTypes.getTable().select(i);
					break;
				}
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
    		MessageDialog.openError(dialogShell, "Error", ex.toString());
    	}
    }
	
	private void initDataBehaiviour() {
    	displayTransTypes(null);
    	tblTypes.setInput(this);
    	tblTypes.setLabelProvider(new TransTypeLabelProvider());
    }

    private void displayTransTypes(List items) {
		tblTypes.getTable().setItemCount(items == null ? 0 : items.size());
		tblTypes.setContentProvider(new CommonItemContentProvider(items));
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
				//return nf.format(type.getZeroLoss());
			default:
				return "";
			}
		}
	}


    public TransformatorType getTransformatorType() {
		return transformatorType;
	}


    public void setTransformatorType(TransformatorType transformatorType) {
		this.transformatorType = transformatorType;
	}

    private void onOk() {
    	int index = tblTypes.getTable().getSelectionIndex();
    	if (index == -1) {
    		return;
    	}
    	CommonItemContentProvider provider = (CommonItemContentProvider) tblTypes.getContentProvider();
    	transformatorType = (TransformatorType) provider.getFullItems().get(index);
    	dialogShell.dispose();
    }

    private void onClose() {
    	transformatorType = null;
    	dialogShell.dispose();
    }
}
