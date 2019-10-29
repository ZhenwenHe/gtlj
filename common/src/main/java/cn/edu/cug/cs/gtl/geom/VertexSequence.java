package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.math.MathSuits;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.math.MathSuits;

/**
 * Created by hadoop on 17-3-20.
 */
public interface VertexSequence extends Serializable {

    /**
     * Standard ordinate index values
     */
    int X = 0;
    int Y = 1;
    int Z = 2;
    int M = 3;

    /**
     * Returns the dimension (number of ordinates in each coordinate)
     * for this sequence.
     *
     * @return the dimension of the sequence.
     */
    int getDimension();

    /**
     * Returns a copy of the i'th coordinate in this sequence.
     * Whether or not the Vertex returned is the actual underlying
     * Vertex or merely a copy depends on the implementation.
     * <p>
     * Note that in the future the semantics of this method may change
     * to guarantee that the Vertex returned is always a copy.
     * Callers should not to assume that they can modify a VertexSequence by
     * modifying the object returned by this method.
     *
     * @param i the index of the coordinate to retrieve
     * @return the i'th coordinate in the sequence
     */
    Vertex getCoordinate(int i);

    /**
     * Copies the i'th coordinate in the sequence to the supplied
     * {@link Vertex}.  Only the first two dimensions are copied.
     *
     * @param index the index of the coordinate to copy
     * @param coord a {@link Vertex} to receive the value
     */
    void getCoordinate(int index, Vertex coord);

    void getCoordinate(int index, Vertex2D coord);

    void getCoordinate(int index, Vertex3D coord);

    /**
     * Returns ordinate X (0) of the specified coordinate.
     *
     * @param index
     * @return the value of the X ordinate in the index'th coordinate
     */
    double getX(int index);

    /**
     * Returns ordinate Y (1) of the specified coordinate.
     *
     * @param index
     * @return the value of the Y ordinate in the index'th coordinate
     */
    double getY(int index);

    /**
     * Returns ordinate Z(2) of the specified coordinate.
     *
     * @param index
     * @return the value of the Z ordinate in the index'th coordinate
     */
    double getZ(int index);

    /**
     * Returns the ordinate of a coordinate in this sequence.
     * Ordinate indices 0 and 1 are assumed to be X and Y.
     * Ordinates indices greater than 1 have user-defined semantics
     * (for instance, they may contain other dimensions or measure values).
     *
     * @param index         the coordinate index in the sequence
     * @param ordinateIndex the ordinate index in the coordinate (in range [0, dimension-1])
     */
    double getOrdinate(int index, int ordinateIndex);

    /**
     * Returns the number of coordinates in this sequence.
     *
     * @return the size of the sequence
     */
    int size();

    /**
     * Sets the value for a given ordinate of a coordinate in this sequence.
     *
     * @param index         the coordinate index in the sequence
     * @param ordinateIndex the ordinate index in the coordinate (in range [0, dimension-1])
     * @param value         the new ordinate value
     */
    void setOrdinate(int index, int ordinateIndex, double value);

    /**
     * Returns (possibly copies of) the Coordinates in this collection.
     * Whether or not the Coordinates returned are the actual underlying
     * Coordinates or merely copies depends on the implementation. Note that
     * if this implementation does not store its data as an array of Coordinates,
     * this method will incur a performance penalty because the array needs to
     * be built from scratch.
     *
     * @return a array of coordinates containing the point values in this sequence
     */
    Vertex[] toCoordinateArray();

    /**
     * Expands the given {@link Envelope} to include the coordinates in the sequence.
     * Allows implementing classes to optimize access to coordinate values.
     *
     * @param env the envelope2D to expand
     * @return a ref to the expanded envelope2D
     */
    Envelope expandEnvelope(Envelope env);

    /**
     * Returns a deep copy of this collection.
     * Called by Geometry#clone.
     *
     * @return a copy of the coordinate sequence containing copies of all points
     */
    Object clone();

    default Envelope getEnvelope() {
        int s = size();
        if (s == 1) {
            Envelope e = Envelope.create(getCoordinate(0), MathSuits.EPSILON);
        }
        double low[] = new double[getDimension()];
        double high[] = new double[getDimension()];
        int dim = getDimension();
        for (int i = 0; i < dim; ++i) {
            low[i] = Double.MAX_VALUE;
            high[i] = -Double.MAX_VALUE;
        }
        double d = 0.0;
        for (int i = 0; i < s; ++i) {
            for (int j = 0; j < dim; ++j) {
                d = getOrdinate(i, j);
                if (Double.compare(low[i], d) >= 0)
                    low[i] = d;
                if (Double.compare(high[i], d) <= 0)
                    high[i] = d;
            }
        }
        return Envelope.create(low, high);
    }
}
