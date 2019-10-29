package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GeometryTest {

    @Test
    public void create() {
    }

    @Test
    public void create1() {
        Point p = (Point) Geometry.create(Point.class, 3);
        System.out.println(p.getDimension());
        System.out.println(Geometry.getTypeName(Geometry.POINT));
        assertTrue(Geometry.getSimpleTypeName(Geometry.POINT).compareTo("Point") == 0);
    }

    @Test
    public void getTypeNameTest() {
        assertTrue(Geometry.getSimpleTypeName(Geometry.POLYGON).equals("Polygon"));
        assertTrue(Geometry.getSimpleTypeName(Geometry.POLYGON).equalsIgnoreCase("POLYGON"));
    }

    @Test
    public void loadTest() {

    }

    @Test
    public void store() {

    }

    @Test
    public void getByteArraySize() {
    }

    @Test
    public void cloneTest() {
    }
}