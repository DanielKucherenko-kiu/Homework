package org.example.homework.sort;

public final class RadixSort {
    private RadixSort() {}

    public static void sort(int[] a) {
        int n = a.length;
        int[] output = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] ^= Integer.MIN_VALUE;
        }

        for (int shift = 0; shift < 32; shift += 8) {
            int[] count = new int[256];

            for (int value : a) {
                int bucket = (value >>> shift) & 0xFF;
                count[bucket]++;
            }

            for (int i = 1; i < 256; i++) {
                count[i] += count[i - 1];
            }

            for (int i = n - 1; i >= 0; i--) {
                int value = a[i];
                int bucket = (value >>> shift) & 0xFF;
                output[--count[bucket]] = value;
            }

            System.arraycopy(output, 0, a, 0, n);
        }

        for (int i = 0; i < n; i++) {
            a[i] ^= Integer.MIN_VALUE;
        }
    }
}