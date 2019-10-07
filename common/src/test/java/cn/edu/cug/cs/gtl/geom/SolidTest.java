package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

/**
 * Created by hadoop on 18-9-15
 */
public class SolidTest {
    Solid solid = null;

    @Test
    public void isEmpty() {
    }

    @Test
    public void cloneTest() {
        solid = new Solid();

        Solid solid1 = new Solid();

        solid1 = (Solid) solid.clone();
    }

    @Test
    public void load() {
    }

    @Test
    public void store() {
    }

    @Test
    public void getByteArraySize() {
    }
}