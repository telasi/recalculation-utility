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
 * General tariff interface.
 * @author dimakura
 */
public interface ITariff extends Serializable {

	public int getId();

	public String getName();

	public boolean isZero();

	public boolean isStep();

	/**
	 * Calculates GEL charge from the given kWh charge for the given dates
	 * interval.
	 * 
	 * @param kwh
	 *            kWh charge
	 * @param d1
	 *            start date
	 * @param d2
	 *            end date
	 * @return GEL charge
	 */
	public double calculate(double kwh, Date d1, Date d2);

	/**
	 * Returns VAT charge.
	 * 
	 * @return VAT charge
	 */
	public double getVAT();

}
