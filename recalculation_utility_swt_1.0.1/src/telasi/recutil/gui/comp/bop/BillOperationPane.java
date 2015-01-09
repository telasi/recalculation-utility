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
package telasi.recutil.gui.comp.bop;

import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.beans.Operation;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class BillOperationPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private TableViewer viewer;
	private ToolItem tiProperties;
	private ToolBar toolBar1;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;

	public BillOperationPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
		validateView();
	}

	//
	// Initialize
	//

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(300, 300);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle = new Label(composite1, SWT.NONE);
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setText(GUIMessages.getMessage("comp.billoper_pane.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
				}
			}
			{
				GridData lblSeparator1LData = new GridData();
				lblSeparator1LData.horizontalAlignment = GridData.FILL;
				lblSeparator1LData.grabExcessHorizontalSpace = true;
				lblSeparator1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator1.setLayoutData(lblSeparator1LData);
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2LData.heightHint = 90;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.widthHint = 48;
					lblImageLData.heightHint = 48;
					lblImageLData.verticalAlignment = GridData.BEGINNING;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/bop.png"));
				}
				{
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription = new Label(composite2, SWT.WRAP);
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.billoper_pane.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				GridData composite3LData = new GridData();
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite3, SWT.BORDER
							| SWT.FULL_SELECTION | SWT.VIRTUAL);
					viewer.getControl().setLayoutData(viewerLData);
				}
			}
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(this, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiProperties = new ToolItem(toolBar1, SWT.NONE);
					tiProperties.setImage(Plugin
							.getImage("icons/22x22/properties.gif"));
					tiProperties
							.setToolTipText(GUIMessages
									.getMessage("comp.billoper_pane.properties_tooltip"));
					tiProperties.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onProperties();
						}
					});
				}
			}
			// $protect>>$
			viewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					validateView();
				}
			});
			// $protect<<$
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		displayOperations(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new OperationLabelProvider());
	}

	//
	// Private section
	//

	private void validateView() {
		Operation oper = getSelectedOperation();
		boolean operIsSelected = oper != null;
		tiProperties.setEnabled(operIsSelected);
	}

	private void onProperties() {
		Operation oper = getSelectedOperation();
		if (oper == null) {
			return;
		}
		BillOperationProperties prop = new BillOperationProperties(getShell(),
				SWT.NONE);
		prop.setOperation(oper);
		prop.open();
	}

	//
	// Public operations
	//

	public void displayOperations(List operations) {
		viewer.setItemCount(operations == null ? 0 : operations.size());
		viewer.setContentProvider(new OperationContentProvider(operations));
		validateView();
	}

	public Operation getSelectedOperation() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		OperationContentProvider provider = (OperationContentProvider) viewer
				.getContentProvider();
		return (Operation) provider.operations.get(index);
	}

	public void selectOperationById(int id) {
		OperationContentProvider provider = (OperationContentProvider) viewer
				.getContentProvider();
		List operations = provider.operations;
		if (operations != null) {
			for (int i = 0; i < operations.size(); i++) {
				Operation oper = (Operation) operations.get(i);
				if (oper.getId() == id) {
					viewer.getTable().setSelection(i);
					viewer.getTable().showSelection();
					break;
				}
			}
		}
		validateView();
	}

	//
	// Inner classes
	//

	private class OperationContentProvider implements
			IStructuredContentProvider {

		private List operations;

		public OperationContentProvider(List operations) {
			this.operations = operations;
		}

		public Object[] getElements(Object inputElement) {
			return operations == null ? new Object[] {} : operations.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class OperationLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			Operation oper = (Operation) element;
			return GUIUtils.getOperationImage(oper);
		}

		public String getColumnText(Object element, int columnIndex) {
			Operation oper = (Operation) element;
			return GUITranslator.GEO_ASCII_TO_KA(oper.getName());
		}

	}

}
