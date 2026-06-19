package badsmells;

/*
 * Refactored smell: Message Chains
 *
 * What changed:
 * - Hid the internal navigation behind University.getCoordinatorPhoneNumber()
 *
 * Why better:
 * Client code no longer depends on the full object chain, which reduces coupling
 * to internal structure and follows the Law of Demeter more closely.
 */
public class MessageChainsExample {

    static class University {

        Department getDepartment() {
            return new Department();
        }

        String getCoordinatorPhoneNumber() {
            return getDepartment().getCoordinatorPhoneNumber();
        }
    }

    static class Department {

        Coordinator getCoordinator() {
            return new Coordinator();
        }

        String getCoordinatorPhoneNumber() {
            return getCoordinator().getOfficePhoneNumber();
        }
    }

    static class Coordinator {

        Office getOffice() {
            return new Office();
        }

        String getOfficePhoneNumber() {
            return getOffice().getPhoneNumber();
        }
    }

    static class Office {

        String getPhoneNumber() {
            return "555-0101";
        }
    }

    public void clientCode() {
        University university = new University();
        System.out.println(university.getCoordinatorPhoneNumber());
    }
}
