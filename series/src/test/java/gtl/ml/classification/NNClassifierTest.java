package gtl.ml.classification;

import gtl.config.Config;
import gtl.io.File;
import gtl.series.common.MultiSeries;
import gtl.series.common.TimeSeries;
import gtl.series.distances.HaxDistanceMetrics;
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