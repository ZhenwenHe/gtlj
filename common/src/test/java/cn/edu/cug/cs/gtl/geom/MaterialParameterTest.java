package cn.edu.cug.cs.gtl.geom;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MaterialParameterTest {

    @Test
    public void loadAndStore() {
        MaterialParameter materialParameter = new MaterialParameter(1, 1, 1, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        try {
            byte[] bs = materialParameter.storeToByteArray();
            //MaterialParameter mp = new MaterialParameter();
            //mp.loadFromByteArray(bs);
            assertEquals(bs.length, materialParameter.getByteArraySize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}