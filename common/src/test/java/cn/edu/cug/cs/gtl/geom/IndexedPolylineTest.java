package cn.edu.cug.cs.gtl.geom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexedPolylineTest {
    IndexedPolyline indexedPolyline2 = null;
    IndexedPolyline indexedPolyline3 = null;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void storeAndLoadWithIndex() throws Exception {
        indexedPolyline2 = new IndexedPolyline(2);
        indexedPolyline3 = new IndexedPolyline(3);

        VectorSequence vs2 = indexedPolyline2.getVectorSequence();
        vs2.add(0, 0);
        vs2.add(1, 1);
        vs2.add(2, 1);
        vs2.add(3, 0);
        vs2.add(4, 0);
        indexedPolyline2.setStyle(3);
        indexedPolyline2.setWidth(4);

        //test indices
        int[] idx = new int[]{2, 4, 1, 2, 3, 4, 2, 1, 2};
        indexedPolyline2.setIndices(idx);

        byte[] vals = indexedPolyline2.storeToByteArray();
        IndexedPolyline ip2 = new IndexedPolyline(2);
        ip2.loadFromByteArray(vals);

        assertTrue(ip2.getWidth() == 4);
        assertTrue(ip2.getStyle() == 3);
        Vector v = ip2.getVectorSequence().getVector(2);
        assertTrue(v.getX() == 2);
        assertTrue(v.getY() == 1);
        assertTrue(ip2.getIndices()[3] == 2);
    }

    /**
     * @throws Exception
     */
    @Test
    public void storeAndLoadWithoutIndex() throws Exception {
        indexedPolyline2 = new IndexedPolyline(2);
        indexedPolyline3 = new IndexedPolyline(3);

        VectorSequence vs2 = indexedPolyline2.getVectorSequence();
        vs2.add(0, 0);
        vs2.add(1, 1);
        vs2.add(2, 1);
        vs2.add(3, 0);
        vs2.add(4, 0);
        indexedPolyline2.setStyle(3);
        indexedPolyline2.setWidth(4);
        assertTrue(indexedPolyline2.getIndices() == null);
        byte[] vals = indexedPolyline2.storeToByteArray();
        IndexedPolyline ip2 = new IndexedPolyline(2);
        ip2.loadFromByteArray(vals);
        assertTrue(ip2.getIndices() == null);
        assertTrue(ip2.getWidth() == 4);
        assertTrue(ip2.getStyle() == 3);
        Vector v = ip2.getVectorSequence().getVector(2);
        assertTrue(v.getX() == 2);
        assertTrue(v.getY() == 1);

    }

    @Test
    public void getByteArraySize() throws Exception {
        indexedPolyline2 = new IndexedPolyline(2);

        VectorSequence vs2 = indexedPolyline2.getVectorSequence();
        vs2.add(0, 0);
        vs2.add(1, 1);
        vs2.add(2, 1);
        vs2.add(3, 0);
        vs2.add(4, 0);
        indexedPolyline2.setStyle(3);
        indexedPolyline2.setWidth(4);

        //test indices
        int[] idx = new int[]{2, 4, 1, 2, 3, 4, 2, 1, 2};
        indexedPolyline2.setIndices(idx);
    }

    @Test
    public void cloneT() throws Exception {
        indexedPolyline2 = new IndexedPolyline(2);

        VectorSequence vs2 = indexedPolyline2.getVectorSequence();
        vs2.add(0, 0);
        vs2.add(1, 1);
        vs2.add(2, 1);
        vs2.add(3, 0);
        vs2.add(4, 0);
        indexedPolyline2.setStyle(3);
        indexedPolyline2.setWidth(4);

        //test indices
        int[] idx = new int[]{2, 4, 1, 2, 3, 4, 2, 1, 2};
        indexedPolyline2.setIndices(idx);

        IndexedPolyline ip2 = (IndexedPolyline) indexedPolyline2.clone();

        assertTrue(ip2.getWidth() == 4);
        assertTrue(ip2.getStyle() == 3);
        Vector v = ip2.getVectorSequence().getVector(2);
        assertTrue(v.getX() == 2);
        assertTrue(v.getY() == 1);
        assertTrue(ip2.getIndices()[3] == 2);
    }

}