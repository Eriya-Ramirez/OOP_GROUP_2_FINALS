import data.BlockSection;
import data.Student;
import display.ChoiceDisplay;
import display.EnrollmentDisplay;
import display.StudentDisplay;

import java.util.ArrayList;
import java.util.List;

public class Main {
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

            choice.addChoice(() -> StudentDisplay.addStudent(students, blockSections), "1");
            choice.addChoice(() -> StudentDisplay.removeStudent(students), "2");
            choice.addChoice(() -> StudentDisplay.searchByNumber(students), "3");
            choice.addChoice(() -> StudentDisplay.searchByEnrollmentYear(students), "4");
            choice.addChoice(() -> StudentDisplay.displayAllStudents(students), "5");

            choice.addChoice(() -> EnrollmentDisplay.addBlockSection(blockSections), "6");
            choice.addChoice(() -> EnrollmentDisplay.addIrregularToSection(students, blockSections), "7");
            choice.addChoice(() -> EnrollmentDisplay.enrollIrregularInCourse(students), "8");
            choice.addChoice(() -> EnrollmentDisplay.unenrollIrregularFromCourse(students), "9");
            choice.addChoice(() -> EnrollmentDisplay.changeCourseGrades(students), "10");

            choice.addChoice(() -> EnrollmentDisplay.calculateFees(students), "11");

            String input = choice.run();

            if (input.equalsIgnoreCase("E")) {
                running = false;
            }
        }

        System.out.println("Exiting program.");
    }
}
