package cn.edu.cug.cs.gtl.common;


import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.*;

/**
 * Generic pair.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 */
public class Pair<K, V> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Key.
     */
    private K key;
    /**
     * Value.
     */
    private V value;

    public Pair() {
        key = null;
        value = null;
    }

    /**
     * Create an entry representing a mapping from the specified key to the
     * specified value.
     *
     * @param k Key (first element of the pair).
     * @param v Value (second element of the pair).
     */
    public Pair(K k, V v) {
        key = k;
        value = v;
    }

    /**
     * Create an entry representing the same mapping as the specified entry.
     *
     * @param entry Entry to copy.
     */
    public Pair(Pair<? extends K, ? extends V> entry) {
        this(entry.getKey(), entry.getValue());
    }

    /**
     * Get the key.
     *
     * @return the key (first element of the pair).
     */
    public K getKey() {
        return key;
    }

    /**
     * Get the value.
     *
     * @return the value (second element of the pair).
     */
    public V getValue() {
        return value;
    }

    /**
     * Get the first element of the pair.
     *
     * @return the first element of the pair.
     * @since 3.1
     */
    public K getFirst() {
        return key;
    }

    public K first() {
        return this.key;
    }

    /**
     * Get the second element of the pair.
     *
     * @return the second element of the pair.
     * @since 3.1
     */
    public V getSecond() {
        return value;
    }

    public V second() {
        return this.value;
    }

    /**
     * Compare the specified object with this entry for equality.
     *
     * @param o Object.
     * @return {@code true} if the given object is also a scene entry and
     * the two entries represent the same mapping.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        } else {
            Pair<?, ?> oP = (Pair<?, ?>) o;
            return (key == null ?
                    oP.key == null :
                    key.equals(oP.key)) &&
                    (value == null ?
                            oP.value == null :
                            value.equals(oP.value));
        }
    }

    /**
     * Compute a hash code.
     *
     * @return the hash code value.
     */
    @Override
    public int hashCode() {
        int result = key == null ? 0 : key.hashCode();

        final int h = value == null ? 0 : value.hashCode();
        result = 37 * result + h ^ (h >>> 16);

        return result;
    }

    @Override
    public String toString() {
        return "[" + getKey() + ", " + getValue() + "]";
    }


    public static <K, V> Pair<K, V> create(K k, V v) {
        return new Pair<K, V>(k, v);
    }

    public void setKey(K k) {
        this.key = k;
    }

    public void setValue(V v) {
        this.value = v;
    }

    @Override
    public Object clone() {
        return (Object) create(this.getKey(), this.getValue());
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Pair) {
            this.key = ((Pair<K, V>) i).key;
            this.value = ((Pair<K, V>) i).value;
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {

        try {
            int len = in.readInt();
            byte[] bs = new byte[len];
            in.readFully(bs, 0, len);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
            this.key = (K) ois.readObject();
            this.value = (V) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.key);
            oos.writeObject(this.value);
            byte[] bs = baos.toByteArray();
            out.writeInt(bs.length);
            out.write(bs, 0, bs.length);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.key);
            oos.writeObject(this.value);
            return baos.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }
}