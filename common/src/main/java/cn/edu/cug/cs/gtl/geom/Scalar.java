package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-3-26.
 */
public class Scalar implements Serializable {
    private static final long serialVersionUID = 1L;

    double scalar;

    public Scalar() {
    }

    public Scalar(double scalar) {
        this.scalar = scalar;
    }

    public Scalar multiply(Scalar s) {
        return new Scalar(s.scalar * this.scalar);
    }

    public Vector multiply(Vector s) {
        Vector v = (Vector) s.clone();
        for (int i = 0; i < s.getDimension(); ++i)
            v.setOrdinate(i, s.getOrdinate(i) * this.scalar);
        return v;
    }

    public Scalar add(Scalar s) {
        return new Scalar(s.scalar + this.scalar);
    }

    public Scalar subtract(Scalar s) {
        return new Scalar(this.scalar - s.scalar);
    }

    public Scalar divide(Scalar s) {
        return new Scalar(this.scalar / s.scalar);
    }

    public double getScalar() {
        return this.scalar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scalar scalar1 = (Scalar) o;

        return Double.compare(scalar1.scalar, scalar) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(scalar);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "Scalar{" +
                "scalar=" + scalar +
                '}';
    }

    @Override
    public Object clone() {
        return new Scalar(this.scalar);
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Scalar) {
            this.scalar = ((Scalar) i).scalar;
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.scalar = in.readDouble();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeDouble(this.scalar);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 8;
    }
}
