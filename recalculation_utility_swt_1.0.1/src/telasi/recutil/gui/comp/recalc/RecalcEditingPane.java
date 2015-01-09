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

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import telasi.recutil.beans.CalculationInterval;
import telasi.recutil.beans.CalculationItem;
import telasi.recutil.beans.DiffSummary;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.User;
import telasi.recutil.calc.NotThrowable;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.comp.recalc.results.ResultsPane;
import telasi.recutil.gui.comp.recalc.savetab.SavePane;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.service.eclipse.recalc.RebuildRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRequest3;

public class RecalcEditingPane extends Composite {
	private SashForm sashForm1;
	private Composite contentPane;
	private ResultsPane pnResults;
	private SavePane savePane1;
	private TabItem tabSave;
	private RecalcInitHistoryPane pnRecalcInitHistory;
	private TabItem tabResults;
	private TabItem tabInitialHistory;
	private TabFolder tabFolder1;
	private RecalcProblemsPane pnProblems;
	private Recalc recalc;

	public RecalcEditingPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			this.setLayout(thisLayout);
			this.setSize(318, 224);
			{
				sashForm1 = new SashForm(this, SWT.VERTICAL | SWT.V_SCROLL);
				GridData sashForm1LData = new GridData();
				sashForm1LData.horizontalAlignment = GridData.FILL;
				sashForm1LData.grabExcessHorizontalSpace = true;
				sashForm1LData.verticalAlignment = GridData.FILL;
				sashForm1LData.grabExcessVerticalSpace = true;
				sashForm1.setLayoutData(sashForm1LData);
				sashForm1.setSize(60, 30);
				{
					contentPane = new Composite(sashForm1, SWT.NONE);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.horizontalSpacing = 0;
					composite1Layout.marginWidth = 0;
					composite1Layout.verticalSpacing = 0;
					composite1Layout.marginHeight = 0;
					contentPane.setLayout(composite1Layout);
					contentPane.setBounds(39, 64, 318, 224);
					{
						tabFolder1 = new TabFolder(contentPane, SWT.NONE);
						{
							tabInitialHistory = new TabItem(tabFolder1, SWT.NONE);
							tabInitialHistory.setText(GUIMessages.getMessage("comp.recalc_edit_pane.init_history"));
							{
								// $hide>>$
								pnRecalcInitHistory = new RecalcInitHistoryPane(tabFolder1, SWT.NONE);
								tabInitialHistory.setControl(pnRecalcInitHistory);
								// $hide<<$
							}
							tabInitialHistory.setImage(Plugin.getImage("icons/16x16/bop/charge.png"));
						}
						{
							tabResults = new TabItem(tabFolder1, SWT.NONE);
							tabResults.setText(GUIMessages.getMessage("comp.recalc_edit_pane.recalc_results"));
							{
								// $hide>>$
								pnResults = new ResultsPane(tabFolder1, SWT.NONE);
								tabResults.setControl(pnResults);
								// $hide<<$
							}
							tabResults.setImage(Plugin.getImage("icons/16x16/bop/voucher.png"));
						}
						{
							tabSave = new TabItem(tabFolder1, SWT.NONE);
							tabSave.setText(GUIMessages.getMessage("comp.general.save"));
							{
								// $hide>>$
								savePane1 = new SavePane(tabFolder1, SWT.NONE);
								tabSave.setControl(savePane1);
								// $hide<<$
							}
							tabSave.setImage(Plugin.getImage("icons/16x16/save.png"));
						}
						GridData tabFolder1LData = new GridData();
						tabFolder1LData.horizontalAlignment = GridData.FILL;
						tabFolder1LData.grabExcessHorizontalSpace = true;
						tabFolder1LData.verticalAlignment = GridData.FILL;
						tabFolder1LData.grabExcessVerticalSpace = true;
						tabFolder1.setLayoutData(tabFolder1LData);
						tabFolder1.setSelection(0);
					}
				}
				{
					// $hide>>$
					pnProblems = new RecalcProblemsPane(sashForm1, SWT.BORDER);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.horizontalSpacing = 0;
					composite2Layout.marginHeight = 0;
					composite2Layout.marginWidth = 0;
					composite2Layout.verticalSpacing = 0;
					pnProblems.setLayout(composite2Layout);
					pnProblems.setBounds(68, 66, 158, 224);
					// $hide<<$
				}
			}
			// $protect>>$
			customInit();
			// $protect<<$
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void customInit() {
		sashForm1.setWeights(new int[] { 2, 1 });
		pnProblems.setParentPane(this);
		setErrorsVisible(false);

		// make links
		pnResults.linkWithRecalcChargePane(pnRecalcInitHistory.getRecalcChargePane());

	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
		pnRecalcInitHistory.setRecalc(recalc);
		savePane1.setRecalc(recalc);
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void refresh() {
		pnRecalcInitHistory.refresh();
		showInitHistory();
	}

	public void clear() {
		this.recalc = null;
		pnRecalcInitHistory.clear();
		pnProblems.showProblems(null);
		setErrorsVisible(false);
		pnResults.clear();
	}

	public boolean rebuild() {
		if (!Application.validateConnection()) {
			return false;
		}

		// I can not understand why we need this additional refresh,
		// but it is neccesary thing
		pnRecalcInitHistory.refreshChargePane();

		RebuildRequest request = new RebuildRequest(Application.USER_NAME, Application.PASSWORD);
		request.setRecalc(recalc);
		try {
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			RebuildRequest callback = (RebuildRequest) resp.getRequest();
			List problems = callback.getProblems();
			if (problems == null || problems.isEmpty()) {
				pnProblems.showProblems(null);
				setErrorsVisible(false);
				return true;
			} else {
				pnProblems.showProblems(problems);
				setErrorsVisible(true);
				for (int i = 0; i < problems.size(); i++) {
					NotThrowable nt = (NotThrowable) problems.get(i);
					if (nt.getSeverity() == NotThrowable.ERROR) {
						return false;
					}
				}
				return true;
			}
		} catch (Throwable t) {
			t.printStackTrace();
			MessageDialog.openError(getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
			return false;
		}
	}

	public void calculate() {
		// pnResults.clear();
		if (!rebuild())
			return;
		RecalcRequest3 request = new RecalcRequest3(Application.USER_NAME, Application.PASSWORD);
		request.setRecalc(recalc);
		try {
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			RecalcRequest3 callback = (RecalcRequest3) resp.getRequest();
			List intervals = callback.getIntervals();
			List diffByCycle = callback.getDiffByCycle();
			DiffSummary summary = callback.getDiffSummary();
			List facturaExpansion = callback.getFacturaExpansion();
			List trashVouchers = callback.getTrashVouchers();

			if (intervals != null && !intervals.isEmpty()) {
				for (int i = 0; i < intervals.size(); i++) {
					CalculationInterval interval = (CalculationInterval) intervals.get(i);
					if (interval.getItems() != null && !interval.getItems().isEmpty()) {
						for (int j = 0; j < interval.getItems().size(); j++) {
							CalculationItem item = (CalculationItem) interval.getItems().get(j);
							item.setOperation(Cache.findOperationById(item.getOperation().getId()));
						}
					}
				}
			}
			pnResults.displayChargeIntervals(intervals);
			pnResults.displayDiffByCycle(diffByCycle);
			pnResults.displayDiffSummary(summary == null ? null : summary.getDetails());
			pnResults.displayFacturaExpansion(facturaExpansion);
			pnResults.displayTrashVouchers(trashVouchers);
			showResults();
		} catch (Throwable t) {
			t.printStackTrace();
			MessageDialog.openError(getShell(), GUIMessages.getMessage("comp.general.error"), t.toString());
		}
	}

	public void setErrorsVisible(boolean visible) {
		sashForm1.setMaximizedControl(visible ? null : contentPane);
	}

	public void showInitHistory() {
		tabFolder1.setSelection(0);
	}

	public void showResults() {
		tabFolder1.setSelection(1);
	}

	public List getSummary() {
		return pnResults.getSummary();
	}

	public List getFacturaExpansion() {
		return pnResults.getFacturaExpansion();
	}

	public List getTrashCorrections() {
		return pnResults.getTrashCorrections();
	}
	
	public User getAdvisor() {
		return savePane1.getAdvisor();
	}

	public String getSaveDescription() {
		return savePane1.getDescription();
	}

}
