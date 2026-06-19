package badsmells;

/*
 * Refactored smell: Feature Envy
 *
 * What changed:
 * - Moved scholarship qualification logic into StudentAccount
 *
 * Why better:
 * The behavior now lives with the data it uses most. That improves cohesion and
 * reduces the need for outside objects to inspect StudentAccount internals.
 */
public class FeatureEnvyExample {

    static class StudentAccount {

        private final int completedCredits;
        private final double gpa;

        StudentAccount(int completedCredits, double gpa) {
            this.completedCredits = completedCredits;
            this.gpa = gpa;
        }

        public boolean qualifiesForScholarship() {
            return completedCredits >= 30 && gpa >= 3.7;
        }
    }

    public void clientCode() {
        StudentAccount account = new StudentAccount(36, 3.9);
        System.out.println(account.qualifiesForScholarship());
    }
}
