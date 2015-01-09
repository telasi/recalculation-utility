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
package telasi.recutil.service.eclipse;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.Response;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.service.ActionConstants;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.security.ConnectRequest;
import telasi.recutil.service.eclipse.security.MyUserPasswordUpdateRequest;

/**
 * This class is used for processing Security issues. E1 processor calls
 * assertPermission method automatically. Threfore there is no need for calling
 * it manually.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class E1Security {

	/**
	 * Process connection request.
	 */
	public static Response processConnection(Request request, Session session) throws RequestException {
		ConnectRequest connectRequest = (ConnectRequest) request;
		String userName = connectRequest.getUserName();
		String password = connectRequest.getPassword();
		long userId = assertPermission(userName, password,
				ActionConstants.ACT_CONNECT, request, session);
		connectRequest.setUserId(userId);
		return new DefaultEJBResponse(connectRequest);
	}

	/**
	 * This method assert whether user with given password has permission on
	 * given action. User also should have permission on CONNECT action. This
	 * methods throws RequestException when assertion fails.
	 */
	public static long assertPermission(String userName, String password,
			int actionId, Request request, Session session)
			throws RequestException {
		try {
			long userId = getUserId(userName, password, session);
			if (userId == -1) {
				throw new RequestException(
						"Login fails. Illegal user name/password.");
			}
			if (actionId != ActionConstants.ACT_CONNECT) {
				assertPermission(userId, ActionConstants.ACT_CONNECT, session);
			}
			// set user ID when my password update
			if (actionId == ActionConstants.ACT_MY_USER_PASSWORD_UPDATE) {
				((MyUserPasswordUpdateRequest) request).setUserId(userId);
			}
			assertPermission(userId, actionId, session);
			return userId;
		} catch (Exception ex) {
			if (ex instanceof RequestException) {
				throw (RequestException) ex;
			} else {
				throw new RequestException(ex.toString(), ex);
			}
		}
	}

	private static void assertPermission(long userId, int actionId,
			Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/permission_assert.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, userId);
			st.setInt(2, actionId);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	private static long getUserId(String userName, String password,
			Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/user_get_id.sql");
		CallableStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setString(1, userName.trim());
			st.setString(2, password.trim());
			st.setLong(3, -1L);
			st.registerOutParameter(3, Types.NUMERIC);
			st.execute();
			Number id = (Number) st.getObject(3);
			return id == null ? -1 : id.longValue();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void addLog(long userId, int actionId, String s1, String s2,
			Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/add_log.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, userId);
			st.setInt(2, actionId);
			if (s1 == null || s1.trim().length() == 0) {
				st.setNull(3, Types.VARCHAR);
			} else {
				st.setString(3, s1);
			}
			if (s2 == null || s2.trim().length() == 0) {
				st.setNull(4, Types.VARCHAR);
			} else {
				st.setString(4, s2);
			}
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}
}
