package cn.edu.cug.cs.gtl.common;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class VariantCollectionTest {

    @Test
    public void clone1() {
        VariantCollection vc1 = new VariantCollection(3);
        Variant v1 = new Variant("v1");
        Variant v2 = new Variant("v2");
        Variant v3 = new Variant("v3");
        vc1.add(0, v1);
        vc1.add(1, v2);
        vc1.add(2, v3);
        VariantCollection vc2;
        vc2 = (VariantCollection) vc1.clone();
        assertTrue(vc1.equals(vc2));
        assertTrue(vc2.get(0).equals(vc1.get(0)));

    }

    @Test
    public void loadAndStore() {
        VariantCollection vc1 = new VariantCollection(3);
        Variant v1 = new Variant("v1");
        Variant v2 = new Variant("v2");
        Variant v3 = new Variant("v3");
        vc1.add(0, v1);
        vc1.add(1, v2);
        vc1.add(2, v3);
        VariantCollection vc2 = new VariantCollection();
        try {
            byte[] b1 = vc1.storeToByteArray();
            assertTrue(b1.length == vc1.getByteArraySize());
            vc2.loadFromByteArray(b1);
            assertTrue(vc1.equals(vc2));
            byte[] b2 = vc2.storeToByteArray();
            assertArrayEquals(b1, b2);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}