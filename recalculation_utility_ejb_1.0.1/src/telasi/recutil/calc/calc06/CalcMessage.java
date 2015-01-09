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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import telasi.recutil.utils.Translator;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Oct, 2006
 */
public class CalcMessage {

	/**
	 * Default locale.
	 */
	private static final Locale EN = new Locale("en");

	/**
	 * Current locale.
	 */

	private static Locale locale = Locale.getDefault();

	/**
	 * Can not instantinate.
	 */
	private CalcMessage() {
	}

	/**
	 * Set locale.
	 * 
	 * @param loc
	 *            locale
	 */
	public static void setLocale(Locale loc) {
		locale = loc;
	}

	/**
	 * Get
	 * 
	 * @return
	 */
	public static Locale getLocale() {
		return locale;
	}

	/**
	 * Returns unformated message.
	 * 
	 * @param msgKey
	 *            key of the message
	 * @return unformated message
	 */
	public static String getMessage(String msgKey) {
		return getMessage(msgKey, locale);
	}

	/**
	 * Get default bundle. Is used when specified locale can not be found.
	 * 
	 * @return default bundle
	 */
	public static ResourceBundle getBundle(Locale locale) {
		return PropertyResourceBundle.getBundle(
				"telasi/recutil/calc/calc06/msg/bundle", locale);
	}

	/**
	 * Tries to find message with the given key for given locale. If can not
	 * find this locale, then look for the default locale. If this also gives
	 * nothing, then return message key.
	 * 
	 * @param msgKey
	 *            key of the message
	 * @param locale
	 *            locale
	 * @return message
	 */
	public static String getMessage(String msgKey, Locale locale) {
		try {
			ResourceBundle bundle = getBundle(locale);
			return Translator.ISO_TO_UTF8(bundle.getString(msgKey));
		} catch (MissingResourceException mre) {
			try {
				return Translator.ISO_TO_UTF8(getBundle(EN).getString(msgKey));
			} catch (MissingResourceException mre2) {
				mre2.printStackTrace();
				return msgKey;
			}
		}
	}

	//
	// Formatted messages
	//

	public static String getMessage(String msgKey, Object[] args) {
		return getMessage(msgKey, locale, args);
	}

	public static String getMessage(String msgKey, Locale loc, Object[] args) {
		return MessageFormat.format(getMessage(msgKey, loc), args);
	}

}
