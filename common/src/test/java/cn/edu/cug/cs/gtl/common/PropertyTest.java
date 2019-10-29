package cn.edu.cug.cs.gtl.common;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PropertyTest {


    @Test
    public void copyFrom() {
        Variant v1 = new Variant(1);
        Property p1 = new Property("p1", v1);
        Property p2 = new Property();
        p2.copyFrom(p1);
        assertTrue(p1.equals(p2));

    }

    @Test
    public void loadAndStore() {
        Property p1 = new Property();
        Property p2 = new Property();
        p1.setName("p1");
        try {
            byte[] bytes = p1.storeToByteArray();
            assertTrue(bytes.length == p1.getByteArraySize());
            p2.loadFromByteArray(bytes);
            assertTrue(p1.equals(p2));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void getByteArraySize() {
        Property p1 = new Property();
        p1.setName("p1");
        String string = p1.toString();
        assertTrue(string.length() == p1.getByteArraySize());
    }
}