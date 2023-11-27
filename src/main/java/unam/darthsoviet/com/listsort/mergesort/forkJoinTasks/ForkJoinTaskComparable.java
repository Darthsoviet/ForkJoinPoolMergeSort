package unam.darthsoviet.com.listsort.mergesort.forkJoinTasks;

import unam.darthsoviet.com.listsort.Sorter;
import unam.darthsoviet.com.listsort.mergesort.MergeSort;
import unam.darthsoviet.com.listsort.utils.ArraysUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ForkJoinTaskComparable<T extends Comparable<T>> extends RecursiveAction {
    private final List<T> array;
    private final int left;
    private final int right;
    private final int limit;

    private final Sorter secuentialSorter;

    public ForkJoinTaskComparable(List<T> array, int left, int right, int limit, Sorter secuentialSorter) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.limit = limit;
        this.secuentialSorter = secuentialSorter;
    }

    public ForkJoinTaskComparable(List<T> array, int left, int right, int limit) {
        this(array, left, right, limit, new MergeSort());

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
        ForkJoinTaskComparable<T> leftTask = new ForkJoinTaskComparable<>(array, left, middle, limit, secuentialSorter);
        ForkJoinTaskComparable<T> rightTask = new ForkJoinTaskComparable<>(array, middle+1, right, limit, secuentialSorter);
        leftTask.fork();
        rightTask.fork();
        leftTask.join();
        rightTask.join();
        merge(middle);
    }

    private void sequentialMethod(int middle) {
        ForkJoinTaskComparable<T> leftTask = new ForkJoinTaskComparable<>(array, left, middle, limit, secuentialSorter);
        ForkJoinTaskComparable<T> rightTask = new ForkJoinTaskComparable<>(array, middle, right, limit, secuentialSorter);
        leftTask.compute();
        rightTask.compute();
        merge(middle);
    }


    private <t extends Comparable<T>> void merge(int middle) {
        int leftIndex = 0;
        int leftLimit = middle - left + 1;

        int rightIndex = 0;
        int rightLimit = right - middle;
        int mergeAbsolutIndex = left;


        List<T> leftList = ArraysUtils.getSubList(array, left, leftLimit);
        List<T> rigthList = ArraysUtils.getSubList(array, middle + 1, rightLimit);


        while (leftIndex < leftList.size() && rightIndex < rigthList.size()) {
            T leftValue = leftList.get(leftIndex);
            T rightValue = rigthList.get(rightIndex);

            if (leftValue.compareTo(rightValue) <= 0) {
                array.set(mergeAbsolutIndex, leftValue);
                leftIndex++;
            } else {
                array.set(mergeAbsolutIndex, rightValue);
                rightIndex++;
            }
            mergeAbsolutIndex++;

        }
        while (rightIndex < rigthList.size()) {
            array.set(mergeAbsolutIndex, rigthList.get(rightIndex));
            rightIndex++;
            mergeAbsolutIndex++;
        }
        while (leftIndex < leftList.size()) {
            array.set(mergeAbsolutIndex, leftList.get(leftIndex));
            leftIndex++;
            mergeAbsolutIndex++;
        }
    }

}
