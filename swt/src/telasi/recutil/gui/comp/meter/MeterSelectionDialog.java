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
package telasi.recutil.gui.comp.meter;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.Meter;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class MeterSelectionDialog extends Dialog {

	private Shell dialogShell;
	private TableViewer viewer;
	private Button btnClose;
	private Button btnOk;
	private Composite composite1;
	private Meter meter;
	private boolean approved;

	public MeterSelectionDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL | SWT.RESIZE);
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			dialogShell.setLayout(thisLayout);
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewerLData.grabExcessVerticalSpace = true;
				viewer = new TableViewer(dialogShell, SWT.BORDER | SWT.SINGLE
						| SWT.FULL_SELECTION | SWT.VIRTUAL);
				viewer.getControl().setLayoutData(viewerLData);
			}
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				composite1Layout.marginWidth = 15;
				composite1Layout.marginHeight = 15;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					btnOk = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
					btnOkLData.widthHint = 75;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
					btnOk.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onOk();
						}
					});
				}
				{
					btnClose = new Button(composite1, SWT.PUSH | SWT.CENTER);
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
			dialogShell.setLayout(thisLayout);
			dialogShell.layout();
			dialogShell.setText(GUIMessages
					.getMessage("comp.meterselector_dialog.title"));

			// $protect>>$
			dialogShell.setMinimumSize(400, 500);
			dialogShell.pack();
			GUIUtils.centerShell(dialogShell);
			dialogShell.open();
			reset();
			viewer.getTable().showSelection();
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

	private void reset() {
		try {
			Cache.refreshMeterList(false);
		} catch (Throwable t) {
			MessageDialog.openError(dialogShell, GUIMessages
					.getMessage("comp.general.error"), t.toString());
			dialogShell.dispose();
			return;
		}

		viewer.setItemCount(Cache.METER_LIST == null ? 0 : Cache.METER_LIST
				.size());
		viewer.setContentProvider(new MeterContentProvider(Cache.METER_LIST));
		viewer.setInput(this);
		viewer.setLabelProvider(new MeterLabelProvider());

		viewer.getTable().deselectAll();
		if (meter != null) {
			for (int i = 0; i < viewer.getTable().getItemCount(); i++) {
				Meter aMeter = (Meter) Cache.METER_LIST.get(i);
				if (meter.getId() == aMeter.getId()) {
					viewer.getTable().setSelection(i);
					break;
				}
			}
		}

	}

	public Meter getMeter() {
		return meter;
	}

	public void setMeter(Meter meter) {
		this.meter = meter;
	}

	private static class MeterContentProvider implements
			IStructuredContentProvider {

		private List meters;

		public MeterContentProvider(List meters) {
			this.meters = meters;
		}

		public Object[] getElements(Object inputElement) {
			return meters == null ? new Object[] {} : meters.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private static class MeterLabelProvider extends LabelProvider {

		public String getText(Object element) {
			return GUITranslator.GEO_ASCII_TO_KA(element.toString());
		}

	}

	public boolean isApproved() {
		return approved;
	}

	private void onOk() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return;
		}
		MeterContentProvider provider = (MeterContentProvider) viewer
				.getContentProvider();
		meter = (Meter) provider.meters.get(index);
		approved = true;
		dialogShell.dispose();
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

}
