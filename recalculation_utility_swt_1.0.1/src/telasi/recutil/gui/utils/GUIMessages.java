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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Oct, 2006
 */
public class GUIMessages {

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
	private GUIMessages() {
	}

	/**
	 * Set locale.
	 * @param loc
	 *            locale
	 */
	public static void setLocale(Locale loc) {
		locale = loc;
	}

	public static Locale getLocale() {
		return locale;
	}

	/**
	 * Returns unformated message.

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
	public static ResourceBundle getBundle(String msgKey, Locale locale) {
		// return
		// PropertyResourceBundle.getBundle("ge/telasi/recut/guiapp/utils/bundle",
		// locale);
		return BundleFinder.findBundle(msgKey, locale);
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
			ResourceBundle bundle = getBundle(msgKey, locale);
			return GUITranslator.ISO_TO_UTF8(bundle.getString(msgKey));
		} catch (MissingResourceException mre) {
			try {
				return GUITranslator.ISO_TO_UTF8(getBundle(msgKey, EN)
						.getString(msgKey));
			} catch (MissingResourceException mre2) {
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
