package telasi.recutil.gui.comp.exchange;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import telasi.recutil.beans.Recalc;
import telasi.recutil.gui.comp.CommonItemContentProvider;
import telasi.recutil.gui.plugin.Plugin;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.gui.utils.GUIUtils;

public class MovementPane extends Composite {
	private Composite composite1;
	private Label lblImage;
	private Label lblTitle;
	private Label label1;
	private Label lblDescription;
	private Button btnOverwrite;
	private Button btnGo;
	private ProgressBar progress;
	private Label lblCurrentRecalc;
	private Composite composite4;
	private TableViewer viewer;
	private Composite composite3;
	private Composite composite2;
	private RecalcExchangePane parentPane;

	public MovementPane(Composite parent, int style) {
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
			this.setSize(474, 268);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.horizontalAlignment = GridData.FILL;
				composite1LData.grabExcessHorizontalSpace = true;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(composite1Layout);
				composite1.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				{
					GridData lblImageLData = new GridData();
					lblImageLData.heightHint = 32;
					lblImageLData.widthHint = 32;
					lblImage = new Label(composite1, SWT.NONE);
					lblImage.setLayoutData(lblImageLData);
					lblImage.setImage(Plugin.getImage("icons/32x32/disc.png"));
					lblImage.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				}
				{
					lblTitle = new Label(composite1, SWT.NONE);
					lblTitle.setText(GUIMessages.getMessage("comp.exchange.move.title"));
					lblTitle.setFont(GUIUtils.createTitleFont(lblTitle.getFont()));
					lblTitle.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
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
					lblDescription.setText(GUIMessages.getMessage("comp.exchange.move.descr"));
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
					btnOverwrite = new Button(composite3, SWT.CHECK | SWT.LEFT);
					btnOverwrite.setText(GUIMessages.getMessage("comp.exchange.move.overwrite"));
				}
				{
					btnGo = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData btnGoLData = new GridData();
					btnGoLData.grabExcessHorizontalSpace = true;
					btnGoLData.horizontalAlignment = GridData.END;
					btnGo.setLayoutData(btnGoLData);
					btnGo.setText(GUIMessages.getMessage("comp.exchange.move.go"));
					btnGo.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							onGo();
						}
					});
				}
				{
					GridData viewerLData = new GridData();
					viewerLData.horizontalAlignment = GridData.FILL;
					viewerLData.grabExcessHorizontalSpace = true;
					viewerLData.verticalAlignment = GridData.FILL;
					viewerLData.grabExcessVerticalSpace = true;
					viewer = new TableViewer(composite3, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL | SWT.MULTI);
					viewer.getControl().setLayoutData(viewerLData);
					displayLogs(null);
					viewer.setLabelProvider(new LogLabelProvider());
					viewer.setInput(this);
				}
				{
					composite4 = new Composite(composite3, SWT.BORDER);
					GridLayout composite4Layout = new GridLayout();
					composite4Layout.numColumns = 2;
					composite4Layout.marginHeight = 0;
					composite4Layout.marginWidth = 0;
					GridData composite4LData = new GridData();
					composite4LData.horizontalAlignment = GridData.FILL;
					composite4LData.grabExcessHorizontalSpace = true;
					composite4.setLayoutData(composite4LData);
					composite4.setLayout(composite4Layout);
					{
						GridData lblCurrentRecalcLData = new GridData();
						lblCurrentRecalcLData.horizontalAlignment = GridData.FILL;
						lblCurrentRecalcLData.grabExcessHorizontalSpace = true;
						lblCurrentRecalc = new Label(composite4, SWT.NONE);
						lblCurrentRecalc.setLayoutData(lblCurrentRecalcLData);
						lblCurrentRecalc.setVisible(false);
					}
					{
						GridData progressLData = new GridData();
						progressLData.widthHint = 100;
						progress = new ProgressBar(composite4, SWT.NONE);
						progress.setLayoutData(progressLData);
						progress.setVisible(false);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Long> recalcIds = new ArrayList<Long>();

	private void onGo() {
		// getting exchange parameters
		List recalcs = parentPane.getSelectedRecalcs();
		IExchangeNode source = parentPane.getSource();
		IExchangeNode destination = parentPane.getDestination();
		if (source == null) {
			String title = GUIMessages.getMessage("comp.general.warning");
			String message = "Source node not defined.";
			MessageDialog.openWarning(getShell(), title, message);
			return;
		}
		if (destination == null) {
			String title = GUIMessages.getMessage("comp.general.warning");
			String message = "Destination node not defined.";
			MessageDialog.openWarning(getShell(), title, message);
			return;
		}
		if (recalcs == null || recalcs.isEmpty()) {
			String title = GUIMessages.getMessage("comp.general.warning");
			String message = "No recalculations are selected.";
			MessageDialog.openWarning(getShell(), title, message);
			return;
		}

		// prepare loader
		Loader loader = null;
		/* File */
		if (source.getType() == IExchangeNode.FILE) {
			MessageDialog.openError(getShell(), "Error", "File source is not yet implemented!");
			return;
		}
		/* Server */
		else if (source.getType() == IExchangeNode.SERVER) {
			ServerLoader serverLoader = new ServerLoader();
			serverLoader.setNode((ServerExchangeNode) source);
			loader = serverLoader;
		}

		// prepare uploader
		Uploader uploader = null;
		/* File */
		if (destination.getType() == IExchangeNode.FILE) {
			MessageDialog.openError(getShell(), "Error", "File destination is not yet implemented!");
			return;
		}
		/* Server */
		else if (destination.getType() == IExchangeNode.SERVER) {
			ServerUploder serverUploder = new ServerUploder();
			serverUploder.setNode((ServerExchangeNode) destination);
			uploader = serverUploder;
		}

		// initiate starting event
		firePropertyChange("start");
		recalcIds.clear();

		// set progress bar and status label visible
		progress.setMinimum(0);
		progress.setMaximum(recalcs.size());
		progress.setVisible(true);
		lblCurrentRecalc.setVisible(true);
		progress.setSelection(0);
		lblCurrentRecalc.setText("Moving...");

		// clear previosu log
		displayLogs(new ArrayList<Log>());

		// whether to check existence
		boolean chechRecalcExistence = !btnOverwrite.getSelection();

		// loop over each recalculation
		int total = recalcs.size();
		for (int i = 0; i < total; i++) {
			Recalc recalc = (Recalc) recalcs.get(i);
			long id = recalc.getId();

			// display
			progress.setSelection(i + 1);
			lblCurrentRecalc.setText(GUITranslator.GEO_ASCII_TO_KA(recalc.getNumber()));
			lblCurrentRecalc.update();

			// process individual recalculation
			try {
				// continue flag
				boolean canContinue = true;

				// #1. whether exists (check only if requeired!)
				if (canContinue && chechRecalcExistence) {
					if (uploader.checkExistence(recalc)) {
						canContinue = false;
						Log log2 = new Log();
						log2.setMessage(String.format("Recalculation #%1$s exists at destination.", recalc.getNumber()));
						log2.setSeverity(Log.ERROR);
						addLog(log2);
					}
				}

				// #2. download recalc
				if (canContinue) {
					loader.downloadFullRecalcInfo(recalc);
				}

				// #3. upload
				if (canContinue) {
					uploader.add(loader.getRecalc(), loader.getRooms(), loader.getFacturaDetails(), loader.getTrashVouchers(), loader.getVoucher());
				}

				// #4. successfully added items
				if (canContinue) {
					Log log2 = new Log();
					log2.setMessage(String.format("Transfer of recalculation #%1$s has completed successfully.", recalc.getNumber()));
					log2.setSeverity(Log.SUCCESS);
					addLog(log2);
					recalcIds.add(id);
				}

				// #5. mark recalculation as finalized
				if (canContinue) {
					loader.finalizeRecalc(recalc);
				}

			} catch (Throwable t) {
				// report exception
				String msg = String.format("Exception while processing voucher #%1$s. Details of the exception: %2$s", recalc.getNumber(), t.toString());
				Log log = new Log();
				log.setSeverity(Log.ERROR);
				log.setMessage(msg);
				addLog(log);
				t.printStackTrace();
			}

		}

		// initiate finishing event
		firePropertyChange("end");

		// hide progress and status labels
		progress.setVisible(false);
		lblCurrentRecalc.setVisible(false);
	}

	private Vector<PropertyChangeListener> listeners = new Vector<PropertyChangeListener>();

	public void addPropertyChangeListener(PropertyChangeListener l) {
		listeners.add(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		listeners.remove(l);
	}

	protected void firePropertyChange(String property) {
		if (property != null) {
			PropertyChangeEvent event = new PropertyChangeEvent(this, property, null, null);
			for (PropertyChangeListener l : listeners)
				l.propertyChange(event);
		}
	}

	protected void blockActions(boolean block) {
		btnGo.setEnabled(!block);
		btnOverwrite.setEnabled(!block);
		btnGo.update();
		btnOverwrite.update();
	}

	public void setParentPane(RecalcExchangePane parentPane) {
		this.parentPane = parentPane;
	}

	public List<Log> logs = new ArrayList<Log>();

	public void addLog(Log log) {
		logs.add(0, log);
		displayLogs(logs);
	}

	public void displayLogs(List<Log> logs) {
		this.logs = logs;
		viewer.setItemCount(logs == null ? 0 : logs.size());
		viewer.setContentProvider(new CommonItemContentProvider(logs));
		viewer.getTable().deselectAll();
		viewer.getTable().select(0);
		viewer.getTable().showSelection();
		viewer.getTable().update();
	}

	public class LogLabelProvider extends LabelProvider {
		@Override
		public Image getImage(Object element) {
			Log log = (Log) element;
			return log.toImage();
		}
	}

	public List<Long> getSuccessfullRecalcIds() {
		return recalcIds;
	}

}
