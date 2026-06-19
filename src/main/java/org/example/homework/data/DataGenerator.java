package org.example.homework.data;

import java.util.Arrays;
import java.util.Random;

public final class DataGenerator {
    public static final int N = 10_000;
    private static final long SEED = 42L;

    private DataGenerator() {}

    public enum Distribution {
        RANDOM,
        SORTED,
        REVERSED,
        NEARLY_SORTED
    }

    public static int[] generate(Distribution distribution) {
        return switch (distribution) {
            case RANDOM -> randomArray(N);
            case SORTED -> sortedArray(N);
            case REVERSED -> reversedArray(N);
            case NEARLY_SORTED -> nearlySortedArray(N);
        };
    }

    private static int[] randomArray(int n) {
        Random random = new Random(SEED);
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = random.nextInt();
        }

        return a;
    }

    private static int[] sortedArray(int n) {
        int[] a = randomArray(n);
        Arrays.sort(a);
        return a;
    }

    private static int[] reversedArray(int n) {
        int[] a = sortedArray(n);

        for (int i = 0, j = a.length - 1; i < j; i++, j--) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        return a;
    }

    private static int[] nearlySortedArray(int n) {
        int[] a = sortedArray(n);
        Random random = new Random(SEED);

        int swaps = n / 100; // 1%

        for (int i = 0; i < swaps; i++) {
            int x = random.nextInt(n);
            int y = random.nextInt(n);

            int temp = a[x];
            a[x] = a[y];
            a[y] = temp;
        }

        return a;
    }
}