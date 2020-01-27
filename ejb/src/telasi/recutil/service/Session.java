/*
 * Copyright 2007, Dimitri Kurashvili (dimakura@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package telasi.recutil.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Session, which provides you with SQL connection.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Oct, 2006
 */
public class Session {
	private static final boolean TEST = true;
	private static String DEFAULT_URL = TEST ? "jdbc:oracle:thin:@192.168.1.247:1521:ORCL" : "jdbc:oracle:thin:@10.235.170.10:1521:BLNG";
//  private static String DEFAULT_URL = TEST ? "jdbc:oracle:thin:@192.168.1.247:1521:ORCL" : "jdbc:oracle:thin:@192.168.1.10:1521:ORCL";
	private static final String PASSWORD = TEST ? "mtXzlkJ45832xxyzuitv" : "azpT883xyi99";

	private static final String USER = "recut";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DATASOURCE_NAME = "java:/RecutDS";

	private Connection connection;
	private String url;
	private static boolean useDirectConnection;

	public static void setDefaultUrl(String url) {
		DEFAULT_URL = url;
	}

	public static String getDefaultUrl() {
		return DEFAULT_URL;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// create/retrive connection
	public Connection getConnection() throws SQLException {
		// check previous connection
		try {
			if (connection != null && !connection.isClosed())
				return connection;
		} catch (Exception ex) {
		}

		if (useDirectConnection) {
			// loading driver
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException ex) {
				throw new SQLException(ex.toString());
			}
			// create connection
			connection = DriverManager.getConnection(url == null ? DEFAULT_URL : url, USER, PASSWORD);
			connection.setAutoCommit(false);
		} else {
			try {
				InitialContext init = new InitialContext();
				DataSource ds = (DataSource) init.lookup(DATASOURCE_NAME);
				connection = ds.getConnection();
				// autoCommit = connection.getAutoCommit();
				connection.setAutoCommit(false);
			} catch (NamingException nme) {
				// throw new SQLException(nme.toString());
			}
		}
		// connection
		return connection;
	}

	// make finalization
	public void close() {
		// finalization code
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (Exception ex) {
		}
	}

	// checks whether connection exists
	public boolean isConnected() throws SQLException {
		return connection != null && !connection.isClosed();
	}

	// commit changes
	public void commit() throws SQLException {
		if (isConnected()) {
			connection.commit();
		} else {
			throw new SQLException("Connection does not exist");
		}
	}

	// rollback changes
	public void rollback() throws SQLException {
		if (isConnected()) {
			connection.rollback();
		} else {
			throw new SQLException("Connection does not exist");
		}
	}

	public static boolean isUseDirectConnection() {
		return useDirectConnection;
	}

	public static void setUseDirectConnection(boolean direct) {
		useDirectConnection = direct;
	}

}
