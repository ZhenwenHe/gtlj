package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-3-16.
 * represent a 3D or 2D vertex
 */
public abstract class Vertex implements Cloneable, Serializable, Comparable<Vertex> {
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
     * Computes a hash code for a double value, using the algorithm from
     * Joshua Bloch's book <i>Effective Java"</i>
     *
     * @return a hashcode for the double value
     */
    public static int hashCode(double x) {
        long f = Double.doubleToLongBits(x);
        return (int) (f ^ (f >>> 32));
    }

    /**
     * Sets this <code>Vertex</code>s (x,y,z) values to that of <code>other</code>.
     *
     * @param other the <code>Vector</code> to copy
     */
    public abstract void setCoordinate(Vector other);

    /**
     * Sets this <code>Vertex</code>s (x,y,z) values to that of <code>other</code>.
     *
     * @param other the <code>Vertex</code> to copy
     */
    public abstract void setCoordinate(Vertex other);

    /**
     * Gets the ordinate value for the given index.
     * The supported values for the index are
     * {@link X}, {@link Y}, and {@link Z}.
     *
     * @param ordinateIndex the ordinate index
     * @return the value of the ordinate
     * @throws IllegalArgumentException if the index is not valid
     */
    public abstract double getOrdinate(int ordinateIndex);

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
    public abstract void setOrdinate(int ordinateIndex, double value);

    /**
     * Returns whether the planar projections of the two <code>Vertex</code>s
     * are equal.
     *
     * @param other a <code>Vertex</code> with which to do the 2D comparison.
     * @return <code>true</code> if the x- and y-coordinates are equal; the
     * z-coordinates do not have to be equal.
     */
    public abstract boolean equals2D(Vertex other);

    /**
     * Tests if another coordinate has the same values for the X and Y ordinates.
     * The Z ordinate is ignored.
     *
     * @param c a <code>Vertex</code> with which to do the 2D comparison.
     * @return true if <code>other</code> is a <code>Vertex</code>
     * with the same values for X and Y.
     */
    public abstract boolean equals2D(Vertex c, double tolerance);

    /**
     * Tests if another coordinate has the same values for the X, Y and Z ordinates.
     *
     * @param other a <code>Vertex</code> with which to do the 3D comparison.
     * @return true if <code>other</code> is a <code>Vertex</code>
     * with the same values for X, Y and Z.
     */
    public abstract boolean equals3D(Vertex other);

    /**
     * Tests if another coordinate has the same value for Z, within a tolerance.
     *
     * @param c         a coordinate
     * @param tolerance the tolerance value
     * @return true if the Z ordinates are within the given tolerance
     */
    public abstract boolean equalInZ(Vertex c, double tolerance);

    public abstract boolean equals(Object other);

    /**
     * Computes the 2-dimensional Euclidean distance2D to another location.
     * The Z-ordinate is ignored.
     *
     * @param c a point
     * @return the 2-dimensional Euclidean distance2D between the locations
     */
    public abstract double distance2D(Vertex c);

    /**
     * Computes the 3-dimensional Euclidean distance2D to another location.
     *
     * @param c a coordinate
     * @return the 3-dimensional Euclidean distance2D between the locations
     */
    public abstract double distance3D(Vertex c);

    /**
     * Gets a hashcode for this coordinate.
     *
     * @return a hashcode for this coordinate
     */
    @Override
    public abstract int hashCode();

    /**
     * Returns a <code>String</code> of the form <I>(x,y,z)</I> .
     *
     * @return a <code>String</code> of the form <I>(x,y,z)</I>
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public abstract Object clone();

    @Override
    public abstract void copyFrom(Object i);

    @Override
    public abstract boolean load(DataInput in) throws IOException;

    @Override
    public abstract boolean store(DataOutput out) throws IOException;

    @Override
    public abstract long getByteArraySize();

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
    public abstract int compareTo(Vertex other);

}
