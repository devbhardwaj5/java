import java.util.InputMismatchException;
import java.util.Scanner;

// 1. Custom Exception Class
class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

// 2. Student Class
class Student {

    private int rollNumber;
    private String studentName;
    private int[] marks;

    // Correct Constructor (Fix for your error)
    public Student(int rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }

    // Validate marks (0–100)
    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException(
                        "Invalid marks for subject " + (i + 1) + ": " + marks[i]);
            }
        }
    }

    // Calculate average marks
    public double calculateAverage() {
        double sum = 0;
        for (int m : marks) sum += m;
        return sum / marks.length;
    }

    // Display student result
    public void displayResult() {
        System.out.println("Roll Number: " + rollNumber);
        System.out.println("Student Name: " + studentName);

        System.out.print("Marks: ");
        for (int m : marks) System.out.print(m + " ");
        System.out.println();

        double avg = calculateAverage();
        System.out.println("Average: " + avg);

        if (avg >= 40)
            System.out.println("Result: Pass");
        else
            System.out.println("Result: Fail");
    }

    public int getRollNumber() {
        return rollNumber;
    }
}

// 3. ResultManager Class
public class ResultManager {

    private Student[] students = new Student[50];
    private int count = 0;
    private Scanner sc = new Scanner(System.in);

    // Add new student
    public void addStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            int roll = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            int[] marks = new int[3];

            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i + 1) + ": ");
                marks[i] = sc.nextInt();
            }

            // Create Student object with corrected constructor
            Student s = new Student(roll, name, marks);

            s.validateMarks(); 

            students[count++] = s;
            System.out.println("Student added successfully.");

        } catch (InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input type. Enter numeric values only.");
            sc.nextLine(); 
        } finally {
            System.out.println("Returning to main menu...");
        }
    }

    // Show student details by roll number
    public void showStudentDetails() {
        try {
            System.out.print("Enter Roll Number to search: ");
            int roll = sc.nextInt();

            boolean found = false;

            for (int i = 0; i < count; i++) {
                if (students[i].getRollNumber() == roll) {
                    students[i].displayResult();
                    found = true;
                    break;
                }
            }

            if (!found)
                System.out.println("Student with Roll Number " + roll + " not found.");

        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number.");
            sc.nextLine();
        } finally {
            System.out.println("Search completed.");
        }
    }

    // Menu-driven program
    public void mainMenu() {
        int choice = 0;

        while (choice != 3) {
            System.out.println("\n===== Student Result Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Show Student Details");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addStudent();
                        break;

                    case 2:
                        showStudentDetails();
                        break;

                    case 3:
                        System.out.println("Exiting program. Thank you!");
                        break;

                    default:
                        System.out.println("Invalid option. Try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                sc.nextLine();
            }
        }

        sc.close();
    }

    // Main method
    public static void main(String[] args) {
        ResultManager rm = new ResultManager();
        rm.mainMenu();
    }
}
