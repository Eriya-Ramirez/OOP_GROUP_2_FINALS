public class Course {
    private final String code, name, department;
    private final int units;

    public Course(String code, String name, String department, int units) {
        this.code = code;
        this.name = name;
        this.department = department;
        this.units = units;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getUnits() {
        return units;
    }
}