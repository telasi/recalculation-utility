package telasi.recutil.gui.comp.exchange;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.comp.recalcsearch.RecalcSearchDialog;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.views.RecalcSearchView;

public class RecalcSelectorPane extends Composite {
	private TableViewer viewer;
	private ToolBar toolbar;
	private TableColumn colAccount;
	private TableColumn colAdvisor;
	private TableColumn colOperator;
	private TableColumn colSaveDate;
	private TableColumn colCreateDate;
	private TableColumn colCustomer;
	private TableColumn colNumber;
	private TableColumn colImage;
	private ToolItem tiRemove;
	private ToolItem tiAdd;
	private ToolItem tiPrint;
	private IExchangeNode source;
	private List displayed = new ArrayList();
	private boolean blocked = false;

	public RecalcSelectorPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(459, 290);
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.grabExcessVerticalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewer = new TableViewer(this, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.VIRTUAL);
				viewer.getTable().setHeaderVisible(true);
				viewer.getControl().setLayoutData(viewerLData);
				viewer.getTable().setLinesVisible(true);
				viewer.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						validateView();
					}
				});
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
					colCustomer.setText(GUIMessages.getMessage("comp.general.customer"));
					colCustomer.setWidth(100);
				}
				{
					colAccount = new TableColumn(viewer.getTable(), SWT.NONE);
					colAccount.setText(GUIMessages.getMessage("comp.general.account"));
					colAccount.setWidth(100);
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
			{
				toolbar = new ToolBar(this, SWT.NONE);
				{
					tiAdd = new ToolItem(toolbar, SWT.NONE);
					tiAdd.setImage(Plugin.getImage("icons/22x22/add.png"));
					tiAdd.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onAdd();
						}
					});
				}
				{
					tiRemove = new ToolItem(toolbar, SWT.NONE);
					tiRemove.setImage(Plugin.getImage("icons/22x22/remove.png"));
					tiRemove.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onRemove();
						}
					});
				}
				{
					new ToolItem(toolbar, SWT.SEPARATOR);
					tiPrint = new ToolItem(toolbar, SWT.NONE);
					tiPrint.setImage(Plugin.getImage("icons/22x22/print.png"));
					tiPrint.setToolTipText(GUIMessages.getMessage("comp.general.print"));
					tiPrint.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							onPrint();
						}
					});
				}
			}
			reset();
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reset() {
		displayRecalcs(new ArrayList());
		
	}

	void validateView() {
		tiAdd.setEnabled(!blocked && source != null);
		List selected = getSelected();
		tiRemove.setEnabled(!blocked && source != null && selected != null && !selected.isEmpty());
		List items = getAll();
		tiPrint.setEnabled(!blocked && items != null && !items.isEmpty());
	}

	@SuppressWarnings("unchecked")
	private List getSelected() {
		int[] indecies = viewer.getTable().getSelectionIndices();
		List selected = new ArrayList();
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer.getContentProvider();
		for (int i = 0; indecies != null && i < indecies.length; i++)
			selected.add(provider.items.get(indecies[i]));
		return selected;
	}
	
	public List getAll() {
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer.getContentProvider();
		return provider.items;
	}

	public IExchangeNode getSource() {
		return source;
	}

	private void initDataBehaivour() {
		displayRecalcs(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new RecalcLabelProvider());
	}

	void displayRecalcs(List recalcs) {
		viewer.setItemCount(recalcs == null ? 0 : recalcs.size());
		viewer.setContentProvider(new CommonItemContentProvider(recalcs));
		displayed = recalcs;
		validateView();
	}

	public void setSource(IExchangeNode source) {
		this.source = source;
	}

	@SuppressWarnings("unchecked")
	private void onAdd() {
		// empty source
		if (source == null) {
			return;
		} else
		// server source
		if (source.getType() == IExchangeNode.SERVER) {
			ServerExchangeNode server = (ServerExchangeNode) source;
			RecalcSearchDialog dialog = new RecalcSearchDialog(getShell(), SWT.NONE);
			dialog.setServerInfo(server);
			dialog.open();
			if (!dialog.isApproved())
				return;
			List selected = dialog.getSelected();
			for (int i = 0; selected != null && i < selected.size(); i++) {
				Recalc recalc = (Recalc) selected.get(i);
				boolean add = true;
				for (int j = 0; j < displayed.size(); j++) {
					Recalc displ = (Recalc) displayed.get(j);
					if (displ.getId() == recalc.getId()) {
						add = false;
						break;
					}
				}
				if (add)
					displayed.add(recalc);
			}
			displayRecalcs(displayed);
		} else
		// file source
		if (source.getType() == IExchangeNode.FILE) {
			// TODO
		}
	}

	@SuppressWarnings("unchecked")
	private void onRemove() {
		List selected = getSelected();
		if (selected == null || selected.isEmpty())
			return;
		String msg = GUIMessages.getMessage("comp.general.confirm_delete_object_count", new Object[] {selected.size()});
		String title = GUIMessages.getMessage("comp.general.warning");
		boolean resp = MessageDialog.openQuestion(getShell(), title, msg);
		if (!resp)
			return;
		displayed.removeAll(selected);
		displayRecalcs(displayed);
	}

	private void onPrint() {
//		List items = getAll();
//		VoucherReesterReport report = new VoucherReesterReport();
//		report.setVouchers(items);
//		String html = report.generateHTML();
//		
//		ReportViewer viewer = new ReportViewer(getShell(), SWT.NONE);
//		viewer.setHTML(html);
//		viewer.open();
		RecalcSearchView.printRecalcReester(getShell(), getAll());
	}
	
	private class RecalcLabelProvider
	extends LabelProvider
	implements ITableLabelProvider {

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
				return Date.format(recalc.getCreateDate());
			case 5:
				return recalc.getSaveDate() == null ? "" : Date.format(recalc.getSaveDate());
			case 6:
				return recalc.getSaveUser() == null ? "" : GUITranslator.GEO_ASCII_TO_KA(recalc.getSaveUser().getFullName());
			case 7:
				return recalc.getAdvisor() == null ? "" : GUITranslator.GEO_ASCII_TO_KA(recalc.getAdvisor().getFullName());
			default:
				return "";
			}
		}
	}

	protected void blockActions(boolean block) {
		this.blocked = block;
		validateView();
		toolbar.update();
	} 

	@SuppressWarnings("unchecked")
	protected void clearSuccessfull(List<Long> ids) {
		List forRemove = new ArrayList();
		for (int i = 0; i < displayed.size(); i++) {
			Recalc displ = (Recalc) displayed.get(i);
			if (ids.contains(displ.getId())) {
				forRemove.add(displ);
			}
		}
		displayed.removeAll(forRemove);
		displayRecalcs(displayed);
	}

}
