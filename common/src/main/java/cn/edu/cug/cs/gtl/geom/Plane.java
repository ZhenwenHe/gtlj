package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by hadoop on 17-3-26.
 * If A, B, C are three non-collinear points, the plane ABC is
 * the set of all points collinear with pairs of points on one
 * or two sides of the triangle ABC.
 * a plane is uniquely determined by any of the following data:
 * 1)by three non-collinear points,
 * 2)by two straight lines meeting one another,
 * 3)by a straight line and a point not on that line, and
 * 4)a point and a line perpendicular to the plane.
 * Thus, there are many ways to represent a plane P. Some methods
 * work in any dimension, and some work only in 3D. In any dimension,
 * one can always specify 3 non-collinear points V0=(x0,y0,z0),
 * V1=(x1,y1,z1), V2=(x2,y2,z2) as the vertices of a triangle,
 * the most primitive planar object.
 * In 3D, this uniquely defines the plane of points P=(x,y,z)
 * satisfying the implicit equation:
 * |x -x0  y -y0 z- z0|
 * |x1-x0  y1-y0 z1-z0|=0
 * |x2-x0  y2-y0 z2-z0|
 * represented as a determinant.
 * In 3 dimensions, another popular and useful representation for
 * a plane P is the normal form that specifies an origin point V0
 * on P and a “normal” vector n which is perpendicular to P .
 * This representation is useful for computing intersections,
 * resulting in compact and efficient formulas.
 * But, this representation only works in 3D space.
 * In a higher n-dimensional space,
 * this representation defines an (n–1)-dimensional linear subspace.
 * We will not pursue this further here except to say that many of
 * the results for planes in 3D carry over to hyperplanes of n-D space
 * (see [Hanson, 1994] for further information).
 * In 3D, a normal vector n for P can be computed from any triangle V0V1V2 of points
 * on P   as the cross-product n=u x v=...
 * Then, any point P on the plane satisfies the normal implicit equation:
 * n.(P-V0)=0
 * For n=(a,b,c), P=(x,y,z) and d = –(n · V0), the equation for the plane is:
 * n.(P-V0)=ax+by+cz+d=0
 * So, the xyz-coefficients (a,b,c) of any linear equation for a plane P
 * always give a vector which is perpendicular to the plane.
 * Also, when d = 0, the plane passes through the origin 0 = (0,0,0).
 * It is often useful to have a unit normal vector for the plane
 * which simplifies some formulas. This is easily done by dividing n by |n|.
 * Then, the associated implicit equation ax+by+cz+d=0 is said to
 * be “normalized”. Further, when |n| = 1,
 * the coordinates (a,b,c) of n are also the direction cosines of
 * the angles that the vector n makes with the xyz-axes.
 * Additionally, d is then the perpendicular distance from the origin
 * 0 to the plane, as we will show in the next section.
 * reference :http://geomalgorithms.com/a04-_planes.html
 */

public class Plane implements Serializable {
    private static final long serialVersionUID = 1L;

    Vector[] vertices;
    Vector normal;

    public Plane(Vector[] vertices) {
        this.vertices = Arrays.copyOf(vertices, 3);
        Vector u = this.vertices[1].subtract(this.vertices[0]);
        Vector v = this.vertices[2].subtract(this.vertices[0]);
        this.normal = u.crossProduct(v);
    }

    public Plane() {
        this.vertices = new Vector[3];
        vertices[0] = new VectorImpl(0.0, 0.0, 0.0);
        vertices[1] = new VectorImpl(0.0, 0.0, 0.0);
        vertices[2] = new VectorImpl(0.0, 0.0, 0.0);
        this.normal = new VectorImpl(0.0, 0.0, 0.0);
    }

    public Vector[] getVertices() {
        return vertices;
    }

    public void setVertices(Vector[] vertices) {
        this.vertices = Arrays.copyOf(vertices, 3);
        ;
    }

    public Vector getNormal() {
        return normal;
    }

    public void setNormal(Vector normal) {
        this.normal = (Vector) normal.clone();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plane plane = (Plane) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(vertices, plane.vertices)) return false;
        return normal.equals(plane.normal);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(vertices);
        result = 31 * result + normal.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "vertices=" + Arrays.toString(vertices) +
                ", normal=" + normal +
                '}';
    }

    @Override
    public Object clone() {
        return new Plane(this.vertices);
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Plane) {
            Plane p = (Plane) i;
            this.vertices = Arrays.copyOf(p.vertices, 3);
            this.normal = (Vector) p.normal.clone();
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.vertices[0].load(in);
        this.vertices[1].load(in);
        this.vertices[2].load(in);
        this.normal.load(in);
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        for (Vector v : this.vertices)
            v.store(out);
        this.normal.store(out);
        return true;
    }

    @Override
    public long getByteArraySize() {
        int len = 0;

        for (Vector v : this.vertices)
            len += v.getByteArraySize();

        len += this.normal.getByteArraySize();
        return len;
    }
}
