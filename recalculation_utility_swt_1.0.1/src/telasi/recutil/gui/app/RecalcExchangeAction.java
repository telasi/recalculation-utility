package telasi.recutil.gui.app;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import telasi.recutil.gui.views.RecalcExchangeView;

/**
 * Administrate recalculations.
 * @author dimitri
 */
public class RecalcExchangeAction extends Action {

	private final IWorkbenchWindow window;

	private int instanceNum = 10;

	RecalcExchangeAction(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run() {
		try {
			window.getActivePage().showView(RecalcExchangeView.ID_VIEW, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
		} catch (PartInitException e) {
			MessageDialog.openError(window.getShell(), "Error", "Error opening view:" + e.getMessage());
		}
	}
	
}
