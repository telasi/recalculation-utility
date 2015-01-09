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
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.calc.NotThrowable;
import telasi.recutil.utils.CoreUtils;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Dec, 2006
 */
public class Validator {

	private KernelConfiguration config = new KernelConfiguration();

	public List validate(Recalc recalc) {

		if (recalc == null || recalc.getDetails() == null) {
			throw new NullPointerException();
		}

		// create full list of problems
		List list = new ArrayList();

		// Tariff history validation
		List tariffHistory = recalc.getDetails().getTariffs();
		List tarErrors = validateIntervals(tariffHistory);
		if (tarErrors != null) {
			list.addAll(tarErrors);
		}

		// installed capacity history
		List instcpHistory = recalc.getDetails().getInstCpItems();
		List instcpErrors = validateIntervals(instcpHistory);
		if (instcpErrors != null) {
			list.addAll(instcpErrors);
		}

		// cut history validation
		List cutHistory = recalc.getDetails().getCuts();
		List cutErrors = validateIntervals(cutHistory);
		if (cutErrors != null) {
			list.addAll(cutErrors);
		}

		// check charge intervals
		List charges = recalc.getDetails().getIntervals();
		List chargeErrors = validateChargeIntervals(charges);
		if (chargeErrors != null && !chargeErrors.isEmpty()) {
			list.addAll(chargeErrors);
		}

		// TODO: additional validations???

		// return results
		return list;

	}

	private List validateIntervals(List intervals) {
		if (intervals == null || intervals.isEmpty()) {
			return null;
		}
		List list = new ArrayList();
		IInterval prevInterval = null;

		// for (Object element : intervals)
		for (int i = 0; i < intervals.size(); i++) {

			// get interval
			Object element = intervals.get(i);
			if (!(element instanceof IInterval)) {
				throw new IllegalArgumentException();
			}
			IInterval interval = (IInterval) element;

			// get interval dates
			Date d1 = interval.getStartDate();
			Date d2 = interval.getEndDate();

			// compare interval dates
			if (d1 != null && d2 != null && Date.isGreater(d1, d2)) {
				String msgId = "validator.interval.start_greater_than_end";
				String sd1 = d1 == null ? "-[inf.]" : d1.toString();
				String sd2 = d2 == null ? "+[inf.]" : d2.toString();
				String msg = CalcMessage.getMessage(msgId, new Object[] { sd1,
						sd2 });
				NotThrowable t = new NotThrowable(interval, NotThrowable.ERROR,
						msg);
				list.add(t);
			}
			// else if (Date.isEqual(d1, d2)) {
			// String msgId = "validator6.interval.start_equals_end";
			// String msg = Messages.getMessage(msgId, d1, d2);
			// NotThrowable t = new NotThrowable(interval, NotThrowable.WARNING,
			// msg);
			// list.add(t);
			// }

			// compare with previous interval
			if (prevInterval != null) {
				Date prev_d2 = prevInterval.getEndDate();
				if (prev_d2 != null) {
					if (d1 == null || Date.isGreaterOrEqual(prev_d2, d1)) {
						String msgId = "validator.interval.prev_interval_intersection";
						String sd1 = d1 == null ? "-[inf.]" : d1.toString();
						String sd2 = prev_d2 == null ? "+[inf.]" : prev_d2
								.toString();
						String msg = CalcMessage.getMessage(msgId,
								new Object[] { sd2, sd1 });
						NotThrowable t = new NotThrowable(interval,
								NotThrowable.ERROR, msg);
						list.add(t);
					}
				}
			}

			// initialize previous interval
			prevInterval = interval;
		}

		return list;
	}

	private List validateChargeIntervals(List intervals) {

		if (intervals == null || intervals.isEmpty()) {
			return null;
		}

		// initialize errors
		List list = new ArrayList();

		// TODO: interval problems?????
		// List intervalErrors = validateIntervals(intervals);
		// if (intervalErrors != null && !intervalErrors.isEmpty()) {
		// list.addAll(intervalErrors);
		// }

		// check each interval
		for (int i = 0; i < intervals.size(); i++) {
			RecalcInterval interval = (RecalcInterval) intervals.get(i);
			if (interval.isEditable()) {
				List itemErrors = validateItems(interval.getItems());
				if (itemErrors != null && !itemErrors.isEmpty()) {
					list.addAll(itemErrors);
				}
			}
		}

		// return results
		return list;
	}

	private List validateItems(List items) {

		if (items == null || items.isEmpty()) {
			return null;
		}

		List list = new ArrayList();

		// general validation
		List generalErrors = validateItem(items);
		if (generalErrors != null && !generalErrors.isEmpty()) {
			list.addAll(generalErrors);
		}

		// return results
		return list;

	}

	private List validateItem(List items) {

		if (items == null || items.isEmpty()) {
			return null;
		}

		// filter items
		List filtered = new ArrayList();
		for (int i = 0; i < items.size(); i++) {
			RecalcItem item = (RecalcItem) items.get(i);
			int status = item.getStatus();
			boolean statusOk = status == RecalcItem.NEW || status == RecalcItem.ORIGINAL;
			boolean itemNumberOk = !"new_restruct".equals(item.getItemNumber());
			if (statusOk && itemNumberOk) {
				filtered.add(item);
			}
		}
		if (filtered == null || filtered.isEmpty()) {
			return null;
		}

		// problem list initialization
		List list = new ArrayList();

		// previous charge item
		RecalcItem prevCharge = null;
		RecalcItem prevCycle = null;

		for (int i = 0; i < filtered.size(); i++) {

			RecalcItem item = (RecalcItem) filtered.get(i);

//			switch (item.getOperation().getType().getId()) {
//			case OperationType.PAYMENT:
//			case OperationType.AUDIT_READING:
//				break;
//			default:
//				// #1. Enterdate > Itemdate
//				if (config.requierEnterDateBeGreateThanItemDate()) {
//					if (Date.isLess(item.getEnterDate(), item.getItemDate())) {
//						String msgId = "validator.items.enterdate_is_earlier";
//						String msg = CalcMessage.getMessage(msgId,
//								new Object[] { item.getItemDate(),
//										item.getEnterDate() });
//						NotThrowable nt = new NotThrowable(item,
//								NotThrowable.ERROR, msg);
//						list.add(nt);
//					}
//				}
//
//				// #2. Distinction between Itemdate/Enterdate
//				if (config.checkEnterDateAndItemdateDistinction()) {
//					int delta = Math.abs(Date.getIntervalInDays(item
//							.getItemDate(), item.getEnterDate()));
//					int allowed = config
//							.getAllowedDistinctionBetweenItemdateAndEnterdate();
//					int maximal = config
//							.getMaxAllowedDistinctionBetweenItemdateAndEnterdate();
//					if (delta > maximal) {
//						String msgId = "validator.items.enterdate_itemdate_distinction_over_max";
//						String msg = CalcMessage.getMessage(msgId,
//								new Object[] { item.getItemDate(),
//										item.getEnterDate(),
//										new Integer(maximal) });
//						NotThrowable nt = new NotThrowable(item,
//								NotThrowable.ERROR, msg);
//						list.add(nt);
//					} else if (delta > allowed) {
//						String msgId = "validator.items.enterdate_itemdate_distinction_over_allowed";
//						String msg = CalcMessage.getMessage(msgId,
//								new Object[] { item.getItemDate(),
//										item.getEnterDate(),
//										new Integer(allowed) });
//						NotThrowable nt = new NotThrowable(item,
//								NotThrowable.WARNING, msg);
//						list.add(nt);
//					}
//				}
//			}

			// #3. R, kWh, GEL, Cycle requiments
			int req_cycle = item.getOperation().getCycleRequiment();
			int req_read = item.getOperation().getReadingRequiment();
			int req_kwh = item.getOperation().getKwhRequiment();
			int req_gel = item.getOperation().getGelRequiment();

			// 3.1 Cycle
			if (req_cycle == Operation.REQUIERED && !item.getCycle()) {
				String msgId = "validator.items.cycle_requiered";
				String msg = CalcMessage.getMessage(msgId);
				NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR,
						msg);
				list.add(nt);
			}

			if (req_cycle == Operation.NOT_REQUIERED_AT_ALL && item.getCycle()) {
				String msgId = "validator.items.cycle_not_requiered";
				String msg = CalcMessage.getMessage(msgId);
				NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR,
						msg);
				list.add(nt);
			}

			// 3.2 Reading
			if (req_read == Operation.REQUIERED
					&& Math.abs(item.getReading()*item.getMeterCoeff()) < CoreUtils.MIN_KWH) {
				String msgId = "validator.items.reading_requiered";
				String msg = CalcMessage.getMessage(msgId);
				NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR,
						msg);
				list.add(nt);
			}

			if (req_read == Operation.NOT_REQUIERED_AT_ALL
					&& Math.abs(item.getReading()) >= CoreUtils.MIN_KWH) {
				String msgId = "validator.items.reading_not_requiered";
				String msg = CalcMessage.getMessage(msgId);
				NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR,
						msg);
				list.add(nt);
			}

			// 3.3 kWh
			if (req_kwh == Operation.REQUIERED
					&& Math.abs(item.getKwh()) < CoreUtils.MIN_KWH) {
				String msgId = "validator.items.kwh_requiered";
				String msg = CalcMessage.getMessage(msgId);
				NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR,
						msg);
				list.add(nt);
			}

			if (req_kwh == Operation.NOT_REQUIERED_AT_ALL
					&& Math.abs(item.getKwh()) >= CoreUtils.MIN_KWH) {
				String msgId = "validator.items.kwh_not_requiered";
				String msg = CalcMessage.getMessage(msgId);
				NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR,
						msg);
				list.add(nt);
			}

			// 3.4 GEL
			if (req_gel == Operation.REQUIERED
					&& Math.abs(item.getGel()) < CoreUtils.MIN_GEL) {
				String msgId = "validator.items.gel_requiered";
				String msg = CalcMessage.getMessage(msgId);
				NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR,
						msg);
				list.add(nt);
			}

			if (req_gel == Operation.NOT_REQUIERED_AT_ALL
					&& Math.abs(item.getGel()) >= CoreUtils.MIN_GEL) {
				String msgId = "validator.items.gel_not_requiered";
				String msg = CalcMessage.getMessage(msgId);
				NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR,
						msg);
				list.add(nt);
			}

			// // #4. First charge should have previous operation date defined
			// if (Calc2006Utils.isCharge(item.getOperation().getId()) &&
			// prevCharge == null) {
			// }

			// #4. compare with previous charge
			if (Calc2006Utils.isCharge(item.getOperation().getId())) {
				if (prevCharge != null) {
					Date d1 = prevCharge.getItemDate();
					Date d2 = item.getItemDate();
					if (Date.isGreater(d1, d2)) {
						String msgId = "validator.items.prev_itemdate_is_greater";
						String msg = CalcMessage.getMessage(msgId,
								new Object[] { d1, d2 });
						NotThrowable nt = new NotThrowable(item,
								NotThrowable.ERROR, msg);
						list.add(nt);
					}
				} else { // this is a first charge operation
					// if (item.getPreviousOperationDate() == null) {
					// String msgId =
					// "validator.items.prevoperdate_should_be_defined_for_thefirst_charge";
					// String msg = CalcMessage.getMessage(msgId);
					// NotThrowable nt = new NotThrowable(item,
					// NotThrowable.WARNING, msg);
					// list.add(nt);
					// }
				}
			}

			// validate that SUBACCOUNT charge has PREVIOUS DATE
			if (item.getOperation().getId() == Operation.SUB_ACCOUNT_CHARGE
					&& item.getPreviousOperationDate() == null) {
				String msgId = "validator.items.prevoperdate_should_be_defined_for_subaccount_charge";
				String msg = CalcMessage.getMessage(msgId);
				NotThrowable nt = new NotThrowable(item, NotThrowable.WARNING,
						msg);
				list.add(nt);
			}

			// #6. first charge kWh < 0
			// if (prevCharge == null &&
			// Calc2006Utils.isCharge(item.getOperation().getId()) &&
			// item.getKwh() < 0.f) {
			// String msgId = "validator.items.first_charge_with_negative_kwh";
			// String msg = CalcMessage.getMessage(msgId);
			// NotThrowable nt = new NotThrowable(item, NotThrowable.WARNING,
			// msg);
			// list.add(nt);
			// }

			// #6. first date is too small
			if (Date.isLessOrEqual(item.getItemDate(), config.getMinimalItemDate())) {
				String msgId = "validator.items.too_early_itemdate";
				String msg = CalcMessage.getMessage(msgId, new Object[] {
						item.getItemDate(), config.getMinimalItemDate() });
				NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR, msg);
				list.add(nt);
			}

			
			// #7. previous cycle, if the same date should be in the neibourhood
			if ( 	prevCycle != null
					&& item.getCycle()
					&& Date .isEqual(prevCycle.getItemDate(), item.getItemDate())) {
				int index1 = filtered.indexOf(prevCycle);
				int index2 = filtered.indexOf(item);
				if (index2 - index1 != 1) {
					String msgId = "validator.items.devided_cycle";
					String msg = CalcMessage.getMessage(msgId);
					NotThrowable nt = new NotThrowable(item, NotThrowable.ERROR, msg);
					list.add(nt);
				}
			}

			// TODO: operation allowed dates validation

			if (Calc2006Utils.isCharge(item.getOperation().getId()))
				prevCharge = item;
			if (item.getCycle())
				prevCycle = item;
		}

		return list;

	}

}
