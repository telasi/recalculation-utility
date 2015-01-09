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
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import telasi.recutil.beans.Recalc;

public class FullInspcpPane extends Composite {

	private SashForm sashForm1;
	private Composite composite1;
	private Composite composite2;
	private RoomPane roomPane1;
	private CutPane cutPane1;
	private InstcpPane instcpPane1;
	private Composite composite4;
	private Composite composite3;
	private SashForm sashForm2;
	private Recalc recalc;

	public FullInspcpPane(Composite parent, int style) {
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
			this.setSize(376, 279);
			{
				sashForm1 = new SashForm(this, SWT.NONE);
				GridData sashForm1LData = new GridData();
				sashForm1LData.horizontalAlignment = GridData.FILL;
				sashForm1LData.grabExcessHorizontalSpace = true;
				sashForm1LData.verticalAlignment = GridData.FILL;
				sashForm1LData.grabExcessVerticalSpace = true;
				sashForm1.setLayoutData(sashForm1LData);
				sashForm1.setSize(60, 30);
				{
					composite1 = new Composite(sashForm1, SWT.BORDER);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.marginWidth = 0;
					composite1Layout.verticalSpacing = 0;
					composite1Layout.horizontalSpacing = 0;
					composite1Layout.marginHeight = 0;
					composite1.setLayout(composite1Layout);
					composite1.setBounds(30, 61, 60, 30);
					{
						GridData instcpPane1LData = new GridData();
						instcpPane1LData.horizontalAlignment = GridData.FILL;
						instcpPane1LData.grabExcessHorizontalSpace = true;
						instcpPane1LData.verticalAlignment = GridData.FILL;
						instcpPane1LData.grabExcessVerticalSpace = true;
						instcpPane1 = new InstcpPane(composite1, SWT.NONE);
						instcpPane1.setLayoutData(instcpPane1LData);
					}
				}
				{
					composite2 = new Composite(sashForm1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.horizontalSpacing = 0;
					composite2Layout.marginHeight = 0;
					composite2Layout.marginWidth = 0;
					composite2Layout.verticalSpacing = 0;
					composite2.setLayout(composite2Layout);
					composite2.setBounds(43, 64, 29, 30);
					{
						sashForm2 = new SashForm(composite2, SWT.VERTICAL
								| SWT.V_SCROLL);
						GridData sashForm2LData = new GridData();
						sashForm2LData.horizontalAlignment = GridData.FILL;
						sashForm2LData.grabExcessHorizontalSpace = true;
						sashForm2LData.verticalAlignment = GridData.FILL;
						sashForm2LData.grabExcessVerticalSpace = true;
						sashForm2.setLayoutData(sashForm2LData);
						sashForm2.setSize(60, 30);
						{
							composite3 = new Composite(sashForm2, SWT.BORDER);
							GridLayout composite3Layout = new GridLayout();
							composite3Layout.horizontalSpacing = 0;
							composite3Layout.marginHeight = 0;
							composite3Layout.marginWidth = 0;
							composite3Layout.verticalSpacing = 0;
							composite3.setLayout(composite3Layout);
							composite3.setBounds(-119, 125, 183, 275);
							{
								GridData roomPane1LData = new GridData();
								roomPane1LData.horizontalAlignment = GridData.FILL;
								roomPane1LData.grabExcessHorizontalSpace = true;
								roomPane1LData.verticalAlignment = GridData.FILL;
								roomPane1LData.grabExcessVerticalSpace = true;
								roomPane1 = new RoomPane(composite3, SWT.NONE);
								roomPane1.setLayoutData(roomPane1LData);
							}
						}
						{
							composite4 = new Composite(sashForm2, SWT.BORDER);
							GridLayout composite4Layout = new GridLayout();
							composite4Layout.horizontalSpacing = 0;
							composite4Layout.marginHeight = 0;
							composite4Layout.marginWidth = 0;
							composite4Layout.verticalSpacing = 0;
							composite4.setLayout(composite4Layout);
							composite4.setBounds(-113, 122, 90, 275);
							{
								GridData cutPane1LData = new GridData();
								cutPane1LData.horizontalAlignment = GridData.FILL;
								cutPane1LData.grabExcessHorizontalSpace = true;
								cutPane1LData.verticalAlignment = GridData.FILL;
								cutPane1LData.grabExcessVerticalSpace = true;
								cutPane1 = new CutPane(composite4, SWT.NONE);
								cutPane1.setLayoutData(cutPane1LData);
							}
						}
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
		instcpPane1.setRecalc(recalc);
		roomPane1.setRecalc(recalc);
		cutPane1.setRecalc(recalc);
	}

	public void refresh() {
		instcpPane1.refresh();
		roomPane1.refresh();
		cutPane1.refresh();
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void clear() {
		instcpPane1.clear();
		roomPane1.clear();
		cutPane1.clear();
	}

}
