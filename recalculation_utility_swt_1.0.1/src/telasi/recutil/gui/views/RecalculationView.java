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
package telasi.recutil.gui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.beans.User;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.cust.CustomerSearchPane;
import telasi.recutil.gui.comp.cust.CustomerSimplePane;
import telasi.recutil.gui.comp.recalc.NewRecalcDialog;
import telasi.recutil.gui.comp.recalc.RecalcEditingPane;
import telasi.recutil.gui.comp.recalc.RecalcHistoryPane;
import telasi.recutil.gui.comp.recalc.RecalcProperties;
import telasi.recutil.gui.comp.recalc.RecalcViewPane;
import telasi.recutil.gui.comp.report.RecalcReportsManager;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.service.eclipse.recalc.RecalcCopyRequest;
import telasi.recutil.service.eclipse.recalc.RecalcDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcFinalizeRequest;
import telasi.recutil.service.eclipse.recalc.RecalcSaveRequest3;
import telasi.recutil.service.eclipse.recalc.RecalcUnblockRequest;
import telasi.recutil.service.eclipse.recalc.RecalcVoucherSelectRequest3;

public class RecalculationView extends ViewPart {
	public static final String ID_VIEW = "ge.telasi.recut.guiapp.views.RecalculationView"; //$NON-NLS-1$
	/**
	 * This secondary ID is used to identify those instances of this view which
	 * are used by application.
	 */
	public static final String ACTIVE_SECONDARY_ID = "5";
	public static final int MODE_NONE = 0;
	public static final int MODE_EDIT = 1;
	public static final int MODE_VIEW = 2;
	private Composite composite1;
	private Composite composite2;
	private ToolBar toolBar1;
	private Composite composite3;
	private SashForm sashForm1;
	/**
	 * Is additional pane visible?
	 */
	private boolean additionalPaneVisible = true;
	private int mode = MODE_NONE;
	// private ToolItem toolItem1;
	private ToolItem tiDeleteRecalc;
	private ToolItem tiRecalcProperties;
	private ToolItem toolItem2;
	private ToolItem tiStop;
	private ToolItem tiBuild;
	private RecalcEditingPane recalcEditingPane;
	private RecalcViewPane recalcViewPane;
	private ToolItem toolItem3;
	private ToolItem tiRecalculate;
	private ToolItem tiEdit;
	private RecalcHistoryPane recalcHistoryPane1;
	private CustomerSimplePane customerPane1;
	private Label lblSeparator1;
	private ToolItem tiRecalcUnblock;
	private ToolItem tiView;
	private ToolItem tiSave;
	private ToolItem toolItem5;
	private ToolItem toolItem4;
	private ToolItem tiPrint;
	private CustomerSearchPane customerSearchPane1;
	private ToolItem tiNewRecalc;
	private ToolItem tiCopyRecalc;
	// private ToolItem tiChangeView;
	private Menu menuChangeView;

	public RecalculationView() {
		super();
	}

	public void createPartControl(Composite parent) {
		{
			parent.setSize(400, 400);
		}
		composite1 = new Composite(parent, SWT.NULL);
		GridLayout composite1Layout = new GridLayout();
		composite1Layout.horizontalSpacing = 0;
		composite1Layout.marginHeight = 0;
		composite1Layout.marginWidth = 0;
		composite1Layout.verticalSpacing = 0;
		composite1.setLayout(composite1Layout);
		{
			GridData toolBar1LData = new GridData();
			toolBar1LData.horizontalAlignment = GridData.FILL;
			toolBar1LData.grabExcessHorizontalSpace = true;
			toolBar1 = new ToolBar(composite1, SWT.NONE);
			toolBar1.setLayoutData(toolBar1LData);
			/*
			 * { tiChangeView = new ToolItem(toolBar1, SWT.NONE);
			 * tiChangeView.addSelectionListener(new SelectionAdapter() { public
			 * void widgetSelected(SelectionEvent e) { additionalPaneVisible =
			 * !additionalPaneVisible; validateView(); } }); } { toolItem1 = new
			 * ToolItem(toolBar1, SWT.SEPARATOR); toolItem1.setText(""); }
			 */
			{
				tiPrint = new ToolItem(toolBar1, SWT.NONE);
				tiPrint.setImage(Plugin.getImage("icons/22x22/print.png"));
				tiPrint.setToolTipText(GUIMessages.getMessage("comp.general.print"));
				tiPrint.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onPrint();
					}
				});
			}
			{
				toolItem2 = new ToolItem(toolBar1, SWT.SEPARATOR);
				toolItem2.setText("");
			}
			{
				tiNewRecalc = new ToolItem(toolBar1, SWT.NONE);
				tiNewRecalc.setToolTipText(GUIMessages.getMessage("comp.recalc_view.new_recalculation"));
				tiNewRecalc.setImage(Plugin.getImage("icons/22x22/new.gif"));
				tiNewRecalc.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onNew();
					}
				});
			}
			{
				tiCopyRecalc = new ToolItem(toolBar1, SWT.NONE);
				tiCopyRecalc.setToolTipText(GUIMessages.getMessage("comp.recalc_view.copy_recalc"));
				tiCopyRecalc.setImage(Plugin.getImage("icons/22x22/edit_copy.png"));
				tiCopyRecalc.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onCopy();
					}
				});
			}
			{
				tiDeleteRecalc = new ToolItem(toolBar1, SWT.NONE);
				tiDeleteRecalc.setToolTipText(GUIMessages.getMessage("comp.recalc_view.delete_recalculation"));
				tiDeleteRecalc.setImage(Plugin.getImage("icons/22x22/trash.png"));
				tiDeleteRecalc.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onDelete();
					}
				});
			}
			{
				tiRecalcProperties = new ToolItem(toolBar1, SWT.NONE);
				tiRecalcProperties.setToolTipText(GUIMessages.getMessage("comp.recalc_view.recalculation_properties"));
				tiRecalcProperties.setImage(Plugin.getImage("icons/22x22/properties.gif"));
				tiRecalcProperties.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onProperties();
					}
				});
			}
			{
				tiRecalcUnblock = new ToolItem(toolBar1, SWT.DROP_DOWN);
				tiRecalcUnblock.setImage(Plugin.getImage("icons/22x22/unlock.png"));
				tiRecalcUnblock.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						if (event.detail == SWT.ARROW || event.detail == 0) {
							Rectangle rect = tiRecalcUnblock.getBounds();
							Point pt = new Point(rect.x, rect.y + rect.height);
							pt = toolBar1.toDisplay(pt);
							menuChangeView.setLocation(pt.x, pt.y);
							menuChangeView.setVisible(true);
						}
					}
				});
				// tiRecalcUnblock.setToolTipText(GUIMessages.getMessage("comp.recalc_view.unblock_recalc"));
				// tiRecalcUnblock.addSelectionListener(new SelectionAdapter() {
				// public void widgetSelected(SelectionEvent e) {
				// onUnblock();
				// }
				// });
			}
			{
				toolItem3 = new ToolItem(toolBar1, SWT.SEPARATOR);
				toolItem3.setText("");
			}
			{
				tiEdit = new ToolItem(toolBar1, SWT.NONE);
				tiEdit.setToolTipText(GUIMessages.getMessage("comp.recalc_view.recalculation_open"));
				tiEdit.setImage(Plugin.getImage("icons/22x22/open.png"));
				tiEdit.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onEdit();
					}
				});
			}
			{
				tiView = new ToolItem(toolBar1, SWT.NONE);
				tiView.setImage(Plugin.getImage("icons/22x22/view.png"));
				tiView.setToolTipText(GUIMessages.getMessage("comp.recalc_view.recalculation_view"));
				tiView.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onView();
					}
				});
			}
			{
				tiStop = new ToolItem(toolBar1, SWT.NONE);
				tiStop.setImage(Plugin.getImage("icons/22x22/stop.png"));
				tiStop.setToolTipText(GUIMessages.getMessage("comp.recalc_view.recalculation_stop"));
				tiStop.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onStop();
					}
				});
			}
			{
				toolItem4 = new ToolItem(toolBar1, SWT.SEPARATOR);
				toolItem4.setText("");
			}
			{
				tiBuild = new ToolItem(toolBar1, SWT.NONE);
				tiBuild.setImage(Plugin.getImage("icons/22x22/build.png"));
				tiBuild.setToolTipText(GUIMessages.getMessage("comp.recalc_view.recalculation_build"));
				tiBuild.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onRebuild();
					}
				});
			}
			{
				tiRecalculate = new ToolItem(toolBar1, SWT.NONE);
				tiRecalculate.setImage(Plugin.getImage("icons/22x22/recalc.png"));
				tiRecalculate.setToolTipText(GUIMessages.getMessage("comp.recalc_view.recalculation_recalc"));
				tiRecalculate.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onRecalculate();
					}
				});
			}
			{
				toolItem5 = new ToolItem(toolBar1, SWT.SEPARATOR);
				toolItem5.setText("");
			}
			{
				tiSave = new ToolItem(toolBar1, SWT.NONE);
				tiSave.setToolTipText(GUIMessages.getMessage("comp.general.save"));
				tiSave.setImage(Plugin.getImage("icons/22x22/save.png"));
				tiSave.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onSave();
					}
				});
			}
		}
		{
			sashForm1 = new SashForm(composite1, SWT.NONE);
			GridData sashForm1LData = new GridData();
			sashForm1LData.horizontalAlignment = GridData.FILL;
			sashForm1LData.grabExcessHorizontalSpace = true;
			sashForm1LData.verticalAlignment = GridData.FILL;
			sashForm1LData.grabExcessVerticalSpace = true;
			sashForm1.setLayoutData(sashForm1LData);
			sashForm1.setSize(60, 30);
			{
				Composite leftComposite = new Composite(sashForm1, SWT.BORDER);
				GridLayout leftCompositeLayout = new GridLayout();
				leftCompositeLayout.horizontalSpacing = 0;
				leftCompositeLayout.marginHeight = 0;
				leftCompositeLayout.marginWidth = 0;
				leftCompositeLayout.verticalSpacing = 0;
				leftComposite.setLayout(leftCompositeLayout);
				{
					composite2 = new Composite(leftComposite, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.horizontalSpacing = 0;
					composite2Layout.marginHeight = 0;
					composite2Layout.marginWidth = 0;
					composite2Layout.verticalSpacing = 0;
					composite2.setLayout(composite2Layout);
					GridData composite2LayoutData = new GridData();
					composite2LayoutData.horizontalAlignment = GridData.FILL;
					composite2LayoutData.grabExcessHorizontalSpace = true;
					composite2LayoutData.verticalAlignment = GridData.FILL;
					composite2LayoutData.grabExcessVerticalSpace = true;
					composite2.setLayoutData(composite2LayoutData);
					{
						GridData customerSearchPane1LData = new GridData();
						customerSearchPane1LData.horizontalAlignment = GridData.FILL;
						customerSearchPane1LData.grabExcessHorizontalSpace = true;
						customerSearchPane1 = new CustomerSearchPane(composite2, SWT.NONE);
						customerSearchPane1.setLayoutData(customerSearchPane1LData);
					}
					{
						GridData lblSeparator1LData = new GridData();
						lblSeparator1LData.horizontalAlignment = GridData.FILL;
						lblSeparator1LData.grabExcessHorizontalSpace = true;
						lblSeparator1 = new Label(composite2, SWT.SEPARATOR | SWT.HORIZONTAL);
						lblSeparator1.setLayoutData(lblSeparator1LData);
					}
					{
						GridData customerPane1LData = new GridData();
						customerPane1LData.horizontalAlignment = GridData.FILL;
						customerPane1LData.grabExcessHorizontalSpace = true;
						customerPane1 = new CustomerSimplePane(composite2, SWT.NONE);
						customerPane1.setLayoutData(customerPane1LData);
					}
					{
						GridData lblSeparator2LData = new GridData();
						lblSeparator2LData.horizontalAlignment = GridData.FILL;
						lblSeparator2LData.grabExcessHorizontalSpace = true;
						Label lblSeparator2 = new Label(composite2, SWT.SEPARATOR | SWT.HORIZONTAL);
						lblSeparator2.setLayoutData(lblSeparator2LData);
					}
					{
						// $hide>>$
						GridData recalculationHistoryPane1LData = new GridData();
						recalculationHistoryPane1LData.horizontalAlignment = GridData.FILL;
						recalculationHistoryPane1LData.grabExcessHorizontalSpace = true;
						recalculationHistoryPane1LData.verticalAlignment = GridData.FILL;
						recalculationHistoryPane1LData.grabExcessVerticalSpace = true;
						recalcHistoryPane1 = new RecalcHistoryPane(composite2, SWT.NONE);
						recalcHistoryPane1.setLayoutData(recalculationHistoryPane1LData);
						// $hide<<$
					}
				}
			}
			{
				composite3 = new Composite(sashForm1, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.horizontalSpacing = 0;
				composite3Layout.marginHeight = 0;
				composite3Layout.marginWidth = 0;
				composite3Layout.verticalSpacing = 0;
				composite3.setLayout(composite3Layout);
			}
		}
		// $hide>>$
		customInit();
		// $hide<<$
	}

	private void customInit() {
		sashForm1.setWeights(new int[] { 1, 2 });
		setPartName(GUIMessages.getMessage("application.action.recalc"));
		customerSearchPane1.setRelatedCustomerPane(customerPane1);
		customerSearchPane1.setRecalculationHistoryPane(recalcHistoryPane1);
		recalcHistoryPane1.setRelatedRecalculationView(this);
		recalcHistoryPane1.getViewer().getTable().addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent arg0) {
				Recalc recalc = recalcHistoryPane1.getSelectedRecalc();
				if (recalc != null && recalc.isChanged()) {
					onEdit();
				} else {
					onView();
				}
			}
		});

		createMenu();
		validateView();
	}

	private void createMenu() {
		menuChangeView = new Menu(getSite().getShell(), SWT.POP_UP);

		MenuItem unblock = new MenuItem(menuChangeView, SWT.POP_UP);
		unblock.setText(GUIMessages.getMessage("comp.recalc_view.unblock_recalc"));
		unblock.setImage(Plugin.getImage("icons/16x16/lock.png"));
		unblock.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				onUnblock();
			}
		});

		MenuItem finalize = new MenuItem(menuChangeView, SWT.POP_UP);
		finalize.setText(GUIMessages.getMessage("comp.recalc_view.finalize_recalc"));
		finalize.setImage(Plugin.getImage("icons/16x16/true.png"));
		finalize.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				onChangeStatus(true);
			}
		});

		MenuItem cancel = new MenuItem(menuChangeView, SWT.POP_UP);
		cancel.setText(GUIMessages.getMessage("comp.recalc_view.cancel_recalc"));
		cancel.setImage(Plugin.getImage("icons/16x16/stop.png"));
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				onChangeStatus(false);
			}
		});

	}

	public void validateView() {

		//
		// additional pane view
		//

		if (additionalPaneVisible) {
			sashForm1.setMaximizedControl(null);
			// tiChangeView.setToolTipText(GUIMessages.getMessage("comp.general.view_without_additional_pane"));
			// tiChangeView.setImage(Plugin.getImage("icons/22x22/show_without_side_panel.png"));
		} else {
			sashForm1.setMaximizedControl(composite3);
			// tiChangeView.setToolTipText(GUIMessages.getMessage("comp.general.view_with_additional_pane"));
			// tiChangeView.setImage(Plugin.getImage("icons/22x22/show_side_panel.png"));
		}

		//
		// enable/disable action
		//

		Recalc recalc = recalcHistoryPane1.getSelectedRecalc();

		boolean customerExist = customerPane1.getCustomer() != null;
		boolean recalculationSelected = customerExist && recalc != null;
		tiNewRecalc.setEnabled(mode == MODE_NONE && customerExist);
		tiCopyRecalc.setEnabled(mode == MODE_NONE && recalculationSelected);
		tiRecalcProperties.setEnabled(/*
									 * mode == MODE_NONE &&
									 */recalculationSelected);
		tiDeleteRecalc.setEnabled(mode == MODE_NONE && recalculationSelected);
		tiEdit.setEnabled(mode != MODE_EDIT && recalculationSelected);
		tiView.setEnabled(mode != MODE_VIEW && recalculationSelected);
		tiStop.setEnabled(mode != MODE_NONE);
		tiRecalculate.setEnabled(mode == MODE_EDIT);
		tiBuild.setEnabled(mode == MODE_EDIT);
		tiPrint.setEnabled((recalculationSelected && mode == MODE_NONE) || (mode == MODE_VIEW));
		tiSave.setEnabled(mode == MODE_EDIT);
		tiRecalcUnblock.setEnabled((recalculationSelected && mode == MODE_NONE) && recalculationSelected && !recalc.isChanged());

		//
		// enable/disable search
		//

		customerSearchPane1.setSearchEnabled(mode == MODE_NONE);

		//
		// show/hide editing pane
		//

		// recalcEditingPane1.setVisible(mode);
	}

	//
	// Actions
	//

	private void onNew() {
		Customer customer = customerPane1.getCustomer();
		if (customer == null) {
			return;
		}
		NewRecalcDialog prop = new NewRecalcDialog(getSite().getShell(), SWT.NONE);
		prop.setCustomer(customer);
		prop.open();
		if (prop.isApproved()) {
			recalcHistoryPane1.addRecalculation(prop.getRecalc());
		}
		validateView();
	}

	private void onCopy() {
		Recalc recalc = recalcHistoryPane1.getSelectedRecalc();
		if (recalc == null)
			return;
		String title = GUIMessages.getMessage("comp.recalc_view.copy_recalc");
		String msg = GUIMessages.getMessage("comp.recalc_view.copy_recalc.confirm", new String[] { recalc.getNumber() });
		boolean resp = MessageDialog.openQuestion(getSite().getShell(), title, msg);
		if (!resp)
			return;
		if (!Application.validateConnection())
			return;
		try {
			RecalcCopyRequest request = new RecalcCopyRequest(Application.USER_NAME, Application.PASSWORD);
			request.setRecalculation(recalc);
			request.setUser(Application.USER);
			DefaultEJBResponse response = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			RecalcCopyRequest callback = (RecalcCopyRequest) response.getRequest();
			Recalc newRecalc = callback.getRecalculation();
			newRecalc.setId(callback.getId());
			recalcHistoryPane1.addRecalculation(newRecalc);
		} catch (Throwable t) {
			MessageDialog.openError(getSite().getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
		}
		validateView();
	}

	@SuppressWarnings("unchecked")
	private void onDelete() {
		List recalcs = recalcHistoryPane1.getSelectedRecalcs();
		if (recalcs == null || recalcs.isEmpty()) {
			return;
		}
		String title = GUIMessages.getMessage("comp.general.confirm");
		String msg = GUIMessages.getMessage("comp.general.confirm_delete_object_count", new Object[] { new Integer(recalcs.size()) });
		boolean resp = MessageDialog.openQuestion(getSite().getShell(), title, msg);
		if (!resp) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			List requests = new ArrayList();
			for (int i = 0; i < recalcs.size(); i++) {
				Recalc recalc = (Recalc) recalcs.get(i);
				RecalcDeleteRequest request = new RecalcDeleteRequest(Application.USER_NAME, Application.PASSWORD);
				request.setRecalculation(recalc);
				requests.add(request);
			}
			DefaultRecutilClient.processRequests(requests);
			recalcHistoryPane1.removeRecalculations(recalcs);
		} catch (Throwable t) {
			MessageDialog.openError(getSite().getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
		}
		validateView();
	}

	private void onProperties() {
		Recalc recalc = recalcHistoryPane1.getSelectedRecalc();
		if (recalc == null) {
			return;
		}
		RecalcProperties prop = new RecalcProperties(getSite().getShell(), SWT.NONE);
		prop.setRecalc(recalc);
		prop.open();
		if (prop.isApproved()) {
			recalc.setNumber(prop.getRecalc().getNumber());
			recalcHistoryPane1.refreshView();
			recalcHistoryPane1.selectRecalcById(prop.getRecalc().getId());
		}
		validateView();
	}

	private void onEdit() {
		// initial validation
		if (mode == MODE_EDIT) {
			return;
		}
		Recalc recalc = recalcHistoryPane1.getSelectedRecalc();
		if (recalc == null) {
			return;
		}

		// change modes
		mode = MODE_EDIT;
		additionalPaneVisible = false;

		// close if previous exists
		clearContentPane();

		// create editing pane
		GridData layoutData = new GridData();
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.verticalAlignment = GridData.FILL;
		layoutData.grabExcessVerticalSpace = true;
		recalcEditingPane = new RecalcEditingPane(composite3, SWT.NONE);
		recalcEditingPane.setLayoutData(layoutData);

		// set recalculation
		recalcEditingPane.setRecalc(recalc);
		composite3.layout();

		// validate view
		validateView();

		// refresh pane
		recalcEditingPane.refresh();
	}

	private void onView() {
		// initial validation
		if (mode == MODE_VIEW)
			return;
		Recalc recalc = recalcHistoryPane1.getSelectedRecalc();
		if (recalc == null)
			return;

		// change modes
		mode = MODE_VIEW;
		additionalPaneVisible = false;

		// close if previous exists
		clearContentPane();

		// create editing pane
		GridData layoutData = new GridData();
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.verticalAlignment = GridData.FILL;
		layoutData.grabExcessVerticalSpace = true;
		recalcViewPane = new RecalcViewPane(composite3, SWT.NONE);
		recalcViewPane.setLayoutData(layoutData);

		composite3.layout();

		// set recalculation
		recalcViewPane.setRecalc(recalc);

		// validate view
		validateView();

		// refresh pane
		recalcViewPane.refresh();

	}

	private void onStop() {
		// initial validation
		if (mode == MODE_NONE) {
			return;
		}

		// change modes
		mode = MODE_NONE;
		additionalPaneVisible = true;

		// clear content
		clearContentPane();

		// validate view
		validateView();
	}

	private void clearContentPane() {
		if (recalcEditingPane != null && !recalcEditingPane.isDisposed()) {
			recalcEditingPane.dispose();
		}
		recalcEditingPane = null;
		if (recalcViewPane != null && !recalcViewPane.isDisposed()) {
			recalcViewPane.dispose();
		}
		recalcViewPane = null;
	}

	private void onRebuild() {
		recalcEditingPane.rebuild();
	}

	private void onRecalculate() {
		recalcEditingPane.calculate();
	}

	private void onSave() {
		// check editing pane
		if (recalcEditingPane == null || recalcEditingPane.isDisposed())
			return;

		// check recalculation
		Recalc recalc = recalcEditingPane.getRecalc();
		if (recalc == null)
			return;

		// get summary
		List summary = recalcEditingPane.getSummary();
		List facturaExpansion = recalcEditingPane.getFacturaExpansion();
		List trashList = recalcEditingPane.getTrashCorrections();
		if (summary == null || summary.isEmpty()) {
			MessageDialog.openWarning(getSite().getShell(), GUIMessages.getMessage("comp.general.warning"), GUIMessages.getMessage("comp.recalc_view.nothing_to_save"));
			return;
		}

		// get advisor, save user and description
		User advisor = recalcEditingPane.getAdvisor();
		User saveUser = Application.USER;
		String description = recalcEditingPane.getSaveDescription();

		// create new recalculation
		Recalc newRecalc = new Recalc();
		newRecalc.setId(recalc.getId());
		newRecalc.setDescription(description);
		newRecalc.setSaveUser(saveUser);
		newRecalc.setAdvisor(advisor);

		// create request
		RecalcSaveRequest3 request = new RecalcSaveRequest3(Application.USER_NAME, Application.PASSWORD);
		request.setRecalc(newRecalc);
		request.setSummary(summary);
		request.setTrashList(trashList);
		request.setFacturaExpansion(facturaExpansion);

		// confirm save
		String msg = GUIMessages.getMessage("comp.recalc_view.confirm_save");
		String title = GUIMessages.getMessage("comp.general.confirm");
		boolean resp = MessageDialog.openConfirm(getSite().getShell(), title, msg);
		if (!resp) {
			return;
		}

		// validate connection
		if (!Application.validateConnection()) {
			return;
		}

		try {
			DefaultRecutilClient.processRequest(request);
		} catch (Throwable t) {
			MessageDialog.openError(getSite().getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
			return;
		}

		// refresh vouchers list
		recalcHistoryPane1.refresh();
		recalcHistoryPane1.selectRecalcById(recalc.getId());

		// open in VIEW mode
		onView();
	}

	private void onUnblock() {
		Recalc recalc = recalcHistoryPane1.getSelectedRecalc();
		if (recalc == null || recalc.isChanged() || mode != MODE_NONE) {
			return;
		}
		String msg1 = GUIMessages.getMessage("comp.recalc_view.unblock_warning");
		String title1 = GUIMessages.getMessage("comp.general.warning");
		String msg2 = GUIMessages.getMessage("comp.recalc_view.unblock_confirm");
		String title2 = GUIMessages.getMessage("comp.general.confirm");
		MessageDialog.openWarning(getSite().getShell(), title1, msg1);
		boolean resp = MessageDialog.openQuestion(getSite().getShell(), title2, msg2);
		if (!resp) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}

		RecalcUnblockRequest request = new RecalcUnblockRequest(Application.USER_NAME, Application.PASSWORD);
		request.setRecalcId(recalc.getId());

		try {
			DefaultRecutilClient.processRequest(request);
			recalcHistoryPane1.refresh();
			recalcHistoryPane1.selectRecalcById(recalc.getId());
		} catch (Throwable t) {
			MessageDialog.openError(getSite().getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
		}

	}

	private void onChangeStatus(boolean finalize) {
		Recalc recalc = recalcHistoryPane1.getSelectedRecalc();
		if (recalc == null || recalc.isChanged() || mode != MODE_NONE)
			return;
		String msg = finalize ? GUIMessages.getMessage("comp.recalc_view.finalize_confirm") : GUIMessages.getMessage("comp.recalc_view.cancel_confirm");
		boolean resp = MessageDialog.openQuestion(getSite().getShell(), GUIMessages.getMessage("comp.general.confirm"), msg);
		if (!resp)
			return;
		if (!Application.validateConnection())
			return;

		RecalcFinalizeRequest request = new RecalcFinalizeRequest(Application.USER_NAME, Application.PASSWORD);
		request.setRecalcId(recalc.getId());
		request.setFinalize(finalize);

		try {
			DefaultRecutilClient.processRequest(request);
			recalcHistoryPane1.refresh();
			recalcHistoryPane1.selectRecalcById(recalc.getId());
		} catch (Throwable t) {
			MessageDialog.openError(getSite().getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
		}
	}

	private void onPrint() {
		if (recalcViewPane != null && !recalcViewPane.isDisposed()) {
			recalcViewPane.print();
		} else {
			Recalc recalc = recalcHistoryPane1.getSelectedRecalc();
			if (recalc == null)
				return;
			if (!Application.validateConnection())
				return;
			RecalcVoucherSelectRequest3 request = new RecalcVoucherSelectRequest3(Application.USER_NAME, Application.PASSWORD);
			request.setRecalc(recalc);
			try {
				DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
				RecalcVoucherSelectRequest3 callback = (RecalcVoucherSelectRequest3) resp.getRequest();
				RecalcVoucher voucher = callback.getVoucher();
				List trashVouchers = callback.getTrashVouchers();
				RecalcReportsManager.openVoucherReport(voucher, trashVouchers, getSite().getShell());
			} catch (Throwable t) {
				MessageDialog.openError(getSite().getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
			}
		}
	}

	// other

	public void setFocus() {
	}

	public void dispose() {
		super.dispose();
	}

	public void openRecalc(Recalc recalc) {
		onStop();
		customerSearchPane1.openCustomer(recalc.getCustomer().getNumber());
		recalcHistoryPane1.selectRecalcById(recalc.getId());
		validateView();
		// if (recalc.isChanged())
		// onEdit();
		// else
		// onView();
	}

}
