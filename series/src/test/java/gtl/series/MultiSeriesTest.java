package gtl.series;

import gtl.config.Config;
import gtl.io.File;
import gtl.series.common.MultiSeries;
import gtl.series.common.TimeSeries;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class MultiSeriesTest {

    @Test
    void readTSV() {
    }

    @Test
    void of() {
        double []xs = {1,2,3,4,5,6,7,8,9};
        double[][] ys={{1,1,1,1,1,1,1,1,1},
                {2,2,2,2,2,2,2,2,2},
                {3,3,3,3,3,3,3,3,3},
                {4,4,4,4,4,4,4,4,4},
                {5,5,5,5,5,5,5,5,5}};
        MultiSeries ms = MultiSeries.of(xs,ys);
        try {
            byte [] bytes = ms.storeToByteArray();
            MultiSeries ms2 = MultiSeries.of(bytes);
            TimeSeries s2 = ms2.getSeries(0);
            Assert.assertArrayEquals(s2.getValues(),ys[0],0.001);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @Test
    void of1() {
        double []xs = {1,2,3,4,5,6,7,8,9};
        double[][] ys={{1,1,1,1,1,1,1,1,1},
                {2,2,2,2,2,2,2,2,2},
                {3,3,3,3,3,3,3,3,3},
                {4,4,4,4,4,4,4,4,4},
                {5,5,5,5,5,5,5,5,5}};
        MultiSeries ms = MultiSeries.of(xs,ys);
        try {
            FileOutputStream f = new FileOutputStream(Config.getTestOutputDirectory()+ File.separator+"test.series");
            ms.write(f);
            f.close();
            FileInputStream f2= new FileInputStream(Config.getTestOutputDirectory()+ File.separator+"test.series");
            MultiSeries ms2 = MultiSeries.of(f2);
            TimeSeries s2 = ms2.getSeries(0);
            Assert.assertArrayEquals(s2.getValues(),ys[0],0.001);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void of2() {
    }

    @Test
    void of3() {
    }

    @Test
    void of4() {
    }

    @Test
    void of5() {
    }

    @Test
    void of6() {
    }

    @Test
    void of7() {
    }

    @Test
    void of8() {
    }

    @Test
    void of9() {
    }

    @Test
    void length() {
    }

    @Test
    void getSeries() {
    }

    @Test
    void count() {
    }

    @Test
    void getLabel() {
    }

    @Test
    void getLabels() {
    }

    @Test
    void load() {
    }

    @Test
    void store() {
    }

    @Test
    void toTrainSet() {
    }

    @Test
    void toTestSet() {
    }
}