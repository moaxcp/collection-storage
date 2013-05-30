/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package collections.test;

import collections.ListFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author john
 */
public abstract class AbstractListFileTest<E> {

    private boolean print;

    public AbstractListFileTest(boolean print) {
        this.print = print;
    }

    public abstract List<E> getOrderedList(int size);

    public abstract List<E> getRandomList(int size);

    public abstract ListFile<E> getOrderedList(File file, long size) throws FileNotFoundException, IOException;

    public abstract ListFile<E> getRandomList(File file, long size) throws FileNotFoundException, IOException;
    
    public abstract ListFile<E> getEmptyList(File file) throws FileNotFoundException, IOException;

    public void print(String message) {
        if (print) {
            System.out.println(message);
        }
    }

    public boolean compareList(ListFile<E> test, List<E> expected) {
        print("Comparing test list with expected results.");
        boolean pass = true;
        if (test.size() != expected.size()) {
            print("compare failed sizes do not match");
            pass = false;
        }
        print("sizes pass");
        for (int i = 0; i < test.size(); i++) {
            E inTest = test.get(i);
            E inExpected = expected.get(i);
            if (!inTest.equals(inExpected)) {
                print(i + " failed compare. got " + inTest + " instead of " + inExpected);
                pass = false;
            }
        }
        if (pass) {
            print("compare passed");
        } else {
            print("compare failed");
        }

        return pass;
    }

    public boolean compareList(ListFile<E> test, ListFile<E> expected) {
        print("Comparing test list with expected results.");
        boolean pass = true;
        if (test.size() != expected.size()) {
            print("compare failed sizes do not match");
            pass = false;
        }
        print("sizes pass");
        for (int i = 0; i < test.size(); i++) {
            E inTest = test.get(i);
            E inExpected = expected.get(i);
            if (!inTest.equals(inExpected)) {
                print(i + " failed compare. got " + inTest + " instead of " + inExpected);
                pass = false;
            }
        }
        if (pass) {
            print("compare passed");
        } else {
            print("compare failed");
        }

        return pass;
    }

    public void printList(ListFile<E> list) {
        print("Printing list");
        for (long i = 0; i < list.size(); i++) {
            print(i + " = " + list.get(i));
        }
    }

    public boolean testAddGet(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        print("testAddGet()");
        boolean pass = true;

        print("testing add(long number) with ordered list");
        List<E> l = getOrderedList(count);
        for (E i : l) {
            list.add(i);
        }
        print(count + " numbers added.");
        pass = compareList(list, l);

        list.clear();

        print("testing add(long number) with random list");
        l = getRandomList(count);
        for (E i : l) {
            list.add(i);
        }
        print(count + " numbers added.");
        pass = compareList(list, l);

        print("testAddGet() " + (pass ? "passed" : "failed"));
        
        list.close();
        file.delete();

        return pass;
    }

    public boolean testAddAtIndex(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        boolean pass = true;
        print("testAddAtIndex() started");

        List<E> check = getOrderedList(count);

        print("Adding values at even index.");
        for (int i = 0; i < count && pass; i += 2) {
            list.add(check.get(i));
        }

        print("adding values at odd index.");
        for (int i = 1; i < count && pass; i += 2) {
            list.add(i, check.get(i));
        }

        pass = compareList(list, check);

        print("testAddAtIndex() " + (pass ? "passed" : "failed"));

        list.close();
        file.delete();
        return pass;
    }

    public boolean testAddAllCollection(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        boolean pass = true;
        print("testAddAllCollection() started");
        List<E> check = new ArrayList<>();
        List<E> addList = getOrderedList(count);
        print("Adding collection.");
        check.addAll(addList);
        list.addAll(addList);

        pass = compareList(list, check);

        print("testAddAllCollection() " + (pass ? "passed" : "failed"));

        list.close();
        file.delete();
        return pass;
    }

    public boolean testAddAllColectionAtIndex(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        boolean pass = true;
        print("testAddAllColectionAtIndex() started");
        List<E> check = getRandomList(count / 2);
        List<E> addList = getOrderedList(count / 2);
        print("Adding collection.");
        list.addAll(check);
        check.addAll(count / 4, addList);
        list.addAll(count / 4, addList);

        pass = compareList(list, check);

        print("testAddAllColectionAtIndex() " + (pass ? "passed" : "failed"));

        list.close();
        file.delete();
        return pass;
    }

    public boolean testAddAllFileList(File to, File from, int count) throws FileNotFoundException, IOException {
        boolean pass = true;
        print("testAddAllFileList() started");
        print("generating lists");
        ListFile<E> toList = getEmptyList(to);
        ListFile<E> fromList = getOrderedList(from, count);
        toList.open("rw"); 
        fromList.open("rw"); 
        
        print("adding lists");
        toList.addAll(fromList);
        
        pass = compareList(toList, fromList);
        
        print("testAddAllFileList() " + (pass ? "passed" : "failed"));
        
        toList.close();
        fromList.close();
        to.delete();
        from.delete();
        return pass;
    }

    public boolean testAddAllFileListAtIndex(File to, File from, int count) throws FileNotFoundException, IOException {
        print("testAddAllFileListAtIndex() started");
        boolean pass = true;
        List<E> toList = getOrderedList(count / 2);
        List<E> fromList = getOrderedList(count / 2);
        ListFile<E> toListFile = getOrderedList(to, count / 2);
        toListFile.open("rw");
        ListFile<E> fromListFile = getOrderedList(from, count / 2);
        fromListFile.open("rw");
        
        print("adding lists");
        
        toList.addAll(count / 4, fromList);
        toListFile.addAll(count / 4, fromListFile);
        
        pass = compareList(toListFile, toList);
        
        print("testAddAllFileListAtIndex() " + (pass ? "passed" : "failed"));
        
        toListFile.close();
        to.delete();
        fromListFile.close();
        from.delete();
        return pass;
    }

    public boolean testContains(File file, int count) throws FileNotFoundException, IOException {
        print("testContains() started");
        boolean pass = true;
        ListFile<E> list = getOrderedList(file, count);
        list.open("rw");
        List<E> check = getOrderedList(count);
        
        print("checking contains");
        for(int i = 0; i < check.size(); i++) {
            pass &= list.contains(check.get(i));
            if(!pass) {
                print("list does not contain " + check.get(i));
                break;
            }
        }
        
        print("testContains() " + (pass ? "passed" : "failed"));
        list.close();
        file.delete();
        return pass;
    }

    public boolean testContainsAllCollection(File file, int count) throws FileNotFoundException, IOException {
        print("testContainsAllCollection() started");
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        
        
        
        list.close();
        file.delete();
        return false;
    }

    public boolean testContainsAllFileList(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testGetStartEnd(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testIndexOf(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testIndexOfNumber(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testIndexAllOf(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testIndexAllOfFile(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testIndexAllOfComparator(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testIndexAllOfComparatorFile(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testIsEmpty(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testLastIndexOf(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testLastIndexOfNumber(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testRemove(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testRemoveStartEnd(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testRemoveAllCollection(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testRetainAllCollection(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testRemoveAllFileList(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testRetainAllFileList(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }

    public boolean testSet(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        print("testSet()");
        boolean pass = true;

        print("testing set(index, element) with ordered list");
        List<E> l = getOrderedList(count);
        for (long i = 0; i < l.size(); i++) {
            list.set(i, l.get((int) i));
        }
        print(count + " elements added.");
        pass = compareList(list, l);

        list.clear();

        print("testing set(index, element) in random order");
        List<Integer> randOrder = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            randOrder.add(i);
        }
        Collections.shuffle(randOrder);

        for (int i = 0; i < randOrder.size(); i++) {
            list.set(randOrder.get(i), l.get(randOrder.get(i)));
        }

        print(count + " numbers added.");
        pass = compareList(list, l);

        print("testSet() " + (pass ? "passed" : "failed"));
        
        list.close();
        file.delete();
        return pass;
    }

    public boolean testSubList(File file, int count) throws FileNotFoundException, IOException {
        ListFile<E> list = getEmptyList(file);
        list.open("rw");
        list.close();
        file.delete();
        return false;
    }
}
