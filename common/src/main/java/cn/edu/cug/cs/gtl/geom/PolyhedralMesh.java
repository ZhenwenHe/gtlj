package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public abstract class PolyhedralMesh extends IndexedSolid {

    private static final long serialVersionUID = 704600878517053105L;
    //每个单元格的属性数量
    private int propertyNumberPerCell = 0;
    private double[] properties = null;

    public int getPropertyNumberPerCell() {
        return propertyNumberPerCell;
    }

    public double[] getProperties() {
        return properties;
    }

    public double[] getCellProperties(int i) {
        return Arrays.copyOfRange(properties,
                i * this.propertyNumberPerCell,
                (i + 1) * i * this.propertyNumberPerCell);
    }

    public void setProperties(double[] properties) {
        this.properties = properties;
    }

    public abstract int getGeometryType();

    /**
     * 获取每个单体的节点数
     *
     * @return
     */
    public abstract int getVertexNumberPerCell();

    /**
     * 获取单体的个数
     *
     * @return
     */
    public int getCellNumber() {
        if (this.indices == null)
            return 0;
        if (this.indices.length == 0)
            return 0;
        return this.indices.length / getVertexNumberPerCell();
    }

    public PolyhedralMesh() {
        this.geometryType = getGeometryType();
    }

    public void copyFrom(PolyhedralMesh i) {
        if (i.getClass().equals(this.getClass())) {
            super.copyFrom(i);
            this.properties = new double[getCellNumber() * getVertexNumberPerCell()];
            Arrays.copyOf(i.properties, i.properties.length);
        }
    }

    public PolyhedralMesh(int dim) {
        this();
        this.coordinates = VectorSequence.create(dim);
    }

    public PolyhedralMesh(VectorSequence vectorSequence, int[] indices) {
        this();
        this.coordinates = VectorSequence.create(vectorSequence);
        this.indices = indices;
    }


    @Override
    public PolyhedralMesh clone() {
        PolyhedralMesh p = (PolyhedralMesh) super.clone();
        p.properties = new double[getCellNumber() * getVertexNumberPerCell()];
        Arrays.copyOf(this.properties, this.properties.length);
        return p;
    }


    @Override
    public boolean load(DataInput in) throws IOException {
        super.load(in);
        this.propertyNumberPerCell = in.readInt();
        int s = in.readInt();//this.properties==null?0:this.properties.length;
        if (s > 0) {
            this.properties = new double[s];
            for (int i = 0; i < s; ++i) {
                this.properties[i] = in.readDouble();
            }
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        out.writeInt(this.propertyNumberPerCell);
        int s = this.properties == null ? 0 : this.properties.length;
        out.writeInt(s);
        if (s > 0) {
            for (double d : this.properties) {
                out.writeDouble(d);
            }
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = super.getByteArraySize() + 8;
        if (this.properties.length > 0)
            len += 8 * this.properties.length;
        return len;
    }
}
