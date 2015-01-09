package telasi.recutil.gui.comp.exchange;

import telasi.recutil.gui.plugin.ConnectionDescriptor;

public class ServerExchangeNode implements IExchangeNode {
	private ConnectionDescriptor server;
	private String userName;
	private String password;

	public ServerExchangeNode(ConnectionDescriptor server) {
		setServer(server);
	}

	public int getType() {
		return SERVER;
	}

	public ConnectionDescriptor getServer() {
		return server;
	}

	public void setServer(ConnectionDescriptor server) {
		this.server = server;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String toString() {
		if (server != null && userName != null) {
			return userName + " @ " + server.toString();
		} else if (server != null) {
			return server.toString();
		} else {
			return "";
		}
	}

}
