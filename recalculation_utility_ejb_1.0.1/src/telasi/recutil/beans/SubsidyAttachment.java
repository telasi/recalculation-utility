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
 * Operation subsidy attachament.
 * @author dimakura
 */
public class SubsidyAttachment implements Serializable {
	private static final long serialVersionUID = 4558857751184385659L;
	
	public static final int KWH_BY_DAY = 0;
	public static final int GEL = 1;
	public static final int KWH = 2;
	public static final int PERCENT = 3;
	public static final int TARIFF = 5;
	
	private boolean recalculable;
	private int[] units;
	private double[] amounts;
	private double[] amounts2;

	public double[] getAmounts() {
		return amounts;
	}

	public void setAmounts(double[] amounts) {
		this.amounts = amounts;
	}

	public boolean isRecalculable() {
		return recalculable;
	}

	public void setRecalculable(boolean recalculable) {
		this.recalculable = recalculable;
	}

	public int[] getUnits() {
		return units;
	}

	public void setUnits(int[] units) {
		this.units = units;
	}

	public double[] getAmounts2() {
		return amounts2;
	}

	public void setAmounts2(double[] amounts2) {
		this.amounts2 = amounts2;
	}

}