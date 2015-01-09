/**
 * ეს ოპერაციები დაემატა გადათვლის უტილიტის ბაზაში 2008 წლის 8 მაისიდან.
 */

INSERT INTO BILLOPERATION (
	ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID
) VALUES (
	150, 'xazina', 'áÀÆÉÍÀ', 3, '01-APR-07', NULL, 0, 0, 0, 0, 6, 2
);
/

INSERT INTO BILLOPERATION (
	ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID
) VALUES (
	80, 'el.energia.das', 'ÄË.ÄÍÄÒÂÉÀ-ÃÀÓ.', 4, '01-APR-07', NULL, 0, 0, 0, 0, 5100, 0
);
/

INSERT INTO BILLOPERATION (
	ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID
) VALUES (
	81, 'el.energia.water', 'ÄË.ÄÍÄÒÂÉÀ-ßÚÀËÉ', 4, '01-APR-07', NULL, 0, 0, 0, 0, 5101, 0
);
/

INSERT INTO BILLOPERATION (
	ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID
) VALUES (
	82, 'dasuftaveba-wyali', 'ÃÀÓÖ×ÈÀÅÄÁÀ-ßÚÀËÉ', 4, '01-APR-07', NULL, 0, 0, 0, 0, 5102, 0
);
/

INSERT INTO BILLOPERATION (
	ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID
) VALUES (
	540, 'ltolvileli.chamowera2007', 'ËÔÏË.ÖÉÌÄÃÏ ÃÀÅÀËÉÀÍÄÁÉÓ ÜÀÌÏßÄÒÀ 01012007', 4, '01-MAY-08', NULL, 0, 0, 0, 0, 5200, 0
);
/

INSERT INTO BILLOPERATION (
	ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID
) VALUES (
	541, 'ltolvileli.chamowerilis.agdgena2007', 'ËÔÏË.ÜÀÌÏßÄÒÉËÉ ÃÀÅÀËÉÀÍÄÁÉÓ ÀÙÃÂÄÍÀ 2007', 4, '01-MAY-08', NULL, 0, 0, 0, 0, 5201, 0
);
/

INSERT INTO BILLOPERATION (
	ID, NAME, NAME_BS, TYPE_ID, D1, D2, REQ_CYCLE, REQ_READING, REQ_KWH, REQ_GEL, SEQ, DIFF_GROUP_ID
) VALUES (
	542, 'ltolvileli.chamoweilis.agdgena.2008', 'ËÔÏË.ÜÀÌÏßÄÒÉËÉ ÃÀÅÀËÉÀÍÄÁÉÓ ÀÙÃÂÄÍÀ 2008', 4, '01-MAY-08', NULL, 0, 0, 0, 0, 5202, 0
);
/

COMMIT;
/