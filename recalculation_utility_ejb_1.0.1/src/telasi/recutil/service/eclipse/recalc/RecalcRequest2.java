package telasi.recutil.service.eclipse.recalc;

import java.util.List;

import telasi.recutil.beans.DiffSummary;
import telasi.recutil.beans.Recalc;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

public class RecalcRequest2 extends DefaultEJBRequest {
	private static final long serialVersionUID = 5987970699304410455L;

	private Recalc recalc;

	private List intervals;

	private List diffByCycle;

	private DiffSummary diffSummary;
	
	private List/*FacturaDetail*/ facturaExpansion;

	public RecalcRequest2(String userName, String password) {
		super(ActionConstants.ACT_RECALCULATE, userName, password);
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public List getIntervals() {
		return intervals;
	}

	public void setIntervals(List intervals) {
		this.intervals = intervals;
	}

	public List getDiffByCycle() {
		return diffByCycle;
	}

	public void setDiffByCycle(List diffByCycle) {
		this.diffByCycle = diffByCycle;
	}

	public DiffSummary getDiffSummary() {
		return diffSummary;
	}

	public void setDiffSummary(DiffSummary diffSummary) {
		this.diffSummary = diffSummary;
	}

	public long getRecalcId() {
		return recalc.getId();
	}

	public List getFacturaExpansion() {
		return facturaExpansion;
	}

	public void setFacturaExpansion(List facturaDetails) {
		this.facturaExpansion = facturaDetails;
	}

}
