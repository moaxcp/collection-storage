package collections.sort.benchmark;

import collections.sort.SortAlgorithm;
import java.util.Map;
import java.util.TreeMap;

/**
 * AlgorithmBenchmark. Contains the name, algorithm, and results for the Benchmark.
 * @author John Mercier <moaxcp at gmail.com>
 */
public class AlgorithmBenchmark {
    private String name;
    private SortAlgorithm algorithm;
    private Map<Long, AlgorithmSortResult> results;
    
    /**
     * creates an algorithm benchmark.
     * @param name
     * @param algorithm 
     */
    public AlgorithmBenchmark(String name, SortAlgorithm algorithm) {
        this.name = name;
        this.algorithm = algorithm;
        results = new TreeMap<Long, AlgorithmSortResult>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the algorithm
     */
    public SortAlgorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * @return the results
     */
    public Map<Long, AlgorithmSortResult> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void addResult(AlgorithmSortResult result) {
        this.results.put(result.getSize(), result);
    }
}
