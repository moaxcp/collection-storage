package collections.sort;

/**
 * A benchmark for an individual sort. contains operations and time to complete sort.
 * @author John Mercier <moaxcp at gmail.com>
 */
public class SortBenchmark {
    private long compares;
    private long swaps;
    private long time;
    
    public SortBenchmark(long compares, long swaps, long time) {
        this.compares = compares;
        this.swaps = swaps;
        this.time = time;
    }

    /**
     * @return the operations
     */
    public long getCompares() {
        return compares;
    }

    /**
     * @param operations the operations to set
     */
    public void setCompares(long compares) {
        this.compares = compares;
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }
    
    public long getSwaps() {
        return swaps;
    }
    
    public void setSwaps(long swaps) {
        this.swaps = swaps;
    }
}
