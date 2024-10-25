package data;

public class RegularStudent extends Student {
    private BlockSection blockSection;

    public RegularStudent(String name, String gender, String degreeProgram, int studentNo, int enrollmentYear, BlockSection blockSection) {
        super(name, gender, degreeProgram, studentNo, enrollmentYear);
        this.blockSection = blockSection;
    }

    public BlockSection getBlockSection() {
        return blockSection;
    }

    public void setBlockSection(BlockSection blockSection) {
        this.blockSection = blockSection;
    }

    @Override
    public void showDetails() {
        super.showCoursesDetails();
        if (blockSection != null) {
            System.out.println("Block Section: " + blockSection.getName());
        }
        super.showCoursesDetails();
    }
}
