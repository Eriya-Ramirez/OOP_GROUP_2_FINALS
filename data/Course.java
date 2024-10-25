package data;

public class Course {
    private final String code;
    private final int units;
    private final String schedule;

    public Course(String code, int units, String schedule) {
        this.code = code;
        this.units = units;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public int getUnits() {
        return units;
    }

    public String getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return code + " (" + schedule + ")";
    }
}
