package cn.edu.cug.cs.gtl.jts.geom;

import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.geom.GeometryOperators;
import cn.edu.cug.cs.gtl.jts.JTSWrapper;

public class GeometryOperatorsImpl implements GeometryOperators {
    Geometry thisGeometry;

    public GeometryOperatorsImpl(Geometry g) {
        thisGeometry = g;
    }

    @Override
    public double distance(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .distance(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public boolean disjoint(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .disjoint(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public boolean touches(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .touches(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public boolean intersects(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .intersects(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public boolean crosses(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .crosses(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public boolean within(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .within(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public boolean contains(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .contains(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public boolean overlaps(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .overlaps(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public boolean covers(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .covers(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public boolean coveredBy(Geometry g) {
        return JTSWrapper
                .toJTSGeometry(thisGeometry)
                .coveredBy(JTSWrapper.toJTSGeometry(g));
    }

    @Override
    public Geometry buffer(double distance) {
        return JTSWrapper.toGTLGeometry(
                JTSWrapper
                        .toJTSGeometry(thisGeometry)
                        .buffer(distance));
    }

    @Override
    public Geometry convexHull() {
        return JTSWrapper.toGTLGeometry(
                JTSWrapper
                        .toJTSGeometry(thisGeometry)
                        .convexHull());
    }

    @Override
    public Geometry intersection(Geometry other) {
        return JTSWrapper.toGTLGeometry(
                JTSWrapper
                        .toJTSGeometry(thisGeometry)
                        .intersection(JTSWrapper.
                                toJTSGeometry(other)));
    }

    @Override
    public Geometry union(Geometry other) {
        return JTSWrapper.toGTLGeometry(
                JTSWrapper
                        .toJTSGeometry(thisGeometry)
                        .union(JTSWrapper.
                                toJTSGeometry(other)));
    }

    @Override
    public Geometry difference(Geometry other) {
        return JTSWrapper.toGTLGeometry(
                JTSWrapper
                        .toJTSGeometry(thisGeometry)
                        .difference(JTSWrapper.
                                toJTSGeometry(other)));
    }

    @Override
    public Geometry symDifference(Geometry other) {
        return JTSWrapper.toGTLGeometry(
                JTSWrapper
                        .toJTSGeometry(thisGeometry)
                        .symDifference(JTSWrapper.
                                toJTSGeometry(other)));
    }
}
