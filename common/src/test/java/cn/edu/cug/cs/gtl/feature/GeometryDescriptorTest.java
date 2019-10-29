package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.Variant;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class GeometryDescriptorTest {

    @Test
    public void getGeometryTypeAndgetCoordinanteReferenceSystem() {
        Variant variant = new Variant("geomtryDescriptor");
        GeometryType geometryType = new GeometryType(variant.getClass());
        GeometryDescriptor g1 = new GeometryDescriptor(geometryType, "g1");
        assertTrue(g1.getGeometryType().equals(geometryType));
        assertTrue(g1.getGeometryType().getName().equals("OBJECT"));
        assertTrue(g1.getGeometryType().getType() == Variant.OBJECT);
        assertTrue(g1.getGeometryType().getBinding() == Variant.class);
        assertTrue(g1.getGeometryType().getCoordinateReferenceSystem() == DefaultGeographicCRS.WGS84);
        assertTrue(g1.getCoordinateReferenceSystem() == DefaultGeographicCRS.WGS84);
    }

    @Test
    public void loadAndStore() {//geomtryType中load store 有问题，这里肯定也会存在问题
        Variant variant = new Variant("geomtryDescriptor");
        GeometryType geometryType = new GeometryType(variant.getClass(), null);
        GeometryDescriptor g1 = new GeometryDescriptor(geometryType, "g1");
        GeometryDescriptor g2 = new GeometryDescriptor();
        try {
            byte[] b1 = g1.storeToByteArray();
            assertTrue(b1.length == g1.getByteArraySize());
            g2.loadFromByteArray(b1);
            byte[] b2 = g2.storeToByteArray();
            assertTrue(g1.getGeometryType().getName().equals(g2.getGeometryType().getName()));
            assertTrue(g1.getGeometryType().getType() == g2.getGeometryType().getType());
            assertTrue(g1.getGeometryType().getBinding() == g2.getGeometryType().getBinding());
            assertTrue(g2.getName().equals("g1"));
            assertTrue(g1.getGeometryType().getCoordinateReferenceSystem() == null);
            assertTrue(g2.getGeometryType().getCoordinateReferenceSystem() == null);
            assertArrayEquals(b1, b2);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void clone1() {
        Variant variant = new Variant("geomtryDescriptor");
        GeometryType geometryType = new GeometryType(variant.getClass());
        GeometryDescriptor g1 = new GeometryDescriptor(geometryType, "g1");
        GeometryDescriptor g2;
        g2 = (GeometryDescriptor) g1.clone();
        assertTrue(g1.getGeometryType().getName().equals(g2.getGeometryType().getName()));
        assertTrue(g1.getGeometryType().getType() == g2.getGeometryType().getType());
        assertTrue(g1.getGeometryType().getBinding() == g2.getGeometryType().getBinding());
        assertTrue(g2.getName().equals("g1"));
        assertTrue(g2.getCoordinateReferenceSystem() == g1.getCoordinateReferenceSystem());
        assertTrue(g2.getGeometryType().getCoordinateReferenceSystem() == DefaultGeographicCRS.WGS84);


    }
}