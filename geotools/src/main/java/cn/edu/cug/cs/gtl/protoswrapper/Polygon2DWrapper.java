package cn.edu.cug.cs.gtl.protoswrapper;

import cn.edu.cug.cs.gtl.protos.BoundingBox;
import cn.edu.cug.cs.gtl.protos.Polygon2D;

public class Polygon2DWrapper {
    public static Polygon2D of(BoundingBox bb){
        return Polygon2D.newBuilder().setShell(LinearRing2DWrapper.of(bb)).build();
    }

    public static String toWKT(Polygon2D p){
        return null;
    }
}
