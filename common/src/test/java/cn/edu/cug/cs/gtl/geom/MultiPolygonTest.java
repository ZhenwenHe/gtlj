package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import static org.junit.Assert.*;

public class MultiPolygonTest {

    @Test
    public void cloneTest() {
        double[] d = {1.1, 1.2, 1.3, 1, 1, 1, 2, 2, 2, 3, 3, 3};
        VectorSequence coordinates = new PackedVectorSequence(d, 3);
        LinearRing linearRing = new LinearRing(coordinates);
        Polygon p = new Polygon(3);
        p.setExteriorRing(linearRing);
        double[] d1 = {2.1, 2.2, 2.3, 2, 3, 4, 3, 3, 3, 3, 3, 3};
        VectorSequence coordinates1 = new PackedVectorSequence(d, 3);
        LinearRing linearRing1 = new LinearRing(coordinates);
        Polygon p1 = new Polygon(3);
        p1.setExteriorRing(linearRing1);
        Polygon[] polygons = {p, p1};
        MultiPolygon multiPolygon = new MultiPolygon(polygons);
        MultiPolygon m2 = multiPolygon.clone();
        System.out.println(m2.geometryType);
        // System.out.println(m2.getGeometry(1));

    }

}