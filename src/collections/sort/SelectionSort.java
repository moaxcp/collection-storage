package collections.sort;

import collections.List64;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * implementation for a selection sort
 * @author John Mercier <moaxcp at gmail.com>
 */
public class SelectionSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    private long compares;
    private long swaps;
    private long time;

    /**
     * sorts a list using selection sort
     * @param list 
     */
    @Override
    public void sort(List64<T> list) throws FileNotFoundException, IOException {
        list.open("rw");
        long start = System.nanoTime();
        for (long i = 0; i < list.size() - 1; i++) {
            long k = i;
            for (long j = i + 1; j < list.size(); j++) {
                if (list.get(j).compareTo(list.get(k)) < 0) {
                    k = j;
                }
                compares++;
            }
            if (k != i) {
                T temp = list.get(i);
                list.set(i, list.get(k));
                list.set(k, temp);
                swaps++;
            }
            compares++;
        }
        time = System.nanoTime() - start;
        list.close();
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
