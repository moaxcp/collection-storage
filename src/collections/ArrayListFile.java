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

/**
 *
 * @author john
 */
public abstract class ArrayListFile {
    
    RandomAccessFile elementFile;
    File file;
    long recordLength;
    
    public ArrayListFile(File file) throws FileNotFoundException, IOException {
        this.file = file;
    }
    
    public ArrayListFile(String path) throws FileNotFoundException, IOException {
        this(new File(path));
    }
    
    public final void open(String mode) throws FileNotFoundException, IOException {
        this.elementFile = new RandomAccessFile(file, mode);
    }
    
    public final void close() throws IOException {
        elementFile.close();
        elementFile = null;
    }
    
    public long getRecordLength() {
        return recordLength;
    }
    
    public final long size() {
        return file.length() / recordLength;
    }
}
