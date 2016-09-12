CREATE TEMPORARY FUNCTION phonenumberformatterbonus AS 'com.loudacre.udfbonus.solution.PhoneNumberFormatter';

DESCRIBE FUNCTION phonenumberformatterbonus;
DESCRIBE FUNCTION extended phonenumberformatterbonus;


SELECT phonenumberformatterbonus(from_phone, "(XXX) XXX-XXXX") AS fromformatted, phonenumberformatterbonus(to_phone, "XXX-XXX-XXXX") AS toformatted FROM calldetails LIMIT 1;

