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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import org.eclipse.swt.widgets.Label;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.RecalcSubsidyAttachment;
import telasi.recutil.gui.comp.bop.OperationPicker;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.calendar.SWTDatePicker;
import telasi.recutil.gui.widgets.spinner.SwingNumericSpinner;
import telasi.recutil.utils.CoreUtils;

public class RecalcItemOperationPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblSeparator2;
	private Label lblReading;
	private Composite composite9;
	private Composite composite8;
	private Composite composite7;
	private Label lblSeparator3;
	private Label lblSubtitle2;
	private Composite composite6;
	private Label lblEnterDate;
	//private Composite composite14;
	private Composite composite12;
	private Composite composite11;
	private Label lblSeparator4;
	private Label lblSubtitle3;
	private Composite composite10;
	private Label lblBalance;
	private Label lblGel;
	private Label lblKwh;
	private Composite composite5;
	private Label lblItemDate;
	private Label lblSubtitle1;
	private Composite composite4;
	private Label lblOperation;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;
	private Combo cmbCalcHint;
	private Label lblCalcHint;
	private Composite composite15;
	// data widgets
	private OperationPicker pkOperation;
	private SWTDatePicker pkItemDate;
	private SWTDatePicker pkEnterDate;
	private Button chkCycle;
	private SwingNumericSpinner spReading;
	private SwingNumericSpinner spGel;
	private SwingNumericSpinner spKwh;
	private SwingNumericSpinner spBalance;
	private Button chkRecalcSubsidy;
	private Button btnSubsRecalcDetails;
	private RecalcSubsidyAttachment attachment;
	private Operation currentOperation;
	// item
	private RecalcItem item;

	public RecalcItemOperationPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initCombo();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
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
					lblTitle.setText(GUIMessages
							.getMessage("comp.recalc_item_oper_pane.title"));
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
					lblImage.setImage(Plugin.getImage("icons/48x48/oper.png"));
				}
				{
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription = new Label(composite2, SWT.WRAP);
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription
							.setText(GUIMessages
									.getMessage("comp.recalc_item_oper_pane.description"));
				}
			}
			{
				composite4 = new Composite(this, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.makeColumnsEqualWidth = true;
				composite4Layout.marginTop = 10;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					lblSubtitle1 = new Label(composite4, SWT.NONE);
					lblSubtitle1.setText(GUIMessages
							.getMessage("comp.general.operation"));
					lblSubtitle1.setFont(GUIUtils
							.createSubtitleFont(lblSubtitle1.getFont()));
				}
			}
			{
				GridData lblSeparator2LData = new GridData();
				lblSeparator2LData.horizontalAlignment = GridData.FILL;
				lblSeparator2LData.grabExcessHorizontalSpace = true;
				lblSeparator2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator2.setLayoutData(lblSeparator2LData);
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblOperation = new Label(composite3, SWT.NONE);
					lblOperation.setText(GUIMessages
							.getMessage("comp.general.operation"));
				}
				{
					GridData operationPicker1LData = new GridData();
					operationPicker1LData.horizontalAlignment = GridData.FILL;
					operationPicker1LData.grabExcessHorizontalSpace = true;
					pkOperation = new OperationPicker(composite3, SWT.BORDER);
					pkOperation.setLayoutData(operationPicker1LData);
					pkOperation
							.addPropertyChangeListener(new PropertyChangeListener() {
								public void propertyChange(
										PropertyChangeEvent evt) {
									if ("operation".equals(evt
											.getPropertyName())) {
										onOperationChange();
									}
								}
							});
				}
				{
					lblItemDate = new Label(composite3, SWT.NONE);
					lblItemDate.setText(GUIMessages
							.getMessage("comp.general.itemdate"));
				}
				{
					composite5 = new Composite(composite3, SWT.NONE);
					GridLayout composite5Layout = new GridLayout();
					composite5Layout.numColumns = 2;
					composite5Layout.marginHeight = 0;
					composite5Layout.marginWidth = 0;
					composite5Layout.verticalSpacing = 0;
					composite5Layout.horizontalSpacing = 30;
					GridData composite5LData = new GridData();
					composite5LData.horizontalAlignment = GridData.FILL;
					composite5LData.grabExcessHorizontalSpace = true;
					composite5.setLayoutData(composite5LData);
					composite5.setLayout(composite5Layout);
					{
						GridData pkItemDateLData = new GridData();
						pkItemDateLData.widthHint = 150;
						pkItemDate = new SWTDatePicker(composite5, SWT.BORDER);
						pkItemDate.setLayoutData(pkItemDateLData);
					}
					{
						chkCycle = new Button(composite5, SWT.CHECK | SWT.LEFT);
						GridData chkCycleLData = new GridData();
						chkCycleLData.horizontalAlignment = GridData.FILL;
						chkCycleLData.grabExcessHorizontalSpace = true;
						chkCycle.setLayoutData(chkCycleLData);
						chkCycle.setText(GUIMessages
								.getMessage("comp.general.cycle_operation"));
					}
				}
				{
					lblEnterDate = new Label(composite3, SWT.NONE);
					lblEnterDate.setText(GUIMessages
							.getMessage("comp.general.enterdate"));
				}
				{
					GridData pkEnterDateLData = new GridData();
					pkEnterDateLData.widthHint = 150;
					pkEnterDate = new SWTDatePicker(composite3, SWT.BORDER);
					pkEnterDate.setLayoutData(pkEnterDateLData);
				}
			}
			{
				composite6 = new Composite(this, SWT.NONE);
				GridLayout composite6Layout = new GridLayout();
				composite6Layout.makeColumnsEqualWidth = true;
				composite6Layout.marginTop = 10;
				GridData composite6LData = new GridData();
				composite6LData.horizontalAlignment = GridData.FILL;
				composite6LData.grabExcessHorizontalSpace = true;
				composite6.setLayoutData(composite6LData);
				composite6.setLayout(composite6Layout);
				{
					lblSubtitle2 = new Label(composite6, SWT.NONE);
					GridData lblSubtitle2LData = new GridData();
					lblSubtitle2LData.horizontalAlignment = GridData.FILL;
					lblSubtitle2LData.grabExcessHorizontalSpace = true;
					lblSubtitle2.setLayoutData(lblSubtitle2LData);
					lblSubtitle2.setText(GUIMessages
							.getMessage("comp.general.charge"));
					lblSubtitle2.setFont(GUIUtils
							.createSubtitleFont(lblSubtitle2.getFont()));
				}
			}
			{
				GridData lblSeparator3LData = new GridData();
				lblSeparator3LData.horizontalAlignment = GridData.FILL;
				lblSeparator3LData.grabExcessHorizontalSpace = true;
				lblSeparator3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator3.setLayoutData(lblSeparator3LData);
			}
			{
				composite7 = new Composite(this, SWT.NONE);
				GridLayout composite7Layout = new GridLayout();
				composite7Layout.numColumns = 2;
				GridData composite7LData = new GridData();
				composite7LData.horizontalAlignment = GridData.FILL;
				composite7LData.grabExcessHorizontalSpace = true;
				composite7.setLayoutData(composite7LData);
				composite7.setLayout(composite7Layout);
				{
					composite8 = new Composite(composite7, SWT.NONE);
					GridLayout composite8Layout = new GridLayout();
					composite8Layout.marginHeight = 0;
					composite8Layout.marginWidth = 0;
					composite8Layout.numColumns = 2;
					GridData composite8LData = new GridData();
					composite8LData.horizontalAlignment = GridData.FILL;
					composite8LData.grabExcessHorizontalSpace = true;
					composite8.setLayoutData(composite8LData);
					composite8.setLayout(composite8Layout);
					{
						lblReading = new Label(composite8, SWT.NONE);
						lblReading.setText(GUIMessages
								.getMessage("comp.general.reading"));
					}
					{
						GridData spReadingLData = new GridData();
						spReadingLData.widthHint = 120;
						spReading = new SwingNumericSpinner(composite8,
								SWT.NONE);
						spReading.setLayoutData(spReadingLData);
					}
					{
						lblKwh = new Label(composite8, SWT.NONE);
						lblKwh.setText(GUIMessages
								.getMessage("comp.general.kwh"));
					}
					{
						GridData spKwhLData = new GridData();
						spKwhLData.widthHint = 120;
						spKwh = new SwingNumericSpinner(composite8, SWT.NONE);
						spKwh.setLayoutData(spKwhLData);
					}
				}
				{
					composite9 = new Composite(composite7, SWT.NONE);
					GridLayout composite9Layout = new GridLayout();
					composite9Layout.numColumns = 2;
					composite9Layout.marginWidth = 0;
					composite9Layout.marginHeight = 0;
					GridData composite9LData = new GridData();
					composite9LData.horizontalAlignment = GridData.FILL;
					composite9LData.grabExcessHorizontalSpace = true;
					composite9.setLayoutData(composite9LData);
					composite9.setLayout(composite9Layout);
					{
						lblGel = new Label(composite9, SWT.NONE);
						lblGel.setText(GUIMessages
								.getMessage("comp.general.gel"));
					}
					{
						GridData spGelLData = new GridData();
						spGelLData.widthHint = 120;
						spGel = new SwingNumericSpinner(composite9, SWT.NONE);
						spGel.setLayoutData(spGelLData);
						((org.eclipse.swt.layout.GridLayout) spGel.getLayout()).marginWidth = 0;
						((org.eclipse.swt.layout.GridLayout) spGel.getLayout()).marginHeight = 0;
						((org.eclipse.swt.layout.GridLayout) spGel.getLayout()).horizontalSpacing = 0;
						((org.eclipse.swt.layout.GridLayout) spGel.getLayout()).verticalSpacing = 0;
					}
					{
						lblBalance = new Label(composite9, SWT.NONE);
						lblBalance.setText(GUIMessages
								.getMessage("comp.general.balance"));
					}
					{
						GridData spBalanceLData = new GridData();
						spBalanceLData.widthHint = 120;
						spBalance = new SwingNumericSpinner(composite9,
								SWT.NONE);
						spBalance.setLayoutData(spBalanceLData);
						((org.eclipse.swt.layout.GridLayout) spBalance
								.getLayout()).marginWidth = 0;
						((org.eclipse.swt.layout.GridLayout) spBalance
								.getLayout()).marginHeight = 0;
						((org.eclipse.swt.layout.GridLayout) spBalance
								.getLayout()).horizontalSpacing = 0;
						((org.eclipse.swt.layout.GridLayout) spBalance
								.getLayout()).verticalSpacing = 0;
					}
				}
			}
			{
				composite10 = new Composite(this, SWT.NONE);
				GridLayout composite10Layout = new GridLayout();
				composite10Layout.makeColumnsEqualWidth = true;
				composite10Layout.marginTop = 10;
				GridData composite10LData = new GridData();
				composite10LData.horizontalAlignment = GridData.FILL;
				composite10LData.grabExcessHorizontalSpace = true;
				composite10.setLayoutData(composite10LData);
				composite10.setLayout(composite10Layout);
				{
					lblSubtitle3 = new Label(composite10, SWT.NONE);
					GridData lblSubtitleLData = new GridData();
					lblSubtitleLData.horizontalAlignment = GridData.FILL;
					lblSubtitleLData.grabExcessHorizontalSpace = true;
					lblSubtitle3.setLayoutData(lblSubtitleLData);
					lblSubtitle3
							.setText(GUIMessages
									.getMessage("comp.recalc_item_oper_pane.additional_recalc_options"));
					lblSubtitle3.setFont(GUIUtils
							.createSubtitleFont(lblSubtitle3.getFont()));
				}
			}
			{
				GridData lblSeparator4LData = new GridData();
				lblSeparator4LData.horizontalAlignment = GridData.FILL;
				lblSeparator4LData.grabExcessHorizontalSpace = true;
				lblSeparator4 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				lblSeparator4.setLayoutData(lblSeparator4LData);
			}
			{
				composite11 = new Composite(this, SWT.NONE);
				GridLayout composite11Layout = new GridLayout();
				composite11Layout.makeColumnsEqualWidth = true;
				GridData composite11LData = new GridData();
				composite11LData.horizontalAlignment = GridData.FILL;
				composite11LData.grabExcessHorizontalSpace = true;
				composite11LData.verticalAlignment = GridData.FILL;
				composite11LData.grabExcessVerticalSpace = true;
				composite11.setLayoutData(composite11LData);
				composite11.setLayout(composite11Layout);
				{
					composite15 = new Composite(composite11, SWT.NONE);
					GridLayout composite15Layout = new GridLayout();
					composite15Layout.numColumns = 2;
					composite15Layout.marginHeight = 0;
					composite15Layout.marginWidth = 0;
					GridData composite15LData = new GridData();
					composite15LData.horizontalAlignment = GridData.FILL;
					composite15LData.grabExcessHorizontalSpace = true;
					composite15.setLayoutData(composite15LData);
					composite15.setLayout(composite15Layout);
					{
						lblCalcHint = new Label(composite15, SWT.NONE);
						lblCalcHint.setText(GUIMessages.getMessage("comp.recalc_item_oper_pane.operation_hint"));
					}
					{
						GridData cmbCalcHintLData = new GridData();
						cmbCalcHintLData.horizontalAlignment = GridData.FILL;
						cmbCalcHintLData.grabExcessHorizontalSpace = true;
						cmbCalcHint = new Combo(composite15, SWT.READ_ONLY);
						cmbCalcHint.setLayoutData(cmbCalcHintLData);
					}
				}
				{
					composite12 = new Composite(composite11, SWT.NONE);
					GridLayout composite12Layout = new GridLayout();
					composite12Layout.marginHeight = 0;
					composite12Layout.marginWidth = 0;
					composite12Layout.numColumns = 2;
					GridData composite12LData = new GridData();
					composite12LData.horizontalAlignment = GridData.FILL;
					composite12LData.grabExcessHorizontalSpace = true;
					composite12.setLayoutData(composite12LData);
					composite12.setLayout(composite12Layout);
					{
						chkRecalcSubsidy = new Button(composite12, SWT.CHECK | SWT.LEFT);
						chkRecalcSubsidy.setText(GUIMessages.getMessage("comp.recalc_item_oper_pane.recalc_subsidy_add_charge"));
						chkRecalcSubsidy.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent e) {
										onSubsidyRecalcCheckChange();
									}
								});

					}
					{
						btnSubsRecalcDetails = new Button(composite12, SWT.PUSH | SWT.CENTER);
						btnSubsRecalcDetails.setText(GUIMessages.getMessage("comp.general.details"));
						btnSubsRecalcDetails.addSelectionListener(new SelectionAdapter() {
									public void widgetSelected(SelectionEvent e) {
										onAttachmentEdit();
									}
								});
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<Integer, Integer> ID_ON_INDEX = new HashMap<Integer, Integer>();

	private Map<Integer, Integer> INDEX_ON_ID = new HashMap<Integer, Integer>();

	private void initCombo() {
		cmbCalcHint.add(GUIUtils.getCalculationHintName(RecalcItem.HINT_NONE));
		ID_ON_INDEX.put(RecalcItem.HINT_NONE, 0);
		INDEX_ON_ID.put(0, RecalcItem.HINT_NONE);

		cmbCalcHint.add(GUIUtils.getCalculationHintName(RecalcItem.HINT_PRESERVE_BOTH));
		ID_ON_INDEX.put(RecalcItem.HINT_PRESERVE_BOTH, 1);
		INDEX_ON_ID.put(1, RecalcItem.HINT_PRESERVE_BOTH);

		cmbCalcHint.add(GUIUtils.getCalculationHintName(RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH));
		ID_ON_INDEX.put(RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH, 2);
		INDEX_ON_ID.put(2, RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH);

		cmbCalcHint.add(GUIUtils.getCalculationHintName(RecalcItem.HINT_USE_CONTINUOUS_BY_INSTCP));
		ID_ON_INDEX.put(RecalcItem.HINT_USE_CONTINUOUS_BY_INSTCP, 3);
		INDEX_ON_ID.put(3, RecalcItem.HINT_USE_CONTINUOUS_BY_INSTCP);

		cmbCalcHint.add(GUIUtils.getCalculationHintName(RecalcItem.HINT_FORCE_DISCHARGE));
		ID_ON_INDEX.put(RecalcItem.HINT_FORCE_DISCHARGE, 4);
		INDEX_ON_ID.put(4, RecalcItem.HINT_FORCE_DISCHARGE);

		cmbCalcHint.select(0);
	}

	public RecalcItem getItem() {
		return item;
	}

	public void setItem(RecalcItem item) {
		this.item = item;
		reset();
	}

	private void reset() {
		if (item == null) {
			pkOperation.setOperation(null);
			pkItemDate.setDate(null);
			pkEnterDate.setDate(null);
			chkCycle.setSelection(false);
			spReading.setValue(new Double(0.f));
			spGel.setValue(new Double(0.f));
			spKwh.setValue(new Double(0.f));
			spBalance.setValue(new Double(0.f));
			cmbCalcHint.select(0);
		} else {
			pkOperation.setOperation(item.getOperation());
			currentOperation = item.getOperation();
			pkItemDate.setDate(item.getItemDate());
			pkEnterDate.setDate(item.getEnterDate());
			chkCycle.setSelection(item.getCycle());
			spReading.setValue(new Double(item.getReading()));
			spGel.setValue(new Double(item.getGel()));
			spKwh.setValue(new Double(item.getKwh()));
			spBalance.setValue(new Double(item.getBalance()));
			attachment = item.getSubsidyAttachment();
			cmbCalcHint.select(ID_ON_INDEX.get(item.getCalculationHint()));
		}
		validateSubsidyAttachmentGroup();
		validateChargeGroup(false);
	}

	private void onOperationChange() {

		// operation not changed
		if (currentOperation == null && pkOperation.getOperation() == null) {
			return;
		}
		if (currentOperation != null
				&& pkOperation.getOperation() != null
				&& currentOperation.getId() == pkOperation.getOperation()
						.getId()) {
			return;
		}

		// create subsidy attachment if applicable
		attachment = CoreUtils.createDefaultAttachment(pkOperation
				.getOperation());

		// change previous operation
		currentOperation = pkOperation.getOperation();

		// validate groups
		validateChargeGroup(true);
		validateSubsidyAttachmentGroup();
	}

	private void validateChargeGroup(boolean changeParameters) {

		// "OTHER" operations
		boolean flagOther = item == null ? false
				: item.getStatus() == RecalcItem.OTHER;

		// cycle
		if (flagOther) {
			chkCycle.setEnabled(false);
		} else if (changeParameters) {
			boolean canNotHaveCycle = currentOperation == null ? false
					: currentOperation.getCycleRequiment() == Operation.NOT_REQUIERED_AT_ALL;
			boolean cycleRequired = currentOperation == null ? false
					: currentOperation.getCycleRequiment() == Operation.REQUIERED;
			chkCycle.setEnabled(!canNotHaveCycle && !cycleRequired);
			if (canNotHaveCycle) {
				chkCycle.setSelection(false);
			} else if (cycleRequired) {
				chkCycle.setSelection(true);
			}
		} else {
			chkCycle.setEnabled(true);
		}

		// item and enter dates
		pkItemDate.setEnabled(!flagOther);
		pkEnterDate.setEnabled(!flagOther);

		// balance
		spBalance.setEnabled(false);

		// reading, kwh, gel
		boolean lockReading = currentOperation.getReadingRequiment() == Operation.NOT_REQUIERED_AT_ALL
				|| flagOther;
		boolean lockKwh = currentOperation.getKwhRequiment() == Operation.NOT_REQUIERED_AT_ALL
				|| flagOther;
		boolean lockGel = currentOperation.getGelRequiment() == Operation.NOT_REQUIERED_AT_ALL
				|| flagOther;
		spReading.setEnabled(!lockReading);
		spKwh.setEnabled(!lockKwh);
		spGel.setEnabled(!lockGel);
		if (changeParameters) {
			if (lockReading) {
				spReading.setValue(new Double(0.f));
			}
			if (lockKwh) {
				spKwh.setValue(new Double(0.f));
			}
			if (lockGel) {
				spGel.setValue(new Double(0.f));
			}
		}

	}

	private void validateSubsidyAttachmentGroup() {

		if (item == null) {
			chkRecalcSubsidy.setEnabled(false);
			btnSubsRecalcDetails.setEnabled(false);
		} else {
			boolean mayHaveAttachment = currentOperation.getSubsidyAttachment() != null;
			if (!mayHaveAttachment) {
				attachment = null;
			}
			chkRecalcSubsidy.setEnabled(mayHaveAttachment);
			chkRecalcSubsidy.setSelection(mayHaveAttachment
					&& attachment != null);
			btnSubsRecalcDetails.setEnabled(chkRecalcSubsidy.getSelection());
		}

	}

	private void onAttachmentEdit() {
		SubsidyAttachmentDialog dialog = new SubsidyAttachmentDialog(
				getShell(), SWT.NONE);
		dialog.setAttachment(attachment);
		dialog.setOperation(pkOperation.getOperation());
		dialog.open();
		if (dialog.isApproved()) {
			attachment = dialog.getAttachment();
		}
	}

	private void onSubsidyRecalcCheckChange() {
		boolean recalc = chkRecalcSubsidy.getSelection();
		attachment = recalc ? CoreUtils
				.createDefaultAttachment(currentOperation) : null;
		validateSubsidyAttachmentGroup();
	}

	// values

	public Operation getOperation() {
		return pkOperation.getOperation();
	}

	public Date getItemDate() {
		return pkItemDate.getDate();
	}

	public Date getEnterDate() {
		return pkEnterDate.getDate();
	}

	public double getGel() {
		spGel.apply();
		return ((Number) spGel.getValue()).doubleValue();
	}

	public double getKwh() {
		spKwh.apply();
		return ((Number) spKwh.getValue()).doubleValue();
	}

	public double getReading() {
		spReading.apply();
		return ((Number) spReading.getValue()).doubleValue();
	}

	public int getCalculationHint() {
		return INDEX_ON_ID.get(cmbCalcHint.getSelectionIndex());
	}

	public RecalcSubsidyAttachment getAttachment() {
		return attachment;
	}

	public boolean getCycle() {
		return chkCycle.getSelection();
	}

}
