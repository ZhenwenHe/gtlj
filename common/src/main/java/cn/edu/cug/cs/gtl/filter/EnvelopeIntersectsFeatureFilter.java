package cn.edu.cug.cs.gtl.filter;

import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.feature.Feature;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EnvelopeIntersectsFeatureFilter implements FeatureFilter {
    private final Envelope envelope;

    public EnvelopeIntersectsFeatureFilter(Envelope envelope) {
        this.envelope = envelope.clone();
    }

    public EnvelopeIntersectsFeatureFilter() {
        this(Envelope.create(Config.getDimension()));
    }

    @Override
    public boolean test(Feature feature) {
        Geometry g = feature.getGeometry();
        if (g == null) return false;
        return this.envelope.intersects(g.getEnvelope());
    }

    @Override
    public Object clone() {
        return new EnvelopeIntersectsGeometryFilter(this.envelope);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.envelope.load(in);
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        this.envelope.store(out);
        return true;
    }
}
