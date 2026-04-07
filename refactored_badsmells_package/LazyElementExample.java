package badsmells;

/*
 * Refactored smell: Lazy Element
 *
 * What changed:
 * - Inlined the tiny helper class into the example
 *
 * Why better:
 * The extra class added almost no value. Removing it simplifies the code and
 * keeps the behavior in the only place that actually uses it.
 */
public class LazyElementExample {

    private String formatStudentName(String name) {
        return name.trim();
    }

    public void clientCode() {
        System.out.println(formatStudentName("  Nino  "));
    }
}
