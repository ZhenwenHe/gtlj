package cn.edu.cug.cs.gtl.ml.classification;

import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
import cn.edu.cug.cs.gtl.ml.dataset.TrainSet;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;

import java.util.Collection;

public interface Classifier<S, L> {
    void setDistanceMetrics(DistanceMetrics<S> distanceMetrics);

    DistanceMetrics<S> getDistanceMetrics();

    void fit(DataSet<S, L> trainSet);

    Iterable<L> predict(Iterable<S> testSamples);

    double score(DataSet<S, L> testSet, Iterable<L> predictedLabels);

    DataSet<S, L> getTrainSet();

    DataSet<S, L> getTestSet();

    void setTrainSet(DataSet<S, L> dataSet);

    void setTestSet(DataSet<S, L> dataSet);

    default double score() {
        assert getTestSet() != null;
        assert getTrainSet() != null;
        assert getDistanceMetrics() != null;
        fit(getTrainSet());
        Iterable<L> labels = predict(getTestSet().getSamples());
        return score(getTestSet(), labels);
    }
}
