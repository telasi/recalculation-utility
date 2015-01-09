/*
 *   Copyright (C) 2007 by JSC Telasi
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.RecalcItem;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class CalculationHintSelector extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Label lblImage;
	private Label lblTitle;
	private Label label1;
	private Label lblImg5;
	private Label lblDescription;
	private Button btnNone;
	private Button btnForceDischarge;
	private Button btnClose;
	private Button btnOk;
	private Composite composite4;
	private Button btnContinuous;
	private Label lblImg4;
	private Button btnPreserveKwh;
	private Label lblImg3;
	private Button btnPreserveBoth;
	private Label lblImg2;
	private Label lblImg1;
	private Group group1;
	private Composite composite3;
	private Composite composite2;
	
	private int hint;
	private boolean approved;

	public CalculationHintSelector(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL
					| SWT.RESIZE);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.marginHeight = 0;
			dialogShellLayout.marginWidth = 0;
			dialogShellLayout.verticalSpacing = 0;
			dialogShellLayout.horizontalSpacing = 0;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.setSize(482, 342);
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 48;
					lblImageLData.widthHint = 48;
					lblImage = new Label(composite1, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/colors.png"));
				}
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.makeColumnsEqualWidth = true;
					GridData composite2LData = new GridData();
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2LData.verticalAlignment = GridData.FILL;
					composite2LData.grabExcessVerticalSpace = true;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					{
						lblTitle = new Label(composite2, SWT.NONE);
						lblTitle.setText(GUIMessages.
								getMessage("comp.calc_hint_selector.title"));
						GridData lblTitleLData = new GridData();
						lblTitleLData.horizontalAlignment = GridData.FILL;
						lblTitleLData.grabExcessHorizontalSpace = true;
						lblTitle.setLayoutData(lblTitleLData);
						lblTitle.setFont(GUIUtils.createSubtitleFont(lblTitle.getFont()));
					}
					{
						GridData label1LData = new GridData();
						label1LData.horizontalAlignment = GridData.FILL;
						label1LData.grabExcessHorizontalSpace = true;
						label1 = new Label(composite2, SWT.SEPARATOR
							| SWT.HORIZONTAL);
						label1.setLayoutData(label1LData);
					}
					{
						lblDescription = new Label(composite2, SWT.WRAP);
						GridData lblDescriptionLData = new GridData();
						lblDescriptionLData.horizontalAlignment = GridData.FILL;
						lblDescriptionLData.grabExcessHorizontalSpace = true;
						lblDescriptionLData.verticalAlignment = GridData.FILL;
						lblDescriptionLData.grabExcessVerticalSpace = true;
						lblDescription.setLayoutData(lblDescriptionLData);
						lblDescription.setText(GUIMessages.
								getMessage("comp.calc_hint_selector.descr"));
					}
				}
			}
			{
			composite3 = new Composite(dialogShell, SWT.NONE);
			GridLayout composite3Layout = new GridLayout();
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					group1 = new Group(composite3, SWT.NONE);
					GridLayout group1Layout = new GridLayout();
					group1Layout.numColumns = 2;
					GridData group1LData = new GridData();
					group1LData.horizontalAlignment = GridData.FILL;
					group1LData.grabExcessHorizontalSpace = true;
					group1LData.verticalAlignment = GridData.FILL;
					group1LData.grabExcessVerticalSpace = true;
					group1.setLayoutData(group1LData);
					group1.setLayout(group1Layout);
					{
						GridData lblImg1LData = new GridData();
						lblImg1LData.heightHint = 16;
						lblImg1LData.widthHint = 16;
						lblImg1 = new Label(group1, SWT.NONE);
						lblImg1.setLayoutData(lblImg1LData);
						lblImg1.setImage(Plugin.getImage("icons/16x16/stop.png"));
					}
					{
						btnNone = new Button(group1, SWT.RADIO | SWT.LEFT);
						btnNone.setText(GUIUtils.getCalculationHintName(RecalcItem.HINT_NONE));
					}
					{
						GridData lblImg2LData = new GridData();
						lblImg2LData.heightHint = 16;
						lblImg2LData.widthHint = 16;
						lblImg2 = new Label(group1, SWT.NONE);
						lblImg2.setLayoutData(lblImg2LData);
						lblImg2.setImage(Plugin.getImage("icons/16x16/lock.png"));
					}
					{
						btnPreserveBoth = new Button(group1, SWT.RADIO
							| SWT.LEFT);
						btnPreserveBoth.setText(GUIUtils.
							getCalculationHintName(RecalcItem.HINT_PRESERVE_BOTH));
					}
					{
						GridData lblImg3LData = new GridData();
						lblImg3LData.heightHint = 16;
						lblImg3LData.widthHint = 16;
						lblImg3 = new Label(group1, SWT.NONE);
						lblImg3.setLayoutData(lblImg3LData);
						lblImg3.setImage(Plugin.getImage("icons/16x16/energy.png"));
					}
					{
						btnPreserveKwh = new Button(group1, SWT.RADIO
							| SWT.LEFT);
						btnPreserveKwh.setText(GUIUtils.
								getCalculationHintName(RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH));
					}
					{
						GridData lblImg4LData = new GridData();
						lblImg4LData.heightHint = 16;
						lblImg4LData.widthHint = 16;
						lblImg4 = new Label(group1, SWT.NONE);
						lblImg4.setLayoutData(lblImg4LData);
						lblImg4.setImage(Plugin.getImage("icons/16x16/date.png"));
					}
					{
						btnContinuous = new Button(group1, SWT.RADIO | SWT.LEFT);
						btnContinuous.setText(GUIUtils.getCalculationHintName(RecalcItem.HINT_USE_CONTINUOUS_BY_INSTCP));
					}
					{
						GridData lblImg5LData = new GridData();
						lblImg5LData.heightHint = 16;
						lblImg5LData.widthHint = 16;
						lblImg5 = new Label(group1, SWT.NONE);
						lblImg5.setLayoutData(lblImg5LData);
						lblImg5.setImage(Plugin.getImage("icons/16x16/force_discharge.png"));
					}
					{
						btnForceDischarge = new Button(group1, SWT.RADIO | SWT.LEFT);
						btnForceDischarge.setText(GUIUtils.getCalculationHintName(RecalcItem.HINT_FORCE_DISCHARGE));
					}
				}
			}
			{
				composite4 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				composite4Layout.marginWidth = 10;
				composite4Layout.marginHeight = 10;
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
					btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
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
					btnClose.setText(GUIMessages.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onClose();
						}
					});
				}
			}
			//$hide>>$
			dialogShell.layout();
			dialogShell.pack();
			//dialogShell.setSize(500, 400);
			reset();
			dialogShell.setText(GUIMessages.getMessage("comp.calc_hint_selector.title"));
			GUIUtils.centerShell(dialogShell);
			dialogShell.open();
			//$hide<<$
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reset() {
	switch(hint) {
	case RecalcItem.HINT_PRESERVE_BOTH:
		btnPreserveBoth.setSelection(true);
		break;
	case RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH:
		btnPreserveKwh.setSelection(true);
		break;
	case RecalcItem.HINT_USE_CONTINUOUS_BY_INSTCP:
		btnContinuous.setSelection(true);
		break;
	case RecalcItem.HINT_FORCE_DISCHARGE:
		btnForceDischarge.setSelection(true);
		break;
	default:
		btnNone.setSelection(true);
		break;
	}
	}

	private void apply() {
	if (btnPreserveBoth.getSelection())
		hint=RecalcItem.HINT_PRESERVE_BOTH;
	else if (btnPreserveKwh.getSelection())
		hint=RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH;
	else if (btnContinuous.getSelection())
		hint=RecalcItem.HINT_USE_CONTINUOUS_BY_INSTCP;
	else if (btnForceDischarge.getSelection())
		hint=RecalcItem.HINT_FORCE_DISCHARGE;
	else
		hint=RecalcItem.HINT_NONE;
	}
	
	public int getHint() {
	return hint;
	}

	public void setHint(int hint) {
	this.hint = hint;
	}
	
	private void onOk() {
	apply();
	approved = true;
	dialogShell.dispose();
	}
	
	private void onClose() {
	approved = false;
	dialogShell.dispose();
	}
	
	public boolean isApproved() {
	return approved;
	}
	
}
