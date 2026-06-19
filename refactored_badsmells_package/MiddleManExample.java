package badsmells;

/*
 * Refactored smell: Middle Man
 *
 * What changed:
 * - Removed unnecessary delegation from the client path
 * - Let the client talk directly to TranscriptService
 *
 * Why better:
 * The extra forwarding layer added no real policy or abstraction value. The
 * client is simpler and the design avoids needless indirection.
 */
public class MiddleManExample {

    static class TranscriptService {

        public String findGrade(String studentId) {
            return "A";
        }
    }

    public void clientCode() {
        TranscriptService transcriptService = new TranscriptService();
        System.out.println(transcriptService.findGrade("s-1001"));
    }
}
