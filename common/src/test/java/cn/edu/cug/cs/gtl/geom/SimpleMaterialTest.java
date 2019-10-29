package cn.edu.cug.cs.gtl.geom;


import org.junit.Test;


import java.io.IOException;

import static org.junit.Assert.*;

public class SimpleMaterialTest {
    private SimpleMaterial material;

    @Test
    public void getTextureIDTest() {
        material = new SimpleMaterial();
        assertEquals(material.getTextureID(), -1);
    }

    @Test
    public void setTextureIDTest() {
        material = new SimpleMaterial();
        material.setTextureID(1);
        assertEquals(material.getTextureID(), 1);
    }

    @Test
    public void getMaterialParameterTest() {
        MaterialParameter materialParameter = new MaterialParameter(1, 1, 1, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        material = new SimpleMaterial(1, "岩土", 1, materialParameter);
        assertEquals(material.getMaterialParameter(), materialParameter);
    }

    @Test
    public void setMaterialParameterTest() {
        MaterialParameter materialParameter = new MaterialParameter(1, 1, 1, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        material = new SimpleMaterial();
        material.setMaterialParameter(materialParameter);
        assertEquals(material.getMaterialParameter(), materialParameter);
    }

    @Test
    public void cloneTest() {
        MaterialParameter materialParameter = new MaterialParameter(1, 1, 1, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        material = new SimpleMaterial(1, "岩土", 1, materialParameter);
        SimpleMaterial material1 = (SimpleMaterial) material.clone();
        assertEquals(material.getTextureID(), material1.getTextureID());
        System.out.println(material1.getMaterialParameter());
        System.out.println(material.getMaterialParameter());
    }

    @Test
    public void loadTest() {
        MaterialParameter materialParameter = new MaterialParameter(1, 1, 1, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        material = new SimpleMaterial(1, "岩土", 1, materialParameter);
        try {
            byte[] bytes = material.storeToByteArray();
            SimpleMaterial material1 = new SimpleMaterial();
            material1.loadFromByteArray(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void store() {
        MaterialParameter materialParameter = new MaterialParameter(1, 1, 1, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        material = new SimpleMaterial(1, "岩土", 1, materialParameter);
        try {
            byte[] t = material.storeToByteArray();
            SimpleMaterial material1 = (SimpleMaterial) material.clone();
            byte[] t2 = material1.storeToByteArray();
            assertEquals(t.length, t2.length);
            assertArrayEquals(t, t2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getByteArraySize() {
        MaterialParameter materialParameter = new MaterialParameter(1, 1, 1, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        material = new SimpleMaterial(1, "岩土", 1, materialParameter);
        long byteArraySize = material.getByteArraySize();
        try {
            byte[] bs = material.storeToByteArray();
            assertEquals(bs.length, byteArraySize);
        } catch (IOException e) {

        }

    }
}