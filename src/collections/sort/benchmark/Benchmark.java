package collections.sort.benchmark;

import collections.List64;
import collections.test.ListFactory;
import collections.sort.BubbleSort;
import collections.sort.CiuraShellSort;
import collections.sort.FrankLazarusShellSort;
import collections.sort.GonnetBaezaYatesShellSort;
import collections.sort.HeapSort;
import collections.sort.InsertionSort;
import collections.sort.KnuthShellSort;
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
    private long startTime;

    public Benchmark() {
        benchmarks = new ArrayList<AlgorithmBenchmark>();
        listFile = new File("/extra/benchmarkList");
        copyFile = new File("/extra/benchmarkCopy");
        listFile.delete();
        copyFile.delete();
    }
    
    public String getTimeString(long time) {
        long millisecond = 1000000;
        long second = 1000 * millisecond;
        long minute = 60 * second;
        long hour = 60 * minute;
        long day = 24 * hour;
        long days = (time / day);
        long hours = (time / hour) % 24;
        long minutes = (time / minute) % 60;
        long seconds = (time / second) % 60;
        long milli = (time / millisecond) % 1000;
        long nano = time % 1000000;
        String sdays = (days < 10 ? "0" + days : ("" + days)) + ":";
        String shours = (hours < 10 ? "0" + hours : ("" + hours)) + ":";
        String sminutes = (minutes < 10 ? "0" + minutes : "" + minutes) + ":";
        String sseconds = (seconds < 10 ? "0" + seconds : "" + seconds) + ":";
        String smilli = (milli < 100 ? milli < 10 ? "00" + milli : "0" + milli : "" + milli);
        String snano = nano < 100000 ? nano < 10000 ? nano < 1000 ? nano < 100 ? nano < 10 ? "00000" + nano : "0000" + nano : "000" + nano : "00" + nano : "0" + nano : "" + nano;
        String postfix = days >= 1 ? "day" : hours >= 1 ? "hr" : minutes >= 1 ? "mn" : seconds >= 1 ? "s" : milli >= 1 ? "ms" : nano >= 1 ? "ns" : "";
        return (days == 0 ? "" : sdays) + (days == 0 && hours == 0 ? "" : shours) + (days == 0 && hours == 0 && minutes == 0 ? "" : sminutes) + (days == 0 && hours == 0 && minutes == 0 && seconds == 0 ? "" : sseconds) + (days == 0 && hours == 0 && minutes == 0 && seconds == 0 && milli == 0 ? "" : smilli) + (milli == 0 ? snano : "") + " " + postfix;
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
        //largeBenchmarks.add(new AlgorithmBenchmark("Frank and Lazarus's Shell Sort", new FrankLazarusShellSort()));
        largeBenchmarks.add(new AlgorithmBenchmark("Knuth Shell Sort", new KnuthShellSort()));
        //largeBenchmarks.add(new AlgorithmBenchmark("Gonnet and Baeza-Yates Shell Sort", new GonnetBaezaYatesShellSort()));
        //largeBenchmarks.add(new AlgorithmBenchmark("Ciura's Shell Sort", new CiuraShellSort()));
        //largeBenchmarks.add(new AlgorithmBenchmark("Heap Sort", new HeapSort()));
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
        smallSizes.add(160);
        smallSizes.add(170);
        smallSizes.add(180);
        smallSizes.add(190);
        smallSizes.add(200);
        smallSizes.add(210);
        smallSizes.add(220);
        smallSizes.add(230);
        smallSizes.add(240);
        smallSizes.add(250);
        smallSizes.add(260);
        smallSizes.add(270);
        smallSizes.add(280);
        smallSizes.add(290);
        smallSizes.add(300);
        
        List<Integer> largeSizes = new ArrayList<Integer>();
        largeSizes.add(400);
        largeSizes.add(500);
        largeSizes.add(600);
        largeSizes.add(700);
        largeSizes.add(800);
        largeSizes.add(900);
        largeSizes.add(1000);
        largeSizes.add(2000);
//        largeSizes.add(3000);
//        largeSizes.add(4000);
//        largeSizes.add(5000);
//        largeSizes.add(6000);
//        largeSizes.add(7000);
//        largeSizes.add(8000);
//        largeSizes.add(9000);
//        largeSizes.add(10000);
//        largeSizes.add(11000);
//        largeSizes.add(12000);
//        largeSizes.add(13000);
//        largeSizes.add(14000);
//        largeSizes.add(15000);
//        largeSizes.add(16000);
//        largeSizes.add(17000);
//        largeSizes.add(18000);
//        largeSizes.add(19000);
//        largeSizes.add(20000);

        startTime = System.nanoTime();
        for (AlgorithmBenchmark benchmark : smallBenchmarks) {
            for(int i : smallSizes) {
                System.out.println("Running " + benchmark.getName() + " " + tests + " times with size " + i);
                System.out.println("Time: " + getTimeString(System.nanoTime() - startTime));
                SortAlgorithm algorithm = benchmark.getAlgorithm();
                AlgorithmSortResult result = runTests(algorithm, i);
                benchmark.addResult(result);
            }
        }
        

        for (AlgorithmBenchmark benchmark : largeBenchmarks) {
            for(int i : smallSizes) {
                System.out.println("Running " + benchmark.getName() + " " + tests + " times with size " + i);
                System.out.println("Time: " + getTimeString(System.nanoTime() - startTime));
                SortAlgorithm algorithm = benchmark.getAlgorithm();
                AlgorithmSortResult result = runTests(algorithm, i);
                benchmark.addResult(result);
            }
            for(int i : largeSizes) {
                System.out.println("Running " + benchmark.getName() + " " + tests + " times with size " + i);
                System.out.println("Time: " + getTimeString(System.nanoTime() - startTime));
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
        System.out.println(2 / 2.2);
        new Benchmark().start();
    }
}
