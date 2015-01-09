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
package telasi.recutil.beans.custhistory;

import java.io.Serializable;
import java.util.List;

import telasi.recutil.beans.Account;

/**
 * This class is used for storing account's tariff history.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a> Jan,
 *         2007
 */
public class AccountTariffs implements Serializable {

	private static final long serialVersionUID = -3804048262825529300L;

	private Account account;

	// list of TariffHistoryRecord
	private List tariffs;

	public List getTariffs() {
		return tariffs;
	}

	public void setTariffs(List tariffs) {
		this.tariffs = tariffs;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
