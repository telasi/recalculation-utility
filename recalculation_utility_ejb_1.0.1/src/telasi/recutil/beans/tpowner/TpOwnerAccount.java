package telasi.recutil.beans.tpowner;

import java.io.Serializable;

import telasi.recutil.beans.Account;

/**
 * Account extension for TP owners calculation.
 * 
 * @author dimakura
 */
public class TpOwnerAccount extends Account implements Serializable {
	private static final long serialVersionUID = 4583274989333937888L;

	public static final int STATUS_NORMAL                 = 1;
	public static final int STATUS_CYCLE_COMPLETED        = 2;
	public static final int STATUS_CALCULATED             = 3;
	public static final int STATUS_CALCULATED_WITH_ERRORS = 4;
	public static final int STATUS_SENDED                 = 5;

	private TransformatorType transType;
	private int statusId;
	private boolean high;
	private boolean low;

	public TransformatorType getTransformatorType() {
		return transType;
	}

	public void setTransformatorType(TransformatorType type) {
		this.transType = type;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public boolean isHigh() {
		return high;
	}

	public void setHigh(boolean high) {
		this.high = high;
	}

	public boolean isLow() {
		return low;
	}

	public void setLow(boolean low) {
		this.low = low;
	}
}