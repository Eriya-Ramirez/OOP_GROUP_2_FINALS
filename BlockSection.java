import java.util.ArrayList;
import java.util.List;

public class BlockSection {
    private final String blockName;
    private final List<Student> students;

    public BlockSection(String blockName) {
        this.blockName = blockName;
        this.students = new ArrayList<>();
    }

    public String getBlockName() {
        return blockName;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    public List<Student> getStudents() {
        return this.students;
    }
}
