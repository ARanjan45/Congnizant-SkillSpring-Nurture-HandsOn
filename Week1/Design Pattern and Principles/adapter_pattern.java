// EXERCISE 4: Adapter Pattern-Payment Gateways
import java.util.*;
interface PaymentProcessor {
    void processPayment(double amount);
}
 
// Adaptees (third-party gateways with their own APIs)
class PayPalGateway {
    public void makePayment(double amount) {
        System.out.println("PayPal: Processing payment of $" + amount);
    }
}
 
class StripeGateway {
    public void charge(double amount) {
        System.out.println("Stripe: Charging $" + amount);
    }
}
 
// Adapters
class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway payPal = new PayPalGateway();
    public void processPayment(double amount) { payPal.makePayment(amount); }
}
 
class StripeAdapter implements PaymentProcessor {
    private StripeGateway stripe = new StripeGateway();
    public void processPayment(double amount) { stripe.charge(amount); }
}
 
class AdapterTest {
    public static void run() {
        System.out.println("\n=== Exercise 4: Adapter Pattern ===");
        PaymentProcessor paypal = new PayPalAdapter();
        PaymentProcessor stripe = new StripeAdapter();
        paypal.processPayment(150.00);
        stripe.processPayment(250.00);
    }
}
 
class Main {
    public static void main(String[] args) {
        AdapterTest.run();
    }
}