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

import java.util.List;

import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

public class InstCpHistoryAfter2003Request extends DefaultEJBRequest {

	private static final long serialVersionUID = -6178469737598244307L;

	// list of InspCpAfter2003Record-s
	private List history;

	public InstCpHistoryAfter2003Request(String userName, String password) {
		super(ActionConstants.OTHER_INST_CP_HISTORY_AFTER_2003_SELECT,
				userName, password);
	}

	public List getHistory() {
		return history;
	}

	public void setHistory(List history) {
		this.history = history;
	}

}
