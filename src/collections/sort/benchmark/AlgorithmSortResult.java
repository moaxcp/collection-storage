package collections.sort.benchmark;

import collections.sort.SortBenchmark;
import java.util.ArrayList;
import java.util.List;

/**
 * Sort results for on set of tests at the specified size.
 * @author John Mercier <moaxcp at gmail.com>
 */
public class AlgorithmSortResult {
    private long size;
    private int tests;
    private List<SortBenchmark> random;
    private List<SortBenchmark> sorted;
    private List<SortBenchmark> reversed;
    private List<SortBenchmark> randomFewUnique;
    private List<SortBenchmark> sortedFewUnique;
    private List<SortBenchmark> reverseFewUnique;
    
    /**
     * Creates an AlgorithmSortResult
     */
    public AlgorithmSortResult() {
        random = new ArrayList<SortBenchmark>();
        sorted = new ArrayList<SortBenchmark>();
        reversed = new ArrayList<SortBenchmark>();
        randomFewUnique = new ArrayList<SortBenchmark>();
        sortedFewUnique = new ArrayList<SortBenchmark>();
        reverseFewUnique = new ArrayList<SortBenchmark>();
    }

    /**
     * the size for these tests
     * @return
     */
    public long getSize() {
        return size;
    }

    /**
     * sets the size for these tests
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * the number of tests performed.
     * @return
     */
    public int getTests() {
        return tests;
    }

    /**
     * set the number of tests performed.
     * @param tests
     */
    public void setTests(int tests) {
        this.tests = tests;
    }

    /**
     * returns the average operations for the random list tests
     * @return
     */
    public long getAverageComparesRandom() {
        long sum = 0;
        for(SortBenchmark i : random) {
            sum += i.getCompares();
        }
        return sum / random.size();
    }
    
    /**
     * returns the standard deviation for the random list tests
     * @return
     */
    public double getStandDevComparesRandom() {
        long average = getAverageComparesRandom();
        long devSum = 0;
        for(SortBenchmark i : random) {
            devSum += (i.getCompares() - average) * (i.getCompares() - average);
        }
        return Math.sqrt(devSum / (double) random.size());
    }

    /**
     * returns the average operations for the random list tests
     * @return
     */
    public long getAverageSwapsRandom() {
        long sum = 0;
        for(SortBenchmark i : random) {
            sum += i.getSwaps();
        }
        return sum / random.size();
    }
    
    /**
     * returns the standard deviation for the random list tests
     * @return
     */
    public double getStandDevSwapsRandom() {
        long average = getAverageSwapsRandom();
        long devSum = 0;
        for(SortBenchmark i : random) {
            devSum += (i.getSwaps() - average) * (i.getSwaps() - average);
        }
        return Math.sqrt(devSum / (double) random.size());
    }

    /**
     * returns the average time for the random list tests
     * @return
     */
    public long getAverageRandomTime() {
        long sum = 0;
        for(SortBenchmark i : random) {
            sum += i.getTime();
        }
        return sum / random.size();
    }
    
    /**
     * returns the standard deviation for the random list test times
     * @return
     */
    public long getStandDevRandomTime() {
        long average = getAverageRandomTime();
        long devSum = 0;
        for(SortBenchmark i : random) {
            devSum += (i.getTime() - average) * (i.getTime() - average);
        }
        return (long) Math.sqrt(devSum / (double) random.size());
    }

    /**
     * add a benchmark for a random list test
     * @param benchmark
     */
    public void addRandom(SortBenchmark benchmark) {
        random.add(benchmark);
    }

    /**
     * get the average operations for the sorted list tests
     * @return
     */
    public long getAverageComparesSorted() {
        long sum = 0;
        for(SortBenchmark i : sorted) {
            sum += i.getCompares();
        }
        return sum / sorted.size();
    }
    
    /**
     * get the standard deviation for the sorted list tests
     * @return
     */
    public double getStandDevComparesSorted() {
        long average = getAverageComparesSorted();
        long devSum = 0;
        for(SortBenchmark i : sorted) {
            devSum += (i.getCompares() - average) * (i.getCompares() - average);
        }
        return Math.sqrt(devSum / (double) sorted.size());
    }

    /**
     * get the average operations for the sorted list tests
     * @return
     */
    public long getAverageSwapsSorted() {
        long sum = 0;
        for(SortBenchmark i : sorted) {
            sum += i.getSwaps();
        }
        return sum / sorted.size();
    }
    
    /**
     * get the standard deviation for the sorted list tests
     * @return
     */
    public double getStandDevSwapsSorted() {
        long average = getAverageSwapsSorted();
        long devSum = 0;
        for(SortBenchmark i : sorted) {
            devSum += (i.getSwaps() - average) * (i.getSwaps() - average);
        }
        return Math.sqrt(devSum / (double) sorted.size());
    }

    /**
     * get the average time for the sorted list tests
     * @return
     */
    public long getAverageSortedTime() {
        long sum = 0;
        for(SortBenchmark i : sorted) {
            sum += i.getTime();
        }
        return sum / sorted.size();
    }
    
    /**
     * get the standard deviation for the sorted test times.
     * @return
     */
    public long getStandDevSortedTime() {
        long average = getAverageSortedTime();
        long devSum = 0;
        for(SortBenchmark i : sorted) {
            devSum += (i.getTime() - average) * (i.getTime() - average);
        }
        return (long) Math.sqrt(devSum / (double) sorted.size());
    }

    /**
     * add benchmark for a sorted list test
     * @param benchmark
     */
    public void addSorted(SortBenchmark benchmark) {
        sorted.add(benchmark);
    }

    /**
     * get the average operations for the reverse list tests
     * @return
     */
    public long getAverageComparesReverse() {
        long sum = 0;
        for(SortBenchmark i : reversed) {
            sum += i.getCompares();
        }
        return sum / reversed.size();
    }
    
    /**
     * get the standard deviation for the reverse list tests
     * @return
     */
    public double getStandDevComparesReverse() {
        long average = getAverageComparesReverse();
        long devSum = 0;
        for(SortBenchmark i : reversed) {
            devSum += (i.getCompares() - average) * (i.getCompares() - average);
        }
        return Math.sqrt(devSum / (double) reversed.size());
    }

    /**
     * get the average operations for the reverse list tests
     * @return
     */
    public long getAverageSwapsReverse() {
        long sum = 0;
        for(SortBenchmark i : reversed) {
            sum += i.getSwaps();
        }
        return sum / reversed.size();
    }
    
    /**
     * get the standard deviation for the reverse list tests
     * @return
     */
    public double getStandDevSwapsReverse() {
        long average = getAverageSwapsReverse();
        long devSum = 0;
        for(SortBenchmark i : reversed) {
            devSum += (i.getSwaps() - average) * (i.getSwaps() - average);
        }
        return Math.sqrt(devSum / (double) reversed.size());
    }

    /**
     * get the average time for the reverse list tests
     * @return
     */
    public long getAverageReverseTime() {
        long sum = 0;
        for(SortBenchmark i : reversed) {
            sum += i.getTime();
        }
        return sum / reversed.size();
    }
    
    /**
     * get the standard deviation for the reverse list times
     * @return
     */
    public long getStandDevReverseTime() {
        long average = getAverageReverseTime();
        long devSum = 0;
        for(SortBenchmark i : reversed) {
            devSum += (i.getTime() - average) * (i.getTime() - average);
        }
        return (long) Math.sqrt(devSum / (double) reversed.size());
    }

    /**
     * add a benchmark for a reverse list test
     * @param benchmark
     */
    public void addReverse(SortBenchmark benchmark) {
        reversed.add(benchmark);
    }

    /**
     * return the average operations for the random few unique tests
     * @return
     */
    public long getAverageComparesRandomFewUnique() {
        long sum = 0;
        for(SortBenchmark i : randomFewUnique) {
            sum += i.getCompares();
        }
        return sum / randomFewUnique.size();
    }
    
    /**
     * return the standard deviation for the random few unique tests
     * @return
     */
    public double getStandDevComparesRandomFewUnique() {
        long average = getAverageComparesRandomFewUnique();
        long devSum = 0;
        for(SortBenchmark i : randomFewUnique) {
            devSum += (i.getCompares() - average) * (i.getCompares() - average);
        }
        return Math.sqrt(devSum / (double) randomFewUnique.size());
    }

    /**
     * return the average operations for the random few unique tests
     * @return
     */
    public long getAverageSwapsRandomFewUnique() {
        long sum = 0;
        for(SortBenchmark i : randomFewUnique) {
            sum += i.getSwaps();
        }
        return sum / randomFewUnique.size();
    }
    
    /**
     * return the standard deviation for the random few unique tests
     * @return
     */
    public double getStandDevSwapsRandomFewUnique() {
        long average = getAverageSwapsRandomFewUnique();
        long devSum = 0;
        for(SortBenchmark i : randomFewUnique) {
            devSum += (i.getSwaps() - average) * (i.getSwaps() - average);
        }
        return Math.sqrt(devSum / (double) randomFewUnique.size());
    }

    /**
     * return the average time for the random few unique tests
     * @return
     */
    public long getAverageRandomFewUniqueTime() {
        long sum = 0;
        for(SortBenchmark i : randomFewUnique) {
            sum += i.getTime();
        }
        return sum / randomFewUnique.size();
    }
    
    /**
     * return the standard deviation for the random few unique times
     * @return
     */
    public long getStandDevRandomFewUniqueTime() {
        long average = getAverageRandomFewUniqueTime();
        long devSum = 0;
        for(SortBenchmark i : randomFewUnique) {
            devSum += (i.getTime() - average) * (i.getTime() - average);
        }
        return (long) Math.sqrt(devSum / (double) randomFewUnique.size());
    }

    /**
     * add a benchmark for a random few unique test
     * @param benchmark
     */
    public void addRandomFewUnique(SortBenchmark benchmark) {
        randomFewUnique.add(benchmark);
    }

    /**
     * get the average operations for the sorted few unique tests
     * @return
     */
    public long getAverageComparesSortedFewUnique() {
        long sum = 0;
        for(SortBenchmark i : sortedFewUnique) {
            sum += i.getCompares();
        }
        return sum / sortedFewUnique.size();
    }
    
    /**
     * get the standard deviation for the sorted few unique operations
     * @return
     */
    public double getStandDevComparesSortedFewUnique() {
        long average = getAverageComparesSortedFewUnique();
        long devSum = 0;
        for(SortBenchmark i : sortedFewUnique) {
            devSum += (i.getCompares() - average) * (i.getCompares() - average);
        }
        return Math.sqrt(devSum / (double) sortedFewUnique.size());
    }

    /**
     * get the average operations for the sorted few unique tests
     * @return
     */
    public long getAverageSwapsSortedFewUnique() {
        long sum = 0;
        for(SortBenchmark i : sortedFewUnique) {
            sum += i.getSwaps();
        }
        return sum / sortedFewUnique.size();
    }
    
    /**
     * get the standard deviation for the sorted few unique operations
     * @return
     */
    public double getStandDevSwapsSortedFewUnique() {
        long average = getAverageSwapsSortedFewUnique();
        long devSum = 0;
        for(SortBenchmark i : sortedFewUnique) {
            devSum += (i.getSwaps() - average) * (i.getSwaps() - average);
        }
        return Math.sqrt(devSum / (double) sortedFewUnique.size());
    }

    /**
     * get the average time for the sorted few unique tests
     * @return
     */
    public long getAverageSortedFewUniqueTime() {
        long sum = 0;
        for(SortBenchmark i : sortedFewUnique) {
            sum += i.getTime();
        }
        return sum / sortedFewUnique.size();
    }
    
    /**
     * get the standard deviation for the sorted few unique times
     * @return
     */
    public long getStandDevSortedFewUniqueTime() {
        long average = getAverageSortedFewUniqueTime();
        long devSum = 0;
        for(SortBenchmark i : sortedFewUnique) {
            devSum += (i.getTime() - average) * (i.getTime() - average);
        }
        return (long) Math.sqrt(devSum / (double) sortedFewUnique.size());
    }

    /**
     * add a benchmark for a sorted few unique test
     * @param benchmark
     */
    public void addSortedFewUnique(SortBenchmark benchmark) {
        sortedFewUnique.add(benchmark);
    }

    /**
     * get the average operations for the reverse few unique tests
     * @return
     */
    public long getAverageComparesReverseFewUnique() {
        long sum = 0;
        for(SortBenchmark i : reverseFewUnique) {
            sum += i.getCompares();
        }
        return sum / reverseFewUnique.size();
    }
    
    /**
     * get the standard deviation for the reverse few unique operations
     * @return
     */
    public double getStandDevComparesReverseFewUnique() {
        long average = getAverageComparesReverseFewUnique();
        long devSum = 0;
        for(SortBenchmark i : reverseFewUnique) {
            devSum += (i.getCompares() - average) * (i.getCompares() - average);
        }
        return Math.sqrt(devSum / (double) reverseFewUnique.size());
    }

    /**
     * get the average operations for the reverse few unique tests
     * @return
     */
    public long getAverageSwapsReverseFewUnique() {
        long sum = 0;
        for(SortBenchmark i : reverseFewUnique) {
            sum += i.getSwaps();
        }
        return sum / reverseFewUnique.size();
    }
    
    /**
     * get the standard deviation for the reverse few unique operations
     * @return
     */
    public double getStandDevSwapsReverseFewUnique() {
        long average = getAverageSwapsReverseFewUnique();
        long devSum = 0;
        for(SortBenchmark i : reverseFewUnique) {
            devSum += (i.getSwaps() - average) * (i.getSwaps() - average);
        }
        return Math.sqrt(devSum / (double) reverseFewUnique.size());
    }

    /**
     * get the average time for the reverse few unique tests
     * @return
     */
    public long getAverageReverseFewUniqueTime() {
        long sum = 0;
        for(SortBenchmark i : reverseFewUnique) {
            sum += i.getTime();
        }
        return sum / reverseFewUnique.size();
    }
    
    /**
     *get the standard deviation for the reverse few unique test times
     * @return
     */
    public long getStandDevReverseFewUniqueTime() {
        long average = getAverageReverseFewUniqueTime();
        long devSum = 0;
        for(SortBenchmark i : reverseFewUnique) {
            devSum += (i.getTime() - average) * (i.getTime() - average);
        }
        return (long) Math.sqrt(devSum / (double) reverseFewUnique.size());
    }

    /**
     * add a benchmark for a reverse few unique test
     * @param benchmark
     */
    public void addReverseFewUnique(SortBenchmark benchmark) {
        reverseFewUnique.add(benchmark);
    }
}
