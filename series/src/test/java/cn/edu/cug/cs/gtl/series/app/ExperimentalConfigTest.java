package cn.edu.cug.cs.gtl.series.app;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ExperimentalConfigTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void generalTest() {
        ExperimentalConfig config = null;
//        config=new ExperimentalConfig(null);
//        Assert.assertEquals(96,config.getDataFiles().size());
//        Assert.assertEquals(5,config.getPaaSizeRange().first().intValue());
//        Assert.assertEquals(20,config.getPaaSizeRange().second().intValue());
//        Assert.assertEquals(3,config.getAlphabetRange().first().intValue());
//        Assert.assertEquals(16,config.getAlphabetRange().second().intValue());
//
//        config=new ExperimentalConfig("series.db");
//        Assert.assertEquals(96,config.getDataFiles().size());
//        Assert.assertEquals(5,config.getPaaSizeRange().first().intValue());
//        Assert.assertEquals(20,config.getPaaSizeRange().second().intValue());
//        Assert.assertEquals(3,config.getAlphabetRange().first().intValue());
//        Assert.assertEquals(16,config.getAlphabetRange().second().intValue());

        config = new ExperimentalConfig(Config.getDataDirectory() + File.separatorChar + "log" + File.separatorChar + "series.db");
        Assert.assertEquals(96, config.getDataFiles().size());
        Assert.assertEquals(5, config.getPaaSizeRange().first().intValue());
        Assert.assertEquals(21, config.getPaaSizeRange().second().intValue());
        Assert.assertEquals(3, config.getAlphabetRange().first().intValue());
        Assert.assertEquals(17, config.getAlphabetRange().second().intValue());
    }

    @Test
    public void writeResults() {
        ExperimentalConfig config = new ExperimentalConfig("series.db");
        List<Pair<String, double[]>> results = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            double[] d = new double[5];
            for (int j = 0; j < 5; ++j) {
                d[j] = (j + 10) * 3;
            }
            Pair<String, double[]> p = new Pair<>(String.valueOf(i), d);
            results.add(p);
        }
        config.writeResults(results);
    }
}