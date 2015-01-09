---- make gap
update billoperation set seq = seq * 8;

---- social
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(317, 'social','ÓÏÝÉÀËÖÒÉ ÐÒÏÂÒÀÌÀ', 5, null, null, 0, 1, 0, 0, 1528, 7);

---- charge (correction)
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(550, '2009 correction', '2009 ß.ÃÀÒÉÝáÅÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 801, 0);
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(551, '2009 discharge', '2009 ß.ÃÀÒÉÝáÅÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 161, 0);
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(552, '2009 corrected', '2009 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÃÀÒÉÝáÅÀ', 4, null, null, 0, 1, 0, 0, 161, 0);

---- debt (correction)
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(553, '2009 debt correction', '2009 ß.ÅÀËÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 233, 0);

---- compensation (correction)
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(554, '2009 compensation correction', '2009 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 929, 0);
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(555, '2009 compensation discharge', '2009 ß.ÊÏÌÐÄÍÓÀÝÉÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 417, 0);
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(556, '2009 compensation corrected', '2009 ß.ÊÏÒÄØÔÉÒÄÁÖËÉ ÊÏÌÐÄÍÓÀÝÉÀ', 4, null, null, 0, 1, 0, 0, 417, 0);

---- % subsidy (correction)
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(557, '2009 percent subsidy corrected', '2009 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÊÏÒÄØÔÉÒÄÁÀ', 4, null, null, 0, 1, 0, 0, 849, 0);
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(558, '2009 percent subsidy corrected', '2009 ß.ÐÒÏÝ. ÛÄÙÀÅÀÈÉÓ ÌÏáÓÍÀ', 4, null, null, 0, 1, 0, 0, 601, 0);
Insert into BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) values
(559, '2009 percent subsidy corrected', '2009 ß.ÛÄÓßÏÒÄÁÖËÉ ÐÒÏÝ ÛÄÙÀÅÀÈÉ', 4, null, null, 0, 1, 0, 0, 601, 0);
