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
package telasi.recutil.gui.widgets.calendar;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;

import java.util.Calendar;

public class SWTCalendarEvent extends TypedEvent
{
static final long serialVersionUID = -4525931268845275613L;

public SWTCalendarEvent(Event event)
{
	super(event);
}

public Calendar getCalendar()
{
	return (Calendar) this.data;
}
}
