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
    c.custkey                   cust_id,
    c.accnumb                   cust_number,
    c.custname                  cust_name,

	a.premisekey				address_id,
    r.regionkey					region_id,
    r.regionname				region_name,
    s.streetkey					street_id,
    s.streetname				street_name,
    a.house						house,
    a.building					building,
    a.porch						porch,
    a.flate						flate,
    a.postindex					postindex,

    a.roomnumber               	roomcount,
    c.balance					cust_balance,
    agr.left_amount             left_amount,
    c.custcatkey				cust_category,
    acc.acckey                  acc_id,
    acc.accid                   acc_number,
    acc.statuskey               acc_status,
    acc.mainaccount             acc_is_main,
    acc.createdate				createdate,
    acc.mtnumb                  mtnumb
FROM 
    bs.customer c,
    bs.account acc,
    bs.address a,
    bs.street s,
    bs.region r,
    bs.debt_agreement agr
WHERE
    c.custkey = acc.custkey and
    c.accnumb = ? and
    a.premisekey = c.premisekey and
    s.streetkey = a.streetkey and
    a.regionkey = r.regionkey and
    agr.acckey(+) = c.custkey
ORDER BY
	acc.mainaccount desc,
	acc.acckey
