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

import telasi.recutil.beans.RecalcRegularItem;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Request for recalculation regular item DELETE.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class RecalcRegularDeleteRequest extends DefaultEJBRequest {

	private static final long serialVersionUID = 613054674446084999L;

	private RecalcRegularItem item;

	public RecalcRegularDeleteRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALC_REGULAR_DELETE, userName, password);
	}

	public RecalcRegularItem getItem() {
		return item;
	}

	public void setItem(RecalcRegularItem item) {
		this.item = item;
	}

}
