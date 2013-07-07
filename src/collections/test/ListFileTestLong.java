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

import collections.List64;
import collections.ListFileLong;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public class ListFileTestLong extends AbstractListFileTest<Long> {
    
    public ListFileTestLong(boolean print) {
        super(print);
    }
    
    @Override
    public List<Long> getOrderedList(int size) {
        List<Long> list = new ArrayList<>((int)size);
        for(long i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public List<Long> getOrderedList(int size, int unique) {
        List<Long> list = new ArrayList<Long>();
        List<Long> values = new ArrayList<Long>();
        for(int i = 0; i < unique; i++) {
            values.add((long)size / unique * i);
        }
        for(int i = 0; i < size; i++) {
            list.add(values.get(i >= size - size % unique ? (size - size % unique - 1) / (size / unique)  : i / (size / unique)));
        }
        return list;
    }
    
    @Override
    public List<Long> getRandomList(int size) {
        List<Long> list = new ArrayList<>(size);
        Random rand = new Random();
        for(int i = 0; i < size; i++) {
            list.add(rand.nextLong());
        }
        return list;
    }

    @Override
    public List<Long> getRandomList(int size, int unique) {
        List<Long> list = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < size; i++) {
            list.add((long)rand.nextInt(unique) * (size / unique));
        }
        return list;
    }
    
    @Override
    public List64<Long> getOrderedList(File file, int size) throws FileNotFoundException, IOException {
        List64<Long> list = new ListFileLong(file);
        list.open("rw");
        for(long i = 0; i < size; i++) {
            list.add(i);
        }
        list.close();
        return list;
    }

    @Override
    public List64<Long> getOrderedList(File file, int size, int unique) throws FileNotFoundException, IOException {
        List64<Long> list = new ListFileLong(file);
        list.open("rw");
        List<Long> values = new ArrayList<Long>();
        for(int i = 0; i < unique; i++) {
            values.add((long)size / unique * i);
        }
        for(int i = 0; i < size; i++) {
            list.add(values.get(i >= size - size % unique ? (size - size % unique - 1) / (size / unique)  : i / (size / unique)));
        }
        list.close();
        return list;
    }
    
    @Override
    public List64<Long> getRandomList(File file, int size) throws FileNotFoundException, IOException {
        List64<Long> list = new ListFileLong(file);
        list.open("rw");
        Random rand = new Random();
        for(int i = 0; i < size; i++) {
            list.add(rand.nextLong());
        }
        list.close();
        return list;
    }

    @Override
    public List64<Long> getRandomList(File file, int size, int unique) throws FileNotFoundException, IOException {
        List64<Long> list = new ListFileLong(file);
        list.open("rw");
        Random rand = new Random();
        for(int i = 0; i < size; i++) {
            list.add((long)rand.nextInt(unique) * (size / unique));
        }
        list.close();
        return list;
    }

    @Override
    public List64<Long> getEmptyList(File file) throws FileNotFoundException, IOException {
        List64<Long> list = new ListFileLong(file);
        return list;
    }
    
}
