package unam.darthsoviet.com.listsort.insertionsort;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import unam.darthsoviet.com.listsort.Sorter;
import unam.darthsoviet.com.util.Timer;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Unit test for simple App.
 */
@Execution(ExecutionMode.CONCURRENT)

public class InsertionSortTest {
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
        List<Integer> expected = new ArrayList<>(numbers);
        Sorter Sorter = new InsertionSort();

        timer.start();
        Sorter.sort(numbers);
        timer.end();
        timer.start();

        Collections.sort(expected);
        timer.end();

        Assertions.assertEquals(expected, numbers);
    }

}
