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
 * Full recalculation interface. 
 * @author dimakura
 */
public class Recalc implements Serializable {
	private static final long serialVersionUID = 5035918310517833712L;

	private long id;
	private RecalcDetails details = new RecalcDetails();
	private String number;
	private Customer customer;
	private Account account;
	private Date createDate;
	private String description;
	private boolean changed;
	private Date saveDate;
	private User saveUser, advisor;
	private double initialBalance;
	private Double finalBalance;

	public User getAdvisor() {
		return advisor;
	}

	public void setAdvisor(User advisor) {
		this.advisor = advisor;
	}

	/**
	 * TRUE when recalculation details can be changed.
	 * FALSE otherwise.
	 */
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	public User getSaveUser() {
		return saveUser;
	}

	public void setSaveUser(User saveUser) {
		this.saveUser = saveUser;
	}

	public Recalc() {
		details.setRecalc(this);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public RecalcDetails getDetails() {
		return details;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getFinalBalance() {
		return finalBalance;
	}

	public void setFinalBalance(Double finalBalance) {
		this.finalBalance = finalBalance;
	}

	public double getInitialBalance() {
		return initialBalance;
	}

	public void setInitialBalance(double initialBalance) {
		this.initialBalance = initialBalance;
	}

}
