package unam.darthsoviet.com.util;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.IntStream;

public final class ArraySaver {

    private static ConcurrentMap<Path,List<Integer>> cache = new ConcurrentHashMap<>();
    public static List<Integer> getOrCreateArrayFromFile(String file) throws IOException, ClassNotFoundException {
        Path path = Paths.get("." + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "data" + File.separator + file);
        if (!Files.exists(path)) {
            System.out.println("creating array...");
            ArrayList<Integer> arrayList = generateRandomIntegerArray(20_000_000);
            saveArray(path, arrayList);
            System.out.println("array created.");
            cache.put(path, arrayList);
            System.out.println("array cached.");


        }

        return getFromPathOrCache(path);

    }

    private static List<Integer> getFromPathOrCache(Path path) throws IOException, ClassNotFoundException {

        if(cache.get(path) == null){
            ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path));
            List<Integer> retrieved = (List) objectInputStream.readObject();
            cache.putIfAbsent(path,retrieved);

        }
        System.out.println("array retrieved.");
        return new ArrayList<>(cache.get(path));
    }

    public static void saveArray(Path path, List<Integer> arrayList) throws IOException {

        Files.createFile(path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path));
        objectOutputStream.writeObject(arrayList);
    }

    public static ArrayList<Integer> generateRandomIntegerArray(int size) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        IntStream.range(0, size).forEach(arrayList::add);
        Collections.shuffle(arrayList);
        return arrayList;
    }
}
