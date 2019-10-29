package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class LineStringTest {
    private LineString line;
    private VectorSequence coordinates;

    @Test
    public void getVectorSequence() {
        double[] d = {1.1, 1.2, 1.3};
        coordinates = new PackedVectorSequence(d, 3);
        line = new LineString(coordinates);
        System.out.println(line.getVectorSequence());

    }

    @Test
    public void isEmpty() {
        line = new LineString();
        assertTrue(line.isEmpty());
    }

    @Test
    public void makeDimension() {
        line = new LineString();
        line.makeDimension(2);
        assertTrue(line.getDimension() == 2);
    }

    @Test
    public void load() {
        double[] d = {1.1, 1.2, 1.3};
        coordinates = new PackedVectorSequence(d, 3);
        line = new LineString(coordinates);
        try {
            byte[] bytes = line.storeToByteArray();
            long s = line.getByteArraySize();
            assertTrue(bytes.length == s);
            LineString l1 = new LineString();
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
        line = new LineString(coordinates);
        try {
            byte[] t = line.storeToByteArray();
            LineString l1 = (LineString) line.clone();
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
        line = new LineString(coordinates);
        long byteArraySize = line.getByteArraySize();
        System.out.println(byteArraySize);
        try {
            assertEquals(line.getByteArraySize(), line.storeToByteArray().length);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void cloneTet() {
        double[] d = {1.1, 1.2, 1.3};
        coordinates = new PackedVectorSequence(d, 3);
        line = new LinearRing(coordinates);
        System.out.println(line.getVectorSequence());
        LineString line1 = (LineString) line.clone();
        System.out.println(line1.getVectorSequence());
        assertEquals(line.getVectorSequence(), line1.getVectorSequence());
    }

    @Test
    public void getVertices() {
    }
}