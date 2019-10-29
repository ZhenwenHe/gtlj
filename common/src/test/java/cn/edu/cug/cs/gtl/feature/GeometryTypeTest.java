package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.jts.geom.Coordinate;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.junit.Test;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.IOException;

import static org.geotools.referencing.crs.DefaultGeographicCRS.WGS84;
import static org.junit.Assert.*;

public class GeometryTypeTest {


    @Test
    public void clone1() {
        Variant v1 = new Variant("geometrytype");
        GeometryType g1 = new GeometryType(v1.getClass());
        GeometryType g2;
        g2 = (GeometryType) g1.clone();
        assertTrue(g1.getName().equals("OBJECT"));
        assertTrue(g1.getType() == Variant.OBJECT);
        assertTrue(g1.getBinding() == Variant.class);
        assertTrue(g1.getCoordinateReferenceSystem() == WGS84);

        assertTrue(g2.getName().equals("OBJECT"));
        assertTrue(g2.getType() == Variant.OBJECT);
        assertTrue(g2.getBinding() == Variant.class);
        assertTrue(g2.getCoordinateReferenceSystem() == WGS84);


    }

    @Test
    public void loadAndStore() {
        Variant v1 = new Variant("geometrytype");
        GeometryType g1 = new GeometryType(v1.getClass(), null);
        GeometryType g2 = new GeometryType();

        assertTrue(g1.getName().equals("OBJECT"));
        assertTrue(g1.getType() == Variant.OBJECT);
        assertTrue(g1.getBinding() == Variant.class);
        assertTrue(g1.getCoordinateReferenceSystem() == null);
        try {
            byte[] b1 = g1.storeToByteArray();
            assertTrue(b1.length == g1.getByteArraySize());
            g2.loadFromByteArray(b1);
            assertTrue(g2.getName().equals("OBJECT"));
            assertTrue(g2.getType() == Variant.OBJECT);
            assertTrue(g2.getBinding() == Variant.class);
            assertTrue(g2.getCoordinateReferenceSystem() == null);

            byte[] b2 = g2.storeToByteArray();
            assertArrayEquals(b1, b2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}