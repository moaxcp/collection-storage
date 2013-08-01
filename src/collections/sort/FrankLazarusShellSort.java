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
public class FrankLazarusShellSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    private long compares;
    private long swaps;
    private long time;

    @Override
    public void sort(List64<T> list) throws FileNotFoundException, IOException {
        list.open("rw");
        long start = System.nanoTime();
        //long shell = list.size() / 4;
        //long gap = 2 * shell + 1;
        for(long shell = list.size() / 4, gap = 2 * shell + 1; gap > 0; shell /= 2, gap = gap == 1 ? 0 : 2 * shell + 1) {
        //while (gap > 0) {
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
            //shell /= 2;
            //gap = gap == 1 ? 0 : 2 * shell + 1;
        }
        time = System.nanoTime() - start;
        list.close();
    }

    @Override
    public SortBenchmark getBenchmark() {
        return new SortBenchmark(compares, swaps, time);
    }
}
