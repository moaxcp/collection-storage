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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public abstract class AbstractListFile<E> implements ListFile<E> {
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
        return file.length() - (file.length() % recordLength) / recordLength;
    }

    @Override
    public final void add(E element) {
        set(size(), element);
    }

    @Override
    public void addAll(Collection<E> c) {
        Iterator<E> iterator = c.iterator();
        long index = size(); //no add() to reduce file system access
        while (iterator.hasNext()) {
            set(index++, iterator.next());
        }
    }

    @Override
    public void addAll(ListFile<E> list) {
        long index = size();
        for (long i = 0; i < list.size(); i++) {
            set(index++, list.get(i));  //no add() to reduce calls to size()
        }
    }

    @Override
    public void addAll(long index, Collection<E> c) {
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()) {
            add(index++, iterator.next());
        }
    }

    @Override
    public void addAll(long index, ListFile<E> list) {
        for (long i = 0; i < list.size(); i++) {
            add(index++, list.get(i));
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
        return indexOf(element) > 0;
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
    public final boolean containsAll(ListFile<E> list) {
        boolean r = true;
        for (long i = 0; i < list.size(); i++) {
            r &= contains(list.get(i));
        }
        return r;
    }

    @Override
    public final List<E> get(long start, long end) {
        ArrayList<E> list = new ArrayList<>();

        for (long i = start; i < end; i++) {
            list.add(get(i));
        }

        return list;
    }

    @Override
    public long indexOf(E element) {
        for (long i = 0; i < size(); i++) {
            if (get(i) == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public long lastIndexOf(E element) {
        for (long i = size() - 1; i > 0; i--) {
            if (get(i) == element) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public E remove(long index) {
        try {
            E r = get(index);
            E last = get(size() - 1);
            elementFile.getChannel().truncate(file.length() - recordLength);
            for (long i = size() - 1; i >= index; i--) {
                E temp = get(i);
                add(i, last);
                last = temp;
            }
            return r;
        } catch (IOException ex) {
            throw new ListFileException(ex);
        }
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
    
    public boolean remove(long start, long end) {
        throw new IllegalArgumentException("remove(long, long)");
    }

    @Override
    public boolean removeAll(Collection<E> c) {
        boolean r = true;
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()) {
            r &= remove(iterator.next());
        }
        return r;
    }

    @Override
    public boolean removeAll(ListFile<E> list) {
        boolean r = true;
        for (long i = 0; i < list.size(); i++) {
            r &= remove(list.get(i));
        }
        return r;
    }

    @Override
    public boolean retainAll(Collection<E> c) {
        boolean r = false;
        for (long i = 0; i < size(); i++) {
            if (!c.contains(get(i))) {
                r = true;
                remove(i);
            }
        }
        return r;
    }

    @Override
    public boolean retainAll(ListFile<E> list) {
        boolean r = false;
        for (long i = 0; i < size(); i++) {
            if (!list.contains(get(i))) {
                r = true;
                remove(i);
            }
        }
        return r;
    }
}
