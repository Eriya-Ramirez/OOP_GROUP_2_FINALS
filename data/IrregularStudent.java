package data;

import java.util.ArrayList;
import java.util.List;

public class IrregularStudent extends Student {
    private final List<BlockSection> blockSections = new ArrayList<>();

    public IrregularStudent(String name, String gender, String degreeProgram, int studentNo, int enrollmentYear) {
        super(name, gender, degreeProgram, studentNo, enrollmentYear);
    }

    public List<BlockSection> getBlockSections() {
        return blockSections;
    }

    public void addBlockSection(BlockSection section) {
        this.blockSections.add(section);
    }

    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Block Sections:");
        for (BlockSection section : blockSections) {
            System.out.println("- " + section.getBlockName());
        }
    }
}
