DROP TABLE IF EXISTS weblogs;
CREATE EXTERNAL TABLE weblogs
  (ip_address STRING,
  user_id STRING,
  hit_time STRING,
  hit_page STRING)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.RegexSerDe'
WITH SERDEPROPERTIES (
  "input.regex" = "(.*) - (\\d*) \\[(.*)] \"GET /(.*) HTTP/1.0\" .*"
)
LOCATION '/loudacre/weblogs';

SELECT hit_page, COUNT(*) FROM weblogs GROUP BY hit_page;
SELECT hit_page, COUNT(*) as hit_count FROM weblogs WHERE user_id < 1000 AND hit_page LIKE "KBDOC-%" GROUP BY hit_page ORDER BY hit_count DESC;

