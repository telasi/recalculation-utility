package telasi.recutil.gui.comp.exchange;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.ejb.RequestException;

/**
 * Uploader.
 * 
 * @author dimitri
 */
public interface Uploader {

	/**
	 * Uploads a new recalculation.
	 * 
	 * @param recalc recalculation
	 */
	public boolean add(Recalc recalc, List rooms, List factura, List trashVouchers, RecalcVoucher voucher)
	throws RemoteException, CreateException, RequestException, NamingException;

	/**
	 * Checks existence of the recalculation.
	 */
	public boolean checkExistence(Recalc recalc)
	throws RemoteException, CreateException, RequestException, NamingException;

	/**
	 * Finalize uploading.
	 */
	public void finalizeUpload();

	/**
	 * Returns last session log.
	 * 
	 * @return last log or null if nothing
	 */
	public Log getLog();

}
