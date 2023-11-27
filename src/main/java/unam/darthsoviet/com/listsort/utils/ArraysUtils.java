package unam.darthsoviet.com.listsort.utils;

import java.util.ArrayList;
import java.util.List;

public final class ArraysUtils {
    public static  <T> List<T> getSubList(List<T> array, int begin, int size) {
        List<T> subList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            subList.add(array.get(begin + i));
        }
        return subList;
    }
}
