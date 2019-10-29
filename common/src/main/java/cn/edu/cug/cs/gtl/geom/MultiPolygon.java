package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

/**
 * Models a collection of {@link Polygon}s.
 * <p>
 * As per the OGC SFS specification,
 * the Polygons in a MultiPolygon may not overlap,
 * and may only touch at single points.
 * This allows the topological point-set semantics
 * to be well-defined.
 *
 * @version 1.7
 */
public class MultiPolygon extends GeometryCollection implements Polygonal {
    private static final long serialVersionUID = 1L;

    public MultiPolygon(Polygon[] geometries) {
        super(geometries);
        this.geometryType = MULTIPOLYGON;
    }

    public MultiPolygon() {
        super();
        this.geometryType = MULTIPOLYGON;
    }

    public MultiPolygon(int dim) {
        super(dim);
        this.geometryType = MULTIPOLYGON;
    }

    @Override
    public MultiPolygon clone() {
        MultiPolygon p = new MultiPolygon();
        p.copyFrom(this);
        return p;
    }

    @Override
    public TextureParameter getTextureParameter() {
        return null;
    }

    @Override
    public void setTextureParameter(TextureParameter textureParameter) {

    }
}



