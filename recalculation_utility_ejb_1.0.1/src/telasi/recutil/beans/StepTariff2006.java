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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;

import telasi.recutil.calc.calc07.Utilities;

/**
 * Step tariff.
 * 
 * @author dimakura
 */
public class StepTariff2006 implements ITariff {
	private static final long serialVersionUID = 799536795359484341L;

	/**
	 * @since 4-sep-2009
	 * 
	 *        დაწვრილებითი კომენტარები ამ თარიღზე იხილეთ კოდში.
	 */
	public static final Date GAMGE_DATE = new Date(2009, 6, 1);

	private int id;
	private String name;
	private double vatPercent, gel;

	public void setVatPercent(double vatPercent) {
		this.vatPercent = vatPercent;
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

	public String toString() {
		NumberFormat nf1 = new DecimalFormat("#,##0");
		NumberFormat nf2 = new DecimalFormat("#,##0.00####");
		StringBuffer sb = new StringBuffer();
		int start = 0;
		int end = 0;
		if (steps != null && !steps.isEmpty()) {
			for (int i = 0; i < steps.size(); i++) {
				TariffStep step = (TariffStep) steps.get(i);
				end += step.interval;
				if (i != 0) {
					sb.append("; ");
				}
				sb.append(nf2.format(step.getTariff()));
				sb.append(" (");
				sb.append(nf1.format(start));
				sb.append(" - ");
				if (step.interval != -1) {
					sb.append(nf1.format(end));
				} else {
					sb.append("+inf.");
				}
				sb.append(")");
				start = end;
			}
		}
		return sb.toString();
	}

	public StepTariff2006(List steps) {
		this.steps = steps;
	}

	private static int NORMA = 30;

	private List steps;

	public Collection getSteps() {
		return steps;
	}

	public double calculate(double kwh, Date d1, Date d2) {
		double gel = 0;
		int distinction = Date.getIntervalInDays(d2, d1);
		if (kwh < 0) {
			throw new IllegalArgumentException("kWh < 0");
		} else if (kwh == 0f || isZero()) {
			gel = 0;
		} else {
			double remaining = kwh;
			int days = distinction;
			for (int i = 0; i < steps.size(); i++) {
				TariffStep step = (TariffStep) steps.get(i);

				/*
				 * @since 4-sep-2009
				 * 
				 * საფეხუროვანი ტარიფის ინტერვალი შეიცვალა 2009 წლის 1
				 * ივნისიდან. ახლა 0-100, 100-300, 300-* კი არა 0-101, 101-301,
				 * 301-* ბიჯებია.
				 */
				int stepInterval = step.getInterval();
				if (Date.isGreater(d2, GAMGE_DATE) && i == 0) { // only the
																// first step
																// needs
																// correction
					stepInterval = stepInterval + 1;
				}
				/* -- ესაა გამგეს გენიალური აზრების ბოლო -- */

				double interval = Utilities.round(stepInterval * days * 1d / NORMA);
				if (remaining <= interval || step.getInterval() == -1) {
					gel = kwh * step.getTariff();
					break;
				} else {
					remaining = Utilities.round(remaining - interval);
				}
			}
		}
		this.gel = gel;
		return gel;
	}

	public boolean isZero() {
		return steps == null || steps.isEmpty();
	}

	/**
	 * Step of the step tariff.
	 */
	public static class TariffStep implements Serializable {

		private static final long serialVersionUID = 9046547699342726854L;

		private int interval;

		private double tariff;

		public TariffStep() {

		}

		public int getInterval() {
			return interval;
		}

		public void setInterval(int interval) {
			this.interval = interval;
		}

		public double getTariff() {
			return tariff;
		}

		public void setTariff(double tariff) {
			this.tariff = tariff;
		}

	}

	public boolean isStep() {
		return true;
	}

	public double getVAT() {
		return gel * vatPercent / (1.0f + vatPercent);
	}

}
