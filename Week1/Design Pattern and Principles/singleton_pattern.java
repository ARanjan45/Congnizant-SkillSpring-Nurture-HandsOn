import java.util.*;
 
// EXERCISE 1: Singleton Pattern
class Logger {
    private static Logger instance;
 
    private Logger() {}
 
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
 
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
 
class SingletonTest {
    public static void run() {
        System.out.println("\n=== Exercise 1: Singleton Pattern ===");
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        logger1.log("Application started");
        logger2.log("Another log message");
        System.out.println("Same instance? " + (logger1 == logger2)); // true
    }
}

class Main {
    public static void main(String[] args) {
        SingletonTest.run();
    }
}