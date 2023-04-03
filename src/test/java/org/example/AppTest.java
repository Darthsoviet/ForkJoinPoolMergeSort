package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Unit test for simple App.
 */
public class AppTest {
    static List<Integer> numbers;
    private Timer timer = new Timer();

    @BeforeAll
    public static void initTestData() throws IOException, ClassNotFoundException {

        Path path = Paths.get("./src/test/java/org/example/data" + "hugeArray.object");
        if (!Files.exists(path)) {
            numbers = new LinkedList<>();
            IntStream.range(0, 500_000).forEach(numbers::add);
            Collections.shuffle(numbers);
            Files.createFile(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path));
            objectOutputStream.writeObject(numbers);
        } else {
            ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path));
            numbers =(List) objectInputStream.readObject();
        }
    }

    @Test
    public void basicSort() {
        List<Integer> integers = Arrays.asList(3, 2, 1, 0);
        MergeSort<Integer> mergeSort = new MergeSort(Comparator.naturalOrder());

        timer.start();
        List<Integer> sort = mergeSort.sort(integers);
        timer.end();
        timer.start();

        Collections.sort(integers);
        timer.end();

        Assertions.assertEquals(integers, sort);


    }


    @Test
    public void bigSort() {
        MergeSort<Integer> mergeSort = new MergeSort(Comparator.naturalOrder());
        timer.start();
        List<Integer> sort = mergeSort.sort(numbers);
        timer.end();
        timer.start();
        Collections.sort(numbers);
        timer.end();
        Assertions.assertEquals(numbers, sort);
    }

}
