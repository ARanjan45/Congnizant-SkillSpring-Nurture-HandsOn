// EXERCISE 10: MVC Pattern - Student Records
import java.util.Scanner;
class Student {
    private String name;
    private String id;
    private String grade;
 
    public Student(String name, String id, String grade) {
        this.name = name; this.id = id; this.grade = grade;
    }
 
    public String getName() { return name; }
    public String getId() { return id; }
    public String getGrade() { return grade; }
    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }
}
 
class StudentView {
    public void displayStudentDetails(String name, String id, String grade) {
        System.out.println("Student: " + name + " | ID: " + id + " | Grade: " + grade);
    }
}
 
class StudentController {
    private Student model;
    private StudentView view;
 
    public StudentController(Student model, StudentView view) {
        this.model = model; this.view = view;
    }
 
    public void updateStudentName(String name) { model.setName(name); }
    public void updateStudentGrade(String grade) { model.setGrade(grade); }
 
    public void updateView() {
        view.displayStudentDetails(model.getName(), model.getId(), model.getGrade());
    }
}
 
class MVCTest {
    public static void run() {
        System.out.println("\n=== Exercise 10: MVC Pattern ===");
        Student student = new Student("Aprajita", "VIT001", "A");
        StudentView view = new StudentView();
        StudentController controller = new StudentController(student, view);
 
        controller.updateView();
        controller.updateStudentGrade("A+");
        controller.updateView();
    }
}
class Main {
    public static void main(String[] args) {
        MVCTest.run();
    }
}