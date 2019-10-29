package cn.edu.cug.cs.gtl.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayUtils {

    public static byte[] createByteArray(byte[] ba) {
        if (ba == null)
            return null;
        byte[] r = new byte[ba.length];
        System.arraycopy(ba, 0, r, 0, r.length);
        return r;
    }

    public static int compare(byte[] a, byte[] b) {
        if (a.length > b.length) return 1;
        if (a.length < b.length) return -1;

        for (int i = 0; i < a.length; ++i) {
            if (a[i] > b[i])
                return 1;
            else if (a[i] < b[i])
                return -1;
            else
                continue;
        }
        return 0;
    }

    public static byte[] createByteArray(int size, byte defaultValue) {
        byte[] r = new byte[size];
        for (int i = 0; i < size; i++)
            r[i] = defaultValue;
        return r;
    }

    public static byte[][] createByteArray(byte[][] baa) {
        byte[][] r = new byte[baa.length][];
        for (int i = 0; i < baa.length; i++) {
            r[i] = new byte[baa[i].length];
            System.arraycopy(baa[i], 0, r[i], 0, baa[i].length);
        }
        return r;
    }

    public static <E> Collection<E> iterableToCollection(Iterable<E> i) {
        if (i instanceof Collection) {
            return (Collection<E>) i;
        } else {
            ArrayList<E> al = new ArrayList<>();
            for (E e : i) {
                al.add(e);
            }
            return al;
        }
    }

    public static <E> List<E> iterableToList(Iterable<E> i) {
        if (i instanceof List) {
            return (List<E>) i;
        } else {
            ArrayList<E> al = new ArrayList<>();
            for (E e : i) {
                al.add(e);
            }
            return al;
        }
    }

    public static <E> List<E> collectionToList(Collection<E> i) {
        if (i instanceof List) {
            return (List<E>) i;
        } else {
            ArrayList<E> al = new ArrayList<>();
            for (E e : i) {
                al.add(e);
            }
            return al;
        }
    }
}
