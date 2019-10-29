package cn.edu.cug.cs.gtl.mps;

import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Vec1i implements Storable {
    int _x;

    public Vec1i() {
        this(0);
    }

    public Vec1i(int value) {
        this._x = value;
    }

    public int getX() {
        return _x;
    }

    public void setX(int value) {
        this._x = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec1i)) return false;

        Vec1i vec1i = (Vec1i) o;

        return _x == vec1i._x;
    }

    @Override
    public int hashCode() {
        return _x;
    }

    @Override
    public Object clone() {
        return new Vec1i(this._x);
    }

    @Override
    public boolean load(DataInput dataInput) throws IOException {
        this._x = dataInput.readInt();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this._x);
        return true;
    }
}
