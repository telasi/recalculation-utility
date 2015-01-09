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


import java.lang.reflect.Constructor;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.part.ViewPart;

import telasi.recutil.gui.comp.cust.CustomerSearchPane;
import telasi.recutil.gui.comp.cust.CustomerSimplePane;
import telasi.recutil.gui.comp.cust.histories.HistorySelector;
import telasi.recutil.gui.comp.cust.histories.ICustomerHistory;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;

public class CustomerHistoryView extends ViewPart {

	public static final String ID_VIEW = "ge.telasi.recut.guiapp.views.CustomerHistoryView"; //$NON-NLS-1$
	private Composite composite1;
	private Composite composite2;
	private HistorySelector historySelector;
	private ToolItem tiChangeView;
	private ToolItem toolItem1;
	private Label lblSeparator2;
	private Label lblSeparator1;
	private ToolItem tiPrint;
	private ToolBar toolbar;
	private CustomerSearchPane customerSearchPane1;
	private CustomerSimplePane customerPane1;
	private Composite composite4;
	private Composite composite3;
	private SashForm sashForm1;

	public CustomerHistoryView() {
		super();
	}

	public void createPartControl(Composite parent) {
		parent.setSize(500, 300);
		composite1 = new Composite(parent, SWT.NULL);
		GridLayout composite1Layout = new GridLayout();
		composite1Layout.horizontalSpacing = 0;
		composite1Layout.marginHeight = 0;
		composite1Layout.marginWidth = 0;
		composite1Layout.verticalSpacing = 0;
		composite1.setLayout(composite1Layout);
		{
			GridData toolbarLData = new GridData();
			toolbarLData.horizontalAlignment = GridData.FILL;
			toolbar = new ToolBar(composite1, SWT.NONE);
			toolbar.setLayoutData(toolbarLData);
			{
				tiChangeView = new ToolItem(toolbar, SWT.NONE);
				tiChangeView.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						additionalPaneVisible = !additionalPaneVisible;
						validateView();
					}
				});
			}
			{
				toolItem1 = new ToolItem(toolbar, SWT.SEPARATOR);
				toolItem1.setText("");
			}
			{
				tiPrint = new ToolItem(toolbar, SWT.NONE);
				tiPrint.setToolTipText(GUIMessages
						.getMessage("comp.general.print"));
				tiPrint.setImage(Plugin.getImage("icons/22x22/print.png"));
			}
		}
		{
			composite2 = new Composite(composite1, SWT.NONE);
			GridLayout composite2Layout = new GridLayout();
			composite2Layout.verticalSpacing = 0;
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
				sashForm1 = new SashForm(composite2, SWT.NONE);
				GridData sashForm1LData = new GridData();
				sashForm1LData.verticalAlignment = GridData.FILL;
				sashForm1LData.grabExcessVerticalSpace = true;
				sashForm1LData.horizontalAlignment = GridData.FILL;
				sashForm1LData.grabExcessHorizontalSpace = true;
				sashForm1.setLayoutData(sashForm1LData);
				{
					composite3 = new Composite(sashForm1, SWT.BORDER);
					GridLayout composite3Layout = new GridLayout();
					composite3Layout.horizontalSpacing = 0;
					composite3Layout.marginHeight = 0;
					composite3Layout.marginWidth = 0;
					composite3Layout.verticalSpacing = 0;
					composite3.setLayout(composite3Layout);
					composite3.setBounds(29, 57, 409, 238);
					{
						GridData customerSearchPane1LData = new GridData();
						customerSearchPane1LData.horizontalAlignment = GridData.FILL;
						customerSearchPane1LData.grabExcessHorizontalSpace = true;
						customerSearchPane1 = new CustomerSearchPane(
								composite3, SWT.NONE);
						customerSearchPane1
								.setLayoutData(customerSearchPane1LData);
					}
					{
						GridData lblSeparator1LData = new GridData();
						lblSeparator1LData.horizontalAlignment = GridData.FILL;
						lblSeparator1LData.grabExcessHorizontalSpace = true;
						lblSeparator1 = new Label(composite3, SWT.SEPARATOR
								| SWT.HORIZONTAL);
						lblSeparator1.setLayoutData(lblSeparator1LData);
					}
					{
						GridData customerPane1LData = new GridData();
						customerPane1LData.horizontalAlignment = GridData.FILL;
						customerPane1LData.grabExcessHorizontalSpace = true;
						customerPane1LData.verticalAlignment = GridData.BEGINNING;
						customerPane1 = new CustomerSimplePane(composite3,
								SWT.NONE);
						customerPane1.setLayoutData(customerPane1LData);
					}
					{
						GridData lblSeparator2LData = new GridData();
						lblSeparator2LData.horizontalAlignment = GridData.FILL;
						lblSeparator2LData.grabExcessHorizontalSpace = true;
						lblSeparator2 = new Label(composite3, SWT.SEPARATOR
								| SWT.HORIZONTAL);
						lblSeparator2.setLayoutData(lblSeparator2LData);
					}
					{
						GridData data1 = new GridData();
						data1.horizontalAlignment = GridData.FILL;
						data1.grabExcessHorizontalSpace = true;
						data1.verticalAlignment = GridData.FILL;
						data1.grabExcessVerticalSpace = true;
						historySelector = new HistorySelector(composite3,
								SWT.NONE);
						historySelector.setLayoutData(data1);
					}
				}
				{
					composite4 = new Composite(sashForm1, SWT.NONE);
					GridLayout composite4Layout = new GridLayout();
					composite4Layout.horizontalSpacing = 0;
					composite4Layout.marginHeight = 0;
					composite4Layout.marginWidth = 0;
					composite4Layout.verticalSpacing = 0;
					composite4.setLayout(composite4Layout);
					composite4.setBounds(51, 64, 203, 238);
				}
			}
		}
		// $protect>>$
		customInit();
		// $protect<<$
	}

	private void customInit() {
		sashForm1.setWeights(new int[] { 1, 2 });
		setPartName(GUIMessages
				.getMessage("application.action.customer_history"));
		customerSearchPane1.setRelatedCustomerPane(customerPane1);

		historySelector.setRelatedCustomerHistoryView(this);
		additionalPaneVisible = true;
		validateView();
	}

	private boolean additionalPaneVisible;

	private void validateView() {
		if (additionalPaneVisible) {
			sashForm1.setMaximizedControl(null);
			tiChangeView.setImage(Plugin
					.getImage("icons/22x22/show_without_side_panel.png"));
			tiChangeView.setToolTipText(GUIMessages
					.getMessage("comp.general.view_without_additional_pane"));
		} else {
			sashForm1.setMaximizedControl(composite4);
			tiChangeView.setImage(Plugin
					.getImage("icons/22x22/show_side_panel.png"));
			tiChangeView.setToolTipText(GUIMessages
					.getMessage("comp.general.view_with_additional_pane"));
		}
	}

	public void setFocus() {
	}

	private Composite currentPane;

	@SuppressWarnings("unchecked")
	public void onOpen() {

		// get history descriptor
		HistorySelector.HistoryDescriptor hd = historySelector
				.getHistoryDescriptor();
		if (hd == null) {
			return;
		}

		// close previous pane
		if (currentPane != null) {
			currentPane.dispose();
			currentPane = null;
			customerSearchPane1.setRelatedCustomerHistory(null);
		}

		try {

			String className = hd.className;
			Class cl = Class.forName(className);
			Constructor constructor = cl.getConstructor(new Class[] { Composite.class, int.class });
			Object newPane = constructor.newInstance(new Object[] { composite4,
					new Integer(SWT.NONE) });

			currentPane = (Composite) newPane;

			GridData layoutData = new GridData();
			layoutData.horizontalAlignment = GridData.FILL;
			layoutData.grabExcessHorizontalSpace = true;
			layoutData.verticalAlignment = GridData.FILL;
			layoutData.grabExcessVerticalSpace = true;
			currentPane.setLayoutData(layoutData);

			ICustomerHistory history = (ICustomerHistory) newPane;
			history.setCustomer(customerSearchPane1.getCustomer());
			history.refresh();
			customerSearchPane1.setRelatedCustomerHistory(history);

		} catch (Throwable t) {

			MessageDialog.openError(getSite().getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());

		}

		composite4.layout();

	}

	/**
	 * Cleans up all resources created by this ViewPart.
	 */
	public void dispose() {
		super.dispose();
	}

}
