DROP FUNCTION IF EXISTS PHONENUMBERFORMATTER(string);

CREATE FUNCTION PHONENUMBERFORMATTER(string) returns string location '/loudacre/udfs/customudf-1.0-SNAPSHOT.jar' symbol='com.loudacre.udf.solution.PhoneNumberFormatter';

SELECT PHONENUMBERFORMATTER(from_phone) FROM calldetails LIMIT 1;
