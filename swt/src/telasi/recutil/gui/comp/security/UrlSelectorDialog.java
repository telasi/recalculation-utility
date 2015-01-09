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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.plugin.ConnectionDescriptor;
import telasi.recutil.gui.plugin.JBossConnectionDescriptor;
import telasi.recutil.gui.plugin.OracleConnectionDescriptor;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.plugin.Settings;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class UrlSelectorDialog extends org.eclipse.swt.widgets.Dialog {
	private Shell dialogShell;
	private Label lblDescription;
	private TableColumn colImage;
	private ToolItem tiDown;
	private ToolItem tiUp;
	private ToolItem toolItem1;
	private TableColumn colEnd;
	private TableColumn colUrl;
	private TableColumn colName;
	private Button btnClose;
	private Button btnOk;
	private Composite composite3;
	private ToolItem tiProperties;
	private ToolItem tiDelete;
	private ToolItem tiNew;
	private ToolBar toolBar1;
	private TableViewer viewer;
	private Composite composite1;
	private Label lblImage;
	private Composite composite2;
	private boolean approved = false;
	private ConnectionDescriptor descriptor;
	private List connections;

	public UrlSelectorDialog(Shell parent, int style) {
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
			dialogShell.layout();
			dialogShell.setText(GUIMessages.getMessage("comp.urlselector.title"));
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
					lblImage.setImage(Plugin.getImage("icons/48x48/server.png"));
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.urlselector.descr"));
				}
			}
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite1, SWT.BORDER
							| SWT.FULL_SELECTION | SWT.VIRTUAL | SWT.MULTI);
					viewer.getTable().setHeaderVisible(true);
					viewer.getTable().setLinesVisible(true);
					viewer.getControl().setLayoutData(viewerLData);
					viewer.addSelectionChangedListener(new ISelectionChangedListener() {
								public void selectionChanged(
										SelectionChangedEvent event) {
									validateView();
								}
							});
					{
						colImage = new TableColumn(viewer.getTable(), SWT.NONE);
						colImage.setWidth(60);
						colImage.setResizable(false);
					}
					{
						colName = new TableColumn(viewer.getTable(), SWT.NONE);
						colName.setText(GUIMessages
								.getMessage("comp.general.name"));
						colName.setWidth(200);
					}
					{
						colUrl = new TableColumn(viewer.getTable(), SWT.NONE);
						colUrl.setText(GUIMessages.getMessage("comp.general.address"));
						colUrl.setWidth(300);
					}
					{
						colEnd = new TableColumn(viewer.getTable(), SWT.NONE);
						colEnd.setWidth(10);
						colEnd.setResizable(false);
					}
					
				}
			}
			{
				GridData toolBar1LData = new GridData();
				toolBar1LData.horizontalAlignment = GridData.FILL;
				toolBar1LData.grabExcessHorizontalSpace = true;
				toolBar1 = new ToolBar(dialogShell, SWT.NONE);
				toolBar1.setLayoutData(toolBar1LData);
				{
					tiNew = new ToolItem(toolBar1, SWT.NONE);
					tiNew.setToolTipText(GUIMessages.getMessage("comp.urlselector.newserver"));
					tiNew.setImage(Plugin.getImage("icons/22x22/new.gif"));
					tiNew.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onNew();
						}
					});
				}
				{
					tiDelete = new ToolItem(toolBar1, SWT.NONE);
					tiDelete.setToolTipText(GUIMessages
							.getMessage("comp.urlselector.deleteserver"));
					tiDelete.setImage(Plugin.getImage("icons/22x22/trash.png"));
					tiDelete.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onDelete();
						}
					});
				}
				{
					tiProperties = new ToolItem(toolBar1, SWT.NONE);
					tiProperties.setToolTipText(GUIMessages
							.getMessage("comp.urlselector.serverproperties"));
					tiProperties.setImage(Plugin
							.getImage("icons/22x22/properties.gif"));
					tiProperties.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onProperties();
						}
					});
				}
				{
					toolItem1 = new ToolItem(toolBar1, SWT.SEPARATOR);
					toolItem1.setText("");
				}
				{
					tiUp = new ToolItem(toolBar1, SWT.NONE);
					tiUp.setToolTipText("up");
					tiUp.setImage(Plugin.getImage("icons/22x22/up.png"));
					tiUp.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onMove(true);
						}
					});
				}
				{
					tiDown = new ToolItem(toolBar1, SWT.NONE);
					tiDown.setToolTipText("down");
					tiDown.setImage(Plugin.getImage("icons/22x22/down.png"));
					tiDown.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onMove(false);
						}
					});
				}
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
					btnOk = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.widthHint = 75;
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
					btnOk.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							onOk();
						}
					});
				}
				{
					btnClose = new Button(composite3, SWT.PUSH | SWT.CENTER);
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
			//dialogShell.setSize(400, 500);
			dialogShell.setMinimumSize(0, 500);
			dialogShell.pack();
			GUIUtils.centerShell(dialogShell);
			reset();
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

	public boolean isApproved() {
		return approved;
	}

	private void onClose() {
		approved = false;
		dialogShell.dispose();
	}

	private void onOk() {
		ConnectionDescriptor descriptor = getSelectedDescriptor();
		if (descriptor == null) {
			return;
		}
		this.descriptor = descriptor;
		approved = true;
		dialogShell.dispose();
	}

	private void reset() {
		initDataBehaivour();
		refreshConnectionsList();

		//List descriptors = Plugin.getSettings().getConnectionDescriptors();
		if (connections != null && descriptor != null) {
			String name = descriptor.getName();
			String url = descriptor.getUrl();
			name = name == null ? "" : name;
			url = url == null ? "" : url;
			for (int i = 0; i < connections.size(); i++) {
				ConnectionDescriptor descr = (ConnectionDescriptor) connections.get(i);
				String name1 = descr.getName();
				String url1 = descr.getUrl();
				if (url.equals(url1) && name.equals(name1)) {
					viewer.getTable().select(i);
					viewer.getTable().showSelection();
					break;
				}
			}
		}

		validateView();
	}

	private void initDataBehaivour() {
		refreshConnectionsList();
		viewer.setInput(this);
		viewer.setLabelProvider(new UrlLabelProvider());
	}

	private void refreshConnectionsList() {
		viewer.getTable().deselectAll();
		connections = Settings.getConnectionDescriptors();
		viewer.setContentProvider(new CommonItemContentProvider(connections));
		viewer.setItemCount(connections == null ? 0 : connections.size());
	}

	private class UrlLabelProvider extends LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			if (element == null || columnIndex != 0)
				return null;
			ConnectionDescriptor descr = (ConnectionDescriptor) element;
			if (descr instanceof OracleConnectionDescriptor) {
				return Plugin.getImage("icons/other/oracle.png");
			} else if (descr instanceof JBossConnectionDescriptor) {
				return Plugin.getImage("icons/other/jboss.png");
			} else {
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null)
				return "";
			ConnectionDescriptor descr = (ConnectionDescriptor) element;
			switch (columnIndex) {
			case 1:
				String name = descr.getName();
				return name == null ? "" : name;
			case 2:
				String url = descr.getUrl();
				return url == null ? "" : url;
			default:
				return "";
			}
		}
	}

	private ConnectionDescriptor getSelectedDescriptor() {
		int index = viewer.getTable().getSelectionIndex();
		if (index == -1) {
			return null;
		}
		return (ConnectionDescriptor) connections.get(index);
	}

	@SuppressWarnings("unchecked")
	private List getSelectedDescriptors() {
		int[] indecies = viewer.getTable().getSelectionIndices();
		if (indecies == null || indecies.length == 0) {
			return null;
		}
		List selection = new ArrayList();
		for (int i = 0; i < indecies.length; i++) {
			int index = indecies[i];
			selection.add(connections.get(index));
		}
		return selection;
	}

	private void validateView() {
		ConnectionDescriptor descr = getSelectedDescriptor();
		tiNew.setEnabled(true);
		tiDelete.setEnabled(descr != null);
		tiProperties.setEnabled(descr != null);
		tiUp.setEnabled(descr != null);
		tiDown.setEnabled(descr != null);
		btnClose.setEnabled(true);
		btnOk.setEnabled(descr != null);
	}

	@SuppressWarnings("unchecked")
	private void onNew() {
		ConnectionTypeSelector selector = new ConnectionTypeSelector(dialogShell, SWT.NONE);
		selector.open();
		if (!selector.isApproved()) return;
		// JBoss
		if (selector.getType() == ConnectionDescriptor.JBOSS) {
			JBossConnectionEditor prop = new JBossConnectionEditor(dialogShell, SWT.NONE);
			prop.open();
			if (!prop.isApproved()) return;
			connections.add(prop.getDescriptor());
			Settings.setConnectionDescriptors(connections);
			refreshConnectionsList();
			viewer.getTable().select(connections.size() - 1);
			viewer.getTable().showSelection();
			validateView();
		}
		// Oracle
		else if (selector.getType() == ConnectionDescriptor.ORACLE) {
			OracleConnectionEditor prop = new OracleConnectionEditor(dialogShell, SWT.NONE);
			prop.open();
			if (!prop.isApproved()) return;
			connections.add(prop.getDescriptor());
			Settings.setConnectionDescriptors(connections);
			refreshConnectionsList();
			viewer.getTable().select(connections.size() - 1);
			viewer.getTable().showSelection();
			validateView();
		}
	}

	@SuppressWarnings("unchecked")
	private void onDelete() {
		List forRemove = getSelectedDescriptors();
		if (forRemove == null) {
			return;
		}
		String msg = GUIMessages.getMessage(
				"comp.general.confirm_delete_object_count",
				new Object[] { new Integer(forRemove.size()) });
		String title = GUIMessages.getMessage("comp.general.confirm");
		boolean resp = MessageDialog.openQuestion(dialogShell, title, msg);
		if (!resp) {
			return;
		}
		connections.removeAll(forRemove);
		Settings.setConnectionDescriptors(connections);
		refreshConnectionsList();
		validateView();
	}

	@SuppressWarnings("unchecked")
	private void onProperties() {
		ConnectionDescriptor oldValue = getSelectedDescriptor();
		if (oldValue == null) return;
		int index = connections.indexOf(oldValue);
		if (index == -1) return;
		ConnectionDescriptor newValue = null;
		if (oldValue instanceof JBossConnectionDescriptor) {
			JBossConnectionEditor prop = new JBossConnectionEditor(dialogShell, SWT.NONE);
			prop.setDescriptor(oldValue);
			prop.open();
			if (!prop.isApproved()) return;
			newValue = prop.getDescriptor();
		} else if (oldValue instanceof OracleConnectionDescriptor) {
			OracleConnectionEditor prop = new OracleConnectionEditor(dialogShell, SWT.NONE);
			prop.setDescriptor(oldValue);
			prop.open();
			if (!prop.isApproved()) return;
			newValue = prop.getDescriptor();
		}
		if (newValue == null) return;
		connections.remove(index);
		connections.add(index, newValue);
		Settings.setConnectionDescriptors(connections);
		refreshConnectionsList();
		viewer.getTable().select(index);
		viewer.getTable().showSelection();
		validateView();
	}

	@SuppressWarnings("unchecked")
	private void onMove(boolean up) {
		ConnectionDescriptor selection = getSelectedDescriptor();
		if (selection == null) {
			return;
		}
		int index = connections.indexOf(selection);
		if (index == -1) {
			return;
		}
		if (up && index == 0) {
			return;
		}
		if (!up && index == connections.size() - 1) {
			return;
		}
		int newIndex = up ? index - 1 : index + 1;
		connections.remove(index);
		connections.add(newIndex, selection);
		Settings.setConnectionDescriptors(connections);
		refreshConnectionsList();
		viewer.getTable().select(newIndex);
		viewer.getTable().showSelection();
		validateView();
	}

	public ConnectionDescriptor getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(ConnectionDescriptor descriptor) {
		this.descriptor = descriptor;
	}

}
