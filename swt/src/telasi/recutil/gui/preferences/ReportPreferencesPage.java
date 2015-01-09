package telasi.recutil.gui.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;

public class ReportPreferencesPage extends PreferencePage implements
		IWorkbenchPreferencePage {
	public static final String BC_NAME_KEY = "telasi_businesscenter_name";
	private Text txtBCName;

	@Override
	protected Control createContents(Composite parent) {
		// new parent component
		Composite newParent = new Composite(parent, SWT.NONE);
		GridLayout l1 = new GridLayout();
		l1.numColumns = 1;
		l1.marginWidth = 0;
		l1.marginHeight = 0;
		GridData d1 = new GridData();
		d1.grabExcessHorizontalSpace = true;
		d1.horizontalAlignment = GridData.FILL;
		newParent.setLayoutData(d1);
		newParent.setLayout(l1);

		// business-center field
		createBCField(newParent);

		// initialize values
		initValues();

		// return parent
		return newParent;
	}

	public void init(IWorkbench workbench) {
	}

	private Composite createBCField(Composite parent) {

		// parent composite for this component
		Composite newParent = new Composite(parent, SWT.NONE);
		GridData d1 = new GridData();
		d1.grabExcessHorizontalSpace = true;
		d1.horizontalAlignment = GridData.FILL;
		newParent.setLayoutData(d1);
		GridLayout l1 = new GridLayout();
		l1.numColumns = 1;
		newParent.setLayout(l1);

		// description label
		Label dscr = new Label(newParent, SWT.WRAP);
		dscr.setText(GUIMessages
				.getMessage("comp.pref.report.bussiness_center.descr"));
		GridData d2 = new GridData();
		d2.grabExcessHorizontalSpace = true;
		d2.horizontalAlignment = GridData.FILL;
		dscr.setLayoutData(d2);

		// create subparent for BC name label/field
		Composite subParent = new Composite(parent, SWT.NONE);
		GridData d3 = new GridData();
		d3.grabExcessHorizontalSpace = true;
		d3.horizontalAlignment = GridData.FILL;
		subParent.setLayoutData(d3);
		GridLayout l3 = new GridLayout();
		l3.numColumns = 2;
		subParent.setLayout(l3);

		// label
		Label lblBCName = new Label(subParent, SWT.NONE);
		lblBCName.setText(GUIMessages
				.getMessage("comp.pref.report.bussiness_center.title"));
		// text field
		txtBCName = new Text(subParent, SWT.BORDER);
		GridData d4 = new GridData();
		d4.grabExcessHorizontalSpace = true;
		d4.horizontalAlignment = GridData.FILL;
		txtBCName.setLayoutData(d4);

		// return composite
		return newParent;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public boolean performOk() {
		IPreferenceStore store = getPreferences();
		String name = txtBCName.getText();
		store.putValue(BC_NAME_KEY, name == null ? "" : name);
		store.setDefault(BC_NAME_KEY, name == null ? "" : name);
		return true;
	}

	private void initValues() {
		IPreferenceStore store = getPreferences();
		String name = store.getString(BC_NAME_KEY);
		txtBCName.setText(name == null ? "" : name);
	}

	private static IPreferenceStore getPreferences() {
		return Plugin.getDefault().getPreferenceStore();
	}
	
	public static String getMyBusinessCenterName() {
		IPreferenceStore store = getPreferences();
		return store.getString(BC_NAME_KEY);
	}

}
