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
package telasi.recutil.gui.comp.avrgcharge;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Meter;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.calendar.SWTDatePicker;
import telasi.recutil.gui.widgets.spinner.SwingNumericSpinner;

public class AvrgChargeIntervalProperties extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Label lblDate1;
	private Label lblDate2;
	private SwingNumericSpinner spCoeff;
	private Composite composite5;
	private Button btnClose;
	private Button btnOk;
	private Composite composite4;
	private Label lblError;
	private SwingNumericSpinner spAcceleration;
	private Label lblAcceleration;
	private SwingNumericSpinner spDigits;
	private Label lblDigits;
	private Label lblCoeff;
	private Composite composite3;
	private Label label2;
	private Label label1;
	private Label lblDescription;
	private Label lblImage;
	private Composite composite2;
	private SwingNumericSpinner spReading2;
	private Label lblR2;
	private SwingNumericSpinner spReading1;
	private Label lblR1;
	private SWTDatePicker pkDate2;
	private SWTDatePicker pkDate1;
	private RecalcInterval interval;

	public AvrgChargeIntervalProperties(Shell parent, int style) {
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
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.simple_avrg_charge_prop.descr"));
				}
			}
			{
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.grabExcessHorizontalSpace = true;
				label1 = new Label(dialogShell, SWT.SEPARATOR | SWT.HORIZONTAL);
				label1.setLayoutData(label1LData);
			}
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 4;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblDate1 = new Label(composite1, SWT.NONE);
					lblDate1.setText(GUIMessages.getMessage("comp.general.start_date"));
				}
				{
					GridData pkDate1LData = new GridData();
					pkDate1LData.widthHint = 150;
					pkDate1 = new SWTDatePicker(composite1, SWT.BORDER);
					pkDate1.setLayoutData(pkDate1LData);
				}
				{
					lblR1 = new Label(composite1, SWT.NONE);
					lblR1.setText(GUIMessages.getMessage("comp.simple_avrg_charge.r1"));
				}
				{
					GridData spReading1LData = new GridData();
					spReading1LData.widthHint = 150;
					spReading1 = new SwingNumericSpinner(composite1, SWT.NONE);
					spReading1.setLayoutData(spReading1LData);
				}
				{
					lblDate2 = new Label(composite1, SWT.NONE);
					lblDate2.setText(GUIMessages.getMessage("comp.general.end_date"));
				}
				{
					GridData pkDate2LData = new GridData();
					pkDate2LData.widthHint = 150;
					pkDate2 = new SWTDatePicker(composite1, SWT.BORDER);
					pkDate2.setLayoutData(pkDate2LData);
				}
				{
					lblR2 = new Label(composite1, SWT.NONE);
					lblR2.setText(GUIMessages.getMessage("comp.simple_avrg_charge.r2"));
				}
				{
					GridData spReading2LData = new GridData();
					spReading2LData.widthHint = 150;
					spReading2 = new SwingNumericSpinner(composite1, SWT.NONE);
					spReading2.setLayoutData(spReading2LData);
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
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblCoeff = new Label(composite3, SWT.NONE);
					lblCoeff.setText(GUIMessages.getMessage("comp.general.coeff"));
				}
				{
					GridData spCoeffLData = new GridData();
					spCoeffLData.widthHint = 150;
					spCoeff = new SwingNumericSpinner(composite3, SWT.NONE);
					spCoeff.setLayoutData(spCoeffLData);
					spCoeff.setMask("#,###");
				}
				{
					lblDigits = new Label(composite3, SWT.NONE);
					lblDigits.setText(GUIMessages.getMessage("comp.general.meter_digits"));
				}
				{
					GridData spDigitsLData = new GridData();
					spDigitsLData.widthHint = 150;
					spDigits = new SwingNumericSpinner(composite3, SWT.NONE);
					spDigits.setLayoutData(spDigitsLData);
					spDigits.setMask("#,###");
				}
				{
					lblAcceleration = new Label(composite3, SWT.NONE);
					lblAcceleration.setText(GUIMessages.getMessage("comp.general.acceleration"));
				}
				{
					GridData spAccelerationLData = new GridData();
					spAccelerationLData.widthHint = 150;
					spAcceleration = new SwingNumericSpinner(composite3, SWT.NONE);
					spAcceleration.setLayoutData(spAccelerationLData);
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
				{
					GridData lblErrorLData = new GridData();
					lblErrorLData.verticalAlignment = GridData.FILL;
					lblErrorLData.grabExcessVerticalSpace = true;
					lblErrorLData.horizontalAlignment = GridData.FILL;
					lblErrorLData.grabExcessHorizontalSpace = true;
					lblError = new Label(composite5, SWT.WRAP);
					lblError.setLayoutData(lblErrorLData);
					lblError.setForeground(lblError.getDisplay()
							.getSystemColor(SWT.COLOR_RED));
				}
			}
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.marginHeight = 10;
				composite4Layout.marginWidth = 10;
				composite4Layout.numColumns = 2;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					btnOk = new Button(composite4, SWT.PUSH | SWT.CENTER);
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
					btnClose = new Button(composite4, SWT.PUSH | SWT.CENTER);
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
			dialogShell.layout();
			dialogShell.setText(GUIMessages.getMessage("comp.simple_avrg_charge.interval_properties"));
			dialogShell.pack();
			//dialogShell.setSize(600, 400);
			GUIUtils.centerShell(dialogShell);
			reset();
			dialogShell.open();
			// $protect<<$

			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RecalcInterval getInterval() {
		return interval;
	}

	public void setInterval(RecalcInterval interval) {
		this.interval = interval;
	}

	private void reset() {
		if (interval == null) {
			pkDate1.setDate(null);
			pkDate2.setDate(null);
			spReading1.setValue(new Float(0));
			spReading2.setValue(new Float(0));
			spCoeff.setValue(new Integer(1));
			spAcceleration.setValue(new Float(0));
			spDigits.setValue(new Integer(5));
		} else {
			RecalcItem item1 = (RecalcItem) interval.getItems().get(0);
			RecalcItem item2 = (RecalcItem) interval.getItems().get(1);
			pkDate1.setDate(item1.getItemDate());
			pkDate2.setDate(item2.getItemDate());
			spReading1.setValue(new Float(item1.getReading()));
			spReading2.setValue(new Float(item2.getReading()));
			spCoeff.setValue(new Float(item1.getMeterCoeff()));
			spAcceleration.setValue(new Float(item1.getMeterAcceleration()));
			spDigits.setValue(new Integer(item1.getMeter().getDigits()));
		}
	}

	@SuppressWarnings("unchecked")
	private void onOk() {
		lblError.setText("");
		spReading1.apply();
		spAcceleration.apply();
		spReading2.apply();
		spCoeff.apply();
		spDigits.apply();
		Date d1 = pkDate1.getDate();
		Date d2 = pkDate2.getDate();
		float r1 = ((Number) spReading1.getValue()).floatValue();
		float r2 = ((Number) spReading2.getValue()).floatValue();
		float acc = ((Number) spAcceleration.getValue()).floatValue();
		int coeff = ((Number) spCoeff.getValue()).intValue();
		int digits = ((Number) spDigits.getValue()).intValue();
		if (d1 == null) {
			lblError
					.setText(GUIMessages
							.getMessage("comp.simple_avrg_charge_prop.define_start_date"));
			return;
		}
		if (d2 == null) {
			lblError
					.setText(GUIMessages
							.getMessage("comp.simple_avrg_charge_prop.define_end_date"));
			return;
		}
		if (r1 <= 0) {
			lblError.setText(GUIMessages
					.getMessage("comp.simple_avrg_charge_prop.define_r1"));
			return;
		}
		if (r2 <= 0) {
			lblError.setText(GUIMessages
					.getMessage("comp.simple_avrg_charge_prop.define_r2"));
			return;
		}
		if (coeff < 1) {
			lblError.setText(GUIMessages
					.getMessage("comp.simple_avrg_charge_prop.define_coeff"));
			return;
		}
		if (digits < 3 || digits > 7) {
			lblError.setText(GUIMessages
					.getMessage("comp.simple_avrg_charge_prop.define_digits"));
			return;
		}

		Meter meter = new Meter();
		meter.setDigits(digits);

		RecalcItem item1 = new RecalcItem();
		item1.setReading(r1);
		item1.setItemDate(d1);
		item1.setMeterAcceleration(acc);
		item1.setMeterCoeff(coeff);
		item1.setMeter(meter);

		RecalcItem item2 = new RecalcItem();
		item2.setReading(r2);
		item2.setItemDate(d2);
		item2.setMeterAcceleration(acc);
		item2.setMeterCoeff(coeff);
		item2.setMeter(meter);

		List items = new ArrayList();
		items.add(item1);
		items.add(item2);
		RecalcInterval interval = new RecalcInterval();
		interval.setId(count++);
		interval.setItems(items);

		approved = true;
		this.interval = interval;
		dialogShell.dispose();

	}

	private static int count = 0;

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	private boolean approved = false;

	public boolean isApproved() {
		return approved;
	}

}
