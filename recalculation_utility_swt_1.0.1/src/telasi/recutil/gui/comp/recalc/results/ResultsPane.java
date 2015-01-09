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
package telasi.recutil.gui.comp.recalc.results;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import telasi.recutil.gui.comp.recalc.RecalcChargePane;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;

public class ResultsPane extends Composite {

	private TabFolder tabFolder1;
	private DerivedChargePane derivedChargePane1;
	private ChangesPane changesPane1;
	private TabItem tabChanges;
	private TabItem tabDerivedHistory;

	public ResultsPane(Composite parent, int style) {
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
			this.setSize(374, 244);
			{
				tabFolder1 = new TabFolder(this, SWT.NONE);
				{
					tabDerivedHistory = new TabItem(tabFolder1, SWT.NONE);
					tabDerivedHistory.setText(GUIMessages.getMessage("comp.results_pane.derived_charge"));
					{
						// $hide>>$
						derivedChargePane1 = new DerivedChargePane(tabFolder1, SWT.NONE);
						tabDerivedHistory.setControl(derivedChargePane1);
						// $hide<<$
					}
					tabDerivedHistory.setImage(Plugin.getImage("icons/16x16/bop/reading.png"));
				}
				{
					tabChanges = new TabItem(tabFolder1, SWT.NONE);
					tabChanges.setText(GUIMessages.getMessage("comp.changes_pane.title"));
					{
						// $hide>>$
						changesPane1 = new ChangesPane(tabFolder1, SWT.NONE);
						tabChanges.setControl(changesPane1);
						// $hide<<$
					}
					tabChanges.setImage(Plugin.getImage("icons/16x16/changes.png"));
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

	public void displayChargeIntervals(List intervals) {
		derivedChargePane1.displayIntervals(intervals);
	}

	public void displayDiffByCycle(List cycleDiffs) {
		changesPane1.displayDiffByCycle(cycleDiffs);
	}

	public void displayDiffSummary(List items) {
		changesPane1.displayDiffSummary(items);
	}

	public void displayFacturaExpansion(List facturaExpansion) {
		changesPane1.displayFacturaExpansion(facturaExpansion);
	}

	public void displayTrashVouchers(List trashVouchers) {
		changesPane1.displayTrashVouchers(trashVouchers);
	}

	public void clear() {
		displayChargeIntervals(null);
		displayDiffByCycle(null);
		displayDiffSummary(null);
		displayFacturaExpansion(null);
		displayTrashVouchers(null);
	}

	public void linkWithRecalcChargePane(RecalcChargePane chargePane) {
		derivedChargePane1.setRelatedChargePane(chargePane);
	}

	public List getSummary() {
		return changesPane1.getSummary();
	}

	public List getFacturaExpansion() {
		return changesPane1.getFacturaExpansion();
	}

	public List getTrashCorrections() {
		return changesPane1.getTrashCorrections();
	}
}
