package telasi.recutil.gui.comp.security;

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

import telasi.recutil.gui.plugin.ConnectionDescriptor;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class ConnectionTypeSelector extends Dialog {
	private Shell dialogShell;
	private Composite composite1;
	private Group group1;
	private Button btnJBoss;
	private Button btnClose;
	private Button btnOk;
	private Composite composite2;
	private Button btnOracle;
	private Label lblOracle;
	private Label lblJBoss;
	private boolean approved;
	private int type;

	public ConnectionTypeSelector(Shell parent, int style) {
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
			dialogShell.setText(GUIMessages.getMessage("comp.url.properties.selectType"));
			{
				composite1 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.verticalAlignment = GridData.FILL;
				composite1LData.grabExcessVerticalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					group1 = new Group(composite1, SWT.NONE);
					GridLayout group1Layout = new GridLayout();
					group1Layout.numColumns = 2;
					group1.setLayout(group1Layout);
					GridData group1LData = new GridData();
					group1LData.horizontalAlignment = GridData.FILL;
					group1LData.grabExcessHorizontalSpace = true;
					group1.setLayoutData(group1LData);
					group1.setText(GUIMessages.getMessage("comp.url.properties.selectType"));
					{
						lblJBoss = new Label(group1, SWT.NONE);
						lblJBoss.setImage(Plugin.getImage("icons/other/jboss.png"));
					}
					{
						btnJBoss = new Button(group1, SWT.RADIO | SWT.LEFT);
						btnJBoss.setText(GUIMessages.getMessage("comp.url.properties.jboss"));
					}
					{
						lblOracle = new Label(group1, SWT.NONE);
						lblOracle.setImage(Plugin.getImage("icons/other/oracle.png"));
					}
					{
						btnOracle = new Button(group1, SWT.RADIO | SWT.LEFT);
						btnOracle.setText(GUIMessages.getMessage("comp.url.properties.oracle"));
					}
				}
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
					composite2Layout.numColumns = 2;
					GridData composite2LData = new GridData();
					composite2LData.horizontalAlignment = GridData.FILL;
					composite2LData.grabExcessHorizontalSpace = true;
					composite2LData.verticalAlignment = GridData.FILL;
					composite2LData.grabExcessVerticalSpace = true;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					{
						btnOk = new Button(composite2, SWT.PUSH | SWT.CENTER);
						GridData btnOkLData = new GridData();
						btnOkLData.horizontalAlignment = GridData.END;
						btnOkLData.grabExcessHorizontalSpace = true;
						btnOkLData.grabExcessVerticalSpace = true;
						btnOkLData.verticalAlignment = GridData.END;
						btnOkLData.widthHint = 75;
						btnOk.setLayoutData(btnOkLData);
						btnOk.setText(GUIMessages.getMessage("comp.general.ok"));
						btnOk.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								if (btnJBoss.getSelection())
									type = ConnectionDescriptor.JBOSS;
								else if (btnOracle.getSelection())
									type = ConnectionDescriptor.ORACLE;
								approved = true;
								dialogShell.close();
							}
						});
					}
					{
						btnClose = new Button(composite2, SWT.PUSH | SWT.CENTER);
						GridData btnCloseLData = new GridData();
						btnCloseLData.grabExcessVerticalSpace = true;
						btnCloseLData.verticalAlignment = GridData.END;
						btnCloseLData.widthHint = 75;
						btnClose.setLayoutData(btnCloseLData);
						btnClose.setText(GUIMessages.getMessage("comp.general.close"));
						btnClose.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								approved = false;
								dialogShell.close();
							}
						});
					}
				}
			}
			//$protect>>$
			dialogShell.pack();
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
	
	public boolean isApproved() {
		return approved;
	}
	
	private void reset() {
		btnJBoss.setSelection(true);
	}

	public int getType() {
		return type;
	}
	
}
