/*
 *   Copyright (C) 2007 by JSC Telasi
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
package telasi.recutil.calc.calc07;

import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.CalculationInterval;
import telasi.recutil.beans.CalculationItem;
import telasi.recutil.beans.Charge;
import telasi.recutil.beans.ChargeElement;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.DiffSummary;
import telasi.recutil.beans.FacturaDetail;
import telasi.recutil.beans.ITariff;
import telasi.recutil.beans.Meter;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcCutItem;
import telasi.recutil.beans.RecalcInstCp;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.RecalcRegularItem;
import telasi.recutil.beans.RecalcTariffItem;
import telasi.recutil.beans.SubsidyAttachment;
import telasi.recutil.calc.DivisionDateFactory;
import telasi.recutil.calc.IDivisionDate;
import telasi.recutil.calc.RecalcException;
import telasi.recutil.calc.calc06.Calc2006Utils;
import telasi.recutil.calc.calc06.CalcMessage;
import telasi.recutil.service.Session;
import telasi.recutil.utils.CoreUtils;

/**
 * Utility classes for billing calculations.
 * 
 * @author <a href="mailto:dimakura@gmail.com">Dimitri Kurashvili</a>
 */
public class Utilities {
	//
	// Constants
	//
	/**
	 * Minimum GEL charge.
	 */
	public static final double MIN_GEL = CoreUtils.MIN_GEL;
	/**
	 * Mininum kWh charge.
	 */
	public static final double MIN_KWH = CoreUtils.MIN_KWH;
	/**
	 * The maximum period of using installed capacity when calculating using
	 * standard procedure.
	 */
	public static final int INSTCP_USING_MAX_VALIDITY_PERIOD = 60;

	//
	// Unique ID generation
	//
	private static long ID = -10000L;

	private static long newId() {
		return ID--;
	}

	//
	// Classification
	//

	/**
	 * Is given operation of the reading group?
	 */
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

	/**
	 * Is given operation of the charge group?
	 */
	public static boolean isCharge(int operId) {
		if (isReading(operId))
			return true;
		switch (operId) {
		case Operation.ESTIMATE:
		case Operation.WITHOUT_METER:
		case Operation.NOT_OPERABLE_METER:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Does operation behave like payment?
	 */
	public static boolean isLikePayment(Operation oper) {
		int type = oper.getType().getId();
		return type == OperationType.SUBSIDY || type == OperationType.PAYMENT;
	}

	//
	// Round operations
	//

	/**
	 * Rounds an argument number with precision of 2 decimal digits.
	 * 
	 * @param number
	 *            number to be rounded
	 * @return rounded result, with precision of 2 decimal digits
	 */
	public static double round(double number) {
		return round(number, 2);
	}

	/**
	 * Rounds an argument number with specified precision.
	 * 
	 * @param number
	 *            number to be rounded
	 * @param precision
	 *            precision, that is a
	 * @return
	 */
	public static double round(double number, int precision) {
		int power = (int) Math.pow(10, precision);
		double num2 = Math.round(number * power);
		return num2 / power;
	}

	//
	// Reading and Kwh derivations
	// Lookup
	//

	/**
	 * Derives reading inside given interval. This function is mainly used when
	 * dealing with estimates correction.
	 * 
	 * @param r1
	 *            reading in the interval start
	 * @param r2
	 *            reading in the interval end
	 * @param d1
	 *            interval start date
	 * @param d2
	 *            interval end date
	 * @param d
	 *            date inside given interval
	 * @param digits
	 *            meter digits
	 * @param inMeterRange
	 *            is derived reading should be in the meter range?
	 */
	public static double deriveReading(double r1, double r2, Date d1, Date d2, Date d, int digits, boolean inMeterRange) {
		/* adjust readings */
		double read1 = r1;
		double read2 = r2;
		double meterMaxReading = (double) Math.pow(10, digits);
		while (read1 > read2)
			read2 += meterMaxReading;
		/* when interval dates are equal */
		if (Date.isEqual(d1, d2))
			return round(r2, 2);
		/* distinctions */
		int interval1 = Date.getIntervalInDays(d2, d1);
		int interval2 = Date.getIntervalInDays(d, d1);
		/* new reading value */
		double newReading = read1 + (read2 - read1) * interval2 * 1.f / interval1;
		/* adjust to the meter range, when required */
		if (inMeterRange) {
			while (newReading >= meterMaxReading)
				newReading -= meterMaxReading;
			if (Math.abs(newReading) < CoreUtils.MIN_KWH)
				newReading = meterMaxReading;
		}
		return round(newReading, 2);
	}

	/**
	 * Derives kWh charge using two readings and meter parameters.
	 * 
	 * @param currR
	 *            current/last value of the reading
	 * @param prevR
	 *            previous reading value
	 * @param coeff
	 *            meter coefficient
	 * @param digits
	 *            meter digits
	 * @param useRound
	 *            round or not obtained result?
	 * @param acceleration
	 *            meter acceleration
	 * @return
	 */
	public static double deriveKwh(double currR, double prevR, double coeff, int digits, double acceleration) {
		double read1 = prevR;
		double read2 = currR;
		if (coeff < 1.d)
			coeff = 1.d;
		if (acceleration < -99d)
			acceleration = -99d;
		double factor = 1.d / (1.d + .01d * acceleration);
		coeff *= factor;
		while (read1 > read2)
			read2 += Math.pow(10, digits);
		return round(read2 * coeff, 0) - round(read1 * coeff, 0);
	}

	/**
	 * Looks up for the last reading/charge in calculated history.
	 * 
	 * @param account
	 *            account for which we need to lookup
	 * @param items
	 *            previous history
	 * @param realReading
	 *            whether we need a real reading or an arbitrary charge
	 * @param forceCycle
	 *            whether we are looking for cycle item
	 * @param considerCurrentAsCharge
	 *            consider current charge operation as charge: this is needed
	 *            for example when recalculating percent based additional
	 *            charges or subsidies
	 * @return last reading or charge
	 */
	public static CalculationItem getLastReading(Account account, List items, boolean realReading, boolean forceCycle, boolean considerCurrentAsCharge) {
		/* empty history */
		if (items == null || items.isEmpty())
			return null;
		/* look up for the item */
		for (int i = items.size() - 1; i >= 0; i--) {
			CalculationItem item = (CalculationItem) items.get(i);
			if (realReading && isReading(item.getOperation().getId()) && account.getId() == item.getAccount().getId())
				if (!forceCycle)
					return item;
				else if (forceCycle && item.getCycle())
					return item;
			if (!realReading && (isCharge(item.getOperation().getId()) || (item.getOperation().getId() == Operation.CURRENT_CHARGE && considerCurrentAsCharge)) && account.getId() == item.getAccount().getId())
				if (!forceCycle)
					return item;
				else if (forceCycle && item.getCycle())
					return item;
		}
		/* nothing can not be found */
		return null;
	}

	/**
	 * Look up for the previous operation date of given item. It combines
	 * searches in both recalculated and initial histories. When
	 * <code>prevReading</code> parameter is defined (and it is cyclic if cycle
	 * lookup is required), then the date is an itemdate of this item. Otherwise
	 * search will be continued in initial history, from the interval which is
	 * before the one we are currently working in.
	 * 
	 * @param newItem
	 *            new item
	 * @param prevReading
	 *            previous reading, in recalculation, <code>null</code> if not
	 *            defined
	 * @param lookCycle
	 *            look for cycle
	 * @return date of the previous reading
	 */
	public static Date getPreviousReadingDate(RecalcItem newItem, CalculationItem prevReading, boolean lookCycle) {
		if (newItem.getPreviousOperationDate() != null)
			return newItem.getPreviousOperationDate();
		if (prevReading != null) {
			if (!lookCycle)
				return prevReading.getItemDate();
			else if (lookCycle && prevReading.getCycle())
				return prevReading.getItemDate();
		}
		RecalcInterval interval = newItem.getInterval();
		int myIndex = interval.getDetails().getIntervals().indexOf(interval);
		int accId = newItem.getAccount().getId();
		for (int j = myIndex - 1; j >= 0; j--) {
			RecalcInterval prevInterval = (RecalcInterval) interval.getDetails().getIntervals().get(j);
			for (int k = prevInterval.getItems().size() - 1; k >= 0; k--) {
				RecalcItem prevItem = (RecalcItem) prevInterval.getItems().get(k);
				/* not the same account */
				if (prevItem.getAccount().getId() != accId)
					continue;
				/* deleted item, not to take into account */
				if (prevItem.getStatus() == RecalcItem.DELETED)
					continue;
				/* whether is a cycle */
				if (lookCycle && !prevItem.getCycle())
					continue;
				/* this is a previous charge */
				if (isCharge(prevItem.getOperation().getId()))
					return prevItem.getItemDate();
			}
		}
		return newItem.getAccount().getCreationDate();
	}

	/**
	 * Check step tariff existence inside given dates interval.
	 */
	public static boolean isStepTariffInChargePeriod(Date d1, Date d2, List tariffs) {
		Charge c = new Charge();
		ChargeElement element = createChargeElement(d1, d2, 0, 0, -1, false, false);
		c.addElement(element);
		return isStepTariffInChargePeriod(c, tariffs);
	}

	/**
	 * Determines whether step tariff exists in the interval described by the
	 * interval
	 * 
	 * @param ch
	 *            charge which holds the periods
	 * @param tariffs
	 *            tariffs list
	 * @return <code>true</code> when step tariff item has intersection with any
	 *         period in the charge
	 */
	public static boolean isStepTariffInChargePeriod(Charge ch, List tariffs) {
		/* for empty charge, nothing to find */
		if (ch == null || ch.expansion().isEmpty())
			return false;
		/* the same for empty tariffs */
		if (tariffs == null || tariffs.isEmpty())
			return false;
		for (int i = 0; i < ch.expansion().size(); i++) {
			ChargeElement el = (ChargeElement) ch.expansion().get(i);
			Date d1 = el.getStartDate();
			Date d2 = el.getEndDate();
			for (int j = 0; j < tariffs.size(); j++) {
				RecalcTariffItem tariff = (RecalcTariffItem) tariffs.get(j);
				if (!tariff.getTariff().isStep())
					continue; // we are not interested with them
				Date t1 = tariff.getStartDate();
				Date t2 = tariff.getEndDate();
				if (!(((t1 == null || Date.isLessOrEqual(t1, d1)) && (t2 != null && Date.isLessOrEqual(t2, d1))) || ((t1 != null && Date.isGreaterOrEqual(t1, d2)) && (t2 == null || Date.isGreaterOrEqual(t2, d2)))))
					/*
					 * Hope this condition is right :)
					 */
					return true;
			}
		}
		/* step was not discovered */
		return false;
	}

	//
	// Billing calculations
	//

	/**
	 * Returns count of days for the previous month. Month is defined using an
	 * argument date.
	 * 
	 * @param d
	 *            date
	 * @return count of days in the month previous to the given date
	 */
	public static int getPrevMonthDaysCount(Date d) {
		return getPrevMonthDaysCount(d.getMonth(), d.getYear());
	}

	/**
	 * Returns count of days for the previous month. Month is defined using
	 * month and year.
	 * 
	 * @param month
	 *            month as it is defined in ge.telasi.recut.core.types.Date
	 *            class
	 * @param year
	 *            year as it is defined in ge.telasi.recut.core.types.Date class
	 * @return count of days in previous month
	 */
	public static int getPrevMonthDaysCount(int month, int year) {
		switch (month) {
		case 1: // january --> december
		case 2: // februry --> january
		case 4: // april --> march
		case 6: // june --> may
		case 8: // august --> july
		case 9: // september --> august
		case 11: // november --> october
			return 31;
		case 3: // march --> february
			if (year % 4 == 0) {
				return 29;
			} else {
				return 28;
			}
		default: // others
			return 30;
		}
	}

	/**
	 * Calculate expansion of homogenous kWh charge in given interval and with
	 * given tariff history.
	 * 
	 * @param kwh
	 *            kWh charge during
	 * @param d1
	 *            interval start date
	 * @param d2
	 *            interval end date
	 * @param tariffs
	 *            tariff history
	 * @return expansion of kWh charge by tariff change intervals with
	 *         appropriate GEL charges
	 */
	public static List expand(double kwh, Date d1, Date d2, List tariffs) {
		/* check whether dates are defined */
		if (d1 == null || d2 == null)
			throw new RecalcException("Interval dates should be defined");
		/* check distinction */
		int distinction = Date.getIntervalInDays(d2, d1);
		if (distinction < 0)
			throw new RecalcException(String.format("Start date %1$s is greter than end date %2$s", new Object[] { d1, d2 }));
		/*
		 * make distinction adjustment: when d1=d2 we considerthis situation as
		 * d2=d1+1!
		 */
		if (distinction == 0)
			distinction = 1;
		/* create empty expansion */
		List expansion = new ArrayList();
		/* when kWh is too small or tariffs are not defined */
		if (Math.abs(kwh) < MIN_KWH || tariffs == null || tariffs.isEmpty()) {
			expansion.add(createChargeElement(d1, d2, kwh, 0.f, -1, false, false));
			return expansion;
		}
		/* loop over tariff history */
		for (int i = 0; i < tariffs.size(); i++) {
			RecalcTariffItem item = (RecalcTariffItem) tariffs.get(i);
			Date t1 = item.getStartDate(); /* tariff item start date */
			Date t2 = item.getEndDate(); /* tariff item end date */
			if ((t1 != null && Date.isGreater(t1.prevDay(), d1) && Date.isLessOrEqual(t1, d2)) && (t2 == null || Date.isGreater(t2, d1))) {
				double partKwh = kwh * Date.getIntervalInDays(t1.prevDay(), d1) / (1.f * distinction);
				double partGel = 0.f;
				expansion.add(createChargeElement(d1, t1.prevDay(), partKwh, partGel, -1, false, false));
				d1 = t1.prevDay();
			}
			int delta = 0; /* elementar distinction */
			/*
			 * Charge first day should not be included.
			 * ======================================== This is an error, which
			 * is not fixed even on our main Billing Server. But this program
			 * deals with a complicated situations and need to take this issue
			 * into account.
			 */
			/* t1 .. d1 .. d2 .. t2 */
			if ((t1 == null || Date.isLessOrEqual(t1, d1)) && (t2 == null || Date.isGreaterOrEqual(t2, d2)))
				delta = Date.getIntervalInDays(d2, d1);
			/* t1 .. d1 .. t2 .. d2 */
			else if ((t1 == null || Date.isLessOrEqual(t1, d1)) && (t2 != null && Date.isGreater(t2, d1)))
				delta = Date.getIntervalInDays(t2, d1);
			/* d1 .. t1 .. t2 .. d2 */
			else if ((t1 != null && Date.isGreater(t1, d1)) && (t2 != null && Date.isLessOrEqual(t2, d2)))
				delta = Date.getIntervalInDays(t2, t1) + 1;
			/* d1 .. t1 .. d2 .. t2 */
			else if ((t1 != null && Date.isLessOrEqual(t1, d2)) && (t2 == null || Date.isGreaterOrEqual(t2, d2)))
				delta = Date.getIntervalInDays(d2, t1) + 1;
			else
				/* no intersection */continue;

			/* calculate kwh and gel */
			ITariff tariff = item.getTariff();
			Date actualEnd = Date.addDays(d1, delta); // one day!!!
			delta = delta == 0 ? 1 : delta;
			Date end = Date.addDays(d1, delta); // one day!!!
			double partKwh = kwh * delta / (1.f * distinction);
			double partGel = tariff.calculate(partKwh, d1, end);
			/* create new charge element and add to expansion */
			expansion.add(createChargeElement(d1, actualEnd, partKwh, partGel, tariff.getId(), tariff.isStep(), tariff.isZero()));
			/* prepare for the next cycle */
			d1 = end;
		}
		/* result expansion */
		return expansion;
	}

	public static Charge expandKwhCharge(Charge kwh, List tariffs) {
		return expandKwhCharge(kwh, tariffs, null, null);
	}

	/**
	 * Expands kWh charge into charge with both kWh and GEL.
	 * 
	 * @param kwh
	 *            kWh charge
	 * @param tariffs
	 *            tariff history
	 * @return a new charge
	 */
	public static Charge expandKwhCharge(Charge kwh, List tariffs, Date forceD1, Date forceD2) {

		/*
		 * First of all, we need to determine whether step-tariff exists in
		 * given period. When step-tariff exists we need to make a single
		 * (summary) charge.
		 */
		Date d1 = null;
		Date d2 = null;
		double summary = 0;
		for (int i = 0; i < kwh.expansion().size(); i++) {
			ChargeElement el = (ChargeElement) kwh.expansion().get(i);
			if (i == 0)
				d1 = el.getStartDate();
			if (i == kwh.expansion().size() - 1)
				d2 = el.getEndDate();
			summary += el.getKwh();
		}
		if (forceD1 != null)
			d1 = forceD1;
		if (forceD2 != null)
			d2 = forceD2;
		boolean stepExists = false;
		if (d1 != null && d2 != null) {
			stepExists = isStepTariffInChargePeriod(d1, d2, tariffs);
		}
		/*
		 * Create a new charge.
		 */
		Charge c = new Charge(); // new charge
		c.setCutUsed(kwh.isCutUsed()); // was cutted or not
		if (stepExists) {
			List els = expand(summary, d1, d2, tariffs);
			for (int j = 0; j < els.size(); j++) {
				ChargeElement el2 = (ChargeElement) els.get(j);
				c.addElement(el2);
			}
		} else {
			for (int i = 0; i < kwh.expansion().size(); i++) { // process all
				// expansions
				ChargeElement el = (ChargeElement) kwh.expansion().get(i);
				List els = expand(el.getKwh(), el.getStartDate(), el.getEndDate(), tariffs);
				for (int j = 0; j < els.size(); j++) {
					ChargeElement el2 = (ChargeElement) els.get(j);
					c.addElement(el2);
				}
			}
		}
		return c;
	}

	/**
	 * Helper method for use with "expand" method.
	 */
	public static ChargeElement createChargeElement(Date d1, Date d2, double kwh, double gel, int tariffId, boolean isStep, boolean isZero) {
		ChargeElement element = new ChargeElement();
		element.setKwh(kwh);
		element.setGel(gel);
		element.setStartDate(d1);
		element.setEndDate(d2);
		element.setTariffId(tariffId);
		element.setStepTariff(isStep);
		element.setZeroTariff(isZero);
		return element;
	}

	/**
	 * Get intervals, which were not cutted (actual charge interval). This is a
	 * helper method for use with expand method.
	 * 
	 * @param d1
	 *            interval start date
	 * @param d2
	 *            interval end date
	 * @param cuts
	 *            cut history
	 * @return intervals when not cutted
	 */
	public static Date[] extractActualChargeIntervals(Date d1, Date d2, List cuts, Charge charge) {
		/* no cut! */
		if (cuts == null || cuts.isEmpty())
			return new Date[] { d1, d2 };
		if (d1.equals(d2))
			return new Date[] { d1, d2 };

		/* determine intervals */
		List intervals = new ArrayList();
		Date markDate = d1;
		boolean makeFinalStep = true;

		for (int i = 0; i < cuts.size(); i++) {
			RecalcCutItem item = (RecalcCutItem) cuts.get(i);
			Date c1 = item.getStartDate();
			Date c2 = item.getEndDate();
			/* full cut: consider charge period only in final date [d2, d2] */
			if ((c1 == null || Date.isLessOrEqual(c1, d1)) && (c2 == null || Date.isGreaterOrEqual(c2, d2))) {
				if (charge != null)
					charge.setCutUsed(true);
				return new Date[] { d2, d2 };
			}
			/* charge period not yet reached */
			else if ((c1 == null || Date.isLess(c1, d1)) && (c2 != null && Date.isLess(c2, d1)))
				continue;
			/* charge period is over-reached */
			else if ((c1 != null && Date.isGreater(c1, d2)) && (c2 == null || Date.isGreater(c2, markDate))) {
				makeFinalStep = true;
				break; // nothing to do with futher cut intervals
			}
			/* cut inside the charge interval */
			else {
				if (charge != null)
					charge.setCutUsed(true);
				if ((c1 == null || Date.isLessOrEqual(c1, d1))) {
					markDate = c2;
				} else if ((c1 != null && Date.isGreater(c1, d1)) && (c2 != null && Date.isLess(c2, d2))) {
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
		} // end of for loop
		/* add final dates */
		if (makeFinalStep) {
			intervals.add(markDate);
			intervals.add(d2);
		}

		/* convert list to the array of dates */
		Date[] dateIntervals = (Date[]) intervals.toArray(new Date[] {});
		return dateIntervals;
	}

	/**
	 * Creates new recalculation which will be used by calculation procedure.
	 * Some basic transformations may be done at this stage.
	 * 
	 * @param proto
	 *            prototype
	 * @return prepared recalculation
	 */
	public static Recalc prepareRecalcForCalculation(Recalc proto) {
		/* create new recalculation and copy header */
		Recalc newRecalc = new Recalc();
		newRecalc.setId(proto.getId());
		newRecalc.setNumber(proto.getNumber());
		newRecalc.setAccount(proto.getAccount());
		newRecalc.setCustomer(proto.getCustomer());
		newRecalc.setCreateDate(proto.getCreateDate());
		newRecalc.getDetails().setRecalc(newRecalc);
		/* copy cut history */
		if (proto.getDetails().getCuts() != null)
			for (int i = 0; i < proto.getDetails().getCuts().size(); i++) {
				RecalcCutItem cut = (RecalcCutItem) proto.getDetails().getCuts().get(i);
				RecalcCutItem newCut = new RecalcCutItem();
				newCut.setId(cut.getId());
				newCut.setStartDate(cut.getStartDate());
				newCut.setEndDate(cut.getEndDate());

				newCut.setDetails(newRecalc.getDetails());
				newRecalc.getDetails().getCuts().add(newCut);
			}
		/* copy installed capacity history */
		if (proto.getDetails().getInstCpItems() != null)
			for (int i = 0; i < proto.getDetails().getInstCpItems().size(); i++) {
				RecalcInstCp instcp = (RecalcInstCp) proto.getDetails().getInstCpItems().get(i);
				RecalcInstCp newInstcp = new RecalcInstCp();
				newInstcp.setAmount(instcp.getAmount());
				newInstcp.setEndDate(instcp.getEndDate());
				newInstcp.setId(instcp.getId());
				newInstcp.setOption(instcp.getOption());
				newInstcp.setStartDate(instcp.getStartDate());

				newInstcp.setDetails(newRecalc.getDetails());
				newRecalc.getDetails().getInstCpItems().add(newInstcp);
			}
		/* copy tariff history */
		if (proto.getDetails().getTariffs() != null)
			for (int i = 0; i < proto.getDetails().getTariffs().size(); i++) {
				RecalcTariffItem tariff = (RecalcTariffItem) proto.getDetails().getTariffs().get(i);
				RecalcTariffItem newTariff = new RecalcTariffItem();
				newTariff.setEndDate(tariff.getEndDate());
				newTariff.setId(tariff.getId());
				newTariff.setStartDate(tariff.getStartDate());
				newTariff.setTariff(tariff.getTariff());

				newTariff.setDetails(newRecalc.getDetails());
				newRecalc.getDetails().getTariffs().add(newTariff);
			}
		/* copy recalculation intervals */
		if (proto.getDetails().getIntervals() != null) {
			for (int i = 0; i < proto.getDetails().getIntervals().size(); i++) {
				RecalcInterval interval = (RecalcInterval) proto.getDetails().getIntervals().get(i);
				RecalcInterval newInterval = prepareInterval(interval, proto.getDetails().getRegulars());
				if (newInterval != null) {
					validateNewRestructs(newInterval);
					newInterval.setDetails(newRecalc.getDetails());
					newRecalc.getDetails().getIntervals().add(newInterval);
				}
			}
		}
		return newRecalc;
	}

	/**
	 * Validates insertion point of NEW_RESTRUCTS and tries to move them on
	 * correct locations.
	 * 
	 * @since 26/03/2008
	 */
	private static void validateNewRestructs(RecalcInterval interval) {
		for (int i = 0; i < interval.getItems().size(); i++) {
			RecalcItem item = (RecalcItem) interval.getItems().get(i);
			if ("new_restruct".equals(item.getItemNumber())) {
				if (i > 0 && i < interval.getItems().size() - 1) {
					RecalcItem prevItem = (RecalcItem) interval.getItems().get(i - 1);
					RecalcItem nextItem = (RecalcItem) interval.getItems().get(i + 1);
					if (prevItem.getCycle() && nextItem.getCycle() && Date.isEqual(prevItem.getItemDate(), item.getItemDate()) && Date.isEqual(nextItem.getItemDate(), item.getItemDate())) {
						interval.getItems().remove(i);
						interval.getItems().add(i - 1, item);
					}
				}
			}
		}
	}

	/**
	 * This is used by "prepareRecalcForCalculation" as a helper method. It
	 * creates a standard list of items and also adds regular charges at
	 * appropriate places.
	 */
	private static RecalcInterval prepareInterval(RecalcInterval interval, List regulars) {
		/* create new interval and copy header information */
		RecalcInterval newInterval = new RecalcInterval();
		newInterval.setId(interval.getId());
		newInterval.setName(interval.getName());
		newInterval.setStartBalance(interval.getStartBalance());
		newInterval.setEditable(interval.isEditable());

		/* create list of new items */
		List newItems = new ArrayList();
		int accId = interval.getDetails().getRecalc().getAccount().getId();
		for (int i = 0; i < interval.getItems().size(); i++) {
			RecalcItem item = (RecalcItem) interval.getItems().get(i);
			/* copy original item */
			RecalcItem newItem = CoreUtils.copyRecalcItem(item);
			newItem.setInterval(newInterval);
			newItems.add(newItem);

			/*
			 * when account creation date is greater than this item ITEMDATE,
			 * than account creation date is assumed to be equal to this
			 * ITEMDATE
			 */
			if (isCharge(newItem.getOperation().getId()) && Date.isGreater(newItem.getAccount().getCreationDate(), newItem.getItemDate())) {
				Account acc = (Account) newItem.getAccount();
				acc.setCreationDate(newItem.getItemDate());// shift acc creation
				// date
			}

			/* not editable interval: nothing more to do */
			if (!interval.isEditable()) /**/
				continue;

			/* we may add regulars only for charges of the same account */
			boolean isCharge = isCharge(item.getOperation().getId());
			boolean isSameAccount = item.getAccount().getId() == accId;
			if (isSameAccount && isCharge && regulars != null) {
				Date date = newItem.getItemDate();
				for (int j = 0; j < regulars.size(); j++) {
					RecalcRegularItem regular = (RecalcRegularItem) regulars.get(j);
					Date t1 = regular.getStartDate();
					Date t2 = regular.getEndDate();
					boolean isCompRefugeSubsidy = isCompRefugeSubsidy(regular.getOperation().getId());
					if ((t1 == null || Date.isGreaterOrEqual(date, t1)) && (t2 == null || Date.isLessOrEqual(date, t2))) {
						boolean isCycle = item.getCycle();
						int unit = regular.getAttachment().getUnit();

						switch (unit) {
						case SubsidyAttachment.KWH:
							if (isCycle || prefferIntervalBasedCharge(newItem.getItemDate())) {
								RecalcItem newRegItem3 = generateItemFromRegular(newItem, regular);
								newRegItem3.setInterval(newInterval);
								newItems.add(newRegItem3);
							}
							break;
						case SubsidyAttachment.GEL:
							int year = newItem.getItemDate().getYear();
							int month = newItem.getItemDate().getMonth();
							if ((isCycle && !isCompRefugeSubsidy) ||
							/* comp. refuge on cycle item */
							(isCycle && isCompRefugeSubsidy && !isCompRefugeOnMonthEndInterval(year, month))) {
								RecalcItem newRegItem = generateItemFromRegular(newItem, regular);
								newRegItem.setInterval(newInterval);
								newItems.add(newRegItem);
							}
							break;
						case SubsidyAttachment.PERCENT:
						case SubsidyAttachment.KWH_BY_DAY:
							RecalcItem newRegItem = generateItemFromRegular(newItem, regular);
							newRegItem.setInterval(newInterval);
							newItems.add(newRegItem);
							break;
						default:
							break;
						}
					}
				}
			}
		}
		// add items
		newInterval.setItems(newItems);
		newInterval.validateDates();

		// add refuge subsidy on monthly basis
		for (int i = 0; newInterval.isEditable() && regulars != null && i < regulars.size(); i++) {
			RecalcRegularItem regular = (RecalcRegularItem) regulars.get(i);
			if (isCompRefugeSubsidy(regular.getOperation().getId())) {
				Date d1 = regular.getStartDate();
				Date d2 = regular.getEndDate();
				if (d1 == null || Date.isLess(d1, newInterval.getStartDate())) {
					d1 = newInterval.getStartDate();
				}
				if (d2 == null || Date.isGreater(d2, newInterval.getEndDate())) {
					d2 = newInterval.getEndDate();
				}
				for (int year = d1.getYear(), month = d1.getMonth(), year2 = d2.getYear(), month2 = d2.getMonth(); (year < year2 || (year == year2 && month <= month2));) {
					Date date = new Date(year, month, getLastDayOf(year, month));
					int index = -1;
					RecalcItem item = null;
					for (int j = 0; j < newInterval.getItems().size(); j++) {
						index = j;
						if (j == newInterval.getItems().size() - 1)
							index = j + 1;
						item = (RecalcItem) newInterval.getItems().get(j);
						Date itemDate = item.getItemDate();
						if (Date.isGreater(itemDate, date)) {
							break;
						}
					}
					if (index >= 0) {
						RecalcItem newRegItem = generateItemFromRegular(item, regular);
						newRegItem.setItemDate(date);
						newRegItem.setEnterDate(date);
						newRegItem.setCycle(false);
						newRegItem.setInterval(newInterval);
						newItems.add(index, newRegItem);
					}
					// shift to the next month
					if (month == 12) {
						month = 1;
						year++;
					} else {
						month++;
					}
				}
			}
		}

		return newInterval;
	}

	public static int getLastDayOf(int year, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 2:
			if (year % 4 == 0) {
				return 29;
			} else {
				return 28;
			}
		default:
			return 30;
		}
	}

	public static boolean isCompRefugeSubsidy(int operId) {
		return operId == Operation.REGULAR_SUBSIDY_COMP_REFUGE || operId == Operation.REGULAR_SUBSIDY_COMP_REFUGE_SEZON;
	}

	/**
	 * Generate regular item.
	 */
	public static RecalcItem generateItemFromRegular(RecalcItem item, RecalcRegularItem regular) {
		RecalcItem newItem = CoreUtils.copyRecalcItem(item);
		newItem.setId(newId());
		newItem.setItemId(-1);
		newItem.setOperation(regular.getOperation());
		newItem.setOriginalOperation(regular.getOperation());
		newItem.setItemNumber("regular_subsidy_or_add_charge");
		newItem.setReading(0.f);
		newItem.setOriginalReading(0.f);
		newItem.setKwh(0.f);
		newItem.setOriginalKwh(0.f);
		newItem.setGel(0.f);
		newItem.setOriginalGel(0.f);
		newItem.setBalance(0.f);
		newItem.setOriginalBalance(0.f);
		newItem.setBalanceGap(0.f);
		newItem.setOriginalBalanceGap(0.f);
		newItem.setStatus(RecalcItem.NEW);
		newItem.setCurrentOperationDate(null);
		newItem.setPreviousOperationDate(null);
		newItem.setSubsidyAttachment(regular.getAttachment());
		return newItem;
	}

	/**
	 * Calculates given interval.
	 * 
	 * @param interval
	 *            interval to be calculated
	 * @return calculated interval
	 */
	public static CalculationInterval calculateInterval(Session session, RecalcInterval interval, List /* FacturaDetail */sysExpansion) {
		/* create derived interval */
		CalculationInterval derived = new CalculationInterval();
		derived.setName(interval.getName());
		/* loop over interval */
		int size = interval.getItems().size();
		for (int i = 0; i < size; i++) {
			RecalcItem item = (RecalcItem) interval.getItems().get(i);
			if (isSystemItem(item))
				continue;
			List history = derived.getItems();
			if (!item.getCycle()) { /*
									 * not cycle items can be calculated
									 * independently
									 */
				List items = new ArrayList();
				items.add(item);
				List results = calculateItems(session, items, history, sysExpansion);
				if (results != null)
					derived.getItems().addAll(results);
			} else {
				List items = new ArrayList();
				items.add(item);
				Date cycleDate = item.getItemDate();
				for (int j = i + 1; j < size; j++) {
					RecalcItem cycleItem = (RecalcItem) interval.getItems().get(j);
					if (cycleItem.getCycle() && Date.isEqual(cycleDate, cycleItem.getItemDate()) && cycleItem.getAccount().getId() == item.getAccount().getId()) {
						items.add(cycleItem);
						i++;
					} else {
						break;
					}
				}
				List results = calculateItems(session, items, history, sysExpansion);
				if (results != null) {
					derived.getItems().addAll(results);
				}
			}
		}
		/* correct balances */
		double balance = interval.getStartBalance();
		for (int k = 0; k < derived.getItems().size(); k++) {
			CalculationItem item = (CalculationItem) derived.getItems().get(k);
			item.setReading(round(item.getReading()));
			double gel = round(item.getCharge().getGel());
			if (isLikePayment(item.getOperation()))
				balance -= Math.abs(gel);
			else
				balance += gel;
			if (Math.abs(balance) < CoreUtils.MIN_GEL)
				balance = 0.f;
		}
		/* return result */
		return derived;
	}

	/**
	 * Calculate items with given previous history. Clients should not use this
	 * method.
	 * 
	 * @param history
	 *            history
	 * @param items
	 *            items
	 * @return recalculated history
	 */
	protected static List calculateItems(Session session, List items, List history, List /* FacturaDetail */sysExpansion) {
		/* prepare results array */
		List results = new ArrayList();
		/* nothing to calculate */
		if (items.isEmpty())
			return results;
		/* do classification */
		List newAndOriginal = new ArrayList(); // new and original charges
		List others = new ArrayList(); // other charges
		List subaccs = new ArrayList(); // subaccount charges
		for (int i = 0; i < items.size(); i++) {
			RecalcItem item = (RecalcItem) items.get(i);
			int status = item.getStatus();
			/* not deleted debt restructurizations should be processed as OTHERS */
			if (item.getOperation().getType().getId() == OperationType.DEBT_RESTRUCTURIZATION && item.getStatus() != RecalcItem.DELETED)
				status = RecalcItem.OTHER;
			switch (status) {
			case RecalcItem.NEW:
			case RecalcItem.ORIGINAL:
				/* populate subaccount charges */
				if (item.getOperation().getId() == Operation.SUB_ACCOUNT_CHARGE)
					subaccs.add(item);
				/* general charges */
				else
					newAndOriginal.add(item);
				break;
			case RecalcItem.OTHER:
				/* charge which will be processed straitforwardly */
				others.add(item);
				break;
			case RecalcItem.DELETED:
				/* if deleted, then nothing to do wih it! */
				break;
			default:
				throw new IllegalArgumentException("Unsupported item status: " + status);
			}
		}
		/* process NEW and ORIGINAL */
		if (!newAndOriginal.isEmpty())
			if (((RecalcItem) newAndOriginal.get(0)).getCycle())
				results.addAll(processCycle(session, newAndOriginal, history, sysExpansion));
			else
				results.addAll(processNotCycle(session, (RecalcItem) newAndOriginal.get(0), history, sysExpansion));
		/* process SUB ACCOUNT */
		for (int i = 0; i < subaccs.size(); i++) {
			RecalcItem item = (RecalcItem) subaccs.get(i);
			results.addAll(processNotCycle(session, item, history, sysExpansion));
		}
		/* process OTHERS */
		for (int i = 0; i < others.size(); i++) {
			RecalcItem item = (RecalcItem) others.get(i);
			results.add(processOther(item));
		}
		/* return results */
		return results;
	}

	/**
	 * Process cycle items.
	 * 
	 * @param items
	 *            cycle items
	 * @param history
	 *            previous recalculated history
	 * @return recalculated items
	 */
	protected static List processCycle(Session session, List items, List history, List/* FacturaExpansion */sysExpansion) {
		/* results array */
		List results = new ArrayList();
		/* new reading */
		RecalcItem newReading = (RecalcItem) items.get(0);

		/* previous and actual readings */
		CalculationItem prevReading = getLastReading(newReading.getAccount(), history, false, false, false);
		CalculationItem prevActReading = getLastReading(newReading.getAccount(), history, true, false, false);

		/*
		 * This is a previous cycle item. We need it to determine cycle
		 * boundaries. If it is <code>null</code>, then call to
		 * <code>getPreviousReadingDate</code> (see below) method will be trying
		 * to find previous cycle date in the initial history. This provides
		 * consistent framework for working with dates.
		 */
		CalculationItem prevCycleReading = getLastReading(newReading.getAccount(), history, false, true, false);

		/* determine cycle boundaries */
		Date d1 = getPreviousReadingDate(newReading, prevReading, false);
		Date d2 = newReading.getItemDate(); // we are neglecting currentDate
		// parameter!
		Date cycleD1 = getPreviousReadingDate(newReading, prevCycleReading, true);
		Date cycleD2 = newReading.getItemDate(); // we are neglecting

		// currentDate parameter!
		boolean stepExists = isStepTariffInChargePeriod(cycleD1, cycleD2, newReading.getInterval().getDetails().getTariffs());
		if (!stepExists) {
			/*
			 * When no step in the charge period then cycle is defined from any
			 * previous reading. For step the full cycle is considered to be a
			 * cycle period.
			 */
			cycleD1 = d1;
		}
		boolean prevOperationDateWasForced = newReading.getPreviousOperationDate() != null;
		/* adjust hint if neccessary */
		if (stepExists && newReading.getCalculationHint() == RecalcItem.HINT_PRESERVE_BOTH) {
			/*
			 * When step-tariff then HINT_PRESERVE_BOTH should be considered as
			 * HINT_DERIVE_FROM_EXISTING_KWH.
			 */
			newReading.setCalculationHint(RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH);
		}
		/* current charge item */
		CalculationItem currentChargeItem = null;
		/* supported cycle operations */
		switch (newReading.getOperation().getId()) {
		case Operation.READING:
		case Operation.CONTROL_READING:
		case Operation.ESTIMATE:
		case Operation.WITHOUT_METER:
		case Operation.NOT_OPERABLE_METER:
			/*
			 * Preserve both kWh and GEL.
			 */
			if (newReading.getCalculationHint() == RecalcItem.HINT_PRESERVE_BOTH) {
				ChargeElement elmnt = createChargeElement(d1, d2, newReading.getKwh(), newReading.getGel(), -1, false, false);
				CalculationItem res = createDerivedItem(newReading, false);
				res.getCharge().addElement(elmnt);
				results.add(res);
				currentChargeItem = res;
			}
			/*
			 * When no previous reading and kWh derivation hint.
			 */
			else if (prevReading == null && newReading.getCalculationHint() == RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH) {
				/* create main charge */
				ChargeElement det = createChargeElement(d1, d2, newReading.getKwh(), 0, -1, false, false);
				CalculationItem res = createDerivedItem(newReading, false);
				Charge kwh = new Charge();
				kwh.addElement(det);
				/*
				 * When new reading is charge (not reading), then we should also
				 * take into account cut periods. This is why below we are
				 * calculating kwh charge using avearage day-charge. COOOOOOOL!
				 */
				if (!isReading(newReading.getOperation().getId())) {
					List instcps = new ArrayList();
					RecalcInstCp instcp = new RecalcInstCp();
					instcp.setAmount(newReading.getKwh() / (Date.getIntervalInDays(d2, d1)));
					instcp.setOption(RecalcInstCp.AVERAGE_DAY_CHARGE);
					instcp.setStartDate(d1.prevDay());
					instcp.setEndDate(d2.nextDay());
					instcps.add(instcp);
					calculateInstCapacityUsingStandardProcedure(kwh, d1, d2, instcps, newReading.getInterval().getDetails().getCuts(), prevOperationDateWasForced);
				}
				if (!stepExists) {
					/* apply GEL charge */
					Charge full = expandKwhCharge(kwh, newReading.getInterval().getDetails().getTariffs());
					full.copyCharge(res.getCharge());
				} else
					kwh.copyCharge(res.getCharge());
				/* add to results */
				results.add(res);
				currentChargeItem = res;
			}
			/*
			 * Common case.
			 */
			else {
				ReadingCalculation calc = calculateKwh(true, newReading, prevReading, prevActReading);

				if (calc.forceEstimateCorrection) {
					/*
					 * Correct Estimates.
					 */
					List corrections = calculateEstimateCorrection(session, history, newReading, sysExpansion);
					if (!corrections.isEmpty()) {
						/* this is a header */
						CalculationItem header = createDerivedItem(newReading, false);
						header.getCharge().clear();
						header.setReading(calc.newReadingValue);
						results.add(header);
						/* change current charge */
						calc.newOperationId = Operation.CURRENT_CHARGE;
						calc.newReadingValue = 0.f;
						for (int i = 0; i < corrections.size(); i++) {
							CalculationItem corrItem = (CalculationItem) corrections.get(i);
							results.add(corrItem);
						}
					}
				}

				if (!stepExists)
					calc.charge = expandKwhCharge(calc.charge, newReading.getInterval().getDetails().getTariffs());

				CalculationItem res = createDerivedItem(newReading, false);
				res.setReading(calc.newReadingValue);
				res.setBalance(0.f);
				res.setOperation(DbUtilities.findOperationById(calc.newOperationId));
				if (calc.kwhPreserved) {
					res.setItemNumber("not_for_discharge");
				} else if (calc.forceEstimateCorrection && calc.newOperationId == Operation.CURRENT_CHARGE) {
					/*
					 * Current charge should be discharged, even if it is the
					 * same as an original one!!!
					 */
					res.setItemNumber("force_discharge");
				}
				calc.charge.copyCharge(res.getCharge());
				results.add(res);
				currentChargeItem = res;
			}
			break;
		default:
			// /*operation not supported as a "reading" for cycle*/
			// throw new RecalcException(CalcMessage.getMessage(
			// "calc.error.not_supported_cycle_operation",
			// new Object[] { new Integer(newReading.getOperation().getId())
			// }));

			/*
			 * Process as others.
			 */
			for (int i = 0; i < items.size(); i++) {
				RecalcItem item = (RecalcItem) items.get(i);
				results.add(processOther(item));
			}
			return results;
		}
		/*
		 * Process other cycle items. Currently we support subsidies and
		 * additional charges only.
		 */
		List fullHistory = new ArrayList();
		CalculationItem currentPercentSubsidy = null;
		/* Subsidies and additional charges */
		for (int i = 1; i < items.size(); i++) {
			RecalcItem newCharge = (RecalcItem) items.get(i);
			switch (newCharge.getOperation().getType().getId()) {
			case OperationType.ADD_CHARGE:
			case OperationType.SUBSIDY:
				fullHistory.clear();
				// if (history != null)
				fullHistory.addAll(history);
				fullHistory.addAll(results);
				SubsCalculation sac = calculateSubsidyOrAddCharge(newCharge, fullHistory);
				if (sac.valuable) {
					CalculationItem calc = createDerivedItem(newCharge, false);
					calc.setBalance(0.f);
					sac.charge.copyCharge(calc.getCharge());
					results.add(calc);
					/* percent subsidy */
					if (calc.getOperation().getType().getId() == OperationType.SUBSIDY && calc.getProducer().getSubsidyAttachment() != null && calc.getProducer().getSubsidyAttachment().getUnit() == SubsidyAttachment.PERCENT)
						currentPercentSubsidy = calc;
				}
			default:
				; // do nothing
			}
		}
		/*
		 * Close the cycle when step exists
		 */
		if (stepExists) {
			/*
			 * Looking up for the item in previous cycle.
			 */
			int prevCycleLastItemIndex = -1;
			if (prevCycleReading != null) {
				int startingIndex = history.indexOf(prevCycleReading);
				Date prevCycleDate = prevCycleReading.getItemDate();
				prevCycleLastItemIndex = startingIndex;
				for (int i = startingIndex; i < history.size(); i++) {
					CalculationItem item = (CalculationItem) history.get(i);
					if (item.getCycle() && Date.isEqual(item.getItemDate(), prevCycleDate))
						prevCycleLastItemIndex = i;
					else
						break;
				}
			}
			/*
			 * Loop over current cycle.
			 */
			/* parameters which we need to find */
			Charge percentSubsidy = new Charge();
			Charge commonCharge = new Charge();
			int subsidyCount = 0;
			/* create full history */
			fullHistory.clear();
			fullHistory.addAll(history);
			fullHistory.addAll(results);

			/*
			 * When summarizing cycle we need to know whether there was an
			 * acceleration in this cycle. If we has acceleration even for a
			 * single item in the cycle, then the whole cycle should be
			 * discharged.
			 */
			boolean hasMeterAcceleration = false;
			for (int i = prevCycleLastItemIndex + 1; i < fullHistory.size(); i++) {
				CalculationItem item = (CalculationItem) fullHistory.get(i);
				/* another account */
				if (item.getAccount().getId() != newReading.getAccount().getId())
					continue;
				/* determine categories */
				// boolean isSubsidy = item.getOperation().getType().getId() ==
				// IOperationType.SUBSIDY;
				boolean isPercentSubsidy = item.getOperation().getDiffCategory() == DiffDetail.SUBSIDY_PERCENT;
				// item.getProducer() != null
				// && item.getProducer().getSubsidyAttachment() != null
				// && item.getProducer().getSubsidyAttachment().getUnit() ==
				// ISubsidyAttachment.PERCENT;
				if (/* isSubsidy && hasPercentUnit */isPercentSubsidy) /*
																		 * %
																		 * subsidy
																		 */{
					for (int k = 0; k < item.getCharge().expansion().size(); k++) {
						ChargeElement subs = (ChargeElement) item.getCharge().expansion().get(k);
						percentSubsidy.addElement(subs);
					}
					subsidyCount++;
				} else /* common category */{
					if (isValuableCycleItem(item)) {
						/* has acceleration? */
						double accelaration = item.getProducer() == null ? 0 : item.getProducer().getMeterAcceleration();
						if (Math.abs(accelaration) > 0.001)
							hasMeterAcceleration = true;
						// populate common charge
						for (int k = 0; k < item.getCharge().expansion().size(); k++) {
							ChargeElement cmn = (ChargeElement) item.getCharge().expansion().get(k);
							commonCharge.addElement(cmn);
						}
					}
				}
			}
			/*
			 * Determine how to be next?
			 */
			boolean gelExists = Math.abs(commonCharge.getGel()) >= CoreUtils.MIN_GEL;
			boolean largeKwh = Math.abs(commonCharge.getKwh() - currentChargeItem.getCharge().getKwh()) >= CoreUtils.MIN_KWH;
			boolean makeSummary = gelExists || largeKwh;
			List tariffs = newReading.getInterval().getDetails().getTariffs();
			if (makeSummary) {
				/* discharge gel */
				if (gelExists) {
					CalculationItem geldis = createDerivedItem(newReading, false);
					geldis.getCharge().clear();
					geldis.setReading(0.f);
					geldis.setBalance(0.f);
					geldis.setOperation(DbUtilities.findOperationById(Operation.DISCHARGE_GEL));
					ChargeElement elmnt = createChargeElement(cycleD1, cycleD2, 0.f, -commonCharge.getGel(), -1, false, false);
					geldis.getCharge().addElement(elmnt);
					results.add(geldis);
				}
				/* make summary */
				CalculationItem summary = createDerivedItem(newReading, false);
				summary.setReading(0.f);
				summary.setBalance(0.f);
				summary.setOperation(DbUtilities.findOperationById(Operation.SUMMARY_CHARGE));
				/* force discharge, when has meter acceleration */
				if (hasMeterAcceleration)
					summary.setItemNumber("force_discharge");
				Charge kwh = new Charge();
				ChargeElement elmnt = createChargeElement(cycleD1, cycleD2, commonCharge.getKwh(), 0.f, -1, false, false);
				kwh.addElement(elmnt);
				Charge full = expandKwhCharge(kwh, tariffs);
				full.copyCharge(summary.getCharge());
				results.add(summary);
				full.copyCharge(commonCharge);
			}
			/*
			 * Current charge only.
			 */
			else {
				/* apply GEL charge */
				// @since 06-Sep-2013 force cycle dates while expanding
				Charge c = expandKwhCharge(currentChargeItem.getCharge(), tariffs, cycleD1, cycleD2);
				c.copyCharge(currentChargeItem.getCharge());
				c.copyCharge(commonCharge);
			}
			/*
			 * Deal with percent subsidy summaries.
			 */
			if (subsidyCount > 0) {
				/* make percent summary? */
				boolean makePercentSummary = currentPercentSubsidy == null || subsidyCount > 1;
				if (Math.abs(percentSubsidy.getGel()) > CoreUtils.MIN_GEL) {
					makePercentSummary = true;
					CalculationItem geldis = createDerivedItem(newReading, false);
					geldis.setReading(0.f);
					geldis.setBalance(0.f);
					geldis.setOperation(DbUtilities.findOperationById(Operation.DISCHARGE_PERCENT_SUBSIDY_GEL));
					Charge c = new Charge();
					ChargeElement elmnt = createChargeElement(cycleD1, cycleD2, 0.f, -percentSubsidy.getGel(), -1, false, false);
					c.addElement(elmnt);
					c.copyCharge(geldis.getCharge());
					results.add(geldis);
				}
				double coeff = percentSubsidy.getKwh() / commonCharge.getKwh();
				if (makePercentSummary) {
					CalculationItem summary = createDerivedItem(newReading, false);
					summary.setReading(0.f);
					summary.setBalance(0.f);
					summary.setOperation(DbUtilities.findOperationById(Operation.SUMMARY_PERCENT_SUBSIDY));
					Charge c = new Charge();
					commonCharge.copyCharge(c);
					c.applyFactor(coeff);
					c.copyCharge(summary.getCharge());
					results.add(summary);
				} else {
					Charge c = new Charge();
					commonCharge.copyCharge(c);
					c.applyFactor(coeff);
					c.copyCharge(currentPercentSubsidy.getCharge());
				}
			}
		}
		// return results
		return results;
	}

	/**
	 * Is the given item valuable when make cycle summary?
	 */
	private static boolean isValuableCycleItem(CalculationItem item) {
		int operId = item.getOperation().getId();
		int typeId = item.getOperation().getType().getId();
		switch (typeId) {
		case OperationType.READING:
		case OperationType.ADD_CHARGE:
		case OperationType.CHARGE:
			if (item.getCharge().getKwh() < CoreUtils.MIN_KWH)
				return false;
			switch (operId) {
			case Operation.SUMMARY_CHARGE:
			case Operation.DISCHARGE_GEL:
			case Operation.SUMMARY_PERCENT_SUBSIDY:
				// case Ids.MANY_TIME_ACT: OLD!!!
			case Operation.ONE_TIME_ACT:
			case Operation.ONE_TIME_ACT_AFTER_VAT_CHANGE_2005:
				return false;
			default:
				return true;
			}
		default:
			return false;
		}
	}

	/**
	 * Process not cycle item.
	 * 
	 * @param item
	 *            item to be recalculated
	 * @param history
	 *            previous recalculated history
	 * @return recalculated items
	 */
	protected static List processNotCycle(Session session, RecalcItem item, List history, List/* FacturaExpansion */sysExpansion) {
		/* output */
		List output = new ArrayList();
		/* operation type id */
		int operTypeId = item.getOperation().getType().getId();
		switch (operTypeId) {
		case OperationType.READING:
			/* not-cycle reading */
			output.addAll(processNotCycleReading(session, item, history, sysExpansion));
			break;
		case OperationType.VOUCHER:
			/* only non-system vouchers, like OTHERS */
			if (!isSystemItem(item))
				output.add(processOther(item));
			break;
		case OperationType.SUBSIDY:
		case OperationType.ADD_CHARGE:
			/* subsidy and additional charge */
			output.addAll(processSubsidyOrAddCharge(item, history));
			break;
		case OperationType.SUBACC_CHARGE:
			// XXX: how to be with subaccount charge?
			output.add(processOther(item));
			break;
		case OperationType.PAYMENT:
		case OperationType.SUBACC_TRANSIT:
		case OperationType.SERVICE:
		case OperationType.POWER_OPERATION:
		case OperationType.DEBT_RESTRUCTURIZATION:
		case OperationType.TRANSIT:
		case OperationType.COMPENSATION:
		case OperationType.USAID6_CORRECTION:
		case OperationType.AUDIT_READING:
		case OperationType.NETWORK_OPERATIONS:
			/* process as OTHERS */
			output.add(processOther(item));
			break;
		}
		/* return output */
		return output;
	}

	/**
	 * Process not cycle reading.
	 */
	protected static List processNotCycleReading(Session session, RecalcItem newReading, List history, List/* FacturaExpansion */sysExpansion) {
		/* current operation */
		int operId = newReading.getOperation().getId();
		/* prepare results */
		List results = new ArrayList();
		/* previous operations */
		CalculationItem prevReading = getLastReading(newReading.getAccount(), history, false, false, false);
		CalculationItem prevActReading = getLastReading(newReading.getAccount(), history, true, false, false);
		/* dates */
		Date d1 = getPreviousReadingDate(newReading, prevReading, false);
		boolean prevOperationDateWasForced = newReading.getPreviousOperationDate() != null;
		Date d2 = newReading.getItemDate();
		int hint = newReading.getCalculationHint();
		/*
		 * Current charges by act and voucher.
		 */
		if (operId == Operation.CURRENT_CHARGE_ACT || operId == Operation.CURRENT_CHARGE_VOUCHER || hint == RecalcItem.HINT_PRESERVE_BOTH) {
			ChargeElement elmnt = createChargeElement(d1, d2, newReading.getKwh(), newReading.getGel(), -1, false, false);
			CalculationItem res = createDerivedItem(newReading, false);
			res.getCharge().addElement(elmnt);
			results.add(res);
		}
		/*
		 * First charge or DERIVE_FROM_EXISTING_KWH hint.
		 */
		else if (prevReading == null || hint == RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH) {
			/* create main charge */
			ChargeElement det = createChargeElement(d1, d2, newReading.getKwh(), 0, -1, false, false);
			CalculationItem res = createDerivedItem(newReading, false);
			res.setItemNumber("not_for_discharge");
			Charge kwh = new Charge();
			kwh.addElement(det);
			/*
			 * When new reading is charge (not reading), then we should also
			 * take into account cut periods. This is why below we are
			 * calculating kwh charge using avearage day-charge. COOOOOL!
			 */
			if (!isReading(newReading.getOperation().getId())) {
				List instcps = new ArrayList();
				RecalcInstCp instcp = new RecalcInstCp();
				instcp.setAmount(newReading.getKwh() / (Date.getIntervalInDays(d2, d1)));
				instcp.setOption(RecalcInstCp.AVERAGE_DAY_CHARGE);
				instcp.setStartDate(d1.prevDay());
				instcp.setEndDate(d2.nextDay());
				instcps.add(instcp);
				calculateInstCapacityUsingStandardProcedure(kwh, d1, d2, instcps, newReading.getInterval().getDetails().getCuts(), prevOperationDateWasForced);
			}
			/*
			 * Apply GEL charge, only when step-tariff does not exists.
			 */
			List tariffs = newReading.getInterval().getDetails().getTariffs();
			if (!isStepTariffInChargePeriod(kwh, tariffs)) {
				Charge full = expandKwhCharge(kwh, tariffs);
				full.copyCharge(res.getCharge());
			} else {/*
					 * Otherwise, use simply kWh charge
					 */
				kwh.copyCharge(res.getCharge());
			}
			/* add to results */
			results.add(res);
		}
		/*
		 * Other cases.
		 */
		else {
			/*
			 * Estimates can not be corrected when no previous actual reading
			 * exists
			 */
			if (isReading(operId) && prevReading != null && prevReading.getOperation().getId() == Operation.ESTIMATE && prevActReading == null) {
				throw new RecalcException(CalcMessage.getMessage("calc.error.no_real_reading_before_estimate"));
			}
			/*
			 * Special condition for the control reading.
			 */
			if ((
			/*
			 * Condition below was removed DUE TO resistence from M.Azniashvili
			 * and R.Gudushauri. But in billing system we have exactly this
			 * condition. To be honest, it seems that in the case of
			 * recalculation utility it was reasonable action. We had discussion
			 * about this.
			 */
			/* operId == IOperation.METER_INSTALL || */
			operId == Operation.CONTROL_READING) && Math.abs(newReading.getKwh()) >= CoreUtils.MIN_KWH && Math.abs(newReading.getGel()) >= CoreUtils.MIN_GEL) {
				ChargeElement elmnt = createChargeElement(d1, d2, newReading.getKwh(), newReading.getGel(), -1, false, false);
				CalculationItem res = createDerivedItem(newReading, false);
				res.getCharge().addElement(elmnt);
				results.add(res);
			}
			/*
			 * Common case.
			 */
			else {
				/* calculate reading */
				ReadingCalculation calc = calculateKwh(false, newReading, prevReading, prevActReading);
				/* correct estimates */
				if (calc.forceEstimateCorrection) {
					/*
					 * Correct Estimates.
					 */
					List corrections = calculateEstimateCorrection(session, history, newReading, sysExpansion);
					if (!corrections.isEmpty()) {
						/* this is a header */
						CalculationItem header = createDerivedItem(newReading, false);
						header.getCharge().clear();
						header.setReading(calc.newReadingValue);
						results.add(header);
						/* change current charge */
						calc.newOperationId = Operation.CURRENT_CHARGE;
						calc.newReadingValue = 0.f;
						for (int i = 0; i < corrections.size(); i++) {
							CalculationItem corrItem = (CalculationItem) corrections.get(i);
							results.add(corrItem);
						}
					}
				}
				List tariffs = newReading.getInterval().getDetails().getTariffs();
				boolean containsStepPeriod = isStepTariffInChargePeriod(calc.charge, tariffs);
				if (!containsStepPeriod)
					calc.charge = expandKwhCharge(calc.charge, tariffs);
				CalculationItem res = createDerivedItem(newReading, false);
				res.setReading(calc.newReadingValue);
				res.setBalance(0.f);
				res.setOperation(DbUtilities.findOperationById(calc.newOperationId));
				if (calc.forceEstimateCorrection && calc.newOperationId == Operation.CURRENT_CHARGE) {
					/*
					 * Current charge should be discharged, even if it is the
					 * same as an original one!!!
					 */
					res.setItemNumber("force_discharge");
				}
				calc.charge.copyCharge(res.getCharge());
				results.add(res);
			}
		}
		/* return results */
		return results;
	}

	/**
	 * Calculate other charges.
	 * 
	 * @param item
	 *            other item
	 * @returnrecalculated item
	 */
	protected static CalculationItem processOther(RecalcItem item) {
		/* create charge elements */
		ChargeElement elmnt = createChargeElement(item.getItemDate(), item.getItemDate(),// start/end
				// dates
				item.getKwh(), item.getGel(), // kWh/GEL
				-1, false, false // tariffId, isStep, isZero
		);
		/* create calculation item */
		CalculationItem drvd = createDerivedItem(item, false);
		drvd.getCharge().addElement(elmnt);
		return drvd;
	}

	/**
	 * Processes subsidies and additional charges.
	 * 
	 * @param charge
	 *            subsidy or add charge item
	 * @param history
	 *            history
	 * @return
	 */
	protected static List processSubsidyOrAddCharge(RecalcItem charge, List history) {
		List res = new ArrayList();
		SubsCalculation subs = calculateSubsidyOrAddCharge(charge, history);
		if (subs.valuable) {
			CalculationItem item = createDerivedItem(charge, false);
			item.setBalance(0.f);
			subs.charge.copyCharge(item.getCharge());
			res.add(item);
		}
		return res;
	}

	/**
	 * Converts recalculation unit to derived item with empty charge.
	 * 
	 * @param item
	 *            original item
	 * @param forceOriginal
	 *            when <code>true</code>, then calculation item will be
	 *            genereated based on original parameter values
	 * @return result item
	 */
	protected static CalculationItem createDerivedItem(RecalcItem item, boolean forceOriginal) {
		/*
		 * We need to use original operation sometimes below.
		 */
		Operation origOperation = item.getOriginalOperation();
		if (forceOriginal)
			origOperation = DbUtilities.findOperationById(origOperation.getId());

		CalculationItem derived = new CalculationItem();
		derived.setAccount(item.getAccount());
		derived.setCustomer(item.getCustomer());
		derived.setItemDate(forceOriginal ? item.getOriginalItemDate() : item.getItemDate());
		if (forceOriginal) {
			/*
			 * When cycle is required, then we force it not depending on the
			 * original history. The same for when the cycle is not required at
			 * all. This is very important!
			 */
			boolean origCycle = item.getOriginalCycle();
			if (origOperation.getCycleRequiment() == Operation.REQUIERED)
				origCycle = true;
			else if (origOperation.getCycleRequiment() == Operation.NOT_REQUIERED_AT_ALL)
				origCycle = false;
			derived.setCycle(origCycle);
		} else {
			derived.setCycle(item.getCycle());
		}
		derived.setOperation(forceOriginal ? origOperation : item.getOperation());
		derived.setReading(forceOriginal ? item.getOriginalReading() : item.getReading());
		derived.setBalance(forceOriginal ? item.getOriginalBalance() : 0.f);
		derived.setProducer(item);
		derived.setItemNumber(item.getItemNumber());
		return derived;
	}

	/**
	 * Whether is a system item? System item is e.g. Current Charge operation.
	 * Or voucher which itemnumber starts with "sys" (see Billing System
	 * documentation for details).
	 * 
	 * @param item
	 * @return
	 */
	protected static boolean isSystemItem(RecalcItem item) {
		if (item.getOperation().getId() == Operation.CURRENT_CHARGE)
			return true;
		String itemNumber = item.getItemNumber();
		if (itemNumber == null || itemNumber.trim().length() == 0)
			return false;
		return itemNumber.trim().startsWith("sys") &&
		// exclude "sys_bank_corr" operation in 15/12/2010
				!itemNumber.trim().equals("sys_bank_corr");
	}

	/**
	 * Process reading.
	 * 
	 * @param isCycle
	 * @param newReading
	 * @param prevReading
	 * @param prevActReading
	 * @return
	 */
	protected static ReadingCalculation calculateKwh(boolean isCycle, RecalcItem newReading, CalculationItem prevReading, CalculationItem prevActReading) {
		/* account creation date */
		Date accCreationDate = newReading.getAccount().getCreationDate();
		/* new reading date */
		/*
		 * 10/04/2007 We are suppressing here using of current reading date It
		 * seems that it is an unneccessary parameter.
		 */
		Date newReadingDate = newReading.getItemDate();
		// Below is how we defined it previously.
		//
		// OLD_VERSION:
		// ============
		// newReading.getCurrentOperationDate() == null
		// ? newReading.getItemDate() : newReading.getCurrentOperationDate();
		/* previous reading date */
		Date prevReadingDate = getPreviousReadingDate(newReading, prevReading, false);
		boolean prevReadingDateWasForced = newReading.getPreviousOperationDate() != null;
		/* check dates */
		if (Date.isLess(newReadingDate, accCreationDate))
			throw new RecalcException(String.format("New reading date %1$s is less than account creation date %2$s", new Object[] { newReadingDate, accCreationDate }));
		if (Date.isLess(newReadingDate, prevReadingDate))
			throw new RecalcException(String.format("New reading date %1$s is less than previous reading date %2$s", new Object[] { newReadingDate, prevReadingDate }));
		/*
		 * Initialization of calculation structure
		 */
		ReadingCalculation calc = new ReadingCalculation();
		calc.prevReadingDateWasForced = prevReadingDateWasForced;
		calc.estimateReading = 0.f;
		calc.forceEstimateCorrection = false;
		calc.newReading = newReading;
		calc.prevReading = prevReading;
		calc.prevActReading = prevActReading;
		calc.newOperationId = newReading.getOperation().getId();
		calc.newReadingValue = newReading.getReading();
		/* create and add charge */
		ChargeElement chargeElement = createChargeElement(prevReadingDate, newReadingDate, // dates
				newReading.getKwh(), 0.f, // charge values
				-1, false, false // tariff parameters
		);
		Charge charge = new Charge();
		charge.addElement(chargeElement);
		calc.charge = charge;
		/*
		 * Make calculations
		 */
		/* cycle */
		if (isCycle)
			calculateCycleKwh(calc);
		/* not cycle */
		else
			calculateNotCycleKwh(calc);
		/* return calculation results */
		return calc;
	}

	/**
	 * A simple structure which is used in reading calculations.
	 */
	static class ReadingCalculation {
		// new reading
		RecalcItem newReading;
		// previous reading
		CalculationItem prevReading;
		// previous actual reading
		CalculationItem prevActReading;
		// was previous reading date forced?
		boolean prevReadingDateWasForced;
		// charge
		Charge charge;
		// new valur for operation
		int newOperationId;
		double newReadingValue;
		// reading derived on the place of previous
		// cycle estimate reading
		double estimateReading;
		// need the previous period estimate
		// history be recalculated?
		boolean forceEstimateCorrection;
		boolean kwhPreserved;
	}

	/**
	 * Cycle kWh calculation.
	 */
	protected static void calculateCycleKwh(ReadingCalculation calc) {
		int operId = calc.newOperationId;
		switch (operId) {
		case Operation.READING:
		case Operation.CONTROL_READING:
			/* reading presents */
			calculateCycleKwhWithReading(calc);
			break;
		default:
			/* no reading */
			calculateCycleKwhWithoutReading(calc);
			break;
		}
	}

	/**
	 * Cycle kWh calculation with reading.
	 */
	protected static void calculateCycleKwhWithReading(ReadingCalculation calc) {
		/* Interval dates */
		Date d1 = ((ChargeElement) calc.charge.expansion().get(0)).getStartDate();
		Date d2 = ((ChargeElement) calc.charge.expansion().get(0)).getEndDate();
		int prevOperationId = calc.prevReading == null ? Operation.WITHOUT_METER : calc.prevReading.getOperation().getId();
		/* Installed capacity and cut history */
		List instCpHistory = calc.newReading.getInterval().getDetails().getInstCpItems();
		List cutHistory = calc.newReading.getInterval().getDetails().getCuts();
		/* calculation hint */
		int calcHint = calc.newReading.getCalculationHint();

		/*
		 * No meter. Set operation to WITHOUT_METER and charge using installed
		 * capacity.
		 */
		if (calc.newReading.getMeter().getId() == Meter.METER_NONE) {
			calc.newOperationId = Operation.WITHOUT_METER;
			calc.newReadingValue = 0.f;
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
		}

		/*
		 * Meter is damaged. Set operation to NOT_OPERABLE_METER and charge
		 * using installed capacity.
		 */
		else if (!calc.newReading.getMeterStatus()) {
			calc.newOperationId = Operation.NOT_OPERABLE_METER;
			calc.newReadingValue = 0.f;
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
		}

		/*
		 * No previous operation. Process as is.
		 */
		else if (calc.prevReading == null) {
			calc.kwhPreserved = true;
		}

		/*
		 * When previous operation was METER_DEINSTALL. Set operation to
		 * WITHOUT_METER and charge using installed capacity.
		 */
		else if (prevOperationId == Operation.METER_DEINSTALL) {
			calc.newOperationId = Operation.WITHOUT_METER;
			calc.newReadingValue = 0.f;
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
		}

		/*
		 * When previous was WITHOUT_METER. Set operation to WITHOUT_METER and
		 * charge using installed capacity.
		 */
		else if (prevOperationId == Operation.WITHOUT_METER || prevOperationId == Operation.NOT_OPERABLE_METER) {
			calc.newOperationId = Operation.WITHOUT_METER;
			calc.newReadingValue = 0.f;
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
		}

		/*
		 * When new reading is not compatible with meter digits.
		 */
		else if (calc.newReading.getReading() > Math.pow(10, calc.newReading.getMeter().getDigits())) {
			/* calculate using intalled capacity */
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
			/*
			 * Determine whether cut was used. This information is stored in
			 * cut-flag value.
			 */
			boolean wasCutted = calc.charge.isCutUsed();
			/*
			 * When was cutted, then repeat previous charge parameters.
			 * Otherwise process as estimate charge.
			 */
			if (Math.abs(calc.charge.getKwh()) < CoreUtils.MIN_KWH && wasCutted) {
				/* repeat previous charge parameters */
				calc.charge.clear(); // no charge! kWh = 0
				calc.newReadingValue = calc.prevReading.getReading();
				calc.newOperationId = calc.prevReading.getOperation().getId();
			} else {
				/* as estimate */
				calc.newOperationId = Operation.ESTIMATE;
				calc.newReadingValue = calc.prevReading.getReading() + calc.charge.getKwh() / calc.newReading.getMeterCoeff();
			}
		}

		/*
		 * No previous real reading.
		 */
		else if (calc.prevActReading == null) {
			calc.newReadingValue = calc.newReading.getReading();
			calc.newOperationId = Operation.CONTROL_READING;
			/* calculate using intalled capacity */
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
		}

		/*
		 * Reading is OK.
		 */
		// a. previous is estimate
		else if (prevOperationId == Operation.ESTIMATE) {
			calc.forceEstimateCorrection = true;
			calc.estimateReading = deriveReading(calc.prevActReading.getReading(), calc.newReading.getReading(), calc.prevActReading.getItemDate(), calc.newReading.getItemDate(), calc.prevReading.getItemDate(), calc.newReading.getMeter().getDigits(), true);
			double kwh = deriveKwh(calc.newReading.getReading(), calc.estimateReading, calc.newReading.getMeterCoeff(), calc.newReading.getMeter().getDigits(), calc.newReading.getMeterAcceleration());
			ChargeElement elmnt = createChargeElement(d1, d2, round(kwh), 0, -1, false, false);
			calc.charge.clear();
			calc.charge.addElement(elmnt);
			calc.newOperationId = Operation.READING;
		}
		// b. previous is actual reading
		else {
			double kwh = deriveKwh(calc.newReading.getReading(), calc.prevActReading.getReading(), calc.newReading.getMeterCoeff(), calc.newReading.getMeter().getDigits(), calc.newReading.getMeterAcceleration());
			ChargeElement elmnt = createChargeElement(d1, d2, round(kwh), 0, -1, false, false);
			calc.charge.clear();
			calc.charge.addElement(elmnt);
			calc.newOperationId = Operation.READING;
		}
	}

	/**
	 * Cycle kWh calculation without reading.
	 */
	protected static void calculateCycleKwhWithoutReading(ReadingCalculation calc) {
		/* Installed capacity and cut history */
		List instCpHistory = calc.newReading.getInterval().getDetails().getInstCpItems();
		List cutHistory = calc.newReading.getInterval().getDetails().getCuts();
		/* calculation hint */
		int calcHint = calc.newReading.getCalculationHint();
		int prevOperationId = calc.prevReading == null ? Operation.WITHOUT_METER : calc.prevReading.getOperation().getId();
		/*
		 * Without meter.
		 */
		if (calc.newReading.getMeter().getId() == Meter.METER_NONE) {
			calc.newOperationId = Operation.WITHOUT_METER;
			calc.newReadingValue = 0.f;
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
		}
		/*
		 * Damaged meter.
		 */
		else if (!calc.newReading.getMeterStatus()) {
			calc.newOperationId = Operation.NOT_OPERABLE_METER;
			calc.newReadingValue = 0.f;
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
		}
		/*
		 * No prev. reading, or previous reading of the following category: not
		 * operable meter, meter deinstall, without meter.
		 */
		else if (/*
				 * calc.prevActReading == null || UNNECESSARY conditional
				 * operation
				 */
		prevOperationId == Operation.WITHOUT_METER || prevOperationId == Operation.NOT_OPERABLE_METER || prevOperationId == Operation.METER_DEINSTALL) {
			calc.newOperationId = Operation.WITHOUT_METER;
			calc.newReadingValue = 0.f;
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
		}
		/*
		 * Otherwise...
		 */
		else {
			// calc.newOperationId = Operation.WITHOUT_METER;
			// calc.newReadingValue = 0.f;
			calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
			boolean wasCutted = calc.charge.isCutUsed();
			if (Math.abs(calc.charge.getKwh()) < CoreUtils.MIN_KWH && wasCutted) {
				calc.charge.clear();
				calc.newOperationId = calc.prevReading.getOperation().getId();
				calc.newReadingValue = calc.prevReading.getReading();
			} else {
				calc.newOperationId = Operation.ESTIMATE;
				calc.newReadingValue = calc.prevReading.getReading() + calc.charge.getKwh() / calc.newReading.getMeterCoeff();
			}
		}
	}

	/**
	 * Notcycle kWh calculation.
	 */
	protected static void calculateNotCycleKwh(ReadingCalculation calc) {
		/* check reading/meter digits compability */
		if (Math.pow(10, calc.newReading.getMeter().getDigits()) < calc.newReading.getReading())
			throw new RecalcException(CalcMessage.getMessage("calc.error.meter_digits_not_compatible_with_reading"));
		/* interval dates */
		Date d1 = ((ChargeElement) calc.charge.expansion().get(0)).getStartDate();
		Date d2 = ((ChargeElement) calc.charge.expansion().get(0)).getEndDate();
		/* Installed capacity and cut history */
		List instCpHistory = calc.newReading.getInterval().getDetails().getInstCpItems();
		List cutHistory = calc.newReading.getInterval().getDetails().getCuts();
		/* calculation hint */
		int calcHint = calc.newReading.getCalculationHint();
		// operation types
		int operId = calc.newReading.getOperation().getId();
		int prevOperId = calc.prevReading == null ? -1 : calc.prevReading.getOperation().getId();
		/* switch using operation ID */
		switch (operId) {
		case Operation.READING:
		case Operation.SALE:
		case Operation.METER_DEINSTALL:
		case Operation.BALANCE_READING:
			/*
			 * Previous was meter deinstall
			 */
			if (prevOperId == Operation.METER_DEINSTALL) {
				calc.newOperationId = Operation.CONTROL_READING;
				calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
			}
			/*
			 * When no previous actual reading and the previous was without
			 * meter
			 */
			else if (calc.prevActReading == null || prevOperId == Operation.WITHOUT_METER || prevOperId == Operation.NOT_OPERABLE_METER) {
				calc.newOperationId = Operation.CONTROL_READING;
				calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
			}
			/*
			 * If the previous was estimate
			 */
			else if (prevOperId == Operation.ESTIMATE) {
				calc.forceEstimateCorrection = true;
				calc.estimateReading = deriveReading(calc.prevActReading.getReading(), calc.newReading.getReading(), calc.prevActReading.getItemDate(), calc.newReading.getItemDate(), calc.prevReading.getItemDate(), calc.newReading.getMeter().getDigits(), true);
				double kwh = deriveKwh(calc.newReading.getReading(), calc.estimateReading, calc.newReading.getMeterCoeff(), calc.newReading.getMeter().getDigits(), calc.newReading.getMeterAcceleration());
				ChargeElement elmnt = createChargeElement(d1, d2, round(kwh), 0, -1, false, false);
				calc.charge.clear();
				calc.charge.addElement(elmnt);
				calc.newOperationId = Operation.READING;
			}
			/*
			 * Previous was real reading
			 */
			else {
				double kwh = deriveKwh(calc.newReading.getReading(), calc.prevActReading.getReading(), calc.newReading.getMeterCoeff(), calc.newReading.getMeter().getDigits(), calc.newReading.getMeterAcceleration());
				ChargeElement elmnt = createChargeElement(d1, d2, round(kwh), 0, -1, false, false);
				calc.charge.clear();
				calc.charge.addElement(elmnt);
			}
			break;

		case Operation.METER_INSTALL:
			/*
			 * If previous was meter deinstall
			 */
			if (prevOperId == Operation.METER_DEINSTALL) {
				if (Date.isEqual(d1, d2))
					calc.charge.clear();
				else
					/* leave unchanged */;
			}
			/*
			 * Everything else
			 */
			else {
				calculateInstCapacity(calc.charge, instCpHistory, cutHistory, calcHint, calc.prevReadingDateWasForced);
			}
			break;
		case Operation.CONTROL_READING:
			/* leave unchanged */
			break;
		default: /* not supported not-cycle operation */
			throw new RecalcException(CalcMessage.getMessage("calc.error.not_supported_not_cycle_operation", new Object[] { new Integer(operId) }));
		}
	}

	/**
	 * <p>
	 * Calculates installed capacity using calculation hint. Calculation period
	 * and initial value of kWh charge should be submitted using
	 * <code>charge</code> parameter.
	 * 
	 * <p>
	 * If calculation hint is {@link RecalcItem#HINT_PRESERVE_BOTH} then the
	 * procedure exits without futher calculations.
	 * 
	 * <p>
	 * If calculation hint is {@link RecalcItem#HINT_DERIVE_FROM_EXISTING_KWH}
	 * then avearage day charge will be derived and calculated as a simple
	 * per-day charge.
	 * 
	 * <p>
	 * If the {@link RecalcItem#HINT_NONE} hint (default) is used then
	 * calculation is performed according to Billing System standard procedure
	 * of charging using intalled capacity; this is using single installed
	 * capacity which contains end-date of the charging period.
	 * 
	 * <p>
	 * If {@link RecalcItem#HINT_USE_CONTINUOUS_BY_INSTCP} is used then
	 * calculation is performed applying the full history of installed
	 * capacities by appropriate intervals.
	 * 
	 * <p>
	 * For the last two cases mentioned above, cut history is also applied.
	 * 
	 * <p>
	 * Note also that there is no guaranty on saving inputed charge. Also charge
	 * on the output will contain only kWh part of charge, with zero GEL charge.
	 * GEL charge should be expanded later.
	 * 
	 * @param charge
	 *            charge, is required to have one and only one charge expansion
	 *            record: initial history charge record. After compliting this
	 *            procedure charge parameter may contain more than one expansion
	 *            record.
	 * @param instcp
	 *            intalled capacity history
	 * @param cuts
	 *            cut history
	 * @param hint
	 *            calculation hint
	 * @param forcePrevDate
	 *            preserve previous date unchanged. When calculating using
	 *            standard inst. capacity procedure there is a restriction on 60
	 *            days of charging. By standard procedure if the charge interval
	 *            is greater than 60 days, then it is adjusted to be equal to 60
	 *            days (by moving previous date). Using this flag you can avoid
	 *            this restriction (when <code>true</code> restriction is
	 *            avoided).
	 */
	public static void calculateInstCapacity(Charge charge, List instcp, List cuts, int hint, boolean forcePrevDate) {
		if (charge.expansion().size() != 1)
			throw new IllegalArgumentException("Initial expansion size should be 1.");
		Date d1 = ((ChargeElement) charge.expansion().get(0)).getStartDate();
		Date d2 = ((ChargeElement) charge.expansion().get(0)).getEndDate();
		double kwh = ((ChargeElement) charge.expansion().get(0)).getKwh();
		switch (hint) {
		case RecalcItem.HINT_PRESERVE_BOTH:
			return; // nothing to change in this case
		case RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH:
			/*
			 * here we derive new installed capacity history fragment which
			 * fully covers this charge and have the only record with avearage
			 * day charge
			 */
			double dayCharge = kwh / (Date.getIntervalInDays(d2, d1));
			RecalcInstCp inst = new RecalcInstCp();
			inst.setAmount(dayCharge);
			inst.setStartDate(d1.prevDay());
			inst.setEndDate(d2.nextDay());
			inst.setOption(RecalcInstCp.AVERAGE_DAY_CHARGE);
			List new_instcp = new ArrayList();
			new_instcp.add(inst);
			calculateInstCapacityUsingStandardProcedure(charge, d1, d2, new_instcp, cuts, forcePrevDate/*
																										 * doesnot
																										 * matter
																										 */);
			return;
		case RecalcItem.HINT_USE_CONTINUOUS_BY_INSTCP:
			/* calculate using full inst. capacity history */
			calculateInstCapacityUsingContinuousProcedure(charge, d1, d2, instcp, cuts);
			return;
		default:
			/* standard procedure */
			calculateInstCapacityUsingStandardProcedure(charge, d1, d2, instcp, cuts, forcePrevDate);
			return;
		}
	}

	/**
	 * See "calculateInstCapacity" method for details. This is a method which is
	 * used to calculate with standard hint.
	 * 
	 * <p>
	 * Not for direct use: take "calculateInstCapacity" with appropriate hint
	 * value.
	 */
	public static void calculateInstCapacityUsingStandardProcedure(Charge charge, Date d1, Date d2, List instcp, List cuts, boolean forcePrevDate) {
		/* clear charge */
		charge.clear();
		/* get dates intervals */
		Date[] intervals = extractActualChargeIntervals(d1, d2, cuts, charge);
		/* charge each interval */
		for (int i = 0; i < intervals.length / 2; i++) {
			Date i1 = intervals[i * 2];
			Date i2 = intervals[i * 2 + 1];
			Date start = i1;
			Date end = i2;
			// NEW!
			RecalcInstCp item = null;
			if (instcp != null) {
				for (int j = 0; j < instcp.size(); j++) {
					RecalcInstCp it = (RecalcInstCp) instcp.get(j);
					Date t1 = it.getStartDate();
					Date t2 = it.getEndDate();
					if ((t1 == null || Date.isLessOrEqual(t1, end)) && (t2 == null || Date.isGreaterOrEqual(t2, end))) {
						item = it;
						break;
					}
				}
			}
			//
			if (item == null)
				continue;
			if (!forcePrevDate && item.getOption() != RecalcInstCp.AVERAGE_DAY_CHARGE && Date.getIntervalInDays(d2, d1) > INSTCP_USING_MAX_VALIDITY_PERIOD)
				start = Date.addDays(d2, -INSTCP_USING_MAX_VALIDITY_PERIOD);

			/*
			 * The last parameter depends on convension. Whether you need to
			 * consider previous month days count at fixed date, then "d2"
			 * should be used, otherwise null is preffered.
			 */
			double kwh = instcpKwhCharge(start, end, item, null/* d2 */);
			ChargeElement elmnt = createChargeElement(start, end, kwh, 0.f, -1, false, false);
			charge.addElement(elmnt);
		}
	}

	/**
	 * See "calculateInstCapacity" method for details. This is a method which is
	 * used to calculate with continuous hint.
	 * 
	 * <p>
	 * Not for direct use: take "calculateInstCapacity" with appropriate hint
	 * value.
	 */
	public static void calculateInstCapacityUsingContinuousProcedure(Charge charge, Date d1, Date d2, List instcp, List cuts) {
		/* clear all previous charge records */
		charge.clear();
		/* loop over installed capacities */
		for (int i = 0; i < instcp.size(); i++) {
			RecalcInstCp cp = (RecalcInstCp) instcp.get(i);
			Date t1 = cp.getStartDate();
			Date t2 = cp.getEndDate();
			Date start = null;
			Date end = null;
			// t1 .. d1 .. d2 .. t2
			if ((t1 == null || Date.isLessOrEqual(t1, d1)) && (t2 == null || Date.isGreaterOrEqual(t2, d2))) {
				start = d1;
				end = d2;
			}
			// t1 .. d1 .. t2 .. d2
			else if ((t1 == null || Date.isLessOrEqual(t1, d1)) && (t2 != null && Date.isGreaterOrEqual(t2, d1))) {
				start = d1;
				end = t2;// .nextDay();
			}
			// d1 .. t1 .. t2 .. d2
			else if ((t1 != null && Date.isGreaterOrEqual(t1, d1)) && (t2 != null && Date.isLessOrEqual(t2, d2))) {
				start = t1.prevDay();
				end = t2;// .nextDay();
			}
			// d1 .. t1 .. d2 .. t2
			else if ((t1 != null && Date.isLessOrEqual(t1, d2) && (t2 == null || Date.isGreaterOrEqual(t2, d2)))) {
				start = t1.prevDay(); //
				end = d2;
			}
			// out of range
			else
				continue;

			/* apply cut history */
			Date[] intervals = extractActualChargeIntervals(start, end, cuts, charge);
			for (int j = 0; j < intervals.length / 2; j++) {
				Date int1 = intervals[2 * j];
				Date int2 = intervals[2 * j + 1];
				double kwh = instcpKwhCharge(int1, int2, cp, end);
				ChargeElement elmnt = createChargeElement(int1, int2, kwh, 0.f, -1, false, false);
				charge.addElement(elmnt);
			}
		}
	}

	/**
	 * Returns amount of kWh for the given intalled capacity history record.
	 * This method is used both in standard and continuous installed capacity
	 * calculation methods.
	 * 
	 * @param d1
	 *            start date
	 * @param d2
	 *            end date
	 * @param instcp
	 *            installed capacity history record
	 * @param forcePrevMonthDaysOn
	 *            when it is required to force previous month days count on
	 *            particular date
	 * @return kWh charge for appropriate period
	 */
	public static double instcpKwhCharge(Date d1, Date d2, RecalcInstCp instcp, Date forcePrevMonthDaysOn) {
		int distinction = Date.getIntervalInDays(d2, d1);
		if (distinction < 0)
			throw new IllegalArgumentException();
		if (distinction == 0)
			return 0.f;
		int option = instcp.getOption();
		int prevMonthDaysCount = getPrevMonthDaysCount(forcePrevMonthDaysOn == null ? d2 : forcePrevMonthDaysOn);
		switch (option) {
		case RecalcInstCp.NORMALIZE_ON_30_DAYS:
			return round(instcp.getAmount() / 30.f * distinction, 0);
		case RecalcInstCp.NORMALIZE_ON_PREV_MONTH:
			return round(instcp.getAmount() / (1.f * prevMonthDaysCount) * distinction, 0);
		case RecalcInstCp.NORM_ON_PRV_MONTH_RND_ON_3_DAYS:
			int dd = prevMonthDaysCount - distinction;
			if (dd >= 0 && dd <= 3)
				return round(instcp.getAmount(), 0);
			else
				return round(instcp.getAmount() / (1.f * prevMonthDaysCount) * distinction, 0);
		case RecalcInstCp.AVERAGE_DAY_CHARGE:
			return instcp.getAmount() * distinction;
		}
		return 0.f;
	}

	/**
	 * Calculates subsidy or additional charge. There are some cases when
	 * subsidy or additional charge can not be recalculated. In such case a
	 * simple chagre is returned which is treated as a payment (same start/end
	 * dates).
	 * 
	 * @param producer
	 *            subsidy or additional charge item
	 * @param history
	 *            previous calculation history
	 * @return subsidy calculation structure
	 */
	public static SubsCalculation calculateSubsidyOrAddCharge(RecalcItem producer, List history) {
		/* have or not attachment? */
		boolean hasAttachemnt = producer.getSubsidyAttachment() != null;
		/* is one time act? */
		int operId = producer.getOperation().getId();
		boolean isOneTimeAct = operId == Operation.ONE_TIME_ACT || operId == Operation.ONE_TIME_ACT_AFTER_VAT_CHANGE_2005;
		/*
		 * One time act and subsidies without details can not be recalculated.
		 * We leave them as is. If neither kWh nor GEL charge are defined, we
		 * mark calculation result as not valuable (it will not appear in
		 * recalculated results).
		 */
		if (!hasAttachemnt || isOneTimeAct) {
			/* create return result */
			SubsCalculation calc = new SubsCalculation();
			/* check whether is valuable */
			double kwh = producer.getKwh();
			double gel = producer.getGel();
			boolean valuable = Math.abs(gel) >= MIN_GEL || Math.abs(kwh) >= MIN_KWH;
			calc.valuable = valuable;
			if (valuable) { /* adding charge */
				ChargeElement elmnt = createChargeElement(producer.getItemDate(), producer.getItemDate(), kwh, gel, -1, false, false);
				calc.charge.addElement(elmnt);
			}
			return calc;
		}
		/*
		 * Summaries are ignored.
		 */
		else if (operId == Operation.SUMMARY_PERCENT_SUBSIDY || operId == Operation.DISCHARGE_PERCENT_SUBSIDY_GEL) {
			/* create return result */
			SubsCalculation calc = new SubsCalculation();
			calc.valuable = false;
			return calc;
		}
		/*
		 * It's OK to recalculate now.
		 */
		else {
			switch (producer.getOperation().getType().getId()) {
			/* additional charge */
			case OperationType.ADD_CHARGE:
				return calculateSubsidfyOrAddCharge(producer, history, true);
				/* subsidy */
			case OperationType.SUBSIDY:
				return calculateSubsidfyOrAddCharge(producer, history, false);
				/* unexpected situation */
			default:
				throw new RecalcException(CalcMessage.getMessage("calc.error.not_a_subsidy_or_addcharge"));
			}
		}
	}

	/**
	 * Simple structure used in subsidy and additional charge calculations.
	 */
	static class SubsCalculation {
		boolean valuable;
		Charge charge = new Charge();
	}

	/**
	 * Calculates subsidy or additional charge.
	 * 
	 * @param charge
	 *            charge item
	 * @param history
	 *            previous calculation history
	 * @param is
	 *            an additional charge?
	 * @return additional charge calculations
	 */
	private static SubsCalculation calculateSubsidfyOrAddCharge(RecalcItem charge, List history, boolean isAddCharge) {
		/*
		 * Calculation parameters: unit, count, amount and summer amount. Summer
		 * amount is used when calculating season subsidy/additional charges;
		 * the main amount is then considered as a winter part.
		 */
		int unit = charge.getSubsidyAttachment().getUnit();
		int count = charge.getSubsidyAttachment().getCount();
		double amount = charge.getSubsidyAttachment().getAmount();
		boolean hasSummerPart = charge.getSubsidyAttachment().hasAmount2();
		double summerAmount = charge.getSubsidyAttachment().getAmount2();
		/*
		 * Determine catefory of the subsidy/additional charge. If all of the
		 * categories are false, then RecalculationException will be thrown.
		 */
		boolean isGelBased = unit == SubsidyAttachment.GEL;
		boolean isKwhBased = unit == SubsidyAttachment.KWH || unit == SubsidyAttachment.KWH_BY_DAY;
		boolean isPercentBased = unit == SubsidyAttachment.PERCENT;
		/* GEL bases subsidy/additional charge */
		if (isGelBased) {
			SubsCalculation calc = new SubsCalculation();
			double kwh = 0;
			double gel = 0;
			Date d = charge.getItemDate();
			/*
			 * Comp. refuge also has summer part.
			 */
			if (hasSummerPart && isSummerMonth(d)) {
				gel = isAddCharge ? Math.abs(summerAmount * count) : -Math.abs(summerAmount * count);
			} else {
				gel = isAddCharge ? Math.abs(amount * count) : -Math.abs(amount * count);
			}
			calc.valuable = Math.abs(gel) >= MIN_GEL;
			if (calc.valuable) {
				ChargeElement elmnt = createChargeElement(charge.getItemDate(), charge.getItemDate(), kwh, gel, -1, false, false);
				calc.charge.addElement(elmnt);
			}
			return calc;
		}
		/* kWh based additional charge */
		else if (isKwhBased) {
			Charge kwh;
			boolean intervalBased = prefferIntervalBasedCharge(charge.getItemDate());
			/* point based charge */
			if (!intervalBased && unit == SubsidyAttachment.KWH) {
				Date d = charge.getItemDate();
				kwh = new Charge();
				double kwhValue = 0;
				if (hasSummerPart && isSummerMonth(d)) {
					kwhValue = Math.abs(summerAmount * count);
				} else {
					kwhValue = Math.abs(amount * count);
				}
				ChargeElement elmnt = createChargeElement(d.prevDay(), d, kwhValue, 0.f, -1, false, false);
				kwh.addElement(elmnt);
			}
			/* interval based charge */
			else {
				/* find previous reading for this add charge */
				CalculationItem prevReading = getLastReading(charge.getAccount(), history, false, false, false);
				RecalcItem producer = charge;
				if (prevReading != null && Date.isEqual(prevReading.getItemDate(), charge.getItemDate())) {
					if (prevReading.getProducer() != null)
						producer = prevReading.getProducer();
					int index = history.indexOf(prevReading);
					List newHistory = new ArrayList();
					for (int i = 0; i < index; i++)
						newHistory.add(history.get(i));
					prevReading = getLastReading(charge.getAccount(), newHistory, false, false, false);
				}
				/* interval dates */
				Date d1 = getPreviousReadingDate(producer, prevReading, false);
				Date d2 = charge.getItemDate();
				/* avearage day charge */
				double dayCharge = Math.abs(amount * count);
				if (hasSummerPart && isSummerMonth(d2))
					dayCharge = Math.abs(summerAmount * count);
				if (intervalBased && unit == SubsidyAttachment.KWH) {
					/*
					 * When calculating based on interval nominal charge is
					 * normalized on 30 days.
					 */
					dayCharge = dayCharge / 30;
				} else if (unit == SubsidyAttachment.KWH) {
					int interval = Date.getIntervalInDays(d2, d1);
					dayCharge = dayCharge / (interval == 0 ? 1 : interval);
				}
				/* calculate as installed capacity */
				RecalcInstCp instcp = new RecalcInstCp();
				instcp.setAmount(dayCharge);
				instcp.setOption(RecalcInstCp.AVERAGE_DAY_CHARGE);
				List instcps = new ArrayList();
				instcps.add(instcp);
				kwh = new Charge();
				ChargeElement elmnt = createChargeElement(d1, d2, 0, 0, -1, false, false);
				kwh.addElement(elmnt);
				List cuts = charge.getInterval().getDetails().getCuts();
				calculateInstCapacity(kwh, instcps, cuts, RecalcItem.HINT_NONE, false);
			}
			/* make full charge */
			Charge full;
			List tariffs = charge.getInterval().getDetails().getTariffs();
			boolean stepExists = isStepTariffInChargePeriod(kwh, tariffs);
			if (!isAddCharge || !stepExists) {
				/*
				 * Make GEL charge when no step or when is kWh subsidy. KWH
				 * based subsidy should be always charged with GEL, while it is
				 * not discharged in
				 */
				full = expandKwhCharge(kwh, tariffs);
			} else {
				full = kwh;
			}
			/* make signs correction */
			if (isAddCharge)
				full.makeAllPositive();
			else
				full.makeAllNegative();
			/* form object to be returned */
			SubsCalculation calc = new SubsCalculation();
			calc.valuable = Math.abs(full.getGel()) >= MIN_GEL || Math.abs(full.getKwh()) >= MIN_KWH;
			calc.charge = full;
			return calc;
		}
		/* percent based additional charge */
		else if (isPercentBased) {
			/*
			 * Find previous reading for this add charge.
			 * 
			 * NB: Note, that here we require that the CURRENT_CHARGE operation
			 * should be considered as a common charge.
			 */
			CalculationItem prevReading = getLastReading(charge.getAccount(), history, false, false, true);
			/*
			 * When no appropriate charge can be found, then return invaluable
			 * result
			 */
			if (prevReading == null || !Date.isEqual(prevReading.getItemDate(), charge.getItemDate())) {
				SubsCalculation calc = new SubsCalculation();
				calc.valuable = false;
				return calc;
			}
			/*
			 * Make copy of the charge.
			 */
			Charge full = new Charge();
			prevReading.getCharge().copyCharge(full);
			/*
			 * When subsidy, we must also take into account all other charges
			 * after the main charge item.
			 */
			if (!isAddCharge) {
				int index = history.indexOf(prevReading);
				for (int i = index + 1; i < history.size(); i++) {
					CalculationItem item = (CalculationItem) history.get(i);
					if (item.getOperation().getDiffCategory() == DiffDetail.CHARGE) {
						for (int j = 0; j < item.getCharge().expansion().size(); j++) {
							ChargeElement el = (ChargeElement) item.getCharge().expansion().get(j);
							el = el.makeCopy();
							full.addElement(el);
						}
					}
				}
			}
			double coeff = isAddCharge ? Math.abs(amount / 100.f) : -Math.abs(amount / 100.f);
			full.applyFactor(coeff);
			SubsCalculation calc = new SubsCalculation();
			calc.valuable = Math.abs(full.getGel()) >= MIN_GEL || Math.abs(full.getKwh()) >= MIN_KWH;
			calc.charge = full;
			return calc;
		} else {
			/* not supported unit */
			throw new RecalcException(CalcMessage.getMessage("calc.error.not_supported_unit", new Object[] { new Integer(unit) }));
		}
	}

	/**
	 * Returns <code>true</code> if for the given date subsidy and additional
	 * charge is preffered to be charged in interval. For Telasi Billing System
	 * this is '01-Jun-2006'. Before this date subsidy was mainly charged using
	 * only current tariff value.
	 * 
	 * @param d
	 *            date of the interest
	 * @return whether to preffer interval-based charging for subsidy and
	 *         additional charge
	 */
	public static boolean prefferIntervalBasedCharge(Date d) {
		return Date.isGreater(d, new Date(2006, 5, 31));
	}

	/**
	 * Compact Refuge subsidy can be billed on month end or on cycle charge.
	 * After Feb, 2006 this subsidy is billed on cycle, but before it was billed
	 * on cycle charge.
	 */
	public static boolean isCompRefugeOnMonthEndInterval(int year, int month) {
		return Date.isGreater(new Date(year, month, 1), new Date(2006, 1, 31));
	}

	/**
	 * Returns true, when given date's month is a summer month. Convension is
	 * due to the seasons determination in Telasi Billing System.
	 */
	public static boolean isSummerMonth(Date d) {
		switch (d.getMonth()) {
		case 1:
		case 2:
		case 3:
		case 11:
		case 12:
			return false;
		default:
			return true;
		}
	}

	//
	// ====================
	// Estimates Correction
	// ====================
	//

	/**
	 * Calculates estimates correction using previous calculation history and
	 * the item which corrects estimates.
	 */
	public static List calculateEstimateCorrection(Session session, List history, // <CalculationItem>
			RecalcItem currReading, List/* FacturaExpansion */sysExpansion) {
		/*
		 * Create a new recalculation for estimate correction.
		 */
		Recalc recalc = createEstimatesCorrectionRecalc(history, currReading);

		/*
		 * Calculate this recalc.
		 */
		Calculator calc = new Calculator();
		calc.calculate(session, recalc);
		DiffSummary summary = calc.getDiffSummary();

		// register in system expansion
		if (sysExpansion != null) {
			List exp = calc.getFacturaExpansion();
			if (exp != null && !exp.isEmpty()) {
				sysExpansion.addAll(exp);
			}
		}

		/*
		 * Generate results.
		 */
		List results = new ArrayList();
		if (summary != null) {
			for (int i = 0; i < summary.getDetails().size(); i++) {
				DiffDetail det = (DiffDetail) summary.getDetails().get(i);
				CalculationItem res = createDerivedItem(currReading, false);
				res.getCharge().clear();
				ChargeElement el = new ChargeElement();
				el.setStartDate(currReading.getItemDate());
				el.setEndDate(currReading.getItemDate());
				el.setGel(det.getOriginalItem().getCharge().getGel());
				el.setKwh(det.getOriginalItem().getCharge().getKwh());
				res.getCharge().addElement(el);
				res.setOperation(DbUtilities.findOperationById(det.getOriginalItem().getOperation().getId()));
				res.setReading(0.f);
				res.setProducer(null);
				res.setItemNumber("sys");
				results.add(res);
			}
		}
		/* RESULTS! */
		return results;
	}

	/**
	 * TODO: write this documentation AGAIN!
	 * =====================================
	 * 
	 * Recalculation is created so, that recalculated produce correction which
	 * is similar to changes from estimates into reading conversion.
	 * 
	 * <p>
	 * First of all we look for the previous real reading. New recalculation
	 * will be created from this reading and including last estimate in history.
	 * All estimates will be changed to reading operations with derived reading
	 * values. The "previous actual reading" will be converted into reading with
	 * emty charge and with "save kWh and GEL charge" hint.
	 * 
	 * <p>
	 * One more option we should pay attention for is whether
	 * "previous actual reading" is cyclic or not. When the period of its cycle
	 * does not contains step-tariff, then its no matter, but if there is a step
	 * period, then we need to handle not-cyclic "previous actual reading" very
	 * carefully. Because when step tariff, then we need to use summarized
	 * charges. Therefore all charges, which are above "prev.act. reading" may
	 * play role when make summary on the first former estimate cycle. We can
	 * take these charges into account if we add an additional charge which is a
	 * sum of all this unhandled charges. The same can be said about percent
	 * subsidy records above "prevActReading".
	 * 
	 * <p>
	 * If the "actual prev. reading" is not cyclic, then we should also force
	 * previous operation date in the first estimate cycle, to be sure to
	 * recalculate properly.
	 */
	private static Recalc createEstimatesCorrectionRecalc(List history, // <CalculationItem>
			RecalcItem currReading) {
		/* getting account */
		Account acc = currReading.getInterval().getDetails().getRecalc().getAccount();
		List tariffs = currReading.getInterval().getDetails().getTariffs();
		/* getting previous actual reading */
		CalculationItem prevActReading = getLastReading(acc, history, true, false, false);
		if (prevActReading == null)
			throw new RecalcException("Can not correct estimates, while " + "no previous actual reading.");
		/*
		 * When previous actual reading is not cyclic, we need to ensure,
		 * whether in this cycle step-tariff is used. If step tariff is used we
		 * need to summarize all the charges and additional charges, which are
		 * above prev.actual reading and take it into account on first estimate
		 * cycle.
		 * 
		 * If previous actual reading is cycle, then we do not need to look up
		 * any more: we may just start from it.
		 */
		Charge commonCharge = new Charge();
		Charge percentSubsidy = new Charge();
		int fromIndex = history.indexOf(prevActReading);
		Date prevCycleDate = null;
		if (!prevActReading.getCycle()) {
			for (int i = fromIndex /*- 1*/; i >= 0; i--) { /*
																 * moving back
																 * in history
																 */
				CalculationItem prev = (CalculationItem) history.get(i);
				boolean isCycle = prev.getCycle();
				if (!isCycle) { /* this are all not cyclic items */
					if (prev.getAccount().getId() != acc.getId())
						continue;
					boolean isCharge = prev.getOperation().getDiffCategory() == DiffDetail.CHARGE;
					boolean isPercentSubsidy = prev.getOperation().getDiffCategory() == DiffDetail.SUBSIDY_PERCENT;
					if (isCharge) {
						for (int j = 0; j < prev.getCharge().expansion().size(); j++) {
							ChargeElement el = (ChargeElement) prev.getCharge().expansion().get(j);
							el = el.makeCopy();
							commonCharge.addElement(el);
						}
					} else if (isPercentSubsidy) {
						for (int j = 0; j < prev.getCharge().expansion().size(); j++) {
							ChargeElement el = (ChargeElement) prev.getCharge().expansion().get(j);
							el = el.makeCopy();
							percentSubsidy.addElement(el);
						}
					}
				} else if (isCycle) { /* exit as soon any cycle item is reached */
					prevCycleDate = prev.getItemDate();
					break;
				}
			}
			/*
			 * We need also to take into account next current charge.
			 */
			for (int i = fromIndex + 1; i < history.size(); i++) { /*
																	 * moving
																	 * forward
																	 * in
																	 * history
																	 */
				CalculationItem next = (CalculationItem) history.get(i);
				boolean sameAccount = next.getAccount().getId() == acc.getId();
				if (!sameAccount)
					continue;
				boolean sameCycle = next.getCycle() == prevActReading.getCycle();
				boolean sameDate = Date.isEqual(next.getItemDate(), prevActReading.getItemDate());
				if (!sameCycle || !sameDate)
					break;
				boolean myCharge = next.getOperation().getId() == Operation.CURRENT_CHARGE;
				if (myCharge) {
					for (int j = 0; j < next.getCharge().expansion().size(); j++) {
						ChargeElement el = (ChargeElement) next.getCharge().expansion().get(j);
						el = el.makeCopy();
						commonCharge.addElement(el);
					}
				}
				boolean mySubsidy = next.getOperation().getDiffCategory() == DiffDetail.SUBSIDY_PERCENT;
				if (mySubsidy) {
					for (int j = 0; j < next.getCharge().expansion().size(); j++) {
						ChargeElement el = (ChargeElement) next.getCharge().expansion().get(j);
						el = el.makeCopy();
						percentSubsidy.addElement(el);
					}
				}
			}
			/*
			 * Adjust previous cycle date.
			 */
			if (prevCycleDate == null)
				prevCycleDate = getPreviousReadingDate(currReading, null, true);
			/* is a step period? */
			boolean stepExists = isStepTariffInChargePeriod(prevCycleDate, currReading.getItemDate(), tariffs);
			/* make empty common and percent subsidy charges if no step */
			if (!stepExists) {
				percentSubsidy.clear();
				commonCharge.clear();
			}
		}
		/*
		 * All above was preparation. Let's now start to build recalculation.
		 */
		Recalc recalc = new Recalc();
		recalc.setCustomer(acc.getCustomer());
		recalc.setAccount(acc);
		recalc.setId(newId());
		/*
		 * The easyest thing to do is copy tariffs, cuts, installed capacities,
		 * but not regulars (!), cos they are already in the given history.
		 */
		List cuts = currReading.getInterval().getDetails().getCuts();
		for (int i = 0; i < cuts.size(); i++) {
			RecalcCutItem cut = (RecalcCutItem) cuts.get(i);
			RecalcCutItem newCut = new RecalcCutItem();
			newCut.setId(newId());
			newCut.setStartDate(cut.getStartDate());
			newCut.setEndDate(cut.getEndDate());
			newCut.setDetails(recalc.getDetails());
			recalc.getDetails().getCuts().add(newCut);
		}
		List instcps = currReading.getInterval().getDetails().getInstCpItems();
		for (int i = 0; i < instcps.size(); i++) {
			RecalcInstCp instcp = (RecalcInstCp) instcps.get(i);
			RecalcInstCp newInstcp = new RecalcInstCp();
			newInstcp.setId(newId());
			newInstcp.setStartDate(instcp.getStartDate());
			newInstcp.setEndDate(instcp.getEndDate());
			newInstcp.setAmount(instcp.getAmount());
			newInstcp.setOption(instcp.getOption());
			newInstcp.setDetails(recalc.getDetails());
			recalc.getDetails().getInstCpItems().add(newInstcp);
		}
		for (int i = 0; i < tariffs.size(); i++) {
			RecalcTariffItem tariff = (RecalcTariffItem) tariffs.get(i);
			RecalcTariffItem newTariff = new RecalcTariffItem();
			newTariff.setId(newId());
			newTariff.setTariff(tariff.getTariff());
			newTariff.setStartDate(tariff.getStartDate());
			newTariff.setEndDate(tariff.getEndDate());
			newTariff.setDetails(recalc.getDetails());
			recalc.getDetails().getTariffs().add(newTariff);
		}
		/*
		 * Create the first not-calculable interval. It is used to store
		 * previous cycle date. If the previous cycle date is not defined, then
		 * this interval will not be created.
		 */
		if (prevCycleDate != null) {
			RecalcInterval preInterval = new RecalcInterval();
			preInterval.setId(newId());
			preInterval.setName("pre_est_correction");
			preInterval.setEditable(false);
			preInterval.setDetails(recalc.getDetails());
			recalc.getDetails().getIntervals().add(preInterval);
			preInterval.setItems(new ArrayList());
			RecalcItem preItem = new RecalcItem();
			Operation oper = DbUtilities.findOperationById(Operation.READING);
			preItem.setOperation(oper);
			preItem.setOriginalOperation(oper);
			preItem.setItemDate(prevCycleDate);
			preItem.setOriginalItemDate(prevCycleDate);
			preItem.setAccount(acc);
			preItem.setCustomer(acc.getCustomer());
			preItem.setCycle(true);
			preInterval.getItems().add(preItem);
			preItem.setInterval(preInterval);
		}
		/*
		 * And now make build interval and items.
		 */
		RecalcInterval interval = new RecalcInterval();
		interval.setId(newId());
		interval.setName("est_correction");
		interval.setEditable(true);
		interval.setDetails(recalc.getDetails());
		recalc.getDetails().getIntervals().add(interval);
		interval.setItems(new ArrayList());
		/*
		 * Create the first item.
		 */
		RecalcItem firstItem = createRecalcItem(prevActReading, currReading);
		firstItem.setCalculationHint(RecalcItem.HINT_PRESERVE_BOTH);

		firstItem.setKwh(0);
		firstItem.setGel(0);
		firstItem.setOriginalKwh(0);
		firstItem.setOriginalGel(0);
		interval.getItems().add(firstItem);
		firstItem.setInterval(interval);
		/*
		 * Create first additional charge item. This item is not recalculable.
		 * Why we need it to be recalculated?
		 */
		if (Math.abs(commonCharge.getKwh()) >= MIN_KWH) {
			RecalcItem firstAddCharge = createRecalcItem(prevActReading, currReading);
			Operation oper = DbUtilities.findOperationById(Operation.REGULAR_ADD_CHARGE_REGULAR);
			firstAddCharge.setOperation(oper);
			firstAddCharge.setOriginalOperation(oper);
			firstAddCharge.setReading(0.f);
			firstAddCharge.setOriginalReading(0.f);
			firstAddCharge.setKwh(commonCharge.getKwh());
			firstAddCharge.setOriginalKwh(commonCharge.getKwh());
			firstAddCharge.setGel(commonCharge.getGel());
			firstAddCharge.setOriginalGel(commonCharge.getGel());
			interval.getItems().add(firstAddCharge);
			firstAddCharge.setInterval(interval);
		}
		/*
		 * Create first percent subsidy item. This item is not recalculable. Why
		 * we need it to be recalculated?
		 */
		if (Math.abs(commonCharge.getKwh()) >= MIN_KWH && Math.abs(percentSubsidy.getKwh()) >= MIN_KWH) {
			RecalcItem firstPercentItem = createRecalcItem(prevActReading, currReading);
			Operation oper = DbUtilities.findOperationById(Operation.REGULAR_SUBSIDY_PERCENT);
			firstPercentItem.setOperation(oper);
			firstPercentItem.setOriginalOperation(oper);
			firstPercentItem.setReading(0.f);
			firstPercentItem.setOriginalReading(0.f);
			firstPercentItem.setKwh(percentSubsidy.getKwh());
			firstPercentItem.setOriginalKwh(percentSubsidy.getKwh());
			firstPercentItem.setGel(percentSubsidy.getGel());
			firstPercentItem.setOriginalGel(percentSubsidy.getGel());
			interval.getItems().add(firstPercentItem);
			firstPercentItem.setInterval(interval);
		}
		/*
		 * We have already found from where to start (fromIndex), now we need
		 * the upper boundary (toIndex). This is a last estimate in history.
		 * Normally it is in the end, but it is quite often, that there are
		 * other operations behind. It is better to cut them off! Lets go!
		 * uuuuuuuuuuuuuuuuuuuuh!
		 */
		int toIndex = history.size() - 1;
		// 06-Aug-2007:
		// --------------------------------------------------------
		// this was incorrect while we lost some items
		// like additional charges
		// --------------------------------------------------------
		// for ( ; toIndex > fromIndex; toIndex--) {
		// CalculationItem item = (CalculationItem) history.get(toIndex);
		// if ( item.getOperation().getId() == Operation.ESTIMATE
		// || item.getOperation().getId() == Operation.SUMMARY_CHARGE)
		// break;
		// }
		/*
		 * OOPS! When prev. actual reading is cyclic, then it may have other
		 * items after itself. We are not interested in those items. Therefore
		 * we must shift from index when it is neccessary.
		 */
		if (prevActReading.getCycle()) {
			for (; fromIndex < toIndex; fromIndex++) {
				CalculationItem item = (CalculationItem) history.get(fromIndex);
				if (item.getCycle() && Date.isEqual(item.getItemDate(), prevActReading.getItemDate()))
					continue;
				break;
			}
		} else {
			// first reading is already OK
			fromIndex++;
		}
		/*
		 * Now loop over each item and gather all charges and percent subsidies.
		 */
		double r1 = prevActReading.getReading();
		double r2 = currReading.getReading();
		Date d1 = prevActReading.getItemDate();
		Date d2 = currReading.getItemDate();
		int digits = currReading.getMeter().getDigits();
		for (int i = fromIndex; i <= toIndex; i++) {
			CalculationItem item = (CalculationItem) history.get(i);
			if (item.getAccount().getId() != acc.getId())
				continue;
			boolean isCharge = item.getOperation().getDiffCategory() == DiffDetail.CHARGE;
			boolean isPercentSubsidy = item.getOperation().getDiffCategory() == DiffDetail.SUBSIDY_PERCENT;
			if (!isCharge && !isPercentSubsidy)
				continue;
			boolean isEstimate = item.getOperation().getId() == Operation.ESTIMATE;
			RecalcItem newItem = createRecalcItem(item, currReading);
			if (isEstimate) {
				Operation oper = DbUtilities.findOperationById(Operation.READING);
				double readingValue = deriveReading(r1, r2, d1, d2, item.getItemDate(), digits, true);
				newItem.setOperation(oper);
				newItem.setReading(readingValue);
				/*
				 * Discharge should be forced!
				 */
				item.setItemNumber("force_discharge");
			} // else if (isPercentSubsidy) {
			newItem.setSubsidyAttachment(item.getProducer().getSubsidyAttachment());
			// }
			interval.getItems().add(newItem);
			newItem.setInterval(interval);
		}
		return recalc;
	}

	/**
	 * Creates RecalcItem from CalculationItem. This procedure copies the main
	 * properties of prototype. Calculation hint is set to HINT_NONE, and
	 * subsidy attachment is set to <code>null</code> and should be defined
	 * elsewhere.
	 */
	private static RecalcItem createRecalcItem(CalculationItem proto, RecalcItem meterProto) {
		RecalcItem item = new RecalcItem();
		item.setStatus(RecalcItem.ORIGINAL);
		item.setId(newId());
		item.setItemId(newId()); // negative, but not -1, i.e. not "NEW"
		item.setCustomer(proto.getCustomer());
		item.setAccount(proto.getAccount());
		item.setOperation(proto.getOperation());
		item.setOriginalOperation(proto.getOperation());
		item.setItemNumber("estimate_correction_item");
		item.setReading(proto.getReading());
		item.setOriginalReading(proto.getReading());
		item.setKwh(proto.getCharge().getKwh());
		item.setOriginalKwh(proto.getCharge().getKwh());
		item.setGel(proto.getCharge().getGel());
		item.setOriginalGel(proto.getCharge().getGel());
		item.setBalance(0.f);
		item.setOriginalBalance(0.f);
		item.setItemDate(proto.getItemDate());
		item.setOriginalItemDate(proto.getItemDate());
		item.setEnterDate(proto.getEnterDate());
		item.setOriginalEnterDate(proto.getEnterDate());
		item.setCycle(proto.getCycle());
		item.setOriginalCycle(proto.getCycle());
		item.setMeterCoeff(meterProto.getMeterCoeff());
		item.setOriginalMeterCoeff(meterProto.getMeterCoeff());
		item.setMeterStatus(true);
		item.setOriginalMeterStatus(true);
		item.setMeterAcceleration(meterProto.getMeterAcceleration());
		item.setMeter(meterProto.getMeter());
		item.setOriginalMeter(meterProto.getMeter());
		item.setCalculationHint(RecalcItem.HINT_NONE);
		item.setSubsidyAttachment(null);
		item.setOriginalSubsidyAttachment(null);
		item.setBalance(0.f);
		item.setOriginalBalanceGap(0.f);
		item.setCurrentOperationDate(null);
		item.setPreviousOperationDate(null);
		item.setSubAccount(null);
		return item;
	}

	//
	//
	// =====================
	// Differences Managment
	// =====================
	//
	//

	/**
	 * There appeared a problem with subaccount charges and estimates
	 * correction. The way we found around this problem is simply split them
	 * from each-other.
	 */
	public static DiffSummary makeDiffSummary2008(List byCycle, List facturaExpansion) {
		// split main and sub-account charges
		List byCycle0 = new ArrayList(); // main charges
		List byCycle1 = new ArrayList(); // sub-account charges
		for (int i = 0; byCycle != null && i < byCycle.size(); i++) {
			DiffSummary cycle = (DiffSummary) byCycle.get(i);
			DiffSummary cycle0 = new DiffSummary();
			DiffSummary cycle1 = new DiffSummary();
			cycle0.setDate(cycle.getDate());
			cycle1.setDate(cycle.getDate());
			for (int j = 0; cycle.getDetails() != null && j < cycle.getDetails().size(); j++) {
				DiffDetail detail = (DiffDetail) cycle.getDetails().get(j);
				if (detail.getOperation() != null && detail.getOperation().getId() == Operation.SUB_ACCOUNT_CHARGE) {
					cycle1.getDetails().add(detail);
				} else {
					cycle0.getDetails().add(detail);
				}
			}
			if (!cycle0.getDetails().isEmpty())
				byCycle0.add(cycle0);
			if (!cycle1.getDetails().isEmpty())
				byCycle1.add(cycle1);
		}

		// calculate them separately...
		DiffSummary summary0 = makeDiffSummary(byCycle0, facturaExpansion);
		DiffSummary summary1 = makeDiffSummary(byCycle1, facturaExpansion);

		// XXX: not sure about this cleanup
		cleanupSummary(summary0);
		cleanupSummary(summary1);

		// combine both
		DiffSummary total = new DiffSummary();
		if (summary0 != null && summary0.getDetails() != null) {
			total.getDetails().addAll(summary0.getDetails());
		}
		for (int i = 0; summary1 != null && summary1.getDetails() != null && i < summary1.getDetails().size(); i++) {
			DiffDetail detail1 = (DiffDetail) summary1.getDetails().get(i);
			boolean appended = false;
			for (int j = 0; summary0 != null && summary0.getDetails() != null && j < summary0.getDetails().size(); j++) {
				DiffDetail detail0 = (DiffDetail) summary0.getDetails().get(j);
				if (detail0.getOperation().getId() == detail1.getOperation().getId()) {
					for (int k = 0; detail1.getOriginalItem().getCharge() != null && k < detail1.getOriginalItem().getCharge().expansion().size(); k++) {
						ChargeElement element = (ChargeElement) detail1.getOriginalItem().getCharge().expansion().get(k);
						detail0.getOriginalItem().getCharge().addElement(element);
					}
					appended = true;
					continue;
				}
			}
			if (!appended) {
				total.add(detail1);
			}
		}

		return total;
	}

	/**
	 * XXX: this procedure cleans-up related vouchers which have the same
	 * discharge/recharge values
	 */
	private static void cleanupSummary(DiffSummary summary) {
		List forRemove = new ArrayList();
		for (int i = 0; summary != null && summary.getDetails() != null && i < summary.getDetails().size(); i++) {
			DiffDetail detail1 = (DiffDetail) summary.getDetails().get(i);
			int operId1 = detail1.getOperation().getId();
			int related1 = DivisionDateFactory.getCoupledOperationId(operId1);
			for (int j = i + 1; j < summary.getDetails().size(); j++) {
				DiffDetail detail2 = (DiffDetail) summary.getDetails().get(j);
				int operId2 = detail2.getOperation().getId();
				if (operId2 == related1 && Math.abs(detail1.getOriginalGel() + detail2.getOriginalGel()) < MIN_GEL && Math.abs(detail1.getOriginalKwh() + detail2.getOriginalKwh()) < MIN_KWH) {
					forRemove.add(detail1);
					forRemove.add(detail2);
					continue;
				}
			}
		}
		if (!forRemove.isEmpty()) {
			summary.getDetails().removeAll(forRemove);
		}
	}

	/**
	 * Makes summary of differences on cycles.
	 */
	public static DiffSummary makeDiffSummary(List/* DiffSummary */byCycle, List facturaExpansion) {
		/* nothing to summarize */
		if (byCycle == null || byCycle.isEmpty())
			return null;
		/* summary */
		DiffSummary summary = new DiffSummary();
		double currDrvdChargeKwh = 0.f;
		double currOrigChargeKwh = 0.f;
		for (int i = 0; i < byCycle.size(); i++) {
			DiffSummary cycle = (DiffSummary) byCycle.get(i);
			boolean isLastOrigCycle = true;
			boolean isLastDrvdCycle = true;
			boolean hasOrigCharge = false;
			boolean hasDrvdCharge = false;
			for (int k = i + 1; k < byCycle.size(); k++) {
				DiffSummary nextCycle = (DiffSummary) byCycle.get(k);
				if (nextCycle.getDetails() != null && !nextCycle.getDetails().isEmpty()) {
					for (int l = 0; l < nextCycle.getDetails().size(); l++) {
						DiffDetail detail = (DiffDetail) nextCycle.getDetails().get(l);
						if (Math.abs(detail.getOriginalKwh()) >= CoreUtils.MIN_KWH || Math.abs(detail.getOriginalGel()) >= CoreUtils.MIN_GEL)
							hasOrigCharge = true;
						if (Math.abs(detail.getDerivedKwh()) >= CoreUtils.MIN_KWH || Math.abs(detail.getDerivedGel()) >= CoreUtils.MIN_GEL)
							hasDrvdCharge = true;
						if (hasOrigCharge && hasDrvdCharge)
							break;
					}
				}
			}
			if (hasOrigCharge)
				isLastOrigCycle = false;
			if (hasDrvdCharge)
				isLastDrvdCycle = false;
			if (cycle.getDetails() != null && !cycle.getDetails().isEmpty()) {
				/*
				 * @since May-18, 2009
				 * 
				 * We need to identify whether original cycle is closed. If it's
				 * closed than we should not consider this cycle items as
				 * current charges.
				 */
				boolean isOrigCycleClosed = false;
				for (int k = cycle.getDetails().size() - 1; k >= 0; k--) {
					DiffDetail detail = (DiffDetail) cycle.getDetails().get(k);
					CalculationItem item = detail.getOriginalItem();
					if (item != null && item.getProducer().getOriginalCycle()) {
						isOrigCycleClosed = true;
						break;
					}
				}
				// ---------------- end of @since May-18, 2009
				for (int j = 0; j < cycle.getDetails().size(); j++) {
					DiffDetail detail = (DiffDetail) cycle.getDetails().get(j);
					boolean isCharge = detail.getCategory() == DiffDetail.CHARGE;
					boolean isOrigCycle = detail.getOriginalItem() == null ? false : detail.getOriginalItem().getCycle();
					boolean isOrigValuable = Math.abs(detail.getOriginalKwh()) >= CoreUtils.MIN_KWH && Math.abs(detail.getOriginalGel()) < CoreUtils.MIN_GEL;
					boolean isNotSubAccCharge = detail.getOperation() != null && detail.getOperation().getId() != Operation.SUB_ACCOUNT_CHARGE;
					boolean isOrigCurrCharge = isLastOrigCycle && isCharge && isOrigValuable && isNotSubAccCharge && !isOrigCycle;
					boolean isOrigAddCharge = detail.getOriginalItem() == null ? false : detail.getOriginalItem().getOperation().getType().getId() == OperationType.ADD_CHARGE;
					// -- end of TEST
					if (isOrigCurrCharge && !isOrigAddCharge && !isOrigCycleClosed) {
						currOrigChargeKwh += detail.getOriginalKwh();
					} else {
						appendIntoSummary(detail, summary, true, cycle.getDate(), facturaExpansion);
					}
					boolean isDrvdValuable = Math.abs(detail.getDerivedKwh()) >= CoreUtils.MIN_KWH && Math.abs(detail.getDerivedGel()) < CoreUtils.MIN_GEL;
					boolean isDrvdCycle = detail.getDerivedItem() == null ? false : detail.getDerivedItem().getCycle();
					boolean isDrvdCurrCharge = isLastDrvdCycle && isCharge && isDrvdValuable && isNotSubAccCharge && !isDrvdCycle;
					// test whether is calculated with zero tariff:
					// it should not be added in CURRENT charge
					if (isDrvdCurrCharge && detail.getDerivedItem() != null && detail.getDerivedItem().getCharge() != null && detail.getDerivedItem().getCharge().expansion() != null && !detail.getDerivedItem().getCharge().expansion().isEmpty()) {
						List expansion = detail.getDerivedItem().getCharge().expansion();
						boolean allAreWithZeroTariff = true;
						for (int k = 0; k < expansion.size(); k++) {
							ChargeElement element = (ChargeElement) expansion.get(k);
							if (element.getTariffId() != CoreUtils.ZERO_TARIFF_ID) {
								allAreWithZeroTariff = false;
								break;
							}
						}
						if (allAreWithZeroTariff)
							isDrvdCurrCharge = false;
					}
					// -- end of TEST
					if (isDrvdCurrCharge) {
						currDrvdChargeKwh += detail.getDerivedKwh();
					} else {
						appendIntoSummary(detail, summary, false, cycle.getDate(), facturaExpansion);
					}
				}
			}
		}
		/* Make current charge record. */
		double currentChargeKwh = currDrvdChargeKwh - currOrigChargeKwh;
		double currentChargeGel = 0.f;
		if (Math.abs(currentChargeKwh) >= CoreUtils.MIN_KWH) {
			DiffDetail newDetail = new DiffDetail();
			CalculationItem item = new CalculationItem();
			item.setBalance(0.f);
			Charge c = new Charge();
			ChargeElement element = createChargeElement(null, null, currentChargeKwh, currentChargeGel, -1, false, false);
			c.addElement(element);
			c.copyCharge(item.getCharge());
			Operation newOper = DbUtilities.findOperationById(Operation.CURRENT_CHARGE);
			item.setOperation(newOper);
			newDetail.setOriginalItem(item);
			summary.add(newDetail);
		}
		/* return results */
		return summary;
	}

	/**
	 * Appends given detail into the summary. You must also define which part of
	 * the detail: original or derived you would like to append into summary.
	 * 
	 * <p>
	 * There are some general rules how to divide charge on date. See
	 * documentation for futher discussion of this topic.
	 */
	private static void appendIntoSummary(DiffDetail detail, DiffSummary summary, boolean original, Date cycleMarkDate, List facturaExpansion) {
		/* get item to be devided */
		CalculationItem charge = original ? detail.getOriginalItem() : detail.getDerivedItem();

		/* empty item or charge: then nothing to do */
		if (charge == null || charge.getCharge().expansion().isEmpty())
			return;

		/*
		 * When new voucher items, then we should preserve them.
		 */
		if ("new_item".equals(charge.getItemNumber()) && charge.getOperation().getType().getId() == OperationType.VOUCHER) {
			if (charge.getProducer() != null && charge.getProducer().getItemId() == -1) {
				ChargeElement element = createChargeElement(null, null, charge.getCharge().getKwh(), charge.getCharge().getGel(), -1, false, false);
				appendIntoSummary(summary, element, charge.getOperation().getId(), facturaExpansion, charge, original, cycleMarkDate);
				return;
			}
		}

		/*
		 * Common situation.
		 */
		Operation oper = charge.getOperation();
		List dds = DivisionDateFactory.getDivisionDates();
		double kwh = 0.f;
		double gel = 0.f;
		for (int i = 0; i < charge.getCharge().expansion().size(); i++) {
			ChargeElement chargeElement = (ChargeElement) charge.getCharge().expansion().get(i);
			kwh += chargeElement.getKwh();
			gel += chargeElement.getGel();
			boolean isLastCharge = i == charge.getCharge().expansion().size() - 1;
			/* look up for appropriate division date */
			for (int j = 0; j < dds.size(); j++) {
				DivisionDateFactory.DivisionDate div = (DivisionDateFactory.DivisionDate) dds.get(j);
				Date date = div.getDate();
				boolean exactCut = div.getType() == IDivisionDate.EXACT;
				// @since december 24, 2009: see task #0712/322 (#1-15/7345)
				boolean isLongPeriod = chargeElement.getDistinction() > 60;
				exactCut = exactCut && !isLongPeriod;
				// >> @since march 2009: one time act should be cutted
				// explicitly
				boolean isOneTimeAct = charge.getOperation().getDiffCategory() == DiffDetail.ONE_TIME_ACT;
				exactCut = exactCut || isOneTimeAct;

				// << @since march 2009
				if ((Date.isLessOrEqual(cycleMarkDate, date)) || (exactCut && Date.isLessOrEqual(chargeElement.getEndDate(), date))) {
					if (!(isLastCharge || exactCut))
						break;
					int operId;
					if (original) { // for discharge
						kwh = -kwh;
						gel = -gel;
						operId = DivisionDateFactory.getDischargeId(oper, div);
						// if ("sys".equals(charge.getItemNumber())) {
						if (DivisionDateFactory.isRecharge(operId))
							operId = DivisionDateFactory.getCoupledDischarge(operId);
						// }
					} else { // for recharge
						operId = DivisionDateFactory.getRechargeId(oper, div);
						// if ("sys".equals(charge.getItemNumber())) {
						if (DivisionDateFactory.isDischarge(operId))
							operId = DivisionDateFactory.getCoupledRecharge(operId);
						// }
					}
					/*
					 * When doing exact cut, then rounding is not allowed. But
					 * it is neccessary in any other cases.
					 */
					if (!exactCut) {
						kwh = round(kwh, 2);
						gel = round(gel, 2);
					}
					ChargeElement element = createChargeElement(null, null, kwh, gel, -1, false, false);
					appendIntoSummary(summary, element, operId, facturaExpansion, charge, original, cycleMarkDate);
					kwh = 0;
					gel = 0;
					break;
				}
			}
		}
	}

	/**
	 * This appends charge element into given summary. This routine simply
	 * checks whether charge for the same operation already exists and add this
	 * charge to the existing detail. Otherwise a new detail will be created.
	 */
	private static void appendIntoSummary(DiffSummary summary, ChargeElement element, int operId, List facturaExpansion, CalculationItem charge, boolean original, Date cycleMarkDate) {
		// Standard Part
		boolean added = false;
		for (int k = 0; k < summary.getDetails().size(); k++) {
			DiffDetail det = (DiffDetail) summary.getDetails().get(k);
			if (det.getOperation().getId() == operId) {
				det.getOriginalItem().getCharge().addElement(element);
				added = true;
				break;
			}
		}
		if (!added) {
			Operation newOper = DbUtilities.findOperationById(operId);
			DiffDetail newDetail = new DiffDetail();
			CalculationItem item = new CalculationItem();
			Charge c = new Charge();
			c.addElement(element);
			c.copyCharge(item.getCharge());
			item.setOperation(newOper);
			newDetail.setOriginalItem(item);
			summary.add(newDetail);
		}

		// Factura Expansion Part
		if (facturaExpansion != null) {
			long itemId = -1;
			Date itemDate = cycleMarkDate;
			RecalcItem item = charge == null ? null : charge.getProducer();
			String itemNumber = charge == null ? null : charge.getItemNumber();
			if (charge != null) {
				if (charge.getItemNumber() == null) {
					itemNumber = "oper_" + String.valueOf(charge.getOperation().getId());
				} else {
					itemNumber = charge.getItemNumber();
				}
			} else {
				itemNumber = null;
			}
			boolean isPayment = false;
			if (item != null) {
				itemDate = item.getItemDate();
				itemId = item.getItemId();
				isPayment = item.getOperation().getType().getId() == OperationType.PAYMENT;
			}
			boolean isSysRecalculated = "sys".equals(itemNumber) && !original;
			if (!isSysRecalculated && !isPayment) {
				FacturaDetail fd = new FacturaDetail();
				fd.setGel(element.getGel());
				fd.setKwh(element.getKwh());
				fd.setItemDate(itemDate);
				fd.setOperation(DbUtilities.findOperationById(operId));
				fd.setOriginal(original);
				fd.setOriginalItemId(itemId);
				fd.setItemNumber(itemNumber);
				facturaExpansion.add(fd);
			}
		}
	}

	/**
	 * Created differences list for the given comparators.
	 */
	public static List makeDiffByCycle(List comparators) {
		List byCycle = new ArrayList();
		for (int i = 0; i < comparators.size(); i++) {
			Comparator comp = (Comparator) comparators.get(i);
			DiffSummary cycleDiff = makeCycleComparison(comp.original, comp.derived);
			if (cycleDiff != null && cycleDiff.getDetails() != null && !cycleDiff.getDetails().isEmpty()) {
				byCycle.add(cycleDiff);
			}
		}
		filterOtherOperations(byCycle);
		return byCycle;
	}

	/**
	 * makeCycleComparison is a good cycle-to-cycle compare tool. But when
	 * cycles are moved it's possible to find payments in different cycles. It
	 * looks like we should correct payment. It is not correct. We need futher
	 * filtering to find out such situations.
	 */
	private static void filterOtherOperations(List byCycle) {
		for (int i = 0; byCycle != null && i < byCycle.size(); i++) {
			DiffSummary dif1 = (DiffSummary) byCycle.get(i);
			for (int j = 0; j < byCycle.size(); j++) {
				if (i != j) {
					DiffSummary dif2 = (DiffSummary) byCycle.get(j);
					if (dif1 != null && dif2 != null && dif1.getDetails() != null && dif2.getDetails() != null && !dif1.getDetails().isEmpty() && !dif2.getDetails().isEmpty()) {
						filterOtherOperations2(dif1, dif2);
					}
				}
			}
		}
	}

	private static void filterOtherOperations2(DiffSummary dif1, DiffSummary dif2) {
		List remove1 = new ArrayList();
		List remove2 = new ArrayList();
		for (int i = 0; i < dif1.getDetails().size(); i++) {
			DiffDetail det1 = (DiffDetail) dif1.getDetails().get(i);
			if (det1.getOriginalItem() != null && looksLikeOther(det1.getOriginalItem().getOperation().getDiffCategory())
			// && Math.abs(det1.getOriginalItem().getCharge().getKwh()) >
					// CoreUtils.MIN_KWH
					&& Math.abs(det1.getOriginalItem().getCharge().getGel()) > CoreUtils.MIN_GEL) {
				int oper1 = det1.getOriginalItem().getOperation().getId();
				double kwh1 = det1.getOriginalItem().getCharge().getKwh();
				double gel1 = det1.getOriginalItem().getCharge().getGel();
				for (int j = 0; j < dif2.getDetails().size(); j++) {
					DiffDetail det2 = (DiffDetail) dif2.getDetails().get(j);
					if (det2.getDerivedItem() != null) {
						double kwh2 = det2.getDerivedItem().getCharge().getKwh();
						double gel2 = det2.getDerivedItem().getCharge().getGel();
						int oper2 = det2.getDerivedItem().getOperation().getId();
						if (oper1 == oper2 && Math.abs(kwh1 - kwh2) < CoreUtils.MIN_KWH && Math.abs(gel1 - gel2) < CoreUtils.MIN_GEL) {
							det1.setOriginalItem(null);
							if (det1.getDerivedItem() == null)
								remove1.add(det1);
							det2.setDerivedItem(null);
							if (det2.getOriginalItem() == null)
								remove2.add(det2);
							break;
						}
					}
				}
			}
		}
		if (!remove1.isEmpty())
			dif1.getDetails().removeAll(remove1);
		if (!remove2.isEmpty())
			dif2.getDetails().removeAll(remove2);
	}

	private static boolean looksLikeOther(int diffCategory) {
		switch (diffCategory) {
		case DiffDetail.BALANCE:
		case DiffDetail.COMPENSATION:
		case DiffDetail.PAYMENT:
		case DiffDetail.PENALTY:
		case DiffDetail.SERVICE:
		case DiffDetail.SUBSIDY_COMP_REFUGE:
		case DiffDetail.SUBSIDY_PENSION:
		case DiffDetail.SUBSIDY_TARIFF_2003:
		case DiffDetail.SUBSIDY_TARIFF_2006:
		case DiffDetail.SUBSIDY_USAID:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Compares new and derived cycles.
	 */
	private static DiffSummary makeCycleComparison(Cycle original, Cycle derived) {
		/* nothing to calculate */
		if (original == null && derived == null)
			return null;
		/* create initial summary object */
		Date markDate = original == null ? derived.markDate : original.markDate;
		DiffSummary summary = new DiffSummary();
		summary.setDate(markDate);
		List origItems = null;
		List drvdItems = null;
		if (original != null && original.items != null && !original.items.isEmpty()) {
			origItems = new ArrayList();
			origItems.addAll(original.items);
		}
		if (derived != null && derived.items != null && !derived.items.isEmpty()) {
			drvdItems = new ArrayList();
			drvdItems.addAll(derived.items);
		}
		/* clear lists before calculation, to remove any unnecessary operations */
		clearCycleList(origItems, true);
		clearCycleList(drvdItems, false);
		/* process original items */
		if (origItems != null) {
			while (!origItems.isEmpty()) {
				CalculationItem origItem = (CalculationItem) origItems.remove(0);
				CalculationItem drvdItem = null;
				if (drvdItems != null && !drvdItems.isEmpty()) {
					drvdItem = findAnalogy(origItem, drvdItems);
					if (drvdItem != null)
						drvdItems.remove(drvdItem);
				}
				// make detail
				DiffDetail detail = createDiffDetail(origItem, drvdItem);
				if (detail != null)
					summary.add(detail);
			}
		}
		/* process rest derived items */
		if (drvdItems != null) {
			while (!drvdItems.isEmpty()) {
				CalculationItem drvdItem = (CalculationItem) drvdItems.remove(0);
				DiffDetail detail = createDiffDetail(null, drvdItem);
				if (detail != null)
					summary.add(detail);
			}
		}

		/* DONE! */
		return summary;
	}

	/**
	 * Creates DiffDetail from two items.
	 */
	private static DiffDetail createDiffDetail(CalculationItem orig, CalculationItem drvd) {
		if (orig == null && drvd == null)
			return null;
		if (orig != null && drvd != null && areTheSame(orig, drvd))
			return null;
		DiffDetail detail = new DiffDetail();
		detail.setOriginalItem(orig);
		detail.setDerivedItem(drvd);
		return detail;
	}

	private static boolean areTheSame(CalculationItem item1, CalculationItem item2) {
		double kwh1 = item1 == null ? .0f : item1.getCharge().getKwh();
		double kwh2 = item2 == null ? .0f : item2.getCharge().getKwh();
		double gel1 = item1 == null ? .0f : item1.getCharge().getGel();
		double gel2 = item2 == null ? .0f : item2.getCharge().getGel();
		boolean sameKwh = Math.abs(kwh1 - kwh2) < CoreUtils.MIN_KWH;
		boolean sameGel = Math.abs(gel1 - gel2) < CoreUtils.MIN_GEL;
		int operId1 = item1.getOperation().getId();
		int operId2 = item2.getOperation().getId();

		/*
		 * When CHARGE <--> READING or ESTIMATE <--> ESTIMATE | WITHOUT_METER
		 * mixture.
		 */
		boolean preserve1 = item1.getProducer() != null && item1.getProducer().getCalculationHint() == RecalcItem.HINT_PRESERVE_BOTH;
		boolean preserve2 = item2.getProducer() != null && item2.getProducer().getCalculationHint() == RecalcItem.HINT_PRESERVE_BOTH;
		boolean isReading1 = isReading(operId1);
		boolean isReading2 = isReading(operId2);
		boolean isEstimate1 = operId1 == Operation.ESTIMATE;
		boolean isEstimate2 = operId2 == Operation.ESTIMATE;
		boolean isWM1 = operId1 == Operation.WITHOUT_METER || operId1 == Operation.NOT_OPERABLE_METER || operId1 == Operation.ESTIMATE;
		boolean isWM2 = operId2 == Operation.WITHOUT_METER || operId2 == Operation.NOT_OPERABLE_METER || operId1 == Operation.ESTIMATE;
		boolean forceDischarge1 = (!preserve1 && !preserve2 && ((isReading1 && !isReading2) || (isEstimate1 /* isWM1 */&& isWM2))) || (!preserve1 && !preserve2 && ((isReading2 && !isReading1) || (isWM1 && /* isWM2 */isEstimate2)));
		/*
		 * Forcing discharge.
		 */
		boolean forceDischarge2 = "force_discharge".equals(item1.getItemNumber()) || "force_discharge".equals(item2.getItemNumber());
		/* exact discharge definition in recalculation */
		boolean forceDischarge2_1 = (item1.getProducer() != null && item1.getProducer().getCalculationHint() == RecalcItem.HINT_FORCE_DISCHARGE) || (item2.getProducer() != null && item2.getProducer().getCalculationHint() == RecalcItem.HINT_FORCE_DISCHARGE);

		/*
		 * Accelerations.
		 */
		double acc1 = item1.getProducer() == null || item1.getProducer().getCalculationHint() == RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH || item1.getProducer().getCalculationHint() == RecalcItem.HINT_PRESERVE_BOTH || "not_for_discharge".equals(item1.getItemNumber()) ? 0 : item1.getProducer().getMeterAcceleration();

		double acc2 = item2.getProducer() == null || item2.getProducer().getCalculationHint() == RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH || item2.getProducer().getCalculationHint() == RecalcItem.HINT_PRESERVE_BOTH || "not_for_discharge".equals(item2.getItemNumber()) ? 0 : item2.getProducer().getMeterAcceleration();
		boolean forceDischarge3 = Math.abs(acc1) > 0.0099 && Math.abs(acc2) > 0.0099;

		// summary for discharge
		boolean forceDischarge = forceDischarge1 || forceDischarge2 || forceDischarge2_1 || forceDischarge3; // OR
		// others...

		return !forceDischarge && sameKwh && sameGel;
	}

	/**
	 * This method is used to find analogy of the given item
	 * 
	 * @param item
	 * @param items
	 * @return
	 */
	private static CalculationItem findAnalogy(CalculationItem item, List items) {
		if (items == null || items.isEmpty() || item == null)
			return null;
		CalculationItem analogy = null;

		boolean isCycle = item.getCycle();
		boolean isCharge = Calc2006Utils.isCharge(item.getOperation().getId());
		boolean hasZeroKwh = Math.abs(item.getCharge().getKwh()) < CoreUtils.MIN_KWH;
		boolean hasZeroGel = Math.abs(item.getCharge().getGel()) < CoreUtils.MIN_GEL;

		// #1. try to find directly: same operation, same date
		if (true) {
			CalculationItem tempAnalogy = null;
			for (int i = 0; i < items.size(); i++) {
				CalculationItem comp = (CalculationItem) items.get(i);
				boolean sameOperation = comp.getOperation().getId() == item.getOperation().getId();
				boolean sameDate = Date.isEqual(comp.getItemDate(), item.getItemDate());
				if (sameOperation && sameDate) {
					tempAnalogy = comp;
					if (isCharge && !hasZeroKwh && !hasZeroGel) {
						boolean tempHasZeroKwh = Math.abs(tempAnalogy.getCharge().getKwh()) < CoreUtils.MIN_KWH;
						boolean tempHasZeroGel = Math.abs(tempAnalogy.getCharge().getGel()) < CoreUtils.MIN_GEL;
						boolean nextIsCurrentCharge = i + 1 < items.size() && ((CalculationItem) items.get(i + 1)).getOperation().getId() == Operation.CURRENT_CHARGE;
						if (tempHasZeroKwh && tempHasZeroGel && nextIsCurrentCharge) {
							CalculationItem forRemove = tempAnalogy;
							tempAnalogy = (CalculationItem) items.get(i + 1);
							items.remove(forRemove);
						}
					}
					break;
				}
			}
			if (tempAnalogy != null)
				analogy = tempAnalogy;
		}

		// #2. try to find as a cycle charge: it should be only ONE!
		if (analogy == null) {

			CalculationItem tempAnalogy = null;
			if (isCycle && isCharge) {
				for (int i = 0; i < items.size(); i++) {
					CalculationItem comp = (CalculationItem) items.get(i);
					boolean cycle = comp.getCycle();
					boolean charge = Calc2006Utils.isCharge(comp.getOperation().getId());
					if (cycle && charge) {
						tempAnalogy = comp;
						boolean tempHasZeroKwh = Math.abs(tempAnalogy.getCharge().getKwh()) < CoreUtils.MIN_KWH;
						boolean tempHasZeroGel = Math.abs(tempAnalogy.getCharge().getGel()) < CoreUtils.MIN_GEL;
						boolean nextIsCurrentCharge = i + 1 < items.size() && ((CalculationItem) items.get(i + 1)).getOperation().getId() == Operation.CURRENT_CHARGE;
						if (tempHasZeroKwh && tempHasZeroGel && nextIsCurrentCharge && !hasZeroKwh && !hasZeroGel) {
							CalculationItem forRemove = tempAnalogy;
							tempAnalogy = (CalculationItem) items.get(i + 1);
							items.remove(forRemove);
						}
						break;
					}
				}
			}
			if (tempAnalogy != null)
				analogy = tempAnalogy;
		}
		/*
		 * 19-June-2007 ============
		 * 
		 * This condition was suppressed, while it produces bad results. Without
		 * it we managed to run all tests correctly. See test voucher 1525022/1.
		 */
		// // #3. The same operation is an only one in the list
		// if (analogy == null) {
		// int operId = item.getOperation().getId();
		// int count = 0;
		// CalculationItem tempAnalogy = null;
		// for (int i = 0; i < items.size(); i++) {
		// CalculationItem comp = (CalculationItem)items.get(i);
		// if (comp.getOperation().getId() == operId) {
		// tempAnalogy = comp;
		// count++;
		// }
		// if (count > 1) {
		// break;
		// }
		// }
		// if (count == 1) {
		// analogy = tempAnalogy;
		// }
		// }

		return analogy;
	}

	/**
	 * Clears list from unnecessary information. For example, when the cycle has
	 * summary charge, all other charges will be removed.
	 * 
	 * @param items
	 *            items to be cleared
	 */
	private static void clearCycleList(List items, boolean isOriginal) {
		/*
		 * When the list has SUMMARY charge, then remove any other charges.
		 */
		if (hasSummaryCharge(items)) {
			List forRemove = new ArrayList();
			for (int i = 0; i < items.size(); i++) {
				CalculationItem item = (CalculationItem) items.get(i);
				if (item.getOperation().getDiffCategory() == DiffDetail.CHARGE && item.getOperation().getId() != Operation.SUMMARY_CHARGE)
					forRemove.add(item);
			}
			items.removeAll(forRemove);
		}
		if (hasSummaryPercentSubsidyCharge(items)) {
			List forRemove = new ArrayList();
			for (int i = 0; i < items.size(); i++) {
				CalculationItem item = (CalculationItem) items.get(i);
				if (item.getOperation().getId() == Operation.SUMMARY_CHARGE && item.getOperation().getDiffCategory() == DiffDetail.SUBSIDY_PERCENT)
					forRemove.add(item);
			}
			items.removeAll(forRemove);
		}

	}

	/**
	 * Determines whether given items contain summary charge.
	 */
	private static boolean hasSummaryCharge(List items) {
		if (items != null) {
			for (int i = 0; i < items.size(); i++) {
				CalculationItem item = (CalculationItem) items.get(i);
				if (item.getOperation().getId() == Operation.SUMMARY_CHARGE)
					return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether given items contain summary percent charge.
	 */
	private static boolean hasSummaryPercentSubsidyCharge(List items) {
		if (items != null) {
			for (int i = 0; i < items.size(); i++) {
				CalculationItem item = (CalculationItem) items.get(i);
				if (item.getOperation().getId() == Operation.SUMMARY_PERCENT_SUBSIDY)
					return true;
			}
		}
		return false;
	}

	/**
	 * This procedure creates comparators.
	 */
	public static List createComparators(Recalc recalc, List calculated) {
		/* work out with original intervals */
		List origCycles = new ArrayList();
		List origDates = new ArrayList();
		List originalIntervals = toCalculationIntervals(recalc);
		createCycles(originalIntervals, origCycles, origDates);
		/* work out with calculated intervals */
		List derivedDates = new ArrayList();
		List derivedCycles = new ArrayList();
		createCycles(calculated, derivedCycles, derivedDates);
		/* now create comparators */
		List comparators = new ArrayList();
		int startIndex = 0;
		if (origDates != null) {
			for (int i = 0; i < origDates.size(); i++) {
				Date origDate = (Date) origDates.get(i);
				Cycle origCycle = (Cycle) origCycles.get(i);
				Cycle derivedCycle = null;
				for (int j = startIndex; j < derivedDates.size(); j++) {
					Date derivedDate = (Date) derivedDates.get(j);
					Cycle derivedCycle2 = (Cycle) derivedCycles.get(j);
					if (Date.isEqual(derivedDate, origDate)) {
						derivedCycle = derivedCycle2;
						startIndex++;
						break; // break is NECESSARY here!
					} else if (Date.isLess(derivedDate, origDate)) {
						Comparator comp = new Comparator();
						comp.derived = derivedCycle2;
						comp.original = null;
						comp.markDate = derivedDate;
						comparators.add(comp);
						startIndex++;
					} else {
						break;
					}
				}
				Comparator comp = new Comparator();
				comp.derived = derivedCycle;
				comp.original = origCycle;
				comp.markDate = origDate;
				comparators.add(comp);
			}
		}
		for (int j = startIndex; j < derivedDates.size(); j++) {
			Date derivedDate = (Date) derivedDates.get(j);
			Cycle derivedCycle = (Cycle) derivedCycles.get(j);
			Comparator comp = new Comparator();
			comp.derived = derivedCycle;
			comp.original = null;
			comp.markDate = derivedDate;
			comparators.add(comp);
		}

		/* DONE! */
		return comparators;
	}

	/**
	 * Fill cycles from intervals.
	 */
	private static void createCycles(List intervals, List cycles, List dates) {
		List tmpCycles = new ArrayList();
		List tmpDates = new ArrayList();
		for (int i = 0; i < intervals.size(); i++) {
			CalculationInterval interval = (CalculationInterval) intervals.get(i);
			Date prevCycleDate = null;
			Date itemDate = null;
			Cycle prevCycle = new Cycle();
			for (int j = 0; j < interval.getItems().size(); j++) {
				CalculationItem item = (CalculationItem) interval.getItems().get(j);
				boolean isCycle = item.getCycle();
				itemDate = item.getItemDate();
				if (prevCycleDate == null) {
					if (isCycle)
						prevCycleDate = itemDate;
					prevCycle.items.add(item);
				} else if (prevCycleDate != null && isCycle && Date.isEqual(prevCycleDate, itemDate)) {
					prevCycle.items.add(item);
				} else if (prevCycleDate != null && (!isCycle || (isCycle && !Date.isEqual(prevCycleDate, itemDate)))) {
					if (!prevCycle.items.isEmpty()) {
						// prevCycle.markDate = prevCycleDate == null ? itemDate
						// : prevCycleDate;
						prevCycle.markDate = calcMarkDate(prevCycle);
						prevCycle.interval = interval.getName();
						tmpDates.add(prevCycle.markDate);
						tmpCycles.add(prevCycle);
					}
					prevCycle = new Cycle();
					prevCycleDate = isCycle ? itemDate : null;
					prevCycle.items.add(item);
				}
			}
			if (!prevCycle.items.isEmpty()) {
				// prevCycle.markDate = prevCycleDate == null ? itemDate :
				// prevCycleDate;
				prevCycle.markDate = calcMarkDate(prevCycle);
				prevCycle.interval = interval.getName();
				tmpDates.add(prevCycle.markDate);
				tmpCycles.add(prevCycle);
			}
		}

		/*
		 * We need to merge those cycles which do not have any cycle charge
		 * items: this simply means that are not real cycles.
		 */
		for (int i = 0; i < tmpCycles.size(); i++) {
			Cycle c = (Cycle) tmpCycles.get(i);
			if (!containsCharge(c)) {
				List items = new ArrayList();
				Cycle merged = new Cycle();
				items.addAll(c.items);

				boolean isChangeOfInterval = false;
				if (i < tmpCycles.size() - 1) {
					Cycle nextCycle = (Cycle) tmpCycles.get(i + 1);
					if (!nextCycle.interval.equals(c.interval))
						isChangeOfInterval = true;
				}

				if (!isChangeOfInterval) {
					i = i + 1; // go to the next step!
					for (; i < tmpCycles.size(); i++) {
						Cycle c2 = (Cycle) tmpCycles.get(i);
						items.addAll(c2.items);
						if (containsCharge(c2))
							break;
					}
				}
				merged.items = items;
				merged.markDate = calcMarkDate(merged);
				cycles.add(merged);
				dates.add(merged.markDate);
			} else {
				cycles.add(c);
				dates.add(tmpDates.get(i));
			}
		}
	}

	/**
	 * Determines whether given cycle contains any charge (as cycle item).
	 */
	private static boolean containsCharge(Cycle cycle) {
		for (int i = 0; i < cycle.items.size(); i++) {
			CalculationItem item = (CalculationItem) cycle.items.get(i);
			if (item.getCycle() && (item.getOperation().getDiffCategory() == DiffDetail.CHARGE
			// isCharge(item.getOperation().getId())
					// || item.getOperation().getType().getId() ==
					// OperationType.ADD_CHARGE
					// || item.getOperation().getType().getId() ==
					// OperationType.SUBSIDY
					)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Define cycle mark date. Cycle last mark date is a date of last charge; or
	 * last itemdate, if no charge can be found.
	 * 
	 * This is good for charges.
	 * 
	 * XXX But for payments for e.g. this may be not correct. At the moment I'm
	 * not sure how to be.
	 */
	private static Date calcMarkDate(Cycle cycle) {
		Date markDate = null;
		for (int i = cycle.items.size() - 1; i >= 0; i--) {
			CalculationItem item = (CalculationItem) cycle.items.get(i);
			if (isCharge(item.getOperation().getId())) {
				markDate = item.getItemDate();
				break;
			}
		}
		if (markDate == null)
			markDate = ((CalculationItem) cycle.items.get(cycle.items.size() - 1)).getItemDate();
		return markDate;
	}

	/**
	 * This class is used to describe cycle.
	 */
	static class Cycle {
		Date markDate;
		List items = new ArrayList();
		String interval;
	}

	/**
	 * Cycles comparator.
	 */
	static class Comparator {
		Date markDate;
		Cycle original;
		Cycle derived;
	}

	/**
	 * This procedure tries to make estimate picture of initial recalculation
	 * history. It is a complex task, and I think it can not be archived well
	 * enough. But we are trying simply to give the best approach.
	 * 
	 * @param recalc
	 *            recalculation we are interested with
	 * @return calculation intervals
	 */
	public static List toCalculationIntervals(Recalc recalc) {
		List result = new ArrayList();
		for (int i = 0; i < recalc.getDetails().getIntervals().size(); i++) {
			RecalcInterval interval = (RecalcInterval) recalc.getDetails().getIntervals().get(i);
			/* we do not need to consider not-editable intervals */
			if (!interval.isEditable())
				continue;
			CalculationInterval calcInterval = toCalculationInterval(interval);
			if (!calcInterval.getItems().isEmpty())
				result.add(calcInterval);
		}
		return result;
	}

	/**
	 * This procedure is used to estimate calculation interval from the original
	 * one.
	 */
	private static CalculationInterval toCalculationInterval(RecalcInterval interval) {
		CalculationInterval calcInterval = new CalculationInterval();
		calcInterval.setName(interval.getName());
		int accId = interval.getDetails().getRecalc().getAccount().getId();
		int chargeCount = 0;
		for (int i = 0; i < interval.getItems().size(); i++) {
			RecalcItem item = (RecalcItem) interval.getItems().get(i);
			/* another account */
			if (item.getAccount().getId() != accId)
				continue;
			/*
			 * Newly created items, but not NEW_RESTRUCTS!
			 */
			if (item.getItemId() == -1 && !(item.getItemNumber() != null && item.getItemNumber().equals("new_restruct")))
				continue;
			/*
			 * Count charges.
			 */
			if (isCharge(item.getOriginalOperation().getId()))
				chargeCount++;
			/*
			 * All system vouchers (and current charges) should be cutted off
			 * for the first cycle.
			 */
			if (chargeCount <= 1 && ((item.getItemNumber() != null && item.getItemNumber().startsWith("sys")) || item.getOriginalOperation().getId() == Operation.CURRENT_CHARGE))
				continue;
			/* convert and add */
			calcInterval.getItems().add(toCalculationItem(item));
		}
		return calcInterval;
	}

	/**
	 * The main procedure in recalculation --> to calculation intervals
	 * conversion chain. Here we are trying to convert all charges to what they
	 * might be.
	 * 
	 * @param item
	 *            item to be converted
	 * @return calculation item
	 */
	private static CalculationItem toCalculationItem(RecalcItem item) {
		/* make initial stub */
		CalculationItem calcItem = createDerivedItem(item, true);
		List tariffs = item.getInterval().getDetails().getTariffs();
		/* original values */
		Operation operation = DbUtilities.findOperationById(item.getOriginalOperation().getId());
		int operId = operation.getId();
		int operTypeId = operation.getType().getId();
		calcItem.setOperation(operation);
		/* make classification */
		boolean inReadingGroup = operTypeId == OperationType.READING;
		boolean isSummary = operId == Operation.SUMMARY_CHARGE || operId == Operation.SUMMARY_PERCENT_SUBSIDY;
		boolean inChargeGroup = operTypeId == OperationType.CHARGE;
		boolean isAddOrSubsidy = operTypeId == OperationType.ADD_CHARGE || operTypeId == OperationType.SUBSIDY;
		boolean isSubaccCharge = operTypeId == OperationType.SUBACC_CHARGE;
		/*
		 * Reading and charge, but not summary
		 */
		if ((inChargeGroup || inReadingGroup) && !isSummary) {
			/* look up for the previous reading date */
			Date d1 = getPreviousReadingDate(item, false, false);
			/* current operation's date */
			Date d2 = item.getOriginalItemDate();
			/* adjust dates */
			if (Date.isLess(d2, d1))
				d1 = d2;
			/* when estimate then this is maximum 60 days period!!! */
			if (inChargeGroup && Date.getIntervalInDays(d2, d1) > INSTCP_USING_MAX_VALIDITY_PERIOD) {
				d1 = Date.addDays(d2, -INSTCP_USING_MAX_VALIDITY_PERIOD);
			}
			double gel = item.getOriginalGel();
			double kwh = item.getOriginalKwh();
			ChargeElement elmnt = createChargeElement(d1, d2, kwh, gel, -1, false, false);
			calcItem.getCharge().addElement(elmnt);
			splitOnVATChange(calcItem.getCharge(), tariffs);
		}
		/*
		 * Subsidy or add charge
		 */
		else if (isAddOrSubsidy) {
			/* current operation's date */
			Date d2 = item.getOriginalItemDate();
			/* look up for the previous reading date */
			int unit = item.getOperation().getSubsidyAttachment() == null ? -1 : item.getOperation().getSubsidyAttachment().getUnits()[0];
			boolean tryToUseInterval = prefferIntervalBasedCharge(d2);
			switch (unit) {
			case SubsidyAttachment.GEL:
			case SubsidyAttachment.TARIFF:
			case -1: // not defined unit
				tryToUseInterval = false;
				break;
			}
			Date d1;
			if (tryToUseInterval)
				d1 = getPreviousReadingDate(item, false, true);
			else
				d1 = d2;
			/* adjust dates */
			if (Date.isLess(d2, d1))
				d1 = d2;
			double gel = item.getOriginalGel();
			double kwh = item.getOriginalKwh();
			ChargeElement elmnt = createChargeElement(d1, d2, kwh, gel, -1, false, false);
			calcItem.getCharge().addElement(elmnt);
			splitOnVATChange(calcItem.getCharge(), tariffs);
		}
		/*
		 * Charge and subsidy summaries
		 */
		else if (isSummary) {
			/* look up for the previous cycle reading date */
			// XXX: 18-Feb-2013: rusos ert-erti vaucheri ar ketdeboda, amitom
			// sevcvalet
			// Date d1 = getPreviousReadingDate(item, true, false);
			Date d1 = getPreviousReadingDate(item, true, true);
			/* current operation's date */
			Date d2 = item.getOriginalItemDate();
			/* adjust dates */
			if (Date.isLess(d2, d1))
				d1 = d2;
			/* make charge element */
			double gel = item.getOriginalGel();
			double kwh = item.getOriginalKwh();
			ChargeElement elmnt = createChargeElement(d1, d2, kwh, gel, -1, false, false);
			calcItem.getCharge().addElement(elmnt);
		}
		/*
		 * Subacc charge
		 */
		else if (isSubaccCharge) {
			Date d1 = item.getPreviousOperationDate();
			Date d2 = item.getOriginalItemDate();
			if (Date.isLess(d2, d1))
				d1 = d2;
			double gel = item.getOriginalGel();
			double kwh = item.getOriginalKwh();
			ChargeElement elmnt = createChargeElement(d1, d2, kwh, gel, -1, false, false);
			calcItem.getCharge().addElement(elmnt);
			splitOnVATChange(calcItem.getCharge(), tariffs);
		}
		/*
		 * All other charges.
		 */
		else {
			Date d = item.getOriginalItemDate();
			double gel = item.getOriginalGel();
			double kwh = item.getOriginalKwh();
			ChargeElement elmnt = createChargeElement(d, d, kwh, gel, -1, false, false);
			calcItem.getCharge().addElement(elmnt);
		}
		/* return result */
		return calcItem;
	}

	/**
	 * Returns whether given tariff id is with VAT. This list need to be
	 * periodically updated.
	 * 
	 * @param tariffId
	 *            tariff id
	 * @return is tariff with VAT?
	 */
	public static boolean isVatTariff(int tariffId) {
		switch (tariffId) {
		case 6:
		case 7:
		case 16:
		case 17:
		case 21:
		case 22:
		case 26:
		case 27:
		case 31:
		case 32:
		case 50:
		case 57:
		case 58:
		case 59:
		case 60:
		case 75:
		case 81:
		case 83:
		case 87:
		case 101:
		case 201:
		case 301:
			return true;
		default:
			return true;
		}
	}

	/**
	 * Splits charge on VAT change period(s). Actually VAT change is the only
	 * period where we need precise spliting. In other cases we simply make
	 * cycle summary.
	 * 
	 * @param charge
	 *            charge to be splitted if neccessary
	 * @param tariffs
	 *            tariffs for the charge, if is supposed to be derived from
	 */
	public static void splitOnVATChange(Charge charge, List tariffs) {
		Charge newCharge = new Charge();
		boolean splitted = false;
		for (int i = 0; i < charge.expansion().size(); i++) {
			ChargeElement element = (ChargeElement) charge.expansion().get(i);
			Date d1 = element.getStartDate();
			Date d2 = element.getEndDate();
			/* make split */
			if (Date.isGreater(VAT_CHANGE_DATE, d1) && Date.isLessOrEqual(VAT_CHANGE_DATE, d2)) {
				RecalcTariffItem tariff = null;
				for (int j = 0; j < tariffs.size(); j++) {
					RecalcTariffItem tar = (RecalcTariffItem) tariffs.get(j);
					if (Date.isLess(tar.getStartDate(), VAT_CHANGE_DATE))
						tariff = tar;
					else if (Date.isGreaterOrEqual(tar.getStartDate(), VAT_CHANGE_DATE)) {
						tariff = tar;
						break;
					} else
						break;
				}
				if (tariff == null) {
					newCharge.addElement(element);
					continue;
				}
				double kwh = element.getKwh();
				double gel = element.getGel();
				if (Math.abs(kwh) < 2 * MIN_KWH) {
					newCharge.addElement(element);
					continue;
				}
				double tar2 = tariff.getTariff().calculate(1, VAT_CHANGE_DATE, VAT_CHANGE_DATE.nextDay());
				double tar1 = 1.016949152542372881f * tar2;/*
															 * 18% -> 20% i.e.
															 * 1.2/1.18
															 */
				int dist = Date.getIntervalInDays(d2, d1);
				int dist1 = Date.getIntervalInDays(VAT_CHANGE_DATE, d1) - 1;
				double kwh1 = dist1 * kwh / dist;
				double kwh2 = kwh - kwh1;
				double gel1 = gel * kwh1 * tar1 / (kwh1 * tar1 + kwh2 * tar2);
				double gel2 = gel - gel1;
				ChargeElement c1 = createChargeElement(d1, VAT_CHANGE_DATE.prevDay(), kwh1, gel1, -1, false, false);
				ChargeElement c2 = createChargeElement(VAT_CHANGE_DATE, d2, kwh2, gel2, -1, false, false);
				newCharge.addElement(c1);
				newCharge.addElement(c2);
				splitted = true;
			}
			/* no split required at this point */
			else
				newCharge.addElement(element);
		}
		/* copy new charge into inputted */
		if (splitted)
			newCharge.copyCharge(charge);
	}

	private static final Date VAT_CHANGE_DATE = new Date(2005, 7, 1);

	/**
	 * This methods looks up for the previous operation date for the given item
	 * based on the original history, not the changed one.
	 * 
	 * @param item
	 * @param lookCycle
	 * @return previous operation date
	 */
	public static Date getPreviousReadingDate(RecalcItem item, boolean lookCycle, boolean ignoreFromTheSameDate) {
		RecalcInterval interval = item.getInterval();
		int intervalIndex = interval.getDetails().getIntervals().indexOf(interval);
		int accId = item.getAccount().getId();
		boolean first = true;
		for (int j = intervalIndex; j >= 0; j--) {
			RecalcInterval prevInterval = (RecalcInterval) interval.getDetails().getIntervals().get(j);
			int startIndex = prevInterval.getItems().size() - 1;
			if (first) {
				startIndex = prevInterval.getItems().indexOf(item) - 1;
			}
			for (int k = startIndex; k >= 0; k--) {
				RecalcItem prevItem = (RecalcItem) prevInterval.getItems().get(k);
				/* not the same account */
				if (prevItem.getAccount().getId() != accId)
					continue;
				/*
				 * new items should not be considered whenlooking at the
				 * original history
				 */
				if (prevItem.getItemId() == -1)
					continue;
				/* whether is a cycle */
				if (lookCycle && !prevItem.getOriginalCycle())
					continue;
				/* ignore items of the same date */
				if (ignoreFromTheSameDate && Date.isEqual(item.getOriginalItemDate(), prevItem.getOriginalItemDate()))
					continue;
				/* this is a previous charge */
				if (isCharge(prevItem.getOriginalOperation().getId()))
					return prevItem.getOriginalItemDate();
			}
			first = false;
		}
		return item.getAccount().getCreationDate();
	}
}
