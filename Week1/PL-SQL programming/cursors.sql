-- ============================================================
-- EXERCISE 6: Cursors
-- ============================================================

-- ----------------------------------------------------------
-- Scenario 1: GenerateMonthlyStatements
-- Fetch all transactions for the current month per customer
-- ----------------------------------------------------------
DECLARE
    CURSOR GenerateMonthlyStatements IS
        SELECT c.Name, t.TransactionID, t.Amount, t.TransactionType, t.TransactionDate
        FROM   Transactions t
        JOIN   Accounts a ON t.AccountID = a.AccountID
        JOIN   Customers c ON a.CustomerID = c.CustomerID
        WHERE  TRUNC(t.TransactionDate, 'MM') = TRUNC(SYSDATE, 'MM')
        ORDER  BY c.Name, t.TransactionDate;

    v_current_customer VARCHAR2(100) := '';
BEGIN
    FOR rec IN GenerateMonthlyStatements LOOP
        IF rec.Name != v_current_customer THEN
            DBMS_OUTPUT.PUT_LINE('--- Statement for: ' || rec.Name || ' ---');
            v_current_customer := rec.Name;
        END IF;
        DBMS_OUTPUT.PUT_LINE('  TxnID: ' || rec.TransactionID ||
                             ' | ' || rec.TransactionType ||
                             ' | $' || rec.Amount ||
                             ' | ' || TO_CHAR(rec.TransactionDate, 'DD-MON-YYYY'));
    END LOOP;
END;
/


-- ----------------------------------------------------------
-- Scenario 2: ApplyAnnualFee
-- Deduct annual maintenance fee from all accounts
-- ----------------------------------------------------------
DECLARE
    CURSOR ApplyAnnualFee IS
        SELECT AccountID, Balance FROM Accounts FOR UPDATE;

    v_fee CONSTANT NUMBER := 50; -- $50 annual fee
BEGIN
    FOR rec IN ApplyAnnualFee LOOP
        IF rec.Balance >= v_fee THEN
            UPDATE Accounts
            SET    Balance      = Balance - v_fee,
                   LastModified = SYSDATE
            WHERE  CURRENT OF ApplyAnnualFee;

            DBMS_OUTPUT.PUT_LINE('Account ' || rec.AccountID ||
                                 ': Fee of $' || v_fee || ' deducted.');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Account ' || rec.AccountID ||
                                 ': Insufficient balance for fee. Skipped.');
        END IF;
    END LOOP;
    COMMIT;
END;
/


-- ----------------------------------------------------------
-- Scenario 3: UpdateLoanInterestRates
-- New policy: reduce interest by 0.5% for loans > $3000
-- ----------------------------------------------------------
DECLARE
    CURSOR UpdateLoanInterestRates IS
        SELECT LoanID, LoanAmount, InterestRate FROM Loans FOR UPDATE;
BEGIN
    FOR rec IN UpdateLoanInterestRates LOOP
        IF rec.LoanAmount > 3000 THEN
            UPDATE Loans
            SET    InterestRate = InterestRate - 0.5
            WHERE  CURRENT OF UpdateLoanInterestRates;

            DBMS_OUTPUT.PUT_LINE('Loan ' || rec.LoanID ||
                                 ': Rate reduced by 0.5%. New Rate: ' ||
                                 (rec.InterestRate - 0.5) || '%');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Loan ' || rec.LoanID ||
                                 ': No change. Amount below threshold.');
        END IF;
    END LOOP;
    COMMIT;
END;
/