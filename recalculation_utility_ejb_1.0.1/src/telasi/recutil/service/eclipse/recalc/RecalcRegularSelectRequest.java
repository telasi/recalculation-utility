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

import java.util.List;

import telasi.recutil.beans.Recalc;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Request for recalculation regular item SELECT.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class RecalcRegularSelectRequest extends DefaultEJBRequest {

	private static final long serialVersionUID = 5685904520453611623L;

	private Recalc recalc;

	private List items;

	public RecalcRegularSelectRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALC_REGULAR_SELECT, userName, password);
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

}
