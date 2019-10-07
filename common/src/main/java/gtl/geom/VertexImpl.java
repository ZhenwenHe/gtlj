package gtl.geom;

import gtl.math.MathSuits;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-3-16.
 */
public class VertexImpl extends Vertex {

    private static final long serialVersionUID = 1L;


    /**
     * Constructs a <code>Vertex</code> at (x,y,z).
     *
     * @param x the x-value
     * @param y the y-value
     * @param z the z-value
     */
    public VertexImpl(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a <code>Vertex</code> at (0,0,NaN).
     */
    public VertexImpl() {
        this(0.0, 0.0);
    }

    /**
     * Constructs a <code>Vertex</code> having the same (x,y,z) values as
     * <code>other</code>.
     *
     * @param c the <code>Vertex</code> to copy.
     */
    public VertexImpl(VertexImpl c) {
        this(c.x, c.y, c.z);
    }

    /**
     * Constructs a <code>Vertex</code> at (x,y,NaN).
     *
     * @param x the x-value
     * @param y the y-value
     */
    public VertexImpl(double x, double y) {
        this(x, y, NULL_ORDINATE);
    }


    public Object clone() {
        return (Object) new VertexImpl(this.x, this.y, this.z);
    }

    /**
     * Sets this <code>Vertex</code>s (x,y,z) values to that of <code>other</code>.
     *
     * @param other the <code>Vertex</code> to copy
     */
    public void setCoordinate(Vertex other) {
        VertexImpl c = (VertexImpl) other;
        x = c.x;
        y = c.y;
        z = c.z;
    }

    @Override
    public void setCoordinate(Vector other) {
        x = other.getX();
        y = other.getY();
        z = other.getZ();
    }

    /**
     * Gets the ordinate value for the given index.
     * The supported values for the index are
     * {@link X}, {@link Y}, and {@link Z}.
     *
     * @param ordinateIndex the ordinate index
     * @return the value of the ordinate
     * @throws IllegalArgumentException if the index is not valid
     */
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

    /**
     * Sets the ordinate for the given index
     * to a given value.
     * The supported values for the index are
     * {@link X}, {@link Y}, and {@link Z}.
     *
     * @param ordinateIndex the ordinate index
     * @param value         the value to set
     * @throws IllegalArgumentException if the index is not valid
     */
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

    /**
     * Returns whether the planar projections of the two <code>Vertex</code>s
     * are equal.
     *
     * @param other a <code>Vertex</code> with which to do the 2D comparison.
     * @return <code>true</code> if the x- and y-coordinates are equal; the
     * z-coordinates do not have to be equal.
     */
    public boolean equals2D(Vertex other) {
        VertexImpl c = (VertexImpl) other;
        if (x != c.x) {
            return false;
        }
        if (y != c.y) {
            return false;
        }
        return true;
    }

    /**
     * Tests if another coordinate has the same values for the X and Y ordinates.
     * The Z ordinate is ignored.
     *
     * @param c a <code>Vertex</code> with which to do the 2D comparison.
     * @return true if <code>other</code> is a <code>Vertex</code>
     * with the same values for X and Y.
     */
    public boolean equals2D(Vertex c, double tolerance) {
        if (!MathSuits.equalsWithTolerance(this.x, c.getOrdinate(X), tolerance)) {
            return false;
        }
        if (!MathSuits.equalsWithTolerance(this.y, c.getOrdinate(Y), tolerance)) {
            return false;
        }
        return true;
    }

    /**
     * Tests if another coordinate has the same values for the X, Y and Z ordinates.
     *
     * @param other a <code>Vertex</code> with which to do the 3D comparison.
     * @return true if <code>other</code> is a <code>Vertex</code>
     * with the same values for X, Y and Z.
     */
    public boolean equals3D(Vertex other) {
        VertexImpl c = (VertexImpl) other;
        return (x == c.x) && (y == c.y) &&
                ((z == c.z) ||
                        (Double.isNaN(z) && Double.isNaN(c.z)));
    }

    /**
     * Tests if another coordinate has the same value for Z, within a tolerance.
     *
     * @param c         a coordinate
     * @param tolerance the tolerance value
     * @return true if the Z ordinates are within the given tolerance
     */
    public boolean equalInZ(Vertex c, double tolerance) {
        return MathSuits.equalsWithTolerance(this.z, c.getOrdinate(Z), tolerance);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vertex)) {
            return false;
        }
        return equals3D((Vertex) other);
    }


    /**
     * Computes the 2-dimensional Euclidean distance2D to another location.
     * The Z-ordinate is ignored.
     *
     * @param c a point
     * @return the 2-dimensional Euclidean distance2D between the locations
     */
    public double distance2D(Vertex c) {
        double dx = x - c.getOrdinate(X);
        double dy = y - c.getOrdinate(Y);
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Computes the 3-dimensional Euclidean distance2D to another location.
     *
     * @param c a coordinate
     * @return the 3-dimensional Euclidean distance2D between the locations
     */
    public double distance3D(Vertex cp) {
        VertexImpl c = (VertexImpl) cp;
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

    /**
     * Compares this {@link Vertex} with the specified {@link Vertex} for order.
     * This method ignores the z value when making the comparison.
     * Returns:
     * <UL>
     * <LI> -1 : this.x &lt; other.x || ((this.x == other.x) &amp;&amp; (this.y &lt; other.y))
     * <LI> 0 : this.x == other.x &amp;&amp; this.y = other.y
     * <LI> 1 : this.x &gt; other.x || ((this.x == other.x) &amp;&amp; (this.y &gt; other.y))
     * <p>
     * </UL>
     * Note: This method assumes that ordinate values
     * are valid numbers.  NaN values are not handled correctly.
     *
     * @param o the <code>Vertex</code> with which this <code>Vertex</code>
     *          is being compared
     * @return -1, zero, or 1 as this <code>Vertex</code>
     * is less than, equal to, or greater than the specified <code>Vertex</code>
     */
    @Override
    public int compareTo(Vertex other) {
        if (x < other.x) return -1;
        if (x > other.x) return 1;
        if (y < other.y) return -1;
        if (y > other.y) return 1;
        return 0;
    }

    /**
     * Gets a hashcode for this coordinate.
     *
     * @return a hashcode for this coordinate
     */
    @Override
    public int hashCode() {
        //Algorithm from Effective Java by Joshua Bloch [Jon Aquino]
        int result = 17;
        result = 37 * result + hashCode(x);
        result = 37 * result + hashCode(y);
        return result;
    }

}
