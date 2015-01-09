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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.widgets.calendar.SWTDatePicker;

public class RecalcItemDatesPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Composite composite2;
	private Label lblHelp3;
	private Label lblImage3;
	private Composite composite4;
	private Button button2;
	private SWTDatePicker pkPrevious;
	private Label lblPrevOperDate;
	private Composite composite3;
	private Label lblDescription;
	private Label lblImage;
	private Label lblSeparator1;
	private RecalcItem item;

	public RecalcItemDatesPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			thisLayout.horizontalSpacing = 0;
			this.setLayout(thisLayout);
			{
				composite1 = new Composite(this, SWT.NONE);
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
							.getMessage("comp.recalc_item_dates_pane.title"));
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
					lblImage.setImage(Plugin.getImage("icons/48x48/clock.png"));
				}
				{
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription = new Label(composite2, SWT.WRAP);
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.recalc_item_dates_pane.description"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.marginTop = 10;
				composite3Layout.numColumns = 3;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					lblPrevOperDate = new Label(composite3, SWT.NONE);
					lblPrevOperDate.setText(GUIMessages
							.getMessage("comp.general.prev_oper_date"));
				}
				{
					GridData pkPreviousDateLData = new GridData();
					pkPreviousDateLData.widthHint = 150;
					pkPrevious = new SWTDatePicker(composite3, SWT.BORDER);
					pkPrevious.setLayoutData(pkPreviousDateLData);
				}
				{
					button2 = new Button(composite3, SWT.PUSH | SWT.CENTER);
					button2.setText(GUIMessages
							.getMessage("comp.general.clear"));
					button2.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							pkPrevious.setDate(null);
						}
					});
				}
			}
			{
				composite4 = new Composite(this, SWT.NONE);
				GridLayout composite4Layout = new GridLayout();
				composite4Layout.numColumns = 2;
				composite4Layout.marginTop = 10;
				composite4Layout.verticalSpacing = 10;
				GridData composite4LData = new GridData();
				composite4LData.horizontalAlignment = GridData.FILL;
				composite4LData.grabExcessHorizontalSpace = true;
				composite4.setLayoutData(composite4LData);
				composite4.setLayout(composite4Layout);
				{
					GridData lblImage3LData = new GridData();
					lblImage3LData.verticalAlignment = GridData.BEGINNING;
					lblImage3 = new Label(composite4, SWT.NONE);
					lblImage3.setLayoutData(lblImage3LData);
					lblImage3.setImage(Display.getDefault().getSystemImage(
							SWT.ICON_WARNING));
				}
				{
					GridData lblHelp3LData = new GridData();
					lblHelp3LData.horizontalAlignment = GridData.FILL;
					lblHelp3LData.grabExcessHorizontalSpace = true;
					lblHelp3 = new Label(composite4, SWT.WRAP);
					lblHelp3.setLayoutData(lblHelp3LData);
					lblHelp3.setText(GUIMessages
							.getMessage("comp.recalc_item_dates_pane.help3"));
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			//pkCurrent.setDate(null);
			pkPrevious.setDate(null);
		} else {
			//pkCurrent.setDate(item.getCurrentOperationDate());
			pkPrevious.setDate(item.getPreviousOperationDate());
			//pkCurrent.setDefault(item.getCurrentOperationDate() == null ? item
			//		.getItemDate() : item.getCurrentOperationDate());
			pkPrevious
					.setDefault(item.getPreviousOperationDate() == null ? item
							.getItemDate() : item.getPreviousOperationDate());
		}
	}

	// values

	public Date getCurrentDate() {
		//return pkCurrent.getDate();
		return null;
	}

	public Date getPreviousDate() {
		return pkPrevious.getDate();
	}

}
