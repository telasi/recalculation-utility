/*
 *   Copyright (C) 2006, 2007 by JSC Telasi
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.instcp.InstCpAfter2003Record;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.Response;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.instcp.InstCpHistoryAfter2003DeleteRequest;
import telasi.recutil.service.eclipse.instcp.InstCpHistoryAfter2003InsertRequest;
import telasi.recutil.service.eclipse.instcp.InstCpHistoryAfter2003Request;
import telasi.recutil.service.eclipse.instcp.InstCpHistoryAfter2003UpdateRequest;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Jan, 2007
 */
public class E1OthersManager {

	public static Response selectInstcpAfter2003(Request req,
			Session session) throws RequestException {
		try {
			InstCpHistoryAfter2003Request request = (InstCpHistoryAfter2003Request) req;
			request.setHistory(getInstcpHistoryAfter2003(session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response insertInstcpAfter2003(Request req,
			Session session) throws RequestException {
		try {
			InstCpHistoryAfter2003InsertRequest request = (InstCpHistoryAfter2003InsertRequest) req;
			insertRecord_for_InstcpHistoryAfter2003(request.getRecord(),
					session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response deleteInstcpAfter2003(Request req,
			Session session) throws RequestException {
		try {
			InstCpHistoryAfter2003DeleteRequest request = (InstCpHistoryAfter2003DeleteRequest) req;
			deleteRecord_for_InstcpHistoryAfter2003(request.getRecord(),
					session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response updateInstcpAfter2003(Request req,
			Session session) throws RequestException {
		try {
			InstCpHistoryAfter2003UpdateRequest request = (InstCpHistoryAfter2003UpdateRequest) req;
			updateRecord_for_InstcpHistoryAfter2003(request.getRecord(),
					session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////

	public static List getInstcpHistoryAfter2003(Session session)
			throws SQLException, IOException {

		String sql = SQLReader.readSQL("other/instcp/instcp_after2003.sql");
		PreparedStatement st = null;
		ResultSet res = null;

		try {
			st = session.getConnection().prepareStatement(sql);
			res = st.executeQuery();
			List history = new ArrayList();
			while (res.next()) {
				int year = res.getInt("YEAR");
				int month = res.getInt("MONTH");
				double instcp = res.getDouble("INST_CP");
				InstCpAfter2003Record record = new InstCpAfter2003Record();
				record.setYear(year);
				record.setMonth(month);
				record.setInstcp(instcp);
				history.add(record);
			}
			return history;
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

	public static void insertRecord_for_InstcpHistoryAfter2003(
			InstCpAfter2003Record record, Session session) throws SQLException,
			IOException {

		String sql = SQLReader
				.readSQL("other/instcp/insert_instcp_after2003.sql");
		PreparedStatement st = null;

		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, record.getYear());
			st.setInt(2, record.getMonth());
			st.setDouble(3, record.getInstcp());
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

	public static void updateRecord_for_InstcpHistoryAfter2003(
			InstCpAfter2003Record record, Session session) throws SQLException,
			IOException {

		String sql = SQLReader
				.readSQL("other/instcp/update_instcp_after2003.sql");
		PreparedStatement st = null;

		try {
			st = session.getConnection().prepareStatement(sql);
			st.setDouble(1, record.getInstcp());
			st.setInt(2, record.getYear());
			st.setInt(3, record.getMonth());
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

	public static void deleteRecord_for_InstcpHistoryAfter2003(
			InstCpAfter2003Record record, Session session) throws SQLException,
			IOException {

		String sql = SQLReader
				.readSQL("other/instcp/delete_instcp_after2003.sql");
		PreparedStatement st = null;

		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, record.getYear());
			st.setInt(2, record.getMonth());
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
