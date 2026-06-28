// EXERCISE 11: Dependency Injection
import java.util.*;
interface CustomerRepository {
    String findCustomerById(int id);
}
 
class CustomerRepositoryImpl implements CustomerRepository {
    public String findCustomerById(int id) {
        // Simulated DB lookup
        Map<Integer, String> db = new HashMap<>();
        db.put(1, "Aprajita Ranjan");
        db.put(2, "John Doe");
        return db.getOrDefault(id, "Customer not found");
    }
}
 
class CustomerService {
    private CustomerRepository repository;
 
    // Constructor injection
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }
 
    public void getCustomer(int id) {
        String customer = repository.findCustomerById(id);
        System.out.println("Found: " + customer);
    }
}
 
class DependencyInjectionTest {
    public static void run() {
        System.out.println("\n=== Exercise 11: Dependency Injection ===");
        CustomerRepository repo = new CustomerRepositoryImpl();
        CustomerService service = new CustomerService(repo); // DI via constructor
        service.getCustomer(1);
        service.getCustomer(2);
        service.getCustomer(99);
    }
}
class Main {
    public static void main(String[] args) {
        DependencyInjectionTest.run();
    }
}