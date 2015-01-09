/*
 *   Copyright (C) 2006 by JSC Telasi
 *   http://www.telasi.ge
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the
 *   Free Software Foundation, Inc.,
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package telasi.recutil.gui.app;

import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import telasi.recutil.beans.User;
import telasi.recutil.gui.comp.security.LoginDialog;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.plugin.Settings;
import telasi.recutil.gui.utils.GUIMessages;

public class Application implements IPlatformRunnable {
	public static final String INITIAL_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
	public static final String URL_PKG_PREFIXES = "org.jboss.naming:org.jnp.interfaces";
	public static final String RECUT_JNDI_NAME = "ejb/RecutilService";
	public static String PROVIDER_URL;
	public static String PROVIDER_URL2;
	public static String USER_NAME = Settings.getLastUserName();
	public static User USER;
	public static String PASSWORD;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IPlatformRunnable#run(java.lang.Object)
	 */
	public Object run(Object args) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IPlatformRunnable.EXIT_RESTART;
			}
			return IPlatformRunnable.EXIT_OK;
		} finally {
			display.dispose();
		}
}

	public static boolean isConnected() {
		boolean userExists = !(USER_NAME == null || USER_NAME.trim().length() == 0);
		boolean passwordExists = !(PASSWORD == null || PASSWORD.trim().length() == 0);
		return userExists && passwordExists;
	}

	public static void updateWindowView(IWorkbenchWindow window) {
		String title = GUIMessages.getMessage("application.title");
		if (isConnected()) {
			title = USER_NAME + " @ " + PROVIDER_URL2 + " -- " + title;
		}
		window.getShell().setText(title);
	}

	public static boolean connect(IWorkbenchWindow window) {
		boolean resp = false;
		LoginDialog dialog = new LoginDialog(window.getShell(), SWT.NONE);
		dialog.open();
		if (dialog.isApproved()) {
			USER_NAME = dialog.getUserName();
			Settings.setLastUserName(USER_NAME);
			PASSWORD = dialog.getPassword();
			resp = true;
			try {
				Cache.refreshRoleList(true);
				USER = Cache.findUserByUserName(USER_NAME);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		updateWindowView(window);
		return resp;
	}

	public static void disconnect(IWorkbenchWindow window) {
		PASSWORD = null;
		USER = null;
		updateWindowView(window);
		DefaultRecutilClient.destroy();
	}

	public static boolean validateConnection() {
		return validateConnection(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

	public static boolean validateConnection(IWorkbenchWindow window) {
		// check whether already connected
		if (isConnected())
			return true;

		// ask before try connection
		String title = GUIMessages.getMessage("comp.general.confirm");
		String message = GUIMessages.getMessage("application.connectvalidation.message");
		boolean resp = MessageDialog.openQuestion(window.getShell(), title, message);
		if (!resp) return false;

		// connect
		return connect(window);
	}

	public static void clearCaches(IWorkbenchWindow window) {
		// individual caches
		boolean roleEmpty = Cache.ROLE_LIST == null;
		boolean operationsEmpty = Cache.OPERATION_TYPES_LIST == null;
		boolean metersEmpty = Cache.METER_LIST == null;
		boolean tariffEmpty = Cache.TARIFF_LIST == null;

		// cache as global
		boolean cacheEmpty = roleEmpty && operationsEmpty && metersEmpty && tariffEmpty;
		if (cacheEmpty) {
			String text = GUIMessages.getMessage("application.clearcache.emptycache");
			MessageDialog.openWarning(window.getShell(), text, text);
			return;
		}

		// confirm cache clear
		String title = GUIMessages.getMessage("comp.general.confirm");
		String message = GUIMessages.getMessage("application.cleatcache.confirmation");
		boolean resp = MessageDialog.openQuestion(window.getShell(), title, message);
		if (!resp) return;

		// clear individual caches
		if (!roleEmpty) {
			Cache.ROLE_LIST.clear();
			Cache.ROLE_LIST = null;
			if (Cache.USERS_MAP_BY_ID != null) {
				Cache.USERS_MAP_BY_ID.clear();
				Cache.USERS_MAP_BY_ID = null;
			}
		}
		if (!operationsEmpty) {
			Cache.OPERATION_TYPES_LIST.clear();
			Cache.OPERATION_TYPES_LIST = null;
			if (Cache.OPERATIONS_MAP_BY_ID != null) {
				Cache.OPERATIONS_MAP_BY_ID.clear();
				Cache.OPERATIONS_MAP_BY_ID = null;
			}
			if (Cache.REGULAR_OPERATIONS_LIST != null) {
				Cache.REGULAR_OPERATIONS_LIST.clear();
				Cache.REGULAR_OPERATIONS_LIST = null;
			}
		}
		if (!metersEmpty) {
			Cache.METER_LIST.clear();
			Cache.METER_LIST = null;
			if (Cache.METER_MAP_BY_ID != null) {
				Cache.METER_MAP_BY_ID.clear();
				Cache.METER_MAP_BY_ID = null;
			}
		}
		if (!tariffEmpty) {
			Cache.TARIFF_LIST.clear();
			Cache.TARIFF_LIST = null;
			if (Cache.TARIFF_BY_ID != null) {
				Cache.TARIFF_BY_ID.clear();
				Cache.TARIFF_BY_ID = null;
			}
		}
}

}
