/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collections.sort;

import collections.List64;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public class HeapSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {
    
    private long swaps;
    private long compares;
    private long time;
    
    private void swap(List64<T> list, long i, long j) {
        T temp = list.get(j);
        list.set(j, list.get(i));
        list.set(i, temp);
        swaps++;
    }
    
    private void siftDown(List64<T> list, long start, long end) {
        long root = start;
        
        while(root *2 + 1 <= end) {
            long child = root * 2 + 1;
            long swap = root;
            if(list.get(swap).compareTo(list.get(child)) < 0) {
                swap = child;
            }
            if(child + 1 <= end && list.get(swap).compareTo(list.get(child + 1)) < 0) {
                swap = child + 1;
            }
            compares++;
            if(swap != root) {
                swap(list, root, swap);
                root = swap;
            } else {
                return;
            }
        }
    }
    
    private void heapify(List64<T> list) {
        long start = (list.size() - 2) / 2;
        while(start >= 0) {
            siftDown(list, start, list.size() - 1);
            start -= 1;
        }
    }

    @Override
    public void sort(List64<T> list) {
        long start = System.nanoTime();
        //add each element to the heap
        heapify(list);
        long end = list.size() - 1;
        while(end > 0) {
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
