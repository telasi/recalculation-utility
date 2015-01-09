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
 * Recalculation tariff item.
 * @author dimakura
 */
public class RecalcTariffItem implements Serializable, IInterval {
	private static final long serialVersionUID = 3906922120421338633L;

	public boolean equals(Object o) {
		if (!(o instanceof RecalcTariffItem))
			return false;
		RecalcTariffItem item = (RecalcTariffItem) o;
		if (item.getId() == -1)
			return item.equals(o);
		return item.getId() == id;
	}

	private long id;
	private RecalcDetails details;
	private ITariff tariff;
	private Date startDate;
	private Date endDate;

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public ITariff getTariff() {
		return tariff;
	}

	public void setTariff(ITariff tariff) {
		this.tariff = tariff;
	}

}
