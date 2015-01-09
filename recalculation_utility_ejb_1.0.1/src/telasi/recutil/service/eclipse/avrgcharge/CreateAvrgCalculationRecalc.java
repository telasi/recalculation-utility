/*
 * Copyright 2007, Dimitri Kurashvili (dimakura@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package telasi.recutil.service.eclipse.avrgcharge;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Recalc;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.service.ActionConstants;

/**
 * RECALCULATION select request for avearage charge calculation.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Jan, 2007
 */
public class CreateAvrgCalculationRecalc extends DefaultEJBRequest {

	private static final long serialVersionUID = 8441646727797685539L;

	private Account account;

	private Recalc recalc;

	public CreateAvrgCalculationRecalc(String userName, String password) {
		super(ActionConstants.OTHER_AVRG_CHARGE_RECALCULATION_SELECT, userName,
				password);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Recalc getRecalc() {
		return recalc;
	}

	public void setRecalc(Recalc recalc) {
		this.recalc = recalc;
	}

}
