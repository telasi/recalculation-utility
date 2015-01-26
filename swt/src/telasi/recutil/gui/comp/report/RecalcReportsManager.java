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
package telasi.recutil.gui.comp.report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.beans.TrashVoucher;
import telasi.recutil.gui.app.Cache;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.reports.recalc.VoucherFacturaReport;
import telasi.recutil.reports.recalc.VoucherReport;

public class RecalcReportsManager {
	private static NumberFormat nf = new DecimalFormat("#,###.00");
	private static int count = 0;

	@SuppressWarnings("unchecked")
	public static void openVoucherReport(RecalcVoucher voucher, List trashVouchers, Shell shell) {
		VoucherReport report = new VoucherReport();

		List trashOperList = new ArrayList();
		if (trashVouchers != null) {
			for (int i = 0; i < trashVouchers.size(); i++) {
				String row[] = new String[3];
				TrashVoucher vouch = (TrashVoucher) trashVouchers.get(i);
				row[0] = TrashVoucher.findOperation(vouch.getTrashOperation()).getName();
				row[1] = nf.format(vouch.getKwh());
				row[2] = nf.format(vouch.getGel());
				trashOperList.add(row);
			}
		}

		List operList = new ArrayList();
		for (int i = 0; voucher.getDetails() != null
				&& i < voucher.getDetails().size(); i++) {
			DiffDetail detail = (DiffDetail) voucher.getDetails().get(i);
			String[] row = new String[3];
			Operation operation = detail.getOperation();
			try {
				operation = Cache.findOperationById(detail.getOperation()
						.getId());
			} catch (Exception ex) {
			}
			row[0] = GUITranslator.GEO_ASCII_TO_KA(operation.getName());
			row[1] = nf.format(detail.getOriginalKwh());
			row[2] = nf.format(detail.getOriginalGel());
			operList.add(row);
		}
		Map map = new HashMap();
		List periodList = new ArrayList();
		for (int i = 0; voucher.getProperties() != null
				&& i < voucher.getProperties().size(); i++) {
			String[] row = (String[]) voucher.getProperties().get(i);
			if ("application.voucher.interval".equals(row[0])) {
				periodList.add(row[1]);
			} else {
				map.put(row[0], GUITranslator.GEO_ASCII_TO_KA(row[1]));
			}
		}
		Object voucherNumber = map.get("application.voucher.number");
		Object serviceCeneter = map.get("application.voucher.businessCenter");
		Object customerName = map.get("application.voucher.customer");
		Object customerNumber = map.get("application.voucher.customer_number");
		Object account = map.get("application.voucher.account");
		Object address = map.get("application.voucher.address");
		Object balanceBefore = map.get("application.voucher.initBalance");
		Object balanceAfter = map.get("application.voucher.finalBalance");
		Object operator = map.get("application.voucher.operator");
		Object advisor = map.get("application.voucher.advisor");
		Object reason = map.get("application.voucher.description");
		Object correctionDate = map.get("application.voucher.saveDate");
		Object leftAmount = map.get("application.voucher.leftAmount");

		// assign parameters
		report.setOperList(operList);
		report.setPeriodList(periodList);
		report.setTrashList(trashOperList);
		report.setVoucherNumber(voucherNumber == null ? "" : voucherNumber.toString());
		report.setServiceCenter(serviceCeneter == null ? "" : serviceCeneter.toString());
		report.setCustomerNumber(customerNumber == null ? "" : customerNumber.toString());
		report.setCustomerName(customerName == null ? "" : customerName.toString());
		report.setAccount(account == null ? "" : account.toString());
		report.setAddress(address == null ? "" : address.toString());
		report.setBalanceBefore(balanceBefore == null ? "" : balanceBefore.toString());
		report.setBalanceAfter(balanceAfter == null ? "" : balanceAfter.toString());
		report.setOperator(operator == null ? "" : operator.toString());
		report.setAdvisor(advisor == null ? "" : advisor.toString());
		report.setReason(reason == null ? "" : reason.toString());
		report.setCorrectionDate(correctionDate == null ? "" : correctionDate.toString());
		report.setLeftAmount(leftAmount == null ? "" : leftAmount.toString());

		// generate HTML and display it
		String html = report.generateHTML();
		// ExternalReportView.showReport(shell, html, "rpt" + count + ".html");
		// count++;
		ReportViewer viewer = new ReportViewer(shell, SWT.NONE);
		viewer.setHTML(html);
		viewer.open();
	}

	@SuppressWarnings("unchecked")
	public static void operVoucherFacturaReport(RecalcVoucher voucher,
			List facturaDetails, Shell shell) {
		Map map = new HashMap();
		List periodList = new ArrayList();
		for (int i = 0; voucher.getProperties() != null && i < voucher.getProperties().size(); i++) {
			String[] row = (String[]) voucher.getProperties().get(i);
			if ("application.voucher.interval".equals(row[0]))
				periodList.add(row[1]);
			else
				map.put(row[0], GUITranslator.GEO_ASCII_TO_KA(row[1]));
		}
		String voucherNumber = (String) map.get("application.voucher.number");
		String customerNumber = (String) map.get("application.voucher.customer_number");
		String account = (String) map.get("application.voucher.account");
		String date = (String) map.get("application.voucher.saveDate");

		VoucherFacturaReport report = new VoucherFacturaReport();
		report.setAccountNumber(account);
		report.setCustomerNumber(customerNumber);
		report.setDate(date);
		report.setVoucherNumber(voucherNumber);
		report.setDetails(facturaDetails);

		// generate HTML and display
		String html = report.generateHTML();
		// ExternalReportView.showReport(shell, html, "rpt" + count + ".html");
		ReportViewer viewer = new ReportViewer(shell, SWT.NONE);
		viewer.setHTML(html);
		viewer.open();
		count++;
	}

}
