The repository needs assignment-aligned deliverables more than algorithm rewrites.

Action items:

- Change `src/main/java/org/example/homework/data/DataGenerator.java` so the benchmarked dataset size is `1_000_000` for the scalable algorithms, with any Bubble Sort exception handled explicitly and documented.
- Add an Assignment 1 report PDF that summarizes JMH results for Bubble Sort, Quick Sort, LSD Radix Sort, and `Arrays.sort(int[])`.
- Expand the visible correctness run so it exercises all required distributions, not only the random dataset used in `src/main/java/org/example/homework/Main.java`.

Recommended order:

1. Fix benchmark size.
2. Add full correctness verification coverage.
3. Re-run JMH and replace the PDF with the correct report.
