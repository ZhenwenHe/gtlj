package cn.edu.cug.cs.gtl.series;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MultiSeriesTest {

    @Test
    public void readTSV() {
    }

    @Test
    public void of() {
        double[] xs = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[][] ys = {{1, 1, 1, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5, 5, 5, 5, 5}};
        MultiSeries ms = MultiSeries.of(xs, ys);
        try {
            byte[] bytes = ms.storeToByteArray();
            MultiSeries ms2 = MultiSeries.of(bytes);
            TimeSeries s2 = ms2.getSeries(0);
            Assert.assertArrayEquals(s2.getValues(), ys[0], 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void of1() {
        double[] xs = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[][] ys = {{1, 1, 1, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5, 5, 5, 5, 5}};
        MultiSeries ms = MultiSeries.of(xs, ys);
        try {
            FileOutputStream f = new FileOutputStream(Config.getTestOutputDirectory() + File.separator + "test.series");
            ms.write(f);
            f.close();
            FileInputStream f2 = new FileInputStream(Config.getTestOutputDirectory() + File.separator + "test.series");
            MultiSeries ms2 = MultiSeries.of(f2);
            TimeSeries s2 = ms2.getSeries(0);
            Assert.assertArrayEquals(s2.getValues(), ys[0], 0.001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void of2() {
    }

    @Test
    public void of3() {
    }

    @Test
    public void of4() {
    }

    @Test
    public void of5() {
    }

    @Test
    public void of6() {
    }

    @Test
    public void of7() {
    }

    @Test
    public void of8() {
    }

    @Test
    public void of9() {
    }

    @Test
    public void length() {
    }

    @Test
    public void getSeries() {
    }

    @Test
    public void count() {
    }

    @Test
    public void getLabel() {
    }

    @Test
    public void getLabels() {
    }

    @Test
    public void load() {
    }

    @Test
    public void store() {
    }

    @Test
    public void toTrainSet() {
    }

    @Test
    public void toTestSet() {
    }
}