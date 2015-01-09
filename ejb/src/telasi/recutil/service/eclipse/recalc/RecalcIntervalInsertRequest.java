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
 * Request for recalculation interval INSERT.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class RecalcIntervalInsertRequest extends DefaultEJBRequest {

	private static final long serialVersionUID = -2947769566126639372L;

	private long recalcId = -1, item1Id, item2Id;

	private String recalcIntervalName;

	private long intervalId;

	public RecalcIntervalInsertRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALC_INTERVAL_INSERT, userName, password);
	}

	public long getIntervalId() {
		return intervalId;
	}

	public void setIntervalId(long intervalId) {
		this.intervalId = intervalId;
	}

	public long getItem1Id() {
		return item1Id;
	}

	public void setItem1Id(long item1Id) {
		this.item1Id = item1Id;
	}

	public long getItem2Id() {
		return item2Id;
	}

	public void setItem2Id(long item2Id) {
		this.item2Id = item2Id;
	}

	public long getRecalcId() {
		return recalcId;
	}

	public void setRecalcId(long recalcId) {
		this.recalcId = recalcId;
	}

	public String getRecalcIntervalName() {
		return recalcIntervalName;
	}

	public void setRecalcName(String recalcIntervalName) {
		this.recalcIntervalName = recalcIntervalName;
	}

}
