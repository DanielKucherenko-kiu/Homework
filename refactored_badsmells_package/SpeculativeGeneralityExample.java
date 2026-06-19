package badsmells;

/*
 * Refactored smell: Speculative Generality
 *
 * What changed:
 * - Removed unused future-focused parameters from the abstraction
 * - Simplified NotificationChannel to the behavior that actually exists today
 *
 * Why better:
 * The interface now reflects current needs instead of imaginary variation.
 * That makes the code easier to understand and use.
 */
public class SpeculativeGeneralityExample {

    interface NotificationChannel {
        void send(String message);
    }

    static class EmailChannel implements NotificationChannel {

        @Override
        public void send(String message) {
            System.out.println(message);
        }
    }

    public void clientCode() {
        NotificationChannel channel = new EmailChannel();
        channel.send("Exam starts at 10:00");
    }
}
