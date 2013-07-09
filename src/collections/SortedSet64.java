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
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public interface SortedSet64<E> {
    void open(String mode) throws FileNotFoundException, IOException;
    void close() throws IOException;
    boolean add(E e);
    boolean addAll(Collection<? extends E> c);
    void clear();
    boolean contains(E e);
    boolean containsAll(Collection<E> c);
    boolean containsAll(List64<E> list);
    E get(long index);
    long indexOf(E element);
    long indexOf(E element, long n);
    List<Long> indexAllOf(E element);
    List64<Long> indexAllOf(E element, File file) throws FileNotFoundException, IOException;
    List<Long> indexAllOf(E element, Comparator<E> comparator);
    List64<Long> indexAllOf(E element, Comparator<E> comparator, File file) throws FileNotFoundException, IOException;
    @Override
    boolean equals(Object o);
    @Override
    int hashCode();
    boolean isEmpty();
    long lastIndexOf(E element);
    long lastIndexOf(E element, long n);
    E remove(long index);
    boolean remove(long start, long end);
    boolean remove(E e);
    boolean removeAll(Collection<E> c);
    boolean removeAll(List64<E> list);
    boolean retainAll(Collection<E> c);
    boolean retainAll(List64<E> list);
    long size();
    AbstractListFile<E> toListFile(File file) throws FileNotFoundException, IOException;
    List<E> subSet(long start, long end);
    List64<E> subSet(File file, long start, long end) throws FileNotFoundException, IOException;
}