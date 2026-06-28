// EXERCISE 6: Proxy Pattern - Image Viewer

import java.util.*;
interface Image {
    void display();
}
 
class RealImage implements Image {
    private String filename;
 
    public RealImage(String filename) {
        this.filename = filename;
        loadFromServer();
    }
 
    private void loadFromServer() {
        System.out.println("Loading image from server: " + filename);
    }
 
    public void display() {
        System.out.println("Displaying: " + filename);
    }
}
 
class ProxyImage implements Image {
    private String filename;
    private RealImage realImage; // null until first display (lazy init)
 
    public ProxyImage(String filename) { this.filename = filename; }
 
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename); // load only when needed
        }
        realImage.display();
    }
}
 
class ProxyTest {
    public static void run() {
        System.out.println("\n=== Exercise 6: Proxy Pattern ===");
        Image img = new ProxyImage("photo.jpg");
        System.out.println("Image object created, not loaded yet.");
        img.display(); // loads + displays
        img.display(); // only displays (cached)
    }
}
class Main {
    public static void main(String[] args) {
        ProxyTest.run();
    }
}