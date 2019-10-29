package cn.edu.cug.cs.gtl.geom;

public class HexahedralMesh extends PolyhedralMesh {

    private static final long serialVersionUID = -5705599883721052654L;

    public HexahedralMesh() {
    }

    public HexahedralMesh(int dim) {
        super(dim);
    }

    public HexahedralMesh(VectorSequence vectorSequence, int[] indices) {
        super(vectorSequence, indices);
    }

    @Override
    public int getGeometryType() {
        return HEXAHEDRALMESH;
    }

    @Override
    public int getVertexNumberPerCell() {
        return 8;
    }
}
