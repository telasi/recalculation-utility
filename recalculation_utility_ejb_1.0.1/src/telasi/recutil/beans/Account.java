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
 * Account.
 *
 * @author dimitri
 */
public class Account implements Serializable {
	private static final long serialVersionUID = 3506272547167819923L;
	private int id;
	private String number;
	private Customer customer;
	private boolean main;
	private int status;
	private Date creationDate;
	private String meterNumber;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String toString() {
		return number;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Account))
			return false;
		Account acc = (Account) o;
		return acc.getId() == id;
	}
	
	public int hashCode() {
		return id;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
}
