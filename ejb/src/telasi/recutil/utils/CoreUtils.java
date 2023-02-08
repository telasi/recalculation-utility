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
package telasi.recutil.utils;

import telasi.recutil.beans.Operation;
import telasi.recutil.beans.RecalcItem;
import telasi.recutil.beans.RecalcSubsidyAttachment;
import telasi.recutil.beans.SubsidyAttachment;

/**
 * Simple utilities.
 * @author dimakura
 */
public class CoreUtils {
	public static final double MIN_KWH = 0.0001;
	public static final double MIN_GEL = 0.00001;
	public static final int ZERO_TARIFF_ID = 12;

	public static RecalcItem copyRecalcItem(RecalcItem proto) {
		RecalcItem item = new RecalcItem();

		item.setId(proto.getId());
		item.setInterval(proto.getInterval());
		item.setItemId(proto.getItemId());
		item.setStatus(proto.getStatus());

		item.setCustomer(proto.getCustomer());
		item.setAccount(proto.getAccount());
		item.setItemNumber(proto.getItemNumber());

		item.setOperation(proto.getOperation());
		item.setOriginalOperation(proto.getOriginalOperation());
		item.setReading(proto.getReading());
		item.setOriginalReading(proto.getOriginalReading());
		item.setKwh(proto.getKwh());
		item.setOriginalKwh(proto.getOriginalKwh());
		item.setGel(proto.getGel());
		item.setOriginalGel(proto.getOriginalGel());
		item.setBalance(proto.getBalance());
		item.setOriginalBalance(proto.getOriginalBalance());
		item.setBalanceGap(proto.getBalanceGap());
		item.setOriginalBalanceGap(proto.getOriginalBalanceGap());
		item.setCalculationHint(proto.getCalculationHint());

		item.setItemDate(proto.getItemDate());
		item.setOriginalItemDate(proto.getOriginalItemDate());
		item.setEnterDate(proto.getEnterDate());
		item.setOriginalEnterDate(proto.getOriginalEnterDate());

		item.setCycle(proto.getCycle());
		item.setOriginalCycle(proto.getOriginalCycle());

		item.setMeter(proto.getMeter());
		item.setOriginalMeter(proto.getOriginalMeter());
		item.setMeterCoeff(proto.getMeterCoeff());
		item.setOriginalMeterCoeff(proto.getOriginalMeterCoeff());
		item.setMeterStatus(proto.getMeterStatus());
		item.setOriginalMeterStatus(proto.getOriginalMeterStatus());
		item.setMeterAcceleration(proto.getMeterAcceleration());

		item.setSubsidyAttachment(proto.getSubsidyAttachment());
		item.setOriginalSubsidyAttachment(proto.getOriginalSubsidyAttachment());

		item.setCurrentOperationDate(proto.getCurrentOperationDate());
		item.setPreviousOperationDate(proto.getPreviousOperationDate());

		return item;
	}

	/**
	 * Creates default subsidy attachment for operation.
	 */
	public static RecalcSubsidyAttachment createDefaultAttachment(Operation oper) {
		if (oper.getSubsidyAttachment() == null)
			return null;
		SubsidyAttachment sa = oper.getSubsidyAttachment();
		if (sa.getAmounts() == null /*
									 * || Math.abs(sa.getAmounts()[0]) <=
									 * 0.0099f
									 */) {
			return null;
		}
		RecalcSubsidyAttachment attachment = new RecalcSubsidyAttachment();
		attachment.setAmount(sa.getAmounts()[0]);
		attachment.setCount(1);
		attachment.setUnit(sa.getUnits()[0]);
		return attachment;
	}

	/**
	 * Creates default subsidy attachment for item.
	 */
	public static RecalcSubsidyAttachment createDefaultAttachment(RecalcItem item) {
		// #1. when operation is changed then determine attachment from
		// operation defaults
		if (item.getOriginalOperation().getId() != item.getOperation().getId())
			return createDefaultAttachment(item.getOperation());
		// #2. when original subsidy attachment is undefined, then same as above
		else if (item.getOriginalSubsidyAttachment() == null)
			return createDefaultAttachment(item.getOperation());
		// #3. else use original subsidy attachment
		else
			return item.getOriginalSubsidyAttachment();
	}
}
