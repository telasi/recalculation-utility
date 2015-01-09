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
package telasi.recutil.gui.plugin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;

/**
 * General settings manager.
 * 
 * @author dimitri
 */
public class Settings  {

	public static String getLastUserName() {
		IDialogSettings st = Plugin.getDefault().getDialogSettings();
		String userName = null;
		try {
			userName = st.get("connection.last.userName");
		} catch (Throwable t) {}
		return userName;
	}
	
	public static void setLastUserName(String userName) {
		IDialogSettings st = Plugin.getDefault().getDialogSettings();
		try {
			st.put("connection.last.userName", userName);
		} catch (Throwable t) {}
	}
	
	public static ConnectionDescriptor getLastConnectionDescriptor() {
		ConnectionDescriptor descr = null;
		IDialogSettings st = Plugin.getDefault().getDialogSettings();
		try {
			String type = st.get("connection.last.type");
			if ("oracle".equals(type)) {
				OracleConnectionDescriptor ora = new OracleConnectionDescriptor();
				ora.setName(st.get("connection.last.name"));
				ora.setHost(st.get("connection.last.host"));
				ora.setPort(st.get("connection.last.port"));
				ora.setServiceName(st.get("connection.last.service"));
				descr = ora;
			} else if ("jboss".equals(type)) {
				JBossConnectionDescriptor jboss = new JBossConnectionDescriptor();
				jboss.setName(st.get("connection.last.name"));
				jboss.setHost(st.get("connection.last.host"));
				jboss.setPort(st.get("connection.last.port"));
				descr = jboss;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return descr;
	}
	
	public static void setLastConnectionDescriptor(ConnectionDescriptor descr) {
		IDialogSettings st = Plugin.getDefault().getDialogSettings();
		try {
			if (descr instanceof OracleConnectionDescriptor) {
				OracleConnectionDescriptor ora = (OracleConnectionDescriptor) descr;
				st.put("connection.last.type", "oracle");
				st.put("connection.last.name", ora.getName());
				st.put("connection.last.host", ora.getHost());
				st.put("connection.last.port", ora.getPort());
				st.put("connection.last.service", ora.getServiceName());
			} else if (descr instanceof JBossConnectionDescriptor) {
				JBossConnectionDescriptor jboss = (JBossConnectionDescriptor) descr;
				st.put("connection.last.type", "jboss");
				st.put("connection.last.name", jboss.getName());
				st.put("connection.last.host", jboss.getHost());
				st.put("connection.last.port", jboss.getPort());
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public static List<ConnectionDescriptor> getConnectionDescriptors() {
		List<ConnectionDescriptor> descrs = new ArrayList<ConnectionDescriptor>();
		IDialogSettings st = Plugin.getDefault().getDialogSettings();
		try {
			int count = st.getInt("connections.count");
			for (int i = 0; i < count; i++) {
				String type = st.get("connections.type[" + i + "]");
				if ("oracle".equals(type)) {
					OracleConnectionDescriptor ora = new OracleConnectionDescriptor();
					ora.setName(st.get("connections.name[" + i + "]"));
					ora.setHost(st.get("connections.host[" + i + "]"));
					ora.setPort(st.get("connections.port[" + i + "]"));
					ora.setServiceName(st.get("connections.service[" + i + "]"));
					descrs.add(ora);
				} else if ("jboss".equals(type)) {
					JBossConnectionDescriptor jboss = new JBossConnectionDescriptor();
					jboss.setName(st.get("connections.name[" + i + "]"));
					jboss.setHost(st.get("connections.host[" + i + "]"));
					jboss.setPort(st.get("connections.port[" + i + "]"));
					descrs.add(jboss);
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return descrs;
	}

	public static void setConnectionDescriptors(List<ConnectionDescriptor> descrs) {
		IDialogSettings st = Plugin.getDefault().getDialogSettings();
		int count = descrs == null ? 0 : descrs.size();
		try {
			st.put("connections.count", count);
			for (int i = 0; i < count; i++) {
				ConnectionDescriptor descr = descrs.get(i);
				if (descr instanceof OracleConnectionDescriptor) {
					OracleConnectionDescriptor ora = (OracleConnectionDescriptor) descr;
					st.put("connections.type[" + i + "]", "oracle");
					st.put("connections.name[" + i + "]", ora.getName());
					st.put("connections.host[" + i + "]", ora.getHost());
					st.put("connections.port[" + i + "]", ora.getPort());
					st.put("connections.service[" + i + "]", ora.getServiceName());
				} else if (descr instanceof JBossConnectionDescriptor) {
					JBossConnectionDescriptor ora = (JBossConnectionDescriptor) descr;
					st.put("connections.type[" + i + "]", "jboss");
					st.put("connections.name[" + i + "]", ora.getName());
					st.put("connections.host[" + i + "]", ora.getHost());
					st.put("connections.port[" + i + "]", ora.getPort());
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
}
