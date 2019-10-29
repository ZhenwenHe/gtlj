package cn.edu.cug.cs.gtl.ml.dataset;

import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class DefaultDataSet<S, L> implements DataSet<S, L>, Storable {
    protected ArrayList<S> samples = null;
    protected ArrayList<L> labels = null;

    protected DefaultDataSet() {

    }

    public DefaultDataSet(ArrayList<S> samples, ArrayList<L> labels) {
        this.samples = samples;
        this.labels = labels;
    }

    @Override
    public Object clone() {
        DefaultDataSet<S, L> ts = new DefaultDataSet<S, L>();
        try {
            byte[] bytes = this.storeToByteArray();
            ts.loadFromByteArray(bytes);
            return ts;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean load(DataInput dataInput) throws IOException {
        int s = dataInput.readInt();
        if (s > 0) {
            this.samples = new ArrayList<>(s);
            for (int i = 0; i < s; ++i)
                this.samples.add((S) ObjectUtils.load(dataInput));
        }
        s = dataInput.readInt();
        if (s > 0) {
            this.labels = new ArrayList<>(s);
            for (int i = 0; i < s; ++i)
                this.labels.add((L) ObjectUtils.load(dataInput));
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dataOutput) throws IOException {
        int s = this.samples == null ? 0 : this.samples.size();
        dataOutput.writeInt(s);
        if (s > 0) {
            for (S si : this.samples)
                ObjectUtils.store(si, dataOutput);
        }

        s = this.labels == null ? 0 : this.labels.size();
        dataOutput.writeInt(s);
        if (s > 0) {
            dataOutput.writeInt(s);
            for (L li : this.labels)
                ObjectUtils.store(li, dataOutput);
        }
        return true;
    }

    @Override
    public long size() {
        return this.samples.size();
    }

    @Override
    public S getSample(int i) {
        return this.samples.get(i);
    }

    @Override
    public L getLabel(int i) {
        if (this.labels != null)
            return this.labels.get(i);
        return null;
    }

    @Override
    public Pair<L, S> get(int i) {
        return new Pair<L, S>(getLabel(i), getSample(i));
    }

    @Override
    public Iterable<S> getSamples() {
        return this.samples;
    }

    @Override
    public Iterable<L> getLabels() {
        if (this.labels != null)
            return this.labels;
        return null;
    }

    @Override
    public void reset(Iterable<S> samples, Iterable<L> labels) {

        if (samples instanceof ArrayList) {
            this.samples = (ArrayList<S>) samples;
        } else {
            if (samples instanceof Collection) {
                Collection<S> c = (Collection<S>) samples;
                int s = c.size();
                this.samples = new ArrayList<>(s);
                System.arraycopy(c, 0, this.samples, 0, s);
            } else {
                this.samples = new ArrayList<>();
                for (S s : samples)
                    this.samples.add(s);
            }
        }

        if (labels instanceof ArrayList) {
            this.labels = (ArrayList<L>) labels;
        } else {
            if (labels instanceof Collection) {
                Collection<L> c = (Collection<L>) samples;
                int s = c.size();
                this.labels = new ArrayList<>(s);
                System.arraycopy(c, 0, this.labels, 0, s);
            } else {
                this.labels = new ArrayList<>();
                for (L s : labels)
                    this.labels.add(s);
            }
        }
    }
}
