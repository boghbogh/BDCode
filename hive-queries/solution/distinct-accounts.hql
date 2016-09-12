-- Distinct Accounts
SELECT DISTINCT accounts.acct_num FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN devicestatus ON (devicestatus.device_id = deviceactivations.device_id)
WHERE accounts.state = "CA" AND devicestatus.name = "Sorrento F41L";
