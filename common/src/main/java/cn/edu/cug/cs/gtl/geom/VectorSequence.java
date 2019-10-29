package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.math.MathSuits;
import cn.edu.cug.cs.gtl.math.MathSuits;

import java.util.Collection;

/**
 * Created by hadoop on 17-3-25.
 */
public interface VectorSequence extends Collection<Vector>, Serializable, Dimensional {
    int getDimension();

    double getOrdinate(int index, int dim);

    void setOrdinate(int index, int dim, double v);

    double getX(int index);

    double getY(int index);

    double getZ(int index);

    void setX(int index, double d);

    void setY(int index, double d);

    void setZ(int index, double d);

    Vector3D getVector3D(int index);

    Vector2D getVector2D(int index);

    Vector4D getVector4D(int index);

    Vector getVector(int index);

    Vector getVector(int index, Vector v);

    int find(Vector v);

    Vector remove(int index);

    void insert(int index, Vector v);

    void add(double x, double y);

    void add(double x, double y, double z);

    void add(double x, double y, double z, double w);

    void reset(double[] vectors, int dimension);

    int size();

    default Envelope getEnvelope() {
        int s = size();
        if (s == 1) {
            return Envelope.create(getVector(0), MathSuits.EPSILON);
        }
        int dim = getDimension();
        double low[] = new double[getDimension()];
        double high[] = new double[getDimension()];
        for (int i = 0; i < dim; ++i) {
            low[i] = Double.MAX_VALUE;
            high[i] = -Double.MAX_VALUE;
        }
        double d = 0.0;
        for (int i = 0; i < s; ++i) {
            for (int j = 0; j < dim; ++j) {
                d = getOrdinate(i, j);
                if (Double.compare(low[j], d) >= 0)
                    low[j] = d;
                if (Double.compare(high[j], d) <= 0)
                    high[j] = d;
            }
        }
        return Envelope.create(low, high);
    }

    static VectorSequence create(int dim) {
        return new PackedVectorSequence(dim);
    }

    static VectorSequence create(int size, int dim) {
        return new PackedVectorSequence(size, dim);
    }

    static VectorSequence create(VectorSequence vs) {
        return new PackedVectorSequence(vs);
    }

    static VectorSequence create(Vector[] vs) {
        return new PackedVectorSequence(vs);
    }

    static VectorSequence create(double[] coords, int dim) {
        return new PackedVectorSequence(coords, dim);
    }

    /**
     * Copies a section of a {@link VectorSequence} to another {@link VectorSequence}.
     * The sequences may have different dimensions;
     * in this case only the common dimensions are copied.
     *
     * @param src     the sequence to copy from
     * @param srcPos  the position in the source sequence to start copying at
     * @param dest    the sequence to copy to
     * @param destPos the position in the destination sequence to copy to
     * @param length  the number of coordinates to copy
     */
    static void copy(VectorSequence src, int srcPos, VectorSequence dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            copyVector(src, srcPos + i, dest, destPos + i);
        }
    }

    /**
     * Copies a coordinate of a {@link VectorSequence} to another {@link VectorSequence}.
     * The sequences may have different dimensions;
     * in this case only the common dimensions are copied.
     *
     * @param src     the sequence to copy from
     * @param srcPos  the source coordinate to copy
     * @param dest    the sequence to copy to
     * @param destPos the destination coordinate to copy to
     */
    static void copyVector(VectorSequence src, int srcPos, VectorSequence dest, int destPos) {
        int minDim = Math.min(src.getDimension(), dest.getDimension());
        for (int dim = 0; dim < minDim; dim++) {
            dest.setOrdinate(destPos, dim, src.getOrdinate(srcPos, dim));
        }
    }

    /**
     * Tests whether a {@link VectorSequence} forms a valid {@link LinearRing},
     * by checking the sequence length and closure
     * (whether the first and last points are identical in 2D).
     * Self-intersection is not checked.
     *
     * @param seq the sequence to test
     * @return true if the sequence is a ring
     * @see LinearRing
     */
    static boolean isRing(VectorSequence seq) {
        int n = seq.size();
        if (n == 0) return true;
        // too few points
        if (n <= 3)
            return false;
        // test if closed
        return seq.getOrdinate(0, 0) == seq.getOrdinate(n - 1, 0)
                && seq.getOrdinate(0, 1) == seq.getOrdinate(n - 1, 1);
    }

    /**
     * Ensures that a VectorSequence forms a valid ring,
     * returning a new closed sequence of the correct length if required.
     * If the input sequence is already a valid ring, it is returned
     * without modification.
     * If the input sequence is too short or is not closed,
     * it is extended with one or more copies of the start point.
     *
     * @param seq the sequence to test
     * @return the original sequence, if it was a valid ring, or a new sequence which is valid.
     */
    static VectorSequence ensureValidRing(VectorSequence seq) {
        int n = seq.size();
        // empty sequence is valid
        if (n == 0) return seq;
        // too short - make a new one
        if (n <= 3)
            return createClosedRing(seq, 4);

        boolean isClosed = seq.getOrdinate(0, 0) == seq.getOrdinate(n - 1, 0)
                && seq.getOrdinate(0, 1) == seq.getOrdinate(n - 1, 1);
        if (isClosed) return seq;
        // make a new closed ring
        return createClosedRing(seq, n + 1);
    }

    static VectorSequence createClosedRing(VectorSequence seq, int size) {
        VectorSequence newseq = VectorSequence.create(size, seq.getDimension());
        int n = seq.size();
        copy(seq, 0, newseq, 0, n);
        // fill remaining coordinates with start point
        for (int i = n; i < size; i++)
            copy(seq, 0, newseq, i, 1);
        return newseq;
    }

    static VectorSequence extend(VectorSequence seq, int size) {
        VectorSequence newseq = VectorSequence.create(size, seq.getDimension());
        int n = seq.size();
        copy(seq, 0, newseq, 0, n);
        // fill remaining coordinates with end point, if it exists
        if (n > 0) {
            for (int i = n; i < size; i++)
                copy(seq, n - 1, newseq, i, 1);
        }
        return newseq;
    }

}
