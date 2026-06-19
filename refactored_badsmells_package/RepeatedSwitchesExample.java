package badsmells;

/*
 * Refactored smell: Repeated Switches
 *
 * What changed:
 * - Centralized student type behavior inside one enum
 * - Removed duplicated switch statements
 *
 * Why better:
 * Each student type now defines its own discount and dorm priority in one place.
 * Adding a new type requires one localized change instead of editing many switches.
 */
public class RepeatedSwitchesExample {

    enum StudentType {
        STUDENT(0.05, "NORMAL"),
        ATHLETE(0.15, "HIGH"),
        EMPLOYEE_CHILD(0.25, "LOW"),
        UNKNOWN(0.0, "UNKNOWN");

        private final double tuitionDiscount;
        private final String dormPriority;

        StudentType(double tuitionDiscount, String dormPriority) {
            this.tuitionDiscount = tuitionDiscount;
            this.dormPriority = dormPriority;
        }

        public double tuitionDiscount() {
            return tuitionDiscount;
        }

        public String dormPriority() {
            return dormPriority;
        }

        public static StudentType from(String value) {
            for (StudentType type : values()) {
                if (type.name().equals(value)) {
                    return type;
                }
            }
            return UNKNOWN;
        }
    }

    public double tuitionDiscount(String studentType) {
        return StudentType.from(studentType).tuitionDiscount();
    }

    public String dormPriority(String studentType) {
        return StudentType.from(studentType).dormPriority();
    }

    public void clientCode() {
        System.out.println(tuitionDiscount("ATHLETE"));
        System.out.println(dormPriority("ATHLETE"));
    }
}
