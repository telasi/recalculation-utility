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
package telasi.recutil.calc;

import telasi.recutil.beans.Date;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Dec, 2006
 */
public interface IDivisionDate {
	public static final int EXACT = 1;
	public static final int ON_CYCLE = 2;

	public int getType();

	public Date getDate();

	public int getDischargeId();

	public int getRechargeId();

	public int getPercentSubsDischargeId();

	public int getPercentSubsRechargeId();

	public int getBalanceCorrectionId();

	public int getServiceDischargeId();

	public int getServiceRechargeId();

	public int getCompensationDischargeId();

	public int getCompensationRechargeId();

	public int getPensionCorrectionId();

	public int getUSAIDCorrectionId();

	public int getFixKwhSubsidyCorrectionId();

	public int getOneTimeActCorrectionId();
	
}
