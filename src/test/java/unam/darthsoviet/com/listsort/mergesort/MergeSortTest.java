package unam.darthsoviet.com.listsort.mergesort;


import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import unam.darthsoviet.com.listsort.Sorter;
import unam.darthsoviet.com.util.Timer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Unit test for simple App.
 */
@Execution(ExecutionMode.SAME_THREAD)
public class MergeSortTest {
    static List<Integer> numbers;
    private Timer timer = new Timer();

    @BeforeAll
    public static void initTestData() throws IOException, ClassNotFoundException {

        Path path = Paths.get("." + File.separator + "src" + File.separator + "test" +  File.separator + "resources" + File.separator + "data" + File.separator + "hugeArray.ser");
        if (!Files.exists(path)) {
            numbers = new LinkedList<>();
            IntStream.range(0, 2_000_000).forEach(numbers::add);
            Collections.shuffle(numbers);
            Files.createFile(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path));
            objectOutputStream.writeObject(numbers);
        } else {
            ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path));
            numbers = (List) objectInputStream.readObject();
        }
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

        Assertions.assertEquals(integers2,integers);


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

        Assertions.assertEquals(integers,integers2);


    }


    @Test
    public void bigSort() {
        Sorter parallel = new ParallelMergeSort(10_000);
        Sorter sequential = new MergeSort();

        timer.start();
        parallel.sort(numbers);
        timer.end();
        timer.start();
        sequential.sort(numbers);
        timer.end();
        Assertions.assertEquals(numbers, numbers);
    }


    @Test

    public void bigSort_comparator() {
        Sorter sequential = new MergeSort();

        Sorter parallel = new ParallelMergeSort(10_000, sequential);

        timer.start();
        timer.start();
        parallel.sort(numbers, Comparator.naturalOrder());
        timer.end();
        timer.start();
        sequential.sort(numbers, Comparator.naturalOrder());
        timer.end();
        timer.end();
        Assertions.assertNotNull(numbers);
    }

}
