package telasi.recutil.gui.comp.exchange;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

import telasi.recutil.gui.comp.security.UrlSelectorDialog;

public class NodePicker extends Composite {
	private Text txtSource;
	private Button btnSelect;
	private IExchangeNode node;
	private boolean destination = false;
	private boolean blocked = false;

	public NodePicker(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			GridLayout thisLayout = new GridLayout();
			this.setLayout(thisLayout);
			{
				GridData txtConnectionLData = new GridData();
				txtConnectionLData.horizontalAlignment = GridData.FILL;
				txtConnectionLData.grabExcessHorizontalSpace = true;
				txtSource = new Text(this, SWT.READ_ONLY | SWT.BORDER);
				txtSource.setLayoutData(txtConnectionLData);
			}
			{
				btnSelect = new Button(this, SWT.PUSH | SWT.CENTER);
				btnSelect.setText("...");
				btnSelect.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						onSelect();
					}
				});
			}
			thisLayout.marginHeight = 0;
			thisLayout.marginWidth = 0;
			thisLayout.numColumns = 2;
			this.layout();
			reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onSelect() {
		// first select type
		NodeTypeSelector typeDialog = new NodeTypeSelector(getShell(), SWT.NONE);
		typeDialog.setDestination(destination);
		if (node != null)
			typeDialog.setType(node.getType());
		typeDialog.open();
		if (!typeDialog.isApproved())
			return;
		// get server
		if (typeDialog.getType() == IExchangeNode.SERVER) {
			UrlSelectorDialog dialog = new UrlSelectorDialog(getShell(), SWT.NONE);
			dialog.open();
			if (!dialog.isApproved()) 
				return;
			ServerExchangeNode src = new ServerExchangeNode(dialog.getDescriptor());
			SimpleLoginDialog login = new SimpleLoginDialog(getShell(), SWT.NONE);
			login.setDescriptor(dialog.getDescriptor());
			login.open();
			if (!login.isApproved())
				return;
			src.setUserName(login.getUserName());
			src.setPassword(login.getPassword());
			// TODO: need to initialize userName/password
			setNode(src);
		}
		// get file
		else if (typeDialog.getType() == IExchangeNode.FILE) {
			FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
			dialog.setText("Select File to Import");
			dialog.open();
			String path = dialog.getFilterPath();
			String name = dialog.getFileName();
			if (path == null || name == null || name.trim().length() == 0)
				return;
			String fullPath = path + System.getProperty("file.separator") + name;
			setNode(new FileExchangeNode(new File(fullPath)));
		}
		// not supported option...
		else {
			throw new UnsupportedOperationException();
		}
		reset();
	}

	void reset() {
		txtSource.setText(node == null ? "" : node.toString());
		validateView();
		// notify listeners
		PropertyChangeEvent evt = new PropertyChangeEvent(this, "reset", null, null);
		for (PropertyChangeListener l : listeners)
			l.propertyChange(evt);
	}

	public IExchangeNode getNode() {
		return node;
	}

	public void setNode(IExchangeNode node) {
		this.node = node;
	}

	private Vector<PropertyChangeListener> listeners = new Vector<PropertyChangeListener>();

	public void addPropertyChangeListener(PropertyChangeListener l) {
		listeners.add(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		listeners.remove(l);
	}

	public boolean isDestination() {
		return destination;
	}

	public void setDestination(boolean destination) {
		this.destination = destination;
	}

	private void validateView() {
		btnSelect.setEnabled(!blocked);
		
	}
	
	protected void blockActions(boolean block) {
		this.blocked = block;
		validateView();
		btnSelect.update();
	}
	
}
