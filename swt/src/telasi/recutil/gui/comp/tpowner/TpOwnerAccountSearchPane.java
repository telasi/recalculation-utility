package telasi.recutil.gui.comp.tpowner;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.tpowner.TpOwnerAccount;
import telasi.recutil.beans.tpowner.TpOwnerRecalc;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.calendar.SWTDatePicker;
import telasi.recutil.service.eclipse.tpowner.TpOwnerAccountsSelectRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerGenerateRecalcRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerSendToItemRequest;

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
public class TpOwnerAccountSearchPane extends org.eclipse.swt.widgets.Composite {
	private Composite composite1;
	private Label label1;
	private TableColumn colStatusId;
	private TableColumn colAccNumber;
	private Label lblTitle;
	private Label lblImage;
	private Composite composite3;
	private Label lblCycleDate;
	private Composite composite2;
	private Label label2;
	private TableColumn colTransType;
	private TableColumn colName;
	private TableColumn colAccId;
	private TableViewer tblAccounts;
	private Button btnSearch;
	private ToolItem tiSendToItem;
	private ToolItem tiSummarizeCustomer;
	private ToolBar toolBar1;
	private Label label4;
	private SWTDatePicker pkCycleDate;
	private TpOwnerRecalcPane recalculationPane;

	// cycle date
	private java.sql.Date cycleDate;

	// recalculations cache
	private static List<TpOwnerRecalc> RECALC_CACHE = new ArrayList<TpOwnerRecalc>();

	public TpOwnerAccountSearchPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehoivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginWidth = 0;
			thisLayout.marginHeight = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(461, 426);
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 32;
					lblImageLData.widthHint = 32;
					lblImage = new Label(composite3, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/32x32/find.png"));
				}
				{
					lblTitle = new Label(composite3, SWT.NONE);
					lblTitle.setText(GUIMessages.getMessage("comp.tpowner.accsearch.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
				}
			}
			{
				label4 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				GridData label4LData = new GridData();
				label4LData.grabExcessHorizontalSpace = true;
				label4LData.horizontalAlignment = GridData.FILL;
				label4.setLayoutData(label4LData);
				label4.setText("label4");
			}
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 3;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					label1 = new Label(composite1, SWT.NONE);
					label1.setText(GUIMessages.getMessage("comp.general.cycle_date"));
				}
				{
					pkCycleDate = new SWTDatePicker(composite1, SWT.BORDER);
				}
				{
					btnSearch = new Button(composite1, SWT.PUSH | SWT.CENTER);
					btnSearch.setToolTipText(GUIMessages.getMessage("comp.tpowner.accsearch.title"));
					btnSearch.setImage(Plugin.getImage("icons/16x16/next.png"));
					btnSearch.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent arg0) {
							onSearch();
						}
					});
				}
			}
			{
				label2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				GridData label2LData = new GridData();
				label2LData.horizontalAlignment = GridData.FILL;
				label2LData.grabExcessHorizontalSpace = true;
				label2.setLayoutData(label2LData);
				label2.setText("n/a");
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2LData.verticalAlignment = GridData.FILL;
				composite2LData.grabExcessVerticalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblCycleDateLData = new GridData();
					lblCycleDateLData.horizontalAlignment = GridData.FILL;
					lblCycleDateLData.grabExcessHorizontalSpace = true;
					lblCycleDate = new Label(composite2, SWT.NONE);
					lblCycleDate.setLayoutData(lblCycleDateLData);
					lblCycleDate.setText("n/a");
				}
				{
					GridData tblCustomersLData = new GridData();
					tblCustomersLData.horizontalAlignment = GridData.FILL;
					tblCustomersLData.grabExcessHorizontalSpace = true;
					tblCustomersLData.verticalAlignment = GridData.FILL;
					tblCustomersLData.grabExcessVerticalSpace = true;
					tblAccounts = new TableViewer(composite2, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION| SWT.VIRTUAL);
					tblAccounts.getControl().setLayoutData(tblCustomersLData);
					tblAccounts.getTable().setHeaderVisible(true);
					tblAccounts.getTable().setLinesVisible(true);
					{
						colStatusId = new TableColumn(tblAccounts.getTable(), SWT.NONE);
						colStatusId.setWidth(25);
						colStatusId.setResizable(false);
					}
					{
						colAccId = new TableColumn(tblAccounts.getTable(), SWT.NONE);
						colAccId.setText(GUIMessages.getMessage("comp.general.account"));
						colAccId.setWidth(75);
					}
					{
						colAccNumber = new TableColumn(tblAccounts.getTable(), SWT.NONE);
						colAccNumber.setText(GUIMessages.getMessage("comp.general.customer"));
						colAccNumber.setWidth(75);
					}
					{
						TableColumn colMetter = new TableColumn(tblAccounts.getTable(), SWT.NONE);
						colMetter.setText(GUIMessages.getMessage("comp.general.meter_number"));
						colMetter.setWidth(75);
					}
					{
						colName = new TableColumn(tblAccounts.getTable(), SWT.NONE);
						colName.setText(GUIMessages.getMessage("comp.general.customer_name"));
						colName.setWidth(100);
					}
					{
						colTransType = new TableColumn(tblAccounts.getTable(), SWT.NONE);
						colTransType.setText(GUIMessages.getMessage("comp.tpowner.transformator"));
						colTransType.setWidth(100);
					}
					tblAccounts.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							onGenerate();
						}
					});
				}
			}
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiSummarizeCustomer = new ToolItem(toolBar1, SWT.NONE);
					tiSummarizeCustomer.setImage(Plugin.getImage("icons/22x22/stock_person.png"));
					tiSummarizeCustomer.setToolTipText(GUIMessages.getMessage("comp.tpowner.viewCustomerSummary"));
					tiSummarizeCustomer.setText(GUIMessages.getMessage("comp.tpowner.customerSummary"));
					tiSummarizeCustomer.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							onViewCustomerSummary();
						}
					});
				}
				{
					tiSendToItem = new ToolItem(toolBar1, SWT.RIGHT);
					tiSendToItem.setToolTipText(GUIMessages.getMessage("comp.tpowner.sendToItem"));
					tiSendToItem.setText(GUIMessages.getMessage("comp.tpowner.send"));
					tiSendToItem.setImage(Plugin.getImage("icons/22x22/document-save.png"));
					tiSendToItem.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							onSendToItem();
						}
					});
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehoivour() {
		displayAccounts(null);
		tblAccounts.setInput(this);
		tblAccounts.setLabelProvider(new TpAccountLabelProvider());
	}

	public void displayAccounts(List accounts) {
		tblAccounts.getTable().setItemCount(accounts == null ? 0 : accounts.size());
		tblAccounts.setContentProvider(new CommonItemContentProvider(accounts));
	}

	private void onSearch() {
		// check existence of search criteria
		if (pkCycleDate.getDate() == null) {
			MessageDialog.openError(getShell(), "Error", "Define search parameters.");
			return;
		}

		// validate connection
		if (!Application.validateConnection()) {
			return;
		}

		// create request
		java.sql.Date date = new java.sql.Date(pkCycleDate.getDate().toDate().getTime());
		TpOwnerAccountsSelectRequest req = new TpOwnerAccountsSelectRequest(Application.USER_NAME, Application.PASSWORD);
		req.setCycleDate(date);
		try {
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(req);
			TpOwnerAccountsSelectRequest callback = (TpOwnerAccountsSelectRequest) resp.getRequest();
			List accounts = callback.getAccounts();
			displayAccounts(accounts);
			this.cycleDate = date;
			String msg = this.cycleDate == null ? "n/a" :
				GUIMessages.getMessage("comp.tpowner.accsearch.current", new Object[] {
						Date.format(Date.create(this.cycleDate))
				});
			lblCycleDate.setText(msg);
			onGenerate();
		} catch (Exception ex) {
			MessageDialog.openError(getShell(), "Error", ex.toString());
			ex.printStackTrace();
		}
	}

	List<Customer> getCalculatedCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		CommonItemContentProvider provider = (CommonItemContentProvider) tblAccounts.getContentProvider();
		List/*TpOwnerAccount*/ accounts = provider.getFullItems();
		for (int i = 0; accounts != null && i < accounts.size(); i++) {
			TpOwnerAccount account = (TpOwnerAccount) provider.getFullItems().get(i);
			if  (account.getStatusId() == TpOwnerAccount.STATUS_CALCULATED) {
				Customer customer = account.getCustomer();
				if (!customers.contains(customer)) {
					customers.add(customer);
				}
			}
		}
		return customers;
	}
	
	private void onViewCustomerSummary() {
		List<Customer> customers = getCalculatedCustomers();
		if (!customers.isEmpty()) {
			CustomerSummaryDialog dialog = new CustomerSummaryDialog(getShell(), SWT.NONE);
			dialog.setCustomers(customers);
			dialog.setCycleDate(cycleDate);
			dialog.open();
		}
	}
	
	private void onSendToItem() {
		List<Customer> customers = getCalculatedCustomers();
		if (!customers.isEmpty()) {
			// validate connection
			if (!Application.validateConnection()) {
				return;
			}
			// show warning
			StringBuilder custList = new StringBuilder();
			for (Customer customer : customers) {
				custList.append("\t* ");
				custList.append(GUITranslator.GEO_ASCII_TO_KA(customer.getNumber()));
				custList.append(" - ");
				custList.append(GUITranslator.GEO_ASCII_TO_KA(customer.getName()));
				custList.append("\n");
			}
			String[] vars = new String[] {custList.toString()};
			String msg = GUIMessages.getMessage("comp.tpowner.sendToItem.warning", vars);
			String title = GUIMessages.getMessage("comp.general.warning");
			MessageDialog.openWarning(getShell(), title, msg);
			// show confirmation
			vars = new String[] {String.valueOf(customers.size())};
			msg = GUIMessages.getMessage("comp.tpowner.sendToItem.confirm", vars);
			title = GUIMessages.getMessage("comp.general.confirm");
			if (MessageDialog.openConfirm(getShell(), title, msg)) {
				TpOwnerSendToItemRequest request = new TpOwnerSendToItemRequest(Application.USER_NAME, Application.PASSWORD);
				request.setCustomers(customers);
				request.setCycleDate(cycleDate);
				try {
					DefaultRecutilClient.processRequest(request);
					onSearch();
				} catch (Exception ex) {
					MessageDialog.openError(getShell(), "Error", ex.toString());
					ex.printStackTrace();
				}
			} 
		}
	}
	
	/**
	 * Generate selected account.
	 */
	private void onGenerate() {
		// generated recalculation
		TpOwnerRecalc recalculation = null;

		// check whether can generate
		int index = tblAccounts.getTable().getSelectionIndex();
		boolean selected = index != -1;
		boolean notmatchingDates = cycleDate != null && pkCycleDate.getDate() != null 
			&& !Date.isEqual(Date.create(cycleDate), pkCycleDate.getDate());

		if (selected && !notmatchingDates) {
			// get selected account
			CommonItemContentProvider provider = (CommonItemContentProvider) tblAccounts.getContentProvider(); 
			TpOwnerAccount account = (TpOwnerAccount) provider.getFullItems().get(index);
			
			// check cycle date existence
			if (cycleDate == null) {
				return;
			}
	
			// validate connection
			if (!Application.validateConnection()) {
				return;
			}

			for (TpOwnerRecalc recalc : RECALC_CACHE) {
				if (recalc.getCycleDate().equals(cycleDate) && recalc.getProducer().getId() == account.getId()) {
					recalc.setProducer(account); // NB: if account is updated!
					recalculation = recalc;
					break;
				}
			}
	
			// make request to database
			if (recalculation == null) {
				// create request
				TpOwnerGenerateRecalcRequest request = new TpOwnerGenerateRecalcRequest(Application.USER_NAME, Application.PASSWORD);
				request.setCycleDate(cycleDate);
				request.setAccount(account);
	
				// send request
				try {
					DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
					TpOwnerGenerateRecalcRequest callback = (TpOwnerGenerateRecalcRequest) resp.getRequest();
					recalculation = callback.getRecalc();
					RECALC_CACHE.add(recalculation);
				} catch (Exception ex) {
					MessageDialog.openError(getShell(), "Error", ex.toString());
					ex.printStackTrace();
				}
			}
		}

		// display recalculation in related recalculation pane
		if (recalculationPane != null) {
			recalculationPane.setRecalculation(recalculation);
		}
	}

	private class TpAccountLabelProvider extends LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			TpOwnerAccount account = (TpOwnerAccount) element;
			switch(columnIndex) {
			case 0:
				return GUIUtils.getTpOwnerStatusImage(account.getStatusId());
			case 4:
				if (account.isHigh()) {
					return Plugin.getImage("icons/16x16/bullet_arrow_up.png");
				} else if (account.isLow()) {
					return Plugin.getImage("icons/16x16/bullet_arrow_down.png");
				} else {
					return Plugin.getImage("icons/16x16/bullet_delete.png");
				}
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			TpOwnerAccount account = (TpOwnerAccount) element;
			switch (columnIndex) {
			case 1:
				return GUITranslator.GEO_ASCII_TO_KA(account.getNumber());
			case 2:
				return GUITranslator.GEO_ASCII_TO_KA(account.getCustomer().getNumber());
			case 3:
				return account.getMeterNumber() == null ? "" : account.getMeterNumber();
			case 4:
				return GUITranslator.GEO_ASCII_TO_KA(account.getCustomer().getName());
			case 5:
				return GUITranslator.GEO_ASCII_TO_KA(account.getTransformatorType().getName());
			default:
				return "";
			}
		}
	}


	public TpOwnerRecalcPane getRecalculationPane() {
		return recalculationPane;
	}

	public void setRecalculationPane(TpOwnerRecalcPane recalculationPane) {
		this.recalculationPane = recalculationPane;
		this.recalculationPane.setSearchPane(this);
	}

	/**
	 * Used for offline status change.
	 */
	protected void setStatusForAccount(int accId, int statusId) {
		List accounts = ((CommonItemContentProvider) tblAccounts.getContentProvider()).getFullItems();
		for (int i = 0; accounts != null && i < accounts.size(); i++) {
			TpOwnerAccount acc = (TpOwnerAccount) accounts.get(i);
			if (acc.getId() == accId) {
				acc.setStatusId(statusId);
				displayAccounts(accounts);
				break;
			}
		}
	}
}
