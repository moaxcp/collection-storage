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
import java.io.IOException;

/**
 * does not buffer index operations because may read large chunk when the operation
 * should exit.
 * @author John Mercier <moaxcp at gmail.com>
 */
public abstract class AbstractBufferedListFile<E> extends AbstractListFile<E> {
    
    int maxBufferSize;
    E[] buffer;
    
    public AbstractBufferedListFile(File file) {
        super(file);
        maxBufferSize = 16 * 1024 * 1024 / recordLength; //16 MB
    }

    public final void setMaxBufferSize(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    private void loadBuffer(long start, long end) {
        buffer = (E[]) new Object[(int) (end - start)];
        for (long i = start; i < end; i++) {
            buffer[(int) (i - start)] = get(i);
        }
    }
    
    abstract void writeBuffer(long start);

    @Override
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
}
