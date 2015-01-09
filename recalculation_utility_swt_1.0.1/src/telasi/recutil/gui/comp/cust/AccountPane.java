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

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Date;
import telasi.recutil.gui.comp.tpowner.TpOwnerPane;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class AccountPane extends Composite {
	private Composite composite1;
	private Composite composite2;
	private TableViewer viewer;
	private Label lblDescription;
	private Label lblImage;
	private TableColumn colNumber;
	private TableColumn colIsActive;
	private TableColumn colIsMain;
	private Composite composite3;
	private Label lblTitle;
	private Customer customer;
	private TpOwnerPane relatedTpownerPage;

	public AccountPane(Composite parent, int style) {
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
			this.setSize(344, 259);
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
					lblImage
							.setImage(Plugin.getImage("icons/32x32/energy.png"));
				}
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.marginHeight = 0;
					composite2Layout.marginWidth = 0;
					GridData composite2LData = new GridData();
					composite2LData.verticalAlignment = GridData.FILL;
					composite2LData.grabExcessVerticalSpace = true;
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					{
						lblTitle = new Label(composite2, SWT.NONE);
						GridData lblTitleLData = new GridData();
						lblTitleLData.horizontalAlignment = GridData.FILL;
						lblTitleLData.grabExcessHorizontalSpace = true;
						lblTitle.setLayoutData(lblTitleLData);
						lblTitle.setText(GUIMessages
								.getMessage("comp.accountpabe.title"));
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
								.getMessage("comp.accountpabe.descr"));
					}
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite3, SWT.BORDER
							| SWT.FULL_SELECTION | SWT.VIRTUAL | SWT.SINGLE);
					viewer.getControl().setLayoutData(viewerLData);
					// viewer.getTable().setLinesVisible(true);
					viewer.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent arg0) {
							updateRelated();
						}
					});
					viewer.getTable().setHeaderVisible(true);
					{
						colIsMain = new TableColumn(viewer.getTable(), SWT.NONE);
						colIsMain.setWidth(20);
						colIsMain.setResizable(false);
					}
					{
						colIsActive = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colIsActive.setWidth(20);
						colIsActive.setResizable(false);
					}
					{
						colNumber = new TableColumn(viewer.getTable(), SWT.NONE);
						colNumber.setWidth(100);
						colNumber.setText(GUIMessages.getMessage("comp.general.account"));
					}
					{
						TableColumn colCrDate = new TableColumn(viewer.getTable(), SWT.NONE);
						colCrDate.setWidth(100);
						colCrDate.setText(GUIMessages.getMessage("comp.general.create_date"));
					}
					{
						TableColumn colMetterNumber = new TableColumn(viewer.getTable(), SWT.NONE);
						colMetterNumber.setWidth(100);
						colMetterNumber.setText(GUIMessages.getMessage("comp.general.meter_number"));
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayAccounts(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new AccountLabelProvider());
	}

	private void displayAccounts(List accounts) {
		viewer.setItemCount(accounts == null ? 0 : accounts.size());
		viewer.setContentProvider(new AccountContentProvider(accounts));
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		reset();
	}

	private void reset() {
		if (customer == null) {
			displayAccounts(null);
		} else {
			displayAccounts(customer.getAccounts());
		}
		updateRelated();
	}

	private class AccountContentProvider implements IStructuredContentProvider {

		private List accounts;

		public AccountContentProvider(List accounts) {
			this.accounts = accounts;
		}

		public Object[] getElements(Object inputElement) {
			return accounts == null ? new Object[] {} : accounts.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class AccountLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			Account acc = (Account) element;
			if (acc == null) {
				return null;
			}
			switch (columnIndex) {
			case 0:
				return acc.isMain() ? Plugin.getImage("icons/16x16/star.png")
						: null;
			case 1:
				return acc.getStatus() == /*Account.ACTIVE*/ 0 ? 
						null : Plugin.getImage("icons/16x16/stop.png");
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			Account acc = (Account) element;
			if (acc == null) {
				return "";
			}
			switch (columnIndex) {
			case 2:
				return GUITranslator.GEO_ASCII_TO_KA(acc.getNumber());
			case 3:
				return Date.format(acc.getCreationDate());
			case 4:
				return acc.getMeterNumber() == null ? "" : acc.getMeterNumber();
			default:
				return null;
			}
		}

	}

	public Account getSelectedAccount() {
		int index = viewer.getTable().getSelectionIndex();
		AccountContentProvider provider = (AccountContentProvider) viewer
				.getContentProvider();
		if (index == -1) {
			if (provider.accounts != null && provider.accounts.size() == 1) {
				return (Account) provider.accounts.get(0);
			}
			return null;
		}
		return (Account) provider.accounts.get(index);
	}

	/**
	 * Update related components.
	 */
	private void updateRelated() {
		Account selected = null;
		int index = viewer.getTable().getSelectionIndex();
		if (index != -1) {
			AccountContentProvider provider = (AccountContentProvider) viewer.getContentProvider();
			selected = (Account) provider.accounts.get(index);
		}
		if (relatedTpownerPage != null) {
			relatedTpownerPage.setAccount(selected);
		}
	}

	public void setRelatedTpownerPage(TpOwnerPane relatedTpownerPage) {
		this.relatedTpownerPage = relatedTpownerPage;
	}

}
