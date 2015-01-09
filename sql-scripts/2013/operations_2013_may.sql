---- charge (correction)
INSERT INTO BILLOPERATION (ID,NAME,NAME_BS,TYPE_ID,D1,D2,REQ_CYCLE,REQ_READING,REQ_KWH,REQ_GEL,SEQ,DIFF_GROUP_ID) VALUES
(120, 'meria correction', 'ÊÏÌÐÄÓÀÝÉÀ ØÓÄËÉÓ ÌÉÄÒÈÄÁÀÆÄ', 4, null, null, 0, 0, 0, 0, 100001, 0);

-- sync OPERATIONS
begin
  for rec IN (select * from bs.billoperation)
  loop
    update billoperation set name_bs = rec.billopername
    where id = rec.billoperkey;
  end loop;
end;