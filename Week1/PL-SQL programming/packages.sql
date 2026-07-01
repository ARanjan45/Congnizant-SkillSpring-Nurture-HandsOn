-- ============================================================
-- EXERCISE 7: Packages
-- ============================================================

-- ----------------------------------------------------------
-- Scenario 1: CustomerManagement Package
-- ----------------------------------------------------------

-- Package Specification
CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddNewCustomer(
        p_id      IN NUMBER,
        p_name    IN VARCHAR2,
        p_dob     IN DATE,
        p_balance IN NUMBER
    );

    PROCEDURE UpdateCustomerDetails(
        p_id      IN NUMBER,
        p_name    IN VARCHAR2,
        p_balance IN NUMBER
    );

    FUNCTION GetCustomerBalance(p_id IN NUMBER) RETURN NUMBER;
END CustomerManagement;
/

-- Package Body
CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddNewCustomer(
        p_id IN NUMBER, p_name IN VARCHAR2, p_dob IN DATE, p_balance IN NUMBER
    ) IS
    BEGIN
        INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
        VALUES (p_id, p_name, p_dob, p_balance, SYSDATE);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Customer added: ' || p_name);
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Customer ID ' || p_id || ' already exists.');
    END;

    PROCEDURE UpdateCustomerDetails(
        p_id IN NUMBER, p_name IN VARCHAR2, p_balance IN NUMBER
    ) IS
    BEGIN
        UPDATE Customers SET Name = p_name, Balance = p_balance, LastModified = SYSDATE
        WHERE  CustomerID = p_id;
        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Customer ID ' || p_id || ' not found.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Customer ' || p_id || ' updated.');
        END IF;
    END;

    FUNCTION GetCustomerBalance(p_id IN NUMBER) RETURN NUMBER IS
        v_balance NUMBER;
    BEGIN
        SELECT Balance INTO v_balance FROM Customers WHERE CustomerID = p_id;
        RETURN v_balance;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN RETURN NULL;
    END;

END CustomerManagement;
/

-- Test CustomerManagement
BEGIN
    CustomerManagement.AddNewCustomer(4, 'Priya Sharma', TO_DATE('1998-06-01','YYYY-MM-DD'), 8000);
    CustomerManagement.UpdateCustomerDetails(4, 'Priya S.', 9000);
    DBMS_OUTPUT.PUT_LINE('Balance: $' || CustomerManagement.GetCustomerBalance(4));
END;
/


-- ----------------------------------------------------------
-- Scenario 2: EmployeeManagement Package
-- ----------------------------------------------------------

CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE HireEmployee(
        p_id         IN NUMBER,
        p_name       IN VARCHAR2,
        p_position   IN VARCHAR2,
        p_salary     IN NUMBER,
        p_department IN VARCHAR2
    );

    PROCEDURE UpdateEmployeeDetails(
        p_id       IN NUMBER,
        p_position IN VARCHAR2,
        p_salary   IN NUMBER
    );

    FUNCTION CalculateAnnualSalary(p_id IN NUMBER) RETURN NUMBER;
END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee(
        p_id IN NUMBER, p_name IN VARCHAR2, p_position IN VARCHAR2,
        p_salary IN NUMBER, p_department IN VARCHAR2
    ) IS
    BEGIN
        INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
        VALUES (p_id, p_name, p_position, p_salary, p_department, SYSDATE);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Employee hired: ' || p_name);
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Employee ID ' || p_id || ' already exists.');
    END;

    PROCEDURE UpdateEmployeeDetails(
        p_id IN NUMBER, p_position IN VARCHAR2, p_salary IN NUMBER
    ) IS
    BEGIN
        UPDATE Employees SET Position = p_position, Salary = p_salary
        WHERE  EmployeeID = p_id;
        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Employee ID ' || p_id || ' not found.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Employee ' || p_id || ' details updated.');
        END IF;
    END;

    FUNCTION CalculateAnnualSalary(p_id IN NUMBER) RETURN NUMBER IS
        v_salary NUMBER;
    BEGIN
        SELECT Salary INTO v_salary FROM Employees WHERE EmployeeID = p_id;
        RETURN v_salary * 12;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN RETURN NULL;
    END;

END EmployeeManagement;
/

-- Test EmployeeManagement
BEGIN
    EmployeeManagement.HireEmployee(3, 'Neha Gupta', 'Tester', 50000, 'QA');
    EmployeeManagement.UpdateEmployeeDetails(3, 'Senior Tester', 60000);
    DBMS_OUTPUT.PUT_LINE('Annual Salary: $' || EmployeeManagement.CalculateAnnualSalary(3));
END;
/


-- ----------------------------------------------------------
-- Scenario 3: AccountOperations Package
-- ----------------------------------------------------------

CREATE OR REPLACE PACKAGE AccountOperations AS
    PROCEDURE OpenAccount(
        p_account_id   IN NUMBER,
        p_customer_id  IN NUMBER,
        p_account_type IN VARCHAR2,
        p_balance      IN NUMBER
    );

    PROCEDURE CloseAccount(p_account_id IN NUMBER);

    FUNCTION GetTotalBalance(p_customer_id IN NUMBER) RETURN NUMBER;
END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount(
        p_account_id IN NUMBER, p_customer_id IN NUMBER,
        p_account_type IN VARCHAR2, p_balance IN NUMBER
    ) IS
    BEGIN
        INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (p_account_id, p_customer_id, p_account_type, p_balance, SYSDATE);
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Account ' || p_account_id || ' opened for Customer ' || p_customer_id);
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('ERROR: Account ID ' || p_account_id || ' already exists.');
    END;

    PROCEDURE CloseAccount(p_account_id IN NUMBER) IS
    BEGIN
        DELETE FROM Accounts WHERE AccountID = p_account_id;
        IF SQL%ROWCOUNT = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Account ID ' || p_account_id || ' not found.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Account ' || p_account_id || ' closed successfully.');
        END IF;
    END;

    FUNCTION GetTotalBalance(p_customer_id IN NUMBER) RETURN NUMBER IS
        v_total NUMBER;
    BEGIN
        SELECT NVL(SUM(Balance), 0) INTO v_total
        FROM   Accounts
        WHERE  CustomerID = p_customer_id;
        RETURN v_total;
    END;

END AccountOperations;
/

-- Test AccountOperations
BEGIN
    AccountOperations.OpenAccount(3, 1, 'Savings', 5000);
    DBMS_OUTPUT.PUT_LINE('Total Balance for Customer 1: $' ||
                          AccountOperations.GetTotalBalance(1));
    AccountOperations.CloseAccount(3);
END;
/