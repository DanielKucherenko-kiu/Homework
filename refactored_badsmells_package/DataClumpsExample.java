package badsmells;

/*
 * Refactored smell: Data Clumps
 *
 * What changed:
 * - Introduced ContactInfo to group name, email, and phone
 * - Moved related formatting and reachability behavior onto that object
 *
 * Why better:
 * The repeated cluster of related values is now modeled as one concept. This
 * reduces noisy method signatures and creates a natural home for related logic.
 */
public class DataClumpsExample {

    static class ContactInfo {
        private final String name;
        private final String email;
        private final String phone;

        ContactInfo(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        public String buildLabel() {
            return name + " <" + email + ">, phone: " + phone;
        }

        public String buildEmailGreeting() {
            return "To: " + email + ", hello " + name;
        }

        public String buildSmsMessage() {
            return "SMS to " + phone + ": Hi " + name;
        }

        public boolean isReachable() {
            return email != null && !email.trim().isEmpty()
                    && phone != null && !phone.trim().isEmpty();
        }
    }

    public void clientCode() {
        ContactInfo student = new ContactInfo("Nino", "nino@example.com", "+995-555-000-001");
        ContactInfo advisor = new ContactInfo("Giorgi", "giorgi@example.com", "+995-555-000-002");
        ContactInfo accountant = new ContactInfo("Maka", "maka@example.com", "+995-555-000-003");

        System.out.println(student.buildLabel());
        System.out.println(student.buildEmailGreeting());
        System.out.println(student.buildSmsMessage());
        System.out.println(student.isReachable());

        System.out.println(advisor.buildLabel());
        System.out.println(advisor.buildEmailGreeting());
        System.out.println(advisor.buildSmsMessage());
        System.out.println(advisor.isReachable());

        System.out.println(accountant.buildLabel());
        System.out.println(accountant.buildEmailGreeting());
        System.out.println(accountant.buildSmsMessage());
        System.out.println(accountant.isReachable());
    }
}
