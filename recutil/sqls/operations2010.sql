---- charge (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(560, '2010 correction', '2010 ß.ÃÀÒÉÝáÅÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 802, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(561, '2010 discharge', '2010 ß.ÃÀÒÉÝáÅÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 162, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(562, '2010corrected', '2010 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÃÀÒÉÝáÅÀ', 4, null, null, 0, 1, 0, 0, 162, 0);

---- debt (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(563, '2010 debt correction', '2010 ß.ÅÀËÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 234, 0);

---- compensation (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(564, '2010 compensation correction', '2010 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 930, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(565, '2010 compensation discharge', '2010 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 418, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(566, '2010 compensation corrected', '2010 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÊÏÌÐÄÍÓÀÝÉÀ', 4, null, null, 0, 1, 0, 0, 419, 0);

---- % subsidy (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(567, '2010 percent subsidy corrected', '2010 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 850, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(568, '2010 percent subsidy corrected', '2010 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 602, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(569, '2010 percent subsidy corrected', '2010 ß.ÛÄÓßÏÒÄÁÖËÉ ÐÒÏÝ ÛÄÙÀÅÀÈÉ', 4, null, null, 0, 1, 0, 0, 602, 0);

---- current act for 2009 (previous year)
INSERT INTO RECUT.BILLOPERATION (ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID)
VALUES (412, 'oper.actcorr.2009', 'ÄÒÈãÄÒÀÃÉ ÀØÔÉ -2009ß', 4, '01-Jan-2010', null, 0, 0, 0, 0, 50112, 0);