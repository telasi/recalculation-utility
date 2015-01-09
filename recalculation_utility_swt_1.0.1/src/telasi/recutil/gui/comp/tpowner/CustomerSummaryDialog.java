package telasi.recutil.gui.comp.tpowner;

import java.sql.Date;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Item;
import telasi.recutil.beans.Operation;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.tpowner.TpOwnerRecalcCustomerSummaryRequest;

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
public class CustomerSummaryDialog extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Label label1;
	private TableViewer tblResults;
	private TableColumn tableColumn1;
	private Button button1;
	private Composite composite2;
	private Combo cmbCustomers;
	private Button btnPrint;
	private TableColumn colGel;
	private TableColumn colKwh;
	private TableColumn colOperation;
	private Label lblCycleDate;
	private Label label3;
	private Label label2;

	private List<Customer> customers;
	private Date cycleDate;
	
	public CustomerSummaryDialog(Shell parent, int style) {
		super(parent, style);
	}
	
	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
			GridLayout dialogShellLayout = new GridLayout();
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(600, 300);
			dialogShell.setText(GUIMessages.getMessage("comp.tpowner.customerSummary.title"));
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 3;
				composite1.setLayout(composite1Layout);
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setBounds(12, 12, 425, 52);
				{
					label3 = new Label(composite1, SWT.RIGHT);
					GridData label3LData = new GridData();
					label3LData.horizontalAlignment = GridData.FILL;
					label3.setLayoutData(label3LData);
					label3.setText(GUIMessages.getMessage("comp.general.date") + ":");
				}
				{
					lblCycleDate = new Label(composite1, SWT.NONE);
					GridData label4LData = new GridData();
					label4LData.horizontalAlignment = GridData.FILL;
					label4LData.grabExcessHorizontalSpace = true;
					lblCycleDate.setLayoutData(label4LData);
				}
				{
					label2 = new Label(composite1, SWT.NONE);
				}
				{
					label1 = new Label(composite1, SWT.RIGHT);
					GridData label1LData = new GridData();
					label1LData.horizontalAlignment = GridData.FILL;
					label1.setLayoutData(label1LData);
					label1.setText(GUIMessages.getMessage("comp.general.customer") + ":");
				}
				{
					GridData cmbCustomersLData = new GridData();
					cmbCustomersLData.horizontalAlignment = GridData.FILL;
					cmbCustomersLData.grabExcessHorizontalSpace = true;
					cmbCustomers = new Combo(composite1, SWT.READ_ONLY);
					cmbCustomers.setLayoutData(cmbCustomersLData);
					cmbCustomers.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							refresh();
						}
					});
				}
				{
					btnPrint = new Button(composite1, SWT.PUSH | SWT.CENTER);
					btnPrint.setText("Print...");
					btnPrint.setImage(Plugin.getImage("icons/22x22/print.png"));
					btnPrint.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							onPrint();
						}
					});
				}
			}
			{
				GridData tableViewer1LData = new GridData();
				tableViewer1LData.horizontalAlignment = GridData.FILL;
				tableViewer1LData.grabExcessHorizontalSpace = true;
				tableViewer1LData.verticalAlignment = GridData.FILL;
				tableViewer1LData.grabExcessVerticalSpace = true;
				tblResults = new TableViewer(dialogShell, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION| SWT.VIRTUAL);
				tblResults.getControl().setLayoutData(tableViewer1LData);
				tblResults.getTable().setHeaderVisible(true);
				tblResults.getTable().setLinesVisible(true);
				{
					colOperation = new TableColumn(tblResults.getTable(), SWT.NONE);
					colOperation.setText(GUIMessages.getMessage("comp.general.operation"));
					colOperation.setWidth(300);
				}
				{
					colKwh = new TableColumn(tblResults.getTable(), SWT.RIGHT);
					colKwh.setText(GUIMessages.getMessage("comp.general.kwh"));
					colKwh.setWidth(100);
				}
				{
					colGel = new TableColumn(tblResults.getTable(), SWT.RIGHT);
					colGel.setText(GUIMessages.getMessage("comp.general.gel"));
					colGel.setWidth(100);
				}
				{
					tableColumn1 = new TableColumn(tblResults.getTable(), SWT.NONE);
					tableColumn1.setWidth(10);
					tableColumn1.setResizable(false);
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					button1 = new Button(composite2, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1LData.grabExcessHorizontalSpace = true;
					button1LData.horizontalAlignment = GridData.END;
					button1.setLayoutData(button1LData);
					button1.setText(GUIMessages.getMessage("comp.general.close"));
					button1.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							dialogShell.dispose();
						}
					});
				}
			}

			// ----
			initDialog();
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
	
	private void initDialog() {
		GUIUtils.centerShell(dialogShell);
		initDataBehoivour();
		if (cycleDate != null) {
			telasi.recutil.beans.Date dt = telasi.recutil.beans.Date.create(cycleDate);
			lblCycleDate.setText(telasi.recutil.beans.Date.format(dt));
		} else {
			lblCycleDate.setText("?");
		}
		if (customers != null) {
			for (Customer customer : customers) {
				String name = GUITranslator.GEO_ASCII_TO_KA(customer.getName());
				String numb = GUITranslator.GEO_ASCII_TO_KA(customer.getNumber());
				String text = numb + " - " + name;
				cmbCustomers.add(text);
			}
			cmbCustomers.select(0);
			refresh();
		}
	}
	
	private void refresh() {
		Customer customer = customers.get(cmbCustomers.getSelectionIndex());
		TpOwnerRecalcCustomerSummaryRequest request = new TpOwnerRecalcCustomerSummaryRequest(Application.USER_NAME, Application.PASSWORD);
		request.setCustomer(customer);
		request.setCycleDate(cycleDate);
		try {
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			TpOwnerRecalcCustomerSummaryRequest callback = (TpOwnerRecalcCustomerSummaryRequest) resp.getRequest();
			List items = callback.getItems();
			for (int i = 0; i < items.size(); i++) {
				Item item = (Item) items.get(i);
				Operation oper = Cache.findOperationById(item.getOperation().getId());
				item.setOperation(oper);
			}
			displayItems(items);
		} catch (Exception ex) {
			MessageDialog.openError(getParent(), "Error", ex.toString());
			ex.printStackTrace();
		}
	}
	
	private void initDataBehoivour() {
		displayItems(null);
		tblResults.setInput(this);
		tblResults.setLabelProvider(new ItemLabelProvider());
	}
	
	private void displayItems(List items) {
		tblResults.getTable().setItemCount(items == null ? 0 : items.size());
		tblResults.setContentProvider(new CommonItemContentProvider(items));
	}
	
	public Date getCycleDate() {
		return cycleDate;
	}
	
	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}
	
	public List<Customer> getCustomers() {
		return customers;
	}
	
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	private void onPrint() {
		// TODO: implement print
	}
	
	private class ItemLabelProvider extends LabelProvider implements ITableLabelProvider {
		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			Item item = (Item) element;
			switch (columnIndex) {
			case 0:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation().getName());
			case 1:
				return nf.format(item.getKwh());
			case 2:
				return nf.format(item.getGel());
			default:
				return "";
			}
		}
	}
	
}
