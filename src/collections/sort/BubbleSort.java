/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collections.sort;

import collections.List64;
import java.util.List;

/**
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public class BubbleSort<T extends Comparable<? super T>>  implements SortAlgorithm<T> {
    
    private long compares;
    private long swaps;
    private long time;

    @Override
    public void sort(List64<T> list) {
        long start = System.nanoTime();
        boolean swapped = false;
        for(long i = 0; i < list.size(); i++) {
            swapped = false;
            for(long j = list.size() - 1; j >= i + 1; j--) {
                if(list.get(j).compareTo(list.get(j - 1)) < 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j - 1));
                    list.set(j - 1, temp);
                    swapped = true;
                    swaps++;
                }
                compares++;
            }
            if(!swapped) {
                break;
            }
        }
        time = System.nanoTime() - start;
    }

    @Override
    public SortBenchmark getBenchmark() {
        return new SortBenchmark(compares, swaps, time);
    }
    
}
