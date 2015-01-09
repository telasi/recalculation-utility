package telasi.recutil.service.eclipse.recalc;

import java.util.List;

import telasi.recutil.beans.Recalc;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

public class RecalcSaveRequest3 extends DefaultEJBRequest {
	private static final long serialVersionUID = 6762790385097182410L;

	private Recalc recalc;
	private List summary;
	private List facturaExpansion;
	private List trashList;

	public RecalcSaveRequest3(String userName, String password) {
		super(ActionConstants.ACT_RECALC_SAVE, userName, password);
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public List getSummary() {
		return summary;
	}

	public void setSummary(List summary) {
		this.summary = summary;
	}

	public List getFacturaExpansion() {
		return facturaExpansion;
	}

	public void setFacturaExpansion(List facturaExpansion) {
		this.facturaExpansion = facturaExpansion;
	}

	public List getTrashList() {
		return trashList;
	}

	public void setTrashList(List trashList) {
		this.trashList = trashList;
	}

}
