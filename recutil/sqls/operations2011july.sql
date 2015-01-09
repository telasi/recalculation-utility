---- charge (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(600, '2011-july correction', '2011 ß.ÃÀÒÉÝáÅÉÓ ÊÏÒÄØÔÉÒÄÁÀ ÉÅËÉÓÉÃÀÍ', 4, null, null, 0, 1, 0, 0, 804, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(601, '2011-july discharge', '2011 ß.ÃÀÒÉÝáÅÉÓ ÌÏáÓÍÀ ÉÅËÉÓÉÃÀÍ', 4, null, null, 0, 1, 0, 0, 164, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(602, '2011-july corrected', '2011 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÃÀÒÉÝáÅÀ ÉÅËÉÓÉÃÀÍ', 4, null, null, 0, 1, 0, 0, 164, 0);

---- current act for 2011 (previous year/period)
INSERT INTO RECUT.BILLOPERATION (ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID)
VALUES (414, 'oper.actcorr.2011', 'ÄÒÈãÄÒÀÃÉ ÀØÔÉ -2011ß ÉÅËÉÓÀÌÃÄ', 4, null, null, 0, 0, 0, 0, 50112, 0);