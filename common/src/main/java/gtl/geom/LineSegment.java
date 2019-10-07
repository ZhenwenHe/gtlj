package gtl.geom;

/**
 * Created by ZhenwenHe on 2017/3/13.
 */
public class LineSegment extends InfiniteLine {
    private static final long serialVersionUID = 1L;

    public LineSegment(Vector startPoint, Vector endPoint) {
        super(startPoint, endPoint);
    }

    public LineSegment(double[] startPoint, double[] endPoint) {
        super(startPoint, endPoint);
    }

    public LineSegment() {
        super();
    }

    @Override
    public Object clone() {
        return new LineSegment(this.startPoint, this.endPoint);
    }


    /**
     * extend to line
     *
     * @return
     */
    public InfiniteLine extend() {
        return new InfiniteLine(startPoint, endPoint);
    }

    public LineSegment flap() {
        Vector2D s = startPoint.flap();
        Vector2D e = endPoint.flap();
        if (s.equals(e)) {
            return null;
        }
        return new LineSegment(s, e);
    }
}
