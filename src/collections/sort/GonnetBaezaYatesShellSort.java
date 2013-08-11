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
public class GonnetBaezaYatesShellSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    private long compares;
    private long swaps;
    private long time;

    @Override
    public void sort(List64<T> list) {
        long start = System.nanoTime();
        long gap = 5 * list.size() / 11;
        boolean one = false;
        while (!one) {
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
            if(gap == 1) {
                one = true;
            }
            gap = gap * 5 / 11 < 1 ? 1 : gap * 5 / 11;
        }
        time = System.nanoTime() - start;
    }

    @Override
    public SortBenchmark getBenchmark() {
        return new SortBenchmark(compares, swaps, time);
    }
}
