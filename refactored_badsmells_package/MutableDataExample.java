package badsmells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Refactored smell: Mutable Data
 *
 * What changed:
 * - Returned an unmodifiable view instead of the internal list itself
 * - Added explicit methods for controlled state changes
 *
 * Why better:
 * Callers can inspect the enrolled students, but they can no longer mutate the
 * object's internal state behind its back.
 */
public class MutableDataExample {

    private final List<String> enrolledStudents = new ArrayList<>();

    public List<String> getEnrolledStudents() {
        return Collections.unmodifiableList(enrolledStudents);
    }

    public void enroll(String studentId) {
        enrolledStudents.add(studentId);
    }

    public int enrolledStudentCount() {
        return enrolledStudents.size();
    }

    public void clientCode() {
        enroll("s-1001");
        System.out.println(getEnrolledStudents());
        System.out.println(enrolledStudentCount());
    }
}
