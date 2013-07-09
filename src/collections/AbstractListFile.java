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
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public abstract class AbstractListFile<E> implements List64<E> {

    RandomAccessFile elementFile;
    File file;
    int recordLength;

    public AbstractListFile(File file) {
        this.file = file;
    }

    @Override
    public final void open(String mode) throws FileNotFoundException, IOException {
        this.elementFile = new RandomAccessFile(file, mode);
    }

    @Override
    public final void close() throws IOException {
        elementFile.close();
        elementFile = null;
    }

    @Override
    public final long size() {
        try {
            return elementFile.length() / recordLength;
        } catch (IOException ex) {
            throw new ListFileException(ex);
        }
    }

    void shiftSubList(long start, long end, long shift) {
        if (shift == 0) {
            return;
        } else if (shift < 0) {
            for (long i = start; i < end; i++) {
                set(i + shift, get(i));
            }
        } else if (shift > 0) {
            for (long i = end - 1; i >= start; i--) {
                set(i + shift, get(i));
            }
        }
    }

    @Override
    public final void add(E element) {
        set(size(), element);
    }

    @Override
    public void add(long index, E element) {
        shiftSubList(index, size(), 1);
        set(index, element);
    }

    @Override
    public final void addAll(Collection<E> c) {
        Iterator<E> iterator = c.iterator();
        long index = size(); //no add() to reduce calls to size()
        while (iterator.hasNext()) {
            set(index++, iterator.next());
        }
    }

    @Override
    public final void addAll(List64<E> list) {
        long index = size();
        for (long i = 0; i < list.size(); i++) {
            set(index++, list.get(i));  //no add() to reduce calls to size()
        }
    }

    @Override
    public final void addAll(long index, Collection<E> c) {
        shiftSubList(index, size(), c.size());
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()) {
            set(index++, iterator.next());
        }
    }

    @Override
    public final void addAll(long index, List64<E> list) {
        shiftSubList(index, size(), list.size());
        for (long i = 0; i < list.size(); i++) {
            set(index++, list.get(i));
        }
    }

    @Override
    public final void clear() {
        try {
            elementFile.getChannel().truncate(0);
        } catch (IOException ex) {
            throw new ListFileException(ex);
        }
    }

    @Override
    public final boolean contains(E element) {
        return indexOf(element) >= 0;
    }

    @Override
    public final boolean containsAll(Collection<E> c) {
        boolean r = true;
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()) {
            r &= contains(iterator.next());
        }
        return r;
    }

    @Override
    public final boolean containsAll(List64<E> list) {
        boolean r = true;
        for (long i = 0; i < list.size(); i++) {
            r &= contains(list.get(i));
        }
        return r;
    }

    @Override
    public final long indexOf(E element) {
        for (long i = 0; i < size(); i++) {
            if (get(i).equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public final long indexOf(E element, long n) {
        long index = -1;
        for (long i = 0, j = 0; i <= n; i++) {
            index = -1;
            for (; j < size(); j++) {
                if (get(j).equals(element)) {
                    index = j++;
                    break;
                }
            }
        }
        return index;
    }

    @Override
    public final List<Long> indexAllOf(E element) {
        List<Long> list = new ArrayList<>();
        for (long j = 0; j < size(); j++) {
            if (get(j).equals(element)) {
                list.add(j);
            }
        }
        return list;
    }

    @Override
    public final List64<Long> indexAllOf(E element, File file) throws FileNotFoundException, IOException {
        List64<Long> list = new ListFileLong(file);
        list.open("rw");
        for (long j = 0; j < size(); j++) {
            if (get(j).equals(element)) {
                list.add(j);
            }
        }
        return list;
    }

    @Override
    public final List<Long> indexAllOf(E element, Comparator<E> comparator) {
        List<Long> list = new ArrayList<>();
        for (long j = 0; j < size(); j++) {
            if (comparator.compare(get(j), element) == 0) {
                list.add(j);
            }
        }
        return list;
    }

    @Override
    public final List64<Long> indexAllOf(E element, Comparator<E> comparator, File file) throws FileNotFoundException, IOException {
        List64<Long> list = new ListFileLong(file);
        list.open("rw");
        for (long j = 0; j < size(); j++) {
            if (comparator.compare(get(j), element) == 0) {
                list.add(j);
            }
        }
        return list;
    }

    @Override
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public long lastIndexOf(E element) {
        for (long i = size() - 1; i > 0; i--) {
            if (get(i).equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public long lastIndexOf(E element, long n) {
        long index = -1;
        for (long i = 0, j = size() - 1; i <= n; i++) {
            index = -1;
            for (; j >= 0; j--) {
                if (get(j).equals(element)) {
                    index = j--;
                    break;
                }
            }
        }
        return index;
    }

    @Override
    public E remove(long index) {
        E r = get(index);
        shiftSubList(index + 1, size(), -1);
        try {
            elementFile.setLength(elementFile.length() - recordLength);
        } catch (IOException ex) {
            throw new ListFileException(ex);
        }
        return r;
    }

    @Override
    public final boolean remove(E element) {
        long index = indexOf(element);
        if (index >= 0) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(long start, long end) {
        shiftSubList(end, size(), end - start);
        try {
            elementFile.setLength(elementFile.length() - recordLength * (end - start));
        } catch (IOException ex) {
            throw new ListFileException(ex);
        }
        return true;
    }

    final boolean batchRemove(Collection<E> c, boolean compliment) {
        long size = size();
        long read = 0;
        long write = 0;
        boolean modified = false;
        try {
            for (; read < size(); read++) {
                E value = get(read);
                if (c.contains(value) == compliment && write != read) {
                    set(write++, value);
                }
            }
        } finally {
            if(read != size) {
                shiftSubList(read, size, write - read);
                write += size - read;
            }
            if(write != size) {
                try {
                    elementFile.getChannel().truncate(write * recordLength);
                } catch (IOException ex) {
                    throw new ListFileException(ex);
                }
            }
        }
        return modified;
    }

    //TODO find a new algorithm that does not use list.contains(value)
    final boolean batchRemove(List64<E> list, boolean compliment) {
        long size = size();
        long read = 0;
        long write = 0;
        boolean modified = false;
        try {
            for (; read < size(); read++) {
                E value = get(read);
                if (list.contains(value) == compliment && write != read) {
                    set(write++, value);
                }
            }
        } finally {
            if(read != size) {
                shiftSubList(read, size, write - read);
                write += size - read;
            }
            if(write != size) {
                try {
                    elementFile.getChannel().truncate(write * recordLength);
                } catch (IOException ex) {
                    throw new ListFileException(ex);
                }
            }
        }
        return modified;
    }

    //TODO find gaps and shift down elements between them until end
    @Override
    public final boolean removeAll(Collection<E> c) {
        return batchRemove(c, false);
    }

    //TODO find gaps and shift down elements between them until end
    @Override
    public final boolean removeAll(List64<E> list) {
        return batchRemove(list, false);
    }

    //TODO find gaps and shift down elements between them until end
    @Override
    public final boolean retainAll(Collection<E> c) {
        return batchRemove(c, true);
    }

    //TODO find gaps and shift down elements between them until end
    @Override
    public final boolean retainAll(List64<E> list) {
        return batchRemove(list, true);
    }

    @Override
    public final List<E> subList(long start, long end) {
        ArrayList<E> list = new ArrayList<>();

        for (long i = start; i < end; i++) {
            list.add(get(i));
        }

        return list;
    }
}
