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
package telasi.recutil.gui.comp.recalc.tariff;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.ITariff;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcTariffItem;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.service.eclipse.recalc.RecalcTariffDefaultRequest;
import telasi.recutil.service.eclipse.recalc.RecalcTariffDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcTariffMoveRequest;
import telasi.recutil.service.eclipse.recalc.RecalcTariffSelectRequest;

public class TariffItemPane extends Composite {
	private ToolBar toolBar1;
	private ToolItem toolItem1;
	private TableViewer viewer;
	private ToolItem toolItem3;
	private ToolItem tiDefault;
	private TableColumn colDetails;
	private TableColumn colTariff;
	private TableColumn colEndDate;
	private TableColumn colStartDate;
	private TableColumn colImage;
	private ToolItem tiDown;
	private ToolItem tiUp;
	private ToolItem toolItem2;
	private ToolItem tiProperties;
	private ToolItem tiDelete;
	private ToolItem tiNew;
	private ToolItem tiRefresh;
	private Recalc recalc;

	public TariffItemPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(558, 163);
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiRefresh = new ToolItem(toolBar1, SWT.NONE);
					tiRefresh.setToolTipText(GUIMessages
							.getMessage("comp.general.refresh"));
					tiRefresh.setImage(Plugin
							.getImage("icons/22x22/refresh.png"));
					tiRefresh.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							refreshInternal();
							validateView();
						}
					});
				}
				{
					toolItem1 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem1.setText("");
				}
				{
					tiDefault = new ToolItem(toolBar1, SWT.NONE);
					tiDefault.setToolTipText(GUIMessages
							.getMessage("comp.tariff_pane.default"));
					tiDefault.setImage(Plugin
							.getImage("icons/22x22/wizard.png"));
					tiDefault.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onGenerateDefault();
						}
					});
				}
				{
					toolItem2 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem2.setText("");
				}
				{
					tiNew = new ToolItem(toolBar1, SWT.NONE);
					tiNew.setToolTipText(GUIMessages
							.getMessage("comp.tariff_pane.new"));
					tiNew.setImage(Plugin.getImage("icons/22x22/new.gif"));
					tiNew.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onNew();
						}
					});
				}
				{
					tiDelete = new ToolItem(toolBar1, SWT.NONE);
					tiDelete.setToolTipText(GUIMessages
							.getMessage("comp.tariff_pane.delete"));
					tiDelete.setImage(Plugin.getImage("icons/22x22/trash.png"));
					tiDelete.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onDelete();
						}
					});
				}
				{
					tiProperties = new ToolItem(toolBar1, SWT.NONE);
					tiProperties.setToolTipText(GUIMessages
							.getMessage("comp.tariff_pane.properties"));
					tiProperties.setImage(Plugin
							.getImage("icons/22x22/properties.gif"));
					tiProperties.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onProperties();
						}
					});
				}
				{
					toolItem3 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem3.setText("");
				}
				{
					tiUp = new ToolItem(toolBar1, SWT.NONE);
					tiUp.setToolTipText(GUIMessages
							.getMessage("comp.tariff_pane.moveup"));
					tiUp.setImage(Plugin.getImage("icons/22x22/up.png"));
					tiUp.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onMove(true);
						}
					});
				}
				{
					tiDown = new ToolItem(toolBar1, SWT.NONE);
					tiDown.setToolTipText(GUIMessages
							.getMessage("comp.tariff_pane.movedown"));
					tiDown.setImage(Plugin.getImage("icons/22x22/down.png"));
					tiDown.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onMove(false);
						}
					});
				}
			}
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewerLData.grabExcessVerticalSpace = true;
				viewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION
						| SWT.VIRTUAL | SWT.MULTI);
				viewer.getControl().setLayoutData(viewerLData);
				viewer.getTable().setLinesVisible(true);
				viewer.getTable().setHeaderVisible(true);
				viewer
						.addSelectionChangedListener(new ISelectionChangedListener() {
							public void selectionChanged(
									SelectionChangedEvent event) {
								validateView();
							}
						});
				{
					colImage = new TableColumn(viewer.getTable(), SWT.NONE);
					colImage.setWidth(20);
					colImage.setResizable(false);
				}
				{
					colStartDate = new TableColumn(viewer.getTable(), SWT.NONE);
					colStartDate.setText(GUIMessages
							.getMessage("comp.general.start_date"));
					colStartDate.setWidth(100);
				}
				{
					colEndDate = new TableColumn(viewer.getTable(), SWT.NONE);
					colEndDate.setText(GUIMessages
							.getMessage("comp.general.end_date"));
					colEndDate.setWidth(100);
				}
				{
					colTariff = new TableColumn(viewer.getTable(), SWT.NONE);
					colTariff.setText(GUIMessages
							.getMessage("comp.general.tariff"));
					colTariff.setWidth(200);
				}
				{
					colDetails = new TableColumn(viewer.getTable(), SWT.NONE);
					colDetails.setText(GUIMessages
							.getMessage("comp.general.details"));
					colDetails.setWidth(200);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onNew() {
		if (recalc == null) {
			return;
		}
		TariffItemProperties prop = new TariffItemProperties(getShell(),
				SWT.NONE);
		prop.setRecalculation(recalc);
		RecalcTariffItem item = getSelectedItem();
		int seq = -1;
		if (item != null) {
			seq = item.getDetails().getTariffs().indexOf(item);
		}
		prop.setSequence(seq);
		prop.open();
		if (prop.isApproved()) {
			refreshInternal();
			selectById(prop.getItem().getId());
			validateView();
		}
	}

	private void onProperties() {

		RecalcTariffItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		TariffItemProperties prop = new TariffItemProperties(getShell(),
				SWT.NONE);
		prop.setItem(item);
		prop.open();
		if (prop.isApproved()) {
			refreshInternal();
			selectById(prop.getItem().getId());
			validateView();
		}

	}

	private void onMove(boolean up) {
		RecalcTariffItem item = getSelectedItem();
		if (item == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcTariffMoveRequest task = new RecalcTariffMoveRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setItem(item);
			task.setMoveUp(up);
			DefaultRecutilClient.processRequest(task);
			refreshInternal();
			selectById(item.getId());
			validateView();
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	@SuppressWarnings("unchecked")
	private void onDelete() {
		List items = getSelectedItems();
		if (items == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		List tasks = new ArrayList();
		for (int i = 0; i < items.size(); i++) {
			RecalcTariffItem item = (RecalcTariffItem) items.get(i);
			RecalcTariffDeleteRequest task = new RecalcTariffDeleteRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setItem(item);
			tasks.add(task);
		}
		boolean resp = MessageDialog.openQuestion(getShell(), GUIMessages
				.getMessage("comp.general.confirm"), GUIMessages.getMessage(
				"comp.general.confirm_delete_object_count",
				new Object[] { new Integer(items.size()) }));
		if (!resp) {
			return;
		}
		try {
			DefaultRecutilClient.processRequests(tasks);
			refreshInternal();
			validateView();
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	private void onGenerateDefault() {
		if (recalc == null) {
			return;
		}
		boolean resp = MessageDialog.openQuestion(getShell(), GUIMessages
				.getMessage("comp.general.confirm"), GUIMessages
				.getMessage("comp.tariff_pane.confirm_default_generation"));
		if (!resp) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcTariffDefaultRequest task = new RecalcTariffDefaultRequest(
					Application.USER_NAME, Application.PASSWORD);
			task.setRecalc(recalc);
			DefaultRecutilClient.processRequest(task);
			refreshInternal();
			validateView();
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	private void initDataBehaivour() {
		displayItems(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new TariffLabelProvider());
	}

	private void displayItems(List items) {
		viewer.setItemCount(items == null ? 0 : items.size());
		viewer.setContentProvider(new CommonItemContentProvider(items));
	}

	public void refresh() {
		refreshInternal();
		validateView();
	}

	@SuppressWarnings("unchecked")
	private void refreshInternal() {
		if (recalc == null) {
			return;
		}
		if (!Application.validateConnection()) {
			return;
		}
		try {
			RecalcTariffSelectRequest request = new RecalcTariffSelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			request.setRecalc(recalc);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			RecalcTariffSelectRequest callback = (RecalcTariffSelectRequest) resp
					.getRequest();
			List items = callback.getTariffs();
			if (items != null) {
				for (int i = 0; i < items.size(); i++) {
					RecalcTariffItem item = (RecalcTariffItem) items.get(i);
					ITariff tariff = Cache.findTariffById(item.getTariff()
							.getId());
					item.setTariff(tariff);
					item.setDetails(recalc.getDetails());
				}
			}
			recalc.getDetails().getTariffs().clear();
			if (items != null) {
				recalc.getDetails().getTariffs().addAll(items);
			}
			displayItems(items);
		} catch (Throwable t) {
			MessageDialog.openError(getShell(), GUIMessages
					.getMessage("comp.general.error"), t.toString());
		}
		viewer.getTable().deselectAll();
	}

	public void clear() {
		displayItems(null);
		recalc = null;
		validateView();
	}

	private void validateView() {
		boolean hasRecalc = recalc != null;
		RecalcTariffItem item = getSelectedItem();
		boolean selected = hasRecalc && item != null;
		tiRefresh.setEnabled(hasRecalc);
		tiDefault.setEnabled(hasRecalc);
		tiNew.setEnabled(hasRecalc);
		tiDelete.setEnabled(selected);
		tiProperties.setEnabled(selected);
		tiUp.setEnabled(selected);
		tiDown.setEnabled(selected);
	}

	public RecalcTariffItem getSelectedItem() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		return (RecalcTariffItem) provider.items.get(index);
	}

	@SuppressWarnings("unchecked")
	public List getSelectedItems() {
		int indecies[] = viewer.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		List items = new ArrayList();
		for (int i = 0; i < indecies.length; i++) {
			int index = indecies[i];
			items.add(provider.items.get(index));
		}
		return items;
	}

	private class TariffLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Plugin.getImage("icons/16x16/bop/payment.png");
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			RecalcTariffItem item = (RecalcTariffItem) element;
			switch (columnIndex) {
			case 1:
				return item == null ? "" : Date.format(item.getStartDate());
			case 2:
				return item == null ? "" : Date.format(item.getEndDate());
			case 3:
				return item == null ? "" : (item.getTariff() == null ? "?"
						: GUITranslator.GEO_ASCII_TO_KA(item.getTariff()
								.getName()));
			case 4:
				return item == null ? "" : (item.getTariff() == null ? "?"
						: item.getTariff().toString());
			default:
				return "";
			}

		}

	}

	private void selectById(long id) {
		CommonItemContentProvider provider = (CommonItemContentProvider) viewer
				.getContentProvider();
		if (provider.items != null) {
			for (int i = 0; i < provider.items.size(); i++) {
				RecalcTariffItem item = (RecalcTariffItem) provider.items
						.get(i);
				if (item.getId() == id) {
					viewer.getTable().select(i);
					viewer.getTable().showSelection();
					break;
				}
			}
		}
	}

}
