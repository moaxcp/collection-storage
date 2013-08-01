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
public class CiuraShellSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    private long compares;
    private long swaps;
    private long time;
    private static int[] gaps = {701, 301, 132, 57, 23, 10, 4, 1};

    private void useGaps(List64<T> list) throws FileNotFoundException, IOException {
        for(long gap : gaps) {
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
    }

    @Override
    public void sort(List64<T> list) throws FileNotFoundException, IOException {
        list.open("rw");
        long start = System.nanoTime();
        useGaps(list);
        time = System.nanoTime() - start;
        list.close();
    }

    @Override
    public SortBenchmark getBenchmark() {
        return new SortBenchmark(compares, swaps, time);
    }
}
