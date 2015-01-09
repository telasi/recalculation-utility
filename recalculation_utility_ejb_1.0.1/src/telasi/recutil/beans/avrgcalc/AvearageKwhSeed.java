/*
 *   Copyright (C) 2006, 2007 by JSC Telasi
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
package telasi.recutil.beans.avrgcalc;

import java.io.Serializable;

import telasi.recutil.beans.Date;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Jan, 2007
 */
public class AvearageKwhSeed implements Serializable {

	private static final long serialVersionUID = -531775798290903608L;

	private Date startDate, endDate;

	private int dayInterval;

	private String name;

	private double fullKwh, aveargeKwh;

	public double getAveargeKwh() {
		return aveargeKwh;
	}

	public void setAveargeKwh(double aveargeKwh) {
		this.aveargeKwh = aveargeKwh;
	}

	public int getDayInterval() {
		return dayInterval;
	}

	public void setDayInterval(int dayInterval) {
		this.dayInterval = dayInterval;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getKwh() {
		return fullKwh;
	}

	public void setFullKwh(double fullKwh) {
		this.fullKwh = fullKwh;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
