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
package telasi.recutil.gui.utils;

import java.util.List;

import telasi.recutil.beans.CalculationItem;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.RecalcItem;

/**
 * Simple utility for summary calculations.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Dec, 2006
 */
public class SummaryCalculator {

	private double gel;
	private double kwh;
	private double balance;

	public void calculateBalance(RecalcItem item) {
		double balance = item.getBalance();
		double gel = item.getGel();
		if (isLikePayment(item.getOperation())) {
			this.balance = balance - Math.abs(gel);
		} else {
			this.balance = balance + gel;
		}
	}

//	public void calculateBalance(DerivedItem item) {
//		double balance = round(item.getBalance());
//		double gel = round(item.getGel());
//		if (isLikePayment(item.getOperation())) {
//			this.balance = balance - Math.abs(gel);
//		} else {
//			this.balance = balance + gel;
//		}
//	}

	public void calculateSummary(List items) {
		this.gel = 0.f;
		this.kwh = 0.f;
		for (int i = 0; i < items.size(); i++) {
			Object aItem = items.get(i);
			if (aItem instanceof RecalcItem) {
				RecalcItem item = (RecalcItem) aItem;
				double kwh = round(item.getKwh());
				double gel = round(item.getGel());
				if (isLikePayment(item.getOperation())) {
					this.gel += -Math.abs(gel);
					this.kwh += -Math.abs(kwh);
				} else {
					this.gel += gel;
					this.kwh += kwh;
				}
			} else if (aItem instanceof CalculationItem) {
				CalculationItem item = (CalculationItem) aItem;
				double kwh = round(item.getCharge().getKwh());
				double gel = round(item.getCharge().getGel());
				if (isLikePayment(item.getOperation())) {
					this.gel += -Math.abs(gel);
					this.kwh += -Math.abs(kwh);
				} else {
					this.gel += gel;
					this.kwh += kwh;
				}
			} else {
				;
			}
		}
	}

	public boolean isLikePayment(Operation oper) {
		return oper.getType().getId() == 3 || oper.getType().getId() == 5;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getGel() {
		return gel;
	}

	public void setGel(double gel) {
		this.gel = gel;
	}

	public double getKwh() {
		return kwh;
	}

	public void setKwh(double kwh) {
		this.kwh = kwh;
	}

	private double round(double x) {
		return Math.round(x * 100.f) / 100.f;
	}

}
