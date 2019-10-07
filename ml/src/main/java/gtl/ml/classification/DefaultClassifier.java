package gtl.ml.classification;

import gtl.ml.dataset.DataSet;
import gtl.ml.distances.DistanceMetrics;

import java.util.ArrayList;
import java.util.List;

public abstract class DefaultClassifier<S,L> implements Classifier<S,L> {
    DataSet<S,L> trainSet=null;
    DataSet<S,L> testSet=null;
    DistanceMetrics<S> distanceMetrics=null;

    protected DefaultClassifier(){
    }

    public DefaultClassifier(DataSet<S, L> trainSet, DataSet<S, L> testSet, DistanceMetrics<S> distanceMetrics) {
        this.trainSet = trainSet;
        this.testSet = testSet;
        this.distanceMetrics = distanceMetrics;
    }

    @Override
    public void setDistanceMetrics(DistanceMetrics<S> distanceMetrics) {
        this.distanceMetrics = distanceMetrics;
    }

    @Override
    public DistanceMetrics<S> getDistanceMetrics() {
        return this.distanceMetrics;
    }

    @Override
    public void fit(DataSet<S, L> trainSet) {
        this.trainSet=trainSet;
    }

    @Override
    public abstract Iterable<L> predict(Iterable<S> testSamples) ;

    @Override
    public double score(DataSet<S, L> testSet, Iterable<L> predictedLabels) {
        this.testSet=testSet;
        double probs = 0.0;
        int count = 0;
        int i=0;
        for(L p: predictedLabels){
            if (this.testSet.getLabel(i).equals(p))
                count++;
            ++i;
        }
        probs = count*1.0 / i;
        return probs;
    }

    @Override
    public DataSet<S, L> getTrainSet() {
        return this.trainSet;
    }

    @Override
    public DataSet<S, L> getTestSet() {
        return this.testSet;
    }
}
