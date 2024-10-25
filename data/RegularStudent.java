package data;

public class RegularStudent extends Student {
    private BlockSection blockSection;

    public RegularStudent(String name, String gender, String degreeProgram, int studentNo, int enrollmentYear) {
        super(name, gender, degreeProgram, studentNo, enrollmentYear);
    }

    public void setBlockSection(BlockSection blockSection) {
        this.blockSection = blockSection;
    }

    @Override
    public void showDetails() {
        super.showDetails();  // Call parent method
        if (blockSection != null) {
            System.out.println("Block Section: " + blockSection.getBlockName());
        }
    }
}
