-- TODO: Fix all TODOs
-- Joins
SELECT * FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN devicestatus ON (devicestatus.device_id = deviceactivations.device_id)
LIMIT TODO;
