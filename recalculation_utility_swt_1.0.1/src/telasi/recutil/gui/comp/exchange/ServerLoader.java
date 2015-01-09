package telasi.recutil.gui.comp.exchange;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.Recalc2;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.gui.ejb.RecutilClient;
import telasi.recutil.service.eclipse.recalc.DownloadFullRecalcRequest2;
import telasi.recutil.service.eclipse.recalc.RecalcFinalizeRequest;

/**
 * Implementation of loader for server.
 * 
 * @author dimitri
 */
public class ServerLoader implements Loader {
	private ServerExchangeNode node;
	private Log log;
	private Recalc recalc;
	private List rooms;
	private List facturaDetails;
	private RecalcVoucher voucher;
	private List trashVouchers;

	public Log getLog() {
		return log;
	}

	/**
	 * Download full version of recalculation.
	 */
	public void downloadFullRecalcInfo(Recalc recalc) throws RemoteException, CreateException, RequestException, NamingException {
		RecutilClient client = new RecutilClient(node.getServer().getUrl());
		DownloadFullRecalcRequest2 request = new DownloadFullRecalcRequest2(node.getUserName(), node.getPassword());
		request.setRecalc(recalc);
		DefaultEJBResponse resp = (DefaultEJBResponse) client.processRequest(request);
		DownloadFullRecalcRequest2 callback = (DownloadFullRecalcRequest2) resp.getRequest();
		this.recalc = callback.getRecalc();
		this.rooms = callback.getRooms();
		this.facturaDetails = callback.getFacturaDetails();
		this.voucher = callback.getVoucher();
		this.trashVouchers = callback.getTrashVouchers();
	}

	public ServerExchangeNode getNode() {
		return node;
	}

	public void setNode(ServerExchangeNode node) {
		this.node = node;
	}

	public List getFacturaDetails() {
		return facturaDetails;
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public List getRooms() {
		return rooms;
	}

	public RecalcVoucher getVoucher() {
		return voucher;
	}

	public List getTrashVouchers() {
		return trashVouchers;
	}
	
	public void finalizeRecalc(Recalc recalc) throws RemoteException, CreateException, RequestException, NamingException {
		if (recalc instanceof Recalc2) {
			Recalc2 recalc2 = (Recalc2) recalc;
			if (recalc2.getStatus() == Recalc2.STATUS_SAVED) {
				RecalcFinalizeRequest request = new RecalcFinalizeRequest(node.getUserName(), node.getPassword());
				request.setFinalize(true);
				request.setRecalcId(recalc2.getId());
				RecutilClient client = new RecutilClient(node.getServer().getUrl());
				client.processRequest(request);
			}
		}
	}

}
