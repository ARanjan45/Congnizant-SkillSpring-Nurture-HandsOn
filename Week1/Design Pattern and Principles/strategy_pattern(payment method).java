// EXERCISE 8: Strategy Pattern - Payment Methods
import java.util.*;
interface PaymentStrategy {
    void pay(double amount);
}
 
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    public CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via Credit Card ending in " +
            cardNumber.substring(cardNumber.length() - 4));
    }
}
 
class PayPalPayment implements PaymentStrategy {
    private String email;
    public PayPalPayment(String email) { this.email = email; }
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " via PayPal (" + email + ")");
    }
}
 
class PaymentContext {
    private PaymentStrategy strategy;
 
    public void setStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
 
    public void executePayment(double amount) {
        if (strategy == null) throw new IllegalStateException("No payment strategy set");
        strategy.pay(amount);
    }
}
 
class StrategyTest {
    public static void run() {
        System.out.println("\n=== Exercise 8: Strategy Pattern ===");
        PaymentContext context = new PaymentContext();
        context.setStrategy(new CreditCardPayment("1234567890123456"));
        context.executePayment(500.00);
 
        context.setStrategy(new PayPalPayment("user@example.com"));
        context.executePayment(200.00);
    }
}
class Main {
    public static void main(String[] args) {
        StrategyTest.run();
    }
}