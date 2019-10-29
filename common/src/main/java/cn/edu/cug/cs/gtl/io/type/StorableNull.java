package cn.edu.cug.cs.gtl.io.type;

import cn.edu.cug.cs.gtl.io.StorableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StorableNull implements StorableComparable<StorableNull> {

    private static final StorableNull THIS = new StorableNull();

    private StorableNull() {
    }                       // no public ctor

    /**
     * Returns the single instance of this class.
     */
    public static StorableNull get() {
        return THIS;
    }

    @Override
    public String toString() {
        return "(null)";
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public int compareTo(StorableNull other) {
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StorableNull;
    }


    @Override
    public Object clone() {
        return new StorableNull();
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        return true;
    }
}