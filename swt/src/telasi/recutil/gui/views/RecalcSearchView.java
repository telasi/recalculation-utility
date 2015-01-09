package telasi.recutil.gui.views;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.beans.Recalc;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.exchange.ServerExchangeNode;
import telasi.recutil.gui.comp.recalcsearch.RecalcSearchPane;
import telasi.recutil.gui.comp.report.ReportViewer;
import telasi.recutil.gui.plugin.ConnectionDescriptor;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.plugin.Settings;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.reports.recalc.VoucherReesterReport;

public class RecalcSearchView extends ViewPart {
	public static final String ID_VIEW = "telasi.recutil.gui.views.RecalcSearchView"; //$NON-NLS-1$
	private RecalcSearchPane pnRecalcSearch;
	Composite composite1;
	ToolBar toolbar;
	ToolItem tiOpen;
	ToolItem tiSendData;
	ToolItem tiPrint;

	public RecalcSearchView() {
		super();
	}

	public void createPartControl(Composite parent) {
		setPartName(GUIMessages.getMessage("application.action.recalcsearch"));
		parent.setSize(660, 147);
		composite1 = new Composite(parent, SWT.NULL);
		GridLayout composite1Layout = new GridLayout();
		composite1Layout.verticalSpacing = 0;
		composite1Layout.marginHeight = 0;
		composite1Layout.marginWidth = 0;
		composite1Layout.horizontalSpacing = 0;
		composite1.setLayout(composite1Layout);
		{
			toolbar = new ToolBar(composite1, SWT.NONE);
			tiOpen = new ToolItem(toolbar, SWT.NONE);
			tiOpen.setToolTipText(GUIMessages
					.getMessage("comp.recalcsearch.openselectedrecalc"));
			tiOpen.setImage(Plugin.getImage("icons/22x22/open.png"));
			tiOpen.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					onOpenRecalc();
				}
			});
			new ToolItem(toolbar, SWT.SEPARATOR);
			tiSendData = new ToolItem(toolbar, SWT.NONE);
			tiSendData.setToolTipText(GUIMessages
					.getMessage("comp.recalcsearch.senddata"));
			tiSendData.setImage(Plugin.getImage("icons/22x22/src.png"));
			tiSendData.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					onSendRecalcs();
				}
			});
			new ToolItem(toolbar, SWT.SEPARATOR);
			tiPrint = new ToolItem(toolbar, SWT.NONE);
			tiPrint.setToolTipText(GUIMessages.getMessage("comp.general.print"));
			tiPrint.setImage(Plugin.getImage("icons/22x22/print.png"));
			tiPrint.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					printRecalcReester(getSite().getShell(), pnRecalcSearch.getSelected());
				}
			});
		}
		{
			GridData pnRecalcSearchLData = new GridData();
			pnRecalcSearchLData.horizontalAlignment = GridData.FILL;
			pnRecalcSearchLData.grabExcessHorizontalSpace = true;
			pnRecalcSearchLData.verticalAlignment = GridData.FILL;
			pnRecalcSearchLData.grabExcessVerticalSpace = true;
			pnRecalcSearch = new RecalcSearchPane(composite1, SWT.NONE);
			pnRecalcSearch.setLayoutData(pnRecalcSearchLData);
			pnRecalcSearch.getViewer().getTable().addMouseListener(
					new MouseAdapter() {
						@Override
						public void mouseDoubleClick(MouseEvent e) {
							onOpenRecalc();
						}
					});
			pnRecalcSearch.getViewer().addSelectionChangedListener(
					new ISelectionChangedListener() {
						public void selectionChanged(SelectionChangedEvent event) {
							validateView();
						}
					});
		}
		validateView();
	}

	public void setFocus() {
	}

	public void dispose() {
		super.dispose();
	}

	/**
	 * This code is called when recalculation is selected for open.
	 */
	private void onOpenRecalc() {
		List recalcs = pnRecalcSearch.getSelected();
		if (recalcs == null || recalcs.isEmpty()) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			IWorkbenchWindow workbench = getSite().getWorkbenchWindow();
			IViewReference ref = workbench.getActivePage().findViewReference(
					RecalculationView.ID_VIEW,
					RecalculationView.ACTIVE_SECONDARY_ID);
			if (ref == null) {
				workbench.getActivePage().showView(RecalculationView.ID_VIEW,
						RecalculationView.ACTIVE_SECONDARY_ID,
						IWorkbenchPage.VIEW_ACTIVATE);
			}
			ref = workbench.getActivePage().findViewReference(
					RecalculationView.ID_VIEW,
					RecalculationView.ACTIVE_SECONDARY_ID);
			if (ref == null) {
				throw new IllegalArgumentException(
						"Can not open recalculation view.");
			}
			RecalculationView view = (RecalculationView) ref.getView(false);
			workbench.getActivePage().bringToTop(view);

			Recalc recalc = (Recalc) recalcs.get(0);
			view.openRecalc(recalc);

		} catch (Exception ex) {
			MessageDialog.openError(getSite().getShell(), GUIMessages
					.getMessage("comp.general.error"), ex.toString());
		}

	}

	private void onSendRecalcs() {
		List recalcs = pnRecalcSearch.getSelected();
		if (recalcs == null || recalcs.isEmpty()) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			// create source server
			ConnectionDescriptor descr = Settings.getLastConnectionDescriptor();
			ServerExchangeNode src = new ServerExchangeNode(descr);
			src.setUserName(Application.USER_NAME);
			src.setPassword(Application.PASSWORD);

			// find view
			IWorkbenchWindow workbench = getSite().getWorkbenchWindow();
			IViewReference ref = workbench.getActivePage().findViewReference(
					RecalcExchangeView.ID_VIEW,
					RecalcExchangeView.ACTIVE_SECONDARY_ID);
			if (ref == null) {
				workbench.getActivePage().showView(RecalcExchangeView.ID_VIEW,
						RecalcExchangeView.ACTIVE_SECONDARY_ID,
						IWorkbenchPage.VIEW_ACTIVATE);
			}
			ref = workbench.getActivePage().findViewReference(
					RecalcExchangeView.ID_VIEW,
					RecalcExchangeView.ACTIVE_SECONDARY_ID);
			if (ref == null) {
				throw new IllegalArgumentException(
						"Can not open exchange view.");
			}
			RecalcExchangeView view = (RecalcExchangeView) ref.getView(false);
			view.addSource(src, recalcs);
			workbench.getActivePage().bringToTop(view);

		} catch (Exception ex) {
			MessageDialog.openError(getSite().getShell(), GUIMessages
					.getMessage("comp.general.error"), ex.toString());
			ex.printStackTrace();
		}
	}

	private void validateView() {
		List recalcs = pnRecalcSearch.getSelected();
		boolean selected = recalcs != null && !recalcs.isEmpty();
		tiOpen.setEnabled(selected);
		tiSendData.setEnabled(selected);
		tiPrint.setEnabled(selected);
	}

	public static void printRecalcReester(Shell shell, List reester) {
		VoucherReesterReport report = new VoucherReesterReport();
		report.setVouchers(reester);
		String html = report.generateHTML();
		ReportViewer viewer = new ReportViewer(shell, SWT.NONE);
		viewer.setHTML(html);
		viewer.open();
	}

}
