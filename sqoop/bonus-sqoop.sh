#!/bin/bash

# Tab delimited
sqoop import \
--connect jdbc:mysql://dev.loudacre.com/loudacre \
--username training --password training \
--table accounts \
--target-dir account-tabbed \
--fields-terminated-by "\t"

# Using sequence file
sqoop import \
--connect jdbc:mysql://dev.loudacre.com/loudacre \
--username training --password training \
--table accounts \
--target-dir account-seq \
--as-sequencefile

# Using Avro
sqoop import \
--connect jdbc:mysql://dev.loudacre.com/loudacre \
--username training --password training \
--table accounts \
--target-dir account-avro \
--as-avrodatafile

# Using a custom query
sqoop import \
--connect jdbc:mysql://dev.loudacre.com/loudacre \
--username training --password training \
--table accounts \
--target-dir account-active-ca \
--where 'state = "CA" and acct_close_dt IS NULL'
