package telasi.recutil.gui.comp.tpowner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.tpowner.TpOwnerAccount;
import telasi.recutil.beans.tpowner.TpOwnerItem;
import telasi.recutil.beans.tpowner.TpOwnerRecalc;
import telasi.recutil.calc.calc08.TpOwnerUtils;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.tpowner.TpOwnerCalculationRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerRecalcErrorRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerRecalcSaveRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerRecalcSavedResultsRequest;

/**
 * Recalculation pane.
 */
public class TpOwnerRecalcPane extends Composite {
	private TableColumn colDate;
	private Label lblTitle1;
	private Label lblImage1;
	private Composite composite4;
	private Composite composite2;
	private SashForm sashForm1;
	private Composite composite3;
	private ToolItem tiCalculate;
	private ToolBar toolBar1;
	private TableColumn colItemNumber;
	private TableColumn colKwhCorrected;
	private TableColumn colGel;
	private TableColumn colAccNumber;
	private TableColumn colCycle;
	private ToolItem tiSave;
	private ToolBar toolBar2;
	private TableColumn colKwh;
	private Label lblDescription;
	private Label label1;
	private TableColumn colAccId;
	private TableColumn colCustomerName;
	private TableColumn colImage;
	private TableColumn colOperation;
	private TableColumn colPrevChargeDate;
	private Composite composite1;
	private TableViewer tblStartHistory;
	private TpOwnerRecalc recalculation;
	private TpOwnerAccountSearchPane searchPane;
	private TpOwnerResultsPane pnResults;

	public TpOwnerRecalcPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehoivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginWidth = 0;
			thisLayout.marginHeight = 0;
			this.setLayout(thisLayout);
			this.setSize(691, 442);
			{
				sashForm1 = new SashForm(this, SWT.VERTICAL | SWT.V_SCROLL);
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
					composite1Layout.marginWidth = 0;
					composite1Layout.verticalSpacing = 0;
					composite1Layout.marginHeight = 0;
					composite1Layout.horizontalSpacing = 0;
					composite1.setLayout(composite1Layout);
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
							GridData lblImage1LData = new GridData();
							lblImage1LData.heightHint = 32;
							lblImage1LData.widthHint = 32;
							lblImage1 = new Label(composite4, SWT.NONE);
							lblImage1.setLayoutData(lblImage1LData);
							lblImage1.setImage(Plugin.getImage("icons/32x32/details.png"));
						}
						{
							lblTitle1 = new Label(composite4, SWT.NONE);
							GridData lblTitle1LData = new GridData();
							lblTitle1LData.grabExcessHorizontalSpace = true;
							lblTitle1.setText(GUIMessages.getMessage("comp.tpowner.recalc.startHistory"));
							lblTitle1.setFont(GUIUtils.createTitleFont(lblTitle1.getFont()));
						}
						{
							lblDescription = new Label(composite4, SWT.RIGHT);
							GridData label2LData = new GridData();
							label2LData.grabExcessHorizontalSpace = true;
							label2LData.horizontalAlignment = GridData.FILL;
							label2LData.verticalAlignment = GridData.END;
							lblDescription.setLayoutData(label2LData);
							lblDescription.setText("n/a");
						}
					}
					{
						label1 = new Label(composite1, SWT.SEPARATOR | SWT.HORIZONTAL);
						GridData label1LData = new GridData();
						label1LData.horizontalAlignment = GridData.FILL;
						label1LData.grabExcessHorizontalSpace = true;
						label1.setLayoutData(label1LData);
						label1.setText("label1");
					}
					{
						composite2 = new Composite(composite1, SWT.NONE);
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
							GridData tblStartHistoryLData = new GridData();
							tblStartHistoryLData.horizontalAlignment = GridData.FILL;
							tblStartHistoryLData.grabExcessHorizontalSpace = true;
							tblStartHistoryLData.verticalAlignment = GridData.FILL;
							tblStartHistoryLData.grabExcessVerticalSpace = true;
							tblStartHistory = new TableViewer(composite2, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL| SWT.MULTI);
							tblStartHistory.getControl().setLayoutData(tblStartHistoryLData);
							tblStartHistory.getTable().setHeaderVisible(true);
							tblStartHistory.getTable().setLinesVisible(true);
							{
								colImage = new TableColumn(tblStartHistory.getTable(), SWT.NONE);
								colImage.setWidth(25);
								colImage.setResizable(false);
							}
							{
								colDate = new TableColumn(tblStartHistory.getTable(), SWT.NONE);
								colDate.setText(GUIMessages.getMessage("comp.general.itemdate"));
								colDate.setWidth(75);
							}
							{
								colAccId = new TableColumn(tblStartHistory.getTable(), SWT.NONE);
								colAccId.setText(GUIMessages.getMessage("comp.general.account"));
								colAccId.setWidth(75);
							}
							{
								colAccNumber = new TableColumn(tblStartHistory.getTable(), SWT.NONE);
								colAccNumber.setText(GUIMessages.getMessage("comp.general.customer"));
								colAccNumber.setWidth(75);
							}
							{
								colCustomerName = new TableColumn(tblStartHistory.getTable(), SWT.NONE);
								colCustomerName.setText(GUIMessages.getMessage("comp.general.customer_name"));
								colCustomerName.setWidth(100);
							}
							{
								colOperation = new TableColumn(tblStartHistory.getTable(), SWT.NONE);
								colOperation.setText(GUIMessages.getMessage("comp.general.operation"));
								colOperation.setWidth(150);
							}
							{
								colCycle = new TableColumn(tblStartHistory.getTable(), SWT.NONE);
								colCycle.setWidth(25);
								colCycle.setResizable(false);
							}
							{
								colKwh = new TableColumn(tblStartHistory.getTable(), SWT.RIGHT);
								colKwh.setText(GUIMessages.getMessage("comp.general.kwh"));
								colKwh.setWidth(100);
							}
							{
								colGel = new TableColumn(tblStartHistory.getTable(), SWT.RIGHT);
								colGel.setText(GUIMessages.getMessage("comp.general.gel"));
								colGel.setWidth(100);
							}
							{
								colKwhCorrected = new TableColumn(tblStartHistory.getTable(), SWT.RIGHT);
								colKwhCorrected.setText(GUIMessages.getMessage("comp.general.kwh_corr"));
								colKwhCorrected.setWidth(100);
							}
							{
								colItemNumber = new TableColumn(tblStartHistory.getTable(), SWT.NONE);
								colItemNumber.setText(GUIMessages.getMessage("comp.general.document"));
								colItemNumber.setWidth(100);
							}
							{
								colPrevChargeDate = new TableColumn(tblStartHistory.getTable(), SWT.NONE);
								colPrevChargeDate.setText(GUIMessages.getMessage("comp.general.prev_oper_date"));
								colPrevChargeDate.setWidth(100);
							}
							
						}
					}
					{
						GridData toolBar1LData = new GridData();
						toolBar1LData.horizontalAlignment = GridData.FILL;
						toolBar1LData.grabExcessHorizontalSpace = true;
						toolBar1 = new ToolBar(composite1, SWT.NONE);
						toolBar1.setLayoutData(toolBar1LData);
						{
							tiCalculate = new ToolItem(toolBar1, SWT.NONE);
							tiCalculate.setImage(Plugin.getImage("icons/22x22/recalc.png"));
							tiCalculate.setText(GUIMessages.getMessage("comp.tpowner.calc"));
							tiCalculate.setToolTipText(GUIMessages.getMessage("comp.tpowner.recalc.calculate"));
							tiCalculate.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									onCalculate();
								}
							});
						}
						{
							ToolItem tiViewSaved = new ToolItem(toolBar1, SWT.NONE);
							tiViewSaved.setImage(Plugin.getImage("icons/22x22/view.png"));
							tiViewSaved.setText(GUIMessages.getMessage("comp.tpowner.view"));
							tiViewSaved.setToolTipText(GUIMessages.getMessage("comp.tpowner.recalc.viewSaved"));
							tiViewSaved.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									onShowSavedResults();
								}
							});
						}
					}
				}
				{
					composite3 = new Composite(sashForm1, SWT.BORDER);
					GridLayout composite3Layout = new GridLayout();
					composite3Layout.marginHeight = 0;
					composite3Layout.marginWidth = 0;
					composite3Layout.verticalSpacing = 0;
					composite3.setLayout(composite3Layout);
					{
						pnResults = new TpOwnerResultsPane(composite3, SWT.NONE);
						GridData composite5LData = new GridData();
						composite5LData.horizontalAlignment = GridData.FILL;
						composite5LData.grabExcessHorizontalSpace = true;
						composite5LData.verticalAlignment = GridData.FILL;
						composite5LData.grabExcessVerticalSpace = true;
						pnResults.setLayoutData(composite5LData);
						
					}
					{
						GridData toolBar2LData = new GridData();
						toolBar2LData.horizontalAlignment = GridData.FILL;
						toolBar2LData.grabExcessHorizontalSpace = true;
						toolBar2 = new ToolBar(composite3, SWT.NONE);
						toolBar2.setLayoutData(toolBar2LData);
						{
							tiSave = new ToolItem(toolBar2, SWT.NONE);
							tiSave.setImage(Plugin.getImage("icons/22x22/save.png"));
							tiSave.setToolTipText(GUIMessages.getMessage("comp.tpowner.recalc.save"));
							tiSave.setText(GUIMessages.getMessage("comp.tpowner.save"));
							tiSave.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									onSave();
								}
							});
						}
						{
							ToolItem tiError = new ToolItem(toolBar2, SWT.NONE);
							tiError.setImage(Plugin.getImage("icons/22x22/stop.png"));
							tiError.setToolTipText(GUIMessages.getMessage("comp.tpowner.recalc.declError"));
							tiError.setText(GUIMessages.getMessage("comp.tpowner.cancel"));
							tiError.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									onDeclareError();
								}
							});
						}
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehoivour() {
		displayRecalcItems(null);
		tblStartHistory.setInput(this);
		tblStartHistory.setLabelProvider(new ItemLabelProvider());
		pnResults.displayResults(null);
	}

	private void displayRecalcItems(List items) {
		tblStartHistory.getTable().setItemCount(items == null ? 0 : items.size());
		tblStartHistory.setContentProvider(new CommonItemContentProvider(items));
	}

	private void displayResults(List items) {
		pnResults.displayResults(items);
	}

	public TpOwnerRecalc getRecalculation() {
		return recalculation;
	}

	public void setRecalculation(TpOwnerRecalc recalculation) {
		this.recalculation = recalculation;
		resetRecalculation();
	}

	/**
	 * Displays recalculation.
	 */
	private void resetRecalculation() {
		if (recalculation == null) {
			displayRecalcItems(null);
			displayResults(null);
			lblDescription.setText("n/a");
		} else {
			displayRecalcItems(recalculation.getItems());
			String descr = GUIMessages.getMessage("comp.tpowner.recalc.current", new Object[] {
					recalculation.getProducer().getNumber(),
					Date.format(Date.create(recalculation.getCycleDate()))
			});
			lblDescription.setText(descr);
			displayResults(null);
		}
	}

	private void onCalculate() {
		if (recalculation == null)
			return;
		if (!Application.validateConnection())
			return;
		
		TpOwnerCalculationRequest request = new TpOwnerCalculationRequest(Application.USER_NAME, Application.PASSWORD);
		request.setRecalculation(recalculation);
		try {
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			TpOwnerCalculationRequest callback = (TpOwnerCalculationRequest) resp.getRequest();
			displayResults(callback.getResult().getCorrections());
		} catch (Exception ex) {
			MessageDialog.openError(getShell(), "Error", ex.toString());
		}
	}

	private void onSave() {
		// check recalculation results
		if (recalculation == null)
			return;
		List items = pnResults.getDisplayedItems();
		if (items == null || items.isEmpty())
			return;

		// check connection
		if (!Application.validateConnection())
			return;

		// sending request
		TpOwnerRecalcSaveRequest request = new TpOwnerRecalcSaveRequest(Application.USER_NAME, Application.PASSWORD);
		request.setItems(items);
		request.setAccountId(recalculation.getProducer().getId());
		request.setCycleDate(recalculation.getCycleDate());
		try {
			DefaultRecutilClient.processRequest(request);
			if (getSearchPane() != null) {
				getSearchPane().setStatusForAccount(recalculation.getProducer().getId(),
						TpOwnerAccount.STATUS_CALCULATED);
			}
			
		} catch (Exception ex) {
			MessageDialog.openError(getShell(), "Error", ex.toString());
		}
	}

	private void onDeclareError() {
		// check recalculation results
		if (recalculation == null)
			return;
		// can not declare as error without first calculating...
		List items = pnResults.getDisplayedItems();
		if (items == null || items.isEmpty())
			return;

		// check connection
		if (!Application.validateConnection())
			return;

		// sending request
		TpOwnerRecalcErrorRequest request = new TpOwnerRecalcErrorRequest(Application.USER_NAME, Application.PASSWORD);
		request.setAccountId(recalculation.getProducer().getId());
		request.setCycleDate(recalculation.getCycleDate());
		try {
			DefaultRecutilClient.processRequest(request);
			if (getSearchPane() != null) {
				getSearchPane().setStatusForAccount(recalculation.getProducer().getId(),
						TpOwnerAccount.STATUS_CALCULATED_WITH_ERRORS);
			}
		} catch (Exception ex) {
			MessageDialog.openError(getShell(), "Error", ex.toString());
		}

	}

	private void onShowSavedResults() {
		if (recalculation == null)
			return;
		if (!Application.validateConnection())
			return;

		TpOwnerRecalcSavedResultsRequest request = new TpOwnerRecalcSavedResultsRequest(Application.USER_NAME, Application.PASSWORD);
		request.setAccountId(recalculation.getProducer().getId());
		request.setCycleDate(recalculation.getCycleDate());
		List results = null;
		try {
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			TpOwnerRecalcSavedResultsRequest callback = (TpOwnerRecalcSavedResultsRequest) resp.getRequest();
			results = callback.getItems();
		} catch(Exception ex) {
			MessageDialog.openError(getShell(), "Error", ex.toString());
			return;
		}
		SavedResultsDialog dialog = new SavedResultsDialog(getShell(), SWT.NONE);
		dialog.setResults(results);
		dialog.open();
	}

	private class ItemLabelProvider extends LabelProvider implements ITableLabelProvider {
		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			TpOwnerItem item = (TpOwnerItem) element;
			switch(columnIndex) {
			case 0:
				return GUIUtils.getOperationImage(item.getOperation());
			case 2:
				Integer baseTariffId = item.getBaseTariffId();
				if (baseTariffId != null && TpOwnerUtils.isHighVoltageTariff(baseTariffId.intValue())) {
					return Plugin.getImage("icons/16x16/bullet_arrow_up.png");
				} else if (baseTariffId != null && TpOwnerUtils.isLowVoltageTariff(baseTariffId.intValue())) {
					return Plugin.getImage("icons/16x16/bullet_arrow_down.png");
				} else if (baseTariffId != null) {
					return Plugin.getImage("icons/16x16/bullet_delete.png");
				}
				return null;
			case 6:
				return item.isCycle() ? Plugin.getImage("icons/16x16/true.png") : null;
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			TpOwnerItem item = (TpOwnerItem) element;
			switch (columnIndex) {
			case 1:
				return Date.format(item.getItemDate());
			case 2:
				return GUITranslator.GEO_ASCII_TO_KA(item.getAccount().getNumber());
			case 3:
				return GUITranslator.GEO_ASCII_TO_KA(item.getAccount().getCustomer().getNumber());
			case 4:
				return GUITranslator.GEO_ASCII_TO_KA(item.getAccount().getCustomer().getName());
			case 5:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation().getName());
			case 7:
				return nf.format(item.getKwh());
			case 8:
				return nf.format(item.getGel());
			case 9:
				return nf.format(item.getKwhCorrected());
			case 10:
				return item.getItemNumber() == null ? "" : GUITranslator.GEO_ASCII_TO_KA(item.getItemNumber());
			case 11:
				return item.getPreviousChargeDate() == null ? "" : Date.format(item.getPreviousChargeDate());
			default:
				return "";
			}
		}
	}

	public TpOwnerAccountSearchPane getSearchPane() {
		return searchPane;
	}

	public void setSearchPane(TpOwnerAccountSearchPane searchPane) {
		this.searchPane = searchPane;
	}

}
