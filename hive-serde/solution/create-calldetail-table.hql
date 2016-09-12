DROP TABLE IF EXISTS calldetails;
CREATE TABLE calldetails
LOCATION
  '/loudacre/calldetails'
AS
SELECT call_id,
  from_utc_timestamp(call_begin, "UTC") as call_begin,
  from_utc_timestamp(call_end, "UTC") as call_end,
  status,
  from_phone,
  to_phone 
FROM calldata;

