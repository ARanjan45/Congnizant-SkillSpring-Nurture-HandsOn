import java.util.Arrays;

/*
 * Sorting Algorithms:
 * - Bubble Sort:    O(n²) average/worst. Simple but slow. Good for educational use only.
 * - Insertion Sort: O(n²) worst, O(n) best (nearly sorted). Good for small/nearly sorted data.
 * - Quick Sort:     O(n log n) average, O(n²) worst (rare with good pivot). In-place, fast in practice.
 * - Merge Sort:     O(n log n) always. Stable, but needs O(n) extra space.
 */

class Order {
    int orderId;
    String customerName;
    double totalPrice;

    public Order(int orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "[OrderID=" + orderId + ", Customer=" + customerName + ", Total=$" + totalPrice + "]";
    }
}

public class Exercise3_SortingOrders {

    // Bubble Sort - O(n²)
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].totalPrice > orders[j + 1].totalPrice) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    // Quick Sort - O(n log n) average
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pi = partition(orders, low, high);
            quickSort(orders, low, pi - 1);
            quickSort(orders, pi + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].totalPrice;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (orders[j].totalPrice <= pivot) {
                i++;
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        Order temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;
        return i + 1;
    }

    static void print(Order[] orders) {
        for (Order o : orders) System.out.println("  " + o);
    }

    public static void main(String[] args) {
        Order[] orders1 = {
            new Order(1, "Alice", 5000),
            new Order(2, "Bob", 1200),
            new Order(3, "Charlie", 8500),
            new Order(4, "Diana", 300),
            new Order(5, "Eve", 4700)
        };

        Order[] orders2 = Arrays.copyOf(orders1, orders1.length);

        System.out.println("=== Bubble Sort (by totalPrice) ===");
        bubbleSort(orders1);
        print(orders1);

        System.out.println("\n=== Quick Sort (by totalPrice) ===");
        quickSort(orders2, 0, orders2.length - 1);
        print(orders2);

        /*
         * ANALYSIS:
         * Bubble Sort: O(n²) — fine for tiny datasets, impractical for real e-commerce volumes.
         * Quick Sort:  O(n log n) average — preferred. In-place, cache-friendly, widely used.
         *
         * Why Quick Sort over Bubble Sort?
         * For n=10,000 orders:
         *   Bubble Sort → ~100,000,000 comparisons
         *   Quick Sort  → ~133,000 comparisons (approx n log n)
         * Quick Sort is typically 100–1000x faster in practice.
         */
    }
}