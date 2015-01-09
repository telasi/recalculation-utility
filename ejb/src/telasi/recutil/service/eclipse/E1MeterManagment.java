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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Meter;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.Response;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.meter.MeterSelectRequest;
import telasi.recutil.utils.Translator;

/**
 * This class is used for Meter managment.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class E1MeterManagment {

	public static Response processMeterSelect(Request req,
			Session session) throws RequestException {
		try {
			MeterSelectRequest request = (MeterSelectRequest) req;
			request.setMeters(getMeters(session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	//
	// database processing
	//

	public static List getMeters(Session session) throws SQLException,
			IOException {
		String sql = SQLReader.readSQL("meter/meter_select.sql");
		ResultSet res = null;
		try {

			res = session.getConnection().createStatement().executeQuery(sql);
			List meters = new ArrayList();

			while (res.next()) {
				Meter m = new Meter();
				m.setId(res.getInt("mttpkey"));
				m.setName(Translator.GEO_ASCII_TO_KA(res.getString("mtname")
						.trim()));
				m.setDigits(res.getInt("digit"));
				meters.add(m);
			}

			return meters;

		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (Exception ex) {
				}
				res = null;
			}
		}

	}

}
