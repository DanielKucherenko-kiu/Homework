package badsmells;

/*
 * Refactored smell: Primitive Obsession
 *
 * What changed:
 * - Replaced raw status and country strings with enums
 * - Introduced Money and StudentAge domain objects
 * - Moved validation into the domain types
 *
 * Why better:
 * The method now works with meaningful concepts instead of unrelated primitives.
 * Rules are clearer and invalid values are harder to pass accidentally.
 */
public class PrimitiveObsessionExample {

    enum StudentStatus {
        ACTIVE,
        BLOCKED
    }

    enum Country {
        GE,
        US
    }

    static class Money {
        private final double amount;

        Money(double amount) {
            this.amount = amount;
        }

        public boolean isLessThan(double threshold) {
            return amount < threshold;
        }
    }

    static class StudentAge {
        private final int years;

        StudentAge(int years) {
            this.years = years;
        }

        public boolean isAdult() {
            return years >= 18;
        }
    }

    public boolean canRentDormRoom(StudentAge age, StudentStatus status, Money unpaidBalance, Country country) {
        return age.isAdult()
                && status == StudentStatus.ACTIVE
                && unpaidBalance.isLessThan(100)
                && country == Country.GE;
    }

    public void clientCode() {
        System.out.println(canRentDormRoom(new StudentAge(19), StudentStatus.ACTIVE, new Money(0.0), Country.GE));
        System.out.println(canRentDormRoom(new StudentAge(17), StudentStatus.BLOCKED, new Money(120.0), Country.US));
    }
}
