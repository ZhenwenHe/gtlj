package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

public class MultiPointTest {

    @Test
    public void cloneTest() {
        Point p1 = new Point(1.1, 1.2, 1.3);
        Point p2 = new Point(2.1, 2.2, 2.3);
        Point[] point = {p1, p2};
        MultiPoint multiPoint = new MultiPoint(point);
        MultiPoint multiPoint1 = (MultiPoint) multiPoint.clone();
        System.out.println(multiPoint.getPoint(1));
        //System.out.println(multiPoint1.getPoint(1));
        //  assertTrue(multiPoint.getPoint(1)==multiPoint1.getPoint(1));

    }


}