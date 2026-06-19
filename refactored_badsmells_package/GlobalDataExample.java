package badsmells;

/*
 * Refactored smell: Global Data
 *
 * What changed:
 * - Moved shared mutable state into AcademicSettings
 * - Encapsulated all changes behind methods
 * - Passed the settings object to the services that need it
 *
 * Why better:
 * State ownership is now explicit and controlled. This reduces hidden coupling
 * and makes it clearer which code is allowed to read or change shared settings.
 */
public class GlobalDataExample {

    static class AcademicSettings {
        private String currentSemester = "SPRING";
        private double tuitionRate = 1250.0;

        public String getCurrentSemester() {
            return currentSemester;
        }

        public double getTuitionRate() {
            return tuitionRate;
        }

        public void openFallSemester() {
            currentSemester = "FALL";
        }

        public void approveRateIncrease() {
            tuitionRate += 100;
        }

        public void applyEmergencyIncrease(double delta) {
            tuitionRate += delta;
        }
    }

    static class BillingService {
        private final AcademicSettings settings;

        BillingService(AcademicSettings settings) {
            this.settings = settings;
        }

        public double calculateInvoice(int credits) {
            return credits * settings.getTuitionRate();
        }
    }

    static class SemesterAdministration {
        private final AcademicSettings settings;

        SemesterAdministration(AcademicSettings settings) {
            this.settings = settings;
        }

        public void openFallSemester() {
            settings.openFallSemester();
        }

        public void approveRateIncrease() {
            settings.approveRateIncrease();
        }
    }

    public void clientCode() {
        AcademicSettings settings = new AcademicSettings();
        BillingService billingService = new BillingService(settings);
        SemesterAdministration administration = new SemesterAdministration(settings);

        System.out.println(settings.getCurrentSemester());
        System.out.println(billingService.calculateInvoice(3));
        administration.openFallSemester();
        administration.approveRateIncrease();
        System.out.println(settings.getCurrentSemester());
        System.out.println(billingService.calculateInvoice(3));
    }
}
