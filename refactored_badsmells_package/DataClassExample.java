package badsmells;

/*
 * Refactored smell: Data Class
 *
 * What changed:
 * - Encapsulated StudentRecord state
 * - Moved eligibility, discount, and standing behavior into StudentRecord
 *
 * Why better:
 * The class is no longer just a passive bag of fields. The behavior now lives
 * with the data it depends on, which improves cohesion and encapsulation.
 */
public class DataClassExample {

    public static class StudentRecord {

        private final String name;
        private final int credits;
        private final double gpa;

        public StudentRecord(String name, int credits, double gpa) {
            this.name = name;
            this.credits = credits;
            this.gpa = gpa;
        }

        public boolean isEligibleForHonors() {
            return credits >= 30 && gpa >= 3.7;
        }

        public double discountPercent() {
            if (gpa >= 3.8) {
                return 0.15;
            }
            if (gpa >= 3.5) {
                return 0.10;
            }
            return 0.0;
        }

        public String academicStandingDescription() {
            if (gpa < 2.0) {
                return name + " is on academic probation";
            }
            if (credits < 15) {
                return name + " is a new student";
            }
            return name + " is in good standing";
        }
    }

    public void clientCode() {
        StudentRecord student = new StudentRecord("Nino", 32, 3.8);

        System.out.println(student.isEligibleForHonors());
        System.out.println(student.discountPercent());
        System.out.println(student.academicStandingDescription());
    }
}
