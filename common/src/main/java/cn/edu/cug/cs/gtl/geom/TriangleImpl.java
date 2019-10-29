package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.jts.geom.Geom2DSuits;
import cn.edu.cug.cs.gtl.jts.geom.Geom2DSuits;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by ZhenwenHe on 2017/3/13.
 * 三角形采用三个顶点来表示，
 * 三个顶点按照逆时针方向进行存储。
 */
class TriangleImpl implements Triangle {

    private static final long serialVersionUID = 1L;

    protected Vector vertices[] = null;//逆时针方向存储点

    public TriangleImpl() {
        this.vertices = new Vector[3];
        for (int i = 0; i < 3; ++i) {
            this.vertices[i] = new VectorImpl();
        }
    }

    public TriangleImpl(Vector[] vertices) {

        try {
            this.vertices = new Vector[3];
            for (int i = 0; i < 3; ++i) {
                this.vertices[i] = (Vector) vertices[i].clone();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public TriangleImpl(Vector v0, Vector v1, Vector v2) {
        try {
            this.vertices = new Vector[3];
            this.vertices[0] = (Vector) v0.clone();
            this.vertices[1] = (Vector) v1.clone();
            this.vertices[2] = (Vector) v2.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vector getVertex(int i) {
        return this.vertices[i % 3];
    }

    /**
     * 默认按照逆时针方向返回三角形的顶点数组
     *
     * @return
     */
    @Override
    public Vector[] getVertices() {
        return this.vertices;
    }

    /**
     * 按照顺时针方向返回节点数组，
     * 也就是交换V1和V2的位置后返回
     *
     * @return
     */
    @Override
    public Vector[] getClockwiseVertices() {
        Vector[] newVectorArray = new Vector[3];
        newVectorArray[0] = vertices[0];
        newVectorArray[1] = vertices[2];
        newVectorArray[2] = vertices[1];
        return newVectorArray;
    }

    /**
     * 默认按照逆时针方向返回三角形的顶点数组
     *
     * @return
     */
    @Override
    public VectorSequence getVectorSequence() {
        PackedVectorSequence pvs = new PackedVectorSequence(vertices);
        return pvs;
    }

    @Override
    public VectorSequence getClockwiseVectorSequence() {
        PackedVectorSequence pvs = new PackedVectorSequence(3);
        pvs.add(vertices[0]);
        pvs.add(vertices[2]);
        pvs.add(vertices[1]);
        return pvs;
    }

    @Override
    public double getAngle(int i) {
        return this.vertices[i].angle(this.vertices[(i + 2) % 3], this.vertices[(i + 1) % 3]);
    }

    @Override
    public double getHeight(int v) {
        return Geom3DSuits.perpendicularDistance(this.vertices[(v + 2) % 3], this.vertices[(v + 1) % 3], this.vertices[v]);
    }

    @Override
    public double getPerimeter() {
        return this.getEdgeLength(0, 1) + this.getEdgeLength(1, 2) + this.getEdgeLength(2, 0);
    }

    /**
     * 求矢量 BA 与 BC 的叉积的模，该值为平行四边形的面积，三角形的面积为其一半
     * ----------------------------------
     * B (V0)
     * / \
     * /     \
     * /         \
     * /             \
     * (V1)  A --------------- C (V2)
     * ----------------------------------
     *
     * @return
     */
    @Override
    public double getArea() {
        Vector p = vertices[1].subtract(vertices[0]).crossProduct(
                vertices[2].subtract(vertices[0]));
        return Math.sqrt(p.dotProduct(p)) / 2.0;
    }

    @Override
    public double getEdgeLength(int s, int e) {
        return this.vertices[e % 3].subtract(this.vertices[s % 3]).length();
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Triangle) {
            Vector[] vv = ((TriangleImpl) i).vertices;
            int k = 0;
            for (Vector v : vv) {
                this.vertices[k].reset(v.getCoordinates());
                ++k;
            }
        }
    }

    @Override
    public LineSegment getEdge(int s, int e) {
        return new LineSegment(vertices[s], vertices[e]);
    }

    @Override
    public int getDimension() {
        return this.vertices[0].getDimension();
    }

    @Override
    public Object clone() {
        return new TriangleImpl(this.vertices);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        for (Vector v : this.vertices) {
            v.load(in);
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        for (Vector v : this.vertices)
            v.store(out);
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 0;
        for (Vector v : this.vertices)
            len += v.getByteArraySize();
        return len;
    }

    @Override
    public Envelope getEnvelope() {
        int dims = this.getDimension();
        double[] origin = new double[dims];
        double[] topRight = new double[dims];
        for (int i = 0; i < dims; ++i) {
            origin[i] = this.vertices[0].getOrdinate(i);
            topRight[i] = this.vertices[0].getOrdinate(i);
        }
        for (int c = 1; c < 3; ++c) {
            for (int i = 0; i < dims; ++i) {
                origin[i] = Math.min(origin[i], this.vertices[c].getOrdinate(i));
                topRight[i] = Math.max(topRight[i], this.vertices[c].getOrdinate(i));
            }
        }
        return new Envelope(origin, topRight);
    }

    @Override
    public Triangle flap() {
        Vector2D v0 = vertices[0].flap();
        Vector2D v1 = vertices[1].flap();
        Vector2D v2 = vertices[2].flap();
        if (v0.equals(v1) || v0.equals(v2) || v1.equals(v2)) {
            return null;
        } else
            return new TriangleImpl(v0, v1, v2);
    }

    @Override
    public Vector getCenter() {
        return this.getCentroid();
    }

    @Override
    public Vector getOrthocenter() {
        //垂线
        return null;
    }

    @Override
    public Vector getCentroid() {
        return null;
    }

    @Override
    public Vector getCircumcenter() {
        return null;
    }

    @Override
    public Vector getIncircleCenter() {
        return null;
    }

    @Override
    public boolean intersects(Envelope e) {
        return false;
    }

    @Override
    public boolean intersects(Triangle e) {
        return false;
    }

    /**
     * 点P是否在三角形内,边上或顶点，返回true，否则返回false
     *
     * @param p
     * @return
     */
    @Override
    public boolean contains(Vector p) {
        return Geom3DSuits.pointInTriangle(p, vertices[0], vertices[2], vertices[1]);
    }

    @Override
    public boolean contains(double x, double y) {
        return Geom2DSuits.contains(this, x, y);
    }

    @Override
    public boolean contains(double x, double y, double z) {
        return Geom3DSuits.pointInTriangle(new VectorImpl(x, y, z), vertices[0], vertices[2], vertices[1]);
    }

    @Override
    public boolean contains(LineSegment p) {
        return contains(p.getStartPoint()) && contains(p.getEndPoint());
    }

    @Override
    public boolean contains(Triangle p) {
        return contains(p.getVertex(0)) && contains(p.getVertex(1))
                && contains(p.getVertex(2));
    }

    @Override
    public boolean contains(Envelope p) {
        return contains(p.getLowVector()) && contains(p.getHighVector());
    }

    @Override
    public boolean isEquilateralTriangle() {
        return false;
    }

    @Override
    public boolean isIsoscelesTriangle() {
        return false;
    }

    @Override
    public boolean isRightAngledTriangle() {
        return false;
    }

    @Override
    public boolean isIsoscelesRightTriangle() {
        return false;
    }

    @Override
    public boolean isScaleneTriangle() {
        return false;
    }


}
