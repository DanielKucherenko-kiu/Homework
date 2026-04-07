package badsmells;

/*
 * Refactored smell: Alternative Classes with Different Interfaces
 *
 * What changed:
 * - Introduced a shared abstraction: Classroom
 * - Aligned the client around one common method: startSession()
 * - Used an adapter for TeamsClassroom so both classroom types can be used uniformly
 *
 * Why better:
 * Client code now depends on a common protocol instead of concrete, mismatched APIs.
 * That lowers coupling and makes it easier to add more classroom providers later.
 */
public class AlternativeClassesWithDifferentInterfacesExample {

    interface Classroom {
        void startSession();
    }

    static class ZoomClassroom implements Classroom {

        @Override
        public void startSession() {
            System.out.println("Zoom session started");
        }
    }

    static class TeamsClassroom {

        public void openMeeting() {
            System.out.println("Teams meeting started");
        }
    }

    static class TeamsClassroomAdapter implements Classroom {
        private final TeamsClassroom teamsClassroom;

        public TeamsClassroomAdapter(TeamsClassroom teamsClassroom) {
            this.teamsClassroom = teamsClassroom;
        }

        @Override
        public void startSession() {
            teamsClassroom.openMeeting();
        }
    }

    public void clientCode() {
        Classroom zoom = new ZoomClassroom();
        Classroom teams = new TeamsClassroomAdapter(new TeamsClassroom());

        zoom.startSession();
        teams.startSession();
    }
}
