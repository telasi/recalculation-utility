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
package telasi.recutil.calc.calc06;

import telasi.recutil.beans.Date;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Dec, 2006
 */
class KernelConfiguration {

	public boolean requierEnterDateBeGreateThanItemDate() {
		return true;
	}

	public boolean checkEnterDateAndItemdateDistinction() {
		return true;
	}

	public int getAllowedDistinctionBetweenItemdateAndEnterdate() {
		return 60;
	}

	public int getMaxAllowedDistinctionBetweenItemdateAndEnterdate() {
		return 90;
	}

	public int getMaxDistictionForInstalledCapacityCalculation() {
		return 60;
	}

	public boolean calculateAddChangeAndSubsidyUsingCurrentTariff(Date d) {
		// after '01-Jun-2006' use distribution of add charge
		return !Date.isGreater(d, new Date(2006, 5, 31));
	}

	public Date getMinimalItemDate() {
		return new Date(1998, 12, 31);
	}

}
