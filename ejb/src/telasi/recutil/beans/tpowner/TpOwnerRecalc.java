package telasi.recutil.beans.tpowner;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Recalculation object for tpowner.
 * 
 * @author dimitri
 */
public class TpOwnerRecalc implements Serializable {
	private static final long serialVersionUID = -390935570649207334L;

	private TpOwnerAccount producer;
	private List /*TpOwnerItem*/ items = new ArrayList();
	private Date cycleDate;

	public List getItems() {
		return items;
	}

	public TpOwnerAccount getProducer() {
		return producer;
	}

	public void setProducer(TpOwnerAccount producer) {
		this.producer = producer;
	}

	public Date getCycleDate() {
		return cycleDate;
	}

	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}

}