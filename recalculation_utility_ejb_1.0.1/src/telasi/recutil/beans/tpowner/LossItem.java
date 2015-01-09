package telasi.recutil.beans.tpowner;

import java.io.Serializable;

/**
 * Loss item.
 * 
 * @author dimitri
 */
public class LossItem implements Serializable {
	private static final long serialVersionUID = 5136707422867625826L;

	private TransformatorType type;
	private double busyFrom;
	private double busyTo;
	private double busyLoss;

	public double getBusyFrom() {
		return busyFrom;
	}

	public void setBusyFrom(double busyFrom) {
		this.busyFrom = busyFrom;
	}

	public double getBusyLoss() {
		return busyLoss;
	}

	public void setBusyLoss(double busyLoss) {
		this.busyLoss = busyLoss;
	}

	public double getBusyTo() {
		return busyTo;
	}

	public void setBusyTo(double busyTo) {
		this.busyTo = busyTo;
	}

	public TransformatorType getType() {
		return type;
	}

	public void setType(TransformatorType type) {
		this.type = type;
	}
}