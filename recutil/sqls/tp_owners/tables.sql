-- TP_OWNER_RECALC result

CREATE SEQUENCE  "RECUT"."TP_OWNER_RECALC_SEQ"
MINVALUE 1 MAXVALUE 1.00000000000000E+27 INCREMENT BY 1 START WITH 1
CACHE 20 NOORDER  NOCYCLE;
/

CREATE TABLE "RECUT"."TP_OWNER_RECALC" (
	"ID" NUMBER(12,0) NOT NULL ENABLE, 
	"CYCLE_DATE" DATE NOT NULL ENABLE, 
	"PRODUCER_CUSTOMER_ID" NUMBER(8,0) NOT NULL ENABLE, 
	"PRODUCER_ACCOUNT_ID" NUMBER(8,0) NOT NULL ENABLE, 
	"CUSTOMER_ID" NUMBER(8,0) NOT NULL ENABLE, 
	"ACCOUNT_ID" NUMBER(8,0) NOT NULL ENABLE, 
	"ITEM_ID" NUMBER(12,0) NOT NULL ENABLE, 
	"ITEM_DATE" DATE NOT NULL ENABLE, 
	"BILLOPERKEY" NUMBER(8,0) NOT NULL ENABLE, 
	"IS_CYCLE" NUMBER(2,0) NOT NULL ENABLE, 
	"ITEM_NUMBER" VARCHAR2(20 BYTE), 
	"KWH" NUMBER(10,2) NOT NULL ENABLE, 
	"GEL" NUMBER(10,2) NOT NULL ENABLE, 
	"KWH_E_CORRECTED" NUMBER(10,2) NOT NULL ENABLE, 
	"ECORR_FROM" NUMBER(12,0), 
	"PREV_CHARGE_DATE" DATE, 
	"BASE_TARIFF_ID" NUMBER(8,0), 
	 CONSTRAINT "TP_OWNER_RECALC_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC" ;

  COMMENT ON COLUMN "RECUT"."TP_OWNER_RECALC"."BASE_TARIFF_ID" IS 'for sub.account charge  this is a tariff used by charging subaccount itself';

  CREATE INDEX "RECUT"."TP_OWNER_RECALC_INDEX1" ON "RECUT"."TP_OWNER_RECALC" ("CYCLE_DATE", "PRODUCER_CUSTOMER_ID", "PRODUCER_ACCOUNT_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC_IND" ;
 
	CREATE OR REPLACE TRIGGER "RECUT"."TP_OWNER_RECALC_TRG" 
	BEFORE INSERT ON TP_OWNER_RECALC FOR EACH ROW 
	BEGIN
		SELECT TP_OWNER_RECALC_SEQ.NEXTVAL INTO :NEW.ID FROM DUAL;
	END;

/
ALTER TRIGGER "RECUT"."TP_OWNER_RECALC_TRG" ENABLE;
/

-- TP_OWNER_SEARCH table

CREATE SEQUENCE  "RECUT"."TP_OWNER_SEARCH_SEQ"
MINVALUE 1 MAXVALUE 1.00000000000000E+27 INCREMENT BY 1 START WITH 1
CACHE 20 NOORDER  NOCYCLE ;

CREATE TABLE "RECUT"."TP_OWNER_SEARCH"(
	"ID" NUMBER(12,0) NOT NULL ENABLE, 
	"ACCOUNT_ID" NUMBER(8,0) NOT NULL ENABLE, 
	"CYCLE_DATE" DATE NOT NULL ENABLE, 
	"CUSTOMER_ID" NUMBER(8,0) NOT NULL ENABLE, 
	"STATUS_ID" NUMBER(2,0), 
	"CURRENT_TARIFF_ID" NUMBER(8,0) NOT NULL ENABLE, 
	 CONSTRAINT "TP_OWNER_SEARCH_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC"  ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC" ;
 
  CREATE INDEX "RECUT"."TP_OWNER_SEARCH_INDEX1" ON "RECUT"."TP_OWNER_SEARCH" ("CYCLE_DATE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC_IND" ;

	CREATE OR REPLACE TRIGGER "RECUT"."TP_OWNER_SEARCH_TRG" 
	BEFORE INSERT ON TP_OWNER_SEARCH 
	FOR EACH ROW 
	BEGIN
		SELECT TP_OWNER_SEARCH_SEQ.NEXTVAL INTO :NEW.ID FROM DUAL;
	END;
/
ALTER TRIGGER "RECUT"."TP_OWNER_SEARCH_TRG" ENABLE;
/

-- TP_OWNER_RECALC_RESULTS table

CREATE SEQUENCE  "RECUT"."TP_OWNER_RECALC_RESULTS_SEQ"
MINVALUE 1 MAXVALUE 1.00000000000000E+27 INCREMENT BY 1 START WITH 1
CACHE 20 NOORDER  NOCYCLE ;

CREATE TABLE "RECUT"."TP_OWNER_RECALC_RESULTS" (
	"ID" NUMBER(12,0) NOT NULL ENABLE, 
	"RECALC_ID" NUMBER(12,0) NOT NULL ENABLE, 
	"OPER_ID" NUMBER(2,0) NOT NULL ENABLE, 
	"START_DATE" DATE NOT NULL ENABLE, 
	"END_DATE" DATE NOT NULL ENABLE, 
	"KWH" NUMBER(10,2) NOT NULL ENABLE, 
	"GEL" NUMBER(10,2) NOT NULL ENABLE, 
	 CONSTRAINT "TP_OWNER_RECALC_RESULTS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC" ENABLE, 
	 CONSTRAINT "TP_OWNER_RECALC_RESULTS_T_FK1" FOREIGN KEY ("RECALC_ID")
	  REFERENCES "RECUT"."TP_OWNER_SEARCH" ("ID") ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC" ;

  CREATE INDEX "RECUT"."TP_OWNER_RECALC_RESULTS_INDEX1" ON "RECUT"."TP_OWNER_RECALC_RESULTS" ("RECALC_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC_IND" ;

	CREATE OR REPLACE TRIGGER "RECUT"."TP_OWNER_RECALC_RESULTS_TRG" 
	BEFORE INSERT ON TP_OWNER_RECALC_RESULTS 
	FOR EACH ROW 
	BEGIN
		SELECT TP_OWNER_RECALC_RESULTS_SEQ.NEXTVAL INTO :NEW.ID FROM DUAL;
	END;
/

ALTER TRIGGER "RECUT"."TP_OWNER_RECALC_RESULTS_TRG" ENABLE;
/

---------------------------------------------------------------------------------------------
--           Voucher table: summary for customer over each RECALCULATED account.           --
---------------------------------------------------------------------------------------------

CREATE TABLE "RECUT"."TP_OWNER_RECALC_VOUCHER" (
	"CUSTOMER_ID" NUMBER(8,0) NOT NULL ENABLE, 
	"CYCLE_DATE" DATE NOT NULL ENABLE, 
	"BILLOPERKEY" NUMBER(8,0) NOT NULL ENABLE, 
	"KWH" NUMBER(10,2) NOT NULL ENABLE, 
	"GEL" NUMBER(10,2) NOT NULL ENABLE, 
	"ORDER_BY" NUMBER(5,0) NOT NULL ENABLE, 
	 CONSTRAINT "TP_OWNER_RECALC_VOUCHER_PK" PRIMARY KEY ("CUSTOMER_ID", "CYCLE_DATE", "BILLOPERKEY")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC"  ENABLE, 
	 CONSTRAINT "TP_OWNER_RECALC_VOUCHER_B_FK1" FOREIGN KEY ("BILLOPERKEY")
	  REFERENCES "RECUT"."BILLOPERATION" ("ID") ENABLE
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "RECALC" ;
