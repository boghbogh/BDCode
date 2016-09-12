-- TODO: Fix all TODOs
-- Status for NV
SELECT status, COUNT(*) AS nevadafailures FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
JOIN calldetails ON (calldetails.from_phone = deviceactivations.phone_number)
WHERE accounts.state = "TODO" GROUP BY TODO;
