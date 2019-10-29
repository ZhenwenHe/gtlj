package cn.edu.cug.cs.gtl.index.shape;

import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Vector;

/**
 * Created by ZhenwenHe on 2017/3/15.
 */
public class ShapeSuits implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public static LineSegmentShape createLineSegment(Vector s, Vector e) {
        return new LineSegmentShape(s, e);
    }

    public static LineSegmentShape createLineSegment(PointShape s, PointShape e) {
        return new LineSegmentShape(s.getCenter(), e.getCenter());
    }

    public static PointShape createPoint() {
        return new PointShape();
    }

    public static PointShape createPoint(double[] c) {
        return new PointShape(c);
    }

    public static PointShape createPoint(double x, double y) {
        return new PointShape(x, y);
    }

    public static PointShape createPoint(double x, double y, double z) {
        return new PointShape(x, y, z);
    }

    public static PointShape[] createPointArray(int size) {
        return new PointShape[size];
    }

    public static RegionShape createRegion(int dim) {
        return new RegionShape(dim);
    }

    public static RegionShape createRegion(double[] low, double[] high) {
        return new RegionShape(low, high);
    }

    public static RegionShape createRegion(Envelope e) {
        return new RegionShape(e);
    }

    public static RegionShape[] createRegionArray(RegionShape[] c) {
        RegionShape[] r = new RegionShape[c.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = (RegionShape) c[i].clone();
        }
        return r;
    }
}
