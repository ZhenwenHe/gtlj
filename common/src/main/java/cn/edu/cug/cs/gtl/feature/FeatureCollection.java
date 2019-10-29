package cn.edu.cug.cs.gtl.feature;


import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class FeatureCollection extends ArrayList<Feature> implements Serializable {

    private static final long serialVersionUID = 4809702586308949912L;

    FeatureType featureType;

    public FeatureCollection() {
        this.featureType = null;
    }

    public FeatureCollection(FeatureType featureType) {
        this.featureType = featureType;
    }

    public FeatureCollection(@NotNull Collection<? extends Feature> c, FeatureType featureType) {
        super(c);
        this.featureType = featureType;
    }


    public FeatureCollection(int initialCapacity, FeatureType featureType) {
        super(initialCapacity);
        this.featureType = featureType;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    @Override
    public Object clone() {
        FeatureCollection vc = new FeatureCollection(this.size(), this.featureType);
        for (Feature v : (Collection<Feature>) this)
            vc.add((Feature) v.clone());
        return vc;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        int n = in.readInt();
        if (n > 0) {
            this.clear();
            this.ensureCapacity(n);
            for (int i = 0; i < n; ++i) {
                this.add((Feature) ObjectUtils.load(in));
            }
        } else
            clear();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(size());
        for (Feature v : (Collection<Feature>) this) {
            ObjectUtils.store(v, out);
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 4;
        for (Feature v : (Collection<Feature>) this) {
            len += ObjectUtils.getByteArraySize(v);
        }
        return len;
    }
}
