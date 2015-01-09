/*
 *   Copyright (C) 2006-2009 by JSC Telasi
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
package telasi.recutil.service.eclipse.tpowner;

import java.sql.Date;
import java.util.List;

import telasi.recutil.beans.Customer;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Request for getting summary recalculation of the customer (tp owner)
 * for given cycle date. Sum is calculated over each recalculated account
 * of this customer. It should dispaly the picture as after sending to
 * BS.ITEM table.
 * 
 * @author dimitri
 */
public class TpOwnerRecalcCustomerSummaryRequest extends DefaultEJBRequest {
	private static final long serialVersionUID = -1888004982722567456L;

	/**
	 * Customer.
	 */
	private Customer customer;
	
	/**
	 * Cycle date for which calculation was done.
	 */
	private Date cycleDate;
	
	/**
	 * Resulting summary items.
	 */
	private List items;
	
	public TpOwnerRecalcCustomerSummaryRequest(String userName, String password) {
		super(ActionConstants.ACT_TPOWNER_RECALCULATION_CUSTOMER_SUMMARY, userName, password);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getCycleDate() {
		return cycleDate;
	}

	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
	
}
