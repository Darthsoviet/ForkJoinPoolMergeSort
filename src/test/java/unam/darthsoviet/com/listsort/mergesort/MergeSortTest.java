package unam.darthsoviet.com.listsort.mergesort;


import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import unam.darthsoviet.com.listsort.Sorter;
import unam.darthsoviet.com.util.ArraySaver;
import unam.darthsoviet.com.util.Timer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class MergeSortTest {
    static List<Integer> numbers;

    static List<Integer> numbersExpectedUnordered;

    private final Timer timer = new Timer();

    @BeforeAll
    public static void initTestData() throws IOException, ClassNotFoundException {
        numbers = ArraySaver.getOrCreateArrayFromFile("hugeArray.ser");
        numbersExpectedUnordered = ArraySaver.getOrCreateArrayFromFile("hugeArray.ser");


    }


    @Test
    public void basicSort_sequential() {
        List<Integer> integers = Arrays.asList(3, 2, 1, 0);
        List<Integer> integers2 = Arrays.asList(3, 2, 1, 0);

        Sorter mergeSort = new MergeSort();

        timer.start();
        mergeSort.sort(integers2);
        timer.end();
        timer.start();

        Collections.sort(integers);
        timer.end();

        Assertions.assertEquals(integers2, integers);


    }

    @Test
    public void basicSort() {
        List<Integer> integers = Arrays.asList(3, 2, 1, 0);
        List<Integer> integers2 = Arrays.asList(3, 2, 1, 0);

        Sorter mergeSort = new ParallelMergeSort();

        timer.start();
        mergeSort.sort(integers2);
        timer.end();
        timer.start();

        Collections.sort(integers);
        timer.end();

        Assertions.assertEquals(integers, integers2);


    }


    @Test
    public void bigSort() {
        Sorter parallel = new ParallelMergeSort(1_000);
        Sorter sequential = new MergeSort();

        timer.start();
        timer.start();
        parallel.sort(numbers);
        timer.end();
        timer.start();
        sequential.sort(numbersExpectedUnordered);
        timer.end();
        timer.end();
        Assertions.assertEquals(numbersExpectedUnordered, numbers);
    }


    @Test

    public void bigSort_comparator() {
        Sorter sequential = new MergeSort();

        Sorter parallel = new ParallelMergeSort(1_000  );

        timer.start();
        timer.start();
        parallel.sort(numbers, Comparator.naturalOrder());
        timer.end();
        timer.start();
        sequential.sort(numbersExpectedUnordered, Comparator.naturalOrder());
        timer.end();
        timer.end();
        Assertions.assertEquals(numbersExpectedUnordered, numbers);
    }

}
