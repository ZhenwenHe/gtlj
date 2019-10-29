package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Coder;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class PolygonTest {

    @Test
    public void load() {
        double[] d = {1.1, 1.2, 1.3, 1, 1, 1, 2, 2, 2, 3, 3, 3};
        VectorSequence coordinates = new PackedVectorSequence(d, 3);
        LinearRing linearRing = new LinearRing(coordinates);
        Polygon p = new Polygon(3);
        p.setExteriorRing(linearRing);

        Coder<Polygon> coder = Coder.of(Polygon.class);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            coder.encode(p, byteArrayOutputStream);
            Polygon p2 = coder.decode(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            assertTrue(p.envelope.equals(p2.envelope));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void store() {
        double[] d = {1.1, 1.2, 1.3, 1, 1, 1, 2, 2, 2, 3, 3, 3};
        VectorSequence coordinates = new PackedVectorSequence(d, 3);
        LinearRing linearRing = new LinearRing(coordinates);
        Polygon p = new Polygon(3);
        p.setExteriorRing(linearRing);

        Coder<Polygon> coder = Coder.of(Polygon.class);
        try {
            Polygon p1 = (Polygon) p.clone();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            coder.encode(p1, byteArrayOutputStream);
            Polygon p2 = coder.decode(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            assertTrue(p.envelope.equals(p2.envelope));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getByteArraySizeTest() {
        double[] d = {1.1, 1.2, 1.3, 1, 1, 1, 2, 2, 2, 3, 3, 3};
        VectorSequence coordinates = new PackedVectorSequence(d, 3);
        LinearRing linearRing = new LinearRing(coordinates);
        Polygon p = new Polygon(3);
        p.setExteriorRing(linearRing);

        long byteArraySize = p.getByteArraySize();
        System.out.println(byteArraySize);
//        try {
//            assertEquals(p.getByteArraySize(),p.storeToByteArray().length);
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }

    }

    @Test
    public void cloneTest() {
        double[] d = {1.1, 1.2, 1.3, 1, 1, 1, 2, 2, 2, 3, 3, 3};
        VectorSequence coordinates = new PackedVectorSequence(d, 3);
        LinearRing linearRing = new LinearRing(coordinates);
        Polygon p = new Polygon(3);
        p.setExteriorRing(linearRing);

        long byteArraySize = p.getByteArraySize();
        System.out.println(byteArraySize);
        Polygon p1 = p.clone();
        try {
            byte[] t = p.storeToByteArray();
            byte[] t1 = p1.storeToByteArray();
            assertEquals(p.getByteArraySize(), p1.getByteArraySize());
            assertArrayEquals(t, t1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}