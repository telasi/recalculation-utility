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
	R.ROLE_ID, R.ROLE_NAME, R.ROLE_DESC, R.ROLE_ENABLED,
 	U.USER_ID, U.USER_NAME, U.USER_FULLNAME, U.USER_ENABLED,
 	U.ADVISOR,
 	U.USER_NUMBER, U.LAST_SEQUENCE
FROM
 	RECUT.ROLE R, RECUT.USERS U
WHERE
	U.ROLE_ID(+) = R.ROLE_ID
ORDER BY
	ROLE_NAME ASC, USER_NUMBER ASC, USER_NAME ASC
