package display;

import data.BlockSection;
import data.Course;
import data.IrregularStudent;
import data.RegularStudent;
import data.Student;

import java.util.ArrayList;
import java.util.List;

import static display.InputHelpers.askBoolean;
import static display.InputHelpers.askValidInput;
import static display.InputHelpers.askValidInteger;
import static display.StudentDisplay.inputSection;
import static display.StudentDisplay.inputStudent;

public class EnrollmentDisplay {
    private EnrollmentDisplay() {
    }

    public static void addBlockSection(List<BlockSection> blockSections) {
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
    }

    public static void addIrregularToSection(List<Student> students, List<BlockSection> blockSections) {
        System.out.println("Adding irregular student to a block section.");
        Student student = inputStudent(students);
        if (student == null) return;

        if (!(student instanceof IrregularStudent irregular)) {
            System.out.println("Cannot add regular student in another block section!");
            return;
        }

        BlockSection section = inputSection(blockSections);
        if (section == null) return;

        irregular.addBlockSection(section);
        if (askBoolean("Enroll student in same courses as the section? [y/n]: ")) {
            student.addCourses(section);
        }

        System.out.println("Added student in block section!");
    }

    public static void enrollIrregularInCourse(List<Student> students) {
        System.out.println("Enroll irregular student in course.");
        Student student = inputStudent(students);
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
    }

    public static void unenrollIrregularFromCourse(List<Student> students) {
        System.out.println("Un-enroll student from course.");
        Student student = inputStudent(students);
        if (student == null) return;

        if (student.getCoursesToGrades().isEmpty()) {
            System.out.println("Student is not enrolled in any course!");
            return;
        }

        System.out.println();
        Course course = selectCourse(student);
        if (course == null) return;

        student.getCoursesToGrades().remove(course);
        System.out.println("Un-enrolled student from course.");
    }

    public static void changeCourseGrades(List<Student> students) {
        System.out.println("Change course grades for student.");
        Student student = inputStudent(students);
        if (student == null) return;

        if (student.getCoursesToGrades().isEmpty()) {
            System.out.println("Student is not enrolled in any course!");
            return;
        }

        System.out.println();
        Course course = selectCourse(student);
        if (course == null) return;

        Float grade = null;
        do {
            System.out.print("Enter a grade: ");
            try {
                grade = Float.parseFloat(InputHelpers.INPUT.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        } while (grade == null);

        student.setCourseGrade(course, grade);
        System.out.println("Changed course grade of student.");
    }

    public static void calculateFees(List<Student> students) {
        System.out.println("Calculating per-unit fees for a student.");
        Student student = inputStudent(students);
        if (student == null) return;

        int feePerUnit = askValidInteger("Fee per credit unit: ", "Please enter a valid number.");

        System.out.println();
        student.showDetails();
        System.out.println();
        int units = student.getEnrolledUnits();
        System.out.println("Total amount of fees: " + units * feePerUnit);
    }

    // Helpers

    public static Course selectCourse(Student student) {
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
                return null;
            }

            // Out of bounds
            if (input > courses.size() || input < 0) {
                System.out.println("Please enter a valid selection.");
                input = -1;
            }
        } while (input < 0);

        return courses.get(input - 1);
    }
}
