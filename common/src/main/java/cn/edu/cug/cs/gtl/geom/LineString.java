package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LineString extends Geometry implements Lineal {
    private static final long serialVersionUID = 1L;

    /**
     * The points of this <code>LineString</code>.
     */
    protected VectorSequence coordinates;

    /**
     * LinearString有参构造函数
     *
     * @param coordinates：包括线的维度和坐标
     */
    public LineString(@NotNull VectorSequence coordinates) {
        this.coordinates = VectorSequence.create(coordinates);
        this.envelope = coordinates.getEnvelope();
        this.geometryType = LINESTRING;
    }


    /**
     * LinearString有参构造函数
     *
     * @param coordinates:线的坐标点数组
     */
    public LineString(Vector[] coordinates) {
        this.coordinates = VectorSequence.create(coordinates);
        this.envelope = this.coordinates.getEnvelope();
        this.geometryType = LINESTRING;
    }

    /**
     * LinearString有参构造函数
     *
     * @param dim：线的维度
     */
    public LineString(int dim) {
        this.coordinates = VectorSequence.create(dim);
        this.geometryType = LINESTRING;
        this.envelope = Envelope.create(dim);
    }

    /**
     * LinearString无参构造函数
     * 默认为2维线对象
     */
    public LineString() {
        this(2);
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
     * 判断线对象是否为空
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return coordinates.size() == 0;
    }

    /**
     * 定义线的维度
     *
     * @param dim：线的维度
     */
    @Override
    public void makeDimension(int dim) {
        super.makeDimension(dim);
        this.coordinates.makeDimension(dim);
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
    public LineString clone() {
        LineString p = new LineString(this.coordinates);
        p.copyFrom(this);
        return p;
    }

    public VectorSequence getVertices() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LineString that = (LineString) o;

        return coordinates != null ? coordinates.equals(that.coordinates) : that.coordinates == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (coordinates != null ? coordinates.hashCode() : 0);
        return result;
    }
}
