-- TODO: Fix all TODOs
SELECT * FROM accounts WHERE acct_num = TODO;
SELECT COUNT(*) AS statecount FROM accounts WHERE state = "TODO";

SELECT accounts.state, COUNT(*) AS statecount FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
GROUP BY TODO;
