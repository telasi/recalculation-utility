/*
 * Copyright 2007, Dimitri Kurashvili (dimakura@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package telasi.recutil.beans;

import java.io.Serializable;

/**
 * Diff detail.
 * 
 * @author dimakura
 */
public class DiffDetail implements Serializable {
	private static final long serialVersionUID = 180240690256953061L;

	public static final int NOT_DIFFERABLE = -1;
	public static final int VOUCHER = 0;
	public static final int CHARGE = 1;
	public static final int PAYMENT = 2;
	public static final int PENALTY = 3;
	public static final int BALANCE = 4;
	public static final int SERVICE = 5;
	public static final int COMPENSATION = 6;
	public static final int SUBSIDY_GENERAL = 7;
	public static final int SUBSIDY_TARIFF_2003 = 8;
	public static final int SUBSIDY_TARIFF_2006 = 9;
	public static final int SUBSIDY_REFUGE = 10;
	public static final int SUBSIDY_COMP_REFUGE = 11;
	public static final int SUBSIDY_PENSION = 12;
	public static final int SUBSIDY_EMPLOYEE = 13;
	public static final int SUBSIDY_USAID = 14;
	public static final int SUBSIDY_FIX_KWH = 15;
	public static final int SUBSIDY_PERCENT = 16;
	public static final int ONE_TIME_ACT = 17;

	private DiffSummary summary;
	private CalculationItem orig;
	private CalculationItem drvd;

	public boolean isCharge() {
		return getCategory() == CHARGE;
	}

	public boolean isVoucher() {
		return getCategory() == VOUCHER;
	}

	public int getCategory() {
		Operation oper = getOperation();
		if (oper != null)
			return oper.getDiffCategory();
		return DiffDetail.NOT_DIFFERABLE;
	}

	public Date getDerivedEndDate() {
		return drvd == null ? null : drvd.getCharge().getEndDate();
	}

	public double getDerivedGel() {
		return drvd == null ? 0 : drvd.getCharge().getGel();
	}

	public double getDerivedKwh() {
		return drvd == null ? 0 : drvd.getCharge().getKwh();
	}

	public Date getDerivedStartDate() {
		return drvd == null ? null : drvd.getCharge().getStartDate();
	}

	public Operation getOperation() {
		if (drvd != null)
			return drvd.getOperation();
		if (orig != null)
			return orig.getOperation();
		return null;
	}

	public Date getOriginalEndDate() {
		return orig == null ? null : orig.getCharge().getEndDate();
	}

	public double getOriginalGel() {
		return orig == null ? 0 : orig.getCharge().getGel();
	}

	public double getOriginalKwh() {
		return orig == null ? 0 : orig.getCharge().getKwh();
	}

	public Date getOriginalStartDate() {
		return orig == null ? null : orig.getCharge().getStartDate();
	}

	public DiffSummary getSummary() {
		return summary;
	}

	public void setSummary(DiffSummary summary) {
		this.summary = summary;
	}

	public CalculationItem getOriginalItem() {
		return orig;
	}

	public CalculationItem getDerivedItem() {
		return drvd;
	}

	public void setOriginalItem(CalculationItem item) {
		this.orig = item;
	}

	public void setDerivedItem(CalculationItem item) {
		this.drvd = item;
	}
}
