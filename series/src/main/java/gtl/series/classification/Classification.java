package gtl.series.classification;

import gtl.ml.distances.DistanceMetrics;
import gtl.series.common.MultiSeries;
import gtl.series.common.TimeSeries;


import java.util.ArrayList;
import java.util.List;

public class Classification {

    /**
     * calculate classification accuracy
     * @param trainTimeSeries multi train time series
     * @param testTimeSeries  multi test time series
     * @return probs accuracy
     */

    public static double timeSeriesClassifier(MultiSeries trainTimeSeries, MultiSeries testTimeSeries, DistanceMetrics<TimeSeries> disfunc){
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
        double probs = count*1.0 / testDataLen;
        return probs;
    }

}
