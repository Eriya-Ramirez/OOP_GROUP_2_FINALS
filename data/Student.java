package data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Student {
    private String name, gender, degreeProgram;
    private int studentNo, enrollmentYear;
    private Map<Course, Float> coursesToGrades = new HashMap<>();

    public Student(String name, String gender, String degreeProgram, int studentNo, int enrollmentYear) {
        this.name = name;
        this.gender = gender;
        this.degreeProgram = degreeProgram;
        this.studentNo = studentNo;
        this.enrollmentYear = enrollmentYear;
    }

    public String getName() {
        return name;
    }

    public int getStudentNo() {
        return studentNo;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public Map<Course, Float> getCoursesToGrades() {
        return coursesToGrades;
    }

    public int getEnrolledUnits() {
        return coursesToGrades.keySet().stream().mapToInt(Course::getUnits).sum();
    }

    public void showDetails() {
        System.out.println("Student Number: " + studentNo);
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Degree Program: " + degreeProgram);
        System.out.println("Enrollment Year: " + enrollmentYear);
        System.out.println("Enrolled Units: " + getEnrolledUnits());
        if (!coursesToGrades.isEmpty()) {
            System.out.println("Courses: ");
            for (Entry<Course, Float> courseGrade : coursesToGrades.entrySet()) {
                System.out.printf(" - %s : %.02f%n", courseGrade.getKey(), courseGrade.getValue());
            }
        }
    }
}
