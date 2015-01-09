package telasi.recutil.gui.comp.exchange;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class RecalcExchangePane extends Composite {
	private SashForm sashForm1;
	private SourcePane pnImportSource;
	private DestinationPane destinationPane1;
	private Label label1;
	private MovementPane movementPane1;
	private Composite composite1;

	public RecalcExchangePane(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(412, 292);
			{
				sashForm1 = new SashForm(this, SWT.NONE);
				GridData sashForm1LData = new GridData();
				sashForm1LData.horizontalAlignment = GridData.FILL;
				sashForm1LData.grabExcessHorizontalSpace = true;
				sashForm1LData.verticalAlignment = GridData.FILL;
				sashForm1LData.grabExcessVerticalSpace = true;
				sashForm1.setLayoutData(sashForm1LData);
				sashForm1.setSize(60, 30);
				{
					pnImportSource = new SourcePane(sashForm1, SWT.BORDER);
				}
				{
					composite1 = new Composite(sashForm1, SWT.BORDER);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.horizontalSpacing = 0;
					composite1Layout.marginHeight = 0;
					composite1Layout.marginWidth = 0;
					composite1Layout.verticalSpacing = 0;
					composite1.setLayout(composite1Layout);
					composite1.setBounds(106, 93, 205, 292);
					{
						GridData destinationPane1LData = new GridData();
						destinationPane1LData.horizontalAlignment = GridData.FILL;
						destinationPane1LData.grabExcessHorizontalSpace = true;
						destinationPane1 = new DestinationPane(composite1, SWT.NONE);
						destinationPane1.setLayoutData(destinationPane1LData);
					}
					{
						GridData label1LData = new GridData();
						label1LData.horizontalAlignment = GridData.FILL;
						label1LData.grabExcessHorizontalSpace = true;
						label1 = new Label(composite1, SWT.SEPARATOR | SWT.HORIZONTAL);
						label1.setLayoutData(label1LData);
					}
					{
						GridData movementPane1LData = new GridData();
						movementPane1LData.horizontalAlignment = GridData.FILL;
						movementPane1LData.grabExcessHorizontalSpace = true;
						movementPane1LData.verticalAlignment = GridData.FILL;
						movementPane1LData.grabExcessVerticalSpace = true;
						movementPane1 = new MovementPane(composite1, SWT.NONE);
						movementPane1.setLayoutData(movementPane1LData);
						movementPane1.addPropertyChangeListener(new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {
								String name = evt.getPropertyName();
								if ("start".equals(name)) {
									blockActions(true); // block
								} else if ("end".equals(name)) {
									clearSuccessfull();
									blockActions(false); // unblock
								}
							}
						});
						movementPane1.setParentPane(this);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void clearSuccessfull() {
		List<Long> ids = movementPane1.getSuccessfullRecalcIds();
		pnImportSource.clearSuccessfull(ids);
	}

	protected void blockActions(boolean block) {
		pnImportSource.blockActions(block);
		movementPane1.blockActions(block);
		destinationPane1.blockActions(block);
	}

	protected List getSelectedRecalcs() {
		return pnImportSource.getSelectedRecalcs();
	}

	protected IExchangeNode getSource() {
		return pnImportSource.getSource();
	}

	protected IExchangeNode getDestination() {
		return destinationPane1.getDestination();
	}

	public void addSource(IExchangeNode src, List recalcs) {
		pnImportSource.addSource(src, recalcs);
	}

}
