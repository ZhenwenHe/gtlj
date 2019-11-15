package cn.edu.cug.cs.gtl.protoswrapper;

import cn.edu.cug.cs.gtl.protos.BoundingBox;
import cn.edu.cug.cs.gtl.protos.LinearRing2D;
import cn.edu.cug.cs.gtl.protos.Vertex2d;

public class LinearRing2DWrapper {
    /**
     *
     * @param cc
     * @return
     */
    public static LinearRing2D of(double [] cc){
        LinearRing2D.Builder builder = LinearRing2D.newBuilder();
        Vertex2d.Builder vb = Vertex2d.newBuilder();
        int i=0;
        while(i<cc.length){
            vb.setX(cc[i]);
            ++i;
            vb.setY(cc[i]);
            ++i;
            builder.addVertex(vb.build());
            vb.clear();
        }
        return builder.build();
    }

    /**
     *
     * @param bb
     * @return
     */
    public static LinearRing2D of(BoundingBox bb){
        double xMin = BoundingBoxWrapper.getMinX(bb);
        double yMin = BoundingBoxWrapper.getMinY(bb);
        double xMax = BoundingBoxWrapper.getMaxX(bb);
        double yMax = BoundingBoxWrapper.getMaxY(bb);
        LinearRing2D.Builder builder = LinearRing2D.newBuilder();
        Vertex2d.Builder vb = Vertex2d.newBuilder();

        vb.setX(xMin);
        vb.setY(yMin);
        builder.addVertex(vb.build());
        vb.clear();

        vb.setX(xMin);
        vb.setY(yMax);
        builder.addVertex(vb.build());
        vb.clear();

        vb.setX(xMax);
        vb.setY(yMax);
        builder.addVertex(vb.build());
        vb.clear();

        vb.setX(xMax);
        vb.setY(yMin);
        builder.addVertex(vb.build());
        vb.clear();

        vb.setX(xMin);
        vb.setY(yMin);
        builder.addVertex(vb.build());
        vb.clear();

        return builder.build();
    }
}
