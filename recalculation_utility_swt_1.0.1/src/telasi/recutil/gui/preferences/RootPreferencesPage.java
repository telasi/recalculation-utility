package telasi.recutil.gui.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class RootPreferencesPage extends PreferencePage implements
		IWorkbenchPreferencePage {

	protected Control createContents(Composite parent) {
		return new Composite(parent, SWT.NONE);
	}

	public void init(IWorkbench workbench) {
	}

	

}
