package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Models an OGC SFS <code>LinearRing</code>.
 * A <code>LinearRing</code> is a {@link LineString} which is both closed and simple.
 * In other words,
 * the first and last coordinate in the ring must be equal,
 * and the interior of the ring must not self-intersect.
 * Either orientation of the ring is allowed.
 * <p>
 * A ring must have either 0 or 4 or more points.
 * The first and last points must be equal (in 2D).
 * If these conditions are not met, the constructors throw
 * an {@link IllegalArgumentException}
 *
 * @version 1.7
 */
public class LinearRing extends LineString {

    /**
     * The minimum number of vertices allowed in a valid non-empty ring (= 4).
     * Empty rings with 0 vertices are also valid.
     */
    public static final int MINIMUM_VALID_SIZE = 4;
    private static final long serialVersionUID = 1848399704909383811L;

    /**
     * LinearRing有参构造函数
     *
     * @param coordinates：包括线环的维度和坐标
     */
    public LinearRing(VectorSequence coordinates) {
        super(coordinates);
        this.geometryType = LINEARRING;
    }

    /**
     * LinearRing有参构造函数
     *
     * @param coordinates:线环的坐标点数组
     */
    public LinearRing(Vector[] coordinates) {
        super(coordinates);
        this.geometryType = LINEARRING;
    }

    /**
     * LinearRing有参构造函数
     *
     * @param dim：线环的维度
     */
    public LinearRing(int dim) {
        super(dim);
        this.geometryType = LINEARRING;
    }

    public LinearRing() {
        super();
        this.geometryType = LINEARRING;
    }

    /**
     * 获得线环的序列，包括维度和坐标
     *
     * @return
     */
    public VectorSequence getVectorSequence() {
        return coordinates;
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
        return super.getByteArraySize();
    }

    /**
     * 拷贝本对象内容到其他同类对象
     *
     * @return
     */
    @Override
    public LinearRing clone() {
        LinearRing p = new LinearRing(this.coordinates);
        p.copyFrom(this);
        return p;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
