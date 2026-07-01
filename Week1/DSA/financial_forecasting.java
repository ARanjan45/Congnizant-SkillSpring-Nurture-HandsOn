import java.util.HashMap;
import java.util.Map;

/*
 * Recursion: A function that calls itself with a smaller sub-problem
 * until it reaches a base case.
 * Future Value formula: FV = PV * (1 + r)^n
 *   PV = present value, r = growth rate per period, n = number of periods
 */

public class Exercise7_FinancialForecasting {

    // Recursive future value calculation - O(n) time, O(n) stack space
    public static double futureValueRecursive(double presentValue, double rate, int periods) {
        if (periods == 0) return presentValue; // base case
        return futureValueRecursive(presentValue * (1 + rate), rate, periods - 1);
    }

    // Memoized version - avoids recomputation in repeated calls - O(n) time, O(n) space
    private static Map<Integer, Double> memo = new HashMap<>();

    public static double futureValueMemo(double presentValue, double rate, int periods) {
        if (periods == 0) return presentValue;
        if (memo.containsKey(periods)) return memo.get(periods);
        double result = futureValueMemo(presentValue, rate, periods - 1) * (1 + rate);
        memo.put(periods, result);
        return result;
    }

    // Iterative version - O(n) time, O(1) space (most optimal)
    public static double futureValueIterative(double presentValue, double rate, int periods) {
        double fv = presentValue;
        for (int i = 0; i < periods; i++) fv *= (1 + rate);
        return fv;
    }

    public static void main(String[] args) {
        double presentValue = 100000; // 1,00,000
        double annualRate   = 0.08;   // 8% annual growth
        int    years        = 5;

        System.out.printf("Present Value: ₹%.2f%n", presentValue);
        System.out.printf("Annual Growth Rate: %.0f%%%n", annualRate * 100);
        System.out.printf("Period: %d years%n%n", years);

        double rv = futureValueRecursive(presentValue, annualRate, years);
        System.out.printf("Future Value (Recursive):  ₹%.2f%n", rv);

        double mv = futureValueMemo(presentValue, annualRate, years);
        System.out.printf("Future Value (Memoized):   ₹%.2f%n", mv);

        double iv = futureValueIterative(presentValue, annualRate, years);
        System.out.printf("Future Value (Iterative):  ₹%.2f%n", iv);

        System.out.println("\n--- Year-by-year Forecast ---");
        for (int y = 1; y <= years; y++) {
            System.out.printf("Year %d: ₹%.2f%n", y, futureValueRecursive(presentValue, annualRate, y));
        }

        /*
         * ANALYSIS:
         * Simple Recursion:  O(n) time, O(n) call stack - risk of StackOverflowError for large n.
         * Memoized:          O(n) time, O(n) space  - avoids redundant calls in complex trees.
         * Iterative:         O(n) time, O(1) space  - best for this linear problem.
         *
         * For compound interest (single linear chain), iterative is ideal.
         * Recursion + memoization shines in branching problems like option pricing trees.
         */
    }
}