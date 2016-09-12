CREATE TEMPORARY FUNCTION phonenumberformatter AS 'com.loudacre.udf.solution.PhoneNumberFormatter';

DESCRIBE FUNCTION phonenumberformatter;
DESCRIBE FUNCTION extended phonenumberformatter;


SELECT phonenumberformatter(from_phone) AS fromformatted, phonenumberformatter(to_phone) AS toformatted FROM calldetails LIMIT 1;
