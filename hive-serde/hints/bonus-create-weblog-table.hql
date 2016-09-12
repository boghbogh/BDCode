-- TODO Fix all TODOs
DROP TABLE IF EXISTS weblogs;
CREATE EXTERNAL TABLE weblogs
  (ip_address TODO,
  user_id TODO,
  hit_time TODO,
  hit_page TODO)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.RegexSerDe'
WITH SERDEPROPERTIES (
  "input.regex" = "(.*) - (\\d*) \\[(.*)] \"GET /(.*) HTTP/1.0\" .*"
)
LOCATION 'TODO';

SELECT hit_page, COUNT(*) FROM weblogs GROUP BY hit_page;
SELECT hit_page, COUNT(*) as hit_count FROM weblogs WHERE user_id < TODO AND hit_page LIKE "TODO%" GROUP BY hit_page ORDER BY hit_count DESC;

