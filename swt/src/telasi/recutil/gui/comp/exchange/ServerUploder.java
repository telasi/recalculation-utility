package telasi.recutil.gui.comp.exchange;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.gui.ejb.RecutilClient;
import telasi.recutil.service.eclipse.recalc.CheckRecalcExistenceRequest;
import telasi.recutil.service.eclipse.recalc.SaveFullRecalcRequest2;

/**
 * Uploads data to server.
 * 
 * @author dimitri
 */
public class ServerUploder implements Uploader {
	private ServerExchangeNode node;
	private Log log;

	/**
	 * Sends recalc to upload server.
	 */
	public boolean add(Recalc recalc, List rooms, List factura, List trashVouchers, RecalcVoucher voucher)
	throws RemoteException, CreateException, RequestException, NamingException {
		RecutilClient client = new RecutilClient(node.getServer().getUrl());
		SaveFullRecalcRequest2 request = new SaveFullRecalcRequest2(node.getUserName(), node.getPassword());
		request.setRecalc(recalc);
		request.setRooms(rooms);
		request.setFacturaDetails(factura);
		request.setVoucher(voucher);
		request.setTrashVouchers(trashVouchers);
		// process request
		client.processRequest(request);
		return true;
	}

	public boolean checkExistence(Recalc recalc)
	throws RemoteException, CreateException, RequestException, NamingException {
		// create client and request
		RecutilClient client = new RecutilClient(node.getServer().getUrl());
		CheckRecalcExistenceRequest request = new CheckRecalcExistenceRequest(node.getUserName(), node.getPassword());
		request.setCustomerId(recalc.getCustomer().getId());
		request.setRecalcNumber(recalc.getNumber());
		// process request
		DefaultEJBResponse resp = (DefaultEJBResponse) client.processRequest(request);
		CheckRecalcExistenceRequest callback = (CheckRecalcExistenceRequest) resp.getRequest();
		// analyze results
		log = null;
		if(callback.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public void finalizeUpload() {
		// TODO Auto-generated method stub
	}

	public Log getLog() {
		return log;
	}

	public ServerExchangeNode getNode() {
		return node;
	}

	public void setNode(ServerExchangeNode node) {
		this.node = node;
	}

}
