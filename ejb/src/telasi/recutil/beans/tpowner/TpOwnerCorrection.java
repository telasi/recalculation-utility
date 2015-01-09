package telasi.recutil.beans.tpowner;

import java.io.Serializable;
import telasi.recutil.beans.Date;

/**
 * Cycle correction class.
 * 
 * @author dimitri
 */
public class TpOwnerCorrection implements Serializable {
	private static final long serialVersionUID = -82847164559491783L;

	public static final int DISCHARGE = 1;
	public static final int RECHARGE = 2;

	private Date startDate;
	private Date endDate;
	private double kwh;
	private double gel;
	private int type;
	private TpOwnerItem mainItem;

	public double getGel() {
		return gel;
	}

	public void setGel(double gel) {
		this.gel = gel;
	}

	public double getKwh() {
		return kwh;
	}

	public void setKwh(double kwh) {
		this.kwh = kwh;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public TpOwnerItem getMainItem() {
		return mainItem;
	}

	public void setMainItem(TpOwnerItem mainItem) {
		this.mainItem = mainItem;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}