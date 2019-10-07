package gtl.geom;

public class QuadMesh extends IndexedPolygon {
    private static final long serialVersionUID = 2189671361958541058L;


    public QuadMesh(VectorSequence coordinates, int[] indices) {
        super(coordinates, indices);
    }

    public QuadMesh(int dim) {
        super(dim);
    }

    public QuadMesh() {
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}
