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
  R.ID,
  R.RECALC_NUMBER,
  R.CUSTOMER,
  R.ACCOUNT,
  R.CREATE_DATE,
  R.DESCRIPTION,
  R.IS_CHANGED,
  R.SAVE_DATE,
  R.SAVE_PERSON   SAVE_PERSON_ID,
  R.ADVISOR       ADVISOR_ID,
  R.INIT_BALANCE,
  R.FINAL_BALANCE,
  C.ACCNUMB,
  C.CUSTNAME,
  A.ACCID,
  TRIM(ST.STREETNAME) || ' ' || TRIM(ADRS.HOUSE) ADDRESS
FROM
  RECUT.RECALC R, BS.CUSTOMER C, BS.ACCOUNT A,
  BS.ADDRESS ADRS, BS.STREET ST
WHERE
  R.CUSTOMER = C.CUSTKEY AND
  A.ACCKEY = R.ACCOUNT AND
  ADRS.PREMISEKEY = C.PREMISEKEY AND
  ADRS.STREETKEY = ST.STREETKEY