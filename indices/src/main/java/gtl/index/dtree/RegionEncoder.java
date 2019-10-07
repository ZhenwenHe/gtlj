package gtl.index.dtree;

import gtl.geom.Envelope;
import gtl.index.shape.RegionShape;

public interface RegionEncoder extends java.io.Serializable{
    long serialVersionUID = 1L;

    RegionShape parse(String code);
    String encode(RegionShape subEnvelope);
}
