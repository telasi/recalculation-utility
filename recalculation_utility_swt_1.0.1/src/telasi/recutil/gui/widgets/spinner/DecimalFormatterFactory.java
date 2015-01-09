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
package telasi.recutil.gui.widgets.spinner;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;

class DecimalFormatterFactory extends AbstractFormatterFactory
{

public static final String PATTERN_0 = "#,###";

public static final String PATTERN_1 = "#,###.0";

public static final String PATTERN_2 = "#,###.00";

public static final String PATTERN_3 = "#,###.000";

public NumberFormat nf = new DecimalFormat(PATTERN_2);

public void setDigits(int digits)
{

	switch (digits) {
	case 0:
		nf = new DecimalFormat(PATTERN_0);
		break;
	case 1:
		nf = new DecimalFormat(PATTERN_1);
		break;
	case 3:
		nf = new DecimalFormat(PATTERN_3);
		break;
	default:
		nf = new DecimalFormat(PATTERN_2);
	}

}

public void setMask(String mask)
{
	nf = new DecimalFormat(mask);
}

public AbstractFormatter getFormatter(JFormattedTextField tf)
{
	return new MyFormatter();
}

private class MyFormatter extends AbstractFormatter
{

private static final long serialVersionUID = -3783797117355241303L;

public Object stringToValue(String text) throws ParseException
{
	if (text == null || text.trim().length() == 0) {
		return null;
	}
	return new Double(((Number) nf.parseObject(text)).doubleValue());
}

public String valueToString(Object value) throws ParseException
{
	if (value == null) {
		return "";
	}
	if (!(value instanceof Number)) {
		return "";
	}
	return nf.format(((Number) value).doubleValue());
}
}

}
