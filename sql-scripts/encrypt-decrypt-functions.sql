create or replace FUNCTION recutil_encrypt(input_string IN VARCHAR2) RETURN VARCHAR2 AS
  enc_str varchar2(1000);
  enc_key varchar2(300);
  input CHAR(16);
BEGIN
  IF input_string IS NOT NULL
  THEN
    input := input_string;
        enc_key := 'Billing_Aes_Telasi_Syst';
        dbms_obfuscation_toolkit.Des3Encrypt(
          input_string => input,
          key_string => enc_key ,
          encrypted_string => enc_str);
    end if;

    return  enc_str;
END recutil_encrypt;
/

create or replace FUNCTION recutil_encrypt(input_string IN VARCHAR2) RETURN VARCHAR2 AS
  enc_str varchar2(1000);
  enc_key varchar2(300);
  input CHAR(16);
BEGIN
  IF input_string IS NOT NULL
  THEN
    input := input_string;
        enc_key := 'Billing_Aes_Telasi_Syst';
        dbms_obfuscation_toolkit.Des3Encrypt(
          input_string => input,
          key_string => enc_key ,
          encrypted_string => enc_str);
    end if;

    return  enc_str;
END recutil_encrypt;
/