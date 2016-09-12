	-- Bonus Exercises
SELECT state, devicemodel, COUNT(*) AS statecount FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN calldetails ON (calldetails.from_phone = deviceactivations.phone_number)
WHERE status = "FAILED"
GROUP BY state, devicemodel;

SELECT devicemodel, COUNT(*) AS activationcount FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN calldetails ON (calldetails.from_phone = deviceactivations.phone_number)
GROUP BY devicemodel
LIMIT 5;

SELECT COUNT(*) AS total, wifi_status, gps_status, bluetooth_status FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN devicestatus ON (devicestatus.device_id = deviceactivations.device_id)
GROUP BY wifi_status, gps_status, bluetooth_status
ORDER BY total DESC
LIMIT 100;
