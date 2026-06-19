package badsmells;

/*
 * Refactored smell: Temporary Field
 *
 * What changed:
 * - Extracted separate classes for onsite and online exam preparation
 * - Moved mode-specific fields into the class that actually uses them
 *
 * Why better:
 * Each object now has a consistent state. There are no irrelevant fields hanging
 * around depending on which preparation mode was used.
 */
public class TemporaryFieldExample {

    static class OnsiteExamPreparation {
        private final String examRoom = "B-204";

        public String prepare() {
            return "Use room " + examRoom;
        }

        public String getExamRoom() {
            return examRoom;
        }
    }

    static class OnlineExamPreparation {
        private final String meetingLink = "https://meet.example/exam";

        public String prepare() {
            return "Join " + meetingLink;
        }

        public String getMeetingLink() {
            return meetingLink;
        }
    }

    public void clientCode() {
        OnsiteExamPreparation onsiteExam = new OnsiteExamPreparation();
        OnlineExamPreparation onlineExam = new OnlineExamPreparation();

        System.out.println(onsiteExam.prepare());
        System.out.println(onlineExam.prepare());
        System.out.println("room=" + onsiteExam.getExamRoom() + ", link=" + onlineExam.getMeetingLink());
    }
}
