package cn.edu.cug.cs.gtl.common;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PropertyTypeTest {
    @Test
    public void getNameAndgetBinding() throws Exception {

        byte[] bv = new byte[10];
        System.out.println(bv.getClass());

        char[] cv = new char[10];
        System.out.println(cv.getClass());


        double[] dv = new double[10];
        System.out.println(dv.getClass());


        float[] fv = new float[10];
        System.out.println(fv.getClass());

        double[] dv2 = new double[12];
        System.out.println(dv.getClass().equals(dv2.getClass()));
        String s = "abc";
        Variant v1 = new Variant("variant");

        PropertyType p1 = new PropertyType(bv.getClass());
        PropertyType p2 = new PropertyType(cv.getClass());
        PropertyType p3 = new PropertyType(dv.getClass());
        PropertyType p4 = new PropertyType(fv.getClass());
        PropertyType p5 = new PropertyType(s.getClass());
        PropertyType p6 = new PropertyType(v1.getClass());
        /////////////////////////getName()
        assertTrue(p1.getName().equals("BYTES"));
        assertTrue(p2.getName().equals("CHARACTERS"));
        assertTrue(p3.getName().equals("DOUBLES"));
        assertTrue(p4.getName().equals("FLOATS"));
        assertTrue(p5.getName().equals("STRING"));
        assertTrue(p6.getName() == "OBJECT");
        ///////////////////////getBinding()
        assertTrue(p1.getBinding() == bv.getClass());
        assertTrue(p2.getBinding() == cv.getClass());
        assertTrue(p3.getBinding() == dv.getClass());
        assertTrue(p4.getBinding() == fv.getClass());
        assertTrue(p5.getBinding() == String.class);
        assertTrue(p6.getBinding() == v1.getClass());
        /////////////reset()
        p5.reset(v1.getClass());
        assertTrue(p5.getName() == "OBJECT");
        assertTrue(p5.getBinding() == v1.getClass());
        p6.reset(s.getClass());
        assertTrue(p6.getBinding() == String.class);
        assertTrue(p6.getBinding() == String.class);


    }

    @Test
    public void clone1() throws Exception {
        Variant v1 = new Variant("variant");
        PropertyType p1 = new PropertyType(v1.getClass());
        PropertyType p2 = new PropertyType();
        p2 = (PropertyType) p1.clone();
        assertTrue(p1.getName().equals(p2.getName()));
        assertTrue(p2.getType() == p1.getType());
        assertTrue(p1.getBinding() == p2.getBinding());
    }


    @Test
    public void loadAndStore() throws Exception {
        Variant variant = new Variant("propertytype");
        PropertyType p1 = new PropertyType(variant.getClass());//用户自定义类型 Variant
        PropertyType p2 = new PropertyType();
        try {
            byte[] t1 = p1.storeToByteArray();
            assertTrue(t1.length == p1.getByteArraySize());
            p2.loadFromByteArray(t1);
            assertTrue(p2.getName().equals("OBJECT"));
            byte[] t2 = p2.storeToByteArray();
            assertArrayEquals(t1, t2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}