DROP TABLE IF EXISTS calldatafail;
CREATE TABLE calldatafail
AS
SELECT call_id,
  call_begin,
  call_end,
  status,
  from_phone,
  to_phone 
FROM calldata
WHERE status <> 'SUCCESS';

