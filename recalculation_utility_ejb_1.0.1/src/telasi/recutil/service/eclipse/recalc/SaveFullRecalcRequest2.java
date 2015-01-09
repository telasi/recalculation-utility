package telasi.recutil.service.eclipse.recalc;

import java.util.List;

public class SaveFullRecalcRequest2 extends SaveFullRecalcRequest {
	private static final long serialVersionUID = -4562789321035003256L;
	private List /* TrashVoucher */trashVouchers;

	public SaveFullRecalcRequest2(String userName, String password) {
		super(userName, password);
	}

	public List getTrashVouchers() {
		return trashVouchers;
	}

	public void setTrashVouchers(List trashVouchers) {
		this.trashVouchers = trashVouchers;
	}
}
