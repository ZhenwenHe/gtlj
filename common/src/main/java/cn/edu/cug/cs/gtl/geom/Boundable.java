package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.io.Storable;

public interface Boundable extends Storable {
    Envelope getEnvelope();
}
