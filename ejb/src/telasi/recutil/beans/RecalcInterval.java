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

import telasi.recutil.calc.calc06.IInterval;

/**
 * Recalculation interval.
 * @author dimakura
 */
public class RecalcInterval implements Serializable, IInterval {
	private static final long serialVersionUID = 1326095772730372590L;
	private long id;
	private RecalcDetails details;
	private String name;
	private List items = new ArrayList();
	private double startBalance;
	private Date startDate;
	private Date endDate;
	private boolean editable;

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public RecalcDetails getDetails() {
		return details;
	}

	public void setDetails(RecalcDetails details) {
		this.details = details;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}

	public void validateDates() {
		Date d1 = null;
		Date d2 = null;
		for (int i = 0; items != null && i < items.size(); i++) {
			RecalcItem item = (RecalcItem) items.get(i);
			if (d1 == null || Date.isLess(item.getItemDate(), d1)) {
				d1 = item.getItemDate();
			}
			if (d2 == null || Date.isGreater(item.getItemDate(), d2)) {
				d2 = item.getItemDate();
			}
		}
		this.startDate = d1;
		this.endDate = d2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean equals(Object o) {
		if (!(o instanceof RecalcInterval))
			return false;
		RecalcInterval interval = (RecalcInterval) o;
		return interval.getId() == id;
	}
	
//	public int hashCode() {
//		return (int) id;
//	}

}
