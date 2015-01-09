package telasi.recutil.gui.comp.cust.histories;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.custhistory.AccountTariffs;
import telasi.recutil.beans.custhistory.TariffHistoryRecord;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.service.eclipse.custhistory.CustomerTariffRequest;

public class CustomerTariffHistoryPane extends Composite implements
		ICustomerHistory {

	private TableTreeViewer viewer;
	private Customer customer;

	public CustomerTariffHistoryPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(457, 363);
			{
				GridData tableTreeViewer1LData = new GridData();
				tableTreeViewer1LData.horizontalAlignment = GridData.FILL;
				tableTreeViewer1LData.grabExcessHorizontalSpace = true;
				tableTreeViewer1LData.verticalAlignment = GridData.FILL;
				tableTreeViewer1LData.grabExcessVerticalSpace = true;
				viewer = new TableTreeViewer(this, SWT.BORDER
						| SWT.FULL_SELECTION);
				viewer.getControl().setLayoutData(tableTreeViewer1LData);
			}
			{
				Table table = viewer.getTableTree().getTable();
				table.setLinesVisible(true);
				table.setHeaderVisible(true);

				TableColumn colExpand = new TableColumn(table, SWT.NONE);
				colExpand.setWidth(30);
				colExpand.setResizable(false);

				TableColumn colImage = new TableColumn(table, SWT.NONE);
				colImage.setWidth(25);
				colImage.setResizable(false);

				TableColumn colAccount = new TableColumn(table, SWT.NONE);
				colAccount.setWidth(100);
				colAccount.setText(GUIMessages
						.getMessage("comp.general.account"));

				TableColumn colStart = new TableColumn(table, SWT.NONE);
				colStart.setWidth(100);
				colStart.setText(GUIMessages
						.getMessage("comp.general.start_date"));

				TableColumn colEnd = new TableColumn(table, SWT.NONE);
				colEnd.setWidth(100);
				colEnd.setText(GUIMessages.getMessage("comp.general.end_date"));

				TableColumn colTariffType = new TableColumn(table, SWT.NONE);
				colTariffType.setWidth(100);
				colTariffType.setText(GUIMessages
						.getMessage("comp.general.type"));

				TableColumn colTariff = new TableColumn(table, SWT.NONE);
				colTariff.setWidth(150);
				colTariff
						.setText(GUIMessages.getMessage("comp.general.tariff"));

				TableColumn colDetails = new TableColumn(table, SWT.NONE);
				colDetails.setWidth(200);
				colDetails.setText(GUIMessages
						.getMessage("comp.general.details"));

			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void refresh() {
		if (customer == null) {
			display(null);
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			CustomerTariffRequest request = new CustomerTariffRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setCustomer(customer);
			DefaultEJBResponse response = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			CustomerTariffRequest callback = (CustomerTariffRequest) response
					.getRequest();
			List items = callback.getHistory();
			for (int i = 0; items != null && i < items.size(); i++) {
				AccountTariffs tariffs = (AccountTariffs) items.get(i);
				for (int j = 0; tariffs.getTariffs() != null
						&& j < tariffs.getTariffs().size(); j++) {
					TariffHistoryRecord record = (TariffHistoryRecord) tariffs
							.getTariffs().get(j);
					record.setTariff(Cache.findTariffById(record.getTariff()
							.getId()));
				}
			}
			display(items);
		} catch (Throwable t) {
			// t.printStackTrace();
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
			display(null);
		}
	}

	private void initDataBehaivour() {
		display(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new TariffHistoryLabelProvider());
	}

	private void display(List items) {
		viewer.setContentProvider(new TariffHistoryContentProvider(items));
	}

	private class TariffHistoryContentProvider implements ITreeContentProvider {

		private List accTariffs;

		private Map PARENTS = new HashMap();

		@SuppressWarnings("unchecked")
		public TariffHistoryContentProvider(List accTariffs) {
			this.accTariffs = accTariffs;
			for (int i = 0; accTariffs != null && i < accTariffs.size(); i++) {
				AccountTariffs t = (AccountTariffs) accTariffs.get(i);
				PARENTS.put(new Integer(t.getAccount().getId()), t);
			}
		}

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof AccountTariffs) {
				AccountTariffs accTariffs = (AccountTariffs) parentElement;
				return accTariffs.getTariffs() == null ? new Object[] {}
						: accTariffs.getTariffs().toArray();
			} else {
				return new Object[] {};
			}
		}

		public Object getParent(Object element) {
			if (element instanceof TariffHistoryRecord) {
				TariffHistoryRecord rec = (TariffHistoryRecord) element;
				return PARENTS.get(new Integer(rec.getAccount().getId()));
			} else {
				return null;
			}
		}

		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}

		public Object[] getElements(Object inputElement) {
			return accTariffs == null ? new Object[] {} : accTariffs.toArray();
		}

		public void dispose() {
			if (accTariffs != null) {
				accTariffs.clear();
				accTariffs = null;
			}
			if (PARENTS != null) {
				PARENTS.clear();
				PARENTS = null;
			}
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class TariffHistoryLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			case 1:
				if (element instanceof AccountTariffs) {
					return Plugin.getImage("icons/16x16/bop/power.png");
				} else {
					// return Plugin.getImage("icons/16x16/bop/payment.png");
				}
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return "";
			}
			if (element instanceof AccountTariffs) {
				AccountTariffs tariffs = (AccountTariffs) element;
				switch (columnIndex) {
				case 2:
					return tariffs.getAccount().getNumber();
				default:
					return "";
				}
			}
			if (element instanceof TariffHistoryRecord) {
				TariffHistoryRecord record = (TariffHistoryRecord) element;
				switch (columnIndex) {
				case 3:
					return Date.format(record.getStartDate());
				case 4:
					return Date.format(record.getEndDate());
				case 5:
					return GUITranslator.GEO_ASCII_TO_KA(record.getTypeName()
							.trim());
				case 6:
					return GUITranslator.GEO_ASCII_TO_KA(record.getTariff()
							.getName().trim());
				case 7:
					return record.getTariff().toString();
				default:
					return "";
				}
			}
			return "";
		}

	}

}
