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

import telasi.recutil.beans.tpowner.TpOwnerRecalc;
import telasi.recutil.beans.tpowner.TpOwnerRecalcResult;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

public class TpOwnerCalculationRequest extends DefaultEJBRequest {
	private static final long serialVersionUID = 1159800341248212686L;

	private TpOwnerRecalc recalculation;
	private TpOwnerRecalcResult result;

	public TpOwnerCalculationRequest(String userName, String password) {
		super(ActionConstants.ACT_TPOWNER_CALCULATE, userName, password);
	}

	public TpOwnerRecalc getRecalculation() {
		return recalculation;
	}

	public void setRecalculation(TpOwnerRecalc recalculation) {
		this.recalculation = recalculation;
	}

	public TpOwnerRecalcResult getResult() {
		return result;
	}

	public void setResult(TpOwnerRecalcResult result) {
		this.result = result;
	}

}
