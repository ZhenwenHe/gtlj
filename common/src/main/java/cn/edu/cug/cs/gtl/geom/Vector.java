package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.Serializable;

/**
 * Created by ZhenwenHe on 2016/12/8.
 */
public interface Vector extends Serializable, Dimensional, Comparable<Vector> {
    /**
     * The value used to indicate a null or missing ordinate value.
     * In particular, used for the value of ordinates for dimensions
     * greater than the defined dimension of a coordinate.
     */
    public static final double NULL_ORDINATE = Double.NaN;

    double[] getCoordinates();

    double getOrdinate(int i);

    void setOrdinate(int i, double d);

    double getX();

    void setX(double x);

    double getY();

    void setY(double y);

    double getZ();

    void setZ(double z);

    void makeInfinite(int dimension);

    void reset(double[] coordinates);

    double normalize();// return the original vector length

    double dotProduct(Vector v);

    Vector crossProduct(Vector b);// this X  b

    double length();

    double angle(Vector a, Vector b);

    Vector subtract(Vector b);//this - b;

    Vector add(Vector b);//this + b;

    Vector multiply(Scalar s);

    Vector multiply(double s);

    Vector divide(Scalar s);

    Vector divide(double s);

    //ignore Z;
    Vector2D flap();

    //ignore Z
    Vector2D flapXY();

    //ignore X
    Vector2D flapYZ();

    //ignore Y
    Vector2D flapXZ();

    default double euclideanDistance(Vector v) {
        return euclideanDistance(v, this);
    }

    static double euclideanDistance(Vector o1, Vector o2) {
        int dim = Math.min(o1.getDimension(), o2.getDimension());
        double s = 0;
        for (int i = 0; i < dim; ++i) {
            s += Math.pow((o1.getOrdinate(i) - o2.getOrdinate(i)), 2);
        }
        return Math.sqrt(s);
    }

    static Vector create() {
        return new VectorImpl();
    }

    static Vector create(int dim) {
        return new VectorImpl(dim);
    }


    static Vector create(double x, double y) {
        return new VectorImpl(x, y);
    }

    static Vector create(double x, double y, double z) {
        return new VectorImpl(x, y, z);
    }

    static Vector create(double x, double y, double z, double t) {
        return new VectorImpl(x, y, z, t);
    }

    static Vector create(double[] coordinates) {
        return new VectorImpl(coordinates);
    }

    static Vector create(double[] coordinates, int beginPosition, int length) {
        return new VectorImpl(coordinates, beginPosition, length);
    }

    static Vector2D createVector2D(double x, double y) {
        return new Vector2D(x, y);
    }

    static Vector3D createVector3D(double x, double y, double z) {
        return new Vector3D(x, y, z);
    }

    static Vector4D createVector4D(double x, double y, double z, double t) {
        return new Vector4D(x, y, z, t);
    }
}
