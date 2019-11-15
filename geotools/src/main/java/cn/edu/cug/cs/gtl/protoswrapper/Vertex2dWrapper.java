package cn.edu.cug.cs.gtl.protoswrapper;

import cn.edu.cug.cs.gtl.protos.Vertex2d;

public class Vertex2dWrapper {
    /**
     *
     * @param cc
     * @return
     */
    public static Vertex2d of(double[] cc){
        Vertex2d.Builder vb = Vertex2d.newBuilder();
        vb.setX(cc[0]);
        vb.setY(cc[1]);
        return vb.build();
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public static Vertex2d of(double x, double y){
        Vertex2d.Builder vb = Vertex2d.newBuilder();
        vb.setX(x);
        vb.setY(y);
        return vb.build();
    }
}
