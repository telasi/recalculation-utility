package telasi.recutil.gui.comp.exchange;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.ejb.RequestException;

/**
 * Loads data from server.
 * 
 * @author dimitri
 */
public interface Loader {

	/**
	 * Loads full version of recalculation.
	 */
	public void downloadFullRecalcInfo(Recalc recalc) throws RemoteException, CreateException, RequestException, NamingException;

	/**
	 * Get last log.
	 */
	public Log getLog();

	public Recalc getRecalc();

	public List getRooms();

	public List getFacturaDetails();

	public RecalcVoucher getVoucher();

	public List getTrashVouchers();

	public void finalizeRecalc(Recalc recalc) throws RemoteException, CreateException, RequestException, NamingException;

}
