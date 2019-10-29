package cn.edu.cug.cs.gtl.index.dtree;

import cn.edu.cug.cs.gtl.index.shape.RegionShape;

public interface RegionEncoder extends java.io.Serializable {
    long serialVersionUID = 1L;

    RegionShape parse(String code);

    String encode(RegionShape subEnvelope);
}
