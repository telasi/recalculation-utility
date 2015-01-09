package telasi.recutil.service.eclipse.recalc;

import java.util.List;

import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Downloads full recalculation info, including summaries.
 * 
 * @author dimitri
 */
public class DownloadFullRecalcRequest extends DefaultEJBRequest {
	private static final long serialVersionUID = 6077368580810930123L;
	private Recalc recalc;
	private RecalcVoucher voucher;
	private List rooms;
	private List/*FacturaDetail*/ facturaDetails;

	public DownloadFullRecalcRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALC_FULL_DOWNLOAD, userName, password);
	}

	public List getFacturaDetails() {
		return facturaDetails;
	}

	public void setFacturaDetails(List facturaDetails) {
		this.facturaDetails = facturaDetails;
	}

	public RecalcVoucher getVoucher() {
		return voucher;
	}

	public void setVoucher(RecalcVoucher voucher) {
		this.voucher = voucher;
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public List getRooms() {
		return rooms;
	}

	public void setRooms(List rooms) {
		this.rooms = rooms;
	}

}
