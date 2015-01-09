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
package telasi.recutil.gui.plugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Plugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "telasi.recutil.guiapp";

	// The shared instance
	private static Plugin plugin;

	//static final String SAVE_PATH = "recutil_settings";
	//private static Settings settings;
	//public static String SETTINGS_URL = ".recutil/settings/01.javaobject";
	public static String EXTERNAL_REPORTS_DIR = ".recutil/reports/";

	/**
	 * The constructor
	 */
	public Plugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
//		File f = new File(SETTINGS_URL);
//		readStateFrom(f);
	}

//	protected void readStateFrom(File f) {
//		InputStream in = null;
//		try {
//			in = new FileInputStream(f);
//			ObjectInputStream oin = new ObjectInputStream(in);
//			settings = (Settings) oin.readObject();
//		} catch (Exception ex) {
//			// ex.printStackTrace();
//			settings = new Settings();
//		} finally {
//			try {
//				in.close();
//			} catch (Exception ex) {
//			}
//		}
//	}
//
//	protected void writeStateInto(File f) {
//		OutputStream out = null;
//		try {
//			if (!f.exists()) {
//				File parent = new File(f.getParent());
//				parent.mkdirs();
//				f.createNewFile();
//			}
//			out = new FileOutputStream(f);
//			ObjectOutputStream oout = new ObjectOutputStream(out);
//			oout.writeObject(settings);
//			oout.flush();
//		} catch (Exception ex) {
//			// ex.printStackTrace();
//		} finally {
//			try {
//				out.close();
//			} catch (Exception ex) {
//			}
//		}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		//File f = new File(SETTINGS_URL);
		//writeStateInto(f);
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Plugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public static Image getImage(String path) {
		return new Image(Display.getDefault(), getImageDescriptor(path)
				.getImageData());
	}

//	public static Settings getSettings() {
//		return settings;
//	}

}
