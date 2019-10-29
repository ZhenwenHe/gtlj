package cn.edu.cug.cs.gtl.geom;

import java.util.ArrayList;

public class MultiSolid extends GeometryCollection implements Polyhedral {
    private static final long serialVersionUID = 1L;

    public MultiSolid(Solid[] geometries) {
        super(geometries);
        this.geometryType = MULTISOLID;
    }

    public MultiSolid() {
        super();
        this.geometryType = MULTISOLID;
    }

    public MultiSolid(int dim) {
        super(dim);
        this.geometryType = MULTISOLID;
    }

    @Override
    public MultiSolid clone() {
        MultiSolid p = new MultiSolid();
        p.copyFrom(this);
        return p;
    }

    @Override
    public ArrayList<Geometry> getGeometries() {
        return super.getGeometries();
    }

    @Override
    public TextureParameter getTextureParameter() {
        return null;
    }

    @Override
    public void setTextureParameter(TextureParameter textureParameter) {

    }
}
