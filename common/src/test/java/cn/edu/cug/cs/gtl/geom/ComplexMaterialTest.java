package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ComplexMaterialTest {
    private ComplexMaterial material;

    @Test
    public void getMaterialItemsTest() {
        List<MaterialItem> materialList = new ArrayList<>();
        MaterialItem item = new MaterialItem(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        materialList.add(item);
        material = new ComplexMaterial(1, "岩土", materialList);
        assertEquals(materialList, material.getMaterialItems());
    }

    @Test
    public void setMaterialItemsTest() {
        List<MaterialItem> materialList = new ArrayList<>();
        MaterialItem item = new MaterialItem(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        materialList.add(item);
        material = new ComplexMaterial();
        material.setMaterialItems(materialList);
        assertEquals(material.getMaterialItems(), materialList);
    }

    @Test
    public void cloneTest() {
        List<MaterialItem> materialList = new ArrayList<>();
        MaterialItem item = new MaterialItem(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        materialList.add(item);
        material = new ComplexMaterial(1, "岩土", materialList);

    }

    @Test
    public void loadTest() {
        List<MaterialItem> materialList = new ArrayList<>();
        MaterialItem item = new MaterialItem(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        materialList.add(item);
        material = new ComplexMaterial(1, "岩土", materialList);
        try {
            byte[] bytes = material.storeToByteArray();
            long s = material.getByteArraySize();
            assertTrue(bytes.length == s);
            ComplexMaterial material1 = new ComplexMaterial();
            material1.loadFromByteArray(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void store() {
        List<MaterialItem> materialList = new ArrayList<>();
        MaterialItem item = new MaterialItem(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        materialList.add(item);
        material = new ComplexMaterial(1, "岩土", materialList);
        try {
            byte[] t = material.storeToByteArray();
            ComplexMaterial material1 = (ComplexMaterial) material.clone();
            byte[] t2 = material1.storeToByteArray();
            assertEquals(t.length, t2.length);
            assertArrayEquals(t, t2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getByteArraySize() {
        List<MaterialItem> materialList = new ArrayList<>();
        MaterialItem item = new MaterialItem(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        materialList.add(item);
        material = new ComplexMaterial(1, "岩土", materialList);
        try {
            byte[] t = material.storeToByteArray();
            assertEquals(t.length, material.getByteArraySize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}