package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.math.MathSuits;
import cn.edu.cug.cs.gtl.math.MathSuits;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-3-24.
 */
public class Vertex2D implements Serializable, Comparable<Vertex2D> {
    private static final long serialVersionUID = 1L;

    /**
     * The value used to indicate a null or missing ordinate value.
     * In particular, used for the value of ordinates for dimensions
     * greater than the defined dimension of a coordinate.
     */
    public static final double NULL_ORDINATE = Double.NaN;

    /**
     * Standard ordinate index values
     */
    public static final int X = 0;
    public static final int Y = 1;

    /**
     * The x-coordinate.
     */
    public double x;
    /**
     * The y-coordinate.
     */
    public double y;


    public Vertex2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vertex2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vertex2D(Vertex2D c) {
        this.x = c.x;
        this.y = c.y;
    }

    /**
     * The "perp dot product"  for  and  vectors in the plane is a modification of the two-dimensional dot product
     * in which  is replaced by the perpendicular vector rotated  to the left defined by Hill (1994).
     * It satisfies the identities where  is the angle from vector  to vector .
     *
     * @param u
     * @param v
     * @return ref:http://mathworld.wolfram.com/PerpDotProduct.html
     */
    public static double perpProduct(Vertex2D u, Vertex2D v) {
        return ((u).x * (v).y - (u).y * (v).x);
    }

    @Override
    public Object clone() {
        return (Object) new Vertex2D(this.x, this.y);
    }

    /**
     * Sets this <code>Vertex</code>s (x,y,z) values to that of <code>other</code>.
     *
     * @param other the <code>Vertex</code> to copy
     */
    public void setCoordinate(Vertex2D other) {
        Vertex2D c = (Vertex2D) other;
        x = c.x;
        y = c.y;
    }

    public double getOrdinate(int ordinateIndex) {
        switch (ordinateIndex) {
            case X:
                return x;
            case Y:
                return y;
        }
        throw new IllegalArgumentException("Invalid ordinate index: " + ordinateIndex);
    }

    public void setOrdinate(int ordinateIndex, double value) {
        switch (ordinateIndex) {
            case X:
                x = value;
                break;
            case Y:
                y = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid ordinate index: " + ordinateIndex);
        }
    }

    public boolean equals(Vertex2D c, double tolerance) {
        if (!MathSuits.equalsWithTolerance(this.x, c.getOrdinate(X), tolerance)) {
            return false;
        }
        if (!MathSuits.equalsWithTolerance(this.y, c.getOrdinate(Y), tolerance)) {
            return false;
        }
        return true;
    }

    public double distance(Vertex2D c) {
        double dx = x - c.getOrdinate(X);
        double dy = y - c.getOrdinate(Y);
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Vertex) {
            Vertex c = (Vertex) i;
            this.x = c.getOrdinate(X);
            this.y = c.getOrdinate(Y);
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.x = in.readDouble();
        this.y = in.readDouble();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeDouble(this.x);
        out.writeDouble(this.y);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 16;
    }

    @Override
    public int compareTo(Vertex2D other) {
        if (x < other.x) return -1;
        if (x > other.x) return 1;
        if (y < other.y) return -1;
        if (y > other.y) return 1;
        return 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex2D vertex2D = (Vertex2D) o;

        if (Double.compare(vertex2D.x, x) != 0) return false;
        return Double.compare(vertex2D.y, y) == 0;
    }

    @Override
    public String toString() {
        return "Vertex2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
