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
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.TrashVoucher;
import telasi.recutil.gui.comp.CommonItemContentProvider;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.NumberFormat;

public class TrashCalculationPane extends Composite {
	private TableViewer viewer;
	private List items;

	public TrashCalculationPane(Composite parent, int style) {
		super(parent, style);
		initGUI();
		initDataBehaivour();
	}

	private void initGUI() {
		GridLayout thisLayout = new GridLayout();
		thisLayout.horizontalSpacing = 0;
		thisLayout.marginHeight = 0;
		thisLayout.marginWidth = 0;
		thisLayout.verticalSpacing = 0;
		this.setLayout(thisLayout);

		Composite composite1 = new Composite(this, SWT.NONE);
		GridLayout composite1Layout = new GridLayout();
		composite1Layout.makeColumnsEqualWidth = true;
		GridData composite1LData = new GridData();
		composite1LData.horizontalAlignment = GridData.FILL;
		composite1LData.verticalAlignment = GridData.FILL;
		composite1LData.grabExcessHorizontalSpace = true;
		composite1LData.grabExcessVerticalSpace = true;
		composite1.setLayoutData(composite1LData);
		composite1.setLayout(composite1Layout);
		{
			GridData viewerLData = new GridData();
			viewerLData.horizontalAlignment = GridData.FILL;
			viewerLData.grabExcessHorizontalSpace = true;
			viewerLData.verticalAlignment = GridData.FILL;
			viewerLData.grabExcessVerticalSpace = true;
			viewer = new TableViewer(composite1, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.VIRTUAL);
			viewer.getControl().setLayoutData(viewerLData);
			viewer.getTable().setHeaderVisible(true);
			viewer.getTable().setLinesVisible(true);
			{
				TableColumn colOperation = new TableColumn(viewer.getTable(), SWT.NONE);
				colOperation.setText("Operation");
				colOperation.setWidth(250);
			}
			{
				TableColumn colKwh = new TableColumn(viewer.getTable(), SWT.RIGHT);
				colKwh.setText("kWh");
				colKwh.setWidth(100);
			}
			{
				TableColumn colGel = new TableColumn(viewer.getTable(), SWT.RIGHT);
				colGel.setText("GEL");
				colGel.setWidth(100);
			}
			{
				TableColumn colEnd = new TableColumn(viewer.getTable(), SWT.NONE);
				colEnd.setWidth(10);
				colEnd.setResizable(false);
			}
		}
		this.layout();
	}

	private void initDataBehaivour() {
		display(null);
		viewer.setInput(this);
		viewer.setLabelProvider(new TrashLabelProvider());
	}

	public List getItems() {
		return items;
	}

	public void display(List items) {
		this.items = items;
		viewer.setItemCount(items == null ? 0 : items.size());
		viewer.setContentProvider(new CommonItemContentProvider(items));
	}

	private class TrashLabelProvider extends LabelProvider implements ITableLabelProvider {
		private NumberFormat nf = new DecimalFormat("#,##0.00");

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			/*
			 * if (element == null) return ""; FacturaDetail item =
			 * (FacturaDetail) element; switch (columnIndex) { case 0: return
			 * GUITranslator.GEO_ASCII_TO_KA(item.getOperation().getName());
			 * case 1: return String.valueOf(item.getOriginalItemId()); case 2:
			 * return item.getItemDate().toString(); case 3: return
			 * nf.format(item.getKwh()); case 4: return
			 * nf.format(item.getGel()); default: return ""; }
			 */

			if (element == null)
				return "";
			TrashVoucher voucher = (TrashVoucher) element;

			switch (columnIndex) {
			case 0:
				return TrashVoucher.findOperation(voucher.getTrashOperation()).getName();
			case 1:
				return nf.format(voucher.getKwh());
			case 2:
				return nf.format(voucher.getGel());
			default:
				return "";
			}
		}
	}

}
