import data.BlockSection;
import data.Course;
import data.IrregularStudent;
import data.RegularStudent;
import data.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static final Scanner INPUT = new Scanner(System.in);
    private static final List<Student> students = new ArrayList<>();
    private static final List<BlockSection> blockSections = new ArrayList<>();

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
                    
                        (6) Add a block section
                        (7) Enroll student in block section
                        (8) Add student to course
                        (9) Remove student from course
                        (10) Change course grades for student
                    
                        (11) Calculate per-unit fees for student
                    
                        (E) Exit program
                    """);

            ChoiceDisplay choice = ChoiceDisplay.begin("Enter your selection: ").setIgnoreCase(true);

            // Exit program
            choice.acknowledgeChoice("E");
            // Add student
            choice.addChoice(() -> {
                Student student = addStudent();
                if (student != null) {
                    students.add(student);
                    System.out.println("Added student!");
                }
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
                    System.out.println("data.Student found:");
                    student.showDetails();
                } else {
                    System.out.println("data.Student not found.");
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
            choice.addChoice(() -> {
                System.out.println("Adding a block section.");
                String name = askValidInput("Section name: ");
                BlockSection section = new BlockSection(name);
                while (askBoolean("Add a course to this section? [y/n]: ")) {
                    section.addCourse(inputCourse());
                }
                blockSections.add(section);
                System.out.println("Added a block section!");
            }, "6");
            choice.addChoice(() -> {

            }, "7");
            choice.addChoice(() -> {

            }, "8");
            choice.addChoice(() -> {

            }, "9");
            choice.addChoice(() -> {

            }, "10");
            choice.addChoice(() -> {

                Student student;
                do {
                    int input = askValidInteger("Student number: ", "Please enter a valid number.");
                    student = students.stream().filter(st -> st.getStudentNo() == input).findFirst().orElse(null);
                    if (student == null) {
                        if (!askBoolean("No student with that number found. Try again? [y/n]:")) {
                            return;
                        }
                    }
                } while (student == null);

                int feePerUnit = askValidInteger("Fee per credit unit: ", "Please enter a valid number.");

                System.out.println();
                student.showDetails();
                System.out.println();
                int units = student.getEnrolledUnits();
                System.out.println("Total amount of fees: " + units * feePerUnit);
            }, "11");

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

        boolean isIrregular = askBoolean("Is this student an irregular student? [y/n]: ");

        if (isIrregular) {
            return new IrregularStudent(name, gender, degreeProgram, studentNo, enrollmentYear);
        } else {
            // Regular student
            BlockSection section;
            do {
                String input = askValidInput("Block section: ");
                section = blockSections.stream().filter(sec -> sec.getName().equalsIgnoreCase(input)).findFirst().orElse(null);
                if (section == null) {
                    if (!askBoolean("This block section does not exist. Try again? [y/n]:")) {
                        return null;
                    }
                }
            } while (section == null);

            RegularStudent student = new RegularStudent(name, gender, degreeProgram, studentNo, enrollmentYear, section);

            // Copy courses from section to student
            for (Course course : section.getCourses()) {
                student.addCourse(course);
            }

            return student;
        }
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

    public static Course inputCourse() {
        String code = askValidInput("Course code: ");
        String schedule = askValidInput("Course schedule: ");
        int units = askValidInteger("Credit units: ", "Please enter a valid number.");
        return new Course(code, units, schedule);
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

    public static boolean askBoolean(String prompt) {
        Boolean result = null;
        while (result == null) {
            System.out.print(prompt);
            String input = INPUT.nextLine().toLowerCase(Locale.ROOT);
            result = switch (input) {
                case "yes", "y" -> true;
                case "no", "n" -> false;
                default -> {
                    System.out.println("Invalid choice.");
                    yield null;
                }
            };
        }
        return result;
    }
}
