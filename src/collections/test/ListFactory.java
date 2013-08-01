package collections.test;

import collections.List64;
import collections.ListFileLong;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * ListFactor. creates different types of lists for testing.
 * @author John Mercier <moaxcp at gmail.com>
 */
public class ListFactory {
    
    public static final int UNIQUE = 10;
    private static Random rand = new Random();

    public static long nextLong(long n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        long bits, val;
        do {
            bits = (rand.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }

    /**
     * tests if a list is ordered.
     *
     * @param list
     * @return true if the list is ordered
     */
    public static boolean isOrdered(List64<? extends Comparable> list) throws FileNotFoundException, IOException {
        boolean r = true;
        list.open("r");
        for (int i = 1; i < list.size(); i++) {
            r &= list.get(i - 1).compareTo(list.get(i)) <= 0;
        }
        list.close();
        return r;
    }
    
    /**
     * tests if a list is ordered.
     * @param list
     * @return true if the list is ordered
     */
    public static boolean isOrdered(List<? extends Comparable> list) {
        boolean r = true;
        for(int i = 1; i < list.size(); i++) {
            r &= list.get(i - 1).compareTo(list.get(i)) <= 0;
        }
        return r;
    }

    /**
     * creates a random list
     *
     * @param count number to count to
     * @param fewUnique only use Unique values
     * @return a random list
     */
    public static List64<Long> getRandomList(File file, long count, boolean fewUnique) throws FileNotFoundException, IOException {
        List64<Long> list = new ListFileLong(file);
        list.open("rw");
        if (fewUnique) {
            int unique = UNIQUE;
            long interval = count / unique;
            for (int i = 0; i < count; i++) {
                list.add(rand.nextInt(unique) * interval);
            }
        } else {
            for (long i = 0; i < count; i++) {
                list.set(nextLong(count), i);
            }
        }
        list.close();

        return list;
    }

    /**
     * creates a random list
     * @param count number to count to
     * @param fewUnique only use Unique values
     * @return a random list
     */
    public static List<Long> getRandomList(int count, boolean fewUnique) {
        List<Long> list = new ArrayList<>(count);
        if (fewUnique) {
            int unique = UNIQUE;
            long interval = count / unique;
            for (int i = 0; i < count; i++) {
                list.add(rand.nextInt(unique) * interval);
            }
        } else {
            for(long i = 0; i < count; i++) {
                list.add(i);
            }
            for (long i = 0; i < count; i++) {
                list.set(rand.nextInt(count), i);
            }
        }

        return list;
    }

    /**
     * creates a sorted list
     *
     * @param count number to count to
     * @param fewUnique only use Unique values
     * @return sorted list
     */
    public static List64<Long> getSortedList(File file, long count, boolean fewUnique) throws FileNotFoundException, IOException {
        List64<Long> list = new ListFileLong(file);
        list.open("rw");
        if (fewUnique) {
            List<Long> values = new ArrayList<>();

            int chunks = UNIQUE;
            for (long i = 0; i < chunks; i++) {
                values.add(count / chunks * i);
            }
            for (int i = 0; i < count; i++) {
                list.add(values.get((int)(i >= count - count % chunks ? (count - count % chunks - 1) / (count / chunks) : i / (count / chunks))));
            }
        } else {
            for (long i = 0; i < count; i++) {
                list.add(i);
            }
        }
        list.close();
        return list;
    }

    /**
     * creates a sorted list
     * @param count number to count to
     * @param fewUnique only use Unique values
     * @return sorted list
     */
    public static List<Long> getSortedList(int count, boolean fewUnique) {
        List<Long> list = new ArrayList<>();
        
        if(fewUnique) {
            List<Long> values = new ArrayList<>();
            
            int chunks = UNIQUE;
            for(long i = 0; i < chunks; i++) {
                values.add(count / chunks * i);
            }
            for(int i = 0; i < count; i++) {
                list.add(values.get(i >= count - count % chunks ? (count - count % chunks - 1) / (count / chunks)  : i / (count / chunks)));
            }
        } else {
            for(long i = 0; i < count; i++) {
                list.add(i);
            }
        }
        
        return list;
    }

    /**
     * creates a reversed list
     *
     * @param count number to count to
     * @param fewUnique only use Unique values
     * @return reversed list
     */
    public static List64<Long> getReversedList(File file, int count, boolean fewUnique) throws FileNotFoundException, IOException {
        List64<Long> list = getSortedList(file, count, fewUnique);
        list.open("rw");
        long size = list.size();
        for (long i = 0, mid = size >> 1, j = size - 1; i < mid; i++, j--) {
            Long temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
        list.close();
        return list;
    }

    /**
     * creates a reversed list
     * @param count number to count to
     * @param fewUnique only use Unique values
     * @return reversed list
     */
    public static List<Long> getReversedList(int count, boolean fewUnique) {
        List<Long> list = getSortedList(count, fewUnique);
        Collections.reverse(list);
        return list;
    }
    
    /**
     * prints the list
     * @param list 
     */
    public static void printList(List<Long> list) {
        for(int i = 0; i < list.size(); i++) {
            System.out.printf("%1$4d ", list.get(i));
            for(int j = 0; j < list.get(i); j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    /**
     * prints the list
     *
     * @param list
     */
    public static void printList(List64<Long> list) throws FileNotFoundException, IOException {
        list.open("r");
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%1$4d ", list.get(i));
            for (int j = 0; j < list.get(i); j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        list.close();
    }
    
    /**
     * Test ListFactory. Creates lists and prints them.
     * @param args 
     */
    public static void main(String... args) throws FileNotFoundException, IOException {
        int size = 1000;
        File file = null;
        String path = "/extra";
        
        System.out.println("Testing ListFactory for List");
        System.out.println("getRandomList(" + size + ", false)");
        printList(getRandomList(size, false));
        System.out.println("getRandomList(" + size + ", true)");
        printList(getRandomList(size, true));
        System.out.println("getSortedList(" + size + ", false)");
        printList(getSortedList(size, false));
        System.out.println("getSortedList(" + size + ", true)");
        printList(getSortedList(size, true));
        System.out.println("getReversedList(" + size + ", false)");
        printList(getReversedList(size, false));
        System.out.println("getReversedList(" + size + ", true)");
        printList(getReversedList(size, true));
        
        System.out.println("Testing ListFactory for List64");
        System.out.println("getRandomList(file, " + size + ", false)");
        file = new File(path + "/random");
        printList(getRandomList(file, size, false));
        file.delete();
        System.out.println("getRandomList(file, " + size + ", true)");
        file = new File(path + "/randomFew");
        printList(getRandomList(file, size, true));
        file.delete();
        System.out.println("getSortedList(file, " + size + ", false)");
        file = new File(path + "/sorted");
        printList(getSortedList(file, size, false));
        file.delete();
        System.out.println("getSortedList(file, " + size + ", true)");
        file = new File(path + "/sortedFew");
        printList(getSortedList(file, size, true));
        file.delete();
        System.out.println("getReversedList(file, " + size + ", false)");
        file = new File(path + "/reversed");
        printList(getReversedList(file, size, false));
        file.delete();
        System.out.println("getReversedList(file, " + size + ", true)");
        file = new File(path + "/reversedFew");
        printList(getReversedList(file, size, true));
        file.delete();
    }
}
