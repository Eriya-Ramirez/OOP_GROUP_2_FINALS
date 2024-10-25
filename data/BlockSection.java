package data;

import java.util.ArrayList;
import java.util.List;

public class BlockSection {
    private final String name;
    private final List<Course> courses = new ArrayList<>();

    public BlockSection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
