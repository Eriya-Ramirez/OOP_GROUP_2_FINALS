package data;

public class Student {
    private String name, gender, degreeProgram;
    private int studentNo, enrollmentYear, enrolledUnits;

    public Student(String name, String gender, String degreeProgram, int studentNo, int enrollmentYear) {
        this.name = name;
        this.gender = gender;
        this.degreeProgram = degreeProgram;
        this.studentNo = studentNo;
        this.enrollmentYear = enrollmentYear;
    }

    public int getStudentNo() {
        return studentNo;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void showDetails() {
        System.out.println("data.Student Number: " + studentNo);
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Degree Program: " + degreeProgram);
        System.out.println("Enrollment Year: " + enrollmentYear);
        System.out.println();
    }
}
