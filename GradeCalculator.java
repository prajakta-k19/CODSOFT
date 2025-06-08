import java.util.*;
public class GradeCalculator {
    public static char calculateGrade(double avgPercentage) {
        if (avgPercentage >= 90.0) {
            return 'O';
        } else if (avgPercentage >= 80.0) {
            return 'E';
        } else if (avgPercentage >= 70.0) {
            return 'A';
        } else if (avgPercentage >= 60.0) {
            return 'B';
        } else if (avgPercentage >= 50.0) {
            return 'C';
        } else if (avgPercentage >= 40.0) {
            return 'D';
        } else {
            return 'F';
        }
    }

    public static double[] readMarks(Scanner sc, int numSubjects) {
        double[] marks = new double[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            while (true) {
                System.out.println("Enter marks for Subject " + (i + 1) + " (out of 100): ");
                double input = sc.nextDouble();
                if (input >= 0.0 && input <= 100.0) {
                    marks[i] = input;
                    break;
                } else {
                    System.out.println("Invalid input. Marks should be between 0 and 100. Please try again.");
                }
            }
        }

        return marks;
    }

    public static double calculateTotal(double[] marks) {
        double total = 0;
        for (double mark : marks) {
            total += mark;
        }
        return total;
    }

    public static void displayReport(String studentName, double[] marks, double total, double avg, char grade) {
        System.out.println("\n📄 ------------ Grade Report ------------");
        System.out.println("Student Name: " + studentName);
        System.out.println("\nSubject-wise Marks:");
        for (int i = 0; i < marks.length; i++) {
            System.out.println("  Subject " + (i + 1) + ": " + marks[i]);
        }
        System.out.println("Total Marks: " + total + " / " + (marks.length * 100));
        System.out.println("Average Percentage: " + avg + "%");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Student's Name: ");
        String studentName = sc.nextLine();
        System.out.print("Enter the total number of subjects: ");
        int numSubjects = sc.nextInt();
        if (numSubjects <= 0) {
            System.out.println("Invalid number of subjects. Please enter a positive number.");
            return;
        }

        double[] marks = readMarks(sc, numSubjects);

        double totalMarks = calculateTotal(marks);
        double avgPercentage = totalMarks / numSubjects;
        char grade = calculateGrade(avgPercentage);

        displayReport(studentName, marks, totalMarks, avgPercentage, grade);
    }
}
