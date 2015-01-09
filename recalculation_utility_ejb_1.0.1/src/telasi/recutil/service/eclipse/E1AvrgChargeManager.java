/*
 *   Copyright (C) 2006, 2007 by JSC Telasi
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
package telasi.recutil.service.eclipse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.avrgcalc.AvearageKwhSeed;
import telasi.recutil.calc.calc06.Calc2006Utils;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.Response;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.service.eclipse.avrgcharge.AvrgChargeCalculationRequest;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Jan, 2007
 */
public class E1AvrgChargeManager {

	public static Response processCreateAvrgCalculationRecalc(
			Request req) throws RequestException {
		throw new RequestException("Request is no longer supported: " + req.getClass().getName());
		// try {
		// CreateAvrgCalculationRecalc request = (CreateAvrgCalculationRecalc)
		// req;
		// request.setRecalc(createAvrgCalculationRecalc(request.getAccount()));
		// return new DefaultEJBResponse(request);
		// } catch (Exception ex) {
		// throw new RequestException(ex.toString(), ex);
		// }
	}

	public static Response processAvrgCalculation(Request req)
			throws RequestException {
		try {
			AvrgChargeCalculationRequest request = (AvrgChargeCalculationRequest) req;
			request.setResults(calculateAvrgKwh_new(request.getRecalc()));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	//
	//
	//

	public static Recalc createAvrgCalculationRecalc(Account account)
	throws SQLException, IOException {

		return null;

		// // get customer history items
		// List items = E1CustomerManagment.getHistory(account.getCustomer());
		//
		// // recalculation head
		// Recalc recalc = new Recalc();
		// recalc.setId(-1L);
		// recalc.setNumber("avrg_recalculation");
		// recalc.setCustomer(account.getCustomer());
		// recalc.setAccount(account);
		//
		// // recalculation interval
		// RecalcInterval interval = new RecalcInterval();
		// recalc.getDetails().getIntervals().add(interval);
		// interval.setDetails(recalc.getDetails());
		// interval.setEditable(false);
		// interval.setName("sys_01");
		//
		// // add recalculation items
		// List ritems = new ArrayList();
		// for (int i = 0; items != null && i < items.size(); i++) {
		// // create each item
		// IItem item = (IItem) items.get(i);
		// if (item.getAccount().getId() != account.getId()) {
		// continue;
		// }
		// int operId = item.getOperation().getId();
		// boolean isCharge = Calc2006Utils.isCharge(operId);
		// boolean isChargeVoucher = DivisionDateFactory.isDischarge(operId) ||
		// DivisionDateFactory.isRecharge(operId) ||
		// DivisionDateFactory.isChargeCorrection(operId);
		// boolean isSubAccCharge = item.getOperation().getId() ==
		// IOperation.SUB_ACCOUNT_CHARGE;
		// boolean isSummary = operId == IOperation.SUMMARY_CHARGE;
		// if ((isCharge || isSubAccCharge || isChargeVoucher) && !isSummary) {
		// RecalcItem ritem = new RecalcItem();
		// ritem.setId(-1L);
		// ritem.setItemDate(item.getItemDate());
		// ritem.setKwh(item.getKwh());
		// ritem.setGel(item.getGel());
		// ritem.setReading(item.getReading());
		// ritem.setCycle(item.getSchedule() != -1);
		// ritem.setOperation(item.getOperation());
		// ritem.setCustomer(item.getCustomer());
		// ritem.setAccount(item.getAccount());
		// ritem.setBalance(item.getBalance());
		// ritem.setStatus(IRecalcItem.ORIGINAL);
		// // add item
		// ritem.setInterval(interval);
		// ritems.add(ritem);
		// }
		// }
		// interval.setItems(ritems);
		//
		// // return recalculation
		// interval.validateDates();
		// return recalc;

	}

	// list of AvearageKwhSeed
	public static List calculateAvrgKwh_new(Recalc recalc)
	throws RequestException {
		List seeds = new ArrayList();
		NumberFormat nf = new DecimalFormat("##00");
		List intervals = recalc.getDetails().getIntervals();
		for (int i = 0; i < intervals.size(); i++) {
			RecalcInterval interval = (RecalcInterval) intervals.get(i);
			RecalcItem item1 = (RecalcItem) interval.getItems().get(0);
			RecalcItem item2 = (RecalcItem) interval.getItems().get(1);
			double r1 = item1.getReading();
			double r2 = item2.getReading();
			Date d1 = item1.getItemDate();
			Date d2 = item2.getItemDate();
			double coeff = item1.getMeterCoeff();
			int digits = item1.getMeter().getDigits();
			double acc = item1.getMeterAcceleration();
			double kwh = Calc2006Utils.deriveKwh(r2, r1, coeff, digits, false, acc);
			kwh = Calc2006Utils.round(kwh, 2);
			int distinction = Date.getIntervalInDays(d2, d1);
			double avrgKwh = kwh / distinction;

			AvearageKwhSeed seed = new AvearageKwhSeed();
			seed.setStartDate(d1);
			seed.setEndDate(d2);
			seed.setDayInterval(distinction);
			seed.setAveargeKwh(avrgKwh);
			seed.setFullKwh(kwh);
			seed.setName(nf.format(i + 1));
			seeds.add(seed);
		}

		if (!seeds.isEmpty()) {
			int distinction = 0;
			double kwh = 0.f;
			double avrgKwh = 0.f;
			for (int i = 0; i < seeds.size(); i++) {
				AvearageKwhSeed seed = (AvearageKwhSeed) seeds.get(i);
				distinction += seed.getDayInterval();
				kwh += seed.getKwh();
			}
			avrgKwh = kwh / distinction;
			AvearageKwhSeed seed = new AvearageKwhSeed();
			seed.setStartDate(null);
			seed.setEndDate(null);
			seed.setDayInterval(distinction);
			seed.setAveargeKwh(avrgKwh);
			seed.setFullKwh(kwh);
			seed.setName("Summary");
			seeds.add(seed);
		}

		return seeds;
	}

	public static List calculateAvrgKwh_old(Recalc recalc)
			throws RequestException {
		List seeds = new ArrayList();
		List intervals = recalc.getDetails().getIntervals();
		for (int i = 0; intervals != null && i < intervals.size(); i++) {
			RecalcInterval interval = (RecalcInterval) intervals.get(i);
			for (int j = 0; j < interval.getItems().size(); j++) {
				RecalcItem item = (RecalcItem) interval.getItems().get(j);
				if (item.getCurrentOperationDate() != null) {
					item.setItemDate(item.getCurrentOperationDate());
				}
			}
		}
		for (int i = 0; intervals != null && i < intervals.size(); i++) {
			RecalcInterval interval = (RecalcInterval) intervals.get(i);
			if (!interval.isEditable()) {
				continue;
			}
			interval.validateDates();
			Date d1 = interval.getStartDate();
			Date d2 = interval.getEndDate();
			if (Date.isEqual(d1, d2)) {
				throw new RequestException(
						"Interval start and end dates are equal.");
			}
			int distinction = Date.getIntervalInDays(d2, d1);
			double kwh = 0.f;
			for (int j = 0; j < interval.getItems().size(); j++) {
				RecalcItem item = (RecalcItem) interval.getItems().get(j);
				if (item.getStatus() == RecalcItem.ORIGINAL) {
					kwh += item.getKwh();
				}
			}
			double avrgKwh = kwh / distinction;
			AvearageKwhSeed seed = new AvearageKwhSeed();
			seed.setStartDate(d1);
			seed.setEndDate(d2);
			seed.setDayInterval(distinction);
			seed.setAveargeKwh(avrgKwh);
			seed.setFullKwh(kwh);
			seed.setName(interval.getName());
			seeds.add(seed);
		}

		if (!seeds.isEmpty()) {
			Date d1 = null;
			Date d2 = null;
			int distinction = 0;
			double kwh = 0.f;
			double avrgKwh = 0.f;
			for (int i = 0; i < seeds.size(); i++) {
				AvearageKwhSeed seed = (AvearageKwhSeed) seeds.get(i);
				distinction += seed.getDayInterval();
				kwh += seed.getKwh();
			}
			avrgKwh = kwh / distinction;
			AvearageKwhSeed seed = new AvearageKwhSeed();
			seed.setStartDate(d1);
			seed.setEndDate(d2);
			seed.setDayInterval(distinction);
			seed.setAveargeKwh(avrgKwh);
			seed.setFullKwh(kwh);
			seed.setName("Summary");
			seeds.add(seed);
		}

		return seeds;
	}

}
