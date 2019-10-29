package cn.edu.cug.cs.gtl.geom;

/**
 * Created by ZhenwenHe on 2017/3/13.
 * V0V1==V0V2
 */
public class IsoscelesTriangle extends TriangleImpl {
    private static final long serialVersionUID = 1L;

    public IsoscelesTriangle() {
    }

    public IsoscelesTriangle(Vector[] vertices) {
        super(vertices);
    }

    public IsoscelesTriangle(Vector v0, Vector v1, Vector v2) {
        super(v0, v1, v2);
    }


}
