package gtl.index.shape;

import gtl.geom.Interval;
import gtl.geom.IntervalType;

/**
 * Created by ZhenwenHe on 2016/12/22.
 */
public class TimePointShape extends PointShape implements TimeShape {
    private static final long serialVersionUID = 1L;

    protected double startTime;
    protected double endTime;

    @Override
    public double getLowerBound() {
        return startTime;
    }

    @Override
    public double getUpperBound() {

        return endTime;
    }

    @Override
    public boolean intersectsShapeInTime(TimeShape in) {
        return false;
    }


    @Override
    public void setBounds(double l, double u) {
        this.startTime = l;
        this.endTime = u;
    }

    @Override
    public boolean intersectsShapeInTime(Interval ivI, TimeShape in) {
        return false;
    }

    @Override
    public boolean intersects(Interval i) {
        if (i == null) return false;
        return intersects(i.getType(), i.getLowerBound(), i.getUpperBound());
    }

    @Override
    public boolean containsShapeInTime(TimeShape in) {
        return false;
    }

    @Override
    public boolean intersects(IntervalType type, double start, double end) {
        if (startTime >= end || endTime <= start)
            return false;
        return true;
    }

    @Override
    public boolean containsShapeInTime(Interval ivI, TimeShape in) {
        return false;
    }

    @Override
    public boolean contains(Interval i) {
        return false;
    }

    @Override
    public IntervalType getType() {
        return null;
    }

    @Override
    public boolean touchesShapeInTime(TimeShape in) {
        return false;
    }

    @Override
    public void reset(IntervalType type, double start, double end) {

    }

    @Override
    public boolean touchesShapeInTime(Interval ivI, TimeShape in) {
        return false;
    }

    @Override
    public double getAreaInTime() {
        return 0;
    }

    @Override
    public double getAreaInTime(Interval ivI) {
        return 0;
    }

    @Override
    public double getIntersectingAreaInTime(TimeShape r) {
        return 0;
    }

    @Override
    public double getIntersectingAreaInTime(Interval ivI, TimeShape r) {
        return 0;
    }

    @Override
    public boolean lowerClosed() {
        return false;
    }

    @Override
    public boolean upperClosed() {
        return false;
    }

    @Override
    public boolean equals(Interval q) {
        return false;
    }

    @Override
    public boolean starts(Interval q) {
        return false;
    }

    @Override
    public boolean startedBy(Interval q) {
        return false;
    }

    @Override
    public boolean meets(Interval q) {
        return false;
    }

    @Override
    public boolean metBy(Interval q) {
        return false;
    }

    @Override
    public boolean finishes(Interval q) {
        return false;
    }

    @Override
    public boolean finishedBy(Interval q) {
        return false;
    }

    @Override
    public boolean before(Interval q) {
        return false;
    }

    @Override
    public boolean after(Interval q) {
        return false;
    }

    @Override
    public boolean overlaps(Interval q) {
        return false;
    }

    @Override
    public boolean overlappedBy(Interval q) {
        return false;
    }

    @Override
    public boolean during(Interval q) {
        return false;
    }

    @Override
    public boolean covers(Interval q) {
        return false;
    }

    @Override
    public boolean coveredBy(Interval q) {
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
