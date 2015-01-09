package telasi.recutil.gui.app;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import telasi.recutil.gui.comp.tpowner.TpOwnerCalculationView;

public class TpOwnerCalculationAction extends Action {

	private final IWorkbenchWindow window;

	private int instanceNum = 0;

	TpOwnerCalculationAction(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run() {
		try {
			window.getActivePage().showView(TpOwnerCalculationView.ID_VIEW, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
		} catch (PartInitException e) {
			MessageDialog.openError(window.getShell(), "Error", "Error opening view:" + e.getMessage());
		}
	}

}
