/*
 * Copyright (C) 2013 John Mercier <moaxcp at gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package collections.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public class ListFileTestMain {
    
    public static String filePath = "/extra";
    
    public static void runListFileTest(AbstractListFileTest tester, int count) throws FileNotFoundException, IOException {
        boolean pass = tester.testAddGet(new File(filePath + "/testAddGet"), count);
        System.out.printf("%2$s %1$s\n", "testAddGet(list, count)", pass ? "passed" : "failed");

        pass = tester.testAddAtIndex(new File(filePath + "/testAddAtIndex"), count);
        System.out.printf("%2$s %1$s\n", "testAddAtIndex(list, count)", pass ? "passed" : "failed");

        pass = tester.testAddAllCollection(new File(filePath + "/testAddAllCollection"), count);
        System.out.printf("%2$s %1$s\n", "testAddAllCollection(list, count)", pass ? "passed" : "failed");

        pass = tester.testAddAllColectionAtIndex(new File(filePath + "/testAddAllColectionAtIndex"), count);
        System.out.printf("%2$s %1$s\n", "testAddAllColectionAtIndex(list, count)", pass ? "passed" : "failed");

        pass = tester.testAddAllFileList(new File(filePath + "/testAddAllFileListTo"), new File(filePath + "/testAddAllFileListFrom"), count);
        System.out.printf("%2$s %1$s\n", "testAddAllFileList(list, count)", pass ? "passed" : "failed");

        pass = tester.testAddAllFileListAtIndex(new File(filePath + "/testAddAllFileListAtIndexTo"), new File(filePath + "/testAddAllFileListFrom"), count);
        System.out.printf("%2$s %1$s\n", "testAddAllFileListAtIndex(list, count)", pass ? "passed" : "failed");

        pass = tester.testContains(new File(filePath + "/testContains"), count);
        System.out.printf("%2$s %1$s\n", "testContains(list, count)", pass ? "passed" : "failed");

        pass = tester.testContainsAllCollection(new File(filePath + "/testAddGet"), count);
        System.out.printf("%2$s %1$s\n", "testContainsAllCollection(list, count)", pass ? "passed" : "failed");

        pass = tester.testContainsAllFileList(new File(filePath + "/testContainsAllCollectionTo"), new File(filePath + "/testContainsAllCollectionFrom"), count);
        System.out.printf("%2$s %1$s\n", "testContainsAllFileList(ListFile, ListFile, count)", pass ? "passed" : "failed");

        pass = tester.testIndexOf(new File(filePath + "/testIndexOf"), count);
        System.out.printf("%2$s %1$s\n", "testIndexOf(list, count)", pass ? "passed" : "failed");

        pass = tester.testIndexOfNumber(new File(filePath + "/testIndexOfNumber"), count, 10);
        System.out.printf("%2$s %1$s\n", "testIndexOfNumber(list, count)", pass ? "passed" : "failed");

        pass = tester.testIndexAllOf(new File(filePath + "/testIndexAllOf"), count);
        System.out.printf("%2$s %1$s\n", "testIndexAllOf(list, count)", pass ? "passed" : "failed");

        pass = tester.testIndexAllOfFile(new File(filePath + "/testIndexAllOfFile"), count);
        System.out.printf("%2$s %1$s\n", "testIndexAllOfFile(list, count)", pass ? "passed" : "failed");

        pass = tester.testIndexAllOfComparator(new File(filePath + "/testIndexAllOfComparator"), count);
        System.out.printf("%2$s %1$s\n", "testIndexAllOfComparator(list, count)", pass ? "passed" : "failed");

        pass = tester.testIndexAllOfComparatorFile(new File(filePath + "/testIndexAllOfComparatorFile"), count);
        System.out.printf("%2$s %1$s\n", "testIndexAllOfComparatorFile(list, count)", pass ? "passed" : "failed");

        pass = tester.testIsEmpty(new File(filePath + "/testIsEmpty"), count);
        System.out.printf("%2$s %1$s\n", "testIsEmpty(list, count)", pass ? "passed" : "failed");

        pass = tester.testLastIndexOf(new File(filePath + "/testLastIndexOf"), count, 10);
        System.out.printf("%2$s %1$s\n", "testLastIndexOf(list, count)", pass ? "passed" : "failed");

        pass = tester.testLastIndexOfNumber(new File(filePath + "/testLastIndexOfNumber"), count);
        System.out.printf("%2$s %1$s\n", "testLastIndexOfNumber(list, count)", pass ? "passed" : "failed");

        pass = tester.testRemove(new File(filePath + "/testRemove"), count);
        System.out.printf("%2$s %1$s\n", "testRemove(list, count)", pass ? "passed" : "failed");

        pass = tester.testRemoveStartEnd(new File(filePath + "/testRemoveStartEnd"), count);
        System.out.printf("%2$s %1$s\n", "testRemoveStartEnd(list, count)", pass ? "passed" : "failed");

        pass = tester.testRemoveAllCollection(new File(filePath + "/testRemoveAllCollection"), count);
        System.out.printf("%2$s %1$s\n", "testRemoveAllCollection(list, count)", pass ? "passed" : "failed");

        pass = tester.testRetainAllCollection(new File(filePath + "/testRetainAllCollection"), count);
        System.out.printf("%2$s %1$s\n", "testRetainAllCollection(list, count)", pass ? "passed" : "failed");

        pass = tester.testRemoveAllFileList(new File(filePath + "/testRemoveAllFileList"), count);
        System.out.printf("%2$s %1$s\n", "testRemoveAllFileList(list, count)", pass ? "passed" : "failed");

        pass = tester.testRetainAllFileList(new File(filePath + "/testRetainAllFileList"), count);
        System.out.printf("%2$s %1$s\n", "testRetainAllFileList(list, count)", pass ? "passed" : "failed");

        pass = tester.testSet(new File(filePath + "/testSet"), count);
        System.out.printf("%2$s %1$s\n", "testSet(list, count)", pass ? "passed" : "failed");

        pass = tester.testSize(new File(filePath + "/testSize"), count);
        System.out.printf("%2$s %1$s\n", "testSize(list, count)", pass ? "passed" : "failed");

        pass = tester.testSubList(new File(filePath + "/testSubList"), count);
        System.out.printf("%2$s %1$s\n", "testSubList(list, count)", pass ? "passed" : "failed");

        pass = tester.testSubListFile(new File(filePath + "/testSubListFile"), count);
        System.out.printf("%2$s %1$s\n", "testSubListFile(list, count)", pass ? "passed" : "failed");

    }
    
    public static void main(String... args) throws FileNotFoundException, IOException {
        int count = 250;
        
        AbstractListFileTest tester = new ListFileTestLong(true);
        runListFileTest(tester, count);
    }
}
