package unam.darthsoviet.com.listsort.mergesort;

import unam.darthsoviet.com.listsort.Sorter;
import unam.darthsoviet.com.listsort.utils.ArraysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MergeSort implements Sorter {


    @Override
    public <T> void sort(List<T> list, Comparator<T> comparator) {
        recursiveSplit(0, list.size()-1, list, comparator);
    }

    @Override
    public <T extends Comparable<T>> void sort(List<T> list) {
        recursiveSplit(0, list.size()-1, list);


    }

    private <T> void recursiveSplit(int left, int right, List<T> array, Comparator<T> comparator) {
        int listLength = right - left;
        int middle = (listLength / 2) + left;

        if (left<right) {

            recursiveSplit(left, middle, array, comparator);
            recursiveSplit(middle, right, array, comparator);

            merge(left, middle,right,array, comparator);

        }


    }

    private <T extends Comparable<T>> void recursiveSplit(int left, int right, List<T> array) {
        int middle = (left + right)/2;

        if (left<right) {

            recursiveSplit(left, middle, array);
            recursiveSplit(middle+1, right, array);

            merge(left, middle,right,array);

        }

    }


    private <T> void merge(int left, int middle,int right,List<T> array, Comparator<T> comparator) {
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

    private <T extends Comparable<T>> void merge(int left, int middle,int right,List<T> array) {
        int leftIndex = 0;
        int leftLimit = middle - left + 1;

        int rightIndex = 0;
        int rightLimit = right - middle;
        int mergeAbsolutIndex = left;


        List<T> leftList = ArraysUtils.getSubList(array, left, leftLimit);
        List<T> rigthList = ArraysUtils.getSubList(array, middle+1 , rightLimit);


        while (leftIndex < leftList.size() && rightIndex < rigthList.size()) {
            T leftValue = leftList.get(leftIndex);
            T rightValue = rigthList.get(rightIndex);

            if (leftValue.compareTo(rightValue) <= 0) {
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
