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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.utils.CoreUtils;

public class SummaryPane extends Composite {

	private Composite composite1;
	private Composite composite2;
	private Label txtRecord;
	private Label lblBalance;
	private Label lblGel;
	private Label lblKwh;
	private Label lblFilterImage;
	private NumberFormat nf = new DecimalFormat("#,###.00");

	public SummaryPane(Composite parent, int style) {
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
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.marginHeight = 0;
				composite1Layout.marginWidth = 0;
				composite1Layout.verticalSpacing = 0;
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.widthHint = 250;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData txtRecordLData = new GridData();
					txtRecordLData.horizontalAlignment = GridData.CENTER;
					txtRecordLData.widthHint = 150;
					txtRecord = new Label(composite1, SWT.BORDER | SWT.CENTER);
					txtRecord.setLayoutData(txtRecordLData);
				}
				{
					GridData lblFilterImageLData = new GridData();
					lblFilterImageLData.heightHint = 16;
					lblFilterImageLData.widthHint = 16;
					lblFilterImage = new Label(composite1, SWT.NONE);
					lblFilterImage.setLayoutData(lblFilterImageLData);
				}
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.marginHeight = 0;
				composite2Layout.marginWidth = 0;
				composite2Layout.verticalSpacing = 0;
				composite2Layout.numColumns = 3;
				composite2Layout.makeColumnsEqualWidth = true;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblKwhLData = new GridData();
					lblKwhLData.horizontalAlignment = GridData.FILL;
					lblKwhLData.grabExcessHorizontalSpace = true;
					lblKwh = new Label(composite2, SWT.CENTER | SWT.BORDER);
					lblKwh.setLayoutData(lblKwhLData);
				}
				{
					GridData lblGelLData = new GridData();
					lblGelLData.horizontalAlignment = GridData.FILL;
					lblGelLData.grabExcessHorizontalSpace = true;
					lblGel = new Label(composite2, SWT.BORDER | SWT.CENTER);
					lblGel.setLayoutData(lblGelLData);
				}
				{
					GridData lblBalanceLData = new GridData();
					lblBalanceLData.horizontalAlignment = GridData.FILL;
					lblBalanceLData.grabExcessHorizontalSpace = true;
					lblBalance = new Label(composite2, SWT.BORDER | SWT.CENTER);
					lblBalance.setLayoutData(lblBalanceLData);
				}
			}
			this.layout();
			reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void reset() {
		setKwh(0.f);
		setGel(0.f);
		setBalance(0.f);
		setLocation(-1, -1);
	}

	public void setKwh(double kwh) {
		if (Math.abs(kwh) < CoreUtils.MIN_KWH) {
			lblKwh.setText("-");
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(nf.format(kwh));
			sb.append(" ");
			sb.append(GUIMessages.getMessage("comp.general.kwh"));
			lblKwh.setText(sb.toString());
		}
	}

	public void setGel(double gel) {
		if (Math.abs(gel) < CoreUtils.MIN_GEL) {
			lblGel.setText("-");
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(nf.format(gel));
			sb.append(" ");
			sb.append(GUIMessages.getMessage("comp.general.gel"));
			lblGel.setText(sb.toString());
		}
	}

	public void setBalance(double balance) {
		if (Math.abs(balance) < CoreUtils.MIN_GEL) {
			lblBalance.setText("-");
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(GUIMessages.getMessage("comp.general.balance"));
			sb.append(" ");
			sb.append(nf.format(balance));
			sb.append(" ");
			sb.append(GUIMessages.getMessage("comp.general.gel"));
			lblBalance.setText(sb.toString());
		}
	}

	public void setLocation(int record, int full) {
		if (record == -1 && full == -1) {
			txtRecord.setText("-");
		} else {
			txtRecord.setText(GUIMessages.getMessage(
					"comp.general.record_of_records", new Object[] {
							new Integer(record), new Integer(full) }));
		}
	}

}
