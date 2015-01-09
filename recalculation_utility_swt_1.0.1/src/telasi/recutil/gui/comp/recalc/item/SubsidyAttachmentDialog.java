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
package telasi.recutil.gui.comp.recalc.item;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.Operation;
import telasi.recutil.beans.RecalcSubsidyAttachment;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.spinner.SwingNumericSpinner;

public class SubsidyAttachmentDialog extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblSeparator2;
	private Label lblCount;
	private Button btnClose;
	private Button btnOk;
	private Composite composite6;
	private Composite composite5;
	private SwingNumericSpinner spCount;
	private SwingNumericSpinner spAmount;
	private Label lblAmount;
	private Combo cmbUnit;
	private Label lblUnit;
	private Composite composite4;
	private Label lblSubtitle;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;
	private RecalcSubsidyAttachment attachment;
	private Operation operation;
	private Map INDEX_ON_UNIT = new HashMap();
	private boolean approved = false;

	public SubsidyAttachmentDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.horizontalSpacing = 0;
			dialogShellLayout.marginHeight = 0;
			dialogShellLayout.marginWidth = 0;
			dialogShellLayout.verticalSpacing = 0;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.setText(GUIMessages.getMessage("comp.subs_att_dialog.title"));
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
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
					lblTitle.setText(GUIMessages.getMessage("comp.subs_att_dialog.title"));
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
				}
			}
			{
				GridData lblSeparator1LData = new GridData();
				lblSeparator1LData.horizontalAlignment = GridData.FILL;
				lblSeparator1LData.grabExcessHorizontalSpace = true;
				lblSeparator1 = new Label(dialogShell, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator1.setLayoutData(lblSeparator1LData);
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.numColumns = 2;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 48;
					lblImageLData.widthHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/bop/subsidy.png"));
				}
				{
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription = new Label(composite2, SWT.WRAP);
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.subs_att_dialog.descr"));
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.marginTop = 10;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblSubtitle = new Label(composite3, SWT.NONE);
					lblSubtitle.setText(GUIMessages.getMessage("comp.subs_att_dialog.subtitle"));
					GridData lblSubtitleLData = new GridData();
					lblSubtitleLData.horizontalAlignment = GridData.FILL;
					lblSubtitleLData.grabExcessHorizontalSpace = true;
					lblSubtitleLData.verticalAlignment = GridData.FILL;
					lblSubtitleLData.grabExcessVerticalSpace = true;
					lblSubtitle.setLayoutData(lblSubtitleLData);
					lblSubtitle.setFont(GUIUtils.createSubtitleFont(lblSubtitle
							.getFont()));
				}
			}
			{
				GridData lblSeparator2LData = new GridData();
				lblSeparator2LData.horizontalAlignment = GridData.FILL;
				lblSeparator2 = new Label(dialogShell, SWT.SEPARATOR
						| SWT.HORIZONTAL);
				lblSeparator2.setLayoutData(lblSeparator2LData);
			}
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				composite4Layout.marginWidth = 10;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					lblUnit = new Label(composite4, SWT.NONE);
					lblUnit
							.setText(GUIMessages
									.getMessage("comp.general.unit"));
				}
				{
					GridData cmbUnitLData = new GridData();
					cmbUnitLData.widthHint = 150;
					cmbUnit = new Combo(composite4, SWT.READ_ONLY);
					cmbUnit.setLayoutData(cmbUnitLData);
					cmbUnit.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onUnitChange();
						}
					});
				}
				{
					lblAmount = new Label(composite4, SWT.NONE);
					lblAmount.setText(GUIMessages
							.getMessage("comp.general.amount"));
				}
				{
					GridData spAmountLData = new GridData();
					spAmountLData.widthHint = 150;
					spAmount = new SwingNumericSpinner(composite4, SWT.NONE);
					spAmount.setMask("#,###.#####");
					spAmount.setLayoutData(spAmountLData);
					((GridLayout) spAmount.getLayout()).marginWidth = 0;
					((GridLayout) spAmount.getLayout()).marginHeight = 0;
					((GridLayout) spAmount.getLayout()).horizontalSpacing = 0;
					((GridLayout) spAmount.getLayout()).verticalSpacing = 0;
				}
				{
					lblCount = new Label(composite4, SWT.NONE);
					lblCount.setText(GUIMessages
							.getMessage("comp.general.count"));
				}
				{
					GridData spCountLData = new GridData();
					spCountLData.widthHint = 150;
					spCount = new SwingNumericSpinner(composite4, SWT.NONE);
					spCount.setMask("#,###");
					spCount.setLayoutData(spCountLData);
				}
			}
			{
				composite5 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite5Layout = new GridLayout();
				composite5Layout.makeColumnsEqualWidth = true;
				GridData composite5LData = new GridData();
				composite5LData.horizontalAlignment = GridData.FILL;
				composite5LData.grabExcessHorizontalSpace = true;
				composite5LData.verticalAlignment = GridData.FILL;
				composite5LData.grabExcessVerticalSpace = true;
				composite5.setLayoutData(composite5LData);
				composite5.setLayout(composite5Layout);
			}
			{
				composite6 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite6Layout = new GridLayout();
				composite6Layout.marginWidth = 10;
				composite6Layout.marginHeight = 10;
				composite6Layout.numColumns = 2;
				GridData composite6LData = new GridData();
				composite6LData.horizontalAlignment = GridData.FILL;
				composite6LData.grabExcessHorizontalSpace = true;
				composite6.setLayoutData(composite6LData);
				composite6.setLayout(composite6Layout);
				{
					btnOk = new Button(composite6, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.widthHint = 75;
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText("OK");
					btnOk.getShell().setDefaultButton(btnOk);
					btnOk.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onOk();
						}
					});
				}
				{
					btnClose = new Button(composite6, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText("Close");
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onClose();
						}
					});
				}
			}
			// $protect>>$
			//dialogShell.setSize(500, 400);
			dialogShell.pack();
			GUIUtils.centerShell(dialogShell);
			reset();
			// $protect<<$

			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RecalcSubsidyAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(RecalcSubsidyAttachment attachment) {
		this.attachment = attachment;
	}

	@SuppressWarnings("unchecked")
	private void reset() {
		if (attachment == null || operation == null
				|| operation.getSubsidyAttachment() == null) {
			cmbUnit.setEnabled(false);
			spAmount.setEnabled(false);
			spCount.setEnabled(false);
		} else {
			int selectionIndex = 0;
			int[] units = operation.getSubsidyAttachment().getUnits();
			for (int i = 0; i < units.length; i++) {
				int unit = units[i];
				if (unit == attachment.getUnit()) {
					selectionIndex = i;
				}
				INDEX_ON_UNIT.put(new Integer(i), new Integer(unit));
				cmbUnit.add(GUIUtils.getUnitName(unit));
			}
			cmbUnit.select(selectionIndex);
			spAmount.setValue(new Double(attachment.getAmount()));
			spCount.setValue(new Integer(attachment.getCount()));
		}
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public boolean isApproved() {
		return approved;
	}

	private void onOk() {

		spAmount.apply();
		spCount.apply();

		int unit = ((Integer) INDEX_ON_UNIT.get(new Integer(cmbUnit
				.getSelectionIndex()))).intValue();
		double value = ((Number) spAmount.getValue()).doubleValue();
		int count = ((Number) spCount.getValue()).intValue();

		attachment = new RecalcSubsidyAttachment();
		attachment.setUnit(unit);
		attachment.setAmount(value);
		attachment.setCount(count);

		approved = true;
		dialogShell.dispose();
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	private void onUnitChange() {
		int newUnit = ((Integer) INDEX_ON_UNIT.get(new Integer(cmbUnit
				.getSelectionIndex()))).intValue();
		double amount = 0;
		if (operation != null && operation.getSubsidyAttachment() != null) {
			int[] units = operation.getSubsidyAttachment().getUnits();
			double[] amounts = operation.getSubsidyAttachment().getAmounts();
			for (int i = 0; i < units.length; i++) {
				int unit = units[i];
				if (newUnit == unit) {
					amount = amounts[i];
					break;
				}
			}
		}
		spAmount.setValue(new Double(amount));
		spCount.setValue(new Integer(1));
	}

}
