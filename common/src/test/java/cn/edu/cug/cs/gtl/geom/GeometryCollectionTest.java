package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GeometryCollectionTest {
    private GeometryCollection geometryCollection;

    @Test
    public void clear() {
        ArrayList<Geometry> geometryArrayList = new ArrayList<>();
        Point p = (Point) Geometry.create(Point.class, 3);
        Point p1 = (Point) Geometry.create(Point.class, 3);
        geometryArrayList.add(p);
        geometryArrayList.add(p1);
        geometryCollection = new GeometryCollection(geometryArrayList);
        geometryCollection.clear();
        System.out.println(geometryCollection.geometries);
        assertTrue(geometryCollection.geometries.size() == 0);
    }

    @Test
    public void loadTest() {
        ArrayList<Geometry> geometryArrayList = new ArrayList<>();
        Point p = (Point) Geometry.create(Point.class, 3);
        Point p1 = (Point) Geometry.create(Point.class, 3);
        geometryArrayList.add(p);
        geometryArrayList.add(p1);
        geometryCollection = new GeometryCollection(geometryArrayList);
        System.out.println(geometryCollection.getGeometry(1));
        try {
            byte[] bytes = geometryCollection.storeToByteArray();
            long s = geometryCollection.getByteArraySize();
            assertTrue(bytes.length == s);
            GeometryCollection g1 = new GeometryCollection();
            g1.loadFromByteArray(bytes);
            assertTrue(g1.getDimension() == 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void storeTest() {
        ArrayList<Geometry> geometryArrayList = new ArrayList<>();
        Point p = (Point) Geometry.create(Point.class, 3);
        Point p1 = (Point) Geometry.create(Point.class, 3);
        geometryArrayList.add(p);
        geometryArrayList.add(p1);
        geometryCollection = new GeometryCollection(geometryArrayList);
//        try{
//            byte [] t = geometryCollection.storeToByteArray();
//            GeometryCollection g1 = (GeometryCollection) geometryCollection.clone();
//            byte [] t2 = g1.storeToByteArray();
//            System.out.println(t.length);
//            System.out.println(t2.length);
//            assertEquals(t.length,t2.length);
//            assertArrayEquals(t,t2);
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }

    }

    @Test
    public void getByteArraySize() {
        ArrayList<Geometry> geometryArrayList = new ArrayList<>();
        Point p = (Point) Geometry.create(Point.class, 3);
        Point p1 = (Point) Geometry.create(Point.class, 3);
        geometryArrayList.add(p);
        geometryArrayList.add(p1);
        geometryCollection = new GeometryCollection(geometryArrayList);
        try {
            byte[] t = geometryCollection.storeToByteArray();
            assertEquals(t.length, geometryCollection.getByteArraySize());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void cloneTest() {
        ArrayList<Geometry> geometryArrayList = new ArrayList<>();
        Point p = (Point) Geometry.create(Point.class, 3);
        Point p1 = (Point) Geometry.create(Point.class, 3);
        geometryArrayList.add(p);
        geometryArrayList.add(p1);
        geometryCollection = new GeometryCollection(geometryArrayList);
        GeometryCollection geometryCollection1 = geometryCollection.clone();
        assertEquals(geometryCollection.geometryType, geometryCollection1.geometryType);
        assertEquals(geometryCollection.size(), geometryCollection1.size());
    }
}