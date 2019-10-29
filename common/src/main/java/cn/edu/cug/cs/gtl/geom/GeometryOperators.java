package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.jts.geom.GeometryOperatorsImpl;
import cn.edu.cug.cs.gtl.jts.geom.GeometryOperatorsImpl;

public interface GeometryOperators {
    double distance(Geometry g);

    boolean disjoint(Geometry g);

    boolean touches(Geometry g);

    boolean intersects(Geometry g);

    boolean crosses(Geometry g);

    boolean within(Geometry g);

    boolean contains(Geometry g);

    boolean overlaps(Geometry g);

    boolean covers(Geometry g);

    boolean coveredBy(Geometry g);

    Geometry buffer(double distance);

    Geometry convexHull();

    Geometry intersection(Geometry other);

    Geometry union(Geometry other);

    Geometry difference(Geometry other);

    Geometry symDifference(Geometry other);

    static GeometryOperators create(Geometry g) {
        return new GeometryOperatorsImpl(g);
    }
}
