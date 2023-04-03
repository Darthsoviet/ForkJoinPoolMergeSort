package org.example;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MergeSort<T> {

    private Comparator<T> comparator;

    public MergeSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public List<T> sort(List<T> list) {

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(new ForkTask(list, 0, list.size(), list.size() / 16));

    }


    /**
     * [0,1,2,3]
     * length = 4;
     * mid =2;
     * left 0 - 2 -> [0,1,x,x]
     * mid =2;
     * rigth 2 - 4 -> [x,x,2,3]
     */
    private class ForkTask extends RecursiveTask<List<T>> {

        private final List<T> array;
        private final int left;
        private final int right;

        private final int limit;

        public ForkTask(List<T> array, int left, int right, int limit) {
            this.array = array;
            this.left = left;
            this.right = right;
            this.limit = limit;
        }


        @Override
        protected List<T> compute() {
            int listLength = right - left;
            int middle = (listLength / 2) + left;


            if (listLength == 1) {
                T baseCase = array.get(left);

                return Collections.singletonList(baseCase);
            } else if (listLength >= limit) {
                return forkJoinMethod(middle);
            } else {
                return secuentialMethod(middle);
            }

        }

        private List<T> forkJoinMethod(int middle) {
            ForkTask leftTask = new ForkTask(array, left, middle, limit);
            ForkTask rightTask = new ForkTask(array, middle, right, limit);
            leftTask.fork();
            rightTask.fork();
            List<T> leftResult = leftTask.join();
            List<T> rightResult = rightTask.join();
            return merge(leftResult, rightResult);
        }

        private List<T> secuentialMethod(int middle) {
            ForkTask leftTask = new ForkTask(array, left, middle, limit);
            ForkTask rightTask = new ForkTask(array, middle, right, limit);
            List<T> leftResult = leftTask.compute();
            List<T> rightResult = rightTask.compute();
            return merge(leftResult, rightResult);
        }


    }


    /**
     * left 0 - 2 -> [0]
     * rigth 2 - 4 -> [3,4]
     */
    private List<T> merge(List<T> leftList, List<T> rigthList) {
        List<T> mergeList = new ArrayList<>();

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rigthList.size()) {
            T leftValue = leftList.get(leftIndex);
            T rightValue = rigthList.get(rightIndex);

            if (comparator.compare(leftValue, rightValue) <= 0) {
                mergeList.add(leftValue);
                leftIndex++;
            } else {
                mergeList.add(rightValue);
                rightIndex++;
            }

        }
        while (rightIndex < rigthList.size()) {
            mergeList.add(rigthList.get(rightIndex));
            rightIndex++;
        }
        while (leftIndex < leftList.size()) {
            mergeList.add(leftList.get(leftIndex));
            leftIndex++;
        }

        return mergeList;


    }


}



