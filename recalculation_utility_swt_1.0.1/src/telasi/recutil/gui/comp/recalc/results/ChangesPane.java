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

import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;

public class ChangesPane extends Composite {
	private Composite composite1;
	private Composite composite2;
	private ChangesSummaryPane changesSummaryPane1;
	private ChangesPerCyclePane changesPerCyclePane1;
	private TabItem tabFactura;
	private TabItem tabTrash;
	private FacturaExpansionPane facturaExpansionPane1;
	private TrashCalculationPane trashCalculationPane;
	private Composite composite3;
	private TabItem tabSummary;
	private TabItem tabByCycle;
	private TabFolder tabFolder1;

	public ChangesPane(Composite parent, int style) {
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
			this.setSize(428, 232);
			{
				tabFolder1 = new TabFolder(this, SWT.NONE);
				{
					tabSummary = new TabItem(tabFolder1, SWT.NONE);
					tabSummary.setText(GUIMessages.getMessage("comp.changes_pane.summary.title"));
					tabSummary.setImage(Plugin.getImage("icons/16x16/bop/sum.png"));
					{
						composite2 = new Composite(tabFolder1, SWT.BORDER);
						GridLayout composite2Layout = new GridLayout();
						composite2Layout.horizontalSpacing = 0;
						composite2Layout.marginHeight = 0;
						composite2Layout.marginWidth = 0;
						composite2Layout.verticalSpacing = 0;
						composite2.setLayout(composite2Layout);
						tabSummary.setControl(composite2);
						{
							// $hide>>$
							GridData changesSummaryPane1LData = new GridData();
							changesSummaryPane1LData.horizontalAlignment = GridData.FILL;
							changesSummaryPane1LData.grabExcessHorizontalSpace = true;
							changesSummaryPane1LData.verticalAlignment = GridData.FILL;
							changesSummaryPane1LData.grabExcessVerticalSpace = true;
							changesSummaryPane1 = new ChangesSummaryPane(composite2, SWT.NONE);
							changesSummaryPane1.setLayoutData(changesSummaryPane1LData);
							// $hide<<$
						}
					}
				}
				{
					tabByCycle = new TabItem(tabFolder1, SWT.NONE);
					tabByCycle.setText(GUIMessages.getMessage("comp.changes_pane.bycycle.title"));
					tabByCycle.setImage(Plugin.getImage("icons/16x16/bop/regular.png"));
					{
						composite1 = new Composite(tabFolder1, SWT.BORDER);
						GridLayout composite1Layout = new GridLayout();
						composite1Layout.horizontalSpacing = 0;
						composite1Layout.marginHeight = 0;
						composite1Layout.marginWidth = 0;
						composite1Layout.verticalSpacing = 0;
						composite1.setLayout(composite1Layout);
						tabByCycle.setControl(composite1);
						{
							// $hide>>$
							GridData changesPerCyclePane1LData = new GridData();
							changesPerCyclePane1LData.horizontalAlignment = GridData.FILL;
							changesPerCyclePane1LData.grabExcessHorizontalSpace = true;
							changesPerCyclePane1LData.verticalAlignment = GridData.FILL;
							changesPerCyclePane1LData.grabExcessVerticalSpace = true;
							changesPerCyclePane1 = new ChangesPerCyclePane(composite1, SWT.NONE);
							changesPerCyclePane1.setLayoutData(changesPerCyclePane1LData);
							// $hide<<$
						}
					}
				}
				{
					tabFactura = new TabItem(tabFolder1, SWT.NONE);
					tabFactura.setText(GUIMessages.getMessage("comp.changes_pane.factura.title"));
					{
						composite3 = new Composite(tabFolder1, SWT.NONE);
						GridLayout composite3Layout = new GridLayout();
						composite3Layout.horizontalSpacing = 0;
						composite3Layout.marginHeight = 0;
						composite3Layout.marginWidth = 0;
						composite3Layout.verticalSpacing = 0;
						composite3.setLayout(composite3Layout);
						tabFactura.setControl(composite3);
						{
							// $hide>>$
							GridData facturaExpansionPane1LData = new GridData();
							facturaExpansionPane1LData.horizontalAlignment = GridData.FILL;
							facturaExpansionPane1LData.grabExcessHorizontalSpace = true;
							facturaExpansionPane1LData.verticalAlignment = GridData.FILL;
							facturaExpansionPane1LData.grabExcessVerticalSpace = true;
							facturaExpansionPane1 = new FacturaExpansionPane(composite3, SWT.NONE);
							facturaExpansionPane1.setLayoutData(facturaExpansionPane1LData);
							// $hide<<$
						}
					}
					tabFactura.setImage(Plugin.getImage("icons/16x16/print.png"));
				}
				{
					tabTrash = new TabItem(tabFolder1, SWT.NONE);
					tabTrash.setText(GUIMessages.getMessage("comp.changes_pane.trash.title"));
					tabTrash.setImage(Plugin.getImage("icons/16x16/trash.png"));
					{
						Composite comp = new Composite(tabFolder1, SWT.NONE);
						GridLayout composite3Layout = new GridLayout();
						composite3Layout.horizontalSpacing = 0;
						composite3Layout.marginHeight = 0;
						composite3Layout.marginWidth = 0;
						composite3Layout.verticalSpacing = 0;
						comp.setLayout(composite3Layout);
						tabTrash.setControl(comp);
						{
							GridData gridData = new GridData();
							gridData.horizontalAlignment = GridData.FILL;
							gridData.grabExcessHorizontalSpace = true;
							gridData.verticalAlignment = GridData.FILL;
							gridData.grabExcessVerticalSpace = true;
							trashCalculationPane = new TrashCalculationPane(comp, SWT.NONE);
							trashCalculationPane.setLayoutData(gridData);// TODO:
						}
					}
				}
				GridData tabFolder1LData = new GridData();
				tabFolder1LData.verticalAlignment = GridData.FILL;
				tabFolder1LData.grabExcessVerticalSpace = true;
				tabFolder1LData.horizontalAlignment = GridData.FILL;
				tabFolder1LData.grabExcessHorizontalSpace = true;
				tabFolder1.setLayoutData(tabFolder1LData);
				tabFolder1.setSelection(0);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void displayDiffByCycle(List diffs) {
		changesPerCyclePane1.display(diffs);
	}

	public void displayDiffSummary(List items) {
		changesSummaryPane1.display(items);
	}

	public void displayFacturaExpansion(List facturaExpansion) {
		facturaExpansionPane1.display(facturaExpansion);
	}

	public void displayTrashVouchers(List trashVouchers) {
		trashCalculationPane.display(trashVouchers);
	}
	
	public List getSummary() {
		return changesSummaryPane1.getSummary();
	}

	public List getFacturaExpansion() {
		return facturaExpansionPane1.getFacturaExpansion();
	}
	
	public List getTrashCorrections() {
		return trashCalculationPane.getItems();
	}

}
