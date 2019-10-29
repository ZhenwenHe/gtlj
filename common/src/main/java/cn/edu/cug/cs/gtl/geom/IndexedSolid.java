package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 18-9-1
 */
public class IndexedSolid extends Geometry implements Polyhedral {
    private static final long serialVersionUID = 1L;

    /**
     * 节点数组
     */
    protected VectorSequence coordinates;
    private ColorSequence colorSequence;
    /**
     * 第0个整数表示简单体对象的个数n,每个简单体由多个简单多边形封闭而成，
     * 第一个整数表示，第一个简单体对象的多边形个数
     * 接下来是，每个简单多边形的信息，分别为节点个数和对应的节点下标
     * ...
     * 第二个简单体对象
     * 第三个简单体对象
     * ......
     * 第n个简单体对象
     */
    protected int[] indices;

    /**
     * 材质ID
     */
    protected long materialID;

    private TextureParameter textureParameter = null;

    public IndexedSolid() {
        this.geometryType = INDEXEDSOLID;
    }

    public long getMaterialID() {
        return materialID;
    }

    public void setMaterialID(long materialID) {
        this.materialID = materialID;
    }


    public void setDefaultColor(long defaultColor) {
        super.setDefaultColor(new Color(defaultColor));
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public VectorSequence getCoordinates() {
        return coordinates;
    }

    public ColorSequence getColorSequence() {
        return colorSequence;
    }

    public void setCoordinates(VectorSequence coordinates) {
        this.coordinates = coordinates;
    }

    public IndexedSolid(int dim) {
        this.coordinates = VectorSequence.create(dim);
        this.indices = null;
        this.geometryType = INDEXEDSOLID;
    }

    public IndexedSolid(VectorSequence vectorSequence, int[] indices) {
        this.coordinates = VectorSequence.create(vectorSequence);
        this.indices = indices;
        this.geometryType = INDEXEDSOLID;
    }

    @Override
    public boolean isEmpty() {
        return this.coordinates == null || this.coordinates.size() == 0;
    }

    @Override
    public IndexedSolid clone() {
        return (IndexedSolid) super.clone();
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

        materialID = in.readLong();
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
        out.writeLong(materialID);
        return true;
    }

    @Override
    public long getByteArraySize() {
        int s = this.indices == null ? 0 : this.indices.length; // 传入的索引数组长度
        return this.coordinates.getByteArraySize() + s + 4 + 4 + 4; // 节点数组大小 + 索引数组长度 + 数组内容 + width + style
    }

    @Override
    public TextureParameter getTextureParameter() {
        return this.textureParameter;
    }

    @Override
    public void setTextureParameter(TextureParameter textureParameter) {
        this.textureParameter = textureParameter;
    }
}
