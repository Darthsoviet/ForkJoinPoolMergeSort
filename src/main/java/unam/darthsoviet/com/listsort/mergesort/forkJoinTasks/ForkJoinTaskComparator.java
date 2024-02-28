package unam.darthsoviet.com.listsort.mergesort.forkJoinTasks;


import unam.darthsoviet.com.listsort.utils.ArraysUtils;


import java.util.Comparator;
import java.util.List;
import java.util.concurrent.RecursiveAction;


public class ForkJoinTaskComparator<T> extends RecursiveAction {

    private final List<T> array;
    private final int left;
    private final int right;
    private final int limit;
    private final Comparator<T> comparator;


    public ForkJoinTaskComparator(List<T> array, int left, int right, int limit, Comparator<T> comparator) {
        this.array = array;
        this.left = left;
        this.right = right;
        this.limit = limit;
        this.comparator = comparator;
    }


    @Override
    protected void compute() {
        int middle = (left + right) / 2;

        int size = right - left;
        if (size > limit) {
            forkJoinMethod(middle);
        } else if (left < right) {
            sequentialMethod(middle);

        }

    }


    private void forkJoinMethod(int middle) {
        ForkJoinTaskComparator<T> leftTask = new ForkJoinTaskComparator<>(array, left, middle, limit, this.comparator);
        ForkJoinTaskComparator<T> rightTask = new ForkJoinTaskComparator<>(array, middle + 1, right, limit, this.comparator);
        leftTask.fork();
        rightTask.fork();
        leftTask.join();
        rightTask.join();
        merge(middle);
    }

    private void sequentialMethod(int middle) {
        ForkJoinTaskComparator<T> leftTask = new ForkJoinTaskComparator<>(array, left, middle, limit, this.comparator);
        ForkJoinTaskComparator<T> rightTask = new ForkJoinTaskComparator<>(array, middle + 1, right, limit, this.comparator);
        leftTask.compute();
        rightTask.compute();
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
