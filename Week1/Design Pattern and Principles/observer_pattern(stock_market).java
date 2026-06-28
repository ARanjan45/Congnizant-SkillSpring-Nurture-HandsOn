// EXERCISE 7: Observer Pattern - Stock Market
import java.util.*;
interface Observer {
    void update(String stockName, double price);
}
 
interface Stock {
    void registerObserver(Observer o);
    void deregisterObserver(Observer o);
    void notifyObservers();
}
 
class StockMarket implements Stock {
    private List<Observer> observers = new ArrayList<>();
    private String stockName;
    private double price;
 
    public void setStock(String stockName, double price) {
        this.stockName = stockName;
        this.price = price;
        notifyObservers();
    }
 
    public void registerObserver(Observer o) { observers.add(o); }
    public void deregisterObserver(Observer o) { observers.remove(o); }
    public void notifyObservers() {
        for (Observer o : observers) o.update(stockName, price);
    }
}
 
class MobileApp implements Observer {
    public void update(String stockName, double price) {
        System.out.println("MobileApp: " + stockName + " = $" + price);
    }
}
 
class WebApp implements Observer {
    public void update(String stockName, double price) {
        System.out.println("WebApp: " + stockName + " = $" + price);
    }
}
 
class ObserverTest {
    public static void run() {
        System.out.println("\n=== Exercise 7: Observer Pattern ===");
        StockMarket market = new StockMarket();
        MobileApp mobile = new MobileApp();
        WebApp web = new WebApp();
 
        market.registerObserver(mobile);
        market.registerObserver(web);
        market.setStock("AAPL", 189.50);
 
        market.deregisterObserver(web);
        market.setStock("GOOGL", 175.20); 
    }
}
class Main {
    public static void main(String[] args) {
        ObserverTest.run();
    }
}