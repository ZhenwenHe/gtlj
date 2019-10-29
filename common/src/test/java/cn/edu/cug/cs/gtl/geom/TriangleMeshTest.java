package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import static org.junit.Assert.*;

public class TriangleMeshTest {

    TriangleMesh TriangleMesh2 = null;


    @Test
    public void cloneT() throws Exception {
        TriangleMesh2 = new TriangleMesh(2);

        VectorSequence vs2 = TriangleMesh2.getVectorSequence();
        vs2.add(0, 0);
        vs2.add(1, 1);
        vs2.add(2, 1);
        vs2.add(3, 0);
        vs2.add(4, 0);

        TriangleMesh2.setMaterialID(4);

        //test indices
        int[] idx = new int[]{2, 4, 1, 2, 3, 4, 2, 1, 2};
        TriangleMesh2.setIndices(idx);

        TriangleMesh ip2 = (TriangleMesh) TriangleMesh2.clone();

        assertTrue(ip2.getMaterialID() == 4);
        Vector v = ip2.getVectorSequence().getVector(2);
        assertTrue(v.getX() == 2);
        assertTrue(v.getY() == 1);
        assertTrue(ip2.getIndices()[3] == 2);
    }
}