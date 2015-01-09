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
package telasi.recutil.reports.recalc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import telasi.recutil.beans.FacturaDetail;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.reports.ReportUtils;

/**
 * Managment of VoucherFactura expansion.
 * 
 * @author dimitri
 */
public class VoucherFacturaReport {
	private String voucherNumber;
	private String customerNumber;
	private String accountNumber;
	private String date;
	private List/*FacturaDetail*/ details;

	// generate HTML report
	@SuppressWarnings("unchecked")
	public String generateHTML() {
		try {
			HashMap map = new HashMap();
			map.put("voucherNumber", voucherNumber == null ? "" : voucherNumber);
			map.put("customerNumber", customerNumber == null ? "" : customerNumber);
			map.put("accountNumber", accountNumber == null ? "" : accountNumber);
			map.put("correctionDate", date == null ? "" : date.toString());
			List dets = new ArrayList();
			NumberFormat nf = new DecimalFormat("#,###.00");
			for (int i = 0; details != null && i < details.size(); i++) {
				FacturaDetail det = (FacturaDetail) details.get(i);
				String[] strings = new String[5];
				strings[0] = GUITranslator.GEO_ASCII_TO_KA(det.getOperation().getName());
				strings[1] = det.getItemDate().toString();
				strings[2] = String.valueOf(det.getOriginalItemId());
				strings[3] = nf.format(det.getKwh());
				strings[4] = nf.format(det.getGel());
				dets.add(strings);
			}
			map.put("details", dets);
			return ReportUtils.generateHTML("designs/voucher_factura2.rptdesign", map);
		} catch (Throwable t) {
			t.printStackTrace();
			return t.toString();
		}
	}

	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	public String getVoucherNumber() {
		return voucherNumber;
	}
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	
	

}
