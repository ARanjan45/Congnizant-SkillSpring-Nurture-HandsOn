import java.util.Arrays;
import java.util.Comparator;

/*
 * Big O Notation:
 *  - Describes how runtime grows relative to input size n.
 *  - Linear Search: Best O(1), Average/Worst O(n)
 *  - Binary Search: Best O(1), Average/Worst O(log n) — requires sorted data
 */

class Product {
    int productId;
    String productName;
    String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    @Override
    public String toString() {
        return "[ID=" + productId + ", Name=" + productName + ", Category=" + category + "]";
    }
}

public class Exercise2_EcommerceSearch {

    // Linear Search - O(n)
    public static Product linearSearch(Product[] products, int targetId) {
        for (Product p : products) {
            if (p.productId == targetId) return p;
        }
        return null;
    }

    // Binary Search - O(log n), array must be sorted by productId
    public static Product binarySearch(Product[] products, int targetId) {
        int low = 0, high = products.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (products[mid].productId == targetId) return products[mid];
            else if (products[mid].productId < targetId) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    public static void main(String[] args) {
        // Unsorted array for linear search
        Product[] unsorted = {
            new Product(105, "Laptop", "Electronics"),
            new Product(102, "Headphones", "Electronics"),
            new Product(108, "Shoes", "Fashion"),
            new Product(101, "T-Shirt", "Fashion"),
            new Product(110, "Bottle", "Kitchen")
        };

        // Sorted array for binary search
        Product[] sorted = Arrays.copyOf(unsorted, unsorted.length);
        Arrays.sort(sorted, Comparator.comparingInt(p -> p.productId));

        System.out.println("=== Linear Search ===");
        Product result = linearSearch(unsorted, 108);
        System.out.println("Search ID 108: " + (result != null ? result : "Not found"));

        result = linearSearch(unsorted, 999);
        System.out.println("Search ID 999: " + (result != null ? result : "Not found"));

        System.out.println("\n=== Binary Search (sorted array) ===");
        result = binarySearch(sorted, 101);
        System.out.println("Search ID 101: " + (result != null ? result : "Not found"));

        result = binarySearch(sorted, 999);
        System.out.println("Search ID 999: " + (result != null ? result : "Not found"));

        /*
         * ANALYSIS:
         * Linear Search: O(n) - scans every element. Simple, works on unsorted data.
         * Binary Search: O(log n) - halves search space each step. Needs sorted data.
         *
         * For an e-commerce platform with millions of products:
         * → Binary Search (or indexing via HashMap/DB index) is far more suitable.
         * → If data changes frequently, maintain sorted order or use a TreeMap.
         */
    }
}