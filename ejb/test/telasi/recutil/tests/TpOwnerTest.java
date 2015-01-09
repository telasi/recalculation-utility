package telasi.recutil.tests;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import junit.framework.TestCase;
import telasi.recutil.beans.tpowner.TpOwnerAccount;
import telasi.recutil.beans.tpowner.TpOwnerCorrection;
import telasi.recutil.beans.tpowner.TpOwnerRecalc;
import telasi.recutil.beans.tpowner.TpOwnerRecalcResult;
import telasi.recutil.calc.calc08.TpOwnerUtils;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.TpOwnersManager;

public class TpOwnerTest extends TestCase {

	private Session createSession() {
		Session s = new Session();
		Session.setUseDirectConnection(true);
		return s;
	}

	public void testTpOwner() throws Exception {
		Session s = createSession();
		Calendar cal = Calendar.getInstance();
		cal.set(2011, Calendar.AUGUST, 22, 0, 0);
		Date d = new Date(cal.getTimeInMillis());
		List accounts = TpOwnersManager.getTpOwnerAccounts(d, s);
		TpOwnerAccount accnt = (TpOwnerAccount) accounts.get(1);
		TpOwnerRecalc recalc = TpOwnersManager.generateTpOwnerRecalc(accnt, d, s);
		TpOwnerRecalcResult result = TpOwnerUtils.calculate(s, recalc);
		List corrections = result.getCorrections();
		for (int i = 0; i < corrections.size(); i++) {
			TpOwnerCorrection corr = (TpOwnerCorrection) corrections.get(i);
			System.out.println(corr.getKwh() + " / " + corr.getMainItem().getKwh());
		}
	}

}
