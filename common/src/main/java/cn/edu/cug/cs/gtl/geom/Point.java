package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

import cn.edu.cug.cs.gtl.math.MathSuits;
import cn.edu.cug.cs.gtl.math.MathSuits;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Represents a single point.
 * <p>
 * A <code>PointShape</code> is topologically valid if and only if:
 * <ul>
 * <li>the coordinate which defines it (if any) is a valid coordinate
 * (i.e. does not have an <code>NaN</code> X or Y ordinate)
 * </ul>
 *
 * @version 1.7
 */
public class Point extends Geometry implements Puntal {
    private static final long serialVersionUID = 1L;

    /**
     * The <code>Vertex</code> wrapped by this <code>PointShape</code>.
     */
    private VectorSequence coordinates;


    /**
     * Point的一种有参的构造函数
     *
     * @param coordinates:表示点向量序列，包括点的维度和坐标
     */
    public Point(VectorSequence coordinates) {
        geometryType = POINT;
        this.coordinates = VectorSequence.create(coordinates);
        this.envelope = coordinates.getEnvelope();
    }

    /**
     * Point的无参构造函数，定义之后函数会自动定义2维坐标点
     */
    public Point() {
        geometryType = POINT;
        this.coordinates = VectorSequence.create(2);
        this.coordinates.add(0, 0);
        this.envelope = Envelope.create(Vector.create(0, 0), MathSuits.EPSILON);
    }

    /**
     * 判断Point对象是否为空
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return this.coordinates == null || this.coordinates.size() == 0;
    }

    /**
     * Point有参构造函数
     * 现只实现2维/3维点的构建
     *
     * @param dim：表示维度
     */
    public Point(int dim) {
        geometryType = POINT;
        this.coordinates = VectorSequence.create(dim);
        if (dim == 2) {
            this.coordinates.add(0, 0);
            this.envelope = Envelope.create(Vector.create(0, 0), MathSuits.EPSILON);
        } else {
            this.coordinates.add(0, 0, 0);
            this.envelope = Envelope.create(Vector.create(0, 0, 0), MathSuits.EPSILON);
        }
    }

    /**
     * Point有参构造函数，构造三维点
     *
     * @param x：Point的横坐标
     * @param y：Point的纵坐标
     * @param z：Point的z坐标
     */
    public Point(double x, double y, double z) {
        geometryType = POINT;
        this.coordinates = VectorSequence.create(3);
        this.coordinates.add(x, y, z);
        this.envelope = Envelope.create(Vector.create(x, y, z), MathSuits.EPSILON);
    }

    /**
     * Point有参构造函数，构造二维点
     *
     * @param x：Point的横坐标
     * @param y：Point的纵坐标
     */
    public Point(double x, double y) {
        geometryType = POINT;
        this.coordinates = VectorSequence.create(2);
        this.coordinates.add(x, y);
        this.envelope = Envelope.create(Vector.create(x, y), MathSuits.EPSILON);
    }

    /**
     * Point的一种有参的构造函数
     *
     * @param v:表示点向量坐标
     */
    public Point(Vector v) {
        geometryType = POINT;
        this.coordinates = VectorSequence.create(v.getDimension());
        this.coordinates.add(v);
        this.envelope = Envelope.create(v, MathSuits.EPSILON);
    }

    /**
     * 获得点向量序列，包括维度和坐标
     *
     * @return
     */
    public VectorSequence getVectorSequence() {
        return coordinates;
    }

    /**
     * 定义点的维度
     *
     * @param dim：点的维度
     */
    @Override
    public void makeDimension(int dim) {
        super.makeDimension(dim);
        this.coordinates.makeDimension(dim);
    }

    /**
     * 获得点的横坐标
     *
     * @return
     */
    public double getX() {
        return this.coordinates.getX(0);
    }

    /**
     * 获得点的纵坐标
     *
     * @return
     */
    public double getY() {
        return this.coordinates.getY(0);
    }

    /**
     * 获得点的z坐标
     *
     * @return
     */
    public double getZ() {
        return this.coordinates.getZ(0);
    }

    /**
     * 从其它同类对象拷贝内容填充本对象
     *
     * @param i:其它任何实现了Puntal接口的对象
     */
    @Override
    public void copyFrom(Object i) {
        if (i instanceof Point) {
            Point g = (Point) i;
            super.copyFrom(i);
            this.coordinates = VectorSequence.create(g.coordinates);
        }
    }

    /**
     * 从存储对象中加载数据，填充本对象
     *
     * @param in:表示可以读取的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        super.load(in);
        this.coordinates.load(in);
        return true;
    }

    /**
     * 将本对象写入存储对象中，存储对象可能是内存、文件、管道等
     *
     * @param out:表示可以写入的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        this.coordinates.store(out);
        return true;
    }

    /**
     * 对象序列化后的字节数，
     * 默认实现为将其写入一个字节数组中，然后返回该字节数
     *
     * @return
     */
    @Override
    public long getByteArraySize() {
        return super.getByteArraySize() + this.coordinates.getByteArraySize();
    }

    /**
     * 拷贝本对象内容到其他同类对象
     *
     * @return
     */
    @Override
    public Point clone() {
        Point p = new Point(this.coordinates);
        p.copyFrom(this);
        return p;
    }

}


