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
 * Operation.
 * @author dimakura
 * 
 */
public class Operation implements Serializable {
	private static final long serialVersionUID = 2836942490295898557L;
	//
	// readings
	//
	public static final int READING = 1;
	public static final int CONTROL_READING = 2;
	public static final int SALE = 3;
	public static final int METER_INSTALL = 4;
	public static final int METER_DEINSTALL = 5;
	public static final int BALANCE_READING = 50;
	public static final int CUT_READING = 6;
	public static final int REPAIR_READING = 7;
	public static final int CURRENT_CHARGE = 504;
	public static final int CURRENT_CHARGE_ACT = 505;
	public static final int CURRENT_CHARGE_VOUCHER = 506;
	public static final int SUMMARY_CHARGE = 507;
	public static final int DISCHARGE_GEL = 508;
	//
	// charges
	//
	public static final int ESTIMATE = 11;
	public static final int WITHOUT_METER = 9;
	public static final int NOT_OPERABLE_METER = 10;
	//
	// subsidies
	//
	public static final int SUMMARY_PERCENT_SUBSIDY = 513;
	public static final int DISCHARGE_PERCENT_SUBSIDY_GEL = 514;
	//
	// additional charges
	//
	public static final int ONE_TIME_ACT = 33;
	public static final int MANNY_TIME_ACT = 34;
	public static final int ONE_TIME_ACT_AFTER_VAT_CHANGE_2005 = 180;
	public static final int SUB_ACCOUNT_CHARGE = 38;
	//
	// regular additional charges
	//
	public static final int REGULAR_ADD_CHARGE_GENERAL = -100;
	public static final int REGULAR_ADD_CHARGE_REGULAR = -101;

	//
	// regular subsidies
	//
	public static final int REGULAR_SUBSIDY_GENERAL = -200;
	public static final int REGULAR_SUBSIDY_EMPLOYEE = -201;
	public static final int REGULAR_SUBSIDY_PERCENT = -202;
	public static final int REGULAR_SUBSIDY_KWH_SEZON = -203;
	public static final int REGULAR_SUBSIDY_COMP_REFUGE = -204;
	public static final int REGULAR_SUBSIDY_COMP_REFUGE_SEZON = -205;
	//
	// resturcturization
	//
	public static final int UNKNOWN_BALANCE_GAP = -300;
	//
	// Requiment options
	//
	public static final int OPTIONAL = 0;
	public static final int NOT_REQUIERED_AT_ALL = 1;
	public static final int REQUIERED = 2;
	
	private int id;
	private int cycleRequiment;
	private int gelRequiment;
	private int kwhRequiment;
	private int readingRequiment;
	private int diffCategory;
	private Date endDate;
	private Date startDate;
	private String name;
	private OperationType type;
	private SubsidyAttachment subsidyAttachment;
	private boolean regular;

	public OperationType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getCycleRequiment() {
		return cycleRequiment;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getGelRequiment() {
		return gelRequiment;
	}

	public int getId() {
		return id;
	}

	public int getKwhRequiment() {
		return kwhRequiment;
	}

	public int getReadingRequiment() {
		return readingRequiment;
	}

	public Date getStartDate() {
		return startDate;
	}

	public SubsidyAttachment getSubsidyAttachment() {
		return subsidyAttachment;
	}

	public boolean isRegular() {
		return regular;
	}

	public void setRegular(boolean regular) {
		this.regular = regular;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCycleRequiment(int cycleRequiment) {
		this.cycleRequiment = cycleRequiment;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setGelRequiment(int gelRequiment) {
		this.gelRequiment = gelRequiment;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setKwhRequiment(int kwhRequiment) {
		this.kwhRequiment = kwhRequiment;
	}

	public void setReadingRequiment(int readingRequiment) {
		this.readingRequiment = readingRequiment;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

	public void setSubsidyAttachment(SubsidyAttachment subsidyAttachment) {
		this.subsidyAttachment = subsidyAttachment;
	}

	public int getDiffCategory() {
		return diffCategory;
	}

	public void setDiffCategory(int diffCategory) {
		this.diffCategory = diffCategory;
	}

	//
	// standard methods
	//

	/**
	 * compare using ID
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Operation))
			return false;
		Operation oper = (Operation) o;
		return id == oper.getId();
	}
	
	public int hashCode() {
		return id;
	}

}
