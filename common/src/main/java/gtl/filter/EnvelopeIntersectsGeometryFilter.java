package gtl.filter;

import gtl.config.Config;
import gtl.feature.Feature;
import gtl.geom.Envelope;
import gtl.geom.Geometry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EnvelopeIntersectsGeometryFilter implements GeometryFilter  {
    private final Envelope envelope;

    public EnvelopeIntersectsGeometryFilter(Envelope envelope) {
        this.envelope=envelope.clone();
    }

    public EnvelopeIntersectsGeometryFilter( ) {
        this(Envelope.create(Config.getDimension()));
    }

    @Override
    public boolean test(Geometry g) {
        if(g==null) return false;
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
