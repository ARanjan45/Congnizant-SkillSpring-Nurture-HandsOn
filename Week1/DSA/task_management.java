/*
 * Linked Lists:
 * - Singly Linked List: each node points to next. Traversal is one-directional.
 * - Doubly Linked List: each node points to next AND previous. Easier deletion.
 * - No contiguous memory needed — nodes can be anywhere in heap.
 * - Dynamic size — grows/shrinks without pre-allocation.
 */

class Task {
    int taskId;
    String taskName;
    String status;
    Task next; // link to next node

    public Task(int taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
        this.next = null;
    }

    @Override
    public String toString() {
        return "[ID=" + taskId + ", Name=" + taskName + ", Status=" + status + "]";
    }
}

class TaskLinkedList {
    private Task head;

    // Add at end - O(n) to reach tail; O(1) if tail pointer maintained
    public void addTask(Task t) {
        if (head == null) { head = t; }
        else {
            Task curr = head;
            while (curr.next != null) curr = curr.next;
            curr.next = t;
        }
        System.out.println("Added: " + t);
    }

    // Search - O(n)
    public Task searchTask(int id) {
        Task curr = head;
        while (curr != null) {
            if (curr.taskId == id) return curr;
            curr = curr.next;
        }
        return null;
    }

    // Traverse - O(n)
    public void traverseTasks() {
        System.out.println("--- Task List ---");
        if (head == null) { System.out.println("No tasks."); return; }
        Task curr = head;
        while (curr != null) {
            System.out.println(curr);
            curr = curr.next;
        }
    }

    // Delete - O(n)
    public void deleteTask(int id) {
        if (head == null) { System.out.println("List is empty."); return; }
        if (head.taskId == id) {
            System.out.println("Deleted: " + head);
            head = head.next;
            return;
        }
        Task prev = head, curr = head.next;
        while (curr != null) {
            if (curr.taskId == id) {
                System.out.println("Deleted: " + curr);
                prev.next = curr.next;
                return;
            }
            prev = curr;
            curr = curr.next;
        }
        System.out.println("Task ID " + id + " not found.");
    }
}

public class Exercise5_TaskManagement {
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        list.addTask(new Task(1, "Design DB Schema", "Pending"));
        list.addTask(new Task(2, "Implement API", "In Progress"));
        list.addTask(new Task(3, "Write Tests", "Pending"));

        System.out.println();
        list.traverseTasks();

        System.out.println();
        Task t = list.searchTask(2);
        System.out.println("Search ID 2: " + (t != null ? t : "Not found"));

        System.out.println();
        list.deleteTask(2);
        list.deleteTask(99); // not found
        list.traverseTasks();

        /*
         * TIME COMPLEXITY:
         * Add (end):  O(n) without tail pointer, O(1) with tail pointer
         * Search:     O(n)
         * Traverse:   O(n)
         * Delete:     O(n)
         *
         * ADVANTAGE OVER ARRAYS:
         * - No size limit upfront (dynamic).
         * - Insertion/deletion at head is O(1) - no shifting needed.
         * - Memory allocated per node - efficient for frequently changing collections.
         */
    }
}