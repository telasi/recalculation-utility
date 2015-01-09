package telasi.recutil.service.eclipse.recalc;

import java.util.List;

import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

public class RecalcVoucherSelectRequest3 extends DefaultEJBRequest {
	private static final long serialVersionUID = -1984653513235639893L;

	private Recalc recalc;
	private RecalcVoucher voucher;
	private List/* FacturaDetail */facturaDetails;
	private List/* TrashVoucher */trashVouchers;

	public RecalcVoucherSelectRequest3(String userName, String password) {
		super(ActionConstants.ACT_GET_SAVED_VOUCHER, userName, password);
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public RecalcVoucher getVoucher() {
		return voucher;
	}

	public void setVoucher(RecalcVoucher voucher) {
		this.voucher = voucher;
	}

	public List getFacturaDetails() {
		return facturaDetails;
	}

	public void setFacturaDetails(List facturaDetails) {
		this.facturaDetails = facturaDetails;
	}

	public List getTrashVouchers() {
		return trashVouchers;
	}

	public void setTrashVouchers(List trashVouchers) {
		this.trashVouchers = trashVouchers;
	}

}
