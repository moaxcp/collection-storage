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
package collections.sort;

import collections.List64;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author John Mercier <moaxcp at gmail.com>
 */
public class QuickSort implements SortAlgorithm {
    
    private long swaps;
    private long compares;
    private long time;

    @Override
    public void sort(List64 list) throws FileNotFoundException, IOException {
        
    }

    @Override
    public SortBenchmark getBenchmark() {
        return new SortBenchmark(compares, swaps, time);
    }
}
