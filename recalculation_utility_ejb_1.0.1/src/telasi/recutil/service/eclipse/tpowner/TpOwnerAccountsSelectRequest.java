/*
 *   Copyright (C) 2006-2009 by JSC Telasi
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
package telasi.recutil.service.eclipse.tpowner;

import java.sql.Date;
import java.util.List;

import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

public class TpOwnerAccountsSelectRequest extends DefaultEJBRequest {
	private static final long serialVersionUID = 1539393924570104160L;

	private Date cycleDate;
	private List /*TpOwnerAccount*/ accounts;
	
	public TpOwnerAccountsSelectRequest(String userName, String password) {
		super(ActionConstants.ACT_TPOWNER_ACCOUNTS_SELECT, userName, password);
	}

	public Date getCycleDate() {
		return cycleDate;
	}

	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}

	public List getAccounts() {
		return accounts;
	}

	public void setAccounts(List accounts) {
		this.accounts = accounts;
	}

}
