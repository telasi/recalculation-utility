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
package telasi.recutil.gui.ejb;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.ejb.Response;
import telasi.recutil.gui.app.Application;

/**
 * This is a class mainly used by RecalculationUtility
 * client part. It takes URL from Application class.
 * 
 * @author dimitri
 */
public class DefaultRecutilClient {
	private static RecutilClient client;

	public static void destroy() {
		if (client != null) {
			client.destroy();
			client = null;
		}
	}

	public static Response processRequest(Request request)
	throws RemoteException, CreateException, RequestException, NamingException {
		if (client == null) client = new RecutilClient(Application.PROVIDER_URL);
		return client.processRequest(request);
	}

	public static List processRequests(List requests)
	throws RemoteException, CreateException, RequestException, NamingException {
		if (client == null) client = new RecutilClient(Application.PROVIDER_URL);
		return client.processRequests(requests);
	}

}
