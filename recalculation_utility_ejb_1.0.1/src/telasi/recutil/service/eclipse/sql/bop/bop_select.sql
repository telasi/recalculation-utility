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
  O.ID            OPER_ID,
  O.NAME_BS       OPER_NAME,
  O.D1            OPER_START,
  O.D2            OPER_END,
  O.REQ_CYCLE     OPER_REQ_CYCLE,
  O.REQ_READING   OPER_REQ_READING,
  O.REQ_KWH       OPER_REQ_KWH,
  O.REQ_GEL       OPER_REQ_GEL,
  O.DIFF_GROUP_ID OPER_DIFF_GROUP_ID,
  T.ID            TYPE_ID,
  T.NAME_BS       TYPE_NAME,
  A.RECALCULABLE  ATT_RECALCULABLE,
  A.UNIT1         ATT_UNIT1,
  A.AMOUNT1       ATT_AMOUNT1,
  A.UNIT2         ATT_UNIT2,
  A.AMOUNT2       ATT_AMOUNT2
FROM
  RECUT.BILLOPERATION O,
  RECUT.BILLOPERTYPE T,
  RECUT.SUBSIDY_ATTACHMENT A
WHERE
  O.TYPE_ID(+) = T.ID AND
  A.OPERATION(+) = O.ID
ORDER BY
  T.ID, O.SEQ, O.NAME_BS
