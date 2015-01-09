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
package telasi.recutil.gui.comp.security;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import telasi.recutil.gui.plugin.ConnectionDescriptor;

public class ServerSelector extends Composite {

	private Label lblServerUrl;
	private Button btnSelect;
	private ConnectionDescriptor descriptor;

	public ServerSelector(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				GridData lblServerUrlLData = new GridData();
				lblServerUrlLData.horizontalAlignment = GridData.FILL;
				lblServerUrlLData.grabExcessHorizontalSpace = true;
				lblServerUrl = new Label(this, SWT.NONE);
				lblServerUrl.setLayoutData(lblServerUrlLData);
			}
			{
				btnSelect = new Button(this, SWT.PUSH | SWT.CENTER);
				btnSelect.setText("...");
				btnSelect.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						onSelect();
					}
				});
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ConnectionDescriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(ConnectionDescriptor descriptor) {
		this.descriptor = descriptor;
		if (descriptor == null) {
			lblServerUrl.setText("");
		} else {
			lblServerUrl.setText(descriptor.toString());
		}
	}

	private void onSelect() {
		UrlSelectorDialog dialog = new UrlSelectorDialog(getShell(), SWT.NONE);
		dialog.setDescriptor(descriptor);
		dialog.open();
		if (!dialog.isApproved()) {
			return;
		}
		setDescriptor(dialog.getDescriptor());
	}

}
