package cn.edu.cug.cs.gtl.ml.classification;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.series.distances.HaxDistanceMetrics;
import org.junit.Test;

class NNClassifierTest {

    @Test
    void predict() {
        String trainFilePath = Config.getTestInputDirectory()+ File.separator+"UCRArchive_2018"+File.separator+"Beef"+File.separator+"Beef_TRAIN.tsv";
        String testFilePath = Config.getTestInputDirectory()+ File.separator+"UCRArchive_2018"+File.separator+"Beef"+File.separator+"Beef_TEST.tsv";
        try{
            MultiSeries train = MultiSeries.readTSV(trainFilePath);
            MultiSeries test= MultiSeries.readTSV(testFilePath);
            HaxDistanceMetrics<TimeSeries> disFunc = new HaxDistanceMetrics<>(10);
            NNClassifier nn = new NNClassifier(train.toTrainSet(),test.toTestSet(),disFunc);
            nn.predict(nn.testSet.getSamples());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}