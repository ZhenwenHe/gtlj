package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class IndexedPolygonTest {
    IndexedPolygon p2 = null;
    IndexedPolygon p3 = null;

    @Test
    public void clonetest() {
        p2 = new IndexedPolygon(2);

        //设置属性
        VectorSequence coordinate2 = p2.getCoordinates();
        coordinate2.add(0, 0);
        coordinate2.add(1, 1);
        coordinate2.add(2, 1);
        coordinate2.add(2, 2);
        coordinate2.add(3, 5);
        p2.setMaterialID(1);
        int[] idx = new int[]{2, 4, 1, 2, 3, 4, 2, 1, 2};
        p2.setIndices(idx);


        //clone
        IndexedPolygon newP2;
        newP2 = (IndexedPolygon) p2.clone();

        //验证
        assertTrue(newP2.getMaterialID() == 1);
        Vector v = newP2.getCoordinates().getVector(2);
        assertTrue(v.getX() == 2);
        assertTrue(v.getY() == 1);
        assertTrue(newP2.getIndices()[1] == 4);


    }

    @Test
    public void loadAndstore() throws IOException {
        p2 = new IndexedPolygon(2);

        //属性设置
        VectorSequence coordinate2 = p2.getCoordinates();
        coordinate2.add(0, 0);
        coordinate2.add(1, 1);
        coordinate2.add(2, 1);
        coordinate2.add(2, 2);
        coordinate2.add(3, 5);
        p2.setMaterialID(1);
        int[] idx = new int[]{2, 4, 1, 2, 3, 4, 2, 1, 2};
        p2.setIndices(idx);


        //////////load  and store
        byte[] a = p2.storeToByteArray();
        IndexedPolygon ip2 = new IndexedPolygon(2);
        ip2.loadFromByteArray(a);

        //验证
        assertTrue(ip2.getMaterialID() == 1);
        Vector v = ip2.getCoordinates().getVector(2);
        assertTrue(v.getX() == 2);
        assertTrue(v.getY() == 1);
        assertTrue(ip2.getIndices()[1] == 4);
    }


    @Test
    public void getByteArraySize() {
        p2 = new IndexedPolygon(2);

        //设置属性
        VectorSequence coordinate2 = p2.getCoordinates();
        coordinate2.add(0, 0);
        coordinate2.add(1, 1);
        coordinate2.add(2, 1);
        coordinate2.add(2, 2);
        coordinate2.add(3, 5);
        p2.setMaterialID(1);
        int[] idx = new int[]{2, 4, 1, 2, 3, 4, 2, 1, 2};
        p2.setIndices(idx);
//        try {
//            assertEquals(p2.getByteArraySize(), p2.storeToByteArray().length);
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
    }

    @Test
    public void testSimple() throws IOException {
        IndexedPolygon p2 = new IndexedPolygon(3);

        //属性设置
        VectorSequence coordinate2 = p2.getCoordinates();
        coordinate2.add(0, 0, 0);
        coordinate2.add(1, 1, 0);
        coordinate2.add(2, 1, 0);
        coordinate2.add(2, 2, 0);
        coordinate2.add(3, 5, 0);
        p2.setMaterialID(1);
        p2.setDefaultColor(Color.create(0, 0, 0, 0));


        //////////load  and store
        byte[] a = p2.storeToByteArray();
        IndexedPolygon ip2 = new IndexedPolygon(2);
        ip2.loadFromByteArray(a);

        //验证
        assertTrue(ip2.getMaterialID() == 1);
        Vector v = ip2.getCoordinates().getVector(2);
        assertTrue(v.getX() == 2);
        assertTrue(v.getY() == 1);
        assertTrue(ip2.getDefaultColor().r == 0);

    }
}