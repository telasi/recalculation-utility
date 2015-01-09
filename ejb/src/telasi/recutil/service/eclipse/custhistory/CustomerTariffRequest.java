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
package telasi.recutil.service.eclipse.custhistory;

import java.util.List;

import telasi.recutil.beans.Customer;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

public class CustomerTariffRequest extends DefaultEJBRequest {

	private static final long serialVersionUID = -8537841121512604334L;

	private Customer customer;

	// list of AccountTariffs
	private List history;

	public CustomerTariffRequest(String userName, String password) {
		super(ActionConstants.OTHER_CUSTOMER_TARIFF_HISTORY, userName, password);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List getHistory() {
		return history;
	}

	public void setHistory(List history) {
		this.history = history;
	}

}
