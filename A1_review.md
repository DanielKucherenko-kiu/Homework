## Summary

The repository contains the expected sorting code, JMH benchmark class, and verification helper, but it misses two important assignment-level requirements: the benchmark size is only `10,000`, and no real Assignment 1 analytical report is evidenced in the repository.

## Strengths

- `pom.xml` contains real JMH dependencies and annotation processing.
- `src/main/java/org/example/homework/benchmark/SortingBenchmark.java` benchmarks all four required algorithms with separate `@Benchmark` methods.
- `src/main/java/org/example/homework/sort/RadixSort.java` is a byte-wise radix implementation with `4` passes and explicit signed-int handling via `Integer.MIN_VALUE`.
- `src/main/java/org/example/homework/verify/SortVerifier.java` checks both sortedness and equality against `Arrays.sort()`.
- `src/main/java/org/example/homework/sort/BubbleSort.java` includes early exit.

## Findings

- Major: `src/main/java/org/example/homework/data/DataGenerator.java` fixes `N = 10_000`, so the tracked benchmark scope is materially below the required `1,000,000` integers.
- Major: the repository does not contain a real Assignment 1 analytical report artifact. `Lab01.pdf` is unrelated to this assignment, so the required report deliverable is still missing.
- Moderate: correctness verification is only visibly exercised in `src/main/java/org/example/homework/Main.java` on the `RANDOM` distribution. A reusable verifier exists, but repository evidence does not show the other required distributions being verified.

## Requirement Checklist

- Java source code: Present
- JMH benchmark classes: Present
- Analytical report in accepted submitted format relevant to Assignment 1: Not satisfied
- Bubble Sort with early exit: Present
- In-place Quick Sort with identifiable pivot strategy: Present
- LSD Radix Sort, base `256`, `4` passes, negative support: Present
- `Arrays.sort(int[])` benchmark/reference: Present
- Uniform random dataset: Present
- Already sorted dataset: Present
- Reverse sorted dataset: Present
- Nearly sorted dataset with about `1%` swaps: Present
- Correctness check against `Arrays.sort()`: Present
- Explicit sortedness check: Present
- Required benchmark size of `1,000,000`: Not satisfied

## Verdict

Partially meets requirements.
