package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class IndexedPolygon extends Geometry implements Polygonal {
    private static final long serialVersionUID = 1L;
    /**
     * 节点数组
     */
    private VectorSequence coordinates;
    private ColorSequence colorSequence;
    /**
     * 第0个整数表示简单多边形对象的个数n
     * 第一个整数表示，第一个简单多边形对象的节点个数
     * 接下来是，第一个简单多边形对象的所有的节点下标
     * ...
     * 第二个简单多边形对象
     * 第三简单多边形对象
     * ......
     * 第n个简单多边形对象
     */
    private int[] indices;

    private long materialID;

    private TextureParameter textureParameter = null;

    public IndexedPolygon(VectorSequence coordinates, int[] indices) {
        this.coordinates = VectorSequence.create(coordinates);
        this.indices = indices;
        this.geometryType = INDEXEDPOLYGON;
    }

    public IndexedPolygon(int dim) {
        this.coordinates = VectorSequence.create(dim);
        this.indices = null;
        this.geometryType = INDEXEDPOLYGON;
    }

    public IndexedPolygon() {
    }

    public VectorSequence getCoordinates() {
        return coordinates;
    }

    public VectorSequence getVectorSequence() {
        return coordinates;
    }

    public ColorSequence getColorSequence() {
        return colorSequence;
    }

    public void setColorSequence(ColorSequence colorSequence) {
        this.colorSequence = colorSequence;
    }

    public void setCoordinates(VectorSequence coordinates) {
        this.coordinates = coordinates;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public long getMaterialID() {
        return materialID;
    }

    public void setMaterialID(long materialID) {
        this.materialID = materialID;
    }

    @Override
    public boolean isEmpty() {
        return this.coordinates.isEmpty();
    }


    @Override
    public void copyFrom(Object i) {
        if (i instanceof IndexedPolygon) {
            super.copyFrom(i);
            this.setMaterialID(((IndexedPolygon) i).getMaterialID());
            this.setCoordinates((VectorSequence) ((IndexedPolygon) i).getCoordinates().clone());
            int[] idx = ((IndexedPolygon) i).getIndices();
            if (idx != null) {
                int[] idx2 = Arrays.copyOf(idx, idx.length);
                this.setIndices(idx2);
            }
        }
    }

    @Override
    public IndexedPolygon clone() {
        IndexedPolygon g = (IndexedPolygon) super.clone();
        if (this.coordinates != null)
            g.coordinates = (VectorSequence) this.coordinates.clone();
        else
            g.coordinates = null;

        if (this.indices != null)
            g.indices = Arrays.copyOf(this.indices, this.indices.length);
        else
            g.indices = null;

        g.materialID = this.materialID;

        if (this.textureParameter != null)
            g.textureParameter = (TextureParameter) this.textureParameter.clone();
        else
            g.textureParameter = null;

        return g;
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

        s = in.readInt();
        if (s > 0) {
            this.textureParameter = new TextureParameter();
            this.textureParameter.load(in);
        }

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

        if (this.textureParameter != null) {
            out.writeInt(1);
            this.textureParameter.store(out);
        } else {
            out.writeInt(0);
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 0;
        len = coordinates.getByteArraySize() + indices.length + 8 + 4;
        return len;
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
