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
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import telasi.recutil.beans.Account;
import telasi.recutil.beans.Address;
import telasi.recutil.beans.CalculationItem;
import telasi.recutil.beans.ChargeElement;
import telasi.recutil.beans.Customer;
import telasi.recutil.beans.Customer2;
import telasi.recutil.beans.Date;
import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.DummyTariff;
import telasi.recutil.beans.FacturaDetail;
import telasi.recutil.beans.ITariff;
import telasi.recutil.beans.Meter;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.Recalc2;
import telasi.recutil.beans.RecalcCutItem;
import telasi.recutil.beans.RecalcInstCp;
import telasi.recutil.beans.RecalcInterval;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.RecalcRegularItem;
import telasi.recutil.beans.RecalcRoomItem;
import telasi.recutil.beans.RecalcSubsidyAttachment;
import telasi.recutil.beans.RecalcTariffItem;
import telasi.recutil.beans.RecalcVoucher;
import telasi.recutil.beans.TrashVoucher;
import telasi.recutil.beans.User;
import telasi.recutil.calc.RecalcException;
import telasi.recutil.calc.calc07.Calculator;
import telasi.recutil.calc.calc07.DbUtilities;
import telasi.recutil.ejb.DefaultEJBResponse;
import telasi.recutil.ejb.Request;
import telasi.recutil.ejb.RequestException;
import telasi.recutil.ejb.Response;
import telasi.recutil.service.Session;
import telasi.recutil.service.eclipse.recalc.CheckRecalcExistenceRequest;
import telasi.recutil.service.eclipse.recalc.DownloadFullRecalcRequest;
import telasi.recutil.service.eclipse.recalc.DownloadFullRecalcRequest2;
import telasi.recutil.service.eclipse.recalc.RebuildRequest;
import telasi.recutil.service.eclipse.recalc.RecalcCopyRequest;
import telasi.recutil.service.eclipse.recalc.RecalcCutDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcCutInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcCutMoveRequest;
import telasi.recutil.service.eclipse.recalc.RecalcCutSelectRequest;
import telasi.recutil.service.eclipse.recalc.RecalcCutUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcFinalizeRequest;
import telasi.recutil.service.eclipse.recalc.RecalcInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcInstcpDefaultRequest;
import telasi.recutil.service.eclipse.recalc.RecalcInstcpDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcInstcpInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcInstcpMoveRequest;
import telasi.recutil.service.eclipse.recalc.RecalcInstcpSelectRequest;
import telasi.recutil.service.eclipse.recalc.RecalcInstcpUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcIntervalDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcIntervalInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcIntervalSelectRequest;
import telasi.recutil.service.eclipse.recalc.RecalcIntervalUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemEnableRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemHintRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemMeterUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemMoveRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemRestoreOriginalRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemSubsAttUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcItemUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRegularDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRegularInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRegularMoveRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRegularSelectRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRegularUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRequest2;
import telasi.recutil.service.eclipse.recalc.RecalcRequest3;
import telasi.recutil.service.eclipse.recalc.RecalcRoomDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRoomInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRoomSelectRequest;
import telasi.recutil.service.eclipse.recalc.RecalcRoomUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcSaveRequest;
import telasi.recutil.service.eclipse.recalc.RecalcSaveRequest2;
import telasi.recutil.service.eclipse.recalc.RecalcSaveRequest3;
import telasi.recutil.service.eclipse.recalc.RecalcSearchRequest;
import telasi.recutil.service.eclipse.recalc.RecalcSearchRequest2;
import telasi.recutil.service.eclipse.recalc.RecalcSelectRequest;
import telasi.recutil.service.eclipse.recalc.RecalcTariffDefaultRequest;
import telasi.recutil.service.eclipse.recalc.RecalcTariffDeleteRequest;
import telasi.recutil.service.eclipse.recalc.RecalcTariffInsertRequest;
import telasi.recutil.service.eclipse.recalc.RecalcTariffMoveRequest;
import telasi.recutil.service.eclipse.recalc.RecalcTariffSelectRequest;
import telasi.recutil.service.eclipse.recalc.RecalcTariffUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcUnblockRequest;
import telasi.recutil.service.eclipse.recalc.RecalcUpdateRequest;
import telasi.recutil.service.eclipse.recalc.RecalcVoucherSelectRequest;
import telasi.recutil.service.eclipse.recalc.RecalcVoucherSelectRequest2;
import telasi.recutil.service.eclipse.recalc.RecalcVoucherSelectRequest3;
import telasi.recutil.service.eclipse.recalc.SaveFullRecalcRequest;
import telasi.recutil.service.eclipse.recalc.SaveFullRecalcRequest2;

/**
 * This class is used for recalculation managment.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Nov, 2006
 */
public class E1RecalcManager {

	public static Response processRebuild(Request req, Session session) throws RequestException {
		try {
			RebuildRequest request = (RebuildRequest) req;
			Recalc recalc = request.getRecalc();
			Calculator calc = new Calculator();
			List problems = new ArrayList();
			List problemsOnRebuild = calc.rebuild(recalc);
			if (problemsOnRebuild != null)
				problems.addAll(problemsOnRebuild);
			request.setProblems(problems.isEmpty() ? null : problems);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalc(Request req, Session session) throws RequestException {
		try {
			if (req instanceof RecalcRequest3) {
				RecalcRequest3 request = (RecalcRequest3) req;
				Recalc recalc = request.getRecalc();
				Calculator calc = new Calculator();
				request.setIntervals(calc.calculate(session, recalc));
				request.setDiffByCycle(calc.getDiffByCycle());
				request.setDiffSummary(calc.getDiffSummary());
				request.setFacturaExpansion(calc.getFacturaExpansion());
				request.setTrashVouchers(calc.getTrashVouchers());
				return new DefaultEJBResponse(request);
			} else if (req instanceof RecalcRequest2) {
				RecalcRequest2 request = (RecalcRequest2) req;
				Recalc recalc = request.getRecalc();
				Calculator calc = new Calculator();
				request.setIntervals(calc.calculate(session, recalc));
				request.setDiffByCycle(calc.getDiffByCycle());
				request.setDiffSummary(calc.getDiffSummary());
				request.setFacturaExpansion(calc.getFacturaExpansion());
				return new DefaultEJBResponse(request);
			} else if (req instanceof RecalcRequest) {
				RecalcRequest request = (RecalcRequest) req;
				Recalc recalc = request.getRecalc();
				Calculator calc = new Calculator();
				request.setIntervals(calc.calculate(session, recalc));
				request.setDiffByCycle(calc.getDiffByCycle());
				request.setDiffSummary(calc.getDiffSummary());
				return new DefaultEJBResponse(request);
			}
			throw new IllegalArgumentException("Not supported request!");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcHistorySelectRequest(Request req, Session session) throws RequestException {
		try {
			RecalcSelectRequest request = (RecalcSelectRequest) req;
			String version = (String) req.getProperties().get(Request.CLIENT_STRING);
			boolean isLowerThan5 = Request.ECLIPSE_CLIENT_1_0_0.equals(version) || Request.ECLIPSE_CLIENT_1_0_1.equals(version) || Request.ECLIPSE_CLIENT_1_0_2.equals(version) || Request.ECLIPSE_CLIENT_1_0_3.equals(version);
			request.setHistory(selectRecalcHistory(request.getCustomer(), session, isLowerThan5));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcInsert(Request req, Session session) throws RequestException {
		try {
			RecalcInsertRequest request = (RecalcInsertRequest) req;
			long userId = request.getUser().getId();
			request.setId(insertRecalc(request.getRecalculation(), userId, session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcCopy(Request req, Session session) throws RequestException {
		try {
			RecalcCopyRequest request = (RecalcCopyRequest) req;
			long userId = request.getUser().getId();
			Recalc recalc = request.getRecalculation();
			Recalc2 copy = copyRecalc(recalc, userId, session);
			request.setId(copy.getId());
			copy.setStatus(Recalc2.STATUS_DEFAULT);
			copy.setAdvisor(null);
			copy.setSaveUser(null);
			copy.setCreateDate(Date.create(System.currentTimeMillis()));
			copy.setSaveDate(null);
			copy.setCustomer(recalc.getCustomer());
			copy.setAccount(recalc.getAccount());
			request.setRecalculation(copy);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcDelete(Request req, Session session) throws RequestException {
		try {
			RecalcDeleteRequest request = (RecalcDeleteRequest) req;
			deleteRecalc(request.getRecalculation(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcUpdateRequest request = (RecalcUpdateRequest) req;
			updateRecalc(request.getRecalculation(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcIntervalSelect(Request req, Session session) throws RequestException {
		try {
			RecalcIntervalSelectRequest request = (RecalcIntervalSelectRequest) req;
			request.setIntervals(getRecalcIntervals(request.getRecalc(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcIntervalInsert(Request req, Session session) throws RequestException {
		try {
			RecalcIntervalInsertRequest request = (RecalcIntervalInsertRequest) req;
			insertRecalcInterval(request, session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcIntervalUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcIntervalUpdateRequest request = (RecalcIntervalUpdateRequest) req;
			updateRecalcInterval(request.getRecalcInterval(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcIntervalDelete(Request req, Session session) throws RequestException {
		try {
			RecalcIntervalDeleteRequest request = (RecalcIntervalDeleteRequest) req;
			deleteRecalcInterval(request.getRecalcInterval(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcItemInsert(Request req, Session session) throws RequestException {
		try {
			RecalcItemInsertRequest request = (RecalcItemInsertRequest) req;
			long id = insertRecalcItem(request.getRecalcItem(), request.getSequence(), session);
			request.setId(id);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcItemUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcItemUpdateRequest request = (RecalcItemUpdateRequest) req;
			updateRecalcItem(request.getRecalcItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcItemDelete(Request req, Session session) throws RequestException {
		try {
			RecalcItemDeleteRequest request = (RecalcItemDeleteRequest) req;
			deleteRecalcItem(request.getRecalcItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcItemEnable(Request req, Session session) throws RequestException {
		try {
			RecalcItemEnableRequest request = (RecalcItemEnableRequest) req;
			enableRecalcItem(request.getRecalcItem(), request.isEnabled(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcItemMove(Request req, Session session) throws RequestException {
		try {
			RecalcItemMoveRequest request = (RecalcItemMoveRequest) req;
			moveRecalcItem(request.getRecalcItem(), request.moveUp(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcItemKwhLock(Request req, Session session) throws RequestException {
		try {
			RecalcItemHintRequest request = (RecalcItemHintRequest) req;
			setHintForRecalcItem(request.getRecalcItem(), request.getHint(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcItemSubsAttUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcItemSubsAttUpdateRequest request = (RecalcItemSubsAttUpdateRequest) req;
			recalcItemSubsAttUpdate(request.getRecalcItem(), request.getAttachment(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcItemMeterUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcItemMeterUpdateRequest request = (RecalcItemMeterUpdateRequest) req;
			recalcItemMeterUpdate(request.getItem().getId(), request.getMeterId(), request.getMeterCoeff(), request.getMeterStatus(), request.getMeterAcceleration(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcItemOriginalsRestore(Request req, Session session) throws RequestException {
		try {
			RecalcItemRestoreOriginalRequest request = (RecalcItemRestoreOriginalRequest) req;
			itemRestoreOriginal(request.getRecalcItem().getId(), request.getOption(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcRegularItemSelet(Request req, Session session) throws RequestException {
		try {
			RecalcRegularSelectRequest request = (RecalcRegularSelectRequest) req;
			request.setItems(selectRegular(request.getRecalc(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcRegularItemInsert(Request req, Session session) throws RequestException {
		try {
			RecalcRegularInsertRequest request = (RecalcRegularInsertRequest) req;
			request.setId(insertRegular(request.getItem(), request.getSequence(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcRegularItemDelete(Request req, Session session) throws RequestException {
		try {
			RecalcRegularDeleteRequest request = (RecalcRegularDeleteRequest) req;
			deleteRegular(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcRegularItemUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcRegularUpdateRequest request = (RecalcRegularUpdateRequest) req;
			updateRegular(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcRegularItemMove(Request req, Session session) throws RequestException {
		try {
			RecalcRegularMoveRequest request = (RecalcRegularMoveRequest) req;
			moveRegular(request.getItem(), request.moveUp(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processInstcpSelect(Request req, Session session) throws RequestException {
		try {
			RecalcInstcpSelectRequest request = (RecalcInstcpSelectRequest) req;
			request.setInstcps(selectInspCp(request.getRecalc(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processInstcpDefault(Request req, Session session) throws RequestException {
		try {
			RecalcInstcpDefaultRequest request = (RecalcInstcpDefaultRequest) req;
			generateDefaultInstCp(request.getRecalc(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processInstcpInsert(Request req, Session session) throws RequestException {
		try {
			RecalcInstcpInsertRequest request = (RecalcInstcpInsertRequest) req;
			request.setId(insertInstcp(request.getItem(), request.getSequence(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processInstcpUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcInstcpUpdateRequest request = (RecalcInstcpUpdateRequest) req;
			updateInstcp(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processInstcpDelete(Request req, Session session) throws RequestException {
		try {
			RecalcInstcpDeleteRequest request = (RecalcInstcpDeleteRequest) req;
			deleteInstcp(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processInstcpMove(Request req, Session session) throws RequestException {
		try {
			RecalcInstcpMoveRequest request = (RecalcInstcpMoveRequest) req;
			moveInstcp(request.getItem(), request.moveUp(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTariffDefault(Request req, Session session) throws RequestException {
		try {
			RecalcTariffDefaultRequest request = (RecalcTariffDefaultRequest) req;
			tariffDefault(request.getRecalc(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTariffSelect(Request req, Session session) throws RequestException {
		try {
			RecalcTariffSelectRequest request = (RecalcTariffSelectRequest) req;
			request.setTariffs(tariffSelect(request.getRecalc(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTariffInsert(Request req, Session session) throws RequestException {
		try {
			RecalcTariffInsertRequest request = (RecalcTariffInsertRequest) req;
			request.setId(tariffInsert(request.getItem(), request.getSequence(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTariffUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcTariffUpdateRequest request = (RecalcTariffUpdateRequest) req;
			tariffUpdate(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTariffDelete(Request req, Session session) throws RequestException {
		try {
			RecalcTariffDeleteRequest request = (RecalcTariffDeleteRequest) req;
			tariffDelete(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processTariffMove(Request req, Session session) throws RequestException {
		try {
			RecalcTariffMoveRequest request = (RecalcTariffMoveRequest) req;
			tariffMove(request.getItem(), request.moveUp(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processCutSelect(Request req, Session session) throws RequestException {
		try {
			RecalcCutSelectRequest request = (RecalcCutSelectRequest) req;
			request.setCuts(selectCut(request.getRecalc(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processCutInsert(Request req, Session session) throws RequestException {
		try {
			RecalcCutInsertRequest request = (RecalcCutInsertRequest) req;
			request.setId(insertCut(request.getItem(), request.getSequence(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processCutUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcCutUpdateRequest request = (RecalcCutUpdateRequest) req;
			updateCut(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processCutDelete(Request req, Session session) throws RequestException {
		try {
			RecalcCutDeleteRequest request = (RecalcCutDeleteRequest) req;
			deleteCut(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processCutMove(Request req, Session session) throws RequestException {
		try {
			RecalcCutMoveRequest request = (RecalcCutMoveRequest) req;
			moveCut(request.getItem(), request.moveUp(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRoomSelect(Request req, Session session) throws RequestException {
		try {
			RecalcRoomSelectRequest request = (RecalcRoomSelectRequest) req;
			request.setRooms(selectRoom(request.getRecalc(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRoomInsert(Request req, Session session) throws RequestException {
		try {
			RecalcRoomInsertRequest request = (RecalcRoomInsertRequest) req;
			request.setId(insertRoom(request.getRecalc().getId(), request.getItem(), session));
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRoomUpdate(Request req, Session session) throws RequestException {
		try {
			RecalcRoomUpdateRequest request = (RecalcRoomUpdateRequest) req;
			updateRoom(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRoomDelete(Request req, Session session) throws RequestException {
		try {
			RecalcRoomDeleteRequest request = (RecalcRoomDeleteRequest) req;
			deleteRoom(request.getItem(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcSave(Request req, Session session) throws RequestException {
		try {
			if (req instanceof RecalcSaveRequest3) {
				RecalcSaveRequest3 request = (RecalcSaveRequest3) req;
				saveRecalc(request.getRecalc(), request.getSummary(), request.getFacturaExpansion(), request.getTrashList(), session);
				return new DefaultEJBResponse(request);
			} else if (req instanceof RecalcSaveRequest2) {
				RecalcSaveRequest2 request = (RecalcSaveRequest2) req;
				saveRecalc(request.getRecalc(), request.getSummary(), request.getFacturaExpansion(), null, session);
				return new DefaultEJBResponse(request);
			} else if (req instanceof RecalcSaveRequest) {
				RecalcSaveRequest request = (RecalcSaveRequest) req;
				// without factura expansion
				saveRecalc(request.getRecalc(), request.getSummary(), null, null, session);
				return new DefaultEJBResponse(request);
			}
			throw new IllegalArgumentException("Not suppoted request type");
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcVoucherSelect(Request req, Session session) throws RequestException {
		try {
			if (req instanceof RecalcVoucherSelectRequest) {
				RecalcVoucherSelectRequest request = (RecalcVoucherSelectRequest) req;
				request.setVoucher(selectVoucher(request.getRecalc(), session));
				return new DefaultEJBResponse(request);
			} else if (req instanceof RecalcVoucherSelectRequest2) {
				RecalcVoucherSelectRequest2 request = (RecalcVoucherSelectRequest2) req;
				request.setVoucher(selectVoucher(request.getRecalc(), session));
				request.setFacturaDetails(selectFacturaExpansion(request.getRecalc().getId(), session));
				return new DefaultEJBResponse(request);
			} else if (req instanceof RecalcVoucherSelectRequest3) {
				RecalcVoucherSelectRequest3 request = (RecalcVoucherSelectRequest3) req;
				request.setVoucher(selectVoucher(request.getRecalc(), session));
				request.setFacturaDetails(selectFacturaExpansion(request.getRecalc().getId(), session));
				request.setTrashVouchers(selectTrashVouchers(request.getRecalc().getId(), session));
				return new DefaultEJBResponse(request);
			} else {
				throw new IllegalArgumentException("Not supported class");
			}
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcFinalize(Request req, Session session) throws RequestException {
		try {
			RecalcFinalizeRequest request = (RecalcFinalizeRequest) req;
			finalizeRecalc(request.getRecalcId(), request.isFinalize(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcUnblock(Request req, Session session) throws RequestException {
		try {
			RecalcUnblockRequest request = (RecalcUnblockRequest) req;
			unblockRecalc(request.getRecalcId(), session);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processRecalcSearch(Request req, Session session) throws RequestException {
		try {
			// simple search
			if (req instanceof RecalcSearchRequest) {
				RecalcSearchRequest request = (RecalcSearchRequest) req;
				String version = (String) req.getProperties().get(Request.CLIENT_STRING);
				boolean isLowerThan5 = Request.ECLIPSE_CLIENT_1_0_0.equals(version) || Request.ECLIPSE_CLIENT_1_0_1.equals(version) || Request.ECLIPSE_CLIENT_1_0_2.equals(version) || Request.ECLIPSE_CLIENT_1_0_3.equals(version);
				List recalcs = searchRecalcs(request.getDate(), null, null, request.isSaveDate(), isLowerThan5, true, session);
				request.setResults(recalcs);
				List roles = E1UserManagment.selectRoles(session);
				for (int i = 0; recalcs != null && i < recalcs.size(); i++) {
					Recalc recalc = (Recalc) recalcs.get(i);
					User saveUser = recalc.getSaveUser() == null ? null : E1UserManagment.searchUserById(recalc.getSaveUser().getId(), roles);
					User advisor = recalc.getAdvisor() == null ? null : E1UserManagment.searchUserById(recalc.getAdvisor().getId(), roles);
					if (saveUser != null) {
						recalc.setSaveUser(saveUser);
					}
					if (advisor != null) {
						recalc.setAdvisor(advisor);
					}
				}
				return new DefaultEJBResponse(request);
			}
			// expanded search
			else {
				RecalcSearchRequest2 request = (RecalcSearchRequest2) req;
				List recalcs = searchRecalcs(request.getStartDate(), request.getEndDate(), request.getRecalcNumber(), request.isSaveDate(), false, false, session);
				request.setResults(recalcs);
				return new DefaultEJBResponse(request);
			}
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processCheckRecalcExistence(Request req, Session session) throws RequestException {
		try {
			CheckRecalcExistenceRequest request = (CheckRecalcExistenceRequest) req;
			request.setExists(checkRecalculationExistence(request.getRecalcNumber(), request.getCustomerId(), session) != -1);
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processFullRecalcDownload(Request req, Session session) throws RequestException {
		try {
			DownloadFullRecalcRequest request = (DownloadFullRecalcRequest) req;
			// fill recalculation details
			Recalc recalc = request.getRecalc();
			recalc.getDetails().getCuts().clear();
			recalc.getDetails().getTariffs().clear();
			recalc.getDetails().getInstCpItems().clear();
			recalc.getDetails().getIntervals().clear();
			recalc.getDetails().getRegulars().clear();
			List cuts = selectCut(recalc, session);
			List tariffs = tariffSelect(recalc, session);
			List instcps = selectInspCp(recalc, session);
			List intervals = getRecalcIntervals(recalc, session);
			List regulars = selectRegular(recalc, session);
			recalc.getDetails().getCuts().addAll(cuts == null ? new ArrayList() : cuts);
			recalc.getDetails().getTariffs().addAll(tariffs == null ? new ArrayList() : tariffs);
			recalc.getDetails().getInstCpItems().addAll(instcps == null ? new ArrayList() : instcps);
			recalc.getDetails().getIntervals().addAll(intervals == null ? new ArrayList() : intervals);
			recalc.getDetails().getRegulars().addAll(regulars == null ? new ArrayList() : regulars);
			// getting rooms
			request.setRooms(selectRoom(recalc, session));
			// get voucher
			request.setVoucher(selectVoucher(recalc, session));
			// get factura expansion
			request.setFacturaDetails(selectFacturaExpansion(recalc.getId(), session));
			if (request instanceof DownloadFullRecalcRequest2) {
				DownloadFullRecalcRequest2 request2 = (DownloadFullRecalcRequest2) request;
				request2.setTrashVouchers(selectTrashVouchers(recalc.getId(), session));
			}
			// return response
			return new DefaultEJBResponse(request);
		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	public static Response processFullRecalcSave(Request req, Session session) throws RequestException {
		try {
			// manage requests
			SaveFullRecalcRequest request = (SaveFullRecalcRequest) req;
			Recalc recalc = request.getRecalc();
			boolean recalc2 = recalc.getClass().equals(Recalc2.class);
			List roles = E1UserManagment.selectRoles(session);

			// adjust operator
			User operator = recalc.getSaveUser();
			if (operator != null) {
				User newOperator = E1UserManagment.searchUserByUsername(operator.getUserName(), roles);
				if (newOperator == null) {
					throw new RequestException(String.format("Can not find user %1$s", new Object[] { operator.getUserName() }));
				}
				recalc.setSaveUser(newOperator);
			}

			// adjust advisor
			User advisor = recalc.getAdvisor();
			if (advisor != null) {
				User newAdvisor = E1UserManagment.searchUserByUsername(advisor.getUserName(), roles);
				if (newAdvisor == null) {
					throw new RequestException(String.format("Can not find user %1$s", new Object[] { advisor.getUserName() }));
				}
				recalc.setAdvisor(newAdvisor);
			}

			// define recalculation ID
			long recalcId = checkRecalculationExistence(recalc.getNumber(), recalc.getCustomer().getId(), session);
			int originalStatus = -1;
			if (recalc2) {
				originalStatus = ((Recalc2) recalc).getStatus();
			} else {
				originalStatus = recalc.isChanged() ? Recalc2.STATUS_DEFAULT : Recalc2.STATUS_SAVED;
			}
			if (recalcId != -1) {
				recalc.setId(recalcId);
				if (recalc2) {
					((Recalc2) recalc).setStatus(Recalc2.STATUS_DEFAULT);
				} else {
					recalc.setChanged(true);
				}
				updateRecalcFull(recalc, session);
				deleteRecalcDetails(recalcId, session);
			} else {
				if (recalc2) {
					((Recalc2) recalc).setStatus(Recalc2.STATUS_DEFAULT);
				} else {
					recalc.setChanged(true);
				}
				recalcId = insertRecalcFull(recalc, session);
				recalc.setId(recalcId);
			}

			// insert cuts
			List cuts = recalc.getDetails().getCuts();
			for (int i = 0; cuts != null && i < cuts.size(); i++) {
				RecalcCutItem cut = (RecalcCutItem) cuts.get(i);
				cut.setDetails(recalc.getDetails());
				insertCut(cut, i, session);
			}
			// isert tariffs
			List tariffs = recalc.getDetails().getTariffs();
			for (int i = 0; tariffs != null && i < tariffs.size(); i++) {
				RecalcTariffItem tariff = (RecalcTariffItem) tariffs.get(i);
				tariff.setDetails(recalc.getDetails());
				tariffInsert(tariff, i, session);
			}
			// insert regulars
			List regulars = recalc.getDetails().getRegulars();
			for (int i = 0; regulars != null && i < regulars.size(); i++) {
				RecalcRegularItem regular = (RecalcRegularItem) regulars.get(i);
				regular.setDetails(recalc.getDetails());
				insertRegular(regular, i, session);
			}
			// installed capacities
			List instcps = recalc.getDetails().getInstCpItems();
			for (int i = 0; instcps != null && i < instcps.size(); i++) {
				RecalcInstCp instcp = (RecalcInstCp) instcps.get(i);
				instcp.setDetails(recalc.getDetails());
				insertInstcp(instcp, i, session);
			}
			// rooms
			List rooms = request.getRooms();
			for (int i = 0; rooms != null && i < rooms.size(); i++) {
				RecalcRoomItem room = (RecalcRoomItem) rooms.get(i);
				insertRoom(recalcId, room, session);
			}

			if (request.getVoucher().getDetails() != null && !request.getVoucher().getDetails().isEmpty()) {
				// factura
				saveFactura(request.getFacturaDetails(), recalc, session);
				// voucher details
				saveVoucherDetails(request.getVoucher().getDetails(), recalc, session);
			}

			// trash vouchers
			if (request instanceof SaveFullRecalcRequest2) {
				SaveFullRecalcRequest2 request2 = (SaveFullRecalcRequest2) request;
				List trashVouchers = request2.getTrashVouchers();
				saveTrashVouchers(trashVouchers, recalc, session);
			}

			// add intervals and items
			for (int i = 0; i < recalc.getDetails().getIntervals().size(); i++) {
				RecalcInterval interval = (RecalcInterval) recalc.getDetails().getIntervals().get(i);
				insertIntervalFull(recalc, i, interval, session);
			}

			// close recalculation
			if (originalStatus != Recalc2.STATUS_DEFAULT) {
				if (recalc2) {
					((Recalc2) recalc).setStatus(originalStatus);
				} else {
					recalc.setChanged(originalStatus == Recalc2.STATUS_DEFAULT);
				}
				updateRecalcFull(recalc, session);
			}

			// nothing to return
			return new DefaultEJBResponse(null);

		} catch (Exception ex) {
			throw new RequestException(ex.toString(), ex);
		}
	}

	// ///////////////////////////////////////////////////////////////////////
	//
	// Database interaction
	//
	// ///////////////////////////////////////////////////////////////////////

	public static Recalc getRecalcById(int id, String clientString, Session session) throws Exception {
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			Recalc recalc = new Recalc();
			recalc.setId(id);
			String sql = "select R.*, c.accnumb from bs.customer c, recut.recalc r " + "where c.custkey = r.customer and r.id = ?";
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, id);
			res = st.executeQuery();
			res.next();
			String accNumb = res.getString("accnumb");
			int accId = res.getInt("account");

			Customer cust = E1CustomerManagment.findCustomerByAccNumber(accNumb, clientString, session);
			recalc.setCustomer(cust);
			for (int i = 0; i < cust.getAccounts().size(); i++) {
				Account acc = (Account) cust.getAccounts().get(i);
				if (acc.getId() == accId) {
					recalc.setAccount(acc);
					break;
				}
			}

			List regulars = selectRegular(recalc, session);
			List cuts = selectCut(recalc, session);
			List instcp = selectInspCp(recalc, session);
			List intervals = getRecalcIntervals(recalc, session);
			List tariffs = tariffSelect(recalc, session);

			for (int i = 0; i < regulars.size(); i++) {
				RecalcRegularItem item = (RecalcRegularItem) regulars.get(i);
				item.setDetails(recalc.getDetails());
				recalc.getDetails().getRegulars().add(item);
			}
			for (int i = 0; i < cuts.size(); i++) {
				RecalcCutItem item = (RecalcCutItem) cuts.get(i);
				item.setDetails(recalc.getDetails());
				recalc.getDetails().getCuts().add(item);
			}
			for (int i = 0; i < instcp.size(); i++) {
				RecalcInstCp item = (RecalcInstCp) instcp.get(i);
				item.setDetails(recalc.getDetails());
				recalc.getDetails().getInstCpItems().add(item);
			}
			for (int i = 0; i < intervals.size(); i++) {
				RecalcInterval item = (RecalcInterval) intervals.get(i);
				item.setDetails(recalc.getDetails());
				recalc.getDetails().getIntervals().add(item);
			}
			for (int i = 0; i < tariffs.size(); i++) {
				RecalcTariffItem item = (RecalcTariffItem) tariffs.get(i);
				item.setDetails(recalc.getDetails());
				recalc.getDetails().getTariffs().add(item);
			}

			for (int i = 0; i < recalc.getDetails().getIntervals().size(); i++) {
				RecalcInterval interval = (RecalcInterval) recalc.getDetails().getIntervals().get(i);
				for (int j = 0; j < interval.getItems().size(); j++) {
					RecalcItem it = (RecalcItem) interval.getItems().get(j);
					it.setOperation(DbUtilities.findOperationById(it.getOperation().getId()));
					it.setMeter(DbUtilities.findMeterById(it.getMeter().getId()));
				}
			}

			for (int i = 0; i < recalc.getDetails().getRegulars().size(); i++) {
				RecalcRegularItem regular = (RecalcRegularItem) recalc.getDetails().getRegulars().get(i);
				regular.setOperation(DbUtilities.findOperationById(regular.getOperation().getId()));
			}

			for (int i = 0; i < recalc.getDetails().getTariffs().size(); i++) {
				RecalcTariffItem tariff = (RecalcTariffItem) recalc.getDetails().getTariffs().get(i);
				tariff.setTariff(DbUtilities.findTariffById(tariff.getTariff().getId()));
			}

			return recalc;
		} finally {
			try {
				res.close();
			} catch (Exception ex) {
			}
			try {
				st.close();
			} catch (Exception ex) {
			}
			res = null;
			st = null;
		}
	}

	// select recalculation history
	public static List selectRecalcHistory(Customer customer, Session session, boolean isLowerThan5) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_history_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, customer.getId());
			res = st.executeQuery();
			List history = new ArrayList();
			while (res.next()) {
				Recalc recalc = null;
				if (isLowerThan5) {
					recalc = new Recalc();
				} else {
					recalc = new Recalc2();
				}
				recalc.setId(res.getLong("ID"));
				recalc.setNumber(res.getString("RECALC_NUMBER"));
				recalc.setCustomer(customer);
				recalc.setCreateDate(Date.create(res.getTimestamp("CREATE_DATE")));
				int accId = res.getInt("ACCOUNT");
				for (int i = 0; i < customer.getAccounts().size(); i++) {
					Account acc = (Account) customer.getAccounts().get(i);
					if (acc.getId() == accId) {
						recalc.setAccount(acc);
						break;
					}
				}

				recalc.setDescription(res.getString("DESCRIPTION"));

				if (isLowerThan5) {
					recalc.setChanged(res.getInt("IS_CHANGED") != 0);
				} else {
					((Recalc2) recalc).setStatus(res.getInt("IS_CHANGED"));
				}

				recalc.setSaveDate(Date.create(res.getTimestamp("SAVE_DATE")));

				Number savePersonId = (Number) res.getObject("SAVE_PERSON_ID");
				if (savePersonId != null) {
					User savePerson = new User(savePersonId.longValue());
					recalc.setSaveUser(savePerson);
				}

				Number advisorId = (Number) res.getObject("ADVISOR_ID");
				if (advisorId != null) {
					User advisor = new User(advisorId.longValue());
					recalc.setAdvisor(advisor);
				}

				recalc.setInitialBalance(res.getDouble("INIT_BALANCE"));
				Number finalBalance = (Number) res.getObject("FINAL_BALANCE");
				if (finalBalance != null) {
					recalc.setFinalBalance(new Double(finalBalance.doubleValue()));
				}

				history.add(recalc);
			}
			return history;
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

	// insert NEW recalculation
	public static long insertRecalc(Recalc recalc, long userId, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_insert.sql");
		CallableStatement st = null;
		PreparedStatement st2 = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setInt(1, recalc.getCustomer().getId());
			st.setInt(2, recalc.getAccount().getId());
			st.setLong(3, userId);
			if (recalc.getDescription() == null || recalc.getDescription().trim().length() == 0) {
				st.setNull(4, Types.VARCHAR);
			} else {
				st.setString(4, recalc.getDescription().trim());
			}
			st.setNull(5, Types.NUMERIC);
			st.registerOutParameter(5, Types.NUMERIC);
			st.execute();
			long id = st.getLong(5);
			System.out.println(id);

			// System.out.println("ID = " + id);
			st2 = session.getConnection().prepareStatement("SELECT RECALC_NUMBER FROM RECUT.RECALC WHERE ID=?");
			st2.setLong(1, id);
			res = st2.executeQuery();
			// System.out.println("Has Next? = " + res.next());
			res.next();
			recalc.setNumber(res.getString(1));

			return id;
		} finally {
			try {
				res.close();
			} catch (Exception ex) {
			}
			try {
				st.close();
			} catch (Exception ex) {
			}
			try {
				st2.close();
			} catch (Exception ex) {
			}
			st = null;
			res = null;
			st2 = null;
		}
	}

	// insert NEW recalculation
	public static Recalc2 copyRecalc(Recalc recalc, long userId, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_copy.sql");
		CallableStatement st = null;
		try {
			// copy recalculation
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, recalc.getId());
			st.setLong(2, userId);
			st.setNull(3, Types.NUMERIC);
			st.setNull(4, Types.VARCHAR);
			st.registerOutParameter(3, Types.NUMERIC);
			st.registerOutParameter(4, Types.VARCHAR);
			st.execute();
			long id = st.getLong(3);
			String number = st.getString(4);
			// create copy
			Recalc2 copy = new Recalc2();
			copy.setId(id);
			copy.setNumber(number);
			return copy;
		} finally {
			try {
				st.close();
			} catch (Exception ex) {
			}
			st = null;
		}
	}

	// delete recalculation
	public static void deleteRecalc(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_delete.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	// update recalculation
	public static void updateRecalc(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_update.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			st.setString(2, recalc.getNumber());
			if (recalc.getDescription() == null) {
				st.setNull(3, Types.VARCHAR);
			} else {
				st.setString(3, recalc.getDescription());
			}
			if (recalc.getAdvisor() == null) {
				st.setNull(4, Types.NUMERIC);
			} else {
				st.setLong(4, recalc.getAdvisor().getId());
			}
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	// insert recalculation interval
	public static void insertRecalcInterval(RecalcIntervalInsertRequest request, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_interval_insert.sql");
		CallableStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, request.getRecalcId());
			st.setString(2, request.getRecalcIntervalName());
			st.setLong(3, request.getItem1Id());
			st.setLong(4, request.getItem2Id());
			st.setLong(5, -1L);
			st.registerOutParameter(5, Types.NUMERIC);
			st.execute();
			request.setIntervalId(st.getLong(5));
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	// select recalculation intervals with their items
	public static List getRecalcIntervals(Recalc recalc, Session session) throws SQLException, IOException {
		PreparedStatement st = null;
		String sql = SQLReader.readSQL("recalc/recalc_interval_select.sql");
		ResultSet res = null;

		List intervals = new ArrayList();

		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			res = st.executeQuery();

			RecalcInterval prevInterval = null;

			while (res.next()) {
				long interval_id = res.getLong("interval_id");
				String interval_name = res.getString("interval_name");
				// int interval_sequence = res.getInt("interval_sequence");
				double start_balance = res.getDouble("start_balance");
				long vouhcer_item_id = res.getLong("recalc_item_id");
				int interval_editable = res.getInt("interval_editable");
				long item_id = res.getLong("item_id");
				int operation_id = res.getInt("operation_id");
				int item_status = res.getInt("item_status");
				double reading = res.getDouble("reading");
				double kwh = res.getDouble("kwh");
				double gel = res.getDouble("gel");
				double balance = res.getDouble("balance");
				Number cycle_id = (Number) res.getObject("cycle_id");
				// Date cycle_date =
				// Date.create(res.getTimestamp("cycle_date"));
				Date item_date = Date.create(res.getTimestamp("item_date"));
				Date enter_date = Date.create(res.getTimestamp("enter_date"));
				Date curr_date = Date.create(res.getTimestamp("curr_date"));
				Date prev_date = Date.create(res.getTimestamp("prev_date"));
				double meter_coeff = res.getDouble("meter_coeff");
				int meter_status = res.getInt("meter_status");
				int meter_type_id = res.getInt("meter_type_id");
				double meter_accelerate = res.getDouble("meter_accelerate");
				String item_number = res.getString("item_number");
				Number att_unit = (Number) res.getObject("att_unit");
				Number att_amount = (Number) res.getObject("att_amount");
				Number att_count = (Number) res.getObject("att_count");
				Number orig_att_unit = (Number) res.getObject("orig_att_unit");
				Number orig_att_amount = (Number) res.getObject("orig_att_amount");
				Number orig_att_count = (Number) res.getObject("orig_att_count");
				// int item_sequence = res.getInt("item_sequence");
				Number orig_operation_id = (Number) res.getObject("orig_operation_id");
				Timestamp orig_item_date = res.getTimestamp("orig_item_date");
				Timestamp orig_enter_date = res.getTimestamp("orig_enter_date");
				// Timestamp orig_cycle_date =
				// res.getTimestamp("orig_cycle_date");
				Number orig_cycle_id = (Number) res.getObject("orig_cycle_id");
				Number orig_reading = (Number) res.getObject("orig_reading");
				Number orig_kwh = (Number) res.getObject("orig_kwh");
				Number orig_gel = (Number) res.getObject("orig_gel");
				Number orig_balance = (Number) res.getObject("orig_balance");
				Number balance_gap = (Number) res.getObject("balance_gap");
				Number orig_balance_gap = (Number) res.getObject("orig_balance_gap");
				Number leave_kwh_unchanged = (Number) res.getObject("leave_kwh_unchanged");
				Number sub_acc_id = (Number) res.getObject("sub_account_id");
				double orig_meter_coeff = res.getDouble("orig_meter_coeff");
				String sub_acc_number = res.getString("sub_account_number");
				// int customer_id = res.getInt("customer_id");
				int account_id = res.getInt("account_id");
				if (prevInterval == null || prevInterval.getId() != interval_id) {
					if (prevInterval != null) {
						// add previous interval
						prevInterval.validateDates();
						intervals.add(prevInterval);
					}
					// create new interval
					prevInterval = new RecalcInterval();
					prevInterval.setItems(new ArrayList());
					prevInterval.setDetails(recalc.getDetails());
					prevInterval.setName(interval_name);
					prevInterval.setId(interval_id);
					prevInterval.setStartBalance(start_balance);
					prevInterval.setEditable(interval_editable == 1);
				}
				RecalcItem item = new RecalcItem();

				item.setId(vouhcer_item_id);
				item.setItemId(item_id);
				item.setInterval(prevInterval);

				// operation
				Operation oper = new Operation();
				oper.setId(operation_id);
				item.setOperation(oper);

				item.setStatus(item_status);
				item.setReading(reading);
				item.setKwh(kwh);
				item.setGel(gel);
				item.setBalance(balance);

				// is cycle?
				boolean isCycle = cycle_id != null && cycle_id.intValue() > 0;
				item.setCycle(isCycle);

				item.setItemDate(item_date);
				item.setEnterDate(enter_date);
				item.setCurrentOperationDate(curr_date);
				item.setPreviousOperationDate(prev_date);
				item.setMeterCoeff(meter_coeff);
				item.setMeterStatus(meter_status == 0);
				item.setMeterAcceleration(meter_accelerate);

				// meter
				Meter meter = new Meter();
				meter.setId(meter_type_id);
				item.setMeter(meter);

				item.setItemNumber(item_number);

				// original operation
				Operation original_oper = null;
				if (orig_operation_id != null) {
					original_oper = new Operation();
					original_oper.setId(orig_operation_id.intValue());
				}
				item.setOriginalOperation(original_oper);

				item.setOriginalItemDate(Date.create(orig_item_date));
				item.setOriginalEnterDate(Date.create(orig_enter_date));

				// is original cycle?
				boolean isOriginalCycle = orig_cycle_id != null && orig_cycle_id.intValue() > 0;
				item.setOriginalCycle(isOriginalCycle);

				item.setOriginalReading(orig_reading == null ? 0.f : orig_reading.doubleValue());
				item.setOriginalKwh(orig_kwh == null ? 0.f : orig_kwh.doubleValue());
				item.setOriginalGel(orig_gel == null ? 0.f : orig_gel.doubleValue());
				item.setOriginalBalance(orig_balance == null ? 0.f : orig_balance.doubleValue());
				item.setBalanceGap(balance_gap == null ? 0.f : balance_gap.doubleValue());
				item.setOriginalBalanceGap(orig_balance_gap == null ? 0.f : orig_balance_gap.doubleValue());
				item.setOriginalMeterCoeff(orig_meter_coeff);
				// item.setKwhLeavedUnchanged(leave_kwh_unchanged == null ?
				// false : leave_kwh_unchanged.intValue() == 1);
				item.setCalculationHint(leave_kwh_unchanged.intValue());
				item.setCustomer(recalc.getCustomer());
				for (int i = 0; i < item.getCustomer().getAccounts().size(); i++) {
					Account account = (Account) item.getCustomer().getAccounts().get(i);
					if (account.getId() == account_id) {
						item.setAccount(account);
						break;
					}
				}

				if (item.getAccount() == null) {
					throw new SQLException("Can not find account.");
				}

				if (att_unit != null && att_amount != null && att_count != null) {
					RecalcSubsidyAttachment att = new RecalcSubsidyAttachment();
					att.setAmount(att_amount.doubleValue());
					att.setCount(att_count.intValue());
					att.setUnit(att_unit.intValue());
					item.setSubsidyAttachment(att);
				}

				if (orig_att_unit != null && orig_att_amount != null && orig_att_count != null) {
					RecalcSubsidyAttachment att2 = new RecalcSubsidyAttachment();
					att2.setAmount(orig_att_amount.doubleValue());
					att2.setCount(orig_att_count.intValue());
					att2.setUnit(orig_att_unit.intValue());
					item.setOriginalSubsidyAttachment(att2);
				}

				// sub account
				Account subacc = null;
				if (sub_acc_id != null) {
					subacc = new Account();
					subacc.setId(sub_acc_id.intValue());
					subacc.setNumber(sub_acc_number);
				}
				item.setSubAccount(subacc);

				prevInterval.getItems().add(item);

			}
			if (prevInterval != null) {
				// add last interval
				prevInterval.validateDates();
				intervals.add(prevInterval);
			}

			return intervals;

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

	// recalculation interval UPDATE
	public static void updateRecalcInterval(RecalcInterval interval, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_interval_update.sql");
		PreparedStatement st = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, interval.getId());
			st.setString(2, interval.getName());
			st.setDouble(3, interval.getStartBalance());
			st.execute();

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	// recalculation interval DELETE
	public static void deleteRecalcInterval(RecalcInterval interval, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_interval_delete.sql");
		PreparedStatement st = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, interval.getId());
			st.execute();

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	// recalculation item insert
	public static long insertRecalcItem(RecalcItem item, int sequence, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_item_insert.sql");
		CallableStatement st = null;

		try {

			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getInterval().getId());
			st.setInt(2, item.getCustomer().getId());
			st.setInt(3, item.getAccount().getId());
			st.setInt(4, item.getOperation().getId());

			st.setDouble(5, item.getReading());
			st.setDouble(6, item.getKwh());
			st.setDouble(7, item.getGel());
			st.setInt(8, item.getCycle() ? +1 : -1);
			st.setTimestamp(9, new Timestamp(item.getItemDate().toDate().getTime()));
			st.setTimestamp(10, new Timestamp(item.getEnterDate().toDate().getTime()));
			if (item.getCurrentOperationDate() == null) {
				st.setNull(11, Types.TIMESTAMP);
			} else {
				st.setTimestamp(11, new Timestamp(item.getCurrentOperationDate().toDate().getTime()));
			}
			if (item.getPreviousOperationDate() == null) {
				st.setNull(12, Types.TIMESTAMP);
			} else {
				st.setTimestamp(12, new Timestamp(item.getPreviousOperationDate().toDate().getTime()));
			}
			st.setDouble(13, item.getMeterCoeff());
			st.setInt(14, item.getMeterStatus() ? 0 : 1);
			st.setInt(15, item.getMeter().getId());
			if (item.getItemNumber() == null) {
				st.setNull(16, Types.VARCHAR);
			} else {
				st.setString(16, item.getItemNumber());
			}
			if (item.getSubsidyAttachment() == null) {
				st.setNull(17, Types.NUMERIC);
				st.setNull(18, Types.NUMERIC);
				st.setNull(19, Types.NUMERIC);
			} else {
				st.setInt(17, item.getSubsidyAttachment().getUnit());
				st.setInt(18, item.getSubsidyAttachment().getCount());
				st.setDouble(19, item.getSubsidyAttachment().getAmount());
			}
			st.setInt(20, sequence);
			st.setDouble(21, item.getBalanceGap());
			// st.setInt(22, item.isKwhLeavedUnchanged() ? 1 : 0);
			st.setInt(22, item.getCalculationHint());
			st.setLong(23, -1L);

			st.registerOutParameter(23, Types.NUMERIC);
			st.execute();
			return st.getLong(23);

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	// recalculation item update
	public static void updateRecalcItem(RecalcItem item, Session session) throws SQLException, IOException {

		String sql = SQLReader.readSQL("recalc/recalc_item_update.sql");
		PreparedStatement st = null;

		try {

			st = session.getConnection().prepareStatement(sql);

			st.setLong(1, item.getId());
			st.setInt(2, item.getOperation().getId());
			st.setDouble(3, item.getReading());
			st.setDouble(4, item.getKwh());
			st.setDouble(5, item.getGel());

			st.setInt(6, item.getCycle() ? +1 : -1);
			st.setTimestamp(7, new Timestamp(item.getItemDate().toDate().getTime()));
			st.setTimestamp(8, new Timestamp(item.getEnterDate().toDate().getTime()));
			if (item.getCurrentOperationDate() == null) {
				st.setNull(9, Types.TIMESTAMP);
			} else {
				st.setTimestamp(9, new Timestamp(item.getCurrentOperationDate().toDate().getTime()));
			}
			if (item.getPreviousOperationDate() == null) {
				st.setNull(10, Types.TIMESTAMP);
			} else {
				st.setTimestamp(10, new Timestamp(item.getPreviousOperationDate().toDate().getTime()));
			}

			st.setDouble(11, item.getMeterCoeff());
			st.setInt(12, item.getMeterStatus() ? 0 : 1);
			st.setInt(13, item.getMeter().getId());
			if (item.getSubsidyAttachment() == null) {
				st.setNull(14, Types.NUMERIC);
				st.setNull(15, Types.NUMERIC);
				st.setNull(16, Types.NUMERIC);
			} else {
				st.setInt(14, item.getSubsidyAttachment().getUnit());
				st.setInt(15, item.getSubsidyAttachment().getCount());
				st.setDouble(16, item.getSubsidyAttachment().getAmount());
			}

			st.setDouble(17, item.getBalanceGap());
			// st.setInt(18, item.isKwhLeavedUnchanged() ? 1 : 0);
			st.setInt(18, item.getCalculationHint());

			st.execute();

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}

	}

	// delete recalculation item
	public static void deleteRecalcItem(RecalcItem item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_item_delete.sql");
		PreparedStatement st = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());

			st.execute();

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	// enable recalculation item
	public static void enableRecalcItem(RecalcItem item, boolean enable, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_item_enable.sql");
		PreparedStatement st = null;

		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.setInt(2, enable ? 0 : 1);

			st.execute();

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}

	}

	// move recalculation item
	public static void moveRecalcItem(RecalcItem item, boolean up, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_item_move.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.setInt(2, up ? 0 : 1);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void setHintForRecalcItem(RecalcItem item, int hint, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_item_kwh_lock.sql");
		PreparedStatement st = null;
		try {
			System.out.println("change hint to " + hint);
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.setInt(2, hint);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	// update recalculation item subsidy attachment
	public static void recalcItemSubsAttUpdate(RecalcItem item, RecalcSubsidyAttachment att, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_item_subsidy_att_update.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			if (att == null) {
				st.setNull(2, Types.NUMERIC);
				st.setNull(3, Types.NUMERIC);
				st.setNull(4, Types.NUMERIC);
			} else {
				st.setInt(2, att.getUnit());
				st.setDouble(3, att.getAmount());
				st.setInt(4, att.getCount());
			}
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	// update recalculation item meter
	public static void recalcItemMeterUpdate(long itemId, int meterid, double coeff, boolean meterStatus, double acceleration, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_item_meter_update.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, itemId);
			st.setInt(2, meterid);
			st.setDouble(3, coeff);
			st.setInt(4, meterStatus ? 0 : 1);
			st.setDouble(5, acceleration);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void itemRestoreOriginal(long itemId, int option, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_item_restore_originals.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, itemId);
			st.setInt(2, option);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static List selectRegular(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_regular_item_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			res = st.executeQuery();

			List items = new ArrayList();

			while (res.next()) {
				long id = res.getLong("ID");
				Date d1 = Date.create(res.getTimestamp("START_DATE"));
				Date d2 = Date.create(res.getTimestamp("END_DATE"));
				Operation oper = new Operation();
				oper.setId(res.getInt("OPERATION_ID"));
				RecalcSubsidyAttachment att = new RecalcSubsidyAttachment();
				att.setUnit(res.getInt("ATT_UNIT"));
				att.setAmount(res.getDouble("ATT_AMOUNT"));
				att.setCount(res.getInt("ATT_COUNT"));
				Number a2 = (Number) res.getObject("ATT_AMOUNT2");
				if (a2 == null) {
					att.setHasAmount2(false);
				} else {
					att.setHasAmount2(true);
					att.setAmount2(a2.doubleValue());
				}

				RecalcRegularItem item = new RecalcRegularItem();

				item.setId(id);
				item.setStartDate(d1);
				item.setEndDate(d2);
				item.setOperation(oper);
				item.setAttachment(att);

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

	public static long insertRegular(RecalcRegularItem item, int seq, Session session) throws SQLException, IOException {

		String sql = SQLReader.readSQL("recalc/recalc_regular_item_insert.sql");
		CallableStatement st = null;
		try {

			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getDetails().getRecalc().getId());
			if (item.getStartDate() == null) {
				st.setNull(2, Types.TIMESTAMP);
			} else {
				st.setTimestamp(2, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.setInt(4, item.getOperation().getId());
			st.setInt(5, item.getAttachment().getUnit());
			st.setDouble(6, item.getAttachment().getAmount());
			if (item.getAttachment().hasAmount2()) {
				st.setDouble(7, item.getAttachment().getAmount2());
			} else {
				st.setNull(7, Types.NUMERIC);
			}
			st.setInt(8, item.getAttachment().getCount());
			st.setInt(9, seq);
			st.setLong(10, -1L);

			st.registerOutParameter(10, Types.NUMERIC);
			st.execute();

			return st.getLong(10);

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void deleteRegular(RecalcRegularItem item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_regular_item_delete.sql");
		PreparedStatement st = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.execute();

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void updateRegular(RecalcRegularItem item, Session session) throws SQLException, IOException {

		String sql = SQLReader.readSQL("recalc/recalc_regular_item_update.sql");
		PreparedStatement st = null;
		try {

			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getId());
			if (item.getStartDate() == null) {
				st.setNull(2, Types.TIMESTAMP);
			} else {
				st.setTimestamp(2, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.setInt(4, item.getOperation().getId());
			st.setInt(5, item.getAttachment().getUnit());
			st.setDouble(6, item.getAttachment().getAmount());
			if (item.getAttachment().hasAmount2()) {
				st.setDouble(7, item.getAttachment().getAmount2());
			} else {
				st.setNull(7, Types.NUMERIC);
			}
			st.setInt(8, item.getAttachment().getCount());

			st.execute();

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void moveRegular(RecalcRegularItem item, boolean up, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_regular_item_move.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.setInt(2, up ? 0 : 1);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static List selectInspCp(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_instcp_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			res = st.executeQuery();

			List instcps = new ArrayList();

			while (res.next()) {
				RecalcInstCp instcp = new RecalcInstCp();
				instcp.setId(res.getLong("ID"));
				instcp.setStartDate(Date.create(res.getTimestamp("START_DATE")));
				instcp.setEndDate(Date.create(res.getTimestamp("END_DATE")));
				instcp.setAmount(res.getDouble("AMOUNT"));
				instcp.setOption(res.getInt("RECALC_OPTION"));
				instcps.add(instcp);
			}

			return instcps;

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

	public static void generateDefaultInstCp(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_instcp_default.sql");
		PreparedStatement st = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			st.execute();

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static long insertInstcp(RecalcInstCp item, int seq, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_instcp_insert.sql");
		CallableStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getDetails().getRecalc().getId());
			if (item.getStartDate() == null) {
				st.setNull(2, Types.TIMESTAMP);
			} else {
				st.setTimestamp(2, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.setDouble(4, item.getAmount());
			st.setInt(5, item.getOption());
			st.setInt(6, seq);
			st.setLong(7, -1L);

			st.registerOutParameter(7, Types.NUMERIC);
			st.execute();
			return st.getLong(7);

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void updateInstcp(RecalcInstCp item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_instcp_update.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getId());
			if (item.getStartDate() == null) {
				st.setNull(2, Types.TIMESTAMP);
			} else {
				st.setTimestamp(2, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.setDouble(4, item.getAmount());
			st.setInt(5, item.getOption());
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void deleteInstcp(RecalcInstCp item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_instcp_delete.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void moveInstcp(RecalcInstCp item, boolean up, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_instcp_move.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.setInt(2, up ? 0 : 1);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void tariffDefault(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_tariff_default.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static List tariffSelect(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_tariff_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			res = st.executeQuery();

			List items = new ArrayList();

			while (res.next()) {
				RecalcTariffItem item = new RecalcTariffItem();
				item.setId(res.getLong("id"));
				item.setStartDate(Date.create(res.getTimestamp("start_date")));
				item.setEndDate(Date.create(res.getTimestamp("end_date")));
				int tariffId = res.getInt("tariff_id");
				DummyTariff tariff = new DummyTariff();
				tariff.setId(tariffId);
				item.setTariff(tariff);
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

	public static long tariffInsert(RecalcTariffItem item, int seq, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_tariff_insert.sql");
		CallableStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getDetails().getRecalc().getId());
			st.setInt(2, item.getTariff().getId());
			if (item.getStartDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(4, Types.TIMESTAMP);
			} else {
				st.setTimestamp(4, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.setInt(5, seq);

			st.registerOutParameter(6, Types.NUMERIC);
			st.execute();
			return st.getLong(6);
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void tariffUpdate(RecalcTariffItem item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_tariff_update.sql");
		PreparedStatement st = null;
		try {

			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getId());
			st.setInt(2, item.getTariff().getId());
			if (item.getStartDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(4, Types.TIMESTAMP);
			} else {
				st.setTimestamp(4, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void tariffDelete(RecalcTariffItem item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_tariff_delete.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getId());
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void tariffMove(RecalcTariffItem item, boolean up, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_tariff_move.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getId());
			st.setInt(2, up ? 0 : 1);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static List selectCut(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_cut_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			res = st.executeQuery();

			List cuts = new ArrayList();

			while (res.next()) {
				RecalcCutItem cut = new RecalcCutItem();
				cut.setId(res.getLong("ID"));
				cut.setStartDate(Date.create(res.getTimestamp("START_DATE")));
				cut.setEndDate(Date.create(res.getTimestamp("END_DATE")));
				cuts.add(cut);
			}

			return cuts;

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

	public static long insertCut(RecalcCutItem item, int seq, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_cut_insert.sql");
		CallableStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getDetails().getRecalc().getId());
			if (item.getStartDate() == null) {
				st.setNull(2, Types.TIMESTAMP);
			} else {
				st.setTimestamp(2, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.setInt(4, seq);
			st.setLong(5, -1L);

			st.registerOutParameter(5, Types.NUMERIC);
			st.execute();
			return st.getLong(5);

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void updateCut(RecalcCutItem item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_cut_update.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getId());
			if (item.getStartDate() == null) {
				st.setNull(2, Types.TIMESTAMP);
			} else {
				st.setTimestamp(2, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void deleteCut(RecalcCutItem item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_cut_delete.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void moveCut(RecalcCutItem item, boolean up, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_cut_move.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.setInt(2, up ? 0 : 1);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static List selectRoom(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_room_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {

			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			res = st.executeQuery();

			List rooms = new ArrayList();

			while (res.next()) {
				RecalcRoomItem room = new RecalcRoomItem();
				room.setId(res.getLong("ID"));
				room.setStartDate(Date.create(res.getTimestamp("START_DATE")));
				room.setEndDate(Date.create(res.getTimestamp("END_DATE")));
				room.setRoomCount(res.getInt("ROOM_COUNT"));
				room.setRecalcId(res.getLong("RECALC_ID"));
				rooms.add(room);
			}

			return rooms;

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

	public static long insertRoom(long recalcId, RecalcRoomItem item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_room_insert.sql");
		CallableStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, recalcId);
			if (item.getStartDate() == null) {
				st.setNull(2, Types.TIMESTAMP);
			} else {
				st.setTimestamp(2, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.setInt(4, item.getRoomCount());
			st.setLong(5, -1L);

			st.registerOutParameter(5, Types.NUMERIC);
			st.execute();
			return st.getLong(5);

		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void updateRoom(RecalcRoomItem item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_room_update.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareCall(sql);
			st.setLong(1, item.getId());
			if (item.getStartDate() == null) {
				st.setNull(2, Types.TIMESTAMP);
			} else {
				st.setTimestamp(2, new Timestamp(item.getStartDate().toDate().getTime()));
			}
			if (item.getEndDate() == null) {
				st.setNull(3, Types.TIMESTAMP);
			} else {
				st.setTimestamp(3, new Timestamp(item.getEndDate().toDate().getTime()));
			}
			st.setInt(4, item.getRoomCount());
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void deleteRoom(RecalcRoomItem item, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_room_delete.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, item.getId());
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void saveRecalc(Recalc recalc, List summary, List facturaExpansion, List trashList, Session session) throws SQLException, IOException {
		String sql1 = SQLReader.readSQL("recalc/recalc_save_head.sql");
		String sql3 = SQLReader.readSQL("recalc/recalc_save_finalize.sql");
		PreparedStatement st1 = null;
		PreparedStatement st3 = null;
		try {

			// head
			st1 = session.getConnection().prepareStatement(sql1);
			st1.setLong(1, recalc.getId());
			if (recalc.getDescription() == null) {
				st1.setNull(2, Types.VARCHAR);
			} else {
				st1.setString(2, recalc.getDescription().trim());
			}
			if (recalc.getSaveUser() == null) {
				st1.setNull(3, Types.NUMERIC);
			} else {
				st1.setLong(3, recalc.getSaveUser().getId());
			}
			if (recalc.getAdvisor() == null) {
				st1.setNull(4, Types.NUMERIC);
			} else {
				st1.setLong(4, recalc.getAdvisor().getId());
			}
			st1.execute();

			// details
			saveVoucherDetails(summary, recalc, session);

			// factura saving
			saveFactura(facturaExpansion, recalc, session);

			// save trash voucher
			if (trashList != null) {
				saveTrash(trashList, recalc, session);
			}

			// finalize save
			st3 = session.getConnection().prepareStatement(sql3);
			st3.setLong(1, recalc.getId());
			st3.execute();

		} finally {
			if (st1 != null) {
				try {
					st1.close();
				} catch (Exception ex) {
				}
				st1 = null;
			}
			if (st3 != null) {
				try {
					st3.close();
				} catch (Exception ex) {
				}
				st3 = null;
			}
		}
	}

	private static void saveTrash(List changes, Recalc recalc, Session session) throws IOException, SQLException {
		String sql = "INSERT INTO RECALC_TRASH(RECALC_ID,TRASHOPERATION_ID,KWH,GEL) VALUES (?,?,?,?)";
		PreparedStatement st4 = null;
		try {
			st4 = session.getConnection().prepareStatement(sql);
			for (int i = 0; i < changes.size(); i++) {
				TrashVoucher voucher = (TrashVoucher) changes.get(i);
				st4.setLong(1, recalc.getId());
				st4.setInt(2, voucher.getTrashOperation());
				st4.setDouble(3, voucher.getKwh());
				st4.setDouble(4, voucher.getGel());
				st4.addBatch();
			}
			st4.executeBatch();
		} finally {
			try {
				st4.close();
			} catch (Exception ex) {
			}
		}
	}

	private static void saveFactura(List facturaExpansion, Recalc recalc, Session session) throws IOException, SQLException {
		PreparedStatement st4 = null;
		String sql4 = SQLReader.readSQL("recalc/recalc_save_factura.sql");
		try {
			if (facturaExpansion != null && !facturaExpansion.isEmpty()) {
				st4 = session.getConnection().prepareStatement(sql4);
				for (int i = 0; i < facturaExpansion.size(); i++) {
					FacturaDetail fd = (FacturaDetail) facturaExpansion.get(i);
					st4.setLong(1, recalc.getId());
					st4.setInt(2, fd.isOriginal() ? 0 : 1);
					st4.setInt(3, fd.getOperation().getId());
					st4.setLong(4, fd.getOriginalItemId());
					st4.setTimestamp(5, new Timestamp(fd.getItemDate().toDate().getTime()));
					st4.setDouble(6, fd.getKwh());
					st4.setDouble(7, fd.getGel());
					st4.addBatch();
				}
				st4.executeBatch();
			}
		} finally {
			try {
				st4.close();
			} catch (Exception ex) {
			}
			st4 = null;
		}
	}

	private static void saveTrashVouchers(List trashVouchers, Recalc recalc, Session session) throws SQLException {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {
			// remove old recalcs
			st2 = session.getConnection().prepareStatement("DELETE FROM RECALC_TRASH WHERE recalc_id=?");
			st2.setLong(1, recalc.getId());
			st2.execute();

			st = session.getConnection().prepareStatement("INSERT INTO RECALC_TRASH (RECALC_ID, TRASHOPERATION_ID, KWH, GEL) VALUES (?,?,?,?)");
			for (int i = 0; i < trashVouchers.size(); i++) {
				TrashVoucher voucher = (TrashVoucher) trashVouchers.get(i);
				st.setLong(1, recalc.getId());
				st.setInt(2, voucher.getTrashOperation());
				st.setDouble(3, voucher.getKwh());
				st.setDouble(4, voucher.getGel());
				st.addBatch();
			}
			st.executeBatch();
		} finally {
			try {
				st2.close();
				st.close();
			} catch (Exception ex) {
			}
		}
	}

	private static void saveVoucherDetails(List summary, Recalc recalc, Session session) throws IOException, SQLException {
		PreparedStatement st2 = null;
		String sql2 = SQLReader.readSQL("recalc/recalc_save_detail.sql");
		try {
			if (summary == null || summary.isEmpty())
				throw new IllegalArgumentException("Can not save empty vouchers list.");
			st2 = session.getConnection().prepareStatement(sql2);
			for (int i = 0; i < summary.size(); i++) {
				DiffDetail detail = (DiffDetail) summary.get(i);
				st2.setLong(1, recalc.getId());
				st2.setInt(2, detail.getOperation().getId());
				st2.setDouble(3, detail.getOriginalKwh());
				st2.setDouble(4, detail.getOriginalGel());
				st2.addBatch();
			}
			st2.executeBatch();
		} finally {
			try {
				st2.close();
			} catch (Exception ex) {
			}
			st2 = null;
		}
	}

	public static RecalcVoucher selectVoucher(Recalc recalc, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_voucher_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalc.getId());
			res = st.executeQuery();

			RecalcVoucher voucher = new RecalcVoucher();

			// details
			List details = new ArrayList();
			while (res.next()) {
				DiffDetail detail = new DiffDetail();
				Operation oper = new Operation();
				oper.setId(res.getInt("OPERATION_ID"));
				// detail.setOperation(oper);
				// detail.setOriginalKwh(res.getdouble("KWH"));
				// detail.setOriginalGel(res.getdouble("GEL"));
				CalculationItem item = new CalculationItem();
				ChargeElement el = new ChargeElement();
				el.setTariffId(-1);
				el.setKwh(res.getDouble("KWH"));
				el.setGel(res.getDouble("GEL"));
				item.getCharge().addElement(el);
				item.setOperation(oper);
				detail.setOriginalItem(item);
				details.add(detail);
			}
			voucher.setDetails(details);

			// properties
			NumberFormat nf = new DecimalFormat("#,###.00");
			String number = recalc.getNumber() == null ? "" : recalc.getNumber();
			String businessCenter = (recalc.getCustomer() == null || recalc.getCustomer().getAddress() == null) ? "" : recalc.getCustomer().getAddress().getRegionName();
			String customer = (recalc.getCustomer() == null) ? "" : recalc.getCustomer().getName();
			String customerNumber = (recalc.getCustomer() == null) ? "" : recalc.getCustomer().getNumber();
			String address = (recalc.getCustomer() == null || recalc.getCustomer().getAddress() == null) ? "" : recalc.getCustomer().getAddress().toString();
			String account = (recalc.getAccount() == null) ? "" : recalc.getAccount().getNumber();
			String saveDate = (recalc.getSaveDate() == null) ? "" : Date.format(recalc.getSaveDate());
			String operator = (recalc.getSaveUser() == null) ? "" : recalc.getSaveUser().getFullName();
			String advisor = (recalc.getAdvisor() == null) ? "" : recalc.getAdvisor().getFullName();
			String description = (recalc.getDescription() == null) ? "" : recalc.getDescription();
			String initBalance = nf.format(recalc.getInitialBalance());
			String finalBalance = recalc.getFinalBalance() == null ? "-" : nf.format(recalc.getFinalBalance().doubleValue());
			Customer2 cust2 = null;
			if (recalc.getCustomer() instanceof Customer2) {
				cust2 = (Customer2) recalc.getCustomer();
			} else {
				cust2 = (Customer2) E1CustomerManagment.findCustomerByAccNumber(recalc.getCustomer().getNumber(), Request.FINAL, session);
			}
			String leftAmount = nf.format(cust2.getLeftAmount());

			voucher.getProperties().add(new String[] { "application.voucher.number", number });
			voucher.getProperties().add(new String[] { "application.voucher.businessCenter", businessCenter });
			voucher.getProperties().add(new String[] { "application.voucher.customer", customer });
			voucher.getProperties().add(new String[] { "application.voucher.customer_number", customerNumber });
			voucher.getProperties().add(new String[] { "application.voucher.address", address });
			voucher.getProperties().add(new String[] { "application.voucher.account", account });
			voucher.getProperties().add(new String[] { "application.voucher.saveDate", saveDate });
			voucher.getProperties().add(new String[] { "application.voucher.operator", operator });
			voucher.getProperties().add(new String[] { "application.voucher.advisor", advisor });
			voucher.getProperties().add(new String[] { "application.voucher.description", description });
			voucher.getProperties().add(new String[] { "application.voucher.initBalance", initBalance });
			voucher.getProperties().add(new String[] { "application.voucher.finalBalance", finalBalance });
			voucher.getProperties().add(new String[] { "application.voucher.leftAmount", leftAmount });

			// intervals
			List intervals = recalc.getDetails().getIntervals();
			if (intervals.isEmpty()) {
				intervals = getRecalcIntervals(recalc, session);
			}
			for (int i = 0; i < intervals.size(); i++) {
				RecalcInterval interval = (RecalcInterval) intervals.get(i);
				if (interval.isEditable()) {
					StringBuffer sb = new StringBuffer();
					sb.append("[");
					sb.append(interval.getStartDate());
					sb.append("] - [");
					sb.append(interval.getEndDate());
					sb.append("]");
					voucher.getProperties().add(new String[] { "application.voucher.interval", sb.toString() });
				}
			}

			// return results
			return voucher;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RecalcException(ex.toString());
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

	public static void finalizeRecalc(long recalcId, boolean finalize, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_finalize.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalcId);
			st.setInt(2, finalize ? Recalc2.STATUS_FINALIZED : Recalc2.STATUS_CANCELED);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	public static void unblockRecalc(long recalcId, Session session) throws SQLException, IOException {
		String sql = SQLReader.readSQL("recalc/recalc_unblock.sql");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalcId);
			st.execute();
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (Exception ex) {
				}
				st = null;
			}
		}
	}

	/**
	 * Search recalculation.
	 */
	public static List searchRecalcs(Date startDate, Date endDate, String recalcNumber, boolean bySaveDate, boolean lowerThan5, boolean simpleSearch, /*
																																					 * simple
																																					 * search
																																					 * was
																																					 * used
																																					 * in
																																					 * older
																																					 * versions
																																					 */
	Session session) throws SQLException, IOException {
		StringBuilder s = new StringBuilder(SQLReader.readSQL("recalc/recalc_search.sql"));
		List params = new ArrayList();
		if (simpleSearch) {
			s.append("\nAND trunc(");
			s.append(bySaveDate ? "SAVE_DATE" : "CREATE_DATE");
			s.append(") = ?");
			params.add(new java.sql.Date(startDate.toDate().getTime()));
		} else {
			if (recalcNumber != null && recalcNumber.trim().length() > 0) {
				s.append("\nAND RECALC_NUMBER LIKE ?");
				params.add(recalcNumber.trim());
			}
			if (startDate != null) {
				s.append("\nAND trunc(");
				s.append(bySaveDate ? "SAVE_DATE" : "CREATE_DATE");
				s.append(") >= ?");
				params.add(new java.sql.Date(startDate.toDate().getTime()));
			}
			if (endDate != null) {
				s.append("\nAND trunc(");
				s.append(bySaveDate ? "SAVE_DATE" : "CREATE_DATE");
				s.append(") <= ?");
				params.add(new java.sql.Date(endDate.toDate().getTime()));
			}
		}
		s.append("\nORDER BY ID");
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(s.toString());
			// st.setTimestamp(1, new Timestamp(d.toDate().getTime()));
			// st.setDate(1, new java.sql.Date(startDate.toDate().getTime()));
			for (int i = 0; i < params.size(); i++) {
				st.setObject(i + 1, params.get(i));
			}
			res = st.executeQuery();
			List recalcs = new ArrayList();
			while (res.next()) {
				// getting parameters
				long id = res.getLong("ID");
				String number = res.getString("RECALC_NUMBER");
				int customerId = res.getInt("CUSTOMER");
				String accNumb = res.getString("ACCNUMB");
				String custName = res.getString("CUSTNAME");
				int accountId = res.getInt("ACCOUNT");
				String accId = res.getString("ACCID");
				Date createDate = Date.create(res.getTimestamp("CREATE_DATE"));
				Date saveDate = Date.create(res.getTimestamp("SAVE_DATE"));
				String description = res.getString("DESCRIPTION");
				int status = res.getInt("IS_CHANGED");
				Number savePersonId = (Number) res.getObject("SAVE_PERSON_ID");
				Number advisorId = (Number) res.getObject("ADVISOR_ID");
				Number initBalance = (Number) res.getObject("INIT_BALANCE");
				Number finalBalance = (Number) res.getObject("FINAL_BALANCE");
				String address = res.getString("ADDRESS");
				// create recalculation
				Customer cust = new Customer();
				cust.setId(customerId);
				cust.setNumber(accNumb);
				cust.setName(custName);
				Address adrs = new Address();
				adrs.setStreetName(address);
				cust.setAddress(adrs);
				Account acc = new Account();
				acc.setId(accountId);
				acc.setNumber(accId);
				cust.addAccount(acc);
				Recalc recalc = lowerThan5 ? new Recalc() : new Recalc2();
				recalc.setId(id);
				recalc.setCustomer(cust);
				recalc.setAccount(acc);
				recalc.setNumber(number);
				recalc.setCreateDate(createDate);
				recalc.setSaveDate(saveDate);
				if (lowerThan5) {
					recalc.setChanged(status != 0);
				} else {
					((Recalc2) recalc).setStatus(status);
				}
				recalc.setDescription(description);
				recalc.setInitialBalance(initBalance == null ? 0 : initBalance.doubleValue());
				recalc.setFinalBalance(finalBalance == null ? null : new Double(finalBalance.doubleValue()));
				User savePerson = savePersonId == null ? null : new User(savePersonId.longValue());
				User advisor = advisorId == null ? null : new User(advisorId.longValue());
				if (savePerson != null) {
					savePerson = E1UserManagment.getUser(savePerson.getId(), session);
				}
				if (advisor != null) {
					advisor = E1UserManagment.getUser(advisor.getId(), session);
				}
				recalc.setSaveUser(savePerson);
				recalc.setAdvisor(advisor);
				// add to the list
				recalcs.add(recalc);
			}
			return recalcs;
		} finally {
			try {
				res.close();
			} catch (Exception ex) {
			}
			try {
				st.close();
			} catch (Exception ex) {
			}
			res = null;
			st = null;
		}
	}

	public static List selectTrashVouchers(long recalcId, Session session) throws SQLException {
		String sql = "SELECT trashoperation_id, kwh, gel FROM RECALC_TRASH where RECALC_ID=?";
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalcId);
			res = st.executeQuery();
			List vouchers = new ArrayList();
			while (res.next()) {
				TrashVoucher voucher = new TrashVoucher();
				voucher.setGel(res.getDouble("gel"));
				voucher.setKwh(res.getDouble("kwh"));
				voucher.setTrashOperation(res.getInt("trashoperation_id"));
				vouchers.add(voucher);
			}
			return vouchers;
		} finally {
			try {
				st.close();
				res.close();
			} catch (Exception ex) {
			}
		}
	}

	public static List selectFacturaExpansion(long recalcId, Session session) throws IOException, SQLException {
		String sql = SQLReader.readSQL("recalc/recalc_factura_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setLong(1, recalcId);
			List exp = new ArrayList();
			res = st.executeQuery();
			while (res.next()) {
				FacturaDetail detail = new FacturaDetail();
				// long id = res.getLong("ID");
				boolean isOriginal = res.getInt("IS_ORIGINAL") == 0;
				int operId = res.getInt("OPERATION_ID");
				long itemId = res.getLong("ITEM_ID");
				Date itemDate = Date.create(res.getTimestamp("ITEM_DATE"));
				double kwh = res.getDouble("KWH");
				double gel = res.getDouble("GEL");
				Operation oper = new Operation();
				oper.setId(operId);
				detail.setGel(gel);
				detail.setKwh(kwh);
				detail.setItemDate(itemDate);
				detail.setOperation(oper);
				detail.setOriginal(isOriginal);
				detail.setOriginalItemId(itemId);
				exp.add(detail);
			}
			return exp;
		} finally {
			try {
				res.close();
			} catch (Exception ex) {
			}
			try {
				st.close();
			} catch (Exception ex) {
			}
			res = null;
			st = null;
		}
	}

	public static long checkRecalculationExistence(String recalcNumber, int customerId, Session session) throws IOException, SQLException {
		String sql = SQLReader.readSQL("recalc/check_recalc_existence.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, customerId);
			st.setString(2, recalcNumber.trim());
			res = st.executeQuery();
			if (res.next()) {
				return res.getLong("ID");
			} else {
				return -1;
			}
		} finally {
			try {
				res.close();
			} catch (Exception ex) {
			}
			try {
				st.close();
			} catch (Exception ex) {
			}
			res = null;
			st = null;
		}
	}

	public static void deleteRecalcDetails(long recalcId, Session session) throws SQLException {
		String sql1 = "DELETE FROM RECUT.RECALC_CUT WHERE RECALC_ID=?";
		String sql2 = "DELETE FROM RECUT.RECALC_INSTCP WHERE RECALC_ID=?";
		String sql3 = "DELETE FROM RECUT.RECALC_INTERVAL WHERE RECALC_ID=?";
		String sql4 = "DELETE FROM RECUT.RECALC_REGULAR WHERE RECALC_ID=?";
		String sql5 = "DELETE FROM RECUT.RECALC_ROOMS WHERE RECALC_ID=?";
		String sql6 = "DELETE FROM RECUT.RECALC_SAVE WHERE RECALC_ID=?";
		String sql7 = "DELETE FROM RECUT.FACTURA WHERE RECALC_ID=?";
		String sql8 = "DELETE FROM RECUT.RECALC_TARIFFS WHERE RECALC_ID=?";
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		PreparedStatement st4 = null;
		PreparedStatement st5 = null;
		PreparedStatement st6 = null;
		PreparedStatement st7 = null;
		PreparedStatement st8 = null;
		try {
			st1 = session.getConnection().prepareStatement(sql1);
			st2 = session.getConnection().prepareStatement(sql2);
			st3 = session.getConnection().prepareStatement(sql3);
			st4 = session.getConnection().prepareStatement(sql4);
			st5 = session.getConnection().prepareStatement(sql5);
			st6 = session.getConnection().prepareStatement(sql6);
			st7 = session.getConnection().prepareStatement(sql7);
			st8 = session.getConnection().prepareStatement(sql8);
			st1.setLong(1, recalcId);
			st2.setLong(1, recalcId);
			st3.setLong(1, recalcId);
			st4.setLong(1, recalcId);
			st5.setLong(1, recalcId);
			st6.setLong(1, recalcId);
			st7.setLong(1, recalcId);
			st8.setLong(1, recalcId);
			st1.execute();
			st2.execute();
			st3.execute();
			st4.execute();
			st5.execute();
			st6.execute();
			st7.execute();
			st8.execute();
		} finally {
			try {
				st1.close();
			} catch (Exception ex) {
			}
			try {
				st2.close();
			} catch (Exception ex) {
			}
			try {
				st3.close();
			} catch (Exception ex) {
			}
			try {
				st4.close();
			} catch (Exception ex) {
			}
			try {
				st5.close();
			} catch (Exception ex) {
			}
			try {
				st6.close();
			} catch (Exception ex) {
			}
			try {
				st7.close();
			} catch (Exception ex) {
			}
			st1 = null;
			st2 = null;
			st3 = null;
			st4 = null;
			st5 = null;
			st6 = null;
			st7 = null;
		}
	}

	/**
	 * This update is different from standard update procedure. This procedure
	 * is intended for use when exporting recalculations, while standard
	 * procedure is used during recalculations editing.
	 */
	public static final void updateRecalcFull(Recalc recalc, Session session) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE RECUT.RECALC SET ");
		sql.append("RECALC_NUMBER=?, CUSTOMER=?, ACCOUNT=?, CREATE_DATE=?,");
		sql.append("DESCRIPTION=?, IS_CHANGED=?, SAVE_DATE=?, SAVE_PERSON=?, ");
		sql.append("ADVISOR=?, INIT_BALANCE=?, FINAL_BALANCE=?\n");
		sql.append("WHERE ID = ?");
		PreparedStatement st = null;
		try {
			st = session.getConnection().prepareStatement(sql.toString());
			// 1. recalc number
			st.setString(1, recalc.getNumber());
			// 2. customer
			st.setInt(2, recalc.getCustomer().getId());
			// 3. account
			st.setInt(3, recalc.getAccount().getId());
			// 4. creation date
			st.setTimestamp(4, new Timestamp(recalc.getCreateDate().toDate().getTime()));
			// 5. description
			st.setString(5, recalc.getDescription() == null ? "" : recalc.getDescription());
			// 6. is changed?
			st.setInt(6, recalc.isChanged() ? 1 : 0);
			// 7. save date
			if (recalc.getSaveDate() == null)
				st.setNull(7, Types.TIMESTAMP);
			else
				st.setTimestamp(7, new Timestamp(recalc.getSaveDate().toDate().getTime()));
			// 8. save user
			if (recalc.getSaveUser() == null)
				st.setNull(8, Types.NUMERIC);
			else
				st.setLong(8, recalc.getSaveUser().getId());
			// 9. advisor
			if (recalc.getAdvisor() == null)
				st.setNull(9, Types.NUMERIC);
			else
				st.setLong(9, recalc.getAdvisor().getId());
			// 10. initial balance
			st.setDouble(10, recalc.getInitialBalance());
			// 11. final balance
			if (recalc.getFinalBalance() == null)
				st.setNull(11, Types.NUMERIC);
			else
				st.setDouble(11, recalc.getFinalBalance().doubleValue());
			// 12. id
			st.setLong(12, recalc.getId());
			st.execute();
		} finally {
			try {
				st.close();
			} catch (Exception ex) {
			}
			st = null;
		}
	}

	public static long insertRecalcFull(Recalc recalc, Session session) throws SQLException {
		StringBuilder sql1 = new StringBuilder();
		sql1.append("INSERT INTO RECUT.RECALC (");
		sql1.append("RECALC_NUMBER, CUSTOMER, ACCOUNT, CREATE_DATE, ");
		sql1.append("DESCRIPTION, IS_CHANGED, SAVE_DATE, SAVE_PERSON, ");
		sql1.append("ADVISOR, INIT_BALANCE, FINAL_BALANCE) ");
		sql1.append("VALUES (?,?,?,?,?,?,?,?,?,?,?) ");
		CallableStatement st1 = null;
		Statement st2 = null;
		ResultSet res = null;
		try {
			// INSERTING
			st1 = session.getConnection().prepareCall(sql1.toString());
			// 1. recalc number
			st1.setString(1, recalc.getNumber());
			// 2. customer
			st1.setInt(2, recalc.getCustomer().getId());
			// 3. account
			st1.setInt(3, recalc.getAccount().getId());
			// 4. creation date
			st1.setTimestamp(4, new Timestamp(recalc.getCreateDate().toDate().getTime()));
			// 5. description
			st1.setString(5, recalc.getDescription() == null ? "" : recalc.getDescription());
			// 6. is changed?
			st1.setInt(6, recalc.isChanged() ? 1 : 0);
			// 7. save date
			if (recalc.getSaveDate() == null)
				st1.setNull(7, Types.TIMESTAMP);
			else
				st1.setTimestamp(7, new Timestamp(recalc.getSaveDate().toDate().getTime()));
			// 8. save user
			if (recalc.getSaveUser() == null)
				st1.setNull(8, Types.NUMERIC);
			else
				st1.setLong(8, recalc.getSaveUser().getId());
			// 9. advisor
			if (recalc.getAdvisor() == null)
				st1.setNull(9, Types.NUMERIC);
			else
				st1.setLong(9, recalc.getAdvisor().getId());
			// 10. initial balance
			st1.setDouble(10, recalc.getInitialBalance());
			// 11. final balance
			if (recalc.getFinalBalance() == null)
				st1.setNull(11, Types.NUMERIC);
			else
				st1.setDouble(11, recalc.getFinalBalance().doubleValue());
			st1.execute();
			// GETTING ID
			st2 = session.getConnection().createStatement();
			res = st2.executeQuery("SELECT RECALC_SEQ.CURRVAL FROM DUAL");
			res.next();
			return res.getLong(1);
		} finally {
			try {
				st1.close();
			} catch (Exception ex) {
			}
			try {
				res.close();
			} catch (Exception ex) {
			}
			try {
				st2.close();
			} catch (Exception ex) {
			}
			st1 = null;
			st2 = null;
			res = null;
		}
	}

	public static void insertIntervalFull(Recalc recalc, int sequence, RecalcInterval interval, Session session) throws SQLException {
		StringBuilder sql1 = new StringBuilder();
		sql1.append("INSERT INTO RECUT.RECALC_INTERVAL(");
		sql1.append("RECALC_ID, NAME, SEQUENCE, START_BALANCE, EDITABLE");
		sql1.append(") VALUES (?,?,?,?,?)");
		StringBuilder sql2 = new StringBuilder();
		sql2.append("INSERT INTO RECUT.RECALC_ITEM(");
		sql2.append("INTERVAL_ID, ITEM_ID, CUSTOMER_ID, ACCOUNT_ID, ");
		sql2.append("OPERATION_ID, STATUS, READING, KWH, ");
		sql2.append("GEL, BALANCE, CYCLE_ID, CYCLE_DATE, ");
		sql2.append("ITEM_DATE, ENTER_DATE, CURR_DATE, PREV_DATE, ");
		sql2.append("METER_COEFF, METER_STATUS, METER_TYPE_ID, ITEM_NUMBER, ");
		sql2.append("ATT_UNIT, ATT_AMOUNT, ATT_COUNT, SEQUENCE, ");
		sql2.append("ORIG_OPERATION_ID, ORIG_ITEM_DATE, ORIG_ENTER_DATE, ORIG_CYCLE_DATE, ");
		sql2.append("ORIG_CYCLE_ID, ORIG_READING, ORIG_KWH, ORIG_GEL, ");
		sql2.append("ORIG_BALANCE, BALANCE_GAP, ORIG_BALANCE_GAP, LEAVE_KWH_UNCHANGED, ");
		sql2.append("ORIG_ATT_UNIT, ORIG_ATT_AMOUNT, ORIG_ATT_COUNT, METER_ACCELERATE, ");
		sql2.append("SUB_ACCOUNT_ID, ORIG_METER_COEFF");
		sql2.append(") VALUES (");
		sql2.append("?,?,?,?,?,?,?,?,?,?,");
		sql2.append("?,?,?,?,?,?,?,?,?,?,");
		sql2.append("?,?,?,?,?,?,?,?,?,?,");
		sql2.append("?,?,?,?,?,?,?,?,?,?,");
		sql2.append("?, ?)");
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		ResultSet res = null;
		try {
			// adding interval
			st1 = session.getConnection().prepareStatement(sql1.toString());
			st1.setLong(1, recalc.getId());
			st1.setString(2, interval.getName());
			st1.setInt(3, sequence);
			st1.setDouble(4, interval.getStartBalance());
			st1.setInt(5, interval.isEditable() ? 1 : 0);
			st1.execute();
			// getting interval ID
			res = session.getConnection().createStatement().executeQuery("SELECT RECALC_INTERVAL_SEQ.CURRVAL FROM DUAL");
			res.next();
			long intervalId = res.getLong(1);
			// inserting items
			st2 = session.getConnection().prepareStatement(sql2.toString());
			for (int i = 0; i < interval.getItems().size(); i++) {
				RecalcItem item = (RecalcItem) interval.getItems().get(i);
				st2.setLong(1, intervalId);
				st2.setLong(2, item.getItemId());
				st2.setInt(3, item.getCustomer().getId());
				st2.setInt(4, item.getAccount().getId());
				st2.setInt(5, item.getOperation().getId());
				st2.setInt(6, item.getStatus());
				st2.setDouble(7, item.getReading());
				st2.setDouble(8, item.getKwh());
				st2.setDouble(9, item.getGel());
				st2.setDouble(10, item.getBalance());
				st2.setInt(11, item.getCycle() ? +1 : -1);
				st2.setNull(12, Types.TIMESTAMP); // cycle date
				st2.setTimestamp(13, new Timestamp(item.getItemDate().toDate().getTime()));
				st2.setTimestamp(14, new Timestamp(item.getEnterDate().toDate().getTime()));
				st2.setNull(15, Types.TIMESTAMP); // current date
				if (item.getPreviousOperationDate() == null)
					st2.setNull(16, Types.TIMESTAMP);
				else
					st2.setTimestamp(16, new Timestamp(item.getPreviousOperationDate().toDate().getTime()));
				st2.setDouble(17, item.getMeterCoeff());
				st2.setInt(18, item.getMeterStatus() ? 0 : 1);
				st2.setInt(19, item.getMeter().getId());
				st2.setString(20, item.getItemNumber() == null ? "" : item.getItemNumber());
				if (item.getSubsidyAttachment() == null) {
					st2.setNull(21, Types.NUMERIC);
					st2.setNull(22, Types.NUMERIC);
					st2.setNull(23, Types.NUMERIC);
				} else {
					st2.setInt(21, item.getSubsidyAttachment().getUnit());
					st2.setDouble(22, item.getSubsidyAttachment().getAmount());
					st2.setInt(23, item.getSubsidyAttachment().getCount());
				}
				st2.setInt(24, i); // sequence
				st2.setInt(25, item.getOriginalOperation().getId());
				st2.setTimestamp(26, new Timestamp(item.getOriginalItemDate().toDate().getTime()));
				st2.setTimestamp(27, new Timestamp(item.getOriginalEnterDate().toDate().getTime()));
				st2.setNull(28, Types.TIMESTAMP); // cycle date
				st2.setInt(29, item.getOriginalCycle() ? +1 : -1);
				st2.setDouble(30, item.getOriginalReading());
				st2.setDouble(31, item.getOriginalKwh());
				st2.setDouble(32, item.getOriginalGel());
				st2.setDouble(33, item.getOriginalBalance());
				st2.setDouble(34, 0); // balance_gap
				st2.setDouble(35, 0); // orig_balance_gap
				st2.setInt(36, item.getCalculationHint());
				if (item.getOriginalSubsidyAttachment() == null) {
					st2.setNull(37, Types.NUMERIC);
					st2.setNull(38, Types.NUMERIC);
					st2.setNull(39, Types.NUMERIC);
				} else {
					st2.setInt(37, item.getOriginalSubsidyAttachment().getUnit());
					st2.setDouble(38, item.getOriginalSubsidyAttachment().getAmount());
					st2.setInt(39, item.getOriginalSubsidyAttachment().getCount());
				}
				st2.setDouble(40, item.getMeterAcceleration());
				if (item.getSubAccount() == null)
					st2.setNull(41, Types.NUMERIC);
				else
					st2.setInt(41, item.getSubAccount().getId());
				st2.setDouble(42, item.getOriginalMeterCoeff());
				st2.addBatch();
			}
			st2.executeBatch();
		} finally {
			try {
				st1.close();
			} catch (Exception ex) {
			}
			try {
				st2.close();
			} catch (Exception ex) {
			}
			try {
				res.close();
			} catch (Exception ex) {
			}
			st1 = null;
			st2 = null;
			res = null;
		}
	}

	public static List getDefaultTariffHistory(int accId, Session session) throws SQLException, IOException {
		List tariffs = E1CustomerManagment.getTariffs(session);
		String sql = SQLReader.readSQL("tariff/account_tariff_select.sql");
		PreparedStatement st = null;
		ResultSet res = null;
		try {
			st = session.getConnection().prepareStatement(sql);
			st.setInt(1, accId);
			res = st.executeQuery();
			List history = new ArrayList();
			while (res.next()) {
				int tariff_id = res.getInt("compkey");
				Date start = Date.create(res.getTimestamp("startdate"));
				Date end = Date.create(res.getTimestamp("enddate"));
				ITariff tariff = null;
				for (int i = 0; i < tariffs.size(); i++) {
					ITariff t = (ITariff) tariffs.get(i);
					if (t.getId() == tariff_id) {
						tariff = t;
						break;
					}
				}
				if (tariff == null) {
					throw new SQLException("Can not locate tariff with ID=" + tariff_id);
				}
				RecalcTariffItem item = new RecalcTariffItem();
				item.setId(-1);
				item.setTariff(tariff);
				item.setStartDate(start);
				item.setEndDate(end);
				history.add(item);
			}
			return history;
		} finally {
			try {
				res.close();
			} catch (Exception ex) {
			}
			try {
				st.close();
			} catch (Exception ex) {
			}
			res = null;
			st = null;
		}
	}

}
