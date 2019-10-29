package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class CornerPointGrid extends Geometry implements Polyhedral {
    protected int[] dimension;
    protected int propertiesNum;
    protected double[] pillars;
    protected double[] cells;
    protected boolean[] valid;
    protected double[][] properties;
    private long defaultColor;

    private long materialID;

    public CornerPointGrid() {
    }

    public Geometry clone() {
        CornerPointGrid c = new CornerPointGrid();
        c.copyFrom(this);
        return c;
    }

    public void copyFrom(CornerPointGrid i) {
        if (i instanceof CornerPointGrid) {
            super.copyFrom(i);
            this.dimension = Arrays.copyOf(i.dimension, i.dimension.length);
            propertiesNum = i.propertiesNum;
            this.pillars = Arrays.copyOf(i.pillars, i.pillars.length);
            this.cells = Arrays.copyOf(i.cells, i.cells.length);
            this.valid = Arrays.copyOf(i.valid, i.valid.length);
            this.properties = new double[i.properties.length][propertiesNum];
            for (int j = 0; j < i.properties.length; j++) {
                this.properties[j] = Arrays.copyOf(i.properties[j], i.properties[j].length);
            }
            defaultColor = i.defaultColor;
            materialID = i.materialID;
        }
    }

    public boolean load(DataInput in) throws IOException {
        dimension = new int[3];
        propertiesNum = in.readInt();
        for (int i = 0; i < 3; i++) {
            dimension[i] = in.readInt();
        }
        if (dimension[0] > 0 && dimension[1] > 0 && dimension[2] > 0) {
            pillars = new double[(dimension[0] + 1) * (dimension[1] + 1)];
            for (int i = 0; i < (dimension[0] + 1) * (dimension[1] + 1); i++) {
                pillars[i] = in.readDouble();
            }
            cells = new double[dimension[0] * dimension[1] * dimension[2] * 8];
            for (int i = 0; i < dimension[0] * dimension[1] * dimension[2] * 8; i++) {
                cells[i] = in.readDouble();
            }
            valid = new boolean[dimension[0] * dimension[1] * dimension[2] * 8];
            for (int i = 0; i < dimension[0] * dimension[1] * dimension[2] * 8; i++) {
                valid[i] = in.readBoolean();
            }
            if (propertiesNum > 0) {
                properties = new double[dimension[0] * dimension[1] * dimension[2]][propertiesNum];
                for (int i = 0; i < dimension[0] * dimension[1] * dimension[2]; i++) {
                    for (int j = 0; j < propertiesNum; j++) {
                        properties[i][j] = in.readDouble();
                    }
                }
            }
        }
        defaultColor = in.readLong();
        materialID = in.readLong();
        return true;
    }


    @Override
    public boolean store(DataOutput out) throws IOException {
        for (int d : dimension) {
            out.writeInt(d);
        }
        out.writeInt(propertiesNum);
        if (pillars != null) {
            for (int i = 0; i < pillars.length; i++) {
                out.writeDouble(pillars[i]);
            }
        }
        if (cells != null) {
            for (int i = 0; i < cells.length; i++) {
                out.writeDouble(cells[i]);
            }
        }
        if (valid != null) {
            for (int i = 0; i < valid.length; i++) {
                out.writeBoolean(valid[i]);
            }
        }
        if (properties != null) {
            for (int i = 0; i < properties.length; i++) {
                for (int j = 0; j < properties[i].length; j++) {
                    out.writeDouble(properties[i][j]);
                }
            }
        }
        out.writeLong(defaultColor);
        out.writeLong(materialID);
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public TextureParameter getTextureParameter() {
        return null;
    }

    @Override
    public void setTextureParameter(TextureParameter textureParameter) {

    }
}