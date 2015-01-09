package telasi.recutil.gui.comp.exchange;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class SourcePane extends Composite {
	private Composite composite1;
	private Label lblImage;
	private Label lblTitle;
	private Label label1;
	private Label lblDescription;
	private RecalcSelectorPane pnRecalcSelector;
	private NodePicker pkSource;
	private Composite composite3;
	private Composite composite2;

	public SourcePane(Composite parent, int style) {
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
			this.setSize(487, 392);
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
					lblImage.setImage(Plugin.getImage("icons/32x32/src.png"));
				}
				{
					lblTitle = new Label(composite1, SWT.NONE);
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
					lblTitle.setText(GUIMessages.getMessage("comp.exchange.importsource.title"));
				}
			}
			{
				GridData label1LData = new GridData();
				label1LData.horizontalAlignment = GridData.FILL;
				label1LData.grabExcessHorizontalSpace = true;
				label1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				label1.setLayoutData(label1LData);
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = true;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.exchange.importsource.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData pkImportSourceLData = new GridData();
					pkImportSourceLData.horizontalAlignment = GridData.FILL;
					pkImportSourceLData.grabExcessHorizontalSpace = true;
					pkSource = new NodePicker(composite3, SWT.NONE);
					pkSource.setLayoutData(pkImportSourceLData);
					pkSource.addPropertyChangeListener(new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							pnRecalcSelector.setSource(pkSource.getNode());
							pnRecalcSelector.reset();
						}
					});
					pkSource.setDestination(false);
				}
			}
			{
				GridData pnRecalcSelectorLData = new GridData();
				pnRecalcSelectorLData.horizontalAlignment = GridData.FILL;
				pnRecalcSelectorLData.grabExcessHorizontalSpace = true;
				pnRecalcSelectorLData.verticalAlignment = GridData.FILL;
				pnRecalcSelectorLData.grabExcessVerticalSpace = true;
				pnRecalcSelector = new RecalcSelectorPane(this, SWT.NONE);
				pnRecalcSelector.setLayoutData(pnRecalcSelectorLData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void blockActions(boolean block) {
		pnRecalcSelector.blockActions(block);
		pkSource.blockActions(block);
	}
	
	protected List getSelectedRecalcs() {
		return pnRecalcSelector.getAll();
	}

	protected IExchangeNode getSource() {
		return pkSource.getNode();
	}
	
	protected void clearSuccessfull(List<Long> ids) {
		pnRecalcSelector.clearSuccessfull(ids);
	}
	
	public void addSource(IExchangeNode src, List recalcs) {
    	pkSource.setNode(src);
    	pkSource.reset();
    	
    	pnRecalcSelector.displayRecalcs(recalcs);
	}
	
}
