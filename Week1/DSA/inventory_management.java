import java.util.*;

// Product class with required attributes
class Product {
    int productId;
    String productName;
    int quantity;
    double price;

    public Product(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "[ID=" + productId + ", Name=" + productName +
               ", Qty=" + quantity + ", Price=$" + price + "]";
    }
}

// Inventory using HashMap for O(1) average-case operations
class Inventory {
    private HashMap<Integer, Product> products = new HashMap<>();

    // Add product — O(1) average
    public void addProduct(Product p) {
        if (products.containsKey(p.productId)) {
            System.out.println("Product with ID " + p.productId + " already exists.");
            return;
        }
        products.put(p.productId, p);
        System.out.println("Added: " + p);
    }

    // Update product - O(1) average
    public void updateProduct(int productId, int newQty, double newPrice) {
        Product p = products.get(productId);
        if (p == null) {
            System.out.println("Product ID " + productId + " not found.");
            return;
        }
        p.quantity = newQty;
        p.price = newPrice;
        System.out.println("Updated: " + p);
    }

    // Delete product - O(1) average
    public void deleteProduct(int productId) {
        Product removed = products.remove(productId);
        if (removed == null) {
            System.out.println("Product ID " + productId + " not found.");
        } else {
            System.out.println("Deleted: " + removed);
        }
    }

    // Display all products - O(n)
    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("--- Inventory ---");
        for (Product p : products.values()) {
            System.out.println(p);
        }
    }
}

public class Exercise1_InventoryManagement {
    public static void main(String[] args) {
        /*
         * WHY HashMap?
         * - Add:    O(1) average (direct key hashing)
         * - Update: O(1) average (key lookup then modify)
         * - Delete: O(1) average (key lookup then remove)
         * - vs ArrayList: search is O(n), making update/delete O(n)
         * Optimization: Use ConcurrentHashMap for thread safety in multi-threaded warehouses.
         */

        Inventory inventory = new Inventory();

        inventory.addProduct(new Product(101, "Laptop", 50, 75000));
        inventory.addProduct(new Product(102, "Mouse", 200, 500));
        inventory.addProduct(new Product(103, "Keyboard", 150, 1200));

        System.out.println();
        inventory.displayAll();

        System.out.println();
        inventory.updateProduct(102, 180, 450);
        inventory.deleteProduct(103);
        inventory.deleteProduct(999); // non-existent

        System.out.println();
        inventory.displayAll();
    }
}