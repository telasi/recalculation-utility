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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import telasi.recutil.beans.Recalc;
import telasi.recutil.gui.comp.recalc.inscp.FullInspcpPane;
import telasi.recutil.gui.comp.recalc.regular.RegularPane;
import telasi.recutil.gui.comp.recalc.tariff.TariffItemPane;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;

public class RecalcInitHistoryPane extends Composite {

	private TabFolder tabFolder1;
	private TabItem tabRegular;
	private TabItem tabTariff;
	private TariffItemPane tariffPane1;
	private FullInspcpPane fullInspcpPane1;
	private RegularPane regularPane1;
	private RecalcChargePane recalcChargePane1;
	private TabItem tabInspcp;
	private TabItem tabCharge;
	private Recalc recalc;

	public RecalcInitHistoryPane(Composite parent, int style) {
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
			this.setSize(435, 271);
			{
				tabFolder1 = new TabFolder(this, SWT.NONE);
				{
					tabCharge = new TabItem(tabFolder1, SWT.NONE);
					tabCharge.setText(GUIMessages.getMessage("comp.recalc_init_history_pane.charge_tab"));
					{
						// $hide>>$
						recalcChargePane1 = new RecalcChargePane(tabFolder1, SWT.NONE);
						tabCharge.setControl(recalcChargePane1);
						// $hide<<$
					}
					tabCharge.setImage(Plugin
							.getImage("icons/16x16/bop/reading.png"));
				}
				{
					tabTariff = new TabItem(tabFolder1, SWT.NONE);
					tabTariff
							.setText(GUIMessages
									.getMessage("comp.recalc_init_history_pane.tariff_tab"));
					{
						// $hide>>$
						tariffPane1 = new TariffItemPane(tabFolder1, SWT.NONE);
						tabTariff.setControl(tariffPane1);
						// $hide<<$
					}
					tabTariff.setImage(Plugin
							.getImage("icons/16x16/bop/payment.png"));
				}
				{
					tabInspcp = new TabItem(tabFolder1, SWT.NONE);
					tabInspcp
							.setText(GUIMessages
									.getMessage("comp.recalc_init_history_pane.instcp_tab"));
					{
						// $hide>>$
						fullInspcpPane1 = new FullInspcpPane(tabFolder1,
								SWT.NONE);
						tabInspcp.setControl(fullInspcpPane1);
						// $hide<<$
					}
					tabInspcp.setImage(Plugin
							.getImage("icons/16x16/bop/power.png"));
				}
				{
					// tabCut = new TabItem(tabFolder1, SWT.NONE);
					// tabCut.setText(GUIMessages.getMessage("comp.recalc_init_history_pane.cut_tab"));
					// tabCut.setImage(Plugin.getImage("icons/16x16/cut.png"));
				}
				{
					tabRegular = new TabItem(tabFolder1, SWT.NONE);
					tabRegular
							.setText(GUIMessages
									.getMessage("comp.recalc_init_history_pane.regular_tab"));
					tabRegular.setImage(Plugin
							.getImage("icons/16x16/bop/regular.png"));
					{
						// $hide>>$
						regularPane1 = new RegularPane(tabFolder1, SWT.NONE);
						tabRegular.setControl(regularPane1);
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
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
		recalcChargePane1.setRecalc(recalc);
		regularPane1.setRecalc(recalc);
		fullInspcpPane1.setRecalc(recalc);
		tariffPane1.setRecalc(recalc);
	}

	public void refresh() {
		recalcChargePane1.refresh();
		regularPane1.refresh();
		fullInspcpPane1.refresh();
		tariffPane1.refresh();
	}

	public void refreshChargePane() {
		recalcChargePane1.refresh();
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void clear() {
		recalcChargePane1.clear();
		regularPane1.clear();
		regularPane1.clear();
		tariffPane1.clear();
	}

	public RecalcChargePane getRecalcChargePane() {
		return recalcChargePane1;
	}

}
