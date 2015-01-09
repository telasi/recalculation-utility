---- charge (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(570, '2011 correction', '2011 ß.ÃÀÒÉÝáÅÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 803, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(571, '2011 discharge', '2011 ß.ÃÀÒÉÝáÅÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 163, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(572, '2011 corrected', '2011 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÃÀÒÉÝáÅÀ', 4, null, null, 0, 1, 0, 0, 163, 0);

---- debt (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(573, '2011 debt correction', '2011 ß.ÅÀËÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 235, 0);

---- compensation (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(574, '2011 compensation correction', '2011 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 931, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(575, '2011 compensation discharge', '2011 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 418, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(576, '2011 compensation corrected', '2011 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÊÏÌÐÄÍÓÀÝÉÀ', 4, null, null, 0, 1, 0, 0, 419, 0);

---- % subsidy (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(577, '2011 percent subsidy corrected', '2011 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 850, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(578, '2011 percent subsidy corrected', '2011 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 602, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(579, '2011 percent subsidy corrected', '2011 ß.ÛÄÓßÏÒÄÁÖËÉ ÐÒÏÝ ÛÄÙÀÅÀÈÉ', 4, null, null, 0, 1, 0, 0, 602, 0);

---- current act for 2010 (previous year)
INSERT INTO RECUT.BILLOPERATION (ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID)
VALUES (413, 'oper.actcorr.2010', 'ÄÒÈãÄÒÀÃÉ ÀØÔÉ -2009ß', 4, '01-Jan-2011', null, 0, 0, 0, 0, 50112, 0);
UPDATE RECUT.BILLOPERATION SET NAME_BS = 'ÄÒÈãÄÒÀÃÉ ÀØÔÉ -2010ß' WHERE ID = 413;

----- subsidy 2011 (20 GEL)
INSERT INTO BILLOPERATION
(
	ID,NAME,NAME_BS,
	TYPE_ID,D1,D2,
	REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL,
	SEQ, DIFF_GROUP_ID)
VALUES (
	127, '2011_20lari_voucher', 'ÄËÄØÔÒÏÄÍÄÒÂÉÉÓ ÅÀÖÜÄÒÉ',
	5, '01-Feb-2011', null,
	0, 1, 0, 0, 137, 13);
