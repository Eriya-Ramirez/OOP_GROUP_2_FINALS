import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static final Scanner INPUT = new Scanner(System.in);
    private static final List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("""
                    
                    = [ Student Management System ] =
                     [[[ ! Enhanced Version ]]]
                    
                        (1) Add a student
                        (2) Remove a student
                        (3) Search by student number
                        (4) Filter by enrollment year
                        (5) Display all students
                        (E) Exit program
                    """);

            ChoiceDisplay choice = ChoiceDisplay.begin("Enter your selection: ").setIgnoreCase(true);

            // Exit program
            choice.acknowledgeChoice("E");
            // Add student
            choice.addChoice(() -> {
                Student student = addStudent();
                students.add(student);
                System.out.println("Added student!");
            }, "1");
            // Remove student
            choice.addChoice(() -> {
                int targetNumber = askValidInteger("Please enter the student number: ", "Please input a valid number.");
                Student targetStudent = searchByStudentNo(targetNumber);
                if (targetStudent != null) {
                    students.remove(targetStudent);
                    System.out.println("Removed the student with number " + targetNumber + ".");
                } else {
                    System.out.println("Could not find a student with number " + targetNumber + ".");
                }
            }, "2");
            // Search by student number
            choice.addChoice(() -> {
                int targetNumber = askValidInteger("Enter student number to search: ", "Please input a valid number.");
                Student student = searchByStudentNo(targetNumber);
                if (student != null) {
                    System.out.println("Student found:");
                    student.showDetails();
                } else {
                    System.out.println("Student not found.");
                }
            }, "3");
            // Filter by enrollment year
            choice.addChoice(() -> {
                int year = askValidInteger("Enter enrollment year to filter: ", "Please enter a valid year.");
                List<Student> filteredStudents = filterByEnrollmentYear(year);
                System.out.println("Found " + filteredStudents.size() + " students enrolled in " + year + ":");
                for (Student student : filteredStudents) {
                    student.showDetails();
                }
            }, "4");
            // Display all students
            choice.addChoice(() -> {
                System.out.println("Displaying " + students.size() + " students:");
                for (Student student : students) {
                    student.showDetails();
                }
            }, "5");

            String input = choice.run();

            if (input.equalsIgnoreCase("E")) {
                running = false;
            }
        }

        System.out.println("Exiting program.");
    }

    // Add student method with input validation
    public static Student addStudent() {
        System.out.println();
        System.out.println("Enter student details:");
        String name = askValidInput("Name: ");
        String gender = askValidInput("Gender: ");
        String degreeProgram = askValidInput("Degree program: ");
        int studentNo = askValidInteger("Student number: ", "Please enter a valid number.");
        int enrollmentYear = askValidInteger("Enrollment year: ", "Please enter a valid number.");
        int enrolledUnits = askValidInteger("Enrolled units: ", "Please enter a valid number.");
        // You can later extend this to allow for selecting Regular or Irregular student
        return new RegularStudent(name, gender, degreeProgram, studentNo, enrollmentYear, enrolledUnits);
    }

    // Search by student number
    public static Student searchByStudentNo(int studentNo) {
        for (Student student : students) {
            if (student.getStudentNo() == studentNo) {
                return student;
            }
        }
        return null;
    }

    // Filter students by enrollment year
    public static List<Student> filterByEnrollmentYear(int year) {
        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.getEnrollmentYear() == year) {
                filteredStudents.add(student);
            }
        }
        return filteredStudents;
    }

    // Input validation methods
    public static String askValidInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = INPUT.nextLine();
        } while (input.isEmpty());
        return input;
    }

    public static int askValidInteger(String prompt, String errorMessage) {
        Integer number = null;
        while (number == null) {
            System.out.print(prompt);
            try {
                number = Integer.parseInt(INPUT.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return number;
    }
}
