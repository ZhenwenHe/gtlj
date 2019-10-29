package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by hadoop on 18-9-1
 */
public class TriangleMesh extends IndexedPolygon {

    private static final long serialVersionUID = 3253371829643430267L;

    public TriangleMesh(VectorSequence coordinates, int[] indices) {
        super(coordinates, indices);
    }

    public TriangleMesh(int dim) {
        super(dim);
    }

    public TriangleMesh() {
    }

    @Override
    public TriangleMesh clone() {
        TriangleMesh t = (TriangleMesh) super.clone();
        return t;
    }
}
