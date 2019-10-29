package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import static org.junit.Assert.*;

public class MultiLineStringTest {

    @Test
    public void cloneTest() {
        double[] d1 = {1.1, 1.2, 1.3};
        VectorSequence coordinate1 = new PackedVectorSequence(d1, 3);
        LineString line1 = new LineString(coordinate1);
        double[] d2 = {2.1, 2.2, 2.3};
        VectorSequence coordinate2 = new PackedVectorSequence(d2, 3);
        LineString line2 = new LineString(coordinate2);
        LineString[] lineStrings = {line1, line2};
        MultiLineString multiLineString = new MultiLineString(lineStrings);
        MultiLineString m2 = multiLineString.clone();
        System.out.println(m2.geometryType);
    }
}