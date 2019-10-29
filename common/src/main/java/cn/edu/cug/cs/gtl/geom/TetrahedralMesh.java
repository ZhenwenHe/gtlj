package cn.edu.cug.cs.gtl.geom;


/**
 * 参照Geo3dML
 */
public class TetrahedralMesh extends PolyhedralMesh {
    public TetrahedralMesh() {
    }

    @Override
    public int getGeometryType() {
        return TETRAHEDRALMESH;
    }

    @Override
    public int getVertexNumberPerCell() {
        return 4;
    }

}
