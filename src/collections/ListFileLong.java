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

/**
 * Creates a list of longs backed by a file. The max number of bytes in the file
 * is equal to Long.MAX_VALUE. This is the max bytes supported by
 * RandomAccessFile.
 *
 * This Basic version can be improved for performance through subclasses and
 * should not be generally used at all.
 *
 * @author john
 */
public class ListFileLong extends AbstractListFile<Long> {

    public ListFileLong(File file) {
        super(file);
        recordLength = Long.SIZE / 8;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ListFileLong) {
            ListFileLong list = (ListFileLong) o;
            if (size() != list.size()) {
                return false;
            }
            for (long i = 0; i < size(); i++) {
                if (get(i) != list.get(i)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final Long get(long index) {
        try {
            elementFile.seek(recordLength * index);
            return elementFile.readLong();
        } catch (IOException ex) {
            throw new ListFileException(ex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7 * (int) size();
        return hash;
    }

    @Override
    public Long set(long index, Long element) {
        Long r = null;
        try {
            elementFile.seek(recordLength * index);
            if (index < size()) {
                r = elementFile.readLong();
                elementFile.seek(recordLength * index);
            }
            elementFile.writeLong(element);
            return r;
        } catch (IOException ex) {
            throw new ListFileException(ex);
        }
    }

    @Override
    public void subList(long start, long end, List64<Long> list) {
        for (long i = start; i < end; i++) {
            list.add(get(i));
        }
    }
}
