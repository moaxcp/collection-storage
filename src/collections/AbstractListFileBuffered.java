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
import java.util.Iterator;

/**
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public abstract class AbstractListFileBuffered<E> extends AbstractListFile<E> {
    
    int maxBufferSize;
    E[] buffer;
    
    public AbstractListFileBuffered(File file) {
        super(file);
        maxBufferSize = 16 * 1024 * 1024 / recordLength; //16 MB
    }
    
//    final E bufferGet(int index) {
//        return (E) buffer[index];
//    }

    public final void setMaxBufferSize(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    final void loadBuffer(long start, long end) {
        buffer = (E[]) new Object[(int) (end - start)];
        for (long i = start; i < end; i++) {
            buffer[(int) (i - start)] = get(i);
        }
    }
    
    abstract void writeBuffer(long start);

    final void shiftSubList(long start, long end, long shift) {
        boolean trim = false;
        if (end + shift < size()) {
            trim = true;
        }

        long buffers = 1 + (end - start) / maxBufferSize;
        if (buffers == 1 && shift != 0) {
            loadBuffer(start, end);
            writeBuffer(start + shift);
        } else if (shift < 0) {
            for (long i = 0; i < buffers; i++) {
                long bufferStart = start + (i * maxBufferSize);
                long bufferEnd = (1 + i) * maxBufferSize > end ? end : (1 + i) * maxBufferSize;
                loadBuffer(bufferStart, bufferEnd);
                writeBuffer(bufferStart + shift);
            }
        } else if (shift > 0) {
            for (long i = buffers - 1; i >= 0; i--) {
                long bufferStart = start + (i * maxBufferSize);
                long bufferEnd = (1 + i) * maxBufferSize > end ? end : (1 + i) * maxBufferSize;
                loadBuffer(bufferStart, bufferEnd);
                writeBuffer(bufferStart + shift);
            }
        }

        buffer = null; //clear buffer for garbage collection
        
        if (trim) {
            try {
                elementFile.getChannel().truncate(end + shift);
            } catch (IOException ex) {
                throw new ListFileException(ex);
            }
        }
    }

    @Override
    public void add(long index, E element) {
            shiftSubList(index, size(), 1);
            set(index, element);
    }

    @Override
    public void addAll(ListFile<E> list) {
        long index = size(); //no add() to reduce file system access
        //TODO buffering to improver performance
        long listIndex = 0;
        long buffers = 1 + list.size() / maxBufferSize;
        for(long i = 0; i < buffers; i++) {
            long listStart = listIndex + ()
        }
        for (long i = 0; i < list.size(); i++) {
            set(index++, list.get(i));
        }
    }
    
    //TODO addAll(ListFileLongCache) does not need to buffer as cache is already
    //      in memory!

    @Override
    public void addAll(long index, Collection<E> c) {
        if(c.isEmpty()) {
            return;
        }
        shiftSubList(index, size(), c.size());
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()) {
            set(index++, iterator.next());
        }
    }

    @Override
    public void addAll(long index, ListFile<E> list) {
        if(list.isEmpty()) {
            return;
        }
        //TODO add buffering to improve performance
        
        shiftSubList(index, size(), list.size());
        for (long i = 0; i < list.size(); i++) {
            set(index++, list.get(i));
        }
    }
    
    //TODO addAll(long, ListFileLongCache) does not need to buffer as cache is already
    //      in memory!

    

    @Override
    public E remove(long index) {
        E r = get(index);
        shiftSubList(index + 1, size(), -1);
        return r;
    }

    //TODO find blocks and shift down each block until end of file
    @Override
    public boolean removeAll(Collection<E> c) {
        boolean r = true;
        Iterator<E> iterator = c.iterator();
        while (iterator.hasNext()) {
            r &= remove(iterator.next());
        }
        return r;
    }

    //TODO same as removeAll but use buffer
    @Override
    public boolean removeAll(ListFile<E> list) {
        boolean r = true;
        for (long i = 0; i < list.size(); i++) {
            r &= remove(list.get(i));
        }
        return r;
    }
    
    //TODO create for ListFileLongCache and remove buffering.

    //TODO find blocks and shift down until end of file
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

    //TODO find blocks and shift down until end of file
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
