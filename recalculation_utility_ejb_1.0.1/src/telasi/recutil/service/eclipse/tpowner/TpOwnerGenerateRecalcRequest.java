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

import telasi.recutil.beans.tpowner.TpOwnerAccount;
import telasi.recutil.beans.tpowner.TpOwnerRecalc;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Recalculation generation request.
 * 
 * @author dimitri
 */
public class TpOwnerGenerateRecalcRequest extends DefaultEJBRequest {
	private static final long serialVersionUID = 143750483786849118L;

	private TpOwnerAccount account;
	private Date cycleDate;
	private TpOwnerRecalc recalc;

	public TpOwnerGenerateRecalcRequest(String userName, String password) {
		super(ActionConstants.ACT_TPOWNER_GENERATE_RECALC, userName, password);
	}

	public TpOwnerAccount getAccount() {
		return account;
	}

	public void setAccount(TpOwnerAccount account) {
		this.account = account;
	}

	public Date getCycleDate() {
		return cycleDate;
	}

	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}

	public TpOwnerRecalc getRecalc() {
		return recalc;
	}

	public void setRecalc(TpOwnerRecalc recalc) {
		this.recalc = recalc;
	}

	
	
}
