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

SELECT
    -- interval part
    intr.id             		interval_id,
    intr.recalc_id    			recalc_id,
    intr.name           		interval_name,
    intr.sequence       		interval_sequence,
    intr.start_balance  		start_balance,
    intr.editable				interval_editable,
    -- item part
    item.id             		recalc_item_id,
    item.item_id        		item_id,
    item.customer_id    		customer_id,
    item.account_id     		account_id,
    item.operation_id   		operation_id,
    item.status         		item_status,
    item.reading        		reading,
    item.kwh            		kwh,
    item.gel            		gel,
    item.balance        		balance,
    item.cycle_id       		cycle_id,
    item.cycle_date     		cycle_date,
    item.item_date      		item_date,
    item.enter_date     		enter_date,
    item.curr_date      		curr_date,
    item.prev_date      		prev_date,
    item.meter_coeff    		meter_coeff,
    item.meter_status   		meter_status,
    item.meter_type_id  		meter_type_id,
    item.item_number    		item_number,
    item.att_unit       		att_unit,
    item.att_amount     		att_amount,
    item.att_count      		att_count,
    item.orig_att_unit       	orig_att_unit,
    item.orig_att_amount     	orig_att_amount,
    item.orig_att_count      	orig_att_count,
    item.sequence       		item_sequence,
    item.orig_operation_id		orig_operation_id,
    item.orig_item_date			orig_item_date,
    item.orig_enter_date		orig_enter_date,
    item.orig_cycle_date		orig_cycle_date,
    item.orig_cycle_id			orig_cycle_id,
    item.orig_reading			orig_reading,
    item.orig_kwh				orig_kwh,
    item.orig_gel				orig_gel,
    item.orig_balance			orig_balance,
    item.balance_gap			balance_gap,
    item.orig_balance_gap   	orig_balance_gap,
    item.leave_kwh_unchanged 	leave_kwh_unchanged,
    item.meter_accelerate       meter_accelerate,
    item.sub_account_id			sub_account_id,
    item.orig_meter_coeff       orig_meter_coeff,
    a.accid						sub_account_number
FROM
    recut.recalc_interval intr,
    recut.recalc_item item,
    bs.account a
WHERE
    intr.id = item.interval_id and
    intr.recalc_id = ? and
    a.acckey(+) = item.sub_account_id
ORDER BY
    intr.sequence,
    item.sequence
