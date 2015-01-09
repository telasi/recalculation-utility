/*
 *   Copyright (C) 2006 by JSC Telasi
 *   http://www.telasi.ge
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the
 *   Free Software Foundation, Inc.,
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package telasi.recutil.service.eclipse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.beans.SubsidyAttachment;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.Response;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.bop.BillOperationSelectRequest;
import telasi.recutil.utils.CoreMessages;

/**
 * This class is used for billing operations managment.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class E1BOPManagment {

	//
	// EJB interaction part
	//

	/**
	 * Process request on Billoperation SELECT.
	 */
	public static Response processBilloperationSelect(Request req, Session session)
	throws RequestException {
		try {
			BillOperationSelectRequest request = (BillOperationSelectRequest) req;
			request.setTypes(selectBillingOperations(session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	//
	// Database part
	//

	public static List selectBillingOperations(Session session)
	throws SQLException, IOException {
		String sql = SQLReader.readSQL("bop/bop_select.sql");
		ResultSet res = null;
		try {

			res = session.getConnection().prepareStatement(sql).executeQuery();

			// initialize types
			List types = new ArrayList();

			// loop over all operations
			OperationType type = null;
			while (res.next()) {
				int typeId = res.getInt("TYPE_ID");
				Number operIdNumber = (Number) res.getObject("OPER_ID");
				boolean createNew = type == null || type.getId() != typeId;
				boolean operExists = operIdNumber != null;
				if (createNew) {
					if (type != null) {
						types.add(type);
					}
					type = new OperationType();
					type.setId(typeId);
					type.setName(res.getString("TYPE_NAME").trim());
					switch (typeId) {
					case OperationType.ADD_CHARGE:
						populateWithAddCharges(type);
						break;
					case OperationType.SUBSIDY:
						populateWithSubsidies(type);
						break;
					case OperationType.DEBT_RESTRUCTURIZATION:
						populateWithResturcts(type);
						break;
					}
				}
				if (operExists) {
					int operId = operIdNumber.intValue();
					String operName = res.getString("OPER_NAME").trim();
					Date d1 = Date.create(res.getTimestamp("OPER_START"));
					Date d2 = Date.create(res.getTimestamp("OPER_END"));
					int cycleReq = res.getInt("OPER_REQ_CYCLE");
					int readingReq = res.getInt("OPER_REQ_READING");
					int kwhReq = res.getInt("OPER_REQ_KWH");
					int gelReq = res.getInt("OPER_REQ_GEL");
					int diffCatId = res.getInt("OPER_DIFF_GROUP_ID");
					Operation oper = new Operation();
					oper.setId(operId);
					oper.setName(operName);
					oper.setStartDate(d1);
					oper.setEndDate(d2);
					oper.setCycleRequiment(cycleReq);
					oper.setReadingRequiment(readingReq);
					oper.setKwhRequiment(kwhReq);
					oper.setGelRequiment(gelReq);
					oper.setRegular(false);
					oper.setDiffCategory(diffCatId);
					Number attUnit1 = (Number) res.getObject("ATT_UNIT1");
					if (attUnit1 != null) {
						Number attRecalc = (Number) res
								.getObject("ATT_RECALCULABLE");
						Number attAmount1 = (Number) res
								.getObject("ATT_AMOUNT1");
						Number attUnit2 = (Number) res.getObject("ATT_UNIT2");
						Number attAmount2 = (Number) res
								.getObject("ATT_AMOUNT2");
						SubsidyAttachment att = new SubsidyAttachment();
						att.setRecalculable(attRecalc.intValue() == 0);
						if (attUnit2 == null) {
							att.setUnits(new int[] { attUnit1.intValue() });
							att.setAmounts(new double[] { attAmount1
									.doubleValue() });
							att.setAmounts2(null);
						} else {
							att.setUnits(new int[] { attUnit1.intValue(),
									attUnit2.intValue() });
							att.setAmounts(new double[] {
									attAmount1.doubleValue(),
									attAmount2.doubleValue() });
							att.setAmounts2(null);
						}
						oper.setSubsidyAttachment(att);
					}
					type.addOperation(oper);
				}
			}
			if (type != null) {
				types.add(type);
			}

			// return results
			return types;

		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (Exception ex) {
				}
				res = null;
			}
		}
	}

	private static void populateWithResturcts(OperationType type) {
		if (type.getId() != OperationType.DEBT_RESTRUCTURIZATION) return;
		{
			int id = Operation.UNKNOWN_BALANCE_GAP;
			String name = CoreMessages
					.getMessage("oper.restruct.unknown_balance_gap");
			Operation oper = new Operation();
			{
				oper.setId(id);
				oper.setName(name);
				oper.setRegular(false);
				oper.setReadingRequiment(Operation.NOT_REQUIERED_AT_ALL);
				oper.setKwhRequiment(Operation.NOT_REQUIERED_AT_ALL);
				oper.setGelRequiment(Operation.REQUIERED);
				oper.setCycleRequiment(Operation.NOT_REQUIERED_AT_ALL);
				oper.setSubsidyAttachment(null);
			}
			oper.setDiffCategory(DiffDetail.BALANCE);
			type.addOperation(oper);
		}
	}

	private static void populateWithAddCharges(OperationType type) {
		if (type.getId() != OperationType.ADD_CHARGE)
			return;
		{
			int id = Operation.REGULAR_ADD_CHARGE_GENERAL;
			String name = CoreMessages
					.getMessage("oper.regular.add_charge.general");
			int units[] = { SubsidyAttachment.KWH,
					SubsidyAttachment.KWH_BY_DAY, SubsidyAttachment.GEL,
					SubsidyAttachment.PERCENT };
			double amounts1[] = { .0f, .0f, .0f, .0f };
			Operation operation = createRegularOperation(id, name, units,
					amounts1, null);
			operation.setDiffCategory(DiffDetail.CHARGE);
			type.addOperation(operation);
		}
		{
			int id = Operation.REGULAR_ADD_CHARGE_REGULAR;
			String name = CoreMessages
					.getMessage("oper.regular.addcharge.sezon");
			int units[] = { SubsidyAttachment.KWH };
			double amounts1[] = { .0f };
			double amounts2[] = { .0f };
			Operation operation = createRegularOperation(id, name, units,
					amounts1, amounts2);
			operation.setDiffCategory(DiffDetail.CHARGE);
			type.addOperation(operation);
		}
		// {
		// int id = IOperation.REGULAR_ADD_CHARGE_ATMOR;
		// String name = "oper.regular.add_charge.atmor";
		// int units[] = { ISubsidyAttachment.KWH };
		// double amounts1[] = { .0f };
		// Operation operation = createRegularOperation(id, name, units,
		// amounts1, null);
		// operation.setDiffCategory(IDiffDetail.CHARGE);
		// type.addOperation(operation);
		// }
	}

	private static void populateWithSubsidies(OperationType type) {
		if (type.getId() != OperationType.SUBSIDY)
			return;
		{
			int id = Operation.REGULAR_SUBSIDY_GENERAL;
			String name = CoreMessages
					.getMessage("oper.regular.subsidy.general");
			int units[] = { SubsidyAttachment.KWH,
					SubsidyAttachment.KWH_BY_DAY, SubsidyAttachment.GEL };
			double amounts1[] = { .0f, .0f, .0f };
			Operation operation = createRegularOperation(id, name, units,
					amounts1, null);
			operation.setDiffCategory(DiffDetail.SUBSIDY_GENERAL);
			type.addOperation(operation);
		}
		{
			int id = Operation.REGULAR_SUBSIDY_EMPLOYEE;
			String name = CoreMessages
					.getMessage("oper.regular.subsidy.employee");
			int units[] = { SubsidyAttachment.KWH, SubsidyAttachment.GEL };
			double amounts1[] = { .0f, .0f };
			Operation operation = createRegularOperation(id, name, units,
					amounts1, null);
			operation.setDiffCategory(DiffDetail.SUBSIDY_EMPLOYEE);
			type.addOperation(operation);
		}
		{
			int id = Operation.REGULAR_SUBSIDY_PERCENT;
			String name = CoreMessages.getMessage("oper.regular.subsidy.percent");
			int units[] = { SubsidyAttachment.PERCENT };
			double amounts1[] = { .0f };
			Operation operation = createRegularOperation(id, name, units,
					amounts1, null);
			operation.setDiffCategory(DiffDetail.SUBSIDY_PERCENT);
			type.addOperation(operation);
		}
		{
			int id = Operation.REGULAR_SUBSIDY_KWH_SEZON;
			String name = CoreMessages.getMessage("oper.regular.subsidy.sezon");
			int units[] = { SubsidyAttachment.KWH };
			double amounts1[] = { .0f };
			double amounts2[] = { .0f };
			Operation operation = createRegularOperation(id, name, units,
					amounts1, amounts2);
			operation.setDiffCategory(DiffDetail.SUBSIDY_GENERAL);
			type.addOperation(operation);
		}
		{
			int id = Operation.REGULAR_SUBSIDY_COMP_REFUGE;
			String name = CoreMessages.getMessage("oper.regular.subsidy.compRefuge");
			int units[] = { SubsidyAttachment.GEL };
			double amounts1[] = { .0f };
			Operation operation = createRegularOperation(id, name, units, amounts1, null);
			operation.setDiffCategory(DiffDetail.SUBSIDY_COMP_REFUGE);
			type.addOperation(operation);
		}
		{
			int id = Operation.REGULAR_SUBSIDY_COMP_REFUGE_SEZON;
			String name = CoreMessages.getMessage("oper.regular.subsidy.compRefugeSezon");
			int units[] = { SubsidyAttachment.GEL };
			double amounts1[] = { .0f };
			double amounts2[] = { .0f };
			Operation operation = createRegularOperation(id, name, units, amounts1, amounts2);
			operation.setDiffCategory(DiffDetail.SUBSIDY_COMP_REFUGE);
			type.addOperation(operation);
		}
	}

	private static Operation createRegularOperation(int id, String name,
			int[] units, double[] amounts1, double[] amounts2) {
		Operation oper = new Operation();
		oper.setId(id);
		oper.setName(name);
		oper.setRegular(true);
		oper.setReadingRequiment(Operation.OPTIONAL);
		oper.setKwhRequiment(Operation.OPTIONAL);
		oper.setGelRequiment(Operation.OPTIONAL);
		oper.setCycleRequiment(Operation.OPTIONAL);
		SubsidyAttachment att = new SubsidyAttachment();
		att.setRecalculable(true);
		att.setAmounts(amounts1);
		att.setAmounts2(amounts2);
		att.setUnits(units);
		oper.setSubsidyAttachment(att);
		return oper;
	}

}
