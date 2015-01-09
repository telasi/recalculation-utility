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
import java.util.ArrayList;
import java.util.List;

/**
 * Customer.
 * 
 * @author dimakura
 */
public class Customer implements Serializable {
	private static final long serialVersionUID = 4089753640709496843L;
	private int id;
	private String number;
	private String name;
	private Address address;
	private int category;
	private int roomCount;
	private List accounts;
	private double currentBalance;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(int roomCount) {
		this.roomCount = roomCount;
	}

	public List getAccounts() {
		return accounts;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	
	public void addAccount(Account acc) {
		if (accounts == null) {
			accounts = new ArrayList();
		}
		if (!accounts.contains(acc)) {
			accounts.add(acc);
			acc.setCustomer(this);
		}
	}

	public void removeAccount(Account acc) {
		if (accounts != null && accounts.contains(acc)) {
			accounts.remove(acc);
			acc.setCustomer(null);
		}
	}

	public String toString() {
		return name;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Customer))
			return false;
		Customer customer = (Customer) o;
		return customer.getId() == id;
	}
	
	public int hashCode() {
		return id;
	}
	
}
