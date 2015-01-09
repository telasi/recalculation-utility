package telasi.recutil.beans.tpowner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Recalculation result object.
 * 
 * @author dimitri
 */
public class TpOwnerRecalcResult implements Serializable {
	private static final long serialVersionUID = -2316326825711361794L;

	private List /*TpOwnerCorrection*/ corrections = new ArrayList();
	
	public List getCorrections() {
		return corrections;
	}
}