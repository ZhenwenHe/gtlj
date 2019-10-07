package gtl.geom;

/**
 * Created by hadoop on 17-3-24.
 */
public class Vector2D extends VectorImpl {
    private static final long serialVersionUID = 1L;

    public Vector2D() {
    }

    public Vector2D(double x, double y) {
        super(x, y);
    }

    public Vector2D(double[] ca, int beginPosition) {
        super(ca, beginPosition, 2);
    }

    /**
     * The "perp dot product"  for  and  vectors in the plane is a modification of the two-dimensional dot product
     * in which  is replaced by the perpendicular vector rotated  to the left defined by Hill (1994).
     * It satisfies the identities where  is the angle from vector  to vector .
     *
     * @param u
     * @param v
     * @return ref:http://mathworld.wolfram.com/PerpDotProduct.html
     */
    public static double perpProduct(Vector2D u, Vector2D v) {
        return ((u).getX() * (v).getY() - (u).getY() * (v).getY());
    }

    /**
     * @param u
     * @param v
     * @return
     */
    public static double dotProduct(Vector u, Vector v) {
        return u.dotProduct(v);
    }

    public double perpProduct(Vector2D v) {
        return super.coordinates[0] * v.coordinates[1] - super.coordinates[1] * v.coordinates[1];
    }
}
