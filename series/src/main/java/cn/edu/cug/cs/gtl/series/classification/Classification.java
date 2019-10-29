package cn.edu.cug.cs.gtl.series.classification;

import cn.edu.cug.cs.gtl.ml.classification.Classifier;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;


import java.util.ArrayList;
import java.util.List;

public class Classification {


    /**
     * @param trainTimeSeries
     * @param testTimeSeries
     * @param distanceMetrics
     * @param classifier
     * @return
     */
    public static double classify(MultiSeries trainTimeSeries,
                                  MultiSeries testTimeSeries,
                                  DistanceMetrics<TimeSeries> distanceMetrics,
                                  Classifier<TimeSeries, String> classifier) {
        classifier.setDistanceMetrics(distanceMetrics);
        classifier.setTrainSet(trainTimeSeries.toTrainSet());
        classifier.setTestSet(testTimeSeries.toTestSet());
        return classifier.score();
    }

    /**
     * calculate classification accuracy
     *
     * @param trainTimeSeries multi train time series
     * @param testTimeSeries  multi test time series
     * @return probs accuracy
     */
    public static double timeSeriesClassifier(MultiSeries trainTimeSeries, MultiSeries testTimeSeries, DistanceMetrics<TimeSeries> disfunc) {
        int trainDataLen = trainTimeSeries.getLabels().size();
        int testDataLen = testTimeSeries.getLabels().size();

        double minDis = Double.MAX_VALUE;
        List<String> labelList = new ArrayList<>(testDataLen);
        String label = "";
        int pos = 0;
        for (int i = 0; i < testDataLen; i++) {
            TimeSeries testSeries = testTimeSeries.getSeries(i);
            minDis = Double.MAX_VALUE;
            for (int j = 0; j < trainDataLen; j++) {
                TimeSeries trainSeries = trainTimeSeries.getSeries(j);
                double tempDis = disfunc.distance(trainSeries, testSeries);
                if (tempDis < minDis) {
                    minDis = tempDis;
                    pos = j;
                }
            }
            label = trainTimeSeries.getLabel(pos);
            labelList.add(label);
        }

        int count = 0;
        for (int i = 0; i < labelList.size(); i++) {
            if (labelList.get(i).equals(testTimeSeries.getLabel(i)))
                count++;
        }
        double probs = count * 1.0 / testDataLen;
        return probs;
    }

}
