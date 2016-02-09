package telasi.recutil.calc.calc12;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import telasi.recutil.beans.CalculationItem;
import telasi.recutil.beans.ChargeElement;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.DiffSummary;
import telasi.recutil.beans.TrashSubsidy;
import telasi.recutil.beans.TrashVoucher;
import telasi.recutil.calc.RecalcException;
import telasi.recutil.calc.calc07.Utilities;

/**
 * Trash calculations.
 * 
 * @author dimitri
 */
public class TrashUtils {

	private static class Interval {
		Date start, end;
		double tariff;
		boolean useFull;

		Interval(Date s, Date e, double t, boolean useFull) {
			this.start = s;
			this.end = e;
			this.tariff = t;
			this.useFull = useFull;
		}

	}

	static final List/* Interval */INTERVALS = new ArrayList();
	static {
		INTERVALS.add(new Interval(                null,  new Date(2011, 7, 31),  0.000, false));
		INTERVALS.add(new Interval(new Date(2011, 8, 1),  new Date(2012, 10, 31), 0.050, true));
		INTERVALS.add(new Interval(new Date(2012, 11, 1), new Date(2013, 3, 31),  0.025, false));
		INTERVALS.add(new Interval(new Date(2013, 4, 1),  new Date(2013, 5, 31),  0.050, false));
	}

	public static double calculateStandardGel(double kwh, Date d1, Date d2) {
		if (Math.abs(kwh) < Utilities.MIN_KWH) {
			return 0;
		}

		if (d1 == null) {
			return ((Interval) INTERVALS.get(0)).tariff * kwh;
		} else if (d2 == null) {
			return ((Interval) INTERVALS.get(INTERVALS.size() - 1)).tariff * kwh;
		}

		double gel = 0;

		int distinction = Date.getIntervalInDays(d2, d1);
		if (distinction < 0)
			throw new RecalcException(String.format("Start date %1$s is greter than end date %2$s", new Object[] { d1, d2 }));
		if (distinction == 0)
			distinction = 1;
		for (int i = 0; i < INTERVALS.size(); i++) {
			Interval interval = (Interval) INTERVALS.get(i);
			Date t1 = interval.start;
			Date t2 = interval.end;
			int delta = 0;
			// NEW! this break was not used in electricity calculations !!!
			if (d1.isGreater(d2))
				break;
			boolean useFull = false;
			/* t1 .. d1 .. d2 .. t2 */
			if ((t1 == null || Date.isLessOrEqual(t1, d1)) && (t2 == null || Date.isGreaterOrEqual(t2, d2))) {
				delta = Date.getIntervalInDays(d2, d1);
			}
			/* t1 .. d1 .. t2 .. d2 */
			else if ((t1 == null || Date.isLessOrEqual(t1, d1)) && (t2 != null && Date.isGreater(t2, d1))) {
				delta = Date.getIntervalInDays(t2, d1);
			}
			/* d1 .. t1 .. t2 .. d2 */
			else if ((t1 != null && Date.isGreater(t1, d1)) && (t2 != null && Date.isLessOrEqual(t2, d2))) {
				delta = Date.getIntervalInDays(t2, t1) + 1;
				if (interval.useFull) {
					useFull = true;
				}
			}
			/* d1 .. t1 .. d2 .. t2 */
			else if ((t1 != null && Date.isLessOrEqual(t1, d2)) && (t2 == null || Date.isGreaterOrEqual(t2, d2))) {
				delta = Date.getIntervalInDays(d2, t1) + 1;
				if (interval.useFull) {
					useFull = true;
				}
			} else
				/* no intersection */continue;

			if (useFull) {
				gel += Math.round(kwh * interval.tariff * 100.0) / 100.0;
				break;
			}

			delta = delta == 0 ? 1 : delta;
			Date end = Date.addDays(d1, delta); // one day!!!
			double partKwh = kwh * delta / (1.f * distinction);
			gel += partKwh * interval.tariff;
			d1 = end;
		}
		return Math.round(gel * 100) / 100.0;
	}

	public static boolean isBetween(Date d1, Date d2, Date t) {
		if (d1 == null && d2 == null)
			return true;
		if (d1 == null && d2.isGreaterOrEqual(t))
			return true;
		if (d2 == null && d1.isLessOrEqual(t))
			return true;
		if (d1 == null || d2 == null)
			return false;
		if (d1.isLessOrEqual(t) && d2.isGreaterOrEqual(t))
			return true;
		return false;
	}

	public static boolean hasIntersection(Date d1, Date d2, Date t1, Date t2) {
		if (d1 == null && d2 == null)
			return true;
		if (t1 == null && t2 == null)
			return true;
		if (t1 != null && isBetween(d1, d2, t1))
			return true;
		if (t2 != null && isBetween(d1, d2, t2))
			return true;
		return false;
	}

	private static boolean isValuableDetail(DiffDetail detail) {
		return detail.isCharge() || detail.isVoucher();
	}

	private static boolean isValueableChargeElement(ChargeElement element, boolean isCycle, boolean isCharge, boolean isVoucher) {
		if (isCycle)
			return true;
		if (isVoucher)
			return true;
		if (!isCycle && isCharge && Math.abs(element.getGel()) > 0.0099)
			return true;
		return false;
	}

	private static boolean noSubsidyPeriod(Date date) {
		if (date.isGreaterOrEqual(new Date(2013, 2, 1)) && date.isLessOrEqual(new Date(2013, 3, 31))) {
			return true;
		}
		return false;
	}

	public static double[] calculateExpansion(CalculationItem item, List trashSubsidies, boolean isCharge, boolean isVoucher) {
		double kwh = 0;
		double gel = 0;
		if (item != null && item.getCharge() != null && item.getCharge().expansion() != null) {
			List expansion = item.getCharge().expansion();
			for (int i = 0; i < expansion.size(); i++) {
				ChargeElement element = (ChargeElement) expansion.get(i);
				if (isValueableChargeElement(element, item.getCycle(), isCharge, isVoucher)) {
					double partGel = calculateStandardGel(element.getKwh(), element.getStartDate(), element.getEndDate());
					double partKwh = element.getKwh();
					if (trashSubsidies == null || trashSubsidies.isEmpty()) {
						if (Math.abs(partGel) > Utilities.MIN_GEL) {
							kwh += partKwh;
							gel += partGel;
						}
					} else {
						if (Math.abs(partGel) > Utilities.MIN_GEL) {
							for (int j = 0; j < trashSubsidies.size(); j++) {
								TrashSubsidy sub = (TrashSubsidy) trashSubsidies.get(j);
								if (hasIntersection(sub.getStartDate(), sub.getEndDate(), element.getStartDate(), element.getEndDate())) {
									kwh += partKwh;
									gel += partGel;
									break;
								}
							}
						}
					}
				}
			}
		}
		return new double[] { kwh, gel };
	}

	public static List/* TrashVoucher */calculateTrashVouchers(List diffByCycle, List trashSubsidies) {
		double kwh = 0;
		double gel = 0;

		// calculate CHARGE vouchers
		for (int l = 0; l < diffByCycle.size(); l++) {
			DiffSummary diffSummary = (DiffSummary) diffByCycle.get(l);
			for (int i = 0; i < diffSummary.getDetails().size(); i++) {
				DiffDetail detail = (DiffDetail) diffSummary.getDetails().get(i);
				if (isValuableDetail(detail)) {
					CalculationItem originalItem = detail.getOriginalItem();
					CalculationItem derivedItem = detail.getDerivedItem();
					if (originalItem != null) {
						double[] orig = calculateExpansion(originalItem, null, detail.isCharge(), detail.isVoucher());
						if (Math.abs(orig[0]) > Utilities.MIN_KWH) {
							kwh -= orig[0];
							gel -= orig[1];
						}
					}
					if (derivedItem != null) {
						double[] drvd = calculateExpansion(derivedItem, null, detail.isCharge(), detail.isVoucher());
						if (Math.abs(drvd[0]) > Utilities.MIN_KWH) {
							kwh += drvd[0];
							gel += drvd[1];
						}
					}
				}
			}
		}

		// calculate SUBSIDY vouchers

		double subsidyKwh = 0;
		double subsidyGel = 0;

		if (trashSubsidies != null && !trashSubsidies.isEmpty()) {
			for (int l = 0; l < diffByCycle.size(); l++) {
				DiffSummary diffSummary = (DiffSummary) diffByCycle.get(l);
				for (int i = 0; i < diffSummary.getDetails().size(); i++) {
					DiffDetail detail = (DiffDetail) diffSummary.getDetails().get(i);
					if (isValuableDetail(detail)) {
						CalculationItem original = detail.getOriginalItem();
						CalculationItem derived = detail.getDerivedItem();
						if (original != null && !noSubsidyPeriod(original.getItemDate())) {
							double[] orig = calculateExpansion(original, trashSubsidies, detail.isCharge(), detail.isVoucher());
							if (Math.abs(orig[0]) > Utilities.MIN_KWH) {
								subsidyKwh += orig[0];
								subsidyGel += orig[1] / 2.0;
							}
						}
						if (derived != null && !noSubsidyPeriod(derived.getItemDate())) {
							double[] drvd = calculateExpansion(derived, trashSubsidies, detail.isCharge(), detail.isVoucher());
							if (Math.abs(drvd[0]) > Utilities.MIN_KWH) {
								subsidyKwh -= drvd[0];
								subsidyGel -= drvd[1] / 2.0;
							}
						}
					}
				}
			}
		}

		// voucher list

		List list = new ArrayList();

		if (Math.abs(kwh) > Utilities.MIN_KWH || Math.abs(gel) > Utilities.MIN_GEL) {
			TrashVoucher voucher = new TrashVoucher();
			voucher.setTrashOperation(TrashVoucher.OPER_CHARGE_CORRECTION_AFTER_2011.getId());
			voucher.setGel(gel);
			voucher.setKwh(kwh);
			list.add(voucher);
		}

		if (Math.abs(subsidyKwh) > Utilities.MIN_KWH || Math.abs(subsidyGel) > Utilities.MIN_GEL) {
			TrashVoucher voucher = new TrashVoucher();
			voucher.setTrashOperation(TrashVoucher.OPER_SUBSIDY_CORRECTION_AFTER_2011.getId());
			voucher.setGel(subsidyGel);
			voucher.setKwh(subsidyKwh);
			list.add(voucher);
		}

		return list;
	}

	// private static ITariff findTariff(Session session, int id) {
	// if (tariffs_cache.isEmpty()) {
	// try {
	// tariffs_cache = E1CustomerManagment.getTariffs(session);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }
	// for (int i = 0; i < tariffs_cache.size(); i++) {
	// ITariff t = (ITariff) tariffs_cache.get(i);
	// if (t.getId() == id)
	// return t;
	// }
	// return null;
	// }
	//
	// private static List tariffs_cache = new ArrayList();

}
