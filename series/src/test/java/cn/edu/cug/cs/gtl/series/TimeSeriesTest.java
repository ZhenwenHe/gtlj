package cn.edu.cug.cs.gtl.series;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class TimeSeriesTest {
    static String testFileName = Config.getTestOutputDirectory()
            + File.separator + "test.timeseries";

    @Test
    public void copy() {
        double[] xs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[] ys = {50, 10, 20, 30, 40, 70, 90, 10, 30, 40};
        TimeSeries s1 = TimeSeries.of(xs, ys);
        TimeSeries s2 = TimeSeries.of(ys);
        try {
            FileOutputStream f = new FileOutputStream(testFileName);
            s1.write(f);
            f.close();
            FileInputStream f2 = new FileInputStream(testFileName);
            TimeSeries s3 = TimeSeries.of(f2);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
            Assert.assertArrayEquals(ys, s2.getValues(), 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void load() {
        double[] xs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[] ys = {50, 10, 20, 30, 40, 70, 90, 10, 30, 40};
        TimeSeries s1 = TimeSeries.of(xs, ys);
        TimeSeries s2 = TimeSeries.of(ys);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            s1.write(baos);
            byte[] bytes = baos.toByteArray();
            baos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            TimeSeries s3 = TimeSeries.of(bais);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
            Assert.assertArrayEquals(ys, s2.getValues(), 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void store() {
        double[] xs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[] ys = {50, 10, 20, 30, 40, 70, 90, 10, 30, 40};
        TimeSeries s1 = TimeSeries.of(xs, ys);
        TimeSeries s2 = TimeSeries.of(ys);
        try {
            byte[] bytes = s1.storeToByteArray();
            TimeSeries s3 = TimeSeries.of(bytes);
            Assert.assertArrayEquals(ys, s3.getValues(), 0.001);
            Assert.assertArrayEquals(ys, s2.getValues(), 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
