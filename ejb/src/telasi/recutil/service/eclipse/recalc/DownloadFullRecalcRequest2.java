package telasi.recutil.service.eclipse.recalc;

import java.util.List;

/**
 * Downloads full recalculation info, including summaries.
 * 
 * @author dimitri
 */
public class DownloadFullRecalcRequest2 extends DownloadFullRecalcRequest {
	private static final long serialVersionUID = -8670656992834921536L;

	private List/* TrashVoucher */trashVouchers;

	public DownloadFullRecalcRequest2(String userName, String password) {
		super(userName, password);
	}

	public List getTrashVouchers() {
		return trashVouchers;
	}

	public void setTrashVouchers(List trashVouchers) {
		this.trashVouchers = trashVouchers;
	}
}
