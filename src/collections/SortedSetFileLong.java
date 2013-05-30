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
 *
 * @author john
 */
public class SortedSetFileLong extends BufferedListFileLong {
    
    public SortedSetFileLong(File file) throws FileNotFoundException, IOException {
        super(file);
    }
    
    public long indexOf(long number) {
        long low = 0;
        long high = size() - 1;
        long mid;
        while (low <= high) {
            mid = (low + high) >>> 1;
            long midVal = get(mid);
            if (midVal == number) {
                return mid;
            } else if (number < midVal) {
                high = mid - 1;
            } else if (number > midVal) {
                low = mid + 1;
            }
        }


        return -1;
    }

    public long first() {
        return get(0);
    }

    public long last() {
        return get(size() - 1);
    }

    public long floor(long number) {
        long low = 0;
        long high = size() - 1;
        long mid = -1;
        while (low <= high) {
            mid = (low + high) >>> 1;
            long midVal = get(mid);
            if (midVal == number) {
                return midVal;
            } else if (number < midVal) {
                high = mid - 1;
            } else if (number > midVal) {
                low = mid + 1;
            }
        }

        long midVal = get(mid);
        if (midVal == number) {
            return midVal;
        } else if (number < midVal) {
            return get(mid - 1);
        } else if (number > midVal) {
            return get(mid + 1);
        }
        
        return -1;
    }
}
