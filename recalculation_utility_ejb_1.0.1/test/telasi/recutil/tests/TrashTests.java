package telasi.recutil.tests;

import java.util.List;

import junit.framework.TestCase;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.TrashSubsidy;
import telasi.recutil.calc.calc12.TrashUtils;
import telasi.recutil.service.Session;

public class TrashTests extends TestCase {

	public void testGelCalculation() {
		assertEquals(0.00, TrashUtils.calculateStandardGel(100, null, null), 0.001);
		assertEquals(0.00, TrashUtils.calculateStandardGel(100, null, null), 0.001);
		assertEquals(2.50, TrashUtils.calculateStandardGel(100, new Date(2013, 01, 01), null), 0.001);
		assertEquals(2.50, TrashUtils.calculateStandardGel(100, new Date(2012, 11, 01), new Date(2012, 11, 01)), 0.001);
		assertEquals(5.00, TrashUtils.calculateStandardGel(100, new Date(2012, 10, 31), new Date(2012, 10, 31)), 0.001);
		assertEquals(2.50, TrashUtils.calculateStandardGel(100, new Date(2012, 10, 31), new Date(2012, 11, 01)), 0.001);
		assertEquals(3.33, TrashUtils.calculateStandardGel(100, new Date(2012, 10, 30), new Date(2012, 11, 02)), 0.001);
		assertEquals(2.50, TrashUtils.calculateStandardGel(100, new Date(2012, 10, 31), new Date(2012, 11, 03)), 0.001);
		assertEquals(13.91, TrashUtils.calculateStandardGel(301, new Date(2012, 10, 03), new Date(2012, 11, 05)), 0.001);
		assertEquals(5.00, TrashUtils.calculateStandardGel(100, new Date(2011, 07, 15), new Date(2012, 8, 5)), 0.001);
		assertEquals(2.40, TrashUtils.calculateStandardGel(53, new Date(2012, 10, 5), new Date(2012, 11, 6)), 0.001);
		assertEquals(8.20, TrashUtils.calculateStandardGel(181, new Date(2012, 10, 5), new Date(2012, 11, 6)), 0.001);
	}

	public void testBetweenRelation() {
		assertTrue(TrashUtils.isBetween(null, null, null));
		assertTrue(TrashUtils.isBetween(null, new Date(2012, 1, 1), new Date(2012, 1, 1)));
		assertTrue(TrashUtils.isBetween(null, new Date(2012, 1, 1), new Date(2011, 1, 1)));
		assertFalse(TrashUtils.isBetween(null, new Date(2012, 1, 1), new Date(2013, 1, 1)));
		assertTrue(TrashUtils.isBetween(new Date(2011, 1, 1), new Date(2012, 1, 1), new Date(2011, 2, 1)));
		assertFalse(TrashUtils.isBetween(new Date(2011, 1, 1), new Date(2012, 1, 1), new Date(2014, 1, 1)));
	}

	public void testTrashSubsidies() throws Exception {
		Session.setUseDirectConnection(true);
		Session session = new Session();
		List subsidies = TrashSubsidy.getSubsidies(session, 121108);
		assertFalse(subsidies.isEmpty());
		assertEquals(TrashSubsidy.class, subsidies.get(0).getClass());
	}

}
