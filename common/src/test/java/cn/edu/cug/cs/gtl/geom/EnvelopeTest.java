package cn.edu.cug.cs.gtl.geom;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnvelopeTest {
    @Test
    public void load() throws Exception {
        String s = "Envelope{low=[-132.268057, 18.117735], high=[-64.768336, 56.407375]}";
        Envelope e = Envelope.fromString(s);
        System.out.println(e.toString());
    }

    @Test
    public void store() throws Exception {
    }

    @Test
    public void getByteArraySize() throws Exception {
    }

    @Test
    public void flap() throws Exception {
    }

    @Test
    public void makeInfinite() throws Exception {
    }

    @Test
    public void makeInfinite1() throws Exception {
    }

    @Test
    public void makeDimension() throws Exception {
    }

    @Test
    public void getDimension() throws Exception {
    }

    @Test
    public void getLowCoordinates() throws Exception {
    }

    @Test
    public void getHighCoordinates() throws Exception {
    }

    @Test
    public void getLowVector() throws Exception {
    }

    @Test
    public void getHighVector() throws Exception {
    }

    @Test
    public void getLowOrdinate() throws Exception {
    }

    @Test
    public void getHighOrdinate() throws Exception {
    }

    @Test
    public void setLowOrdinate() throws Exception {
    }

    @Test
    public void setHighOrdinate() throws Exception {
    }

    @Test
    public void reset() throws Exception {
    }

    @Test
    public void equals() throws Exception {
    }


    @Test
    public void copyFrom() throws Exception {
    }

    @Test
    public void reset1() throws Exception {
    }

    @Test
    public void intersects() throws Exception {
    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void touches() throws Exception {
    }

    @Test
    public void contains1() throws Exception {
    }

    @Test
    public void touches1() throws Exception {
    }

    @Test
    public void getIntersectingEnvelope() throws Exception {
    }

    @Test
    public void getIntersectingArea() throws Exception {
    }

    @Test
    public void getMargin() throws Exception {
    }

    @Test
    public void combine() throws Exception {
    }

    @Test
    public void combine1() throws Exception {
    }

    @Test
    public void getCombinedEnvelope() throws Exception {
    }

    @Test
    public void split() throws Exception {
        Envelope e = Envelope.create(0, 0, 10, 10);
        double ordinate = 5;
        Envelope e2d1 = Envelope.create(0, 0, 5, 10);
        Envelope e2d2 = Envelope.create(5, 0, 10, 10);
        Envelope[] e2ds = e.split(ordinate, 0);
        assertEquals(e2d1, e2ds[0]);
        assertEquals(e2d2, e2ds[1]);

        e2d1 = Envelope.create(0, 0, 10, 5);
        e2d2 = Envelope.create(0, 5, 10, 10);
        e2ds = e.split(ordinate, 1);
        assertEquals(e2d1, e2ds[0]);
        assertEquals(e2d2, e2ds[1]);

        e = Envelope.create(0, 0, 0, 10, 10, 10);
        Envelope e3d1 = Envelope.create(0, 0, 0, 5, 10, 10);
        Envelope e3d2 = Envelope.create(5, 0, 0, 10, 10, 10);
        Envelope[] e3ds = e.split(ordinate, 0);
        assertEquals(e3d1, e3ds[0]);
        assertEquals(e3d2, e3ds[1]);

        e3d1 = Envelope.create(0, 0, 0, 10, 5, 10);
        e3d2 = Envelope.create(0, 5, 0, 10, 10, 10);
        e3ds = e.split(ordinate, 1);
        assertEquals(e3d1, e3ds[0]);
        assertEquals(e3d2, e3ds[1]);

        e3d1 = Envelope.create(0, 0, 0, 10, 10, 5);
        e3d2 = Envelope.create(0, 0, 5, 10, 10, 10);
        e3ds = e.split(ordinate, 2);
        assertEquals(e3d1, e3ds[0]);
        assertEquals(e3d2, e3ds[1]);
    }

    @Test
    public void split1() throws Exception {
    }

    @Test
    public void create() throws Exception {
    }

    @Test
    public void create1() throws Exception {
    }

    @Test
    public void create2() throws Exception {
    }

    @Test
    public void create3() throws Exception {
    }

    @Test
    public void create4() throws Exception {
    }

    @Test
    public void randomEnvelope() {
        Envelope e2d = new Envelope(0, 100, 0, 100);
        Envelope e2d1 = Envelope.randomEnvelope(e2d, 0.02);
        Envelope e2d2 = Envelope.randomEnvelope(e2d, 0.03);

    }
}