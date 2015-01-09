package telasi.recutil.gui.comp.recalc.results;

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

import telasi.recutil.beans.FacturaDetail;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.NumberFormat;

public class FacturaExpansionPane extends Composite {
	private Composite composite1;
	private Label lblTitle;
	private Label label1;
	private Label lblDescription;
	private TableColumn colEnd;
	private TableColumn colGel;
	private TableColumn colKwh;
	private TableColumn colItemDate;
	private TableColumn colItem;
	private TableColumn colOperation;
	private TableViewer viewer;
	private Composite composite3;
	private Label lblImage;
	private Composite composite2;
	private List items;

	public FacturaExpansionPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			thisLayout.horizontalSpacing = 0;
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.verticalSpacing = 0;
			this.setLayout(thisLayout);
			this.setSize(764, 436);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				{
					lblTitle = new Label(composite1, SWT.NONE);
					GridData lblTitleLData = new GridData();
					lblTitleLData.horizontalAlignment = GridData.FILL;
					lblTitleLData.grabExcessHorizontalSpace = true;
					lblTitle.setLayoutData(lblTitleLData);
					lblTitle.setText(GUIMessages.getMessage("comp.factura_expansion.title2"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
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
				composite2Layout.numColumns = 2;
				GridData composite2LData = new GridData();
				composite2LData.horizontalAlignment = GridData.FILL;
				composite2LData.grabExcessHorizontalSpace = true;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 48;
					lblImageLData.widthHint = 48;
					lblImage = new Label(composite2, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/48x48/print.png"));
				}
				{
					lblDescription = new Label(composite2, SWT.WRAP);
					GridData lblDescriptionLData = new GridData();
					lblDescriptionLData.horizontalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessHorizontalSpace = true;
					lblDescriptionLData.verticalAlignment = GridData.FILL;
					lblDescriptionLData.grabExcessVerticalSpace = true;
					lblDescription.setLayoutData(lblDescriptionLData);
					lblDescription.setText(GUIMessages.getMessage("comp.factura_expansion.descr"));
				}
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				GridData composite3LData = new GridData();
				composite3LData.horizontalAlignment = GridData.FILL;
				composite3LData.grabExcessHorizontalSpace = true;
				composite3LData.verticalAlignment = GridData.FILL;
				composite3LData.grabExcessVerticalSpace = true;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite3, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.VIRTUAL);
					viewer.getControl().setLayoutData(viewerLData);
					viewer.getTable().setHeaderVisible(true);
					viewer.getTable().setLinesVisible(true);
					{
						colOperation = new TableColumn(viewer.getTable(), SWT.NONE);
						colOperation.setText("Operation");
						colOperation.setWidth(250);
					}
					{
						colItem = new TableColumn(viewer.getTable(), SWT.NONE);
						colItem.setText("Item ID");
						colItem.setWidth(150);
					}
					{
						colItemDate = new TableColumn(viewer.getTable(), SWT.NONE);
						colItemDate.setText("Item Date");
						colItemDate.setWidth(150);
					}
					{
						colKwh = new TableColumn(viewer.getTable(), SWT.RIGHT);
						colKwh.setText("kWh");
						colKwh.setWidth(100);
					}
					{
						colGel = new TableColumn(viewer.getTable(), SWT.RIGHT);
						colGel.setText("GEL");
						colGel.setWidth(100);
					}
					{
						colEnd = new TableColumn(viewer.getTable(), SWT.NONE);
						colEnd.setWidth(10);
						colEnd.setResizable(false);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initDataBehaivour() {
		display(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new FacturaExpansionLabelProvider());
	}

	public void display(List items) {
		this.items = items;
		viewer.setItemCount(items == null ? 0 : items.size());
		viewer.setContentProvider(new CommonItemContentProvider(items));
	}

	private class FacturaExpansionLabelProvider extends LabelProvider implements ITableLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00");

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null)
				return "";
			FacturaDetail item = (FacturaDetail) element;
			switch (columnIndex) {
			case 0:
				return GUITranslator.GEO_ASCII_TO_KA(item.getOperation().getName());
			case 1:
				return String.valueOf(item.getOriginalItemId());
			case 2:
				return item.getItemDate().toString();
			case 3:
				return nf.format(item.getKwh());
			case 4:
				return nf.format(item.getGel());
			default:
				return "";
			}
		}
	}

	public List getFacturaExpansion() {
		return items;
	}

}
