package telasi.recutil.gui.comp.tpowner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.beans.tpowner.TransformatorType;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class TransTypePicker extends Composite {
	private Button btnSearch;
	private Text txtTypeName;
	private TransformatorType transformatorType;

	public TransTypePicker(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			thisLayout.numColumns = 2;
			this.setLayout(thisLayout);
			{
				GridData txtTypeNameLData = new GridData();
				txtTypeNameLData.horizontalAlignment = GridData.FILL;
				txtTypeNameLData.grabExcessHorizontalSpace = true;
				txtTypeName = new Text(this, SWT.READ_ONLY);
				txtTypeName.setLayoutData(txtTypeNameLData);
			}
			{
				btnSearch = new Button(this, SWT.PUSH | SWT.CENTER);
				btnSearch.setText("...");
				btnSearch.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						onSearch();
					}
				});
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TransformatorType getTransformatorType() {
		return transformatorType;
	}

	public void setTransformatorType(TransformatorType transformatorType) {
		this.transformatorType = transformatorType;
		reset();
	}

	private void reset() {
		if (transformatorType == null) {
			txtTypeName.setText("<not defined>");
		} else {
			txtTypeName.setText(transformatorType.getName());
		}
	}

	private void onSearch() {
		TransformatorSelector selector = new TransformatorSelector(getShell(), SWT.NONE);
		selector.setTransformatorType(transformatorType);
		selector.open();
		if (selector.getTransformatorType() != null)
			setTransformatorType(selector.getTransformatorType());
	}
}
