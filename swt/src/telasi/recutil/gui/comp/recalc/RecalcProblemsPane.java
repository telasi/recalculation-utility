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

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
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

import telasi.recutil.beans.RecalcCutItem;
import telasi.recutil.beans.RecalcInstCp;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.RecalcTariffItem;
import telasi.recutil.calc.NotThrowable;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;

public class RecalcProblemsPane extends org.eclipse.swt.widgets.Composite {

	private ToolBar toolBar1;
	private TableViewer viewer;
	private TableColumn colDescription;
	private TableColumn colLine;
	private TableColumn colSource;
	private TableColumn colImage;
	private ToolItem tiClose;
	private RecalcEditingPane parentPane;

	public RecalcProblemsPane(Composite parent, int style) {
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
			this.setSize(525, 237);
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiClose = new ToolItem(toolBar1, SWT.NONE);
					tiClose.setToolTipText(GUIMessages
							.getMessage("comp.general.close"));
					tiClose
							.setImage(Plugin
									.getImage("icons/22x22/disable.png"));
					tiClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							if (parentPane != null) {
								parentPane.setErrorsVisible(false);
							}
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
				viewer = new TableViewer(this, SWT.FULL_SELECTION | SWT.SINGLE
						| SWT.VIRTUAL);
				viewer.getControl().setLayoutData(viewerLData);
				viewer.getTable().setHeaderVisible(true);
				viewer.getTable().setLinesVisible(true);
				{
					colImage = new TableColumn(viewer.getTable(), SWT.NONE);
					colImage.setWidth(20);
					colImage.setResizable(false);
				}
				{
					colSource = new TableColumn(viewer.getTable(), SWT.NONE);
					colSource.setText(GUIMessages
							.getMessage("comp.problem_pane.source"));
					colSource.setWidth(75);
				}
				{
					colLine = new TableColumn(viewer.getTable(), SWT.NONE);
					colLine.setText(GUIMessages
							.getMessage("comp.problem_pane.line"));
					colLine.setWidth(75);
				}
				{
					colDescription = new TableColumn(viewer.getTable(),
							SWT.NONE);
					colDescription.setText(GUIMessages
							.getMessage("comp.problem_pane.description"));
					colDescription.setWidth(500);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		showProblems(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new ProblemsLabelProvider());
	}

	public void setParentPane(RecalcEditingPane parentPane) {
		this.parentPane = parentPane;
	}

	public void showProblems(List problems) {
		viewer.setItemCount(problems == null ? 0 : problems.size());
		viewer.setContentProvider(new CommonItemContentProvider(problems));
	}

	private class ProblemsLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			switch (columnIndex) {
			case 0:
				NotThrowable problem = (NotThrowable) element;
				switch (problem.getSeverity()) {
				case NotThrowable.ERROR:
					return Plugin.getImage("icons/16x16/error.gif");
				case NotThrowable.WARNING:
					return Plugin.getImage("icons/16x16/warning.gif");
				default:
					return null;
				}
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			NotThrowable problem = (NotThrowable) element;
			switch (columnIndex) {
			case 1:
				return getPlace(problem.getSource());
			case 2:
				return getLine(problem.getSource());
			case 3:
				return problem == null ? "" : problem.getMessage();
			default:
				return "";
			}

		}

		private String getPlace(Object src) {
			if (src instanceof RecalcItem) {
				return GUIMessages
						.getMessage("comp.recalc_init_history_pane.charge_tab");
			} else if (src instanceof RecalcTariffItem) {
				return GUIMessages
						.getMessage("comp.recalc_init_history_pane.tariff_tab");
			} else if (src instanceof RecalcInstCp) {
				return GUIMessages
						.getMessage("comp.recalc_init_history_pane.instcp_tab");
			} else if (src instanceof RecalcCutItem) {
				return GUIMessages
						.getMessage("comp.recalc_init_history_pane.cut_tab");
			}
			return "";
		}

		private String getLine(Object src) {
			if (src instanceof RecalcItem) {
				RecalcItem item = (RecalcItem) src;
				int index = item.getInterval().getItems().indexOf(item);
				return index == -1 ? "?" : item.getInterval().getName() + " - "
						+ String.valueOf(index + 1);
			} else if (src instanceof RecalcTariffItem) {
				RecalcTariffItem item = (RecalcTariffItem) src;
				int index = item.getDetails().getTariffs().indexOf(item);
				return index == -1 ? "?" : String.valueOf(index + 1);
			} else if (src instanceof RecalcInstCp) {
				RecalcInstCp item = (RecalcInstCp) src;
				int index = item.getDetails().getInstCpItems().indexOf(item);
				return index == -1 ? "?" : String.valueOf(index + 1);
			} else if (src instanceof RecalcCutItem) {
				RecalcCutItem item = (RecalcCutItem) src;
				int index = item.getDetails().getCuts().indexOf(item);
				return index == -1 ? "?" : String.valueOf(index + 1);
			}
			return "";
		}

	}

}
