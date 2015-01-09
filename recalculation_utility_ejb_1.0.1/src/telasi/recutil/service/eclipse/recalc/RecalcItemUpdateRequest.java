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

import telasi.recutil.beans.RecalcItem;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Request for recalculation item UPDATE.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class RecalcItemUpdateRequest extends DefaultEJBRequest {

	private static final long serialVersionUID = -4750175350513057851L;

	private RecalcItem item;

	public RecalcItemUpdateRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALC_ITEM_UPDATE, userName, password);
	}

	public RecalcItem getRecalcItem() {
		return item;
	}

	public void setRecalcItem(RecalcItem recalcItem) {
		this.item = recalcItem;
	}

}
