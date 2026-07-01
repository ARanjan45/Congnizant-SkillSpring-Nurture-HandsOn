-- ============================================================
-- EXERCISE 3: Stored Procedures
-- ============================================================

-- ----------------------------------------------------------
-- Scenario 1: ProcessMonthlyInterest - 1% interest on savings accounts
-- ----------------------------------------------------------
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest IS
BEGIN
    UPDATE Accounts
    SET    Balance      = Balance * 1.01,
           LastModified = SYSDATE
    WHERE  AccountType = 'Savings';

    DBMS_OUTPUT.PUT_LINE(SQL%ROWCOUNT || ' savings account(s) updated with 1% interest.');
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
END;
/

-- Test
BEGIN
    ProcessMonthlyInterest;
END;
/


-- ----------------------------------------------------------
-- Scenario 2: UpdateEmployeeBonus - add bonus % to a department
-- ----------------------------------------------------------
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(
    p_department    IN VARCHAR2,
    p_bonus_percent IN NUMBER
) IS
BEGIN
    UPDATE Employees
    SET    Salary = Salary + (Salary * p_bonus_percent / 100)
    WHERE  Department = p_department;

    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No employees found in department: ' || p_department);
    ELSE
        DBMS_OUTPUT.PUT_LINE(SQL%ROWCOUNT || ' employee(s) in ' || p_department ||
                             ' received ' || p_bonus_percent || '% bonus.');
    END IF;
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
END;
/

-- Test
BEGIN
    UpdateEmployeeBonus('IT', 15);
    UpdateEmployeeBonus('HR', 10);
END;
/


-- ----------------------------------------------------------
-- Scenario 3: TransferFunds - transfer between accounts
-- ----------------------------------------------------------
CREATE OR REPLACE PROCEDURE TransferFunds(
    p_from_account IN NUMBER,
    p_to_account   IN NUMBER,
    p_amount       IN NUMBER
) IS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM   Accounts
    WHERE  AccountID = p_from_account
    FOR UPDATE;

    IF v_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20003, 'Insufficient balance. Available: $' || v_balance);
    END IF;

    UPDATE Accounts SET Balance = Balance - p_amount WHERE AccountID = p_from_account;
    UPDATE Accounts SET Balance = Balance + p_amount WHERE AccountID = p_to_account;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer of $' || p_amount || ' from Account ' ||
                         p_from_account || ' to Account ' || p_to_account || ' successful.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: Account not found.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
END;
/

-- Test
BEGIN
    TransferFunds(1, 2, 100);
    TransferFunds(2, 1, 99999); -- will fail
END;
/