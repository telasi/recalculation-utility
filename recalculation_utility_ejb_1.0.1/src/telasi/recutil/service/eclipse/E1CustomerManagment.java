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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Address;
import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Customer2;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.DummyTariff;
import telasi.recutil.beans.FlatTariff;
import telasi.recutil.beans.Item;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.StepTariff2006;
import telasi.recutil.beans.custhistory.AccountTariffs;
import telasi.recutil.beans.custhistory.TariffHistoryRecord;
import telasi.recutil.ejb.DefaultEJBRequest;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.Response;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.custhistory.CustomerTariffRequest;
import telasi.recutil.service.eclipse.customer.CustomerByAccnumbSelectRequest;
import telasi.recutil.service.eclipse.customer.CustomerFindRequest;
import telasi.recutil.service.eclipse.customer.CustomerHistorySelectRequest;
import telasi.recutil.service.eclipse.customer.TariffSelectRequest;
import telasi.recutil.utils.Translator;

/**
 * This class is used for customer managment.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class E1CustomerManagment {

	//
	// EJB interaction methods
	//

	/**
	 * Process request on Billoperation SELECT.
	 */
	public static Response processCustomerByAccnumbSelect(Request req, Session session)
	throws RequestException {
		try {
			CustomerByAccnumbSelectRequest request = (CustomerByAccnumbSelectRequest) req;
			String clientString = req.getProperties().get(Request.CLIENT_STRING).toString();
			request.setCustomer(findCustomerByAccNumber(request.getAccNumber(), clientString, session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	/**
	 * Process request on customer history SELECT.
	 */
	public static Response processCustomerHistorySelect(Request req, Session session)
	throws RequestException {
		try {
			CustomerHistorySelectRequest request = (CustomerHistorySelectRequest) req;
			request.setHistory(getHistory(request.getCustomer(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response tariffSelect(Request req, Session session)
	throws RequestException {
		try {
			TariffSelectRequest request = (TariffSelectRequest) req;
			request.setTariffs(getTariffs(session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response customerTariffSelect(Request req, Session session)
	throws RequestException {
		try {
			CustomerTariffRequest request = (CustomerTariffRequest) req;
			request.setHistory(getCustomerTariffHistory(request.getCustomer(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response customerFind(Request req, Session session)
	throws RequestException {
		try {
			CustomerFindRequest request = (CustomerFindRequest) req;
			request.setCustomers(findCustomers(request, session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	//
	// Database methods
	//

	public static Customer findCustomerByAccNumber(String accnumb, String clientString, Session session)
	throws SQLException, IOException {
		String sql = SQLReader.readSQL("cust/cust_select_by_accnumb.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		Customer customer = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setString(1, accnumb);
			res = st.executeQuery();
			customer = extract(res, clientString);
			return customer;
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (Exception ex) {
				}
				res = null;
			}
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	private static Customer extract(ResultSet res,  String clientString) throws SQLException {
		Customer customer = null;
		int index = 0;
		while (res.next()) {
			if (index == 0) {
				boolean less7 = DefaultEJBRequest.versionLessThan7(clientString);
				if (less7) {
					customer = new Customer();
				} else {
					// left amount support after 1.0.7
					Customer2 cust2 = new Customer2();
					Number left = (Number) res.getObject("left_amount");
					cust2.setLeftAmount(left == null ? 0f : left.doubleValue());
					customer = cust2;
				}
				customer.setId(res.getInt("cust_id"));
				customer.setNumber(res.getString("cust_number"));
				customer.setName(res.getString("cust_name"));

				Address address = new Address();
				address.setId(res.getInt("address_id"));
				address.setRegionId(res.getInt("region_id"));
				address.setRegionName(res.getString("region_name"));
				address.setStreetId(res.getInt("street_id"));
				address.setStreetName(res.getString("street_name"));
				address.setHouse(res.getString("house"));
				address.setBuilding(res.getString("building"));
				address.setPorch(res.getString("porch"));
				address.setFlate(res.getString("flate"));
				address.setPostIndex(res.getString("postindex"));
				customer.setAddress(address);

				customer.setRoomCount(res.getInt("roomcount"));
				customer.setCurrentBalance(res.getDouble("cust_balance"));
				customer.setCategory(res.getInt("cust_category"));
			}
			index++;
			Account account = new Account();
			account.setId(res.getInt("acc_id"));
			account.setNumber(Translator.GEO_ASCII_TO_KA(res
					.getString("acc_number")));
			account.setStatus(res.getInt("acc_status"));
			Date created = Date.create(res.getTimestamp("createdate"));
			account.setCreationDate(created);
			account.setMain(res.getInt("acc_is_main") == 1);
			account.setMeterNumber(res.getString("mtnumb"));
			customer.addAccount(account);
		}
		return customer;
	}

	public static List getHistory(Customer customer, Session session)
	throws SQLException, IOException {
		String sql = SQLReader.readSQL("cust/cust_history_select.sql");
		List items = new ArrayList();
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, customer.getId());
			res = st.executeQuery();
			while (res.next()) {
				Item item = new Item();
				item.setId(res.getLong("itemkey"));
				item.setCustomer(customer);
				int accid = res.getInt("acckey");
				List accounts = customer.getAccounts();
				for (int i = 0; i < accounts.size(); i++) {
					Account account = (Account) accounts.get(i);
					if (account.getId() == accid) {
						item.setAccount(account);
						break;
					}
				}
				int operid = res.getInt("billoperkey");
				// operation
				Operation oper = new Operation();
				oper.setId(operid);
				item.setOperation(oper);
				item.setNumber(Translator.GEO_ASCII_TO_KA(res
						.getString("itemnumber")));
				item.setReading(res.getDouble("reading"));
				item.setKwh(res.getDouble("kwt"));
				item.setGel(res.getDouble("amount"));
				item.setBalance(res.getDouble("balance"));
				Number schedule = (Number) res.getObject("schedkey");
				item.setSchedule(schedule == null ? -1 : schedule.intValue());
				item.setItemDate(Date.create(res.getTimestamp("itemdate")));
				item.setEnterDate(Date.create(res.getTimestamp("enterdate")));
				items.add(item);
			}
			return items;
		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (Exception ex) {
				}
				res = null;
			}
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}

	}

	public static List getTariffs(Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("tariff/tariff_select.sql");
		ResultSet res = null;
		try {
			res = session.getConnection().createStatement().executeQuery(sql);
			List tariffs = new ArrayList();
			while (res.next()) {
				int tariffId = res.getInt("compkey");
				String tariffName = res.getString("compname");
				double flatTariffValue = res.getDouble("amount");
				Number stepTariffValue = (Number) res.getObject("ts_val");
				Number stepTariffInterval = (Number) res.getObject("ts_interval");
				boolean isStepTariff = stepTariffValue != null;
				if (!isStepTariff) {
					FlatTariff tariff = new FlatTariff(flatTariffValue);
					tariff.setId(tariffId);
					tariff.setName(tariffName.trim());
					tariffs.add(tariff);
				} else {
					List steps = new ArrayList();
					StepTariff2006.TariffStep step = new StepTariff2006.TariffStep();
					step.setTariff(stepTariffValue.doubleValue());
					step.setInterval(stepTariffInterval.intValue());
					steps.add(step);
					while (stepTariffInterval != null) {
						res.next();
						stepTariffValue = (Number) res.getObject("ts_val");
						stepTariffInterval = (Number) res.getObject("ts_interval");
						step = new StepTariff2006.TariffStep();
						step.setTariff(stepTariffValue.doubleValue());
						step.setInterval(stepTariffInterval == null ? -1 : stepTariffInterval.intValue());
						steps.add(step);
					}
					StepTariff2006 tariff = new StepTariff2006(steps);
					tariff.setId(tariffId);
					tariff.setName(tariffName.trim());
					tariffs.add(tariff);
				}
			}

			return tariffs;

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

	public static List getCustomerTariffHistory(Customer customer, Session session)
	throws SQLException, IOException {
		String sql = SQLReader.readSQL("other/custhistory/tariff_history.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, customer.getId());
			res = st.executeQuery();
			List customerTariffs = new ArrayList();
			Account prevAcc = null;
			List tariffs = null;
			while (res.next()) {
				int prevAccId = prevAcc == null ? -1 : prevAcc.getId();
				int accId = res.getInt("ACCKEY");
				int tariffId = res.getInt("COMPKEY");
				Date start = Date.create(res.getTimestamp("STARTDATE"));
				Date end = Date.create(res.getTimestamp("ENDDATE"));
				String typeName = res.getString("BASECOMPNAME");
				if (accId != prevAccId) {
					if (tariffs != null && prevAccId != -1) {
						AccountTariffs accTariffs = new AccountTariffs();
						accTariffs.setAccount(prevAcc);
						accTariffs.setTariffs(tariffs);
						customerTariffs.add(accTariffs);
					}
					tariffs = new ArrayList();
					prevAcc = null;
					for (int i = 0; i < customer.getAccounts().size(); i++) {
						Account acc = (Account) customer.getAccounts().get(i);
						if (acc.getId() == accId) {
							prevAcc = acc;
							break;
						}
					}
					if (prevAcc == null) {
						throw new SQLException("Can not find account with [ID="
								+ accId + "] for the cuystomer with [ID="
								+ customer.getId() + "].");
					}
				}
				TariffHistoryRecord record = new TariffHistoryRecord();
				record.setAccount(prevAcc);
				record.setStartDate(start);
				record.setEndDate(end);
				DummyTariff tariff = new DummyTariff();
				tariff.setId(tariffId);
				record.setTariff(tariff);
				record.setTypeName(typeName);
				tariffs.add(record);
			}
			if (tariffs != null && prevAcc != null) {
				AccountTariffs accTariffs = new AccountTariffs();
				accTariffs.setAccount(prevAcc);
				accTariffs.setTariffs(tariffs);
				customerTariffs.add(accTariffs);
			}

			return customerTariffs;

		} finally {

			if (res != null) {
				try {
					res.close();
				} catch (Exception ex) {
				}
				res = null;
			}
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}

		}

	}

	public static List findCustomers(CustomerFindRequest request, Session session)
	throws SQLException, IOException {

		// generate SQL command
		String sql = SQLReader.readSQL("cust/cust_find.sql");
		String addition;
		switch (request.getOrderField()) {
		case CustomerFindRequest.NAME:
			addition = "\nORDER BY c.custname, c.custkey";
			break;
		case CustomerFindRequest.ADDRESS:
			addition = "\nORDER BY s.streetname, a.house, a.building, a.porch, a.flate, c.custname, c.custkey";
			break;
		default:
			addition = null;
			break;
		}
		if (addition != null) {
			StringBuffer b = new StringBuffer();
			b.append(sql);
			b.append(addition);
			sql = b.toString();
		}

		// SQL objects
		PreparedStatement st = null;
		ResultSet res = null;

		// condition fields
		String p_name = getOraConditionString(request.getName());
		String p_serviceCeneter = getOraConditionString(request
				.getServiceCenter());
		String p_street = getOraConditionString(request.getStreet());
		String p_house = getOraConditionString(request.getHouse());
		String p_building = getOraConditionString(request.getBuilding());
		String p_porch = getOraConditionString(request.getPorch());
		String p_flate = getOraConditionString(request.getFlat());

		try {

			// create statement
			st = session.getConnection().prepareStatement(sql);
			st.setString(1, p_serviceCeneter);
			st.setString(2, p_name);
			st.setString(3, p_street);
			st.setString(4, p_house);
			st.setString(5, p_building);
			st.setString(6, p_porch);
			st.setString(7, p_flate);
			st.setInt(8, request.getMaxCount());

			// execute query
			res = st.executeQuery();

			// get customers
			List customers = new ArrayList();
			while (res.next()) {
				// get parameters
				int cust_id = res.getInt("cust_id");
				String cust_number = res.getString("cust_number");
				String cust_name = res.getString("cust_name").trim();
				String region_name = res.getString("region_name");
				String street_name = res.getString("street_name");
				String house = res.getString("house");
				String building = res.getString("building");
				String porch = res.getString("porch");
				String flate = res.getString("flate");
				String postindex = res.getString("postindex");
				// create customer
				Customer customer = new Customer();
				customer.setId(cust_id);
				customer.setName(cust_name);
				customer.setNumber(cust_number);
				Address address = new Address();
				address.setRegionName(region_name);
				address.setStreetName(street_name);
				address.setHouse(house);
				address.setBuilding(building);
				address.setPorch(porch);
				address.setFlate(flate);
				address.setPostIndex(postindex);
				customer.setAddress(address);
				// add to the customer's list
				customers.add(customer);
			}

			// return results
			return customers;

		} finally {
			if (res != null) {
				try {
					res.close();
				} catch (Exception ex) {
				}
				res = null;
			}
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}

	}

	private static String getOraConditionString(String field) {
		String newField = field;
		if (newField == null || newField.trim().length() == 0) {
			newField = "%";
		} else {
			newField = newField.trim();
		}
		return newField;
	}
	
}
