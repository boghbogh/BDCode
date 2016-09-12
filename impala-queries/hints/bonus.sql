-- TODO: Fix all TODOs
-- Bonus Exercises
SELECT state, devicemodel, COUNT(*) AS statecount FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN calldetails ON (calldetails.from_phone = deviceactivations.phone_number)
WHERE status = "TODO"
GROUP BY TODO, TODO;

SELECT devicemodel, COUNT(*) AS activationcount FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN calldetails ON (calldetails.from_phone = deviceactivations.phone_number)
GROUP BY TODO
LIMIT TODO;

SELECT COUNT(*) AS total, wifi_status, gps_status, bluetooth_status FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN devicestatus ON (devicestatus.device_id = deviceactivations.device_id)
GROUP BY TODO, TODO, TODO
ORDER BY total DESC
LIMIT 100;
