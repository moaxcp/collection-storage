package collections.sort;


import collections.List64;
import java.util.List;

/**
 * implementation for an insertion sort.
 * @author John Mercier <moaxcp at gmail.com>
 */
public class InsertionSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {
    
    private long compares;
    private long swaps;
    private long time;

    /**
     * sorts the list using a insertion sort.
     * @param list 
     */
    @Override
    public void sort(List64<T> list) {
        long start = System.nanoTime();
        for(long i = 1; i < list.size(); i++) {
            T current = list.get(i);
            long j;
            for(j = i - 1; j >= 0 && current.compareTo(list.get(j)) < 0; j--) {
                list.set(j + 1, list.get(j));
                compares++;
                swaps++;
            }
            list.set(j + 1, current);
            compares++;
            swaps++;
        }
        time = System.nanoTime() - start;
    }

    /**
     * returns the benchmark for the sort.
     * @return 
     */
    @Override
    public SortBenchmark getBenchmark() {
        return new SortBenchmark(compares, swaps, time);
    }
    
}
