package cn.edu.cug.cs.gtl.geom;

import java.io.*;
import java.util.Arrays;

public class IndexedPolyline extends Geometry implements Lineal {
    private static final long serialVersionUID = 1L;


    /**
     * 节点数组
     */
    private VectorSequence coordinates;
    private ColorSequence colorSequence;

    /**
     * 第0个整数表示简单线对象的个数n
     * 第一个整数表示，第一个简单线对象的节点个数
     * 接下来是，第一个简单线对象的所有的节点下标
     * ...
     * 第二个简单线对象
     * 第三个简单线对象
     * ......
     * 第n个简单线对象
     */
    private int[] indices;
    private int width;
    private int style;

    public IndexedPolyline(VectorSequence vectorSequence, int[] indices) {
        this.coordinates = VectorSequence.create(vectorSequence);
        this.indices = indices;
        this.geometryType = INDEXEDPOLYLINE;
    }

    public IndexedPolyline(int dim) {
        this.coordinates = VectorSequence.create(dim);
        this.indices = null;
        this.geometryType = INDEXEDPOLYLINE;
    }

    public IndexedPolyline() {
    }

    public VectorSequence getVectorSequence() {
        return coordinates;
    }

    public void setVectorSequence(VectorSequence vectorSequence) {
        this.coordinates = vectorSequence;
    }

    public ColorSequence getColorSequence() {
        return colorSequence;
    }

    public void setColorSequence(ColorSequence colorSequence) {
        this.colorSequence = colorSequence;
    }

    public int[] getIndices() {
        return indices;
    }

    /**
     * 第0个整数表示简单线对象的个数n
     * 第一个整数表示，第一个简单线对象的节点个数
     * 接下来是，第一个简单线对象的所有的节点下标
     * ...
     * 第二个简单线对象
     * 第三简单线对象
     * ......
     * 第n个简单线对象
     *
     * @param indices
     */
    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    @Override
    public boolean isEmpty() {
        return this.coordinates == null || this.coordinates.size() == 0;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        super.load(in);
        coordinates.load(in);
        int s = in.readInt();
        if (s > 0) {
            this.indices = new int[s];
            for (int i = 0; i < s; ++i)
                this.indices[i] = in.readInt();
        }

        width = in.readInt();
        style = in.readInt();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        coordinates.store(out);
        int s = indices == null ? 0 : indices.length;
        out.writeInt(s);
        if (s > 0) {
            for (int v : indices) {
                out.writeInt(v);
            }
        }

        out.writeInt(width);
        out.writeInt(style);
        return true;
    }

    @Override
    public long getByteArraySize() {
        int s = this.indices == null ? 0 : this.indices.length; // 传入的索引数组长度
        return this.coordinates.getByteArraySize() + s + 4 + 4 + 4; // 节点数组大小 + 索引数组长度 + 数组内容 + width + style
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof IndexedPolyline) {
            super.copyFrom(i);
            this.setStyle(((IndexedPolyline) i).getStyle());
            this.setWidth(((IndexedPolyline) i).getWidth());
            this.setVectorSequence((VectorSequence) ((IndexedPolyline) i).getVectorSequence().clone());
            int[] idx = ((IndexedPolyline) i).getIndices();
            if (idx != null) {
                int[] idx2 = Arrays.copyOf(idx, idx.length);
                this.setIndices(idx2);
            }
        }
    }

    @Override
    public IndexedPolyline clone() {
        IndexedPolyline ip2 = new IndexedPolyline(this.getDimension());
        ip2.copyFrom(this);
        return ip2;
    }
}
