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
package telasi.recutil.gui.comp.recalc.inscp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcInstCp;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.calendar.SWTDatePicker;
import telasi.recutil.gui.widgets.spinner.SwingNumericSpinner;
import telasi.recutil.service.eclipse.recalc.RecalcInstcpInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcInstcpUpdateRequest;

@SuppressWarnings("unchecked")
public class InstcpProperties extends Dialog {

	public static List RECALC_OPTIONS = new ArrayList();

	static {
		RECALC_OPTIONS.add(new Integer(RecalcInstCp.AVERAGE_DAY_CHARGE));
		RECALC_OPTIONS.add(new Integer(RecalcInstCp.NORMALIZE_ON_30_DAYS));
		RECALC_OPTIONS.add(new Integer(RecalcInstCp.NORMALIZE_ON_PREV_MONTH));
		RECALC_OPTIONS.add(new Integer(
				RecalcInstCp.NORM_ON_PRV_MONTH_RND_ON_3_DAYS));
	}

	private Shell dialogShell;
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Combo cmbUnit;
	private Label lblUnit;
	private Composite composite9;
	private Label lblSubtitle2;
	private Composite composite8;
	private Button button1;
	private Button btnClose;
	private Button btnOk;
	private Composite composite13;
	private Label lblError;
	private Composite composite12;
	private SwingNumericSpinner spAmount1;
	private Composite composite10;
	private Label lblAmoun;
	private Label label2;
	private Button button2;
	private SWTDatePicker pkEndDate;
	private Composite composite7;
	private Label lblEndDate;
	private SWTDatePicker pkStartDate;
	private Composite composite6;
	private Label lblStartDate;
	private Composite composite5;
	private Composite composite4;
	private Label label1;
	private Label lblSubtitle1;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;
	private boolean isNewMode;
	private boolean approved = false;
	private Map INDEX_ON_UNIT = new HashMap();
	private Map UNIT_ON_INDEX = new HashMap();
	private RecalcInstCp item;

	// private Button btnAvearageWizard;

	private Recalc recalculation;

	private int sequence = -1;

	private Button btnReset;

	public InstcpProperties(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL | SWT.RESIZE);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.horizontalSpacing = 0;
			// dialogShellLayout.marginHeight = 0;
			// dialogShellLayout.marginWidth = 0;
			dialogShellLayout.verticalSpacing = 0;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			// dialogShell.setSize(550, 450);
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.marginTop = 0;
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
					lblTitle.setText(GUIMessages
							.getMessage("comp.instcp_properties.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
				}
			}
			{
				GridData lblSeparator1LData = new GridData();
				lblSeparator1LData.horizontalAlignment = GridData.FILL;
				lblSeparator1LData.grabExcessHorizontalSpace = true;
				lblSeparator1 = new Label(dialogShell, SWT.SEPARATOR
						| SWT.HORIZONTAL);
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
					lblImage
							.setImage(Plugin.getImage("icons/48x48/energy.png"));
				}
				{
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription = new Label(composite2, SWT.WRAP);
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages
							.getMessage("comp.instcp_properties.descr"));
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
					GridData lblSubtitle1LData = new GridData();
					lblSubtitle1LData.horizontalAlignment = GridData.FILL;
					lblSubtitle1LData.grabExcessHorizontalSpace = true;
					lblSubtitle1 = new Label(composite3, SWT.NONE);
					lblSubtitle1.setLayoutData(lblSubtitle1LData);
					lblSubtitle1.setText(GUIMessages
							.getMessage("comp.instcp_properties.subtitle1"));
					lblSubtitle1.setFont(GUIUtils
							.createSubtitleFont(lblSubtitle1.getFont()));
				}
			}
			{
				label1 = new Label(dialogShell, SWT.SEPARATOR | SWT.HORIZONTAL);
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.grabExcessHorizontalSpace = true;
				label1.setLayoutData(label1LData);
			}
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.horizontalSpacing = 0;
				composite4Layout.marginHeight = 0;
				composite4Layout.marginWidth = 0;
				composite4Layout.verticalSpacing = 0;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					composite5 = new Composite(composite4, SWT.NONE);
					GridLayout composite5Layout = new GridLayout();
					composite5Layout.numColumns = 2;
					GridData composite5LData = new GridData();
					composite5LData.horizontalAlignment = GridData.FILL;
					composite5LData.grabExcessHorizontalSpace = true;
					composite5.setLayoutData(composite5LData);
					composite5.setLayout(composite5Layout);
					{
						lblStartDate = new Label(composite5, SWT.NONE);
						lblStartDate.setText(GUIMessages
								.getMessage("comp.general.start_date"));
					}
					{
						composite6 = new Composite(composite5, SWT.NONE);
						GridLayout composite6Layout = new GridLayout();
						composite6Layout.marginHeight = 0;
						composite6Layout.numColumns = 2;
						composite6Layout.marginWidth = 0;
						GridData composite6LData = new GridData();
						composite6LData.horizontalAlignment = GridData.FILL;
						composite6LData.grabExcessHorizontalSpace = true;
						composite6.setLayoutData(composite6LData);
						composite6.setLayout(composite6Layout);
						{
							GridData spStartDateLData = new GridData();
							spStartDateLData.widthHint = 150;
							pkStartDate = new SWTDatePicker(composite6,
									SWT.BORDER);
							pkStartDate.setLayoutData(spStartDateLData);
						}
						{
							button1 = new Button(composite6, SWT.PUSH
									| SWT.CENTER);
							button1.setText(GUIMessages
									.getMessage("comp.general.clear"));
							button1
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent e) {
											pkStartDate.setDate(null);
										}
									});
						}
					}
					{
						lblEndDate = new Label(composite5, SWT.NONE);
						lblEndDate.setText(GUIMessages
								.getMessage("comp.general.end_date"));
					}
					{
						composite7 = new Composite(composite5, SWT.NONE);
						GridLayout composite7Layout = new GridLayout();
						composite7Layout.numColumns = 2;
						composite7Layout.marginWidth = 0;
						composite7Layout.marginHeight = 0;
						GridData composite7LData = new GridData();
						composite7LData.horizontalAlignment = GridData.FILL;
						composite7LData.grabExcessHorizontalSpace = true;
						composite7.setLayoutData(composite7LData);
						composite7.setLayout(composite7Layout);
						{
							GridData pkEndDateLData = new GridData();
							pkEndDateLData.widthHint = 150;
							pkEndDate = new SWTDatePicker(composite7,
									SWT.BORDER);
							pkEndDate.setLayoutData(pkEndDateLData);
						}
						{
							button2 = new Button(composite7, SWT.PUSH
									| SWT.CENTER);
							button2.setText(GUIMessages
									.getMessage("comp.general.clear"));
							button2
									.addSelectionListener(new SelectionAdapter() {
										public void widgetSelected(
												SelectionEvent e) {
											pkEndDate.setDate(null);
										}
									});
						}
					}
				}
			}
			{
				composite8 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite8Layout = new GridLayout();
				composite8Layout.marginTop = 10;
				GridData composite8LData = new GridData();
				composite8LData.horizontalAlignment = GridData.FILL;
				composite8LData.grabExcessHorizontalSpace = true;
				composite8.setLayoutData(composite8LData);
				composite8.setLayout(composite8Layout);
				{
					lblSubtitle2 = new Label(composite8, SWT.NONE);
					lblSubtitle2.setText(GUIMessages
							.getMessage("comp.instcp_properties.subtitle2"));
					lblSubtitle2.setFont(GUIUtils
							.createSubtitleFont(lblSubtitle2.getFont()));
				}
			}
			{
				GridData label2LData = new GridData();
				label2LData.horizontalAlignment = GridData.FILL;
				label2LData.grabExcessHorizontalSpace = true;
				label2 = new Label(dialogShell, SWT.SEPARATOR | SWT.HORIZONTAL);
				label2.setLayoutData(label2LData);
			}
			{
				composite9 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite9Layout = new GridLayout();
				composite9Layout.numColumns = 2;
				GridData composite9LData = new GridData();
				composite9LData.horizontalAlignment = GridData.FILL;
				composite9LData.grabExcessHorizontalSpace = true;
				composite9.setLayoutData(composite9LData);
				composite9.setLayout(composite9Layout);
				{
					lblUnit = new Label(composite9, SWT.NONE);
					lblUnit.setText(GUIMessages
							.getMessage("comp.general.recalc_method"));
				}
				{
					GridData cmbUnitLData = new GridData();
					cmbUnitLData.horizontalAlignment = GridData.FILL;
					cmbUnitLData.grabExcessHorizontalSpace = true;
					cmbUnit = new Combo(composite9, SWT.READ_ONLY);
					cmbUnit.setLayoutData(cmbUnitLData);
				}
				{
					/*
					 * btnAvearageWizard = new Button(composite9, SWT.PUSH |
					 * SWT.CENTER);
					 * btnAvearageWizard.setImage(Plugin.getImage("icons/22x22/wizard.png"));
					 * btnAvearageWizard.setToolTipText(GUIMessages.getMessage("comp.instcp_properties.avearage_charge_wizard"));
					 * btnAvearageWizard.addSelectionListener(new
					 * SelectionAdapter() { public void
					 * widgetSelected(SelectionEvent arg0) { //onAvearage(); }
					 * });
					 */
				}
				{
					lblAmoun = new Label(composite9, SWT.NONE);
					lblAmoun.setText(GUIMessages
							.getMessage("comp.general.value"));
				}
				{
					composite10 = new Composite(composite9, SWT.NONE);
					GridLayout composite10Layout = new GridLayout();
					composite10Layout.numColumns = 2;
					composite10Layout.marginHeight = 0;
					composite10Layout.marginWidth = 0;
					GridData composite10LData = new GridData();
					composite10LData.horizontalAlignment = GridData.FILL;
					composite10LData.grabExcessHorizontalSpace = true;
					composite10.setLayoutData(composite10LData);
					composite10.setLayout(composite10Layout);
					{
						GridData spAmount1LData = new GridData();
						spAmount1LData.widthHint = 100;
						spAmount1 = new SwingNumericSpinner(composite10,
								SWT.NONE);
						spAmount1.setLayoutData(spAmount1LData);
						spAmount1.setMask("#,###.#####");
						((org.eclipse.swt.layout.GridLayout) spAmount1
								.getLayout()).marginWidth = 0;
						((org.eclipse.swt.layout.GridLayout) spAmount1
								.getLayout()).marginHeight = 0;
						((org.eclipse.swt.layout.GridLayout) spAmount1
								.getLayout()).horizontalSpacing = 0;
						((org.eclipse.swt.layout.GridLayout) spAmount1
								.getLayout()).verticalSpacing = 0;
					}
				}
			}
			{
				composite12 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite12Layout = new GridLayout();
				composite12Layout.makeColumnsEqualWidth = true;
				GridData composite12LData = new GridData();
				composite12LData.horizontalAlignment = GridData.FILL;
				composite12LData.grabExcessHorizontalSpace = true;
				composite12LData.verticalAlignment = GridData.FILL;
				composite12LData.grabExcessVerticalSpace = true;
				composite12.setLayoutData(composite12LData);
				composite12.setLayout(composite12Layout);
				{
					lblError = new Label(composite12, SWT.WRAP);
					GridData lblErrorLData = new GridData();
					lblErrorLData.horizontalAlignment = GridData.FILL;
					lblErrorLData.grabExcessHorizontalSpace = true;
					lblErrorLData.verticalAlignment = GridData.FILL;
					lblErrorLData.grabExcessVerticalSpace = true;
					lblError.setLayoutData(lblErrorLData);
					lblError.setForeground(lblError.getDisplay()
							.getSystemColor(SWT.COLOR_RED));
				}
			}
			{
				composite13 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite13Layout = new GridLayout();
				// composite13Layout.marginWidth = 5;
				// composite13Layout.marginHeight = 5;
				composite13Layout.numColumns = 3;
				GridData composite13LData = new GridData();
				composite13LData.horizontalAlignment = GridData.FILL;
				composite13LData.grabExcessHorizontalSpace = true;
				composite13.setLayoutData(composite13LData);
				composite13.setLayout(composite13Layout);
				{
					btnReset = new Button(composite13, SWT.PUSH | SWT.CENTER);
					GridData btnResetLData = new GridData();
					btnResetLData.widthHint = 75;
					btnReset.setLayoutData(btnResetLData);
					btnReset.setText(GUIMessages
							.getMessage("comp.general.reset"));
					btnReset.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							reset();
						}

					});
				}
				{
					btnOk = new Button(composite13, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
					btnOkLData.widthHint = 75;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
					btnOk.getShell().setDefaultButton(btnOk);
					btnOk.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onOk();
						}

					});
				}
				{
					btnClose = new Button(composite13, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.widthHint = 75;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText(GUIMessages
							.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onClose();
						}

					});
				}
			}
			// $protect>>$
			createCombo();
			reset();
			dialogShell.pack();
			//dialogShell.setSize(500, 450);
			GUIUtils.centerShell(dialogShell);
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

	private void createCombo() {
		for (int i = 0; i < RECALC_OPTIONS.size(); i++) {
			int method = ((Integer) RECALC_OPTIONS.get(i)).intValue();
			UNIT_ON_INDEX.put(new Integer(method), new Integer(i));
			INDEX_ON_UNIT.put(new Integer(i), new Integer(method));
			cmbUnit.add(GUIUtils.getInstcpRecalculationMethodName(method));
		}

		// add combo listener
		cmbUnit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				// int method = ((Integer) INDEX_ON_UNIT.get(new
				// Integer(cmbUnit.getSelectionIndex()))).intValue();
				// btnAvearageWizard.setEnabled(method ==
				// IRecalcInstCp.AVERAGE_DAY_CHARGE);
			}
		});

	}

	/*
	 * private void onAvearage() {
	 * 
	 * IRecalc recalc = this.recalculation; if (recalc == null) { if (item ==
	 * null) { return; } recalc = item.getDetails().getRecalc(); } int method =
	 * ((Integer) INDEX_ON_UNIT.get(new
	 * Integer(cmbUnit.getSelectionIndex()))).intValue(); if (method !=
	 * IRecalcInstCp.AVERAGE_DAY_CHARGE) { return; }
	 * 
	 * AvearageChangeWizard wizard = new AvearageChangeWizard(dialogShell,
	 * SWT.NONE); wizard.setRecalc(recalc); wizard.open();
	 *  }
	 */

	private void reset() {
		if (item == null && recalculation != null) {
			pkStartDate.setDate(null);
			pkEndDate.setDate(null);
			isNewMode = true;
			spAmount1.setValue(new Double(.0f));
			dialogShell.setText(GUIMessages
					.getMessage("comp.instcp_properties.new_title"));
			cmbUnit.select(0);
		} else if (item != null) {
			int index = ((Integer) UNIT_ON_INDEX.get(new Integer(item
					.getOption()))).intValue();
			cmbUnit.select(index);
			pkStartDate.setDate(item.getStartDate());
			pkEndDate.setDate(item.getEndDate());
			spAmount1.setValue(new Double(item.getAmount()));
			isNewMode = false;
			dialogShell.setText(GUIMessages
					.getMessage("comp.instcp_properties.edit_title"));
		} else {
			pkStartDate.setDate(null);
			pkEndDate.setDate(null);
			isNewMode = true; // ????? no matter what mode is here
			dialogShell.setText("WARNING: illegal mode");
			btnOk.setEnabled(false);
		}
		btnReset.setVisible(!isNewMode);
	}

	public boolean isApproved() {
		return approved;
	}

	private void onOk() {
		lblError.setText("");
		if (item == null && recalculation == null) {
			return;
		}

		Date d1 = pkStartDate.getDate();
		Date d2 = pkEndDate.getDate();
		int method = ((Integer) INDEX_ON_UNIT.get(new Integer(cmbUnit
				.getSelectionIndex()))).intValue();
		spAmount1.apply();
		double value = ((Number) spAmount1.getValue()).doubleValue();

		RecalcInstCp newItem = new RecalcInstCp();
		newItem.setId(isNewMode ? -1L : item.getId());
		newItem.setStartDate(d1);
		newItem.setEndDate(d2);
		newItem.setOption(method);
		newItem.setAmount(value);
		newItem.setDetails(isNewMode ? recalculation.getDetails() : item
				.getDetails());

		if (!Application.validateConnection()) {
			return;
		}

		try {
			if (isNewMode) {
				RecalcInstcpInsertRequest request = new RecalcInstcpInsertRequest(
						Application.USER_NAME, Application.PASSWORD);
				request.setItem(newItem);
				request.setSequence(sequence);
				DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
						.processRequest(request);
				RecalcInstcpInsertRequest callback = (RecalcInstcpInsertRequest) resp
						.getRequest();
				newItem.setId(callback.getId());
				this.item = newItem;
				approved = true;
				dialogShell.dispose();
			} else {
				RecalcInstcpUpdateRequest request = new RecalcInstcpUpdateRequest(
						Application.USER_NAME, Application.PASSWORD);
				request.setItem(newItem);
				DefaultRecutilClient.processRequest(request);
				this.item = newItem;
				approved = true;
				dialogShell.dispose();
			}
		} catch (Exception ex) {
			approved = false;
			lblError.setText(ex.toString());
		}
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	public Recalc getRecalculation() {
		return recalculation;
	}

	public void setRecalculation(Recalc recalculation) {
		this.recalculation = recalculation;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public RecalcInstCp getItem() {
		return item;
	}

	public void setItem(RecalcInstCp item) {
		this.item = item;
	}

}
