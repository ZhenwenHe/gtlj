package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Color3b implements Serializable {
    private static final long serialVersionUID = 1L;

    protected byte r;
    protected byte g;
    protected byte b;

    public Color3b(byte r, byte g, byte b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color3b() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public byte getRedValue() {
        return r;
    }

    public void setRedValue(byte r) {
        this.r = r;
    }

    public byte getGreenValue() {
        return g;
    }

    public void setGreenValue(byte g) {
        this.g = g;
    }

    public byte getBlueValue() {
        return b;
    }

    public void setBlueValue(byte b) {
        this.b = b;
    }

    @Override
    public Object clone() {
        return new Color3b(r, g, b);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        r = in.readByte();
        g = in.readByte();
        b = in.readByte();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeByte(r);
        out.writeByte(g);
        out.writeByte(b);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 8 * 3;
    }
}
