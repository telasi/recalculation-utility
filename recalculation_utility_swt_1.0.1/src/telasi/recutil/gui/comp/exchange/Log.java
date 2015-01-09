package telasi.recutil.gui.comp.exchange;

import org.eclipse.swt.graphics.Image;

import telasi.recutil.gui.plugin.Plugin;

/**
 * Log for load/upload operations.
 * 
 * @author dimitri
 */
public class Log {
	public static final int INFO = 0;
	public static final int SUCCESS = 1;
	public static final int WARNING = 2;
	public static final int ERROR = 3;

	private int severity;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public String toString() {
		return message;
	}

	public Image toImage() {
		switch (severity) {
		case INFO:
			return Plugin.getImage("icons/16x16/info.png");
		case SUCCESS:
			return Plugin.getImage("icons/16x16/true.png");
		case WARNING:
			return Plugin.getImage("icons/16x16/warning.gif");
		case ERROR:
			return Plugin.getImage("icons/16x16/error.gif");
		default:
			return null;
		}
	}

}
