package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by hadoop on 17-3-25.
 */
public class PackedVectorSequence implements VectorSequence {
    private static final long serialVersionUID = 1L;

    /**
     * The dimensions of the coordinates hold in the packed array
     */
    protected int dimension;

    /**
     * The packed coordinate array
     */
    double[] coordinates;

    public PackedVectorSequence(double[] coordinates, int dimension) {
        if (dimension < 2) {
            throw new IllegalArgumentException("Must have at least 2 dimensions");
        }
        if (coordinates.length % dimension != 0) {
            throw new IllegalArgumentException("Packed array does not contain "
                    + "an integral number of coordinates");
        }

        this.dimension = dimension;
        this.coordinates = Arrays.copyOf(coordinates, coordinates.length);
    }

    public PackedVectorSequence(int size, int dimension) {
        if (dimension < 2) {
            throw new IllegalArgumentException("Must have at least 2 dimensions");
        }

        this.dimension = dimension;
        this.coordinates = new double[size * dimension];
    }

    public PackedVectorSequence(Vector[] vv) {
        int dimension = vv[0].getDimension();
        if (dimension < 2) {
            throw new IllegalArgumentException("Must have at least 2 dimensions");
        }
        this.dimension = dimension;
        this.coordinates = new double[dimension * vv.length];
        int j = 0;
        for (Vector v : vv) {
            for (int i = 0; i < dimension; ++i) {
                this.coordinates[j] = v.getOrdinate(i);
                ++j;
            }
        }
    }

    public PackedVectorSequence(VectorSequence vs) {
        this.dimension = vs.getDimension();
        if (vs instanceof PackedVectorSequence) {
            PackedVectorSequence pvs = (PackedVectorSequence) vs;
            this.coordinates = Arrays.copyOf(pvs.coordinates, pvs.coordinates.length);
        } else {
            this.coordinates = new double[vs.size() * this.dimension];
            Iterator<Vector> it = vs.iterator();
            int i = 0;
            double[] v = null;
            while (it.hasNext()) {
                v = it.next().getCoordinates();
                for (int j = 0; j < this.dimension; ++j) {
                    this.coordinates[i] = v[j];
                    ++i;
                }
            }
        }
    }

    public PackedVectorSequence(int dimension) {
        this.dimension = dimension;
        this.coordinates = null;
    }

    public PackedVectorSequence() {
        this.dimension = 2;
        this.coordinates = null;
    }

    @Override
    public void reset(double[] vectors, int dimension) {
        this.dimension = dimension;
        this.coordinates = Arrays.copyOf(vectors, vectors.length);
    }


    @Override
    public Object clone() {
        return new PackedVectorSequence(this.coordinates, this.dimension);
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof VectorSequence) {
            if (i instanceof PackedVectorSequence) {
                PackedVectorSequence p = (PackedVectorSequence) i;
                this.dimension = p.dimension;
                this.coordinates = Arrays.copyOf(p.coordinates, p.coordinates.length);
            } else {
                VectorSequence p = (VectorSequence) i;
                Object[] vv = p.toArray();
                this.clear();
                for (Object v : vv) {
                    this.add((Vector) v);
                }
            }
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.dimension = in.readInt();
        int len = in.readInt();
        if (len > 0) {
            this.coordinates = new double[len];
            for (int i = 0; i < len; ++i)
                this.coordinates[i] = in.readDouble();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this.dimension);
        int len = this.coordinates == null ? 0 : this.coordinates.length;
        out.writeInt(len);
        if (len > 0)
            for (double d : this.coordinates) out.writeDouble(d);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 4 + 4 + this.coordinates.length * 8;
    }

    @Override
    public int size() {
        if (this.coordinates != null)
            return this.coordinates.length / this.dimension;
        else
            return 0;
    }

    @Override
    public boolean isEmpty() {
        return this.coordinates == null;
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Vector) {
            Vector vi = new VectorImpl(this.coordinates, 0, this.dimension);
            Vector vo = (Vector) o;
            int s = this.size();
            for (int i = 0; i < s; ++i)
                if (this.getVector(i, vi).equals(vo)) {
                    return true;
                }
        }
        return false;
    }

    @Override
    public Iterator<Vector> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        int c = this.size();
        if (c > 0) {
            Object[] a = new Object[c];
            for (int i = 0; i < c; i++)
                a[i] = (Object) this.getVector(i);

            return a;
        } else
            return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int c = this.size();
        if (c > 0) {
            for (int i = 0; i < c; i++)
                a[i] = (T) this.getVector(i);

            return a;
        } else
            return null;
    }

    @Override
    public boolean add(Vector vector) {
        if (this.coordinates == null) {
            this.dimension = vector.getDimension();
            this.coordinates = new double[this.dimension];
            for (int i = 0; i < this.dimension; ++i)
                this.coordinates[i] = vector.getOrdinate(i);
        } else {
            int len = this.coordinates.length;
            double[] dd = Arrays.copyOf(this.coordinates, len + this.dimension);
            for (int i = 0; i < this.dimension; ++i)
                dd[len + i] = vector.getOrdinate(i);
            this.coordinates = dd;
        }

        return true;
    }

    @Override
    public int find(Vector o) {
        Vector vi = new VectorImpl(this.coordinates, 0, this.dimension);
        int s = this.size();
        int i = 0;
        while (i < s) {
            if (this.getVector(i, vi).equals(o)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Vector) {
            Vector v = (Vector) o;
            int i = find(v);
            if (i != -1) {
                double[] dd = new double[this.coordinates.length - this.dimension];
                //0-i
                System.arraycopy(this.coordinates, 0, dd, 0, i * this.dimension);
                //i+1-len;
                System.arraycopy(this.coordinates, (i + 1) * this.dimension,
                        dd, i * this.dimension, (this.size() - 1 - i) * this.dimension);
                this.coordinates = dd;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public Vector remove(int index) {
        Vector v = getVector(index);
        this.remove(v);
        return v;
    }

    @Override
    public void insert(int index, Vector v) {
        if (index >= this.size() || this.isEmpty()) {
            add(v);
        } else {
            int len = this.coordinates.length;
            int newLen = len + this.dimension;
            int i = 0;
            double[] dd = new double[newLen];
            len = index * this.dimension;
            for (i = 0; i < len; ++i) dd[i] = this.coordinates[i];
            for (i = (index + 1) * this.dimension; i < newLen; ++i)
                dd[i] = this.coordinates[i - this.dimension];
            for (i = 0; i < this.dimension; ++i)
                dd[i + len] = v.getOrdinate(i);
            this.coordinates = dd;
        }
    }

    @Override
    public void add(double x, double y) {
        this.add(new Vector2D(x, y));
    }

    @Override
    public void add(double x, double y, double z) {
        this.add(new Vector3D(x, y, z));
    }

    @Override
    public void add(double x, double y, double z, double w) {
        this.add(new Vector4D(x, y, z, w));
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator<?> it = c.iterator();
        while (it.hasNext()) {
            if (this.contains(it.next()) == false)
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Vector> c) {
        Iterator<?> it = c.iterator();
        while (it.hasNext()) {
            this.add((Vector) it.next());
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator<?> it = c.iterator();
        while (it.hasNext()) {
            this.remove(it.next());
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int s = c.size();
        this.coordinates = new double[s * this.dimension];
        Iterator<?> it = c.iterator();
        Vector v = null;
        int k = 0;
        while (it.hasNext()) {
            v = (Vector) (it.next());
            for (int d = 0; d < this.dimension; ++d) {
                this.coordinates[k] = v.getOrdinate(d);
                k++;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        this.coordinates = null;
    }

    @Override
    public void makeDimension(int dimension) {
        if (dimension != getDimension()) {
            if (this.coordinates != null) {
                PackedVectorSequence pvs = new PackedVectorSequence(dimension);
                int s = size();
                int dim = this.dimension > dimension ? dimension : this.dimension;
                for (int i = 0; i < s; ++i) {
                    Vector v = Vector.create(dimension);
                    for (int j = 0; j < dim; ++j) {
                        v.setOrdinate(j, this.getOrdinate(i, j));
                    }
                    pvs.add(v);
                }
                this.coordinates = pvs.coordinates;
                pvs.coordinates = null;
            }
            this.dimension = dimension;
        }
    }

    @Override
    public int getDimension() {
        return this.dimension;
    }

    @Override
    public double getOrdinate(int index, int dim) {
        return this.coordinates[index * this.dimension + dim];
    }

    @Override
    public void setOrdinate(int index, int dim, double v) {
        this.coordinates[index * this.dimension + dim] = v;
    }

    @Override
    public double getX(int index) {
        return this.coordinates[index * this.dimension];
    }

    @Override
    public double getY(int index) {
        return this.coordinates[index * this.dimension + 1];
    }

    @Override
    public double getZ(int index) {
        return this.coordinates[index * this.dimension + 2];
    }

    @Override
    public void setX(int index, double d) {
        this.coordinates[index * this.dimension] = d;
    }

    @Override
    public void setY(int index, double d) {
        this.coordinates[index * this.dimension + 1] = d;
    }

    @Override
    public void setZ(int index, double d) {
        this.coordinates[index * this.dimension + 2] = d;
    }

    @Override
    public Vector3D getVector3D(int index) {
        Vector3D v3d = new Vector3D(this.coordinates, index * this.dimension);
        if (this.dimension < 3) {
            v3d.setZ(Vector.NULL_ORDINATE);
        }
        return v3d;
    }

    @Override
    public Vector2D getVector2D(int index) {
        return new Vector2D(this.coordinates, index * this.dimension);
    }

    @Override
    public Vector4D getVector4D(int index) {
        Vector4D v4d = new Vector4D(this.coordinates, index * this.dimension);
        if (this.dimension == 2) {
            v4d.setZ(Vector.NULL_ORDINATE);
            v4d.setOrdinate(3, Vector.NULL_ORDINATE);
        }
        if (this.dimension == 3) {
            v4d.setOrdinate(3, Vector.NULL_ORDINATE);
        }
        return v4d;
    }

    @Override
    public Vector getVector(int index) {
        return new VectorImpl(this.coordinates, index * this.dimension, this.dimension);
    }

    @Override
    public Vector getVector(int index, Vector v) {
        Vector vr = getVector(index);
        vr.copyTo(v);
        return v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackedVectorSequence vectors = (PackedVectorSequence) o;

        if (dimension != vectors.dimension) return false;
        return Arrays.equals(coordinates, vectors.coordinates);
    }

    @Override
    public int hashCode() {
        int result = dimension;
        result = 31 * result + Arrays.hashCode(coordinates);
        return result;
    }

    @Override
    public String toString() {
        return "PackedVectorSequence{" +
                "dimension=" + dimension +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }


    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<Vector> {
        int cursor = 0;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        public boolean hasNext() {
            return cursor != PackedVectorSequence.this.size();
        }

        @SuppressWarnings("unchecked")
        public Vector next() {
            int i = cursor;
            if (i >= PackedVectorSequence.this.size())
                throw new NoSuchElementException();
            double[] elementData = PackedVectorSequence.this.coordinates;
            if (i >= elementData.length / PackedVectorSequence.this.dimension)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            lastRet = i;
            return new VectorImpl(elementData, lastRet * PackedVectorSequence.this.dimension, PackedVectorSequence.this.dimension);
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                PackedVectorSequence.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super Vector> consumer) {
            Objects.requireNonNull(consumer);
            final int size = PackedVectorSequence.this.size();
            int i = cursor;
            if (i >= size) {
                return;
            }
            final double[] elementData = PackedVectorSequence.this.coordinates;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size) {
                Vector v = new VectorImpl(elementData, i * PackedVectorSequence.this.dimension, PackedVectorSequence.this.dimension);
                consumer.accept(v);
                i++;
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
        }
    }

}
