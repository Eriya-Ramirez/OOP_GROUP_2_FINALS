package display;

import data.BlockSection;
import data.IrregularStudent;
import data.RegularStudent;
import data.Student;

import java.util.List;

import static display.InputHelpers.askBoolean;
import static display.InputHelpers.askValidInput;
import static display.InputHelpers.askValidInteger;

public class StudentDisplay {
    private StudentDisplay() {
    }

    public static void addStudent(List<Student> students, List<BlockSection> sections) {
        System.out.println();
        System.out.println("Enter student details:");
        String name = askValidInput("Name: ");
        String gender = askValidInput("Gender: ");
        String degreeProgram = askValidInput("Degree program: ");
        int studentNo = askValidInteger("Student number: ", "Please enter a valid number.");
        int enrollmentYear = askValidInteger("Enrollment year: ", "Please enter a valid number.");

        boolean isIrregular = askBoolean("Is this student an irregular student? [y/n]: ");

        Student student = null;
        if (isIrregular) {
            student = new IrregularStudent(name, gender, degreeProgram, studentNo, enrollmentYear);
        } else {
            // Regular student
            BlockSection section = inputSection(sections);
            if (section != null) {
                student = new RegularStudent(name, gender, degreeProgram, studentNo, enrollmentYear, section);

                // Copy courses from section to student
                student.addCourses(section);
            }
        }

        if (student != null) {
            students.add(student);
            System.out.println("Added student!");
        }
    }

    public static void removeStudent(List<Student> students) {
        System.out.println("Removing a student.");
        Student targetStudent = inputStudent(students);
        if (targetStudent != null) {
            students.remove(targetStudent);
            System.out.println("Removed the student with number " + targetStudent.getStudentNo() + ".");
        }
    }

    public static void searchByNumber(List<Student> students) {
        System.out.println("Searching for a student by number.");
        Student targetStudent = inputStudent(students);
        if (targetStudent != null) {
            System.out.println("Student found:");
            targetStudent.showDetails();
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void searchByEnrollmentYear(List<Student> students) {
        int year = askValidInteger("Enter enrollment year to filter: ", "Please enter a valid year.");
        List<Student> filteredStudents = students.stream()
                .filter(student -> student.getEnrollmentYear() == year)
                .toList();
        System.out.println("Found " + filteredStudents.size() + " students enrolled in " + year + ":");
        for (Student student : filteredStudents) {
            student.showDetails();
        }
    }

    public static void displayAllStudents(List<Student> students) {
        System.out.println("Displaying " + students.size() + " students:");
        for (Student student : students) {
            student.showDetails();
        }
    }

    // Helpers

    public static Student inputStudent(List<Student> students) {
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

    public static BlockSection inputSection(List<BlockSection> sections) {
        BlockSection section;
        do {
            String input = askValidInput("Block section: ");
            section = sections.stream().filter(sec -> sec.getName().equalsIgnoreCase(input)).findFirst().orElse(null);
            if (section == null) {
                if (!askBoolean("This block section does not exist. Try again? [y/n]:")) {
                    return null;
                }
            }
        } while (section == null);
        return section;
    }
}
