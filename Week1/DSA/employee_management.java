class Employee {
    int employeeId;
    String name;
    String position;
    double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "[ID=" + employeeId + ", Name=" + name + ", Position=" + position + ", Salary=" + salary + "]";
    }
}

public class Exercise4_EmployeeManagement {
    private static final int MAX_SIZE = 10;
    private static Employee[] employees = new Employee[MAX_SIZE];
    private static int count = 0;

    // Add - O(1) if space available
    public static void addEmployee(Employee e) {
        if (count == MAX_SIZE) {
            System.out.println("Array full. Cannot add more employees.");
            return;
        }
        employees[count++] = e;
        System.out.println("Added: " + e);
    }

    // Search - O(n) linear scan
    public static Employee searchEmployee(int id) {
        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == id) return employees[i];
        }
        return null;
    }

    // Traverse - O(n)
    public static void traverseEmployees() {
        System.out.println("--- All Employees ---");
        if (count == 0) { System.out.println("No employees."); return; }
        for (int i = 0; i < count; i++) {
            System.out.println(employees[i]);
        }
    }

    // Delete - O(n): find + shift elements left
    public static void deleteEmployee(int id) {
        for (int i = 0; i < count; i++) {
            if (employees[i].employeeId == id) {
                System.out.println("Deleted: " + employees[i]);
                // Shift left
                for (int j = i; j < count - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[--count] = null;
                return;
            }
        }
        System.out.println("Employee ID " + id + " not found.");
    }

    public static void main(String[] args) {
        addEmployee(new Employee(1, "Alice", "Manager", 70000));
        addEmployee(new Employee(2, "Bob", "Developer", 60000));
        addEmployee(new Employee(3, "Charlie", "Analyst", 55000));

        System.out.println();
        traverseEmployees();

        System.out.println();
        Employee e = searchEmployee(2);
        System.out.println("Search ID 2: " + (e != null ? e : "Not found"));

        System.out.println();
        deleteEmployee(2);
        traverseEmployees();

        /*
         * TIME COMPLEXITY SUMMARY:
         * Add (at end):  O(1)
         * Search:        O(n)
         * Traverse:      O(n)
         * Delete:        O(n) - due to shifting
         *
         * LIMITATIONS:
         * - Fixed size (must resize manually or use ArrayList).
         * - Insertion/deletion in the middle is expensive.
         * USE ARRAYS WHEN: size is known, random access is needed, memory is tight.
         */
    }
}