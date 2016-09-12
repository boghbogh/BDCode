DROP TABLE IF EXISTS calldata;
CREATE EXTERNAL TABLE calldata
  (call_id STRING,
  call_begin STRING,
  call_end STRING,
  status STRING,
  from_phone STRING,
  to_phone STRING)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.RegexSerDe'
WITH SERDEPROPERTIES (
  "input.regex" = "(.{36})(.{19})(.{19})([A-Z]+)\\s+(.{10})(.{10})"
)
LOCATION '/loudacre/calllogs';

