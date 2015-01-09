package telasi.recutil.gui.comp.exchange;

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

import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class NodeTypeSelector extends Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Label lblImage;
	private Label lblDescription;
	private Button btnFile;
	private Label lblFile;
	private Button btnServer;
	private Label lblServer;
	private Button btnClose;
	private Button btnOk;
	private Group group1;
	private Composite composite3;
	private Composite composite2;
	private boolean destination;
	private int type;
	private boolean approved;

	public NodeTypeSelector(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
			//$protect>>$
			String title = destination ?
					GUIMessages.getMessage("comp.exchange.export.selecttype") :
					GUIMessages.getMessage("comp.exchange.import.selecttype");
			//$protect<<$
			dialogShell.setLayout(new GridLayout());
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.BEGINNING;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 32;
					lblImageLData.widthHint = 32;
					lblImage = new Label(composite1, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(
							destination ?
							Plugin.getImage("icons/32x32/dest.png") :
							Plugin.getImage("icons/32x32/src.png"));
				}
				{
					lblDescription = new Label(composite1, SWT.NONE);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(title);
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2LData.verticalAlignment = GridData.FILL;
				composite2LData.grabExcessVerticalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					group1 = new Group(composite2, SWT.NONE);
					GridLayout group1Layout = new GridLayout();
					group1Layout.numColumns = 2;
					group1.setLayout(group1Layout);
					GridData group1LData = new GridData();
					group1LData.horizontalAlignment = GridData.FILL;
					group1LData.grabExcessHorizontalSpace = true;
					group1LData.verticalAlignment = GridData.FILL;
					group1LData.grabExcessVerticalSpace = true;
					group1.setLayoutData(group1LData);
					{
						GridData lblServerLData = new GridData();
						lblServerLData.heightHint = 48;
						lblServerLData.widthHint = 48;
						lblServer = new Label(group1, SWT.NONE);
						lblServer.setLayoutData(lblServerLData);
						lblServer.setImage(Plugin.getImage("icons/48x48/server.png"));
					}
					{
						btnServer = new Button(group1, SWT.RADIO | SWT.LEFT);
						btnServer.setText(GUIMessages.getMessage("comp.exchange.type.server"));
					}
					{
						GridData lblFileLData = new GridData();
						lblFileLData.widthHint = 48;
						lblFileLData.heightHint = 48;
						lblFile = new Label(group1, SWT.NONE);
						lblFile.setLayoutData(lblFileLData);
						lblFile.setImage(Plugin.getImage("icons/48x48/file.png"));
					}
					{
						btnFile = new Button(group1, SWT.RADIO | SWT.LEFT);
						btnFile.setText(GUIMessages.getMessage("comp.exchange.type.file"));
					}
				}
			}
			{
				composite3 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.numColumns = 2;
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					btnOk = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnOkLData = new GridData();
					btnOkLData.grabExcessHorizontalSpace = true;
					btnOkLData.horizontalAlignment = GridData.END;
					btnOkLData.widthHint = 75;
					btnOk.setLayoutData(btnOkLData);
					btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
					btnOk.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if (btnFile.getSelection()) {
								type = IExchangeNode.FILE;
							} else {
								type = IExchangeNode.SERVER;
							}
							approved = true;
							dialogShell.dispose();
						}
					});
					btnOk.getShell().setDefaultButton(btnOk);
				}
				{
					btnClose = new Button(composite3, SWT.PUSH | SWT.CENTER);
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
			dialogShell.layout();
			dialogShell.pack();
			//$protect>>$
			dialogShell.setText(title);
			GUIUtils.centerShell(dialogShell);
			reset();
			//$protect<<$
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
		switch (type) {
		case IExchangeNode.FILE:
			btnFile.setSelection(true);
			break;
		default:
			btnServer.setSelection(true);
		}
	}

	public boolean isDestination() {
		return destination;
	}

	public void setDestination(boolean destination) {
		this.destination = destination;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isApproved() {
		return approved;
	}

}
