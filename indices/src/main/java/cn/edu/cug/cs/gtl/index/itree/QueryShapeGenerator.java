package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.geom.Interval;
import cn.edu.cug.cs.gtl.geom.Triangle;
import cn.edu.cug.cs.gtl.geom.Vector2D;
import cn.edu.cug.cs.gtl.index.shape.*;

/**
 * Created by ZhenwenHe on 2017/4/3.
 */
class QueryShapeGenerator<T extends Interval> implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    //refer to the tree's base triangle
    TriangleShape baseTriangle;

    public QueryShapeGenerator(TriangleShape baseTriangle) {
        this.baseTriangle = baseTriangle;
    }

    public QueryShapeGenerator(Triangle baseTriangle) {
        this.baseTriangle = new TriangleShape(baseTriangle);
    }

    public void reset(TriangleShape baseTriangle) {
        this.baseTriangle = baseTriangle;
    }

    /**
     * this is (Is,Ie)
     * q is the input parameter Inteval （Qs,Qe）
     * <p>
     * Equals Query: Is = Qs and Ie = Qe.
     * Starts Query: Is = Qs and Qs < Ie < Qe; as shown in Fig. 3a.
     * StartedBy Query: Is = Qs and Ie > Qe; as shown in Fig. 3b.
     * Meets Query: Is < Ie = Qs < Qe; as shown in Fig. 3c.
     * MetBy Query: Qs < Qe = Is < Ie; as shown in Fig. 3d.
     * Finishes Query: Qs < Is < Qe and Ie = Qe; as shown in Fig. 3e.
     * FinishedBy Query: Is < Qs and Ie = Qe; as shown in Fig. 3f.
     * Before Query: Is < Ie < Qs < Qe; as shown in Fig. 3a.
     * After Query: Qs < Qe < Is < Ie; as shown in Fig. 4b.
     * Overlaps Query: Is < Qs and Qs < Ie < Qe; as shown in Fig. 4c.
     * OverlappedBy Query: Qs < Is < Qe and Ie > Qe; as shown in      Fig. 4d.
     * During Query: Qs < Is < Ie < Qe; as shown in Fig. 4e.
     * Contains Query: Is < Qs < Qe < Ie; as shown in Fig. 4f.
     */
    public Shape equals(T q) {
        return new PointShape(q.getLowerBound(), q.getUpperBound());
    }

    public Shape starts(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(s, s),
                new Vector2D(s, e));
    }

    public Shape startedBy(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(s, e),
                new Vector2D(s, baseTriangle.getVertex(0).getY()));
    }

    public Shape meets(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(baseTriangle.getVertex(0).getX(), s),
                new Vector2D(s, s));
    }

    public Shape metBy(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(e, e),
                new Vector2D(e, baseTriangle.getVertex(2).getY()));
    }

    public Shape finishes(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(s, e),
                new Vector2D(e, e));
    }

    public Shape finishedBy(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new LineSegmentShape(
                new Vector2D(baseTriangle.getVertex(0).getX(), s),
                new Vector2D(s, e));
    }

    public Shape before(T q) {
        double s = q.getLowerBound();
        return new RegionShape(
                baseTriangle.getVertex(1),
                new Vector2D(s, s));
    }

    public Shape after(T q) {
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(e, e),
                baseTriangle.getVertex(2));
    }

    public Shape overlaps(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(0, s),
                new Vector2D(s, e));
    }

    public Shape overlappedBy(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(s, e),
                new Vector2D(e, baseTriangle.getVertex(2).getY()));
    }

    public Shape during(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(s, s),
                new Vector2D(e, e));
    }

    public Shape contains(T q) {
        double s = q.getLowerBound();
        double e = q.getUpperBound();
        return new RegionShape(
                new Vector2D(baseTriangle.getVertex(1).getX(), e),
                new Vector2D(s, baseTriangle.getVertex(2).getY()));
    }

    public Shape covers(T q) {
        return new PointShape(q.getLowerBound(), q.getUpperBound());
    }

    public Shape coveredBy(T q) {
        return new PointShape(q.getLowerBound(), q.getUpperBound());
    }
}
