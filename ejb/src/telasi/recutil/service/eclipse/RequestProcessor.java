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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import telasi.recutil.ejb.IRequestProcessor;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.ejb.Response;
import telasi.recutil.service.ActionConstants;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.security.ConnectRequest;

/**
 * Eclipse RCP v1 request processor. This processor may be used also for other
 * client types.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Oct, 2006
 */
public class RequestProcessor implements IRequestProcessor {

    //
    // interface implementation
    //
    private int prevRequestCode = -1;
    private long userId = -1;

    public List processRequests(List requests) throws RequestException {
        Session session = null;
        try {
            prevRequestCode = -1;
            List responses = new ArrayList();
            session = new Session();
            for (int i = 0; i < requests.size(); i++) {
                Request request = (Request) requests.get(i);
                Response response = processSingleRequest(request, session);
                if (response != null) {
                    responses.add(response);
                }
            }
            session.commit();
            return responses;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                session.rollback();
            } catch (Exception ex2) {
            }
            if (ex instanceof RequestException) {
                throw (RequestException) ex;
            } else {
                throw new RequestException(ex.toString());
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Response processRequest(Request request)
    throws RequestException {
        prevRequestCode = -1;
        Session session = null;
        try {
            session = new Session();
            Response resp = processSingleRequest(request, session);
            session.commit();
            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                session.rollback();
            } catch (Exception ex2) {
            }
            if (ex instanceof RequestException) {
                throw (RequestException) ex;
            } else {
                throw new RequestException(ex.toString());
            }
        } finally {
            session.close();
        }
    }

    public Response processSingleRequest(Request request, Session session) throws RequestException {

        // analyze request code
        Map properties = request.getProperties();
        if (properties == null || properties.isEmpty()) {
            throw new RequestException("Properties for the request are empty");
        }
        Object requestCodeString = properties.get(Request.REQUEST_CODE);
        if (requestCodeString == null) {
            throw new RequestException("Request code is not defined.");
        }
        Integer requestCode;
        try {
            requestCode = new Integer(requestCodeString.toString());
        } catch (Exception ex) {
            throw new RequestException("Problems with request code format", ex);
        }

        // check permissions here
        if (requestCode.intValue() != prevRequestCode) {
            // getting user name and password
            String user = (String) properties.get(Request.USERNAME);
            String password = (String) properties.get(Request.PASSWORD);
            if (user == null || user.trim().length() == 0) {
                throw new RequestException("User name should be defined");
            }
            if (password == null || password.trim().length() == 0) {
                throw new RequestException("Password should be defined");
            }
            // Asserts permissions (except CONNECT action).
            if (requestCode.intValue() != ActionConstants.ACT_CONNECT) {
                userId = E1Security.assertPermission(user, password, requestCode.intValue(), request, session);
            }
        }
        prevRequestCode = requestCode.intValue();

        //
        // Process request
        //

        Response resp = null;

        switch (requestCode.intValue()) {

        // connect
        case ActionConstants.ACT_CONNECT:
            resp = E1Security.processConnection(request, session);
            userId = ((ConnectRequest) request).getUserId();
            break;

        // role selection
        case ActionConstants.ACT_ROLE_SELECT:
            resp = E1UserManagment.processRoleSelect(request, session);
            break;

        // role insert
        case ActionConstants.ACT_ROLE_INSERT:
            resp = E1UserManagment.processRoleInsert(request, session);
            break;

        // role update
        case ActionConstants.ACT_ROLE_UPDATE:
            resp = E1UserManagment.processRoleUpdate(request, session);
            break;

        // role delete
        case ActionConstants.ACT_ROLE_DELETE:
            resp = E1UserManagment.processRoleDelete(request, session);
            break;

        // user insert
        case ActionConstants.ACT_USER_INSERT:
            resp = E1UserManagment.processUserInsert(request, session);
            break;

        // user update
        case ActionConstants.ACT_USER_UPDATE:
            resp = E1UserManagment.processUserUpdate(request, session);
            break;

        // user delete
        case ActionConstants.ACT_USER_DELETE:
            resp = E1UserManagment.processUserDelete(request, session);
            break;

        // update MY password
        case ActionConstants.ACT_MY_USER_PASSWORD_UPDATE:
            resp = E1UserManagment.processMyPasswordUpdate(request, session);
            break;

        // permission insert
        case ActionConstants.ACT_PERMISSION_INSERT:
            resp = E1UserManagment.processPermissionInsert(request, session);
            break;

        // permission delete
        case ActionConstants.ACT_PERMISSION_DELETE:
            resp = E1UserManagment.processPermissionDelete(request, session);
            break;

        // billing operations select
        case ActionConstants.ACT_BILLOPERATION_SELECT:
            resp = E1BOPManagment.processBilloperationSelect(request, session);
            break;

        // customer by ACCNUMB select request
        case ActionConstants.ACT_CUSTOMER_SELECT_BY_ACCNUMB:
            resp = E1CustomerManagment.processCustomerByAccnumbSelect(request,
                    session);
            break;

        // customer history select request
        case ActionConstants.ACT_CUSTOMER_HISTORY_SELECT:
            resp = E1CustomerManagment.processCustomerHistorySelect(request,
                    session);
            break;

        // find customer
        case ActionConstants.ACT_CUSTOMER_FIND:
            resp = E1CustomerManagment.customerFind(request, session);
            break;

        // tariff SELECT request
        case ActionConstants.ACT_TARIFF_SELECT:
            resp = E1CustomerManagment.tariffSelect(request, session);
            break;

        // recalculation history select request
        case ActionConstants.ACT_RECALCULATION_HISTORY_SELECT:
            resp = E1RecalcManager.processRecalcHistorySelectRequest(request,
                    session);
            break;

        // meter select
        case ActionConstants.ACT_METER_SELECT:
            resp = E1MeterManagment.processMeterSelect(request, session);
            break;

        // recalculation INSERT
        case ActionConstants.ACT_RECALCULATION_INSERT:
            resp = E1RecalcManager.processRecalcInsert(request, session);
            break;

        // recalculation COPY
        case ActionConstants.ACT_RECALC_COPY:
        	resp = E1RecalcManager.processRecalcCopy(request, session);
        	break;

        // recalculation DELETE
        case ActionConstants.ACT_RECALC_DELETE:
            resp = E1RecalcManager.processRecalcDelete(request, session);
            break;

        // recalculation UPDATE
        case ActionConstants.ACT_RECAL_UPDATE:
            resp = E1RecalcManager.processRecalcUpdate(request, session);
            break;

        // recalculation intervals SELECT
        case ActionConstants.ACT_RECALCULATION_INTERVAL_SELECT:
            resp = E1RecalcManager
                    .processRecalcIntervalSelect(request, session);
            break;

        // recalculation interval INSERT
        case ActionConstants.ACT_RECALC_INTERVAL_INSERT:
            resp = E1RecalcManager
                    .processRecalcIntervalInsert(request, session);
            break;

        // recalculation interval UPDATE
        case ActionConstants.ACT_RECALC_INTERVAL_UPDATE:
            resp = E1RecalcManager
                    .processRecalcIntervalUpdate(request, session);
            break;

        // recalculation interval DELETE
        case ActionConstants.ACT_RECALC_INTERVAL_DELETE:
            resp = E1RecalcManager
                    .processRecalcIntervalDelete(request, session);
            break;

        // recalculation item INSERT
        case ActionConstants.ACT_RECALC_ITEM_INSERT:
            resp = E1RecalcManager.processRecalcItemInsert(request, session);
            break;

        // recalculation item UPDATE
        case ActionConstants.ACT_RECALC_ITEM_UPDATE:
            resp = E1RecalcManager.processRecalcItemUpdate(request, session);
            break;

        // recalculation item DELETE
        case ActionConstants.ACT_RECALC_ITEM_DELETE:
            resp = E1RecalcManager.processRecalcItemDelete(request, session);
            break;

        // recalculation item ENABLE/DISABLE
        case ActionConstants.ACT_RECALC_ITEM_ENABLE:
            resp = E1RecalcManager.processRecalcItemEnable(request, session);
            break;

        // recalculation item MOVE
        case ActionConstants.ACT_RECALC_ITEM_MOVE:
            resp = E1RecalcManager.processRecalcItemMove(request, session);
            break;

        // recalculation item KWH_LOCK
        case ActionConstants.ACT_RECALC_ITEM_HINT:
            resp = E1RecalcManager.processRecalcItemKwhLock(request, session);
            break;

        // recalculation item SUBSIDY_ATTACHMENT_UPDATE
        case ActionConstants.ACT_RECALC_ITEM_SUBSATT_UPDATE:
            resp = E1RecalcManager.processRecalcItemSubsAttUpdate(request,
                    session);
            break;

        // recalculation item METER_UPDATE
        case ActionConstants.ACT_RECALC_ITEM_METER_UPDATE:
            resp = E1RecalcManager.processRecalcItemMeterUpdate(request,
                    session);
            break;

        // recalculation item RESTORE originals
        case ActionConstants.ACT_RECALC_ITEM_RESTORE_ORIGINAL:
            resp = E1RecalcManager.processRecalcItemOriginalsRestore(request,
                    session);
            break;

        // recalculation regular item INSERT
        case ActionConstants.ACT_RECALC_REGULAR_SELECT:
            resp = E1RecalcManager.processRecalcRegularItemSelet(request,
                    session);
            break;

        // recalculation regular item INSERT
        case ActionConstants.ACT_RECALC_REGULAR_INSERT:
            resp = E1RecalcManager.processRecalcRegularItemInsert(request,
                    session);
            break;

        // recalculation regular item DELETE
        case ActionConstants.ACT_RECALC_REGULAR_DELETE:
            resp = E1RecalcManager.processRecalcRegularItemDelete(request,
                    session);
            break;

        // recalculation regular item UPDATE
        case ActionConstants.ACT_RECALC_REGULAR_UPDATE:
            resp = E1RecalcManager.processRecalcRegularItemUpdate(request,
                    session);
            break;

        // recalculation regular item UPDATE
        case ActionConstants.ACT_RECALC_REGULAR_MOVE:
            resp = E1RecalcManager.processRecalcRegularItemMove(request,
                    session);
            break;

        // recalculation instcp DEFAULT history generation
        case ActionConstants.ACT_RECALC_INSTCP_SELECT:
            resp = E1RecalcManager.processInstcpSelect(request, session);
            break;

        // recalculation instcp DEFAULT history generation
        case ActionConstants.ACT_RECALC_INSTCP_DEFAULT:
            resp = E1RecalcManager.processInstcpDefault(request, session);
            break;

        // recalculation inst.cp. item INSERT
        case ActionConstants.ACT_RECALC_INSTCP_INSERT:
            resp = E1RecalcManager.processInstcpInsert(request, session);
            break;

        // recalculation inst.cp. item UPDATE
        case ActionConstants.ACT_RECALC_INSTCP_UPDATE:
            resp = E1RecalcManager.processInstcpUpdate(request, session);
            break;

        // recalculation inst.cp. item UPDATE
        case ActionConstants.ACT_RECALC_INSTCP_DELETE:
            resp = E1RecalcManager.processInstcpDelete(request, session);
            break;

        // recalculation inst.cp. item MOVE
        case ActionConstants.ACT_RECALC_INSTCP_MOVE:
            resp = E1RecalcManager.processInstcpMove(request, session);
            break;

        // recalculation cut DEFAULT history select
        case ActionConstants.ACT_RECALC_CUT_SELECT:
            resp = E1RecalcManager.processCutSelect(request, session);
            break;

        // recalculation cut item INSERT
        case ActionConstants.ACT_RECALC_CUT_INSERT:
            resp = E1RecalcManager.processCutInsert(request, session);
            break;

        // recalculation cut item UPDATE
        case ActionConstants.ACT_RECALC_CUT_UPDATE:
            resp = E1RecalcManager.processCutUpdate(request, session);
            break;

        // recalculation cut item UPDATE
        case ActionConstants.ACT_RECALC_CUT_DELETE:
            resp = E1RecalcManager.processCutDelete(request, session);
            break;

        // recalculation cut item MOVE
        case ActionConstants.ACT_RECALC_CUT_MOVE:
            resp = E1RecalcManager.processCutMove(request, session);
            break;

        // recalculation DEFAULT tariff history generation
        case ActionConstants.ACT_RECALC_TARIFF_DEFAULT:
            resp = E1RecalcManager.processTariffDefault(request, session);
            break;

        // recalculation tariff history SELECT
        case ActionConstants.ACT_RECALC_TARIFF_SELECT:
            resp = E1RecalcManager.processTariffSelect(request, session);
            break;

        // recalculation tariff item INSERT
        case ActionConstants.ACT_RECALC_TARIFF_INSERT:
            resp = E1RecalcManager.processTariffInsert(request, session);
            break;

        // recalculation tariff item UPDATE
        case ActionConstants.ACT_RECALC_TARIFF_UPDATE:
            resp = E1RecalcManager.processTariffUpdate(request, session);
            break;

        // recalculation tariff item DELETE
        case ActionConstants.ACT_RECALC_TARIFF_DELETE:
            resp = E1RecalcManager.processTariffDelete(request, session);
            break;

        // recalculation tariff item DELETE
        case ActionConstants.ACT_RECALC_TARIFF_MOVE:
            resp = E1RecalcManager.processTariffMove(request, session);
            break;

        // ROOM manipulations
        case ActionConstants.ACT_RECALC_ROOM_SELECT:
            resp = E1RecalcManager.processRoomSelect(request, session);
            break;
        case ActionConstants.ACT_RECALC_ROOM_INSERT:
            resp = E1RecalcManager.processRoomInsert(request, session);
            break;
        case ActionConstants.ACT_RECALC_ROOM_UPDATE:
            resp = E1RecalcManager.processRoomUpdate(request, session);
            break;
        case ActionConstants.ACT_RECALC_ROOM_DELETE:
            resp = E1RecalcManager.processRoomDelete(request, session);
            break;

        // recalculation REBUILD
        case ActionConstants.ACT_REBUILD:
            resp = E1RecalcManager.processRebuild(request, session);
            break;

        // RECALCULATION
        case ActionConstants.ACT_RECALCULATE:
            resp = E1RecalcManager.processRecalc(request, session);
            break;

        // save recalculation
        case ActionConstants.ACT_RECALC_SAVE:
            resp = E1RecalcManager.processRecalcSave(request, session);
            break;

        // get recalculation as voucher
        case ActionConstants.ACT_GET_SAVED_VOUCHER:
            resp = E1RecalcManager.processRecalcVoucherSelect(request, session);
            break;

        // finalize recalculation
        case ActionConstants.ACT_RECALC_FINALIZE:
            resp = E1RecalcManager.processRecalcFinalize(request, session);
            break;
            
        // unblock recalculation
        case ActionConstants.ACT_RECALC_UNBLOCK:
            resp = E1RecalcManager.processRecalcUnblock(request, session);
            break;

        // search recalc
        case ActionConstants.ACT_RECALC_SEARCH:
        	resp = E1RecalcManager.processRecalcSearch(request, session);
        	break;

        // check recalc # existence
        case ActionConstants.ACT_RECALC_CHECK_EXISTENCE:
        	resp = E1RecalcManager.processCheckRecalcExistence(request, session);
        	break;

        // process full download
        case ActionConstants.ACT_RECALC_FULL_DOWNLOAD:
        	resp = E1RecalcManager.processFullRecalcDownload(request, session);
        	break;
        	
        case ActionConstants.ACT_RECALC_FULL_SAVE:
        	resp = E1RecalcManager.processFullRecalcSave(request, session);
        	break;

        // get customer tariff history
        case ActionConstants.OTHER_CUSTOMER_TARIFF_HISTORY:
            resp = E1CustomerManagment.customerTariffSelect(request, session);
            break;

        // process inst. cp. history selection after 2003
        case ActionConstants.OTHER_INST_CP_HISTORY_AFTER_2003_SELECT:
            resp = E1OthersManager.selectInstcpAfter2003(request, session);
            break;
        case ActionConstants.OTHER_INST_CP_HISTORY_AFTER_2003_INSERT:
            resp = E1OthersManager.insertInstcpAfter2003(request, session);
            break;
        case ActionConstants.OTHER_INST_CP_HISTORY_AFTER_2003_DELETE:
            resp = E1OthersManager.deleteInstcpAfter2003(request, session);
            break;
        case ActionConstants.OTHER_INST_CP_HISTORY_AFTER_2003_UPDATE:
            resp = E1OthersManager.updateInstcpAfter2003(request, session);
            break;

        // create avearage calculation request
        case ActionConstants.OTHER_AVRG_CHARGE_RECALCULATION_SELECT:
            resp = E1AvrgChargeManager
                    .processCreateAvrgCalculationRecalc(request);
            break;
        case ActionConstants.OTHER_AVRG_CHARGE_CALCULATION:
            resp = E1AvrgChargeManager.processAvrgCalculation(request);
            break;

// --------------- TP Owners Calculation Utils ------------

        case ActionConstants.ACT_TPOWNER_ACCOUNTS_SELECT:
        	resp = TpOwnersManager.processTpOwnerAccountsSelect(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_GENERATE_RECALC:
        	resp = TpOwnersManager.processTpOwnerRecalcGeneration(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_CALCULATE:
        	resp = TpOwnersManager.processTpOwnerRecalculation(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_TRANSFORMATORS_SELECT:
        	resp = TpOwnersManager.processTransformatorTypesSelect(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_ACCOUNT_SELECT:
        	resp = TpOwnersManager.processTpOwnerAccountSelect(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_ACCOUNT_SAVE:
        	resp = TpOwnersManager.processTpOwnerSaveRequest(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_RECALCULATION_SAVE:
        	resp = TpOwnersManager.processTpOwnerRecalcSaveRequest(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_RECALCULATION_DECLARE_ERROR:
        	resp = TpOwnersManager.processTpOwnerRecalcErrorRequest(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_RECALCULATION_VIEW_SAVED_RESULTS:
        	resp = TpOwnersManager.processTpOwnerRecalcSavedResultsRequest(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_RECALCULATION_CUSTOMER_SUMMARY:
        	resp = TpOwnersManager.processTpOwnerRecalcCustomerSummary(request, session);
        	break;
        case ActionConstants.ACT_TPOWNER_SEND_TO_ITEM:
        	resp = TpOwnersManager.processSendToItem(request, userId, session);
        	break;

        // unknown request code: raise error
        default:
            throw new RequestException("Unknown request code " + requestCode + ".");
        }

        // return response
        return resp;

    }

    static int MAX_LENGTH = 3999;

}
