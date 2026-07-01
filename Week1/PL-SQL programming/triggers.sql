-- ============================================================
-- EXERCISE 5: Triggers
-- ============================================================

-- AuditLog table needed for Scenario 2
CREATE TABLE AuditLog (
    LogID           NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    TransactionID   NUMBER,
    AccountID       NUMBER,
    Amount          NUMBER,
    TransactionType VARCHAR2(10),
    LogDate         DATE
);


-- ----------------------------------------------------------
-- Scenario 1: UpdateCustomerLastModified
-- Updates LastModified on every customer record update
-- ----------------------------------------------------------
CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
BEFORE UPDATE ON Customers
FOR EACH ROW
BEGIN
    :NEW.LastModified := SYSDATE;
END;
/

-- Test
UPDATE Customers SET Balance = Balance + 100 WHERE CustomerID = 1;
SELECT CustomerID, Name, LastModified FROM Customers WHERE CustomerID = 1;


-- ----------------------------------------------------------
-- Scenario 2: LogTransaction
-- Inserts an audit record whenever a new transaction is added
-- ----------------------------------------------------------
CREATE OR REPLACE TRIGGER LogTransaction
AFTER INSERT ON Transactions
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (TransactionID, AccountID, Amount, TransactionType, LogDate)
    VALUES (:NEW.TransactionID, :NEW.AccountID, :NEW.Amount, :NEW.TransactionType, SYSDATE);
END;
/

-- Test
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (3, 1, SYSDATE, 500, 'Deposit');

SELECT * FROM AuditLog;


-- ----------------------------------------------------------
-- Scenario 3: CheckTransactionRules
-- Prevents invalid withdrawals (> balance) and negative deposits
-- ----------------------------------------------------------
CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
    v_balance NUMBER;
BEGIN
    -- Deposits must be positive
    IF :NEW.TransactionType = 'Deposit' AND :NEW.Amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20010, 'Deposit amount must be positive.');
    END IF;

    -- Withdrawals must not exceed balance
    IF :NEW.TransactionType = 'Withdrawal' THEN
        SELECT Balance INTO v_balance
        FROM   Accounts
        WHERE  AccountID = :NEW.AccountID;

        IF :NEW.Amount > v_balance THEN
            RAISE_APPLICATION_ERROR(-20011,
                'Withdrawal of $' || :NEW.Amount ||
                ' exceeds available balance of $' || v_balance);
        END IF;
    END IF;
END;
/

-- Test valid withdrawal
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (4, 1, SYSDATE, 100, 'Withdrawal');

-- Test invalid withdrawal (will raise error)
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (5, 1, SYSDATE, 999999, 'Withdrawal');