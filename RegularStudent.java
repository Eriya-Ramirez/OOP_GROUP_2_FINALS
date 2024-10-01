public class RegularStudent extends Student {
    private BlockSection blockSection;

    public RegularStudent(String name, String gender, String degreeProgram, int studentNo, int enrollmentYear, int enrolledUnits) {
        super(name, gender, degreeProgram, studentNo, enrollmentYear, enrolledUnits);
    }

    public BlockSection getBlockSection() {
        return blockSection;
    }

    public void setBlockSection(BlockSection blockSection) {
        this.blockSection = blockSection;
    }
}