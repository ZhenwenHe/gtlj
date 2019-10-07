package gtl.index.shape;


import gtl.geom.Envelope;
import gtl.geom.Geom3DSuits;
import gtl.geom.LineSegment;
import gtl.geom.Vector;
import gtl.math.MathSuits;

/**
 * Created by ZhenwenHe on 2016/12/8.
 */
public class LineSegmentShape extends LineSegment implements Shape {

    private static final long serialVersionUID = 1L;

    public LineSegmentShape() {
        super();
    }

    public LineSegmentShape(double[] startPoint, double[] endPoint) {
        reset(startPoint, endPoint);
    }

    public LineSegmentShape(Vector startPoint, Vector endPoint) {
        super(startPoint, endPoint);
    }

    //some helpers for intersects methods
    protected static double doubleAreaTriangle(PointShape a, PointShape b, PointShape c) {
        double[] pA = a.getCoordinates();
        double[] pB = b.getCoordinates();
        double[] pC = c.getCoordinates();

        return (((pB[0] - pA[0]) * (pC[1] - pA[1])) - ((pC[0] - pA[0]) * (pB[1] - pA[1])));
    }

    protected static boolean leftOf(PointShape a, PointShape b, PointShape c) {
        return (doubleAreaTriangle(a, b, c) > 0);
    }

    protected static boolean collinear(PointShape a, PointShape b, PointShape c) {
        return (doubleAreaTriangle(a, b, c) == 0);
    }

    protected static boolean between(PointShape a, PointShape b, PointShape c) {

        if (!collinear(a, b, c)) {
            return false;
        }
        double[] pA = a.getCoordinates();
        double[] pB = b.getCoordinates();
        double[] pC = c.getCoordinates();
        if (pA[0] != pB[0]) { // a & b are not on the same vertical, compare on x axis
            return between(pA[0], pB[0], pC[0]);
        } else { // a & b are a vertical segment, we need to compare on y axis
            return between(pA[1], pB[1], pC[1]);
        }
    }

    protected static boolean between(double a, double b, double c) {
        return (((a <= c) && (c <= b)) || ((a >= c) && (c >= b)));
    }

    protected static boolean intersectsProper(PointShape a, PointShape b, PointShape c, PointShape d) {

        if (collinear(a, b, c) || collinear(a, b, d) ||
                collinear(c, d, a) || collinear(c, d, b)) {
            return false;
        }

        return ((leftOf(a, b, c) ^ leftOf(a, b, d)) && (leftOf(c, d, a) ^ leftOf(c, d, b)));
    }

    protected static boolean intersects(PointShape a, PointShape b, PointShape c, PointShape d) {

        if (intersectsProper(a, b, c, d)) {
            return true;
        } else if (between(a, b, c) || between(a, b, d) ||
                between(c, d, a) || between(c, d, b)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean intersectsLineSegment(LineSegmentShape l) {
        assert this.getDimension() == 2;

        // use Geometry::intersects
        PointShape p1 = ShapeSuits.createPoint(this.getStartCoordinates());
        PointShape p2 = ShapeSuits.createPoint(this.getEndCoordinates());
        PointShape p3 = ShapeSuits.createPoint(l.getStartCoordinates());
        PointShape p4 = ShapeSuits.createPoint(l.getEndCoordinates());
        return LineSegmentShape.intersects(p1, p2, p3, p4);
    }

    @Override
    public boolean intersectsShape(Shape in) {
        if (in instanceof LineSegmentShape) {
            this.intersectsLineSegment((LineSegmentShape) in);
        }
        if (in instanceof RegionShape) {
            this.intersectsRegion((RegionShape) in);
        }
        return false;
    }

    public boolean intersectsRegion(RegionShape p) {
        assert this.getDimension() == 2;
        return p.intersectsLineSegment((this));
    }

    @Override
    public boolean containsShape(Shape in) {
        return false;
    }

    @Override
    public boolean touchesShape(Shape in) {

        return false;
    }

    public double getMinimumDistance(PointShape p) {
        assert this.getDimension() == 2;
        double[] startCoordinates = this.getStartCoordinates();
        double[] endCoordinates = this.getEndCoordinates();
        if (endCoordinates[0] >= startCoordinates[0] - MathSuits.EPSILON &&
                endCoordinates[0] <= startCoordinates[0] + MathSuits.EPSILON)
            return Math.abs(p.getCoordinate(0) - startCoordinates[0]);

        if (endCoordinates[1] >= startCoordinates[1] - MathSuits.EPSILON &&
                endCoordinates[1] <= startCoordinates[1] + MathSuits.EPSILON)
            return Math.abs(p.getCoordinate(1) - startCoordinates[1]);

        double x1 = startCoordinates[0];
        double x2 = endCoordinates[0];
        double x0 = p.getCoordinate(0);
        double y1 = startCoordinates[1];
        double y2 = endCoordinates[1];
        double y0 = p.getCoordinate(1);

        return Math.abs((x2 - x1) * (y1 - y0) - (x1 - x0) * (y2 - y1)) / (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
    }

    public double getRelativeMinimumDistance(PointShape p) {
        assert getDimension() == 2;
        double[] startCoordinates = this.getStartCoordinates();
        double[] endCoordinates = this.getEndCoordinates();

        if (endCoordinates[0] >= startCoordinates[0] - MathSuits.EPSILON &&
                endCoordinates[0] <= startCoordinates[0] + MathSuits.EPSILON) {
            if (startCoordinates[1] < endCoordinates[1]) return startCoordinates[0] - p.getCoordinate(0);
            if (startCoordinates[1] >= endCoordinates[1]) return p.getCoordinate(0) - startCoordinates[0];
        }

        if (endCoordinates[1] >= startCoordinates[1] - MathSuits.EPSILON &&
                endCoordinates[1] <= startCoordinates[1] + MathSuits.EPSILON) {
            if (startCoordinates[0] < endCoordinates[0]) return p.getCoordinate(1) - startCoordinates[1];
            if (startCoordinates[0] >= endCoordinates[0]) return startCoordinates[1] - p.getCoordinate(1);
        }

        double x1 = startCoordinates[0];
        double x2 = endCoordinates[0];
        double x0 = p.getCoordinate(0);
        double y1 = startCoordinates[1];
        double y2 = endCoordinates[1];
        double y0 = p.getCoordinate(1);

        return ((x1 - x0) * (y2 - y1) - (x2 - x1) * (y1 - y0)) / (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
    }

    @Override
    public int getDimension() {
        return Math.min(this.getStartPoint().getDimension(), this.getEndPoint().getDimension());
    }

    @Override
    public Envelope getMBR() {
        double[] startCoordinates = this.getStartCoordinates();
        double[] endCoordinates = this.getEndCoordinates();
        int dims = this.getDimension();
        double[] low = new double[dims];
        double[] high = new double[dims];
        for (int cDim = 0; cDim < dims; ++cDim) {
            low[cDim] = Math.min(startCoordinates[cDim], endCoordinates[cDim]);
            high[cDim] = Math.max(startCoordinates[cDim], endCoordinates[cDim]);
        }

        return Geom3DSuits.createEnvelope(low, high);
    }

    public double getRelativeMaximumDistance(RegionShape r) {
        assert this.getDimension() == 2;
        // clockwise.
        double d1 = this.getRelativeMinimumDistance(ShapeSuits.createPoint(r.getLowCoordinates()));

        double[] coords = new double[this.getDimension()];
        coords[0] = r.getLowOrdinate(0);
        coords[1] = r.getHighOrdinate(1);
        double d2 = getRelativeMinimumDistance(ShapeSuits.createPoint(coords));

        double d3 = getRelativeMinimumDistance(ShapeSuits.createPoint(r.getHighCoordinates()));

        coords[0] = r.getHighOrdinate(0);
        coords[1] = r.getLowCoordinates()[1];
        double d4 = getRelativeMinimumDistance(ShapeSuits.createPoint(coords));

        return Math.max(d1, Math.max(d2, Math.max(d3, d4)));
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public double getMinimumDistance(Shape in) {
        if (in instanceof PointShape) {
            return this.getMinimumDistance((PointShape) in);
        }
        if (in instanceof RegionShape) {
            assert false;

        }
        return Double.MAX_VALUE;
    }

    public double getAngleOfPerpendicularRay() {
        assert this.getDimension() == 2;
        double[] startCoordinates = this.getStartCoordinates();
        double[] endCoordinates = this.getEndCoordinates();

        if (startCoordinates[0] >= endCoordinates[0] - MathSuits.EPSILON &&
                startCoordinates[0] <= endCoordinates[0] + MathSuits.EPSILON) return 0.0;

        if (startCoordinates[1] >= endCoordinates[1] - MathSuits.EPSILON &&
                startCoordinates[1] <= endCoordinates[1] + MathSuits.EPSILON) return MathSuits.M_PI_2;

        return Math.atan(-(startCoordinates[0] - endCoordinates[0]) / (startCoordinates[1] - endCoordinates[1]));

    }

    @Override
    public Object clone() {
        return new LineSegmentShape(getStartPoint(), getEndPoint());
    }

    public void makeInfinite(int dimension) {
        this.makeDimension(dimension);
        double[] startCoordinates = this.getStartCoordinates();
        double[] endCoordinates = this.getEndCoordinates();
        for (int cIndex = 0; cIndex < dimension; ++cIndex) {
            startCoordinates[cIndex] = Double.MAX_VALUE;
            endCoordinates[cIndex] = Double.MAX_VALUE;
        }
    }

    public void makeDimension(int dimension) {
        if (dimension != this.getDimension()) {
            getStartPoint().makeDimension(dimension);
            getEndPoint().makeDimension(dimension);
        }
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof LineSegmentShape) {
            LineSegmentShape lss = (LineSegmentShape) i;
            this.reset(lss.getStartCoordinates(), lss.getEndCoordinates());
        }
    }

    public double[] getStartCoordinates() {
        return super.getStartPoint().getCoordinates();
    }

    public double[] getEndCoordinates() {
        return super.getEndPoint().getCoordinates();
    }

    public double getStartCoordinate(int i) {
        return super.getStartPoint().getOrdinate(i);
    }

    public double getEndCoordinate(int i) {
        return super.getEndPoint().getOrdinate(i);
    }

    @Override
    public Vector getCenter() {
        return null;
    }
}
