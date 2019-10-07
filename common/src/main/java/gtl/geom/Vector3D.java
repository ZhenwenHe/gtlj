package gtl.geom;

/**
 * Created by hadoop on 17-3-24.
 */
public class Vector3D extends VectorImpl {
    private static final long serialVersionUID = 1L;

    public Vector3D() {
        super();
    }

    public Vector3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector3D(double[] ca, int beginPosition) {
        super(ca, beginPosition, 3);
    }
}
