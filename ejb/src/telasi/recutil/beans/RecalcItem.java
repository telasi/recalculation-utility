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

import telasi.recutil.utils.CoreUtils;

/**
 * Recalculation item.
 * @author dimakura
 *
 */
public class RecalcItem implements Serializable {
	private static final long serialVersionUID = 8310989989872015068L;

	//
	// Changes
	//
	public static final int CHANGE_OPERATION = 1;
	public static final int CHANGE_ITEMDATE = 2;
	public static final int CHANGE_ENTERDATE = 3;
	public static final int CHANGE_READING = 4;
	public static final int CHANGE_KWH = 5;
	public static final int CHANGE_GEL = 6;
	public static final int CHANGE_CYCLE = 7;
	
	//
	// Statuses
	//
	public static final int ORIGINAL = 1;
	public static final int OTHER = 2;
	public static final int NEW = 3;
	public static final int DELETED = 4;

	//
	// Hints
	//

	/**
	 * This hint is used for default calculation parameters.
	 */
	public static final int HINT_NONE = 0;

	/**
	 * This is a strong hint. It preserves both kWh and GEL.
	 */
	public static final int HINT_PRESERVE_BOTH = 1;

	/**
	 * Use existing kWh as a base when deriving kWh using installed capacity.
	 * This hint doesnot neccessarily means that kWh will be saved. It has no
	 * effect when applied to the reading. Also kWh can be changed when
	 * calculating installed capacity on cutted period.
	 */
	public static final int HINT_DERIVE_FROM_EXISTING_KWH = 2;

	/**
	 * Calculating installed capacity using full inst.capacity history.
	 */
	public static final int HINT_USE_CONTINUOUS_BY_INSTCP = 3;

	/**
	 * This hint can be used on items which remain the same.
	 * But we need their discharge nevertheless. This situations
	 * are rare and you need to decide for youself whether to
	 * use this hint.
	 */
	public static final int HINT_FORCE_DISCHARGE = 4;

	public boolean equals(Object o) {
		if (!(o instanceof RecalcItem))
			return false;
		RecalcItem item = (RecalcItem) o;
		return item.getId() == id;
	}

	private long id;
	private RecalcInterval interval;
	private long itemId;
	private Customer customer;
	private Account account;
	private Operation operation;
	private Operation originalOperation;
	private String itemNumber;
	private double reading;
	private double originalReading;
	private double kwh;
	private double originalKwh;
	private double gel;
	private double originalGel;
	private double balance;
	private double originalBalance;
	private Date itemDate;
	private Date originalItemDate;
	private Date enterDate;
	private Date originalEnterDate;
	private boolean cycle;
	private boolean originalCycle;
	private double meterCoeff;
	private double originalMeterCoeff;
	private boolean meterStatus;
	private boolean originalMeterStatus;
	private double meterAcceleration;
	private Meter meter;
	private Meter originalMeter;
	private int status;
	private int calculationHint;
	private RecalcSubsidyAttachment subsidyAttachment;
	private RecalcSubsidyAttachment originalSubsidyAttachment;
	private double balanceGap;
	private double originalBalanceGap;
	private Date currentOperationDate;
	private Date previousOperationDate;
	private Account subAccount;

	public int getCalculationHint() {
		return calculationHint;
	}

	public void setCalculationHint(int option) {
		this.calculationHint = option;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBalanceGap() {
		return balanceGap;
	}

	public void setBalanceGap(double balanceGap) {
		this.balanceGap = balanceGap;
	}

	public Date getCurrentOperationDate() {
		return currentOperationDate;
	}

	public void setCurrentOperationDate(Date currentOperationDate) {
		this.currentOperationDate = currentOperationDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean getCycle() {
		return cycle;
	}

	public boolean getOriginalCycle() {
		return originalCycle;
	}

	public void setOriginalCycle(boolean originalCycle) {
		this.originalCycle = originalCycle;
	}

	public void setCycle(boolean cycle) {
		this.cycle = cycle;
	}

	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	public double getGel() {
		return gel;
	}

	public void setGel(double gel) {
		this.gel = gel;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RecalcInterval getInterval() {
		return interval;
	}

	public void setInterval(RecalcInterval interval) {
		this.interval = interval;
	}

	public Date getItemDate() {
		return itemDate;
	}

	public void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public double getKwh() {
		return kwh;
	}

	public void setKwh(double kwh) {
		this.kwh = kwh;
	}

	public Meter getMeter() {
		return meter;
	}

	public void setMeter(Meter meter) {
		this.meter = meter;
	}

	public double getMeterCoeff() {
		return meterCoeff;
	}

	public void setMeterCoeff(double meterCoeff) {
		this.meterCoeff = meterCoeff;
	}

	public boolean getMeterStatus() {
		return meterStatus;
	}

	public void setMeterStatus(boolean meterStatus) {
		this.meterStatus = meterStatus;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public double getOriginalBalance() {
		return originalBalance;
	}

	public void setOriginalBalance(double originalBalance) {
		this.originalBalance = originalBalance;
	}

	public double getOriginalBalanceGap() {
		return originalBalanceGap;
	}

	public void setOriginalBalanceGap(double originalBalanceGap) {
		this.originalBalanceGap = originalBalanceGap;
	}

	public Date getOriginalEnterDate() {
		return originalEnterDate;
	}

	public void setOriginalEnterDate(Date originalEnterDate) {
		this.originalEnterDate = originalEnterDate;
	}

	public double getOriginalGel() {
		return originalGel;
	}

	public void setOriginalGel(double originalGel) {
		this.originalGel = originalGel;
	}

	public Date getOriginalItemDate() {
		return originalItemDate;
	}

	public void setOriginalItemDate(Date originalItemDate) {
		this.originalItemDate = originalItemDate;
	}

	public double getOriginalKwh() {
		return originalKwh;
	}

	public void setOriginalKwh(double originalKwh) {
		this.originalKwh = originalKwh;
	}

	public Meter getOriginalMeter() {
		return originalMeter;
	}

	public void setOriginalMeter(Meter originalMeter) {
		this.originalMeter = originalMeter;
	}

	public double getOriginalMeterCoeff() {
		return originalMeterCoeff;
	}

	public void setOriginalMeterCoeff(double originalMeterCoeff) {
		this.originalMeterCoeff = originalMeterCoeff;
	}

	public boolean getOriginalMeterStatus() {
		return originalMeterStatus;
	}

	public void setOriginalMeterStatus(boolean originalMeterStatus) {
		this.originalMeterStatus = originalMeterStatus;
	}

	public Operation getOriginalOperation() {
		return originalOperation;
	}

	public void setOriginalOperation(Operation originalOperation) {
		this.originalOperation = originalOperation;
	}

	public double getOriginalReading() {
		return originalReading;
	}

	public void setOriginalReading(double originalReading) {
		this.originalReading = originalReading;
	}

	public Date getPreviousOperationDate() {
		return previousOperationDate;
	}

	public void setPreviousOperationDate(Date previousOperationDate) {
		this.previousOperationDate = previousOperationDate;
	}

	public double getReading() {
		return reading;
	}

	public void setReading(double reading) {
		this.reading = reading;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public RecalcSubsidyAttachment getSubsidyAttachment() {
		return subsidyAttachment;
	}

	public void setSubsidyAttachment(RecalcSubsidyAttachment subsidyAttachment) {
		this.subsidyAttachment = subsidyAttachment;
	}

	public boolean isChanged() {
		List changes = getChanges();
		return changes != null && !changes.isEmpty();
	}

	public List getChanges() {

		// new record
		if (getItemId() == -1 && !isSysRestruct()) {
			return null;
		}

		// other record
		if (this.getStatus() == RecalcItem.OTHER) {
			return null;
		}

		List changes = new ArrayList();

		// operation changed
		if (getOperation().getId() != getOriginalOperation().getId()) {
			Changes change = new Changes(CHANGE_OPERATION,
					getOriginalOperation(), getOperation());
			changes.add(change);
		}

		// itemdate changed
		if (!Date.isEqual(getItemDate(), getOriginalItemDate())) {
			Changes change = new Changes(CHANGE_ITEMDATE,
					getOriginalItemDate(), getItemDate());
			changes.add(change);
		}

		// enterdate changed
		if (!Date.isEqual(getEnterDate(), getOriginalEnterDate())) {
			Changes change = new Changes(CHANGE_ENTERDATE,
					getOriginalEnterDate(), getEnterDate());
			changes.add(change);
		}

		// reading changed
		if (Math.abs(getReading() - getOriginalReading()) >= CoreUtils.MIN_KWH) {
			Changes change = new Changes(CHANGE_READING, new Double(getOriginalReading()), new Double(getReading()));
			changes.add(change);
		}

		// kwh changed
		if (Math.abs(getKwh() - getOriginalKwh()) >= CoreUtils.MIN_KWH) {
			Changes change = new Changes(CHANGE_KWH, new Double(getOriginalKwh()), new Double(getKwh()));
			changes.add(change);
		}

		// gel changed
		if (Math.abs(getGel() - getOriginalGel()) >= CoreUtils.MIN_GEL) {
			Changes change = new Changes(CHANGE_GEL, new Double(getOriginalGel()), new Double(getGel()));
			changes.add(change);
		}

		// cycle change
		if (getCycle() != getOriginalCycle()) {
			Changes change = new Changes(CHANGE_CYCLE, new Boolean(
					getOriginalCycle()), new Boolean(getCycle()));
			changes.add(change);
		}

		// return changes list
		return changes;

	}

	public RecalcSubsidyAttachment getOriginalSubsidyAttachment() {
		return originalSubsidyAttachment;
	}

	public void setOriginalSubsidyAttachment(RecalcSubsidyAttachment originalSubsidyAttachment) {
		this.originalSubsidyAttachment = originalSubsidyAttachment;
	}

	public double getMeterAcceleration() {
		return meterAcceleration;
	}

	public void setMeterAcceleration(double meterAcceleration) {
		this.meterAcceleration = meterAcceleration;
	}

	public boolean isSysRestruct() {
		return getItemNumber() != null
				&& getItemNumber().startsWith("new_restruct");
	}

	public class Changes implements Serializable {
		private static final long serialVersionUID = -6639248496663555822L;

		private int code;

		private Object original, current;

		public Changes(int code, Object original, Object current) {
			this.code = code;
			this.current = current;
			this.original = original;
		}

		public int getCode() {
			return code;
		}

		public Object getCurrent() {
			return current;
		}

		public Object getOriginal() {
			return original;
		}

	}

	public Account getSubAccount() {
		return subAccount;
	}

	public void setSubAccount(Account subAccount) {
		this.subAccount = subAccount;
	}

}
