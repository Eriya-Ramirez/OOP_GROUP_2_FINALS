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
                        (7) Add irregular student to block section
                        (8) Enroll irregular student to course
                        (9) Un-enroll student from course
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
                    String code = askValidInput("Course code: ");
                    if (section.containsCourse(code)) {
                        System.out.println("Cannot duplicate a course with that code!");
                        continue;
                    }
                    String schedule = askValidInput("Course schedule: ");
                    int units = askValidInteger("Credit units: ", "Please enter a valid number.");
                    section.addCourse(new Course(code, units, schedule));
                }
                blockSections.add(section);
                System.out.println("Added a block section!");
            }, "6");
            choice.addChoice(() -> {
                System.out.println("Adding irregular student to a block section.");
                Student student = inputStudent();
                if (student == null) return;

                if (!(student instanceof IrregularStudent irregular)) {
                    System.out.println("Cannot add regular student in another block section!");
                    return;
                }

                BlockSection section = inputSection();
                if (section == null) return;

                irregular.addBlockSection(section);
                if (askBoolean("Enroll student in same courses as the section? [y/n]: ")) {
                    student.addCourses(section);
                }

                System.out.println("Added student in block section!");
            }, "7");
            choice.addChoice(() -> {
                System.out.println("Enroll irregular student in course.");
                Student student = inputStudent();
                if (student == null) return;

                if (student instanceof RegularStudent) {
                    System.out.println("Cannot enroll student in another course!");
                    return;
                }

                String code = askValidInput("Course code: ");
                if (student.containsCourse(code)) {
                    System.out.println("Cannot enroll student in duplicate course!");
                    return;
                }

                String schedule = askValidInput("Course schedule: ");
                int units = askValidInteger("Credit units: ", "Please enter a valid number.");
                Course course = new Course(code, units, schedule);
                student.addCourse(course);
                System.out.println("Enrolled student in course!");

            }, "8");
            choice.addChoice(() -> {
                System.out.println("Un-enroll student from course.");
                Student student = inputStudent();
                if (student == null) return;

                if (student.getCoursesToGrades().isEmpty()) {
                    System.out.println("Student is not enrolled in any course!");
                    return;
                }

                System.out.println();
                List<Course> courses = new ArrayList<>(student.getCoursesToGrades().keySet());
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    System.out.printf(" (%s) %s%n", i + 1, course);
                }

                int input;
                do {
                    input = askValidInteger("Select a course above (or 0 to cancel): ", "Please enter a valid selection.");
                    // Input should be in range of [1, courses.size()], or 0 (to cancel)
                    if (input == 0) {
                        return;
                    }

                    // Out of bounds
                    if (input > courses.size() || input < 0) {
                        System.out.println("Please enter a valid selection.");
                        input = -1;
                    }
                } while (input < 0);

                Course course = courses.get(input - 1);
                student.getCoursesToGrades().remove(course);
                System.out.println("Un-enrolled student from course.");

            }, "9");
            choice.addChoice(() -> {

            }, "10");
            choice.addChoice(() -> {
                Student student = inputStudent();
                if (student == null) return;

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
            BlockSection section = inputSection();
            if (section == null) return null;

            RegularStudent student = new RegularStudent(name, gender, degreeProgram, studentNo, enrollmentYear, section);

            // Copy courses from section to student
            student.addCourses(section);

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

    public static Student inputStudent() {
        Student student;
        do {
            int input = askValidInteger("Student number: ", "Please enter a valid number.");
            student = students.stream().filter(st -> st.getStudentNo() == input).findFirst().orElse(null);
            if (student == null) {
                if (!askBoolean("No student with that number found. Try again? [y/n]:")) {
                    break;
                }
            }
        } while (student == null);
        return student;
    }

    public static BlockSection inputSection() {
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
        return section;
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
