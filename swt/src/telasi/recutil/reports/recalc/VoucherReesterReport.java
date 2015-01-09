package telasi.recutil.reports.recalc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import telasi.recutil.beans.Recalc;
import telasi.recutil.gui.preferences.ReportPreferencesPage;
import telasi.recutil.gui.utils.GUITranslator;
import telasi.recutil.reports.ReportUtils;

public class VoucherReesterReport {
	private List vouchers;

	@SuppressWarnings("unchecked")
	public String generateHTML() {
		try {
			HashMap map = new HashMap();
			List vouchers = prepareVouchers();
			map.put("vouchers", vouchers == null ? new ArrayList() : vouchers);
			map.put("bc", ReportPreferencesPage.getMyBusinessCenterName());
			return ReportUtils.generateHTML("designs/voucher_reester.rptdesign", map);
		} catch (Throwable t) {
			t.printStackTrace();
			return t.toString();
		}
	}

	@SuppressWarnings("unchecked")
	List prepareVouchers() {
		ArrayList list = new ArrayList();
		for (int i = 0; vouchers != null && i < vouchers.size(); i++) {
			Recalc recalc = (Recalc) vouchers.get(i);
			String recalcNumber = recalc.getNumber() == null ? "" : recalc.getNumber();
			String customerNumber = recalc.getCustomer().getNumber() == null ? "" : recalc.getCustomer().getNumber();
			customerNumber = GUITranslator.GEO_ASCII_TO_KA(customerNumber);
			String customerName = recalc.getCustomer().getName() == null ? "" : recalc.getCustomer().getName();
			customerName = GUITranslator.GEO_ASCII_TO_KA(customerName);
			String customerAddress = recalc.getCustomer().getAddress() == null ? "" : recalc.getCustomer().getAddress().toString();
			customerAddress = GUITranslator.GEO_ASCII_TO_KA(customerAddress);
			String[] row = {recalcNumber, customerNumber, customerName, customerAddress};
			list.add(row);
		}
		return list;
	}
	
	public List getVouchers() {
		return vouchers;
	}

	public void setVouchers(List vouchers) {
		this.vouchers = vouchers;
	}

}
