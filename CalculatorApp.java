import java.util.Scanner;

class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    int subtract(int a, int b) {
        return a - b;
    }

    double multiply(double a, double b) {
        return a * b;
    }

    double divide(int a, int b) {
        if (b == 0) {
            System.out.println("Error: Cannot divide by zero!");
            return 0;
        }
        return (double) a / b;
    }
}

public class CalculatorApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator c = new Calculator();
        int choice;

        do {
            System.out.println("\n===== Calculator Application =====");
            System.out.println("1. Add Numbers");
            System.out.println("2. Subtract Numbers");
            System.out.println("3. Multiply Numbers");
            System.out.println("4. Divide Numbers");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("1. Add two integers");
                    System.out.println("2. Add two doubles");
                    System.out.println("3. Add three integers");
                    System.out.print("Enter sub-choice: ");
                    int sub = sc.nextInt();

                    if (sub == 1) {
                        System.out.print("Enter two integers: ");
                        int a = sc.nextInt();
                        int b = sc.nextInt();
                        System.out.println("Result: " + c.add(a, b));
                    } else if (sub == 2) {
                        System.out.print("Enter two doubles: ");
                        double x = sc.nextDouble();
                        double y = sc.nextDouble();
                        System.out.println("Result: " + c.add(x, y));
                    } else if (sub == 3) {
                        System.out.print("Enter three integers: ");
                        int p = sc.nextInt();
                        int q = sc.nextInt();
                        int r = sc.nextInt();
                        System.out.println("Result: " + c.add(p, q, r));
                    } else {
                        System.out.println("Invalid sub-choice!");
                    }
                    break;

                case 2:
                    System.out.print("Enter two integers: ");
                    int s1 = sc.nextInt();
                    int s2 = sc.nextInt();
                    System.out.println("Result: " + c.subtract(s1, s2));
                    break;

                case 3:
                    System.out.print("Enter two numbers: ");
                    double m1 = sc.nextDouble();
                    double m2 = sc.nextDouble();
                    System.out.println("Result: " + c.multiply(m1, m2));
                    break;

                case 4:
                    System.out.print("Enter two integers: ");
                    int d1 = sc.nextInt();
                    int d2 = sc.nextInt();
                    System.out.println("Result: " + c.divide(d1, d2));
                    break;

                case 5:
                    System.out.println("Thank you for using the calculator!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);

    }
}
