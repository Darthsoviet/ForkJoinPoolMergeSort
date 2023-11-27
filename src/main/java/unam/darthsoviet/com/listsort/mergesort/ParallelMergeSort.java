package unam.darthsoviet.com.listsort.mergesort;

import unam.darthsoviet.com.listsort.Sorter;
import unam.darthsoviet.com.listsort.mergesort.forkJoinTasks.ForkJoinTaskComparable;
import unam.darthsoviet.com.listsort.mergesort.forkJoinTasks.ForkJoinTaskComparator;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort implements Sorter {

    private final Sorter Sorter;


    private final int threshold;


    public ParallelMergeSort(int threshold, Sorter thresholdSorter) {
        this.threshold = threshold;
        this.Sorter = thresholdSorter;

    }

    public ParallelMergeSort(int threshold) {
        this(threshold, new MergeSort());

    }

    public ParallelMergeSort() {
        this(100, new MergeSort());
    }


    public <T> void sort(List<T> list, Comparator<T> comparator) {

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinTaskComparator<T> forkTaskComparator = new ForkJoinTaskComparator<>(list, 0, list.size()-1, this.threshold, comparator, Sorter);
        forkJoinPool.invoke(forkTaskComparator);

    }

    public <T extends Comparable<T>> void sort(List<T> list) {

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinTaskComparable<T> forkTask = new ForkJoinTaskComparable<>(list, 0, list.size()-1, this.threshold, Sorter);
        forkJoinPool.invoke(forkTask);

    }


}



