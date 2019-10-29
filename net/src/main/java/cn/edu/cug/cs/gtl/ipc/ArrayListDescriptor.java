package cn.edu.cug.cs.gtl.ipc;


import cn.edu.cug.cs.gtl.io.Storable;
import org.apache.hadoop.io.Writable;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayListDescriptor<T extends Storable>
        extends ArrayList<T> implements Storable, Writable {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public ArrayListDescriptor(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayListDescriptor() {
        super();
    }

    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public ArrayListDescriptor(Collection<? extends T> c) {
        super(c);
    }

    /**
     * Returns a shallow copy of this <tt>ArrayList</tt> instance.  (The
     * elements themselves are not copied.)
     *
     * @return a clone of this <tt>ArrayList</tt> instance
     */
    public Object clone() {
        try {
            ArrayListDescriptor<T> v = new ArrayListDescriptor<T>(size());
            for (T t : (List<T>) this)
                v.add((T) t.clone());
            return v;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void copyFrom(Object i) {
        List<T> v = (List<T>) (i);
        this.clear();
        try {
            for (T t : v)
                this.add((T) t.clone());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        try {
            int s = in.readInt();
            clear();
            int len = in.readInt();
            byte[] bs = new byte[len];
            in.readFully(bs, 0, len);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
            for (int i = 0; i < s; ++i) {
                T a = (T) ois.readObject();
                add(a);
            }
            ois.close();
        } catch (IOException | NullPointerException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        try {
            int s = this.size();
            out.writeInt(s);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            for (int i = 0; i < s; ++i) {
                oos.writeObject(get(i));
            }
            byte[] bs = baos.toByteArray();
            out.writeInt(bs.length);
            out.write(bs, 0, bs.length);
            oos.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        try {
            int s = this.size();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            for (int i = 0; i < s; ++i) {
                oos.writeObject(get(i));
            }
            return baos.size() + 4;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayListDescriptor read(DataInput in) throws IOException {
        ArrayListDescriptor t = new ArrayListDescriptor();
        t.readFields(in);
        return t;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        store(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        load(in);
    }
}
