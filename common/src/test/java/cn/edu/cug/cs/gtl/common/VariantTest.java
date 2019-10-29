package cn.edu.cug.cs.gtl.common;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class VariantTest {

    @Test
    public void constructor() {
        int iV = 1;
        int[] iV1 = new int[2];
        boolean bV = true;
        boolean[] bV1 = new boolean[2];
        String sV = "variant";
        String[] sV1 = new String[2];
        for (int i = 0; i < 2; ++i) {
            iV1[i] = i;
            bV1[i] = true;
            sV1[i] = sV + String.valueOf(i);
        }

        Variant v1 = new Variant(iV);
        Variant v2 = new Variant(iV1);
        Variant v3 = new Variant(bV);
        Variant v4 = new Variant(bV1);
        Variant v5 = new Variant(sV);
        Variant v6 = new Variant(sV1);

        assertTrue(v1.getType() == 5);
        assertTrue(v2.getType() == 14);
        assertTrue(v3.getType() == 1);
        assertTrue(v4.getType() == 12);
        assertTrue(v5.getType() == 9);
        assertTrue(v6.getType() == 18);
        assertTrue(v1.isNumber() == true);
        assertTrue(v1.intValue() == iV);
        assertTrue(v2.isNumber() == false);
        assertTrue(v3.booleanValue() == true);
        assertTrue(v5.strValue().equals(sV));
        assertTrue(v6.isArray() == true);


    }

    @Test
    public void compareTo() {
        double iV = 1;
        double iV3 = 2;
        double[] iV1 = new double[2];
        boolean bV = true;
        boolean[] bV1 = new boolean[2];

        for (int i = 0; i < 2; ++i) {
            iV1[i] = i;
            bV1[i] = true;
        }

        Variant v1 = new Variant(iV);
        Variant v2 = new Variant(iV1);
        Variant v3 = new Variant(bV);
        Variant v4 = new Variant(bV1);
        Variant v5 = new Variant(iV3);

        assertTrue(v5.compareTo(v1) == 1);
        assertTrue(v1.compareTo(v2) == v1.toString().compareTo(v2.toString()));
        assertTrue(v1.compareTo(v3) == v1.toString().compareTo(v3.toString()));
        assertTrue(v4.compareTo(v2) == v4.toString().compareTo(v2.toString()));
    }


    @Test
    public void loadAndStore() {
        int iV = 1;
        int[] iV1 = new int[2];
        boolean bV = true;
        boolean[] bV1 = new boolean[2];
        String sV = "variant";
        String[] sV1 = new String[2];
        char cV = 'a';
        for (int i = 0; i < 2; ++i) {
            iV1[i] = i;
            bV1[i] = true;
            sV1[i] = sV + String.valueOf(i);
        }

        Variant v1 = new Variant(iV);
        Variant v2 = new Variant(iV1);
        Variant v3 = new Variant(bV);
        Variant v4 = new Variant(bV1);
        Variant v5 = new Variant(sV);
        Variant v6 = new Variant(sV1);
        Variant v7 = new Variant();
        Variant v8 = new Variant();
        Variant v9 = new Variant(cV);
        v7.copyFrom(v1);
        assertTrue(v7.equals(v1));
        assertTrue(v7.getType() == 5);
        assertTrue(v7.intValue() == 1);
        try {
            byte[] t1 = v6.storeToByteArray();

            assertTrue(t1.length == v6.getByteArraySize());
            v8.loadFromByteArray(t1);
            assertTrue(v6.equals(v8));
            byte[] t2 = v8.storeToByteArray();
            assertArrayEquals(t1, t2);

            byte[] t3 = v1.storeToByteArray();
            assertTrue(t3.length == v1.getByteArraySize());
            byte[] t4 = v2.storeToByteArray();
            assertTrue(t4.length == v2.getByteArraySize());
            byte[] t5 = v3.storeToByteArray();
            assertTrue(t5.length == v3.getByteArraySize());
            byte[] t6 = v4.storeToByteArray();
            assertTrue(t6.length == v4.getByteArraySize());
            byte[] t7 = v5.storeToByteArray();
            assertTrue(t7.length == v5.getByteArraySize());
            byte[] t8 = v9.storeToByteArray();
            assertTrue(t8.length == v9.getByteArraySize());
/**
 * 以上测试表明  泛型变量Variant 中 string  和strings 两个类型的 getArraySize存在问题； 与实际相差string。lenth的长度，
 * 修改Variant的getByteArraySize中String与Strings部分代码后，测试通过
 */

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}