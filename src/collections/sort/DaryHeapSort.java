/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collections.sort;

import collections.Collections;
import collections.List64;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public class DaryHeapSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    private long swaps;
    private long compares;
    private long time;
    private long dary;

    public DaryHeapSort(int dary) {
        this.dary = dary;
    }

    private void swap(List64<T> list, long i, long j) {
        //System.out.println("swap: " + i + "(" + list.get(i) + ") " + j + "(" + list.get(j) + ") " + Collections.toString(list));
        T temp = list.get(j);
        list.set(j, list.get(i));
        list.set(i, temp);
        swaps++;
    }

    private void siftDown(List64<T> list, long start, long end) {
        //System.out.println("siftDown(" + start + ", " + end + ") " + Collections.toString(list));
        long root = start;
        while (root * dary + 1 <= end) {
            long swap = root;
            for (int i = 1; i <= dary; i++) {
                long child = root * dary + i;
                compares++;
                if (child <= end && list.get(swap).compareTo(list.get(child)) < 0) {
                    swap = child;
                }
            }
            if (swap != root) {
                swap(list, root, swap);
                root = swap;
            } else {
                return;
            }
        }
    }

    private void heapify(List64<T> list) {
        long start = (list.size() - dary) / 2;
        start = start < 0 ? 0 : start;
        //System.out.println(list.size() - dary + " " + start);
        while (start >= 0) {
            siftDown(list, start, list.size() - 1);
            start -= 1;
        }
        //System.out.println("heapified " + Collections.toString(list));
    }

    @Override
    public void sort(List64<T> list) {
        long start = System.nanoTime();
        //fix the heap property
        heapify(list);
        long end = list.size() - 1;
        while (end > 0) {
            //add the root to the sorted list
            swap(list, end, 0);
            end -= 1;
            //restore heap property of the root node
            siftDown(list, 0, end);
        }
        time = System.nanoTime() - start;
    }

    @Override
    public SortBenchmark getBenchmark() {
        return new SortBenchmark(compares, swaps, time);
    }

}
