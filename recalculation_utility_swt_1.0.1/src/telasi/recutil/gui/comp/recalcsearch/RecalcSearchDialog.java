package telasi.recutil.gui.comp.recalcsearch;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.gui.comp.exchange.ServerExchangeNode;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class RecalcSearchDialog extends Dialog {
	private Shell dialogShell;
	private RecalcSearchPane recalcSearchPane1;
	private Composite composite1;
	private Button btnOk;
	private Button btnClose;
	private ServerExchangeNode serverInfo;
	private boolean approved;
	private List selected;

	public RecalcSearchDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
			dialogShell.setLayout(new GridLayout());
			dialogShell.layout();
			dialogShell.setText(GUIMessages.getMessage("comp.recalcsearch.dialog.title"));
			//$hide>>$
			{
				GridData recalcSearchPane1LData = new GridData();
				recalcSearchPane1LData.horizontalAlignment = GridData.FILL;
				recalcSearchPane1LData.grabExcessHorizontalSpace = true;
				recalcSearchPane1LData.verticalAlignment = GridData.FILL;
				recalcSearchPane1LData.grabExcessVerticalSpace = true;
				recalcSearchPane1 = new RecalcSearchPane(dialogShell, SWT.NONE);
				recalcSearchPane1.setLayoutData(recalcSearchPane1LData);
				recalcSearchPane1.setServerInfo(serverInfo);
			}
			//$hide<<$
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.verticalAlignment = GridData.END;
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					btnOk = new Button(composite1, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.widthHint = 75;
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
					btnOk.getShell().setDefaultButton(btnOk);
					btnOk.addSelectionListener(new SelectionAdapter() {
						@Override
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
					btnClose.setText(GUIMessages.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							approved = false;
							dialogShell.dispose();
						}
					});
				}
			}
			initSizeAndLocation();
			dialogShell.addControlListener(new ControlListener() {
				public void controlMoved(ControlEvent e) {
					saveSizeAndLocation();
				}
				public void controlResized(ControlEvent e) {
					saveSizeAndLocation();
				}
			});
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

	private void initSizeAndLocation() {
		IDialogSettings settings = Plugin.getDefault().getDialogSettings();
		try {
			int x = settings.getInt("recalcSearchDialog.x");
			int y = settings.getInt("recalcSearchDialog.y");
			int w = settings.getInt("recalcSearchDialog.w");
			int h = settings.getInt("recalcSearchDialog.h");
			dialogShell.setLocation(new Point(x, y));
			dialogShell.setSize(new Point(w, h));
		} catch (Throwable t) {
			dialogShell.setSize(800, 500);
			GUIUtils.centerShell(dialogShell);
		}
	}
	
	private void saveSizeAndLocation() {
		IDialogSettings settings = Plugin.getDefault().getDialogSettings();
		try {
			Point loc = dialogShell.getLocation();
			Point size = dialogShell.getSize();
			settings.put("recalcSearchDialog.x", loc.x);
			settings.put("recalcSearchDialog.y", loc.y);
			settings.put("recalcSearchDialog.w", size.x);
			settings.put("recalcSearchDialog.h", size.y);
		} catch(Throwable t) {
			t.printStackTrace();
		}
	}

	public ServerExchangeNode getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(ServerExchangeNode serverInfo) {
		this.serverInfo = serverInfo;
	}

	private void onOk() {
		selected = recalcSearchPane1.getSelected();
		if (selected != null && !selected.isEmpty()) {
			approved = true;
			dialogShell.dispose();
		}
	}

	public boolean isApproved() {
		return approved;
	}

	public List getSelected() {
		return selected;
	}

}
