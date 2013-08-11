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
public class ShellSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    private long compares;
    private long swaps;
    private long time;

    @Override
    public void sort(List64<T> list) {
        long start = System.nanoTime();
        for(long gap = list.size() / 2; gap > 0; gap /= 2) {
            for (long i = gap; i < list.size(); i++) {
                T temp = list.get(i);
                long j = i;
                while(j >= gap && list.get(j - gap).compareTo(temp) > 0) {
                    list.set(j, list.get(j - gap));
                    j -= gap;
                    compares++;
                    swaps++;
                }
                list.set(j, temp);
                compares++;
                swaps++;
            }
        }
        time = System.nanoTime() - start;
    }

    @Override
    public SortBenchmark getBenchmark() {
        return new SortBenchmark(compares, swaps, time);
    }
}
