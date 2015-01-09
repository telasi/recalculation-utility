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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Role;
import telasi.recutil.beans.User;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.ejb.Response;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.security.MyUserPasswordUpdateRequest;
import telasi.recutil.service.eclipse.security.PermissionDeleteRequest;
import telasi.recutil.service.eclipse.security.PermissionInsertRequest;
import telasi.recutil.service.eclipse.security.RoleDeleteRequest;
import telasi.recutil.service.eclipse.security.RoleInsertRequest;
import telasi.recutil.service.eclipse.security.RoleSelectRequest;
import telasi.recutil.service.eclipse.security.RoleUpdateRequest;
import telasi.recutil.service.eclipse.security.UserDeleteRequest;
import telasi.recutil.service.eclipse.security.UserInsertRequest;
import telasi.recutil.service.eclipse.security.UserUpdateRequest;

/**
 * This class is used for user managment.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class E1UserManagment {

	//
	// Request proccessors
	//
	
	/**
	 * Process request on role SELECT.
	 */
	public static Response processRoleSelect(Request req, Session session)
	throws RequestException {
		try {
			RoleSelectRequest request = (RoleSelectRequest) req;
			List roles = selectRoles(session);
			request.setRoles(roles);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static User searchUserById(long id, List roles) {
		for (int i = 0; roles != null && i < roles.size(); i++) {
			List users = ((Role) roles.get(i)).getUsers();
			for (int j = 0; users != null && j < users.size(); j++) {
				User user = (User) users.get(j);
				if (user.getId() == id)
					return user;
			}
		}
		return null;
	}
	
	public static User searchUserByUsername(String username, List roles) {
		for (int i = 0; roles != null && i < roles.size(); i++) {
			List users = ((Role) roles.get(i)).getUsers();
			for (int j = 0; users != null && j < users.size(); j++) {
				User user = (User) users.get(j);
				if (username.equals(user.getUserName()))
					return user;
			}
		}
		return null;
	}
	
	/**
	 * Process request on role INSERT.
	 */
	public static Response processRoleInsert(Request req,
			Session session) throws RequestException {
		try {
			RoleInsertRequest request = (RoleInsertRequest) req;
			Role role = request.getRole();
			long id = insertRole(role, session);
			request.setId(id);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	/**
	 * Process request on role UPDATE.
	 */
	public static Response processRoleUpdate(Request req,
			Session session) throws RequestException {
		try {
			RoleUpdateRequest request = (RoleUpdateRequest) req;
			Role role = request.getRole();
			updateRole(role, session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	/**
	 * Process request on role DELETE.
	 */
	public static Response processRoleDelete(Request req,
			Session session) throws RequestException {
		try {
			RoleDeleteRequest request = (RoleDeleteRequest) req;
			Role role = request.getRole();
			deleteRole(role, session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	/**
	 * Process request on user INSERT.
	 */
	public static Response processUserInsert(Request req,
			Session session) throws RequestException {
		try {
			UserInsertRequest request = (UserInsertRequest) req;
			User user = request.getUser();
			request.setId(insertUser(user, session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	/**
	 * Process request on user UPDATE.
	 */
	public static Response processUserUpdate(Request req,
			Session session) throws RequestException {
		try {
			UserUpdateRequest request = (UserUpdateRequest) req;
			User user = request.getUser();
			updateUser(user, session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	/**
	 * Process request on user DELETE.
	 */
	public static Response processUserDelete(Request req,
			Session session) throws RequestException {
		try {
			UserDeleteRequest request = (UserDeleteRequest) req;
			User user = request.getUser();
			deleteUser(user, session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	/**
	 * Process request on permission INSERT.
	 */
	public static Response processPermissionInsert(Request req,
			Session session) throws RequestException {
		try {
			PermissionInsertRequest request = (PermissionInsertRequest) req;
			Role role = request.getRole();
			int actionId = request.getAction();
			insertPermission(role, actionId, session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	/**
	 * Process request on permission DELETE.
	 */
	public static Response processPermissionDelete(Request req,
			Session session) throws RequestException {
		try {
			PermissionDeleteRequest request = (PermissionDeleteRequest) req;
			Role role = request.getRole();
			int actionId = request.getAction();
			deletePermission(role, actionId, session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	/**
	 * Process request on permission INSERT.
	 */
	public static Response processMyPasswordUpdate(Request req,
			Session session) throws RequestException {
		try {
			MyUserPasswordUpdateRequest request = (MyUserPasswordUpdateRequest) req;
			updateMyPassword(request.getUserId(), request.getPassword(),
					session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	//
	// Helper methods
	//

	/**
	 * Get roles from database.
	 */
	public static List selectRoles(Session session) throws SQLException,
			IOException {
		String sql1 = SQLReader.readSQL("security/role_users_select.sql");
		String sql2 = SQLReader.readSQL("security/role_permissions_select.sql");
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		ResultSet res1 = null;
		ResultSet res2 = null;
		try {
			st1 = session.getConnection().prepareStatement(sql1);
			st2 = session.getConnection().prepareStatement(sql2);
			res1 = st1.executeQuery();
			res2 = st2.executeQuery();
			List roles = new ArrayList();
			Role role = null;
			// create roles and users
			while (res1.next()) {
				long roleId = res1.getLong("ROLE_ID");
				Number userIdNumber = (Number) res1.getObject("USER_ID");
				boolean createNew = role == null || role.getId() != roleId;
				boolean userExists = userIdNumber != null;
				if (createNew) {
					if (role != null) {
						roles.add(role);
					}
					role = new Role(roleId);
					role.setName(res1.getString("ROLE_NAME"));
					role.setDescription(res1.getString("ROLE_DESC"));
					role.setEnabled(res1.getInt("ROLE_ENABLED") == 0);
				}
				if (userExists) {
					User user = new User(userIdNumber.longValue());
					user.setUserName(res1.getString("USER_NAME"));
					user.setFullName(res1.getString("USER_FULLNAME"));
					user.setEnabled(res1.getInt("USER_ENABLED") == 0);
					user.setNumber(res1.getString("USER_NUMBER"));
					user.setSequence(res1.getInt("LAST_SEQUENCE"));
					Number advisorId = (Number) res1.getObject("ADVISOR");
					user.setAdvisor(advisorId == null ? null : new User(advisorId.longValue()));
					role.addUser(user);
				}
			}
			if (role != null) {
				roles.add(role);
			}
			// populate permissions
			while (res2.next()) {
				long roleId = res2.getLong("ROLE_ID");
				int actionId = res2.getInt("ACTION_ID");
				for (int i = 0; i < roles.size(); i++) {
					Role r = (Role) roles.get(i);
					if (r.getId() == roleId) {
						r.addAction(actionId);
						break;
					}
				}
			}
			return roles;
		} finally {
			if (res1 != null) {
				try {
					res1.close();
				} catch (Exception ex) {
				}
				res1 = null;
			}
			if (st1 != null) {
				try {
					st1.close();
				} catch (Exception ex) {
				}
				st1 = null;
			}
			if (res2 != null) {
				try {
					res2.close();
				} catch (Exception ex) {
				}
				res2 = null;
			}
			if (st2 != null) {
				try {
					st2.close();
				} catch (Exception ex) {
				}
				st2 = null;
			}
		}
	}

	/**
	 * Insert new role.
	 */
	public static long insertRole(Role role, Session session)
			throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/role_insert.sql");
		CallableStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setString(1, role.getName().trim());
			if (role.getDescription() == null) {
				st.setNull(2, Types.VARCHAR);
			} else {
				st.setString(2, role.getDescription().trim());
			}
			st.setInt(3, role.isEnabled() ? 0 : 1);
			st.setLong(4, -1L);
			st.registerOutParameter(4, Types.NUMERIC);
			st.execute();
			return st.getLong(4);
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

	/**
	 * Update the role.
	 */
	public static void updateRole(Role role, Session session)
			throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/role_update.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, role.getId());
			st.setString(2, role.getName());
			if (role.getDescription() == null
					|| role.getDescription().trim().length() == 0) {
				st.setNull(3, Types.VARCHAR);
			} else {
				st.setString(3, role.getDescription());
			}
			st.setInt(4, role.isEnabled() ? 0 : 1);
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

	/**
	 * Delete role.
	 */
	public static void deleteRole(Role role, Session session)
			throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/role_delete.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, role.getId());
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * Insert user.
	 */
	public static long insertUser(User user, Session session)
			throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/user_insert.sql");
		CallableStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setString(1, user.getUserName());
			st.setString(2, user.getPassword());
			st.setString(3, user.getFullName());
			st.setLong(4, user.getRole().getId());
			st.setInt(5, user.isEnabled() ? 0 : 1);
			if (user.getAdvisor() == null) {
				st.setNull(6, Types.NUMERIC);
			} else {
				st.setLong(6, user.getAdvisor().getId());
			}
			st.setString(7, user.getNumber());
			st.setLong(8, -1L);
			st.registerOutParameter(8, Types.NUMERIC);
			st.execute();
			return st.getLong(8);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * Update user.
	 */
	public static void updateUser(User user, Session session)
			throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/user_update.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, user.getId());
			if (user.getPassword() == null
					|| user.getPassword().trim().length() == 0) {
				st.setNull(2, Types.VARCHAR);
			} else {
				st.setString(2, user.getPassword());
			}
			st.setString(3, user.getFullName().trim());
			st.setLong(4, user.getRole().getId());
			st.setInt(5, user.isEnabled() ? 0 : 1);
			if (user.getAdvisor() == null) {
				st.setNull(6, Types.NUMERIC);
			} else {
				st.setLong(6, user.getAdvisor().getId());
			}
			st.setString(7, user.getNumber());
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

	/**
	 * Delete user.
	 */
	public static void deleteUser(User user, Session session)
			throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/user_delete.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, user.getId());
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

	/**
	 * Insert permission.
	 */
	public static void insertPermission(Role role, int actionId, Session session)
			throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/permission_insert.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, role.getId());
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

	/**
	 * Delete permission.
	 */
	public static void deletePermission(Role role, int actionId, Session session)
			throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/permission_delete.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, role.getId());
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

	public static void updateMyPassword(long userId, String password,
			Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("security/my_password_update.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, userId);
			st.setString(2, password);
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

	public static User getUser(long userId, Session session) throws SQLException {
		String sql = "SELECT USER_NAME, USER_FULLNAME, USER_NUMBER FROM RECUT.USERS WHERE USER_ID=?";
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, userId);
			res = st.executeQuery();
			res.next();
			User user = new User(userId);
			user.setUserName(res.getString("USER_NAME"));
			user.setFullName(res.getString("USER_FULLNAME"));
			user.setNumber(res.getString("USER_NUMBER"));
			return user;
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (Exception ex) {
				}
				res = null;
			}
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
