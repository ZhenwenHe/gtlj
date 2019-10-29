package cn.edu.cug.cs.gtl.series;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.series.common.Series;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class SeriesTest {
    static String testFileName = Config.getTestOutputDirectory()
            + File.separator + "test.series";

    @Test
    public void copy() {

        double[] ys = {50, 10, 20, 30, 40, 70, 90, 10, 30, 40};
        Series s1 = Series.of(ys);
        try {
            FileOutputStream f = new FileOutputStream(testFileName);
            s1.write(f);
            f.close();
            FileInputStream f2 = new FileInputStream(testFileName);
            Series s3 = Series.of(f2);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void load() {

        double[] ys = {50, 10, 20, 30, 40, 70, 90, 10, 30, 40};

        Series s1 = Series.of(ys);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            s1.write(baos);
            byte[] bytes = baos.toByteArray();
            baos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            Series s3 = Series.of(bais);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void store() {
        double[] ys = {50, 10, 20, 30, 40, 70, 90, 10, 30, 40};

        Series s1 = Series.of(ys);
        try {
            byte[] bytes = s1.storeToByteArray();
            Series s3 = Series.of(bytes);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}