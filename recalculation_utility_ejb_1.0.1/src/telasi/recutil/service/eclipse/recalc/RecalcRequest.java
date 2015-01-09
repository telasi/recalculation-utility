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

import telasi.recutil.beans.DiffSummary;
import telasi.recutil.beans.Recalc;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * RECALCULATION request.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Dec, 2006
 */
public class RecalcRequest extends DefaultEJBRequest {

	private static final long serialVersionUID = 6624185599850963675L;

	private Recalc recalc;

	private List intervals;

	private List diffByCycle;

	private DiffSummary diffSummary;

	public RecalcRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALCULATE, userName, password);
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public List getIntervals() {
		return intervals;
	}

	public void setIntervals(List intervals) {
		this.intervals = intervals;
	}

	public List getDiffByCycle() {
		return diffByCycle;
	}

	public void setDiffByCycle(List diffByCycle) {
		this.diffByCycle = diffByCycle;
	}

	public DiffSummary getDiffSummary() {
		return diffSummary;
	}

	public void setDiffSummary(DiffSummary diffSummary) {
		this.diffSummary = diffSummary;
	}

	public long getRecalcId() {
		return recalc.getId();
	}
}
