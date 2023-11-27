package unam.darthsoviet.com.listsort.mergesort.forkJoinTasks;

import unam.darthsoviet.com.listsort.Sorter;
import unam.darthsoviet.com.listsort.mergesort.MergeSort;
import unam.darthsoviet.com.listsort.utils.ArraysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.RecursiveAction;


public class ForkJoinTaskComparator<T> extends RecursiveAction {

    private final List<T> array;
    private final int left;
    private final int right;
    private final int limit;
    private final Comparator<T> comparator;

    private final Sorter secuentialSorter;

    public ForkJoinTaskComparator(List<T> array, int left, int right, int limit, Comparator<T> comparator) {
        this(array,left,right,limit,comparator,new MergeSort());

    }

    public ForkJoinTaskComparator(List<T> array, int left, int right, int limit, Comparator<T> comparator, Sorter secuentialSorter) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.limit = limit;
        this.comparator = comparator;
        this.secuentialSorter = secuentialSorter;
    }


    @Override
    protected void compute() {
        int middle = (left + right) / 2;

        if (left<right) {
            forkJoinMethod(middle);
            merge(middle);

        }

    }


    private void forkJoinMethod(int middle) {
        ForkJoinTaskComparator<T> leftTask = new ForkJoinTaskComparator<>(array, left, middle, limit, this.comparator, secuentialSorter);
        ForkJoinTaskComparator<T> rightTask = new ForkJoinTaskComparator<>(array, middle+1, right, limit, this.comparator, secuentialSorter);
        leftTask.fork();
        rightTask.fork();
        leftTask.join();
        rightTask.join();
        merge(middle);
    }


    private <t> void merge(int middle) {
        int leftIndex = 0;
        int leftLimit = middle - left + 1;
        int mergeAbsolutIndex = left;

        int rightIndex = 0;
        int rightLimit = right - middle;


        List<T> leftList = ArraysUtils.getSubList(array, left, leftLimit);
        List<T> rigthList = ArraysUtils.getSubList(array, middle + 1, rightLimit);


        while (leftIndex < leftList.size() && rightIndex < rigthList.size()) {
            T leftValue = leftList.get(leftIndex);
            T rightValue = rigthList.get(rightIndex);

            if (comparator.compare(leftValue, rightValue) <= 0) {
                array.set(mergeAbsolutIndex,leftValue);
                leftIndex++;
            } else {
                array.set(mergeAbsolutIndex,rightValue);
                rightIndex++;
            }
            mergeAbsolutIndex++;

        }
        while (rightIndex < rigthList.size()) {
            array.set(mergeAbsolutIndex,rigthList.get(rightIndex));
            rightIndex++;
            mergeAbsolutIndex++;
        }
        while (leftIndex < leftList.size()) {
            array.set(mergeAbsolutIndex,leftList.get(leftIndex));
            leftIndex++;
            mergeAbsolutIndex++;
        }

    }

}
