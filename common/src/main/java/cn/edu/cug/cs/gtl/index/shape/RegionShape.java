package cn.edu.cug.cs.gtl.index.shape;

import cn.edu.cug.cs.gtl.exception.IllegalDimensionException;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Geom3DSuits;
import cn.edu.cug.cs.gtl.geom.Vector;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by ZhenwenHe on 2016/12/7.
 */
public class RegionShape extends Envelope implements Shape {
    private static final long serialVersionUID = 1L;

    public RegionShape(int dim) {
        super(dim);
    }

    public RegionShape(double[] low, double[] high) {
        super(low, high);
    }

    public RegionShape(Envelope e) {
        super(e.getLowCoordinates(), e.getHighCoordinates());
    }

    public RegionShape(Vector leftBottom, Vector rightTop) {
        super(leftBottom.getCoordinates(), rightTop.getCoordinates());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean intersectsShape(Shape in) {
        if (in == null) return false;
        if (in instanceof RegionShape) {
            return this.intersectsRegion((RegionShape) in);
        }

        if (in instanceof LineSegmentShape) {
            return this.intersectsLineSegment((LineSegmentShape) in);
        }

        if (in instanceof PointShape) {
            return this.containsPoint((PointShape) in);
        }

        return false;
    }

    @Override
    public boolean containsShape(Shape in) {
        if (in == null) return false;

        if (in instanceof RegionShape) {
            return this.containsRegion((RegionShape) in);
        }

        if (in instanceof PointShape) {
            return this.containsPoint((PointShape) in);
        }

        return false;
    }

    @Override
    public boolean touchesShape(Shape in) {
        if (in == null) return false;

        if (in instanceof RegionShape) {
            return this.touchesRegion((RegionShape) in);
        }

        if (in instanceof PointShape) {
            return this.touchesPoint((PointShape) in);
        }

        return false;
    }

    @Override
    public Vector getCenter() {
        PointShape p = ShapeSuits.createPoint();

        int dims = this.getDimension();
        p.makeDimension(dims);
        double[] cc = p.getCoordinates();
        for (int i = 0; i < dims; i++) {
            cc[i] = (this.getLowOrdinate(i) + this.getHighOrdinate(i)) / 2.0;
        }
        return p.getCenter();
    }

    @Override
    public Envelope getMBR() {
        return (Envelope) this.clone();
    }

    @Override
    public double getArea() {

        double area = 1.0;
        int dims = this.getDimension();
        for (int i = 0; i < dims; ++i) {
            area *= (this.getHighOrdinate(i) - this.getLowOrdinate(i));
        }

        return area;
    }

    @Override
    public double getMinimumDistance(Shape in) {

        if (in instanceof RegionShape) {
            return this.getMinimumDistance((RegionShape) in);
        }

        if (in instanceof PointShape) {
            return this.getMinimumDistance((PointShape) in);
        }

        return 0;
    }

    @Override
    public RegionShape clone() {
        return new RegionShape(getLowCoordinates(), getHighCoordinates());
    }


    public boolean intersectsRegion(RegionShape in) {
        return intersects(in.getMBR());
    }


    public boolean containsRegion(RegionShape in) {
        return contains(in.getMBR());
    }


    public boolean touchesRegion(RegionShape in) {
        return touches(in.getMBR());
    }


    public double getMinimumDistance(RegionShape e) {
        if (e == null) return Double.MAX_VALUE;
        int dims = this.getDimension();
        if (dims != e.getDimension()) return Double.MAX_VALUE;

        double ret = 0.0;

        for (int i = 0; i < dims; ++i) {
            double x = 0.0;

            if (e.getHighOrdinate(i) < this.getLowOrdinate(i)) {
                x = Math.abs(e.getHighOrdinate(i) - this.getLowOrdinate(i));
            } else if (this.getHighOrdinate(i) < e.getLowOrdinate(i)) {
                x = Math.abs(e.getLowOrdinate(i) - this.getHighOrdinate(i));
            }

            ret += x * x;
        }

        return Math.sqrt(ret);
    }


    public boolean containsPoint(PointShape in) {
        return contains(in.getCenter());
    }


    public boolean touchesPoint(PointShape in) {
        return touches(in.getCenter());
    }


    public boolean intersectsLineSegment(LineSegmentShape e) {
        if (e == null) return false;
        int dims = this.getDimension();
        if (dims != e.getDimension()) return false;

        assert dims == 2;

        // there may be a more efficient method, but this suffices for now
        PointShape ll = ShapeSuits.createPoint(this.getLowCoordinates());
        PointShape ur = ShapeSuits.createPoint(this.getHighCoordinates());
        // fabricate ul and lr coordinates and points
        PointShape ul = ShapeSuits.createPoint(this.getLowOrdinate(0), this.getHighOrdinate(1));
        PointShape lr = ShapeSuits.createPoint(this.getHighOrdinate(0), this.getLowOrdinate(1));

        // Points/LineSegmentShape for the segment
        PointShape p1 = ShapeSuits.createPoint(e.getStartCoordinates());
        PointShape p2 = ShapeSuits.createPoint(e.getEndCoordinates());


        //Check whether either or both the endpoints are within the region OR
        //whether any of the bounding segments of the RegionShape intersect the segment
        return (this.containsPoint(p1) || this.containsPoint(p2) ||
                e.intersectsShape(ShapeSuits.createLineSegment(ll, ul)) || e.intersectsShape(ShapeSuits.createLineSegment(ul, ur)) ||
                e.intersectsShape(ShapeSuits.createLineSegment(ur, lr)) || e.intersectsShape(ShapeSuits.createLineSegment(lr, ll)));
    }

    public double getMinimumDistance(PointShape p) {

        if (p == null) return Double.MAX_VALUE;
        int dims = this.getDimension();
        if (dims != p.getDimension()) return Double.MAX_VALUE;


        double ret = 0.0;

        for (int i = 0; i < dims; ++i) {
            if (p.getCoordinate(i) < this.getLowOrdinate(i)) {
                ret += Math.pow(this.getLowOrdinate(i) - p.getCoordinate(i), 2.0);
            } else if (p.getCoordinate(i) > this.getHighOrdinate(i)) {
                ret += Math.pow(p.getCoordinate(i) - this.getHighOrdinate(i), 2.0);
            }
        }

        return Math.sqrt(ret);
    }


    public RegionShape getIntersectingRegion(RegionShape r) {
        return new RegionShape(getIntersectingEnvelope(r.getMBR()));
    }


    public void combineRegion(RegionShape in) {
        combine(in.getMBR());
    }


    public void combinePoint(PointShape in) {
        combine(in.getCenter());
    }


    public RegionShape getCombinedRegion(RegionShape in) {

        RegionShape r = (RegionShape) this.clone();
        r.combineRegion(in);
        return r;
    }

    /**
     * 按照X,Y,Z,W方向由小向大排列
     * 如果是二维，则索引需要为0,1,2,3
     * 如果为三维，则索引序号为0,1,2,4,5,6,7,
     * 如果为四维，则索引序号为0~15
     *
     * @return
     */
    public RegionShape subregion(int i) {

        int dim = getDimension();
        try {
            switch (dim) {
                case 2:
                    return subregion2D(i);
                case 3:
                    return subregion3D(i);
                default:
                    return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按照X,Y,Z,W方向由小向大排列
     * 如果是二维，则索引需要为0,1,2,3
     * 如果为三维，则索引序号为0,1,2,4,5,6,7,
     * 如果为四维，则索引序号为0~15
     *
     * @return
     */
    private RegionShape subregion2D(int i) throws Exception {
        if (getDimension() != 2)
            throw new IllegalDimensionException("the RegionShape must be two dimension in the function RegionShape subregion2D(int i)");

        Vector lv = getLowVector();
        Vector hv = getHighVector();
        Vector tv = hv.subtract(lv).divide(2);
        switch (i) {
            case 0: {
                return new RegionShape(this.getLowVector(), lv.add(tv));
            }
            case 1: {
                lv.setX(lv.getX() + tv.getX());
                hv.setY(hv.getY() - tv.getY());
                return new RegionShape(lv, hv);
            }
            case 2: {
                return new RegionShape(lv.add(tv), hv);
            }
            case 3: {
                lv.setY(lv.getY() + tv.getY());
                hv.setX(hv.getX() - tv.getX());
                return new RegionShape(lv, hv);
            }
            default: {
                throw new IllegalArgumentException("the legal parameter must be between 0 and 3");
            }
        }

    }

    /**
     * 按照X,Y,Z,W方向由小向大排列
     * 如果是二维，则索引需要为0,1,2,3
     * 如果为三维，则索引序号为0,1,2,4,5,6,7,
     * 如果为四维，则索引序号为0~15
     *
     * @return
     */
    private RegionShape subregion3D(int i) throws Exception {
        if (getDimension() != 3)
            throw new IllegalDimensionException("the RegionShape must be three dimension in the function RegionShape subregion3D(int i)");

        Vector lv = getLowVector();
        Vector hv = getHighVector();
        Vector tv = hv.subtract(lv).divide(2);

        switch (i) {
            case 0: {
                return new RegionShape(lv, lv.subtract(tv));
            }
            case 1: {
                lv.setX(lv.getX() + tv.getX());
                hv = lv.add(tv);
                return new RegionShape(lv, hv);
            }
            case 2: {
                lv.setX(lv.getX() + tv.getX());
                lv.setY(lv.getY() + tv.getY());
                hv = lv.add(tv);
                return new RegionShape(lv, hv);
            }
            case 3: {
                lv.setY(lv.getY() + tv.getY());
                hv = lv.add(tv);
                return new RegionShape(lv, hv);
            }
            case 4: {

            }
            case 5: {

            }
            case 6: {

            }
            case 7: {

            }
        }
        return null;
    }
}

