package badsmells;

/*
 * Refactored smell: Long Parameter List
 *
 * What changed:
 * - Introduced parameter objects for address and guardian contact
 * - Wrapped the full registration data in StudentRegistration
 *
 * Why better:
 * The signature now reflects meaningful concepts instead of a long list of raw
 * values, which makes both the call site and the method easier to read.
 */
public class LongParameterListExample {

    static class Address {
        private final String city;
        private final String street;
        private final String zipCode;

        Address(String city, String street, String zipCode) {
            this.city = city;
            this.street = street;
            this.zipCode = zipCode;
        }

        public String summary() {
            return city + ", " + street + ", " + zipCode;
        }
    }

    static class GuardianContact {
        private final String name;
        private final String phone;

        GuardianContact(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }
    }

    static class StudentRegistration {
        private final String firstName;
        private final String lastName;
        private final String email;
        private final String phone;
        private final Address address;
        private final GuardianContact guardianContact;
        private final String program;
        private final int startYear;
        private final boolean scholarship;

        StudentRegistration(String firstName, String lastName, String email, String phone,
                            Address address, GuardianContact guardianContact,
                            String program, int startYear, boolean scholarship) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.guardianContact = guardianContact;
            this.program = program;
            this.startYear = startYear;
            this.scholarship = scholarship;
        }
    }

    public String registerStudent(StudentRegistration registration) {
        return registration.firstName + " " + registration.lastName + " -> "
                + registration.program + " (" + registration.startYear + "), guardian="
                + registration.guardianContact.name + ", scholarship=" + registration.scholarship
                + ", address=" + registration.address.summary() + ", contact="
                + registration.email + "/" + registration.phone + ", guardianPhone="
                + registration.guardianContact.phone;
    }

    public void clientCode() {
        Address address = new Address("Tbilisi", "Rustaveli Ave 10", "0108");
        GuardianContact guardian = new GuardianContact("Maka Beridze", "+995-555-000-999");
        StudentRegistration registration = new StudentRegistration(
                "Nino",
                "Beridze",
                "nino@example.com",
                "+995-555-000-001",
                address,
                guardian,
                "Computer Science",
                2026,
                true
        );

        String summary = registerStudent(registration);
        System.out.println(summary);
    }
}
