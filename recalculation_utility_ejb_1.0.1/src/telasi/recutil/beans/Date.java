/*
 * Copyright 2007, Dimitri Kurashvili (dimakura@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package telasi.recutil.beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Date class.
 * 
 * @author dimakura
 * 
 */
public class Date implements Serializable {
	private static final long serialVersionUID = -9184190919060779766L;
	private int year, month, day;

	public static String format(Date d) {
		if (d == null) {
			return "";
		}
		NumberFormat nf = new DecimalFormat("00");
		StringBuffer b = new StringBuffer();
		b.append(nf.format(d.getDay()));
		String name = nf.format(d.getMonth());
		b.append("/");
		b.append(name);
		b.append("/");
		b.append(d.year);
		return b.toString();
	}

	private Date() {
	}

	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	//
	// Interface
	//

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String toString() {
		return Date.format(this);
	}

	public Date nextDay() {
		return addDays(this, 1);
	}

	public Date prevDay() {
		return addDays(this, -1);
	}

	//
	// Conventors
	//

	public java.util.Date toDate() {
		Calendar cal = Calendar.getInstance();
		int day = getDay();
		int month = fromDateToCalendar(getMonth());
		int year = getYear();
		cal.set(year, month, day);
		return cal.getTime();
	}

	//
	// Static
	//

	public static final int DAY_MILLIS = 3600 * 24 * 1000;

	public static Date addDays(Date aDate, int days) {

		if (aDate == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.set(aDate.year, fromDateToCalendar(aDate.month), aDate.day);
		cal.add(Calendar.DAY_OF_MONTH, days);

		return Date.create(cal.getTime());
	}

	public static int getIntervalInDays(Date end, Date start) {
		Calendar cal = Calendar.getInstance();
		cal.set(end.year, fromDateToCalendar(end.month), end.day);
		long t1 = cal.getTimeInMillis();
		cal.set(start.year, fromDateToCalendar(start.month), start.day);
		long t2 = cal.getTimeInMillis();
		int interval = Math.round(((t1 - t2) * 1.0f / DAY_MILLIS));
		return interval;
	}

	static int fromCalendarToDate(int month) {
		switch (month) {
		case Calendar.JANUARY:
			return 1;
		case Calendar.FEBRUARY:
			return 2;
		case Calendar.MARCH:
			return 3;
		case Calendar.APRIL:
			return 4;
		case Calendar.MAY:
			return 5;
		case Calendar.JUNE:
			return 6;
		case Calendar.JULY:
			return 7;
		case Calendar.AUGUST:
			return 8;
		case Calendar.SEPTEMBER:
			return 9;
		case Calendar.OCTOBER:
			return 10;
		case Calendar.NOVEMBER:
			return 11;
		case Calendar.DECEMBER:
			return 12;
		default:
			return -1;
		}
	}

	static int fromDateToCalendar(int month) {
		switch (month) {
		case 1:
			return Calendar.JANUARY;
		case 2:
			return Calendar.FEBRUARY;
		case 3:
			return Calendar.MARCH;
		case 4:
			return Calendar.APRIL;
		case 5:
			return Calendar.MAY;
		case 6:
			return Calendar.JUNE;
		case 7:
			return Calendar.JULY;
		case 8:
			return Calendar.AUGUST;
		case 9:
			return Calendar.SEPTEMBER;
		case 10:
			return Calendar.OCTOBER;
		case 11:
			return Calendar.NOVEMBER;
		case 12:
			return Calendar.DECEMBER;
		default:
			return -1;
		}
	}

	public static Date create(Date date) {
		Date d = new Date();
		d.setDay(date.getDay());
		d.setMonth(date.getMonth());
		d.setYear(date.getYear());
		return d;
	}

	public static Date create(java.sql.Timestamp t) {
		return t == null ? null : create(new java.util.Date(t.getTime()));
	}

	public static Date create(java.util.Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date d = new Date();
		d.setDay(cal.get(Calendar.DAY_OF_MONTH));
		d.setMonth(fromCalendarToDate(cal.get(Calendar.MONTH)));
		d.setYear(cal.get(Calendar.YEAR));
		return d;
	}

	public static Date create(long time) {
		return create(new java.util.Date(time));
	}

	public static boolean isEqual(Date d1, Date d2) {
		return compare(d1, d2) == 0;
	}

	public static boolean isGreater(Date d1, Date d2) {
		return compare(d1, d2) > 0;
	}

	public static boolean isGreaterOrEqual(Date d1, Date d2) {
		return compare(d1, d2) >= 0;
	}

	public static boolean isLess(Date d1, Date d2) {
		return compare(d1, d2) < 0;
	}

	public static boolean isLessOrEqual(Date d1, Date d2) {
		return compare(d1, d2) <= 0;
	}

	public boolean isGreater(Date date) {
		return Date.isGreater(this, date);
	}

	public boolean isGreaterOrEqual(Date date) {
		return Date.isGreaterOrEqual(this, date);
	}

	public boolean isLess(Date date) {
		return Date.isLess(this, date);
	}

	public boolean isLessOrEqual(Date date) {
		return Date.isLessOrEqual(this, date);
	}

	/**
	 * Returns 0, when equal; >0, when first is greater and <0, when second is
	 * greater.
	 */
	public static int compare(Date d1, Date d2) {
		if (d1 == null && d2 == null) {
			return 0;
		}
		if (d1 == null || d2 == null) {
			throw new NullPointerException();
		}
		if (d1.year > d2.year) {
			return +1;
		}
		if (d1.year < d2.year) {
			return -1;
		}
		if (d1.month > d2.month) {
			return +1;
		}
		if (d1.month < d2.month) {
			return -1;
		}
		if (d1.day > d2.day) {
			return +1;
		}
		if (d1.day < d2.day) {
			return -1;
		}
		return 0;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Date)) {
			return false;
		}
		return compare((Date) o, this) == 0;
	}

}
