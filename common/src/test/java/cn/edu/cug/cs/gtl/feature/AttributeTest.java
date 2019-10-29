package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Variant;


import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class AttributeTest {


    @Test
    public void copyFrom() {
        Variant v1 = new Variant(1);

        Identifier id1 = Identifier.create(0L);

        Attribute a1 = new Attribute("a1", v1, id1);
        Attribute a2 = new Attribute();
        a2.copyFrom(a1);
        assertTrue(a1.equals(a2));


    }

    @Test
    public void loadAndStore() {
        Variant v1 = new Variant(1);
        Attribute a1 = new Attribute("a1", v1, null);
        try {
            byte[] b1 = a1.storeToByteArray();
            assertTrue(b1.length == a1.getByteArraySize());
            Attribute a2 = new Attribute();
            a2.loadFromByteArray(b1);
            assertTrue(a1.equals(a2));
            byte[] b2 = a2.storeToByteArray();
            assertArrayEquals(b1, b2);
        } catch (IOException e) {
            e.printStackTrace();

        }


    }


}