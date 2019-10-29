package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class LinearRingTest {

    private LinearRing linearRing;
    private VectorSequence coordinates;

    @Test
    public void getVectorSequence() {
        double[] d = {1.1, 1.2, 1.3};
        coordinates = new PackedVectorSequence(d, 3);
        linearRing = new LinearRing(coordinates);
        System.out.println(linearRing.getVectorSequence());
    }

    @Test
    public void load() {
        double[] d = {1.1, 1.2, 1.3};
        coordinates = new PackedVectorSequence(d, 3);
        linearRing = new LinearRing(coordinates);
        try {
            byte[] bytes = linearRing.storeToByteArray();
            long s = linearRing.getByteArraySize();
            assertTrue(bytes.length == s);
            LinearRing l1 = new LinearRing();
            l1.loadFromByteArray(bytes);
            assertTrue(l1.getDimension() == 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void store() {
        double[] d = {1.1, 1.2, 1.3};
        coordinates = new PackedVectorSequence(d, 3);
        linearRing = new LinearRing(coordinates);
        try {
            byte[] t = linearRing.storeToByteArray();
            LinearRing l1 = (LinearRing) linearRing.clone();
            byte[] t2 = l1.storeToByteArray();
            assertEquals(t.length, t2.length);
            assertArrayEquals(t, t2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getByteArraySize() {
        double[] d = {1.1, 1.2, 1.3};
        coordinates = new PackedVectorSequence(d, 3);
        linearRing = new LinearRing(coordinates);
        long byteArraySize = linearRing.getByteArraySize();
        System.out.println(linearRing.getByteArraySize());
        try {
            byte[] bs = linearRing.storeToByteArray();
            assertEquals(bs.length, byteArraySize);
        } catch (IOException e) {

        }

    }

    @Test
    public void cloneTest() {
        double[] d = {-1.1, 1.2, 1.3};
        coordinates = new PackedVectorSequence(d, 3);
        linearRing = new LinearRing(coordinates);
        LinearRing linearRing1 = (LinearRing) linearRing.clone();
        assertEquals(linearRing, linearRing1);
        System.out.println(linearRing.getVectorSequence());
        System.out.println(linearRing1.getVectorSequence());
    }
}