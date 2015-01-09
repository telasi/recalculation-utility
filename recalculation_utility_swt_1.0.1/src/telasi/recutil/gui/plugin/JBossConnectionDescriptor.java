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

/**
 * Description for JBoss application server.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * Jun, 2007
 */
public class JBossConnectionDescriptor extends ConnectionDescriptor {
	private static final long serialVersionUID = 5741683653990271463L;
	private String name;
	private String host = "localhost";
	private String port = "1099";

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUrl() {
		StringBuffer bf = new StringBuffer();
		bf.append("jnp://");
		bf.append(host);
		bf.append(":");
		bf.append(port);
		return bf.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		String url = host == null || port == null ? null : getUrl();
		StringBuffer b = new StringBuffer();
		if ((name == null || name.trim().length() == 0)
				&& (url == null || url.trim().length() == 0)) {
			// nothing
		} else if (name == null || name.trim().length() == 0) {
			b.append(url);
		} else if (url == null || url.trim().length() == 0) {
			b.append(name);
		} else {
			b.append(name);
			b.append(" (");
			b.append(url);
			b.append(")");
		}
		return b.toString();
	}

}
