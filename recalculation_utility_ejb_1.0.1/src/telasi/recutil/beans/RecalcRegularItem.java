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
 * Item used for regular subsidy/add. charge recalculations.
 * @author dimakura
 */
public class RecalcRegularItem implements Serializable, IInterval {
	private static final long serialVersionUID = -6674397000659162077L;

	public boolean equals(Object o) {
		if (!(o instanceof RecalcRegularItem))
			return false;
		RecalcRegularItem item = (RecalcRegularItem) o;
		if (item.getId() == -1)
			return super.equals(o);
		return item.getId() == id;
	}

	private long id;
	private Operation operation;
	private RecalcDetails details;
	private RecalcSubsidyAttachment attach;
	private Date d1;
	private Date d2;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDetails(RecalcDetails details) {
		this.details = details;
	}

	public RecalcDetails getDetails() {
		return details;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public RecalcSubsidyAttachment getAttachment() {
		return attach;
	}

	public void setAttachment(RecalcSubsidyAttachment attachment) {
		attach = attachment;
	}

	public Date getStartDate() {
		return d1;
	}

	public Date getEndDate() {
		return d2;
	}

	public void setStartDate(Date startDate) {
		d1 = startDate;
	}

	public void setEndDate(Date endDate) {
		d2 = endDate;
	}

}
