package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

/**
 * Models a collection of {@link LineString}s.
 * <p>
 * Any collection of LineStrings is a valid MultiLineString.
 *
 * @version 1.7
 */
public class MultiLineString extends GeometryCollection implements Lineal {
    private static final long serialVersionUID = 1L;


    public MultiLineString(LineString[] geometries) {
        super(geometries);
        this.geometryType = MULTILINESTRING;
    }


    public MultiLineString() {
        super();
        this.geometryType = MULTILINESTRING;
    }

    public MultiLineString(int dim) {
        super(dim);
        this.geometryType = MULTILINESTRING;
    }

    @Override
    public MultiLineString clone() {
        MultiLineString p = new MultiLineString();
        p.copyFrom(this);
        return p;
    }
}

