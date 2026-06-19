package org.example.homework.benchmark;

import org.example.homework.data.DataGenerator;
import org.example.homework.sort.BubbleSort;
import org.example.homework.sort.JavaArraySort;
import org.example.homework.sort.QuickSort;
import org.example.homework.sort.RadixSort;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
@State(Scope.Thread)
public class SortingBenchmark {

    @Param({"RANDOM", "SORTED", "REVERSED", "NEARLY_SORTED"})
    public String distributionName;

    private int[] baseArray;

    @Setup(Level.Trial)
    public void setup() {
        DataGenerator.Distribution distribution =
                DataGenerator.Distribution.valueOf(distributionName);
        baseArray = DataGenerator.generate(distribution);
    }

    @Benchmark
    public int[] bubbleSort() {
        int[] copy = Arrays.copyOf(baseArray, baseArray.length);
        BubbleSort.sort(copy);
        return copy;
    }

    @Benchmark
    public int[] quickSort() {
        int[] copy = Arrays.copyOf(baseArray, baseArray.length);
        QuickSort.sort(copy);
        return copy;
    }

    @Benchmark
    public int[] radixSort() {
        int[] copy = Arrays.copyOf(baseArray, baseArray.length);
        RadixSort.sort(copy);
        return copy;
    }

    @Benchmark
    public int[] arraysSort() {
        int[] copy = Arrays.copyOf(baseArray, baseArray.length);
        JavaArraySort.sort(copy);
        return copy;
    }
}