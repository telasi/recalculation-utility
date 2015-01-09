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
package telasi.recutil.gui.utils;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Find bundle for the message.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class BundleFinder
{

public static ResourceBundle findBundle(String msgId, Locale locale)
{

	if (msgId.startsWith("application")) {
		return PropertyResourceBundle.getBundle("telasi/recutil/gui/msg/application", locale);
	}

	if (msgId.startsWith("dbaction")) {
		return PropertyResourceBundle.getBundle("telasi/recutil/gui/msg/dbaction", locale);
	}

	if (msgId.startsWith("oper")) {
		return PropertyResourceBundle.getBundle("telasi/recutil/gui/msg/oper", locale);
	}

	if (msgId.startsWith("comp")) {
		return PropertyResourceBundle.getBundle("telasi/recutil/gui/msg/comp", locale);
	}

	return PropertyResourceBundle.getBundle("telasi/recutil/gui/msg/other", locale);

}

}
