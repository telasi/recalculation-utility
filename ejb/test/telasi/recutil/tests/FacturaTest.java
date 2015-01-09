package telasi.recutil.tests;

import java.util.List;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.FacturaDetail;
import telasi.recutil.beans.Recalc;
import telasi.recutil.calc.calc07.Calculator;
import telasi.recutil.calc.calc07.DbUtilities;
import telasi.recutil.ejb.Request;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.E1RecalcManager;
import telasi.recutil.utils.CoreUtils;

public class FacturaTest extends TestCase {
	public static final float PRECISION = 0.0099f;

	public FacturaTest(String name) {
		super(name);
	}

	public static Test suite() {
		DbUtilities.DEBUG_MODE = true;
		TestSuite suite = new TestSuite();
		suite.addTest(new FacturaTest("factura01"));
		return suite;
	}

	public void factura01() {
		testFacturaById(14118); // accnumb=3313097 - #factura1
		testFacturaById(14117); // accnumb=4317509 - #factura2
		// testFacturaById(14122); // accnumb=4009771 - #factura3
		testFacturaById(14203); // accnumb=4021105 - #factura4
		testFacturaById(14204); // accnumb=4015489 - #factura5

		// not saved---testFacturaById(19595); // accnumb=3358262 - #00020212
	}

	public static void testFacturaById(int recalcId) {
		Session session = new Session();
		Session.setUseDirectConnection(true);
		Recalc recalc = null;
		List expansion = null;
		try {
			recalc = E1RecalcManager.getRecalcById(recalcId, Request.FINAL, session);
			expansion = E1RecalcManager.selectFacturaExpansion(recalcId, session);
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(recalc);
		Assert.assertNotNull(expansion);
		Assert.assertFalse(expansion.isEmpty());

		List newExpansion = null;
		try {
			Calculator c = new Calculator();
			c.calculate(session, recalc);
			newExpansion = c.getFacturaExpansion();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(newExpansion);
		Assert.assertFalse(newExpansion.isEmpty());
		Assert.assertEquals(expansion.size(), newExpansion.size());
		for (int i = 0; i < expansion.size(); i++) {
			FacturaDetail det = (FacturaDetail) expansion.get(i);
			boolean matchFound = false;
			for (int j = 0; j < newExpansion.size(); j++) {
				FacturaDetail det2 = (FacturaDetail) newExpansion.get(j);
				if (det.getOperation().getId() == det2.getOperation().getId() && Date.isEqual(det.getItemDate(), det2.getItemDate()) && det.getOriginalItemId() == det2.getOriginalItemId() && Math.abs(det.getKwh() - det2.getKwh()) < CoreUtils.MIN_KWH && Math.abs(det.getGel() - det2.getGel()) < CoreUtils.MIN_GEL) {
					matchFound = true;
					break;
				}
			}
			Assert.assertTrue(matchFound);
		}
	}

}
