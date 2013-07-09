package collections.sort.benchmark;

import collections.List64;
import collections.ListFactory;
import collections.sort.BubbleSort;
import collections.sort.HeapSort;
import collections.sort.InsertionSort;
import collections.sort.SelectionSort;
import collections.sort.ShellSort;
import collections.sort.SortAlgorithm;
import collections.sort.SortBenchmark;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Benchmark. Runs benchmarks on various sort algorithms.
 * @author John Mercier <moaxcp at gmail.com>
 */
public class Benchmark {

    private List<AlgorithmBenchmark> benchmarks;
    private int tests = 5; //number of sorts to be averaged
    private File listFile;
    private File copyFile;

    public Benchmark() {
        benchmarks = new ArrayList<AlgorithmBenchmark>();
        listFile = new File("/extra/benchmarkList");
        copyFile = new File("/extra/benchmarkCopy");
        listFile.delete();
        copyFile.delete();
    }

    /**
     * sorts a list using the supplied algorithm
     * @param algorithm
     * @param list
     * @return the benchmark for the sort
     */
    public SortBenchmark runSort(SortAlgorithm algorithm, List64<Long> list) throws FileNotFoundException, IOException {
        algorithm.sort(list);
        return algorithm.getBenchmark();
    }

    /**
     * run all tests for algorithm using specified list size.
     * The test are defined by the different list types and are supplied by
     * ListFactory
     * random, sorted, reverse, randomFew, sortedFew, reverseFew
     * @param algorithm
     * @param size
     * @return 
     */
    public AlgorithmSortResult runTests(SortAlgorithm algorithm, int size) throws FileNotFoundException, IOException {
        AlgorithmSortResult results = new AlgorithmSortResult();
        results.setSize(size);
        results.setTests(tests);

        //random tests for this algorithm
        System.out.print("Random tests");
        List64<Long> list = ListFactory.getRandomList(listFile, size, false);
        for (int i = 0; i < tests; i++) {
            List64<Long> copy = list.copy(copyFile); //uses the same list for each sort
            SortBenchmark sort = runSort(algorithm, copy);
            System.out.print(".");
            System.out.flush();
            if (!ListFactory.isOrdered(copy)) {
                throw new IllegalStateException("List is out of order for " + algorithm + " " + copy);
            }
            results.addRandom(sort);
        }
        System.out.println();
        listFile.delete();
        copyFile.delete();

        //sorted tests
        System.out.print("Sorted tests");
        list = ListFactory.getSortedList(listFile, size, false);
        for (int i = 0; i < tests; i++) {
            List64<Long> copy = list.copy(copyFile);
            SortBenchmark sort = runSort(algorithm, copy);
            System.out.print(".");
            System.out.flush();
            if (!ListFactory.isOrdered(copy)) {
                throw new IllegalStateException("List is out of order for " + algorithm + " " + copy);
            }
            results.addSorted(sort);
        }
        System.out.println();
        listFile.delete();
        copyFile.delete();

        //reverse tests
        System.out.print("Reverse tests");
        list = ListFactory.getReversedList(listFile, size, false);
        for (int i = 0; i < tests; i++) {
            List64<Long> copy = list.copy(copyFile);
            SortBenchmark sort = runSort(algorithm, copy);
            System.out.print(".");
            System.out.flush();
            if (!ListFactory.isOrdered(copy)) {
                throw new IllegalStateException("List is out of order for " + algorithm + " " + copy);
            }
            results.addReverse(sort);
        }
        System.out.println();
        listFile.delete();
        copyFile.delete();

        //random unique tests
        System.out.print("Random few unique tests");
        list = ListFactory.getRandomList(listFile, size, true);
        for (int i = 0; i < tests; i++) {
            List64<Long> copy = list.copy(copyFile);
            SortBenchmark sort = runSort(algorithm, copy);
            System.out.print(".");
            System.out.flush();
            if (!ListFactory.isOrdered(copy)) {
                throw new IllegalStateException("List is out of order for " + algorithm + " " + copy);
            }
            results.addRandomFewUnique(sort);
        }
        System.out.println();
        listFile.delete();
        copyFile.delete();

        //sorted unique tests
        System.out.print("Sorted few unique tests");
        list = ListFactory.getSortedList(listFile, size, true);
        for (int i = 0; i < tests; i++) {
            List64<Long> copy = list.copy(copyFile);
            SortBenchmark sort = runSort(algorithm, copy);
            System.out.print(".");
            System.out.flush();
            if (!ListFactory.isOrdered(copy)) {
                throw new IllegalStateException("List is out of order for " + algorithm + " " + copy);
            }
            results.addSortedFewUnique(sort);
        }
        System.out.println();
        listFile.delete();
        copyFile.delete();

        //reverse unique
        System.out.print("Reverse few unique tests");
        list = ListFactory.getReversedList(listFile, size, true);
        for (int i = 0; i < tests; i++) {
            List64<Long> copy = list.copy(copyFile);
            SortBenchmark sort = runSort(algorithm, copy);
            System.out.print(".");
            System.out.flush();
            if (!ListFactory.isOrdered(copy)) {
                throw new IllegalStateException("List is out of order for " + algorithm + " " + copy);
            }
            results.addReverseFewUnique(sort);
        }
        System.out.println();
        listFile.delete();
        copyFile.delete();

        return results;
    }

    /**
     * run all benchmarks
     */
    public void runBenchmarks() throws FileNotFoundException, IOException {
        List<AlgorithmBenchmark> smallBenchmarks = new ArrayList<AlgorithmBenchmark>();
        //smallBenchmarks.add(new AlgorithmBenchmark("Insertion Sort", new InsertionSort()));
        //smallBenchmarks.add(new AlgorithmBenchmark("Selection Sort", new SelectionSort()));
        //smallBenchmarks.add(new AlgorithmBenchmark("Bubble Sort", new BubbleSort()));
        for(AlgorithmBenchmark a : smallBenchmarks) {
            benchmarks.add(a);
        }
        
        List<AlgorithmBenchmark> largeBenchmarks = new ArrayList<AlgorithmBenchmark>();
        //largeBenchmarks.add(new AlgorithmBenchmark("Shell Sort", new ShellSort()));
        largeBenchmarks.add(new AlgorithmBenchmark("Heap Sort", new HeapSort()));
        for(AlgorithmBenchmark a : largeBenchmarks) {
            benchmarks.add(a);
        }
        
        List<Integer> smallSizes = new ArrayList<Integer>();
        smallSizes.add(10);
        smallSizes.add(20);
        smallSizes.add(30);
        smallSizes.add(40);
        smallSizes.add(50);
        smallSizes.add(60);
        smallSizes.add(70);
        smallSizes.add(80);
        smallSizes.add(90);
        smallSizes.add(100);
        smallSizes.add(110);
        smallSizes.add(120);
        smallSizes.add(130);
        smallSizes.add(140);
        smallSizes.add(150);
//        smallSizes.add(160);
//        smallSizes.add(170);
//        smallSizes.add(180);
//        smallSizes.add(190);
//        smallSizes.add(200);
//        smallSizes.add(210);
//        smallSizes.add(220);
//        smallSizes.add(230);
//        smallSizes.add(240);
//        smallSizes.add(250);
        
        List<Integer> largeSizes = new ArrayList<Integer>();
//        largeSizes.add(100000);
//        largeSizes.add(200000);
//        largeSizes.add(300000);
//        largeSizes.add(400000);
//        largeSizes.add(500000);
//        largeSizes.add(600000);
//        largeSizes.add(700000);
//        largeSizes.add(800000);
//        largeSizes.add(900000);
//        largeSizes.add(1000000);
//        largeSizes.add(2000000);
//        largeSizes.add(3000000);
//        largeSizes.add(4000000);
//        largeSizes.add(5000000);
//        largeSizes.add(6000000);
//        largeSizes.add(7000000);
//        largeSizes.add(8000000);
//        largeSizes.add(9000000);
//        largeSizes.add(10000000);

        for (AlgorithmBenchmark benchmark : smallBenchmarks) {
            for(int i : smallSizes) {
                System.out.println("Running " + benchmark.getName() + " " + tests + " times with size " + i);
                SortAlgorithm algorithm = benchmark.getAlgorithm();
                AlgorithmSortResult result = runTests(algorithm, i);
                benchmark.addResult(result);
            }
        }

        for (AlgorithmBenchmark benchmark : largeBenchmarks) {
            for(int i : smallSizes) {
                System.out.println("Running " + benchmark.getName() + " " + tests + " times with size " + i);
                SortAlgorithm algorithm = benchmark.getAlgorithm();
                AlgorithmSortResult result = runTests(algorithm, i);
                benchmark.addResult(result);
            }
            for(int i : largeSizes) {
                System.out.println("Running " + benchmark.getName() + " " + tests + " times with size " + i);
                SortAlgorithm algorithm = benchmark.getAlgorithm();
                AlgorithmSortResult result = runTests(algorithm, i);
                benchmark.addResult(result);
            }
        }
    }
    
    /**
     * display results of the benchmarks
     */
    public void displayResults() {
        System.out.println("*************************");
        for(AlgorithmBenchmark benchmark : benchmarks) {
            System.out.println(benchmark.getName() + " average compares");
            System.out.println("n, random, sorted, reverse, random few, sorted few, reverse few");
            for(long i : benchmark.getResults().keySet()) {
                AlgorithmSortResult result = benchmark.getResults().get(i);
                System.out.println(i + ", " + result.getAverageComparesRandom() + ", " + result.getAverageComparesSorted() + ", " + result.getAverageComparesReverse() + ", " + result.getAverageComparesRandomFewUnique() + ", " + result.getAverageComparesSortedFewUnique() + ", " + result.getAverageComparesReverseFewUnique());
            }
            System.out.println(benchmark.getName() + " average swaps");
            System.out.println("n, random, sorted, reverse, random few, sorted few, reverse few");
            for(long i : benchmark.getResults().keySet()) {
                AlgorithmSortResult result = benchmark.getResults().get(i);
                System.out.println(i + ", " + result.getAverageSwapsRandom() + ", " + result.getAverageSwapsSorted() + ", " + result.getAverageSwapsReverse() + ", " + result.getAverageSwapsRandomFewUnique() + ", " + result.getAverageSwapsSortedFewUnique() + ", " + result.getAverageSwapsReverseFewUnique());
            }
            System.out.println(benchmark.getName() + " average times");
            System.out.println("n, random, sorted, reverse, random few, sorted few, reverse few");
            for(long i : benchmark.getResults().keySet()) {
                AlgorithmSortResult result = benchmark.getResults().get(i);
                System.out.println(i + ", " + result.getAverageRandomTime() + ", " + result.getAverageSortedTime() + ", " + result.getAverageReverseTime() + ", " + result.getAverageRandomFewUniqueTime() + ", " + result.getAverageSortedFewUniqueTime() + ", " + result.getAverageReverseFewUniqueTime());
            }
        }
    }
    
    /**
     * start Benchmark
     */
    public void start() throws FileNotFoundException, IOException {
        runBenchmarks();
        displayResults();
    }

    /**
     * main method for Benchmark
     * @param args 
     */
    public static void main(String... args) throws FileNotFoundException, IOException {
        new Benchmark().start();
    }
}
