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
  TAR.ACCKEY,
  TAR.COMPKEY,
  TAR.STARTDATE,
  TAR.ENDDATE,
  TAR.STATUS,
  TAR.ACCTARTYPE,
  TRIM(BASE.BASECOMPNAME) BASECOMPNAME
FROM
  BS.ACCTARIFFS  TAR,
  BS.ACCOUNT     ACC,
  BS.CUSTOMER    CUST,
  BS.TARCOMP     COMP,
  BS.BASETARCOMP BASE
WHERE
  TAR.ACCKEY = ACC.ACCKEY AND
  ACC.CUSTKEY = CUST.CUSTKEY AND
  COMP.COMPKEY = TAR.COMPKEY AND
  COMP.BASECOMPKEy = BASE.BASECOMPKEY AND
  CUST.CUSTKEY = ?
ORDER BY
  ACC.MAINACCOUNT DESC,
  ACC.ACCKEY,
  TAR.ACCTARKEY
