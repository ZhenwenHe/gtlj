package cn.edu.cug.cs.gtl.ml.dataset;

import cn.edu.cug.cs.gtl.common.Pair;

public interface DataSet<S, L> {
    long size();

    S getSample(int i);

    L getLabel(int i);

    Pair<L, S> get(int i);

    Iterable<S> getSamples();

    Iterable<L> getLabels();

    void reset(Iterable<S> samples, Iterable<L> labels);
}
