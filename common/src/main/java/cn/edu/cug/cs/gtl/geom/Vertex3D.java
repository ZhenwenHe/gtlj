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
public class Vertex3D implements Serializable, Comparable<Vertex3D> {
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
    public static final int Z = 2;

    /**
     * The x-coordinate.
     */
    public double x;
    /**
     * The y-coordinate.
     */
    public double y;
    /**
     * The z-coordinate.
     */
    public double z;

    /**
     * Constructs a <code>Vertex</code> at (x,y,z).
     *
     * @param x the x-value
     * @param y the y-value
     * @param z the z-value
     */
    public Vertex3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a <code>Vertex</code> at (0,0,NaN).
     */
    public Vertex3D() {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Constructs a <code>Vertex</code> having the same (x,y,z) values as
     * <code>other</code>.
     *
     * @param c the <code>Vertex</code> to copy.
     */
    public Vertex3D(Vertex3D c) {
        this(c.x, c.y, c.z);
    }


    public Object clone() {
        return (Object) new Vertex3D(this.x, this.y, this.z);
    }

    public void setCoordinate(Vertex c) {
        x = c.x;
        y = c.y;
        z = c.z;
    }

    public void setCoordinate(Vector other) {
        x = other.getX();
        y = other.getY();
        z = other.getZ();
    }

    public double getOrdinate(int ordinateIndex) {
        switch (ordinateIndex) {
            case X:
                return x;
            case Y:
                return y;
            case Z:
                return z;
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
            case Z:
                z = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid ordinate index: " + ordinateIndex);
        }
    }

    public boolean equals2D(Vertex3D other) {
        Vertex3D c = (Vertex3D) other;
        if (x != c.x) {
            return false;
        }
        if (y != c.y) {
            return false;
        }
        return true;
    }

    public boolean equals2D(Vertex3D c, double tolerance) {
        if (!MathSuits.equalsWithTolerance(this.x, c.getOrdinate(X), tolerance)) {
            return false;
        }
        if (!MathSuits.equalsWithTolerance(this.y, c.getOrdinate(Y), tolerance)) {
            return false;
        }
        return true;
    }

    public double distance(Vertex3D c) {
        double dx = x - c.x;
        double dy = y - c.y;
        double dz = z - c.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


    @Override
    public void copyFrom(Object i) {
        if (i instanceof Vertex) {
            Vertex c = (Vertex) i;
            this.x = c.getOrdinate(X);
            this.y = c.getOrdinate(Y);
            this.z = c.getOrdinate(Z);
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.z = in.readDouble();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeDouble(this.x);
        out.writeDouble(this.y);
        out.writeDouble(this.z);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 24;
    }

    @Override
    public int compareTo(Vertex3D other) {
        if (x < other.x) return -1;
        if (x > other.x) return 1;
        if (y < other.y) return -1;
        if (y > other.y) return 1;
        return 0;
    }
}
