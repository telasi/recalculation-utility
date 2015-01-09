begin
  for rec IN (select * from bs.billoperation)
  loop
    update recut.billoperation set name_bs = rec.billopername
    where id = rec.billoperkey;
  end loop;
end;
