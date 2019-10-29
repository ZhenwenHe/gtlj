package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.common.Variant;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Arrays;

/**
 * Created by hadoop on 17-3-20.
 */

/**
 * A {@link VertexSequence} implementation based on a packed arrays.
 * In this implementation, {@link Vertex}s returned by #toArray and #get are copies
 * of the internal values.
 * To change the actual values, use the provided setters.
 * <p>
 * For efficiency, created Vertex arrays
 * are cached using a soft reference.
 * The cache is cleared each time the coordinate sequence contents are
 * modified through a setter method.
 */
class PackedVertexSequence implements VertexSequence, Dimensional {
    private static final long serialVersionUID = 1L;

    /**
     * The dimensions of the coordinates hold in the packed array
     */
    protected int dimension;
    /**
     * A soft reference to the Vertex[] representation of this sequence.
     * Makes repeated coordinate array accesses more efficient.
     */
    protected SoftReference coordRef;
    /**
     * The packed coordinate array
     */
    double[] coords;

    public PackedVertexSequence() {
    }

    /**
     * Builds a new packed coordinate sequence
     *
     * @param coords
     * @param dimensions
     */
    public PackedVertexSequence(double[] coords, int dimensions) {
        if (dimensions < 2) {
            throw new IllegalArgumentException("Must have at least 2 dimensions");
        }
        if (coords.length % dimensions != 0) {
            throw new IllegalArgumentException("Packed array does not contain "
                    + "an integral number of coordinates");
        }
        this.dimension = dimensions;
        this.coords = coords;
    }


    /**
     * Builds a new packed coordinate sequence out of a float coordinate array
     *
     * @param coordinates
     */
    public PackedVertexSequence(float[] coordinates, int dimensions) {
        this.coords = new double[coordinates.length];
        this.dimension = dimensions;
        for (int i = 0; i < coordinates.length; i++) {
            this.coords[i] = coordinates[i];
        }
    }


    /**
     * Builds a new packed coordinate sequence out of a coordinate array
     *
     * @param vertices
     */
    public PackedVertexSequence(Vertex[] vertices, int dimension) {
        if (vertices == null)
            vertices = new Vertex[0];
        this.dimension = dimension;

        coords = new double[vertices.length * this.dimension];
        for (int i = 0; i < vertices.length; i++) {
            coords[i * this.dimension] = vertices[i].x;
            if (this.dimension >= 2)
                coords[i * this.dimension + 1] = vertices[i].y;
            if (this.dimension >= 3)
                coords[i * this.dimension + 2] = vertices[i].z;
        }
    }

    /**
     * Builds a new packed coordinate sequence out of a coordinate array
     *
     * @param vertices
     */
    public PackedVertexSequence(Vertex[] vertices) {
        this(vertices, 3);
    }

    /**
     * Builds a new empty packed coordinate sequence of a given size and dimension
     */
    public PackedVertexSequence(int size, int dimension) {
        this.dimension = dimension;
        coords = new double[size * this.dimension];
    }

    @Override
    public void makeDimension(int dimension) {
        if (dimension != getDimension()) {
            if (this.coords != null) {
                int s = size();
                PackedVertexSequence pvs = new PackedVertexSequence(s, dimension);
                int dim = this.dimension > dimension ? dimension : this.dimension;
                for (int i = 0; i < s; ++i) {
                    Vector v = Vector.create(dimension);
                    for (int j = 0; j < dim; ++j) {
                        pvs.setOrdinate(i, j, this.getOrdinate(i, j));
                    }
                }
                this.coords = pvs.coords;
                pvs.coords = null;
            }
            this.dimension = dimension;
        }
    }

    @Override
    public int getDimension() {
        return this.dimension;
    }

    @Override
    public Vertex getCoordinate(int i) {
        Vertex[] coords = getCachedCoords();
        if (coords != null)
            return coords[i];
        else
            return getCoordinateInternal(i);
    }

    public Vertex getCoordinateCopy(int i) {
        return getCoordinateInternal(i);
    }

    @Override
    public void getCoordinate(int i, Vertex coord) {
        coord.x = getOrdinate(i, 0);
        coord.y = getOrdinate(i, 1);
        if (dimension > 2) coord.z = getOrdinate(i, 2);
    }

    @Override
    public void getCoordinate(int i, Vertex2D coord) {
        coord.x = getOrdinate(i, 0);
        coord.y = getOrdinate(i, 1);
    }

    @Override
    public void getCoordinate(int i, Vertex3D coord) {
        coord.x = getOrdinate(i, 0);
        coord.y = getOrdinate(i, 1);
        coord.z = getOrdinate(i, 2);
    }

    @Override
    public Vertex[] toCoordinateArray() {
        Vertex[] coords = getCachedCoords();
// testing - never cache
        if (coords != null)
            return coords;

        coords = new Vertex[size()];
        for (int i = 0; i < coords.length; i++) {
            coords[i] = getCoordinateInternal(i);
        }
        coordRef = new SoftReference(coords);

        return coords;
    }

    /**
     * @return
     */
    private Vertex[] getCachedCoords() {
        if (coordRef != null) {
            Vertex[] coords = (Vertex[]) coordRef.get();
            if (coords != null) {
                return coords;
            } else {
                // System.out.print("-");
                coordRef = null;
                return null;
            }
        } else {
            // System.out.print("-");
            return null;
        }

    }

    @Override
    public double getX(int index) {
        return getOrdinate(index, 0);
    }

    @Override
    public double getY(int index) {
        return getOrdinate(index, 1);
    }


    /**
     * Sets the first ordinate of a coordinate in this sequence.
     *
     * @param index the coordinate index
     * @param value the new ordinate value
     */
    public void setX(int index, double value) {
        coordRef = null;
        setOrdinate(index, 0, value);
    }

    /**
     * Sets the second ordinate of a coordinate in this sequence.
     *
     * @param index the coordinate index
     * @param value the new ordinate value
     */
    public void setY(int index, double value) {
        coordRef = null;
        setOrdinate(index, 1, value);
    }

    public String toString() {
        return VertexSequences.toString(this);
    }


    public Vertex getCoordinateInternal(int i) {
        double x = coords[i * dimension];
        double y = coords[i * dimension + 1];
        double z = dimension == 2 ? Vertex.NULL_ORDINATE : coords[i * dimension + 2];
        return new VertexImpl(x, y, z);
    }

    /**
     * Gets the underlying array containing the coordinate values.
     *
     * @return the array of coordinate values
     */
    public double[] getRawCoordinates() {
        return coords;
    }

    @Override
    public int size() {
        return coords.length / dimension;
    }

    @Override
    public Object clone() {
        double[] clone = new double[coords.length];
        System.arraycopy(coords, 0, clone, 0, coords.length);
        return new PackedVertexSequence(clone, dimension);
    }

    @Override
    public double getOrdinate(int index, int ordinate) {
        return coords[index * dimension + ordinate];
    }

    @Override
    public void setOrdinate(int index, int ordinate, double value) {
        coordRef = null;
        coords[index * dimension + ordinate] = value;
    }

    @Override
    public Envelope expandEnvelope(Envelope env) {
        for (int i = 0; i < coords.length; i += dimension) {
            env.combine(Vector.create(coords[i], coords[i + 1]));
        }
        return env;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof PackedVertexSequence) {
            PackedVertexSequence p = (PackedVertexSequence) i;
            this.dimension = p.dimension;
            this.coords = Arrays.copyOf(p.coords, p.coords.length);
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.dimension = in.readInt();
        this.coords = Variant.readDoubles(in);
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this.dimension);
        Variant.writeDoubles(out, this.coords);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 4 + 4 + this.coords.length * 8;
    }

    @Override
    public double getZ(int index) {
        return getOrdinate(index, 2);
    }
}