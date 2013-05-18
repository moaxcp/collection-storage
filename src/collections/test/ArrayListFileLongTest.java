/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collections.test;

import collections.ArrayListFileLong;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author john
 */
public class ArrayListFileLongTest {
    
    public static List<Long> getOrderedList(int size) {
        List<Long> list = new ArrayList<>(size);
        for(long i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }
    
    public static List<Long> getRandomList(int size) {
        List<Long> list = new ArrayList<>(size);
        Random rand = new Random();
        for(int i = 0; i < size; i++) {
            list.add(rand.nextLong());
        }
        return list;
    }
    
    public static void compareList(ArrayListFileLong test, List<Long> expected) {
        System.out.println("Comparing test list with expected results.");
        if(test.size() != expected.size()) {
            System.out.println("compare failed sizes do not match");
        } else {
            System.out.println("sizes pass");
        }
        boolean pass = true;
        for(int i = 0; i < test.size(); i++) {
            long inTest = test.get(i);
            long inExpected = expected.get(i);
            if(inTest != inExpected) {
                System.out.println(i + " failed. got " + inTest + " instead of " + inExpected);
                pass = false;
            }
        }
        if(pass) {
            System.out.println("compare passed");
        } else {
            System.out.println("compare failed");
        }
    }
    
    public static void testRecordLength(ArrayListFileLong list) {
        System.out.println("testing recordLength " + Long.SIZE + " " + list.getRecordLength() + " " + (Long.SIZE / 8 == list.getRecordLength()));
    }
    
    public static void testAddGet(ArrayListFileLong list, int count) {
        list.clear();
        
        System.out.println("testing add(long number) with ordered list");
        List<Long> l = getOrderedList(count);
        for(long i : l) {
            list.add(i);
        }
        System.out.println(count + " numbers added.");
        compareList(list, l);
        
        list.clear();
        
        System.out.println("testing add(long number) with random list");
        l = getRandomList(count);
        for(long i : l) {
            list.add(i);
        }
        System.out.println(count + " numbers added.");
        compareList(list, l);
        
        System.out.println("add(long number) complete");
    }
    
    public static void main(String... args) {
        ArrayListFileLong list = null;
        try {
            list = new ArrayListFileLong("/home/john/testList.dat");
            list.open("rw");
            list.clear();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArrayListFileLongTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArrayListFileLongTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        testRecordLength(list);
        
        int count = 1 * 1024 / 8;
        
        testAddGet(list, count);
        
        
        
//        Random rand = new Random();
//        
//        for(long i = 0; i < count; i++) {
//            list.add(rand.nextLong());
//            
//            System.out.println(list.size());
//        }
    }
}
