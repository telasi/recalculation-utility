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
package telasi.recutil.gui.comp.cust;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.Item;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.customer.CustomerHistorySelectRequest;

public class CustomerChargeHistoryPane extends Composite {
	private Composite composite2;
	private TableViewer viewer;
	private TableColumn colDocument;
	private TableColumn colEnterDate;
	private TableColumn colBalance;
	private TableColumn colGel;
	private TableColumn colKwh;
	private TableColumn colReading;
	private TableColumn colIsCycle;
	private TableColumn colOperation;
	private TableColumn colAccount;
	private TableColumn colItemDate;
	private TableColumn colImage;
	private Customer customer;
	private Account accForFilter;
	private boolean filterCahrges = false;

	public CustomerChargeHistoryPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehoivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(593, 391);
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.horizontalSpacing = 0;
				composite2Layout.marginHeight = 0;
				composite2Layout.marginWidth = 0;
				composite2Layout.verticalSpacing = 0;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2LData.verticalAlignment = GridData.FILL;
				composite2LData.grabExcessVerticalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite2, SWT.BORDER
							| SWT.FULL_SELECTION | SWT.VIRTUAL | SWT.MULTI);
					viewer.getControl().setLayoutData(viewerLData);
					viewer.getTable().setHeaderVisible(true);
					viewer.getTable().setLinesVisible(true);
					{
						colImage = new TableColumn(viewer.getTable(), SWT.NONE);
						colImage.setWidth(20);
						colImage.setResizable(false);
					}
					{
						colItemDate = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colItemDate.setText(GUIMessages
								.getMessage("comp.general.itemdate"));
						colItemDate.setWidth(75);
					}
					{
						colAccount = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colAccount.setText(GUIMessages
								.getMessage("comp.general.account"));
						colAccount.setWidth(75);
					}
					{
						colOperation = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colOperation.setText(GUIMessages
								.getMessage("comp.general.operation"));
						colOperation.setWidth(200);
					}
					{
						colIsCycle = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colIsCycle.setWidth(20);
						colIsCycle.setResizable(false);
					}
					{
						colReading = new TableColumn(viewer.getTable(),
								SWT.RIGHT);
						colReading.setText(GUIMessages
								.getMessage("comp.general.reading"));
						colReading.setWidth(75);
					}
					{
						colKwh = new TableColumn(viewer.getTable(), SWT.RIGHT);
						colKwh.setText(GUIMessages
								.getMessage("comp.general.kwh"));
						colKwh.setWidth(75);
					}
					{
						colGel = new TableColumn(viewer.getTable(), SWT.RIGHT);
						colGel.setText(GUIMessages
								.getMessage("comp.general.gel"));
						colGel.setWidth(75);
					}
					{
						colBalance = new TableColumn(viewer.getTable(),
								SWT.RIGHT);
						colBalance.setText(GUIMessages
								.getMessage("comp.general.balance"));
						colBalance.setWidth(75);
					}
					{
						colEnterDate = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colEnterDate.setText(GUIMessages
								.getMessage("comp.general.enterdate"));
						colEnterDate.setWidth(75);
					}
					{
						colDocument = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colDocument.setText(GUIMessages
								.getMessage("comp.general.document"));
						colDocument.setWidth(100);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehoivour() {
		displayItems(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new ItemLabelProvider());
	}

	public void displayItems(List items) {
		List filtered = filter(items);
		viewer.setItemCount(filtered == null ? 0 : filtered.size());
		viewer.setContentProvider(new ItemContentProvider(filtered));
	}

	@SuppressWarnings("unchecked")
	private List filter(List items) {
		if (accForFilter == null) {
			return items;
		} else {
			if (items != null) {
				List filtered = new ArrayList();
				for (int i = 0; i < items.size(); i++) {
					Item item = (Item) items.get(i);
					if (item.getAccount().getId() == accForFilter.getId()) {
						if (!filterCahrges) {
							filtered.add(item);
						} else {
							int operTypeId = item.getOperation().getType()
									.getId();
							boolean reading = operTypeId == 1;
							boolean charge = operTypeId == 2;
							if (reading || charge) {
								filtered.add(item);
							}
						}
					}
				}
				return filtered;
			} else {
				return null;
			}
		}
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void filterByAccount(Account acc) {
		accForFilter = acc;
		filterCahrges = false;
	}

	public void filterByAccountAndCharge(Account acc) {
		accForFilter = acc;
		filterCahrges = true;
	}

	@SuppressWarnings("unchecked")
	public void refresh() {
		if (customer == null) {
			displayItems(null);
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			CustomerHistorySelectRequest request = new CustomerHistorySelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setCustomer(customer);
			DefaultEJBResponse response = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			CustomerHistorySelectRequest callback = (CustomerHistorySelectRequest) response
					.getRequest();
			List items = callback.getHistory();
			if (items != null) {
				List newItems = new ArrayList();
				for (int i = 0; i < items.size(); i++) {
					Item item = (Item) items.get(i);
					item.setOperation(Cache.findOperationById(item
							.getOperation().getId()));
					newItems.add(item);
				}
				items.clear();
				items = null;
				items = newItems;
			}
			displayItems(items);
		} catch (Throwable t) {
			// t.printStackTrace();
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
			displayItems(null);
		}
	}

	private class ItemContentProvider implements IStructuredContentProvider {

		private List items;

		public ItemContentProvider(List items) {
			this.items = items;
		}

		public List getFullItems() {
			return items;
		}

		public Object[] getElements(Object inputElement) {
			return items == null ? new Object[] {} : items.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class ItemLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			Item item = (Item) element;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getOperationImage(item.getOperation());
			case 4:
				return item.isCycle() ? Plugin.getImage("icons/16x16/true.png")
						: null;
			default:
				return null;
			}

		}

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public String getColumnText(Object element, int columnIndex) {
			Item item = (Item) element;
			switch (columnIndex) {
			case 1:
				return Date.format(item.getItemDate());
			case 2:
				return item.getAccount().getNumber();
			case 3:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation()
						.getName());
			case 5:
				return nf.format(item.getReading());
			case 6:
				return nf.format(item.getKwh());
			case 7:
				return nf.format(item.getGel());
			case 8:
				return nf.format(item.getBalance());
			case 9:
				return Date.format(item.getEnterDate());
			case 10:
				return item.getNumber() == null ? "" : item.getNumber();
			default:
				return "";
			}
		}
	}

	public void addViewerMouseListener(MouseListener ml) {
		viewer.getTable().addMouseListener(ml);
	}

	public void removeViewerMouseListener(MouseListener ml) {
		viewer.getTable().removeMouseListener(ml);
	}

	public void selectItemById(long id) {
		ItemContentProvider provider = (ItemContentProvider) viewer
				.getContentProvider();
		if (provider.items == null) {
			return;
		}
		for (int i = 0; i < provider.items.size(); i++) {
			Item item = (Item) provider.items.get(i);
			if (item.getId() == id) {
				viewer.getTable().setSelection(i);
				viewer.getTable().showSelection();
				break;
			}
		}
	}

	public Item getSelectedItem() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		ItemContentProvider provider = (ItemContentProvider) viewer
				.getContentProvider();
		return (Item) provider.items.get(index);
	}

}
