package org.example.homework;

import org.example.homework.data.DataGenerator;
import org.example.homework.sort.BubbleSort;
import org.example.homework.sort.JavaArraySort;
import org.example.homework.sort.QuickSort;
import org.example.homework.sort.RadixSort;
import org.example.homework.verify.SortVerifier;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Generating data...");
        int[] original = DataGenerator.generate(DataGenerator.Distribution.RANDOM);

        // Bubble Sort
        int[] bubble = Arrays.copyOf(original, original.length);
        System.out.println("Running Bubble Sort...");
        BubbleSort.sort(bubble);
        SortVerifier.assertCorrect(original, bubble);
        System.out.println("BubbleSort OK");

        // Quick Sort
        int[] quick = Arrays.copyOf(original, original.length);
        System.out.println("Running Quick Sort...");
        QuickSort.sort(quick);
        SortVerifier.assertCorrect(original, quick);
        System.out.println("QuickSort OK");

        // Radix Sort
        int[] radix = Arrays.copyOf(original, original.length);
        System.out.println("Running Radix Sort...");
        RadixSort.sort(radix);
        SortVerifier.assertCorrect(original, radix);
        System.out.println("RadixSort OK");

        // Java Arrays.sort
        int[] jdk = Arrays.copyOf(original, original.length);
        System.out.println("Running Arrays.sort...");
        JavaArraySort.sort(jdk);
        SortVerifier.assertCorrect(original, jdk);
        System.out.println("Arrays.sort OK");

        System.out.println("All algorithms are correct ✅");
    }
}