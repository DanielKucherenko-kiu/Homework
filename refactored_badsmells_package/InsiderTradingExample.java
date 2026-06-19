package badsmells;

/*
 * Refactored smell: Insider Trading
 *
 * What changed:
 * - Hid BankAccount fields
 * - Moved account-specific decision and mutation behind intention-revealing methods
 *
 * Why better:
 * AuditService no longer manipulates account internals directly. The classes are
 * less tightly coupled and BankAccount protects its own invariants.
 */
public class InsiderTradingExample {

    static class BankAccount {

        private double balance;
        private String status;

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public void freezeIfOverdrawn() {
            if (balance < 0) {
                status = "FROZEN";
            }
        }

        public String getStatus() {
            return status;
        }
    }

    static class AuditService {

        public void freezeIfNeeded(BankAccount account) {
            account.freezeIfOverdrawn();
        }
    }

    public void clientCode() {
        BankAccount account = new BankAccount();
        account.setBalance(-50);

        AuditService auditService = new AuditService();
        auditService.freezeIfNeeded(account);

        System.out.println(account.getStatus());
    }
}
