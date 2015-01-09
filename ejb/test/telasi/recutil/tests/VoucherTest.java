package telasi.recutil.tests;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import telasi.recutil.beans.CalculationItem;
import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.DiffSummary;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.calc.calc07.Calculator;
import telasi.recutil.calc.calc07.DbUtilities;
import telasi.recutil.ejb.Request;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.E1RecalcManager;

public class VoucherTest extends TestCase {

	public static final double PRECISION = 0.0099f;

	public static Test suite() {
		DbUtilities.DEBUG_MODE = true;
		TestSuite suite = new TestSuite();
		suite.addTest(new VoucherTest("myTests"));
		suite.addTest(new VoucherTest("marika01"));
		suite.addTest(new VoucherTest("marika02"));
		suite.addTest(new VoucherTest("marika03"));
		suite.addTest(new VoucherTest("marika04"));
		suite.addTest(new VoucherTest("marika05"));
		suite.addTest(new VoucherTest("marika06"));
		suite.addTest(new VoucherTest("marika07"));
		suite.addTest(new VoucherTest("marika08"));
		return suite;
	}

	public VoucherTest(String name) {
		super(name);
	}

	public void myTests() {
		testVoucherById(6025); // accnumb=0001777 - #01
		testVoucherById(6036); // accnumb=0001777 - #02
		testVoucherById(6037); // accnumb=0001777 - #03
		testVoucherById(6038); // accnumb=0001777 - #04
		testVoucherById(6113); // accnumb=0001777 - #05
		testVoucherById(18476); // accnumb=0001777 - #aa020017
	}

	public void marika01() {
		testVoucherById(5582); // accnumb=2041695 - #1
		testVoucherById(5584); // accnumb=3260688 - #1
		testVoucherById(5587); // accnumb=4202026 - #1
		testVoucherById(5589); // accnumb=2005751 - #1
		testVoucherById(5591); // accnumb=3234155 - #1
		testVoucherById(5592); // accnumb=2069843 - #1
		testVoucherById(5612); // accnumb=3600259 - #1
		testVoucherById(5535); // accnumb=4345220 - #1
		testVoucherById(5362); // accnumb=2040384 - #1
		testVoucherById(5397); // accnumb=2178510 - #1
	}

	public void marika02() {
		testVoucherById(5647); // accnumb=2005216 - #1
		testVoucherById(5648); // accnumb=3575205 - #1
		testVoucherById(5360); // accnumb=3248587 - #1
		testVoucherById(5682); // accnumb=1996610 - #1
		testVoucherById(5389); // accnumb=3582796 - #1
		testVoucherById(5713); // accnumb=2030885 - #1
		testVoucherById(5588); // accnumb=2070323 - #1
		testVoucherById(2791); // accnumb=0697838 - #1
		testVoucherById(5354); // accnumb=0871685 - #1
		testVoucherById(5356); // accnumb=0866637 - #1
	}

	public void marika03() {
		testVoucherById(5386); // accnumb=0673630 - #1
		testVoucherById(5074); // accnumb=1521437 - #1
		testVoucherById(5379); // accnumb=0673649 - #1
		testVoucherById(5636); // accnumb=0146960 - #1
		testVoucherById(4923); // accnumb=0168179 - #1
		testVoucherById(5641); // accnumb=0302121 - #1
		testVoucherById(5643); // accnumb=0823808 - #1
		testVoucherById(5755); // accnumb=3271015 - #1
		testVoucherById(5756); // accnumb=1918241 - #1
		testVoucherById(5757); // accnumb=2997467 - #1
	}

	public void marika04() {
		testVoucherById(6402); // accnumb=1806950 - #1
		testVoucherById(6417); // accnumb=1728820 - #2
		testVoucherById(6425); // accnumb=1566558 - #1
		testVoucherById(6462); // accnumb=2507004 - #1
		testVoucherById(6450); // accnumb=1789078 - #1
		testVoucherById(6632); // accnumb=2253698 - #1
		testVoucherById(6684); // accnumb=4166538 - #1
		testVoucherById(6692); // accnumb=1460261 - #1
		testVoucherById(6704); // accnumb=1625138 - #1
		testVoucherById(6728); // accnumb=2538409 - #2
	}

	public void marika05() {
		testVoucherById(6736); // accnumb=2538294 - #1
		testVoucherById(7469); // accnumb=3915179 - #2
		testVoucherById(6983); // accnumb=2003478 - #1
		testVoucherById(7742); // accnumb=1783779 - #1
		testVoucherById(7600); // accnumb=2395241 - #1
		testVoucherById(8159); // accnumb=0204709 - #1
		testVoucherById(8151); // accnumb=0169533 - #1
		testVoucherById(7829); // accnumb=0234491 - #1
		testVoucherById(8318); // accnumb=1630523 - #1
		testVoucherById(7016); // accnumb=0187568 - #1
	}

	public void marika06() {
		testVoucherById(7058); // accnumb=1531211 - #1
		testVoucherById(7735); // accnumb=0462252 - #01
		testVoucherById(8578); // accnumb=1414133 - #3
		testVoucherById(8722); // accnumb=4044359 - #1
		testVoucherById(8235); // accnumb=0954391 - #1
		testVoucherById(9089); // accnumb=2461614 - #1
		testVoucherById(9134); // accnumb=2461614 - #2
		testVoucherById(9324); // accnumb=0122 - #1
		testVoucherById(9400); // accnumb=0755338 - #1
		testVoucherById(9400); // accnumb=0755338 - #1
		testVoucherById(7047); // accnumb=2693712 - #2
	}

	public void marika07() {
		testVoucherById(9778); // accnumb=0140029 - #1
		testVoucherById(9816); // accnumb=1525022 - #1
		testVoucherById(10882); // accnumb=2578446 - #2
		testVoucherById(13253); // accnumb=2205063 - #2
		testVoucherById(13659); // accnumb=1565915 - #00020005
		testVoucherById(15686); // accnumb=1646543 - #00290124
		testVoucherById(16338); // accnumb=0806934 - #00020099
		testVoucherById(22291); // accnumb=2464915 - #00280559
		testVoucherById(22763); // accnumb=0746704 - #00020425
		testVoucherById(23200); // accnumb=1727279 - #00280598
	}

	public void marika08() {
		testVoucherById(23006); // accnumb=A1576 - #00020430
		testVoucherById(23556); // accnumb=3710005 - #00150337
		testVoucherById(24646); // accnumb=4289353 - #aa020043
		testVoucherById(27191); // accnumb=0001777 - #aa020051
		testVoucherById(27248); // accnumb=0001777 - #aa020052
		testVoucherById(28457); // accnumb=2526993 - #00270368
		testVoucherById(63328); // accnumb=4089337 - 00041092
		// -- 3 left
	}

	/**
	 * Test recalculation againist the saved voucher.
	 * 
	 * @param recalcId
	 *            recalculation ID
	 */
	public static void testVoucherById(int recalcId) {
		Session session = new Session();
		Session.setUseDirectConnection(true);
		Recalc recalc = null;
		RecalcVoucher voucher = null;
		try {
			recalc = E1RecalcManager.getRecalcById(recalcId, Request.FINAL, session);
			voucher = E1RecalcManager.selectVoucher(recalc, session);
			session.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Assert.assertNotNull(recalc);
		Assert.assertNotNull(voucher);
		DiffSummary summary = null;
		try {
			Calculator c = new Calculator();
			c.calculate(session, recalc);
			summary = c.getDiffSummary();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Assert.assertNotNull(summary);
		Assert.assertEquals(summary.getDetails().size(), voucher.getDetails().size());
		for (int i = 0; i < summary.getDetails().size(); i++) {
			DiffDetail det = (DiffDetail) summary.getDetails().get(i);
			CalculationItem item = det.getOriginalItem();
			double kwh = item.getCharge().getKwh();
			double gel = item.getCharge().getGel();
			int operId = item.getOperation().getId();
			boolean found = false;
			for (int j = 0; j < voucher.getDetails().size(); j++) {
				DiffDetail vouchdet = (DiffDetail) voucher.getDetails().get(j);
				if (vouchdet.getOperation().getId() == operId) {
					double kwh1 = vouchdet.getOriginalItem().getCharge().getKwh();
					double gel1 = vouchdet.getOriginalItem().getCharge().getGel();
					Assert.assertEquals(kwh, kwh1, PRECISION);
					Assert.assertEquals(gel, gel1, PRECISION);
					found = true;
					break;
				}
			}
			Assert.assertTrue(found);
		}
	}

}
