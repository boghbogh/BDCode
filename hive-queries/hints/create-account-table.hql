-- TODO: Fix all TODOs
DROP TABLE IF EXISTS accounts;
CREATE EXTERNAL TABLE accounts
  (acct_num INT,
  acct_create_dt TIMESTAMP,
  acct_close_dt TIMESTAMP,
  first_name STRING,
  last_name STRING,
  address STRING,
  city STRING,
  state STRING,
  zipcode STRING,
  phone_number STRING,
  created TIMESTAMP,
  modified TIMESTAMP)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LOCATION 'TODO';
