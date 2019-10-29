package cn.edu.cug.cs.gtl.geom;


import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PointTest {

    private Point point;
    private VectorSequence coordinates;

    @Test
    public void isEmptyTest() {
        point = new Point();
        assertFalse(point.isEmpty());
    }


    @Test
    public void makeDimensionTest() {
        Point point1 = new Point();
        point1.makeDimension(2);
        assertTrue(point1.getDimension() == 2);
    }


    @Test
    public void loadTest() {
        Point p = new Point(1, 1, 1);
        try {
            byte[] bytes = p.storeToByteArray();
            long s = p.getByteArraySize();
            assertTrue(bytes.length == s);
            Point p2 = new Point();
            p2.loadFromByteArray(bytes);
            assertTrue(p2.getDimension() == 3);
            assertTrue(p2.getY() == 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void storeTest() {
        Point p = new Point(1, 1, 1);
        try {
            byte[] t = p.storeToByteArray();
            Point p1 = (Point) p.clone();
            byte[] t2 = p1.storeToByteArray();
            assertEquals(t.length, t2.length);
            assertArrayEquals(t, t2);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getByteArraySizeTest() {
        point = new Point();
        long byteArraySize = point.getByteArraySize();
        try {
            byte[] bs = point.storeToByteArray();
            assertEquals(bs.length, byteArraySize);
        } catch (IOException e) {

        }
    }

    @Test
    public void cloneTest() {
        Point point1 = new Point(1, 2, 3);
        Point point2 = (Point) point1.clone();
        assertEquals(point1, point2);
        System.out.println(point1.getVectorSequence());
        System.out.println(point2.getVectorSequence());
        assertEquals(point1.getVectorSequence(), point2.getVectorSequence());
        assertTrue(point2.getX() == point1.getX());
        assertTrue(point2.getY() == 2);
        assertTrue(point2.getZ() == 3);

        Point point3 = new Point(3);
        VectorSequence coordinate1 = point3.getVectorSequence();
        coordinate1.add(4, 5, 6);
        Point point4 = (Point) point3.clone();
        System.out.println(point4.getDimension());
        Vector v = point4.getVectorSequence().getVector(1);
        assertTrue(v.getX() == 4);
        assertTrue(v.getY() == 5);
        assertTrue(v.getZ() == 6);

        double[] de = {1.2, 1.4, 1.5};
        VectorSequence vectorSequence = new PackedVectorSequence(de, 3);
        Point point5 = new Point(vectorSequence);
    }

}