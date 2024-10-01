import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static final Scanner INPUT = new Scanner(System.in);

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        boolean running = true;
        while (running) {
            System.out.println("""
                    
                    = [ Student Management System ] =
                     [[[ ! WIP. Early prototype. ]]]
                    
                        (1) Add a student
                        (2) Remove a student
                        (3) Update student details (NYI)
                        (4) Display all students
                    
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
            choice.addChoice(() -> {
                int targetNumber = askInput("Please enter the student number: ", "Please input a valid number", INTEGER_CONVERTER);
                Student targetStudent = null;
                for (Student student : students) {
                    if (student.getStudentNo() == targetNumber) {
                        targetStudent = student;
                        break;
                    }
                }
                if (targetStudent != null) {
                    // Found the student, delete
                    students.remove(targetStudent);
                    System.out.println("Removed the student with number " + targetNumber + ".");
                    displayStudent(targetStudent, "\t");
                } else {
                    System.out.println("Could not find a student with number " + targetNumber + ".");
                }
                System.out.println("Press enter to continue...");
                INPUT.nextLine();
            }, "2");
            // Display all students
            choice.addChoice(() -> {
                System.out.println("Displaying " + students.size() + " students.");
                System.out.println();
                for (int i = 0; i < students.size(); i++) {
                    System.out.println("#" + (i + 1) + ".");
                    displayStudent(students.get(i), "\t");
                }
                System.out.println();
                System.out.println("Press enter to continue...");
                INPUT.nextLine();
            }, "4");

            String input = choice.run();

            if (input.equalsIgnoreCase("E")) {
                running = false;
            }
        }

        System.out.println("Exiting program.");
    }

    public static Student addStudent() {
        System.out.println();
        System.out.println("Enter student details:");
        String name = askInput("Name: ");
        String gender = askInput("Gender: ");
        String degreeProgram = askInput("Degree program: ");
        int studentNo = askInput("Student number: ", "Please enter a valid number.", INTEGER_CONVERTER);
        int enrollmentYear = askInput("Enrollment year: ", "Please enter a valid number.", INTEGER_CONVERTER);
        int enrolledUnits = askInput("Enrolled units: ", "Please enter a valid number.", INTEGER_CONVERTER);
        // TODO: regular vs irregular
        return new Student(name, gender, degreeProgram, studentNo, enrollmentYear, enrolledUnits);
    }

    public static void displayStudent(Student student, String prefix) {
        System.out.println(prefix + "Student Number: " + student.getStudentNo());
        System.out.println(prefix + "Name: " + student.getName());
        System.out.println(prefix + "Gender: " + student.getGender());
        System.out.println(prefix + "Degree Program: " + student.getDegreeProgram());
        System.out.println(prefix + "Enrollment Year: " + student.getEnrollmentYear());
        System.out.println(prefix + "Enrolled Units: " + student.getEnrolledUnits());
    }

    public static Function<String, Boolean> BOOLEAN_CONVERTER = input -> switch (input.toLowerCase(Locale.ROOT)) {
        case "y", "yes", "true" -> true;
        case "n", "no", "false" -> false;
        default -> null;
    };
    public static Function<String, Integer> INTEGER_CONVERTER = input -> {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Not a number; we ignore it.
            return null;
        }
    };

    public static String askInput(String prompt) {
        return askInput(prompt, "!!!", Function.identity());
    }

    public static <T> T askInput(String prompt, String errorMessage, Function<String, T> converter) {
        T ret;
        do {
            System.out.print(prompt);
            String line = INPUT.nextLine();
            ret = converter.apply(line);
            if (ret == null) {
                System.out.println(errorMessage);
            }
        } while (ret == null);
        return ret;
    }
}