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

import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

public class SWTCalendarPlugin extends AbstractUIPlugin
{
private static SWTCalendarPlugin plugin;

public SWTCalendarPlugin()
{
	plugin = this;
}

public void start(BundleContext context) throws Exception
{
	super.start(context);
}

public void stop(BundleContext context) throws Exception
{
	super.stop(context);
	plugin = null;
}

public static SWTCalendarPlugin getDefault()
{
	return plugin;
}

public static ImageDescriptor getImageDescriptor(String path)
{
	return AbstractUIPlugin.imageDescriptorFromPlugin("ge.telasi.swt.calendar", path);
}

}
