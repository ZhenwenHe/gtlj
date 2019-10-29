package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.math.MathSuits;
import cn.edu.cug.cs.gtl.math.MathSuits;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-3-26.
 * The primal way to specify a line L is by giving two distinct points, P0 and P1, on it.
 * In fact, this defines a finite line segment S going from P0 to P1 which are the endpoints
 * of S. This is how the Greeks understood straight lines, and it coincides with our natural
 * intuition for the most direct and shortest path between the two endpoints. This line can
 * then be extended indefinitely beyond either endpoint producing infinite rays in both directions.
 * When extended simultaneously beyond both ends, one gets the concept of an infinite line which
 * is how we often think of it today.
 * <p>
 * reference :http://geomalgorithms.com/a02-_lines.html
 * reference :LineSegmentShape
 */
public class InfiniteLine implements Serializable, Comparable<InfiniteLine> {
    private static final long serialVersionUID = 1L;

    Vector startPoint;
    Vector endPoint;

    public InfiniteLine(Vector startPoint, Vector endPoint) {
        try {
            this.startPoint = (Vector) startPoint.clone();
            this.endPoint = (Vector) endPoint.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InfiniteLine(double[] startPoint, double[] endPoint) {
        reset(startPoint, endPoint);
    }

    public InfiniteLine() {
        this.startPoint = new VectorImpl(0.0, 0.0, 0.0);
        this.endPoint = new VectorImpl(0.0, 0.0, 0.0);
    }

    /**
     * 测试点与线的位置关系,3D空间，则只有在线上或不在线上
     * The condition for three points v, v0,and v1,
     * to be collinear can also be expressed as the statement
     * that the distance between any one point and the line
     * determined by the other two is zero.
     * | (v1-v0)x（v-v0)|
     * -------------------------  = d
     * |v1-v0|
     *
     * @param v 待测试的点
     * @return 0- 点在线上，也即是三点共线
     * 非0都表示在线外
     */
    public static int test3D(Vector v, Vector v0, Vector v1) {
        double d = v1.subtract(v0).crossProduct(v.subtract(v0)).length();
        if (Math.abs(d) < MathSuits.EPSILON)
            return 0;
        else
            return -1;
    }

    /**
     * 测试2D平面(XOY)上点与线的位置关系
     *
     * @param v  测试点
     * @param v0 直线起点，方向从v0指向v1
     * @param v1 直线终点， 方向从v0指向v1
     * @return 0- 点在线上，也即是三点共线
     * -1 - 点在线的左边
     * 1 - 点在线的右边
     */
    public static int test2D(Vector v, Vector v0, Vector v1) {
        double[] P0;
        double[] P1;
        double[] P2;
        double d;
        P0 = v0.getCoordinates();
        P1 = v1.getCoordinates();
        P2 = v.getCoordinates();

        d = (P1[0] - P0[0]) * (P2[1] - P0[1]) - (P2[0] - P0[0]) * (P1[1] - P0[1]);

        if (Math.abs(d) < MathSuits.EPSILON) return 0;
        if (d > 0) return -1;
        if (d < 0) return 1;
        return 0;
    }

    /**
     * 测试2D平面(XOY)上点与线的位置关系
     *
     * @param v  测试点
     * @param v0 直线起点，方向从v0指向v1
     * @param v1 直线终点， 方向从v0指向v1
     * @return 0- 点在线上，也即是三点共线
     * -1 - 点在线的左边
     * 1 - 点在线的右边
     */
    public static int test2D(double[] v, double[] v0, double[] v1) {
        double d = (v1[0] - v0[0]) * (v[1] - v0[1]) - (v[0] - v0[0]) * (v1[1] - v0[1]);
        if (Math.abs(d) < MathSuits.EPSILON) return 0;
        if (d > 0) return -1;
        if (d < 0) return 1;
        return 0;
    }

    @Override
    public Object clone() {
        return new InfiniteLine(this.startPoint, this.endPoint);
    }

    public void reset(Vector s, Vector e) {
        this.startPoint = new VectorImpl(s.getCoordinates());
        this.endPoint = new VectorImpl(e.getCoordinates());
    }

    public void reset(double[] s, double[] e) {
        this.startPoint = new VectorImpl(s);
        this.endPoint = new VectorImpl(e);
    }

    @Override
    public void copyFrom(Object i) {
        if (i == null) return;
        if (i instanceof InfiniteLine) {
            this.reset(((InfiniteLine) i).getStartPoint(), ((InfiniteLine) i).getEndPoint());
        }
    }

    public Vector getStartPoint() {
        return this.startPoint;
    }

    public Vector getEndPoint() {
        return this.endPoint;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.startPoint.load(in);
        this.endPoint.load(in);
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        this.startPoint.store(out);
        this.endPoint.store(out);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return this.startPoint.getByteArraySize() + this.endPoint.getByteArraySize();
    }

    @Override
    public int compareTo(InfiniteLine o) {
        return 0;
    }

    /**
     * 测试2D平面(XOY)上点与线的位置关系
     * 如果是3D空间，则只有在线上或不在线上
     *
     * @param v 待测试的点
     * @return 如果是2D空间，则
     * 0- 点在线上，也即是三点共线
     * -1- 点在线的左边
     * 1 - 点在线的右边
     * 如果是3D空间，则
     * 0- 点在线上，也即是三点共线
     * -1，1都表示在线外
     */
    public int test(Vector v) {
        if (v.getDimension() == 2) {
            return InfiniteLine.test2D(v, startPoint, endPoint);
        } else {
            return InfiniteLine.test3D(v, startPoint, endPoint);
        }
    }

    /**
     * 投影到XOY的2D平面上
     *
     * @return
     */
    public InfiniteLine flap() {
        Vector2D s = startPoint.flap();
        Vector2D e = endPoint.flap();
        if (s.equals(e)) {
            return null;
        }
        return new InfiniteLine(s, e);
    }

}
