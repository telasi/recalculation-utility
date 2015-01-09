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
package telasi.recutil.reports.recalc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import telasi.recutil.reports.ReportUtils;

public class VoucherReport {
	private List operList;
	private List periodList;
	private List trashList;
	private String voucherNumber;
	private String serviceCenter;
	private String customerName;
	private String reason;
	private String customerNumber;
	private String account;
	private String address;
	private String correctionDate;
	private String balanceBefore;
	private String balanceAfter;
	private String operator;
	private String advisor;
	private String leftAmount;

	//generate HTML report
	@SuppressWarnings("unchecked")
	public String generateHTML() {
		try {
			HashMap map = new HashMap();
			map.put("operList", operList == null ? new ArrayList() : operList);
			map.put("periodList", periodList == null ? new ArrayList() : periodList);
			map.put("trashList", trashList == null ? new ArrayList() : trashList);
			map.put("voucherNumber", voucherNumber == null ? "" : voucherNumber);
			map.put("serviceCenter", serviceCenter == null ? "" : serviceCenter);
			map.put("customerName", customerName == null ? "" : customerName);
			map.put("customerNumber", customerNumber == null ? "" : customerNumber);
			map.put("account", account == null ? "" : account);
			map.put("address", address == null ? "" : address);
			map.put("correctionDate", correctionDate == null ? "" : correctionDate);
			map.put("balanceBefore", balanceBefore == null ? "" : balanceBefore);
			map.put("balanceAfter", balanceAfter == null ? "" : balanceAfter);
			map.put("operator", operator == null ? "" : operator);
			map.put("advisor", advisor == null ? "" : advisor);
			map.put("reason", reason == null ? "" : reason);
			map.put("leftAmount", leftAmount == null ? "" : leftAmount);
			//return ReportUtils.generateHTML("designs/voucher_report.rptdesign", map);
			return ReportUtils.generateHTML("designs/voucher_report2.rptdesign", map);
		} catch (Throwable t) {
			t.printStackTrace();
			return t.toString();
		}
	}

	public void setTrashList(List trashList) {
		this.trashList = trashList;
	}

	public List getTrashList() {
		return trashList;
	}

	public List getOperList() {
		return operList;
	}

	public void setOperList(List operList) {
		this.operList = operList;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public List getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List periodList) {
		this.periodList = periodList;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdvisor() {
		return advisor;
	}

	public void setAdvisor(String advisor) {
		this.advisor = advisor;
	}

	public String getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(String balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public String getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(String balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public String getCorrectionDate() {
		return correctionDate;
	}

	public void setCorrectionDate(String correctionDate) {
		this.correctionDate = correctionDate;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getServiceCenter() {
		return serviceCenter;
	}

	public void setServiceCenter(String serviceCenter) {
		this.serviceCenter = serviceCenter;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(String leftAmount) {
		this.leftAmount = leftAmount;
	}

}
