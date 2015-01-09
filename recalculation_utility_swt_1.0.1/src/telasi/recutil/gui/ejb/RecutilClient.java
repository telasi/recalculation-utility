package telasi.recutil.gui.ejb;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import telasi.recutil.ejb.IRequestProcessor;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.ejb.Response;
import telasi.recutil.ejb.interfaces.RecutilService;
import telasi.recutil.ejb.interfaces.RecutilServiceHome;
import telasi.recutil.gui.app.Application;
import telasi.recutil.service.RequestProcessorFactory;
import telasi.recutil.service.Session;

/**
 * Client of recalculation utility.
 * 
 * @author dimitri
 */
public class RecutilClient {
	private RecutilServiceHome home;
	private IRequestProcessor processor;
	private String url;

	public RecutilClient(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	@SuppressWarnings("unchecked")
	private Context getInitialContext() throws NamingException {
		Hashtable props = new Hashtable();
		props.put(Context.INITIAL_CONTEXT_FACTORY, Application.INITIAL_CONTEXT_FACTORY);
		props.put(Context.URL_PKG_PREFIXES, Application.URL_PKG_PREFIXES);
		props.put(Context.PROVIDER_URL, url);
		Context ctx = new InitialContext(props);
		return ctx;
	}
	
	private RecutilServiceHome createHome() throws NamingException {
		Context ctx = getInitialContext();
		Object o = ctx.lookup(Application.RECUT_JNDI_NAME);
		RecutilServiceHome intf = (RecutilServiceHome) PortableRemoteObject.narrow(o, RecutilServiceHome.class);
		
		return intf;
	}
	
	private void initialize() throws NamingException {
		if (home == null) {
			home = createHome();
		}
	}

	public void destroy() {
		if (home != null) {
			try {
				home.remove(home.getHomeHandle());
			} catch (Throwable t) {
				//t.printStackTrace();
			}
		}
		home = null;
		processor = null;
	}
	
	public Response processRequest(Request request)
	throws RemoteException, CreateException, RequestException, NamingException {
		if (url == null) {
			throw new IllegalArgumentException("URL not defined");
		} else if (url.startsWith("jnp://")) {
			initialize();
			RecutilService recut = home.create();
			return recut.processEJBRequest(request);
		} else if (url.startsWith("jdbc:oracle:thin:@")) {
			if (processor == null) {
				Session.setDefaultUrl(url);
				Session.setUseDirectConnection(true);
				processor = RequestProcessorFactory.createRequestProcessor(Request.FINAL);
			}
			return processor.processRequest(request);
		} else {
			throw new IllegalArgumentException("URL format " + url + " is not yet supported.");
		}
	}

	public List processRequests(List requests)
	throws RemoteException, CreateException, RequestException, NamingException {
		if (url == null) {
			throw new IllegalArgumentException("URL not defined");
		} else if (url.startsWith("jnp://")) {
			initialize();
			RecutilService recut = home.create();
			return recut.processDatabaseRequests(requests);
		} else if (url.startsWith("jdbc:oracle:thin:@")) {
			if (processor == null) {
				Session.setDefaultUrl(url);
				Session.setUseDirectConnection(true);
				processor = RequestProcessorFactory.createRequestProcessor(Request.ECLIPSE_CLIENT_1_0_3);
			}
			return processor.processRequests(requests);
		} else {
			throw new IllegalArgumentException("URL format "
					+ url + " is not yet supported.");
		}
	}
	
}
