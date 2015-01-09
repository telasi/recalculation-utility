---- charge (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(580, '2012 correction', '2012 ß.ÃÀÒÉÝáÅÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 804, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(581, '2012 discharge', '2012 ß.ÃÀÒÉÝáÅÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 164, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(582, '2012 corrected', '2012 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÃÀÒÉÝáÅÀ', 4, null, null, 0, 1, 0, 0, 164, 0);

---- debt (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(583, '2012 debt correction', '2012 ß.ÅÀËÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 236, 0);

---- compensation (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(584, '2012 compensation correction', '2012 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 931, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(585, '2012 compensation discharge', '2012 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 418, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(586, '2012 compensation corrected', '2012 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÊÏÌÐÄÍÓÀÝÉÀ', 4, null, null, 0, 1, 0, 0, 419, 0);

---- % subsidy (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(587, '2011 percent subsidy corrected', '2012 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 851, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(588, '2011 percent subsidy corrected', '2012 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 603, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(589, '2011 percent subsidy corrected', '2012 ß.ÛÄÓßÏÒÄÁÖËÉ ÐÒÏÝ ÛÄÙÀÅÀÈÉ', 4, null, null, 0, 1, 0, 0, 603, 0);

---- current act for 2011 (previous year)
INSERT INTO RECUT.BILLOPERATION (ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID)
VALUES (415, 'oper.actcorr.2011_2', 'ÄÒÈãÄÒÀÃÉ ÀØÔÉ ÌÉÌÃÉÍÀÒÄ ßËÉÓ 1 ÉÅËÉÓÉÃÀÍ', 4, '30-Jun-2011', null, 0, 0, 0, 0, 50113, 0);

---- other operations
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(152, 'restore.correction', 'ÀÙÃÂÄÍÉÓ ÂÀÃÀáÃÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 100000, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(605, 'payment_corr_2011jul', 'ÂÀÃÀáÃÉÓ ÊÏÒÄØÔÉÒÄÁÀ ÉÅËÉÓÉÃÀÍ', 4, null, null, 0, 1, 0, 0, 100001, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(606, 'payment_trash_billing', 'ÄË.ÄÍÄÒÂÉÀ-ÃÀÓÖ×ÈÀÅÄÁÀ ÉÅËÉÓÉÃÀÍ', 4, null, null, 0, 1, 0, 0, 100002, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(608, 'return_of_error_payment', 'ÛÄÝÃÏÌÉÈ ÜÀÒÉÝáÖËÉ ÈÀÍáÉÓ ÃÀÁÒÖÍÄÁÀ ÉÅËÉÓÉÃÀÍ', 4, null, null, 0, 1, 0, 0, 100003, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(610, 'electricity_trash_mosakrebeli', 'ÄË.ÄÍÄÒÂÉÀ-ÃÀÓÖ×ÈÀÅÄÁÀ(ÌÏÓÀÊÒÄÁÄËÉ)', 4, null, null, 0, 1, 0, 0, 100004, 0);
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(611, 'mosakrebeli_return_payment', 'ÛÄÝÃÏÌÉÈ ÜÀÒÉÝáÖËÉ ÈÀÍáÉÓ ÃÀÁÒÖÍÄÁÀ (ÌÏÓÀÊÒÄÁÄËÉ)', 4, null, null, 0, 1, 0, 0, 100005, 0);