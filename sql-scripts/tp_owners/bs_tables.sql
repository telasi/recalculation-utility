CREATE TABLE "BS"."TRANS_TYPES" (
	"ID" NUMBER(8,0) NOT NULL ENABLE, 
	"NAME" VARCHAR2(50 CHAR) NOT NULL ENABLE, 
	"POWER" NUMBER(10,2), 
	"POWER0" NUMBER(10,2), 
	 CONSTRAINT "TRANS_TYPES_PK" PRIMARY KEY ("ID")
USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
TABLESPACE "AES_INDEX"  ENABLE)
PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
TABLESPACE "AES_DATA" ;

-- INSERTING into TRANS_TYPES
Insert into bs.TRANS_TYPES (ID,NAME,POWER,POWER0) values (1,'63 kVA',63,63);
Insert into bs.TRANS_TYPES (ID,NAME,POWER,POWER0) values (2,'100 kVA',100,100);
Insert into bs.TRANS_TYPES (ID,NAME,POWER,POWER0) values (3,'160 kVA',160,160);
Insert into bs.TRANS_TYPES (ID,NAME,POWER,POWER0) values (4,'250 kVA',250,250);
Insert into bs.TRANS_TYPES (ID,NAME,POWER,POWER0) values (5,'400 kVA',400,400);
Insert into bs.TRANS_TYPES (ID,NAME,POWER,POWER0) values (6,'630 kVA',630,630);
Insert into bs.TRANS_TYPES (ID,NAME,POWER,POWER0) values (7,'1000 kVA',1000,1000);
Insert into bs.TRANS_TYPES (ID,NAME,POWER,POWER0) values (8,'1600 kVA',1600,1600);

-- TRANS_LOSSES

CREATE TABLE "BS"."TRANS_LOSSES" (
	"ID" NUMBER(8,0) NOT NULL ENABLE, 
	"TYPE_ID" NUMBER(8,0) NOT NULL ENABLE, 
	"ZERO_LOSS" NUMBER(10,2) NOT NULL ENABLE, 
	"BUSY_FROM" NUMBER(10,2) NOT NULL ENABLE, 
	"BUSY_TO" NUMBER(10,2) NOT NULL ENABLE, 
	"BUSY_LOSS" NUMBER(10,2) NOT NULL ENABLE, 
	 CONSTRAINT "TRANS_LOSSES_PK" PRIMARY KEY ("ID")
USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
TABLESPACE "AES_INDEX"  ENABLE, 
CONSTRAINT "TRANS_LOSSES_TRANS_TYPES_FK1" FOREIGN KEY ("TYPE_ID")
REFERENCES "BS"."TRANS_TYPES" ("ID") ENABLE)
PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
TABLESPACE "AES_DATA" ;

-- INSERTING into TRANS_LOSSES
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (1,1,191,0,0.25,0.4);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (2,1,191,0.25,0.5,0.8);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (3,1,191,0.5,0.75,1.2);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (4,1,191,0.75,1,1.6);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (5,2,263,0,0.25,0.4);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (6,2,263,0.25,0.5,0.8);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (7,2,263,0.5,0.75,1.2);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (8,2,263,0.75,1,1.6);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (9,3,407,0,0.25,0.3);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (10,3,407,0.25,0.5,0.7);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (11,3,407,0.5,0.75,1);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (12,3,407,0.75,1,1.2);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (13,4,590,0,0.25,0.3);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (14,4,590,0.25,0.5,0.6);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (15,4,590,0.5,0.75,0.9);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (16,4,590,0.75,1,1.2);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (17,5,756,0,0.25,0.3);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (18,5,756,0.25,0.5,0.6);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (19,5,756,0.5,0.75,0.8);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (20,5,756,0.75,1,1.1);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (21,6,1123,0,0.25,0.2);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (22,6,1123,0.25,0.5,0.5);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (23,6,1123,0.5,0.75,0.7);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (24,6,1123,0.75,1,1);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (25,7,1764,0,0.25,0.2);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (26,7,1764,0.25,0.5,0.5);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (27,7,1764,0.5,0.75,0.7);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (28,7,1764,0.75,1,1);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (29,8,2376,0,0.25,0.2);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (30,8,2376,0.25,0.5,0.5);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (31,8,2376,0.5,0.75,0.7);
Insert into bs.TRANS_LOSSES (ID,TYPE_ID,ZERO_LOSS,BUSY_FROM,BUSY_TO,BUSY_LOSS) values (32,8,2376,0.75,1,1);

-- TRANS_OWNER_PARAMS

CREATE SEQUENCE "BS"."TRANS_PARAMS_SEQ"
MINVALUE 1 MAXVALUE 1.00000000000000E+27 INCREMENT BY 1 START WITH 1
CACHE 20 NOORDER  NOCYCLE;

CREATE TABLE "BS"."TRANS_OWNER_PARAMS"(
	"ID" NUMBER(8,0) NOT NULL ENABLE, 
	"ACCOUNT_ID" NUMBER(8,0) NOT NULL ENABLE, 
	"STATUS_ID" NUMBER(1,0) NOT NULL ENABLE, 
	"TYPE_ID" NUMBER(8,0), 
	 CONSTRAINT "TRANS_PARAMS_PK" PRIMARY KEY ("ID")
USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
TABLESPACE "AES_INDEX"  ENABLE, 
	CONSTRAINT "TRANS_PARAMS_ACCOUNT_FK1" FOREIGN KEY ("ACCOUNT_ID")
	REFERENCES "BS"."ACCOUNT" ("ACCKEY") ENABLE)
PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
STORAGE(INITIAL 1048576 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
TABLESPACE "AES_DATA" ;

CREATE OR REPLACE TRIGGER "BS"."TRANS_PARAMS_TRIG1" 
BEFORE INSERT ON BS.TRANS_OWNER_PARAMS 
REFERENCING OLD AS OLD NEW AS NEW 
FOR EACH ROW 
BEGIN
  IF :NEW.ID IS NULL
  THEN
    SELECT TRANS_PARAMS_SEQ.NEXTVAL INTO :NEW.ID
    FROM DUAL;
  END IF;
END;

/
ALTER TRIGGER "BS"."TRANS_PARAMS_TRIG1" ENABLE;
 