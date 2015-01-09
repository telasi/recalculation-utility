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
 * Charge element is a smallest part for charge division.
 * It contains information about charge interval, kWh and
 * GEL values. It is useful when dividing the charges, while
 * each ChargeElement can be divided proportionaly.
 * 
 * @author dimakura
 */
public class ChargeElement implements Serializable {
    private static final long serialVersionUID = 6433455393270562680L;
    /*start date*/
    private Date startDate;
    /*end date*/
    private Date endDate;
    /*kwh charge*/
    private double kwh;
    /*gel charge*/
    private double gel;
    /*Id of the tariff used for gel calculation*/
    private int tariffId;
    private boolean stepTariff;
    private boolean isZeroTariff;

    public ChargeElement makeCopy() {
        ChargeElement newElement = new ChargeElement();
        newElement.setStartDate(getStartDate() == null ? null : Date.create(getStartDate()));
        newElement.setEndDate(getEndDate() == null ? null : Date.create(getEndDate()));
        newElement.setGel(getGel());
        newElement.setKwh(getKwh());
        newElement.setStepTariff(isStepTariff());
        newElement.setTariffId(getTariffId());
        newElement.setZeroTariff(isZeroTariff());
        return newElement;
    }
    
    /**
     * Get charge element start date.
     * @return charge element start date
     */
    public Date getStartDate() {
    return startDate;
    }
    /**
     * Set charge element start date.
     * @param startDate charge element start date
     */
    public void setStartDate(Date startDate) {
    this.startDate = startDate;
    }

    /**
     * Charge element end date.
     * @return end element
     */
    public Date getEndDate() {
    return endDate;
    }
    /**
     * Set charge element end date.
     * @param endDate
     */
    public void setEndDate(Date endDate) {
    this.endDate = endDate;
    }
    
    /**
     * Get GEL part of the charge.
     * @return GEL charge
     */
    public double getGel() {
    return gel;
    }
    /**
     * Set GEL part of the charge.
     * @param gel
     */
    public void setGel(double gel) {
    this.gel = gel;
    }
    
    /**
     * Get kWh part of the charge.
     * @return kWh charge
     */
    public double getKwh() {
    return kwh;
    }
    /**
     * Set kWh part of the charge.
     * @param kwh kWh charge
     */
    public void setKwh(double kwh) {
    this.kwh = kwh;
    }
    
    /**
     * Returns ID of the tariff used for charge calculations.
     * @return tariffId
     */
    public int getTariffId() {
    return tariffId;
    }
    /**
     * Set tariffId.
     * @param tariffId tariffId
     */
    public void setTariffId(int tariffId) {
    this.tariffId = tariffId;
    }
    public boolean isStepTariff() {
        return stepTariff;
    }
    public void setStepTariff(boolean stepTariff) {
        this.stepTariff = stepTariff;
    }
    public boolean isZeroTariff() {
        return isZeroTariff;
    }
    public void setZeroTariff(boolean isZeroTariff) {
        this.isZeroTariff = isZeroTariff;
    }
    
    public int getDistinction() {
    	try {
    		return Date.getIntervalInDays(getEndDate(), getStartDate());
    	} catch (NullPointerException ex) {
    		return -1;
    	}
    }
    
}
