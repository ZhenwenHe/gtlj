package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by ZhenwenHe on 2016/12/8.
 * 2017/02/12 finished and checked
 */
public class VectorImpl implements Vector {

    private static final long serialVersionUID = 1L;

    double[] coordinates;

    public VectorImpl() {
        this.coordinates = new double[3];
    }

    public VectorImpl(int dim) {
        this.coordinates = new double[dim];
    }

    public VectorImpl(double x, double y) {
        this.coordinates = new double[2];
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }

    public VectorImpl(double x, double y, double z) {
        this.coordinates = new double[3];
        this.coordinates[0] = x;
        this.coordinates[1] = y;
        this.coordinates[2] = z;
    }

    public VectorImpl(double x, double y, double z, double t) {
        this.coordinates = new double[4];
        this.coordinates[0] = x;
        this.coordinates[1] = y;
        this.coordinates[2] = z;
        this.coordinates[3] = t;
    }

    public VectorImpl(double[] coordinates) {
        this.coordinates = new double[coordinates.length];
        System.arraycopy(coordinates, 0, this.coordinates, 0, coordinates.length);
    }

    public VectorImpl(double[] coordinates, int beginPosition, int length) {
        this.coordinates = new double[length];
        System.arraycopy(coordinates, beginPosition, this.coordinates, 0, length);
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Object clone() {
        double[] td = new double[this.coordinates.length];
        System.arraycopy(this.coordinates, 0, td, 0, td.length);
        return new VectorImpl(td);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VectorImpl)) return false;

        VectorImpl point = (VectorImpl) o;

        return Arrays.equals(getCoordinates(), point.getCoordinates());

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getCoordinates());
    }

    @Override
    public String toString() {
        return "VectorImpl{" +
                "coordinates=" + Arrays.toString(coordinates) +
                '}';
    }

    @Override
    public int getDimension() {
        return this.coordinates.length;
    }

    @Override
    public double getX() {
        return this.coordinates[0];
    }

    @Override
    public void setX(double x) {
        this.coordinates[0] = x;
    }

    @Override
    public double getY() {
        return this.coordinates[1];
    }

    @Override
    public void setY(double y) {
        this.coordinates[1] = y;
    }

    @Override
    public double getZ() {
        return this.coordinates[2];
    }

    @Override
    public void setZ(double z) {
        this.coordinates[2] = z;
    }

    @Override
    public boolean load(DataInput dis) throws IOException {
        int dims = dis.readInt();
        this.makeDimension(dims);
        for (int i = 0; i < dims; i++) {
            this.coordinates[i] = dis.readDouble();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dos) throws IOException {
        int dims = this.getDimension();
        dos.writeInt(dims);
        for (double d : this.coordinates)
            dos.writeDouble(d);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return getDimension() * 8 + 4;
    }

    @Override
    public void makeInfinite(int dimension) {
        makeDimension(dimension);
        for (int cIndex = 0; cIndex < this.coordinates.length; ++cIndex) {
            this.coordinates[cIndex] = Double.MAX_VALUE;
        }
    }

    @Override
    public void makeDimension(int dimension) {
        if (this.getDimension() != dimension) {
            double[] newData = new double[dimension];
            int minDims = Math.min(newData.length, this.coordinates.length);
            for (int i = 0; i < minDims; i++) {
                newData[i] = this.coordinates[i];
            }
            this.coordinates = newData;
        }
    }

    @Override
    public double getOrdinate(int i) {
        if (i < this.getDimension())
            return this.coordinates[i];
        else
            return Vector.NULL_ORDINATE;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Vector) {
            Vector v = (Vector) i;
            if (v.getCoordinates().length == this.coordinates.length) {
                System.arraycopy(
                        v.getCoordinates(), 0,
                        this.coordinates, 0, this.coordinates.length);
            } else {
                this.coordinates = new double[v.getCoordinates().length];
                System.arraycopy(
                        v.getCoordinates(), 0,
                        this.coordinates, 0, this.coordinates.length);
            }
        }
    }

    @Override
    public void reset(double[] coordinates) {
        if (this.coordinates.length == coordinates.length) {
            System.arraycopy(this.coordinates, 0,
                    coordinates, 0, coordinates.length);
        } else {
            this.coordinates = new double[coordinates.length];
            System.arraycopy(this.coordinates, 0,
                    coordinates, 0, coordinates.length);
        }
    }

    @Override
    public double normalize() {
        double dist = 0.0;
        double invDist = 0.0;
        dist = this.length();
        if (dist > 0.0) {
            invDist = 1.0 / dist;
            for (int i = 0; i < this.coordinates.length; ++i)
                this.coordinates[i] *= invDist;
        }
        return dist;
    }

    @Override
    public double dotProduct(Vector v) {

        double dRtn = 0.0;
        double[] B = v.getCoordinates();
        int i = 0;
        for (double d : this.coordinates) {
            dRtn += d * B[i];
            ++i;
        }
        return dRtn;
    }

    @Override
    public Vector crossProduct(Vector b) {
        assert b.getDimension() == 3 && this.getDimension() == 3;
        Vector vRet = new VectorImpl(0.0, 0.0, 0.0);
        double[] V = vRet.getCoordinates();
        double[] A = this.coordinates;
        double[] B = b.getCoordinates();
        V[0] = A[1] * B[2] - A[2] * B[1];
        V[1] = A[2] * B[0] - A[0] * B[2];
        V[2] = A[0] * B[1] - A[1] * B[0];
        return vRet;
    }

    @Override
    public double length() {
        double sum = 0;
        for (double d : this.coordinates)
            sum += d * d;
        return java.lang.Math.sqrt(sum);
    }

    /**
     * 求向量ao与bo的夹角
     *
     * @param a 点坐标
     * @param b 点坐标
     * @return 返回弧度
     */
    @Override
    public double angle(Vector a, Vector b) {
        Vector ao = a.subtract(this);
        Vector bo = b.subtract(this);
        double lfRgn = ao.dotProduct(bo);
        double lfLA = ao.length();
        double lfLB = bo.length();
        double cosA = lfRgn / (lfLA * lfLB);
        return java.lang.Math.acos(cosA);
    }

    @Override
    public Vector subtract(Vector b) {
        assert b.getDimension() >= this.getDimension();
        Vector v = (Vector) this.clone();
        double[] dv = v.getCoordinates();
        double[] bv = b.getCoordinates();
        for (int i = 0; i < dv.length; ++i) {
            dv[i] -= bv[i];
        }
        return v;
    }

    @Override
    public Vector add(Vector b) {
        assert b.getDimension() >= this.getDimension();
        Vector v = (Vector) this.clone();
        double[] dv = v.getCoordinates();
        double[] bv = b.getCoordinates();
        for (int i = 0; i < dv.length; ++i) {
            dv[i] += bv[i];
        }
        return v;
    }

    @Override
    public void setOrdinate(int i, double d) {
        this.coordinates[i] = d;
    }

    @Override
    public Vector multiply(Scalar s) {
        VectorImpl v = new VectorImpl(this.coordinates);
        for (int i = 0; i < this.coordinates.length; ++i) {
            v.coordinates[i] *= s.getScalar();
        }

        return v;
    }

    @Override
    public Vector multiply(double s) {
        VectorImpl v = new VectorImpl(this.coordinates);
        for (int i = 0; i < this.coordinates.length; ++i) {
            v.coordinates[i] *= s;
        }
        return v;
    }

    @Override
    public Vector divide(Scalar s) {
        VectorImpl v = new VectorImpl(this.coordinates);
        for (int i = 0; i < this.coordinates.length; ++i) {
            v.coordinates[i] /= s.scalar;
        }
        return v;
    }

    @Override
    public Vector divide(double s) {
        VectorImpl v = new VectorImpl(this.coordinates);
        for (int i = 0; i < this.coordinates.length; ++i) {
            v.coordinates[i] /= s;
        }
        return v;
    }

    /**
     * 按照X，Y，Z方向上的坐标分量大小比较，返回1，0，-1
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Vector o) {
        OrdinateComparator<Vector> c = new OrdinateComparator<>(0);
        int dim = Math.min(getDimension(), o.getDimension());
        for (int i = 0; i < dim; ++i) {
            c.setDimensionOrder(i);
            int r = c.compare(this, o);
            if (r != 0) return r;
        }
        return 0;
    }

    @Override
    public Vector2D flap() {
        return new Vector2D(this.coordinates[0], this.coordinates[1]);
    }

    @Override
    public Vector2D flapXY() {
        return new Vector2D(this.coordinates[0], this.coordinates[1]);
    }

    @Override
    public Vector2D flapYZ() {
        return new Vector2D(this.coordinates[1], this.coordinates[2]);
    }

    @Override
    public Vector2D flapXZ() {
        return new Vector2D(this.coordinates[0], this.coordinates[2]);
    }
}
