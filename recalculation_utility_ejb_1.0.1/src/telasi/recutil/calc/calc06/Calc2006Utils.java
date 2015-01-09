/*
 *   Copyright (C) 2006 by JSC Telasi
 *   http://www.telasi.ge
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the
 *   Free Software Foundation, Inc.,
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package telasi.recutil.calc.calc06;

import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.beans.RecalcCutItem;
import telasi.recutil.beans.RecalcTariffItem;
import telasi.recutil.calc.RecalcException;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.E1BOPManagment;
import telasi.recutil.utils.CoreUtils;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Dec, 2006
 */
public class Calc2006Utils {

	//
	// Round operations
	//

	public static double round(double number) {
		return round(number, 2);
	}

	public static double round(double number, int precision) {
		int power = (int) Math.pow(10, precision);
		double num2 = Math.round(number * power);
		return num2 / power;
	}

	//
	// Charge determination
	//

	public static boolean isReading(int operId) {
		switch (operId) {
		case Operation.READING:
		case Operation.CONTROL_READING:
		case Operation.SALE:
		case Operation.BALANCE_READING:
		case Operation.METER_INSTALL:
		case Operation.METER_DEINSTALL:
		case Operation.CUT_READING:
		case Operation.REPAIR_READING:
			return true;
		default:
			return false;
		}
	}

	public static boolean isCharge(int operId) {
		if (isReading(operId)) {
			return true;
		}
		switch (operId) {
		case Operation.ESTIMATE:
		case Operation.WITHOUT_METER:
		case Operation.NOT_OPERABLE_METER:
			return true;
		default:
			return false;
		}
	}

	public static boolean isLikePayment(Operation oper) {
		return oper.getType().getId() == 3 || oper.getType().getId() == 5;
	}

	//
	// Reading and kWh derivation
	//

	public static double deriveReading(double r1, double r2, Date d1, Date d2, Date d, int digits) {
		return deriveReading(r1, r2, d1, d2, d, digits, false);
	}

	public static double deriveReading(double r1, double r2, Date d1, Date d2,
			Date d, int digits, boolean inMeterRange) {

		double read1 = r1;
		double read2 = r2;

		double meterMaxReading = (double) Math.pow(10, digits);

		while (read1 > read2) {
			read2 += meterMaxReading;
		}

		if (Date.isEqual(d1, d2)) {
			return r2;
		}

		int interval1 = Date.getIntervalInDays(d2, d1);
		int interval2 = Date.getIntervalInDays(d, d1);

		double newReading = read1 + (read2 - read1) * interval2 * 1.f
				/ interval1;

		// ignore meter range
		if (!inMeterRange) {
			return newReading;
		}

		// corrct range

		double newReadingInRange = newReading;
		while (newReadingInRange >= meterMaxReading) {
			newReadingInRange -= meterMaxReading;
		}

		if (Math.abs(newReadingInRange) < CoreUtils.MIN_KWH) {
			newReadingInRange = meterMaxReading;
		}

		return newReadingInRange;
	}

	public static double deriveKwh(double currR, double prevR, double coeff,
			int digits, boolean useRound, double acceleration) {

		double read1 = prevR;
		double read2 = currR;

		if (coeff < 1.f) {
			coeff = 1.f;
		}

		if (acceleration < -99f) {
			acceleration = -99f;
		}

		double factor = 1.f / (1.f + .01f * acceleration);
		coeff *= factor;

		while (read1 > read2) {
			read2 += Math.pow(10, digits);
		}

		if (useRound) {
			// return Math.round(read2 * coeff - read1 * coeff);
			return Math.round(read2 * coeff) - Math.round(read1 * coeff);
		} else {
			return (read2 - read1) * coeff;
		}

	}

//	//
//	// derived item from a common one
//	//
//
//	public static DerivedItem createDerivedItem(RecalcItem item) {
//		DerivedItem derived = new DerivedItem();
//
//		derived.setAccount(item.getAccount());
//		derived.setCustomer(item.getCustomer());
//		derived.setGel(item.getGel());
//		derived.setItemDate(item.getItemDate());
//		derived.setCycle(item.getCycle());
//		derived.setKwh(item.getKwh());
//		derived.setOperation(item.getOperation());
//		derived.setReading(item.getReading());
//		derived.setBalance(0.f);
//		derived.setProducer(item);
//
//		return derived;
//
//	}

//	//
//	// history lookups
//	//
//
//	static DerivedItem getLastReading(Account account, List items) {
//		return getLastReading(account, items, true);
//	}
//
//	static DerivedItem getLastCharge(Account account, List items) {
//		return getLastReading(account, items, false);
//	}
//
//	private static DerivedItem getLastReading(Account account, List items, boolean realReading) {
//
//		// empty history
//		if (items == null || items.isEmpty()) {
//			return null;
//		}
//
//		// look up for the item
//		for (int i = items.size() - 1; i >= 0; i--) {
//
//			DerivedItem item = (DerivedItem) items.get(i);
//
//			if (realReading && isReading(item.getOperation().getId())
//					&& account.getId() == item.getAccount().getId()) {
//				return item;
//			}
//			if (!realReading && isCharge(item.getOperation().getId())
//					&& account.getId() == item.getAccount().getId()) {
//				return item;
//			}
//		}
//
//		// can not be found
//		return null;
//	}

	public static boolean stepExists(List tariffs, Date d1, Date d2) {
		boolean stepExists = false;
		if (tariffs != null) {
			for (int i = 0; i < tariffs.size(); i++) {
				RecalcTariffItem item = (RecalcTariffItem) tariffs.get(i);
				if (item.getTariff().isStep()) {
					Date td1 = item.getStartDate();
					Date td2 = item.getEndDate();
					boolean c1 = d1 != null
							&& (td1 == null || Date.isLess(td1, d1))
							&& (td2 != null && Date.isLess(td2, d1));
					boolean c2 = d2 != null
							&& (td1 != null && Date.isGreater(td1, d2))
							&& (td2 == null || Date.isGreater(td2, d2));
					stepExists = !c1 && !c2;
				}
			}
		}
		return stepExists;
	}

	//
	// create operation by id
	//

	private static List BILL_OPERATIONS = new ArrayList();

	private static void refresh(boolean forceNew) {
		synchronized (BILL_OPERATIONS) {
			if (forceNew || BILL_OPERATIONS == null
					|| BILL_OPERATIONS.isEmpty()) {
				Session session = null;
				try {
					session = new Session();
					BILL_OPERATIONS = E1BOPManagment
							.selectBillingOperations(session);
					if (BILL_OPERATIONS == null) {
						BILL_OPERATIONS = new ArrayList();
					}
				} catch (Throwable t) {
					throw new RecalcException(t.toString());
				} finally {
					if (session != null) {
						session.close();
					}
				}
			}
		}
	}

	private static Operation lookupOperation(int id) {
		Operation operation = null;
		if (BILL_OPERATIONS != null) {
			for (int i = 0; i < BILL_OPERATIONS.size(); i++) {
				OperationType operType = (OperationType) BILL_OPERATIONS.get(i);
				if (operType.getOperations() != null) {
					for (int j = 0; j < operType.getOperations().size(); j++) {
						Operation oper = (Operation) operType.getOperations().get(j);
						if (oper.getId() == id) {
							operation = oper;
							break;
						}
					}
				}
			}
		}
		return operation;
	}

	public static Operation findOperationById(int id) {
		refresh(false);
		Operation oper = lookupOperation(id);
		if (oper == null) {
			refresh(true);
			oper = lookupOperation(id);
		}
		if (oper == null) {
			throw new RecalcException("Can not find operation with ID = " + id);
		}
		return oper;
	}

	/**
	 * Get charge intervals, which were not cutted.
	 * 
	 * @param d1
	 *            charge period start date
	 * @param d2
	 *            charge period end date
	 * @param cuts
	 *            cut item
	 * @return intersections
	 */
	public static Date[] getChargeIntervals(Date d1, Date d2, List cuts) {
		// no cut
		if (cuts == null || cuts.isEmpty()) {
			return new Date[] { d1, d2 };
		}

		// equal dates
		if (d1.equals(d2)) {
			return new Date[] { d1, d2 };
		}

		List intervals = new ArrayList();
		Date markDate = d1;
		boolean makeFinalStep = true;

		for (int i = 0; i < cuts.size(); i++) {
			RecalcCutItem item = (RecalcCutItem) cuts.get(i);
			Date c1 = item.getStartDate();
			Date c2 = item.getEndDate();
			// #1. full cut: consider charge period in final date [d2, d2]
			if ((c1 == null || Date.isLessOrEqual(c1, d1))
					&& (c2 == null || Date.isGreaterOrEqual(c2, d2))) {
				return new Date[] { d2, d2 };
			}
			// #2. charge period not yet reached
			else if ((c1 == null || Date.isLess(c1, d1))
					&& (c2 != null && Date.isLess(c2, d1))) {
				continue;
			}
			// #3. charge period is over-reached
			else if ((c1 != null && Date.isGreater(c1, d2))
					&& (c2 == null || Date.isGreater(c2, markDate))) {
				makeFinalStep = true;
				break; // nothing to do with futher cut intervals
			}
			// #4. cut inside the charge interval
			else {
				if ((c1 == null || Date.isLessOrEqual(c1, d1))) {
					markDate = c2;
				} else if ((c1 != null && Date.isGreater(c1, d1))
						&& (c2 != null && Date.isLess(c2, d2))) {
					if (!Date.isEqual(markDate, c1)) {
						intervals.add(markDate);
						intervals.add(c1);
					}
					markDate = c2;
				} else {
					if (!Date.isEqual(markDate, c1)) {
						intervals.add(markDate);
						intervals.add(c1);
					}
					makeFinalStep = false;
					break;
				}
			}
		}
		if (makeFinalStep) {
			intervals.add(markDate);
			intervals.add(d2);
		}

		Date[] dateIntervals = new Date[intervals.size()];
		for (int i = 0; i < intervals.size(); i++) {
			dateIntervals[i] = (Date) intervals.get(i);
		}

		return dateIntervals;
	}
}
