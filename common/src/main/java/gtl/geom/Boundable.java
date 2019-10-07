package gtl.geom;

import gtl.io.Storable;

public interface Boundable extends Storable {
    Envelope getEnvelope();
}
