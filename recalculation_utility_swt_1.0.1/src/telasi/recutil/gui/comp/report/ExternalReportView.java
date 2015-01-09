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
package telasi.recutil.gui.comp.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.gui.plugin.Plugin;

public class ExternalReportView {

	public static void showReport(Shell parentShell, String reportHTML, String fileName) {
		try {
			String path = Plugin.EXTERNAL_REPORTS_DIR + fileName;
			File f = new File(path);
			File parent = new File(f.getParent());
			if (!parent.exists())
				parent.mkdirs();
			OutputStream out = new FileOutputStream(f);
			out.write(reportHTML.getBytes());
			out.flush();
			out.close();
			Program.launch(f.toURI().toURL().toString());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
