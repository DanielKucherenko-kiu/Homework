package badsmells;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Refactored smell: Loops
 *
 * What changed:
 * - Replaced the manual loop with a stream pipeline
 * - Extracted the GPA predicate into a named method
 *
 * Why better:
 * The method now communicates the collection transformation directly: filter the
 * honor students, then map them to names.
 */
public class LoopsExample {

    public List<String> honorStudents(List<Student> students) {
        return students.stream()
                .filter(this::isHonorStudent)
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    private boolean isHonorStudent(Student student) {
        return student.getGpa() > 3.5;
    }

    static class Student {

        private final String name;
        private final double gpa;

        Student(String name, double gpa) {
            this.name = name;
            this.gpa = gpa;
        }

        public String getName() {
            return name;
        }

        public double getGpa() {
            return gpa;
        }
    }

    public void clientCode() {
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("Nino", 3.9));
        students.add(new Student("Giorgi", 3.1));
        students.add(new Student("Maka", 3.7));

        System.out.println(honorStudents(students));
    }
}
