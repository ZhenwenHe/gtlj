package cn.edu.cug.cs.gtl.common;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PropertyDescriptorImplTest {

    @Test
    public void getByteArraySize() {
        Variant variant = new Variant("propertydescriptor");
        PropertyType propertyType = new PropertyType(variant.getClass());
        PropertyDescriptorImpl propertyDescriptor = new PropertyDescriptorImpl("propertyDescriptor", propertyType, 0, 0, 1, 1, true);
        try {
            byte[] b1 = propertyDescriptor.storeToByteArray();
            assertTrue(b1.length == propertyDescriptor.getByteArraySize());
            /**
             * b1.lenth=199,propertyDescriptor.getByteArraySize()=191;
             * 这里有问题；
             * 但是不知道怎么修改；
             * 查看代码发现少加了 protected int decimal;protected int length;两个变量的长度，正好8字节；
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}