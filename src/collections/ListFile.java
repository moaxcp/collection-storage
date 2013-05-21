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
import java.util.Collection;
import java.util.List;

/**
 * ListFile is very similar to List. List cannot be used in these collections
 * because these collections have a long index instead of an int index.
 * @author John Mercier <moaxcp at gmail.com>
 */
public interface ListFile<E> {
    void open(String mode) throws FileNotFoundException, IOException;
    void close() throws IOException;
    long size();
    void add(E element);
    void add(long index, E element);
    void addAll(Collection<E> c);
    void addAll(ListFile<E> list);
    void addAll(long index, Collection<E> c);
    void addAll(long index, ListFile<E> list);
    void clear();
    boolean contains(E element);
    boolean containsAll(Collection<E> c);
    boolean containsAll(ListFile<E> list);
    E get(long index);
    List<E> get(long start, long end);
    long indexOf(E element);
    boolean isEmpty();
    long lastIndexOf(E element);
    E remove(long index);
    boolean remove(long start, long end);
    boolean remove(E element);
    boolean removeAll(Collection<E> c);
    boolean removeAll(ListFile<E> list);
    boolean retainAll(Collection<E> c);
    boolean retainAll(ListFile<E> list);
    E set(long index, E element);
    ListFile<E> subList(File file, long start, long end) throws FileNotFoundException, IOException;
}
