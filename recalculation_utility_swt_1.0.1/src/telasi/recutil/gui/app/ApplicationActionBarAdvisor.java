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
package telasi.recutil.gui.app;


import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor
{

private IWorkbenchAction actionOptions;
private IWorkbenchAction actionExit;
private IWorkbenchAction actionAbout;
private IWorkbenchAction actionHelpContent;
private UserViewAction userViewAction;
private ConnectAction connectionAction;
private DisconnectAction disconnectAction;
private ClearHistoryAction clearHistoryAction;
private CustomerHistoryAction customerHistoryAction;
private BilloperationViewAction billoperationViewAction;
private RecalculationAction recalculationAction;
private InstCpViewAction instCpViewAction;
private AvearageChargeAction avrgChargeAction;
private ChangePasswordAction changePasswordAction;
private CustomerSearchAction customerSearchAction;
private RecutilWebSiteAction recutilWebSiteAction;
private RecalcExchangeAction recalcExchangeAction;
private RecalcSearchAction recalcSearchAction;
private TpOwnerCalculationAction tpOwnerCalculation;
private TransformatorsAction transformators;
private TpOwnerManagmentAction tpOwnerAction;

public ApplicationActionBarAdvisor(IActionBarConfigurer configurer)
{
	super(configurer);
}

protected void makeActions(IWorkbenchWindow window)
{
	actionOptions = ActionFactory.PREFERENCES.create(window);
	actionOptions.setText(GUIMessages.getMessage("application.action.options"));
	actionOptions.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/configure.png"));

	actionExit = ActionFactory.QUIT.create(window);
	actionExit.setText(GUIMessages.getMessage("application.action.exit"));
	actionExit.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/exit.png"));

	actionAbout = ActionFactory.ABOUT.create(window);
	actionAbout.setText(GUIMessages.getMessage("application.action.about"));
	actionAbout.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/logo.png"));

	actionHelpContent = ActionFactory.HELP_CONTENTS.create(window);
	actionHelpContent.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/help.png"));
	actionHelpContent.setText(GUIMessages.getMessage("application.action.help_content"));

	userViewAction = new UserViewAction(window);
	userViewAction.setText(GUIMessages.getMessage("application.action.users"));
	userViewAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/login.png"));

	connectionAction = new ConnectAction(window);
	connectionAction.setText(GUIMessages.getMessage("application.action.connect"));
	connectionAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/connect.png"));

	disconnectAction = new DisconnectAction(window);
	disconnectAction.setText(GUIMessages.getMessage("application.action.disconnect"));
	disconnectAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/disconnect.png"));

	clearHistoryAction = new ClearHistoryAction(window);
	clearHistoryAction.setText(GUIMessages.getMessage("application.action.clearhistory"));
	clearHistoryAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/clear_history.png"));

	customerHistoryAction = new CustomerHistoryAction(window);
	customerHistoryAction.setText(GUIMessages.getMessage("application.action.customer_history"));
	customerHistoryAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/user.png"));

	billoperationViewAction = new BilloperationViewAction(window);
	billoperationViewAction.setText(GUIMessages.getMessage("application.action.bopview"));
	billoperationViewAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/bop.png"));

	recalculationAction = new RecalculationAction(window);
	recalculationAction.setText(GUIMessages.getMessage("application.action.recalc"));
	recalculationAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/calc.png"));

	instCpViewAction = new InstCpViewAction(window);
	instCpViewAction.setText(GUIMessages.getMessage("application.action.instcp_history"));
	instCpViewAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/power.png"));

	avrgChargeAction = new AvearageChargeAction(window);
	avrgChargeAction.setText(GUIMessages.getMessage("application.action.avrg_day_chagre_calc"));
	avrgChargeAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/bop/charge.png"));

	changePasswordAction = new ChangePasswordAction(window);
	changePasswordAction.setText(GUIMessages.getMessage("application.action.changepassword"));
	changePasswordAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/editfield.png"));

	customerSearchAction = new CustomerSearchAction(window);
	customerSearchAction.setText(GUIMessages.getMessage("application.action.customer_search"));
	customerSearchAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/find.png"));

	recutilWebSiteAction = new RecutilWebSiteAction();
	recutilWebSiteAction.setText(GUIMessages.getMessage("application.action.website"));
	recutilWebSiteAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/web.png"));
	
	recalcExchangeAction = new RecalcExchangeAction(window);
	recalcExchangeAction.setText(GUIMessages.getMessage("application.action.recalcexchange"));
	recalcExchangeAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/server.png"));

	recalcSearchAction = new RecalcSearchAction(window);
	recalcSearchAction.setText(GUIMessages.getMessage("application.action.recalcsearch"));
	recalcSearchAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/find.png"));

	tpOwnerCalculation = new TpOwnerCalculationAction(window);
	tpOwnerCalculation.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/calc.png"));
	tpOwnerCalculation.setText(GUIMessages.getMessage("comp.tpowner.calcview.title"));

	transformators = new TransformatorsAction(window);
	transformators.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/power.png"));
	transformators.setText(GUIMessages.getMessage("comp.tpowner.transformator.title"));

	tpOwnerAction = new TpOwnerManagmentAction(window);
	tpOwnerAction.setImageDescriptor(Plugin.getImageDescriptor("icons/16x16/tpowner.png"));
	tpOwnerAction.setText(GUIMessages.getMessage("comp.tpowner.title"));
}

protected void fillMenuBar(IMenuManager menuBar)
{
	// file menu
	IMenuManager fileMenu = new MenuManager(GUIMessages.getMessage("application.menu.file"));
	fileMenu.add(connectionAction);
	fileMenu.add(disconnectAction);
	fileMenu.add(new Separator());
	fileMenu.add(clearHistoryAction);
	fileMenu.add(new Separator());
	fileMenu.add(userViewAction);
	fileMenu.add(changePasswordAction);
	fileMenu.add(new Separator());
	fileMenu.add(actionOptions);
	fileMenu.add(actionExit);

	// billing menu
	IMenuManager billingMenu = new MenuManager(GUIMessages.getMessage("application.menu.billing"));
	billingMenu.add(customerHistoryAction);
	billingMenu.add(customerSearchAction);
	billingMenu.add(new Separator());
	billingMenu.add(instCpViewAction);
	billingMenu.add(billoperationViewAction);

	// recalculation menu
	IMenuManager recalcMenu = new MenuManager(GUIMessages.getMessage("application.menu.recalc"));
	recalcMenu.add(recalculationAction);
	recalcMenu.add(avrgChargeAction);
	recalcMenu.add(new Separator());
	recalcMenu.add(recalcSearchAction);
	recalcMenu.add(recalcExchangeAction);

	// TP owners menu
	IMenuManager tpOwnerMenu = new MenuManager(GUIMessages.getMessage("comp.tpowner.mainmenu.title"));
	tpOwnerMenu.add(tpOwnerAction);
	tpOwnerMenu.add(tpOwnerCalculation);
	tpOwnerMenu.add(new Separator());
	tpOwnerMenu.add(transformators);

	// help menu
	IMenuManager helpMenu = new MenuManager(GUIMessages.getMessage("application.menu.help"));
	helpMenu.add(recutilWebSiteAction);
	helpMenu.add(new Separator());
	helpMenu.add(actionHelpContent);
	helpMenu.add(actionAbout);

	// add to main menu bar
	menuBar.add(fileMenu);
	menuBar.add(billingMenu);
	menuBar.add(recalcMenu);
	menuBar.add(tpOwnerMenu);
	menuBar.add(helpMenu);
}

}
