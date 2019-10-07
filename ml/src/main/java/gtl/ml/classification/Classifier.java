package gtl.ml.classification;

import gtl.ml.dataset.DataSet;
import gtl.ml.dataset.TestSet;
import gtl.ml.dataset.TrainSet;
import gtl.ml.distances.DistanceMetrics;

import java.util.Collection;

public interface Classifier<S,L> {
    void setDistanceMetrics(DistanceMetrics<S> distanceMetrics);
    DistanceMetrics<S> getDistanceMetrics();
    void fit(DataSet<S,L> trainSet);
    Iterable<L> predict(Iterable<S> testSamples );
    double score(DataSet<S,L> testSet, Iterable<L> predictedLabels);
    DataSet<S,L> getTrainSet();
    DataSet<S,L> getTestSet();
    default double score(){
        assert getTestSet()!=null;
        assert getTrainSet()!=null;
        assert getDistanceMetrics()!=null;
        fit(getTrainSet());
        Iterable<L> labels = predict(getTestSet().getSamples());
        return score(getTestSet(),labels);
    }
}
