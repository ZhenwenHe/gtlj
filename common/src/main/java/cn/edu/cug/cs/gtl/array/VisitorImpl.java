package cn.edu.cug.cs.gtl.array;

import cn.edu.cug.cs.gtl.math.Dimensions;
import cn.edu.cug.cs.gtl.math.Dimensions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class VisitorImpl implements Visitor {
    private static final long serialVersionUID = -5085906539020245380L;

    transient Array reference;
    Index[] indices;
    Dimensions dimensions;

    VisitorImpl(Array reference, Index[] indices, Dimensions dims) {
        this.reference = reference;
        this.indices = indices;
        this.dimensions = dims;
    }

    @Override
    public Array array() {
        return Array.create((Dimensions) dimensions.clone(), host());
    }

    @Override
    public boolean isEmpty() {
        return reference == null || indices == null;
    }

    @Override
    public double get(int... idxs) {
        int[] idx = new int[Array.MAXDIMS];
        int s = Math.min(idxs.length, Array.MAXDIMS);
        for (int i = 0; i < s; ++i) {
            idx[i] = (int) (idxs[i] * indices[i].step() + indices[i].begin());
        }
        for (int j = s; j < idx.length; ++j) {
            idx[j] = 0;
        }
        return reference.get(idx);
    }

    @Override
    public double scalar() {
        return get(indices[0].ibegin(), indices[1].ibegin(), indices[2].ibegin(), indices[3].ibegin());
    }

    @Override
    public double[] host() {
        double[] d = new double[dimensions.numberElements()];
        int[] dims = this.dimensions.get();
        int k = 0;
        for (int c2 = 0; c2 < dims[3]; ++c2) {
            for (int r2 = 0; r2 < dims[2]; ++r2) {
                for (int c1 = 0; c1 < dims[1]; ++c1) {
                    for (int r1 = 0; r1 < dims[0]; ++r1) {
                        d[k] = get(r1, c1, r2, c2);
                        ++k;
                    }
                }
            }
        }
        return d;
    }

    @Override
    public Visitor row(int i) {
        Index[] idxs = new Index[Array.MAXDIMS];
        for (int j = 0; j < Array.MAXDIMS; ++j) {
            idxs[j] = (Index) indices[j].clone();
        }
        idxs[0] = new Index(indices[0].ibegin() + i);
        Dimensions d4 = new Dimensions(1, dims(1), 1, 1);
        return new VisitorImpl(reference, idxs, d4);
    }

    @Override
    public Visitor rows(int first, int last) {
        Index[] idxs = new Index[Array.MAXDIMS];
        for (int j = 0; j < Array.MAXDIMS; ++j) {
            idxs[j] = (Index) indices[j].clone();
        }
        idxs[0] = new Index(first + indices[0].ibegin(), last + indices[0].ibegin());
        Dimensions d4 = new Dimensions(last - first, dims(1), 1, 1);
        return new VisitorImpl(reference, idxs, d4);
    }

    @Override
    public Visitor col(int i) {
        Index[] idxs = new Index[Array.MAXDIMS];
        for (int j = 0; j < Array.MAXDIMS; ++j) {
            idxs[j] = (Index) indices[j].clone();
        }
        idxs[1] = new Index(1 + indices[1].ibegin());
        Dimensions d4 = new Dimensions(dims(0), 1, 1, 1);
        return new VisitorImpl(reference, idxs, d4);
    }

    @Override
    public Visitor cols(int first, int last) {
        Index[] idxs = new Index[Array.MAXDIMS];
        for (int j = 0; j < Array.MAXDIMS; ++j) {
            idxs[j] = (Index) indices[j].clone();
        }
        idxs[1] = new Index(first + indices[1].ibegin(), last + indices[1].ibegin());
        Dimensions d4 = new Dimensions(dims(0), last - first, 1, 1);
        return new VisitorImpl(reference, idxs, d4);
    }

    @Override
    public Visitor slice(int index) {
        Index[] idxs = new Index[Array.MAXDIMS];
        for (int j = 0; j < Array.MAXDIMS; ++j) {
            idxs[j] = (Index) indices[j].clone();
        }
        idxs[2] = new Index(index + indices[2].ibegin());
        Dimensions d4 = new Dimensions(dims(0), dims(1), 1, 1);
        return new VisitorImpl(reference, idxs, d4);
    }

    @Override
    public Visitor slices(int first, int last) {
        Index[] idxs = new Index[Array.MAXDIMS];
        for (int j = 0; j < Array.MAXDIMS; ++j) {
            idxs[j] = (Index) indices[j].clone();
        }
        idxs[2] = new Index(first + indices[2].ibegin(), last + indices[2].ibegin());
        Dimensions d4 = new Dimensions(dims(0), dims(1), last - first, 1);
        return new VisitorImpl(reference, idxs, d4);
    }


    @Override
    public Dimensions dims() {
        return this.dimensions;
    }


    @Override
    public Object clone() {
        Index[] idxs = new Index[Array.MAXDIMS];
        for (int i = 0; i < idxs.length; ++i) {
            idxs[i] = (Index) indices[i].clone();
        }
        return new VisitorImpl(reference, idxs, (Dimensions) dimensions.clone());
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        if (dimensions == null)
            dimensions = new Dimensions(1, 1, 1, 1);
        dimensions.load(in);
        if (indices == null)
            indices = new Index[Array.MAXDIMS];
        for (int i = 0; i < Array.MAXDIMS; ++i) {
            indices[i] = new Index(0);
            indices[i].load(in);
        }
        if (reference == null)
            reference = new ArrayImpl(1, 1, 1, 1);
        reference.load(in);
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        dimensions.store(out);
        for (Index i : indices)
            i.store(out);
        reference.store(out);
        return true;
    }
}
