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
package telasi.recutil.utils;

import java.io.UnsupportedEncodingException;

/**
 * Transalator is used when converting characters.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Oct, 2006
 */
public class Translator
{

static String KA = CoreMessages.getMessage("characters.ka").trim();

static String GEO_ASCII = CoreMessages.getMessage("characters.geo").trim();

public static String KA_TO_GEO_ASCII(String src)
{
	return translate(src, KA, GEO_ASCII);
}

public static String GEO_ASCII_TO_KA(String src)
{
	return translate(src, GEO_ASCII, KA);
}

public static String translate(String src, String set1, String set2)
{
	if (src == null || "".equals(src) || set1 == null || set2 == null || set1.length() != set2.length())
	{
		return src;
	}
	StringBuffer b = new StringBuffer();
	for (int i = 0; i < src.length(); i++)
	{
		char c = src.charAt(i);
		int index = set1.indexOf(c);
		if (index == -1)
		{
			b.append(c);
		}
		else
		{
			b.append(set2.charAt(index));
		}
	}
	return b.toString();
}

public static String convertEncodig(String src, String from, String to)
{
	if (src == null || src.length() == 0)
	{
		return src;
	}
	try
	{
		return new String(src.getBytes(from), to);
	}
	catch (UnsupportedEncodingException e)
	{
		return src;
	}
}

public static String ISO_TO_UTF8(String src)
{
	return convertEncodig(src, "ISO-8859-1", "UTF-8");
}

}
