package telasi.recutil.beans.tpowner;

import java.io.Serializable;
import java.util.List;

/**
 * Transformator types.
 * 
 * @author dimakura
 */
public class TransformatorType implements Serializable {
	private static final long serialVersionUID = -4771695525630180502L;

	private int id;
	private String name;
	private double power;
	private List /*LossItem*/ lossItems;
	private double zeroLoss;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public List getLossItems() {
		return lossItems;
	}

	public void setLossItems(List lossItems) {
		this.lossItems = lossItems;
	}

	public double getZeroLoss() {
		return zeroLoss;
	}

	public void setZeroLoss(double zeroLoss) {
		this.zeroLoss = zeroLoss;
	}
}