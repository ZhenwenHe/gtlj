package cn.edu.cug.cs.gtl.array;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayImplTest {

    @Test
    public void raw() {
    }

    @Test
    public void array() {
    }

    @Test
    public void index() {
    }

    @Test
    public void set() {
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void get() {
    }

    @Test
    public void scalar() {
    }

    @Test
    public void host() {
    }

    @Test
    public void row() {
    }

    @Test
    public void rows() {
    }

    @Test
    public void col() {
        double[] dat = {1, 2, 3, 4,
                5, 6, 7, 8,
                9, 10, 11, 12};
        Array a = Array.of(4, 3, dat);
        Array.print(a);
        Assert.assertEquals(6, a.get(1, 1), 0.0001);
        Visitor v = a.col(1);
        double[] b = {5, 6, 7, 8};
        Assert.assertArrayEquals(v.array().raw(), b, Double.MIN_NORMAL);
    }

    @Test
    public void cols() {
    }

    @Test
    public void slice() {
    }

    @Test
    public void slices() {
    }

    @Test
    public void dims() {
    }

    @Test
    public void reset() {
    }
}