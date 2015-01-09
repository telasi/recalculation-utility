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
package telasi.recutil.gui.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import telasi.recutil.beans.DiffDetail;
import telasi.recutil.beans.Operation;
import telasi.recutil.beans.OperationType;
import telasi.recutil.beans.Recalc;
import telasi.recutil.beans.Recalc2;
import telasi.recutil.beans.RecalcInstCp;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.Role;
import telasi.recutil.beans.SubsidyAttachment;
import telasi.recutil.beans.User;
import telasi.recutil.beans.tpowner.TpOwnerAccount;
import telasi.recutil.beans.tpowner.TpOwnerCorrection;
import telasi.recutil.gui.plugin.Plugin;

/**
 * Here are utility methods for GUI.
 * 
 * @author <a href="mailto:dimakura@dev.java.net">Dimitri Kurashvili</a>
 * @version 1.0.0 Oct, 2006
 */
public class GUIUtils {

	private static Font TITLE_FONT = null;

	private static Font SUBTITLE_FONT = null;

	public static void centerShell(Shell shell) {

		Point size = shell.getSize();
		Point parentLocation = shell.getParent().getLocation();
		Point parentSize = shell.getParent().getSize();
		int x = parentLocation.x + (parentSize.x - size.x) / 2;
		int y = parentLocation.y + (parentSize.y - size.y) / 2;
		x = x < 0 ? 0 : x;
		y = y < 0 ? 0 : y;
		shell.setLocation(x, y);

	}

	public static Font createTitleFont(Font plainTextFont) {
		if (TITLE_FONT == null) {
			initFonts(plainTextFont);
		}
		return TITLE_FONT;
	}

	public static Font createSubtitleFont(Font plainTextFont) {
		if (TITLE_FONT == null) {
			initFonts(plainTextFont);
		}
		return SUBTITLE_FONT;
	}

	private static void initFonts(Font plainTextFont) {
		FontData data = plainTextFont.getFontData()[0];
		int originalHeight = data.getHeight();
		data.setHeight(originalHeight + 4);
		data.setStyle(SWT.BOLD);
		TITLE_FONT = new Font(Display.getDefault(), data);
		data.setHeight(originalHeight + 2);
		data.setStyle(SWT.BOLD);
		SUBTITLE_FONT = new Font(Display.getDefault(), data);
	}

	public static Image getRoleImage(Role role) {
		if (role.isEnabled()) {
			return Plugin.getImage("icons/16x16/login.png");
		} else {
			return Plugin.getImage("icons/16x16/login_gray.png");
		}
	}

	public static Image getUserImage(User user) {
		if (user.isEnabled()) {
			return Plugin.getImage("icons/16x16/user.png");
		} else {
			return Plugin.getImage("icons/16x16/user_gray.png");
		}
	}

	public static String getActionName(int actionId) {
		return GUIMessages.getMessage("dbaction." + actionId);
	}

	private static Image getOperationTypeImage(int operationType, boolean enabled) {
		try {
			switch (operationType) {
			case OperationType.READING:
				return enabled ? Plugin.getImage("icons/16x16/bop/reading.png") : Plugin.getImage("icons/16x16/bop/reading2.png");
			case OperationType.CHARGE:
				return enabled ? Plugin.getImage("icons/16x16/bop/charge.png") : Plugin.getImage("icons/16x16/bop/charge2.png");
			case OperationType.PAYMENT:
				return enabled ? Plugin.getImage("icons/16x16/bop/payment.png") : Plugin.getImage("icons/16x16/bop/payment2.png");
			case OperationType.VOUCHER:
				return enabled ? Plugin.getImage("icons/16x16/bop/voucher.png") : Plugin.getImage("icons/16x16/bop/voucher2.png");
			case OperationType.SUBSIDY:
				return enabled ? Plugin.getImage("icons/16x16/bop/subsidy.png") : Plugin.getImage("icons/16x16/bop/subsidy2.png");
			case OperationType.ADD_CHARGE:
				return enabled ? Plugin.getImage("icons/16x16/bop/add.png") : Plugin.getImage("icons/16x16/bop/add2.png");
			case OperationType.PENALTY:
				return enabled ? Plugin.getImage("icons/16x16/bop/penalty.png") : Plugin.getImage("icons/16x16/bop/penalty2.png");
			case OperationType.SUBACC_TRANSIT:
				return enabled ? Plugin.getImage("icons/16x16/bop/sub_transit.png") : Plugin.getImage("icons/16x16/bop/sub_transit2.png");
			case OperationType.SERVICE:
				return enabled ? Plugin.getImage("icons/16x16/bop/service.png") : Plugin.getImage("icons/16x16/bop/service2.png");
			case OperationType.POWER_OPERATION:
				return enabled ? Plugin.getImage("icons/16x16/bop/power.png") : Plugin.getImage("icons/16x16/bop/power2.png");
			case OperationType.SUBACC_CHARGE:
				return enabled ? Plugin.getImage("icons/16x16/bop/subaccount.png") : Plugin.getImage("icons/16x16/bop/subaccount2.png");
			case OperationType.DEBT_RESTRUCTURIZATION:
				return enabled ? Plugin.getImage("icons/16x16/bop/restruct.png") : Plugin.getImage("icons/16x16/bop/restruct2.png");
			case OperationType.TRANSIT:
				return enabled ? Plugin.getImage("icons/16x16/bop/trans.png") : Plugin.getImage("icons/16x16/bop/trans2.png");
			case OperationType.COMPENSATION:
				return enabled ? Plugin.getImage("icons/16x16/bop/compensation.png") : Plugin.getImage("icons/16x16/bop/compensation2.png");
			case OperationType.AUDIT_READING:
				return enabled ? Plugin.getImage("icons/16x16/bop/audit.png") : Plugin.getImage("icons/16x16/bop/audit2.png");
			case OperationType.USAID6_CORRECTION:
				return enabled ? Plugin.getImage("icons/16x16/bop/usaid6.png") : Plugin.getImage("icons/16x16/bop/usaid62.png");
			case OperationType.NETWORK_OPERATIONS:
				return enabled ? Plugin.getImage("icons/16x16/bop/power.png") : Plugin.getImage("icons/16x16/bop/power2.png");
			default:
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	public static Image getOperationTypeImage(int operationType) {
		return getOperationTypeImage(operationType, true);
	}

	public static Image getItemImage(RecalcItem item) {
		switch (item.getStatus()) {
		case RecalcItem.DELETED:
			return getOperationImage(item.getOperation(), false);
		default:
			return getOperationImage(item.getOperation(), true);
		}
	}

	private static Image getOperationImage(Operation operation, boolean enabled) {
		try {
			if (operation.isRegular()) {
				return enabled ? Plugin.getImage("icons/16x16/bop/regular.png") : Plugin.getImage("icons/16x16/bop/regular2.png");
			}
			int operId = operation.getId();
			switch (operId) {
			case Operation.SUMMARY_CHARGE:
			case Operation.SUMMARY_PERCENT_SUBSIDY:
			case Operation.DISCHARGE_GEL:
			case Operation.DISCHARGE_PERCENT_SUBSIDY_GEL:
				return enabled ? Plugin.getImage("icons/16x16/bop/sum.png") : Plugin.getImage("icons/16x16/bop/sum2.png");
			case Operation.CURRENT_CHARGE:
			case Operation.CURRENT_CHARGE_ACT:
			case Operation.CURRENT_CHARGE_VOUCHER:
				return enabled ? Plugin.getImage("icons/16x16/bop/current.png") : Plugin.getImage("icons/16x16/bop/current2.png");
			default:
				return getOperationTypeImage(operation.getType().getId(), enabled);
			}
		} catch (Exception ex) {
			return null;
		}
	}

	public static Image getOperationImage(Operation operation) {
		return getOperationImage(operation, true);
	}

	public static String getRequimentName(int req) {
		switch (req) {
		case Operation.REQUIERED:
			return GUIMessages.getMessage("comp.general.required.requiered");
		case Operation.NOT_REQUIERED_AT_ALL:
			return GUIMessages.getMessage("comp.general.required.not_requiered");
		case Operation.OPTIONAL:
			return GUIMessages.getMessage("comp.general.required.optional");
		default:
			return "?";
		}
	}

	public static String getDiffGroupName(int groupId) {
		switch (groupId) {
		case DiffDetail.BALANCE:
			return GUIMessages.getMessage("oper.diff.balance");
		case DiffDetail.CHARGE:
			return GUIMessages.getMessage("oper.diff.charge");
		case DiffDetail.COMPENSATION:
			return GUIMessages.getMessage("oper.diff.compensation");
		case DiffDetail.PAYMENT:
			return GUIMessages.getMessage("oper.diff.payment");
		case DiffDetail.PENALTY:
			return GUIMessages.getMessage("oper.diff.penalty");
		case DiffDetail.SERVICE:
			return GUIMessages.getMessage("oper.diff.service");
		case DiffDetail.SUBSIDY_COMP_REFUGE:
			return GUIMessages.getMessage("oper.diff.sibsidy_comp_refuge");
		case DiffDetail.SUBSIDY_EMPLOYEE:
			return GUIMessages.getMessage("oper.diff.sibsidy_employee");
		case DiffDetail.SUBSIDY_FIX_KWH:
			return GUIMessages.getMessage("oper.diff.sibsidy_fix_kwh");
		case DiffDetail.SUBSIDY_GENERAL:
			return GUIMessages.getMessage("oper.diff.subsidy_general");
		case DiffDetail.SUBSIDY_PENSION:
			return GUIMessages.getMessage("oper.diff.sibsidy_pension");
		case DiffDetail.SUBSIDY_PERCENT:
			return GUIMessages.getMessage("oper.diff.sibsidy_percent");
		case DiffDetail.SUBSIDY_REFUGE:
			return GUIMessages.getMessage("oper.diff.sibsidy_refuge");
		case DiffDetail.SUBSIDY_TARIFF_2003:
			return GUIMessages.getMessage("oper.diff.sibsidy_tariff_2003");
		case DiffDetail.SUBSIDY_TARIFF_2006:
			return GUIMessages.getMessage("oper.diff.sibsidy_tariff_2006");
		case DiffDetail.SUBSIDY_USAID:
			return GUIMessages.getMessage("oper.diff.sibsidy_usaid");
		case DiffDetail.VOUCHER:
			return GUIMessages.getMessage("oper.diff.voucher");
		case DiffDetail.ONE_TIME_ACT:
			return GUIMessages.getMessage("oper.diff.one_time_act");
		default:
			return GUIMessages.getMessage("oper.diff.none");
		}
	}

	public static String getUnitName(int unit) {
		switch (unit) {
		case SubsidyAttachment.GEL:
			return GUIMessages.getMessage("comp.general.gel");
		case SubsidyAttachment.KWH:
			return GUIMessages.getMessage("comp.general.kwh");
		case SubsidyAttachment.KWH_BY_DAY:
			return GUIMessages.getMessage("comp.general.kwh_by_days");
		case SubsidyAttachment.PERCENT:
			return GUIMessages.getMessage("comp.general.percent");
		case SubsidyAttachment.TARIFF:
			return GUIMessages.getMessage("comp.general.tariff");
		default:
			return "?";
		}
	}

	public static String getItemStatusName(int status) {
		switch (status) {
		case RecalcItem.DELETED:
			return GUIMessages.getMessage("voucher.item.status.deleted");
		case RecalcItem.ORIGINAL:
			return GUIMessages.getMessage("voucher.item.status.original");
		case RecalcItem.OTHER:
			return GUIMessages.getMessage("voucher.item.status.other");
		case RecalcItem.NEW:
			return GUIMessages.getMessage("voucher.item.status.new");
		default:
			return "?";
		}
	}

	public static String getInstcpRecalculationMethodName(int method) {
		switch (method) {
		case RecalcInstCp.AVERAGE_DAY_CHARGE:
			return GUIMessages.getMessage("instcp.recalc.option.daycharge");
		case RecalcInstCp.NORM_ON_PRV_MONTH_RND_ON_3_DAYS:
			return GUIMessages.getMessage("instcp.recalc.option.prevmonth_round_3_days");
		case RecalcInstCp.NORMALIZE_ON_30_DAYS:
			return GUIMessages.getMessage("instcp.recalc.option.30days");
		case RecalcInstCp.NORMALIZE_ON_PREV_MONTH:
			return GUIMessages.getMessage("instcp.recalc.option.prevmonth");
		default:
			return "";
		}
	}

	public static String getCalculationHintName(int hint) {
		switch (hint) {
		case RecalcItem.HINT_NONE:
			return GUIMessages.getMessage("hint.none");
		case RecalcItem.HINT_PRESERVE_BOTH:
			return GUIMessages.getMessage("hint.preserve_both");
		case RecalcItem.HINT_DERIVE_FROM_EXISTING_KWH:
			return GUIMessages.getMessage("hint.preserve_kwh");
		case RecalcItem.HINT_USE_CONTINUOUS_BY_INSTCP:
			return GUIMessages.getMessage("hint.use_continuous_instcp_history");
		case RecalcItem.HINT_FORCE_DISCHARGE:
			return GUIMessages.getMessage("hint.force_discharge");
		default:
			return "?";
		}
	}

	public static Image getRecalcImage(Recalc recalc) {
		if (recalc instanceof Recalc2) {
			Recalc2 recalc2 = (Recalc2) recalc;
			switch (recalc2.getStatus()) {
			case Recalc2.STATUS_DEFAULT:
				return Plugin.getImage("icons/16x16/changed.png");
			case Recalc2.STATUS_SAVED:
				return Plugin.getImage("icons/16x16/save.png");
			case Recalc2.STATUS_FINALIZED:
				return Plugin.getImage("icons/16x16/true.png");
			case Recalc2.STATUS_CANCELED:
				return Plugin.getImage("icons/16x16/stop.png");
			default:
				return null;
			}
		} else {
			return recalc.isChanged() ? Plugin.getImage("icons/16x16/changed.png") : Plugin.getImage("icons/16x16/save.png");
		}
	}

	public static Image getTpOwnerStatusImage(int statusId) {
		switch (statusId) {
		case TpOwnerAccount.STATUS_NORMAL:
			return Plugin.getImage("icons/16x16/no_cycle.png");
		case TpOwnerAccount.STATUS_CYCLE_COMPLETED:
			return Plugin.getImage("icons/16x16/info.png");
		case TpOwnerAccount.STATUS_CALCULATED:
			return Plugin.getImage("icons/16x16/calc.png");
		case TpOwnerAccount.STATUS_CALCULATED_WITH_ERRORS:
			return Plugin.getImage("icons/16x16/stop.png");
		case TpOwnerAccount.STATUS_SENDED:
			return Plugin.getImage("icons/16x16/true.png");
		default:
			return null;
		}
	}

	public static String getTpOwnerCorrectionName(int typeId) {
		switch (typeId) {
		case TpOwnerCorrection.RECHARGE:
			return GUIMessages.getMessage("comp.tpowner.recal.recharge");
		case TpOwnerCorrection.DISCHARGE:
			return GUIMessages.getMessage("comp.tpowner.recal.discharge");
		default:
			return "<unknown>";
		}

	}

}
