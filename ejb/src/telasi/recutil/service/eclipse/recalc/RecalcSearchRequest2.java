package telasi.recutil.service.eclipse.recalc;

import java.util.List;

import telasi.recutil.beans.Date;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * Expanded recalc search request.
 * 
 * @author dimakura
 */
public class RecalcSearchRequest2 extends DefaultEJBRequest {
	private static final long serialVersionUID = 3760368078865610174L;

	private Date startDate;
	private Date endDate;
	private boolean saveDate;
	private String recalcNumber;
	private List/*Recalc*/ results;
	
	public RecalcSearchRequest2(String userName, String password) {
		super(ActionConstants.ACT_RECALC_SEARCH, userName, password);
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRecalcNumber() {
		return recalcNumber;
	}

	public void setRecalcNumber(String recalcNumber) {
		this.recalcNumber = recalcNumber;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}

	public boolean isSaveDate() {
		return saveDate;
	}

	public void setSaveDate(boolean saveDate) {
		this.saveDate = saveDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
