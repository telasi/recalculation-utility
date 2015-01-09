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

import telasi.recutil.beans.Meter;
import telasi.recutil.gui.comp.meter.MeterPicker;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.spinner.SwingNumericSpinner;

public class MeterProperties extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblAcceleration;
	private Label lblHelp1;
	private Label lblInfo1;
	private Composite composite6;
	private Button btnClose;
	private Button btnOk;
	private Composite composite5;
	private Composite composite4;
	private Button chkStatus;
	private Label lblEmpty;
	private SwingNumericSpinner spAcceleration;
	private SwingNumericSpinner spCoeff;
	private Label lblCoeff;
	private MeterPicker pkMeter;
	private Label lblMeter;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label label1;
	// fields
	private Meter meter;
	private double coeff;
	private boolean status;
	private double acceleration;
	private boolean approved;

	public MeterProperties(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			// thisLayout.marginHeight = 0;
			// thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			dialogShell.setLayout(thisLayout);
			dialogShell.setText(GUIMessages.getMessage("comp.meter_properties.title"));
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.marginTop = 10;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblTitle = new Label(composite1, SWT.NONE);
					lblTitle.setText(GUIMessages
							.getMessage("comp.meter_properties.title"));
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle
							.getFont()));
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
					lblImage.setImage(Plugin
							.getImage("icons/48x48/configure.png"));
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
							.getMessage("comp.meter_properties.descr"));
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				composite3Layout.marginTop = 15;
				composite3Layout.marginWidth = 10;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblMeter = new Label(composite3, SWT.NONE);
					lblMeter.setText(GUIMessages
							.getMessage("comp.general.meter"));
				}
				{
					GridData pkMeterLData = new GridData();
					pkMeterLData.horizontalAlignment = GridData.FILL;
					pkMeterLData.grabExcessHorizontalSpace = true;
					pkMeter = new MeterPicker(composite3, SWT.BORDER);
					pkMeter.setLayoutData(pkMeterLData);
				}
				{
					lblCoeff = new Label(composite3, SWT.NONE);
					lblCoeff.setText(GUIMessages
							.getMessage("comp.general.coeff"));
				}
				{
					GridData spCoeffLData = new GridData();
					spCoeffLData.widthHint = 150;
					spCoeff = new SwingNumericSpinner(composite3, SWT.NONE);
					spCoeff.setMask("#,###.#####");
					spCoeff.setLayoutData(spCoeffLData);
				}
				{
					lblAcceleration = new Label(composite3, SWT.NONE);
					lblAcceleration.setText(GUIMessages
							.getMessage("comp.general.acceleration"));
				}
				{
					GridData spAccelerationLData = new GridData();
					spAccelerationLData.widthHint = 150;
					spAcceleration = new SwingNumericSpinner(composite3,
							SWT.NONE);
					spAcceleration.setLayoutData(spAccelerationLData);
					spAcceleration.setMask("#,###.#####");
					((org.eclipse.swt.layout.GridLayout) spAcceleration
							.getLayout()).marginWidth = 0;
					((org.eclipse.swt.layout.GridLayout) spAcceleration
							.getLayout()).marginHeight = 0;
					((org.eclipse.swt.layout.GridLayout) spAcceleration
							.getLayout()).horizontalSpacing = 0;
					((org.eclipse.swt.layout.GridLayout) spAcceleration
							.getLayout()).verticalSpacing = 0;
				}
				{
					lblEmpty = new Label(composite3, SWT.NONE);
					lblEmpty.setText("");
				}
				{
					chkStatus = new Button(composite3, SWT.CHECK | SWT.LEFT);
					chkStatus.setText(GUIMessages
							.getMessage("comp.general.status"));
				}
			}
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.makeColumnsEqualWidth = true;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4LData.verticalAlignment = GridData.FILL;
				composite4LData.grabExcessVerticalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
			}
			{
				composite6 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite6Layout = new GridLayout();
				composite6Layout.numColumns = 2;
				composite6Layout.marginBottom = 15;
				GridData composite6LData = new GridData();
				composite6LData.horizontalAlignment = GridData.FILL;
				composite6LData.grabExcessHorizontalSpace = true;
				composite6.setLayoutData(composite6LData);
				composite6.setLayout(composite6Layout);
				{
					GridData lblInfo1LData = new GridData();
					lblInfo1LData.verticalAlignment = GridData.BEGINNING;
					lblInfo1 = new Label(composite6, SWT.NONE);
					lblInfo1.setLayoutData(lblInfo1LData);
					lblInfo1.setImage(Display.getCurrent().getSystemImage(
							SWT.ICON_INFORMATION));
				}
				{
					lblHelp1 = new Label(composite6, SWT.WRAP);
					GridData lblHelp1LData = new GridData();
					lblHelp1LData.horizontalAlignment = GridData.FILL;
					lblHelp1LData.grabExcessHorizontalSpace = true;
					lblHelp1LData.verticalAlignment = GridData.BEGINNING;
					lblHelp1.setLayoutData(lblHelp1LData);
					lblHelp1.setText(GUIMessages
							.getMessage("comp.meter_properties.help1"));
				}
			}
			{
				composite5 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite5Layout = new GridLayout();
				composite5Layout.marginWidth = 10;
				composite5Layout.marginHeight = 10;
				composite5Layout.numColumns = 2;
				GridData composite5LData = new GridData();
				composite5LData.horizontalAlignment = GridData.FILL;
				composite5LData.grabExcessHorizontalSpace = true;
				composite5.setLayoutData(composite5LData);
				composite5.setLayout(composite5Layout);
				{
					btnOk = new Button(composite5, SWT.PUSH | SWT.CENTER);
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
					btnClose = new Button(composite5, SWT.PUSH | SWT.CENTER);
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
			reset();
			dialogShell.pack();
			//dialogShell.setSize(450, 530);
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

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public double getCoeff() {
		return coeff;
	}

	public void setCoeff(double coeff) {
		this.coeff = coeff;
	}

	public Meter getMeter() {
		return meter;
	}

	public void setMeter(Meter meter) {
		this.meter = meter;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	private void reset() {
		pkMeter.setMeter(meter);
		spCoeff.setValue(new Double(coeff));
		chkStatus.setSelection(status);
		spAcceleration.setValue(new Double(acceleration));
	}

	public boolean isApproved() {
		return approved;
	}

	private void onOk() {
		if (pkMeter.getMeter() == null) {
			return;
		}
		spCoeff.apply();
		spAcceleration.apply();
		coeff = ((Number) spCoeff.getValue()).doubleValue();
		acceleration = ((Number) spAcceleration.getValue()).doubleValue();
		meter = pkMeter.getMeter();
		status = chkStatus.getSelection();
		approved = true;
		dialogShell.dispose();
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

}
