package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Color4b extends Color3b {
    private static final long serialVersionUID = 1L;

    protected byte a;

    public Color4b(byte r, byte g, byte b, byte a) {
        super(r, g, b);
        this.a = a;
    }

    public Color4b(byte r, byte g, byte b) {
        super(r, g, b);
        this.a = 0;
    }

    public Color4b() {
        super((byte) 0, (byte) 0, (byte) 0);
        this.a = 0;
    }

    public byte getAlphaValue() {
        return a;
    }

    public void setAlphaValue(byte a) {
        this.a = a;
    }

    @Override
    public Object clone() {
        return new Color4b(r, g, b, a);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        r = in.readByte();
        g = in.readByte();
        b = in.readByte();
        a = in.readByte();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeByte(r);
        out.writeByte(g);
        out.writeByte(b);
        out.writeByte(a);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 8 * 4;
    }
}
