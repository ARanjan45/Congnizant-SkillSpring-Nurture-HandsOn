-- ============================================================
-- EXERCISE 4: Functions
-- ============================================================

-- ----------------------------------------------------------
-- Scenario 1: CalculateAge - returns customer age from DOB
-- ----------------------------------------------------------
CREATE OR REPLACE FUNCTION CalculateAge(p_dob IN DATE)
RETURN NUMBER IS
    v_age NUMBER;
BEGIN
    v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
    RETURN v_age;
END;
/

-- Test
DECLARE
    v_dob DATE := TO_DATE('1960-03-20', 'YYYY-MM-DD');
BEGIN
    DBMS_OUTPUT.PUT_LINE('Age: ' || CalculateAge(v_dob) || ' years');
END;
/

-- Use in query
SELECT Name, DOB, CalculateAge(DOB) AS Age FROM Customers;


-- ----------------------------------------------------------
-- Scenario 2: CalculateMonthlyInstallment (EMI)
-- Formula: EMI = P * r * (1+r)^n / ((1+r)^n - 1)
--   P = principal, r = monthly rate, n = total months
-- ----------------------------------------------------------
CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment(
    p_loan_amount   IN NUMBER,
    p_annual_rate   IN NUMBER,  -- in percent e.g. 8 for 8%
    p_duration_years IN NUMBER
) RETURN NUMBER IS
    v_monthly_rate NUMBER;
    v_months       NUMBER;
    v_emi          NUMBER;
BEGIN
    v_monthly_rate := (p_annual_rate / 100) / 12;
    v_months       := p_duration_years * 12;

    IF v_monthly_rate = 0 THEN
        v_emi := p_loan_amount / v_months;
    ELSE
        v_emi := p_loan_amount
                 * v_monthly_rate
                 * POWER(1 + v_monthly_rate, v_months)
                 / (POWER(1 + v_monthly_rate, v_months) - 1);
    END IF;

    RETURN ROUND(v_emi, 2);
END;
/

-- Test
DECLARE
    v_emi NUMBER;
BEGIN
    v_emi := CalculateMonthlyInstallment(500000, 8, 5);
    DBMS_OUTPUT.PUT_LINE('Monthly EMI: $' || v_emi);
END;
/


-- ----------------------------------------------------------
-- Scenario 3: HasSufficientBalance - returns TRUE/FALSE
-- ----------------------------------------------------------
CREATE OR REPLACE FUNCTION HasSufficientBalance(
    p_account_id IN NUMBER,
    p_amount     IN NUMBER
) RETURN VARCHAR2 IS         -- Oracle PL/SQL uses VARCHAR2 instead of BOOLEAN for SQL use
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM   Accounts
    WHERE  AccountID = p_account_id;

    IF v_balance >= p_amount THEN
        RETURN 'TRUE';
    ELSE
        RETURN 'FALSE';
    END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 'FALSE'; -- account doesn't exist
END;
/

-- Test
DECLARE
    v_result VARCHAR2(5);
BEGIN
    v_result := HasSufficientBalance(1, 500);
    DBMS_OUTPUT.PUT_LINE('Account 1 has sufficient balance for $500? ' || v_result);

    v_result := HasSufficientBalance(1, 999999);
    DBMS_OUTPUT.PUT_LINE('Account 1 has sufficient balance for $999999? ' || v_result);
END;
/