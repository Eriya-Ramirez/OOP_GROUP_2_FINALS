
public class Student {
    private String name, gender, degreeProgram;
    private int studentNo, enrollmentYear, enrolledUnits;

    public Student(String name, String gender, String degreeProgram, int studentNo, int enrollmentYear, int enrolledUnits) {
        this.name = name;
        this.gender = gender;
        this.degreeProgram = degreeProgram;
        this.studentNo = studentNo;
        this.enrollmentYear = enrollmentYear;
        this.enrolledUnits = enrolledUnits;
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

    public int getEnrolledUnits() {
        return enrolledUnits;
    }
}