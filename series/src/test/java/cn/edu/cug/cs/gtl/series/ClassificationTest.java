package cn.edu.cug.cs.gtl.series;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import cn.edu.cug.cs.gtl.series.classification.Classification;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;
import cn.edu.cug.cs.gtl.series.distances.*;
import cn.edu.cug.cs.gtl.ml.classification.KNNClassifier;
import cn.edu.cug.cs.gtl.ml.classification.NNClassifier;
import org.apache.log4j.*;
import org.junit.Test;
import org.apache.log4j.Logger;

import jxl.Workbook;
import jxl.write.*;


import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.PrintStream;


public class ClassificationTest {

    @Test
    public void predict() {
        String trainFilePath = Config.getTestInputDirectory() + cn.edu.cug.cs.gtl.io.File.separator + "UCRArchive_2018" + cn.edu.cug.cs.gtl.io.File.separator + "Beef" + cn.edu.cug.cs.gtl.io.File.separator + "Beef_TRAIN.tsv";
        String testFilePath = Config.getTestInputDirectory() + cn.edu.cug.cs.gtl.io.File.separator + "UCRArchive_2018" + cn.edu.cug.cs.gtl.io.File.separator + "Beef" + File.separator + "Beef_TEST.tsv";
        try {
            MultiSeries train = MultiSeries.readTSV(trainFilePath);
            MultiSeries test = MultiSeries.readTSV(testFilePath);
            TIOPlane tioPlane = TIOPlane.of(Math.min(train.min(), test.min()), Math.max(train.max(), test.max()));
            HaxDistanceMetrics<TimeSeries> disFunc = new HaxDistanceMetrics<>(10, tioPlane);
            NNClassifier nn = new NNClassifier(train.toTrainSet(), test.toTestSet(), disFunc);
            System.out.println(nn.score());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
