package telasi.recutil.service.eclipse;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Item;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.beans.tpowner.LossItem;
import telasi.recutil.beans.tpowner.TpOwnerAccount;
import telasi.recutil.beans.tpowner.TpOwnerCorrection;
import telasi.recutil.beans.tpowner.TpOwnerItem;
import telasi.recutil.beans.tpowner.TpOwnerRecalc;
import telasi.recutil.beans.tpowner.TpOwnerRecalcResult;
import telasi.recutil.beans.tpowner.TransformatorType;
import telasi.recutil.calc.calc08.TpOwnerUtils;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.ejb.Response;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.tpowner.TpOwnerAccountSelectRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerAccountsSelectRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerCalculationRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerGenerateRecalcRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerRecalcCustomerSummaryRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerRecalcErrorRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerRecalcSaveRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerRecalcSavedResultsRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerSaveRequest;
import telasi.recutil.service.eclipse.tpowner.TpOwnerSendToItemRequest;
import telasi.recutil.service.eclipse.tpowner.TransformatorTypeSelectRequest;

/**
 * Tp owners manager.
 * 
 * @author dimitri
 */
public class TpOwnersManager {

	// --- Service ---

	public static Response processTpOwnerRecalculation(Request req, Session session) throws RequestException {
		try {
			TpOwnerCalculationRequest request = (TpOwnerCalculationRequest) req;
			TpOwnerRecalc recalc = request.getRecalculation();
			TpOwnerRecalcResult result = TpOwnerUtils.calculate(session, recalc);
			request.setResult(result);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTpOwnerAccountsSelect(Request req, Session session) throws RequestException {
		try {
			TpOwnerAccountsSelectRequest request = (TpOwnerAccountsSelectRequest) req;
			request.setAccounts(getTpOwnerAccounts(request.getCycleDate(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTpOwnerRecalcGeneration(Request req, Session session) throws RequestException {
		try {
			TpOwnerGenerateRecalcRequest request = (TpOwnerGenerateRecalcRequest) req;
			request.setRecalc(generateTpOwnerRecalc(request.getAccount(), request.getCycleDate(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTransformatorTypesSelect(Request req, Session session) throws RequestException {
		try {
			TransformatorTypeSelectRequest request = (TransformatorTypeSelectRequest) req;
			getTransType(session, 100);
//			for (int i = 0; i < TRANS_TYPES.size(); i++) {
//				TransformatorType type = (TransformatorType) TRANS_TYPES.get(i);
//				System.out.println(type.getName() + " -- " + type.getPower());
//			}
			request.setTypes(TRANS_TYPES);
			return new DefaultEJBResponse(request);			
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTpOwnerAccountSelect(Request req, Session session) throws RequestException {
		try {
			TpOwnerAccountSelectRequest request = (TpOwnerAccountSelectRequest) req;
			request.setTpowner(getTpOwnerAccount(request.getAccount(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTpOwnerSaveRequest(Request req, Session session) throws RequestException {
		try {
			TpOwnerSaveRequest request = (TpOwnerSaveRequest) req;
			saveTpOwnerAccount(request.getAccountId(), request.getTypeId(), request.getStatusId(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTpOwnerRecalcSaveRequest(Request req, Session session) throws RequestException {
		try {
			TpOwnerRecalcSaveRequest request = (TpOwnerRecalcSaveRequest) req;
			saveRecalculation(request.getAccountId(), request.getCycleDate(), request.getItems(), session);
			return new DefaultEJBResponse(null);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTpOwnerRecalcErrorRequest(Request req, Session session) throws RequestException {
		try {
			TpOwnerRecalcErrorRequest request = (TpOwnerRecalcErrorRequest) req;
			setAsError(request.getAccountId(), request.getCycleDate(), session);
			return new DefaultEJBResponse(null);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTpOwnerRecalcSavedResultsRequest(Request req, Session session) throws RequestException {
		try {
			TpOwnerRecalcSavedResultsRequest request = (TpOwnerRecalcSavedResultsRequest) req;
			List/*TpOwnerCorrection*/ results = getResults(request.getAccountId(), request.getCycleDate(), session);
			request.setItems(results);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}
	
	public static Response processTpOwnerRecalcCustomerSummary(Request req, Session session) throws RequestException {
		try {
			TpOwnerRecalcCustomerSummaryRequest request = (TpOwnerRecalcCustomerSummaryRequest) req;
			int customerId = request.getCustomer().getId();
			Date cycleDate = request.getCycleDate();
			List/*TpOwnerCorrection*/ items = getCustomerSummary(customerId, cycleDate, session);
			request.setItems(items);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}
	
	public static Response processSendToItem(Request req, long userId, Session session) throws RequestException {
		try {
			TpOwnerSendToItemRequest request = (TpOwnerSendToItemRequest) req;
			List/*Customer*/ customers = request.getCustomers();
			Date cycleDate = request.getCycleDate();
			sendToItem(customers, cycleDate, userId, session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}
	
	// --- SQL ---

	// transformator types cache
	private static List TRANS_TYPES = null;

	private static void resetTransCache(Session session) throws Exception {
		// try to refresh cache
		String sql1 = "SELECT ID, NAME, POWER FROM BS.TRANS_TYPES";
		String sql2 = "SELECT TYPE_ID, ZERO_LOSS, BUSY_FROM, BUSY_TO, BUSY_LOSS " 
			+ "FROM BS.TRANS_LOSSES ORDER BY TYPE_ID, BUSY_FROM";
		Statement st = null;
		ResultSet res = null;
		try {
			// getting headers
			st = session.getConnection().createStatement();
			res = st.executeQuery(sql1);
			while (res.next()) {
				if (TRANS_TYPES == null)
					TRANS_TYPES = new ArrayList();
				TransformatorType type = new TransformatorType();
				type.setId(res.getInt("ID"));
				type.setName(res.getString("NAME"));
				type.setPower(res.getDouble("POWER"));
				TRANS_TYPES.add(type);
			}
			// fill details
			res = st.executeQuery(sql2);
			TransformatorType type = null;
			while (res.next()) {
				int newTypeId = res.getInt("TYPE_ID");
				if (type == null || type.getId() != newTypeId) {
					type = null;
					for (int i = 0; i < TRANS_TYPES.size(); i++) {
						TransformatorType tp = (TransformatorType) TRANS_TYPES.get(i);
						if (tp.getId() == newTypeId) {
							type = tp;
							break;
						}
					}
					if (type == null)
						continue;
					type.setLossItems(new ArrayList());
					type.setZeroLoss(res.getDouble("ZERO_LOSS"));
				}
				LossItem item = new LossItem();
				item.setType(type);
				item.setBusyFrom(res.getDouble("BUSY_FROM"));
				item.setBusyTo(res.getDouble("BUSY_TO"));
				item.setBusyLoss(res.getDouble("BUSY_LOSS"));
				type.getLossItems().add(item);
			}
		} finally {
			try {st.close();} catch (Exception ex) {}
			try {res.close();} catch (Exception ex) {}
			st = null;
			res = null;
		}
	}

	// get transformator type parameters
	private static TransformatorType getTransType(Session session, int typeId) throws Exception {

		// try to find in cache
		if (TRANS_TYPES != null) {
			for (int i = 0; i < TRANS_TYPES.size(); i++) {
				TransformatorType type = (TransformatorType) TRANS_TYPES.get(i);
				if (type.getId() == typeId)
					return type;
			}
		}

		// reset cache
		if (TRANS_TYPES != null) {
			TRANS_TYPES.clear();
		}
		resetTransCache(session);

		// try to find in cache again
		if (TRANS_TYPES != null) {
			for (int i = 0; i < TRANS_TYPES.size(); i++) {
				TransformatorType type = (TransformatorType) TRANS_TYPES.get(i);
				if (type.getId() == typeId)
					return type;
			}
		}

		// nothing!
		return null;
	}

	// select tp-owner customers for the given date
	public static List getTpOwnerAccounts(Date cycleDate, Session session) throws Exception {
		String sql1 = "CALL RECUT.TP_OWNER_RECALC_MANAGER.make_search_cache(?)";
		String sql2 = SQLReader.readSQL("tpowner/tpowner_accounts_select.sql");
		CallableStatement call = null;
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			// prepare and validate cache
			call = session.getConnection().prepareCall(sql1);
			call.setDate(1, cycleDate);
			call.execute();
			// execute select
			st = session.getConnection().prepareStatement(sql2);
			st.setDate(1, cycleDate);
			res = st.executeQuery();
			List accounts = new ArrayList();
			while(res.next()) {
				TpOwnerAccount tpAccount = new TpOwnerAccount();
				tpAccount.setId(res.getInt("ACCKEY"));
				tpAccount.setNumber(res.getString("ACCID"));
				tpAccount.setStatusId(res.getInt("STATUS_ID"));
				tpAccount.setMeterNumber(res.getString("MTNUMB"));
				Customer customer = new Customer();
				customer.setId(res.getInt("CUSTKEY"));
				customer.setNumber(res.getString("ACCNUMB"));
				customer.setName(res.getString("CUSTNAME"));
				tpAccount.setCustomer(customer);
				TransformatorType type = new TransformatorType();
				type.setId(res.getInt("TYPE_ID"));
				tpAccount.setTransformatorType(type);
				int tariffId = res.getInt("CURRENT_TARIFF_ID");
				boolean isHigh = TpOwnerUtils.isHighVoltageTariff(tariffId);
				boolean isLow = TpOwnerUtils.isLowVoltageTariff(tariffId);
				tpAccount.setHigh(isHigh);
				tpAccount.setLow(isLow);
				accounts.add(tpAccount);
			}

			// update to full types
			for (int i = 0; i < accounts.size(); i++) {
				TpOwnerAccount account = (TpOwnerAccount) accounts.get(i);
				account.setTransformatorType(getTransType(session, account.getTransformatorType().getId()));
			}
			return accounts;
		} finally {
			if (call != null) {
				try {call.close();} catch (Exception ex2) {}
				call = null;
			}
			if (res != null) {
				try {res.close();} catch (Exception ex2) {}
				res = null;
			}
			if (st != null) {
				try {st.close();} catch (Exception ex2) {}
				st = null;
			}
		}
	}

	public static TpOwnerAccount getTpOwnerAccount(Account acc, Session session) throws Exception {
		String sql = "SELECT TYPE_ID, STATUS_ID FROM BS.TRANS_OWNER_PARAMS WHERE ACCOUNT_ID=?";
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, acc.getId());
			res = st.executeQuery();
			if (!res.next()) {
				return null;
			} else {
				TpOwnerAccount tpowner = new TpOwnerAccount();
				tpowner.setId(acc.getId());
				tpowner.setCreationDate(acc.getCreationDate());
				tpowner.setCustomer(acc.getCustomer());
				tpowner.setMain(acc.isMain());
				tpowner.setNumber(acc.getNumber());
				tpowner.setStatus(acc.getStatus());
				tpowner.setStatusId(res.getInt("STATUS_ID"));
				int typeId = res.getInt("TYPE_ID");
				TransformatorType type = getTransType(session, typeId);
				tpowner.setTransformatorType(type);
				return tpowner;
			}
		} finally {
			try {res.close();} catch (Exception ex) {}
			try {st.close();} catch (Exception ex) {}
			res = null;
			st = null;
		}
	}

	private static void saveTpOwnerAccount(int accountId, int typeId, int statusId, Session session) throws Exception {
		Account acc = new Account();
		acc.setId(accountId);
		TpOwnerAccount owner = getTpOwnerAccount(acc, session);
		String sql = owner == null ? "INSERT INTO BS.TRANS_OWNER_PARAMS (TYPE_ID, STATUS_ID, ACCOUNT_ID) VALUES (?, ?, ?)"
				: "UPDATE BS.TRANS_OWNER_PARAMS SET TYPE_ID=?, STATUS_ID=? WHERE ACCOUNT_ID = ?";
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, typeId);
			st.setInt(2, statusId);
			st.setInt(3, accountId);
			st.execute();
		} finally {
			try {st.close();} catch (Exception ex) {}
		}
	}

	// generate TP
	public static TpOwnerRecalc generateTpOwnerRecalc(TpOwnerAccount account, Date cycleDate, Session session) throws Exception {
		String sql1 = "CALL RECUT.TP_OWNER_RECALC_MANAGER.generate_calculation(?, ?)";
		String sql2 = SQLReader.readSQL("tpowner/tpowner_recalc_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		CallableStatement call = null;
		try {
			// call generation, it will generate recalc, if not yet generated
			call = session.getConnection().prepareCall(sql1);
			call.setDate(1, cycleDate);
			call.setInt(2, account.getId());
			call.execute();
			// make selection
			st = session.getConnection().prepareStatement(sql2);
			st.setDate(1, cycleDate);
			st.setInt(2, account.getCustomer().getId());
			st.setInt(3, account.getId());
			res = st.executeQuery();
			// create recalculation
			TpOwnerRecalc recalc = new TpOwnerRecalc();
			recalc.setProducer(account);
			recalc.setCycleDate(cycleDate);
			while (res.next()) {
				TpOwnerItem item = new TpOwnerItem();
				item.setRecalculation(recalc);
				item.setId(res.getLong("ID"));
				Account acc = new Account();
				acc.setId(res.getInt("ACCOUNT_ID"));
				acc.setNumber(res.getString("ACCID"));
				Customer cust = new Customer();
				cust.setId(res.getInt("CUSTOMER_ID"));
				cust.setName(res.getString("CUSTNAME"));
				cust.setNumber(res.getString("ACCNUMB"));
				acc.setCustomer(cust);
				item.setAccount(acc);
				item.setItemId(res.getLong("ITEM_ID"));
				item.setItemDate(telasi.recutil.beans.Date.create(res.getDate("ITEM_DATE")));
				item.setPreviousChargeDate(telasi.recutil.beans.Date.create(res.getDate("PREV_CHARGE_DATE")));
				Operation oper = new Operation();
				oper.setId(res.getInt("BILLOPERKEY"));
				item.setOperation(oper);
				item.setCycle(res.getInt("IS_CYCLE") == 1);
				item.setItemNumber(res.getString("ITEM_NUMBER"));
				item.setKwh(res.getDouble("KWH"));
				item.setGel(res.getDouble("GEL"));
				item.setKwhCorrected(res.getDouble("KWH_E_CORRECTED"));
				Number correctedFrom = (Number) res.getObject("ECORR_FROM");
				item.setCorrectedFrom(correctedFrom == null ? null : new Long(correctedFrom.longValue()));
				Number baseTariffId = (Number) res.getObject("BASE_TARIFF_ID");
				item.setBaseTariffId(baseTariffId == null ? null : new Integer(baseTariffId.intValue()));
				recalc.getItems().add(item);
			}
			List operations = E1BOPManagment.selectBillingOperations(session);
			for (int i = 0; recalc.getItems() != null && i < recalc.getItems().size(); i++) {
				TpOwnerItem item = (TpOwnerItem) recalc.getItems().get(i);
				int operId = item.getOperation().getId();
A:				for (int j = 0; operations != null && j < operations.size(); j++) {
						OperationType type = (OperationType) operations.get(j);
						for (int k = 0; type.getOperations() != null && k < type.getOperations().size(); k++) {
							Operation oper = (Operation) type.getOperations().get(k);
							if (oper.getId() == operId) {
								item.setOperation(oper);
								break A;
							}
						}
					}
				}
			return recalc;
		} finally {
			try {call.close();} catch(Exception ex) {}
			try {res.close();} catch(Exception ex) {}
			try {st.close();} catch(Exception ex) {}
			res = null;
			st = null;
			call = null;
		}
	}

	private static long getRecalcId(int accid, Date cycleDate, Session session, boolean validateOnSave) throws Exception {
		String sql1 = "SELECT ID, STATUS_ID FROM RECUT.TP_OWNER_SEARCH WHERE ACCOUNT_ID=? AND CYCLE_DATE=?";
		PreparedStatement st1 = null;
		ResultSet res1 = null;
		try {
			// get current status
			st1 = session.getConnection().prepareStatement(sql1);
			st1.setInt(1, accid);
			st1.setDate(2, cycleDate);
			res1 = st1.executeQuery();
			res1.next();
			int statusId = res1.getInt("STATUS_ID");
			long recalcId = res1.getLong("ID");
			if (validateOnSave && statusId == TpOwnerAccount.STATUS_SENDED) {
				throw new IllegalArgumentException("Already sended.");
			}
			return recalcId;
		} finally {
			try { st1.close(); res1.close(); } catch(Exception ex) {}
			st1 = null;
			res1 = null;
		}
	}

	// save calculation results and declare recalculation as calculated
	private static void saveRecalculation(int accid, Date cycleDate, List/*TpOwnerCorrection*/ results, Session session) throws Exception {
		long recalcId = getRecalcId(accid, cycleDate, session, true);
		String sql1 = "DELETE FROM RECUT.TP_OWNER_RECALC_RESULTS WHERE RECALC_ID=?";
		String sql2 = "INSERT INTO RECUT.TP_OWNER_RECALC_RESULTS(RECALC_ID, OPER_ID, START_DATE, " 
			+ "END_DATE, KWH, GEL) VALUES (?, ?, ?, ?, ?, ?)";
		String sql3 = "UPDATE RECUT.TP_OWNER_SEARCH SET STATUS_ID=? WHERE ID=?";
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		try {
			// clear all previous calculations
			st1 = session.getConnection().prepareStatement(sql1);
			st1.setLong(1, recalcId);
			st1.execute();
			// add items
			st2 = session.getConnection().prepareStatement(sql2);
			for (int i = 0; i < results.size(); i++) {
				TpOwnerCorrection corr = (TpOwnerCorrection) results.get(i);
				st2.setLong(1, recalcId);
				st2.setInt(2, corr.getType());
				st2.setDate(3, new java.sql.Date(corr.getStartDate().toDate().getTime()));
				st2.setDate(4, new java.sql.Date(corr.getEndDate().toDate().getTime()));
				st2.setDouble(5, corr.getKwh());
				st2.setDouble(6, corr.getGel());
				st2.addBatch();
			}
			st2.executeBatch();
			// set as calculated
			st3 = session.getConnection().prepareStatement(sql3);
			st3.setInt(1, TpOwnerAccount.STATUS_CALCULATED);
			st3.setLong(2, recalcId);
			st3.execute();
		} finally {
			try { st1.close(); } catch(Exception ex) {}
			st1 = null;
			try { st2.close(); } catch(Exception ex) {}
			st2 = null;
			try { st3.close(); } catch(Exception ex) {}
			st3 = null;
		}
	}

	// declares recalculation as error
	private static void setAsError(int accid, Date cycleDate, Session session) throws Exception {
		long recalcId = getRecalcId(accid, cycleDate, session, true);
		String sql3 = "UPDATE RECUT.TP_OWNER_SEARCH SET STATUS_ID=? WHERE ID=?";
		PreparedStatement st3 = null;
		try {
			// set as error
			st3 = session.getConnection().prepareStatement(sql3);
			st3.setInt(1, TpOwnerAccount.STATUS_CALCULATED_WITH_ERRORS);
			st3.setLong(2, recalcId);
			st3.execute();
		} finally {
			try { st3.close(); } catch(Exception ex) {}
			st3 = null;
		}
	}

	/**
	 * Get calculation saved results.
	 */
	private static List/*TpOwnerCorrection*/ getResults(int accid, Date cycleDate, Session session) throws Exception {
		long recalcId = getRecalcId(accid, cycleDate, session, false);
		String sql = "SELECT OPER_ID, START_DATE, END_DATE, KWH, GEL FROM RECUT.TP_OWNER_RECALC_RESULTS WHERE RECALC_ID=? ORDER BY ID";
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalcId);
			res = st.executeQuery();
			List results = new ArrayList();
			while (res.next()) {
				TpOwnerCorrection corr = new TpOwnerCorrection();
				corr.setType(res.getInt("OPER_ID"));
				corr.setStartDate(telasi.recutil.beans.Date.create(res.getDate("START_DATE")));
				corr.setEndDate(telasi.recutil.beans.Date.create(res.getDate("END_DATE")));
				corr.setKwh(res.getDouble("KWH"));
				corr.setGel(res.getDouble("GEL"));
				results.add(corr);
			}
			return results;
		} finally {
			try {
				st.close();
				res.close();
			} catch (Exception ex) {}
			st = null;
			res = null;
		}
	}

	private static List/*Item*/ getCustomerSummary(int customerId, Date cycleDate, Session session) throws Exception {
		String sql1 = SQLReader.readSQL("tpowner/tpowner_customer_summary_generate.sql");
		String sql2 = SQLReader.readSQL("tpowner/tpowner_customer_summary_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql1);
			st.setInt(1, customerId);
			st.setDate(2, cycleDate);
			st.executeUpdate();
			st = session.getConnection().prepareStatement(sql2);
			st.setInt(1, customerId);
			st.setDate(2, cycleDate);
			res = st.executeQuery();
			List items = new ArrayList();
			while(res.next()) {
				Item item = new Item();
				item.setKwh(res.getDouble("kwh"));
				item.setGel(res.getDouble("gel"));
				Operation oper = new Operation();
				oper.setId(res.getInt("billoperkey"));
				item.setOperation(oper);
				items.add(item);
			}
			return items;
		} finally {
			try {
				st.close();
				res.close();
			} catch (Exception ex) {}
			st = null;
			res = null;
		}
	}

	private static void sendToItem(List/*Customer*/ customers, Date cycleDate, long userId, Session session) throws Exception {
		String sql = SQLReader.readSQL("tpowner/tpowner_send_to_item.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			for (int i = 0; i < customers.size(); i++) {
				Customer customer = (Customer) customers.get(i);
				st.setInt(1, customer.getId());
				st.setDate(2, cycleDate);
				st.setLong(3, userId);
				st.addBatch();
			}
			st.executeBatch();
		} finally {
			try {
				st.close();
			} catch (Exception ex) {}
			st = null;
		}
	}

}
