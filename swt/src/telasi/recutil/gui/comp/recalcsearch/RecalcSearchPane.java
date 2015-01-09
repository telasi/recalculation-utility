package telasi.recutil.gui.comp.recalcsearch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.comp.exchange.ServerExchangeNode;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.ejb.RecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.calendar.SWTDatePicker;
import telasi.recutil.service.eclipse.recalc.RecalcSearchRequest2;

public class RecalcSearchPane extends Composite {
	private Composite composite1;
	private Composite composite2;
	private Composite composite3;
	private Label lblDescription1;
	private Label label1;
	private Button btnSearch;
	private TableViewer viewer;
	private Composite composite6;
	private Button btnCreate;
	private Button btnSaveDate;
	private Group group1;
	private Composite composite5;
	private SWTDatePicker pkDate;
	private Label lblDate;
	private Composite composite4;
	private SashForm sash1;
	private Label lblImage1;
	private TableColumn colAccount;
	private TableColumn colAdvisor;
	private TableColumn colOperator;
	private TableColumn colSaveDate;
	private TableColumn colCreateDate;
	private TableColumn colCustomer;
	private TableColumn colNumber;
	private TableColumn colImage;
	private ServerExchangeNode serverInfo;
	private Label lblEndDate;
	private SWTDatePicker pkEndDate;
	private Label lblRecalcNumber;
	private Text txtRecalcNumber;
	private Label lblHelp;
	private Button btnClearStart;
	private Button btnClearEnd;

	public RecalcSearchPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(838, 323);
			{
				sash1 = new SashForm(this, SWT.NONE);
				GridData sash1LData = new GridData();
				sash1LData.horizontalAlignment = GridData.FILL;
				sash1LData.grabExcessHorizontalSpace = true;
				sash1LData.verticalAlignment = GridData.FILL;
				sash1LData.grabExcessVerticalSpace = true;
				sash1.setLayoutData(sash1LData);
				{
					composite1 = new Composite(sash1, SWT.BORDER);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.horizontalSpacing = 0;
					composite1Layout.marginHeight = 0;
					composite1Layout.marginWidth = 0;
					composite1Layout.verticalSpacing = 0;
					composite1.setLayout(composite1Layout);
					composite1.setBounds(-270, 2, 266, 114);
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
							GridData lblImage1LData = new GridData();
							lblImage1LData.heightHint = 32;
							lblImage1LData.widthHint = 32;
							lblImage1 = new Label(composite3, SWT.NONE);
							lblImage1.setLayoutData(lblImage1LData);
							lblImage1.setImage(Plugin.getImage("icons/32x32/find.png"));
						}
						{
							lblDescription1 = new Label(composite3, SWT.NONE);
							lblDescription1.setText(GUIMessages.getMessage("comp.recalcsearch.search_title"));
							lblDescription1.setFont(GUIUtils.createSubtitleFont(lblDescription1.getFont()));
						}
					}
					{
						GridData label1LData = new GridData();
						label1LData.horizontalAlignment = GridData.FILL;
						label1LData.grabExcessHorizontalSpace = true;
						label1 = new Label(composite1, SWT.SEPARATOR | SWT.HORIZONTAL);
						label1.setLayoutData(label1LData);
					}
					{
						composite4 = new Composite(composite1, SWT.NONE);
						GridLayout composite4Layout = new GridLayout();
						composite4Layout.numColumns = 3;
						GridData composite4LData = new GridData();
						composite4LData.horizontalAlignment = GridData.FILL;
						composite4LData.grabExcessHorizontalSpace = true;
						composite4.setLayoutData(composite4LData);
						composite4.setLayout(composite4Layout);
						{
							lblRecalcNumber = new Label(composite4, SWT.NONE);
							lblRecalcNumber.setText(GUIMessages.getMessage("comp.recalcsearch.recalc_number"));
						}
						{
							GridData data = new GridData();
							data.horizontalAlignment = GridData.FILL;
							data.grabExcessHorizontalSpace = true;
							txtRecalcNumber = new Text(composite4, SWT.BORDER);
							txtRecalcNumber.setLayoutData(data);
						}
						{
							new Label(composite4, SWT.NONE);
						}
						{
							lblDate = new Label(composite4, SWT.NONE);
							lblDate.setText(GUIMessages.getMessage("comp.recalcsearch.start_date"));
						}
						{
							GridData data = new GridData();
							data.horizontalAlignment = GridData.FILL;
							data.grabExcessHorizontalSpace = true;
							pkDate = new SWTDatePicker(composite4, SWT.BORDER);
							pkDate.setLayoutData(data);
						}
						{
							btnClearStart = new Button(composite4, SWT.PUSH | SWT.CENTER);
							btnClearStart.setText(GUIMessages.getMessage("comp.general.clear"));
							btnClearStart.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									pkDate.setDate(null);
								}
							});
						}
						{
							lblEndDate = new Label(composite4, SWT.NONE);
							lblEndDate.setText(GUIMessages.getMessage("comp.recalcsearch.end_date"));
						}
						{
							GridData data = new GridData();
							data.horizontalAlignment = GridData.FILL;
							data.grabExcessHorizontalSpace = true;
							pkEndDate = new SWTDatePicker(composite4, SWT.BORDER);
							pkEndDate.setLayoutData(data);
						}
						{
							btnClearEnd = new Button(composite4, SWT.PUSH | SWT.CENTER);
							btnClearEnd.setText(GUIMessages.getMessage("comp.general.clear"));
							btnClearEnd.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									pkEndDate.setDate(null);
								}
							});
						}
					}
					{
						composite5 = new Composite(composite1, SWT.NONE);
						GridLayout composite5Layout = new GridLayout();
						composite5Layout.makeColumnsEqualWidth = true;
						GridData composite5LData = new GridData();
						composite5LData.horizontalAlignment = GridData.FILL;
						composite5LData.grabExcessHorizontalSpace = true;
						composite5.setLayoutData(composite5LData);
						composite5.setLayout(composite5Layout);
						{
							group1 = new Group(composite5, SWT.NONE);
							GridLayout group1Layout = new GridLayout();
							GridData group1LData = new GridData();
							group1LData.horizontalAlignment = GridData.FILL;
							group1LData.grabExcessHorizontalSpace = true;
							group1.setLayoutData(group1LData);
							group1.setLayout(group1Layout);
							{
								btnSaveDate = new Button(group1, SWT.RADIO | SWT.LEFT);
								btnSaveDate.setText(GUIMessages.getMessage("comp.general.save_date"));
								btnSaveDate.setSelection(true);
							}
							{
								btnCreate = new Button(group1, SWT.RADIO | SWT.LEFT);
								btnCreate.setText(GUIMessages.getMessage("comp.general.create_date"));
							}
						}
					}
					{
						composite6 = new Composite(composite1, SWT.NONE);
						GridLayout composite6Layout = new GridLayout();
						composite6Layout.makeColumnsEqualWidth = true;
						GridData composite6LData = new GridData();
						composite6LData.horizontalAlignment = GridData.FILL;
						composite6LData.grabExcessHorizontalSpace = true;
						composite6.setLayoutData(composite6LData);
						composite6.setLayout(composite6Layout);
						{
							btnSearch = new Button(composite6, SWT.PUSH | SWT.CENTER);
							GridData btnSearchLData = new GridData();
							btnSearchLData.grabExcessHorizontalSpace = true;
							btnSearchLData.horizontalAlignment = GridData.END;
							btnSearch.setLayoutData(btnSearchLData);
							btnSearch.setText(GUIMessages.getMessage("comp.general.search"));
							btnSearch.addSelectionListener(new SelectionAdapter() {
										@Override
										public void widgetSelected(SelectionEvent e) {
											onSearch();
										}
									});
						}
					}
					{
						lblHelp = new Label(composite1, SWT.WRAP);
						lblHelp.setText(GUIMessages.getMessage("comp.recalcsearch.help1"));
					}
				}
				{
					composite2 = new Composite(sash1, SWT.BORDER);
					GridLayout composite2Layout = new GridLayout();
					composite2.setLayout(composite2Layout);
					composite2.setBounds(2, 2, 166, 245);
					{
						GridData viewerLData = new GridData();
						viewerLData.horizontalAlignment = GridData.FILL;
						viewerLData.grabExcessHorizontalSpace = true;
						viewerLData.verticalAlignment = GridData.FILL;
						viewerLData.grabExcessVerticalSpace = true;
						viewer = new TableViewer(composite2, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.VIRTUAL);
						viewer.getControl().setLayoutData(viewerLData);
						viewer.getTable().setHeaderVisible(true);
						viewer.getTable().setLinesVisible(true);
					}
					{
						colImage = new TableColumn(viewer.getTable(), SWT.NONE);
						colImage.setWidth(20);
						colImage.setResizable(false);
					}
					{
						colNumber = new TableColumn(viewer.getTable(), SWT.NONE);
						colNumber.setText(GUIMessages.getMessage("comp.general.number"));
						colNumber.setWidth(100);
					}
					{
						colCustomer = new TableColumn(viewer.getTable(), SWT.NONE);
						colCustomer.setText(GUIMessages.getMessage("comp.general.accnumb"));
						colCustomer.setWidth(100);
					}
					{
						colAccount = new TableColumn(viewer.getTable(), SWT.NONE);
						colAccount.setText(GUIMessages.getMessage("comp.general.account"));
						colAccount.setWidth(100);
					}
					{
						TableColumn colCustomerName = new TableColumn(viewer.getTable(), SWT.NONE);
						colCustomerName.setText(GUIMessages.getMessage("comp.general.customer"));
						colCustomerName.setWidth(150);
					}
					{
						colCreateDate = new TableColumn(viewer.getTable(), SWT.NONE);
						colCreateDate.setText(GUIMessages.getMessage("comp.general.create_date"));
						colCreateDate.setWidth(150);
					}
					{
						colSaveDate = new TableColumn(viewer.getTable(), SWT.NONE);
						colSaveDate.setText(GUIMessages.getMessage("comp.general.save_date"));
						colSaveDate.setWidth(150);
					}
					{
						colOperator = new TableColumn(viewer.getTable(), SWT.NONE);
						colOperator.setText(GUIMessages.getMessage("comp.general.operator"));
						colOperator.setWidth(150);
					}
					{
						colAdvisor = new TableColumn(viewer.getTable(), SWT.NONE);
						colAdvisor.setText(GUIMessages.getMessage("comp.general.advisor"));
						colAdvisor.setWidth(150);
					}
				}
			}
			// $protect>>$
			sash1.setWeights(new int[] { 2, 3 });
			// $protect<<$
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onSearch() {
		Date startDate = pkDate.getDate();
		Date endDate = pkEndDate.getDate();
		String recalcNumber = txtRecalcNumber.getText();

		if (startDate == null && endDate == null && (recalcNumber == null || recalcNumber.trim().length() == 0)) {
			MessageDialog.openWarning(getShell(), "Warning", "Define search criteria.");
			return;
		}
		if (serverInfo == null) {
			if (!Application.validateConnection()) {
				return;
			}
		}
		boolean bySaveDate = btnSaveDate.getSelection();
		String userName = serverInfo == null ? Application.USER_NAME : serverInfo.getUserName();
		String password = serverInfo == null ? Application.PASSWORD : serverInfo.getPassword();
		RecalcSearchRequest2 request = new RecalcSearchRequest2(userName, password);
		request.setStartDate(startDate);
		request.setSaveDate(bySaveDate);
		request.setEndDate(endDate);
		request.setRecalcNumber(recalcNumber);
		try {
			DefaultEJBResponse resp = null;
			if (serverInfo == null) {
				resp = (DefaultEJBResponse) DefaultRecutilClient
						.processRequest(request);
			} else {
				RecutilClient client = new RecutilClient(serverInfo.getServer()
						.getUrl());
				resp = (DefaultEJBResponse) client.processRequest(request);
			}
			RecalcSearchRequest2 callback = (RecalcSearchRequest2) resp.getRequest();
			List results = callback.getResults();
			// if (results != null && !results.isEmpty()) {
			// for (int i = 0; i < results.size(); i++) {
			// Recalc recalc = (Recalc) results.get(i);
			// long saveUserId = recalc.getSaveUser() == null ? -1 :
			// recalc.getSaveUser().getId();
			// long advisorId = recalc.getAdvisor() == null ? -1 :
			// recalc.getAdvisor().getId();
			// User saveUser = Cache.findUserById(saveUserId);
			// User advisor = Cache.findUserById(advisorId);
			// recalc.setSaveUser(saveUser);
			// recalc.setAdvisor(advisor);
			// }
			// }
			displayRecalcs(results);
		} catch (Throwable t) {
			t.printStackTrace();
			MessageDialog.openError(getShell(), "Error", t.toString());
		}

	}

	private void initDataBehaivour() {
		displayRecalcs(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new RecalcLabelProvider());
	}

	private void displayRecalcs(List recalcs) {
		viewer.setItemCount(recalcs == null ? 0 : recalcs.size());
		viewer.setContentProvider(new CommonItemContentProvider(recalcs));
	}

	@SuppressWarnings("unchecked")
	public List getSelected() {
		int[] indecies = viewer.getTable().getSelectionIndices();
		List recalcs = new ArrayList();
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		for (int i = 0; indecies != null && i < indecies.length; i++)
			recalcs.add(provider.items.get(indecies[i]));
		return recalcs;
	}

	/**
	 * Class for recalculations presentation.
	 */
	private class RecalcLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			Recalc recalc = (Recalc) element;
			if (recalc == null)
				return null;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getRecalcImage(recalc);
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			Recalc recalc = (Recalc) element;
			switch (columnIndex) {
			case 1:
				return GUITranslator.GEO_ASCII_TO_KA(recalc.getNumber());
			case 2:
				return recalc.getCustomer().getNumber();
			case 3:
				return recalc.getAccount().getNumber();
			case 4:
				return GUITranslator.GEO_ASCII_TO_KA(recalc.getCustomer().getName());
			case 5:
				return Date.format(recalc.getCreateDate());
			case 6:
				return recalc.getSaveDate() == null ? "" : Date.format(recalc
						.getSaveDate());
			case 7:
				return recalc.getSaveUser() == null ? "" : GUITranslator
						.GEO_ASCII_TO_KA(recalc.getSaveUser().getFullName());
			case 8:
				return recalc.getAdvisor() == null ? "" : GUITranslator
						.GEO_ASCII_TO_KA(recalc.getAdvisor().getFullName());
			default:
				return "";
			}
		}
	}

	public ServerExchangeNode getServerInfo() {
		return serverInfo;
	}

	/**
	 * Define serverInfo, if you do not want to use default application
	 * connection.
	 */
	public void setServerInfo(ServerExchangeNode serverInfo) {
		this.serverInfo = serverInfo;
	}

	public TableViewer getViewer() {
		return viewer;
	}

}
