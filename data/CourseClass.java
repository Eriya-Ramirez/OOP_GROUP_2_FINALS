package data;

import java.util.ArrayList;
import java.util.List;

public class CourseClass {
    private final Course info;
    private final int classCode;
    private final CourseGrades grades;
    private String instructor;
    private BlockSection assignedSection;
    private final List<Student> students;

    public CourseClass(Course info, int classCode, String instructor, BlockSection assignedSection) {
        this.info = info;
        this.classCode = classCode;
        this.grades = new CourseGrades();
        this.students = new ArrayList<>();
        this.instructor = instructor;
        this.assignedSection = assignedSection;
    }

    public Course getInfo() {
        return info;
    }

    public int getClassCode() {
        return classCode;
    }

    public CourseGrades getGrades() {
        return grades;
    }

    public List<Student> getStudents() {
        return students;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String name) {
        this.instructor = instructor;
    }

    public BlockSection getAssignedSection() {
        return assignedSection;
    }

    public void setAssignedSection(BlockSection assignedSection) {
        this.assignedSection = assignedSection;
    }
}