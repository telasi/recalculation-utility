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

import java.io.Serializable;

/**
 * Problems/information reporting. 
 * @author dimakura
 */
public class NotThrowable implements Serializable {

	private static final long serialVersionUID = 3537275210784329114L;

	public static final int INFO = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;

	private int severity;

	private String msg;

	private Object source;

	public NotThrowable(Object src, int severity, String msg) {
		this.source = src;
		this.severity = severity;
		this.msg = msg;
	}

	public Object getSource() {
		return source;
	}

	public String getMessage() {
		return msg;
	}

	public int getSeverity() {
		return severity;
	}

}
