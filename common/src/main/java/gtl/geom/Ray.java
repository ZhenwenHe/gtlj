package gtl.geom;

import gtl.exception.WarningException;

/**
 * Created by hadoop on 17-3-27.
 * Line and Ray and Segment with defining  points {PointShape P0, P1;}
 * (a Line is infinite, Rays and  Segments start at P0)
 * (a Ray extends beyond P1, but a  Segment ends at P1)
 * ref:http://geomalgorithms.com/a06-_intersect-2.html
 */
public class Ray extends InfiniteLine {
    private static final long serialVersionUID = 1L;

    public Ray(Vector startPoint, Vector endPoint) {
        super(startPoint, endPoint);
    }

    public Ray(double[] startPoint, double[] endPoint) {
        super(startPoint, endPoint);
    }

    public Ray() {
        super();
    }

    @Override
    public Object clone() {
        return new Ray(startPoint, endPoint);
    }

    public InfiniteLine extend() {
        return new InfiniteLine(startPoint, endPoint);
    }

    public Ray flap() {
        try {
            Ray r = new Ray(startPoint.flap(), endPoint.flap());
            if (r.startPoint.equals(r.endPoint)) {
                throw new WarningException("Ray flap operation: the return object will be a point");
            }
            return r;
        } catch (Exception w) {
            w.printStackTrace();
        }
        return null;
    }
}
