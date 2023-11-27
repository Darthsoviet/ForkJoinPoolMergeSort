package unam.darthsoviet.com.listsort;

import java.util.Comparator;
import java.util.List;

public interface Sorter {
    <T extends Comparable<T>> void sort(List<T> list);
    <T> void sort(List<T> list, Comparator<T> comparator);

}
