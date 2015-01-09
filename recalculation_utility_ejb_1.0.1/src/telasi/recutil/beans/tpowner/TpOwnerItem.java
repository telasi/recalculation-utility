package telasi.recutil.beans.tpowner;

import java.io.Serializable;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Charge;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.Operation;

/**
 * This item is used for Tp-Owners recalculation.
 * 
 * @author dimitri
 */
public class TpOwnerItem implements Serializable {
	private static final long serialVersionUID = -3746294640814701750L;

	private long id;
	private long itemId;
	private Date itemDate;
	private Operation operation;
	private boolean cycle;
	private String itemNumber;
	private double kwh;
	private double gel;
	private double kwhCorrected;
	private Long correctedFrom;
	private TpOwnerRecalc recalculation;
	private Account account;
	private Date previousChargeDate;
	private Charge calculatedCharge;
	private Integer baseTariffId;

	public Long getCorrectedFrom() {
		return correctedFrom;
	}

	public void setCorrectedFrom(Long correctedFrom) {
		this.correctedFrom = correctedFrom;
	}

	public boolean isCycle() {
		return cycle;
	}

	public void setCycle(boolean cycle) {
		this.cycle = cycle;
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

	public double getKwhCorrected() {
		return kwhCorrected;
	}

	public void setKwhCorrected(double kwhCorrected) {
		this.kwhCorrected = kwhCorrected;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public TpOwnerRecalc getRecalculation() {
		return recalculation;
	}

	public void setRecalculation(TpOwnerRecalc recalculation) {
		this.recalculation = recalculation;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getPreviousChargeDate() {
		return previousChargeDate;
	}

	public void setPreviousChargeDate(Date previousChargeDate) {
		this.previousChargeDate = previousChargeDate;
	}

	public Charge getCalculatedCharge() {
		return calculatedCharge;
	}

	public void setCalculatedCharge(Charge calculatedCharge) {
		this.calculatedCharge = calculatedCharge;
	}

	public Integer getBaseTariffId() {
		return baseTariffId;
	}

	public void setBaseTariffId(Integer baseTariffId) {
		this.baseTariffId = baseTariffId;
	}

}