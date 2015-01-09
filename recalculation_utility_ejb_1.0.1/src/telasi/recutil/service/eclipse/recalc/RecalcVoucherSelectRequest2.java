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
package telasi.recutil.service.eclipse.recalc;

import java.util.List;

import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Request for select recalculation as voucher.
 * It has the same code as RecalcVoucherSelectRequest class.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * Jul, 2007
 */
public class RecalcVoucherSelectRequest2 extends DefaultEJBRequest {
	private static final long serialVersionUID = -1984653513235639893L;

	private Recalc recalc;
	private RecalcVoucher voucher;
	private List/*FacturaDetail*/ facturaDetails;

	public RecalcVoucherSelectRequest2(String userName, String password) {
		super(ActionConstants.ACT_GET_SAVED_VOUCHER, userName, password);
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

	public RecalcVoucher getVoucher() {
		return voucher;
	}

	public void setVoucher(RecalcVoucher voucher) {
		this.voucher = voucher;
	}

	public List getFacturaDetails() {
		return facturaDetails;
	}

	public void setFacturaDetails(List facturaDetails) {
		this.facturaDetails = facturaDetails;
	}

}
