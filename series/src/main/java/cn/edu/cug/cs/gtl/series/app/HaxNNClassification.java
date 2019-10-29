package cn.edu.cug.cs.gtl.series.app;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.ml.classification.NNClassifier;
import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
import cn.edu.cug.cs.gtl.ml.dataset.TrainSet;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;
import cn.edu.cug.cs.gtl.series.distances.HaxDistanceMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HaxNNClassification {

    private static final Logger LOGGER = LoggerFactory.getLogger(HaxNNClassification.class);

    public static void main(String[] args) {
        LOGGER.debug("begin Hax NN");
        ExperimentalConfig config = new ExperimentalConfig(Config.getDataDirectory() + File.separatorChar + "log" + File.separatorChar + "series.db");

        int m = config.getDataFiles().size();
        int n = config.getPaaSizeRange().second() - config.getPaaSizeRange().first();
        List<Pair<String, double[]>> results = new ArrayList<>();
        int k = 0;
        config.setResultFileName("hax_nn.xls");

        LOGGER.info("data files:{}", config.getDataFiles().size());

        try {
            for (Pair<String, String> p : config.getDataFiles()) {
                String name = File.getFileNameWithoutSuffix(p.first());
                name = name.substring(0, name.indexOf('_'));
                LOGGER.info(name);
                MultiSeries train = Series.readTSV(p.first());
                MultiSeries test = Series.readTSV(p.second());
                TrainSet<TimeSeries, String> trainSet = train.toTrainSet();
                TestSet<TimeSeries, String> testSet = test.toTestSet();
                TIOPlane tioPlane = TIOPlane.of(Math.min(train.min(), test.min()),
                        Math.max(train.max(), test.max()));
                Pair<Integer, Integer> paaSizeRange = config.getPaaSizeRange();
                double[] r = new double[n];
                k = 0;
                for (int i = paaSizeRange.first(); i < paaSizeRange.second(); ++i) {
                    HaxDistanceMetrics<TimeSeries> disFunc = new HaxDistanceMetrics<>(i, tioPlane);
                    NNClassifier<TimeSeries, String> nnClassifier = new NNClassifier<>(trainSet, testSet, disFunc);
                    r[k] = nnClassifier.score();
                    LOGGER.info("paaSize {}, score {}", i, r[k]);
                    k++;
                }
                results.add(new Pair<>(name, r));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("begin output {}", results);
        config.writeResults(results);
        LOGGER.info("end output {}", results);

        LOGGER.debug("end Hax NN");
    }


}
