package telasi.recutil.gui.comp.exchange;

/**
 * Exhange operation node descriptor.
 * 
 * @author dimitri
 */
public interface IExchangeNode {
	public static final int SERVER = 1;
	public static final int FILE = 2;

	int getType();
}
