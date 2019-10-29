package cn.edu.cug.cs.gtl.series.common.sax;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static cn.edu.cug.cs.gtl.series.common.sax.Utils.*;

public class UtilsTest {
    public static NormalAlphabet normalA = new NormalAlphabet();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void seriesToString() {
        final double[] series = {-1., -2., -1., 0., 2., 1., 1., 0.};

        try {
            char[] cc = Utils.seriesToString(series, 3, normalA.getCuts(3), 0.001);
            assertEquals(String.valueOf(cc), "acc");
            assertTrue(
                    String.valueOf(Utils.seriesToString(series, 8, normalA.getCuts(3), 0.001)).equals("aaabcccb"));
        } catch (SAXException e) {
            fail("exception shall not be thrown!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}