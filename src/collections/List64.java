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
import java.util.Comparator;
import java.util.List;

/**
 * List64 is very similar to List. List cannot be used in these collections
 * because these collections have a long index instead of an int index.
 * 
 * This interface adds to List allowning for operations with other ListFiles
 * instead of just collections. There are added indexing options to help create an
 * index List<E> or List64<E> for a specific element. This can help create index
 * files of equal items or comparable items using a comparator for searches.
 * @author John Mercier <moaxcp at gmail.com>
 */
public interface List64<E> {
    void open(String mode) throws FileNotFoundException, IOException;
    void close() throws IOException;
    long size();
    void add(E element);
    void add(long index, E element);
    void addAll(Collection<E> c);
    void addAll(List64<E> list);
    void addAll(long index, Collection<E> c);
    void addAll(long index, List64<E> list);
    void clear();
    boolean contains(E element);
    boolean containsAll(Collection<E> c);
    boolean containsAll(List64<E> list);
    E get(long index);
    long indexOf(E element);
    long indexOf(E element, long n);
    List<Long> indexAllOf(E element);
    List64<Long> indexAllOf(E element, File file) throws FileNotFoundException, IOException;
    List<Long> indexAllOf(E element, Comparator<E> comparator);
    List64<Long> indexAllOf(E element, Comparator<E> comparator, File file) throws FileNotFoundException, IOException;
    boolean isEmpty();
    long lastIndexOf(E element);
    long lastIndexOf(E element, long n);
    E remove(long index);
    boolean remove(long start, long end);
    boolean remove(E element);
    boolean removeAll(Collection<E> c);
    boolean removeAll(List64<E> list);
    boolean retainAll(Collection<E> c);
    boolean retainAll(List64<E> list);
    E set(long index, E element);
    List<E> subList(long start, long end);
    List64<E> subList(File file, long start, long end) throws FileNotFoundException, IOException;
}
