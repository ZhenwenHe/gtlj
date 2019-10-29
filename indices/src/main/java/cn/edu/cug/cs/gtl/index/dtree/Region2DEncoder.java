package cn.edu.cug.cs.gtl.index.dtree;

import cn.edu.cug.cs.gtl.index.shape.RegionShape;

/**
 * Region为2D正方形
 * leftBottom 0
 * rightBottom 1
 * leftTop 2
 * rightTop 3
 */
public class Region2DEncoder implements RegionEncoder {

    RegionShape regionShape;

    public Region2DEncoder(RegionShape regionShape) {
        this.regionShape = (RegionShape) regionShape.clone();
    }

    @Override
    public RegionShape parse(String code) {
        if (code == null) return null;
        if (code.isEmpty()) return null;
        if (code.equals("1"))
            return regionShape;
        return null;
    }

    @Override
    public String encode(RegionShape subEnvelope) {
        return null;
    }
}
