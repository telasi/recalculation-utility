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
package telasi.recutil.service.eclipse.recalc;

import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Finalize request for recalculation.
 * 
 * @author dimitri
 */
public class RecalcFinalizeRequest extends DefaultEJBRequest {
	private static final long serialVersionUID = -3276216450814569746L;

	private long recalcId;
	private boolean finalize;
	
	public RecalcFinalizeRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALC_FINALIZE, userName, password);
	}

	public boolean isFinalize() {
		return finalize;
	}

	public void setFinalize(boolean finalize) {
		this.finalize = finalize;
	}

	public long getRecalcId() {
		return recalcId;
	}

	public void setRecalcId(long recalcId) {
		this.recalcId = recalcId;
	}

}
