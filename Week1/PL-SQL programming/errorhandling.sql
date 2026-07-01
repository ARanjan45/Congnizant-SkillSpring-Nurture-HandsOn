-- ============================================================
-- EXERCISE 2: Error Handling
-- ============================================================

-- ----------------------------------------------------------
-- Scenario 1: SafeTransferFunds - transfer with rollback on error
-- ----------------------------------------------------------
CREATE OR REPLACE PROCEDURE SafeTransferFunds(
    p_from_account IN NUMBER,
    p_to_account   IN NUMBER,
    p_amount       IN NUMBER
) IS
    v_balance NUMBER;
BEGIN
    -- Check source balance
    SELECT Balance INTO v_balance
    FROM   Accounts
    WHERE  AccountID = p_from_account
    FOR UPDATE;

    IF v_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds in account ' || p_from_account);
    END IF;

    -- Debit source
    UPDATE Accounts SET Balance = Balance - p_amount
    WHERE  AccountID = p_from_account;

    -- Credit destination
    UPDATE Accounts SET Balance = Balance + p_amount
    WHERE  AccountID = p_to_account;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer of $' || p_amount || ' successful.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: One or both account IDs do not exist.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
END;
/

-- Test
BEGIN
    SafeTransferFunds(1, 2, 200);   -- valid transfer
    SafeTransferFunds(1, 2, 99999); -- insufficient funds
END;
/


-- ----------------------------------------------------------
-- Scenario 2: UpdateSalary - raise salary, handle missing employee
-- ----------------------------------------------------------
CREATE OR REPLACE PROCEDURE UpdateSalary(
    p_employee_id   IN NUMBER,
    p_percentage    IN NUMBER
) IS
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count FROM Employees WHERE EmployeeID = p_employee_id;

    IF v_count = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Employee ID ' || p_employee_id || ' not found.');
    END IF;

    UPDATE Employees
    SET    Salary = Salary + (Salary * p_percentage / 100)
    WHERE  EmployeeID = p_employee_id;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Salary updated for Employee ID: ' || p_employee_id);

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
END;
/

-- Test
BEGIN
    UpdateSalary(1, 10);   -- valid
    UpdateSalary(999, 10); -- invalid employee
END;
/


-- ----------------------------------------------------------
-- Scenario 3: AddNewCustomer - insert with duplicate ID check
-- ----------------------------------------------------------
CREATE OR REPLACE PROCEDURE AddNewCustomer(
    p_customer_id IN NUMBER,
    p_name        IN VARCHAR2,
    p_dob         IN DATE,
    p_balance     IN NUMBER
) IS
BEGIN
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (p_customer_id, p_name, p_dob, p_balance, SYSDATE);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Customer added: ' || p_name);

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Customer with ID ' || p_customer_id || ' already exists.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('ERROR: ' || SQLERRM);
END;
/

-- Test
BEGIN
    AddNewCustomer(3, 'Ravi Kumar', TO_DATE('1995-08-10', 'YYYY-MM-DD'), 5000);
    AddNewCustomer(1, 'Duplicate Test', SYSDATE, 1000); -- duplicate ID
END;
/