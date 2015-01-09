package telasi.recutil.service.eclipse.recalc;

import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * This is a request for checking of existence of the recalc
 * with given number for given customer.
 * 
 * @author dimitri
 */
public class CheckRecalcExistenceRequest extends DefaultEJBRequest {
	private static final long serialVersionUID = -64287894114787183L;

	private String recalcNumber;
	private int customerId;
	private boolean exists;

	public CheckRecalcExistenceRequest(String userName, String password) {
		super(ActionConstants.ACT_RECALC_CHECK_EXISTENCE, userName, password);
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getRecalcNumber() {
		return recalcNumber;
	}

	public void setRecalcNumber(String recalcNumber) {
		this.recalcNumber = recalcNumber;
	}

	public boolean exists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

}
