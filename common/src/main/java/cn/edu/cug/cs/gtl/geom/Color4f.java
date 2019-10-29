package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Color4f extends Color3f {

    private static final long serialVersionUID = 1L;

    protected float a;

    public Color4f(float r, float g, float b, float a) {
        super(r, g, b);
        this.a = a;
    }

    public Color4f(float r, float g, float b) {
        super(r, g, b);
        this.a = 0.0f;
    }

    public Color4f() {
        super();
        this.a = 0.0f;
    }

    public float getAlphaValue() {
        return a;
    }

    public void setAlphaValue(float a) {
        this.a = a;
    }

    @Override
    public Color4f clone() {
        return new Color4f(r, g, b, a);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        r = in.readFloat();
        g = in.readFloat();
        b = in.readFloat();
        a = in.readFloat();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeFloat(r);
        out.writeFloat(g);
        out.writeFloat(b);
        out.writeFloat(a);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 32 * 4;
    }
}
