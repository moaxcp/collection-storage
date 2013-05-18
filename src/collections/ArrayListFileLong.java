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

package collections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author john
 */
public class ArrayListFileLong extends ArrayListFile {
    
    public ArrayListFileLong(File file) throws FileNotFoundException, IOException {
        super(file);
        recordLength = Long.SIZE / 8;
    }
    
    public ArrayListFileLong(String path) throws FileNotFoundException, IOException {
        this(new File(path));
    }
    
    public void add(long number) {
        try {
            elementFile.seek(recordLength * size());
            elementFile.writeLong(number);
        } catch (IOException ex) {
            throw new ArrayListFileException(ex);
        }
    }
    
    public void add(long index, long number) {
        try {
            for(long i = size(); i > index; i--) {
                elementFile.seek(recordLength * (i - 1));
                long temp = elementFile.readLong();
                elementFile.seek(recordLength * i);
                elementFile.writeLong(temp);
            }
            elementFile.seek(recordLength * index);
            elementFile.writeLong(number);
        } catch (IOException ex) {
            throw new ArrayListFileException(ex);
        }
    }
    
    public void addAll(Collection<Long> c) {
        Iterator<Long> iterator = c.iterator();
        while(iterator.hasNext()) {
            add(iterator.next());
        }
    }
    
    public void addAll(ArrayListFileLong list) {
        for(long i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }
    
    public void addAll(long index, Collection<Long> c) {
        Iterator<Long> iterator = c.iterator();
        while(iterator.hasNext()) {
            add(index++, iterator.next());
        }
    }
    
    public void addAll(long index, ArrayListFileLong list) {
        for(long i = 0; i < list.size(); i++) {
            add(index++, list.get(i));
        }
    }
    
    public void clear() {
        try {
            elementFile.getChannel().truncate(0);
        } catch (IOException ex) {
            throw new ArrayListFileException(ex);
        }
    }
    
    public boolean contains(long number) {
        return indexOf(number) > 0;
    }
    
    public boolean containsAll(Collection<Long> c) {
        boolean r = true;
        Iterator<Long> iterator = c.iterator();
        while(iterator.hasNext()) {
            r &= contains(iterator.next());
        }
        return r;
    }
    
    public boolean containsAll(ArrayListFileLong list) {
        boolean r = true;
        for(long i = 0; i < list.size(); i++) {
            r &= contains(list.get(i));
        }
        return r;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof ArrayListFileLong) {
            ArrayListFileLong list = (ArrayListFileLong) o;
            if(size() != list.size()) {
                return false;
            }
            for(long i = 0; i < size(); i++) {
                if(get(i) != list.get(i)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    public long get(long index) {
        try {
            elementFile.seek(recordLength * index);
            return elementFile.readLong();
        } catch (IOException ex) {
            throw new ArrayListFileException(ex);
        }
    }
    
    public ArrayList<Long> get(long start, long end) {
         ArrayList<Long> list = new ArrayList<>();
         
         for(long i = start; i < end; i++) {
             list.add(get(i));
         }
         
         return list;
    }

    @Override
    public int hashCode() {
        int hash = 7 * (int)size();
        return hash;
    }
    
    public long indexOf(long number) {
        for(long i = 0; i < size(); i++) {
            if(get(i) == number) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public long lastIndexOf(long number) {
        for(long i = size() - 1; i > 0; i--) {
            if(get(i) == number) {
                return i;
            }
        }
        return -1;
    }
    
    public long remove(long index) {
        try {
            long r = get(index);
            long last = get(size() - 1);
            elementFile.getChannel().truncate(file.length() - recordLength);
            for(long i = size() - 1; i >= index; i--) {
                long temp = get(i);
                add(i, last);
                last = temp;
            }
            return r;
        } catch (IOException ex) {
            throw new ArrayListFileException(ex);
        }
    }
    
    public boolean removeValue(long number) {
        long index = indexOf(number);
        if(index >= 0) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean removeAll(Collection<Long> c) {
        boolean r = true;
        Iterator<Long> iterator = c.iterator();
        while(iterator.hasNext()) {
            r &= removeValue(iterator.next());
        }
        return r;
    }
    
    public boolean removeAll(ArrayListFileLong list) {
        boolean r = true;
        for(long i = 0; i < list.size(); i++) {
            r &= removeValue(list.get(i));
        }
        return r;
    }
    
    public boolean retainAll(Collection<Long> c) {
        boolean r = false;
        for(long i = 0; i < size(); i++) {
            if(!c.contains(get(i))) {
                r = true;
                remove(i);
            }
        }
        return r;
    }
    
    public boolean retainAll(ArrayListFileLong list) {
        boolean r = false;
        for(long i = 0; i < size(); i++) {
            if(!list.contains(get(i))) {
                r = true;
                remove(i);
            }
        }
        return r;
    }
    
    public long set(long index, long number) {
        try {
            elementFile.seek(recordLength * index);
            long r = elementFile.readLong();
            elementFile.writeLong(number);
            return r;
        } catch (IOException ex) {
            throw new ArrayListFileException(ex);
        }
    }
    
    public ArrayListFileLong subList(File file, long start, long end) throws FileNotFoundException, IOException {
        ArrayListFileLong list = new ArrayListFileLong(file);
        for(long i = start; i < end; i++) {
            list.add(get(i));
        }
        return list;
    }
    
    public ArrayListFileLong subList(String path, long start, long end) throws FileNotFoundException, IOException {
        ArrayListFileLong list = new ArrayListFileLong(path);
        for(long i = start; i < end; i++) {
            list.add(get(i));
        }
        return list;
    }
}
