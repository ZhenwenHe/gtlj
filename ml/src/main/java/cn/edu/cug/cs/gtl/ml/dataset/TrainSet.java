package cn.edu.cug.cs.gtl.ml.dataset;

import java.io.IOException;
import java.util.ArrayList;

public class TrainSet<S, L> extends DefaultDataSet<S, L> {
    protected TrainSet() {
    }

    public TrainSet(ArrayList<S> samples, ArrayList<L> labels) {
        super(samples, labels);
        assert samples != null && labels != null;
    }

    @Override
    public Object clone() {
        TrainSet<S, L> ts = new TrainSet<S, L>();
        try {
            byte[] bytes = this.storeToByteArray();
            ts.loadFromByteArray(bytes);
            return ts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
