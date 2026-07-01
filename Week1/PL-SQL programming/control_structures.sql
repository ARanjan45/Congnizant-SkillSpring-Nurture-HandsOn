-- EXERCISE 1: Control Structures
-- Schema: Customers(CustomerID, Name, DOB, Balance, LastModified)
--         Loans(LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)

-- ----------------------------------------------------------
-- Scenario 1: Apply 1% discount on loan interest rate
--             for customers aged above 60
-- ----------------------------------------------------------
DECLARE
    v_age NUMBER;
BEGIN
    FOR rec IN (
        SELECT c.CustomerID, c.Name, c.DOB, l.LoanID, l.InterestRate
        FROM   Customers c
        JOIN   Loans l ON c.CustomerID = l.CustomerID
    ) LOOP
        v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, rec.DOB) / 12);

        IF v_age > 60 THEN
            UPDATE Loans
            SET    InterestRate = InterestRate - 1
            WHERE  LoanID = rec.LoanID
              AND  InterestRate > 1; -- prevent negative rate

            DBMS_OUTPUT.PUT_LINE('Discount applied for ' || rec.Name ||
                                 ' (Age: ' || v_age || ') on LoanID: ' || rec.LoanID);
        ELSE
            DBMS_OUTPUT.PUT_LINE('No discount for ' || rec.Name ||
                                 ' (Age: ' || v_age || ')');
        END IF;
    END LOOP;
    COMMIT;
END;
/


-- ----------------------------------------------------------
-- Scenario 2: Set IsVIP flag for customers with balance > $10,000
-- Note: Adds a conceptual VIP flag via DBMS_OUTPUT
--       (Add IsVIP VARCHAR2(5) column to Customers if needed)
-- ----------------------------------------------------------
DECLARE
    v_is_vip VARCHAR2(5);
BEGIN
    FOR rec IN (SELECT CustomerID, Name, Balance FROM Customers) LOOP
        IF rec.Balance > 10000 THEN
            v_is_vip := 'TRUE';
            -- UPDATE Customers SET IsVIP = 'TRUE' WHERE CustomerID = rec.CustomerID;
        ELSE
            v_is_vip := 'FALSE';
        END IF;
        DBMS_OUTPUT.PUT_LINE('Customer: ' || rec.Name ||
                             ' | Balance: ' || rec.Balance ||
                             ' | IsVIP: ' || v_is_vip);
    END LOOP;
    COMMIT;
END;
/


-- ----------------------------------------------------------
-- Scenario 3: Send reminders for loans due within 30 days
-- ----------------------------------------------------------
BEGIN
    FOR rec IN (
        SELECT l.LoanID, c.Name, l.EndDate
        FROM   Loans l
        JOIN   Customers c ON l.CustomerID = c.CustomerID
        WHERE  l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('REMINDER: Dear ' || rec.Name ||
                             ', your Loan ID ' || rec.LoanID ||
                             ' is due on ' || TO_CHAR(rec.EndDate, 'DD-MON-YYYY') ||
                             '. Please ensure timely repayment.');
    END LOOP;
END;
/