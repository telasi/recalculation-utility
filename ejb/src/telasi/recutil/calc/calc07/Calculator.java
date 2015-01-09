package telasi.recutil.calc.calc07;

import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.CalculationInterval;
import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.DiffSummary;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.TrashSubsidy;
import telasi.recutil.calc.ICalculator;
import telasi.recutil.calc.calc06.Validator;
import telasi.recutil.calc.calc12.TrashUtils;
import telasi.recutil.service.Session;
import telasi.recutil.utils.CoreUtils;

public class Calculator implements ICalculator {

	private List intervals = null;
	private List diffByCycle = null;
	private DiffSummary diffSummary;
	private List /* FacturaDetail */facturaExpansion = new ArrayList();
	private List /* FacturaDetail */sysExpansion = new ArrayList();
	private List /* TrashVoiucher */trashVouchers = new ArrayList();

	public List calculate(Session session, Recalc recalc) {
		if (recalc == null || recalc.getDetails() == null || recalc.getDetails().getIntervals() == null || recalc.getDetails().getIntervals().isEmpty())
			throw new NullPointerException("Recalculation not defined");
		Recalc newRecalc = Utilities.prepareRecalcForCalculation(recalc);
		intervals = new ArrayList();
		for (int i = 0; i < newRecalc.getDetails().getIntervals().size(); i++) {
			RecalcInterval interval = (RecalcInterval) newRecalc.getDetails().getIntervals().get(i);
			if (interval.isEditable()) {
				CalculationInterval derived = Utilities.calculateInterval(session, interval, sysExpansion);
				if (derived != null)
					intervals.add(derived);
			}
		}
		diffByCycle = Utilities.makeDiffByCycle(Utilities.createComparators(recalc, intervals));
		diffSummary = Utilities.makeDiffSummary2008(diffByCycle, facturaExpansion);
		facturaExpansion.addAll(sysExpansion);
		// remove zeros from diffSummary
		if (diffSummary != null && diffSummary.getDetails() != null && !diffSummary.getDetails().isEmpty()) {

			// remove from diffSummary
			List forRemove = new ArrayList();
			for (int i = 0; i < diffSummary.getDetails().size(); i++) {
				DiffDetail detail = (DiffDetail) diffSummary.getDetails().get(i);
				double gel = detail.getOriginalItem().getCharge().getGel();
				double kwh = detail.getOriginalItem().getCharge().getKwh();
				boolean hasGel = Math.abs(gel) >= CoreUtils.MIN_GEL;
				boolean hasKwh = Math.abs(kwh) >= CoreUtils.MIN_KWH;
				if (!hasGel && !hasKwh)
					forRemove.add(detail);
			}
			diffSummary.getDetails().removeAll(forRemove);
			// make factura normalization
			facturaExpansion = FacturaUtils.normalizeFactura(session, facturaExpansion, recalc);
			// calculate TRASH vouchers
			List trashSubsidies = null;
			try {
				int customerId = recalc.getCustomer().getId();
				trashSubsidies = TrashSubsidy.getSubsidies(session, customerId);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			trashVouchers = TrashUtils.calculateTrashVouchers(diffByCycle, trashSubsidies);
		}

		/*
		 * Return calculated intervals.
		 */
		return intervals;
	}

	public List rebuild(Recalc recalc) {
		Validator validator = new Validator();
		return validator.validate(recalc);
	}

	public List getDiffByCycle() {
		return diffByCycle;
	}

	public DiffSummary getDiffSummary() {
		return diffSummary;
	}

	public List getFacturaExpansion() {
		return facturaExpansion;
	}

	public List getTrashVouchers() {
		return trashVouchers;
	}
}
