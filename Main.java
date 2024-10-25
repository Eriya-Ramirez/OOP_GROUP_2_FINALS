import data.BlockSection;
import data.Course;
import data.CourseClass;
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
    private static final List<Course> courses = new ArrayList<>();
    private static final List<CourseClass> courseClasses = new ArrayList<>();
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
                    
                        (6) Add a course
                        (7) Create a new class for a course
                        (8) Enroll a student in a course class
                    
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
                    System.out.println("Enrolled units: " + calculateUnits(student));
                }
            }, "4");
            // Display all students
            choice.addChoice(() -> {
                System.out.println("Displaying " + students.size() + " students:");
                for (Student student : students) {
                    student.showDetails();
                    System.out.println("Enrolled units: " + calculateUnits(student));
                }
            }, "5");
            // Add a course
            choice.addChoice(() -> {
                // TODO: refactor into separate method

                System.out.println();
                System.out.println("Enter course details:");
                String code = askValidInput("Course code: ");
                String name = askValidInput("Course name: ");
                String department = askValidInput("Department: ");
                int units = askValidInteger("Credit units: ", "Please enter a valid number");

                Course course = new Course(code, name, department, units);
                courses.add(course);
                System.out.println("Added new course!");
            }, "6");
            choice.addChoice(() -> {
                // TODO: refactor into separate method

                System.out.println();
                System.out.println("Enter class details:");

                Course course;
                do {
                    String input = askValidInput("Course code: ");
                    course = courses.stream().filter(co -> co.getCode().equalsIgnoreCase(input)).findFirst().orElse(null);
                    if (course == null) {
                        if (!askBoolean("No course with that code found. Try again? [y/n]:")) {
                            return;
                        }
                    }
                } while (course == null);
                int classCode = askValidInteger("Class code: ", "Please enter a valid number.");
                String instructor = askValidInput("Instructor name: ");
                BlockSection blockSection = askBlockSection();

                CourseClass courseClass = new CourseClass(course, classCode, instructor, blockSection);
                courseClasses.add(courseClass);
                System.out.println("Created a class for a course!");
            }, "7");
            choice.addChoice(() -> {
                // TODO: refactor into separate method

                System.out.println();
                System.out.println("Enter details of student and course:");

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

                CourseClass courseClass;
                do {
                    int input = askValidInteger("Class code: ", "Please enter a valid number.");
                    courseClass = courseClasses.stream().filter(co -> co.getClassCode() == input).findFirst().orElse(null);
                    if (courseClass == null) {
                        if (!askBoolean("No course with that code found. Try again? [y/n]:")) {
                            return;
                        }
                    }
                } while (courseClass == null);
                BlockSection assigned = courseClass.getAssignedSection();

                if (student instanceof RegularStudent regular && !regular.getBlockSection().equals(assigned)) {
                    System.out.println("Cannot enroll regular student in this class.");
                    System.out.println("Student's section is " + regular.getBlockSection() + " while this class is for " + assigned + ".");
                    return;
                }

                if (student instanceof IrregularStudent irregular) {
                    if (!irregular.getBlockSections().contains(assigned)) {
                        System.out.println("Adding irregular student to section " + assigned + ".");
                        irregular.addBlockSection(assigned);
                        assigned.addStudent(irregular);
                    }
                }

                courseClass.getStudents().add(student);
                System.out.println("Added student to course class!");
            }, "8");

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
            BlockSection section = askBlockSection();
            RegularStudent student = new RegularStudent(name, gender, degreeProgram, studentNo, enrollmentYear, section);
            section.addStudent(student);
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

    public static int calculateUnits(Student student) {
        // Find all classes the student is enrolled in, and sum up their units
        return courseClasses.stream()
                .filter(cls -> cls.getStudents().contains(student))
                .mapToInt(cls -> cls.getInfo().getUnits())
                .sum();
    }

    public static BlockSection askBlockSection() {
        BlockSection section;
        do {
            String input = askValidInput("Block section: ");
            section = blockSections.stream().filter(sec -> sec.getBlockName().equalsIgnoreCase(input)).findFirst().orElse(null);
            if (section == null) {
                if (askBoolean("This block section does not exist yet. Create it? [y/n]:")) {
                    section = new BlockSection(input);
                    blockSections.add(section);
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
