package telasi.recutil.gui.comp.tpowner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.tpowner.TpOwnerCorrection;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUIUtils;

public class TpOwnerResultsPane extends org.eclipse.swt.widgets.Composite {

	private TableViewer tblResults;
	
	public TpOwnerResultsPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehoivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.layout();
			
			{
				Composite composite5 = new Composite(this, SWT.NONE);
				GridLayout composite5Layout = new GridLayout();
				composite5Layout.numColumns = 2;
				composite5.setLayout(composite5Layout);
				GridData composite5LData = new GridData();
				composite5LData.horizontalAlignment = GridData.FILL;
				composite5LData.grabExcessHorizontalSpace = true;
				composite5LData.heightHint = 42;
				composite5.setLayoutData(composite5LData);
				{
					Label label2 = new Label(composite5, SWT.NONE);
					GridData label2LData1 = new GridData();
					label2LData1.widthHint = 32;
					label2LData1.heightHint = 32;
					label2.setLayoutData(label2LData1);
					label2.setImage(Plugin.getImage("icons/32x32/calc.png"));
				}
				{
					Label label3 = new Label(composite5, SWT.NONE);
					label3.setText(GUIMessages.getMessage("comp.tpowner.recalc.recalcResults"));
					label3.setFont(GUIUtils.createTitleFont(label3.getFont()));
				}
			}
			{
				Label label4 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
				GridData label4LData = new GridData();
				label4LData.horizontalAlignment = GridData.FILL;
				label4LData.grabExcessHorizontalSpace = true;
				label4.setLayoutData(label4LData);
				label4.setText("label4");
			}
			{
				Composite composite6 = new Composite(this, SWT.NONE);
				GridLayout composite6Layout = new GridLayout();
				composite6Layout.makeColumnsEqualWidth = true;
				GridData composite6LData = new GridData();
				composite6LData.horizontalAlignment = GridData.FILL;
				composite6LData.grabExcessHorizontalSpace = true;
				composite6LData.verticalAlignment = GridData.FILL;
				composite6LData.grabExcessVerticalSpace = true;
				composite6.setLayoutData(composite6LData);
				composite6.setLayout(composite6Layout);
				{
					GridData tblResultsLData = new GridData();
					tblResultsLData.horizontalAlignment = GridData.FILL;
					tblResultsLData.grabExcessHorizontalSpace = true;
					tblResultsLData.verticalAlignment = GridData.FILL;
					tblResultsLData.grabExcessVerticalSpace = true;
					tblResults = new TableViewer(composite6, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL | SWT.MULTI);
					tblResults.getControl().setLayoutData(tblResultsLData);
					tblResults.getTable().setHeaderVisible(true);
					tblResults.getTable().setLinesVisible(true);
					{
						TableColumn colOperation2 = new TableColumn(tblResults.getTable(), SWT.NONE);
						colOperation2.setText(GUIMessages.getMessage("comp.general.operation"));
						colOperation2.setWidth(200);
					}
					{
						TableColumn colStartDate = new TableColumn(tblResults.getTable(), SWT.NONE);
						colStartDate.setText(GUIMessages.getMessage("comp.general.start_date"));
						colStartDate.setWidth(100);
					}
					{
						TableColumn colEndDate = new TableColumn(tblResults.getTable(), SWT.NONE);
						colEndDate.setText(GUIMessages.getMessage("comp.general.end_date"));
						colEndDate.setWidth(100);
					}
					{
						TableColumn colKwh2 = new TableColumn(tblResults.getTable(), SWT.RIGHT);
						colKwh2.setText(GUIMessages.getMessage("comp.general.kwh"));
						colKwh2.setWidth(100);
					}
					{
						TableColumn colGel2 = new TableColumn(tblResults.getTable(), SWT.RIGHT);
						colGel2.setText(GUIMessages.getMessage("comp.general.gel"));
						colGel2.setWidth(100);
					}
					{
						TableColumn colDummy = new TableColumn(tblResults.getTable(), SWT.NONE);
						colDummy.setWidth(10);
						colDummy.setResizable(false);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehoivour() {
		displayResults(null);
		tblResults.setInput(this);
		tblResults.setLabelProvider(new ResultsLabelProvider());
	}

	public void displayResults(List items) {
		tblResults.getTable().setItemCount(items == null ? 0 : items.size());
		tblResults.setContentProvider(new CommonItemContentProvider(items));
	}

	private class ResultsLabelProvider extends LabelProvider implements ITableLabelProvider {
		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			TpOwnerCorrection correction = (TpOwnerCorrection) element;
			switch (columnIndex) {
			case 0:
				return GUIUtils.getTpOwnerCorrectionName(correction.getType());
			case 1:
				return Date.format(correction.getStartDate());
			case 2:
				return Date.format(correction.getEndDate());
			case 3:
				return nf.format(correction.getKwh());
			case 4:
				return nf.format(correction.getGel());
			default:
				return "";
			}
		}
	}

	public List getDisplayedItems() {
		return ((CommonItemContentProvider) tblResults.getContentProvider()).getFullItems();
	}
}
