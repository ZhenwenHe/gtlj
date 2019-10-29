package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DEMGrid extends Geometry implements Polygonal {
    private static final long serialVersionUID = 1L;

    public DEMGrid(int dim) {
        this.makeDimension(dim);
    }

    public DEMGrid() {
        super();
        this.geometryType = DEMGRID;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public DEMGrid clone() {
        DEMGrid t = new DEMGrid();
        t.copyFrom(this);
        return t;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        return false;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 0;
        return len;
    }

    @Override
    public TextureParameter getTextureParameter() {
        return null;
    }

    @Override
    public void setTextureParameter(TextureParameter textureParameter) {

    }
}
