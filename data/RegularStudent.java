package data;

public class RegularStudent extends Student {
    private final BlockSection blockSection;

    public RegularStudent(String name, String gender, String degreeProgram, int studentNo, int enrollmentYear, BlockSection blockSection) {
        super(name, gender, degreeProgram, studentNo, enrollmentYear);
        this.blockSection = blockSection;
    }

    public BlockSection getBlockSection() {
        return blockSection;
    }

    @Override
    public void showDetails() {
        super.showBasicDetails();
        if (blockSection != null) {
            System.out.println("Block Section: " + blockSection.getName());
        }
        super.showCoursesDetails();
    }
}
