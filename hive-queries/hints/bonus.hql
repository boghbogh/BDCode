-- TODO: Fix all TODOs
-- Bonus Exercises
SELECT accounts.state, devicestatus.name, COUNT(*) AS devicecount FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN devicestatus ON (devicestatus.device_id = deviceactivations.device_id)
GROUP BY TODO, TODO;

SELECT accounts.state, devicestatus.name, COUNT(*) AS devicecount FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN devicestatus ON (devicestatus.device_id = deviceactivations.device_id)
GROUP BY TODO, TODO
ORDER BY devicecount DESC LIMIT 20;

SELECT accounts.state, AVG(ambient_temp) AS ambientavg FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN devicestatus ON (devicestatus.device_id = deviceactivations.device_id)
GROUP BY TODO;
