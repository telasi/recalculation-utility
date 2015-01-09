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
package telasi.recutil.gui.comp.cust.histories;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;
import telasi.recutil.gui.views.CustomerHistoryView;

/**
 * @deprecated not used any more
 */
public class HistorySelector extends Composite {
	private TableViewer viewer;
	private Composite composite1;
	private Label lblImage;
	private Label lblTitle;
	private Label label1;
	private Button button1;
	private Composite composite3;
	private Label lblDescreption;
	private Composite composite2;
	private List descriptors = new ArrayList();

	public HistorySelector(Composite parent, int style) {
		super(parent, style);
		initGUI();
		lookupHistories();
		initDataBehaivour();
		validateView();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			this.setSize(324, 241);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 32;
					lblImageLData.widthHint = 32;
					lblImage = new Label(composite1, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin
							.getImage("icons/32x32/history.png"));
				}
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.horizontalSpacing = 0;
					composite2Layout.marginHeight = 0;
					composite2Layout.marginWidth = 0;
					GridData composite2LData = new GridData();
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2LData.verticalAlignment = GridData.FILL;
					composite2LData.grabExcessVerticalSpace = true;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					{
						lblTitle = new Label(composite2, SWT.NONE);
						lblTitle.setText("label1");
						lblTitle.setFont(GUIUtils.createSubtitleFont(lblTitle
								.getFont()));
						lblTitle.setText(GUIMessages
								.getMessage("comp.history_selector.title"));
					}
					{
						lblDescreption = new Label(composite2, SWT.WRAP);
						GridData lblDescreptionLData = new GridData();
						lblDescreptionLData.horizontalAlignment = GridData.FILL;
						lblDescreptionLData.grabExcessHorizontalSpace = true;
						lblDescreption.setLayoutData(lblDescreptionLData);
						lblDescreption.setText(GUIMessages
								.getMessage("comp.history_selector.descr"));
					}
				}
			}
			{
				GridData viewerLData = new GridData();
				viewerLData.horizontalAlignment = GridData.FILL;
				viewerLData.grabExcessHorizontalSpace = true;
				viewerLData.verticalAlignment = GridData.FILL;
				viewerLData.grabExcessVerticalSpace = true;
				viewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION
						| SWT.VIRTUAL);
				viewer.getControl().setLayoutData(viewerLData);
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					label1 = new Label(composite3, SWT.NONE);
					GridData label1LData = new GridData();
					label1LData.grabExcessHorizontalSpace = true;
					label1LData.horizontalAlignment = GridData.FILL;
					label1.setLayoutData(label1LData);
					label1.setText("<select history>");
				}
				{
					button1 = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1LData.horizontalAlignment = GridData.END;
					button1.setLayoutData(button1LData);
					button1
							.setText(GUIMessages
									.getMessage("comp.general.open"));
					button1.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onOpen();
						}
					});
				}
			}
			viewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					validateView();
				}
			});
			viewer.getTable().addMouseListener(new MouseAdapter() {
				public void mouseDoubleClick(MouseEvent e) {
					onOpen();
				}
			});
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class HistoryDescriptor {
		public String name, icon, className, pluginId;

		public String toString() {
			return name;
		}

	}

	@SuppressWarnings("unchecked")
	private void lookupHistories() {
		IExtensionPoint point = Platform.getExtensionRegistry()
				.getExtensionPoint("telasi.recutil.guiapp.customer_history");
		IExtension[] extensions = point.getExtensions();
		if (extensions == null || extensions.length == 0) {
			return;
		}
		for (int i = 0; i < extensions.length; i++) {
			IExtension ext = extensions[i];
			IConfigurationElement[] elements = ext.getConfigurationElements();
			String pluginId = ext.getNamespaceIdentifier();
			for (int j = 0; j < elements.length; j++) {
				IConfigurationElement element = elements[j];
				HistoryDescriptor hd = new HistoryDescriptor();
				hd.name = element.getAttribute("name");
				hd.icon = element.getAttribute("icon");
				hd.className = element.getAttribute("class");
				hd.pluginId = pluginId;
				descriptors.add(hd);
			}
		}
	}

	private class HistoryContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			return descriptors.toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

	}

	private class HistoryLabelProvider extends LabelProvider implements
			ILabelProvider {

		public Image getImage(Object element) {
			HistoryDescriptor hd = (HistoryDescriptor) element;
			if (hd == null) {
				return null;
			}
			if (hd.pluginId == null || hd.icon == null) {
				return null;
			} else {
				ImageData data = AbstractUIPlugin.imageDescriptorFromPlugin(
						hd.pluginId, hd.icon).getImageData();
				return new Image(Display.getDefault(), data);
			}
		}

	}

	private void initDataBehaivour() {
		viewer.setItemCount(descriptors.size());
		viewer.setContentProvider(new HistoryContentProvider());
		viewer.setInput(this);
		viewer.setLabelProvider(new HistoryLabelProvider());
	}

	private void validateView() {
		button1.setEnabled(viewer.getTable().getSelectionIndex() != -1);
	}

	public HistoryDescriptor getHistoryDescriptor() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		return (HistoryDescriptor) descriptors.get(index);
	}

	private CustomerHistoryView customerHistoryView;

	public void setRelatedCustomerHistoryView(CustomerHistoryView chv) {
		this.customerHistoryView = chv;
	}

	private void onOpen() {
		HistoryDescriptor hd = getHistoryDescriptor();
		if (customerHistoryView != null && hd != null) {
			customerHistoryView.onOpen();
			label1.setText(hd.name);

		}
	}

}
