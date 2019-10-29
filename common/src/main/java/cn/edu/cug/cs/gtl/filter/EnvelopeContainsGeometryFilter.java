package cn.edu.cug.cs.gtl.filter;

import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.config.Config;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EnvelopeContainsGeometryFilter implements GeometryFilter {
    private final Envelope envelope;

    public EnvelopeContainsGeometryFilter(Envelope envelope) {
        this.envelope = envelope.clone();
    }

    public EnvelopeContainsGeometryFilter() {
        this(Envelope.create(Config.getDimension()));
    }

    @Override
    public boolean test(Geometry geometry) {
        return this.envelope.contains(geometry.getEnvelope());
    }

    @Override
    public Object clone() {
        return new EnvelopeContainsGeometryFilter(this.envelope);
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
