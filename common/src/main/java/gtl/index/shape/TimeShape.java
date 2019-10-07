package gtl.index.shape;

import gtl.geom.Interval;

/**
 * Created by ZhenwenHe on 2016/12/8.
 */
public interface TimeShape extends Interval {
    boolean intersectsShapeInTime(TimeShape in);

    boolean intersectsShapeInTime(Interval ivI, TimeShape in);

    boolean containsShapeInTime(TimeShape in);

    boolean containsShapeInTime(Interval ivI, TimeShape in);

    boolean touchesShapeInTime(TimeShape in);

    boolean touchesShapeInTime(Interval ivI, TimeShape in);

    double getAreaInTime();

    double getAreaInTime(Interval ivI);

    double getIntersectingAreaInTime(TimeShape r);

    double getIntersectingAreaInTime(Interval ivI, TimeShape r);
}
