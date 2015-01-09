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
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcRoomItem;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.app.Application;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.calendar.SWTDatePicker;
import telasi.recutil.gui.widgets.spinner.SwingNumericSpinner;
import telasi.recutil.service.eclipse.recalc.RecalcRoomInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRoomUpdateRequest;

public class RoomProperties extends Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Button button1;
	private Button btnClose;
	private Button btnOk;
	private Composite composite13;
	private Label lblError;
	private Composite composite12;
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
	private Recalc recalculation;
	private int sequence = -1;
	private Button btnReset;

	public RoomProperties(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL | SWT.RESIZE);

			GridLayout dialogShellLayout = new GridLayout();
			dialogShellLayout.horizontalSpacing = 0;
			dialogShellLayout.verticalSpacing = 0;
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
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
							.getMessage("comp.room_properties.title"));
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
					lblImage.setImage(Plugin.getImage("icons/48x48/home.png"));
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
							.getMessage("comp.room_properties.descr"));
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
							.getMessage("comp.room_properties.subtitle1"));
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
				composite8Layout.makeColumnsEqualWidth = true;
				GridData composite8LData = new GridData();
				composite8LData.horizontalAlignment = GridData.FILL;
				composite8LData.grabExcessHorizontalSpace = true;
				composite8.setLayoutData(composite8LData);
				composite8.setLayout(composite8Layout);
				{
					lblSubstitle2 = new Label(composite8, SWT.NONE);
					lblSubstitle2.setText(GUIMessages
							.getMessage("comp.room_properties.subtitle2"));
					lblSubstitle2.setFont(GUIUtils
							.createSubtitleFont(lblSubstitle2.getFont()));
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
					lblRooms = new Label(composite9, SWT.NONE);
					lblRooms.setText(GUIMessages
							.getMessage("comp.general.count"));
				}
				{
					GridData spRoomsLData = new GridData();
					spRoomsLData.widthHint = 100;
					spRooms = new SwingNumericSpinner(composite9, SWT.NONE);
					spRooms.setLayoutData(spRoomsLData);
					spRooms.setMask("#,###");
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
			reset();
			dialogShell.pack();
			//dialogShell.setSize(630, 400);
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

	private void reset() {
		if (item == null && recalculation != null) {
			pkStartDate.setDate(null);
			pkEndDate.setDate(null);
			spRooms.setValue(new Integer(recalculation.getCustomer()
					.getRoomCount()));
			isNewMode = true;
			dialogShell.setText(GUIMessages
					.getMessage("comp.room_properties.new_title"));
		} else if (item != null) {
			pkStartDate.setDate(item.getStartDate());
			pkEndDate.setDate(item.getEndDate());
			spRooms.setValue(new Integer(item.getRoomCount()));
			isNewMode = false;
			dialogShell.setText(GUIMessages
					.getMessage("comp.room_properties.edit_title"));
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
		spRooms.apply();

		int count = ((Number) spRooms.getValue()).intValue();
		if (count <= 0) {
			lblError.setText("<= 0");
			return;
		}

		RecalcRoomItem newItem = new RecalcRoomItem();
		newItem.setId(isNewMode ? -1L : item.getId());
		newItem.setStartDate(d1);
		newItem.setEndDate(d2);
		newItem.setRoomCount(count);
		newItem.setRecalcId(recalculation.getId());

		if (!Application.validateConnection()) {
			return;
		}

		try {
			if (isNewMode) {
				RecalcRoomInsertRequest request = new RecalcRoomInsertRequest(
						Application.USER_NAME, Application.PASSWORD);
				request.setItem(newItem);
				request.setRecalc(recalculation);
				DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient
						.processRequest(request);
				RecalcRoomInsertRequest callback = (RecalcRoomInsertRequest) resp
						.getRequest();
				newItem.setId(callback.getId());
				this.item = newItem;
				approved = true;
				dialogShell.dispose();
			} else {
				RecalcRoomUpdateRequest request = new RecalcRoomUpdateRequest(
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

	private RecalcRoomItem item;

	private SwingNumericSpinner spRooms;

	private Label lblRooms;

	private Composite composite9;

	private Label label2;

	private Label lblSubstitle2;

	private Composite composite8;

	public RecalcRoomItem getItem() {
		return item;
	}

	public void setItem(RecalcRoomItem item) {
		this.item = item;
	}

}
