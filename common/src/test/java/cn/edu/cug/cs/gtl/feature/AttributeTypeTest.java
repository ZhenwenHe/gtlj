package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.Variant;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class AttributeTypeTest {

    @Test
    public void constructorAndClone() {
        Variant variant = new Variant("v1");
        AttributeType a1 = new AttributeType(variant.getClass());//object 类型
        AttributeType a2 = new AttributeType(1, null);//boolean 类型
        assertTrue(a1.getName().equals("OBJECT"));
        assertTrue(a1.getType() == Variant.OBJECT);
        assertTrue(a1.getBinding() == Variant.class);
        assertTrue(a2.getName().equals("BOOLEAN"));
        assertTrue(a2.getType() == Variant.BOOLEAN);
        assertTrue(a2.getBinding() == Boolean.class);
        AttributeType a3, a4;
        a3 = (AttributeType) a1.clone();
        a4 = (AttributeType) a2.clone();
        assertTrue(a3.getName().equals("OBJECT"));
        assertTrue(a3.getType() == Variant.OBJECT);
        assertTrue(a3.getBinding() == Variant.class);
        assertTrue(a4.getName().equals("BOOLEAN"));
        assertTrue(a4.getType() == Variant.BOOLEAN);
        assertTrue(a4.getBinding() == Boolean.class);
    }

    @Test
    public void loadAndStore() {
        Variant variant = new Variant("attributetype");
        AttributeType a1 = new AttributeType(variant.getClass());//object 类型
        AttributeType a2 = new AttributeType();
        try {
            byte[] b1 = a1.storeToByteArray();
            assertTrue(b1.length == a1.getByteArraySize());
            a2.loadFromByteArray(b1);
            assertTrue(a2.getName().equals("OBJECT"));
            assertTrue(a2.getType() == Variant.OBJECT);
            assertTrue(a2.getBinding() == Variant.class);
            byte[] b2 = a2.storeToByteArray();
            assertArrayEquals(b1, b2);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}