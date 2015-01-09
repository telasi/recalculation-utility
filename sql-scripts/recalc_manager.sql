create or replace
PACKAGE "RECALC_MANAGER" AS

--------------------------------------------------------------------------------

TYPE DATE_ARRAY IS TABLE OF DATE;

STAT_ORIGINAL     CONSTANT NUMBER := 1;
STAT_OTHERS       CONSTANT NUMBER := 2;
STAT_NEW          CONSTANT NUMBER := 3;
STAT_DELETED      CONSTANT NUMBER := 4;

INSTCP_NORMALIZE_ON_PREV_MONTH CONSTANT NUMBER := 1;
INSTCP_NORMALIZE_ON_30_DAYS    CONSTANT NUMBER := 2;
INSTCP_AVERAGE_DAY_CHARGE      CONSTANT NUMBER := 3;
INSTCP_NORMALIZE_ON_PRVMNTH_R3 CONSTANT NUMBER := 4;

STEP_TARIFF_START_DATE      CONSTANT DATE   := '1-Jun-2006';
MONTHLY_INSTCP_START_DATE   CONSTANT DATE   := '1-Apr-2003';
ROUND_ON_3_DAYS_END_DATE    CONSTANT DATE   := '31-Dec-2001';
MISSED_TARIFF_CHANGE_2000   CONSTANT DATE   := '4-Sep-2000';

MISSED_TARIFF_ID_2000 CONSTANT NUMBER := 1;

CHANGE_OPERATION CONSTANT NUMBER := 1;
CHANGE_ITEMDATE  CONSTANT NUMBER := 2;
CHANGE_ENTERDATE CONSTANT NUMBER := 3;
CHANGE_READING   CONSTANT NUMBER := 4;
CHANGE_KWH       CONSTANT NUMBER := 5;
CHANGE_GEL       CONSTANT NUMBER := 6;
CHANGE_CYCLE     CONSTANT NUMBER := 7;

RECALC_STATUS_DEFAULT  CONSTANT NUMBER := 1;
RECALC_STATUS_SAVED    CONSTANT NUMBER := 0;
RECALC_STATUS_CANCELED CONSTANT NUMBER := 2;
RECALC_STATUS_FINALIZE CONSTANT NUMBER := 3;

--------------------------------------------------------------------------------

PROCEDURE MARK_CHANGED (
  P_RECALC_ID NUMBER
);

PROCEDURE SAVE_RECALC (
  P_RECALC_ID   NUMBER,
  P_DESCRIPTION NVARCHAR2,
  P_SAVER       NUMBER,
  P_ADVISOR     NUMBER
);

PROCEDURE SAVE_RECALC_DETAIL (
  P_RECALC_ID    NUMBER,
  P_OPERATION_ID NUMBER,
  P_KWH          NUMBER,
  P_GEL          NUMBER
);

PROCEDURE FINALIZE_RECALC_ON_SAVE (
  P_RECALC_ID NUMBER
);

PROCEDURE CHANGE_STATUS (
  P_RECALC_ID NUMBER,
  P_STATUS NUMBER
);
--------------------------------------------------------------------------------

PROCEDURE RECALC_INSERT (
  P_CUSTOMER_ID IN  NUMBER,
  P_ACCOUNT_ID  IN  NUMBER,
  P_USER_ID     IN  NUMBER,
  P_DESCRIPTION IN  NVARCHAR2,
  --P_NUMBER      OUT NVARCHAR2,
  P_RECALC_ID   OUT NUMBER
);

PROCEDURE RECALC_UPDATE (
  P_RECALC_ID     NUMBER,
  P_RECALC_NUMBER NVARCHAR2,
  P_DESCRIPTION   NVARCHAR2,
  P_ADVISOR       NUMBER
);

PROCEDURE RECALC_DELETE (
  P_RECALC_ID IN NUMBER
);

PROCEDURE RECALC_COPY (
  p_recalc_id NUMBER,
  p_user_id NUMBER,
  p_new_recalc_id OUT NUMBER,
  p_new_recalc_number OUT VARCHAR2
);

FUNCTION GET_VOUCHER_NUMBER (
  P_USER_ID     IN  NUMBER
) RETURN VARCHAR2;
--------------------------------------------------------------------------------

PROCEDURE RECALC_INTERVAL_CREATE (
  P_RECALC_ID   IN     NUMBER,
  P_RECALC_NAME IN     NVARCHAR2,
  P_ITEM1       IN     NUMBER,
  P_ITEM2       IN     NUMBER,
  P_INTERVAL_ID    OUT NUMBER
);

PROCEDURE RECALC_INTERVAL_UPDATE (
  P_INTERVAL_ID     IN NUMBER,
  P_INTERVAL_NAME   IN NVARCHAR2,
  P_INITIAL_BALANCE IN NUMBER
);

PROCEDURE RECALC_INTERVAL_DELETE (
  P_INTERVAL_ID IN NUMBER
);

PROCEDURE GET_INTERVAL_DEFAULT_BALANCE (
  p_interval_id   NUMBER,
  p_start_balance OUT NUMBER
);

--------------------------------------------------------------------------------

PROCEDURE RECALC_ITEM_INSERT (
  p_interval_id   IN NUMBER,  -- 1
  p_customer_id   IN NUMBER,
  p_account_id    IN NUMBER,
  p_operation_id  IN NUMBER,
  p_reading       IN NUMBER,  -- 5
  p_kwh           IN NUMBER,
  p_gel           IN NUMBER,
  p_cycle         IN NUMBER,
  p_item_date     IN DATE,
  p_enter_date    IN DATE,    -- 10
  p_curr_date     IN DATE,
  p_prev_date     IN DATE,
  p_meter_coeff   IN NUMBER,
  p_meter_status  IN NUMBER,
  p_meter_type_id IN NUMBER,  -- 15
  p_item_number   IN NVARCHAR2,
  p_att_unit      IN NUMBER,
  p_att_count     IN NUMBER,
  p_att_amount    IN NUMBER,
  p_sequence      IN NUMBER,  -- 20
  p_balance_gap   IN NUMBER,
  p_leave_kwh_unchanged IN  NUMBER,
  p_recalc_item_id      OUT NUMBER
);

PROCEDURE RECALC_ITEM_UPDATE (
  p_recalc_item_id      NUMBER, -- 1
  p_operation_id        NUMBER,
  p_reading             NUMBER,
  p_kwh                 NUMBER,
  p_gel                 NUMBER, -- 5   
  p_cycle               NUMBER,
  p_item_date           DATE,
  p_enter_date          DATE,
  p_curr_date           DATE,   
  p_prev_date           DATE,   -- 10
  p_meter_coeff         NUMBER,
  p_meter_status        NUMBER,
  p_meter_type_id       NUMBER,
  p_att_unit            NUMBER,
  p_att_count           NUMBER, -- 15
  p_att_amount          NUMBER,
  p_balance_gap         NUMBER,
  p_leave_kwh_unchanged NUMBER
);

PROCEDURE RECALC_ITEM_DELETE (
  P_ITEM_ID NUMBER
);

PROCEDURE RECALC_ITEM_ENABLE (
  P_RECALC_ITEM_ID NUMBER,
  P_ENABLE         NUMBER
);

PROCEDURE MOVE_RECALC_ITEM (
  P_RECALC_ITEM_ID IN NUMBER,
  P_DIRECTION      IN NUMBER -- 0 up; 1 down
);

PROCEDURE LOCK_KWH_RECALCULATION (
  P_ITEM_ID NUMBER,
  P_LOCK    NUMBER
);

PROCEDURE ITEM_SUBSIDY_ATT_UPDATE (
  P_ITEM_ID    NUMBER,
  P_ATT_UNIT   NUMBER,
  P_ATT_AMOUNT NUMBER,
  P_ATT_COUNT  NUMBER
);

PROCEDURE RECALC_ITEM_METER_UPDATE (
  P_ITEM_ID          NUMBER,
  P_METER_ID         NUMBER,
  P_METER_COEFF      NUMBER,
  P_METER_STATUS     NUMBER,
  P_METER_ACCELERATE NUMBER
);

PROCEDURE RECALC_ITEM_RESTORE_ORIGINALS (
  P_ITEM_ID NUMBER,
  P_OPTION  NUMBER
);

--------------------------------------------------------------------------------

PROCEDURE REGULAR_ITEM_INSERT (
  P_RECALC_ID   NUMBER,
  P_START_DATE  DATE,
  P_END_DATE    DATE,
  P_OPERATION   NUMBER,
  P_ATT_UNIT    NUMBER,
  P_ATT_AMOUNT  NUMBER,
  P_ATT_AMOUNT2 NUMBER,
  P_ATT_COUNT   NUMBER,
  P_SEQUENCE    NUMBER,
  P_ITEM_ID    OUT NUMBER
);

PROCEDURE REGULAR_ITEM_DELETE (
  P_RECALC_ITEM_ID NUMBER
);

PROCEDURE REGULAR_ITEM_UPDATE (
  P_ITEM_ID     NUMBER,
  P_START_DATE  DATE,
  P_END_DATE    DATE,
  P_OPERATION   NUMBER,
  P_ATT_UNIT    NUMBER,
  P_ATT_AMOUNT  NUMBER,
  P_ATT_AMOUNT2 NUMBER,
  P_ATT_COUNT   NUMBER
);

PROCEDURE MOVE_REGULAR (
  p_item_id   NUMBER,
  p_direction NUMBER
);

--------------------------------------------------------------------------------

FUNCTION GET_ROOMS_COUNT (
  p_recalc_id NUMBER,
  p_date      DATE
) RETURN NUMBER;

PROCEDURE CREATE_ROOM_HISTORY_ITEM (
  p_recalc_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_room_count NUMBER,
  p_id         OUT NUMBER
);

PROCEDURE UPDATE_ROOM_HISTORY_ITEM (
  p_id            IN  NUMBER,
  p_start_date    IN  DATE,
  p_end_date      IN  DATE,
  p_room_count    IN  NUMBER
);

PROCEDURE DELETE_ROOM_HISTORY_ITEM (
  p_id    IN  NUMBER
);

--------------------------------------------------------------------------------

PROCEDURE CREATE_DEFAULT_INSTCP_HISTORY (
  p_recalc_id NUMBER
);

PROCEDURE INSTCP_ITEM_INSERT (
  p_recalc_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_amount     NUMBER,
  p_option     NUMBER,
  p_sequence   NUMBER,
  p_instcp_id OUT NUMBER
);

PROCEDURE INSTCP_ITEM_DELETE (
  p_instcp_id NUMBER
);

PROCEDURE INSTCP_ITEM_UPDATE (
  p_item_id NUMBER,
  p_start_date DATE,
  p_end_date DATE,
  p_amount NUMBER,
  p_option NUMBER
);

PROCEDURE INSTCP_ITEM_MOVE (
  p_item_id   NUMBER,
  p_direction NUMBER -- 0 up; 1 down
);

--------------------------------------------------------------------------------

PROCEDURE CREATE_DEFAULT_TARIFF_HISTORY (
  p_recalc_id NUMBER
);

PROCEDURE TARIFF_ITEM_INSERT (
  p_recalc_id  NUMBER,
  p_tariff_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_sequence   NUMBER,
  p_item_id    OUT NUMBER
);

PROCEDURE TARIFF_ITEM_UPDATE (
  p_item_id    NUMBER,
  p_tariff_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE
);

PROCEDURE TARIFF_ITEM_DELETE (
  p_item_id NUMBER
);

PROCEDURE TARIFF_ITEM_MOVE (
  p_item_id   NUMBER,
  p_direction NUMBER -- 0 up; 1 down
);

--------------------------------------------------------------------------------

PROCEDURE CUT_ITEM_INSERT (
  p_recalc_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_sequence   NUMBER,
  p_item_id    OUT NUMBER
);

PROCEDURE CUT_ITEM_UPDATE (
  p_item_id    NUMBER,
  p_start_date DATE,
  p_end_date   DATE
);

PROCEDURE CUT_ITEM_DELETE (
  p_cut_id NUMBER
);

PROCEDURE CUT_ITEM_MOVE (
  p_cut_item_id NUMBER,
  p_direction   NUMBER
);

--------------------------------------------------------------------------------

PROCEDURE ROOM_ITEM_INSERT (
  p_recalc_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_room_count NUMBER,
  p_id OUT NUMBER
);

PROCEDURE ROOM_ITEM_UPDATE (
  p_id         NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_room_count NUMBER
);

PROCEDURE ROOM_ITEM_DELETE (
  p_id NUMBER
);

--------------------------------------------------------------------------------

FUNCTION FIND_SUBSIDY_RECALCULATION (
  P_ITEM_ID    IN  NUMBER,
  P_ATT_UNIT   OUT NUMBER,
  P_ATT_AMOUNT OUT NUMBER,
  P_ATT_COUNT  OUT NUMBER
) RETURN BOOLEAN;

FUNCTION IS_RECALCULABLE_SUBSIDY (
  OPERATION_ID NUMBER
) RETURN BOOLEAN;

PROCEDURE get_interval_defaul_balance (
  p_interval_id   IN     NUMBER,
  p_start_balance    OUT NUMBER
);

PROCEDURE GET_METER_PROPERTIES (
  p_account   IN  NUMBER,
  p_item      IN  NUMBER,
  p_type      OUT NUMBER,
  p_coeff     OUT NUMBER,
  p_meter_st  OUT NUMBER
);

--------------------------------------------------------------------------------

END;
/

create or replace PACKAGE BODY "RECALC_MANAGER" AS

--------------------------------------------------------------------------------

PROCEDURE create_inst_cp_history_part2 (
  p_recalc_id            NUMBER,
  p_last_sequence_from_p1 NUMBER
);

PROCEDURE create_inst_cp_history_part1 (
  p_recalc_id    IN      NUMBER,
  p_last_sequence     OUT NUMBER
);

--------------------------------------------------------------------------------

FUNCTION IS_RECALC_SAVED (
  P_RECALC_ID NUMBER
) RETURN BOOLEAN IS
  P_CHANGED_FLAG NUMBER;
BEGIN

  SELECT IS_CHANGED INTO P_CHANGED_FLAG
  FROM RECALC WHERE ID = P_RECALC_ID;

  RETURN NVL(P_CHANGED_FLAG, 0) IN (
    RECALC_STATUS_SAVED,
    RECALC_STATUS_CANCELED,
    RECALC_STATUS_FINALIZE);

END; -- IS_RECALC_SAVED

PROCEDURE ASSERT_NOT_SAVED (
  P_RECALC_ID NUMBER
) IS
BEGIN

  IF IS_RECALC_SAVED(P_RECALC_ID)
  THEN
  
  RAISE_APPLICATION_ERROR(-20000, 'ASSERT_NOT_SAVED: recalculation is saved');
  
  END IF;

END; -- ASSERT_NOT_SAVED

PROCEDURE MARK_CHANGED (
  P_RECALC_ID NUMBER
) IS
BEGIN

UPDATE RECALC SET IS_CHANGED = 1
WHERE ID = P_RECALC_ID;

END; -- MARK_CHANGED

/**
 * Changes status of the recalculation.
 * There are some restrictions on status changing.
 */
PROCEDURE CHANGE_STATUS (
  P_RECALC_ID NUMBER,
  P_STATUS NUMBER
) IS
  P_CURR_STATUS NUMBER;
BEGIN

SELECT IS_CHANGED INTO P_CURR_STATUS
FROM RECALC WHERE ID = P_RECALC_ID;

-- check exceptions
IF P_CURR_STATUS = P_STATUS
THEN
  RETURN;
ELSIF
  P_CURR_STATUS != RECALC_STATUS_SAVED OR
  P_STATUS NOT IN (RECALC_STATUS_CANCELED, RECALC_STATUS_FINALIZE)
THEN
  RAISE_APPLICATION_ERROR(-20000, 'Can only finalize or cancel saved recalculation.');
END IF;

-- update
UPDATE RECALC SET IS_CHANGED = P_STATUS
WHERE ID = P_RECALC_ID;

END; -- MARK_CHANGED

PROCEDURE SAVE_RECALC (
  P_RECALC_ID   NUMBER,
  P_DESCRIPTION NVARCHAR2,
  P_SAVER       NUMBER,
  P_ADVISOR     NUMBER
) IS
BEGIN

-- should not be saved
ASSERT_NOT_SAVED(P_RECALC_ID);

-- update recalc
UPDATE RECALC SET
  SAVE_DATE   = SYSDATE,
  SAVE_PERSON = P_SAVER,
  ADVISOR     = P_ADVISOR,
  DESCRIPTION = P_DESCRIPTION,
  IS_CHANGED  = 0
WHERE
  ID = P_RECALC_ID;

-- clear saved details list
DELETE RECALC_SAVE
WHERE RECALC_ID = P_RECALC_ID;

-- clear factura expansion table
DELETE FACTURA
WHERE RECALC_ID = P_RECALC_ID;

END; -- SAVE_RECALC

PROCEDURE SAVE_RECALC_DETAIL (
  P_RECALC_ID    NUMBER,
  P_OPERATION_ID NUMBER,
  P_KWH          NUMBER,
  P_GEL          NUMBER
) IS
BEGIN

INSERT INTO RECALC_SAVE (
  RECALC_ID, OPERATION_ID, KWH, GEL
) VALUES (
  P_RECALC_ID, P_OPERATION_ID, P_KWH, P_GEL
);

END; -- SAVE_RECALC_DETAIL

PROCEDURE FINALIZE_RECALC_ON_SAVE (
  P_RECALC_ID NUMBER
) IS
  P_DELTA NUMBER;
  P_FINAL_BALANCE NUMBER;
BEGIN

  SELECT SUM(GEL) INTO P_DELTA
  FROM RECALC_SAVE
  WHERE RECALC_ID = P_RECALC_ID;

  UPDATE RECALC
  SET
    FINAL_BALANCE = INIT_BALANCE + P_DELTA
  WHERE
    ID = P_RECALC_ID;

END; -- FINALIZE_RECALC_SAVE

--------------------------------------------------------------------------------

/**
 * Returns balance for the data from item row.
 */
FUNCTION GET_ITEM_BALANCE (
  P_OPERATION NUMBER,
  P_AMOUNT    NUMBER,
  P_BALANCE   NUMBER
) RETURN NUMBER IS
  P_OPERCAT NUMBER;
  P_GEL NUMBER;
BEGIN

  SELECT OPERTPKEY INTO P_OPERCAT
  FROM BS.BILLOPERATION
  WHERE BILLOPERKEY = P_OPERATION;

  IF P_OPERCAT IN (3, 5)
  THEN
    P_GEL := -ABS(P_AMOUNT);
  ELSE
    P_GEL := P_AMOUNT;
  END IF;

  RETURN P_BALANCE + P_GEL;

END; -- GET_CURR_BALANCE

/**
 * Manage balance gaps, according to the debt agreement.
 */
PROCEDURE MANAGE_BALANCE_GAPS (
  P_RECALC_ID NUMBER,
  P_INTERVAL_ID NUMBER,
  P_DA_REC    BS.DEBT_AGREEMENT%ROWTYPE
) IS
  P_ITEM  NUMBER;
  P_DELTA NUMBER;
  P_INDEX NUMBER;
  P_CURR_BALANCE NUMBER;
  P_PREV_BALANCE NUMBER;
  P_ITEM_SEQ NUMBER;
  P_CUSTOMER NUMBER;
  P_ACCOUNT NUMBER;
  P_DATE DATE;
  P_INSERTED_ITEM_ID NUMBER;
  P_CANCELATION_DATE DATE;
BEGIN

--------------------------------------------------------------------------------
-- 1. find restructurization start
--------------------------------------------------------------------------------
P_ITEM := NULL;
P_DELTA := 0;
P_INDEX := 0;
FOR rec IN (
  SELECT ITEMKEY, BILLOPERKEY, AMOUNT, BALANCE FROM (
    SELECT ITEMKEY, BILLOPERKEY, AMOUNT, BALANCE FROM BS.ITEM
    WHERE CUSTKEY = P_DA_REC.ACCKEY AND
      ITEMKEY >= P_DA_REC.ITEMKEY
    ORDER BY ITEMKEY
  ) WHERE ROWNUM <= 5
) LOOP
  P_CURR_BALANCE := GET_ITEM_BALANCE(rec.BILLOPERKEY, rec.AMOUNT, rec.BALANCE);
  IF P_INDEX > 0
  THEN
    P_DELTA := rec.BALANCE - P_PREV_BALANCE;
    IF ABS(P_DELTA) >= 0.01
    THEN
      P_ITEM := rec.ITEMKEY;
      EXIT;
    END IF;
  END IF;
  P_PREV_BALANCE := P_CURR_BALANCE;
  P_INDEX := P_INDEX + 1;
END LOOP;

-- can not find or incorrect start
IF P_ITEM IS NULL OR P_DELTA > 0
THEN
  RETURN;
END IF;

--------------------------------------------------------------------------------
-- 2. insert debt restructurization record
--------------------------------------------------------------------------------
-- get customer and account
SELECT CUSTOMER, ACCOUNT INTO P_CUSTOMER, P_ACCOUNT
FROM RECALC WHERE ID = P_RECALC_ID;

-- get other parameters
SELECT
  SEQUENCE - 1, ITEM_DATE
INTO
  P_ITEM_SEQ, P_DATE
FROM RECALC_ITEM
WHERE
  INTERVAL_ID = P_INTERVAL_ID AND
  ITEM_ID = P_ITEM;

-- insert operation
RECALC_ITEM_INSERT (
  P_INTERVAL_ID, P_CUSTOMER, P_ACCOUNT, 217,
  0, 0, P_DELTA, -- reading, kwh, gel
  -1, P_DATE, P_DATE, NULL, NULL, -- cycle and dates
  1, 0, 15, -- meter
  'new_restruct',
  NULL, NULL, NULL,
  P_ITEM_SEQ, 0, 0,
  P_INSERTED_ITEM_ID
);
UPDATE RECALC_ITEM SET
  BALANCE = P_PREV_BALANCE
WHERE ID = P_INSERTED_ITEM_ID;

--------------------------------------------------------------------------------
-- 3. find restructurization end
--------------------------------------------------------------------------------
IF P_DA_REC.STATUS IN (0, 1) -- current of fully used
THEN
  RETURN;
END IF;

-- find debt cancelation date
SELECT MAX(PAYDATE) INTO P_CANCELATION_DATE
FROM BS.DEBT_SCHEDULE
WHERE AGREEMENTKEY = P_DA_REC.AGREEMENTKEY AND
  STATUS = 1;

-- cancelation date
IF P_CANCELATION_DATE IS NULL
THEN
  RETURN;
END IF;

-- find balance gap
P_ITEM := NULL;
P_DELTA := 0;
P_INDEX := 0;
P_PREV_BALANCE := 0;
FOR rec IN (
  SELECT ITEMKEY, BILLOPERKEY, AMOUNT, BALANCE FROM (
    SELECT ITEMKEY, BILLOPERKEY, AMOUNT, BALANCE FROM BS.ITEM
    WHERE CUSTKEY = P_DA_REC.ACCKEY AND
      ITEMDATE >= P_CANCELATION_DATE
    ORDER BY ITEMKEY
  ) WHERE ROWNUM <= 5
) LOOP
  P_CURR_BALANCE := GET_ITEM_BALANCE(rec.BILLOPERKEY, rec.AMOUNT, rec.BALANCE);
  IF P_INDEX > 0
  THEN
    P_DELTA := rec.BALANCE - P_PREV_BALANCE;
    IF ABS(P_DELTA) >= 0.01
    THEN
      P_ITEM := rec.ITEMKEY;
      EXIT;
    END IF;
  END IF;
  P_PREV_BALANCE := P_CURR_BALANCE;
  P_INDEX := P_INDEX + 1;
END LOOP;

-- can not find or incorrect start
IF P_ITEM IS NULL OR P_DELTA < 0
THEN
  RETURN;
END IF;

--------------------------------------------------------------------------------
-- 4. insert debt activation record
--------------------------------------------------------------------------------
-- get other parameters
SELECT
  SEQUENCE - 1, ITEM_DATE
INTO
  P_ITEM_SEQ, P_DATE
FROM RECALC_ITEM
WHERE
  INTERVAL_ID = P_INTERVAL_ID AND
  ITEM_ID = P_ITEM;

-- insert operation
RECALC_ITEM_INSERT (
  P_INTERVAL_ID, P_CUSTOMER, P_ACCOUNT, 218,
  0, 0, P_DELTA, -- reading, kwh, gel
  -1, P_DATE, P_DATE, NULL, NULL, -- cycle and dates
  1, 0, 15, -- meter
  'new_restruct',
  NULL, NULL, NULL,
  P_ITEM_SEQ, 0, 0,
  P_INSERTED_ITEM_ID
);
UPDATE RECALC_ITEM SET
  BALANCE = P_PREV_BALANCE
WHERE ID = P_INSERTED_ITEM_ID;

END; -- MANAGE_BALANCE_GAPS

/**
 * Manges balance gaps.
 */
PROCEDURE MANAGE_BALANCE_GAPS (
  P_RECALC_ID NUMBER,
  P_INTERVAL_ID NUMBER
) IS
  P_CUSTOMER NUMBER;
  P_ACCOUNT NUMBER;

  P_PREV_BALANCE NUMBER;
  P_CURR_BALANCE NUMBER;
  P_INDEX NUMBER;
  P_INSERTION_COUNT NUMBER;
  P_DELTA NUMBER;
  P_INSERTED_ITEM_ID NUMBER;
BEGIN

-- get customer and account
SELECT CUSTOMER, ACCOUNT INTO P_CUSTOMER, P_ACCOUNT
FROM RECALC
WHERE ID = P_RECALC_ID;

--------------------------------------------------------------------------------
-- 1. manage balance gaps from debt agreement table
--------------------------------------------------------------------------------
FOR rec IN (
  SELECT * FROM BS.DEBT_AGREEMENT
  WHERE ACCKEY = P_CUSTOMER
  ORDER BY AGREEMENTKEY
) LOOP
  MANAGE_BALANCE_GAPS(P_RECALC_ID, P_INTERVAL_ID, rec);
END LOOP;

--------------------------------------------------------------------------------
-- 2. manage the rest gaps
--------------------------------------------------------------------------------
P_INDEX := 0;
P_CURR_BALANCE := 0;
P_PREV_BALANCE := 0;
P_INSERTION_COUNT := 0;
FOR rec IN (
  SELECT * FROM RECALC_ITEM
  WHERE INTERVAL_ID = P_INTERVAL_ID
  ORDER BY SEQUENCE
) LOOP

P_CURR_BALANCE := GET_ITEM_BALANCE(rec.OPERATION_ID, rec.GEL, rec.BALANCE);

IF P_INDEX > 0 AND rec.ACCOUNT_ID = P_ACCOUNT
THEN
  P_DELTA := rec.BALANCE - P_PREV_BALANCE;
  IF ABS(P_DELTA) > 0.01
  THEN 
    -- insert operation
    RECALC_ITEM_INSERT (
      P_INTERVAL_ID, P_CUSTOMER, P_ACCOUNT, -300,
      0, 0, P_DELTA, -- reading, kwh, gel
      -1, rec.ITEM_DATE, rec.ITEM_DATE, NULL, NULL, -- cycle and dates
      1, 0, 15, -- meter
      'new_restruct',
      NULL, NULL, NULL,
      rec.SEQUENCE - 1 + P_INSERTION_COUNT, 0, 0,
      P_INSERTED_ITEM_ID
    );
    UPDATE RECALC_ITEM SET
      BALANCE = P_PREV_BALANCE
    WHERE
      ID = P_INSERTED_ITEM_ID;
    P_INSERTION_COUNT := P_INSERTION_COUNT + 1;
  END IF;
END IF;

P_INDEX := P_INDEX + 1;
P_PREV_BALANCE := P_CURR_BALANCE;

END LOOP;

END; -- MANAGE_BALANCE_GAPS, 2

--------------------------------------------------------------------------------

FUNCTION GET_VOUCHER_NUMBER (
  P_USER_ID     IN  NUMBER
) RETURN VARCHAR2 IS
  p_prefix VARCHAR2(10);
  p_last_sequence NUMBER;
BEGIN

  SELECT USER_NUMBER, LAST_SEQUENCE INTO p_prefix, p_last_sequence
  FROM USERS
  WHERE USER_ID = P_USER_ID;
  
  IF p_prefix IS NULL
  THEN
    RAISE_APPLICATION_ERROR(-20000, 'User number not defined!');
  END IF;
  
  UPDATE USERS SET LAST_SEQUENCE = p_last_sequence + 1
  WHERE USER_ID = P_USER_ID;
  
  RETURN p_prefix || TRIM( to_char(p_last_sequence + 1, '9999990000'));

END;

/**
 * Creates new recalculation.
 */
PROCEDURE RECALC_INSERT (
  P_CUSTOMER_ID IN  NUMBER,
  P_ACCOUNT_ID  IN  NUMBER,
  P_USER_ID     IN  NUMBER,
  P_DESCRIPTION IN  NVARCHAR2,
  --P_NUMBER      OUT NVARCHAR2,
  P_RECALC_ID   OUT NUMBER
) IS
  P_NUMBER VARCHAR2(100);
  P_INTERVAL_ID NUMBER;
  P_CYCLE_ID NUMBER;
  P_SEQUENCE NUMBER;
  p_meter_type NUMBER;
  p_meter_coeff NUMBER;
  p_meter_status NUMBER;
  p_this_account BOOLEAN;
  P_UNIT NUMBER;
  P_AMOUNT NUMBER;
  P_COUNT NUMBER;
  P_STATUS NUMBER;
  P_NEW_ITEM_KEY NUMBER;
  P_INIT_BALANCE NUMBER;
BEGIN

--------------------------------------------------------------------------------
-- #1. Create recalculation head
--------------------------------------------------------------------------------
SELECT BALANCE INTO P_INIT_BALANCE
FROM BS.CUSTOMER WHERE CUSTKEY=P_CUSTOMER_ID;
P_NUMBER := GET_VOUCHER_NUMBER(P_USER_ID);
INSERT INTO RECALC (
  RECALC_NUMBER, CUSTOMER, ACCOUNT, CREATE_DATE, DESCRIPTION, IS_CHANGED,
  INIT_BALANCE
) VALUES (
  P_NUMBER, P_CUSTOMER_ID, P_ACCOUNT_ID, SYSDATE, P_DESCRIPTION, 1,
  P_INIT_BALANCE
) RETURNING ID INTO P_RECALC_ID;

--------------------------------------------------------------------------------
-- #2. Create SYS_01 interval: full history
--------------------------------------------------------------------------------
INSERT INTO RECALC_INTERVAL (
  RECALC_ID, NAME, SEQUENCE, EDITABLE
) VALUES (
  P_RECALC_ID, 'sys_01', 0, 0
) RETURNING
  ID INTO P_INTERVAL_ID;
--------------------------------------------------------------------------------
-- #3. fill SYS_01 with data
--------------------------------------------------------------------------------
P_SEQUENCE := 0;
FOR rec IN (
  SELECT * FROM BS.ITEM
  WHERE CUSTKEY = P_CUSTOMER_ID
  ORDER BY
    ITEMKEY
) LOOP
  -- is this account?
  p_this_account := rec.ACCKEY = P_ACCOUNT_ID;
  -- check for subsidy recalculation parameters
  P_UNIT := NULL;
  P_AMOUNT := NULL;
  P_COUNT := NULL;
  IF p_this_account AND IS_RECALCULABLE_SUBSIDY(rec.BILLOPERKEY)
  THEN
  BEGIN
    IF NOT
      -- try to find information in billing database ...
      FIND_SUBSIDY_RECALCULATION(rec.ITEMKEY, P_UNIT, P_AMOUNT, P_COUNT)
    THEN
      -- ... when not found, then
      -- take defaults from operation properties
      SELECT
        UNIT1, AMOUNT1, 1 INTO P_UNIT, P_AMOUNT, P_COUNT
      FROM SUBSIDY_ATTACHMENT
      WHERE
        OPERATION = rec.BILLOPERKEY;
    END IF;
    IF NOT (ABS(NVL(P_AMOUNT, 0)) >= 0.01)
    THEN
      P_UNIT := NULL;
      P_AMOUNT := NULL;
      P_COUNT := NULL;
    END IF;
  END;
  END IF;
  -- get cycle ID
  IF (rec.SCHEDKEY IS NOT NULL AND rec.SCHEDKEY > 0) OR
    rec.BILLOPERKEY IN (9, 10, 11)
  THEN
    P_CYCLE_ID := 1;
  ELSE
    P_CYCLE_ID := -1;
  END IF;
  -- get meter parameters
  GET_METER_PROPERTIES(P_ACCOUNT_ID, rec.ITEMKEY, p_meter_type, p_meter_coeff,
    p_meter_status);
  -- get statuse
  P_STATUS := NULL;
  IF p_this_account
  THEN
    P_STATUS := STAT_ORIGINAL;
  ELSE
    P_STATUS := STAT_OTHERS;
  END IF;
  -- insert into ITEM
  INSERT INTO RECALC_ITEM (
    INTERVAL_ID, ITEM_ID,
    CUSTOMER_ID, ACCOUNT_ID, OPERATION_ID,
    STATUS, READING, KWH, GEL, BALANCE,
    CYCLE_ID, ITEM_DATE, ENTER_DATE, ITEM_NUMBER,
    METER_COEFF, METER_STATUS, METER_TYPE_ID, METER_ACCELERATE,
    SEQUENCE,
    ORIG_OPERATION_ID, ORIG_ITEM_DATE, ORIG_ENTER_DATE, ORIG_CYCLE_ID,
    ORIG_READING, ORIG_KWH, ORIG_GEL, ORIG_BALANCE,
    BALANCE_GAP, ORIG_BALANCE_GAP,
    LEAVE_KWH_UNCHANGED, 
    ATT_UNIT,      ATT_AMOUNT,      ATT_COUNT,
    ORIG_ATT_UNIT, ORIG_ATT_AMOUNT, ORIG_ATT_COUNT,
    ORIG_METER_COEFF
  ) VALUES (
    P_INTERVAL_ID, rec.ITEMKEY,
    rec.CUSTKEY, rec.ACCKEY, rec.BILLOPERKEY,
    P_STATUS, rec.READING, rec.KWT, rec.AMOUNT, rec.BALANCE,
    P_CYCLE_ID, rec.ITEMDATE, rec.ENTERDATE, rec.ITEMNUMBER,
    p_meter_coeff, p_meter_status, p_meter_type, 0,
    P_SEQUENCE,
    rec.BILLOPERKEY, rec.ITEMDATE, rec.ENTERDATE, P_CYCLE_ID,
    rec.READING, rec.KWT, rec.AMOUNT, rec.BALANCE,
    0, 0,
    0,
    -- XXX: only original values?
    --P_UNIT, P_AMOUNT, P_COUNT,
    NULL, NULL, NULL,
    P_UNIT, P_AMOUNT, P_COUNT,
    -- original meter coefficient
    p_meter_coeff
  ) RETURNING ID INTO P_NEW_ITEM_KEY;

  -- manage subaccount record
  IF rec.BILLOPERKEY = 38 AND P_STATUS = STAT_ORIGINAL
  THEN
    DECLARE
      P_ITEMNUMBER VARCHAR2(20) := rec.ITEMNUMBER;
      P_CHILD_ITEM NUMBER;
      P_CHILD_ACCKEY NUMBER;
      P_PREV_DATE DATE;
    BEGIN

      IF P_ITEMNUMBER IS NULL OR LENGTH(TRIM(P_ITEMNUMBER)) = 0
      THEN
        P_PREV_DATE := NULL;
      ELSE
        P_ITEMNUMBER := TRIM(P_ITEMNUMBER);
        IF LENGTH(P_ITEMNUMBER) > 3 AND SUBSTR(P_ITEMNUMBER, 1, 3) = 'sys'
        THEN
          P_CHILD_ITEM := TO_NUMBER(SUBSTR(P_ITEMNUMBER, 4, LENGTH(P_ITEMNUMBER) - 3));
        ELSE
          P_CHILD_ITEM := TO_NUMBER(P_ITEMNUMBER);
        END IF;

        SELECT ACCKEY INTO P_CHILD_ACCKEY
        FROM BS.ITEM WHERE ITEMKEY = P_CHILD_ITEM;

        BEGIN
          SELECT ITEMDATE INTO P_PREV_DATE FROM
          (
            SELECT ITEMDATE FROM BS.ITEM
            WHERE
              ITEMKEY < P_CHILD_ITEM AND
              ACCKEY = P_CHILD_ACCKEY AND
              BILLOPERKEY IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 50)
            ORDER BY ITEMKEY DESC
          ) WHERE ROWNUM = 1;
        EXCEPTION WHEN NO_DATA_FOUND THEN
          SELECT CREATEDATE INTO P_PREV_DATE
          FROM BS.ACCOUNT WHERE ACCKEY = P_CHILD_ACCKEY;
      END;

      UPDATE RECALC_ITEM SET
        SUB_ACCOUNT_ID = P_CHILD_ACCKEY,
        PREV_DATE = P_PREV_DATE
      WHERE ID = P_NEW_ITEM_KEY;

    END IF;

  END;
  END IF;

  -- increase counter
  P_SEQUENCE := P_SEQUENCE + 1;

END LOOP;

-- create default tariff history
CREATE_DEFAULT_TARIFF_HISTORY(P_RECALC_ID);

-- manage balance gaps
MANAGE_BALANCE_GAPS(P_RECALC_ID, P_INTERVAL_ID);

-- delete records with status 'other' 
-- and then correct sequences
DELETE FROM RECALC_ITEM
WHERE
  INTERVAL_ID = P_INTERVAL_ID AND
  STATUS = STAT_OTHERS;
P_SEQUENCE := 0;
FOR rec IN (
  SELECT ID FROM RECALC_ITEM
  WHERE INTERVAL_ID = P_INTERVAL_ID
  ORDER BY SEQUENCE
) LOOP
  UPDATE RECALC_ITEM SET SEQUENCE = P_SEQUENCE
  WHERE ID = rec.ID;
  P_SEQUENCE := P_SEQUENCE + 1;
END LOOP;

END; -- RECALCULATION_INSERT

/**
 * Update recalculation.
 */
PROCEDURE RECALC_UPDATE (
  P_RECALC_ID     NUMBER,
  P_RECALC_NUMBER NVARCHAR2,
  P_DESCRIPTION   NVARCHAR2,
  P_ADVISOR       NUMBER
) IS
BEGIN

-- should not be saved
ASSERT_NOT_SAVED(P_RECALC_ID);

UPDATE
  RECALC
SET
  RECALC_NUMBER = P_RECALC_NUMBER,
  DESCRIPTION = P_DESCRIPTION,
  ADVISOR = P_ADVISOR
WHERE ID = P_RECALC_ID;

END; -- RECALC_UPDATE

/**
 * Delete recalculation.
 */
PROCEDURE RECALC_DELETE (
  P_RECALC_ID IN NUMBER
) IS
BEGIN

-- should not be saved
ASSERT_NOT_SAVED(P_RECALC_ID);

DELETE RECALC WHERE ID = P_RECALC_ID;

END; -- RECALC_DELETE

--------------------------------------------------------------------------------

/**
 * validates intervals
 */
PROCEDURE VALIDATE_INTERVALS (
  P_RECALC_ID NUMBER
) IS
  P_COUNT NUMBER;
  P_INTERVAL_SEQ     NUMBER;
  P_SYS_INTERVAL_SEQ NUMBER;
  P_EDITABLE NUMBER;
  P_PREV_EDITABLE NUMBER;
  P_PREV_INTERVAL_ID NUMBER;
  P_PREV_MAX_SEQ NUMBER;
  P_CURR_INTERVAL_ID NUMBER;
  P_ITEM_SEQUENCE NUMBER;
  P_SYS_NAME VARCHAR(10);
BEGIN

P_PREV_EDITABLE := -1;
P_SYS_INTERVAL_SEQ := 0;
P_INTERVAL_SEQ := 0;
P_PREV_INTERVAL_ID := -1;
P_PREV_MAX_SEQ := 0;
FOR rec1 IN (
  SELECT * FROM RECALC_INTERVAL
  WHERE RECALC_ID = P_RECALC_ID
  ORDER BY SEQUENCE
) LOOP

  -- current interval ID
  P_CURR_INTERVAL_ID := rec1.ID;

  --count of items in this interval
  SELECT COUNT(ID) INTO P_COUNT
  FROM RECALC_ITEM WHERE INTERVAL_ID = P_CURR_INTERVAL_ID;

  -- when no item, then delete interval!
  IF NVL(P_COUNT, 0) = 0
  THEN
    DELETE RECALC_INTERVAL
    WHERE ID = P_CURR_INTERVAL_ID;
  -- validate this interval and items inside it
  ELSE
    -- get editable property
    SELECT EDITABLE INTO P_EDITABLE
    FROM RECALC_INTERVAL
    WHERE ID = rec1.ID;
    -- current and previous intervals should be merged!
    IF P_PREV_INTERVAL_ID != -1 AND --P_PREV_EDITABLE = 0 AND P_EDITABLE = 0
      NVL(P_PREV_EDITABLE, 0) = NVL(P_EDITABLE, 0)
    THEN
      -- merge
      UPDATE RECALC_ITEM
        SET
          INTERVAL_ID = P_PREV_INTERVAL_ID,
          SEQUENCE = SEQUENCE + P_PREV_MAX_SEQ + 1
      WHERE
        INTERVAL_ID = P_CURR_INTERVAL_ID;
      -- delete current interal
      DELETE RECALC_INTERVAL
      WHERE ID = P_CURR_INTERVAL_ID;
      -- reassign current interval ID
      P_CURR_INTERVAL_ID := P_PREV_INTERVAL_ID;
    ELSE
      -- write previous interval parameters
      P_PREV_INTERVAL_ID := P_CURR_INTERVAL_ID;
      P_PREV_EDITABLE := P_EDITABLE;
      -- set new sequence value
      UPDATE RECALC_INTERVAL
        SET SEQUENCE = P_INTERVAL_SEQ
      WHERE ID = P_CURR_INTERVAL_ID;
      P_INTERVAL_SEQ := P_INTERVAL_SEQ + 1;
      -- set system name for the NOT editable interval
      IF P_EDITABLE = 0
      THEN
        P_SYS_INTERVAL_SEQ := P_SYS_INTERVAL_SEQ + 1;
        IF P_SYS_INTERVAL_SEQ < 100
        THEN
          P_SYS_NAME := 'sys_' || to_char(P_SYS_INTERVAL_SEQ, '00');
        ELSE
          P_SYS_NAME := 'sys_' || to_char(P_SYS_INTERVAL_SEQ);
        END IF;
        UPDATE RECALC_INTERVAL
          SET NAME = P_SYS_NAME
        WHERE ID = P_CURR_INTERVAL_ID;
      END IF;
    END IF;
    -- update sequences
    P_ITEM_SEQUENCE := 0;
    FOR rec2 IN (
      SELECT * FROM RECALC_ITEM
      WHERE INTERVAL_ID = P_CURR_INTERVAL_ID
      ORDER BY SEQUENCE
    ) LOOP
      UPDATE RECALC_ITEM SET
        SEQUENCE = P_ITEM_SEQUENCE
      WHERE
        ID = rec2.ID;
      P_ITEM_SEQUENCE := P_ITEM_SEQUENCE + 1;
    END LOOP;
    -- update P_PREV_MAX_SEQ parameter
    P_PREV_MAX_SEQ := P_ITEM_SEQUENCE;
  END IF;

END LOOP;

END; -- VALIDATE_INTERVALS

/**
 * Recalculation interval creation.
 */
PROCEDURE RECALC_INTERVAL_CREATE (
  P_RECALC_ID   IN     NUMBER,
  P_RECALC_NAME IN     NVARCHAR2,
  P_ITEM1       IN     NUMBER,
  P_ITEM2       IN     NUMBER,
  P_INTERVAL_ID    OUT NUMBER
) IS
  P_INTERVAL_SEQUENCE  NUMBER;
  P_INTERVAL_SEQUENCE1 NUMBER;
  P_INTERVAL_SEQUENCE2 NUMBER;
  P_CUT_IS_EDITABLE    NUMBER;
  P_ITEM_SEQUENCE      NUMBER;
  P_CUT_INTERVAL_ID    NUMBER;
  P_INTERVAL_ID_PRE    NUMBER;
  P_INTERVAL_ID_POST   NUMBER;
  P_ITEM_SEQ_1 NUMBER;
  P_ITEM_SEQ_2 NUMBER;
BEGIN

-- should not be saved
ASSERT_NOT_SAVED(P_RECALC_ID);

--------------------------------------------------------------------------------
-- #1. check items
--------------------------------------------------------------------------------
SELECT SEQUENCE INTO P_ITEM_SEQ_1
FROM RECALC_ITEM WHERE ID = P_ITEM1;

SELECT SEQUENCE INTO P_ITEM_SEQ_2
FROM RECALC_ITEM WHERE ID = P_ITEM2;

IF P_ITEM_SEQ_1 > P_ITEM_SEQ_2 -- P_ITEM1 > P_ITEM2
THEN
  RAISE_APPLICATION_ERROR(-20000, 'RECALC_INTERVAL_CREATE: first item is greater');
END IF;

--------------------------------------------------------------------------------
-- #2. check intersection
--------------------------------------------------------------------------------

-- get first item's interval sequence
SELECT
  SEQUENCE, EDITABLE, ID INTO
  P_INTERVAL_SEQUENCE1, P_CUT_IS_EDITABLE, P_CUT_INTERVAL_ID
FROM
  RECALC_INTERVAL
WHERE
  ID = (SELECT INTERVAL_ID FROM RECALC_ITEM WHERE ID = P_ITEM1);

-- cut interval is editable: can not make cut
IF NVL(P_CUT_IS_EDITABLE, 0) != 0
THEN
  RAISE_APPLICATION_ERROR(-20000,
    'RECALC_INTERVAL_CREATE: can not make cut on editable interval');
END IF;

-- get second item's interval sequence
SELECT
  SEQUENCE INTO P_INTERVAL_SEQUENCE2
FROM
  RECALC_INTERVAL
WHERE
  ID = (SELECT INTERVAL_ID FROM RECALC_ITEM WHERE ID = P_ITEM2);

-- intersection!
IF P_INTERVAL_SEQUENCE1 != P_INTERVAL_SEQUENCE2
THEN
  RAISE_APPLICATION_ERROR(-20000,
    'RECALC_INTERVAL_CREATE: interval intersection');
END IF;

--------------------------------------------------------------------------------
-- #2. create interval head
--------------------------------------------------------------------------------
-- new interval sequence
P_INTERVAL_SEQUENCE := P_INTERVAL_SEQUENCE1 + 1;
-- make gap for intervals
UPDATE RECALC_INTERVAL
  SET SEQUENCE = SEQUENCE + 2
WHERE
  RECALC_ID = P_RECALC_ID AND
  SEQUENCE > P_INTERVAL_SEQUENCE - 1;
-- create new sequence
INSERT INTO RECALC_INTERVAL (
  RECALC_ID, NAME, SEQUENCE, EDITABLE
) VALUES (
  P_RECALC_ID, P_RECALC_NAME, P_INTERVAL_SEQUENCE, 1 -- editable!
) RETURNING
  ID INTO P_INTERVAL_ID;

--------------------------------------------------------------------------------
-- #3. temporary interval heads
--------------------------------------------------------------------------------
-- insert pre interval
INSERT INTO RECALC_INTERVAL (
  RECALC_ID, NAME, SEQUENCE, EDITABLE
) VALUES (
  P_RECALC_ID, 'temp_pre', P_INTERVAL_SEQUENCE - 1, 0 -- not editable!
) RETURNING
  ID INTO P_INTERVAL_ID_PRE;
-- insert post interval
INSERT INTO RECALC_INTERVAL (
  RECALC_ID, NAME, SEQUENCE, EDITABLE
) VALUES (
  P_RECALC_ID, 'temp_post', P_INTERVAL_SEQUENCE + 1, 0 -- not editable!
) RETURNING
  ID INTO P_INTERVAL_ID_POST;

--------------------------------------------------------------------------------
-- #4. loop over items
--------------------------------------------------------------------------------
P_ITEM_SEQUENCE := 0;
FOR rec IN (
  SELECT * FROM RECALC_ITEM
  WHERE
    INTERVAL_ID = P_CUT_INTERVAL_ID AND
    SEQUENCE BETWEEN P_ITEM_SEQ_1 AND P_ITEM_SEQ_2
  ORDER BY SEQUENCE
) LOOP
  -- reasign item
  UPDATE RECALC_ITEM
    SET
      INTERVAL_ID = P_INTERVAL_ID,
      SEQUENCE = P_ITEM_SEQUENCE
  WHERE
    ID = rec.ID;
  -- increase counter
  P_ITEM_SEQUENCE := P_ITEM_SEQUENCE + 1;
END LOOP; -- end item's loop

--------------------------------------------------------------------------------
-- #5. loop over pre items
--------------------------------------------------------------------------------
P_ITEM_SEQUENCE := 0;
FOR rec IN (
  SELECT * FROM RECALC_ITEM
  WHERE
    INTERVAL_ID = P_CUT_INTERVAL_ID AND
    SEQUENCE < P_ITEM_SEQ_1
  ORDER BY SEQUENCE
) LOOP
  -- reasign item
  UPDATE RECALC_ITEM
    SET
      INTERVAL_ID = P_INTERVAL_ID_PRE,
      SEQUENCE = P_ITEM_SEQUENCE
  WHERE
    ID = rec.ID;
  -- increase counter
  P_ITEM_SEQUENCE := P_ITEM_SEQUENCE + 1;
END LOOP; -- end item's loop

--------------------------------------------------------------------------------
-- #6. loop over post items
--------------------------------------------------------------------------------
P_ITEM_SEQUENCE := 0;
FOR rec IN (
  SELECT * FROM RECALC_ITEM
  WHERE
    INTERVAL_ID = P_CUT_INTERVAL_ID AND
    SEQUENCE > P_ITEM_SEQ_2
  ORDER BY SEQUENCE
) LOOP
  -- reasign item
  UPDATE RECALC_ITEM
    SET
      INTERVAL_ID = P_INTERVAL_ID_POST,
      SEQUENCE = P_ITEM_SEQUENCE
  WHERE
    ID = rec.ID AND
    INTERVAL_ID = P_CUT_INTERVAL_ID;
  -- increase counter
  P_ITEM_SEQUENCE := P_ITEM_SEQUENCE + 1;
END LOOP; -- end item's loop

--------------------------------------------------------------------------------
-- #7. validate intervals
--------------------------------------------------------------------------------
VALIDATE_INTERVALS(P_RECALC_ID);

END; -- RECALC_INTERVAL_INSERT

/**
 * Update recalculation interval.
 */
PROCEDURE RECALC_INTERVAL_UPDATE (
  P_INTERVAL_ID   IN NUMBER,
  P_INTERVAL_NAME IN NVARCHAR2,
  P_INITIAL_BALANCE IN NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_INTERVAL WHERE ID = P_INTERVAL_ID;
ASSERT_NOT_SAVED(P_RECALC_ID);

UPDATE
  RECALC_INTERVAL
SET
  NAME = P_INTERVAL_NAME,
  START_BALANCE = P_INITIAL_BALANCE
WHERE
  ID = P_INTERVAL_ID;

END; -- RECALC_INTERVAL_UPDATE

/**
 * Delete recalculation interval.
 */
PROCEDURE RECALC_INTERVAL_DELETE (
  P_INTERVAL_ID IN NUMBER
) IS
  P_RECALC_ID NUMBER;
  P_EDITABLE NUMBER;
BEGIN

SELECT RECALC_ID, EDITABLE INTO P_RECALC_ID, P_EDITABLE
FROM RECALC_INTERVAL
WHERE ID = P_INTERVAL_ID;

-- should not be saved
ASSERT_NOT_SAVED(P_RECALC_ID);

IF NVL(P_EDITABLE, 0) = 0
THEN
RAISE_APPLICATION_ERROR(-20000, 'RECALC_INTERVAL_DELETE: can not delete not editable interval');
END IF;

UPDATE RECALC_INTERVAL
SET EDITABLE = 0, NAME = 'temp_sys'
WHERE ID = P_INTERVAL_ID;

VALIDATE_INTERVALS(P_RECALC_ID);

END; -- RECALC_INTERVAL_DELETE

/**
 * Returns default balance for this interval.
 */
PROCEDURE GET_INTERVAL_DEFAULT_BALANCE (
  p_interval_id   NUMBER,
  p_start_balance OUT NUMBER
) IS
BEGIN

        SELECT
            BALANCE INTO p_start_balance
        FROM (
            SELECT
                BALANCE
            FROM
                RECALC_ITEM
            WHERE
                INTERVAL_ID = p_interval_id AND
                ITEM_ID != -1
            ORDER BY
                SEQUENCE
        ) WHERE ROWNUM = 1;

    EXCEPTION WHEN NO_DATA_FOUND
    THEN

        p_start_balance := 0;

END; -- GET_INTERVAL_DEFAULT_BALANCE

--------------------------------------------------------------------------------

/**
 * Inserts a new recalculation item into RECALC_ITEM table.
 */
PROCEDURE RECALC_ITEM_INSERT (
  p_interval_id   IN NUMBER,
  p_customer_id   IN NUMBER,
  p_account_id    IN NUMBER,
  p_operation_id  IN NUMBER,
  p_reading       IN NUMBER,
  p_kwh           IN NUMBER,
  p_gel           IN NUMBER,
  p_cycle         IN NUMBER,
  p_item_date     IN DATE,
  p_enter_date    IN DATE,
  p_curr_date     IN DATE,
  p_prev_date     IN DATE,
  p_meter_coeff   IN NUMBER,
  p_meter_status  IN NUMBER,
  p_meter_type_id IN NUMBER,
  p_item_number   IN NVARCHAR2,
  p_att_unit      IN NUMBER,
  p_att_count     IN NUMBER,
  p_att_amount    IN NUMBER,
  p_sequence      IN NUMBER,
  p_balance_gap   IN NUMBER,
  p_leave_kwh_unchanged IN  NUMBER,
  p_recalc_item_id      OUT NUMBER
) IS
p_producer_item_id NUMBER := -1;
p_status NUMBER := STAT_NEW;
p_balance NUMBER := 0;
p_count NUMBER;
P_RECALC_ID NUMBER;
BEGIN

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID FROM RECALC_INTERVAL
WHERE ID = p_interval_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

-- validate index
SELECT COUNT(*) INTO p_count
FROM RECALC_ITEM WHERE INTERVAL_ID = p_interval_id;
IF p_sequence >= p_count OR p_sequence < -1
THEN
  RAISE_APPLICATION_ERROR(-20000, 'RECALC_ITEM_INSERT: index out of range');
END IF;

-- adding a new record
INSERT INTO RECALC_ITEM (
  INTERVAL_ID, ITEM_ID, CUSTOMER_ID, ACCOUNT_ID,
  OPERATION_ID, STATUS, READING, KWH, GEL, BALANCE,
  CYCLE_ID, ITEM_DATE, ENTER_DATE, CURR_DATE, PREV_DATE,
  METER_COEFF, METER_STATUS, METER_TYPE_ID,
  ITEM_NUMBER, ATT_UNIT, ATT_COUNT, ATT_AMOUNT, SEQUENCE,
  BALANCE_GAP, LEAVE_KWH_UNCHANGED,
  ORIG_OPERATION_ID, ORIG_ITEM_DATE, ORIG_ENTER_DATE, ORIG_CYCLE_ID,
  ORIG_READING, ORIG_KWH, ORIG_GEL, ORIG_BALANCE, ORIG_BALANCE_GAP
) VALUES (
  p_interval_id, p_producer_item_id, p_customer_id, p_account_id,
  p_operation_id, p_status, p_reading, p_kwh, p_gel, p_balance,
  p_cycle, TRUNC(p_item_date), TRUNC(p_enter_date),
  TRUNC(p_curr_date), TRUNC(p_prev_date),
  p_meter_coeff, p_meter_status, p_meter_type_id,
  p_item_number, p_att_unit, p_att_count, p_att_amount, p_sequence  + 1,
  p_balance_gap, p_leave_kwh_unchanged,
  p_operation_id, TRUNC(p_item_date), TRUNC(p_enter_date), p_cycle,
  p_reading, p_kwh, p_gel, p_balance, p_balance_gap
) RETURNING ID INTO p_recalc_item_id;

-- updating sequence intervals
UPDATE
  RECALC_ITEM
SET
  SEQUENCE = SEQUENCE + 1
WHERE
  INTERVAL_ID = p_interval_id AND
  SEQUENCE >= p_sequence + 1 AND
  ID != p_recalc_item_id;

END; -- RECALC_ITEM_INSERT

/**
 * Updates recalculation item in RECALC_ITEM table.
 */
PROCEDURE RECALC_ITEM_UPDATE (
  p_recalc_item_id      NUMBER, -- 1
  p_operation_id        NUMBER,
  p_reading             NUMBER,
  p_kwh                 NUMBER,
  p_gel                 NUMBER, -- 5   
  p_cycle               NUMBER,
  p_item_date           DATE,
  p_enter_date          DATE,
  p_curr_date           DATE,   
  p_prev_date           DATE,   -- 10
  p_meter_coeff         NUMBER,
  p_meter_status        NUMBER,
  p_meter_type_id       NUMBER,
  p_att_unit            NUMBER,
  p_att_count           NUMBER, -- 15
  p_att_amount          NUMBER,
  p_balance_gap         NUMBER,
  p_leave_kwh_unchanged NUMBER
) IS
  P_RECALC_ID NUMBER;
  P_RECALC_ACCOUNT NUMBER;
  P_ITEM_ACCOUNT NUMBER;
  P_ITEM_STATUS NUMBER;
BEGIN

SELECT
  I.RECALC_ID, ITEM.ACCOUNT_ID, ITEM.STATUS
INTO
  P_RECALC_ID, P_ITEM_ACCOUNT, P_ITEM_STATUS
FROM
  RECALC_ITEM ITEM,
  RECALC_INTERVAL I
WHERE
  ITEM.INTERVAL_ID = I.ID AND
  ITEM.ID = p_recalc_item_id;

-- should not be saved
ASSERT_NOT_SAVED(P_RECALC_ID);

SELECT
  ACCOUNT
INTO
  P_RECALC_ACCOUNT
FROM
  RECALC
WHERE
  ID = P_RECALC_ID;

IF P_RECALC_ACCOUNT != P_ITEM_ACCOUNT
THEN
  RAISE_APPLICATION_ERROR(-20000, 'RECALC_ITEM_UPDATE: different account');
END IF;

UPDATE
  RECALC_ITEM
SET
  OPERATION_ID = p_operation_id,
  READING = p_reading,
  KWH = p_kwh,
  GEL = p_gel,
  CYCLE_ID = p_cycle,
  ITEM_DATE  = TRUNC(p_item_date),
  ENTER_DATE = TRUNC(p_enter_date),
  CURR_DATE  = TRUNC(p_curr_date),
  PREV_DATE  = TRUNC(p_prev_date),
  METER_COEFF = p_meter_coeff,
  METER_STATUS = p_meter_status,
  METER_TYPE_ID = p_meter_type_id,
  ATT_UNIT = p_att_unit,
  ATT_COUNT = p_att_count,
  ATT_AMOUNT = p_att_amount,
  BALANCE_GAP = p_balance_gap,
  LEAVE_KWH_UNCHANGED = p_leave_kwh_unchanged
WHERE
  ID = p_recalc_item_id;        

END; -- RECALC_ITEM_UPDATE

/**
 * Delete item.
 */
PROCEDURE RECALC_ITEM_DELETE (
  P_ITEM_ID NUMBER
) IS
  P_STATUS NUMBER;
  P_SEQUENCE NUMBER;
  P_INTERVAL_ID NUMBER;
  P_PRODUCER_ITEM_ID NUMBER;
  P_ITEM_NUMBER VARCHAR2(50);
  P_RECALC_ID NUMBER;
BEGIN

-- get item parameters
SELECT
  STATUS, SEQUENCE, INTERVAL_ID, ITEM_ID, ITEM_NUMBER
INTO
  P_STATUS, P_SEQUENCE, P_INTERVAL_ID, P_PRODUCER_ITEM_ID, P_ITEM_NUMBER
FROM
  RECALC_ITEM
WHERE
  ID = P_ITEM_ID;

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID FROM RECALC_INTERVAL
WHERE ID = p_interval_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

IF P_PRODUCER_ITEM_ID != -1
THEN
  RAISE_APPLICATION_ERROR(-20000,
    'RECALC_ITEM_DELETE: only NEW is allowed for delete');
END IF;

IF P_ITEM_NUMBER = 'new_restruct'
THEN
  RAISE_APPLICATION_ERROR(-20000,
    'RECALC_ITEM_DELETE: can not delete SYSTEM restructurization record');
END IF;

-- remove item
DELETE FROM RECALC_ITEM
WHERE ID = P_ITEM_ID;

-- update sequences
UPDATE RECALC_ITEM
SET SEQUENCE = SEQUENCE - 1
WHERE
  INTERVAL_ID = P_INTERVAL_ID AND
  SEQUENCE > P_SEQUENCE;

END; -- RECALC_ITEM_DELETE

/**
 * Enable/disable given item.
 */
PROCEDURE RECALC_ITEM_ENABLE (
  P_RECALC_ITEM_ID NUMBER,
  P_ENABLE         NUMBER
) IS
  P_ITEM_ACCOUNT NUMBER;
  P_RECALC_ACCOUNT NUMBER;
  P_NEW_STATUS NUMBER;
  P_ITEM_ID NUMBER;
  P_RECALC_ID NUMBER;
BEGIN

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_INTERVAL WHERE ID IN (
  SELECT INTERVAL_ID FROM RECALC_ITEM WHERE ID = P_RECALC_ITEM_ID
);
ASSERT_NOT_SAVED(P_RECALC_ID);

IF NVL(P_ENABLE, 0) = 1
THEN
  P_NEW_STATUS := STAT_DELETED;
ELSE

  SELECT
    I.ACCOUNT_ID, R.ACCOUNT, I.ITEM_ID
  INTO
    P_ITEM_ACCOUNT, P_RECALC_ACCOUNT, P_ITEM_ID
  FROM
    RECALC_ITEM I, RECALC_INTERVAL INTR, RECALC R
  WHERE
    I.INTERVAL_ID = INTR.ID AND
    INTR.RECALC_ID = R.ID AND
    I.ID = P_RECALC_ITEM_ID;

  IF P_ITEM_ACCOUNT != P_RECALC_ACCOUNT
  THEN
    P_NEW_STATUS := STAT_OTHERS;
  ELSIF P_ITEM_ID = -1
  THEN
    P_NEW_STATUS := STAT_NEW;
  ELSE
    P_NEW_STATUS := STAT_ORIGINAL;
  END IF;

END IF;

UPDATE RECALC_ITEM
SET STATUS = P_NEW_STATUS
WHERE ID = P_RECALC_ITEM_ID;

END; -- RECALC_ITEM_ENABLE

/**
 * Move recalculation item.
 */
PROCEDURE MOVE_RECALC_ITEM (
  P_RECALC_ITEM_ID IN NUMBER,
  P_DIRECTION      IN NUMBER -- 0 up; 1 down
) IS
  p_sequence NUMBER;
  p_sequence2 NUMBER;
  p_interval_id NUMBER;
  p_item_id NUMBER;
  p_count NUMBER;
  p_status NUMBER;
  P_RECALC_ID NUMBER;
BEGIN

-- getting item parameters
SELECT
  SEQUENCE, INTERVAL_ID, ITEM_ID, STATUS
INTO
  p_sequence, p_interval_id, p_item_id, p_status
FROM
  RECALC_ITEM
WHERE
  ID = p_recalc_item_id;
  
-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_INTERVAL WHERE ID = p_interval_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

-- raise error, when not newly created record
IF p_item_id != -1
THEN
  RAISE_APPLICATION_ERROR(-20000,
    'MOVE_RECALC_ITEM: only NEW is allowed for move');
END IF;

IF NVL(p_direction, 0) = 0
-- up
THEN
  -- can not move Up -- first record
  IF p_sequence = 0
  THEN
    RETURN;
  END IF;

-- down
ELSE

  SELECT
    COUNT(*) INTO p_count
  FROM
    RECALC_ITEM
  WHERE
    INTERVAL_ID = p_interval_id;

  -- can not move Down -- last record
  IF p_sequence >= p_count - 1
  THEN
    RETURN;
  END IF;

END IF;

-- determine destination sequence
IF NVL(p_direction, 0) = 0
THEN
  p_sequence2 := p_sequence - 1;
ELSE
  p_sequence2 := p_sequence + 1;
END IF;

-- move this item to destination
UPDATE
  RECALC_ITEM
SET
  SEQUENCE = p_sequence2
WHERE
  ID = p_recalc_item_id;

-- clear destination area
UPDATE
  RECALC_ITEM
SET
  SEQUENCE = p_sequence
WHERE
  ID != p_recalc_item_id AND
  SEQUENCE = p_sequence2 AND
  INTERVAL_ID = p_interval_id;

END; -- MOVE_RECALC_ITEM

/**
 * Locks KWH field for the item.
 */
PROCEDURE LOCK_KWH_RECALCULATION (
  P_ITEM_ID NUMBER,
  P_LOCK    NUMBER -- 0 - unlock, 1-lock
) IS
  P_NEW_LOCK NUMBER;
  P_RECALC_ID NUMBER;
BEGIN

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_INTERVAL WHERE ID IN (
  SELECT INTERVAL_ID FROM RECALC_ITEM WHERE ID = P_ITEM_ID
);
ASSERT_NOT_SAVED(P_RECALC_ID);

P_NEW_LOCK := NVL(P_LOCK, 0);
/*IF P_NEW_LOCK NOT IN (0, 1)
THEN
  P_NEW_LOCK := 0;
END IF;*/

UPDATE RECALC_ITEM
SET LEAVE_KWH_UNCHANGED = P_NEW_LOCK
WHERE ID = P_ITEM_ID;

END; -- LOCK_KWH_RECALCULATION

/**
 * Set the specified options for subsidy recalculation.
 */
PROCEDURE ITEM_SUBSIDY_ATT_UPDATE (
  P_ITEM_ID    NUMBER,
  P_ATT_UNIT   NUMBER,
  P_ATT_AMOUNT NUMBER,
  P_ATT_COUNT  NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_INTERVAL WHERE ID IN (
  SELECT INTERVAL_ID FROM RECALC_ITEM WHERE ID = P_ITEM_ID
);
ASSERT_NOT_SAVED(P_RECALC_ID);

UPDATE RECALC_ITEM
SET
  ATT_UNIT = P_ATT_UNIT,
  ATT_COUNT = P_ATT_COUNT,
  ATT_AMOUNT = P_ATT_AMOUNT
WHERE
  ID = P_ITEM_ID;

END; -- SET_ITEM_SUBSIDY_RECALC

/**
 * Update of Meter for recalculation item.
 */
PROCEDURE RECALC_ITEM_METER_UPDATE (
  P_ITEM_ID          NUMBER,
  P_METER_ID         NUMBER,
  P_METER_COEFF      NUMBER,
  P_METER_STATUS     NUMBER,
  P_METER_ACCELERATE NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_INTERVAL WHERE ID IN (
  SELECT INTERVAL_ID FROM RECALC_ITEM WHERE ID = P_ITEM_ID
);
ASSERT_NOT_SAVED(P_RECALC_ID);

UPDATE RECALC_ITEM
SET
  METER_TYPE_ID    = P_METER_ID,
  METER_COEFF      = P_METER_COEFF,
  METER_STATUS     = P_METER_STATUS,
  METER_ACCELERATE = P_METER_ACCELERATE
WHERE
  ID = P_ITEM_ID;

END; -- RECALC_ITEM_METER_UPDATE

/**
 * Restores item originals.
 */
PROCEDURE RECALC_ITEM_RESTORE_ORIGINALS (
  P_ITEM_ID NUMBER,
  P_OPTION  NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_INTERVAL WHERE ID IN (
  SELECT INTERVAL_ID FROM RECALC_ITEM WHERE ID = P_ITEM_ID
);
ASSERT_NOT_SAVED(P_RECALC_ID);

-- empty
IF P_OPTION IS NULL
THEN
  NULL;
-- operation
ELSIF P_OPTION = CHANGE_OPERATION
THEN
  UPDATE RECALC_ITEM SET OPERATION_ID = ORIG_OPERATION_ID
  WHERE ID = P_ITEM_ID;
-- item date
ELSIF P_OPTION = CHANGE_ITEMDATE
THEN
  UPDATE RECALC_ITEM SET ITEM_DATE = ORIG_ITEM_DATE
  WHERE ID = P_ITEM_ID;
-- enter date
ELSIF P_OPTION = CHANGE_ENTERDATE
THEN
  UPDATE RECALC_ITEM SET ENTER_DATE = ORIG_ENTER_DATE
  WHERE ID = P_ITEM_ID;
-- reading
ELSIF P_OPTION = CHANGE_READING
THEN
  UPDATE RECALC_ITEM SET READING = ORIG_READING
  WHERE ID = P_ITEM_ID;
-- kwh
ELSIF P_OPTION = CHANGE_KWH
THEN
  UPDATE RECALC_ITEM SET KWH = ORIG_KWH
  WHERE ID = P_ITEM_ID;
-- gel
ELSIF P_OPTION = CHANGE_GEL
THEN
  UPDATE RECALC_ITEM SET GEL = ORIG_GEL
  WHERE ID = P_ITEM_ID;
-- cycle
ELSIF P_OPTION = CHANGE_CYCLE
THEN
  UPDATE RECALC_ITEM SET CYCLE_ID = ORIG_CYCLE_ID
  WHERE ID = P_ITEM_ID;
END IF;

END; -- RECALC_ITEM_RESTORE_ORIGINALS

--------------------------------------------------------------------------------

/**
 * Inserts regular charge item.
 */
PROCEDURE REGULAR_ITEM_INSERT (
  P_RECALC_ID   NUMBER,
  P_START_DATE  DATE,
  P_END_DATE    DATE,
  P_OPERATION   NUMBER,
  P_ATT_UNIT    NUMBER,
  P_ATT_AMOUNT  NUMBER,
  P_ATT_AMOUNT2 NUMBER,
  P_ATT_COUNT   NUMBER,
  P_SEQUENCE    NUMBER,
  P_ITEM_ID    OUT NUMBER
) IS
  p_items_count NUMBER;
BEGIN

-- should not be saved
ASSERT_NOT_SAVED(P_RECALC_ID);

-- determine total count
SELECT
  COUNT(ID) INTO p_items_count
FROM
  RECALC_REGULAR
WHERE
  RECALC_ID = P_RECALC_ID;

-- check sequence ranges
IF NVL(P_SEQUENCE, 0) < -1 OR NVL(p_sequence, 0) > NVL(p_items_count, 0)
THEN
  RAISE_APPLICATION_ERROR(-20000, 'REGULAR_ITEM_INSERT: index out of range');
END IF;

-- inserting regular item
INSERT INTO RECALC_REGULAR (
  RECALC_ID, START_DATE, END_DATE,
  SEQUENCE, OPERATION_ID, ATT_UNIT,
  ATT_AMOUNT, ATT_COUNT, ATT_AMOUNT2
) VALUES (
  P_RECALC_ID,  TRUNC(P_START_DATE), TRUNC(P_END_DATE),
  p_sequence + 1, P_OPERATION, P_ATT_UNIT,
  P_ATT_AMOUNT, P_ATT_COUNT, P_ATT_AMOUNT2
) RETURNING ID INTO P_ITEM_ID;

-- update sequences
UPDATE
  RECALC_REGULAR
SET
  SEQUENCE = SEQUENCE + 1
WHERE
  SEQUENCE > p_sequence AND
  ID != P_ITEM_ID AND
  RECALC_ID = P_RECALC_ID;

END; -- REGULAR_ITEM_INSERT

/**
 * Regular item delete.
 */
PROCEDURE REGULAR_ITEM_DELETE (
  P_RECALC_ITEM_ID NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_REGULAR WHERE ID = P_RECALC_ITEM_ID;
ASSERT_NOT_SAVED(P_RECALC_ID);

DELETE RECALC_REGULAR
WHERE ID = P_RECALC_ITEM_ID;

END; -- REGULAR_ITEM_DELETE

/**
 * Update record in regulars table.
 */
PROCEDURE REGULAR_ITEM_UPDATE (
  P_ITEM_ID     NUMBER,
  P_START_DATE  DATE,
  P_END_DATE    DATE,
  P_OPERATION   NUMBER,
  P_ATT_UNIT    NUMBER,
  P_ATT_AMOUNT  NUMBER,
  P_ATT_AMOUNT2 NUMBER,
  P_ATT_COUNT   NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

-- should not be saved
SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_REGULAR WHERE ID = P_ITEM_ID;
ASSERT_NOT_SAVED(P_RECALC_ID);

UPDATE RECALC_REGULAR
SET
  START_DATE   = P_START_DATE,
  END_DATE     = P_END_DATE,
  OPERATION_ID = P_OPERATION,
  ATT_UNIT     = P_ATT_UNIT,
  ATT_AMOUNT   = P_ATT_AMOUNT,
  ATT_COUNT    = P_ATT_COUNT,
  ATT_AMOUNT2  = P_ATT_AMOUNT2
WHERE
  ID = P_ITEM_ID;

END; -- REGULAR_ITEM_UPDATE

/**
 * Moves regular item.
 */
PROCEDURE MOVE_REGULAR (
  p_item_id   NUMBER,
  p_direction NUMBER
) IS
  p_sequence  NUMBER;
  p_sequence2 NUMBER;
  p_count     NUMBER;
  p_recalc_id NUMBER;
BEGIN

-- getting item parameters
SELECT
  SEQUENCE, RECALC_ID
INTO
  p_sequence, p_recalc_id
FROM
  RECALC_REGULAR
WHERE
  ID = p_item_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

-- check move possibility
IF NVL(p_direction, 0) = 0

-- up
THEN
  -- can not move Up -- first record
  IF p_sequence = 0
  THEN
    RETURN;
   END IF;

-- down
ELSE

  SELECT COUNT(*) INTO p_count
  FROM RECALC_REGULAR
  WHERE RECALC_ID = p_recalc_id;

  -- can not move Down -- last record
  IF p_sequence >= p_count - 1
  THEN
    RETURN;
  END IF;

END IF;

-- determine destination sequence
IF NVL(p_direction, 0) = 0
THEN
  p_sequence2 := p_sequence - 1;
ELSE
  p_sequence2 := p_sequence + 1;
END IF;

-- update sequences

-- move this item to destination
UPDATE
  RECALC_REGULAR
SET
  SEQUENCE = p_sequence2
WHERE
  ID = p_item_id;

-- clear destination area
UPDATE
  RECALC_REGULAR
SET
  SEQUENCE = p_sequence
WHERE
  ID != p_item_id AND
  SEQUENCE = p_sequence2 AND
  RECALC_ID = p_recalc_id;

END; -- MOVE_REGULAR

--------------------------------------------------------------------------------

/**
 * Returns rooms count for the given date.
 */
FUNCTION GET_ROOMS_COUNT (
  p_recalc_id NUMBER,
  p_date      DATE
) RETURN NUMBER IS
  p_count NUMBER;
BEGIN

ASSERT_NOT_SAVED(P_RECALC_ID);

BEGIN

SELECT
  ROOM_COUNT INTO p_count
FROM
  RECALC_ROOMS
WHERE
  RECALC_ID = p_recalc_id AND
  p_date BETWEEN NVL(START_DATE, BS.BILL_MANAGER_2006.INFINITY_PAST) AND
    NVL(END_DATE, BS.BILL_MANAGER_2006.INFINITY_FUTURE) AND
  ROWNUM = 1;

EXCEPTION WHEN NO_DATA_FOUND
THEN

SELECT
  A.ROOMNUMBER INTO p_count
FROM
  BS.CUSTOMER C, BS.ADDRESS A
WHERE
  C.PREMISEKEY = A.PREMISEKEY AND
  C.CUSTKEY IN (SELECT CUSTOMER FROM RECALC WHERE ID = p_recalc_id);

END;

RETURN p_count;

END; -- GET_ROOMS_COUNT


PROCEDURE CREATE_ROOM_HISTORY_ITEM (
  p_recalc_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_room_count NUMBER,
  p_id         OUT NUMBER
) IS
BEGIN

ASSERT_NOT_SAVED(P_RECALC_ID);

INSERT INTO RECALC_ROOMS (
  START_DATE, END_DATE, ROOM_COUNT, RECALC_ID
) VALUES (
  p_start_date, p_end_date, ROUND(p_room_count), p_recalc_id
) RETURNING ID INTO p_id;

END; -- CREATE_ROOM_HISTORY_ITEM


PROCEDURE UPDATE_ROOM_HISTORY_ITEM (
  p_id            IN  NUMBER,
  p_start_date    IN  DATE,
  p_end_date      IN  DATE,
  p_room_count    IN  NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

SELECT RECALC_ID INTO P_RECALC_ID FROM RECALC_ROOMS
WHERE ID = p_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

  UPDATE
    RECALC_ROOMS
  SET
    START_DATE = p_start_date,
    END_DATE = p_end_date,
    ROOM_COUNT = ROUND(p_room_count)
  WHERE
    ID = p_id;

END; -- UPDATE_ROOM_HISTORY_ITEM

PROCEDURE DELETE_ROOM_HISTORY_ITEM (
  p_id    IN  NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

SELECT RECALC_ID INTO P_RECALC_ID FROM RECALC_ROOMS
WHERE ID = p_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

DELETE FROM
  RECALC_ROOMS
WHERE
  ID = p_id;

END; -- DELETE_ROOM_HISTORY_ITEM

--------------------------------------------------------------------------------

PROCEDURE CREATE_DEFAULT_INSTCP_HISTORY (
  p_recalc_id NUMBER
) IS
  p_account_id NUMBER;
  p_customer_id NUMBER;
  p_category_id NUMBER;
  p_is_common_customer BOOLEAN;
  p_acc_create_date DATE;
  p_start_date DATE;
  p_end_date DATE;
  p_sequence NUMBER;
BEGIN

ASSERT_NOT_SAVED(P_RECALC_ID);

        -- delete previous history
        DELETE RECALC_INSTCP WHERE RECALC_ID = p_recalc_id;

        -- getting account and customer information
        SELECT CUSTOMER, ACCOUNT INTO p_customer_id, p_account_id FROM RECALC
        WHERE ID = p_recalc_id;

        -- determine category
        SELECT CUSTCATKEY INTO p_category_id FROM BS.CUSTOMER
        WHERE CUSTKEY = p_customer_id;

        -- common customer category
        p_is_common_customer := p_category_id IN (1);

        -- do not calculate not common customers
        IF NOT p_is_common_customer
        THEN
            RAISE_APPLICATION_ERROR(-20000, 'CREATE_DEFAULT_INSTCP_HISTORY: ' ||
                'Can not create default ' ||
                'installed capacity history. This customer does not ' ||
                'belong to common category.');
        END IF;

        -- determine account creation date
        SELECT CREATEDATE INTO p_acc_create_date FROM BS.ACCOUNT
        WHERE ACCKEY = p_account_id;

        -- start/end dates
        p_start_date := TRUNC(p_acc_create_date);
        p_end_date := TRUNC(SYSDATE);

        -- create inst.capacity history from room count history
        IF p_start_date < MONTHLY_INSTCP_START_DATE
        THEN
            create_inst_cp_history_part1(p_recalc_id, p_sequence);
        END IF;

        -- create inst.capacity history on monthly basis
        IF p_end_date >= MONTHLY_INSTCP_START_DATE
        THEN
            IF p_sequence IS NULL
            THEN
                p_sequence := -1;
            END IF;
            create_inst_cp_history_part2(p_recalc_id, p_sequence);
        END IF;

END; -- CREATE_DEFAULT_INSTCP_HISTORY

PROCEDURE create_inst_cp_history_part1 (
  p_recalc_id    IN      NUMBER,
  p_last_sequence     OUT NUMBER
) IS
  p_room_count NUMBER;
  p_option NUMBER;
  p_inst_cp NUMBER;
  p_dates DATE_ARRAY;
  p_index NUMBER;
  p_d1 DATE;
  p_d2 DATE;
BEGIN

        -- initialize sequence
        p_last_sequence := -1;

        -- loop over all records
        FOR rec_1 IN (
            SELECT DISTINCT
                START_DATE, END_DATE
            FROM
                INST_CP_BEFORE_2003
            ORDER BY
                START_DATE
        ) LOOP

            -- initialize critical dates array
            p_dates := DATE_ARRAY();

            -- start date
            p_dates.EXTEND();
            p_dates(p_dates.COUNT) := TRUNC(rec_1.START_DATE);

            -- loop over rooms history
            FOR rec_2 IN (
                SELECT
                    START_DATE
                FROM
                    RECALC_ROOMS
                WHERE
                    NVL(START_DATE, BS.BILL_MANAGER_2006.INFINITY_PAST)
                        BETWEEN
                            rec_1.START_DATE AND
                            NVL(rec_1.END_DATE, MONTHLY_INSTCP_START_DATE - 1)
                        AND recalc_id = p_recalc_id
                ORDER BY
                    START_DATE
            ) LOOP
                p_dates.EXTEND();
                p_dates(p_dates.COUNT) := TRUNC(rec_2.START_DATE);
            END LOOP;

            -- loop over critical dates
            p_index := 1;
            WHILE p_index <= p_dates.COUNT
            LOOP

                -- first date
                p_d1 := p_dates(p_index);
                -- second date
                IF p_index = p_dates.COUNT
                THEN
                    p_d2 := NVL(rec_1.END_DATE, MONTHLY_INSTCP_START_DATE - 1);
                ELSE
                    p_d2 := p_dates(p_index + 1) - 1;
                END IF;

                -- get rooms count
                p_room_count := get_rooms_count(p_recalc_id, p_d2);
                IF p_room_count > 5
                THEN
                    p_room_count := 5;
                END IF;
                IF p_room_count < 1
                THEN
                    p_room_count := 1;
                END IF;

                -- get option
                IF p_d2 <= ROUND_ON_3_DAYS_END_DATE
                THEN
                    p_option := INSTCP_NORMALIZE_ON_PRVMNTH_R3;
                ELSE
                    p_option := INSTCP_NORMALIZE_ON_PREV_MONTH;
                END IF;

                -- get installed capacity items
                BEGIN
                SELECT
                    KWH INTO p_inst_cp
                FROM
                    INST_CP_BEFORE_2003
                WHERE
                    ROOM_COUNT = p_room_count AND
                    p_d2 BETWEEN
                        NVL(START_DATE, BS.BILL_MANAGER_2006.INFINITY_PAST) AND
                        NVL(END_DATE, BS.BILL_MANAGER_2006.INFINITY_FUTURE) AND
                    ROWNUM = 1;
                EXCEPTION WHEN NO_DATA_FOUND
                THEN
                    p_inst_cp := 0;
                END;

                -- insert new item
                p_last_sequence := p_last_sequence + 1;
                INSERT INTO RECALC_INSTCP (
                    START_DATE, END_DATE, AMOUNT, RECALC_OPTION,
                    RECALC_ID, SEQUENCE
                ) VALUES (
                    p_d1, p_d2, p_inst_cp, p_option,
                    p_recalc_id, p_last_sequence
                );

                -- increase counter
                p_index := p_index + 1;

            END LOOP;

        END LOOP;

END; -- create_inst_cp_history_part1

PROCEDURE create_inst_cp_history_part2 (
  p_recalc_id            NUMBER,
  p_last_sequence_from_p1 NUMBER
) IS
  p_sequence NUMBER := p_last_sequence_from_p1 + 1;
  p_d1 DATE := MONTHLY_INSTCP_START_DATE;
  p_d2 DATE;
  p_year NUMBER;
  p_month NUMBER;
  p_inst_cp NUMBER;
  p_option NUMBER;
BEGIN

        WHILE p_d1 <= TRUNC(SYSDATE)
        LOOP

            -- second date
            p_d2 := LAST_DAY(p_d1);

            -- get inst capacity for this month
            p_year := EXTRACT(YEAR FROM p_d1);
            p_month := EXTRACT(MONTH FROM p_d1);

            SELECT
                INST_CP INTO p_inst_cp
            FROM
                INST_CP_AFTER_2003
            WHERE
                YEAR = p_year AND
                MONTH = p_month;

            IF p_d2 >= STEP_TARIFF_START_DATE
            THEN
                p_option := INSTCP_NORMALIZE_ON_30_DAYS;
            ELSE
                p_option := INSTCP_NORMALIZE_ON_PREV_MONTH;
            END IF;

            INSERT INTO RECALC_INSTCP(
                START_DATE, END_DATE, AMOUNT, RECALC_OPTION, RECALC_ID,
                SEQUENCE
            ) VALUES (
                p_d1, p_d2, p_inst_cp, p_option, p_recalc_id,
                p_sequence
            );

            -- prepare next step
            p_sequence := p_sequence + 1;
            p_d1 := p_d2 + 1;

        END LOOP;

END; -- create_inst_cp_history_part2

/**
 * Add a new Installed Capacity item insert.
 */
PROCEDURE INSTCP_ITEM_INSERT (
  p_recalc_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_amount     NUMBER,
  p_option     NUMBER,
  p_sequence   NUMBER,
  p_instcp_id OUT NUMBER
) IS
  p_items_count NUMBER;
BEGIN

ASSERT_NOT_SAVED(P_RECALC_ID);

-- calculate items count
SELECT COUNT(*) INTO p_items_count
FROM RECALC_INSTCP
WHERE RECALC_ID = p_recalc_id;

-- check sequence value
IF NVL(p_sequence, -1) < -1 OR NVL(p_sequence, -1) > NVL(p_items_count, 0)
THEN
  RAISE_APPLICATION_ERROR(-20000, 'INSTCP_INSERT: index out of range.');
END IF;

-- add new item
INSERT INTO RECALC_INSTCP (
  START_DATE, END_DATE, AMOUNT, RECALC_OPTION, RECALC_ID, SEQUENCE
) VALUES (
  TRUNC(p_start_date), TRUNC(p_end_date), p_amount, p_option,
  p_recalc_id, p_sequence + 1
) RETURNING ID INTO p_instcp_id;

-- update sequences
UPDATE RECALC_INSTCP
SET  SEQUENCE = SEQUENCE + 1
WHERE
  SEQUENCE > p_sequence AND
  ID != p_instcp_id AND
  RECALC_ID = p_recalc_id;

END; -- create_instcp_history_item

/**
 * Delete Installed Capacity item.
 */
PROCEDURE INSTCP_ITEM_DELETE (
  p_instcp_id NUMBER
) IS
  p_sequence NUMBER;
  p_recalc_id NUMBER;
BEGIN

-- get sequence for this inst.capacity history record
SELECT SEQUENCE, RECALC_ID INTO p_sequence, p_recalc_id
FROM RECALC_INSTCP 
WHERE ID = p_instcp_id;

ASSERT_NOT_SAVED(P_RECALC_ID);

-- delete record
DELETE RECALC_INSTCP
WHERE ID = p_instcp_id;

-- update voucher tariffs sequences
UPDATE RECALC_INSTCP
SET SEQUENCE = SEQUENCE - 1
WHERE
  SEQUENCE > p_sequence AND
  RECALC_ID = p_recalc_id;

END; -- INSTCP_DELETE

/**
 * Installed capacity item update.
 */
PROCEDURE INSTCP_ITEM_UPDATE (
  p_item_id NUMBER,
  p_start_date DATE,
  p_end_date DATE,
  p_amount NUMBER,
  p_option NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

SELECT RECALC_ID INTO P_RECALC_ID FROM RECALC_INSTCP
WHERE ID = p_item_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

  UPDATE
    RECALC_INSTCP
  SET
    START_DATE = TRUNC(p_start_date),
    END_DATE = TRUNC(p_end_date),
    AMOUNT = p_amount,
    RECALC_OPTION = p_option
  WHERE
    ID = p_item_id;

END; -- INSTCP_ITEM_UPDATE


/**
 * Move installed capacity item move.
 */
PROCEDURE INSTCP_ITEM_MOVE (
  p_item_id   NUMBER,
  p_direction NUMBER -- 0 up; 1 down
) IS
        p_sequence NUMBER;
        p_sequence2 NUMBER;
        p_count NUMBER;
        p_recalc_id NUMBER;
BEGIN

        -- getting this item parameters

        SELECT
            SEQUENCE, RECALC_ID
        INTO
            p_sequence, p_recalc_id
        FROM
            RECALC_INSTCP
        WHERE
            ID = p_item_id;

ASSERT_NOT_SAVED(P_RECALC_ID);

        -- check move possibility

        IF NVL(p_direction, 0) = 0
        -- up
        THEN
            -- can not move Up -- first record
            IF p_sequence = 0
            THEN
                RETURN;
            END IF;
        -- down
        ELSE
            SELECT
                COUNT(*) INTO p_count
            FROM
                RECALC_INSTCP
            WHERE
                RECALC_ID = p_recalc_id;

            -- can not move Down -- last record
            IF p_sequence >= p_count - 1
            THEN
                RETURN;
            END IF;

        END IF;

        -- determine destination sequence

        IF NVL(p_direction, 0) = 0
        THEN
            p_sequence2 := p_sequence - 1;
        ELSE
            p_sequence2 := p_sequence + 1;
        END IF;

        -- update sequences

        -- move this item to destination

        UPDATE
            RECALC_INSTCP
        SET
            SEQUENCE = p_sequence2
        WHERE
            ID = p_item_id;

        -- clear destination area

        UPDATE
            RECALC_INSTCP
        SET
            SEQUENCE = p_sequence
        WHERE
            ID != p_item_id AND
            SEQUENCE = p_sequence2 AND
            RECALC_ID = p_recalc_id;

END; -- move_instcp_history_item

--------------------------------------------------------------------------------

/**
 * Creates default tariff history.
 */
PROCEDURE CREATE_DEFAULT_TARIFF_HISTORY (
  p_recalc_id NUMBER
) IS

  p_account_id NUMBER;
  p_sequence NUMBER := 0;
  p_customer_cat_id NUMBER;

BEGIN

ASSERT_NOT_SAVED(P_RECALC_ID);

-- clear old history
DELETE FROM RECALC_TARIFFS
WHERE RECALC_ID = p_recalc_id;

-- get customer
SELECT
  R.ACCOUNT, C.CUSTCATKEY
INTO
  p_account_id, p_customer_cat_id
FROM
  RECALC R, BS.CUSTOMER C
WHERE
  ID = p_recalc_id AND
  C.CUSTKEY = R.CUSTOMER;

-- retrive tariff history
FOR rec IN (
          SELECT
                T.COMPKEY, T.STARTDATE, T.ENDDATE
            FROM
                BS.ACCTARIFFS T, BS.TARCOMP C
            WHERE
                C.COMPKEY = T.COMPKEY AND
                T.ACCKEY = p_account_id AND
                C.BASECOMPKEY = 1
            ORDER BY
                T.ACCTARKEY
) LOOP

            IF p_customer_cat_id = 1 AND (
                MISSED_TARIFF_CHANGE_2000 BETWEEN
                    NVL(rec.STARTDATE, BS.BILL_MANAGER_2006.INFINITY_PAST) AND
                    NVL(rec.ENDDATE, BS.BILL_MANAGER_2006.INFINITY_FUTURE)
            ) THEN

                INSERT INTO RECALC_TARIFFS (
                    RECALC_ID, TARIFF_ID, START_DATE, END_DATE,
                    SEQUENCE
                ) VALUES (
                    p_recalc_id, rec.COMPKEY, TRUNC(rec.STARTDATE),
                    MISSED_TARIFF_CHANGE_2000 - 1, p_sequence
                );

                p_sequence := p_sequence + 1;

                INSERT INTO RECALC_TARIFFS (
                    RECALC_ID, TARIFF_ID, START_DATE, END_DATE,
                    SEQUENCE
                ) VALUES (
                    p_recalc_id, MISSED_TARIFF_ID_2000, MISSED_TARIFF_CHANGE_2000,
                    TRUNC(rec.ENDDATE), p_sequence
                );

            ELSE

                -- adding common record

                INSERT INTO RECALC_TARIFFS (
                    RECALC_ID, TARIFF_ID, START_DATE, END_DATE,
                    SEQUENCE
                ) VALUES (
                    p_recalc_id, rec.COMPKEY, TRUNC(rec.STARTDATE),
                    TRUNC(rec.ENDDATE), p_sequence
                );

            END IF;

            p_sequence := p_sequence + 1;

END LOOP;

END; -- CREATE_DEFAULT_TARIFF_HISTORY

PROCEDURE TARIFF_ITEM_INSERT (
  p_recalc_id  NUMBER,
  p_tariff_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_sequence   NUMBER,
  p_item_id    OUT NUMBER
) IS
        p_items_count NUMBER;
BEGIN

ASSERT_NOT_SAVED(P_RECALC_ID);

        -- determine count of the tariff items for this voucher
        SELECT
            COUNT(*) INTO p_items_count
        FROM
            RECALC_TARIFFS
        WHERE
            RECALC_ID = p_recalc_id;

        -- check sequence ranges
        IF NVL(p_sequence, 0) < -1 OR NVL(p_sequence, 0) > NVL(p_items_count, 0)
        THEN
            RAISE_APPLICATION_ERROR(-20000,
              'CREATE_TARIFF_ITEM: index out of range.');
        END IF;

        -- inserting item
        INSERT INTO RECALC_TARIFFS (
            RECALC_ID, TARIFF_ID, START_DATE, END_DATE, SEQUENCE
        ) VALUES (
            p_recalc_id, p_tariff_id, TRUNC(p_start_date), TRUNC(p_end_date),
            p_sequence + 1
        ) RETURNING ID INTO p_item_id;

        -- update sequences
        UPDATE
            RECALC_TARIFFS
        SET
            SEQUENCE = SEQUENCE + 1
        WHERE
            SEQUENCE > p_sequence AND
            ID != p_item_id AND
            RECALC_ID = p_recalc_id;

END; -- create_tariff_history_item

PROCEDURE TARIFF_ITEM_UPDATE (
  p_item_id    NUMBER,
  p_tariff_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE
) IS
  P_RECALC_ID NUMBER;
BEGIN

SELECT RECALC_ID INTO P_RECALC_ID FROM RECALC_TARIFFS
WHERE ID = p_item_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

        UPDATE
            RECALC_TARIFFS
        SET
            TARIFF_ID = p_tariff_id,
            START_DATE = TRUNC(p_start_date),
            END_DATE = TRUNC(p_end_date)
        WHERE
            ID = p_item_id;

END; -- UPDATE_TARIFF_ITEM

PROCEDURE TARIFF_ITEM_DELETE (
  p_item_id NUMBER
) IS
  p_sequence NUMBER;
  p_recalc_id NUMBER;
BEGIN

        SELECT
            SEQUENCE, RECALC_ID INTO p_sequence, p_recalc_id
        FROM
            RECALC_TARIFFS
        WHERE
            ID = p_item_id;

ASSERT_NOT_SAVED(P_RECALC_ID);

        DELETE
            RECALC_TARIFFS
        WHERE
            ID = p_item_id;

        UPDATE
            RECALC_TARIFFS
        SET
            SEQUENCE = SEQUENCE - 1
        WHERE
            SEQUENCE > p_sequence AND
            RECALC_ID = p_recalc_id;

END; -- DELETE_TARIFF_ITEM

PROCEDURE TARIFF_ITEM_MOVE (
  p_item_id   NUMBER,
  p_direction NUMBER -- 0 up; 1 down
) IS
        p_sequence NUMBER;
        p_sequence2 NUMBER;
        p_count NUMBER;
        p_recalc_id NUMBER;
BEGIN

        SELECT
            SEQUENCE, RECALC_ID
        INTO
            p_sequence, p_recalc_id
        FROM
            RECALC_TARIFFS
        WHERE
            ID = p_item_id;

ASSERT_NOT_SAVED(P_RECALC_ID);

        IF NVL(p_direction, 0) = 0
        -- up
        THEN

            -- can not move Up -- first record
            IF p_sequence = 0
            THEN
                RETURN;
            END IF;

        -- down
        ELSE

            SELECT
                COUNT(*) INTO p_count
            FROM
                RECALC_TARIFFS
            WHERE
                RECALC_ID = p_recalc_id;

            -- can not move Down -- last record
            IF p_sequence >= p_count - 1
            THEN

                RETURN;

            END IF;

        END IF;

        -- determine destination sequence
        IF NVL(p_direction, 0) = 0
        THEN
            p_sequence2 := p_sequence - 1;
        ELSE
            p_sequence2 := p_sequence + 1;
        END IF;

        -- update sequences

        -- move this item to destination
        UPDATE
            RECALC_TARIFFS
        SET
            SEQUENCE = p_sequence2
        WHERE
            ID = p_item_id;

        -- clear destination area
        UPDATE
            RECALC_TARIFFS
        SET
            SEQUENCE = p_sequence
        WHERE
            ID != p_item_id AND
            SEQUENCE = p_sequence2 AND
            RECALC_ID = p_recalc_id;

END; -- MOVE_TARIFF_ITEM

--------------------------------------------------------------------------------

/**
 * Looks up for the default recalculation parameters for the
 * subsidy/additional charge item.
 * Returns TRUE when this look up gives results, FALSE otherwise.
 */
FUNCTION FIND_SUBSIDY_RECALCULATION (
  P_ITEM_ID    IN  NUMBER,
  P_ATT_UNIT   OUT NUMBER,
  P_ATT_AMOUNT OUT NUMBER,
  P_ATT_COUNT  OUT NUMBER
) RETURN BOOLEAN IS 
BEGIN

SELECT
  D.UNITTYPEKEY ATT_UNIT,
  D.UNITVALUE   ATT_AMOUNT,
  D.UNITNUMB    ATT_COUNT
INTO
  P_ATT_UNIT,
  P_ATT_AMOUNT,
  P_ATT_COUNT
FROM
  BS.BILLDETAILS D, BS.ITEM I
WHERE
  I.ITEMKEY = P_ITEM_ID AND
  I.ACCKEY = D.ACCKEY AND
  D.BILLOPERKEY = I.BILLOPERKEY AND
  (
    (D.ACTIVE = 1 AND I.ITEMDATE BETWEEN D.STARTDATE AND D.ENDDATE)
  OR
    (D.ACTIVE = 0)
  ) AND
  ROWNUM = 1;

RETURN TRUE;

EXCEPTION
WHEN NO_DATA_FOUND
THEN

  P_ATT_UNIT   := NULL;
  P_ATT_AMOUNT := NULL;
  P_ATT_COUNT  := NULL;

  RETURN FALSE;

END; -- FIND_SUBSIDY_RECALCULATION

--------------------------------------------------------------------------------

/**
 * Adds cut history item.
 */
PROCEDURE CUT_ITEM_INSERT (
  p_recalc_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_sequence   NUMBER,
  p_item_id    OUT NUMBER
) IS
  p_items_count NUMBER;
BEGIN

ASSERT_NOT_SAVED(P_RECALC_ID);

        -- determine count of the cut items for this voucher
        SELECT
            COUNT(*) INTO p_items_count
        FROM
            RECALC_CUT
        WHERE
            RECALC_ID = p_recalc_id;

        -- check sequence ranges
        IF NVL(p_sequence, 0) < -1 OR NVL(p_sequence, 0) > NVL(p_items_count, 0)
        THEN
            RAISE_APPLICATION_ERROR(-20000, 'CUT_ITEM_INSERT: index out of range');
        END IF;

        -- inserting cut item
        INSERT INTO RECALC_CUT (
            RECALC_ID, START_DATE, END_DATE, SEQUENCE
        ) VALUES (
            p_recalc_id,  TRUNC(p_start_date), TRUNC(p_end_date),
            p_sequence + 1
        ) RETURNING ID INTO p_item_id;

        -- update sequences

        UPDATE
            RECALC_CUT
        SET
            SEQUENCE = SEQUENCE + 1
        WHERE
            SEQUENCE > p_sequence AND
            ID != p_item_id AND
            RECALC_ID = p_recalc_id;

END; -- CUT_ITEM_INSERT

/**
 * Cut item update.
 */
PROCEDURE CUT_ITEM_UPDATE (
  p_item_id    NUMBER,
  p_start_date DATE,
  p_end_date   DATE
) IS
  P_RECALC_ID NUMBER;
BEGIN

SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_CUT WHERE ID = p_item_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

        UPDATE
            RECALC_CUT
        SET
            START_DATE = TRUNC(p_start_date),
            END_DATE = TRUNC(p_end_date)
        WHERE
            ID = p_item_id;

END; -- CUT_ITEM_UPDATE

/**
 * Delete cut item.
 */
PROCEDURE CUT_ITEM_DELETE (
  p_cut_id NUMBER
) IS
  p_sequence NUMBER;
  p_recalc_id NUMBER;
BEGIN

        SELECT
            SEQUENCE, RECALC_ID INTO p_sequence, p_recalc_id
        FROM
            RECALC_CUT
        WHERE
            ID = p_cut_id;

ASSERT_NOT_SAVED(P_RECALC_ID);

        DELETE
            RECALC_CUT
        WHERE
            ID = p_cut_id;

        UPDATE
            RECALC_CUT
        SET
            SEQUENCE = SEQUENCE - 1
        WHERE
            SEQUENCE > p_sequence AND
            RECALC_ID = p_recalc_id;

END; -- CUT_ITEM_DELETE

/**
 * Move cut item.
 */
PROCEDURE CUT_ITEM_MOVE (
  p_cut_item_id NUMBER,
  p_direction   NUMBER
) IS
  p_sequence NUMBER;
  p_sequence2 NUMBER;
  p_count NUMBER;
  p_recalc_id NUMBER;
BEGIN
    
        -- getting this item parameters
        SELECT
            SEQUENCE, RECALC_ID
        INTO
            p_sequence, p_recalc_id
        FROM
            RECALC_CUT
        WHERE
            ID = p_cut_item_id;

ASSERT_NOT_SAVED(P_RECALC_ID);

        -- check move possibility
        IF NVL(p_direction, 0) = 0
        -- up
        THEN
            -- can not move Up -- first record
            IF p_sequence = 0
            THEN
                RETURN;
            END IF;
        -- down
        ELSE
            SELECT
                COUNT(*) INTO p_count
            FROM
                RECALC_CUT
            WHERE
                RECALC_ID = p_recalc_id;
            -- can not move Down -- last record
            IF p_sequence >= p_count - 1
            THEN
                RETURN;
            END IF;
        END IF;

        -- determine destination sequence
        IF NVL(p_direction, 0) = 0
        THEN
            p_sequence2 := p_sequence - 1;
        ELSE
            p_sequence2 := p_sequence + 1;
        END IF;

        -- update sequences

        -- move this item to destination
        UPDATE
            RECALC_CUT
        SET
            SEQUENCE = p_sequence2
        WHERE
            ID = p_cut_item_id;

        -- clear destination area
        UPDATE
            RECALC_CUT
        SET
            SEQUENCE = p_sequence
        WHERE
            ID != p_cut_item_id AND
            SEQUENCE = p_sequence2 AND
            RECALC_ID = p_recalc_id;

END; -- CUT_ITEM_MOVE

--------------------------------------------------------------------------------

PROCEDURE ROOM_ITEM_INSERT (
  p_recalc_id  NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_room_count NUMBER,
  p_id OUT NUMBER
) IS
BEGIN

ASSERT_NOT_SAVED(P_RECALC_ID);
        INSERT INTO RECALC_ROOMS (
            START_DATE, END_DATE, ROOM_COUNT, RECALC_ID
        ) VALUES (
            p_start_date, p_end_date, ROUND(p_room_count), p_recalc_id
        ) RETURNING ID INTO p_id;

END; -- ROOM_ITEM_INSERT

PROCEDURE ROOM_ITEM_UPDATE (
  p_id         NUMBER,
  p_start_date DATE,
  p_end_date   DATE,
  p_room_count NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_ROOMS WHERE ID = p_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

        UPDATE
            RECALC_ROOMS
        SET
            START_DATE = p_start_date,
            END_DATE = p_end_date,
            ROOM_COUNT = ROUND(p_room_count)
        WHERE
            ID = p_id;

END; -- ROOM_ITEM_UPDATE

PROCEDURE ROOM_ITEM_DELETE (
  p_id NUMBER
) IS
  P_RECALC_ID NUMBER;
BEGIN

SELECT RECALC_ID INTO P_RECALC_ID
FROM RECALC_ROOMS WHERE ID = p_id;
ASSERT_NOT_SAVED(P_RECALC_ID);

        DELETE FROM
            RECALC_ROOMS
        WHERE
            ID = p_id;

END; -- ROOM_ITEM_DELETE

--------------------------------------------------------------------------------

/**
 * Is given operation recalculable as subdiy or additional charge?
 */
FUNCTION IS_RECALCULABLE_SUBSIDY (
  OPERATION_ID NUMBER
) RETURN BOOLEAN
IS
BEGIN

/*

NOTE: The list below is obtained from the query

  SELECT
    OPERATION
  FROM
    RECUT.SUBSIDY_ATTACHMENT
  WHERE
    UNIT1 IS NOT NULL
  ORDER BY
    OPERATION

We are not using this query here while it may be too slow when executing.

*/
RETURN OPERATION_ID IN (21,22,25,26,27,28,29,32,34,36,37,61,67,87,121,135,
  136,137,138,140,141,158,159,515);

END; -- IS_RECALCULABLE_SUBSIDY

/**
 * Returns default start balance for the given interval.
 */
PROCEDURE get_interval_defaul_balance (
  p_interval_id   IN     NUMBER,
  p_start_balance    OUT NUMBER
) IS
BEGIN

        SELECT
            BALANCE INTO p_start_balance
        FROM (
            SELECT
                BALANCE
            FROM
                RECALC_ITEM
            WHERE
                INTERVAL_ID = p_interval_id AND
                ITEM_ID != -1
            ORDER BY
                SEQUENCE
        ) WHERE ROWNUM = 1;

    EXCEPTION WHEN NO_DATA_FOUND
    THEN

        p_start_balance := 0;

END; -- get_interval_defaul_balance

/**
 * Returns meter default (current) properties.
 * Use of BS.BILL_MANAGER_2006.
 */
PROCEDURE GET_METER_DEFAULT (
        p_account   IN  NUMBER,
        p_type      OUT NUMBER,
        p_coeff     OUT NUMBER,
        p_meter_st  OUT NUMBER
    ) IS

        p_acc_cr_date DATE;
        p_inst_cp NUMBER;
        p_is_cutted BOOLEAN;
        p_cust_key NUMBER;
        p_cust_cat_key NUMBER;
        p_min_kwh NUMBER;
        p_max_kwh NUMBER;
        p_balance NUMBER;
        p_is_main_acc BOOLEAN;
        p_is_closed BOOLEAN;
        p_seal_st NUMBER;
        p_digits NUMBER;

    BEGIN

        BS.BILL_MANAGER_2006.get_acc_info(p_account, p_acc_cr_date, p_inst_cp,
            p_is_cutted, p_cust_key, p_cust_cat_key, p_type, p_digits, p_coeff,
            p_min_kwh, p_max_kwh, p_balance, p_is_main_acc, p_is_closed);

        SELECT
            MTCONDIT, MTSLCOND
        INTO
            p_meter_st, p_seal_st
        FROM
            BS.ACCOUNT
        WHERE
            ACCKEY = p_account;

        IF p_meter_st = 1 OR p_seal_st = 1
        THEN

            p_meter_st := 1;

        END IF;

    END; -- get_meter_default

/**
 * Returns metter properties for the given item.
 */
PROCEDURE GET_METER_PROPERTIES (
  p_account   IN  NUMBER,
  p_item      IN  NUMBER,
  p_type      OUT NUMBER,
  p_coeff     OUT NUMBER,
  p_meter_st  OUT NUMBER
) IS
  p_seal_st NUMBER;
BEGIN

  SELECT
    M_TYPE, M_COEFF, M_CONDIT, S_CONDIT
  INTO
    p_type, p_coeff, p_meter_st, p_seal_st
  FROM (
            SELECT
                H.MTTPKEY M_TYPE, T.DIGIT M_DIGIT, H.MTKOEF M_COEFF,
                H.MTCONDIT M_CONDIT, H.MTSLCOND S_CONDIT
            FROM
                BS.METER_HISTORY H, BS.MTTYPE T
            WHERE
                H.MTTPKEY = T.MTTPKEY AND
                ACCKEY = p_account AND
                LASTITEMKEY > p_item
            ORDER BY
                LASTITEMKEY DESC,
                ENTERDATE DESC
        ) WHERE ROWNUM = 1;

        IF p_meter_st = 1 OR p_seal_st = 1
        THEN
            p_meter_st := 1;
        END IF;

    EXCEPTION
    WHEN
        NO_DATA_FOUND
    THEN

        get_meter_default(p_account, p_type, p_coeff, p_meter_st);

    END; -- get_meter_properties


--------------------------------------------------------------------------------

-- Copy recalculation.
PROCEDURE RECALC_COPY (
  p_recalc_id NUMBER,
  p_user_id NUMBER,
  p_new_recalc_id OUT NUMBER,
  p_new_recalc_number OUT VARCHAR2
) IS
  p_recalc RECALC%ROWTYPE;
  p_new_interval_id NUMBER;
BEGIN

  -- new recalculation number
  p_new_recalc_id := NULL;
  p_new_recalc_number := get_voucher_number(p_user_id);

  -- get old recalculation parameters
  SELECT * INTO p_recalc FROM recalc WHERE id = p_recalc_id;

  -- create new recalculation header
  INSERT INTO recalc (
    RECALC_NUMBER, CUSTOMER, ACCOUNT, CREATE_DATE,
    DESCRIPTION, IS_CHANGED,
    INIT_BALANCE
  ) VALUES (
    p_new_recalc_number, p_recalc.customer, p_recalc.account, SYSDATE,
    p_recalc.description, 1,
    p_recalc.init_balance
  ) RETURNING ID INTO p_new_recalc_id;

  -- copy intervals and items
  for rec IN (SELECT * FROM recalc_interval WHERE recalc_id = p_recalc_id)
  loop
    insert into recalc_interval (
      recalc_id, name, sequence, start_balance, editable
    ) VALUES (
      p_new_recalc_id, rec.name, rec.sequence, rec.start_balance, rec.editable
    ) RETURNING ID into p_new_interval_id;
    for it_rec IN (
      select * from recalc_item where interval_id = rec.id
    ) loop
      insert into recalc_item (
        interval_id, item_id, customer_id, account_id, operation_id, status,
        reading, kwh, gel, balance, cycle_id, cycle_date, item_date, enter_date,
        curr_date, prev_date, meter_coeff, meter_status, meter_type_id,
        item_number, att_unit, att_amount, att_count, sequence,
        orig_operation_id, orig_item_date, orig_enter_date, orig_cycle_date,
        orig_cycle_id, orig_reading, orig_kwh, orig_gel, orig_balance, balance_gap,
        orig_balance_gap, leave_kwh_unchanged, orig_att_unit, orig_att_amount,
        orig_att_count, meter_accelerate, sub_account_id, orig_meter_coeff
      ) values (
        p_new_interval_id, it_rec.item_id, it_rec.customer_id, it_rec.account_id, it_rec.operation_id, it_rec.status,
        it_rec.reading, it_rec.kwh, it_rec.gel, it_rec.balance, it_rec.cycle_id, it_rec.cycle_date, it_rec.item_date, it_rec.enter_date,
        it_rec.curr_date, it_rec.prev_date, it_rec.meter_coeff, it_rec.meter_status, it_rec.meter_type_id,
        it_rec.item_number, it_rec.att_unit, it_rec.att_amount, it_rec.att_count, it_rec.sequence,
        it_rec.orig_operation_id, it_rec.orig_item_date, it_rec.orig_enter_date, it_rec.orig_cycle_date,
        it_rec.orig_cycle_id, it_rec.orig_reading, it_rec.orig_kwh, it_rec.orig_gel, it_rec.orig_balance, it_rec.balance_gap,
        it_rec.orig_balance_gap, it_rec.leave_kwh_unchanged, it_rec.orig_att_unit, it_rec.orig_att_amount,
        it_rec.orig_att_count, it_rec.meter_accelerate, it_rec.sub_account_id, it_rec.orig_meter_coeff
      );
    end loop;
  end loop;

  -- copy instcps
  for rec IN (SELECT * FROM recalc_instcp WHERE recalc_id = p_recalc_id)
  loop
    insert into recalc_instcp (
      recalc_id, start_date, end_date, amount, recalc_option, sequence
    ) values (
      p_new_recalc_id, rec.start_date, rec.end_date, rec.amount, rec.recalc_option, rec.sequence
    );
  end loop;

  -- copy regulars
  for rec IN (SELECT * FROM recalc_regular WHERE recalc_id = p_recalc_id)
  loop
    insert into recalc_regular (
      recalc_id, start_date, end_date, sequence, operation_id,
      att_unit, att_amount, att_count, att_amount2
    ) values (
      p_new_recalc_id, rec.start_date, rec.end_date, rec.sequence, rec.operation_id,
      rec.att_unit, rec.att_amount, rec.att_count, rec.att_amount2
    );
  end loop;

  -- copy rooms
  for rec IN (SELECT * FROM recalc_rooms WHERE recalc_id = p_recalc_id)
  loop
    insert into recalc_rooms (
      recalc_id, start_date, end_date, room_count
    ) values (
      p_new_recalc_id, rec.start_date, rec.end_date, rec.room_count
    );
  end loop;

  -- copy tariffs
  for rec IN (SELECT * FROM recalc_tariffs where recalc_id = p_recalc_id)
  loop
    insert into recalc_tariffs (
      recalc_id, tariff_id, start_date, end_date, sequence
    ) values (
      p_new_recalc_id, rec.tariff_id, rec.start_date, rec.end_date, rec.sequence
    );
  end loop;

  -- copy cuts
  for rec in (select * from recalc_cut where recalc_id = p_recalc_id)
  loop
    insert into recalc_cut (
      recalc_id, start_date, end_date, sequence
    ) values (
      p_new_recalc_id, rec.start_date, rec.end_date, rec.sequence
    );
  end loop;

END RECALC_COPY;

END;