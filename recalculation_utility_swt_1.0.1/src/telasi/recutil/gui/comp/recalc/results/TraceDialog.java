package telasi.recutil.gui.comp.recalc.results;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;

import telasi.recutil.beans.Charge;
import telasi.recutil.beans.ChargeElement;
import telasi.recutil.beans.Date;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.utils.GUIUtils;

public class TraceDialog extends Dialog {

	private Shell dialogShell;
	private Composite composite1;
	private Composite composite2;
	private TableColumn colStart;
	private TableColumn colEnd;
	private TableColumn colLast;
	private TableColumn colGel;
	private TableColumn colKwh;
	private Button btnClose;
	private TableViewer viewer;
	private Charge charge;

	public TraceDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			dialogShell.setText("Calculation Trace");

			dialogShell.setLayout(new GridLayout());
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
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewerLData.grabExcessHorizontalSpace = true;
					viewer = new TableViewer(composite1,
							  SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE | SWT.VIRTUAL);
					viewer.getControl().setLayoutData(viewerLData);
					viewer.getTable().setLinesVisible(true);
					viewer.getTable().setHeaderVisible(true);
					{
						colStart = new TableColumn(viewer.getTable(), SWT.NONE);
						colStart.setText("Start");
						colStart.setWidth(100);
					}
					{
						colEnd = new TableColumn(viewer.getTable(), SWT.NONE);
						colEnd.setText("End");
						colEnd.setWidth(100);
					}
					{
						colKwh = new TableColumn(viewer.getTable(), SWT.NONE);
						colKwh.setText("kWh");
						colKwh.setWidth(100);
					}
					{
						colGel = new TableColumn(viewer.getTable(), SWT.NONE);
						colGel.setText("GEL");
						colGel.setWidth(100);
					}
					{
						colLast = new TableColumn(viewer.getTable(), SWT.NONE);
						colLast.setWidth(10);
						colLast.setResizable(false);
					}
				}
				{
					composite2 = new Composite(composite1, SWT.NONE);
					GridLayout composite2Layout = new GridLayout();
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
						btnClose.setText("Close");
						btnClose.addSelectionListener(new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								dialogShell.dispose();
							}
						});
					}
				}
			}
			dialogShell.setMinimumSize(new Point(100, 300));
			dialogShell.layout();
			dialogShell.pack();
			initDisplayData();
			GUIUtils.centerShell(dialogShell);
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


	private void initDisplayData() {
	viewer.setItemCount(charge == null ? 0 : charge.expansion().size());
	viewer.setContentProvider(new CommonItemContentProvider(charge == null ? null : charge.expansion()));
	viewer.setInput(this);
	viewer.setLabelProvider(new ChargeLabelProvider());
	}
	
	public Charge getCharge() {
		return charge;
	}


	public void setCharge(Charge charge) {
		this.charge = charge;
	}
	
	private class ChargeLabelProvider
	 			extends LabelProvider
	 			implements ITableLabelProvider {

		private NumberFormat nf = new DecimalFormat("#,###.00##");
		
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			ChargeElement ce = (ChargeElement) element;
			switch (columnIndex) {
			case 0: return ce.getStartDate() == null ? "" : Date.format(ce.getStartDate());
			case 1: return ce.getEndDate() == null ? "" : Date.format(ce.getEndDate());
			case 2: return nf.format(ce.getKwh());
			case 3: return nf.format(ce.getGel());
			default: return "";
			}
		}
		
	}	
}
