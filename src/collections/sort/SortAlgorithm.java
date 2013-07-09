package collections.sort;


import collections.List64;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * interface for a SortAlgorithm.
 * @author John Mercier <moaxcp at gmail.com>
 */
public interface SortAlgorithm<T extends Comparable<? super T>> {
    void sort(List64<T> list) throws FileNotFoundException, IOException;
    SortBenchmark getBenchmark();
}
