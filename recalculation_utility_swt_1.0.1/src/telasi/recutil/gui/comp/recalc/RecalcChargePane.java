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
package telasi.recutil.gui.comp.recalc;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.comp.recalc.charge_pane_views.ChargePaneView;
import telasi.recutil.gui.comp.recalc.charge_pane_views.ElEnergyChargePane;
import telasi.recutil.gui.comp.recalc.charge_pane_views.NormalChargePane;
import telasi.recutil.gui.comp.recalc.charge_pane_views.SubsidyChargePane;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.service.eclipse.recalc.RecalcIntervalDeleteRequest;

public class RecalcChargePane extends Composite {

	private Composite composite1;
	private Composite contentPane;
	private ToolItem toolItem3;
	private ToolItem toolItem4;
	private ToolItem tiMaximizeContent;
	private ChargePaneView chargePane;
	private ToolItem tiRefresh;
	private ToolItem toolItem2;
	private ToolItem tiIntervalProperties;
	private ToolItem tiDeleteInterval;
	// private ToolItem tiNewInterval;
	private ToolItem toolItem1;
	private ToolItem tiChangeView;
	private ToolBar toolBar1;
	private RecalcIntervalPane recalcIntervalPane;
	private SashForm sashForm1;
	private Composite composite3;
	private int viewOption;
	private Menu menuChangeView;
	private Recalc recalc;
	private boolean isMaximizedView = false;

	//
	// Possible view options
	//

	public static final int VIEW_COMMON = 0;

	public static final int VIEW_ELENERGY_CHARGE = 1;

	public static final int VIEW_SUBSIDY = 2;

	public RecalcChargePane(Composite parent, int style) {
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
			this.setLayout(thisLayout);
			this.setSize(466, 308);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.horizontalSpacing = 0;
				composite1Layout.marginHeight = 0;
				composite1Layout.marginWidth = 0;
				composite1Layout.verticalSpacing = 0;
				GridData composite1LData = new GridData();
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData toolBar1LData = new GridData();
					toolBar1LData.horizontalAlignment = GridData.FILL;
					toolBar1LData.grabExcessHorizontalSpace = true;
					toolBar1 = new ToolBar(composite1, SWT.NONE);
					toolBar1.setLayoutData(toolBar1LData);
					{
						tiMaximizeContent = new ToolItem(toolBar1, SWT.NONE);
						tiMaximizeContent
								.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent e) {
										isMaximizedView = !isMaximizedView;
										validateMaximizeView();
									}
								});
					}
					{
						toolItem4 = new ToolItem(toolBar1, SWT.SEPARATOR);
						toolItem4.setText("");
					}
					{
						tiChangeView = new ToolItem(toolBar1, SWT.DROP_DOWN);
						tiChangeView.setToolTipText(GUIMessages.getMessage("comp.recalc_charge_pane.change_view"));
						tiChangeView.addListener(SWT.Selection, new Listener() {
							public void handleEvent(Event event) {
								if (event.detail == SWT.ARROW || event.detail == 0) {
									Rectangle rect = tiChangeView.getBounds();
									Point pt = new Point(rect.x, rect.y + rect.height);
									pt = toolBar1.toDisplay(pt);
									menuChangeView.setLocation(pt.x, pt.y);
									menuChangeView.setVisible(true);
								}
							}
						});
					}
					{
						toolItem1 = new ToolItem(toolBar1, SWT.SEPARATOR);
						toolItem1.setText("");
					}
					{
						tiRefresh = new ToolItem(toolBar1, SWT.NONE);
						tiRefresh.setImage(Plugin
								.getImage("icons/22x22/refresh.png"));
						tiRefresh.setToolTipText(GUIMessages
								.getMessage("comp.general.refresh"));
						tiRefresh.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent e) {
								onRefresh();
							}
						});
					}
					{
						toolItem3 = new ToolItem(toolBar1, SWT.SEPARATOR);
						toolItem3.setText("");
					}
					/*
					 * { tiNewInterval = new ToolItem(toolBar1, SWT.NONE);
					 * tiNewInterval.setToolTipText(GUIMessages.getMessage("comp.recalc_charge_pane.new_interval"));
					 * tiNewInterval.setImage(Plugin.getImage("icons/22x22/new.gif"));
					 * tiNewInterval.addSelectionListener(new SelectionAdapter() {
					 * public void widgetSelected(SelectionEvent e) {
					 * onNewInterval(); } }); }
					 */
					{
						tiDeleteInterval = new ToolItem(toolBar1, SWT.NONE);
						tiDeleteInterval
								.setToolTipText(GUIMessages
										.getMessage("comp.recalc_charge_pane.delete_interval"));
						tiDeleteInterval.setImage(Plugin
								.getImage("icons/22x22/trash.png"));
						tiDeleteInterval
								.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent e) {
										onDeleteInterval();
									}
								});
					}
					{
						tiIntervalProperties = new ToolItem(toolBar1, SWT.NONE);
						tiIntervalProperties
								.setToolTipText(GUIMessages
										.getMessage("comp.recalc_charge_pane.interval_properties"));
						tiIntervalProperties.setImage(Plugin
								.getImage("icons/22x22/properties.gif"));
					}
					{
						toolItem2 = new ToolItem(toolBar1, SWT.SEPARATOR);
						toolItem2.setText("");
					}
				}
				{
					sashForm1 = new SashForm(composite1, SWT.NONE);
					GridData sashForm1LData = new GridData();
					sashForm1LData.verticalAlignment = GridData.FILL;
					sashForm1LData.grabExcessVerticalSpace = true;
					sashForm1LData.horizontalAlignment = GridData.FILL;
					sashForm1LData.grabExcessHorizontalSpace = true;
					sashForm1.setLayoutData(sashForm1LData);
					sashForm1.setSize(60, 30);
					{
						composite3 = new Composite(sashForm1, SWT.NONE);
						GridLayout composite3Layout = new GridLayout();
						composite3Layout.numColumns = 2;
						composite3Layout.horizontalSpacing = 0;
						composite3Layout.marginWidth = 0;
						composite3Layout.marginHeight = 0;
						composite3Layout.verticalSpacing = 0;
						composite3.setLayout(composite3Layout);
						composite3.setBounds(0, 0, 200, 308);
						{
							// $hide>>$
							GridData recalcIntervalPaneLData = new GridData();
							recalcIntervalPaneLData.horizontalAlignment = GridData.FILL;
							recalcIntervalPaneLData.grabExcessHorizontalSpace = true;
							recalcIntervalPaneLData.verticalAlignment = GridData.FILL;
							recalcIntervalPaneLData.grabExcessVerticalSpace = true;
							recalcIntervalPane = new RecalcIntervalPane(
									composite3, SWT.NONE);
							recalcIntervalPane
									.setLayoutData(recalcIntervalPaneLData);
							recalcIntervalPane.setRelatedRecalcChargePane(this);
							// $hide<<$
						}

					}
					{
						contentPane = new Composite(sashForm1, SWT.NONE);
						GridLayout composite2Layout = new GridLayout();
						composite2Layout.horizontalSpacing = 0;
						composite2Layout.marginHeight = 0;
						composite2Layout.marginWidth = 0;
						composite2Layout.verticalSpacing = 0;
						contentPane.setLayout(composite2Layout);
					}
				}
			}
			this.layout();
			// $protect>>$
			createMenu();
			changeView(VIEW_COMMON);
			sashForm1.setWeights(new int[] { 1, 3 });
			validateMaximizeView();
			// $protect<<$
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void changeView(int viewOption) {
		this.viewOption = viewOption;

		long[] ids = null;

		// dispose previous pane
		if (chargePane != null) {
			if (!chargePane.isDisposed()) {
				ids = chargePane.getSelectedIds();
				chargePane.dispose();
			}
			chargePane = null;
		}

		switch (this.viewOption) {
		case VIEW_COMMON:
			tiChangeView.setImage(Plugin
					.getImage("icons/22x22/bop/reading.png"));
			chargePane = new NormalChargePane(contentPane, SWT.NONE);
			break;
		case VIEW_ELENERGY_CHARGE:
			tiChangeView.setImage(Plugin.getImage("icons/22x22/bop/power.png"));
			chargePane = new ElEnergyChargePane(contentPane, SWT.NONE);
			break;
		case VIEW_SUBSIDY:
			tiChangeView.setImage(Plugin
					.getImage("icons/22x22/bop/subsidy.png"));
			chargePane = new SubsidyChargePane(contentPane, SWT.NONE);
			break;
		default:
			changeView(VIEW_COMMON);
		}

		// set layout
		if (chargePane != null) {
			GridData data1 = new GridData();
			data1.horizontalAlignment = GridData.FILL;
			data1.grabExcessHorizontalSpace = true;
			data1.verticalAlignment = GridData.FILL;
			data1.grabExcessVerticalSpace = true;
			chargePane.setLayoutData(data1);
			chargePane.setParentPane(this);

		}
		contentPane.layout(true);
		validateView();
		if (chargePane != null) {
			chargePane.selectById(ids);
		}
	}

	private void createMenu() {

		// create menu
		menuChangeView = new Menu(getShell(), SWT.POP_UP);

		// common
		MenuItem commonView = new MenuItem(menuChangeView, SWT.POP_UP);
		commonView.setText(GUIMessages.getMessage("comp.recalc_charge_pane.view.common"));
		commonView.setImage(Plugin.getImage("icons/16x16/bop/reading.png"));
		commonView.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeView(VIEW_COMMON);
			}
		});

		// estimate
		MenuItem estimateView = new MenuItem(menuChangeView, SWT.POP_UP);
		estimateView.setText(GUIMessages
				.getMessage("comp.recalc_charge_pane.view.estimate"));
		estimateView.setImage(Plugin.getImage("icons/16x16/bop/power.png"));
		estimateView.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeView(VIEW_ELENERGY_CHARGE);
			}
		});

		// subsdiy
		MenuItem subsidyView = new MenuItem(menuChangeView, SWT.POP_UP);
		subsidyView.setText(GUIMessages
				.getMessage("comp.recalc_charge_pane.view.subsidy"));
		subsidyView.setImage(Plugin.getImage("icons/16x16/bop/subsidy.png"));
		subsidyView.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				changeView(VIEW_SUBSIDY);
			}
		});
	}

	private void onRefresh() {
		RecalcInterval interval = recalcIntervalPane.getSelectedInterval();
		recalcIntervalPane.refresh();
		if (interval != null) {
			recalcIntervalPane.selectIntervalById(interval.getId());
		}
		validateView();
	}

	/*
	 * private void onNewInterval() { if (recalc == null) { return; }
	 * NewRecalcIntervalDialog dialog = new NewRecalcIntervalDialog(getShell(),
	 * SWT.NONE); dialog.setRecalc(recalc); dialog.open();
	 * 
	 * if (!dialog.isApproved()) { return; }
	 *  // refresh and select by ID refresh(); long intervalId =
	 * dialog.getNewIntervalId();
	 * recalcIntervalPane.selectIntervalById(intervalId); validateView(); }
	 */

	private void onDeleteInterval() {

		if (recalc == null) {
			return;
		}

		RecalcInterval interval = recalcIntervalPane.getSelectedInterval();
		if (interval == null || !interval.isEditable()) {
			return;
		}

		boolean resp = MessageDialog.openQuestion(getShell(), GUIMessages
				.getMessage("comp.general.confirm"), GUIMessages.getMessage(
				"comp.recalc_charge_pane.confirm_interval_delete",
				new Object[] { interval.getName() }));
		if (!resp) {
			return;
		}

		if (!Application.validateConnection()) {
			return;
		}

		try {

			RecalcIntervalDeleteRequest request = new RecalcIntervalDeleteRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRecalcInterval(interval);
			DefaultRecutilClient.processRequest(request);
			recalcIntervalPane.refresh();

		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}

	}

	/*
	 * This operation is called on open.
	 */
	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
		recalcIntervalPane.setRecalc(recalc);

		changeView(VIEW_COMMON);
		validateView();

	}

	public void refresh() {
		// recalcIntervalPane.refresh();
		onRefresh();
	}

	public void clear() {
		this.recalc = null;
		recalcIntervalPane.clear();
		validateView();
		if (chargePane != null) {
			chargePane.clear();
		}
	}

	private void validateMaximizeView() {
		if (isMaximizedView) {
			sashForm1.setMaximizedControl(contentPane);
			tiMaximizeContent.setImage(Plugin
					.getImage("icons/22x22/show_side_panel.png"));
			tiMaximizeContent.setToolTipText(GUIMessages
					.getMessage("comp.recalc_charge_pane.show_intervals"));
		} else {
			sashForm1.setMaximizedControl(null);
			tiMaximizeContent.setImage(Plugin
					.getImage("icons/22x22/show_without_side_panel.png"));
			tiMaximizeContent.setToolTipText(GUIMessages
					.getMessage("comp.recalc_charge_pane.hide_intervals"));
		}
	}

	public void validateView() {

		RecalcInterval interval = recalcIntervalPane.getSelectedInterval();
		boolean recalcExist = recalc != null;
		boolean intervalSelected = interval != null;

		// tiNewInterval.setEnabled(recalcExist);
		tiRefresh.setEnabled(recalcExist);

		tiDeleteInterval.setEnabled(recalcExist && intervalSelected
				&& interval.isEditable());
		tiIntervalProperties.setEnabled(recalcExist && intervalSelected);

		if (chargePane != null) {
			if (intervalSelected) {
				chargePane.displayItems(interval.getItems());
				chargePane.setInterval(interval);
			} else {
				chargePane.displayItems(null);
			}
		}

	}

	public void selectIntervalById(long id) {
		recalcIntervalPane.selectIntervalById(id);
		validateView();
	}

	public void selectInterval(RecalcInterval interval) {
		if (interval == null) {
			return;
		}
		selectIntervalById(interval.getId());
	}

}
