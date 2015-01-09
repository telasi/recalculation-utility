package telasi.recutil.gui.comp.exchange;

import java.io.File;

/**
 * File node.
 * 
 * @author dimitri
 */
public class FileExchangeNode implements IExchangeNode {
	private File file;

	public FileExchangeNode(File file) {
		setFile(file);
	}

	public int getType() {
		return FILE;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String toString() {
		return "file://" + file.getPath();
	}

}
