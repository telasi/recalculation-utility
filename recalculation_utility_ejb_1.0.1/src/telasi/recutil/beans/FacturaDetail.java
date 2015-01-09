package telasi.recutil.beans;

import java.io.Serializable;

/**
 * This detail is used for expansion in factura calculations.
 *
 * @author dimitri
 */
public class FacturaDetail implements Serializable {
	private static final long serialVersionUID = -2936864877943379244L;

	/**
	 * Is for original charge.
	 */
	private boolean original;

	/**
	 * Operation ID.
	 */
	private Operation operation;

	/**
	 * Id of the original item. -1 if not defined.
	 */
	private long originalItemId;

	/**
	 * Item date.
	 */
	private Date itemDate;

	/**
	 * Kwh.
	 */
	private double kwh;
	
	private transient String itemNumber;

	/**
	 * GEL.
	 */
	private double gel;

	public double getGel() {
		return gel;
	}

	public void setGel(double gel) {
		this.gel = gel;
	}

	public Date getItemDate() {
		return itemDate;
	}

	public void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}

	public double getKwh() {
		return kwh;
	}

	public void setKwh(double kwh) {
		this.kwh = kwh;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public boolean isOriginal() {
		return original;
	}

	public void setOriginal(boolean original) {
		this.original = original;
	}

	public long getOriginalItemId() {
		return originalItemId;
	}

	public void setOriginalItemId(long originalItemId) {
		this.originalItemId = originalItemId;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
}
