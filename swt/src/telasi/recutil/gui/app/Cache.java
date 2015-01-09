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
package telasi.recutil.gui.app;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import telasi.recutil.beans.ITariff;
import telasi.recutil.beans.Meter;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.beans.Role;
import telasi.recutil.beans.User;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.gui.ejb.DefaultRecutilClient;
import telasi.recutil.gui.utils.GUIMessages;
import telasi.recutil.service.eclipse.bop.BillOperationSelectRequest;
import telasi.recutil.service.eclipse.customer.TariffSelectRequest;
import telasi.recutil.service.eclipse.meter.MeterSelectRequest;
import telasi.recutil.service.eclipse.security.RoleSelectRequest;

public class Cache {

	// role
	public static List ROLE_LIST;
	public static Map USERS_MAP_BY_ID;

	// operations
	public static List OPERATION_TYPES_LIST;
	public static Map OPERATIONS_MAP_BY_ID;
	public static List REGULAR_OPERATIONS_LIST;

	// meter
	public static List METER_LIST;
	public static Map METER_MAP_BY_ID;

	// tariffs
	public static List TARIFF_LIST;
	public static Map TARIFF_BY_ID;

	@SuppressWarnings("unchecked")
	public static void refreshRoleList(boolean forceNew) throws Exception {
		if (forceNew || Cache.ROLE_LIST == null || Cache.ROLE_LIST.isEmpty()) {
			if (!Application.validateConnection())
				return;
			RoleSelectRequest request = new RoleSelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			DefaultEJBResponse resp = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			RoleSelectRequest requestCallback = (RoleSelectRequest) resp
					.getRequest();
			Cache.ROLE_LIST = requestCallback.getRoles();
			if (Cache.ROLE_LIST != null) {
				// fill user-by-ID buffer
				USERS_MAP_BY_ID = new HashMap();
				for (int i = 0; i < Cache.ROLE_LIST.size(); i++) {
					Role role = (Role) Cache.ROLE_LIST.get(i);
					if (role.getUsers() != null) {
						for (int j = 0; j < role.getUsers().size(); j++) {
							User user = (User) role.getUsers().get(j);
							USERS_MAP_BY_ID.put(new Long(user.getId()), user);
						}
					}
				}
				// check advisors
				for (int i = 0; i < Cache.ROLE_LIST.size(); i++) {
					Role role = (Role) Cache.ROLE_LIST.get(i);
					if (role.getUsers() != null) {
						for (int j = 0; j < role.getUsers().size(); j++) {
							User user = (User) role.getUsers().get(j);
							if (user.getAdvisor() != null) {
								long advisorId = user.getAdvisor().getId();
								User advisor = (User) USERS_MAP_BY_ID
										.get(new Long(advisorId));
								user.setAdvisor(advisor);
							}
						}
					}
				}
			}
		}

	}

	public static User findUserById(long userId) throws Exception {
		refreshRoleList(false);
		if (ROLE_LIST == null)
			return null;
		return (User) USERS_MAP_BY_ID.get(new Long(userId));
	}

	public static User findUserByUserName(String userName) throws Exception {
		refreshRoleList(false);
		if (ROLE_LIST == null)
			return null;
		User result = null;
		for (int i = 0; i < Cache.ROLE_LIST.size() && result == null; i++) {
			Role role = (Role) Cache.ROLE_LIST.get(i);
			if (role.getUsers() != null) {
				for (int j = 0; j < role.getUsers().size() && result == null; j++) {
					User user = (User) role.getUsers().get(j);
					if (userName.equals(user.getUserName())) {
						result = user;
					}
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static void refreshBillOperationsList(boolean forceNew) throws Exception {
		if (forceNew || Cache.OPERATION_TYPES_LIST == null || Cache.OPERATION_TYPES_LIST.isEmpty()) {
			if (!Application.validateConnection()) {
				return;
			}
			BillOperationSelectRequest request = new BillOperationSelectRequest(Application.USER_NAME, Application.PASSWORD);
			DefaultEJBResponse response = (DefaultEJBResponse) DefaultRecutilClient.processRequest(request);
			BillOperationSelectRequest callback = (BillOperationSelectRequest) response.getRequest();
			Cache.OPERATION_TYPES_LIST = callback.getTypes();
			if (OPERATIONS_MAP_BY_ID != null) {
				OPERATIONS_MAP_BY_ID.clear();
				OPERATIONS_MAP_BY_ID = null;
			}
			if (REGULAR_OPERATIONS_LIST != null) {
				REGULAR_OPERATIONS_LIST.clear();
				REGULAR_OPERATIONS_LIST = null;
			}
			OPERATIONS_MAP_BY_ID = new HashMap();
			REGULAR_OPERATIONS_LIST = new ArrayList();
			for (int i = 0; i < Cache.OPERATION_TYPES_LIST.size(); i++) {
				OperationType operationType = (OperationType) OPERATION_TYPES_LIST.get(i);
				if (operationType.getOperations() != null) {
					for (int j = 0; j < operationType.getOperations().size(); j++) {
						Operation operation = (Operation) operationType.getOperations().get(j);
						operation.setName(GUIMessages.getMessage(operation.getName()));
						OPERATIONS_MAP_BY_ID.put(new Integer(operation.getId()), operation);
						if (operation.isRegular())
							REGULAR_OPERATIONS_LIST.add(operation);
					}
				}
			}
		}
	}

	public static Operation findOperationById(int operId) throws Exception {
		refreshBillOperationsList(false);
		if (OPERATION_TYPES_LIST == null)
			return null;
		return (Operation) OPERATIONS_MAP_BY_ID.get(new Integer(operId));
	}

	@SuppressWarnings("unchecked")
	public static void refreshMeterList(boolean forceNew) throws Exception {
		if (forceNew || Cache.METER_LIST == null || Cache.METER_LIST.isEmpty()) {
			if (!Application.validateConnection()) {
				return;
			}
			MeterSelectRequest request = new MeterSelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			DefaultEJBResponse response = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			MeterSelectRequest callback = (MeterSelectRequest) response
					.getRequest();
			Cache.METER_LIST = callback.getMeters();
			METER_MAP_BY_ID = new HashMap();
			for (int i = 0; i < Cache.METER_LIST.size(); i++) {
				Meter meter = (Meter) Cache.METER_LIST.get(i);
				METER_MAP_BY_ID.put(new Integer(meter.getId()), meter);
			}
		}
	}

	public static Meter findMeterById(int meterId) throws Exception {
		refreshMeterList(false);
		if (METER_LIST == null)
			return null;
		return (Meter) METER_MAP_BY_ID.get(new Integer(meterId));
	}

	@SuppressWarnings("unchecked")
	public static void refreshTariff(boolean forceNew) throws Exception {
		if (forceNew || Cache.TARIFF_LIST == null
				|| Cache.TARIFF_LIST.isEmpty()) {
			if (!Application.validateConnection()) {
				return;
			}
			TariffSelectRequest request = new TariffSelectRequest(
					Application.USER_NAME, Application.PASSWORD);
			DefaultEJBResponse response = (DefaultEJBResponse) DefaultRecutilClient
					.processRequest(request);
			TariffSelectRequest callback = (TariffSelectRequest) response
					.getRequest();
			Cache.TARIFF_LIST = callback.getTariffs();
			Cache.TARIFF_BY_ID = new HashMap();
			for (int i = 0; i < Cache.TARIFF_LIST.size(); i++) {
				ITariff tariff = (ITariff) Cache.TARIFF_LIST.get(i);
				TARIFF_BY_ID.put(new Integer(tariff.getId()), tariff);
			}
		}
	}

	public static ITariff findTariffById(int tariffId) throws Exception {
		refreshTariff(false);
		if (TARIFF_LIST == null) {
			return null;
		}
		return (ITariff) TARIFF_BY_ID.get(new Integer(tariffId));
	}

}
