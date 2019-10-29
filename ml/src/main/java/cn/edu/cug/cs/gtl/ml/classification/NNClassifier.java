package cn.edu.cug.cs.gtl.ml.classification;

import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;


import java.util.ArrayList;
import java.util.List;

public class NNClassifier<S, L> extends DefaultClassifier<S, L> {

    protected NNClassifier() {
    }

    public NNClassifier(DataSet<S, L> trainSet, DataSet<S, L> testSet, DistanceMetrics<S> distanceMetrics) {
        super(trainSet, testSet, distanceMetrics);
    }

    @Override
    public Iterable<L> predict(Iterable<S> testSamples) {
        long trainDataLen = this.trainSet.size();
        double minDis = Double.MAX_VALUE;
        List<L> labelList = new ArrayList<>();
        L label = null;
        int pos = 0;
        for (S i : testSamples) {
            minDis = Double.MAX_VALUE;
            for (int j = 0; j < trainDataLen; ++j) {
                double tempDis = this.distanceMetrics.distance(i, this.trainSet.getSample(j));
                if (tempDis < minDis) {
                    minDis = tempDis;
                    pos = j;
                }
            }
            label = this.trainSet.getLabel(pos);
            labelList.add(label);
        }
        return labelList;
    }

}
