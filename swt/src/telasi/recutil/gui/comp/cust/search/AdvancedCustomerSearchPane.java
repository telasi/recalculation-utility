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
package telasi.recutil.gui.comp.cust.search;


import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Customer;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.comp.cust.AccountPane;
import telasi.recutil.gui.comp.cust.CustomerAddressPane;
import telasi.recutil.gui.comp.cust.CustomerPane;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.spinner.SwingNumericSpinner;
import telasi.recutil.service.eclipse.customer.CustomerByAccnumbSelectRequest;
import telasi.recutil.service.eclipse.customer.CustomerFindRequest;

public class AdvancedCustomerSearchPane extends Composite {

	private static final int DEFAULT_MAX_COUNT = 100;

	private SashForm sashForm1;
	private Composite composite1;
	private Composite composite2;
	private Composite composite3;
	private Label lblImage;
	private Label lblTitle;
	private Label label1;
	private Text txtName;
	private Label lblMaxNumber;
	private Composite composite13;
	private Button btnSearch;
	private AccountPane accountPane1;
	private SwingNumericSpinner spMaxCount;
	private CustomerAddressPane customerAddressPane1;
	private CustomerPane customerPane1;
	private TabItem tabAccounts;
	private TabItem tabAddress;
	private TabItem tabGeneral;
	private TabFolder tabFolder1;
	private Text txtFlat;
	private Text txtPorch;
	private Composite composite12;
	private Label lblPorch;
	private Text txtBuilding;
	private Text txtHouse;
	private Composite composite11;
	private Label lblHouse;
	private TableColumn colFlate;
	private TableColumn colPorch;
	private TableColumn colBuilding;
	private TableColumn colHouse;
	private TableColumn colStreet;
	private TableColumn colRegion;
	private TableColumn colName;
	private TableColumn colNumber;
	private TableViewer viewer;
	private Composite composite10;
	private Composite composite9;
	private SashForm sashForm2;
	private Label lblHelp1;
	private Composite composite8;
	private Composite composite7;
	private Composite composite6;
	private Button btnAddress;
	private Button btnName;
	private Group group1;
	private Label label2;
	private Label lblTitle2;
	private Label lblImage2;
	private Composite composite5;
	private Text txtServiceCenter;
	private Label lblServiceCenter;
	private Text txtStreet;
	private Label lblStreet;
	private Label lblName;

	private Composite composite4;

	public AdvancedCustomerSearchPane(Composite parent, int style) {
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
			this.setSize(781, 470);
			{
				sashForm1 = new SashForm(this, SWT.NONE);
				GridData sashForm1LData = new GridData();
				sashForm1LData.horizontalAlignment = GridData.FILL;
				sashForm1LData.grabExcessHorizontalSpace = true;
				sashForm1LData.verticalAlignment = GridData.FILL;
				sashForm1LData.grabExcessVerticalSpace = true;
				sashForm1.setLayoutData(sashForm1LData);
				sashForm1.setSize(60, 30);
				{
					composite1 = new Composite(sashForm1, SWT.BORDER);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.horizontalSpacing = 0;
					composite1Layout.marginHeight = 0;
					composite1Layout.marginWidth = 0;
					composite1Layout.verticalSpacing = 0;
					composite1.setLayout(composite1Layout);
					composite1.setBounds(32, 73, 588, 348);
					{
						composite3 = new Composite(composite1, SWT.NONE);
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
							lblImage.setImage(Plugin
									.getImage("icons/32x32/find.png"));
						}
						{
							lblTitle = new Label(composite3, SWT.NONE);
							GridData lblTitleLData = new GridData();
							lblTitleLData.horizontalAlignment = GridData.FILL;
							lblTitleLData.grabExcessHorizontalSpace = true;
							lblTitle.setLayoutData(lblTitleLData);
							lblTitle
									.setText(GUIMessages
											.getMessage("comp.advanced_customer_search_pane.search_title"));
							lblTitle.setFont(GUIUtils
									.createSubtitleFont(lblTitle.getFont()));
						}
					}
					{
						GridData label1LData = new GridData();
						label1LData.horizontalAlignment = GridData.FILL;
						label1LData.grabExcessHorizontalSpace = true;
						label1 = new Label(composite1, SWT.SEPARATOR
								| SWT.HORIZONTAL);
						label1.setLayoutData(label1LData);
					}
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
							lblServiceCenter = new Label(composite4, SWT.NONE);
							lblServiceCenter
									.setText(GUIMessages
											.getMessage("comp.advanced_customer_search_pane.searvice_center"));
						}
						{
							GridData txtServiceCenterLData = new GridData();
							txtServiceCenterLData.horizontalAlignment = GridData.FILL;
							txtServiceCenterLData.grabExcessHorizontalSpace = true;
							txtServiceCenter = new Text(composite4, SWT.BORDER);
							txtServiceCenter
									.setLayoutData(txtServiceCenterLData);
						}
						{
							lblName = new Label(composite4, SWT.NONE);
							lblName.setText(GUIMessages
									.getMessage("comp.general.name"));
						}
						{
							GridData txtNameLData = new GridData();
							txtNameLData.horizontalAlignment = GridData.FILL;
							txtNameLData.grabExcessHorizontalSpace = true;
							txtName = new Text(composite4, SWT.BORDER);
							txtName.setLayoutData(txtNameLData);
						}
						{
							lblStreet = new Label(composite4, SWT.NONE);
							lblStreet.setText(GUIMessages
									.getMessage("comp.general.street"));
						}
						{
							GridData txtStreetLData = new GridData();
							txtStreetLData.horizontalAlignment = GridData.FILL;
							txtStreetLData.grabExcessHorizontalSpace = true;
							txtStreet = new Text(composite4, SWT.BORDER);
							txtStreet.setLayoutData(txtStreetLData);
						}
						{
							lblHouse = new Label(composite4, SWT.NONE);
							lblHouse
									.setText(GUIMessages
											.getMessage("comp.advanced_customer_search_pane.house_building"));
						}
						{
							composite11 = new Composite(composite4, SWT.NONE);
							GridLayout composite11Layout = new GridLayout();
							composite11Layout.makeColumnsEqualWidth = true;
							composite11Layout.numColumns = 2;
							composite11Layout.marginWidth = 0;
							composite11Layout.marginHeight = 0;
							GridData composite11LData = new GridData();
							composite11LData.horizontalAlignment = GridData.FILL;
							composite11LData.grabExcessHorizontalSpace = true;
							composite11.setLayoutData(composite11LData);
							composite11.setLayout(composite11Layout);
							{
								GridData txtHouseLData = new GridData();
								txtHouseLData.horizontalAlignment = GridData.FILL;
								txtHouseLData.grabExcessHorizontalSpace = true;
								txtHouse = new Text(composite11, SWT.BORDER);
								txtHouse.setLayoutData(txtHouseLData);
							}
							{
								GridData txtBuildingLData = new GridData();
								txtBuildingLData.horizontalAlignment = GridData.FILL;
								txtBuildingLData.grabExcessHorizontalSpace = true;
								txtBuilding = new Text(composite11, SWT.BORDER);
								txtBuilding.setLayoutData(txtBuildingLData);
							}
						}
						{
							lblPorch = new Label(composite4, SWT.NONE);
							lblPorch
									.setText(GUIMessages
											.getMessage("comp.advanced_customer_search_pane.porch_flat"));
						}
						{
							composite12 = new Composite(composite4, SWT.NONE);
							GridLayout composite12Layout = new GridLayout();
							composite12Layout.makeColumnsEqualWidth = true;
							composite12Layout.numColumns = 2;
							composite12Layout.marginWidth = 0;
							composite12Layout.marginHeight = 0;
							GridData composite12LData = new GridData();
							composite12LData.horizontalAlignment = GridData.FILL;
							composite12LData.grabExcessHorizontalSpace = true;
							composite12.setLayoutData(composite12LData);
							composite12.setLayout(composite12Layout);
							{
								GridData txtPorchLData = new GridData();
								txtPorchLData.horizontalAlignment = GridData.FILL;
								txtPorchLData.grabExcessHorizontalSpace = true;
								txtPorch = new Text(composite12, SWT.BORDER);
								txtPorch.setLayoutData(txtPorchLData);
							}
							{
								GridData txtFlatLData = new GridData();
								txtFlatLData.horizontalAlignment = GridData.FILL;
								txtFlatLData.grabExcessHorizontalSpace = true;
								txtFlat = new Text(composite12, SWT.BORDER);
								txtFlat.setLayoutData(txtFlatLData);
							}
						}
					}
					{
						composite5 = new Composite(composite1, SWT.NONE);
						GridLayout composite5Layout = new GridLayout();
						composite5Layout.numColumns = 2;
						GridData composite5LData = new GridData();
						composite5LData.horizontalAlignment = GridData.FILL;
						composite5LData.grabExcessHorizontalSpace = true;
						composite5.setLayoutData(composite5LData);
						composite5.setLayout(composite5Layout);
						{
							GridData lblImage2LData = new GridData();
							lblImage2LData.heightHint = 32;
							lblImage2LData.widthHint = 32;
							lblImage2 = new Label(composite5, SWT.NONE);
							lblImage2.setLayoutData(lblImage2LData);
							lblImage2.setImage(Plugin
									.getImage("icons/32x32/details.png"));
						}
						{
							lblTitle2 = new Label(composite5, SWT.NONE);
							lblTitle2
									.setText(GUIMessages
											.getMessage("comp.advanced_customer_search_pane.order_title"));
							lblTitle2.setFont(GUIUtils
									.createSubtitleFont(lblTitle2.getFont()));
						}
					}
					{
						GridData label2LData = new GridData();
						label2LData.horizontalAlignment = GridData.FILL;
						label2LData.grabExcessHorizontalSpace = true;
						label2 = new Label(composite1, SWT.SEPARATOR
								| SWT.HORIZONTAL);
						label2.setLayoutData(label2LData);
					}
					{
						composite6 = new Composite(composite1, SWT.NONE);
						GridLayout composite6Layout = new GridLayout();
						GridData composite6LData = new GridData();
						composite6LData.horizontalAlignment = GridData.FILL;
						composite6LData.grabExcessHorizontalSpace = true;
						composite6.setLayoutData(composite6LData);
						composite6.setLayout(composite6Layout);
						{
							group1 = new Group(composite6, SWT.BORDER);
							GridLayout group1Layout = new GridLayout();
							group1Layout.numColumns = 2;
							GridData group1LData = new GridData();
							group1LData.horizontalAlignment = GridData.FILL;
							group1LData.grabExcessHorizontalSpace = true;
							group1.setLayoutData(group1LData);
							group1.setLayout(group1Layout);
							group1
									.setText(GUIMessages
											.getMessage("comp.advanced_customer_search_pane.sort_by"));
							{
								btnName = new Button(group1, SWT.RADIO
										| SWT.LEFT);
								btnName
										.setText(GUIMessages
												.getMessage("comp.advanced_customer_search_pane.by_name"));
							}
							{
								btnAddress = new Button(group1, SWT.RADIO
										| SWT.LEFT);
								btnAddress
										.setText(GUIMessages
												.getMessage("comp.advanced_customer_search_pane.by_address"));
							}
						}
						{
							composite13 = new Composite(composite6, SWT.NONE);
							GridLayout composite13Layout = new GridLayout();
							composite13Layout.numColumns = 2;
							composite13Layout.marginWidth = 0;
							composite13Layout.marginHeight = 0;
							GridData composite13LData = new GridData();
							composite13LData.horizontalAlignment = GridData.FILL;
							composite13LData.grabExcessHorizontalSpace = true;
							composite13.setLayoutData(composite13LData);
							composite13.setLayout(composite13Layout);
							{
								lblMaxNumber = new Label(composite13, SWT.NONE);
								lblMaxNumber
										.setText(GUIMessages
												.getMessage("comp.advanced_customer_search_pane.maxcount"));
							}
							{
								spMaxCount = new SwingNumericSpinner(
										composite13, SWT.NONE);
								GridData spMaxCountLData = new GridData();
								spMaxCountLData.widthHint = 75;
								spMaxCount.setLayoutData(spMaxCountLData);
								spMaxCount.setMask("#,###");
							}
						}
					}
					{
						composite7 = new Composite(composite1, SWT.NONE);
						GridLayout composite7Layout = new GridLayout();
						composite7Layout.makeColumnsEqualWidth = true;
						GridData composite7LData = new GridData();
						composite7LData.horizontalAlignment = GridData.FILL;
						composite7LData.grabExcessHorizontalSpace = true;
						composite7LData.verticalAlignment = GridData.FILL;
						composite7LData.grabExcessVerticalSpace = true;
						composite7.setLayoutData(composite7LData);
						composite7.setLayout(composite7Layout);
						{
							btnSearch = new Button(composite7, SWT.PUSH
									| SWT.CENTER);
							GridData btnSearchLData = new GridData();
							btnSearchLData.grabExcessHorizontalSpace = true;
							btnSearchLData.horizontalAlignment = GridData.END;
							btnSearch.setLayoutData(btnSearchLData);
							btnSearch.setText(GUIMessages
									.getMessage("comp.general.search"));
							btnSearch
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent e) {
											onSearch();
										}
									});
						}
					}
					{
						composite8 = new Composite(composite1, SWT.NONE);
						GridLayout composite8Layout = new GridLayout();
						composite8Layout.numColumns = 2;
						GridData composite8LData = new GridData();
						composite8LData.horizontalAlignment = GridData.FILL;
						composite8LData.grabExcessHorizontalSpace = true;
						composite8.setLayoutData(composite8LData);
						composite8.setLayout(composite8Layout);
						{
							lblHelp1 = new Label(composite8, SWT.WRAP);
							GridData lblHelp1LData = new GridData();
							lblHelp1LData.horizontalAlignment = GridData.FILL;
							lblHelp1LData.grabExcessHorizontalSpace = true;
							lblHelp1LData.verticalAlignment = GridData.FILL;
							lblHelp1LData.grabExcessVerticalSpace = true;
							lblHelp1.setLayoutData(lblHelp1LData);
							lblHelp1
									.setText(GUIMessages
											.getMessage("comp.advanced_customer_search_pane.help_1"));
						}
					}
				}
				{
					composite2 = new Composite(sashForm1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.horizontalSpacing = 0;
					composite2Layout.marginHeight = 0;
					composite2Layout.marginWidth = 0;
					composite2Layout.verticalSpacing = 0;
					composite2.setLayout(composite2Layout);
					composite2.setBounds(28, 60, 293, 348);
					{
						sashForm2 = new SashForm(composite2, SWT.VERTICAL
								| SWT.V_SCROLL);
						GridData sashForm2LData = new GridData();
						sashForm2LData.horizontalAlignment = GridData.FILL;
						sashForm2LData.grabExcessHorizontalSpace = true;
						sashForm2LData.verticalAlignment = GridData.FILL;
						sashForm2LData.grabExcessVerticalSpace = true;
						sashForm2.setLayoutData(sashForm2LData);
						sashForm2.setSize(60, 30);
						{
							composite9 = new Composite(sashForm2, SWT.NONE);
							GridLayout composite9Layout = new GridLayout();
							composite9Layout.horizontalSpacing = 0;
							composite9Layout.marginHeight = 0;
							composite9Layout.marginWidth = 0;
							composite9Layout.verticalSpacing = 0;
							composite9.setLayout(composite9Layout);
							composite9.setBounds(-188, 127, 519, 493);
							{
								GridData viewerLData = new GridData();
								viewerLData.horizontalAlignment = GridData.FILL;
								viewerLData.grabExcessHorizontalSpace = true;
								viewerLData.verticalAlignment = GridData.FILL;
								viewerLData.grabExcessVerticalSpace = true;
								viewer = new TableViewer(composite9, SWT.BORDER
										| SWT.FULL_SELECTION | SWT.SINGLE
										| SWT.VIRTUAL);
								viewer.getControl().setLayoutData(viewerLData);
								viewer.getTable().setHeaderVisible(true);
								viewer.getTable().setLinesVisible(true);
								viewer
										.addSelectionChangedListener(new ISelectionChangedListener() {
											public void selectionChanged(
													SelectionChangedEvent event) {
												displayCustomerLight();
											}
										});
								viewer.getTable().addMouseListener(
										new MouseAdapter() {
											public void mouseDoubleClick(
													MouseEvent e) {
												displayCustomerHeavy();
											}
										});
								{
									colRegion = new TableColumn(viewer
											.getTable(), SWT.NONE);
									colRegion
											.setText(GUIMessages
													.getMessage("comp.general.business_center"));
									colRegion.setWidth(100);
								}
								{
									colNumber = new TableColumn(viewer
											.getTable(), SWT.NONE);
									colNumber
											.setText(GUIMessages
													.getMessage("comp.general.accnumb"));
									colNumber.setWidth(100);
								}
								{
									colName = new TableColumn(
											viewer.getTable(), SWT.NONE);
									colName.setText(GUIMessages
											.getMessage("comp.general.name"));
									colName.setWidth(200);
								}
								{
									colStreet = new TableColumn(viewer
											.getTable(), SWT.NONE);
									colStreet.setText(GUIMessages
											.getMessage("comp.general.street"));
									colStreet.setWidth(150);
								}
								{
									colHouse = new TableColumn(viewer
											.getTable(), SWT.NONE);
									colHouse.setText(GUIMessages
											.getMessage("comp.general.house"));
									colHouse.setWidth(100);
								}
								{
									colBuilding = new TableColumn(viewer
											.getTable(), SWT.NONE);
									colBuilding
											.setText(GUIMessages
													.getMessage("comp.general.building"));
									colBuilding.setWidth(100);
								}
								{
									colPorch = new TableColumn(viewer
											.getTable(), SWT.NONE);
									colPorch.setText(GUIMessages
											.getMessage("comp.general.pourch"));
									colPorch.setWidth(100);
								}
								{
									colFlate = new TableColumn(viewer
											.getTable(), SWT.NONE);
									colFlate.setText(GUIMessages
											.getMessage("comp.general.flate"));
									colFlate.setWidth(100);
								}
							}
						}
						{
							composite10 = new Composite(sashForm2, SWT.NONE);
							GridLayout composite10Layout = new GridLayout();
							composite10Layout.horizontalSpacing = 0;
							composite10Layout.marginHeight = 0;
							composite10Layout.marginWidth = 0;
							composite10Layout.verticalSpacing = 0;
							composite10.setLayout(composite10Layout);
							composite10.setBounds(-157, 128, 258, 493);
							{
								tabFolder1 = new TabFolder(composite10,
										SWT.NONE);
								{
									tabGeneral = new TabItem(tabFolder1,
											SWT.NONE);
									tabGeneral
											.setText(GUIMessages
													.getMessage("comp.general.customer"));
									{
										// $hide>>$
										customerPane1 = new CustomerPane(
												tabFolder1, SWT.NONE);
										tabGeneral.setControl(customerPane1);
										// $hide<<$
									}
								}
								{
									tabAddress = new TabItem(tabFolder1,
											SWT.NONE);
									tabAddress
											.setText(GUIMessages
													.getMessage("comp.general.address"));
									{
										// $hide>>$
										customerAddressPane1 = new CustomerAddressPane(
												tabFolder1, SWT.NONE);
										tabAddress
												.setControl(customerAddressPane1);
										// $hide<<$
									}
								}
								{
									tabAccounts = new TabItem(tabFolder1,
											SWT.NONE);
									tabAccounts
											.setText(GUIMessages
													.getMessage("comp.general.accounts"));
									{
										// $hide>>$
										accountPane1 = new AccountPane(
												tabFolder1, SWT.NONE);
										tabAccounts.setControl(accountPane1);
										// $hide<<$
									}
								}
								GridData tabFolder1LData = new GridData();
								tabFolder1LData.horizontalAlignment = GridData.FILL;
								tabFolder1LData.grabExcessHorizontalSpace = true;
								tabFolder1LData.verticalAlignment = GridData.FILL;
								tabFolder1LData.grabExcessVerticalSpace = true;
								tabFolder1.setLayoutData(tabFolder1LData);
								tabFolder1.setSelection(0);
							}
						}
					}
				}
			}
			// $protect>>$
			customerInit();
			// $protect<<$
			this.layout();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void customerInit() {
		sashForm1.setWeights(new int[] { 1, 2 });
		sashForm2.setWeights(new int[] { 1, 1 });
		btnName.setSelection(true);
		spMaxCount.setValue(new Integer(DEFAULT_MAX_COUNT));
	}

	private boolean empty(String s) {
		return s == null || s.trim().length() == 0;
	}

	private void onSearch() {
		String name = GUITranslator.KA_TO_GEO_ASCII(txtName.getText());
		String street = GUITranslator.KA_TO_GEO_ASCII(txtStreet.getText());
		String bussinesCenter = GUITranslator.KA_TO_GEO_ASCII(txtServiceCenter
				.getText());
		String house = GUITranslator.KA_TO_GEO_ASCII(txtHouse.getText());
		String building = GUITranslator.KA_TO_GEO_ASCII(txtBuilding.getText());
		String porch = GUITranslator.KA_TO_GEO_ASCII(txtPorch.getText());
		String flat = GUITranslator.KA_TO_GEO_ASCII(txtFlat.getText());
		spMaxCount.apply();
		int maxCount = ((Number) spMaxCount.getValue()).intValue();

		int orderOption = CustomerFindRequest.NAME;
		if (btnAddress.getSelection()) {
			orderOption = CustomerFindRequest.ADDRESS;
		}
		if (empty(name) && empty(street) && empty(bussinesCenter)
				&& empty(house) && empty(building) && empty(porch)
				&& empty(flat)) {
			String message = GUIMessages
					.getMessage("comp.advanced_customer_search_pane.define_search_parameters");
			MessageDialog.openWarning(getShell(), GUIMessages
					.getMessage("comp.general.warning"), message);
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		CustomerFindRequest request = new CustomerFindRequest(
				Application.USER_NAME, Application.PASSWORD);
		request.setName(name);
		request.setServiceCenter(bussinesCenter);
		request.setStreet(street);
		request.setHouse(house);
		request.setBuilding(building);
		request.setPorch(porch);
		request.setFlat(flat);
		request.setOrderField(orderOption);
		request.setMaxCount(maxCount <= 1 ? DEFAULT_MAX_COUNT : maxCount);
		try {
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			CustomerFindRequest callback = (CustomerFindRequest) resp
					.getRequest();
			List customers = callback.getCustomers();
			displayCustomers(customers);
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	private void initDataBehaivour() {
		displayCustomers(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new CustomerLabelProvider());
	}

	private void displayCustomers(List customers) {
		viewer.setItemCount(customers == null ? 0 : customers.size());
		viewer.setContentProvider(new CommonItemContentProvider(customers));
	}

	private class CustomerLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return "";
			}
			Customer c = (Customer) element;
			switch (columnIndex) {
			case 0:
				return c.getAddress().getRegionName() == null ? ""
						: GUITranslator.GEO_ASCII_TO_KA(c.getAddress()
								.getRegionName());
			case 1:
				return c.getNumber() == null ? "" : GUITranslator
						.GEO_ASCII_TO_KA(c.getNumber());
			case 2:
				return c.getName() == null ? "" : GUITranslator
						.GEO_ASCII_TO_KA(c.getName());
			case 3:
				return c.getAddress().getStreetName() == null ? ""
						: GUITranslator.GEO_ASCII_TO_KA(c.getAddress()
								.getStreetName());
			case 4:
				return c.getAddress().getHouse() == null ? "" : GUITranslator
						.GEO_ASCII_TO_KA(c.getAddress().getHouse());
			case 5:
				return c.getAddress().getBuilding() == null ? ""
						: GUITranslator.GEO_ASCII_TO_KA(c.getAddress()
								.getBuilding());
			case 6:
				return c.getAddress().getPorch() == null ? "" : GUITranslator
						.GEO_ASCII_TO_KA(c.getAddress().getPorch());
			case 7:
				return c.getAddress().getFlate() == null ? "" : GUITranslator
						.GEO_ASCII_TO_KA(c.getAddress().getFlate());
			default:
				return "";
			}
		}

	}

	public Customer getSelectedCustomer() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		return (Customer) provider.items.get(index);

	}

	private void displayCustomerLight() {
		Customer customer = getSelectedCustomer();
		customerPane1.setCustomer(customer);
		customerAddressPane1.setCustomer(customer);
		accountPane1.setCustomer(customer);
	}

	public Customer getCustomer() {
		Customer customer = getSelectedCustomer();
		if (customer == null) {
			return null;
		}
		return lookupCustomer(customer.getNumber());
	}

	private Customer lookupCustomer(String accNumber) {
		try {
			Customer customer = null;
			CustomerByAccnumbSelectRequest request = new CustomerByAccnumbSelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setAccNumber(accNumber);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			CustomerByAccnumbSelectRequest callback = (CustomerByAccnumbSelectRequest) resp
					.getRequest();
			if (callback.getCustomer() == null) {
				throw new RequestException(GUIMessages
						.getMessage("comp.general.information_not_found"));
			} else {
				customer = callback.getCustomer();
			}
			return customer;
		} catch (Exception ex) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), ex.toString());
			return null;
		}
	}

	private void displayCustomerHeavy() {
		Customer customer = getSelectedCustomer();
		if (customer == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		customer = lookupCustomer(customer.getNumber());
		if (customer == null) {
			return;
		}
		customerPane1.setCustomer(customer);
		customerAddressPane1.setCustomer(customer);
		accountPane1.setCustomer(customer);
	}

}
