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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.views.RecalculationView;
import telasi.recutil.service.eclipse.recalc.RecalcSelectRequest;

public class RecalcHistoryPane extends Composite {

	private Composite composite1;
	private Composite composite2;
	private TableViewer viewer;
	private TableColumn colCreated;
	private TableColumn colAccount;
	private TableColumn colImage;
	private TableColumn colCustomer;
	private TableColumn colNumber;
	private Composite composite3;
	private Label lblDescription;
	private Label lblTitle;
	private Label lblImage;
	private Customer customer;

	public RecalcHistoryPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	//
	// Initialization
	//

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(254, 202);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 32;
					lblImageLData.widthHint = 32;
					lblImage = new Label(composite1, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/32x32/calc.png"));
				}
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.horizontalSpacing = 0;
					composite2Layout.marginWidth = 0;
					composite2Layout.marginHeight = 0;
					GridData composite2LData = new GridData();
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2LData.verticalAlignment = GridData.FILL;
					composite2LData.grabExcessVerticalSpace = true;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					{
						GridData lblTitleLData = new GridData();
						lblTitleLData.horizontalAlignment = GridData.FILL;
						lblTitleLData.grabExcessHorizontalSpace = true;
						lblTitle = new Label(composite2, SWT.NONE);
						lblTitle.setLayoutData(lblTitleLData);
						lblTitle.setFont(GUIUtils.createSubtitleFont(lblTitle
								.getFont()));
						lblTitle.setText(GUIMessages
								.getMessage("comp.recalc_history_pane.title"));
					}
					{
						GridData lblDescriptionLData = new GridData();
						lblDescriptionLData.horizontalAlignment = GridData.FILL;
						lblDescriptionLData.grabExcessHorizontalSpace = true;
						lblDescription = new Label(composite2, SWT.NONE);
						lblDescription.setLayoutData(lblDescriptionLData);
						lblDescription.setText(GUIMessages
								.getMessage("comp.recalc_history_pane.descr"));
					}
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite3, SWT.BORDER | SWT.MULTI
							| SWT.VIRTUAL | SWT.FULL_SELECTION);
					viewer.getControl().setLayoutData(viewerLData);
					viewer.getTable().setLinesVisible(true);
					viewer.getTable().setHeaderVisible(true);
					{
						colImage = new TableColumn(viewer.getTable(), SWT.NONE);
						colImage.setWidth(20);
						colImage.setResizable(false);
					}
					viewer
							.addSelectionChangedListener(new ISelectionChangedListener() {
								public void selectionChanged(
										SelectionChangedEvent event) {
									validateView();
								}
							});
					{
						colNumber = new TableColumn(viewer.getTable(), SWT.NONE);
						colNumber.setText(GUIMessages
								.getMessage("comp.general.number"));
						colNumber.setWidth(75);
					}
					{
						colCustomer = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colCustomer.setText(GUIMessages
								.getMessage("comp.general.customer"));
						colCustomer.setWidth(75);
					}
					{
						colAccount = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colAccount.setText(GUIMessages
								.getMessage("comp.general.account"));
						colAccount.setWidth(75);
					}
					{
						colCreated = new TableColumn(viewer.getTable(),
								SWT.NONE);
						colCreated.setText(GUIMessages
								.getMessage("comp.general.create_date"));
						colCreated.setWidth(100);
					}
					{
						TableColumn colSave = new TableColumn(
								viewer.getTable(), SWT.NONE);
						colSave.setText(GUIMessages
								.getMessage("comp.general.save_date"));
						colSave.setWidth(100);
					}
					{
						TableColumn colOperator = new TableColumn(viewer
								.getTable(), SWT.NONE);
						colOperator.setText(GUIMessages
								.getMessage("comp.general.operator"));
						colOperator.setWidth(150);
					}
					{
						TableColumn colAdvisor = new TableColumn(viewer
								.getTable(), SWT.NONE);
						colAdvisor.setText(GUIMessages
								.getMessage("comp.general.advisor"));
						colAdvisor.setWidth(150);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayRecalculations(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new RecalculationHistoryLabelProvider());
	}

	private void displayRecalculations(List recalculations) {
		viewer.setItemCount(recalculations == null ? 0 : recalculations.size());
		viewer.setContentProvider(new RecalculationHistoryContentProvider(
				recalculations));
		validateView();
	}

	public TableViewer getViewer() {
		return viewer;
	}

	//
	// Public methods
	//

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void refresh() {
		if (customer == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcSelectRequest request = new RecalcSelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setCustomer(customer);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			RecalcSelectRequest callback = (RecalcSelectRequest) resp
					.getRequest();
			List history = callback.getHistory();
			displayRecalculations(history);
			if (history != null && !history.isEmpty()) {
				for (int i = 0; i < history.size(); i++) {
					Recalc recalc = (Recalc) history.get(i);
					if (recalc.getSaveUser() != null) {
						recalc.setSaveUser(Cache.findUserById(recalc
								.getSaveUser().getId()));
					}
					if (recalc.getAdvisor() != null) {
						recalc.setAdvisor(Cache.findUserById(recalc
								.getAdvisor().getId()));
					}
				}
			}
		} catch (Throwable t) {
			t.toString();
		}
	}

	public Recalc getSelectedRecalc() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		RecalculationHistoryContentProvider provider = (RecalculationHistoryContentProvider) viewer
				.getContentProvider();
		return (Recalc) provider.recalculations.get(index);
	}

	@SuppressWarnings("unchecked")
	public List getSelectedRecalcs() {
		int[] indecies = viewer.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		RecalculationHistoryContentProvider provider = (RecalculationHistoryContentProvider) viewer
				.getContentProvider();
		List recalcs = new ArrayList();
		for (int i = 0; i < indecies.length; i++) {
			int index = indecies[i];
			recalcs.add(provider.recalculations.get(index));
		}
		return recalcs;
	}

	public void selectRecalcById(long id) {
		RecalculationHistoryContentProvider provider = (RecalculationHistoryContentProvider) viewer
				.getContentProvider();
		if (provider.recalculations == null) {
			return;
		}
		for (int i = 0; i < provider.recalculations.size(); i++) {
			Recalc recalc = (Recalc) provider.recalculations.get(i);
			if (recalc.getId() == id) {
				viewer.getTable().setSelection(i);
				break;
			}
		}
	}

	public void refreshView() {
		viewer.refresh();
	}

	@SuppressWarnings("unchecked")
	public void addRecalculation(Recalc newRecalc) {
		RecalculationHistoryContentProvider provider = (RecalculationHistoryContentProvider) viewer
				.getContentProvider();
		List data = provider.recalculations;
		if (data == null) {
			data = new ArrayList();
		}
		data.add(newRecalc);
		displayRecalculations(data);
		selectRecalcById(newRecalc.getId());
	}

	@SuppressWarnings("unchecked")
	public void removeRecalculations(List recalcs) {
		viewer.getTable().deselectAll();
		RecalculationHistoryContentProvider provider = (RecalculationHistoryContentProvider) viewer
				.getContentProvider();
		List data = provider.recalculations;
		if (data != null) {
			data.removeAll(recalcs);
		}
		displayRecalculations(data);
	}
	
	//
	// Inner classes
	//

	private class RecalculationHistoryContentProvider implements
			IStructuredContentProvider {

		private List recalculations;

		public RecalculationHistoryContentProvider(List recalculations) {
			this.recalculations = recalculations;
		}

		public Object[] getElements(Object inputElement) {
			return recalculations == null ? new Object[] {} : recalculations
					.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class RecalculationHistoryLabelProvider extends LabelProvider
			implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			Recalc recalc = (Recalc) element;
			if (recalc == null) {
				return null;
			}
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
				return recalc.getSaveDate() == null ? "" : Date.format(recalc
						.getSaveDate());
			case 6:
				return recalc.getSaveUser() == null ? "" : GUITranslator
						.GEO_ASCII_TO_KA(recalc.getSaveUser().getFullName());
			case 7:
				return recalc.getAdvisor() == null ? "" : GUITranslator
						.GEO_ASCII_TO_KA(recalc.getAdvisor().getFullName());
			default:
				return "";
			}
		}

	}

	//
	// Related forms
	//

	private RecalculationView recalculationView;

	public void setRelatedRecalculationView(RecalculationView recalculationView) {
		this.recalculationView = recalculationView;
	}

	private void validateView() {
		if (recalculationView != null) {
			recalculationView.validateView();
		}
	}

}
