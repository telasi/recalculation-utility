package telasi.recutil.gui.comp.tpowner;

import java.util.List;

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

import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

/**
 * Results dialog.
 */
public class SavedResultsDialog extends Dialog {

	private Shell dialogShell;
	private TpOwnerResultsPane pnResults;
	private Button btnClose;
	private Composite composite2;
	private Composite composite1;
	private List results;

	public SavedResultsDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
			dialogShell.setText(GUIMessages.getMessage("comp.tpowner.savedCalc.title"));
			GridLayout dialogShellLayout = new GridLayout();
			dialogShell.setLayout(dialogShellLayout);
			dialogShell.layout();
			dialogShell.pack();			
			dialogShell.setSize(650, 300);
			{
				pnResults = new TpOwnerResultsPane(dialogShell, SWT.NONE);
				GridData pnResultsLData = new GridData();
				pnResultsLData.horizontalAlignment = GridData.FILL;
				pnResultsLData.grabExcessHorizontalSpace = true;
				pnResultsLData.verticalAlignment = GridData.FILL;
				pnResultsLData.grabExcessVerticalSpace = true;
				pnResults.setLayoutData(pnResultsLData);
				{
					composite1 = new Composite(pnResults, SWT.NONE);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.makeColumnsEqualWidth = true;
					composite1.setLayout(composite1Layout);
					composite1.setBounds(155, 255, 60, 30);
				}
			}
			{
				composite2 = new Composite(dialogShell, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					btnClose = new Button(composite2, SWT.PUSH | SWT.CENTER);
					GridData btnCloseLData = new GridData();
					btnCloseLData.grabExcessHorizontalSpace = true;
					btnCloseLData.horizontalAlignment = GridData.END;
					btnClose.setLayoutData(btnCloseLData);
					btnClose.setText(GUIMessages.getMessage("comp.general.close"));
					btnClose.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							onClose();
						}
					});
				}
			}
			GUIUtils.centerShell(dialogShell);
			dialogShell.open();
			pnResults.displayResults(results);
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onClose() {
		dialogShell.dispose();
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}

}
