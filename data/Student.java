package data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Student {
    private final String name, gender, degreeProgram;
    private final int studentNo, enrollmentYear;
    private final Map<Course, Float> coursesToGrades = new LinkedHashMap<>();

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

    public String getGender() {
        return gender;
    }

    public String getDegreeProgram() {
        return degreeProgram;
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

    public void addCourse(Course course) {
        coursesToGrades.putIfAbsent(course, 0F);
    }

    public void setCourseGrade(Course course, float grade) {
        coursesToGrades.put(course, grade);
    }

    public void addCourses(BlockSection section) {
        for (Course course : section.getCourses()) {
            addCourse(course);
        }
    }

    public boolean containsCourse(String code) {
        return coursesToGrades.keySet().stream().anyMatch(c -> c.getCode().equalsIgnoreCase(code));
    }

    public int getEnrolledUnits() {
        return coursesToGrades.keySet().stream().mapToInt(Course::getUnits).sum();
    }

    public abstract void showDetails();

    protected void showBasicDetails() {
        System.out.println("Student Number: " + studentNo);
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Degree Program: " + degreeProgram);
        System.out.println("Enrollment Year: " + enrollmentYear);
        System.out.println("Enrolled Units: " + getEnrolledUnits());
    }

    protected void showCoursesDetails() {
        if (!coursesToGrades.isEmpty()) {
            System.out.println("Courses: ");
            for (Entry<Course, Float> courseGrade : coursesToGrades.entrySet()) {
                System.out.printf(" - %s : %.02f%n", courseGrade.getKey(), courseGrade.getValue());
            }
        }
    }
}
