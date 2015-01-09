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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.Operation;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class BillOperationRequimentPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Text txtGel;
	private Label lblGel;
	private Text txtKwh;
	private Label lblKwh;
	private Text txtCycle;
	private Label lblCycle;
	private Text txtReading;
	private Label lblReading;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;
	private Operation operation;

	public BillOperationRequimentPane(Composite parent, int style) {
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
			this.setSize(314, 383);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				composite1Layout.marginTop = 10;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblTitle = new Label(composite1, SWT.NONE);
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setText(GUIMessages.getMessage("comp.billoper_req_pane.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
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
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.widthHint = 48;
					lblImageLData.heightHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/lock.png"));
				}
				{
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescription = new Label(composite2, SWT.WRAP);
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.billoper_req_pane.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				composite3Layout.marginTop = 10;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblReading = new Label(composite3, SWT.NONE);
					lblReading.setText(GUIMessages
							.getMessage("comp.general.reading"));
				}
				{
					GridData txtReadingLData = new GridData();
					txtReadingLData.horizontalAlignment = GridData.FILL;
					txtReadingLData.grabExcessHorizontalSpace = true;
					txtReading = new Text(composite3, SWT.READ_ONLY
							| SWT.BORDER);
					txtReading.setLayoutData(txtReadingLData);
				}
				{
					lblCycle = new Label(composite3, SWT.NONE);
					lblCycle.setText(GUIMessages
							.getMessage("comp.general.cycle"));
				}
				{
					GridData txtCycleLData = new GridData();
					txtCycleLData.horizontalAlignment = GridData.FILL;
					txtCycleLData.grabExcessHorizontalSpace = true;
					txtCycle = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtCycle.setLayoutData(txtCycleLData);
				}
				{
					lblKwh = new Label(composite3, SWT.NONE);
					lblKwh.setText(GUIMessages.getMessage("comp.general.kwh"));
				}
				{
					GridData txtKwhLData = new GridData();
					txtKwhLData.horizontalAlignment = GridData.FILL;
					txtKwhLData.grabExcessHorizontalSpace = true;
					txtKwh = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtKwh.setLayoutData(txtKwhLData);
				}
				{
					lblGel = new Label(composite3, SWT.NONE);
					lblGel.setText(GUIMessages.getMessage("comp.general.gel"));
				}
				{
					GridData txtGelLData = new GridData();
					txtGelLData.horizontalAlignment = GridData.FILL;
					txtGelLData.grabExcessHorizontalSpace = true;
					txtGel = new Text(composite3, SWT.READ_ONLY | SWT.BORDER);
					txtGel.setLayoutData(txtGelLData);
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
		reset();
	}

	public Operation getOperation() {
		return operation;
	}

	private void reset() {
		if (operation == null) {
			txtReading.setText("");
			txtCycle.setText("");
			txtKwh.setText("");
			txtGel.setText("");
		} else {
			txtReading.setText(GUIUtils.getRequimentName(operation
					.getReadingRequiment()));
			txtCycle.setText(GUIUtils.getRequimentName(operation
					.getCycleRequiment()));
			txtKwh.setText(GUIUtils.getRequimentName(operation
					.getKwhRequiment()));
			txtGel.setText(GUIUtils.getRequimentName(operation
					.getGelRequiment()));
		}
	}

}
