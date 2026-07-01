import java.util.Arrays;
import java.util.Comparator;

class Book {
    int bookId;
    String title;
    String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "[ID=" + bookId + ", Title=" + title + ", Author=" + author + "]";
    }
}

public class Exercise6_LibraryManagement {

    // Linear Search by title — O(n)
    public static Book linearSearchByTitle(Book[] books, String title) {
        for (Book b : books) {
            if (b.title.equalsIgnoreCase(title)) return b;
        }
        return null;
    }

    // Binary Search by title - O(log n), array must be sorted alphabetically by title
    public static Book binarySearchByTitle(Book[] books, String title) {
        int low = 0, high = books.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = books[mid].title.compareToIgnoreCase(title);
            if (cmp == 0) return books[mid];
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    public static void main(String[] args) {
        Book[] books = {
            new Book(3, "Clean Code", "Robert Martin"),
            new Book(1, "Design Patterns", "GoF"),
            new Book(5, "Effective Java", "Joshua Bloch"),
            new Book(2, "Introduction to Algorithms", "CLRS"),
            new Book(4, "The Pragmatic Programmer", "Hunt & Thomas")
        };

        // Sorted copy for binary search
        Book[] sortedBooks = Arrays.copyOf(books, books.length);
        Arrays.sort(sortedBooks, Comparator.comparing(b -> b.title.toLowerCase()));

        System.out.println("=== Linear Search ===");
        String query = "Effective Java";
        Book result = linearSearchByTitle(books, query);
        System.out.println("Search '" + query + "': " + (result != null ? result : "Not found"));

        result = linearSearchByTitle(books, "Unknown Book");
        System.out.println("Search 'Unknown Book': " + (result != null ? result : "Not found"));

        System.out.println("\n=== Binary Search (sorted by title) ===");
        result = binarySearchByTitle(sortedBooks, "Design Patterns");
        System.out.println("Search 'Design Patterns': " + (result != null ? result : "Not found"));

        result = binarySearchByTitle(sortedBooks, "Unknown Book");
        System.out.println("Search 'Unknown Book': " + (result != null ? result : "Not found"));

        /*
         * WHEN TO USE:
         * Linear Search:
         *   - Small datasets (< few hundred books)
         *   - Unsorted or frequently updated collections
         *   - One-time searches where sorting isn't worth the cost
         *
         * Binary Search:
         *   - Large sorted datasets (library with thousands of books)
         *   - Repeated searches - sort once, search many times
         *   - Time complexity: O(log n) vs O(n) for linear
         */
    }
}