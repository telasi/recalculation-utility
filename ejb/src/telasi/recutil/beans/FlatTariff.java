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

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * FlatTariff tariff.
 * @author dimakura
 */
public class FlatTariff implements ITariff {
	private static final long serialVersionUID = -1795746820167653851L;
	private double tariff, vatPercent;
	private double gel;
	private int id;
	private String name;

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

	public FlatTariff(double tariff) {
		this.tariff = tariff;
	}

	public double calculate(double kwh, Date d1, Date d2) {
		gel = tariff < 0 ? 0 : tariff * kwh;
		return gel;
	}

	public boolean isZero() {
		return Math.abs(tariff) < 0.0099f;
	}

	public String toString() {
		NumberFormat nf = new DecimalFormat("#,###.00000");
		return nf.format(tariff);
	}

	public boolean isStep() {
		return false;
	}

	public double getVAT() {
		return gel * vatPercent / (1.0f + vatPercent);
	}

	public void setVatPercent(double vatPercent) {
		this.vatPercent = vatPercent;
	}

}
