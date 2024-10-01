import java.util.ArrayList;
import java.util.List;

public class IrregularStudent extends Student {
    private final List<BlockSection> blockSections = new ArrayList<>();

    public IrregularStudent(String name, String gender, String degreeProgram, int studentNo, int enrollmentYear, int enrolledUnits) {
        super(name, gender, degreeProgram, studentNo, enrollmentYear, enrolledUnits);
    }

    public void addBlockSection(BlockSection section) {
        this.blockSections.add(section);
    }

    public void removeBlockSection(BlockSection section) {
        this.blockSections.remove(section);
    }

    public List<BlockSection> getBlockSections() {
        return this.blockSections;
    }
}
