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

    public int getStudentNo() {
        return studentNo;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void showDetails() {
        System.out.println("Student Number: " + studentNo);
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Degree Program: " + degreeProgram);
        System.out.println("Enrollment Year: " + enrollmentYear);
        System.out.println("Enrolled Units: " + enrolledUnits);
        System.out.println();
    }
}
