/*
 *   Copyright (C) 2007 by JSC Telasi
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

import java.util.List;

import telasi.recutil.beans.Date;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * This request is used by the system to search
 * recalculations by date.
 * 
 * @author dimitri
 */
public class RecalcSearchRequest extends DefaultEJBRequest {
	private static final long serialVersionUID = -1252738050765410124L;
	private Date date;
	private boolean saveDate;
	private List/*Recalc*/ results;
	
	public RecalcSearchRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALC_SEARCH, userName, password);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isSaveDate() {
		return saveDate;
	}

	public void setSaveDate(boolean saveDate) {
		this.saveDate = saveDate;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}
	
}
