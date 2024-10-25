package data;

import java.util.HashMap;
import java.util.Map;

public class CourseGrades {
    private final Map<Student, Float> grades = new HashMap<>();

    public CourseGrades() {
    }

    public float getGrade(Student student) {
        return this.grades.getOrDefault(student, 0F);
    }

    public void setGrade(Student student, float grade) {
        this.grades.put(student, grade);
    }
}
