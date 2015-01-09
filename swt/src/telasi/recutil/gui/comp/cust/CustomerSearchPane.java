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


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Customer;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.cust.histories.ICustomerHistory;
import telasi.recutil.gui.comp.cust.search.AdvancedCustomerSearch;
import telasi.recutil.gui.comp.recalc.RecalcHistoryPane;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.customer.CustomerByAccnumbSelectRequest;

public class CustomerSearchPane extends Composite {

	private Composite composite1;
	private Text txtAccNumber;
	private Label lblDescription;
	private Label lblImage;
	private Composite composite2;
	private Label lblTitle;
	private Button btnAdvanced;
	private Button btnSearch;
	private Composite composite3;
	private Customer customer;
	private boolean enabled = true;
	
	public CustomerSearchPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			setLayout(thisLayout);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1Layout.marginTop = 10;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 32;
					lblImageLData.widthHint = 32;
					lblImage = new Label(composite1, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/32x32/find.png"));
				}
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.horizontalSpacing = 0;
					composite2Layout.marginHeight = 0;
					composite2Layout.marginWidth = 0;
					GridData composite2LData = new GridData();
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2LData.verticalAlignment = GridData.FILL;
					composite2LData.grabExcessVerticalSpace = true;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					{
						lblTitle = new Label(composite2, SWT.NONE);
						GridData lblTitleLData = new GridData();
						lblTitle.setLayoutData(lblTitleLData);
						lblTitle.setText(GUIMessages
								.getMessage("comp.custsearch.title"));
						lblTitle.setFont(GUIUtils.createSubtitleFont(lblTitle
								.getFont()));
					}
					{
						GridData lblDescriptionLData = new GridData();
						lblDescriptionLData.horizontalAlignment = GridData.FILL;
						lblDescriptionLData.grabExcessHorizontalSpace = true;
						lblDescription = new Label(composite2, SWT.NONE);
						lblDescription.setLayoutData(lblDescriptionLData);
						lblDescription.setText(GUIMessages
								.getMessage("comp.custsearch.descr"));
					}
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 3;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData txtAccNumberLData = new GridData();
					txtAccNumberLData.horizontalAlignment = GridData.FILL;
					txtAccNumberLData.grabExcessHorizontalSpace = true;
					txtAccNumber = new Text(composite3, SWT.BORDER);
					txtAccNumber.setLayoutData(txtAccNumberLData);
					txtAccNumber.setToolTipText(GUIMessages
							.getMessage("comp.custsearch.field_tooltip"));
					txtAccNumber.addKeyListener(new KeyAdapter() {
						public void keyPressed(KeyEvent e) {
							onKeyPress(e);
						}

						public void keyReleased(KeyEvent e) {
							onKeyRelease(e);
						}
					});
				}
				{
					btnSearch = new Button(composite3, SWT.PUSH | SWT.CENTER);
					btnSearch.setImage(Plugin.getImage("icons/16x16/next.png"));
					btnSearch.setToolTipText(GUIMessages
							.getMessage("comp.custsearch.search_tooltip"));
					btnSearch.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onSearch();
						}
					});
				}
				{
					btnAdvanced = new Button(composite3, SWT.PUSH | SWT.CENTER);
					btnAdvanced
							.setImage(Plugin.getImage("icons/16x16/mag.png"));
					btnAdvanced
							.setToolTipText(GUIMessages
									.getMessage("comp.custsearch.advanced_search_tooltip"));
					btnAdvanced.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onAdvancedSearch();
						}
					});
				}
			}
			this.layout();
			validateView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onKeyPress(KeyEvent e) {
		switch (e.keyCode) {
		case 13:
		case 16777296:
			// Enter
			onSearch();
			break;
		case 27:
			// Escape
			txtAccNumber.setText("");
			validateView();
			break;
		default:
			// Other keys
			break;
		}
	}

	private void onKeyRelease(KeyEvent e) {
		switch (e.keyCode) {
		case 13:
		case 16777296:
			break;
		case 27:
			break;
		default:
			validateView();
		}
	}

	private void validateView() {
		String accNumb = txtAccNumber.getText();
		boolean enableSearch = accNumb != null && accNumb.trim().length() > 0;
		btnSearch.setEnabled(enableSearch && enabled);
		btnAdvanced.setEnabled(enabled);
	}

	private void onSearch() {
		if (!enabled) {
			return;
		}
		String accnumb = txtAccNumber.getText();
		if (accnumb == null || accnumb.trim().length() == 0) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			CustomerByAccnumbSelectRequest request = new CustomerByAccnumbSelectRequest(Application.USER_NAME, Application.PASSWORD);
			request.setAccNumber(accnumb);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			CustomerByAccnumbSelectRequest callback = (CustomerByAccnumbSelectRequest) resp.getRequest();
			customer = callback.getCustomer();
			if (customer == null) {
				throw new RequestException(GUIMessages.getMessage("comp.general.information_not_found"));
			}
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
			customer = null;
		}
		updateRelated(customer);
	}

	private void onAdvancedSearch() {
		if (!enabled) {
			return;
		}
		AdvancedCustomerSearch searchDialog = new AdvancedCustomerSearch(
				getShell(), SWT.NONE);
		searchDialog.open();
		if (!searchDialog.isApproved()) {
			return;
		}
		customer = searchDialog.getCustomer();
		txtAccNumber.setText(customer.getNumber());
		updateRelated(customer);
		validateView();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setSearchEnabled(boolean enabled) {
		this.enabled = enabled;
		validateView();
	}

	public void openCustomer(String accNumb) {
		txtAccNumber.setText(accNumb);
		onSearch();
	}
	
	//
	// related components
	//

	private CustomerSimplePane customerPane;

	private AccountPane accountPane;

	private CustomerChargeHistoryPane customerHistoryPane;

	private RecalcHistoryPane recalculationHistoryPane;

	private ICustomerHistory customerHistory;

	public void setRelatedCustomerHistory(ICustomerHistory customerHistory) {
		this.customerHistory = customerHistory;
	}

	public void setRelatedCustomerPane(CustomerSimplePane customerPane) {
		this.customerPane = customerPane;
	}

	public void setRelatedAccountPane(AccountPane accountPane) {
		this.accountPane = accountPane;
	}

	public void setRelatedCustomerHistoryPane(
			CustomerChargeHistoryPane customerHistoryPane) {
		this.customerHistoryPane = customerHistoryPane;
	}

	public void setRecalculationHistoryPane(
			RecalcHistoryPane recalculationHistoryPane) {
		this.recalculationHistoryPane = recalculationHistoryPane;
	}

	private void updateRelated(Customer customer) {
		if (customer == null) {
			return;
		}
		if (customerPane != null) {
			customerPane.setCustomer(customer);
		}
		if (accountPane != null) {
			accountPane.setCustomer(customer);
		}
		if (customerHistoryPane != null) {
			customerHistoryPane.setCustomer(customer);
			customerHistoryPane.refresh();
		}
		if (recalculationHistoryPane != null) {
			recalculationHistoryPane.setCustomer(customer);
			recalculationHistoryPane.refresh();
		}
		if (customerHistory != null) {
			customerHistory.setCustomer(customer);
			customerHistory.refresh();
		}
	}

}
