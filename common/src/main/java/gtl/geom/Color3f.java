package gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Color3f implements gtl.io.Serializable {
    private static final long serialVersionUID = 1L;

    protected float r;
    protected float g;
    protected float b;

    public Color3f(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color3f() {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public float getRedValue() {
        return r;
    }

    public void setRedValue(float r) {
        this.r = r;
    }

    public float getGreenValue() {
        return g;
    }

    public void setGreenValue(float g) {
        this.g = g;
    }

    public float getBlueValue() {
        return b;
    }

    public void setBlueValue(float b) {
        this.b = b;
    }

    @Override
    public Object clone() {
        return new Color3f(r, g, b);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        r = in.readFloat();
        g = in.readFloat();
        b = in.readFloat();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeFloat(r);
        out.writeFloat(g);
        out.writeFloat(b);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 32 * 3;
    }
}
