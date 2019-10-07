package gtl.geom;

/**
 * Created by hadoop on 17-3-24.
 */
public class Vector4D extends VectorImpl {
    private static final long serialVersionUID = 1L;

    public Vector4D() {
    }

    public Vector4D(double x, double y, double z, double t) {
        super(x, y, z, t);
    }

    public Vector4D(double[] coordinates) {
        super(coordinates);
    }

    public Vector4D(double[] ca, int beginPosition) {
        super(ca, beginPosition, 4);
    }
}
