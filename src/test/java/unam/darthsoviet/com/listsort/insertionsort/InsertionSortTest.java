package unam.darthsoviet.com.listsort.insertionsort;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import unam.darthsoviet.com.listsort.Sorter;
import unam.darthsoviet.com.util.ArraySaver;
import unam.darthsoviet.com.util.Timer;

import java.io.IOException;
import java.util.*;

/**
 * Unit test for simple App.
 */
@Execution(ExecutionMode.CONCURRENT)

public class InsertionSortTest {
    static List<Integer> numbers;
    static List<Integer> numbersExpectedUnordered;

    private Timer timer = new Timer();

    @BeforeAll
    public static void initTestData() throws IOException, ClassNotFoundException {
        numbers = ArraySaver.getOrCreateArrayFromFile("hugeArray.ser");
        numbersExpectedUnordered = ArraySaver.getOrCreateArrayFromFile("hugeArray.ser");
    }


    @Test
    public void basicSort() {
        List<Integer> integers = Arrays.asList(3, 2, 1, 0);
        List<Integer> integersExpected = Arrays.asList(3, 2, 1, 0);

        Sorter Sorter = new InsertionSort();

        timer.start();
        Sorter.sort(integers);
        timer.end();
        timer.start();

        Collections.sort(integersExpected);
        timer.end();

        Assertions.assertEquals(integersExpected, integers);


    }

    @Test
    public void bigSort() {
        Sorter Sorter = new InsertionSort();

        timer.start();
        Sorter.sort(numbers);
        timer.end();
        timer.start();

        Collections.sort(numbersExpectedUnordered);
        timer.end();

        Assertions.assertEquals(numbersExpectedUnordered, numbers);
    }

}
