-- TODO: Fix all TODOs
DROP TABLE IF EXISTS devicestatus;
CREATE EXTERNAL TABLE devicestatus
  (time TODO,
  name TODO,
  device_id TODO,
  device_temp TODO,
  ambient_temp TODO,
  battery_pct TODO,
  signal_pct TODO,
  cpu_load TODO,
  ram_usage TODO,
  gps_status TODO,
  bluetooth_status TODO,
  wifi_status TODO,
  longitude TODO,
  latitude TODO)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LOCATION 'TODO';
