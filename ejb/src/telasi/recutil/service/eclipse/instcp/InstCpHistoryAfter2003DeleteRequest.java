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
package telasi.recutil.service.eclipse.instcp;

import telasi.recutil.beans.instcp.InstCpAfter2003Record;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

public class InstCpHistoryAfter2003DeleteRequest extends DefaultEJBRequest {

	private static final long serialVersionUID = 1221555835372619917L;

	public InstCpHistoryAfter2003DeleteRequest(String userName, String password) {
		super(ActionConstants.OTHER_INST_CP_HISTORY_AFTER_2003_DELETE,
				userName, password);
	}

	private InstCpAfter2003Record record;

	public InstCpAfter2003Record getRecord() {
		return record;
	}

	public void setRecord(InstCpAfter2003Record record) {
		this.record = record;
	}

}
