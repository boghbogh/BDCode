-- TODO: Fix all TODOs
-- Distinct Accounts
SELECT DISTINCT accounts.acct_num FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN devicestatus ON (devicestatus.device_id = deviceactivations.device_id)
WHERE accounts.state = "TODO" AND devicestatus.name = "TODO";
