package telasi.recutil.beans;

/**
 * Customer with left amount.
 * 
 * @author dimitri
 */
public class Customer2 extends Customer {
	private static final long serialVersionUID = -7725935407232558153L;
	private double leftAmount;

	public double getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(double leftAmount) {
		this.leftAmount = leftAmount;
	}
}
