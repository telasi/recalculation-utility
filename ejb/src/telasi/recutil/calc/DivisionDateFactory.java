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
package telasi.recutil.calc;

import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Date;
import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.Operation;

/**
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Dec, 2006
 */
public class DivisionDateFactory {

	public static List getDivisionDates() {

		// exception dates list
		List exceptionDates = new ArrayList();

		// years from 1999 to 2050
		for (int year = 1999; year < 2050; year++) {

			DivisionDate dd = new DivisionDate();
			dd.date = new Date(year, 12, 31); // 31-Dec-YYYY
			dd.type = IDivisionDate.ON_CYCLE;
			dd.rechargeId = getRechargeId(year, false);
			dd.dischargeId = getDischargeId(year, false);
			dd.percentSubsRechargeId = getPercentSubsRechargeId(year);
			dd.percentSubsDischargeId = getPercentSubsDischargeId(year);
			dd.balanceCorrectionId = getBalanceCorrectionId(year);
			dd.serviceDischargeId = getServiceDischargeId(year, false);
			dd.serviceRechargeId = getServiceRechargeId(year, false);
			dd.compensationDischargeId = getCompensationDischargeId(year, false);
			dd.compensationRechargeId = getCompensationRechargeId(year, false);
			dd.pensionCorrectionId = getPensionCorrectionId(year);
			dd.USAIDCorrectionId = getUSAIDCorrectionId(year);
			dd.fixKwhSubsidyCorrectionId = getFixKwhSubsidyCorrectionId(year);
			dd.oneTimeActId = getOneTimeActCorrectionId(year, false);
			exceptionDates.add(dd);

			// Removed (24-Dec-2009): see Giorgi Lomidze's letter #1-15/7345
			// (task system #0712/322)

			// VAT change date at 01/07/2005
			if (year == 2005) {

				dd.type = IDivisionDate.EXACT;
				dd.date = new Date(year, 6, 30); // 30-Jun-2005

				DivisionDate dd2005 = new DivisionDate();
				dd2005.date = new Date(year, 12, 31); // 31-Dec-2005
				dd2005.type = IDivisionDate.ON_CYCLE;
				dd2005.rechargeId = getRechargeId(year, true);
				dd2005.dischargeId = getDischargeId(year, true);
				dd2005.percentSubsRechargeId = getPercentSubsRechargeId(year);
				dd2005.percentSubsDischargeId = getPercentSubsDischargeId(year);
				dd2005.balanceCorrectionId = getBalanceCorrectionId(year);
				dd2005.serviceDischargeId = getServiceDischargeId(year, true);
				dd2005.serviceRechargeId = getServiceRechargeId(year, true);
				dd2005.compensationDischargeId = getCompensationDischargeId(year, true);
				dd2005.compensationRechargeId = getCompensationRechargeId(year, true);
				dd2005.pensionCorrectionId = getPensionCorrectionId(year);
				dd2005.USAIDCorrectionId = getUSAIDCorrectionId(year);
				dd2005.fixKwhSubsidyCorrectionId = getFixKwhSubsidyCorrectionId(year);
				dd2005.oneTimeActId = getOneTimeActCorrectionId(year, true);
				exceptionDates.add(dd2005);
			}

			// TRASH join
			if (year == 2011) {
				dd.type = IDivisionDate.ON_CYCLE;
				dd.date = new Date(year, 7, 31); // 30-Jul-2011

				DivisionDate dd2011 = new DivisionDate();
				dd2011.date = new Date(year, 12, 31); // 31-Dec-2011
				dd2011.type = IDivisionDate.ON_CYCLE;
				dd2011.rechargeId = getRechargeId(year, true);
				dd2011.dischargeId = getDischargeId(year, true);
				dd2011.percentSubsRechargeId = getPercentSubsRechargeId(year);
				dd2011.percentSubsDischargeId = getPercentSubsDischargeId(year);
				dd2011.balanceCorrectionId = getBalanceCorrectionId(year);
				dd2011.serviceDischargeId = getServiceDischargeId(year, true);
				dd2011.serviceRechargeId = getServiceRechargeId(year, true);
				dd2011.compensationDischargeId = getCompensationDischargeId(year, true);
				dd2011.compensationRechargeId = getCompensationRechargeId(year, true);
				dd2011.pensionCorrectionId = getPensionCorrectionId(year);
				dd2011.USAIDCorrectionId = getUSAIDCorrectionId(year);
				dd2011.fixKwhSubsidyCorrectionId = getFixKwhSubsidyCorrectionId(year);
				dd2011.oneTimeActId = getOneTimeActCorrectionId(year, true);
				exceptionDates.add(dd2011);
			}
			

			// test code telmico telasi
			if (year == 2021) {
				dd.type = IDivisionDate.ON_CYCLE;
				dd.date = new Date(year, 6, 30); // 30-Jun-2021

				DivisionDate dd2021 = new DivisionDate();
				dd2021.date = new Date(year, 12, 31); // 31-Dec-2021
				dd2021.type = IDivisionDate.ON_CYCLE;
				dd2021.rechargeId = getRechargeId(year, true);
				dd2021.dischargeId = getDischargeId(year, true);
				dd2021.percentSubsRechargeId = getPercentSubsRechargeId(year);
				dd2021.percentSubsDischargeId = getPercentSubsDischargeId(year);
				dd2021.balanceCorrectionId = getBalanceCorrectionId(year);
				dd2021.serviceDischargeId = getServiceDischargeId(year, true);
				dd2021.serviceRechargeId = getServiceRechargeId(year, true);
				dd2021.compensationDischargeId = getCompensationDischargeId(year, true);
				dd2021.compensationRechargeId = getCompensationRechargeId(year, true);
				dd2021.pensionCorrectionId = getPensionCorrectionId(year);
				dd2021.USAIDCorrectionId = getUSAIDCorrectionId(year);
				dd2021.fixKwhSubsidyCorrectionId = getFixKwhSubsidyCorrectionId(year);
				dd2021.oneTimeActId = getOneTimeActCorrectionId(year, true);
				exceptionDates.add(dd2021);
			}			
		}

		// return results
		return exceptionDates;
	}

	static final List RECHARGE = new ArrayList();
	static final List DISCHARGE = new ArrayList();
	static final List PERCENT_RECHARGE = new ArrayList();
	static final List PERCENT_DISCHARGE = new ArrayList();
	static final List BALANCE_CORRECTION = new ArrayList();
	static final List SERVICE_RECHARGE = new ArrayList();
	static final List SERVICE_DISCHARGE = new ArrayList();
	static final List COMPENSATION_RECHARGE = new ArrayList();
	static final List COMPENSATION_DISCHARGE = new ArrayList();
	static final List PENSION_CORRECTION = new ArrayList();
	static final List USAID_CORRECTION = new ArrayList();
	static final List FIX_KWH_CORRECTION = new ArrayList();
	static final List CHARGE_CORRECTION = new ArrayList();
	static final List ONE_TIME_ACTS = new ArrayList();

	static {
		// rechages
		RECHARGE.add(new Integer(190)); // 1999
		RECHARGE.add(new Integer(191)); // 2000
		RECHARGE.add(new Integer(192)); // 2001
		RECHARGE.add(new Integer(193)); // 2002
		RECHARGE.add(new Integer(194)); // 2003
		RECHARGE.add(new Integer(195)); // 2004
		RECHARGE.add(new Integer(196)); // 2005
		RECHARGE.add(new Integer(197)); // 2005_2
		RECHARGE.add(new Integer(199)); // 2006
		RECHARGE.add(new Integer(352)); // 2007
		RECHARGE.add(new Integer(532)); // 2008
		RECHARGE.add(new Integer(552)); // 2009
		RECHARGE.add(new Integer(562)); // 2010
		RECHARGE.add(new Integer(572)); // 2011
		RECHARGE.add(new Integer(602)); // 2011_1
		RECHARGE.add(new Integer(582)); // 2012
		RECHARGE.add(new Integer(592)); // 2013
		RECHARGE.add(new Integer(692)); // 2014
		RECHARGE.add(new Integer(792)); // 2015
		RECHARGE.add(new Integer(892)); // 2016
		RECHARGE.add(new Integer(895)); // 2017
		RECHARGE.add(new Integer(898)); // 2018
		RECHARGE.add(new Integer(901)); // 2019	
		RECHARGE.add(new Integer(904)); // 2020
		RECHARGE.add(new Integer(907)); // 2021		
		RECHARGE.add(new Integer(910)); // 2021-1
		RECHARGE.add(new Integer(912)); // 2022
		RECHARGE.add(new Integer(912)); // 2022-1
		// discharges
		DISCHARGE.add(new Integer(189)); // 1999
		DISCHARGE.add(new Integer(280)); // 2000
		DISCHARGE.add(new Integer(281)); // 2001
		DISCHARGE.add(new Integer(282)); // 2002
		DISCHARGE.add(new Integer(283)); // 2003
		DISCHARGE.add(new Integer(289)); // 2004
		DISCHARGE.add(new Integer(290)); // 2005
		DISCHARGE.add(new Integer(292)); // 2005_1
		DISCHARGE.add(new Integer(291)); // 2006
		DISCHARGE.add(new Integer(351)); // 2007
		DISCHARGE.add(new Integer(531)); // 2008
		DISCHARGE.add(new Integer(551)); // 2009
		DISCHARGE.add(new Integer(561)); // 2010
		DISCHARGE.add(new Integer(571)); // 2011
		DISCHARGE.add(new Integer(601)); // 2011_1
		DISCHARGE.add(new Integer(581)); // 2012
		DISCHARGE.add(new Integer(591)); // 2013
		DISCHARGE.add(new Integer(691)); // 2014
		DISCHARGE.add(new Integer(791)); // 2015
		DISCHARGE.add(new Integer(891)); // 2016
		DISCHARGE.add(new Integer(894)); // 2017
		DISCHARGE.add(new Integer(897)); // 2018
		DISCHARGE.add(new Integer(900)); // 2019
		DISCHARGE.add(new Integer(903)); // 2020
		DISCHARGE.add(new Integer(906)); // 2021
		DISCHARGE.add(new Integer(909)); // 2021-1
		DISCHARGE.add(new Integer(911)); // 2022
		DISCHARGE.add(new Integer(911)); // 2022-1
		// percent rechages
		PERCENT_RECHARGE.add(new Integer(190)); // 1999
		PERCENT_RECHARGE.add(new Integer(271)); // 2000
		PERCENT_RECHARGE.add(new Integer(271)); // 2001
		PERCENT_RECHARGE.add(new Integer(271)); // 2002
		PERCENT_RECHARGE.add(new Integer(272)); // 2003
		PERCENT_RECHARGE.add(new Integer(222)); // 2004
		PERCENT_RECHARGE.add(new Integer(223)); // 2005
		PERCENT_RECHARGE.add(new Integer(223)); // 2005_1
		PERCENT_RECHARGE.add(new Integer(276)); // 2006
		PERCENT_RECHARGE.add(new Integer(359)); // 2007
		PERCENT_RECHARGE.add(new Integer(539)); // 2008
		PERCENT_RECHARGE.add(new Integer(559)); // 2009
		PERCENT_RECHARGE.add(new Integer(569)); // 2010
		PERCENT_RECHARGE.add(new Integer(579)); // 2011
		PERCENT_RECHARGE.add(new Integer(609)); // 2011_1
		PERCENT_RECHARGE.add(new Integer(589)); // 2012
		PERCENT_RECHARGE.add(new Integer(599)); // 2013
		PERCENT_RECHARGE.add(new Integer(599)); // 2014
		PERCENT_RECHARGE.add(new Integer(599)); // 2015
		PERCENT_RECHARGE.add(new Integer(599)); // 2016
		PERCENT_RECHARGE.add(new Integer(599)); // 2017
		PERCENT_RECHARGE.add(new Integer(599)); // 2018
		PERCENT_RECHARGE.add(new Integer(599)); // 2019
		PERCENT_RECHARGE.add(new Integer(599)); // 2020
		PERCENT_RECHARGE.add(new Integer(599)); // 2021
		PERCENT_RECHARGE.add(new Integer(599)); // 2021-1
		PERCENT_RECHARGE.add(new Integer(599)); // 2022
		PERCENT_RECHARGE.add(new Integer(599)); // 2022-1
		// percent dischages
		PERCENT_DISCHARGE.add(new Integer(305)); // 1999
		PERCENT_DISCHARGE.add(new Integer(305)); // 2000
		PERCENT_DISCHARGE.add(new Integer(305)); // 2001
		PERCENT_DISCHARGE.add(new Integer(305)); // 2002
		PERCENT_DISCHARGE.add(new Integer(306)); // 2003
		PERCENT_DISCHARGE.add(new Integer(307)); // 2004
		PERCENT_DISCHARGE.add(new Integer(308)); // 2005
		PERCENT_DISCHARGE.add(new Integer(308)); // 2005_1
		PERCENT_DISCHARGE.add(new Integer(309)); // 2006
		PERCENT_DISCHARGE.add(new Integer(358)); // 2007
		PERCENT_DISCHARGE.add(new Integer(538)); // 2008
		PERCENT_DISCHARGE.add(new Integer(558)); // 2009
		PERCENT_DISCHARGE.add(new Integer(568)); // 2010
		PERCENT_DISCHARGE.add(new Integer(578)); // 2011
		PERCENT_DISCHARGE.add(new Integer(608)); // 2011_1
		PERCENT_DISCHARGE.add(new Integer(588)); // 2012
		PERCENT_DISCHARGE.add(new Integer(598)); // 2013
		PERCENT_DISCHARGE.add(new Integer(598)); // 2014
		PERCENT_DISCHARGE.add(new Integer(598)); // 2015
		PERCENT_DISCHARGE.add(new Integer(598)); // 2016
		PERCENT_DISCHARGE.add(new Integer(598)); // 2017
		PERCENT_DISCHARGE.add(new Integer(598)); // 2018
		PERCENT_DISCHARGE.add(new Integer(598)); // 2019
		PERCENT_DISCHARGE.add(new Integer(598)); // 2020
		PERCENT_DISCHARGE.add(new Integer(598)); // 2021
		PERCENT_DISCHARGE.add(new Integer(598)); // 2021-1
		PERCENT_DISCHARGE.add(new Integer(598)); // 2022
		PERCENT_DISCHARGE.add(new Integer(598)); // 2022-1
		// balance correction
		BALANCE_CORRECTION.add(new Integer(94)); // 1999
		BALANCE_CORRECTION.add(new Integer(95)); // 2000
		BALANCE_CORRECTION.add(new Integer(17)); // 2001
		BALANCE_CORRECTION.add(new Integer(96)); // 2002
		BALANCE_CORRECTION.add(new Integer(146)); // 2003
		BALANCE_CORRECTION.add(new Integer(165)); // 2004
		BALANCE_CORRECTION.add(new Integer(171)); // 2005
		BALANCE_CORRECTION.add(new Integer(171)); // 2005_1
		BALANCE_CORRECTION.add(new Integer(212)); // 2006
		BALANCE_CORRECTION.add(new Integer(356)); // 2007
		BALANCE_CORRECTION.add(new Integer(533)); // 2008
		BALANCE_CORRECTION.add(new Integer(553)); // 2009
		BALANCE_CORRECTION.add(new Integer(563)); // 2010
		BALANCE_CORRECTION.add(new Integer(573)); // 2011
		BALANCE_CORRECTION.add(new Integer(573)); // 2011_2
		BALANCE_CORRECTION.add(new Integer(583)); // 2012
		BALANCE_CORRECTION.add(new Integer(593)); // 2013
		BALANCE_CORRECTION.add(new Integer(693)); // 2014
		BALANCE_CORRECTION.add(new Integer(793)); // 2015
		BALANCE_CORRECTION.add(new Integer(893)); // 2016
		BALANCE_CORRECTION.add(new Integer(896)); // 2017
		BALANCE_CORRECTION.add(new Integer(899)); // 2018
		BALANCE_CORRECTION.add(new Integer(902)); // 2019
		BALANCE_CORRECTION.add(new Integer(905)); // 2020
		BALANCE_CORRECTION.add(new Integer(908)); // 2021
		BALANCE_CORRECTION.add(new Integer(908)); // 2021-1
		BALANCE_CORRECTION.add(new Integer(913)); // 2022
		BALANCE_CORRECTION.add(new Integer(913)); // 2022-1
		// service discharge
		SERVICE_DISCHARGE.add(new Integer(200)); // 1999
		SERVICE_DISCHARGE.add(new Integer(200)); // 2000
		SERVICE_DISCHARGE.add(new Integer(200)); // 2001
		SERVICE_DISCHARGE.add(new Integer(200)); // 2002
		SERVICE_DISCHARGE.add(new Integer(200)); // 2003
		SERVICE_DISCHARGE.add(new Integer(200)); // 2004
		SERVICE_DISCHARGE.add(new Integer(200)); // 2005
		SERVICE_DISCHARGE.add(new Integer(201)); // 2005_1
		SERVICE_DISCHARGE.add(new Integer(201)); // 2006
		SERVICE_DISCHARGE.add(new Integer(201)); // 2007
		SERVICE_DISCHARGE.add(new Integer(201)); // 2008
		SERVICE_DISCHARGE.add(new Integer(201)); // 2009
		SERVICE_DISCHARGE.add(new Integer(201)); // 2010
		SERVICE_DISCHARGE.add(new Integer(201)); // 2011
		SERVICE_DISCHARGE.add(new Integer(201)); // 2011_1
		SERVICE_DISCHARGE.add(new Integer(201)); // 2012
		SERVICE_DISCHARGE.add(new Integer(201)); // 2013
		SERVICE_DISCHARGE.add(new Integer(201)); // 2014
		SERVICE_DISCHARGE.add(new Integer(201)); // 2015
		SERVICE_DISCHARGE.add(new Integer(201)); // 2016
		SERVICE_DISCHARGE.add(new Integer(201)); // 2017
		SERVICE_DISCHARGE.add(new Integer(201)); // 2018
		SERVICE_DISCHARGE.add(new Integer(201)); // 2019
		SERVICE_DISCHARGE.add(new Integer(201)); // 2020
		SERVICE_DISCHARGE.add(new Integer(201)); // 2021
		SERVICE_DISCHARGE.add(new Integer(201)); // 2021-1
		SERVICE_DISCHARGE.add(new Integer(201)); // 2022
		SERVICE_DISCHARGE.add(new Integer(201)); // 2022-1
		// service recharge
		SERVICE_RECHARGE.add(new Integer(200)); // 1999
		SERVICE_RECHARGE.add(new Integer(200)); // 2000
		SERVICE_RECHARGE.add(new Integer(200)); // 2001
		SERVICE_RECHARGE.add(new Integer(200)); // 2002
		SERVICE_RECHARGE.add(new Integer(200)); // 2003
		SERVICE_RECHARGE.add(new Integer(200)); // 2004
		SERVICE_RECHARGE.add(new Integer(200)); // 2005
		SERVICE_RECHARGE.add(new Integer(201)); // 2005_1
		SERVICE_RECHARGE.add(new Integer(201)); // 2006
		SERVICE_RECHARGE.add(new Integer(201)); // 2007
		SERVICE_RECHARGE.add(new Integer(201)); // 2008
		SERVICE_RECHARGE.add(new Integer(201)); // 2009
		SERVICE_RECHARGE.add(new Integer(201)); // 2010
		SERVICE_RECHARGE.add(new Integer(201)); // 2011
		SERVICE_RECHARGE.add(new Integer(201)); // 2011_1
		SERVICE_RECHARGE.add(new Integer(201)); // 2012
		SERVICE_RECHARGE.add(new Integer(201)); // 2013
		SERVICE_RECHARGE.add(new Integer(201)); // 2014
		SERVICE_RECHARGE.add(new Integer(201)); // 2015
		SERVICE_RECHARGE.add(new Integer(201)); // 2016
		SERVICE_RECHARGE.add(new Integer(201)); // 2017
		SERVICE_RECHARGE.add(new Integer(201)); // 2018
		SERVICE_RECHARGE.add(new Integer(201)); // 2019		
		SERVICE_RECHARGE.add(new Integer(201)); // 2020
		SERVICE_RECHARGE.add(new Integer(201)); // 2021
		SERVICE_RECHARGE.add(new Integer(201)); // 2021-1
		SERVICE_RECHARGE.add(new Integer(201)); // 2022
		SERVICE_RECHARGE.add(new Integer(201)); // 2022-1
		// compensation discharge
		COMPENSATION_DISCHARGE.add(new Integer(103)); // 1999
		COMPENSATION_DISCHARGE.add(new Integer(297)); // 2000
		COMPENSATION_DISCHARGE.add(new Integer(298)); // 2001
		COMPENSATION_DISCHARGE.add(new Integer(299)); // 2002
		COMPENSATION_DISCHARGE.add(new Integer(300)); // 2003
		COMPENSATION_DISCHARGE.add(new Integer(301)); // 2004
		COMPENSATION_DISCHARGE.add(new Integer(302)); // 2005
		COMPENSATION_DISCHARGE.add(new Integer(303)); // 2005_1
		COMPENSATION_DISCHARGE.add(new Integer(304)); // 2006
		COMPENSATION_DISCHARGE.add(new Integer(354)); // 2007
		COMPENSATION_DISCHARGE.add(new Integer(535)); // 2008
		COMPENSATION_DISCHARGE.add(new Integer(555)); // 2009
		COMPENSATION_DISCHARGE.add(new Integer(565)); // 2010
		COMPENSATION_DISCHARGE.add(new Integer(575)); // 2011
		COMPENSATION_DISCHARGE.add(new Integer(575)); // 2011_1
		COMPENSATION_DISCHARGE.add(new Integer(585)); // 2012
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2013
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2014
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2015
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2016
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2017
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2018
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2019
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2020
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2021
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2021-1
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2022
		COMPENSATION_DISCHARGE.add(new Integer(595)); // 2022-1
		// compensation recharge
		COMPENSATION_RECHARGE.add(new Integer(103)); // 1999
		COMPENSATION_RECHARGE.add(new Integer(203)); // 2000
		COMPENSATION_RECHARGE.add(new Integer(204)); // 2001
		COMPENSATION_RECHARGE.add(new Integer(205)); // 2002
		COMPENSATION_RECHARGE.add(new Integer(206)); // 2003
		COMPENSATION_RECHARGE.add(new Integer(207)); // 2004
		COMPENSATION_RECHARGE.add(new Integer(208)); // 2005
		COMPENSATION_RECHARGE.add(new Integer(209)); // 2005_1
		COMPENSATION_RECHARGE.add(new Integer(211)); // 2006
		COMPENSATION_RECHARGE.add(new Integer(355)); // 2007
		COMPENSATION_RECHARGE.add(new Integer(536)); // 2008
		COMPENSATION_RECHARGE.add(new Integer(556)); // 2009
		COMPENSATION_RECHARGE.add(new Integer(566)); // 2010
		COMPENSATION_RECHARGE.add(new Integer(576)); // 2011
		COMPENSATION_RECHARGE.add(new Integer(576)); // 2011_1
		COMPENSATION_RECHARGE.add(new Integer(586)); // 2012
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2013
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2014
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2015
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2016
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2017
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2018
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2019
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2020
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2021
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2021-1
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2022
		COMPENSATION_RECHARGE.add(new Integer(596)); // 2022-1
		// pension correction
		PENSION_CORRECTION.add(new Integer(100)); // 1999
		PENSION_CORRECTION.add(new Integer(101)); // 2000
		PENSION_CORRECTION.add(new Integer(70)); // 2001
		PENSION_CORRECTION.add(new Integer(102)); // 2002
		PENSION_CORRECTION.add(new Integer(102)); // 2003
		PENSION_CORRECTION.add(new Integer(102)); // 2004
		PENSION_CORRECTION.add(new Integer(102)); // 2005
		PENSION_CORRECTION.add(new Integer(102)); // 2005_1
		PENSION_CORRECTION.add(new Integer(102)); // 2006
		PENSION_CORRECTION.add(new Integer(102)); // 2007
		PENSION_CORRECTION.add(new Integer(102)); // 2008
		PENSION_CORRECTION.add(new Integer(102)); // 2009
		PENSION_CORRECTION.add(new Integer(102)); // 2010
		PENSION_CORRECTION.add(new Integer(102)); // 2011
		PENSION_CORRECTION.add(new Integer(102)); // 2011_1
		PENSION_CORRECTION.add(new Integer(102)); // 2012
		PENSION_CORRECTION.add(new Integer(102)); // 2013
		PENSION_CORRECTION.add(new Integer(102)); // 2014
		PENSION_CORRECTION.add(new Integer(102)); // 2015
		PENSION_CORRECTION.add(new Integer(102)); // 2016
		PENSION_CORRECTION.add(new Integer(102)); // 2017
		PENSION_CORRECTION.add(new Integer(102)); // 2018
		PENSION_CORRECTION.add(new Integer(102)); // 2019
		PENSION_CORRECTION.add(new Integer(102)); // 2020
		PENSION_CORRECTION.add(new Integer(102)); // 2021
		PENSION_CORRECTION.add(new Integer(102)); // 2021-1
		PENSION_CORRECTION.add(new Integer(102)); // 2022
		PENSION_CORRECTION.add(new Integer(102)); // 2022-1
		// USAID correction
		USAID_CORRECTION.add(new Integer(72)); // 1999
		USAID_CORRECTION.add(new Integer(107)); // 2000
		USAID_CORRECTION.add(new Integer(108)); // 2001
		USAID_CORRECTION.add(new Integer(109)); // 2002
		USAID_CORRECTION.add(new Integer(142)); // 2003
		USAID_CORRECTION.add(new Integer(163)); // 2004
		USAID_CORRECTION.add(new Integer(163)); // 2005
		USAID_CORRECTION.add(new Integer(163)); // 2005_1
		USAID_CORRECTION.add(new Integer(163)); // 2006
		USAID_CORRECTION.add(new Integer(163)); // 2007
		USAID_CORRECTION.add(new Integer(163)); // 2008
		USAID_CORRECTION.add(new Integer(163)); // 2009
		USAID_CORRECTION.add(new Integer(163)); // 2010
		USAID_CORRECTION.add(new Integer(163)); // 2011
		USAID_CORRECTION.add(new Integer(163)); // 2011_1
		USAID_CORRECTION.add(new Integer(163)); // 2012
		USAID_CORRECTION.add(new Integer(163)); // 2013
		USAID_CORRECTION.add(new Integer(163)); // 2014
		USAID_CORRECTION.add(new Integer(163)); // 2015
		USAID_CORRECTION.add(new Integer(163)); // 2016
		USAID_CORRECTION.add(new Integer(163)); // 2017
		USAID_CORRECTION.add(new Integer(163)); // 2018
		USAID_CORRECTION.add(new Integer(163)); // 2019
		USAID_CORRECTION.add(new Integer(163)); // 2020
		USAID_CORRECTION.add(new Integer(163)); // 2021
		USAID_CORRECTION.add(new Integer(163)); // 2021-1
		USAID_CORRECTION.add(new Integer(163)); // 2022
		USAID_CORRECTION.add(new Integer(163)); // 2022-1
		// FIX_KWH correction
		FIX_KWH_CORRECTION.add(new Integer(97)); // 1999
		FIX_KWH_CORRECTION.add(new Integer(98)); // 2000
		FIX_KWH_CORRECTION.add(new Integer(69)); // 2001
		FIX_KWH_CORRECTION.add(new Integer(99)); // 2002
		FIX_KWH_CORRECTION.add(new Integer(144)); // 2003
		FIX_KWH_CORRECTION.add(new Integer(167)); // 2004
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2005
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2005_1
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2006
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2007
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2008
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2009
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2010
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2011
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2011_1
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2012
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2013
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2014
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2015
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2016
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2017
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2018
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2019
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2020
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2021
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2021-1
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2022
		FIX_KWH_CORRECTION.add(new Integer(173)); // 2022-1
		// CHARGE correction
		CHARGE_CORRECTION.add(new Integer(293)); // 1999
		CHARGE_CORRECTION.add(new Integer(78)); // 2000
		CHARGE_CORRECTION.add(new Integer(18)); // 2001
		CHARGE_CORRECTION.add(new Integer(86)); // 2002
		CHARGE_CORRECTION.add(new Integer(139)); // 2003
		CHARGE_CORRECTION.add(new Integer(164)); // 2004
		CHARGE_CORRECTION.add(new Integer(170)); // 2005
		CHARGE_CORRECTION.add(new Integer(176)); // 2005_1
		CHARGE_CORRECTION.add(new Integer(198)); // 2006
		CHARGE_CORRECTION.add(new Integer(350)); // 2007
		CHARGE_CORRECTION.add(new Integer(530)); // 2008
		CHARGE_CORRECTION.add(new Integer(550)); // 2009
		CHARGE_CORRECTION.add(new Integer(560)); // 2010
		CHARGE_CORRECTION.add(new Integer(570)); // 2011
		CHARGE_CORRECTION.add(new Integer(570)); // 2011_1
		CHARGE_CORRECTION.add(new Integer(580)); // 2012
		CHARGE_CORRECTION.add(new Integer(590)); // 2013
		CHARGE_CORRECTION.add(new Integer(690)); // 2014
		CHARGE_CORRECTION.add(new Integer(790)); // 2015
		CHARGE_CORRECTION.add(new Integer(790)); // 2016
		CHARGE_CORRECTION.add(new Integer(790)); // 2017
		CHARGE_CORRECTION.add(new Integer(790)); // 2018
		CHARGE_CORRECTION.add(new Integer(790)); // 2019
		CHARGE_CORRECTION.add(new Integer(790)); // 2020
		CHARGE_CORRECTION.add(new Integer(790)); // 2021
		CHARGE_CORRECTION.add(new Integer(790)); // 2021-1
		CHARGE_CORRECTION.add(new Integer(790)); // 2022
		CHARGE_CORRECTION.add(new Integer(790)); // 2022-1
		// ONE_TIME_ACT correction
		ONE_TIME_ACTS.add(new Integer(401)); // 1999
		ONE_TIME_ACTS.add(new Integer(402)); // 2000
		ONE_TIME_ACTS.add(new Integer(403)); // 2001
		ONE_TIME_ACTS.add(new Integer(404)); // 2002
		ONE_TIME_ACTS.add(new Integer(405)); // 2003
		ONE_TIME_ACTS.add(new Integer(406)); // 2004
		ONE_TIME_ACTS.add(new Integer(407)); // 2005
		ONE_TIME_ACTS.add(new Integer(408)); // 2005_1
		ONE_TIME_ACTS.add(new Integer(409)); // 2006
		ONE_TIME_ACTS.add(new Integer(410)); // 2007
		ONE_TIME_ACTS.add(new Integer(411)); // 2008
		ONE_TIME_ACTS.add(new Integer(412)); // 2009
		ONE_TIME_ACTS.add(new Integer(413)); // 2010
		ONE_TIME_ACTS.add(new Integer(414)); // 2011
		ONE_TIME_ACTS.add(new Integer(415)); // 2011_1
		ONE_TIME_ACTS.add(new Integer(416)); // 2012
		ONE_TIME_ACTS.add(new Integer(417)); // 2013
		ONE_TIME_ACTS.add(new Integer(418)); // 2014
		ONE_TIME_ACTS.add(new Integer(421)); // 2015
		ONE_TIME_ACTS.add(new Integer(180)); // 2016
		ONE_TIME_ACTS.add(new Integer(422)); // 2017
		ONE_TIME_ACTS.add(new Integer(423)); // 2018
		ONE_TIME_ACTS.add(new Integer(424)); // 2019
		ONE_TIME_ACTS.add(new Integer(425)); // 2020
		ONE_TIME_ACTS.add(new Integer(426)); // 2021
		ONE_TIME_ACTS.add(new Integer(183)); // 2021-1
		ONE_TIME_ACTS.add(new Integer(427)); // 2022	
		ONE_TIME_ACTS.add(new Integer(428)); // 2022-1		
	}

	public static int getYearIndex(int year, boolean firstHalf) {
		if (year < 1999) {
			return 0;
		}
		switch (year) {
		case 1999:
			return 0;
		case 2000:
			return 1;
		case 2001:
			return 2;
		case 2002://Giorgi Lomidze
			return 3;
		case 2003:
			return 4;
		case 2004:
			return 5;
		case 2005:
			return firstHalf ? 6 : 7;
		case 2006:
			return 8;
		case 2007:
			return 9;
		case 2008:
			return 10;
		case 2009:
			return 11;
		case 2010:
			return 12;
		case 2011:
			return firstHalf ? 13 : 14;
		case 2012:
			return 15;//Giorgi Lomidze
		case 2013:
			return 16;
		case 2014:
			return 17;
		case 2015:
			return 18;
		case 2016:
			return 19;
		case 2017:
			return 20;
		case 2018:
			return 21;
		case 2019:
			return 22;
		case 2020:
			return 23;
		case 2021:
			return firstHalf ? 24 : 25;			
		default:
			return  firstHalf ? 26 : 27; // 2022 - +
		}
	}

	public static boolean isRecharge(int operId) {
		return RECHARGE.contains(new Integer(operId)) || PERCENT_RECHARGE.contains(new Integer(operId));
	}

	public static boolean isDischarge(int operId) {
		return DISCHARGE.contains(new Integer(operId)) || PERCENT_DISCHARGE.contains(new Integer(operId));
	}

	public static boolean isChargeCorrection(int operId) {
		return CHARGE_CORRECTION.contains(new Integer(operId));
	}

	public static int getCoupledDischarge(int operId) {
		int index = RECHARGE.indexOf(new Integer(operId));
		if (index == -1) {
			index = PERCENT_RECHARGE.indexOf(new Integer(operId));
			if (index == -1) {
				return -1;
			}
			return ((Number) PERCENT_DISCHARGE.get(index)).intValue();
		}
		return ((Number) DISCHARGE.get(index)).intValue();
	}

	public static int getCoupledRecharge(int operId) {
		int index = DISCHARGE.indexOf(new Integer(operId));
		if (index == -1) {
			index = PERCENT_DISCHARGE.indexOf(new Integer(operId));
			if (index == -1) {
				return -1;
			}
			return ((Number) PERCENT_RECHARGE.get(index)).intValue();
		}
		return ((Number) RECHARGE.get(index)).intValue();
	}

	private static int getRechargeId(int year, boolean onVATChange) {
		int index = getYearIndex(year, !onVATChange);
		return ((Number) RECHARGE.get(index)).intValue();
	}

	private static int getDischargeId(int year, boolean onVATChange) {
		int index = getYearIndex(year, !onVATChange);
		return ((Number) DISCHARGE.get(index)).intValue();
	}

	public static int getCoupledOperationId(int operId) {
		if (isDischarge(operId)) {
			return getCoupledRecharge(operId);
		} else if (isRecharge(operId)) {
			return getCoupledDischarge(operId);
		}
		return -1;
	}

	private static int getPercentSubsRechargeId(int year) {
		int index = getYearIndex(year, !false);
		return ((Number) PERCENT_RECHARGE.get(index)).intValue();
	}

	private static int getPercentSubsDischargeId(int year) {
		int index = getYearIndex(year, false);
		return ((Number) PERCENT_DISCHARGE.get(index)).intValue();
	}

	private static int getBalanceCorrectionId(int year) {
		int index = getYearIndex(year, false);
		return ((Number) BALANCE_CORRECTION.get(index)).intValue();
	}

	private static int getServiceDischargeId(int year, boolean onVATChange) {
		int index = getYearIndex(year, !onVATChange);
		return ((Number) SERVICE_DISCHARGE.get(index)).intValue();
	}

	private static int getServiceRechargeId(int year, boolean onVATChange) {
		int index = getYearIndex(year, !onVATChange);
		return ((Number) SERVICE_RECHARGE.get(index)).intValue();
	}

	private static int getCompensationDischargeId(int year, boolean onVATChange) {
		int index = getYearIndex(year, !onVATChange);
		return ((Number) COMPENSATION_DISCHARGE.get(index)).intValue();
	}

	private static int getCompensationRechargeId(int year, boolean onVATChange) {
		int index = getYearIndex(year, !onVATChange);
		return ((Number) COMPENSATION_RECHARGE.get(index)).intValue();
	}

	private static int getPensionCorrectionId(int year) {
		int index = getYearIndex(year, false);
		return ((Number) PENSION_CORRECTION.get(index)).intValue();
	}

	private static int getUSAIDCorrectionId(int year) {
		int index = getYearIndex(year, false);
		return ((Number) USAID_CORRECTION.get(index)).intValue();
	}

	private static int getFixKwhSubsidyCorrectionId(int year) {
		int index = getYearIndex(year, false);
		return ((Number) FIX_KWH_CORRECTION.get(index)).intValue();
	}

	private static int getOneTimeActCorrectionId(int year, boolean onVATChange) {
		int index = getYearIndex(year, !onVATChange);
		return ((Number) ONE_TIME_ACTS.get(index)).intValue();
	}

	public static class DivisionDate implements IDivisionDate {
		private int type;
		private Date date;
		private int dischargeId, rechargeId, percentSubsDischargeId, percentSubsRechargeId;
		private int balanceCorrectionId;
		private int serviceDischargeId, serviceRechargeId;
		private int compensationDischargeId, compensationRechargeId;
		private int pensionCorrectionId;
		private int USAIDCorrectionId;
		private int fixKwhSubsidyCorrectionId;
		private int oneTimeActId;

		public Date getDate() {
			return date;
		}

		public int getDischargeId() {
			return dischargeId;
		}

		public int getRechargeId() {
			return rechargeId;
		}

		public int getPercentSubsDischargeId() {
			return percentSubsDischargeId;
		}

		public int getPercentSubsRechargeId() {
			return percentSubsRechargeId;
		}

		public int getType() {
			return type;
		}

		public int getBalanceCorrectionId() {
			return balanceCorrectionId;
		}

		public int getServiceDischargeId() {
			return serviceDischargeId;
		}

		public int getServiceRechargeId() {
			return serviceRechargeId;
		}

		public int getCompensationDischargeId() {
			return compensationDischargeId;
		}

		public int getCompensationRechargeId() {
			return compensationRechargeId;
		}

		public int getPensionCorrectionId() {
			return pensionCorrectionId;
		}

		public int getUSAIDCorrectionId() {
			return USAIDCorrectionId;
		}

		public int getFixKwhSubsidyCorrectionId() {
			return fixKwhSubsidyCorrectionId;
		}

		public int getOneTimeActCorrectionId() {
			return oneTimeActId;
		}

	}

	//
	// methods used for VOUCHER generation
	//

	public static int getDischargeId(Operation operation, IDivisionDate date) {
		int catId = operation.getDiffCategory();
		switch (catId) {
		case DiffDetail.VOUCHER:
			// return the same for the voucher
			return operation.getId();
		case DiffDetail.CHARGE:
			// if (operation.getId() == IOperation.SUB_ACCOUNT_CHARGE) {
			// return getCoupledRecharge(date.getDischargeId());
			// } else {
			return date.getDischargeId();
			// }
		case DiffDetail.PAYMENT:
			return 19;
		case DiffDetail.PENALTY:
			return 13;
		case DiffDetail.BALANCE:
			return date.getBalanceCorrectionId();
		case DiffDetail.SERVICE:
			if (operation.getId() == 84)
				return 151;
			return date.getServiceDischargeId();
		case DiffDetail.COMPENSATION:
			return date.getCompensationDischargeId();
		case DiffDetail.SUBSIDY_GENERAL:
			return 20;
		case DiffDetail.SUBSIDY_TARIFF_2003:
			return 147;
		case DiffDetail.SUBSIDY_TARIFF_2006:
			return 516;
		case DiffDetail.SUBSIDY_REFUGE:
			return 71;
		case DiffDetail.SUBSIDY_COMP_REFUGE:
			return 73;
		case DiffDetail.SUBSIDY_PENSION:
			return date.getPensionCorrectionId();
		case DiffDetail.SUBSIDY_EMPLOYEE:
			return 155;
		case DiffDetail.SUBSIDY_USAID:
			return date.getUSAIDCorrectionId();
		case DiffDetail.SUBSIDY_FIX_KWH:
			return date.getFixKwhSubsidyCorrectionId();
		case DiffDetail.SUBSIDY_PERCENT:
			return date.getPercentSubsDischargeId();
		case DiffDetail.ONE_TIME_ACT:
			return date.getOneTimeActCorrectionId();
		default:
			// throw new RecalcException("unknown category " + catId);
			// return -1;
			return operation.getId();
		}

	}

	public static int getRechargeId(Operation operation, IDivisionDate date) {
		int catId = operation.getDiffCategory();
		switch (catId) {
		case DiffDetail.VOUCHER:
			// return the same for the voucher
			return operation.getId();
		case DiffDetail.CHARGE:
			// if (operation.getId() == IOperation.SUB_ACCOUNT_CHARGE) {
			// return getCoupledDischarge(date.getRechargeId());
			// } else {
			return date.getRechargeId();
			// }
		case DiffDetail.PAYMENT:
			return 19;
		case DiffDetail.PENALTY:
			return 13;
		case DiffDetail.BALANCE:
			return date.getBalanceCorrectionId();
		case DiffDetail.SERVICE:
			if (operation.getId() == 84)
				return 151;
			return date.getServiceRechargeId();
		case DiffDetail.COMPENSATION:
			return date.getCompensationRechargeId();
		case DiffDetail.SUBSIDY_GENERAL:
			return 20;
		case DiffDetail.SUBSIDY_TARIFF_2003:
			return 147;
		case DiffDetail.SUBSIDY_TARIFF_2006:
			return 517;
		case DiffDetail.SUBSIDY_REFUGE:
			return 71;
		case DiffDetail.SUBSIDY_COMP_REFUGE:
			return 73;
		case DiffDetail.SUBSIDY_PENSION:
			return date.getPensionCorrectionId();
		case DiffDetail.SUBSIDY_EMPLOYEE:
			return 155;
		case DiffDetail.SUBSIDY_USAID:
			return date.getUSAIDCorrectionId();
		case DiffDetail.SUBSIDY_FIX_KWH:
			return date.getFixKwhSubsidyCorrectionId();
		case DiffDetail.SUBSIDY_PERCENT:
			return date.getPercentSubsRechargeId();
		case DiffDetail.ONE_TIME_ACT:
			return date.getOneTimeActCorrectionId();
		default:
			// throw new RecalcException("unknown category " + catId);
			// return -1;
			return operation.getId();
		}
	}

}
