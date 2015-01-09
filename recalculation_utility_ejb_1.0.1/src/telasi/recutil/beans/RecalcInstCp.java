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

import telasi.recutil.calc.calc06.IInterval;

/**
 * Installed capacity history item.
 * @author dimakura
 */
public class RecalcInstCp implements Serializable, IInterval {
	private static final long serialVersionUID = -708021983486680899L;
	
	public static final int NORMALIZE_ON_PREV_MONTH = 1;
	public static final int NORMALIZE_ON_30_DAYS = 2;
	public static final int AVERAGE_DAY_CHARGE = 3;
	public static final int NORM_ON_PRV_MONTH_RND_ON_3_DAYS = 4;
	
	private long id;
	private RecalcDetails details;
	private int option;
	private double amount;
	private Date startDate, endDate;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public RecalcDetails getDetails() {
		return details;
	}

	public void setDetails(RecalcDetails details) {
		this.details = details;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean equals(Object o) {
		if (!(o instanceof RecalcInstCp))
			return false;
		RecalcInstCp item = (RecalcInstCp) o;
		if (item.getId() == -1) return super.equals(o);
		return item.getId() == id;
	}
	
//	public int hashCode() {
//		return (int) id;
//	}

}
