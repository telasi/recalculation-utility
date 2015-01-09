package telasi.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;
import telasi.recutil.gui.app.Application;

public class TestConnectToEJB extends TestCase {

	@SuppressWarnings("unchecked")
	public void testEJBConnection() throws Exception {
		Hashtable props = new Hashtable();
		props.put(Context.INITIAL_CONTEXT_FACTORY, Application.INITIAL_CONTEXT_FACTORY);
		props.put(Context.URL_PKG_PREFIXES, Application.URL_PKG_PREFIXES);
		props.put(Context.PROVIDER_URL, "jnp://192.168.1.22:1099");
		Context ctx = new InitialContext(props);
		Object o = ctx.lookup("ejb/RecutilService");
	}
}
