package unam.darthsoviet.com.listsort.insertionsort;

import unam.darthsoviet.com.listsort.Sorter;

import java.util.Comparator;
import java.util.List;

public class InsertionSort implements Sorter {

    @Override
    public <T extends Comparable<T>> void sort(List<T> list) {
        for (int leftIndex = 0; leftIndex < list.size(); leftIndex++) {
            for (int rightIndex = leftIndex + 1; rightIndex < list.size(); rightIndex++) {

                T right = list.get(rightIndex);
                T left = list.get(leftIndex);
                if (left.compareTo(right) > 0) {
                    list.set(leftIndex, right);
                    list.set(rightIndex, left);
                }
            }
        }
    }

    @Override
    public <T> void sort(List<T> list, Comparator<T> comparator) {
        for (int leftIndex = 0; leftIndex < list.size(); leftIndex++) {
            for (int rightIndex = leftIndex + 1; rightIndex < list.size(); rightIndex++) {

                T right = list.get(rightIndex);
                T left = list.get(leftIndex);
                if (comparator.compare(left, right) > 0) {
                    list.set(leftIndex, right);
                    list.set(rightIndex, left);
                }
            }
        }
    }

}
