DROP TABLE IF EXISTS devicestatus;
CREATE EXTERNAL TABLE devicestatus
  (time BIGINT,
  name STRING,
  device_id STRING,
  device_temp INT,
  ambient_temp INT,
  battery_pct INT,
  signal_pct INT,
  cpu_load INT,
  ram_usage INT,
  gps_status STRING,
  bluetooth_status STRING,
  wifi_status STRING,
  longitude FLOAT,
  latitude FLOAT)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LOCATION '/loudacre/devicedata';
