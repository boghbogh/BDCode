-- TODO: Fix all TODOs
DROP TABLE IF EXISTS mostpopularkbs;
CREATE EXTERNAL TABLE mostpopularkbs
  (kb_article STRING,
  hits INT)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LOCATION 'TODO';