package telasi.recutil.calc.calc08;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Charge;
import telasi.recutil.beans.ChargeElement;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.ITariff;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.RecalcTariffItem;
import telasi.recutil.beans.tpowner.LossItem;
import telasi.recutil.beans.tpowner.TpOwnerAccount;
import telasi.recutil.beans.tpowner.TpOwnerCorrection;
import telasi.recutil.beans.tpowner.TpOwnerItem;
import telasi.recutil.beans.tpowner.TpOwnerRecalc;
import telasi.recutil.beans.tpowner.TpOwnerRecalcResult;
import telasi.recutil.beans.tpowner.TransformatorType;
import telasi.recutil.calc.RecalcException;
import telasi.recutil.calc.calc06.Calc2006Utils;
import telasi.recutil.calc.calc07.Utilities;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.E1CustomerManagment;

/**
 * Tp-Owners calculation utilities.
 * 
 * @author dimitri
 */
public class TpOwnerUtils {

	/**
	 * Get full tariffs list.
	 */
	public static List getTariffs(Session session) throws Exception {
		return E1CustomerManagment.getTariffs(session);
	}

	/**
	 * Get account tariff history.
	 */
	public static List getAccountTariffHistory(Session session, int accId, List allTariffs) throws Exception {
		String sql = "SELECT acct.compkey, acct.startdate, acct.enddate " +
			"FROM bs.acctariffs acct, bs.tarcomp tc " +
			"WHERE acct.compkey = tc.compkey AND tc.basecompkey = 1 AND acckey = ? " +
			"ORDER BY ACCTARKEY";
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, accId);
			res = st.executeQuery();
			List tariffs = new ArrayList();
			while (res.next()) {
				RecalcTariffItem tariff = new RecalcTariffItem();
				tariff.setStartDate(Date.create(res.getDate("startdate")));
				tariff.setEndDate(Date.create(res.getDate("enddate")));
				int compId = res.getInt("compkey");
				for (int i = 0; allTariffs != null && i < allTariffs.size(); i++) {
					ITariff comp = (ITariff) allTariffs.get(i);
					if (comp.getId() == compId) {
						tariff.setTariff(comp);
						break;
					}
				}
				tariffs.add(tariff);
			}
			return tariffs;
		} finally {
			try {res.close();} catch (Exception ex) {}
			try {st.close();} catch (Exception ex) {}
			st = null;
			res = null;
		}
	}

	/**
	 * Calculate TpOwner recalculation.
	 */
	public static TpOwnerRecalcResult calculate(Session session, TpOwnerRecalc recalc) throws Exception {
		// TODO: check whether there are all readings: if not, then no correction cannot be applied

		// get full tariff list and account tariff history
		List allTariffs = getTariffs(session);
		List accTariffs = getAccountTariffHistory(session, recalc.getProducer().getId(), allTariffs);

		// create and recalculate cycles
		List cycles = getCycles(recalc);
		List corrections = new ArrayList();
		
		for (int i = 0; cycles != null && i < cycles.size(); i++) {
			Cycle cycle = (Cycle) cycles.get(i);
			validateCycle(cycle, accTariffs);
			List cycleCorrections = calculateCycleCorrection(recalc.getProducer(), cycle, allTariffs);
			corrections.addAll(cycleCorrections);
		}
		
		// create correction results object
		TpOwnerRecalcResult results = new TpOwnerRecalcResult();
		results.getCorrections().addAll(corrections);

		// return results
		return results;
	}

	/**
	 * Calculates correction for cycle.
	 */
	private static List /*TpOwnerCorrection*/ calculateCycleCorrection(TpOwnerAccount mainAccount, Cycle cycle, List allTariffs) {

		// get main and sub items
		List mainItems = new ArrayList();
		List subItems = new ArrayList();
		List highSubItems = new ArrayList();
		for (int i = 0; cycle.items != null && i < cycle.items.size(); i++) {
			TpOwnerItem item = (TpOwnerItem) cycle.items.get(i);
			boolean isCharge = Utilities.isCharge(item.getOperation().getId());
			boolean isMainAccount = item.getAccount().getId() == mainAccount.getId();
			boolean isSubaccCharge = item.getOperation().getId() == Operation.SUB_ACCOUNT_CHARGE;
			boolean isHighVoltageSubaccCharge = isSubaccCharge && item.getBaseTariffId() != null
				&& isHighVoltageTariff(item.getBaseTariffId().intValue());
			if (isCharge && isMainAccount) {
				mainItems.add(item);
			} else if (isSubaccCharge && !isMainAccount && !isHighVoltageSubaccCharge) {
				subItems.add(item);
			} else if (isHighVoltageSubaccCharge) {
				highSubItems.add(item);
			}
		}

//		System.out.println(mainItems.size() + " : " + subItems.size() + " : " + highSubItems.size());

		// corrections list
		List correction = new ArrayList();

		// loop over charges in main item and calculate corrections
		for (int i = 0; mainItems != null && i < mainItems.size(); i++) {
			TpOwnerItem mainItem = (TpOwnerItem) mainItems.get(i);
			Charge charge = mainItem.getCalculatedCharge();
			for (int j = 0; charge.expansion() != null && j < charge.expansion().size(); j++) {
				ChargeElement che = (ChargeElement) charge.expansion().get(j);
				// analyze charge item
				int tariffId = che.getTariffId();
				boolean isHighVoltage = isHighVoltageTariff(tariffId);
				boolean isLowVoltage = isLowVoltageTariff(tariffId);
				boolean hasSubitems = subItems != null && !subItems.isEmpty();
				// no correction can be applied
				if (isHighVoltage && !hasSubitems)
					continue;
				if (!isHighVoltage && !isLowVoltage)
					continue;

				// -- Corrections...

				// high voltage side
				if (isHighVoltage) {

					// get subaacounts charge
					double[] subCharge = calcChargeForInterval(subItems, che.getStartDate(), che.getEndDate());
					double subKwh = subCharge[0];
					double subGel = subCharge[1];
					if (Math.abs(subKwh) < 0.01)
						continue; // no subaccount charge!

					// main charges - high subcharges
					double kwh0 = che.getKwh();
					double gel0 = che.getGel();
					double highSubCharges[] = calcChargeForInterval(highSubItems, che.getStartDate(), che.getEndDate());
					kwh0 += highSubCharges[0];
					gel0 += highSubCharges[1];

					// calculate loss
					double loss = calcLoss(mainAccount.getTransformatorType(), kwh0, che.getStartDate(), che.getEndDate());
					double lossPerKwh = loss/kwh0;

					// calculate corrected values
					double subLoss = Math.abs(subKwh) * lossPerKwh;
					double kwh1 = kwh0 - subLoss + subKwh;
					double gel1 = calculateGel(kwh1, che.getStartDate(), che.getEndDate(), che.getTariffId(), allTariffs);

					// -- Append corrections

					// discharge
					TpOwnerCorrection corr1 = createCorrectionItem(mainItem, che.getStartDate(), che.getEndDate(), -kwh0 - subKwh, -gel0 - subGel, TpOwnerCorrection.DISCHARGE);
					correction.add(corr1);

					// recharge
					TpOwnerCorrection corr2 = createCorrectionItem(mainItem, che.getStartDate(), che.getEndDate(), kwh1, gel1, TpOwnerCorrection.RECHARGE);
					correction.add(corr2);

				} else if (isLowVoltage) {

					// get subaacounts charge
					double[] subCharge = calcChargeForInterval(subItems, che.getStartDate(), che.getEndDate());
					double subKwh = subCharge[0];
					double subGel = subCharge[1];

					// main charges
					double kwh0 = che.getKwh();
					double gel0 = che.getGel();

					// calculate loss
					double loss = calcLoss(mainAccount.getTransformatorType(), kwh0, che.getStartDate(), che.getEndDate());
					double lossPerKwh = loss/kwh0;

					// calculate corrected values
					double subLoss = Math.abs(subKwh) * lossPerKwh;
					double kwh1 = kwh0 + loss - subLoss + subKwh;
					int joinTariffId = getJoinTariddId(che.getTariffId());
					double gel1 = calculateGel(kwh1, che.getStartDate(), che.getEndDate(), joinTariffId, allTariffs);

					//	-- Append corrections

					// discharge
					TpOwnerCorrection corr1 = createCorrectionItem(mainItem, che.getStartDate(), che.getEndDate(), -kwh0 - subKwh, -gel0 - subGel, TpOwnerCorrection.DISCHARGE);
					correction.add(corr1);

					// recharge
					TpOwnerCorrection corr2 = createCorrectionItem(mainItem, che.getStartDate(), che.getEndDate(), kwh1, gel1, TpOwnerCorrection.RECHARGE);
					correction.add(corr2);
				}

				// TODO: E-corrections
				// As I think now: we need use corrected kWh always in the calculations above...
			}
		}

		// return correction
		return correction;
	}

	/**
	 * Correction item creation utility.
	 */
	private static TpOwnerCorrection createCorrectionItem(TpOwnerItem item, Date d1, Date d2, double kwh, double gel, int operTypeId) {
		TpOwnerCorrection corr = new TpOwnerCorrection();
		corr.setMainItem(item);
		corr.setStartDate(d1);
		corr.setEndDate(d2);
		corr.setKwh(kwh);
		corr.setGel(gel);
		corr.setType(operTypeId);
		return corr;
	}

	/**
	 * Calculate loss.
	 */
	private static double calcLoss(TransformatorType trans, double kwh, Date d1, Date d2) {
		int distinction = Date.getIntervalInDays(d2, d1);
		double power = trans.getPower() * distinction * 24.0;
		if (kwh > power) {
			throw new IllegalArgumentException(String.format("დარიცხვა რომელიც დაფიქსირდა %1$s-დან %2$s-მდე " 
					+ "აღემატება ტრანსფორმატორის სიმძლავრეს. არ ვიცი როგორ უნდა მოვიქცე ასეთ ვითარებაში.",
					new Object[] {d1, d2}));
		}
		double zeroLoss = trans.getZeroLoss() * distinction / 30.0;
		double busy = kwh / power;
		double lossCoeff = 0;
		for (int i = 0; trans.getLossItems() != null && i < trans.getLossItems().size(); i++) {
			LossItem item = (LossItem) trans.getLossItems().get(i);
			if (busy > item.getBusyFrom() && busy <= item.getBusyTo()) {
				lossCoeff = item.getBusyLoss() * 0.01;
			}
		}
		return kwh * lossCoeff + zeroLoss;
	}

	/**
	 * Extracts kWh charge from items for given interval.
	 */
	private static double[] calcChargeForInterval(List items, Date d1, Date d2){
		double kwh = 0;
		double gel = 0;
		int distinction = Date.getIntervalInDays(d2, d1);
	    if (distinction < 0)
	    	throw new RecalcException(String.format("საწყისი თარიღი %1$s მეტია საბოლოო თარიღზე %2$s.", new Object[] {d1, d2}));
	    if (distinction == 0)
	    	distinction = 1;
		for (int i = 0; items != null && i < items.size(); i++) {
			TpOwnerItem item = (TpOwnerItem) items.get(i);
			Charge ch = item.getCalculatedCharge();
			for (int j = 0; ch != null && j < ch.expansion().size(); j++) {
				ChargeElement che = (ChargeElement) ch.expansion().get(j);
				Date t1 = che.getStartDate();
				Date t2 = che.getEndDate();
				
				
				
				int delta = 0;
				/* t1 .. d1 .. d2 .. t2 */ 
				if (       (t1 == null || Date.isLessOrEqual(t1, d1))
		                && (t2 == null || Date.isGreaterOrEqual(t2, d2)) )
		            delta = Date.getIntervalInDays(d2, d1);
		        /* t1 .. d1 .. t2 .. d2 */
		        else if (  (t1 == null || Date.isLessOrEqual(t1, d1))
		                && (t2 != null && Date.isGreater(t2, d1)) )
		            delta = Date.getIntervalInDays(t2, d1);
		        /* d1 .. t1 .. t2 .. d2 */
		        else if (  (t1!=null && Date.isGreater(t1, d1))
		                && (t2!=null && Date.isLessOrEqual(t2, d2))    )
		            delta = Date.getIntervalInDays(t2, t1) + 1;
		        /* d1 .. t1 .. d2 .. t2 */
		        else if (  (t1 != null && Date.isLessOrEqual(t1, d2))
		                && (t2 == null || Date.isGreaterOrEqual(t2, d2)) )
		            delta = Date.getIntervalInDays(d2, t1) + 1;
		        else /*no intersection*/
		        	continue;
				delta = delta == 0 ? 1 : delta;
				double coeff = 0;
				// @since 24-Aug-2011 
				if (delta < distinction) {
					coeff = 1;
				} else {
					coeff = distinction * 1.d / delta;
				}
		        double partKwh = ch.getKwh() * coeff;
		        double partGel = ch.getGel() * coeff;
		        kwh += Calc2006Utils.round(partKwh);
		        gel += Calc2006Utils.round(partGel);
			}
		}
		return new double[] { kwh, gel };
	}

	/**
	 * Validate cycle. During this procedure each item in the cycle is recalculated
	 * and recalculated charge is compared with existing one. If some
	 * difference is detected exception will be thrown.
	 */
	private static void validateCycle(Cycle cycle, List accTariffs) {
		// validate each charge and subaccount charge item
		for (int i = 0; cycle.items != null && i < cycle.items.size(); i++) {
			TpOwnerItem item = (TpOwnerItem) cycle.items.get(i);
			boolean isCharge = Utilities.isCharge(item.getOperation().getId());
			boolean isSubaccCharge = item.getOperation().getId() == Operation.SUB_ACCOUNT_CHARGE;
			if (isCharge || isSubaccCharge) {
				double kwh = item.getKwh();
				Date startDate = item.getPreviousChargeDate() == null ? cycle.startDate : item.getPreviousChargeDate();
				Date endDate = item.getItemDate();
				Charge charge = calculateCharge(kwh, startDate, endDate, accTariffs);
				double gelToCompare = item.getGel();
				double gelCalculated = charge.getGel();
				if (Math.abs(gelCalculated - gelToCompare) >= 0.01) {
					throw new IllegalArgumentException(
							String.format("გადათვლის შედეგად %1$s-ში ვერ მივიღე ლარის იგივე მნიშვნელობა. " +
									"ასეთ სიტუაციაში თვლა ჩემს ძალებს აღემატება.",
									new Object[] {Date.format(item.getItemDate())})
					);
				}
				// XXX: charge is recalculated for it's corrected value
				if (Math.abs(item.getKwh() - item.getKwhCorrected()) >= 0.01) {
					charge = calculateCharge(item.getKwhCorrected(), startDate, endDate, accTariffs);
				}
				item.setCalculatedCharge(charge);
			}
		}
	}

	/**
	 * Calculate GEL for the single tariff.
	 */
	private static double calculateGel(double kwh, Date startDate, Date endDate, int tariffId, List allTariffs) {
		// find tariff
		ITariff tariff = null;
		for (int i = 0; allTariffs != null && i < allTariffs.size(); i++) {
			ITariff tar = (ITariff) allTariffs.get(i);
			if (tar.getId() == tariffId) {
				tariff = tar;
				break;
			}
		}
		if (tariff == null)
			return 0;

		// create tariff history from a single record.
		List tariffs = new ArrayList();
		RecalcTariffItem rec = new RecalcTariffItem();
		rec.setTariff(tariff);
		tariffs.add(rec);

		// get charge
		Charge charge = calculateCharge(kwh, startDate, endDate, tariffs);
		return charge.getGel();
	}

	/**
	 * Calculate charge for the given kWh in given period.
	 */
	private static Charge calculateCharge(double kwh, Date startDate, Date endDate, List accTariffs) {
		List expansion = Utilities.expand(kwh, startDate, endDate, accTariffs);
		Charge charge = new Charge();
		for (int i = 0; expansion != null && i < expansion.size(); i++) {
			ChargeElement element = (ChargeElement) expansion.get(i);
			charge.addElement(element);
		}
		return charge;
	}

	/**
	 * This type is used to describe cycle structure.
	 */
	static class Cycle {
		/**
		 * Previous cycle date.
		 */
		Date startDate;
		/**
		 * Current cycle date.
		 */
		Date endDate;
		/**
		 * Cycle items.
		 */
		List items = new ArrayList();
	}

	/**
	 * Creates cycles which are associated with this recalculation.
	 */
	private static List /*Cycle*/ getCycles(TpOwnerRecalc recalc) {
		List cycles = new ArrayList();
		Date previousCycleDate = null;
		Cycle cycle = null;
		TpOwnerItem item = null;
		for (int i = 0; recalc.getItems() != null && i < recalc.getItems().size(); i++) {
			item = (TpOwnerItem) recalc.getItems().get(i);
			if (previousCycleDate == null || (item.isCycle() && !Date.isEqual(item.getItemDate(), previousCycleDate))) {
				if (cycle != null) {
					if (previousCycleDate != null) {
						cycle.startDate = previousCycleDate;
					} else {
						// first cycle!
						cycle.startDate = findPreviousCycleDateForTheFirstCycle(recalc);
					}
					cycle.endDate = item.getItemDate();
					cycles.add(cycle);
				}
				cycle = new Cycle();
				previousCycleDate = item.getItemDate();
			}
			cycle.items.add(item);
		}
		if (cycle != null) {
			if (previousCycleDate != null) {
				cycle.startDate = previousCycleDate;
			} else {
				// first cycle!
				cycle.startDate = findPreviousCycleDateForTheFirstCycle(recalc); 
			}
			cycle.endDate = item.getItemDate();
			cycles.add(cycle);
		}
		return cycles;
	}

	/**
	 * Find previous cycle date for the first cycle.
	 * ----------------------------------------------------------------------------
	 * For the first cycle we assume that the previous cycle starts where the
	 * minimum previous charge date is fixed for the producer account.
	 * Other account charges should be cutted on that date if they
	 * have earlier previous dates.
	 * ----------------------------------------------------------------------------
	 */
	private static Date findPreviousCycleDateForTheFirstCycle(TpOwnerRecalc recalc) {
		Date date = null;
		for (int i = 0; recalc.getItems() != null && i < recalc.getItems().size(); i++) {
			TpOwnerItem item = (TpOwnerItem) recalc.getItems().get(i);
			if (item.getAccount().getId() == recalc.getProducer().getId() && item.getPreviousChargeDate() != null) {
				date = item.getPreviousChargeDate();
				break;
			}
		}
		return date;
	}

	/**
	 * Is this tariff component associated with low voltage side?
	 */
	public static boolean isLowVoltageTariff(int tariffId) {
		return tariffId == 80 || tariffId == 81;
	}

	/**
	 * Is this tariff component associated with high voltage side?
	 */
	public static boolean isHighVoltageTariff(int tariffId) {
		return tariffId == 82 || tariffId == 83;
	}

	public static int getJoinTariddId(int tariffId) {
		switch(tariffId) {
		case 80:
			return 82;
		case 81:
			return 83;
		case 82:
			return 80;
		case 83:
			return 81;
		default:
			return tariffId;
		}		
	}

}

