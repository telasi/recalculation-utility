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
 * Charge sructure is used to hold information about charge generation periods.
 * 
 * @author dimakura
 * 
 */
public class Charge implements Serializable {
	private static final long serialVersionUID = 8417428496114811252L;

	/* expansion */
	private List/* ChargeElement */expansion = new ArrayList();
	/* summary kwh */
	private double kwh;
	/* summary gel */
	private double gel;
	/* wheter cut was used */
	private boolean cutUsed;

	public double kwh() {
		double kwh = 0;
		for (int i = 0; i < expansion.size(); i++) {
			kwh += ((ChargeElement) expansion.get(i)).getKwh();
		}
		return kwh;
	}

	/**
	 * Clear this charge.
	 */
	public void clear() {
		expansion.clear();
		kwh = 0.;
		gel = 0.;
		cutUsed = false;
	}

	public Date getEndDate() {
		if (expansion.isEmpty())
			return null;
		return ((ChargeElement) expansion.get(expansion.size() - 1))
				.getEndDate();
	}

	public Date getStartDate() {
		if (expansion.isEmpty())
			return null;
		return ((ChargeElement) expansion.get(0)).getEndDate();
	}

	/**
	 * Returns expansion of this charge.
	 * 
	 * @return expansion of this charge
	 */
	public List expansion() {
		return expansion;
	}

	/**
	 * Add charge element to the expansion of this charge.
	 * 
	 * @param element
	 *            element to be added
	 * @return true when was added
	 */
	public boolean addElement(ChargeElement element) {
		if (expansion.add(element)) {
			kwh += element.getKwh();
			gel += element.getGel();
			return true;
		}
		return false;
	}

	public boolean addExpansion(List elements) {
		boolean modified = false;
		for (int i = 0; i < elements.size(); i++) {
			ChargeElement element = (ChargeElement) elements.get(i);
			if (addElement(element))
				modified = true;
		}
		return modified;
	}

	/**
	 * Remove charge element from the expansion of this charge.
	 * 
	 * @param element
	 *            element to be added
	 * @return true when was removed
	 */
	public boolean removeElement(ChargeElement element) {
		if (expansion.remove(element)) {
			kwh -= element.getKwh();
			gel -= element.getGel();
			return true;
		}
		return false;
	}

	/**
	 * Summary of gel charge.
	 * 
	 * @return gel
	 */
	public double getGel() {
		return gel;
	}

	/**
	 * Summary of kWh charge
	 * 
	 * @return kWh
	 */
	public double getKwh() {
		return kwh;
	}

	public boolean isCutUsed() {
		return cutUsed;
	}

	public void setCutUsed(boolean used) {
		this.cutUsed = used;
	}

	/**
	 * Makes copy of the source charge into destination.
	 * 
	 * @param src
	 *            src charge
	 * @param dest
	 *            destination charge
	 */
	public void copyCharge(Charge dest) {
		dest.clear();
		dest.setCutUsed(isCutUsed());
		for (int i = 0; i < expansion.size(); i++) {
			ChargeElement el = (ChargeElement) expansion.get(i);
			ChargeElement newElement = el.makeCopy();
			dest.addElement(newElement);
		}
	}

	/**
	 * Calling this procedure makes all charges to be positive.
	 */
	public void makeAllPositive() {
		double kwh = 0;
		double gel = 0;
		for (int i = 0; i < expansion.size(); i++) {
			ChargeElement el = (ChargeElement) expansion.get(i);
			el.setKwh(Math.abs(el.getKwh()));
			el.setGel(Math.abs(el.getGel()));
			kwh += el.getKwh();
			gel += el.getGel();
		}
		this.kwh = kwh;
		this.gel = gel;
	}

	/**
	 * Calling this procedure makes all charges to be positive.
	 */
	public void makeAllNegative() {
		double kwh = 0;
		double gel = 0;
		for (int i = 0; i < expansion.size(); i++) {
			ChargeElement el = (ChargeElement) expansion.get(i);
			el.setKwh(-Math.abs(el.getKwh()));
			el.setGel(-Math.abs(el.getGel()));
			kwh += el.getKwh();
			gel += el.getGel();
		}
		this.kwh = kwh;
		this.gel = gel;
	}

	public void applyFactor(double coeff) {
		double kwh = 0;
		double gel = 0;
		for (int i = 0; i < expansion.size(); i++) {
			ChargeElement el = (ChargeElement) expansion.get(i);
			el.setKwh(el.getKwh() * coeff);
			el.setGel(el.getGel() * coeff);
			kwh += el.getKwh();
			gel += el.getGel();
		}
		this.kwh = kwh;
		this.gel = gel;
	}

}
