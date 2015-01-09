create or replace PACKAGE TP_OWNER_RECALC_MANAGER AS

--------------------------------------------------------------------------------

  -- calculation statuses
  STATUS_NORMAL CONSTANT NUMBER := 1;
  STATUS_CYCLE_COMPLETED CONSTANT NUMBER := 2;
  STATUS_CALCULATED CONSTANT NUMBER := 3;
  STATUS_CALCULATED_WITH_ERRORS CONSTANT NUMBER := 4;
  STATUS_SENDED CONSTANT NUMBER := 5;

  -- discharge/recharge operations
  DISCHARGE CONSTANT NUMBER := 1;
  RECHARGE CONSTANT NUMBER := 2;

--------------------------------------------------------------------------------

  /**
   * Tariff history type.
   */
  TYPE TP_TARIFF_HISTORY IS TABLE OF BS.ACCTARIFFS%ROWTYPE;

  /**
   * Type used in account lookup.
   */
  TYPE TP_ACCOUNT IS RECORD (
    /**
     * Account ID.
     */
    pAccountId NUMBER,
    /**
     * Customer ID.
     */
    pCustomerId NUMBER,
    /**
     * From which itemkey is the Estimate correction? If NULL,
     * then there was no estimate correction.
     */
    pCorrectedFrom NUMBER
  );

  /**
   * Array of accounts.
   */
  TYPE TP_ACCOUNTS IS TABLE OF TP_ACCOUNT;

  /**
   * This structure is used for storing correction data.
   */
  TYPE TP_CORRECTION IS RECORD (
    /* billing operation for this correction */
    pBillingOperationId NUMBER,
    /* correction kWh amount */
    pKwh NUMBER,
    /* correction GEL amount */
    pGel NUMBER
  );

  /**
   * Corrections array.
   */ 
  TYPE TP_CORRECTIONS IS TABLE OF TP_CORRECTION;
  
--------------------------------------------------------------------------------

  PROCEDURE make_search_cache(pCycleDate DATE);

  /**
   * Prepare data so that is can be used in calculations by recalc-utility.
   */
  PROCEDURE generate_calculation(pCycleDate DATE, pAccountId NUMBER);

  /**
   * Prepare full customer summary for all recalculated accoutns.
   */
  PROCEDURE prepare_customer_summary(pCustomer NUMBER, pCycleDate DATE);

  /**
   * Send recalculation for the given customer to BS.ITEM table as a result.
   */
  PROCEDURE send_to_item(pCustomer NUMBER, pCycleDate DATE, pUserId NUMBER);

--------------------------------------------------------------------------------

END TP_OWNER_RECALC_MANAGER;
/

create or replace PACKAGE BODY TP_OWNER_RECALC_MANAGER AS

------- Generate Search Results

/**
 * TP owners cursor.
 */
CURSOR TP_OWNERS (pCycleDate DATE) IS
SELECT A.* FROM
(SELECT
  C.CUSTKEY        CUSTKEY,
  A.ACCKEY         ACCKEY,
  T.TYPE_ID        TYPE_ID
FROM BS.ACCOUNT A,
  BS.CUSTOMER C,
  BS.TRANS_OWNER_PARAMS T,
  BS.BLOCK BL,
  BS.CALENDAR CAL,
  BS.ROUTE RT,
  BS.ROUTEACC RTA
WHERE
  A.CUSTKEY = C.CUSTKEY AND
  A.ACCKEY = T.ACCOUNT_ID AND
  T.STATUS_ID = 0 AND
  BL.CYCLEDAY = CAL.CYCLEDAY AND
  RT.BLOCKKEY = BL.BLOCKKEY AND
  RTA.ROUTEKEY = RT.ROUTEKEY AND
  RTA.ACCKEY = T.ACCOUNT_ID AND
  CAL.CYCLEDATE = pCycleDate
) A,
(SELECT
  ACCOUNT_ID
FROM
  TP_OWNER_SEARCH
WHERE
  CYCLE_DATE = pCycleDate
) B
WHERE
  A.ACCKEY = B.ACCOUNT_ID (+) AND
  B.ACCOUNT_ID IS NULL;

/**
 * Validate whether cycle was completed for some customers.
 */
PROCEDURE validate_cycle(
  pCycleDate DATE
) IS
  pDummy NUMBER;
BEGIN

  -- check not completed cycles once again...
  FOR rec IN (
    SELECT * FROM TP_OWNER_SEARCH
    WHERE CYCLE_DATE = pCycleDate AND STATUS_ID = STATUS_NORMAL
  ) LOOP

    BEGIN

      -- check cycle existence
      SELECT ITEMKEY INTO pDummy
      FROM ITEM
      WHERE CUSTKEY = rec.CUSTOMER_ID AND ACCKEY = rec.ACCOUNT_ID
        AND ITEMDATE = pCycleDate AND SCHEDKEY IS NOT NULL
        AND ROWNUM = 1;

      -- mark as with cycle
      UPDATE TP_OWNER_SEARCH SET STATUS_ID = STATUS_CYCLE_COMPLETED
      WHERE ID = rec.ID;

    EXCEPTION WHEN NO_DATA_FOUND
    THEN
      NULL;
    END;

  END LOOP;

END validate_cycle;

/**
 * Make search cache.
 */
PROCEDURE make_search_cache(pCycleDate DATE) IS
  pTariffId NUMBER;
BEGIN
    FOR rec IN TP_OWNERS(pCycleDate)
    LOOP

      -- Get last compkey.
      -- XXX: Why not compkey for the particular pCycleDate? For the moment this
      -- approach works. We may need change this in the future.
      BEGIN
        SELECT COMPKEY INTO pTariffId
        FROM (
          SELECT TAR.COMPKEY
            FROM bs.acctariffs tar,
              bs.tarcomp comp
            WHERE tar.compkey = comp.compkey
             AND comp.basecompkey = 1 -- normal tariff
             AND tar.enddate IS NULL
             AND tar.acckey = rec.acckey
          ORDER BY TAR.ACCTARKEY DESC
        ) WHERE ROWNUM = 1;
      EXCEPTION WHEN NO_DATA_FOUND
      THEN
        pTariffId := -1;
      END;

      -- add result
      INSERT INTO TP_OWNER_SEARCH (
        ACCOUNT_ID, CUSTOMER_ID, CYCLE_DATE, STATUS_ID, CURRENT_TARIFF_ID
      ) VALUES (
        rec.ACCKEY, rec.CUSTKEY, pCycleDate, STATUS_NORMAL, pTariffId
      );
    END LOOP;

  -- validate this cycle
  validate_cycle(pCycleDate);

END make_search_cache;

--------------------------------------------------------------------------------

/**
 * Returns cycle start and end points.
 */
PROCEDURE getCycleBounds(
  pAccountId NUMBER,
  pCycleDate DATE,
  pStartItem OUT NUMBER,
  pEndItem   OUT NUMBER,
  pStartDate OUT DATE,
  pEndDate OUT DATE
) IS
BEGIN

  -- end item for current cycle
  BEGIN
    SELECT ITEMKEY, ITEMDATE INTO pEndItem, pEndDate FROM (
      SELECT ITEMKEY, ITEMDATE FROM BS.ITEM
      WHERE ACCKEY = pAccountId AND ITEMDATE = TRUNC(pCycleDate) AND
        -- subaacount charges are not marked with schedule
        -- this is an assumption but we have no other choise
        (SCHEDKEY IS NOT NULL OR BILLOPERKEY = 38)
      ORDER BY ITEMKEY DESC
    ) WHERE ROWNUM = 1;
  EXCEPTION WHEN NO_DATA_FOUND THEN
    RAISE_APPLICATION_ERROR(-20000, 'Can not find cycle for ACCKEY=' || pAccountId || ' in ' || pCycleDate);
  END;

  -- start item for current cycle
  BEGIN
    -- look up for the previous cycle last item
    SELECT ITEMKEY, ITEMDATE INTO pStartItem, pStartDate FROM (
      SELECT ITEMKEY, ITEMDATE FROM BS.ITEM
      WHERE ACCKEY = pAccountId AND ITEMDATE < TRUNC(pCycleDate) AND
        -- subaacount charge again!
        (SCHEDKEY IS NOT NULL OR BILLOPERKEY = 38)
      ORDER BY ITEMKEY DESC
    ) WHERE ROWNUM = 1;
  EXCEPTION WHEN NO_DATA_FOUND THEN
    -- no lower bound!
    pStartItem := -1;
  END;

END getCycleBounds;

/**
 * Searches for related item.
 */
FUNCTION locateRelatedItem(
  pItemRow BS.ITEM%ROWTYPE,
  pOnlyAccountKey BOOLEAN
) RETURN BS.ITEM%ROWTYPE IS
  pRelatedItem ITEM%ROWTYPE;
BEGIN

  -- #1. for subaccount charge ItemNumber has
  -- information about related child item
  IF
    pItemRow.BILLOPERKEY = 38 AND
    pItemRow.ITEMNUMBER IS NOT NULL
  THEN

    -- register this child
    DECLARE
      pItemId NUMBER;
      pSubaccountId NUMBER;
    BEGIN

      pItemId := to_number(pItemRow.ITEMNUMBER);

      -- get item info
      SELECT * INTO pRelatedItem FROM BS.ITEM WHERE ITEMKEY = pItemId;

      RETURN pRelatedItem;

    EXCEPTION WHEN NO_DATA_FOUND
    THEN
      RAISE_APPLICATION_ERROR(-20000, 'Can not locate subaccount charge.');
    END;

  END IF;

  -- #2. parent voucher item
  IF
    pItemRow.ITEMNUMBER IS NOT NULL AND
    pItemRow.ITEMNUMBER LIKE 'prnt%'
  THEN

    -- look up for the parent acccount
    DECLARE
      pSubaccountId NUMBER;
    BEGIN

      -- accId is after 'prnt' string
      pSubaccountId := to_number(SUBSTR(pItemRow.ITEMNUMBER, 5));

      IF pOnlyAccountKey
      THEN

        pRelatedItem.ACCKEY := pSubaccountId;

      ELSE

        -- previous reading
        SELECT * INTO pRelatedItem FROM (
          SELECT * FROM BS.ITEM
          WHERE BILLOPERKEY IN (1, 2, 3, 4, 7, 50) AND
            ACCKEY = pSubaccountId AND ITEMKEY < pItemRow.ITEMKEY AND
            ITEMDATE < pItemRow.ITEMDATE
            ORDER BY ITEMKEY DESC
        ) WHERE ROWNUM = 1;

      END IF;

      RETURN pRelatedItem;

    EXCEPTION WHEN NO_DATA_FOUND
    THEN
      RAISE_APPLICATION_ERROR(-20000, 'Can not locate subaccount charge.');
    END;

  END IF;

  -- nothing was found    
  RETURN NULL;

END locateRelatedItem;

FUNCTION locateRelatedItem(
  pItemId NUMBER,
  pOnlyAccountKey BOOLEAN
) RETURN BS.ITEM%ROWTYPE IS
  pItemRow BS.ITEM%ROWTYPE;
BEGIN
  SELECT * INTO pItemRow FROM ITEM
  WHERE ITEMKEY = pItemId;
  RETURN locateRelatedItem(pItemRow, pOnlyAccountKey);
END locateRelatedItem;

PROCEDURE validateCycleGeneration(pAccountId NUMBER, pCycleDate DATE, pAccounts OUT TP_ACCOUNTS)
IS
  pStartItem NUMBER;
  pEndItem NUMBER;
  pStartDate DATE;
  pEndDate DATE;

  -- append correction
  PROCEDURE appendAccount(
    pAccounts IN OUT TP_ACCOUNTS,
    pAccountId NUMBER,
    pItemFrom NUMBER
  ) IS
    pIndex NUMBER;
    pInList BOOLEAN;
    pAccount TP_ACCOUNT;
    pCustomerId NUMBER;
  BEGIN
    pIndex := 1;
    pInList := FALSE;

    -- loop over existing
    WHILE pAccounts IS NOT NULL AND pIndex <= pAccounts.COUNT
    LOOP
      IF pAccounts(pIndex).pAccountId = pAccountId
      THEN
        IF pAccounts(pIndex).pCorrectedFrom IS NULL
          AND pItemFrom IS NOT NULL
        THEN
          pAccount.pAccountId := pAccountId;
          pAccount.pCorrectedFrom := pItemFrom;
          pAccount.pCustomerId := pAccounts(pIndex).pCustomerId;
          pAccounts(pIndex) := pAccount;
        END IF;
        pInList := TRUE;
        EXIT;
      END IF;
      pIndex := pIndex + 1;
    END LOOP;

    -- add if not yet added
    IF NOT pInList
    THEN
      SELECT CUSTKEY INTO pCustomerId FROM ACCOUNT WHERE ACCKEY = pAccountId;
      pAccount.pAccountId := pAccountId;
      pAccount.pCorrectedFrom := pItemFrom;
      pAccount.pCustomerId := pCustomerId;
      IF pAccounts IS NULL
      THEN
        pAccounts := TP_ACCOUNTS();
      END IF;
      pAccounts.EXTEND;
      pAccounts(pAccounts.COUNT) := pAccount;
    END IF;

  END appendAccount;

  -- whether estimate correction is already marked for the given account
  FUNCTION estimateCorrectionMarked(pAccounts TP_ACCOUNTS, pAccountId NUMBER) RETURN BOOLEAN
  IS
    pIndex NUMBER;
  BEGIN

    -- look up for this account record
    pIndex := 1;
    WHILE pAccounts IS NOT NULL AND pIndex < pAccounts.COUNT
    LOOP
      IF pAccounts(pIndex).pAccountId = pAccountId
      THEN
        RETURN pAccounts(pIndex).pCorrectedFrom IS NOT NULL;
      END IF;
    END LOOP;

    -- no record for this account was found
    RETURN FALSE;
  END estimateCorrectionMarked;

BEGIN

  -- find whether there is a cycle in this date for the given account
  DECLARE
    pDummyVar NUMBER;
  BEGIN
    SELECT ITEMKEY INTO pDummyVar FROM BS.ITEM
    WHERE ITEMDATE = pCycleDate AND SCHEDKEY IS NOT NULL AND
      ACCKEY = pAccountId AND ROWNUM = 1;
  EXCEPTION WHEN NO_DATA_FOUND
  THEN
    RAISE_APPLICATION_ERROR(-20000, 'Can not find cycle for ACCKEY=' ||
      pAccountId || ' in ' || pCycleDate);
  END;

  -- get some essential cycle parameters
  getCycleBounds(pAccountId, pCycleDate, pStartItem, pEndItem, pStartDate, pEndDate);

  -- add myself to this list :)
  appendAccount(pAccounts, pAccountId, NULL);

  -- loop over this cycle: we need to configure whether there are
  -- estimate corrections (own and from children accounts)
  DECLARE
    pItemId NUMBER;
    pRow ITEM%ROWTYPE;
  BEGIN

    FOR rec IN (
      SELECT IT.* FROM BS.ITEM IT, BS.BILLOPERATION BOP
      WHERE ACCKEY = pAccountId AND ITEMKEY > pStartItem
        AND ITEMKEY <= pEndItem
        AND BOP.BILLOPERKEY = IT.BILLOPERKEY
      ORDER BY ITEMKEY
    ) LOOP

      -- #1. subaacount charge
      IF rec.BILLOPERKEY = 38 AND rec.ITEMNUMBER IS NOT NULL
      THEN

        -- locate related item
        pRow := locateRelatedItem(rec, true);

        -- append item into accounts list
        appendAccount(pAccounts, pRow.ACCKEY, NULL);

      -- #2. SYS voucher
      ELSIF rec.ITEMNUMBER IS NOT NULL AND rec.ITEMNUMBER = 'sys'
      THEN

        -- check whether correction item was already found
        IF NOT estimateCorrectionMarked(pAccounts, pAccountId)
        THEN

          -- look up for correction item for this estimate correction
          BEGIN

            -- previous reading
            SELECT ITEMKEY INTO pItemId FROM (
              SELECT ITEMKEY FROM ITEM
              WHERE BILLOPERKEY IN (1, 2, 3, 4, 7, 50) AND
                ACCKEY = pAccountId AND ITEMKEY < rec.ITEMKEY AND
                ITEMDATE < rec.ITEMDATE
                ORDER BY ITEMKEY DESC
            ) WHERE ROWNUM = 1;

            -- append correction item
            appendAccount(pAccounts, pAccountId, pItemId);

          EXCEPTION WHEN NO_DATA_FOUND
          THEN
            RAISE_APPLICATION_ERROR(-20000, 'Estimate correction start reading not found.');
          END;

        END IF;

      -- #3. subaacount voucher
      ELSIF rec.ITEMNUMBER IS NOT NULL AND rec.ITEMNUMBER LIKE 'prnt%'
      THEN

        -- look up for the parent acccount
        DECLARE
          pSubaccountId NUMBER;
        BEGIN

          -- get subaaccountId
          pRow := locateRelatedItem(rec, true);
          pSubaccountId := pRow.ACCKEY;

          -- check whether estimate correction already exist for this account
          IF NOT estimateCorrectionMarked(pAccounts, pSubaccountId)
          THEN

            -- previous reading
            pRow := locateRelatedItem(rec, false);

            -- append correction item
            appendAccount(pAccounts, pSubaccountId, pRow.ITEMKEY);

          END IF;

        END;

      END IF;
    END LOOP;

  END;

END validateCycleGeneration;

FUNCTION previousChargeDate(
  pRelAccountId NUMBER,
  pRelatedItemId NUMBER
) RETURN DATE
IS
  pPrevChargeDate DATE;
BEGIN

  BEGIN
    SELECT ITEMDATE INTO pPrevChargeDate
    FROM (
      SELECT IT.ITEMDATE FROM BS.ITEM IT, BS.BILLOPERATION BOP
      WHERE IT.BILLOPERKEY = BOP.BILLOPERKEY AND
        BOP.OPERTPKEY IN (1, 2) AND IT.ACCKEY = pRelAccountId AND
        IT.ITEMKEY < pRelatedItemId
      ORDER BY
        IT.ITEMKEY DESC
    ) WHERE ROWNUM = 1;
  EXCEPTION WHEN NO_DATA_FOUND
  THEN
    SELECT CREATEDATE INTO pPrevChargeDate
    FROM BS.ACCOUNT
    WHERE ACCKEY = pRelAccountId;
  END;

  RETURN pPrevChargeDate;

END previousChargeDate;

/**
 * Fill data, which is used later by calculation procedure.
 */
PROCEDURE fillCalculationData(pAccountId NUMBER, pAccounts TP_ACCOUNTS, pCycleDate DATE)
IS
  pOwnerCustomerId NUMBER;
  pMinCycleDate DATE;
  pHasOtherCustomers BOOLEAN;
BEGIN

  -- get owning customer
  SELECT CUSTKEY INTO pOwnerCustomerId
  FROM BS.ACCOUNT WHERE ACCKEY = pAccountId;

  -- Find minimum cycle date.
  -- By the way we'll find whether there are other customers, different from this one.
  pHasOtherCustomers := FALSE;
  DECLARE
    pAccount TP_ACCOUNT;
    pTempItemId NUMBER;
    pIndex NUMBER;
    pMinItemId NUMBER;
  BEGIN

    -- loop over accounts
    pIndex := 1;
    WHILE pIndex <= pAccounts.COUNT
    LOOP

      -- current record
      pAccount := pAccounts(pIndex);

      -- check whether is different customer
      IF pAccount.pCustomerId != pOwnerCustomerId
      THEN
        pHasOtherCustomers := TRUE;
      END IF;

      -- determine minimum
      pTempItemId := pAccount.pCorrectedFrom;
      IF pTempItemId IS NOT NULL AND
        (pMinItemId IS NULL OR pMinItemId > pTempItemId)
      THEN
        pMinItemId := pTempItemId;
      END IF;

      -- next step...
      pIndex := pIndex + 1;
    END LOOP;

    -- no estimate correction: we need to consider only current cycle!
    IF pMinItemId IS NULL -- we need to consider only current cycle
    THEN

      DECLARE
        pStartItem NUMBER;
        pEndItem NUMBER;
        pStartDate DATE;
        pEndDate DATE;
      BEGIN
        getCycleBounds(pAccountId, pCycleDate, pStartItem,
          pEndItem, pStartDate, pEndDate);
        pMinCycleDate := pStartDate;
      END;

    -- find "minimum" cycle date
    ELSE

      DECLARE
        pDate DATE;
        pSchedule NUMBER;
        pAccountId2 NUMBER;
      BEGIN
        -- get 
        SELECT ITEMDATE, SCHEDKEY, ACCKEY INTO pDate, pSchedule, pAccountId2
        FROM BS.ITEM WHERE ITEMKEY = pMinItemId;

        -- this was cycle item!
        IF pSchedule IS NOT NULL
        THEN
          -- minimal cycle date
          pMinCycleDate := pDate;
        ELSE
          BEGIN
             -- try to find nearest cycle
            SELECT ITEMDATE INTO pMinCycleDate
            FROM (SELECT ITEMDATE FROM BS.ITEM
              WHERE ACCKEY = pAccountId2 AND ITEMKEY < pMinItemId
                AND SCHEDKEY IS NOT NULL)
            WHERE ROWNUM = 1;

          EXCEPTION WHEN NO_DATA_FOUND
          THEN
            -- there was no previous cycle for the account
            RAISE_APPLICATION_ERROR(-20000, 'Not yet implemented...');
          END;
        END IF;
      END;

    END IF;

  END;

  -- check whether is recalculable case
  /*DECLARE
    pTariffs TP_TARIFF_HISTORY;
    pHighSide BOOLEAN;
    pLowSide BOOLEAN;
    pTariffId NUMBER;
  BEGIN

    -- get tariff history
    pTariffs := getTariffHistory(pAccountId);
    IF pTariffs IS NULL OR pTariffs.COUNT = 0
    THEN
      RAISE_APPLICATION_ERROR(-20000, 'No tariff history for this account.');
    END IF;

    -- last tariff is
    pTariffId := pTariffs(pTariffs.COUNT).COMPKEY;
    pHighSide := isHighVoltageSide(pTariffId);
    pLowSide := isLowVoltageSide(pTariffId);

    -- correction can not be applied
    IF (NOT pHighSide AND NOT pLowSide) OR (pHighSide AND NOT pHasOtherCustomers)
    THEN
      RAISE_APPLICATION_ERROR(-20000, 'No correction can be applied.');
    END IF;

  END;
  */

  -- loop over full period and fill data
  DECLARE
    pIsCycle NUMBER;
    pCycleSeq NUMBER;
    pPrevCycleDate DATE;
    pRelatedItem ITEM%ROWTYPE;
    pRelCustomerId NUMBER;
    pRelAccountId NUMBER;
    pECorrectionFrom NUMBER;
    pIndex NUMBER;
    pAccount TP_ACCOUNT;
    pPrevChargeDate DATE;
    pBaseTariffId NUMBER;
  BEGIN

    -- loop over history of this account
    pCycleSeq := 0;
    pPrevCycleDate := pMinCycleDate;
    FOR rec IN (
      SELECT IT.*, IR.IR_E_KWH, BOP.OPERTPKEY FROM BS.ITEM IT, BS.BILLOPERATION BOP, BS.ITEM_RELATION IR
      WHERE IT.ACCKEY = pAccountId AND (
        (IT.ITEMDATE = pCycleDate AND (IT.SCHEDKEY IS NOT NULL OR IT.BILLOPERKEY = 38)) OR
        (IT.ITEMDATE = pMinCycleDate AND (IT.SCHEDKEY IS NULL AND IT.BILLOPERKEY != 38)) OR
        (IT.ITEMDATE > pMinCycleDate AND IT.ITEMDATE < pCycleDate)
      ) AND BOP.BILLOPERKEY = IT.BILLOPERKEY AND (
        BOP.OPERTPKEY IN (1, 2) OR
        IT.ITEMNUMBER = 'sys' OR ITEMNUMBER LIKE 'prnt%' OR
        IT.BILLOPERKEY = 38
      ) AND IR.IR_ITEM = IT.ITEMKEY (+)
      ORDER BY IT.ITEMKEY
    ) LOOP

      pBaseTariffId := null;
      pRelCustomerId := rec.CUSTKEY;
      pRelAccountId := rec.ACCKEY;

      -- whether is a plain cycle? and previous charge date
      pIsCycle := 0;
      pPrevChargeDate := NULL;
      IF rec.SCHEDKEY IS NOT NULL
      THEN
        pIsCycle := 1;
      END IF;

      -- subaacount charge
      IF rec.BILLOPERKEY = 38 OR rec.ITEMNUMBER LIKE 'prnt%'
      THEN
        pRelatedItem := locateRelatedItem(rec.ITEMKEY, false);
        pRelCustomerId := pRelatedItem.CUSTKEY;
        pRelAccountId := pRelatedItem.ACCKEY;
        IF pRelatedItem.SCHEDKEY IS NOT NULL
        THEN
          pIsCycle := 1;
        END IF;
        pPrevChargeDate := previousChargeDate(pRelAccountId, pRelatedItem.ITEMKEY);
        IF pRelatedItem.ACCTARKEY IS NOT NULL
        THEN
          BEGIN
            SELECT COMPKEY INTO pBaseTariffId
            FROM BS.ACCTARIFFS WHERE ACCTARKEY = pRelatedItem.ACCTARKEY;
          EXCEPTION WHEN NO_DATA_FOUND
          THEN
            pBaseTariffId := NULL;
          END;
        END IF;
      ELSIF rec.OPERTPKEY IN (1, 2)
      THEN
        pPrevChargeDate := previousChargeDate(rec.ACCKEY, rec.ITEMKEY);
      END IF;

      -- find previous charge date

      -- manage cycle sequence parameter
      IF pIsCycle = 1 AND rec.ITEMDATE > pPrevCycleDate
      THEN
        pCycleSeq := pCycleSeq + 1;
        pPrevCycleDate := rec.ITEMDATE;
      END IF;

      -- look up for correction itemId
      pECorrectionFrom := NULL;
      pIndex := 1;
      WHILE pIndex <= pAccounts.COUNT
      LOOP
        pAccount := pAccounts(pIndex);
        IF pAccount.pAccountId = pRelAccountId
        THEN
          pECorrectionFrom := pAccount.pCorrectedFrom;
          EXIT;
        END IF;
        pIndex := pIndex + 1;
      END LOOP;

      -- add data to calculation table
      INSERT INTO TP_OWNER_RECALC (
        CYCLE_DATE,
        PRODUCER_CUSTOMER_ID, PRODUCER_ACCOUNT_ID, CUSTOMER_ID, ACCOUNT_ID,
        ITEM_ID, ITEM_DATE, BILLOPERKEY, ITEM_NUMBER, KWH,
        GEL, IS_CYCLE, ECORR_FROM, KWH_E_CORRECTED,
        PREV_CHARGE_DATE,
        BASE_TARIFF_ID
      ) VALUES (
        pCycleDate,
        pOwnerCustomerId, pAccountId, pRelCustomerId, pRelAccountId,
        rec.ITEMKEY, rec.ITEMDATE, rec.BILLOPERKEY, rec.ITEMNUMBER, rec.KWT,
        rec.AMOUNT, pIsCycle, pECorrectionFrom, NVL(rec.IR_E_KWH, rec.KWT),
        pPrevChargeDate,
        pBaseTariffId
      );

    END LOOP;

  END;

END fillCalculationData;

/**
 * Generate calculation table for the account.
 */
PROCEDURE generate_calculation(
  pCycleDate DATE,
  pAccountId NUMBER
) IS
  pDummy NUMBER;
  pAccounts TP_ACCOUNTS;
  pCustomerId NUMBER;
BEGIN

  -- validate existence of such recalculation
  BEGIN

    SELECT ID INTO pDummy
    FROM TP_OWNER_RECALC
    WHERE PRODUCER_ACCOUNT_ID = pAccountId AND CYCLE_DATE = pCycleDate
      AND ROWNUM = 1;

    -- EXIT!
    RETURN;

  EXCEPTION WHEN NO_DATA_FOUND
  THEN

    -- continue...
    NULL;

  END;

  -- get appropriate customer
  SELECT CUSTKEY INTO pCustomerId FROM BS.ACCOUNT WHERE ACCKEY = pAccountId;

  -- clear all previously generated data
--  DELETE FROM TP_OWNER_RECALC
--  WHERE CYCLE_DATE = pCycleDate AND CUSTOMER_ID = pCustomerId
--    AND ACCOUNT_ID = pAccountId;

  -- make cycle validation
  validateCycleGeneration(pAccountId, TRUNC(pCycleDate), pAccounts);

  -- check results
  IF pAccounts IS NULL OR pAccounts.COUNT = 0
  THEN
    RAISE_APPLICATION_ERROR(-20000, 'Accounts list is empty, it looks strange.');
  END IF;

  -- fill calculation tables
  fillCalculationData(pAccountId, pAccounts, pCycleDate);

END generate_calculation;

--------------------------------------------------------------------------------

/**
 * Determines whether some date is in current month.
 */
FUNCTION this_month(pDate DATE) RETURN BOOLEAN
IS
  p_curr_month NUMBER;
  p_curr_year NUMBER;
  p_month NUMBER;
  p_year NUMBER;
BEGIN
  p_month := EXTRACT (MONTH FROM pDate);
  p_year := EXTRACT (YEAR FROM pDate);
  p_curr_month := EXTRACT (MONTH FROM SYSDATE);
  p_curr_year := EXTRACT (YEAR FROM SYSDATE);

  RETURN p_month = p_curr_month AND p_year = p_curr_year;
END this_month;

/**
 * Get billing voucher number.
 */
FUNCTION get_billing_correction_code(
  p_oper NUMBER, -- discharge/recharge
  p_date DATE -- operation date
) RETURN NUMBER
IS
  p_is_same_month BOOLEAN := this_month(p_date);
  p_year NUMBER := EXTRACT (YEAR from p_date);
  p_type NUMBER;
BEGIN

--  XXX: we may need to produce different pair of discharge/recharge
--       operations for the same month
--  IF p_is_same_month
--  THEN
--    RETURN SOME_YET_UNKNOWN_OPERATION;
--  ELSE  

    /* discharge/recharge in terms of BILL_MANAGER */
    IF p_oper = DISCHARGE
    THEN
      p_type := bs.bill_manager_2006.discharge_plain;
    ELSE
      p_type := bs.bill_manager_2006.recharge_plain;
    END IF;

    /* get appropriate correction code */
    RETURN bs.bill_manager_2006.get_correction_code(p_year, p_type);

--  END IF;

  return -1;

END get_billing_correction_code;

/**
 * Append correction into corrections array.
 */
PROCEDURE appendCorrection(
  pCorrection         TP_CORRECTION,
  pCorrections IN OUT TP_CORRECTIONS
) IS
  p_found BOOLEAN := FALSE;
  p_corr TP_CORRECTION;
  p_index NUMBER := 1;
BEGIN

  -- create array, of not yet created
  IF pCorrections IS NULL
  THEN
    pCorrections := TP_CORRECTIONS();
  END IF;
  
  -- try to merge this correction with existing ones
  WHILE (NOT p_found) AND (p_index <= pCorrections.COUNT)
  LOOP
    p_corr := pCorrections(p_index);
    IF p_corr.pBillingOperationId = pCorrection.pBillingOperationId
    THEN
      p_corr.pKwh := p_corr.pKwh + pCorrection.pKwh;
      p_corr.pGel := p_corr.pGel + pCorrection.pGel;
      pCorrections(p_index) := p_corr;
      p_found := true;
    END IF;
    p_index := p_index + 1;
  END LOOP;
  
  -- appened if appropriate operation was not found  
  IF NOT p_found
  THEN
    pCorrections.EXTEND;
    pCorrections(pCorrections.COUNT) := pCorrection;
  END IF;
  
END appendCorrection;

/**
 * Prepare full customer summary for all recalculated accoutns.
 */
PROCEDURE prepare_customer_summary(pCustomer NUMBER, pCycleDate DATE)
IS
  p_correction TP_CORRECTION;
  p_corrections TP_CORRECTIONS;
  p_index NUMBER;
BEGIN
  
  -- make summary for the given customer
  
  FOR rec IN (
    SELECT
      res.oper_id, -- discharge or recharge
      res.end_date itemdate, -- operation date
      SUM(res.kwh) kwh, -- kwh/gel summarized
      SUM(gel) gel
    FROM
      tp_owner_search srch, tp_owner_recalc_results res
    WHERE
      srch.id = res.recalc_id AND
      srch.customer_id = pCustomer AND -- customer for the given cycle date
      srch.cycle_date = TRUNC(pCycleDate) AND
      srch.status_id = STATUS_CALCULATED -- only calculated are sent!
    GROUP BY
      res.end_date, res.oper_id
    ORDER BY -- First DISCHARGES, then RECHARGES!
             -- Date order is not so important. It can be even removed.
      res.oper_id, res.end_date
  ) LOOP
    p_correction.pBillingOperationId := get_billing_correction_code(rec.oper_id, rec.itemdate);
    p_correction.pKwh := rec.kwh;
    p_correction.pGel := rec.gel;
    appendCorrection(p_correction, p_corrections);
  END LOOP;
  
  -- write into TP_OWNER_RECALC_VOUCHER
  
  -- empty corrections error
  IF p_corrections IS NULL OR p_corrections.COUNT = 0
  THEN
    RAISE_APPLICATION_ERROR(-20000, 'No corrections!');
  END IF;
  
  -- delete previous voucher
  DELETE FROM TP_OWNER_RECALC_VOUCHER
  WHERE CUSTOMER_ID = pCustomer AND CYCLE_DATE = TRUNC(pCycleDate);
  
  -- insert new results
  p_index := 1;
  WHILE p_index <= p_corrections.COUNT
  LOOP
    p_correction := p_corrections(p_index);
    INSERT INTO TP_OWNER_RECALC_VOUCHER (
      CUSTOMER_ID, CYCLE_DATE, BILLOPERKEY,
      KWH, GEL, ORDER_BY
    ) VALUES (
      pCustomer, TRUNC(pCycleDate), p_correction.pBillingOperationId,
      p_correction.pKwh, p_correction.pGel, p_index
    );
    p_index := p_index + 1;
  END LOOP;

END prepare_customer_summary;

/**
 * Send recalculation for the given customer and cycle date into
 * BS.ITEM table. This procedure summarize all calculated accounts
 * of the customer; accounts which are marked as errors are ignored.
 */
PROCEDURE send_to_item(pCustomer NUMBER, pCycleDate DATE, pUserId NUMBER)
IS
  p_person NUMBER := SECURITY.get_bs_user(pUserId);
  p_mainacc NUMBER;
  p_dummy NUMBER;
BEGIN
  
  -- prepare summary
  prepare_customer_summary(pCustomer, pCycleDate);

  -- write into BS.ITEM
  
  FOR rec IN (SELECT * FROM TP_OWNER_RECALC_VOUCHER
    WHERE customer_id = pcustomer AND cycle_date = TRUNC(pcycledate)
    ORDER BY ORDER_BY
  ) LOOP
    SELECT ACCKEY INTO p_mainacc FROM BS.ACCOUNT
    WHERE custkey=pCustomer AND mainaccount = 1;
    
    -- write into BS.ITEM table
    p_dummy := bs.BILL_MANAGER_2006.populate_item (
      pCustomer, p_mainacc, NULL, NULL, rec.billoperkey, -- customer, account, tariff, schedule, billoper
      p_person, p_person, TRUNC(SYSDATE), 'tpown',-- operator, sign_person
      0, rec.kwh, rec.gel, 0, -- reading, kwh, gel, item_Category
      NULL, bs.BILL_MANAGER_2006.ITEM_REC_COMMON, -- notekey, item_category
      NULL, NULL, NULL, NULL -- relation parameters
    );
  END LOOP;

  -- update statuses of the TP_OWNER_SEARCH items

  UPDATE tp_owner_search SET status_id = STATUS_SENDED
  WHERE customer_id = pCustomer AND cycle_date = pCycleDate
    AND status_id = STATUS_CALCULATED;

END send_to_item;

END TP_OWNER_RECALC_MANAGER;
/
