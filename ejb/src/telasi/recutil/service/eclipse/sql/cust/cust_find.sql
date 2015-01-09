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
    c.custkey       cust_id,
    c.accnumb       cust_number,
    c.custname      cust_name,
    r.regionname    region_name,
    s.streetname    street_name,
    a.house         house,
    a.building	    building,
    a.porch	    	porch,
    a.flate         flate,
    a.postindex     postindex
FROM 
    bs.customer c,
    bs.address a,
    bs.street s,
    bs.region r
WHERE
    a.premisekey = c.premisekey AND
    s.streetkey  = a.streetkey  AND
    a.regionkey  = r.regionkey  AND
--------------------------------- custom parameters ----------------------------------
    r.regionname        LIKE ?  AND
    c.custname          LIKE ?  AND
    s.streetname        LIKE ?  AND
    NVL(a.house,   ' ') LIKE ?  AND
    NVL(a.building,' ') LIKE ?  AND
    NVL(a.porch,   ' ') LIKE ?  AND
    NVL(a.flate,   ' ') LIKE ?  AND
    ROWNUM <= ?
