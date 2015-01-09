---- charge (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(590, '2013 correction', '2013 ß.ÃÀÒÉÝáÅÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 804, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(591, '2013 discharge', '2013 ß.ÃÀÒÉÝáÅÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 164, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(592, '2013 corrected', '2013 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÃÀÒÉÝáÅÀ', 4, null, null, 0, 1, 0, 0, 164, 0);

---- debt (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(593, '2013 debt correction', '2013 ß.ÅÀËÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 236, 0);

---- compensation (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(594, '2013 compensation correction', '2013 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 931, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(595, '2013 compensation discharge', '2013 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 418, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(596, '2013 compensation corrected', '2013 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÊÏÌÐÄÍÓÀÝÉÀ', 4, null, null, 0, 1, 0, 0, 419, 0);

---- % subsidy (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(597, '2013 percent subsidy corrected', '2013 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 851, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(598, '2013 percent subsidy corrected', '2013 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 603, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(599, '2013 percent subsidy corrected', '2013 ß.ÛÄÓßÏÒÄÁÖËÉ ÐÒÏÝ ÛÄÙÀÅÀÈÉ', 4, null, null, 0, 1, 0, 0, 603, 0);

---- current act for 2011 (previous year)
INSERT INTO RECUT.BILLOPERATION (ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID)
VALUES (416, 'oper.actcorr.2013', 'ÄÒÈãÄÒÀÃÉ ÀØÔÉ ÌÉÌÃÉÍÀÒÄ ßËÉÓ 1 ÉÅËÉÓÉÃÀÍ', 4, '30-Jun-2011', null, 0, 0, 0, 0, 50113, 0);
