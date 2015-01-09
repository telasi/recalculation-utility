package telasi.recutil.gui.comp.recalc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.FacturaDetail;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.beans.TrashVoucher;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.comp.report.RecalcReportsManager;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.service.eclipse.recalc.RecalcVoucherSelectRequest3;

public class RecalcViewPane extends Composite {
	private TabFolder tabs;
	private TabItem tabHeader;
	private TabItem tabDetails;
	private TabItem tabTrash;
	private TabItem tabFactura;
	private TableViewer tblVoucher;
	private TableViewer tblFacturas;
	private TableViewer tblProperties;
	private TableViewer tblTrash;
	private Text txtValue;

	private Recalc recalc;
	private RecalcVoucher voucher;
	private List/* FacturaDetail */facturaDetails;
	private List/* TrashVoucher */trashVouchers;

	public RecalcViewPane(Composite parent, int style) {
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
			this.setSize(776, 495);
			{
				Composite composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					Label lblTitle = new Label(composite1, SWT.NONE);
					lblTitle.setText(GUIMessages.getMessage("comp.recalc_view_pane.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
				}
			}
			{
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.grabExcessHorizontalSpace = true;
				Label label1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				label1.setLayoutData(label1LData);
			}
			{
				Composite composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 48;
					lblImageLData.widthHint = 48;
					Label lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/recalc.png"));
				}
				{
					Label lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.recalc_view_pane.descr"));
				}
			}
			{
				Composite composite3 = new Composite(this, SWT.NONE);
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
					tabs = new TabFolder(composite3, SWT.NONE);
					{
						tabHeader = new TabItem(tabs, SWT.NONE);
						tabHeader.setText(GUIMessages.getMessage("comp.recalc_view_pane.voucher_head"));
						tabHeader.setImage(Plugin.getImage("icons/16x16/star.png"));
						{
							Composite composite8 = new Composite(tabs, SWT.NONE);
							GridLayout composite8Layout = new GridLayout();
							composite8Layout.marginHeight = 0;
							composite8Layout.marginWidth = 0;
							composite8.setLayout(composite8Layout);
							tabHeader.setControl(composite8);
							{
								GridData viewer1LData = new GridData();
								viewer1LData.horizontalAlignment = GridData.FILL;
								viewer1LData.grabExcessHorizontalSpace = true;
								viewer1LData.verticalAlignment = GridData.FILL;
								viewer1LData.grabExcessVerticalSpace = true;
								tblProperties = new TableViewer(composite8, SWT.BORDER | SWT.SINGLE | SWT.VIRTUAL | SWT.FULL_SELECTION);
								tblProperties.getControl().setLayoutData(viewer1LData);
								tblProperties.getTable().setHeaderVisible(true);
								tblProperties.getTable().setLinesVisible(true);
								{
									TableColumn colProperty = new TableColumn(tblProperties.getTable(), SWT.NONE);
									colProperty.setText(GUIMessages.getMessage("comp.general.property"));
									colProperty.setWidth(300);
								}
								{
									TableColumn colValue = new TableColumn(tblProperties.getTable(), SWT.NONE);
									colValue.setText(GUIMessages.getMessage("comp.general.value"));
									colValue.setWidth(600);
								}
								{
									TableColumn colEnd = new TableColumn(tblProperties.getTable(), SWT.NONE);
									colEnd.setWidth(10);
									colEnd.setResizable(false);
								}
								tblProperties.addSelectionChangedListener(new ISelectionChangedListener() {
									public void selectionChanged(SelectionChangedEvent event) {
										int index = tblProperties.getTable().getSelectionIndex();
										if (index == -1) {
											txtValue.setText("");
											return;
										}
										CommonItemContentProvider provider = (CommonItemContentProvider) tblProperties.getContentProvider();
										String[] pair = (String[]) provider.items.get(index);
										txtValue.setText(pair[1] == null ? "" : GUITranslator.GEO_ASCII_TO_KA(pair[1]));
									}
								});
							}
							{
								GridData txtValueLData = new GridData();
								txtValueLData.horizontalAlignment = GridData.FILL;
								txtValueLData.grabExcessHorizontalSpace = true;
								txtValueLData.heightHint = 150;
								txtValue = new Text(composite8, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL | SWT.READ_ONLY);
								txtValue.setLayoutData(txtValueLData);
							}
						}
					}
					{
						tabDetails = new TabItem(tabs, SWT.NONE);
						tabDetails.setText(GUIMessages.getMessage("comp.recalc_view_pane.voucher_details"));
						tabDetails.setImage(Plugin.getImage("icons/16x16/bop/voucher.png"));
						{
							Composite composite9 = new Composite(tabs, SWT.NONE);
							GridLayout composite9Layout = new GridLayout();
							composite9Layout.marginHeight = 0;
							composite9Layout.marginWidth = 0;
							composite9.setLayout(composite9Layout);
							tabDetails.setControl(composite9);
							{
								tblVoucher = new TableViewer(composite9, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.VIRTUAL);
								tblVoucher.getTable().setLinesVisible(true);
								GridData viewer2LData = new GridData();
								viewer2LData.horizontalAlignment = GridData.FILL;
								viewer2LData.grabExcessHorizontalSpace = true;
								viewer2LData.verticalAlignment = GridData.FILL;
								viewer2LData.grabExcessVerticalSpace = true;
								tblVoucher.getControl().setLayoutData(viewer2LData);
								tblVoucher.getTable().setHeaderVisible(true);
								{
									TableColumn colImage = new TableColumn(tblVoucher.getTable(), SWT.NONE);
									colImage.setWidth(20);
									colImage.setResizable(false);
								}
								{
									TableColumn colOperation = new TableColumn(tblVoucher.getTable(), SWT.NONE);
									colOperation.setText(GUIMessages.getMessage("comp.general.operation"));
									colOperation.setWidth(250);
								}
								{
									TableColumn colGel = new TableColumn(tblVoucher.getTable(), SWT.RIGHT);
									colGel.setText(GUIMessages.getMessage("comp.general.gel"));
									colGel.setWidth(100);
								}
								{
									TableColumn colKwh = new TableColumn(tblVoucher.getTable(), SWT.RIGHT);
									colKwh.setText(GUIMessages.getMessage("comp.general.kwh"));
									colKwh.setWidth(100);
								}
								{
									TableColumn tableColumn1 = new TableColumn(tblVoucher.getTable(), SWT.NONE);
									tableColumn1.setWidth(10);
									tableColumn1.setResizable(false);
								}
							}
						}
					}
					{
						tabTrash = new TabItem(tabs, SWT.NONE);
						tabTrash.setText(GUIMessages.getMessage("comp.recalc_view_pane.trash_voucher"));
						tabTrash.setImage(Plugin.getImage("icons/16x16/trash.png"));
						{
							Composite cmpTrash = new Composite(tabs, SWT.NONE);
							GridLayout layoTrash = new GridLayout();
							layoTrash.marginHeight = 0;
							layoTrash.marginWidth = 0;
							cmpTrash.setLayout(layoTrash);
							tabTrash.setControl(cmpTrash);
							{
								tblTrash = new TableViewer(cmpTrash, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.VIRTUAL);
								tblTrash.getTable().setLinesVisible(true);
								GridData viewerData = new GridData();
								viewerData.horizontalAlignment = GridData.FILL;
								viewerData.grabExcessHorizontalSpace = true;
								viewerData.verticalAlignment = GridData.FILL;
								viewerData.grabExcessVerticalSpace = true;
								tblTrash.getControl().setLayoutData(viewerData);
								tblTrash.getTable().setHeaderVisible(true);
								{
									TableColumn colOperation = new TableColumn(tblTrash.getTable(), SWT.NONE);
									colOperation.setText(GUIMessages.getMessage("comp.general.operation"));
									colOperation.setWidth(250);
								}
								{
									TableColumn colGel = new TableColumn(tblTrash.getTable(), SWT.RIGHT);
									colGel.setText(GUIMessages.getMessage("comp.general.gel"));
									colGel.setWidth(100);
								}
								{
									TableColumn colKwh = new TableColumn(tblTrash.getTable(), SWT.RIGHT);
									colKwh.setText(GUIMessages.getMessage("comp.general.kwh"));
									colKwh.setWidth(100);
								}
								{
									TableColumn tableColumn1 = new TableColumn(tblTrash.getTable(), SWT.NONE);
									tableColumn1.setWidth(10);
									tableColumn1.setResizable(false);
								}
							}
						}
					}
					{
						tabFactura = new TabItem(tabs, SWT.NONE);
						tabFactura.setText(GUIMessages.getMessage("comp.recalc_view_pane.voucher_factura"));
						{
							Composite composite4 = new Composite(tabs, SWT.NONE);
							GridLayout composite4Layout = new GridLayout();
							composite4Layout.marginHeight = 0;
							composite4Layout.marginWidth = 0;
							composite4.setLayout(composite4Layout);
							tabFactura.setControl(composite4);
							{
								GridData viewer3LData = new GridData();
								viewer3LData.horizontalAlignment = GridData.FILL;
								viewer3LData.grabExcessHorizontalSpace = true;
								viewer3LData.verticalAlignment = GridData.FILL;
								viewer3LData.grabExcessVerticalSpace = true;
								tblFacturas = new TableViewer(composite4, SWT.BORDER | SWT.MULTI | SWT.VIRTUAL | SWT.FULL_SELECTION);
								tblFacturas.getControl().setLayoutData(viewer3LData);
								tblFacturas.getTable().setLinesVisible(true);
								tblFacturas.getTable().setHeaderVisible(true);
								{
									TableColumn colOperation2 = new TableColumn(tblFacturas.getTable(), SWT.NONE);
									colOperation2.setText("Operation");
									colOperation2.setWidth(250);
								}
								{
									TableColumn colItem = new TableColumn(tblFacturas.getTable(), SWT.NONE);
									colItem.setText("Item ID");
									colItem.setWidth(150);
								}
								{
									TableColumn colItemDate = new TableColumn(tblFacturas.getTable(), SWT.NONE);
									colItemDate.setText("Item Date");
									colItemDate.setWidth(150);
								}
								{
									TableColumn colKwh2 = new TableColumn(tblFacturas.getTable(), SWT.RIGHT);
									colKwh2.setText("kWh");
									colKwh2.setWidth(100);
								}
								{
									TableColumn colGel2 = new TableColumn(tblFacturas.getTable(), SWT.RIGHT);
									colGel2.setText("GEL");
									colGel2.setWidth(100);
								}
								{
									TableColumn colEnd2 = new TableColumn(tblFacturas.getTable(), SWT.NONE);
									colEnd2.setWidth(10);
									colEnd2.setResizable(false);
								}
							}
						}
						tabFactura.setImage(Plugin.getImage("icons/16x16/print.png"));
					}
					GridData tabsLData = new GridData();
					tabsLData.horizontalAlignment = GridData.FILL;
					tabsLData.grabExcessHorizontalSpace = true;
					tabsLData.verticalAlignment = GridData.FILL;
					tabsLData.grabExcessVerticalSpace = true;
					tabs.setLayoutData(tabsLData);
					tabs.setSelection(0);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	private void initDataBehaivour() {
		reset();
		tblProperties.setInput(this);
		tblProperties.setLabelProvider(new PropertiesLabelProvider());
		tblVoucher.setInput(this);
		tblVoucher.setLabelProvider(new DetailsLabelProvider());
		tblFacturas.setInput(this);
		tblFacturas.setLabelProvider(new FacturaExpansionLabelProvider());
		tblTrash.setInput(this);
		tblTrash.setLabelProvider(new TrashLabelProvider());
	}

	public void refresh() {
		if (recalc == null)
			return;
		if (!Application.validateConnection())
			return;
		RecalcVoucherSelectRequest3 request = new RecalcVoucherSelectRequest3(Application.USER_NAME, Application.PASSWORD);
		request.setRecalc(recalc);
		try {
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			RecalcVoucherSelectRequest3 callback = (RecalcVoucherSelectRequest3) resp.getRequest();
			voucher = callback.getVoucher();
			facturaDetails = callback.getFacturaDetails();
			trashVouchers = callback.getTrashVouchers();
			for (int i = 0; i < voucher.getDetails().size() && voucher.getDetails() != null; i++) {
				DiffDetail detail = (DiffDetail) voucher.getDetails().get(i);
				detail.getOriginalItem().setOperation(Cache.findOperationById(detail.getOperation().getId()));
			}
			for (int i = 0; facturaDetails != null && i < facturaDetails.size(); i++) {
				FacturaDetail detail = (FacturaDetail) facturaDetails.get(i);
				detail.setOperation(Cache.findOperationById(detail.getOperation().getId()));
			}
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
			voucher = null;
			t.printStackTrace();
		}
		reset();
	}

	private void reset() {
		if (voucher == null) {
			tblProperties.setContentProvider(new CommonItemContentProvider(null));
			tblProperties.setItemCount(0);
			tblVoucher.setContentProvider(new CommonItemContentProvider(null));
			tblVoucher.setItemCount(0);
			tblFacturas.setContentProvider(new CommonItemContentProvider(null));
			tblFacturas.setItemCount(0);
			tblTrash.setContentProvider(new CommonItemContentProvider(null));
			tblTrash.setItemCount(0);
		} else {
			tblProperties.setContentProvider(new CommonItemContentProvider(voucher.getProperties()));
			tblProperties.setItemCount(voucher.getProperties() == null ? 0 : voucher.getProperties().size());
			tblVoucher.setContentProvider(new CommonItemContentProvider(voucher.getDetails()));
			tblVoucher.getTable().setItemCount(voucher.getDetails() == null ? 0 : voucher.getDetails().size());
			tblFacturas.setContentProvider(new CommonItemContentProvider(facturaDetails));
			tblFacturas.getTable().setItemCount(facturaDetails == null ? 0 : facturaDetails.size());
			tblTrash.setContentProvider(new CommonItemContentProvider(trashVouchers));
			tblTrash.setItemCount(trashVouchers == null ? 0 : trashVouchers.size());
		}
	}

	private class PropertiesLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null)
				return "";
			String[] array = (String[]) element;
			switch (columnIndex) {
			case 0:
				return GUIMessages.getMessage(array[0]);
			case 1:
				return GUITranslator.GEO_ASCII_TO_KA(cutOnNewLine(array[1]));
			default:
				return "";
			}
		}
	}

	private class DetailsLabelProvider extends LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			if (element == null) {
				return null;
			}
			DiffDetail detail = (DiffDetail) element;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getOperationImage(detail.getOperation());
			default:
				return null;
			}
		}

		private NumberFormat nf = new DecimalFormat("#,##0.00");

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return null;
			}
			DiffDetail detail = (DiffDetail) element;
			switch (columnIndex) {
			case 1:
				return GUITranslator.GEO_ASCII_TO_KA(detail.getOperation().getName());
			case 2:
				return nf.format(detail.getOriginalItem().getCharge().getGel());
			case 3:
				return nf.format(detail.getOriginalItem().getCharge().getKwh());
			default:
				return "";
			}
		}

	}

	private class FacturaExpansionLabelProvider extends LabelProvider implements ITableLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,##0.00");

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null)
				return "";
			FacturaDetail item = (FacturaDetail) element;
			switch (columnIndex) {
			case 0:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation().getName());
			case 1:
				return String.valueOf(item.getOriginalItemId());
			case 2:
				return item.getItemDate().toString();
			case 3:
				return nf.format(item.getKwh());
			case 4:
				return nf.format(item.getGel());
			default:
				return "";
			}
		}
	}

	private class TrashLabelProvider extends LabelProvider implements ITableLabelProvider {
		private NumberFormat nf = new DecimalFormat("#,##0.00");

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null)
				return "";
			TrashVoucher item = (TrashVoucher) element;
			switch (columnIndex) {
			case 0:
				return TrashVoucher.findOperation(item.getTrashOperation()).getName();
			case 1:
				return nf.format(item.getKwh());
			case 2:
				return nf.format(item.getGel());
			default:
				return "";
			}
		}
	}

	private static String cutOnNewLine(String someString) {
		if (someString == null) {
			return someString;
		}
		int index = -1;
		for (int i = 0; i < someString.length(); i++) {
			char aChar = someString.charAt(i);
			if (aChar == '\n') {
				index = i;
				break;
			}
		}
		return index == -1 ? someString : someString.substring(0, index);
	}

	public void print() {
		if (voucher == null)
			return;
		if (!isFacturaTabSelected()) {
			RecalcReportsManager.openVoucherReport(voucher, trashVouchers, getShell());
		} else {
			RecalcReportsManager.operVoucherFacturaReport(voucher, facturaDetails, getShell());
		}
	}

	public boolean isFacturaTabSelected() {
		return tabs.getSelectionIndex() == 3;
	}

}
