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

import telasi.recutil.beans.RecalcInstCp;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Request for recalculation inst.cp. item MOVE.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class RecalcInstcpMoveRequest extends DefaultEJBRequest {

	private static final long serialVersionUID = -921593940167598727L;

	private RecalcInstCp item;

	private boolean up;

	public RecalcInstcpMoveRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALC_INSTCP_MOVE, userName, password);
	}

	public RecalcInstCp getItem() {
		return item;
	}

	public void setItem(RecalcInstCp item) {
		this.item = item;
	}

	public boolean moveUp() {
		return up;
	}

	public void setMoveUp(boolean up) {
		this.up = up;
	}

}
