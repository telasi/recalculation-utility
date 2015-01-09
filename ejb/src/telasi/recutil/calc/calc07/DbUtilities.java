/*
 *   Copyright (C) 2007 by JSC Telasi
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
package telasi.recutil.calc.calc07;

import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.ITariff;
import telasi.recutil.beans.Meter;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.calc.RecalcException;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.E1BOPManagment;
import telasi.recutil.service.eclipse.E1CustomerManagment;
import telasi.recutil.service.eclipse.E1MeterManagment;

/**
 * This utilities are used for interaction with database.
 * @author dimakura
 */
public class DbUtilities {
    public static boolean DEBUG_MODE = false;
    private static List BILL_OPERATIONS = new ArrayList();
    private static List TARIFFS = new ArrayList();
    private static List METERS = new ArrayList();

    private static void refreshBillOperations(boolean forceNew) {
        synchronized (BILL_OPERATIONS) {
            if (forceNew || BILL_OPERATIONS.isEmpty()) {
                Session session = null;
                try {
                    session = new Session();
                    if (DEBUG_MODE)
                        Session.setUseDirectConnection(true);
                    BILL_OPERATIONS = E1BOPManagment
                            .selectBillingOperations(session);
                } catch (Throwable t) {
                    throw new RecalcException(t.toString());
                } finally {
                    if (session != null)
                        session.close();
                }
            }
        }
    }

    private static Operation lookupOperation(int id) {
        if (BILL_OPERATIONS != null) {
            for (int i = 0; i < BILL_OPERATIONS.size(); i++) {
                OperationType operType = (OperationType) BILL_OPERATIONS.get(i);
                if (operType.getOperations() != null) {
                    for (int j = 0; j < operType.getOperations().size(); j++) {
                        Operation oper = (Operation) operType.getOperations().get(j);
                        if (oper.getId() == id) {
                            return oper;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Looks up for the operation with given ID. First lazy refresh is done. If
     * the operation can not be found then complete refresh will be done. If
     * operation can not be found nevertheless, exception is thrown.
     * 
     * @param id
     *            operation id
     * @return operation founded using this id
     */
    public static Operation findOperationById(int id) {
        refreshBillOperations(false);
        Operation oper = lookupOperation(id);
        if (oper == null) {
            refreshBillOperations(true);
            oper = lookupOperation(id);
        }
        if (oper == null)
            throw new RecalcException("Can not find operation with ID = " + id);
        return oper;
    }

    private static void refreshTariff(boolean forceNew) {
        synchronized (TARIFFS) {
            if (forceNew || TARIFFS.isEmpty()) {
                Session session = null;
                try {
                    session = new Session();
                    if (DEBUG_MODE)
                        Session.setUseDirectConnection(true);
                    TARIFFS = E1CustomerManagment.getTariffs(session);
                } catch (Throwable t) {
                    throw new RecalcException(t.toString());
                } finally {
                    if (session != null)
                        session.close();
                }
            }
        }
    }

    private static ITariff lookupTariff(int id) {
        if (TARIFFS != null) {
            for (int i = 0; i < TARIFFS.size(); i++) {
                ITariff tariff = (ITariff) TARIFFS.get(i);
                if (tariff.getId() == id) {
                    return tariff;
                }
            }
        }
        return null;
    }

    public static ITariff findTariffById(int id) {
        refreshTariff(false);
        ITariff tariff = lookupTariff(id);
        if (tariff == null) {
            refreshTariff(true);
            tariff = lookupTariff(id);
        }
        if (tariff == null)
            throw new RecalcException("Can not find meter with ID = " + id);
        return tariff;
    }

    private static void refreshMeter(boolean forceNew) {
        synchronized (METERS) {
            if (forceNew || METERS.isEmpty()) {
                Session session = null;
                try {
                    session = new Session();
                    if (DEBUG_MODE)
                        Session.setUseDirectConnection(true);
                    METERS = E1MeterManagment.getMeters(session);
                } catch (Throwable t) {
                    throw new RecalcException(t.toString());
                } finally {
                    if (session != null)
                        session.close();
                }
            }
        }
    }

    private static Meter lookupMeter(int id) {
        if (METERS != null) {
            for (int i = 0; i < METERS.size(); i++) {
                Meter meter = (Meter) METERS.get(i);
                if (meter.getId() == id) {
                    return meter;
                }
            }
        }
        return null;
    }

    public static Meter findMeterById(int id) {
        refreshMeter(false);
        Meter meter = lookupMeter(id);
        if (meter == null) {
            refreshMeter(true);
            meter = lookupMeter(id);
        }
        if (meter == null)
            throw new RecalcException("Can not find meter with ID = " + id);
        return meter;
    }

    
}
