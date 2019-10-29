package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.Variant;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class AttributeDescriptorTest {

    @Test
    public void getDefaultValueAndgetAttributeType() {
        Variant variant = new Variant("attributetypedescriptor");
        AttributeType attributeType = new AttributeType(Variant.OBJECT, variant.getClass());
        AttributeDescriptor a1 = new AttributeDescriptor(Variant.OBJECT, variant.getClass(), "a1");
        AttributeDescriptor a2 = new AttributeDescriptor(attributeType, "a2", 1, 1, true, "a2");
        assertTrue(a1.getDefaultValue() == null);
        assertTrue(a2.getDefaultValue() == "a2");
        assertTrue(a1.getAttributeType().getType() == Variant.OBJECT);
        assertTrue(a1.getAttributeType().getName().equals("OBJECT"));
        assertTrue(a1.getAttributeType().getBinding() == Variant.class);
        assertTrue(a2.getAttributeType().equals(attributeType));
    }


    @Test
    public void clone1() {
        Variant variant = new Variant("attributetypedescriptor");
        AttributeDescriptor a1 = new AttributeDescriptor(Variant.OBJECT, variant.getClass(), "a1");
        AttributeDescriptor a2;
        a2 = (AttributeDescriptor) a1.clone();
        assertTrue(a2.getName().equals("a1"));
        assertTrue(a2.getAttributeType().getName().equals("OBJECT"));
        assertTrue(a2.getAttributeType().getType() == Variant.OBJECT);
        assertTrue(a2.getAttributeType().getBinding() == variant.getClass());
        assertTrue(a2.getDefaultValue() == a1.getDefaultValue());


    }

    @Test
    public void loadAndStore() {
        Variant variant = new Variant("attributetypedescriptor");
        AttributeType attributeType = new AttributeType(Variant.OBJECT, variant.getClass());
        AttributeDescriptor a1 = new AttributeDescriptor(attributeType, "a1", 1, 1, true, "a1");
        AttributeDescriptor a2 = new AttributeDescriptor();
        try {
            byte[] b1 = a1.storeToByteArray();
            a2.loadFromByteArray(b1);
            byte[] b2 = a2.storeToByteArray();
            assertArrayEquals(b1, b2);


            assertTrue(b1.length == a1.getByteArraySize());
            /**
             * b1.lenth = 236 a1.getByteArraySize()=228 ,查看代码，没有找到问题所在；
             * 测试父类propertyDescriptorImpl,发现其getByteArraySize()较真实值少8，错误定位到父类中对应函数；
             *
             */
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void store() {
    }

    @Test
    public void getByteArraySize() {
    }
}