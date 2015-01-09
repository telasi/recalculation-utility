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
package telasi.recutil.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Action constants.
 * 
 * @author dimitri
 */
public class ActionConstants {

	//
	// Well known roles
	//
	public static final int ROLE_ADMIN = 1;

	//
	// Actions
	//

	// connect action
	public static final int ACT_CONNECT = 1;
	// user related actions
	public static final int ACT_USER_INSERT = 11;
	public static final int ACT_USER_UPDATE = 12;
	public static final int ACT_USER_DELETE = 13;
	public static final int ACT_MY_USER_PASSWORD_UPDATE = 14;
	// role related actions
	public static final int ACT_ROLE_SELECT = 20;
	public static final int ACT_ROLE_INSERT = 21;
	public static final int ACT_ROLE_UPDATE = 22;
	public static final int ACT_ROLE_DELETE = 23;
	// permission related actions
	public static final int ACT_PERMISSION_INSERT = 31;
	public static final int ACT_PERMISSION_DELETE = 32;
	// billoperation related operations
	public static final int ACT_BILLOPERATION_SELECT = 40;
	// customer related operations
	public static final int ACT_CUSTOMER_SELECT_BY_ACCNUMB = 50;
	public static final int ACT_CUSTOMER_SELECT_BY_ID = 51;
	public static final int ACT_CUSTOMER_HISTORY_SELECT = 52;
	public static final int ACT_CUSTOMER_FIND = 53;
	// meter related operations
	public static final int ACT_METER_SELECT = 60;
	// recalculation related operations
	public static final int ACT_RECALCULATION_HISTORY_SELECT = 100;
	public static final int ACT_RECALCULATION_INSERT = 101;
	public static final int ACT_RECAL_UPDATE = 102;
	public static final int ACT_RECALC_DELETE = 103;
	public static final int ACT_RECALC_COPY = 104;
	public static final int ACT_RECALCULATION_INTERVAL_SELECT = 110;
	public static final int ACT_RECALC_INTERVAL_INSERT = 111;
	public static final int ACT_RECALC_INTERVAL_DELETE = 112;
	public static final int ACT_RECALC_INTERVAL_UPDATE = 113;
	public static final int ACT_RECALC_ITEM_INSERT = 121;
	public static final int ACT_RECALC_ITEM_DELETE = 122;
	public static final int ACT_RECALC_ITEM_UPDATE = 123;
	public static final int ACT_RECALC_ITEM_ENABLE = 124;
	public static final int ACT_RECALC_ITEM_MOVE = 125;
	public static final int ACT_RECALC_ITEM_HINT = 126;
	public static final int ACT_RECALC_ITEM_SUBSATT_UPDATE = 127;
	public static final int ACT_RECALC_ITEM_METER_UPDATE = 128;
	public static final int ACT_RECALC_ITEM_RESTORE_ORIGINAL = 129;
	public static final int ACT_TARIFF_SELECT = 130;
	public static final int ACT_RECALC_REGULAR_SELECT = 140;
	public static final int ACT_RECALC_REGULAR_INSERT = 141;
	public static final int ACT_RECALC_REGULAR_DELETE = 142;
	public static final int ACT_RECALC_REGULAR_UPDATE = 143;
	public static final int ACT_RECALC_REGULAR_MOVE = 144;
	public static final int ACT_RECALC_INSTCP_SELECT = 150;
	public static final int ACT_RECALC_INSTCP_INSERT = 151;
	public static final int ACT_RECALC_INSTCP_DELETE = 152;
	public static final int ACT_RECALC_INSTCP_UPDATE = 153;
	public static final int ACT_RECALC_INSTCP_MOVE = 154;
	public static final int ACT_RECALC_INSTCP_DEFAULT = 155;
	public static final int ACT_RECALC_TARIFF_SELECT = 160;
	public static final int ACT_RECALC_TARIFF_INSERT = 161;
	public static final int ACT_RECALC_TARIFF_DELETE = 162;
	public static final int ACT_RECALC_TARIFF_UPDATE = 163;
	public static final int ACT_RECALC_TARIFF_MOVE = 164;
	public static final int ACT_RECALC_TARIFF_DEFAULT = 165;
	public static final int ACT_RECALC_CUT_SELECT = 170;
	public static final int ACT_RECALC_CUT_INSERT = 171;
	public static final int ACT_RECALC_CUT_DELETE = 172;
	public static final int ACT_RECALC_CUT_UPDATE = 173;
	public static final int ACT_RECALC_CUT_MOVE = 174;
	public static final int ACT_RECALC_ROOM_SELECT = 180;
	public static final int ACT_RECALC_ROOM_INSERT = 181;
	public static final int ACT_RECALC_ROOM_DELETE = 182;
	public static final int ACT_RECALC_ROOM_UPDATE = 183;
	public static final int ACT_REBUILD = 500;
	public static final int ACT_RECALCULATE = 501;
	public static final int ACT_RECALC_SAVE = 502;
	public static final int ACT_GET_SAVED_VOUCHER = 503;
	public static final int ACT_RECALC_UNBLOCK = 504;
	public static final int ACT_RECALC_FINALIZE = 505;
	public static final int ACT_RECALC_SEARCH = 600;
	public static final int ACT_RECALC_CHECK_EXISTENCE = 601;
	public static final int ACT_RECALC_FULL_DOWNLOAD = 602;
	public static final int ACT_RECALC_FULL_SAVE = 603;
	// Other categories
	public static final int OTHER_CUSTOMER_TARIFF_HISTORY = 1000;
	public static final int OTHER_INST_CP_HISTORY_AFTER_2003_SELECT = 1001;
	public static final int OTHER_INST_CP_HISTORY_AFTER_2003_INSERT = 1002;
	public static final int OTHER_INST_CP_HISTORY_AFTER_2003_DELETE = 1003;
	public static final int OTHER_INST_CP_HISTORY_AFTER_2003_UPDATE = 1004;
	public static final int OTHER_AVRG_CHARGE_RECALCULATION_SELECT = 1010;
	public static final int OTHER_AVRG_CHARGE_CALCULATION = 1011;
	// High voltage
	public static final int ACT_TPOWNER_ACCOUNTS_SELECT = 5000;
	public static final int ACT_TPOWNER_GENERATE_RECALC = 5001;
	public static final int ACT_TPOWNER_CALCULATE = 5002;
	public static final int ACT_TPOWNER_TRANSFORMATORS_SELECT = 5003;
	public static final int ACT_TPOWNER_ACCOUNT_SELECT = 5004;
	public static final int ACT_TPOWNER_ACCOUNT_SAVE = 5005;
	public static final int ACT_TPOWNER_RECALCULATION_SAVE = 5006;
	public static final int ACT_TPOWNER_RECALCULATION_DECLARE_ERROR = 5007;
	public static final int ACT_TPOWNER_RECALCULATION_VIEW_SAVED_RESULTS = 5008;
	public static final int ACT_TPOWNER_RECALCULATION_CUSTOMER_SUMMARY = 5009;
	public static final int ACT_TPOWNER_SEND_TO_ITEM = 5010;
	
	// list of all actions and it's initialization
	public static final List ALL_ACTIONS;

	static {
		// security & users
		ALL_ACTIONS = new ArrayList();
		ALL_ACTIONS.add(new Integer(ACT_CONNECT));
		ALL_ACTIONS.add(new Integer(ACT_USER_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_USER_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_USER_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_MY_USER_PASSWORD_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_ROLE_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_ROLE_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_ROLE_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_ROLE_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_PERMISSION_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_PERMISSION_DELETE));
		// billoperation
		ALL_ACTIONS.add(new Integer(ACT_BILLOPERATION_SELECT));
		// customer
		ALL_ACTIONS.add(new Integer(ACT_CUSTOMER_SELECT_BY_ACCNUMB));
		ALL_ACTIONS.add(new Integer(ACT_CUSTOMER_SELECT_BY_ID));
		ALL_ACTIONS.add(new Integer(ACT_CUSTOMER_HISTORY_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_CUSTOMER_FIND));
		// meter
		ALL_ACTIONS.add(new Integer(ACT_METER_SELECT));
		// recalculation
		ALL_ACTIONS.add(new Integer(ACT_RECALCULATION_HISTORY_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_RECALCULATION_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_RECAL_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_COPY));
		ALL_ACTIONS.add(new Integer(ACT_RECALCULATION_INTERVAL_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_INTERVAL_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_INTERVAL_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_INTERVAL_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ITEM_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ITEM_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ITEM_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ITEM_ENABLE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ITEM_MOVE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ITEM_HINT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ITEM_SUBSATT_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ITEM_METER_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ITEM_RESTORE_ORIGINAL));
		ALL_ACTIONS.add(new Integer(ACT_TARIFF_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_REGULAR_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_REGULAR_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_REGULAR_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_REGULAR_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_REGULAR_MOVE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_INSTCP_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_INSTCP_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_INSTCP_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_INSTCP_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_INSTCP_MOVE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_INSTCP_DEFAULT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_CUT_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_CUT_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_CUT_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_CUT_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_CUT_MOVE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ROOM_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ROOM_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ROOM_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_ROOM_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_TARIFF_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_TARIFF_INSERT));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_TARIFF_DELETE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_TARIFF_UPDATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_TARIFF_MOVE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_TARIFF_DEFAULT));
		ALL_ACTIONS.add(new Integer(ACT_REBUILD));
		ALL_ACTIONS.add(new Integer(ACT_RECALCULATE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_SAVE));
		ALL_ACTIONS.add(new Integer(ACT_GET_SAVED_VOUCHER));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_FINALIZE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_UNBLOCK));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_SEARCH));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_CHECK_EXISTENCE));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_FULL_DOWNLOAD));
		ALL_ACTIONS.add(new Integer(ACT_RECALC_FULL_SAVE));
		// others
		ALL_ACTIONS.add(new Integer(OTHER_CUSTOMER_TARIFF_HISTORY));
		ALL_ACTIONS.add(new Integer(OTHER_INST_CP_HISTORY_AFTER_2003_SELECT));
		ALL_ACTIONS.add(new Integer(OTHER_INST_CP_HISTORY_AFTER_2003_INSERT));
		ALL_ACTIONS.add(new Integer(OTHER_INST_CP_HISTORY_AFTER_2003_DELETE));
		ALL_ACTIONS.add(new Integer(OTHER_INST_CP_HISTORY_AFTER_2003_UPDATE));
		//ALL_ACTIONS.add(new Integer(OTHER_AVRG_CHARGE_RECALCULATION_SELECT));
		ALL_ACTIONS.add(new Integer(OTHER_AVRG_CHARGE_CALCULATION));
		// high voltage
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_ACCOUNTS_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_GENERATE_RECALC));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_CALCULATE));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_TRANSFORMATORS_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_ACCOUNT_SELECT));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_ACCOUNT_SAVE));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_RECALCULATION_SAVE));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_RECALCULATION_DECLARE_ERROR));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_RECALCULATION_VIEW_SAVED_RESULTS));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_RECALCULATION_CUSTOMER_SUMMARY));
		ALL_ACTIONS.add(new Integer(ACT_TPOWNER_SEND_TO_ITEM));
	}

}
