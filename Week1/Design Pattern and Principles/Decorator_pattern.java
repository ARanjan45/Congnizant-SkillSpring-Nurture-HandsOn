// EXERCISE 5: Decorator Pattern-Notifications

import java.util.*;

interface Notifier {
    void send(String message);
}
 
class EmailNotifier implements Notifier {
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}
 
abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappedNotifier;
    public NotifierDecorator(Notifier notifier) { this.wrappedNotifier = notifier; }
    public void send(String message) { wrappedNotifier.send(message); }
}
 
class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) { super(notifier); }
    public void send(String message) {
        super.send(message);
        System.out.println("SMS: " + message);
    }
}
 
class SlackNotifierDecorator extends NotifierDecorator {
    public SlackNotifierDecorator(Notifier notifier) { super(notifier); }
    public void send(String message) {
        super.send(message);
        System.out.println("Slack: " + message);
    }
}
 
class DecoratorTest {
    public static void run() {
        System.out.println("\n=== Exercise 5: Decorator Pattern ===");
        Notifier notifier = new EmailNotifier();
        notifier = new SMSNotifierDecorator(notifier);
        notifier = new SlackNotifierDecorator(notifier);
        notifier.send("Server is down!");
    }
}

class Main {
    public static void main(String[] args) {
        DecoratorTest.run();
    }
}