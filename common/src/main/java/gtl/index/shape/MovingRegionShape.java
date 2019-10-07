package gtl.index.shape;

import gtl.geom.Envelope;
import gtl.geom.Interval;
import gtl.geom.Vector;

/**
 * Created by ZhenwenHe on 2016/12/22.
 */
public class MovingRegionShape extends TimeRegionShape implements EvolvingShape {
    private static final long serialVersionUID = 1L;

    public MovingRegionShape(int dim, Interval i) {
        super(dim, i);
    }

    public MovingRegionShape(double[] low, double[] high, Interval i) {
        super(low, high, i);
    }

    public MovingRegionShape(Envelope e, Interval i) {
        super(e, i);
    }

    public MovingRegionShape(Vector leftBottom, Vector rightTop, Interval i) {
        super(leftBottom, rightTop, i);
    }

    @Override
    public RegionShape getVMBR() {
        return null;
    }

    @Override
    public RegionShape getMBRAtTime(double t) {
        return null;
    }
}