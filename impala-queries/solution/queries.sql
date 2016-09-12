SELECT * FROM accounts WHERE acct_num = 42;
SELECT COUNT(*) AS statecount FROM accounts WHERE state = "OR";

SELECT accounts.state, COUNT(*) AS statecount FROM accounts
JOIN deviceactivations ON (accounts.acct_num = deviceactivations.acct_num)
GROUP BY accounts.state;
