package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

import java.util.ArrayList;

/**
 * Models a collection of {@link Point}s.
 * <p>
 * Any collection of Points is a valid MultiPoint.
 *
 * @version 1.7
 */
public class MultiPoint extends GeometryCollection implements Puntal {

    private static final long serialVersionUID = 1L;


    public MultiPoint(Point[] gs) {
        super(gs);
        this.geometryType = MULTIPOINT;
    }

    public MultiPoint(ArrayList<Point> gs) {
        this.geometries = new ArrayList<Geometry>(gs.size());
        int i = 0;
        for (Geometry g : gs) {
            this.geometries.set(i, g);
            i++;
            this.envelope.combine(g.getEnvelope());
        }
        this.geometryType = MULTIPOINT;
    }

    public MultiPoint() {
        this.geometryType = MULTIPOINT;
    }

    public MultiPoint(int dim) {
        super(dim);
        this.geometryType = MULTIPOINT;
    }

    //没有克隆成功
    @Override
    public MultiPoint clone() {
        MultiPoint p = new MultiPoint();
        p.copyFrom(this);
        return p;
    }

    public Point getPoint(int n) {
        return (Point) getGeometry(n);
    }
}

