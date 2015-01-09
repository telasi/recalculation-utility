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

import telasi.recutil.beans.CalculationInterval;
import telasi.recutil.beans.CalculationItem;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.FacturaDetail;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.RecalcRegularItem;
import telasi.recutil.beans.RecalcTariffItem;
import telasi.recutil.calc.DivisionDateFactory;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.E1RecalcManager;
import telasi.recutil.utils.CoreUtils;

/**
 * This class is used for precise factura expansion.
 * 
 * @author dimitri
 */
public class FacturaUtils {

	/**
	 * This method is used for normalization of factura. The method should be
	 * called at the end, when all factura expansions are already gathered. It
	 * includes as normalization also clean up of not-valueable facturas. Under
	 * normalization here we mean matching of the ItemID to those facturas which
	 * remain unmatched at calculation time. The matching procedure is not a
	 * strict one and some factura details may remain unmatched even after this
	 * procedure.
	 */
	public static List normalizeFactura(Session session, List factura, Recalc recalc) {
		List newFactura = new ArrayList();
		List factura0 = removeOneTimeActMatchings(factura);
		List factura1 = removeSysVouchersMatchings(factura0);
		List factura2 = expandExistingSysVouchers(session, factura1, recalc);
		for (int i = 0; i < factura2.size(); i++) {
			FacturaDetail detail = (FacturaDetail) factura2.get(i);
			boolean valuable = Math.abs(detail.getGel()) >= CoreUtils.MIN_GEL || Math.abs(detail.getKwh()) >= CoreUtils.MIN_KWH;
			if (valuable) {
				long itemId = detail.getOriginalItemId();
				Date date = detail.getItemDate();
				if (itemId < -1) {
					itemId = -1;
					for (int j = 0; j < recalc.getDetails().getIntervals().size(); j++) {
						RecalcInterval interval = (RecalcInterval) recalc.getDetails().getIntervals().get(j);
						for (int k = 0; k < interval.getItems().size(); k++) {
							RecalcItem item = (RecalcItem) interval.getItems().get(k);
							if (Date.isEqual(date, item.getItemDate()) && Utilities.isCharge(item.getOperation().getId()) && item.getItemId() > 0) {
								itemId = item.getItemId();
								break;
							}
						}
						if (itemId != -1)
							break;
					}
				}
				if (itemId == -1) {
					for (int j = recalc.getDetails().getIntervals().size() - 1; j >= 0; j--) {
						RecalcInterval interval = (RecalcInterval) recalc.getDetails().getIntervals().get(j);
						for (int k = interval.getItems().size() - 1; k >= 0; k--) {
							RecalcItem item = (RecalcItem) interval.getItems().get(k);
							if (Date.isLessOrEqual(item.getItemDate(), date) && Utilities.isCharge(item.getOperation().getId()) && item.getItemId() > 0) {
								itemId = item.getItemId();
								break;
							}
						}
						if (itemId != -1)
							break;
					}
				}
				detail.setOriginalItemId(itemId);
				newFactura.add(detail);
			}
		}
		return newFactura;
	}

	/**
	 * When recalculating SYS vouchers many matching items are left. We need to
	 * clear them.
	 */
	private static List removeSysVouchersMatchings(List factura) {
		List newFactura = new ArrayList();
		newFactura.addAll(factura);

		List forRemove = new ArrayList();
		for (int i = 0; i < newFactura.size(); i++) {
			FacturaDetail det = (FacturaDetail) newFactura.get(i);
			if ("force_discharge".equals(det.getItemNumber())) {
				for (int j = 0; j < newFactura.size(); j++) {
					FacturaDetail det2 = (FacturaDetail) newFactura.get(j);
					if ("estimate_correction_item".equals(det2.getItemNumber()) && Date.isEqual(det.getItemDate(), det2.getItemDate()) && (Math.abs(det2.getGel() + det.getGel()) < CoreUtils.MIN_GEL && Math.abs(det2.getKwh() + det.getKwh()) < CoreUtils.MIN_KWH)) {
						forRemove.add(det);
						forRemove.add(det2);
					}
				}
			}
		}
		newFactura.removeAll(forRemove);

		return newFactura;
	}

	/**
	 * Using this procedure you can add expansion over sys vouchers.
	 */
	private static List expandExistingSysVouchers(Session session, List factura, Recalc recalc) {
		List sysVouchers = new ArrayList();
		Date correctionDate = null;
		List newFacturaExpansion = factura;
		int startIndex = -1;
		for (int i = 0; i < recalc.getDetails().getIntervals().size(); i++) {
			RecalcInterval interval = (RecalcInterval) recalc.getDetails().getIntervals().get(i);
			if (!interval.isEditable())
				continue;
			for (int j = 0; j < interval.getItems().size(); j++) {
				RecalcItem item = (RecalcItem) interval.getItems().get(j);
				if (item.getAccount().getId() != recalc.getAccount().getId())
					continue;
				if (item.getOperation().getType().getId() == OperationType.VOUCHER && "sys".equals(item.getItemNumber())) {
					correctionDate = item.getItemDate();
					sysVouchers.add(item);
					if (startIndex == -1)
						startIndex = j;
				} else {
					if (correctionDate != null) {
						// expand specific vouchers
						newFacturaExpansion = expandExistingSysVouchers(session, factura, recalc, sysVouchers, interval, startIndex - 1 // item
																																// start
																																// index
						);
						// clear previous vouchers list
						correctionDate = null;
						sysVouchers.clear();
						startIndex = -1;
					}
				}
			}
		}
		return newFacturaExpansion;
	}

	private static List removeOneTimeActMatchings(List factura) {
		List newFactura = new ArrayList();
		newFactura.addAll(factura);

		List forRemove = new ArrayList();
		for (int i = 0; i < newFactura.size(); i++) {
			FacturaDetail det = (FacturaDetail) newFactura.get(i);
			int operId = det.getOperation().getId();
			if (operId == Operation.ONE_TIME_ACT || operId == Operation.ONE_TIME_ACT_AFTER_VAT_CHANGE_2005) {
				for (int j = 0; j < newFactura.size(); j++) {
					FacturaDetail det2 = (FacturaDetail) newFactura.get(j);
					int operId2 = det2.getOperation().getId();
					if ((operId2 == Operation.ONE_TIME_ACT || operId2 == Operation.ONE_TIME_ACT_AFTER_VAT_CHANGE_2005) && Date.isEqual(det.getItemDate(), det2.getItemDate()) && (Math.abs(det2.getGel() + det.getGel()) < CoreUtils.MIN_GEL && Math.abs(det2.getKwh() + det.getKwh()) < CoreUtils.MIN_KWH)) {
						forRemove.add(det);
						forRemove.add(det2);
					}
				}
			}
		}
		newFactura.removeAll(forRemove);

		return newFactura;
	}

	/**
	 * Expands given sys vouchers group.
	 */
	private static List expandExistingSysVouchers(Session session, List factura, Recalc recalc, List sysVouchers, RecalcInterval interval, int itemStartIndex) {
		// getting releavant items
		List items = new ArrayList();
		int r1 = -1;
		int r2 = -1;
		for (int i = itemStartIndex; 0 <= i; i--) {
			RecalcItem item = (RecalcItem) interval.getItems().get(i);
			if (item.getAccount().getId() != recalc.getAccount().getId())
				continue;
			items.add(0, item);
			boolean isReading = Utilities.isReading(item.getOriginalOperation().getId());
			if (isReading) {
				if (r2 == -1)
					r2 = i;
				else
					r1 = i;
			}
			if (r1 != -1 && r2 != -1)
				break;
		}
		// two readings are not defined: we can not
		// recalculate this SYS vouchers
		if (r1 == -1 || r2 == -1 || items.isEmpty())
			return factura;

		// create new recalculation and recalculate it
		Recalc newRecalc = createExistingSysVoucherRecalc(recalc, interval, items);
		Calculator calc = new Calculator();
		CalculationInterval calculatedInterval = (CalculationInterval) calc.calculate(session, newRecalc).get(0);
		List recalculatedExpansion = calc.getFacturaExpansion();

		// getting recalculated system vouchers
		List newSysVouchers = new ArrayList();
		for (int i = 0; i < calculatedInterval.getItems().size(); i++) {
			CalculationItem item = (CalculationItem) calculatedInterval.getItems().get(i);
			if (item.getOperation().getType().getId() == OperationType.VOUCHER && "sys".equals(item.getItemNumber())) {
				newSysVouchers.add(item);
			}
		}

		// compare recalculated vouchers with existing
		boolean compatible;
		if (newSysVouchers.size() != sysVouchers.size()) {
			compatible = false;
		} else {
			compatible = true;
			for (int i = 0; i < sysVouchers.size(); i++) {
				RecalcItem item1 = (RecalcItem) sysVouchers.get(i);
				for (int j = 0; j < newSysVouchers.size(); j++) {
					CalculationItem item2 = (CalculationItem) newSysVouchers.get(j);
					if (item1.getOperation().getId() == item2.getOperation().getId()) {
						double kwh1 = item1.getKwh();
						double gel1 = item1.getGel();
						double kwh2 = item2.getCharge().getKwh();
						double gel2 = item2.getCharge().getGel();
						if (!(Math.abs(kwh1 - kwh2) < CoreUtils.MIN_KWH && Math.abs(gel1 - gel2) < CoreUtils.MIN_GEL)) {
							compatible = false;
							break;
						}
					}
				}
				if (!compatible) {
					break;
				}
			}
		}
		if (!compatible)
			return factura;

		// creating new factura
		List newFactura = new ArrayList();
		newFactura.addAll(factura);

		// adding recalculated estimate correction expansions
		// and remove those items which were on their place initially
		if (recalculatedExpansion != null && !recalculatedExpansion.isEmpty()) {
			List forRemove = new ArrayList();
			List forAdd = new ArrayList();
			for (int i = 0; i < recalculatedExpansion.size(); i++) {
				FacturaDetail det = (FacturaDetail) recalculatedExpansion.get(i);
				if ("estimate_correction_item".equals(det.getItemNumber())) {
					det.setKwh(-det.getKwh());
					det.setGel(-det.getGel());
					if (DivisionDateFactory.isRecharge(det.getOperation().getId())) {
						det.setOperation(DbUtilities.findOperationById(DivisionDateFactory.getCoupledDischarge(det.getOperation().getId())));
					} else {
						det.setOperation(DbUtilities.findOperationById(det.getOperation().getId()));
					}
					forAdd.add(det);
					for (int j = 0; j < newFactura.size(); j++) {
						FacturaDetail det2 = (FacturaDetail) newFactura.get(j);
						if (det2.getOriginalItemId() == det.getOriginalItemId() && "oper_11".equals(det2.getItemNumber()) // estimate
																															// only!
						) {
							forRemove.add(det2);
						}
						boolean oposites = Math.abs(det.getKwh() + det2.getKwh()) < CoreUtils.MIN_KWH && Math.abs(det.getGel() + det2.getGel()) < CoreUtils.MIN_GEL;
						if (oposites && Date.isEqual(det.getItemDate(), det2.getItemDate()) && !"estimate_correction_item".equals(det2.getItemNumber())) {
							forRemove.add(det);
							forRemove.add(det2);
						}
					}
				}
			}
			newFactura.addAll(forAdd);
			newFactura.removeAll(forRemove);
		}

		// remove all system voucher factura details
		List forRemove = new ArrayList();
		for (int i = 0; i < sysVouchers.size(); i++) {
			RecalcItem item = (RecalcItem) sysVouchers.get(i);
			for (int j = 0; j < newFactura.size(); j++) {
				FacturaDetail det2 = (FacturaDetail) newFactura.get(j);
				if (det2.getOriginalItemId() == item.getItemId()) {
					forRemove.add(det2);
					break;
				}
			}
		}
		newFactura.removeAll(forRemove);
		// return results
		return newFactura;
	}

	private static Recalc createExistingSysVoucherRecalc(Recalc recalc, RecalcInterval interval, List items) {
		// first prepare according to standard procedure
		Recalc newRecalc = Utilities.prepareRecalcForCalculation(recalc);
		// clear all intervals
		newRecalc.getDetails().getIntervals().clear();
		// create a new interval
		RecalcInterval newInterval = new RecalcInterval();
		newInterval.setEditable(true);
		newInterval.setId(1);
		newInterval.setName(interval.getName());
		newInterval.setStartBalance(0.f);
		newInterval.setStartDate(((RecalcItem) items.get(0)).getItemDate());
		newInterval.setEndDate(((RecalcItem) items.get(items.size() - 1)).getItemDate());
		newRecalc.getDetails().getIntervals().add(newInterval);
		newInterval.setDetails(newRecalc.getDetails());
		// add items
		for (int i = 0; i < items.size(); i++) {
			RecalcItem item = (RecalcItem) items.get(i);
			if (item.getItemId() != -1) {
				// create new recalculation item
				RecalcItem newItem = CoreUtils.copyRecalcItem(item);
				newItem.setReading(newItem.getOriginalReading());
				newItem.setKwh(newItem.getOriginalKwh());
				newItem.setGel(newItem.getOriginalGel());
				newItem.setMeterCoeff(newItem.getOriginalMeterCoeff());
				newItem.setCycle(newItem.getOriginalCycle());
				newItem.setMeterAcceleration(0.f);
				newItem.setOperation(DbUtilities.findOperationById(newItem.getOriginalOperation().getId()));
				newItem.setCalculationHint(RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH);
				/*
				 * XXX: Question about status is a very difficult one. It is
				 * hard to predict what the user might mean. We suppose that any
				 * operation except subsidy and and additional charges should be
				 * consider with the ORIGINAL status.
				 * 
				 * If to be 100% precise we need additional settings when
				 * recalculating factura. But for the moment it seems to be so
				 * heavy task. Let's first try a simple way. Simple way is a
				 * best way sometimes.
				 */
				int operTypeId = newItem.getOperation().getType().getId();
				if (operTypeId == OperationType.ADD_CHARGE || operTypeId == OperationType.SUBSIDY) {
					// leave this as is
					;
				} else {
					newItem.setStatus(RecalcItem.ORIGINAL);
				}
				// add to new interval
				newItem.setInterval(newInterval);
				newInterval.getItems().add(newItem);
			}
		}
		// we need to restore regulars, while they were cleaned
		for (int i = 0; i < recalc.getDetails().getRegulars().size(); i++) {
			RecalcRegularItem reg = (RecalcRegularItem) recalc.getDetails().getRegulars().get(i);
			RecalcRegularItem newreg = new RecalcRegularItem();
			newreg.setAttachment(reg.getAttachment());
			newreg.setId(reg.getId());
			newreg.setOperation(reg.getOperation());
			newreg.setStartDate(reg.getStartDate());
			newreg.setEndDate(reg.getEndDate());
			newreg.setDetails(newRecalc.getDetails());
			newRecalc.getDetails().getRegulars().add(newreg);
		}
		// NB: we need to use original tariff history!!!
		Session session = null;
		try {
			session = new Session();
			List tariffHistory = E1RecalcManager.getDefaultTariffHistory(recalc.getAccount().getId(), session);
			newRecalc.getDetails().getTariffs().clear();
			for (int i = 0; i < tariffHistory.size(); i++) {
				RecalcTariffItem item = (RecalcTariffItem) tariffHistory.get(i);
				item.setDetails(newRecalc.getDetails());
				newRecalc.getDetails().getTariffs().add(item);
			}
		} catch (Exception ex) {
		} finally {
			try {
				session.close();
			} catch (Exception ex) {
			}
			session = null;
		}
		// new recalculation
		return newRecalc;
	}

}
